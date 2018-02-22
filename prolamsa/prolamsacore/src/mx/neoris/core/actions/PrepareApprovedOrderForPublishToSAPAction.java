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
import de.hybris.platform.store.BaseStoreModel;

import javax.annotation.Resource;

import mx.neoris.core.orders.NeorisHSSOrderSplitter;

import org.apache.log4j.Logger;


/**
 * This action implements payment authorization using {@link CreditCardPaymentInfoModel}. Any other payment model could
 * be implemented here, or in a separate action, if the process flow differs.
 */
public class PrepareApprovedOrderForPublishToSAPAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(PrepareApprovedOrderForPublishToSAPAction.class);

	@Resource(name = "neorisHSSOrderSplitter")
	private NeorisHSSOrderSplitter neorisHSSOrderSplitter;

	@Override
	public Transition executeAction(final OrderProcessModel process)
	{
		final OrderModel orderModel = process.getOrder();

		if (orderModel == null)
		{
			return Transition.NOK;
		}

		orderModel.setStatus(OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID);
		modelService.save(orderModel);

		final BaseStoreModel baseStoreModel = orderModel.getStore();
		if (baseStoreModel.getHSSFunctionaliatyEnabled() != null && baseStoreModel.getHSSFunctionaliatyEnabled() == true)
		{
			neorisHSSOrderSplitter.split(orderModel);
		}

		return Transition.OK;
	}
}
