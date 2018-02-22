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
package mx.neoris.core.actions;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;

import javax.annotation.Resource;

import mx.neoris.core.orders.NeorisHSSOrderSplitter;
import mx.neoris.core.orders.NeorisSapOrderCreator;

import org.apache.log4j.Logger;


/**
 * This action implements payment authorization using {@link CreditCardPaymentInfoModel}. Any other payment model could
 * be implemented here, or in a separate action, if the process flow differs.
 */
public class PublishPendingApprovalOrderToSAPAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(PublishPendingApprovalOrderToSAPAction.class);

	@Resource(name = "neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrdersCreator;

	@Resource(name = "neorisHSSOrderSplitter")
	private NeorisHSSOrderSplitter neorisHSSOrderSplitter;

	@Override
	public Transition executeAction(final OrderProcessModel process)
	{
		final OrderModel orderModel = process.getOrder();

		boolean transitionOK = false;

		if (orderModel == null)
		{
			return Transition.NOK;
		}

		try
		{
			neorisSapOrdersCreator.createSapOrderFor(orderModel);
			transitionOK = true;
		}
		catch (final Exception e)
		{
			orderModel.setStatus(OrderStatus.ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID);
			orderModel.setSapExceptionMessage(e.getMessage());
			modelService.save(orderModel);

			LOG.error("Error while publishing to SAP order: " + orderModel.getCode() + " error message"
					+ e.getStackTrace().toString());
		}

		if (orderModel.getHssOrder() != null)
		{
			final OrderModel hssOrderModel = orderModel.getHssOrder();

			try
			{
				neorisSapOrdersCreator.createSapOrderFor(hssOrderModel);
				transitionOK = true;
			}
			catch (final Exception e)
			{
				hssOrderModel.setStatus(OrderStatus.ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID);
				hssOrderModel.setSapExceptionMessage(e.getMessage());
				modelService.save(hssOrderModel);

				LOG.error("Error while publishing to SAP  HSS order: " + hssOrderModel.getCode() + " error message" + e.getMessage());
			}
		}

		return transitionOK ? Transition.OK : Transition.NOK;
	}
}
