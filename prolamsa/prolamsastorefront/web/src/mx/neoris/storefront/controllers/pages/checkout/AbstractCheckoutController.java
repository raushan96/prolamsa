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
package mx.neoris.storefront.controllers.pages.checkout;

import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.data.CartData;

import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.flow.B2BCheckoutFlowFacade;
import mx.neoris.storefront.controllers.pages.AbstractPageController;

import javax.annotation.Resource;

/**
 * Base controller for all checkout page controllers. Provides common functionality for all checkout page controllers.
 */
public abstract class AbstractCheckoutController extends AbstractPageController
{
	@Resource(name = "b2bCheckoutFlowFacade")
	private B2BCheckoutFlowFacade checkoutFlowFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;

	protected B2BCheckoutFlowFacade getCheckoutFlowFacade()
	{
		return checkoutFlowFacade;
	}

	protected I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	/**
	 * Checks if there are any items in the cart.
	 * 
	 * @return returns true if items found in cart.
	 */
	protected boolean hasItemsInCart()
	{
		//final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
		final CartData cartData = neorisCartFacade.getSessionCart();
		
		return (cartData.getEntries() != null && !cartData.getEntries().isEmpty());
	}
}
