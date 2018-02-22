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
package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.constants.GeneratedB2BApprovalprocessConstants.Attributes.B2BCustomer;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.B2BOrderFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorfacades.order.data.ScheduledCartData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.workflow.enums.WorkflowActionType;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.enums.NoticeType;
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.model.NoticeModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.AddNoticeParameters;
import mx.neoris.core.services.B2BCustomerSaveSettingParameters;
import mx.neoris.core.services.B2BCustomerSettingParameters;
import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisQuoteSearchParameters;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisNoticesFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.document.data.NoticeData;
import mx.neoris.facades.orders.NeorisB2BOrderFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.Breadcrumb;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.AddIncidentForm;
import mx.neoris.storefront.forms.AddressForm;
import mx.neoris.storefront.forms.B2BCustomerSettingForm;
import mx.neoris.storefront.forms.B2BCustomerSettingsSearchForm;
import mx.neoris.storefront.forms.CreateNoticeForm;
import mx.neoris.storefront.forms.NeorisOrderApprovalSearchForm;
import mx.neoris.storefront.forms.NeorisQuoteSearchForm;
import mx.neoris.storefront.forms.OrderApprovalDecisionForm;
import mx.neoris.storefront.forms.OrderHistorySearchForm;
import mx.neoris.storefront.forms.QuoteOrderForm;
import mx.neoris.storefront.forms.ReorderForm;
import mx.neoris.storefront.forms.UpdateEmailForm;
import mx.neoris.storefront.forms.UpdatePasswordForm;
import mx.neoris.storefront.forms.UpdateProfileForm;
import mx.neoris.storefront.util.XSSFilterUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller for home page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/my-account/settings")
public class B2BCustomerSettingsPageController extends AbstractSearchPageController
{
	
	// CMS Pages
	private static final String B2BCUSTOMER_SETTINGS_CMS_PAGE = "B2BCustomerSettingsListPage";
	
	
	private static final Logger LOG = Logger.getLogger(B2BCustomerSettingsPageController.class);
	
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "dateFormatter_yyyy-MM-dd")	
	private SimpleDateFormat dateFormatter;
	
	@Resource(name = "neorisNoticesFacade")
	private NeorisNoticesFacade neorisNoticesFacade;
	
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource
	private MediaService mediaService;
	
	@Resource(name="neorisB2BCustomerDefaultSettingsFacade")
	NeorisB2BCustomerDefaultSettingsFacade neorisB2BCustomerDefaultSettingsFacade;
	
	@ModelAttribute("noticeTypes")
	public List<NoticeType> getNoticeTypes()
	{
		return enumerationService.getEnumerationValues(NoticeType.class);
	}

	
	@RequestMapping(value = "/list",  method = { RequestMethod.GET })
	@RequireHardLogIn
	public String noticesList(	final Model model)throws CMSItemNotFoundException
	{
		B2BUnitModel b2BUnitModel = getRootUnit();
		
		B2BCustomerSettingsSearchForm b2bCustomerSettingsSearchForm = new B2BCustomerSettingsSearchForm();
		b2bCustomerSettingsSearchForm.setName(getRootUnit().getUid());
		
		model.addAttribute("b2bCustomerSettingsSearchForm",b2bCustomerSettingsSearchForm);
		
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		
		model.addAttribute("addresses", neorisFacade.getShippingAddress(b2BUnitModel));
		
		model.addAttribute("transportationModes", baseStoreModel.getTransportationModes());
		
		model.addAttribute("uoms", baseStoreModel.getUnits());
		
		model.addAttribute("systemList", enumerationService.getEnumerationValues("MeasurementSystemType"));
		
		model.addAttribute("locations", baseStoreModel.getPointOfServices());
		
		B2BCustomerModel b2bCustomerModel = neorisFacade.getCurrentCustomer();
		
		B2BCustomerSettingParameters parameters = new B2BCustomerSettingParameters();
		
		parameters.setB2bCustomerModel(b2bCustomerModel);
		parameters.setB2bUnitModel(b2BUnitModel);
		parameters.setBaseStoreModel(baseStoreModel);
		
		NeorisB2BCustomerDefaultSettingModel currentSetting = neorisB2BCustomerDefaultSettingsFacade.getSetting(parameters);
		
		model.addAttribute("currentSetting", currentSetting);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(B2BCUSTOMER_SETTINGS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(B2BCUSTOMER_SETTINGS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("b2bcustomer.settings.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/get",  method = { RequestMethod.GET })
	@RequireHardLogIn
	public String getSettings(B2BCustomerSettingsSearchForm b2bCustomerSettingsSearchForm,	final Model model)throws CMSItemNotFoundException
	{
		//List<NoticeData> noticeDatas = neorisNoticesFacade.getNoticesForCurrentStore(); 
		
		model.addAttribute("b2bCustomerSettingsSearchForm",b2bCustomerSettingsSearchForm);
		
		if(StringUtils.isNotBlank(b2bCustomerSettingsSearchForm.getName()))
		{
			String b2bUnitID = b2bCustomerSettingsSearchForm.getName();
			B2BUnitModel b2BUnitModel = neorisFacade.getB2BUnitWithUid(b2bUnitID);
			
			BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
			
			model.addAttribute("addresses", neorisFacade.getShippingAddress(b2BUnitModel));
			
			model.addAttribute("transportationModes", baseStoreModel.getTransportationModes());
			
			model.addAttribute("uoms", baseStoreModel.getUnits());
			
			model.addAttribute("systemList", enumerationService.getEnumerationValues("MeasurementSystemType"));
			
			model.addAttribute("locations", baseStoreModel.getPointOfServices());
			
			B2BCustomerModel b2bCustomerModel = neorisFacade.getCurrentCustomer();
			
			B2BCustomerSettingParameters parameters = new B2BCustomerSettingParameters();
			
			parameters.setB2bCustomerModel(b2bCustomerModel);
			parameters.setB2bUnitModel(b2BUnitModel);
			parameters.setBaseStoreModel(baseStoreModel);
			
			NeorisB2BCustomerDefaultSettingModel currentSetting = neorisB2BCustomerDefaultSettingsFacade.getSetting(parameters);
			
			model.addAttribute("currentSetting", currentSetting);
		}
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(B2BCUSTOMER_SETTINGS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(B2BCUSTOMER_SETTINGS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("b2bcustomer.settings.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/save",  method = { RequestMethod.GET })
	@RequireHardLogIn
	public String saveSettings(B2BCustomerSettingForm b2bCustomerSettingForm,	final Model model,final RedirectAttributes redirectModel)throws CMSItemNotFoundException
	{
		//List<NoticeData> noticeDatas = neorisNoticesFacade.getNoticesForCurrentStore(); 
		if(StringUtils.isNotBlank(b2bCustomerSettingForm.getB2BUnit()))
		{
			model.addAttribute("b2bCustomerSettingsSearchForm",b2bCustomerSettingForm);
			
			String b2bUnitID = b2bCustomerSettingForm.getB2BUnit();
			B2BUnitModel b2BUnitModel = neorisFacade.getB2BUnitWithUid(b2bUnitID);
			
			
			BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
			
			B2BCustomerSaveSettingParameters parameters = new B2BCustomerSaveSettingParameters();
			
			parameters.setB2bCustomerModel(neorisFacade.getCurrentCustomer());
			parameters.setB2bUnitModel(b2BUnitModel);
			parameters.setBaseStoreModel(baseStoreModel);
			
			parameters.setAddress(b2bCustomerSettingForm.getAddress());
			parameters.setLocation(b2bCustomerSettingForm.getLocation());
			parameters.setTransportationMode(b2bCustomerSettingForm.getTransportationMode());
			parameters.setUom(b2bCustomerSettingForm.getUom());
			
			NeorisB2BCustomerDefaultSettingModel currentSetting = neorisB2BCustomerDefaultSettingsFacade.saveSetting(parameters);
			
			if(currentSetting == null)
			{
				LOG.error("There was an error saving the setting.");
				GlobalMessages.addErrorMessage(model, "b2bcustomer.settings.error");
			}else
			{		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
						"b2bcustomer.settings.success", new Object[]
								{ currentSetting.getB2bunit().getUid() });
				
				return REDIRECT_PREFIX + "/my-account/settings/get?name="+currentSetting.getB2bunit().getUid();
			}
		}
			
		storeCmsPageInModel(model, getContentPageForLabelOrId(B2BCUSTOMER_SETTINGS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(B2BCUSTOMER_SETTINGS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("b2bcustomer.settings.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}		
}
