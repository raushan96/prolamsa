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
package mx.neoris.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;

import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.model.QuoteRequestedProcessModel;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisProductFacade;

import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Required;


/**
 * Context (velocity) for email order notification.
 */
public class QuoteNotificationEmailContext extends CustomerEmailContext
{
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;
	
	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	
	@Resource(name="neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	@Override
	public void init(final StoreFrontCustomerProcessModel model, final EmailPageModel emailPageModel)
	{
		super.init(model, emailPageModel);
		
		if (model instanceof QuoteRequestedProcessModel)
		{
			QuoteRequestedProcessModel quoteRequestedModel = (QuoteRequestedProcessModel) model;
			orderData = getOrderConverter().convert(quoteRequestedModel.getOrder());
			neorisProductFacade.injectCustomerNameAndDescriptionDataOnEmail(orderData.getEntries(),quoteRequestedModel.getOrder().getUnit());
		}
		
		internalPut("numberTool", new NumberTool());
		internalPut("dateTool", new DateTool());
				
		if(orderData != null)
		{
			if (orderData.getEntries() != null && !orderData.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : orderData.getEntries())
				{
					final Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(orderData.getUnitWhenPlaced(), entry.getQuantity(), entry.getProduct(), entry.getIsQuantityInPieces());
					entry.setConvertedQuantity(convertedQuantity);
				}
			}
			
			internalPut("orderWeightPattern", neorisCartPriceHelper.getOrderWeightPatterFor(orderData));
			internalPut("orderQtyPattern", neorisCartPriceHelper.getQuantityPattern(orderData.getUnitWhenPlaced()));
			internalPut("orderWeightKgPattern", neorisCartPriceHelper.getWeightKgNumberPattern());
			internalPut("orderWeightTonPattern", neorisCartPriceHelper.getTonPattern());
			internalPut("orderIncludesNegPrices", orderData.getHasNegPrices());
		}
	}

	protected Converter<OrderModel, OrderData> getOrderConverter()
	{
		return orderConverter;
	}

	@Required
	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter)
	{
		this.orderConverter = orderConverter;
	}

	public OrderData getOrder()
	{
		return orderData;
	}

	
}
