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
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import javax.annotation.Resource;

import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisProductFacade;

import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Required;


/**
 * Context (velocity) for email order notification.
 */
public class OrderNotificationEmailContext extends AbstractEmailContext<OrderProcessModel>
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
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());
		
		neorisProductFacade.injectCustomerNameAndDescriptionDataOnEmail(orderData.getEntries(),orderProcessModel.getOrder().getUnit());

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
		}
	}

	@Override
	protected BaseSiteModel getSite(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderProcessModel orderProcessModel)
	{
		return (CustomerModel) orderProcessModel.getOrder().getUser();
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

	@Override
	protected LanguageModel getEmailLanguage(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getLanguage();
	}
}
