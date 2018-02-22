/**
 * 
 */
package mx.neoris.core.jobs;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.services.NeorisOrderHistoryService;

import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class NeorisOrderAttachmentsUpdaterJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(NeorisOrderAttachmentsUpdaterJob.class);

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisOrderHistoryService")
	private NeorisOrderHistoryService neorisOrderHistoryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		try
		{
			final List<OrderModel> ordersAndQuotes = neorisOrderHistoryService.getAllOrdersAndQuotes();

			for (final OrderModel orderModel : ordersAndQuotes)
			{
				LOG.info("Updating atachments of order " + orderModel.getCode());

				final NeorisMediaModel attachment = orderModel.getAttachedPO();

				if (attachment == null)
				{
					LOG.info("Order " + orderModel.getCode() + " has not attachments");
					continue;
				}

				// migrating attachment to be saved in attachment list
				final List<NeorisMediaModel> attachmentsList = new ArrayList<NeorisMediaModel>();
				attachmentsList.add(attachment);

				orderModel.setAttachmentsPO(attachmentsList);

				modelService.save(orderModel);
			}
		}
		catch (final Exception e)
		{
			LOG.error("Error while migrating order attachments due to: " + e.getMessage());
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
}
