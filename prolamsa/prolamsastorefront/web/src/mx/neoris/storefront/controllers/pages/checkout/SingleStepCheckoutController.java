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

import de.hybris.platform.b2b.constants.GeneratedB2BCommerceConstants.Attributes.AbstractOrderEntry;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BCommentData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BCostCenterData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BDaysOfWeekData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BPaymentTypeData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BReplenishmentRecurrenceEnum;
import de.hybris.platform.b2bacceleratorfacades.order.data.ScheduledCartData;
import de.hybris.platform.b2bacceleratorfacades.order.data.TriggerData;
import de.hybris.platform.b2bacceleratorservices.enums.CheckoutPaymentType;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.data.ZoneDeliveryModeData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.data.TransportationModeData;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.cronjob.enums.DayOfWeek;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.TermsAndCoditionsByStoreModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.NeorisB2BOrderService;
import mx.neoris.core.services.NeorisCommerceCheckoutService;
import mx.neoris.core.services.NeorisTransportationModeService;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.flow.impl.DefaultB2BCheckoutFlowFacade;
import mx.neoris.facades.media.data.NeorisMediaData;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.impl.ContentPageBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.AddressForm;
import mx.neoris.storefront.forms.PaymentDetailsForm;
import mx.neoris.storefront.forms.PlaceOrderForm;
import mx.neoris.storefront.forms.validation.PaymentDetailsValidator;
import mx.neoris.storefront.security.B2BUserGroupProvider;
import mx.neoris.storefront.util.XSSFilterUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration.ArrayNode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SingleStepCheckoutController
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/checkout/single")
public class SingleStepCheckoutController extends AbstractCheckoutController {
	protected static final Logger LOG = Logger.getLogger(SingleStepCheckoutController.class);

	private static final String SINGLE_STEP_CHECKOUT_SUMMARY_CMS_PAGE = "singleStepCheckoutSummaryPage";

	public static final String POPUP_PAGE = "pages/order/orderShippingInstructionsPopup";

	@Resource(name = "paymentDetailsValidator")
	private PaymentDetailsValidator paymentDetailsValidator;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "b2bUserGroupProvider")
	private B2BUserGroupProvider b2bUserGroupProvider;

	@Resource(name = "b2bContentPageBreadcrumbBuilder")
	private ContentPageBreadcrumbBuilder contentPageBreadcrumbBuilder;

	@Resource(name = "neorisTransportationModeConverter")
	private Converter<TransportationModeModel, TransportationModeData> tmConverter;

	@Resource(name = "neorisDefaultCommerceCheckoutService")
	private NeorisCommerceCheckoutService neorisDefaultCommerceCheckoutService;

	@Resource(name = "defaultB2BCheckoutFlowFacade")
	private DefaultB2BCheckoutFlowFacade defaultB2BCheckoutFlowFacade;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@Resource(name = "neorisTransportationModeService")
	private NeorisTransportationModeService neorisTransportationModeService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2BCustomerService;

	@Resource(name = "neorisB2BOrderService")
	private NeorisB2BOrderService neorisB2BOrderService;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource
	CartService cartService;

	@Resource
	private MediaService mediaService;

	private static final int MAX_PO_ATTACHMENTS = 2;
	private static final long MAX_ALLOWED_MEDIA_MAX_SIZE = 10485760L;
	private static final String MAX_ALLOWED_FILE_SIZE_LABEL_MB = "10M";
	private static final String EXE_FILE_EXTENSION = ".exe";

	@ModelAttribute("titles")
	public Collection<TitleData> getTitles() {
		return getUserFacade().getTitles();
	}

	@ModelAttribute("termsAndConditionsChecked")
	public Boolean getTermsAndConditionsChecked() {
		BaseStoreModel currentBaseStore = getCurrentBaseStore();

		B2BCustomerModel currentB2BCustomer = b2BCustomerService.getCurrentB2BCustomer();

		if (!currentBaseStore.isAutomaticallyCheckTermsAndConditions()) {
			return false;
		}

		final List<TermsAndCoditionsByStoreModel> termsByCustomer = currentB2BCustomer.getTermsAndCoditionsByStore();

		if (termsByCustomer != null && termsByCustomer.size() > 0) {
			for (TermsAndCoditionsByStoreModel eachTerm : termsByCustomer) {
				if (currentBaseStore.getUid().equalsIgnoreCase(eachTerm.getBaseStoreId())) {
					return eachTerm.getTermsAndConditionsChecked();
				}
			}
		}

		return false;
	}

	@ModelAttribute("countries")
	public Collection<CountryData> getCountries() {
		return getCheckoutFlowFacade().getDeliveryCountries();
	}

	@ModelAttribute("billingCountries")
	public Collection<CountryData> getBillingCountries() {
		return getCheckoutFlowFacade().getBillingCountries();
	}

	@ModelAttribute("costCenters")
	public List<? extends B2BCostCenterData> getVisibleActiveCostCenters() {
		final List<? extends B2BCostCenterData> costCenterData = getCheckoutFlowFacade().getActiveVisibleCostCenters();
		return costCenterData == null ? Collections.<B2BCostCenterData> emptyList() : costCenterData;
	}

	@ModelAttribute("paymentTypes")
	public Collection<B2BPaymentTypeData> getAllB2BPaymentTypes() {
		return getCheckoutFlowFacade().getPaymentTypesForCheckoutSummary();
	}

	@ModelAttribute("daysOfWeek")
	public Collection<B2BDaysOfWeekData> getAllDaysOfWeek() {
		return getCheckoutFlowFacade().getDaysOfWeekForReplenishmentCheckoutSummary();
	}

	@ModelAttribute("startYears")
	public List<SelectOption> getStartYears() {
		final List<SelectOption> startYears = new ArrayList<SelectOption>();
		final Calendar calender = new GregorianCalendar();

		for (int i = calender.get(Calendar.YEAR); i > (calender.get(Calendar.YEAR) - 6); i--) {
			startYears.add(new SelectOption(String.valueOf(i), String.valueOf(i)));
		}

		return startYears;
	}

	@ModelAttribute("expiryYears")
	public List<SelectOption> getExpiryYears() {
		final List<SelectOption> expiryYears = new ArrayList<SelectOption>();
		final Calendar calender = new GregorianCalendar();

		for (int i = calender.get(Calendar.YEAR); i < (calender.get(Calendar.YEAR) + 11); i++) {
			expiryYears.add(new SelectOption(String.valueOf(i), String.valueOf(i)));
		}

		return expiryYears;
	}

	@ModelAttribute("cardTypes")
	public Collection<CardTypeData> getCardTypes() {
		return getCheckoutFlowFacade().getSupportedCardTypes();
	}

	@ModelAttribute("months")
	public List<SelectOption> getMonths() {
		final List<SelectOption> months = new ArrayList<SelectOption>();

		months.add(new SelectOption("1", "01"));
		months.add(new SelectOption("2", "02"));
		months.add(new SelectOption("3", "03"));
		months.add(new SelectOption("4", "04"));
		months.add(new SelectOption("5", "05"));
		months.add(new SelectOption("6", "06"));
		months.add(new SelectOption("7", "07"));
		months.add(new SelectOption("8", "08"));
		months.add(new SelectOption("9", "09"));
		months.add(new SelectOption("10", "10"));
		months.add(new SelectOption("11", "11"));
		months.add(new SelectOption("12", "12"));

		return months;
	}

	@InitBinder
	protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
		final DateFormat dateFormat = new SimpleDateFormat(
				getMessageSource().getMessage("text.store.dateformat", null, getI18nService().getCurrentLocale()));
		final CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String checkoutSummary() {
		if (hasItemsInCart()) {
			return REDIRECT_PREFIX + "/checkout/single/summary";
		}
		return REDIRECT_PREFIX + "/cart";
	}

	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	@RequireHardLogIn
	public String checkoutSummary(final Model model) throws Exception {

		// set the first center cost
		// NEORIS_CHANGE #30 7-APRIL-14
		final List<? extends B2BCostCenterData> listCostCenter = getCheckoutFlowFacade().getActiveVisibleCostCenters();
		final B2BCostCenterData cc = (listCostCenter.size() == 0) ? null : listCostCenter.get(0);

		// initialize purchase order and transportation mode
		// getCheckoutFlowFacade().setPurchaseOrderNumber("");
		// getCheckoutFlowFacade().setTransportationMode(null);

		if (cc != null) {
			// getCheckoutFlowFacade().setCostCenterForCart(cc.getCode(),
			// this.getCheckoutFlowFacade().getCheckoutCart().getCode());
			CartData cartDataCC = neorisCartFacade.getSessionCart();
			getCheckoutFlowFacade().setCostCenterForCart(cc.getCode(), cartDataCC.getCode());
		}

		if (!b2bUserGroupProvider.isCurrentUserAuthorizedToCheckOut()) {
			GlobalMessages.addErrorMessage(model, "checkout.error.invalid.accountType");
			return FORWARD_PREFIX + "/cart";
		}

		if (!hasItemsInCart()) {
			// no items in the cart
			return FORWARD_PREFIX + "/cart";
		}

		// NEORIS_CHANGE #74 If there is only one delivery address set it
		List<? extends AddressData> deliveryAddressesData = getDeliveryAddresses();

		// NEORIS_CAHNGE #Set Billing Address
		AddressData billingAddressesData = new AddressData();
		if (getBillingAddresses() != null && getBillingAddresses().size() > 0) {

			try {
				billingAddressesData = getBillingAddresses().get(0);
			} catch (Exception e) {
				LOG.warn("Not found Billing Address");
			}
		}

		model.addAttribute("placeOrderForm", new PlaceOrderForm());

		// NEORIS_CHANGE #Get Payment Terms by store
		// PaymentTermsType tPago = neorisFacade.getRootUnit().getTPago();
		model.addAttribute("paymentTerms", neorisFacade.getTPagoFor(getRootUnit(), getCurrentBaseStore()));

		// NEORIS_CHANGE #74 Put the delivery addresses on model
		model.addAttribute("deliveryAddresses", deliveryAddressesData);

		// NEORIS_CHANGE #Put the billing addresses on model
		model.addAttribute("billingAddresses", billingAddressesData);

		// it will try to load default settings for: delivery address and
		// transportation mode
		getCheckoutFlowFacade().setDeliveryAddressIfAvailableFor();
		getCheckoutFlowFacade().setTransportationModelIfAvailableFor();
		//

		getCheckoutFlowFacade().setDeliveryModeIfAvailable();
		getCheckoutFlowFacade().setPaymentInfoIfAvailable();
		getCheckoutFlowFacade().setDefaultPaymentTypeForCheckout();
		getCheckoutFlowFacade().setBillingAddressIfAvailable();
		getCheckoutFlowFacade().setPaymentTerms();
		getCheckoutFlowFacade().setRequestedDeliveryDate(getCheckoutCart().getRequestedDeliveryDate());

		CartData cartData = neorisCartFacade.getSessionCart();

		neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(cartData.getEntries());

		// Display quantity with the UOM selected
		UnitModel currentUnit = neorisFacade.getCurrentUnit();
		for (OrderEntryData orderEntryData : cartData.getEntries()) {
			Double convertedQuantity = 0.0d;
			convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnit,
					orderEntryData.getQuantity(), orderEntryData.getProduct());
			orderEntryData.setConvertedQuantity(convertedQuantity);
		}

		// Try to set default delivery address and delivery mode
		if (cartData.getPaymentType() == null) {
			getCheckoutFlowFacade().setPaymentTypeSelectedForCheckout(CheckoutPaymentType.ACCOUNT.getCode());
		}

		// NEORIS_CHANGE #Set Days added to Requested Delivery Day
		Integer addedDays = 0;
		if (baseStoreService.getCurrentBaseStore().getDaysAddedDeliveryDate() != null) {
			addedDays = baseStoreService.getCurrentBaseStore().getDaysAddedDeliveryDate();
		}

		// NEORIS_CHANGE #74 added to show the available transportation modes on
		// checkout page
		model.addAttribute("addedDays", addedDays);
		model.addAttribute("transportationModes", getTransportationModes());
		model.addAttribute("cartData", cartData);
		model.addAttribute("allItems", cartData.getEntries());
		model.addAttribute("deliveryAddress", cartData.getDeliveryAddress());
		model.addAttribute("deliveryMode", cartData.getDeliveryMode());
		model.addAttribute("paymentInfo", cartData.getPaymentInfo());
		model.addAttribute("costCenter", cartData.getCostCenter());
		model.addAttribute("quoteText", new B2BCommentData());
		// TODO:Make configuration hmc driven than hardcoding in controllers
		model.addAttribute("nDays", getNumberRange(1, 30));
		model.addAttribute("nthDayOfMonth", getNumberRange(1, 31));
		model.addAttribute("nthWeek", getNumberRange(1, 12));

		if (cartData.getTransportationMode() != null
				&& !StringUtils.isBlank(cartData.getTransportationMode().getCode())) {
			model.addAttribute("incoterms", getIncoterms(cartData.getTransportationMode().getCode()));
		}

		model.addAttribute(new AddressForm());
		model.addAttribute(new PaymentDetailsForm());

		model.addAttribute("enabledPlaceOrderButton",
				baseStoreService.getCurrentBaseStore().getPlaceOrderButtonEnabled());

		if (!model.containsAttribute("placeOrderForm")) {
			final PlaceOrderForm placeOrderForm = new PlaceOrderForm();
			// TODO: Make setting of default recurrence enum value hmc driven
			// rather hard coding in controller
			placeOrderForm.setReplenishmentRecurrence(B2BReplenishmentRecurrenceEnum.MONTHLY);
			placeOrderForm.setnDays("14");
			final List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
			daysOfWeek.add(DayOfWeek.MONDAY);
			placeOrderForm.setnDaysOfWeek(daysOfWeek);
			model.addAttribute("placeOrderForm", placeOrderForm);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(SINGLE_STEP_CHECKOUT_SUMMARY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SINGLE_STEP_CHECKOUT_SUMMARY_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.SingleStepCheckout.CheckoutSummaryPage;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/getCheckoutCart.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public CartData getCheckoutCart() {
		// return getCheckoutFlowFacade().getCheckoutCart();
		return neorisCartFacade.getSessionCart();
	}

	@ResponseBody
	@RequestMapping(value = "/summary/getCostCenters.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public List<? extends B2BCostCenterData> getCostCenters() {
		return getVisibleActiveCostCenters();
	}

	@ResponseBody
	@RequestMapping(value = "/summary/getDeliveryAddresses.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public List<? extends AddressData> getDeliveryAddresses() {
		// NEORIS_CHANGE #74
		return getCheckoutFlowFacade().getDeliveryAdrressesFor(getRootUnit());
	}

	@ResponseBody
	@RequestMapping(value = "/summary/getBillingAddresses.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public List<? extends AddressData> getBillingAddresses() {
		// NEORIS_CHANGE #74
		return getCheckoutFlowFacade().getBillingAdrressesFor(getRootUnit());
	}

	// NEORIS_CHANGE #74
	@ResponseBody
	@RequestMapping(value = "/summary/getTransportationModes.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public List<? extends TransportationModeData> getTransportationModes() {
		List<TransportationModeModel> transportationModeModels = baseStoreService.getCurrentBaseStore()
				.getTransportationModes();

		List<TransportationModeData> transportationModeDatas = new ArrayList<TransportationModeData>();

		for (TransportationModeModel eachModel : transportationModeModels) {
			if (!isInList(eachModel, transportationModeDatas)) {
				TransportationModeData eachData = tmConverter.convert(eachModel);
				transportationModeDatas.add(eachData);
			}
		}

		return transportationModeDatas;
	}

	private boolean isInList(TransportationModeModel model, List<TransportationModeData> list) {
		for (TransportationModeData eachData : list) {
			if (eachData.getMaxCapacity().doubleValue() == model.getMaxCapacity()
					&& eachData.getName().equalsIgnoreCase(model.getName()))
				return true;
		}

		return false;
	}

	// NEORIS_CHANGE #74
	@ResponseBody
	@RequestMapping(value = "/summary/getIncoterms.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public List<? extends TransportationModeData> getIncoterms(@RequestParam(value = "transportationModeCode")
	final String transportationModeCode) throws Exception {
		List<TransportationModeData> transportationModeDatas = new ArrayList<TransportationModeData>();

		if (transportationModeCode.equalsIgnoreCase("none")) {
			return transportationModeDatas;
		}

		List<TransportationModeModel> transportationModeModels = neorisTransportationModeService
				.getTransportationModesForCode(transportationModeCode);

		BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();

		// 05Ene2017 CILS / Ya que se repiten para el sitio MX algunos
		// internalCode, tambien se repetia en el combo los incoterm
		Map<String, TransportationModeData> incotermMap = new LinkedHashMap<String, TransportationModeData>();

		for (TransportationModeModel eachModel : transportationModeModels) {
			if (currentBaseStore.getTransportationModes().contains(eachModel)) {
				TransportationModeData eachData = tmConverter.convert(eachModel);
				// transportationModeDatas.add(eachData);
				incotermMap.put(eachModel.getInternalCode(), eachData);
			}
		}

		transportationModeDatas = new ArrayList<TransportationModeData>(incotermMap.values());
		return transportationModeDatas;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setDefaultAddress.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public List<? extends AddressData> setDefaultAddress(@RequestParam(value = "addressId")
	final String addressId) {
		getUserFacade().setDefaultAddress(getUserFacade().getAddressForCode(addressId));
		return getDeliveryAddresses();
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setDeliveryAddress.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public CartData setDeliveryAddress(@RequestParam(value = "addressCode")
	final String addressCode) {
		AddressData addressData = null;

		// final List<? extends AddressData> deliveryAddresses =
		// getCheckoutFlowFacade().getSupportedDeliveryAddresses(false);
		// NEORIS_CHANGE #74 Get all the delivery addresses in the b2b unit.
		final List<? extends AddressData> deliveryAddresses = getCheckoutFlowFacade()
				.getDeliveryAdrressesFor(getRootUnit());

		for (final AddressData deliveryAddress : deliveryAddresses) {
			if (deliveryAddress.getCode().equals(addressCode)) {
				addressData = deliveryAddress;
				break;
			}
		}

		if (addressData != null && getCheckoutFlowFacade().setDeliveryAddress(addressData)) {

			// set the delivery mode
			// NEORIS_CHANGE #30 8-APRIL-14
			final List<? extends DeliveryModeData> listDeliveryModes = getCheckoutFlowFacade()
					.getSupportedDeliveryModes();
			final DeliveryModeData deliveryMode = (listDeliveryModes.size() == 0) ? null : listDeliveryModes.get(0);

			if (deliveryMode != null) {

				getCheckoutFlowFacade().setDeliveryMode(deliveryMode.getCode());
			}

		} else { // When the address data is null means that the user return the
					// select to "Select an address..."
			getCheckoutFlowFacade().removeDeliveryAddress();
		}
		return neorisCartFacade.getSessionCart();
		// return getCheckoutFlowFacade().getCheckoutCart();

	}

	@ResponseBody
	@RequestMapping(value = "/summary/setTransportationMode.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public CartData setTransportationMode(@RequestParam(value = "transportationModeCode")
	final String transportationModeCode) {
		List<TransportationModeModel> transportationModeModels = baseStoreService.getCurrentBaseStore()
				.getTransportationModes();

		getCheckoutFlowFacade().setTransportationMode(null);

		TransportationModeModel transportationModeModel = null;

		for (TransportationModeModel eachModel : transportationModeModels) {
			if (eachModel.getInternalCode().equals(transportationModeCode)) {
				transportationModeModel = eachModel;
				break;
			}

		}

		getCheckoutFlowFacade().setTransportationMode(transportationModeModel);

		// return getCheckoutFlowFacade().getCheckoutCart();
		return neorisCartFacade.getSessionCart();

	}

	@ResponseBody
	@RequestMapping(value = "/summary/setIncoterm.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public CartData setIncoterm(@RequestParam(value = "incotermCode")
	final String incotermCode) {
		List<TransportationModeModel> transportationModeModels = baseStoreService.getCurrentBaseStore()
				.getTransportationModes();

		getCheckoutFlowFacade().setTransportationMode(null);

		TransportationModeModel transportationModeModel = null;

		for (TransportationModeModel eachModel : transportationModeModels) {
			if (eachModel.getInternalCode().equals(incotermCode)) {
				transportationModeModel = eachModel;
				break;
			}

		}

		getCheckoutFlowFacade().setTransportationMode(transportationModeModel);

		// return getCheckoutFlowFacade().getCheckoutCart();
		return neorisCartFacade.getSessionCart();

	}

	// NEORIS_CHANGE #74 Added to update SAP information
	@ResponseBody
	@RequestMapping(value = "/summary/calculatePrices.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public Object calculatePrices(final Model model) {
		Double minNegPrice;
		Double maxNegPrice;

		/*
		 * final ObjectNode node = JsonNodeFactory.instance.objectNode();
		 * org.codehaus.jackson.node.ArrayNode result =
		 * JsonNodeFactory.instance.arrayNode();
		 */
		try {
			// CartData cartData = getCheckoutCart();
			CartData cartData = neorisCartFacade.getSessionCart();
			if (getCheckoutFlowFacade().calculateCart(cartData)) {
				// Christian Loredo 08122015 Se guarda el resultado de la
				// consulta de precio
				neorisCartFacade.saveCalculatePrice(cartData);

				AbstractOrderModel cartModel = getSessionService().getAttribute("cart");
				neorisDefaultCommerceCheckoutService.saveSAPPrices(cartModel, cartData);
				neorisDefaultCommerceCheckoutService.saveSAPWeight(cartModel, cartData);
				modelService.save(cartModel);

				neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(cartData.getEntries());

				// TODO:PASAR A UN METODO

				UnitModel currentUnit = neorisFacade.getCurrentUnit();

				if (baseStoreService.getCurrentBaseStore().getUid().equalsIgnoreCase("6000")
						&& neorisFacade.isInternalCustomer()) {
					for (OrderEntryData eachEntry : cartData.getEntries()) {
						maxNegPrice = baseStoreService.getCurrentBaseStore().getQuoteNegotiationDiscountMax();
						if (maxNegPrice == null)
							maxNegPrice = 0D;

						minNegPrice = baseStoreService.getCurrentBaseStore().getQuoteNegotiationDiscountMin();
						if (minNegPrice == null)
							minNegPrice = 0D;

						// Fase 9 CILS
						if (eachEntry.getPricePerTon() != null) {
							eachEntry.setMinNegPrice(eachEntry.getPricePerTon() + minNegPrice);
							eachEntry.setMaxNegPrice(eachEntry.getPricePerTon() + maxNegPrice);
						} else {
							eachEntry.setMinNegPrice(minNegPrice);
							eachEntry.setMaxNegPrice(maxNegPrice);
						}
						/*
						 * if(eachEntry.getNetPriceWOTaxes() != null) {
						 * eachEntry.setMinNegPrice(eachEntry.getNetPriceWOTaxes
						 * ()+minNegPrice);
						 * eachEntry.setMaxNegPrice(eachEntry.getNetPriceWOTaxes
						 * ()+maxNegPrice); }else {
						 * eachEntry.setMinNegPrice(minNegPrice);
						 * eachEntry.setMaxNegPrice(maxNegPrice); }
						 */
					}
				} else {
					if (baseStoreService.getCurrentBaseStore().getPriceNegotiationEnabled()) {
						for (OrderEntryData eachEntry : cartData.getEntries()) {
							// Define max range and min range for negotiable
							// price
							if (neorisFacade.getCurrentCurrency() != null
									&& neorisFacade.getCurrentCurrency().getIsocode().equalsIgnoreCase("USD")) {
								// Max negotiable price in dls/kg
								minNegPrice = 0.33d;
								maxNegPrice = 2.0d;
							} else {
								// Max negotiable price in pesos/kg
								minNegPrice = 5.0d;
								maxNegPrice = 30.0;
							}

							ProductData eachProduct = eachEntry.getProduct();

							if (currentUnit.getCode().contains("bun")) {
								minNegPrice = eachProduct.getBundleKgEquiv() * minNegPrice;
								maxNegPrice = eachProduct.getBundleKgEquiv() * maxNegPrice;

							} else if (currentUnit.getCode().contains("pc")) {
								minNegPrice = eachProduct.getPcKgEquiv() * minNegPrice;
								maxNegPrice = eachProduct.getPcKgEquiv() * maxNegPrice;

							} else if (currentUnit.getCode().contains("kg")) {
								eachEntry.setMinNegPrice(minNegPrice);
								eachEntry.setMaxNegPrice(maxNegPrice);

							} else if (currentUnit.getCode().contains("lb")) {
								minNegPrice = minNegPrice / 2.20462;
								maxNegPrice = maxNegPrice / 2.20462;
							} else if (currentUnit.getCode().contains("ton")) {
								minNegPrice = minNegPrice * 1000;
								maxNegPrice = maxNegPrice * 1000;
							} else if (currentUnit.getCode().contains("ft")) {
								minNegPrice = eachProduct.getKgEquiv() / eachProduct.getFtEquiv() * minNegPrice;
								maxNegPrice = eachProduct.getKgEquiv() / eachProduct.getFtEquiv() * maxNegPrice;
							} else if (currentUnit.getCode().contains("mt")) {
								minNegPrice = eachProduct.getKgEquiv() / eachProduct.getMtEquiv() * minNegPrice;
								maxNegPrice = eachProduct.getKgEquiv() / eachProduct.getMtEquiv() * maxNegPrice;
							}

							eachEntry.setMinNegPrice(minNegPrice);
							eachEntry.setMaxNegPrice(maxNegPrice);
						}
					}
				}

			}
			model.addAttribute("labelUnit", cartData.getSapLabelWeightUnit());
			return cartData;

			/*
			 * ObjectNode data = JsonNodeFactory.instance.objectNode();
			 * 
			 * data.put("sapTotalPrice", cartData.getSapTotalPrice());
			 * 
			 * result.add(data); node.put("status", AJAX_STATUS_OK);
			 * node.put("result", result);
			 * 
			 * return node;
			 */
		} catch (Exception ex) {
			/*
			 * LOG.error("Error while calculating the cart", ex); String msg=
			 * "XXRD"; GlobalMessages.addErrorMessage(model, msg);
			 */

			LOG.error("Error while calculating the cart", ex);

			/*
			 * String msg =
			 * getMessageWithDefaultContext("validate.visibilityClient") +
			 * ":TEST"; LOG.error(msg);
			 * 
			 * node.put("status", AJAX_STATUS_ERROR); node.put("message", msg);
			 * 
			 * return node;
			 */
			return null;
		}

		/*
		 * String msg = "Texto de prueba"; //model.addAttribute(ERROR_MSG_TYPE,
		 * msg); GlobalMessages.addMessage(model,
		 * GlobalMessages.ERROR_MESSAGES_HOLDER, msg, null);
		 */

		// return null;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/getDeliveryModes.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public List<? extends DeliveryModeData> getDeliveryModes() {
		final List<? extends DeliveryModeData> deliveryModes = getCheckoutFlowFacade().getSupportedDeliveryModes();
		return deliveryModes == null ? Collections.<ZoneDeliveryModeData> emptyList() : deliveryModes;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setDeliveryMode.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData setDeliveryMode(@RequestParam(value = "modeCode")
	final String modeCode) {
		if (getCheckoutFlowFacade().setDeliveryMode(modeCode)) {
			// return getCheckoutFlowFacade().getCheckoutCart();
			return neorisCartFacade.getSessionCart();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/summary/getDeliveryAddressForm.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getDeliveryAddressForm(final Model model, @RequestParam(value = "addressId")
	final String addressId, @RequestParam(value = "createUpdateStatus")
	final String createUpdateStatus) {
		AddressData addressData = null;
		if (addressId != null && !addressId.isEmpty()) {
			addressData = getCheckoutFlowFacade().getDeliveryAddressForCode(addressId);
		}

		final AddressForm addressForm = new AddressForm();

		final boolean hasAddressData = addressData != null;
		if (hasAddressData) {
			addressForm.setAddressId(addressData.getId());
			addressForm.setTitleCode(addressData.getTitleCode());
			addressForm.setFirstName(addressData.getFirstName());
			addressForm.setLastName(addressData.getLastName());
			addressForm.setLine1(addressData.getLine1());
			addressForm.setLine2(addressData.getLine2());
			addressForm.setTownCity(addressData.getTown());
			addressForm.setPostcode(addressData.getPostalCode());
			addressForm.setCountryIso(addressData.getCountry().getIsocode());
			addressForm.setShippingAddress(Boolean.valueOf(addressData.isShippingAddress()));
			addressForm.setBillingAddress(Boolean.valueOf(addressData.isBillingAddress()));
		}

		model.addAttribute("edit", Boolean.valueOf(hasAddressData));
		model.addAttribute("noAddresses", Boolean.valueOf(getUserFacade().isAddressBookEmpty()));

		model.addAttribute(addressForm);
		model.addAttribute("createUpdateStatus", createUpdateStatus);

		// Work out if the address form should be displayed based on the payment
		// type
		// final B2BPaymentTypeData paymentType =
		// getCheckoutFlowFacade().getCheckoutCart().getPaymentType();
		final B2BPaymentTypeData paymentType = neorisCartFacade.getSessionCart().getPaymentType();
		final boolean payOnAccount = paymentType != null
				&& CheckoutPaymentType.ACCOUNT.getCode().equals(paymentType.getCode());
		model.addAttribute("showAddressForm", Boolean.valueOf(!payOnAccount));

		return ControllerConstants.Views.Fragments.SingleStepCheckout.DeliveryAddressFormPopup;
	}

	@RequestMapping(value = "/summary/createUpdateDeliveryAddress.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public String createUpdateDeliveryAddress(final Model model, @Valid
	final AddressForm form, final BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("edit", Boolean.valueOf(StringUtils.isNotBlank(form.getAddressId())));
			// Work out if the address form should be displayed based on the
			// payment type
			// final B2BPaymentTypeData paymentType =
			// getCheckoutFlowFacade().getCheckoutCart().getPaymentType();
			final B2BPaymentTypeData paymentType = neorisCartFacade.getSessionCart().getPaymentType();
			final boolean payOnAccount = paymentType != null
					&& CheckoutPaymentType.ACCOUNT.getCode().equals(paymentType.getCode());
			model.addAttribute("showAddressForm", Boolean.valueOf(!payOnAccount));

			return ControllerConstants.Views.Fragments.SingleStepCheckout.DeliveryAddressFormPopup;
		}

		// create delivery address and set it on cart
		final AddressData addressData = new AddressData();
		addressData.setId(form.getAddressId());
		addressData.setTitleCode(form.getTitleCode());
		addressData.setFirstName(form.getFirstName());
		addressData.setLastName(form.getLastName());
		addressData.setLine1(form.getLine1());
		addressData.setLine2(form.getLine2());
		addressData.setTown(form.getTownCity());
		addressData.setPostalCode(form.getPostcode());
		addressData.setCountry(getI18NFacade().getCountryForIsocode(form.getCountryIso()));
		addressData.setShippingAddress(
				Boolean.TRUE.equals(form.getShippingAddress()) || Boolean.TRUE.equals(form.getSaveInAddressBook()));

		addressData.setVisibleInAddressBook(
				Boolean.TRUE.equals(form.getSaveInAddressBook()) || StringUtils.isNotBlank(form.getAddressId()));
		addressData.setDefaultAddress(Boolean.TRUE.equals(form.getDefaultAddress()));

		if (StringUtils.isBlank(form.getAddressId())) {
			getUserFacade().addAddress(addressData);
		} else {
			getUserFacade().editAddress(addressData);
		}

		getCheckoutFlowFacade().setDeliveryAddress(addressData);

		// if (getCheckoutFlowFacade().getCheckoutCart().getDeliveryMode() ==
		// null)
		if (neorisCartFacade.getSessionCart().getDeliveryMode() == null) {
			getCheckoutFlowFacade().setDeliveryModeIfAvailable();
		}

		model.addAttribute("createUpdateStatus", "Success");
		model.addAttribute("addressId", addressData.getId());

		return REDIRECT_PREFIX + "/checkout/single/summary/getDeliveryAddressForm.json?addressId=" + addressData.getId()
				+ "&createUpdateStatus=Success";
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setCostCenter.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData setCostCenter(@RequestParam(value = "costCenterId")
	final String costCenterId) {
		// remove the delivery address;
		getCheckoutFlowFacade().removeDeliveryAddress();
		getCheckoutFlowFacade().removeDeliveryMode();

		// final CartData cartData =
		// getCheckoutFlowFacade().setCostCenterForCart(costCenterId,
		// this.getCheckoutFlowFacade().getCheckoutCart().getCode());

		final CartData cartData = getCheckoutFlowFacade().setCostCenterForCart(costCenterId,
				neorisCartFacade.getSessionCart().getCode());

		return cartData;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/updateCostCenter.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData updateCostCenterForCart(@RequestParam(value = "costCenterId")
	final String costCenterId) {
		// final CartData cartData =
		// getCheckoutFlowFacade().setCostCenterForCart(costCenterId,
		// getCheckoutFlowFacade().getCheckoutCart().getCode());

		final CartData cartData = getCheckoutFlowFacade().setCostCenterForCart(costCenterId,
				neorisCartFacade.getSessionCart().getCode());

		return cartData;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/getSavedCards.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public List<CCPaymentInfoData> getSavedCards() {
		final List<CCPaymentInfoData> paymentInfos = getUserFacade().getCCPaymentInfos(true);
		return paymentInfos == null ? Collections.<CCPaymentInfoData> emptyList() : paymentInfos;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setPaymentDetails.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData setPaymentDetails(@RequestParam(value = "paymentId")
	final String paymentId) {
		if (StringUtils.isNotBlank(paymentId) && getCheckoutFlowFacade().setPaymentDetails(paymentId)) {
			// return getCheckoutFlowFacade().getCheckoutCart();
			return neorisCartFacade.getSessionCart();
		}

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setPaymentType.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData setPaymentType(@RequestParam(value = "paymentType")
	final String paymentType) {
		getCheckoutFlowFacade().removeDeliveryAddress();
		getCheckoutFlowFacade().removeDeliveryMode();
		// getCheckoutFlowFacade().setCostCenterForCart("",
		// getCheckoutFlowFacade().getCheckoutCart().getCode());
		getCheckoutFlowFacade().setCostCenterForCart("", neorisCartFacade.getSessionCart().getCode());
		getCheckoutFlowFacade().setPaymentTypeSelectedForCheckout(paymentType);
		// return getCheckoutFlowFacade().getCheckoutCart();
		return neorisCartFacade.getSessionCart();
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setPurchaseOrderNumber.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData setPurchaseOrderNumber(@RequestParam(value = "purchaseOrderNumber")
	final String purchaseOrderNumber) {
		getCheckoutFlowFacade().setPurchaseOrderNumber(purchaseOrderNumber);
		// return getCheckoutFlowFacade().getCheckoutCart();
		return neorisCartFacade.getSessionCart();
	}

	@ResponseBody
	@RequestMapping(value = "/summary/validatePO.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public Object isPOAlreadyUsed(@RequestParam(value = "purchaseOrderNumber")
	final String purchaseOrderNumber) {

		ObjectNode node = JsonNodeFactory.instance.objectNode();
		boolean isPOAlreadyUsed = false;

		if (StringUtils.isNotBlank(purchaseOrderNumber))
			isPOAlreadyUsed = neorisB2BOrderService.isPOAlreadyUsed(purchaseOrderNumber, getRootUnit());

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);
		node.put("isPOAlreadyUsed", isPOAlreadyUsed);

		return node;
	}

	@ResponseBody
	@RequestMapping(value = "/summary/setRequestedDeliveryDate.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public CartData setRequestedDeliveredDate(@RequestParam(value = "requestedDeliveryDate")
	final String requestedDeliveryDate) {
		// <formDate:formFormatDate cambio el dateFormat
		// OJO hay que arreglar aqui porque tronara para USA por el formato de
		// la fecha
		// DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		LOG.info("baseStoreModel " + baseStoreModel.getUid());
		DateFormat format;
		String currentLanguage = neorisFacade.getCurrentUser().getSessionLanguage().getIsocode();
		String newDate = "";

		if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000")) {
			// 01-Mar-2016
			LOG.info("dentro del if 1000 ");
			String day = requestedDeliveryDate.substring(0, 2);
			String month = requestedDeliveryDate.substring(3, 6);
			String year = requestedDeliveryDate.substring(7, 11);
			String monthSpanish = month;
			/*
			 * if(currentLanguage.equalsIgnoreCase("en")) { LOG.info(
			 * "dentro del if 1000, leng EN "); switch(month){ case "Jan":
			 * monthSpanish = "Ene"; break; case "Apr": monthSpanish = "Abr";
			 * break; case "Aug": monthSpanish = "Ago"; break; case "Dec":
			 * monthSpanish = "Dic"; break; } }
			 */

			// Codigo para QA, validar que codigo necesita PRD
			switch (month) {
			case "Ene":
				monthSpanish = "Jan";
				break;
			case "Abr":
				monthSpanish = "Apr";
				break;
			case "Ago":
				monthSpanish = "Aug";
				break;
			case "Dic":
				monthSpanish = "Dec";
				break;
			}

			newDate = day + "-" + monthSpanish + "-" + year;
			format = new SimpleDateFormat("dd-MMM-yyyy");
		} else {
			LOG.info("dentro del else 2000 ");
			// Mar-01-2016
			String month = requestedDeliveryDate.substring(0, 3);
			String day = requestedDeliveryDate.substring(4, 6);
			String year = requestedDeliveryDate.substring(7, 11);
			String monthSpanish = month;

			/*
			 * if(currentLanguage.equalsIgnoreCase("en")) { LOG.info(
			 * "dentro del else 2000, leng EN "); switch(month){ case "Jan":
			 * monthSpanish = "Ene"; break; case "Apr": monthSpanish = "Abr";
			 * break; case "Aug": monthSpanish = "Ago"; break; case "Dec":
			 * monthSpanish = "Dic"; break; } }
			 */

			// Codigo para QA, validar que codigo necesita PRD
			switch (month) {
			case "Ene":
				monthSpanish = "Jan";
				break;
			case "Abr":
				monthSpanish = "Apr";
				break;
			case "Ago":
				monthSpanish = "Aug";
				break;
			case "Dic":
				monthSpanish = "Dec";
				break;
			}

			newDate = monthSpanish + "-" + day + "-" + year;
			format = new SimpleDateFormat("MMM-dd-yyyy");
		}

		try {
			LOG.info("newDate " + newDate);
			// Date date = format.parse(requestedDeliveryDate);
			Date date = format.parse(newDate);
			LOG.info("date formateada " + date);
			getCheckoutFlowFacade().setRequestedDeliveryDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// return getCheckoutFlowFacade().getCheckoutCart();
		return neorisCartFacade.getSessionCart();
	}

	@RequestMapping(value = "/summary/getPaymentDetailsForm.json", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPaymentDetailsForm(final Model model, @RequestParam(value = "paymentId")
	final String paymentId, @RequestParam(value = "createUpdateStatus")
	final String createUpdateStatus) {
		CCPaymentInfoData paymentInfoData = null;
		if (StringUtils.isNotBlank(paymentId)) {
			paymentInfoData = getUserFacade().getCCPaymentInfoForCode(paymentId);
		}

		final PaymentDetailsForm paymentDetailsForm = new PaymentDetailsForm();

		if (paymentInfoData != null) {
			paymentDetailsForm.setPaymentId(paymentInfoData.getId());
			paymentDetailsForm.setCardTypeCode(paymentInfoData.getCardType());
			paymentDetailsForm.setNameOnCard(paymentInfoData.getAccountHolderName());
			paymentDetailsForm.setCardNumber(paymentInfoData.getCardNumber());
			paymentDetailsForm.setStartMonth(paymentInfoData.getStartMonth());
			paymentDetailsForm.setStartYear(paymentInfoData.getStartYear());
			paymentDetailsForm.setExpiryMonth(paymentInfoData.getExpiryMonth());
			paymentDetailsForm.setExpiryYear(paymentInfoData.getExpiryYear());
			paymentDetailsForm.setSaveInAccount(Boolean.valueOf(paymentInfoData.isSaved()));
			paymentDetailsForm.setIssueNumber(paymentInfoData.getIssueNumber());

			final AddressForm addressForm = new AddressForm();
			final AddressData addressData = paymentInfoData.getBillingAddress();
			if (addressData != null) {
				addressForm.setAddressId(addressData.getId());
				addressForm.setTitleCode(addressData.getTitleCode());
				addressForm.setFirstName(addressData.getFirstName());
				addressForm.setLastName(addressData.getLastName());
				addressForm.setLine1(addressData.getLine1());
				addressForm.setLine2(addressData.getLine2());
				addressForm.setTownCity(addressData.getTown());
				addressForm.setPostcode(addressData.getPostalCode());
				addressForm.setCountryIso(addressData.getCountry().getIsocode());
				addressForm.setShippingAddress(Boolean.valueOf(addressData.isShippingAddress()));
				addressForm.setBillingAddress(Boolean.valueOf(addressData.isBillingAddress()));
			}

			paymentDetailsForm.setBillingAddress(addressForm);
		}

		model.addAttribute("edit", Boolean.valueOf(paymentInfoData != null));
		model.addAttribute("paymentInfoData", getUserFacade().getCCPaymentInfos(true));
		model.addAttribute(paymentDetailsForm);
		model.addAttribute("createUpdateStatus", createUpdateStatus);
		return ControllerConstants.Views.Fragments.SingleStepCheckout.PaymentDetailsFormPopup;
	}

	@RequestMapping(value = "/summary/createUpdatePaymentDetails.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public String createUpdatePaymentDetails(final Model model, @Valid
	final PaymentDetailsForm form, final BindingResult bindingResult) {
		paymentDetailsValidator.validate(form, bindingResult);

		final boolean editMode = StringUtils.isNotBlank(form.getPaymentId());

		if (bindingResult.hasErrors()) {
			model.addAttribute("edit", Boolean.valueOf(editMode));

			return ControllerConstants.Views.Fragments.SingleStepCheckout.PaymentDetailsFormPopup;
		}

		final CCPaymentInfoData paymentInfoData = new CCPaymentInfoData();
		paymentInfoData.setId(form.getPaymentId());
		paymentInfoData.setCardType(form.getCardTypeCode());
		paymentInfoData.setAccountHolderName(form.getNameOnCard());
		paymentInfoData.setCardNumber(form.getCardNumber());
		paymentInfoData.setStartMonth(form.getStartMonth());
		paymentInfoData.setStartYear(form.getStartYear());
		paymentInfoData.setExpiryMonth(form.getExpiryMonth());
		paymentInfoData.setExpiryYear(form.getExpiryYear());
		paymentInfoData.setSaved(Boolean.TRUE.equals(form.getSaveInAccount()));
		paymentInfoData.setIssueNumber(form.getIssueNumber());

		final AddressData addressData;
		if (!editMode && Boolean.FALSE.equals(form.getNewBillingAddress())) {
			addressData = getCheckoutCart().getDeliveryAddress();
			if (addressData == null) {
				GlobalMessages.addErrorMessage(model,
						"checkout.paymentMethod.createSubscription.billingAddress.noneSelected");

				model.addAttribute("edit", Boolean.valueOf(editMode));
				return ControllerConstants.Views.Fragments.SingleStepCheckout.PaymentDetailsFormPopup;
			}

			addressData.setBillingAddress(true); // mark this as billing address
		} else {
			final AddressForm addressForm = form.getBillingAddress();

			addressData = new AddressData();
			if (addressForm != null) {
				addressData.setId(addressForm.getAddressId());
				addressData.setTitleCode(addressForm.getTitleCode());
				addressData.setFirstName(addressForm.getFirstName());
				addressData.setLastName(addressForm.getLastName());
				addressData.setLine1(addressForm.getLine1());
				addressData.setLine2(addressForm.getLine2());
				addressData.setTown(addressForm.getTownCity());
				addressData.setPostalCode(addressForm.getPostcode());
				addressData.setCountry(getI18NFacade().getCountryForIsocode(addressForm.getCountryIso()));
				addressData.setShippingAddress(Boolean.TRUE.equals(addressForm.getShippingAddress()));
				addressData.setBillingAddress(Boolean.TRUE.equals(addressForm.getBillingAddress()));
			}
		}

		paymentInfoData.setBillingAddress(addressData);

		final CCPaymentInfoData newPaymentSubscription = getCheckoutFlowFacade()
				.createPaymentSubscription(paymentInfoData);
		if (newPaymentSubscription != null && StringUtils.isNotBlank(newPaymentSubscription.getSubscriptionId())) {
			if (Boolean.TRUE.equals(form.getSaveInAccount()) && getUserFacade().getCCPaymentInfos(true).size() <= 1) {
				getUserFacade().setDefaultPaymentInfo(newPaymentSubscription);
			}
			getCheckoutFlowFacade().setPaymentDetails(newPaymentSubscription.getId());
		} else {
			GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.createSubscription.failed");

			model.addAttribute("edit", Boolean.valueOf(editMode));
			return ControllerConstants.Views.Fragments.SingleStepCheckout.PaymentDetailsFormPopup;
		}

		model.addAttribute("createUpdateStatus", "Success");
		model.addAttribute("paymentId", newPaymentSubscription.getId());

		return REDIRECT_PREFIX + "/checkout/single/summary/getPaymentDetailsForm.json?paymentId="
				+ paymentInfoData.getId() + "&createUpdateStatus=Success";
	}

	@RequestMapping(value = "/termsAndConditions")
	@RequireHardLogIn
	public String getTermsAndConditions(final Model model) throws CMSItemNotFoundException {
		final ContentPageModel pageForRequest = getCmsPageService().getPageForLabel("/termsAndConditions");
		storeCmsPageInModel(model, pageForRequest);
		setUpMetaDataForContentPage(model, pageForRequest);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, contentPageBreadcrumbBuilder.getBreadcrumbs(pageForRequest));
		return ControllerConstants.Views.Fragments.Checkout.TermsAndConditionsPopup;
	}

	@RequestMapping(value = "/orderConditions")
	@RequireHardLogIn
	public String getOrderConditions(final Model model) throws CMSItemNotFoundException {
		final ContentPageModel pageForRequest = getCmsPageService().getPageForLabel("/orderConditions");
		storeCmsPageInModel(model, pageForRequest);
		setUpMetaDataForContentPage(model, pageForRequest);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, contentPageBreadcrumbBuilder.getBreadcrumbs(pageForRequest));
		return ControllerConstants.Views.Fragments.Checkout.OrderConditionsPopup;
	}

	@RequestMapping(value = "/FAQ")
	@RequireHardLogIn
	public String getFAQ(final Model model) throws CMSItemNotFoundException {
		final ContentPageModel pageForRequest = getCmsPageService().getPageForLabel("/FAQ");
		storeCmsPageInModel(model, pageForRequest);
		setUpMetaDataForContentPage(model, pageForRequest);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, contentPageBreadcrumbBuilder.getBreadcrumbs(pageForRequest));
		return ControllerConstants.Views.Fragments.Checkout.TermsAndConditionsPopup;
	}

	@RequestMapping(value = "/placeOrder", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String placeOrder(final HttpServletRequest request, final Model model, String requestedDeliveryDate, @Valid
	final PlaceOrderForm placeOrderForm, final BindingResult bindingResult) throws Exception {
		// validate the cart
		final CartData cartData = neorisCartFacade.getSessionCart();
		final boolean isAccountPaymentType = CheckoutPaymentType.ACCOUNT.getCode()
				.equals(cartData.getPaymentType().getCode());
		final String securityCode = placeOrderForm.getSecurityCode();
		// final boolean termsChecked = placeOrderForm.isTermsCheck();
		final boolean termsChecked = true;

		if (!termsChecked) {
			GlobalMessages.addErrorMessage(model, "checkout.error.terms.not.accepted");
		}

		if (validateOrderform(placeOrderForm, model, cartData)) {
			placeOrderForm.setTermsCheck(false);
			model.addAttribute(placeOrderForm);
			return checkoutSummary(model);
		}

		if (!isAccountPaymentType && !getCheckoutFlowFacade().authorizePayment(securityCode)) {
			return checkoutSummary(model);
		}

		// validate quote negotiation
		if (placeOrderForm.isNegotiateQuote()) {
			/*
			 * if
			 * (StringUtils.isBlank(placeOrderForm.getQuoteRequestDescription())
			 * ) { GlobalMessages.addErrorMessage(model,
			 * "checkout.error.noQuoteDescription"); return
			 * checkoutSummary(model); } else {
			 * getCheckoutFlowFacade().setQuoteRequestDescription(XSSFilterUtil.
			 * filter(placeOrderForm.getQuoteRequestDescription())); }
			 */

			BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();
			if (currentBaseStore.getPriceNegotiationEnabled()) {
				getCheckoutFlowFacade().setQuoteNegotiablePrices(placeOrderForm.getNegotiablePrices());
			}

			if (currentBaseStore.getUid().equalsIgnoreCase("6000") && neorisFacade.isInternalCustomer()) {
				getCheckoutFlowFacade().setQuoteNegotiablePrices(placeOrderForm.getNegotiablePrices());
			}

			getCheckoutFlowFacade()
					.setQuoteRequestDescription(XSSFilterUtil.filter(placeOrderForm.getQuoteRequestDescription()));
		}

		if (!termsChecked) {
			return checkoutSummary(model);
		}

		// Set is order API, when cart includes API Products
		getCheckoutFlowFacade().setIsAPIOrder();

		// validate replenishment
		if (placeOrderForm.isReplenishmentOrder()) {
			if (placeOrderForm.getReplenishmentStartDate() == null) {
				bindingResult.addError(
						new FieldError(placeOrderForm.getClass().getSimpleName(), "replenishmentStartDate", ""));
				GlobalMessages.addErrorMessage(model, "checkout.error.replenishment.noStartDate");
				return checkoutSummary(model);
			}
			if (B2BReplenishmentRecurrenceEnum.WEEKLY.equals(placeOrderForm.getReplenishmentRecurrence())) {
				if (CollectionUtils.isEmpty(placeOrderForm.getnDaysOfWeek())) {
					GlobalMessages.addErrorMessage(model, "checkout.error.replenishment.no.Frequency");
					return checkoutSummary(model);
				}
			}
			final TriggerData triggerData = new TriggerData();
			populateTriggerDataFromPlaceOrderForm(placeOrderForm, triggerData);
			final ScheduledCartData scheduledCartData = getCheckoutFlowFacade().scheduleOrder(triggerData);
			return REDIRECT_PREFIX + "/checkout/replenishmentConfirmation/" + scheduledCartData.getJobCode();
		}

		OrderData orderData;
		try {
			String shippingInstructions = null;
			if (!placeOrderForm.getShippingInstructions().trim().isEmpty()) {
				shippingInstructions = placeOrderForm.getShippingInstructions();
			}

			getCheckoutFlowFacade().setShippingInstructions(XSSFilterUtil.filter(shippingInstructions));

			// MultipartFile multipartFile =
			// getSessionService().getAttribute(ControllerConstants.Views.Pages.Order.Document);
			orderData = getCheckoutFlowFacade().placeOrderWith(null);
			orderData.setTPago(cartData.getTPago());
			// La siguiente ln no estaba comentada. Hice la prueba debugeando,
			// parece que no lo necesita, ya que se llena correctamente esta
			// info previamente.
			// orderData.setRequestedDeliveryDate(cartData.getRequestedDeliveryDate());
			//
			// // order was placed, remove the container from the session and
			// filesystem
			// if (multipartFile != null)
			// {
			// // clear the session information
			// getSessionService().removeAttribute(ControllerConstants.Views.Pages.Order.Document);
			// }
		} catch (InvalidCartException e) {
			LOG.error("Error while placing order due to: " + e.getMessage());
			GlobalMessages.addErrorMessage(model, "checkout.placeOrder.failed.cart");
			placeOrderForm.setNegotiateQuote(true);
			model.addAttribute(placeOrderForm);
			return checkoutSummary(model);
		} catch (final Exception e) {
			LOG.error("Error while placing order due to: " + e.getMessage());
			GlobalMessages.addErrorMessage(model, "checkout.placeOrder.failed");
			placeOrderForm.setNegotiateQuote(true);
			model.addAttribute(placeOrderForm);
			return checkoutSummary(model);
		}

		// NEORIS_CHANGE #83 delete the template cart when the order is placed
		Wishlist2Model wishlist = sessionService.getAttribute(ControllerConstants.Templates.DRAFT);
		if (wishlist != null) {
			try {
				neorisFacade.deleteWishlist(wishlist.getPk().getLong());

				sessionService.removeAttribute(ControllerConstants.Templates.DRAFT);
			} catch (Exception e) {
				LOG.error("error while deleting draft from database", e);
			}
		}

		final BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();

		if (placeOrderForm.isNegotiateQuote()) {
			if (currentBaseStore.getQuickCheckoutConfirmationEnabled() != null
					&& currentBaseStore.getQuickCheckoutConfirmationEnabled().booleanValue()) {
				return REDIRECT_PREFIX + "/checkout/quickQuoteOrderConfirmation/" + orderData.getCode();
			}

			return REDIRECT_PREFIX + "/checkout/quoteOrderConfirmation/" + orderData.getCode();
		} else {
			if (currentBaseStore.getQuickCheckoutConfirmationEnabled() != null
					&& currentBaseStore.getQuickCheckoutConfirmationEnabled().booleanValue()) {
				return REDIRECT_PREFIX + "/checkout/quickOrderConfirmation/" + orderData.getCode();
			}

			return REDIRECT_PREFIX + "/checkout/orderConfirmation/" + orderData.getCode();
		}
	}

	protected boolean validateOrderform(final PlaceOrderForm placeOrderForm, final Model model,
			final CartData cartData) {
		final boolean accountPaymentType = CheckoutPaymentType.ACCOUNT.getCode()
				.equals(cartData.getPaymentType().getCode());
		final String securityCode = placeOrderForm.getSecurityCode();
		boolean invalid = false;

		if (cartData.getDeliveryAddress() == null) {
			GlobalMessages.addErrorMessage(model, "checkout.deliveryAddress.notSelected");
			invalid = true;
		}

		if (cartData.getDeliveryMode() == null) {
			GlobalMessages.addErrorMessage(model, "checkout.deliveryMethod.notSelected");
			invalid = true;
		}

		if (!accountPaymentType && cartData.getPaymentInfo() == null) {
			GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.notSelected");
			invalid = true;
		} else if (!accountPaymentType && StringUtils.isBlank(securityCode)) {
			GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.noSecurityCode");
			invalid = true;
		}

		return invalid;
	}

	@RequestMapping(value = "/summary/reorder", method = { RequestMethod.PUT, RequestMethod.POST })
	@RequireHardLogIn
	public String reorder(@RequestParam(value = "orderCode")
	final String orderCode, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException, InvalidCartException, ParseException, CommerceCartModificationException {
		// create a cart from the order and set it as session cart.
		getCheckoutFlowFacade().createCartFromOrder(orderCode);
		// validate for stock and availability
		final List<? extends CommerceCartModification> cartModifications = getCheckoutFlowFacade()
				.validateSessionCart();
		for (final CommerceCartModification cartModification : cartModifications) {
			if (CommerceCartModificationStatus.NO_STOCK.equals(cartModification.getStatusCode())) {
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
						"basket.page.message.update.reducedNumberOfItemsAdded.noStock",
						new Object[] { cartModification.getEntry().getProduct().getName() });
				break;
			} else if (cartModification.getQuantity() != cartModification.getQuantityAdded()) {
				// item has been modified to match available stock levels
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
						"basket.information.quantity.adjusted");
				break;
			}
			// TODO: handle more specific messaging, i.e. out of stock, product
			// not available
		}
		return REDIRECT_PREFIX + "/checkout/single/summary";// checkoutSummary(model);
	}

	/**
	 * Need to move out of controller utility method for Replenishment
	 * 
	 */
	protected List<String> getNumberRange(final int startNumber, final int endNumber) {
		final List<String> numbers = new ArrayList<String>();
		for (int number = startNumber; number <= endNumber; number++) {
			numbers.add(String.valueOf(number));
		}
		return numbers;
	}

	/**
	 * Util method to copy values from place order form to TriggerData for
	 * replenishment
	 * 
	 * @param placeOrderForm
	 * @param triggerData
	 * @throws ParseException
	 */
	protected void populateTriggerDataFromPlaceOrderForm(final PlaceOrderForm placeOrderForm,
			final TriggerData triggerData) throws ParseException {
		final Date replenishmentStartDate = placeOrderForm.getReplenishmentStartDate();
		final Calendar calendar = Calendar.getInstance(getI18nService().getCurrentTimeZone(),
				getI18nService().getCurrentLocale());
		triggerData.setActivationTime(
				(replenishmentStartDate.before(calendar.getTime()) ? calendar.getTime() : replenishmentStartDate));

		final B2BReplenishmentRecurrenceEnum recurrenceValue = placeOrderForm.getReplenishmentRecurrence();

		if (B2BReplenishmentRecurrenceEnum.DAILY.equals(recurrenceValue)) {
			triggerData.setDay(Integer.valueOf(placeOrderForm.getnDays()));
			triggerData.setRelative(Boolean.TRUE);
		} else if (B2BReplenishmentRecurrenceEnum.WEEKLY.equals(recurrenceValue)) {
			triggerData.setDaysOfWeek(placeOrderForm.getnDaysOfWeek());
			triggerData.setWeekInterval(Integer.valueOf(placeOrderForm.getnWeeks()));
			triggerData.setHour(Integer.valueOf(0));
			triggerData.setMinute(Integer.valueOf(0));
		} else if (B2BReplenishmentRecurrenceEnum.MONTHLY.equals(recurrenceValue)) {
			triggerData.setDay(Integer.valueOf(placeOrderForm.getNthDayOfMonth()));
			triggerData.setRelative(Boolean.FALSE);
		}
	}

	/**
	 * Data class used to hold a drop down select option value. Holds the code
	 * identifier as well as the display name.
	 */
	public static class SelectOption {
		private final String code;
		private final String name;

		public SelectOption(final String code, final String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}

	@RequestMapping(value = "/upload-document", method = RequestMethod.POST)
	public String acceptFile(Map<String, Object> model, MultipartHttpServletRequest request) {
		ObjectNode node = JsonNodeFactory.instance.objectNode();

		MultipartFile multipartFile = request.getFile("file");

		try {
			if (multipartFile == null) {
				throw new RuntimeException(getMessageWithDefaultContext("checkout.documentupload.no_file_set"));
			}

			// validate file size and extension
			if (multipartFile.getSize() == 0) {
				throw new RuntimeException(getMessageWithDefaultContext("checkout.documentupload.error.file.empty"));
			}

			if (multipartFile.getOriginalFilename().contains(EXE_FILE_EXTENSION)) {
				// file has .exe extension
				throw new RuntimeException(
						getMessageWithDefaultContext("checkout.documentupload.error.file.invalidExtension"));
			}

			if (multipartFile.getSize() > MAX_ALLOWED_MEDIA_MAX_SIZE) {
				// file exceed size
				throw new RuntimeException(
						getMessageWithDefaultContext("checkout.documentupload.error.file.exceededSize"));
			}

			// review number of attachments
			// CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
			CartData cartData = neorisCartFacade.getSessionCart();

			if (cartData.getAttachmentsPO() != null && cartData.getAttachmentsPO().size() == MAX_PO_ATTACHMENTS) {
				throw new RuntimeException(getMessageWithDefaultContext("checkout.documentupload.maxPOAttachments",
						new Object[] { MAX_PO_ATTACHMENTS }));
			}

			cartData = getCheckoutFlowFacade().addAttachment(multipartFile);

			node.put("status", AJAX_STATUS_OK);
			node.put("message", getMessageWithDefaultContext("checkout.documentupload.file_uploaded"));

			org.codehaus.jackson.node.ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

			for (NeorisMediaData neorisMediaData : cartData.getAttachmentsPO()) {
				ObjectNode nodeAttachment = JsonNodeFactory.instance.objectNode();
				nodeAttachment.put("name", neorisMediaData.getName());
				nodeAttachment.put("code", neorisMediaData.getCode());
				nodeAttachment.put("url", neorisMediaData.getUrl());

				arrayNode.add(nodeAttachment);
			}

			node.put("attachmentsPO", arrayNode);
		} catch (IOException ioE) {
			String msg = getMessageWithDefaultContext("checkout.documentupload.error_while_uploading_file");
			LOG.error(msg, ioE);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", msg);
		} catch (RuntimeException ex) {
			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		try {
			// populate the iframe with the callback function
			String callbackFunction = getCallbackFunctionNameFrom(request);
			populateModelForIFrame(callbackFunction, node, model);
		} catch (Exception ex) {
			LOG.error("error while creating node response", ex);
		}

		return "pages/misc/iframe-post";
	}

	@RequestMapping(value = "/summary/downloadAttachment", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public void downloadAttachmentFromCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mediaCode = request.getParameter("poAttachmentCode");

		if (StringUtils.isEmpty(mediaCode)) {
			throw new Exception(getMessageWithDefaultContext(""));
		}

		NeorisMediaModel media = getCheckoutFlowFacade().getAttachment(mediaCode);

		if (media == null) {
			throw new Exception(getMessageWithDefaultContext(""));
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = mediaService.getStreamFromMedia(media);

			String documentName = media.getRealFileName();
			String documentType = media.getMime();

			response.setContentType(documentType);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + documentName + "\";");

			outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
		} catch (Exception ex) {
			LOG.error("error while getting document", ex);
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/summary/deleteAttachment.json", method = RequestMethod.POST)
	@RequireHardLogIn
	public Object deleteAttachmentFromCart(@RequestParam(value = "code")
	final String mediaCode) {
		ObjectNode node = JsonNodeFactory.instance.objectNode();

		try {
			if (StringUtils.isEmpty(mediaCode)) {
				throw new Exception(getMessageWithDefaultContext(""));
			}

			CartData cartData = getCheckoutFlowFacade().deleteAttachment(mediaCode);

			node.put("status", AJAX_STATUS_OK);
			node.put("message", "");

			org.codehaus.jackson.node.ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

			for (NeorisMediaData neorisMediaData : cartData.getAttachmentsPO()) {
				ObjectNode nodeAttachment = JsonNodeFactory.instance.objectNode();
				nodeAttachment.put("name", neorisMediaData.getName());
				nodeAttachment.put("code", neorisMediaData.getCode());
				nodeAttachment.put("url", neorisMediaData.getUrl());

				arrayNode.add(nodeAttachment);
			}

			node.put("attachmentsPO", arrayNode);
		} catch (Exception ex) {
			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/shipping-instructions-popup", method = RequestMethod.GET)
	public String showShippingInstructionsPopup(final Model model, final HttpServletRequest request) {
		return POPUP_PAGE;
	}

	@RequestMapping(value = "/summary/no-price-delete-popup", method = RequestMethod.POST)
	public String deleteNoPriceEntries(final Model model, final HttpServletRequest request) {
		AbstractOrderModel cartModel = (CartModel) getSessionService().getAttribute("cart");
		List<AbstractOrderEntryModel> aOE = cartModel.getEntries();

		List<AbstractOrderEntryModel> removeEntries = new LinkedList<>();
		List<AbstractOrderEntryModel> addEntries = new LinkedList<>();

		CartData cartData = neorisCartFacade.getSessionCart();
		List<Integer> entryNumber = new LinkedList<>();

		for (OrderEntryData oe : cartData.getEntries()) {
			if (null == oe.getPricePerFeet() && null == oe.getPricePerPc()
					&& null == oe.getPricePerTon() ) {
				Integer entry = oe.getEntryNumber();
				entryNumber.add(entry);
				removeEntries.add(aOE.get(entry));
			}
		}
		for (AbstractOrderEntryModel entry : aOE) {
			boolean flag = false;
			for (Integer i : entryNumber) {
				if (i == entry.getEntryNumber()) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				addEntries.add(entry);
			}
		}
		cartModel.setEntries(addEntries);
		modelService.removeAll(removeEntries);
		modelService.saveAll(addEntries);
		modelService.save(cartModel);
		modelService.refresh(cartModel);

		return REDIRECT_PREFIX + "/checkout/single/summary";

	}
}
