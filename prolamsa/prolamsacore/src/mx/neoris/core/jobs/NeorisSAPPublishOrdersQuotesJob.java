/**
 *
 */
package mx.neoris.core.jobs;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.neoris.core.model.LostOrderJobModel;
import mx.neoris.core.model.QuoteRequestedProcessModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointQuoteNegotiationWebService;


/**
 *
 */
public class NeorisSAPPublishOrdersQuotesJob extends AbstractJobPerformable<LostOrderJobModel>
{
	private static final Logger LOG = Logger.getLogger(NeorisSAPPublishOrdersQuotesJob.class);

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "neorisSharePointQuoteNegotiationWebService")
	private NeorisSharePointQuoteNegotiationWebService neorisSharePointQuoteNegotiationWebService;

	@Resource(name = "neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrdersCreator;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "businessProcessService")
	private BusinessProcessService businessProcessService;

	public static final Integer MAX_STACK_TRACE_SIZE = 4096;

	@Override
	public PerformResult perform(final LostOrderJobModel cronJobModel)
	{
		LOG.info("Starting job");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		final Date lastSuccessFullDate = cronJobModel.getLastSuccessfulDate();
		String lastSuccessFullDateString = null;
		if (null == lastSuccessFullDate)
		{
			lastSuccessFullDateString = sdf.format(new Date());
		}
		else
		{
			lastSuccessFullDateString = sdf.format(lastSuccessFullDate);
		}

		final String query = "SELECT {o.pk} FROM {Order as o JOIN OrderStatus as oStatus ON  {o.status}={oStatus.pk} } "
				+ "WHERE ({oStatus.code}='ORDER_APPROVED_AND_PENDING_SAP_ID' "
				+ "OR {oStatus.code}='ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID' " + "OR {oStatus.code}='QUOTE_PENDING_SAP_ID')"
				+ "AND {o.date}>=" + "'" + lastSuccessFullDateString + "'";

		LOG.info("Final Query to Push the Order :" + query);

		final SearchResult<OrderModel> searchResult = flexibleSearchService.search(new FlexibleSearchQuery(query));

		if (searchResult != null && searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			for (final OrderModel eachOrderModel : searchResult.getResult())
			{
				final OrderStatus status = eachOrderModel.getStatus();

				if (status.getCode().equalsIgnoreCase(OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID.getCode()))
				{
					try
					{
						neorisSapOrdersCreator.createSapOrderFor(eachOrderModel);

						final OrderProcessModel orderProcessModel = (OrderProcessModel) businessProcessService.createProcess(
								"b2bOrderConfirmationEmailProcess" + "-" + eachOrderModel.getCode() + "-" + System.currentTimeMillis(),
								"b2bOrderConfirmationEmailProcess");
						orderProcessModel.setOrder(eachOrderModel);
						modelService.save(orderProcessModel);
						businessProcessService.startProcess(orderProcessModel);

						eachOrderModel.setStatus(OrderStatus.APPROVED);
						modelService.save(eachOrderModel);
					}
					catch (final Exception e)
					{
						eachOrderModel.setStatus(OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID);
						eachOrderModel.setSapExceptionMessage(e.getMessage());
						modelService.save(eachOrderModel);

						LOG.error("Error while publishing to SAP order: " + eachOrderModel.getCode() + " error message"
								+ e.getStackTrace().toString());

						return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
					}

				}
				else if (status.getCode().equalsIgnoreCase(OrderStatus.ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID.getCode()))
				{

					try
					{
						neorisSapOrdersCreator.createSapOrderFor(eachOrderModel);

						final OrderProcessModel orderProcessModel = (OrderProcessModel) businessProcessService.createProcess(
								"orderPendingApprovalEmailProcess" + "-" + eachOrderModel.getCode() + "-" + System.currentTimeMillis(),
								"orderPendingApprovalEmailProcess");
						orderProcessModel.setOrder(eachOrderModel);
						modelService.save(orderProcessModel);
						businessProcessService.startProcess(orderProcessModel);

						eachOrderModel.setStatus(OrderStatus.PENDING_APPROVAL);
						modelService.save(eachOrderModel);
					}
					catch (final Exception e)
					{
						eachOrderModel.setStatus(OrderStatus.ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID);
						eachOrderModel.setSapExceptionMessage(e.getMessage());
						modelService.save(eachOrderModel);

						LOG.error("Error while publishing to SAP order: " + eachOrderModel.getCode() + " error message"
								+ e.getStackTrace().toString());
						return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
					}


				}
				else if (status.getCode().equalsIgnoreCase(OrderStatus.QUOTE_PENDING_SAP_ID.getCode()))
				{
					boolean sendEmailConfirmation = true;
					try
					{
						neorisSapOrdersCreator.createSapOrderFor(eachOrderModel);
					}
					catch (final Exception e)
					{
						eachOrderModel.setStatus(OrderStatus.QUOTE_PENDING_SAP_ID);
						eachOrderModel.setSapExceptionMessage(e.getMessage());
						modelService.save(eachOrderModel);

						sendEmailConfirmation = false;

						LOG.error("Error while publishing to SAP order: " + eachOrderModel.getCode() + " error message"
								+ e.getStackTrace().toString());
					}


					// publishing negotiation quote to Sharepoint
					if (eachOrderModel.getHasNegPrices())
					{
						try
						{
							final String negotiationSharePointId = neorisSharePointQuoteNegotiationWebService
									.placeQuoteNegotiationToSharePoint(eachOrderModel);

							if (StringUtils.isNotBlank(negotiationSharePointId))
							{
								LOG.info("Quote negotation: " + eachOrderModel.getCode() + " published on Sharepoint with id: "
										+ negotiationSharePointId);
								eachOrderModel.setSharePointId(negotiationSharePointId);
							}
						}
						catch (final Exception e)
						{
							sendEmailConfirmation = false;

							final String message = e.getMessage().substring(0,
									Math.min(MAX_STACK_TRACE_SIZE, e.getMessage().length() - 1));
							eachOrderModel.setSharePointErrorTrace(message);

							LOG.error("Error publishing negotiation to Sharepoint due to: " + e.getMessage());
						}
					}

					if (sendEmailConfirmation)
					{
						final QuoteRequestedProcessModel quoteRequestedProcess = (QuoteRequestedProcessModel) businessProcessService
								.createProcess(
										"quoteRequestedEmailProcess" + "-" + eachOrderModel.getCode() + "-" + System.currentTimeMillis(),
										"quoteRequestedEmailProcess");

						quoteRequestedProcess.setOrder(eachOrderModel);

						quoteRequestedProcess.setSite(eachOrderModel.getSite());
						quoteRequestedProcess.setStore(eachOrderModel.getStore());
						quoteRequestedProcess.setCustomer((CustomerModel) eachOrderModel.getUser());
						quoteRequestedProcess.setLanguage(eachOrderModel.getLanguage());
						quoteRequestedProcess.setCurrency(eachOrderModel.getCurrency());

						modelService.save(quoteRequestedProcess);
						businessProcessService.startProcess(quoteRequestedProcess);

						eachOrderModel.setStatus(OrderStatus.PENDING_QUOTE);
						modelService.save(eachOrderModel);

					}


				}

			}
		}

		cronJobModel.setLastSuccessfulDate(new Date());
		modelService.save(cronJobModel);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);

	}

	@Override
	public boolean isAbortable()
	{
		return true;
	}
}
