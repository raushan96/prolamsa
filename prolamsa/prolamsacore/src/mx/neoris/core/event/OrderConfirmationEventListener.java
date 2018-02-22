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

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.events.OrderPlacedEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.BaseStoreModel;

import javax.annotation.Resource;

import mx.neoris.core.orders.NeorisHSSOrderSplitter;

import org.springframework.beans.factory.annotation.Required;


/**
 * Event listener for order confirmation functionality.
 */
public class OrderConfirmationEventListener extends AbstractSiteEventListener<OrderPlacedEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Resource(name = "neorisHSSOrderSplitter")
	private NeorisHSSOrderSplitter neorisHSSOrderSplitter;

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
	protected void onSiteEvent(final OrderPlacedEvent orderPlacedEvent)
	{
		final OrderModel orderModel = orderPlacedEvent.getProcess().getOrder();
		if (orderModel.getStatus() != OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID)
		{
			final OrderProcessModel orderProcessModel = (OrderProcessModel) getBusinessProcessService().createProcess(
					"b2bOrderConfirmationEmailProcess" + "-" + orderModel.getCode() + "-" + System.currentTimeMillis(),
					"b2bOrderConfirmationEmailProcess");
			orderProcessModel.setOrder(orderModel);
			getModelService().save(orderProcessModel);
			getBusinessProcessService().startProcess(orderProcessModel);
		}
		final BaseStoreModel baseStoreModel = orderModel.getStore();
		if (baseStoreModel.getHSSFunctionaliatyEnabled() != null && baseStoreModel.getHSSFunctionaliatyEnabled() == true)
		{
			final OrderModel hssOrderModel = orderModel.getHssOrder();

			if (hssOrderModel != null && hssOrderModel.getStatus() != OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID)
			{
				final OrderProcessModel hssOrderProcessModel = (OrderProcessModel) getBusinessProcessService().createProcess(
						"b2bOrderConfirmationEmailProcess" + "-" + hssOrderModel.getCode() + "-" + System.currentTimeMillis(),
						"b2bOrderConfirmationEmailProcess");
				hssOrderProcessModel.setOrder(hssOrderModel);
				getModelService().save(hssOrderProcessModel);
				getBusinessProcessService().startProcess(hssOrderProcessModel);
			}

		}


	}

	@Override
	protected boolean shouldHandleEvent(final OrderPlacedEvent event)
	{
		final OrderModel order = event.getProcess().getOrder();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order", order);
		final BaseSiteModel site = order.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return SiteChannel.B2B.equals(site.getChannel());
	}
}
