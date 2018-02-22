/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.core.event;


import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.model.QuoteRequestedProcessModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointQuoteNegotiationWebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * A listener of {@link de.hybris.platform.b2b.event.QuoteApprovedEvent} which is fired when a quote is approved by a
 * manager
 */
public class QuoteRequestedEventListener extends AbstractSiteEventListener<QuoteRequestedEvent>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BusinessProcessService businessProcessService;
	private ModelService modelService;

	private static final Logger LOG = Logger.getLogger(QuoteRequestedEventListener.class);

	@Resource(name = "neorisSharePointQuoteNegotiationWebService")
	private NeorisSharePointQuoteNegotiationWebService neorisSharePointQuoteNegotiationWebService;

	@Resource(name = "neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrdersCreator;

	public static final Integer MAX_STACK_TRACE_SIZE = 4096;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	protected void onSiteEvent(final QuoteRequestedEvent event)
	{
		final OrderModel quoteOrder = event.getOrder();

		boolean sendEmailConfirmation = true;

		try
		{
			// publishing quote to SAP
			neorisSapOrdersCreator.createSapQuoteFromOrder(quoteOrder);
		}
		catch (final Exception e)
		{
			LOG.error("Error publishing quote request: " + quoteOrder.getCode() + " to SAP");
			sendEmailConfirmation = false;

			quoteOrder.setStatus(OrderStatus.QUOTE_PENDING_SAP_ID);
			quoteOrder.setSapExceptionMessage(e.getMessage());
			modelService.save(quoteOrder);
		}

		// publishing negotiation quote to Sharepoint
		if (quoteOrder.getHasNegPrices())
		{
			try
			{
				final String negotiationSharePointId = neorisSharePointQuoteNegotiationWebService
						.placeQuoteNegotiationToSharePoint(quoteOrder);

				if (StringUtils.isNotBlank(negotiationSharePointId))
				{
					LOG.info("Quote negotation: " + quoteOrder.getCode() + " published on Sharepoint with id: "
							+ negotiationSharePointId);
					quoteOrder.setSharePointId(negotiationSharePointId);
				}
			}
			catch (final Exception e)
			{
				sendEmailConfirmation = false;

				final String message = e.getMessage().substring(0, Math.min(MAX_STACK_TRACE_SIZE, e.getMessage().length() - 1));
				quoteOrder.setSharePointErrorTrace(message);

				LOG.error("Error publishing negotiation to Sharepoint due to: " + e.getMessage());
			}
		}

		if (sendEmailConfirmation)
		{
			final QuoteRequestedProcessModel quoteRequestedProcess = (QuoteRequestedProcessModel) getBusinessProcessService()
					.createProcess("quoteRequestedEmailProcess" + "-" + quoteOrder.getCode() + "-" + System.currentTimeMillis(),
							"quoteRequestedEmailProcess");

			quoteRequestedProcess.setOrder(quoteOrder);

			quoteRequestedProcess.setSite(event.getSite());
			quoteRequestedProcess.setStore(event.getBaseStore());
			quoteRequestedProcess.setCustomer(event.getCustomer());
			quoteRequestedProcess.setLanguage(event.getLanguage());
			quoteRequestedProcess.setCurrency(event.getCurrency());

			getModelService().save(quoteRequestedProcess);
			getBusinessProcessService().startProcess(quoteRequestedProcess);

		}
		//		final B2BCommentModel comment = getLatestCommentByUser(quoteOrder, manager);
		//		createSnapshot(quoteOrder, comment, manager, OrderStatus.CREATED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.commerceservices.event.AbstractSiteEventListener#shouldHandleEvent(de.hybris.platform.servicelayer
	 * .event.events.AbstractEvent)
	 */
	@Override
	protected boolean shouldHandleEvent(final QuoteRequestedEvent paramT)
	{
		// YTODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.event.ClusterAwareEvent#publish(int, int)
	 */

}
