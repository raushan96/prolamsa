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


import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.b2bacceleratorfacades.order.B2BOrderFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.ScheduledCartData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.enums.B2BCheckoutFlowEnum;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.services.NeorisCommerceCheckoutService;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.flow.impl.DefaultB2BCheckoutFlowFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller for checkout process.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/checkout")
public class CheckoutController extends AbstractCheckoutController
{
	protected static final Logger LOG = Logger.getLogger(CheckoutController.class);

	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";
	private static final String JOB_CODE_PATH_VARIABLE_PATTERN = "{jobCode:.*}";

	private static final String CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE = "orderConfirmationPage";
	private static final String ACCOUNT_REPLENISHMENT_PAGE = "/my-account/my-replenishment";
	
	private static final String CONTINUE_URL = "continueUrl";
	
	@Resource(name="neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	// NEORIS_CHANGE #74
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	// NEORIS_CHANGE #74
	@Resource(name = "neorisCartPriceCalculator")
	private NeorisCartPriceCalculator neorisCartPriceCalculator;
	
	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "b2bOrderFacade")
	private B2BOrderFacade b2bOrderFacade;
	
	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	
	@Resource(name = "neorisDefaultCommerceCheckoutService")
	private NeorisCommerceCheckoutService neorisDefaultCommerceCheckoutService;
	
	@Resource(name="defaultB2BCheckoutFlowFacade")
	private DefaultB2BCheckoutFlowFacade defaultB2BCheckoutFlowFacade;

	@Resource
	private MediaService mediaService;

	@RequestMapping(method = RequestMethod.GET)
	public String checkout()
	{
		if (hasItemsInCart())
		{
			return getCheckoutRedirectUrl();
		}
		LOG.info("Missing or empty cart");

		// No session cart or empty session cart. Bounce back to the cart page.
		return REDIRECT_PREFIX + "/cart";
	}

	@RequestMapping(value = "/orderConfirmation/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderConfirmation(@PathVariable("orderCode") final String orderCode, final Model model)
			throws CMSItemNotFoundException
	{
		final OrderData orderDetails = b2bOrderFacade.getOrderDetailsForCode(orderCode);
		//NEORIS_CHANGE #Get Payment Terms
//		PaymentTermsType tPago = neorisFacade.getRootUnit().getTPago();		
		orderDetails.setTPago( neorisFacade.getTPagoFor(getRootUnit(), getCurrentBaseStore()));
		addOrderWeightPatterToModel(model,orderDetails);
		
		//set sap information			
		/*String sapWeightUnit = sessionService.getCurrentSession().getAttribute("sapWeightUnit");
		String sapCurrency = sessionService.getCurrentSession().getAttribute("sapCurrency");
					
		orderDetails.setSapCurrency(sapWeightUnit);
		orderDetails.setSapWeightUnit(sapCurrency);*/
		
		// NEORIS_CHANGE #74 We need to change the quantities in the order depending on the current unit
		// Now we show the weight and quantities according to the unit used when the oreder was placed
		//UnitModel currentUnit = sessionService.getAttribute(NeorisFacade.PRODUCTUNIT_SLOT);
			
		
		if (orderDetails.getEntries() != null && !orderDetails.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : orderDetails.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
				entry.setProduct(product);
				final Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(orderDetails.getUnitWhenPlaced(), entry.getQuantity(), entry.getProduct(), entry.getIsQuantityInPieces());
				entry.setConvertedQuantity(convertedQuantity);
			}
		}
		
		// injecting quantity pattern based in the order unit
		addOrderQuantityPatternToModel(model, orderDetails);
		
		// NEORIS_CHANGE #74
		neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderDetails.getEntries());
		neorisCartPriceHelper.sortEntriesBySapOrderEntryNumber(orderDetails);
		neorisCartPriceHelper.setFormattedSapData(orderDetails);		
				
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderData", orderDetails);
		model.addAttribute("allItems", orderDetails.getEntries());
		model.addAttribute("deliveryAddress", orderDetails.getDeliveryAddress());
		model.addAttribute("deliveryMode", orderDetails.getDeliveryMode());
		model.addAttribute("paymentInfo", orderDetails.getPaymentInfo());
		model.addAttribute("email", getCustomerFacade().getCurrentCustomer().getUid());
		model.addAttribute("pageType", PageType.ORDERCONFIRMATION.name());
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		//NEORIS_CHANGE #74
		model.addAttribute("defaultUoM", baseStoreService.getCurrentBaseStore().getUnit());
		
		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE);
		storeCmsPageInModel(model, cmsPage);

		return ControllerConstants.Views.Pages.Checkout.CheckoutConfirmationPage;
	}

	@RequestMapping(value = "/quoteOrderConfirmation/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String quoteOrderConfirmation(@PathVariable("orderCode") final String orderCode, final Model model)
			throws CMSItemNotFoundException
	{
		final OrderData orderDetails = b2bOrderFacade.getOrderDetailsForCode(orderCode);

		//NEORIS_CHANGE #Get Payment Terms
//		PaymentTermsType tPago = neorisFacade.getRootUnit().getTPago();		
		orderDetails.setTPago( neorisFacade.getTPagoFor(getRootUnit(), getCurrentBaseStore()));
		addOrderWeightPatterToModel(model,orderDetails);
		// NEORIS_CHANGE #74 We need to change the quantities in the order depending on the current unit
		// Now we use the unit used when the order was placed
		//UnitModel currentUnit = sessionService.getAttribute(NeorisFacade.PRODUCTUNIT_SLOT);
		
		//set sap information			
		/*String sapWeightUnit = sessionService.getCurrentSession().getAttribute("sapWeightUnit");
		String sapCurrency = sessionService.getCurrentSession().getAttribute("sapCurrency");
					
		orderDetails.setSapCurrency(sapWeightUnit);
		orderDetails.setSapWeightUnit(sapCurrency);*/
		
		if (orderDetails.getEntries() != null && !orderDetails.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : orderDetails.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
				entry.setProduct(product);
				final Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(orderDetails.getUnitWhenPlaced(), entry.getQuantity(), entry.getProduct(), entry.getIsQuantityInPieces());
				entry.setConvertedQuantity(convertedQuantity);
			}
		}
		
		// injecting quantity pattern based in the order unit
		addOrderQuantityPatternToModel(model, orderDetails);
		
		// NEORIS_CHANGE #74
		neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderDetails.getEntries());
		neorisCartPriceHelper.sortEntriesBySapOrderEntryNumber(orderDetails);
		neorisCartPriceHelper.setFormattedSapData(orderDetails);

		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderData", orderDetails);
		model.addAttribute("allItems", orderDetails.getEntries());
		model.addAttribute("deliveryAddress", orderDetails.getDeliveryAddress());
		model.addAttribute("deliveryMode", orderDetails.getDeliveryMode());
		model.addAttribute("paymentInfo", orderDetails.getPaymentInfo());
		model.addAttribute("email", getCustomerFacade().getCurrentCustomer().getUid());
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		//NEORIS_CHANGE #74
		model.addAttribute("defaultUoM", baseStoreService.getCurrentBaseStore().getUnit());
		
		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE);
		storeCmsPageInModel(model, cmsPage);

		return ControllerConstants.Views.Pages.Checkout.QuoteCheckoutConfirmationPage;
	}

	@RequestMapping(value = "/replenishmentConfirmation/" + JOB_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String replenishmentConfirmation(@PathVariable("jobCode") final String jobCode, final Model model)
			throws CMSItemNotFoundException
	{

		final ScheduledCartData scheduledCartData = b2bOrderFacade.getReplenishmentOrderDetailsForCode(jobCode, getCustomerFacade()
				.getCurrentCustomer().getUid());

		if (scheduledCartData.getEntries() != null && !scheduledCartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : scheduledCartData.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
				entry.setProduct(product);
			}
		}

		model.addAttribute("orderData", scheduledCartData);
		model.addAttribute("allItems", scheduledCartData.getEntries());
		model.addAttribute("deliveryAddress", scheduledCartData.getDeliveryAddress());
		model.addAttribute("deliveryMode", scheduledCartData.getDeliveryMode());
		model.addAttribute("paymentInfo", scheduledCartData.getPaymentInfo());
		model.addAttribute("email", getCustomerFacade().getCurrentCustomer().getUid());
		model.addAttribute("metaRobots", "no-index,no-follow");
		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute("continueUrl", (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);
		model.addAttribute("scheduleUrl", ACCOUNT_REPLENISHMENT_PAGE + "/" + jobCode);

		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE);
		storeCmsPageInModel(model, cmsPage);

		return ControllerConstants.Views.Pages.Checkout.CheckoutReplenishmentConfirmationPage;
	}

	/**
	 * Method used to determine the checkout redirect URL that will handle the checkout process.
	 * 
	 * @return A <code>String</code> object of the URL to redirect to.
	 */
	protected String getCheckoutRedirectUrl()
	{
		final B2BCheckoutFlowEnum checkoutFlow = getCheckoutFlowFacade().getCheckoutFlow();
		if (B2BCheckoutFlowEnum.MULTISTEP.equals(checkoutFlow))
		{
			return REDIRECT_PREFIX + "/checkout/multi";
		}

		// Default to the single-step checkout
		return REDIRECT_PREFIX + "/checkout/single";
	}
	
	@RequestMapping(value = "/quickOrderConfirmation/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String quickOrderConfirmation(@PathVariable("orderCode") final String orderCode, final Model model)
			throws CMSItemNotFoundException
	{
		OrderData orderData = b2bOrderFacade.getOrderDetailsForCode(orderCode);
		
		boolean isOrderSplittedDueHSS = false;
		
		if(orderData.getHasHSSProducts())
		{
			for(OrderEntryData eachEntry : orderData.getEntries())
			{
				if(!eachEntry.getProduct().getIsHSS())
				{
					isOrderSplittedDueHSS = true;
					break;
				}
			}
		}
		
		model.addAttribute("isOrderSplittedDueHSS", isOrderSplittedDueHSS);
		
		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE);
		storeCmsPageInModel(model, cmsPage);
		
		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);
		
		return ControllerConstants.Views.Pages.Checkout.QuickCheckoutConfirmationPage;
	}
	
	@RequestMapping(value = "/quickQuoteOrderConfirmation/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String quickQuoteOrderConfirmation(@PathVariable("orderCode") final String orderCode, final Model model)
			throws CMSItemNotFoundException
	{
		OrderData orderData = b2bOrderFacade.getOrderDetailsForCode(orderCode);
		
		boolean isOrderSplittedDueHSS = false;
		
		if(orderData.getHasHSSProducts())
		{
			for(OrderEntryData eachEntry : orderData.getEntries())
			{
				if(!eachEntry.getProduct().getIsHSS())
				{
					isOrderSplittedDueHSS = true;
					break;
				}
			}
		}
		
		model.addAttribute("isOrderSplittedDueHSS", isOrderSplittedDueHSS);
		
		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE);
		storeCmsPageInModel(model, cmsPage);
		
		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);
		
		return ControllerConstants.Views.Pages.Checkout.QuickQuoteCheckoutConfirmationPage;
	}
}
