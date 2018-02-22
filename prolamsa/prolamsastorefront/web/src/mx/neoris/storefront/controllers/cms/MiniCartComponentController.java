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
package mx.neoris.storefront.controllers.cms;

import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.data.PriceData;

import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.storefront.controllers.ControllerConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for CMS MiniCartComponent.
 */
@Controller("MiniCartComponentController")
@Scope("tenant")
@RequestMapping(value = ControllerConstants.Actions.Cms.MiniCartComponent)
public class MiniCartComponentController extends AbstractCMSComponentController<MiniCartComponentModel>
{
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String TOTAL_ITEMS = "totalItems";
	public static final String TOTAL_DISPLAY = "totalDisplay";
	public static final String TOTAL_NO_DELIVERY = "totalNoDelivery";
	public static final String SUB_TOTAL = "subTotal";
	public static final String UNIT = "unit";

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final MiniCartComponentModel component)
	{
		// NEORIS_CHANGE #75 Change to get the cartData with the updated quantities
		final CartData cartData = neorisCartFacade.getSessionCart();
		model.addAttribute(SUB_TOTAL, cartData.getSubTotal());
		if (cartData.getDeliveryCost() != null)
		{
			final PriceData withoutDelivery = cartData.getDeliveryCost();
			withoutDelivery.setValue(cartData.getTotalPrice().getValue().subtract(cartData.getDeliveryCost().getValue()));
			model.addAttribute(TOTAL_NO_DELIVERY, withoutDelivery);
		}
		else
		{
			model.addAttribute(TOTAL_NO_DELIVERY, cartData.getTotalPrice());
		}
		model.addAttribute(TOTAL_PRICE, cartData.getTotalPrice());
		model.addAttribute(TOTAL_DISPLAY, component.getTotalDisplay());
		model.addAttribute(TOTAL_ITEMS, cartData.getEntries().size());
		model.addAttribute(UNIT, neorisFacade.getCurrentUnit().toString());
		model.addAttribute("cartData",cartData);
		model.addAttribute("tonPattern", neorisCartPriceHelper.getTonPattern());
		
	}
}
