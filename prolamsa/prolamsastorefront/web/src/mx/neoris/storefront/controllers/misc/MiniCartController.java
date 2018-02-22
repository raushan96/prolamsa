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
package mx.neoris.storefront.controllers.misc;


import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.store.services.BaseStoreService;

import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.services.NeorisCreditScoreCard;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.controllers.AbstractController;
import mx.neoris.storefront.controllers.ControllerConstants;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller for MiniCart functionality which is not specific to a page.
 */
@Controller
@Scope("tenant")
public class MiniCartController extends AbstractController
{
	protected static final Logger LOG = Logger.getLogger(MiniCartController.class);
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String TOTAL_DISPLAY_PATH_VARIABLE_PATTERN = "{totalDisplay:.*}";
	private static final String COMPONENT_UID_PATH_VARIABLE_PATTERN = "{componentUid:.*}";
	
	@Resource(name = "neorisSapCreditScoreCard")
	private NeorisCreditScoreCard neorisSapCreditScoreCard;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;
	
	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
		
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	

	@RequestMapping(value = "/cart/miniCart/" + TOTAL_DISPLAY_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String getMiniCart(@PathVariable final String totalDisplay, final Model model)
	{
		final CartData cartData = neorisCartFacade.getSessionCart();
		model.addAttribute("totalPrice", cartData.getTotalPrice());
		model.addAttribute("subTotal", cartData.getSubTotal());
		if (cartData.getDeliveryCost() != null)
		{
			final PriceData withoutDelivery = cartData.getDeliveryCost();
			withoutDelivery.setValue(cartData.getTotalPrice().getValue().subtract(cartData.getDeliveryCost().getValue()));
			model.addAttribute("totalNoDelivery", withoutDelivery);
		}
		else
		{
			model.addAttribute("totalNoDelivery", cartData.getTotalPrice());
		}
		model.addAttribute("totalItems", cartData.getEntries().size());
		model.addAttribute("totalDisplay", totalDisplay);
		model.addAttribute("cartData",cartData);
		model.addAttribute("tonPattern", neorisCartPriceHelper.getTonPattern());
		return ControllerConstants.Views.Fragments.Cart.MiniCartPanel;
	}


	@RequestMapping(value = "/cart/rollover/" + COMPONENT_UID_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String rolloverMiniCartPopup(@PathVariable final String componentUid, final Model model)
			throws CMSItemNotFoundException
	{
		// NEORIS_CHANGE #64
		CartData cartData = neorisCartFacade.getSessionCart();
		
		//inject product reference data if exists
		neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(cartData.getEntries());
							
		model.addAttribute("cartData", cartData);
		
		//NEORIS Modificacion Christian Loredo 20112015
		//El llamado a la funcion Valorizacion se hace solo 1 vez (Al agregar el producto al carrito)
		try
		{
			Integer scoreLowLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardLowLimit();
			Integer scoreHighLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardHighLimit();
			
			model.addAttribute("creditScoreCard", cartData.getCreditScoreCard());
			model.addAttribute("semaphoreCredit", cartData.getSemaphoreCredit());
			
			if (scoreLowLimit!=null && scoreHighLimit!=null && scoreHighLimit > scoreLowLimit) {
				model.addAttribute("scoreLowLimit", scoreLowLimit);
				model.addAttribute("scoreHighLimit", scoreHighLimit);
			}  else {
				model.addAttribute("scoreLowLimit", 0);
				model.addAttribute("scoreHighLimit", 0);
			}
		}catch (Exception e) {
			LOG.error("Error MiniCartController: getCreditScoreCard: error: " + e.getMessage());
			model.addAttribute("creditScoreCard", 0.0);
			model.addAttribute("scoreLowLimit", 0);
			model.addAttribute("scoreHighLimit", 0);
		}

		
		//NEORIS_CAHNGE#Credit Score Card: LCC
		//get the Credit Score Card
		/*try {
			neorisSapCreditScoreCard.calculateCreditScoreCard(cartData);
			
			Integer scoreLowLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardLowLimit();
			Integer scoreHighLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardHighLimit();
			
			model.addAttribute("creditScoreCard", cartData.getCreditScoreCard());
			model.addAttribute("semaphoreCredit", cartData.getSemaphoreCredit());
			
			if (scoreLowLimit!=null && scoreHighLimit!=null && scoreHighLimit > scoreLowLimit) {
				model.addAttribute("scoreLowLimit", scoreLowLimit);
				model.addAttribute("scoreHighLimit", scoreHighLimit);
			}  else {
				model.addAttribute("scoreLowLimit", 0);
				model.addAttribute("scoreHighLimit", 0);
			}
			
		} catch (Exception e) {
			LOG.error("Error AddToCartController: CalculateCreditScoreCard: error: " + e.getMessage());
			model.addAttribute("creditScoreCard", 0.0);
			model.addAttribute("scoreLowLimit", 0);
			model.addAttribute("scoreHighLimit", 0);
		}*/
		
		
		final MiniCartComponentModel component = (MiniCartComponentModel) cmsComponentService.getSimpleCMSComponent(componentUid);

		final List entries = cartData.getEntries();
		if (entries != null)
		{
			Collections.reverse(entries);
			model.addAttribute("entries", entries);

			model.addAttribute("numberItemsInCart", Integer.valueOf(entries.size()));
			if (entries.size() < component.getShownProductCount())
			{
				model.addAttribute("numberShowing", Integer.valueOf(entries.size()));
			}
			else
			{
				model.addAttribute("numberShowing", Integer.valueOf(component.getShownProductCount()));
			}
		}
		model.addAttribute("lightboxBannerComponent", component.getLightboxBannerComponent());
		
		model.addAttribute("unit", neorisFacade.getCurrentUnit().getCode().toString());

		return ControllerConstants.Views.Fragments.Cart.CartPopup;
	}
}
