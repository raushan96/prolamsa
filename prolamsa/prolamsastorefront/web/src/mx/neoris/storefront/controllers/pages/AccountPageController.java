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
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.workflow.enums.WorkflowActionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisQuoteSearchParameters;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.orders.NeorisB2BOrderFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.Breadcrumb;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.AddressForm;
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
@RequestMapping("/my-account")
public class AccountPageController extends AbstractSearchPageController
{
	// Internal Redirects
	private static final String REDIRECT_MY_ACCOUNT = REDIRECT_PREFIX + "/my-account";
	private static final String REDIRECT_TO_ADDRESS_BOOK_PAGE = REDIRECT_PREFIX + "/my-account/address-book";
	private static final String REDIRECT_TO_PAYMENT_INFO_PAGE = REDIRECT_PREFIX + "/my-account/payment-details";
	private static final String REDIRECT_TO_PROFILE_PAGE = REDIRECT_PREFIX + "/my-account/profile";
	private static final String REDIRECT_TO_MYREPLENISHMENTS_PAGE = REDIRECT_PREFIX + "/my-account/my-replenishment";
	private static final String REDIRECT_TO_MYREPLENISHMENTS_DETAIL_PAGE = REDIRECT_PREFIX + "/my-account/my-replenishment/%s/";
	private static final String REDIRECT_TO_QUOTES_DETAILS = REDIRECT_PREFIX + "/my-account/my-quote/%s";	
	
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";
	private static final String ADDRESS_CODE_PATH_VARIABLE_PATTERN = "{addressCode:.*}";
	private static final String JOB_CODE_PATH_VARIABLE_PATTERN = "{jobCode:.*}";
	private static final String WORKFLOW_ACTION_CODE_PATH_VARIABLE_PATTERN = "{workflowActionCode:.*}";

	// CMS Pages
	private static final String ACCOUNT_CMS_PAGE = "account";
	private static final String PROFILE_CMS_PAGE = "profile";
	private static final String ADDRESS_BOOK_CMS_PAGE = "address-book";
	private static final String ADD_EDIT_ADDRESS_CMS_PAGE = "add-edit-address";
	private static final String PAYMENT_DETAILS_CMS_PAGE = "payment-details";
	private static final String ORDER_HISTORY_CMS_PAGE = "orders";
	private static final String ORDER_DETAIL_CMS_PAGE = "order";
	private static final String MY_QUOTES_CMS_PAGE = "my-quotes";
	private static final String MY_REPLENISHMENT_ORDERS_CMS_PAGE = "my-replenishment-orders";
	private static final String ORDER_APPROVAL_DASHBOARD_CMS_PAGE = "order-approval-dashboard";
	
	private static final Logger LOG = Logger.getLogger(AccountPageController.class);
	
	//NEORIS_CHANGE #96
	@Resource(name="neorisB2BOrderFacade")
	private NeorisB2BOrderFacade neorisB2BOrderFacade;
	
	//	NEORIS_CHANGE #96
	@Resource(name="neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	// NEORIS_CHANGE #96
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	// NEORIS_CHANGE #96
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	// NEORIS_CHANGE #96
	@Resource(name = "neorisCartPriceCalculator")
	private NeorisCartPriceCalculator neorisCartPriceCalculator;
	
	@Resource(name = "b2bOrderFacade")
	private B2BOrderFacade orderFacade;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "userFacade")
	protected UserFacade userFacade;

	@Resource(name = "b2bCustomerFacade")
	protected CustomerFacade customerFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "neorisAddressConverter")
	private Converter<AddressModel, AddressData> addressConverter;
	
	@Resource(name = "neorisB2BUnitConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter;
	
	@Resource(name = "dateFormatter_yyyy-MM-dd")	
	private SimpleDateFormat dateFormatter;
	
	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	
	@Resource (name="neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrderCreator;
	
	@Resource(name = "neorisService")
	private NeorisService neorisService;
	
	@RequestMapping(method = RequestMethod.GET)
	@RequireHardLogIn
	public String account(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ACCOUNT_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountHomePage;
	}
	
	@RequestMapping(value = "/orders", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showFormOrders(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sort,final Model model)
			throws CMSItemNotFoundException
	{
		//get all status
		OrderStatus[] orderStatuses ={ //OrderStatus.OPEN, OrderStatus.CREATED,OrderStatus.ASSIGNED_TO_ADMIN,
			OrderStatus.COMPLETED, OrderStatus.CANCELLED, OrderStatus.IN_PROCESS, OrderStatus.PENDING_APPROVAL,
			OrderStatus.APPROVED, OrderStatus.ON_VALIDATION };
		model.addAttribute("orderStatuses", orderStatuses);
					
		// add sorts to searchPageData
		String[] sortProps = new String[] {"order.sortby.code","order.sortby.status","order.sortby.date","order.sortby.deliveryAddress"};
		List<SortData> sorts = getSortListFor(sortProps, "order.sortby.code-asc" , "{0}-(asc)", "{0}-(desc)");
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<OrderData> searchPageData = new SearchPageData<OrderData>();
		searchPageData.setPagination(new PaginationData());
		
		/////////INICIA//////////////////////////
		B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
		String customerTrick = currentB2BUnit.getUid();
		
		// save search form on model
		OrderHistorySearchForm searchForm = new OrderHistorySearchForm();
		searchForm.setCustomer(customerTrick);
		model.addAttribute("orderHistorySearchForm", searchForm);	
			
		final List<OrderStatus> orderStatusesTrick = neorisFacade.getValidStatusForOrdersOnlyEdit();
				
		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 20, null, showMode);
				
		OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters(); 
		searchParameters.setPageableData(pageableData);		
		searchParameters.setCustomer(customerTrick);
				
		searchPageData = neorisFacade.getPagedOrderHistory(searchParameters, neorisFacade.getCurrentCustomer(), orderStatusesTrick);
		////////FINALIZA///////////////////////////
					
		populateModel(model, searchPageData, showMode);

		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderHistory"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountOrderHistoryPage;
	}
	
	@RequestMapping(value = "/orders/list",  method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String searchOrders(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,			
			OrderHistorySearchForm searchForm,	final Model model,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		// Get all order statuses exist in PROLAMSA
		final List<OrderStatus> orderStatuses = neorisFacade.getValidStatusForOrdersOnlyEdit();
		model.addAttribute("orderStatuses", orderStatuses);
		
		//get all current user B2BUnits
		List<B2BUnitModel> listB2BUnits = neorisFacade.getB2BUnitModelsFromCustomer(neorisFacade.getCurrentCustomer());							
		
		List<B2BUnitData> listB2BData = new ArrayList<B2BUnitData>();
		for(B2BUnitModel unitModel :  listB2BUnits)
		{
			if(unitModel.getBaseStores().contains(getCurrentBaseStore()))
			{
			
				B2BUnitData unitData = new B2BUnitData();
				b2bUnitConverter.convert(unitModel,unitData);	
				listB2BData.add(unitData);
			}	
		}		
		model.addAttribute("listB2BUnits", listB2BData);	
		
		//get all delivery addresses for B2BUnits selected
		if(!"all".equalsIgnoreCase(searchForm.getCustomer()))
		{
			// Combo address detail
			B2BUnitModel preDetailModel = new B2BUnitModel();
			preDetailModel = b2bUnitService.getUnitForUid(searchForm.getCustomer());

			List<AddressData> deliveryAddresses = neorisFacade.getShippingAddress(preDetailModel);
			model.addAttribute("deliveryAddresses", deliveryAddresses);
		}
		
		// save search form on model				
		model.addAttribute("orderHistorySearchForm", searchForm);								
								
		// initialize clean pagination
		SearchPageData<OrderData> searchPageData = new SearchPageData<OrderData>();
		searchPageData.setPagination(new PaginationData());
		
		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 20, null, showMode);
		
		// create and fill the search parameters
		String customerSelected = "all";
		if (searchForm.getCustomer() != null && !searchForm.getCustomer().isEmpty())
		{
			customerSelected = searchForm.getCustomer();
		}
		
		OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters(); 
		searchParameters.setPageableData(pageableData);		
		searchParameters.setCustomer(customerSelected);
		searchParameters.setDeliveryAddress(searchForm.getDeliveryAddress());		
		searchParameters.setOrderNumber(searchForm.getOrderNumber());
		searchParameters.setPoNumber(searchForm.getPoNumber());
		searchParameters.setStatus(searchForm.getStatus());
		
		// get and set sort information
		String sortCode = searchForm.getSort(); 
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
			{
				/*if ("Order Number".equals(sortInfo[0]))
				{
					searchParameters.setSortBy("code");
				}
				else if("Order Status".equals(sortInfo[0]))
				{
					searchParameters.setSortBy("status");
				}
				else if("Date Placed".equals(sortInfo[0]))
				{
					searchParameters.setSortBy("date");
				}
				else if("Ship To".equals(sortInfo[0]))
				{
					searchParameters.setSortBy("deliveryAddress");
				}*/
				searchParameters.setSortBy(sortInfo[0]);
				searchParameters.setSortOrder(sortInfo[1]);
			}
			
		// add sorts to searchPageData
		//String[] sortProps = new String[] { "Order Number","Order Status","Date Placed","Ship To" };
		//List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "Order Number-asc" : sortCode, "{0}-(asc)", "{0}-(desc)");
		String[] sortProps = new String[] {"order.sortby.code","order.sortby.status","order.sortby.date","order.sortby.deliveryAddress"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "order.sortby.code-asc" : "order.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);	
		
		
		String baseStoreFormatDate = "USA";
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
		{
			baseStoreFormatDate = "MX";
		}
		
		// process dates
		String param = searchForm.getInitialDate();
		if (param != null && param.length() > 0){
			try
			{	
				if(baseStoreFormatDate.equalsIgnoreCase("USA"))
				{
					String month = param.substring(0, 3);
					String day = param.substring(4, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
					
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
									
					String newDate = year+"-"+monthSpanish+"-"+day;
					searchParameters.setInitialDate(dateFormatter.parse(newDate));
				}else
				{
					//01-Mar-2016
					String day = param.substring(0, 2);
					String month = param.substring(3, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
					
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
					
					
					String newDate = year+"-"+monthSpanish+"-"+day;
					searchParameters.setInitialDate(dateFormatter.parse(newDate));
				
				}
				
				//searchParameters.setInitialDate(dateFormatter.parse(param));
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
		}
		param = searchForm.getFinalDate();
		if (param != null && param.length() > 0){
			try
			{
				if(baseStoreFormatDate.equalsIgnoreCase("USA"))
				{
					String month = param.substring(0, 3);
					String day = param.substring(4, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
					
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
					String newDate = year+"-"+monthSpanish+"-"+day;
					
					/*searchParameters.setFinalDate(dateFormatter.parse(newDate));*/
					
					Date finalDate = dateFormatter.parse(newDate);
					// after Date created, set it to the latest time of day to include whole day
					Calendar cal = Calendar.getInstance();
					cal.setTime(finalDate);
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					
					searchParameters.setFinalDate(cal.getTime());
					
				}else
				{
					//01-Mar-2016
					String day = param.substring(0, 2);
					String month = param.substring(3, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
				
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
				
					String newDate = year+"-"+monthSpanish+"-"+day;
					//searchParameters.setFinalDate(dateFormatter.parse(newDate));
					
					Date finalDate = dateFormatter.parse(newDate);
					// after Date created, set it to the latest time of day to include whole day
					Calendar cal = Calendar.getInstance();
					cal.setTime(finalDate);
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					
					searchParameters.setFinalDate(cal.getTime());
				}
			}
				
			/*	Date finalDate = dateFormatter.parse(param);
				// after Date created, set it to the latest time of day to include whole day
				Calendar cal = Calendar.getInstance();
				cal.setTime(finalDate);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				
				searchParameters.setFinalDate(cal.getTime());
			}*/
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
		}
		
		try
		{			
			//searchPageData = neorisFacade.getPagedOrderHistory(searchParameters, neorisFacade.getCurrentCustomer(), validStatus);
		    searchPageData = neorisFacade.getPagedOrderHistory(searchParameters, neorisFacade.getCurrentCustomer(), orderStatuses);
		}		
		catch (Exception ex)
		{
			// if other type of error
			LOG.error("Unable to retrieve Order History", ex);

			GlobalMessages.addErrorMessage(model, "orderHistory.list.error");
		}
		
		populateModel(model, searchPageData, showMode);

		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderHistory"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountOrderHistoryPage;
	}
	
	//CILS 26Nov2014
	@RequestMapping(value = "/orderMobile" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderMobile(@RequestParam("orderCode") final String orderCode,@RequestParam("productCode") final String productCode, final Model model) throws CMSItemNotFoundException
	{
		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			model.addAttribute("orderData", orderDetails);
			model.addAttribute("productCode", productCode);
			model.addAttribute(new ReorderForm());
			
			final B2BUnitModel unit = neorisService.getB2BUnitForUID(orderDetails.getUnit().getUid());
			
   		// NEORIS_CHANGE #96
   		//neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderDetails.getEntries());
		neorisProductFacade.injectCustomerNameAndDescriptionDataOn(orderDetails.getEntries(), unit);
   		//	Change to remove gruopind data and keep the order en in the entries
   		//neorisCartPriceHelper.sortEntriesByReadyToShipDate(orderDetails);

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/orders", getMessageSource().getMessage("text.account.orderHistory", null,
					getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.order.orderBreadcrumb", new Object[]
			{ orderDetails.getCode() }, "Order {0}", getI18nService().getCurrentLocale()), null));
			model.addAttribute("breadcrumbs", breadcrumbs);
			//NEORIS_CHANGE #96
			model.addAttribute("defaultUoM", baseStoreService.getCurrentBaseStore().getUnit());

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.AccountOrderItemPageMobile;
	}

	
	
	
	@RequestMapping(value = "/order/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String order(@PathVariable("orderCode") final String orderCode, final Model model) throws CMSItemNotFoundException
	{
		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			addOrderWeightPatterToModel(model,orderDetails);
						
			if (orderDetails.getEntries() != null && !orderDetails.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : orderDetails.getEntries())
				{
					final Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(orderDetails.getUnitWhenPlaced(), entry.getQuantity(), entry.getProduct(), entry.getIsQuantityInPieces());
					entry.setConvertedQuantity(convertedQuantity);
				}
			}
			
			model.addAttribute("orderData", orderDetails);
			
			model.addAttribute(new ReorderForm());
			
			// injecting quantity pattern based in the order unit
			addOrderQuantityPatternToModel(model, orderDetails);
			
			final B2BUnitModel unit = neorisService.getB2BUnitForUID(orderDetails.getUnit().getUid());
			
			// NEORIS_CHANGE #96
			//neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderDetails.getEntries());
			neorisProductFacade.injectCustomerNameAndDescriptionDataOn(orderDetails.getEntries(), unit);
			neorisCartPriceHelper.sortEntriesBySapOrderEntryNumber(orderDetails);

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/orders", getMessageSource().getMessage("text.account.orderHistory", null,
					getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.order.orderBreadcrumb", new Object[]
			{ orderDetails.getCode() }, "Order {0}", getI18nService().getCurrentLocale()), null));
			model.addAttribute("breadcrumbs", breadcrumbs);
			//NEORIS_CHANGE #96
			model.addAttribute("defaultUoM", baseStoreService.getCurrentBaseStore().getUnit());
			
			// show link on order number to download sales order document
			model.addAttribute("showDownloadSO", true);
			model.addAttribute("showSAPEntriesPosition", true);
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.AccountOrderPage;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	@RequireHardLogIn
	public String profile(final Model model) throws CMSItemNotFoundException
	{
		final List<TitleData> titles = userFacade.getTitles();

		final CustomerData customerData = customerFacade.getCurrentCustomer();

		if (customerData.getTitleCode() != null)
		{
			model.addAttribute("title", CollectionUtils.find(titles, new Predicate()
			{
				@Override
				public boolean evaluate(final Object object)
				{
					if (object instanceof TitleData)
					{
						return customerData.getTitleCode().equals(((TitleData) object).getCode());
					}
					return false;
				}
			}));
		}

		model.addAttribute("customerData", customerData);

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountProfilePage;
	}

	@RequestMapping(value = "/update-email", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editEmail(final Model model) throws CMSItemNotFoundException
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		final UpdateEmailForm updateEmailForm = new UpdateEmailForm();

		updateEmailForm.setEmail(customerData.getDisplayUid());

		model.addAttribute("updateEmailForm", updateEmailForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountProfileEmailEditPage;
	}

	@RequestMapping(value = "/update-email", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updateEmail(@Valid final UpdateEmailForm updateEmailForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		String returnAction = REDIRECT_TO_PROFILE_PAGE;

		if (!updateEmailForm.getEmail().equals(updateEmailForm.getChkEmail()))
		{
			bindingResult.rejectValue("chkEmail", "validation.checkEmail.equals", new Object[] {}, "validation.checkEmail.equals");
		}

		if (bindingResult.hasErrors())
		{
			returnAction = errorUpdatingEmail(model);
		}
		else
		{
			try
			{
				customerFacade.changeUid(updateEmailForm.getEmail(), updateEmailForm.getPassword());
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
						"text.account.profile.confirmationUpdated");

				// Replace the spring security authentication with the new UID
				final String newUid = customerFacade.getCurrentCustomer().getUid().toLowerCase();
				final Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
				final UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(newUid, null,
						oldAuthentication.getAuthorities());
				newAuthentication.setDetails(oldAuthentication.getDetails());
				SecurityContextHolder.getContext().setAuthentication(newAuthentication);
			}
			catch (final DuplicateUidException e)
			{
				bindingResult.rejectValue("email", "profile.email.unique");
				returnAction = errorUpdatingEmail(model);
			}
			catch (final PasswordMismatchException passwordMismatchException)
			{
				bindingResult.rejectValue("email", "profile.currentPassword.invalid");
				returnAction = errorUpdatingEmail(model);
			}
		}

		return returnAction;
	}

	protected String errorUpdatingEmail(final Model model) throws CMSItemNotFoundException
	{
		final String returnAction;
		GlobalMessages.addErrorMessage(model, "form.global.error");
		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile"));
		returnAction = ControllerConstants.Views.Pages.Account.AccountProfileEmailEditPage;
		return returnAction;
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editProfile(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("titleData", userFacade.getTitles());

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		final UpdateProfileForm updateProfileForm = new UpdateProfileForm();

		updateProfileForm.setTitleCode(customerData.getTitleCode());
		updateProfileForm.setFirstName(customerData.getFirstName());
		updateProfileForm.setLastName(customerData.getLastName());

		model.addAttribute("updateProfileForm", updateProfileForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountProfileEditPage;
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updateProfile(@Valid final UpdateProfileForm updateProfileForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		String returnAction = ControllerConstants.Views.Pages.Account.AccountProfileEditPage;
		final CustomerData currentCustomerData = customerFacade.getCurrentCustomer();
		final CustomerData customerData = new CustomerData();
		customerData.setTitleCode(updateProfileForm.getTitleCode());
		customerData.setFirstName(updateProfileForm.getFirstName());
		customerData.setLastName(updateProfileForm.getLastName());
		customerData.setUid(currentCustomerData.getUid());
		customerData.setDisplayUid(currentCustomerData.getDisplayUid());

		model.addAttribute("titleData", userFacade.getTitles());

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
		}
		else
		{
			try
			{
				customerFacade.updateProfile(customerData);
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
						"text.account.profile.confirmationUpdated");
				returnAction = REDIRECT_TO_PROFILE_PAGE;
			}
			catch (final DuplicateUidException e)
			{
				bindingResult.rejectValue("email", "registration.error.account.exists.title");
				GlobalMessages.addErrorMessage(model, "form.global.error");
			}
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile"));
		return returnAction;
	}

	@RequestMapping(value = "/reset-password", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object passwordReset(final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "");
		node.put("status", AJAX_STATUS_OK);

		CustomerData customerData = getCustomerFacade().getCurrentCustomer();

		try
		{
			getCustomerFacade().forgottenPassword(customerData.getDisplayUid());
			node.put("message", getMessageWithDefaultContext("account.confirmation.forgotten.password.link.sent"));
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.warn("Email: " + customerData.getDisplayUid() + " does not exist in the database.");
			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", getMessageWithDefaultContext("account.error.account.not.found"));
		}

		return node;
	}

	@RequestMapping(value = "/update-password", method = RequestMethod.GET)
	@RequireHardLogIn
	public String updatePassword(final Model model) throws CMSItemNotFoundException
	{
		final UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();

		model.addAttribute("updatePasswordForm", updatePasswordForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.updatePasswordForm"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountChangePasswordPage;
	}

	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updatePassword(@Valid final UpdatePasswordForm updatePasswordForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{

		if (!bindingResult.hasErrors())
		{
			if (updatePasswordForm.getNewPassword().equals(updatePasswordForm.getCheckNewPassword()))
			{
				try
				{
					customerFacade.changePassword(updatePasswordForm.getCurrentPassword(), updatePasswordForm.getNewPassword());
				}
				catch (final PasswordMismatchException localException)
				{
					bindingResult.rejectValue("currentPassword", "profile.currentPassword.invalid", new Object[] {},
							"profile.currentPassword.invalid");
				}

			}
			else
			{
				bindingResult.rejectValue("checkNewPassword", "validation.checkPwd.equals", new Object[] {},
						"validation.checkPwd.equals");
			}
		}

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));

			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.updatePasswordForm"));
			return ControllerConstants.Views.Pages.Account.AccountChangePasswordPage;
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
					"text.account.confirmation.password.updated");
			return REDIRECT_TO_PROFILE_PAGE;
		}
	}
	
	@ModelAttribute("formattedB2BUnitsSortBy")
	public List<B2BUnitData> formattedB2BUnitsSortBy()
	{
		return neorisFacade.getFormattedB2BUnitsSortBy();
	}

	@RequestMapping(value = "/address-book", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String getAddressBook(final AddressForm addressForm, final Model model) throws CMSItemNotFoundException
	{
		//LR
		List<B2BUnitModel> units = new ArrayList<B2BUnitModel>();
		
		String selectedUnit = addressForm.getCustomer();

		// get and set sort information
		String sortCode = addressForm.getSortBy();
		String defaultSortCode = "code-asc";
		
		if (StringUtils.isEmpty(sortCode))
			sortCode = defaultSortCode;

		String[] sortInfo = getSortByAndOrderFrom(sortCode);

		// add sorts to searchPageData
		String[] sortProps = new String[] {"code,Delivery_Address_Id", "name,Delivery_Address_Name"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode, "{0}-(asc)", "{0}-(desc)");
		model.addAttribute("sorts", sorts);

		List<AddressData> addresses = neorisFacade.getCurrentCustomerB2BUnitAddresses(selectedUnit, sortInfo[0], sortInfo[1]);
		model.addAttribute("sortedAddresses", addresses);
		List<B2BUnitAddresses> unitList = new ArrayList<B2BUnitAddresses>();

		String ownerUid = "";
		B2BUnitAddresses unitAddresses = null;
		int totalShippingAddresses = 0;
		for (AddressData address : addresses)
		{
			if (!StringUtils.equalsIgnoreCase(ownerUid, address.getOwnerUid()))
			{
				unitAddresses = new B2BUnitAddresses();
				unitAddresses.setShippingAddressList(new ArrayList<AddressData>());
				unitAddresses.setBillingAddressList(Collections.singletonList(new AddressData()));
				ownerUid = address.getOwnerUid();
				units.add(b2bUnitService.getUnitForUid(address.getOwnerUid()));
			}

			if (address.isBillingAddress())
			{
				unitAddresses.billingAddressList = Collections.singletonList(address);
			}
			if (address.isShippingAddress())
			{
				unitAddresses.shippingAddressList.add(address);
				totalShippingAddresses++;
			}
			
			if (!unitList.contains(unitAddresses))
			{
				unitList.add(unitAddresses);
			}
		}
		
		ShowMode showMode = ShowMode.Page;

		SearchPageData searchPageData = new SearchPageData();
		PaginationData paginationData = new PaginationData();
		paginationData.setCurrentPage(0);
		paginationData.setNumberOfPages(0);
		paginationData.setTotalNumberOfResults(totalShippingAddresses);
		paginationData.setPageSize(Integer.MAX_VALUE);
		paginationData.setSort("");
		searchPageData.setPagination(paginationData);

		populateModel(model, searchPageData,  showMode);

		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "false");

		model.addAttribute("totalShippingAddresses", totalShippingAddresses);
		model.addAttribute("unitList", unitList);
		model.addAttribute("modelDetail", units);
		//model.addAttribute("addressData", userFacade.getAddressBook());
		model.addAttribute("addressForm", addressForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.addressBook"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountAddressBookPage;
	}

	
	@RequestMapping(value = "/add-address", method = RequestMethod.GET)
	@RequireHardLogIn
	public String addAddress(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		model.addAttribute("titleData", userFacade.getTitles());
		model.addAttribute("addressForm", new AddressForm());
		model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));

		storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/address-book", getMessageSource().getMessage("text.account.addressBook", null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress", null,
				getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountEditAddressPage;
	}

	@RequestMapping(value = "/add-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String addAddress(@Valid final AddressForm addressForm, final BindingResult bindingResult, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("titleData", userFacade.getTitles());
			return ControllerConstants.Views.Pages.Account.AccountEditAddressPage;
		}

		final AddressData newAddress = new AddressData();
		newAddress.setTitleCode(addressForm.getTitleCode());
		newAddress.setFirstName(addressForm.getFirstName());
		newAddress.setLastName(addressForm.getLastName());
		newAddress.setLine1(addressForm.getLine1());
		newAddress.setLine2(addressForm.getLine2());
		newAddress.setTown(addressForm.getTownCity());
		newAddress.setPostalCode(addressForm.getPostcode());
		newAddress.setBillingAddress(false);
		newAddress.setShippingAddress(true);
		newAddress.setVisibleInAddressBook(true);

		final CountryData countryData = new CountryData();

		countryData.setIsocode(addressForm.getCountryIso());
		newAddress.setCountry(countryData);

		if (userFacade.isAddressBookEmpty())
		{
			newAddress.setDefaultAddress(true);
		}
		else
		{
			newAddress.setDefaultAddress(addressForm.getDefaultAddress().booleanValue());
		}
		userFacade.addAddress(newAddress);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.added");

		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String editAddress(@PathVariable("addressCode") final String addressCode, final Model model)
			throws CMSItemNotFoundException
	{
		final AddressForm addressForm = new AddressForm();
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		model.addAttribute("titleData", userFacade.getTitles());
		model.addAttribute("addressForm", addressForm);
		model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));

		for (final AddressData addressData : userFacade.getAddressBook())
		{
			if (addressData.getId() != null && addressData.getId().equals(addressCode))
			{
				model.addAttribute("addressData", addressData);
				addressForm.setAddressId(addressData.getId());
				addressForm.setTitleCode(addressData.getTitleCode());
				addressForm.setFirstName(addressData.getFirstName());
				addressForm.setLastName(addressData.getLastName());
				addressForm.setLine1(addressData.getLine1());
				addressForm.setLine2(addressData.getLine2());
				addressForm.setTownCity(addressData.getTown());
				addressForm.setPostcode(addressData.getPostalCode());
				addressForm.setCountryIso(addressData.getCountry().getIsocode());
				if (userFacade.getDefaultAddress() != null && userFacade.getDefaultAddress().getId() != null
						&& userFacade.getDefaultAddress().getId().equals(addressData.getId()))
				{
					addressForm.setDefaultAddress(Boolean.TRUE);
				}
				break;
			}
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/address-book", getMessageSource().getMessage("text.account.addressBook", null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress", null,
				getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountEditAddressPage;
	}

	@RequestMapping(value = "/edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST)
	@RequireHardLogIn
	public String editAddress(@Valid final AddressForm addressForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		model.addAttribute("metaRobots", "no-index,no-follow");
		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("titleData", userFacade.getTitles());
			return ControllerConstants.Views.Pages.Account.AccountEditAddressPage;
		}

		final AddressData newAddress = new AddressData();
		newAddress.setId(addressForm.getAddressId());
		newAddress.setTitleCode(addressForm.getTitleCode());
		newAddress.setFirstName(addressForm.getFirstName());
		newAddress.setLastName(addressForm.getLastName());
		newAddress.setLine1(addressForm.getLine1());
		newAddress.setLine2(addressForm.getLine2());
		newAddress.setTown(addressForm.getTownCity());
		newAddress.setPostalCode(addressForm.getPostcode());
		newAddress.setBillingAddress(false);
		newAddress.setShippingAddress(true);
		newAddress.setVisibleInAddressBook(true);

		final CountryData countryData = new CountryData();

		countryData.setIsocode(addressForm.getCountryIso());
		newAddress.setCountry(countryData);

		if (Boolean.TRUE.equals(addressForm.getDefaultAddress()) || userFacade.getAddressBook().size() <= 1)
		{
			newAddress.setDefaultAddress(true);
		}
		else
		{
			newAddress.setDefaultAddress(addressForm.getDefaultAddress() != null && addressForm.getDefaultAddress().booleanValue());
		}
		userFacade.editAddress(newAddress);

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.addressBook.confirmationUpdated");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/remove-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String removeAddress(@PathVariable("addressCode") final String addressCode, final RedirectAttributes redirectModel)
	{
		final AddressData addressData = new AddressData();
		addressData.setId(addressCode);
		userFacade.removeAddress(addressData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.removed");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/set-default-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String setDefaultAddress(@PathVariable("addressCode") final String addressCode, final RedirectAttributes redirectModel)
	{
		final AddressData addressData = new AddressData();
		addressData.setDefaultAddress(true);
		addressData.setVisibleInAddressBook(true);
		addressData.setId(addressCode);
		userFacade.setDefaultAddress(addressData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.default.address.changed");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/payment-details", method = RequestMethod.GET)
	@RequireHardLogIn
	public String paymentDetails(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("customerData", customerFacade.getCurrentCustomer());
		model.addAttribute("paymentInfoData", userFacade.getCCPaymentInfos(true));
		storeCmsPageInModel(model, getContentPageForLabelOrId(PAYMENT_DETAILS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.paymentDetails"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountPaymentInfoPage;
	}

	@RequestMapping(value = "/set-default-payment-details", method = RequestMethod.POST)
	@RequireHardLogIn
	public String setDefaultPaymentDetails(@RequestParam final String paymentInfoId)
	{
		CCPaymentInfoData paymentInfoData = null;
		if (StringUtils.isNotBlank(paymentInfoId))
		{
			paymentInfoData = userFacade.getCCPaymentInfoForCode(paymentInfoId);
		}
		userFacade.setDefaultPaymentInfo(paymentInfoData);
		return REDIRECT_TO_PAYMENT_INFO_PAGE;
	}

	@RequestMapping(value = "/remove-payment-method", method = RequestMethod.POST)
	@RequireHardLogIn
	public String removePaymentMethod(final Model model, @RequestParam(value = "paymentInfoId") final String paymentMethodId,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		userFacade.unlinkCCPaymentInfo(paymentMethodId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.profile.paymentCart.removed");
		return REDIRECT_TO_PAYMENT_INFO_PAGE;
	}

	@RequestMapping(value = "/orderApprovalDetails/" + WORKFLOW_ACTION_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderApprovalDetails(@PathVariable("workflowActionCode") final String workflowActionCode, final Model model)
			throws CMSItemNotFoundException
	{
		try
		{
			final B2BOrderApprovalData orderApprovalDetails = orderFacade.getOrderApprovalDetailsForCode(workflowActionCode);
			
			final B2BUnitModel unit = neorisService.getB2BUnitForUID(orderApprovalDetails.getB2bOrderData().getUnit().getUid());
			
			//neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderApprovalDetails.getB2bOrderData().getEntries());
			neorisProductFacade.injectCustomerNameAndDescriptionDataOn(orderApprovalDetails.getB2bOrderData().getEntries(), unit);
			neorisCartPriceHelper.sortEntriesBySapOrderEntryNumber(orderApprovalDetails.getB2bOrderData());
			model.addAttribute("orderApprovalData", orderApprovalDetails);
			addOrderWeightPatterToModel(model, orderApprovalDetails.getB2bOrderData());
			if (!model.containsAttribute("orderApprovalDecisionForm"))
			{
				model.addAttribute("orderApprovalDecisionForm", new OrderApprovalDecisionForm());
			}

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/approval-dashboard", getMessageSource().getMessage(
					"text.account.orderApprovalDashboard", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.order.orderBreadcrumb", new Object[]
			{ orderApprovalDetails.getB2bOrderData().getCode() }, "Order {0}", getI18nService().getCurrentLocale()), null));

			model.addAttribute("breadcrumbs", breadcrumbs);
			model.addAttribute("showSAPEntriesPosition", true);

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountOrderApprovalDetailsPage;
	}

	@RequestMapping(value = "/order/approvalDecision", method = RequestMethod.POST)
	@RequireHardLogIn
	public String orderApprovalDecision(
			@ModelAttribute("orderApprovalDecisionForm") final OrderApprovalDecisionForm orderApprovalDecisionForm, final Model model)
			throws CMSItemNotFoundException
	{
		try
		{
			if ("REJECT".contains(orderApprovalDecisionForm.getApproverSelectedDecision()))
				{
					neorisSapOrderCreator.rejectOrAcceptSapOrderForCode(orderApprovalDecisionForm.getOrderCode(), orderApprovalDecisionForm.getComments(), "R");
				}
			else
				{
					neorisSapOrderCreator.rejectOrAcceptSapOrderForCode(orderApprovalDecisionForm.getOrderCode(), orderApprovalDecisionForm.getComments(), "A");
				}
		}catch (Exception e) {
			LOG.warn("Error to send status Hybris - SAP", e);
		}
		
		try
		{	
			if ("REJECT".contains(orderApprovalDecisionForm.getApproverSelectedDecision())
					&& StringUtils.isEmpty(orderApprovalDecisionForm.getComments()))
			{
				GlobalMessages.addErrorMessage(model, "text.account.orderApproval.addApproverComments");
				model.addAttribute("orderApprovalDecisionForm", orderApprovalDecisionForm);
				return orderApprovalDetails(orderApprovalDecisionForm.getWorkFlowActionCode(), model);
			}
			
			final B2BOrderApprovalData b2bOrderApprovalData = new B2BOrderApprovalData();
			b2bOrderApprovalData.setSelectedDecision(orderApprovalDecisionForm.getApproverSelectedDecision());
			b2bOrderApprovalData.setApprovalComments(orderApprovalDecisionForm.getComments());
			b2bOrderApprovalData.setWorkflowActionModelCode(orderApprovalDecisionForm.getWorkFlowActionCode());

			orderFacade.setOrderApprovalDecision(b2bOrderApprovalData);

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}

		return REDIRECT_MY_ACCOUNT + "/orderApprovalDetails/" + orderApprovalDecisionForm.getWorkFlowActionCode();
	}

	@RequestMapping(value = "/my-quotes", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showFormQuotes(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sort, final Model model)
			throws CMSItemNotFoundException
	{	
		//get all status
		final List<OrderStatus> quoteStatuses = neorisFacade.getValidStatusForQuotesOnly();
		
		// add quote statuses on model
		model.addAttribute("quoteStatuses", quoteStatuses);
		
		// add search form on model
		NeorisQuoteSearchForm searchForm = new NeorisQuoteSearchForm();
		model.addAttribute("quoteHistorySearchForm", searchForm);
		
		// add sorts to searchPageData
		String[] sortProps = new String[] { "Quote Number", "Status",
				"Date Placed"};
		List<SortData> sorts = getSortListFor(sortProps, "Quote Number-asc",
				"{0}-(asc)", "{0}-(desc)");
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<OrderData> searchPageData = new SearchPageData<OrderData>();
		searchPageData.setPagination(new PaginationData());

		populateModel(model, searchPageData, showMode);

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/my-quotes", getMessageSource().getMessage(
				"text.account.manageQuotes.breadcrumb", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		return ControllerConstants.Views.Pages.Account.AccountMyQuotesPage;
	}
	
	@RequestMapping(value = "/my-quotes/list",  method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String searchQuotes(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,			
			NeorisQuoteSearchForm searchForm,	final Model model,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		// Get all order statuses exist in PROLAMSA
		final List<OrderStatus> quoteStatuses = neorisFacade.getValidStatusForQuotesOnly();
		model.addAttribute("quoteStatuses", quoteStatuses);
		
		//get all current user B2BUnits
		List<B2BUnitModel> listB2BUnits = neorisFacade.getB2BUnitModelsFromCustomer(neorisFacade.getCurrentCustomer());							
		
		List<B2BUnitData> listB2BData = new ArrayList<B2BUnitData>();
		for(B2BUnitModel unitModel :  listB2BUnits)
		{
			if(unitModel.getBaseStores().contains(getCurrentBaseStore()))
			{
			
				B2BUnitData unitData = new B2BUnitData();
				b2bUnitConverter.convert(unitModel,unitData);	
				listB2BData.add(unitData);
			}	
		}
		
		// add list b2bunits on model
		model.addAttribute("listB2BUnits", listB2BData);	
		
		// get all delivery addresses for B2BUnits selected
		if(!"all".equalsIgnoreCase(searchForm.getCustomer()))
		{
		
			List<AddressData> deliveryAddresses = getDeliveryAdrressesForSelect(listB2BUnits);
			model.addAttribute("deliveryAddresses", deliveryAddresses);	
		}
		
		// add search form on model				
		model.addAttribute("quoteHistorySearchForm", searchForm);								
								
		// initialize clean pagination
		SearchPageData<OrderData> searchPageData = new SearchPageData<OrderData>();
		searchPageData.setPagination(new PaginationData());
		
		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 20, null, showMode);
		
		// create and fill the search parameters
		// re-usage order history search parameters to send search parameters
		String customerSelected = "all";
		if (searchForm.getCustomer() != null && !searchForm.getCustomer().isEmpty())
		{
			customerSelected = searchForm.getCustomer();
		}
		
		OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters();		
		searchParameters.setPageableData(pageableData);		
		searchParameters.setCustomer(customerSelected);
		searchParameters.setDeliveryAddress(searchForm.getDeliveryAddress());		
		searchParameters.setOrderNumber(searchForm.getQuoteNumber());
		searchParameters.setPoNumber(searchForm.getPoNumber());
		searchParameters.setStatus(searchForm.getStatus());
		
		// get and set sort information
		String sortCode = searchForm.getSort(); 
		if (sortCode == null || sortCode.isEmpty())
		{
			sortCode = "Status-desc";
		}
		
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
		{
			if ("Quote Number".equals(sortInfo[0]))
			{
				searchParameters.setSortBy("code");
			}
			else if("Status".equals(sortInfo[0]))
			{
				searchParameters.setSortBy("status");
			}
			else if("Date Placed".equals(sortInfo[0]))
			{
				searchParameters.setSortBy("date");
			}

			searchParameters.setSortOrder(sortInfo[1]);
		}
					
		// add sorts to searchPageData
		String[] sortProps = new String[] { "Quote Number", "Status", "Date Placed"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "Quote Number-asc" : sortCode,
				"{0}-(asc)", "{0}-(desc)");
		model.addAttribute("sorts", sorts);
		
		String baseStoreFormatDate = "USA";
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
		{
			baseStoreFormatDate = "MX";
		}
		
		// process dates
		String param = searchForm.getInitialDate();
		if (param != null && param.length() > 0)
		{
			try
			{		
				if(baseStoreFormatDate.equalsIgnoreCase("USA"))
				{
					String month = param.substring(0, 3);
					String day = param.substring(4, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
					
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
									
					String newDate = year+"-"+monthSpanish+"-"+day;
					searchParameters.setInitialDate(dateFormatter.parse(newDate));
				}else
				{
					//01-Mar-2016
					String day = param.substring(0, 2);
					String month = param.substring(3, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
					
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
					
					
					String newDate = year+"-"+monthSpanish+"-"+day;
					searchParameters.setInitialDate(dateFormatter.parse(newDate));
				
				}
				//searchParameters.setInitialDate(dateFormatter.parse(param));
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
		}
		
		param = searchForm.getFinalDate();
		if (param != null && param.length() > 0)
		{
			try
			{

				if(baseStoreFormatDate.equalsIgnoreCase("USA"))
				{
					String month = param.substring(0, 3);
					String day = param.substring(4, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
					
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
					String newDate = year+"-"+monthSpanish+"-"+day;
					
					/*searchParameters.setFinalDate(dateFormatter.parse(newDate));*/
					
					Date finalDate = dateFormatter.parse(newDate);
					// after Date created, set it to the latest time of day to include whole day
					Calendar cal = Calendar.getInstance();
					cal.setTime(finalDate);
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					
					searchParameters.setFinalDate(cal.getTime());
					
				}else
				{
					//01-Mar-2016
					String day = param.substring(0, 2);
					String month = param.substring(3, 6);
					String year = param.substring(7, 11);
					String monthSpanish = "";
				
					switch(month){
					case "Jan": monthSpanish = "01"; break;
					case "Ene": monthSpanish = "01"; break;
					case "Feb": monthSpanish = "02"; break;
					case "Mar": monthSpanish = "03"; break;
					case "Apr": monthSpanish = "04"; break; 
					case "Abr": monthSpanish = "04"; break;
					case "May": monthSpanish = "05"; break;
					case "Jun": monthSpanish = "06"; break;
					case "Jul": monthSpanish = "07"; break;
					case "Aug": monthSpanish = "08"; break;
					case "Ago": monthSpanish = "08"; break;
					case "Sep": monthSpanish = "09"; break;
					case "Oct": monthSpanish = "10"; break;
					case "Nov": monthSpanish = "11"; break;
					case "Dec": monthSpanish = "12"; break;
					case "Dic": monthSpanish = "12"; break;
					//default: monthSpanish = month; break;
					}
				
					String newDate = year+"-"+monthSpanish+"-"+day;
					//searchParameters.setFinalDate(dateFormatter.parse(newDate));
					
					Date finalDate = dateFormatter.parse(newDate);
					// after Date created, set it to the latest time of day to include whole day
					Calendar cal = Calendar.getInstance();
					cal.setTime(finalDate);
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					
					searchParameters.setFinalDate(cal.getTime());
				}
				/*
				Date finalDate = dateFormatter.parse(param);
				// after Date created, set it to the latest time of day to include whole day
				Calendar cal = Calendar.getInstance();
				cal.setTime(finalDate);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				
				searchParameters.setFinalDate(cal.getTime());
				*/
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
		}
		
		try
		{			
		    searchPageData = neorisFacade.getPagedOrderHistory(searchParameters, neorisFacade.getCurrentCustomer(), quoteStatuses);
		}		
		catch (Exception ex)
		{
			LOG.error("Unable to retrieve Quote History", ex);

			GlobalMessages.addErrorMessage(model, "quoteHistory.list.error");
		}
		
		populateModel(model, searchPageData, showMode);
		
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/my-quotes", getMessageSource().getMessage(
				"text.account.manageQuotes.breadcrumb", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		return ControllerConstants.Views.Pages.Account.AccountMyQuotesPage;
	}
	
	@RequestMapping(value = "/my-quote/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String quotesDetails(@PathVariable("orderCode") final String orderCode, final Model model)
			throws CMSItemNotFoundException
	{
		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			addOrderWeightPatterToModel(model,orderDetails);
			
			if (orderDetails.getEntries() != null && !orderDetails.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : orderDetails.getEntries())
				{
					final Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(orderDetails.getUnitWhenPlaced(), entry.getQuantity(), entry.getProduct(), entry.getIsQuantityInPieces());
					entry.setConvertedQuantity(convertedQuantity);
				}
			}
			
			model.addAttribute("orderData", orderDetails);

			model.addAttribute(new ReorderForm());

			if (!model.containsAttribute("quoteOrderDecisionForm"))
			{
				model.addAttribute("quoteOrderDecisionForm", new QuoteOrderForm());
			}
			
			// injecting quantity pattern based in the order unit
			addOrderQuantityPatternToModel(model, orderDetails);
			
			final B2BUnitModel unit = neorisService.getB2BUnitForUID(orderDetails.getUnit().getUid());

			// NEORIS_CHANGE #96
			//neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderDetails.getEntries());
			neorisProductFacade.injectCustomerNameAndDescriptionDataOn(orderDetails.getEntries(), unit);
			neorisCartPriceHelper.sortEntriesBySapOrderEntryNumber(orderDetails);
			

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/my-quotes", getMessageSource().getMessage(
					"text.account.manageQuotes.breadcrumb", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("/my-account/my-quotes/" + orderDetails.getCode(), getMessageSource().getMessage(
					"text.account.manageQuotes.details.breadcrumb", new Object[]
					{ orderDetails.getCode() }, "Quote Details {0}", getI18nService().getCurrentLocale()), null));
			model.addAttribute("breadcrumbs", breadcrumbs);
			//NEORIS_CHANGE #96
			model.addAttribute("defaultUoM", baseStoreService.getCurrentBaseStore().getUnit());

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountQuoteDetailPage;
	}

	@RequestMapping(value = "/quote/quoteOrderDecision")
	@RequireHardLogIn
	public String quoteOrderDecision(@ModelAttribute("quoteOrderDecisionForm") final QuoteOrderForm quoteOrderForm,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_QUOTES_CMS_PAGE));
		String orderCode = null;
		try
		{
			orderCode = quoteOrderForm.getOrderCode();

			final String comment = XSSFilterUtil.filter(quoteOrderForm.getComments());

			if ("NEGOTIATEQUOTE".equals(quoteOrderForm.getSelectedQuoteDecision()))
			{
				if (StringUtils.isBlank(comment))
				{
					setUpCommentIsEmptyError(quoteOrderForm, model);
					return quotesDetails(orderCode, model);
				}
				orderFacade.createAndSetNewOrderFromNegotiateQuote(orderCode, comment);
			}

			if ("ACCEPTQUOTE".equals(quoteOrderForm.getSelectedQuoteDecision()))
			{
				final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
				final Date quoteExpirationDate = orderDetails.getQuoteExpirationDate();
				if (quoteExpirationDate != null && quoteExpirationDate.before(new Date()))
				{
					GlobalMessages.addErrorMessage(model, "text.quote.expired");
					return quotesDetails(orderCode, model);
				}

				//NEORIS_CHANGE
				try
				{					
					String shippingInstructions = null;
					if (!quoteOrderForm.getShippingInstructions().trim().isEmpty()) 
					{
						shippingInstructions = quoteOrderForm.getShippingInstructions();
					}
					
					String newOrderCode = neorisB2BOrderFacade.cloneAndSetNewOrderFromApprovedQuote(orderCode, comment, shippingInstructions);
					return REDIRECT_PREFIX + "/checkout/orderConfirmation/" + newOrderCode;
				}
				catch (Exception ex)
				{
					GlobalMessages.addErrorMessage(model, ex.getMessage());
					return quotesDetails(orderCode, model);
				}
			}

			if ("CANCELQUOTE".equals(quoteOrderForm.getSelectedQuoteDecision()))
			{
				// NEORIS_CHANGE 30082014 CLS
				try {
					neorisSapOrderCreator.rejectOrAcceptSapOrderForCode(orderCode, comment, "R");
					orderFacade.cancelOrder(orderCode, comment);
				} catch (Exception ex) {
					GlobalMessages.addErrorMessage(model, ex.getMessage());
					return quotesDetails(orderCode, model);
				}
			}

			if ("ADDADDITIONALCOMMENT".equals(quoteOrderForm.getSelectedQuoteDecision()))
			{
				if (StringUtils.isBlank(comment))
				{
					setUpCommentIsEmptyError(quoteOrderForm, model);
					return quotesDetails(orderCode, model);
				}
				orderFacade.addAdditionalComment(orderCode, comment);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
						"text.confirmation.quote.comment.added");
				return String.format(REDIRECT_TO_QUOTES_DETAILS, orderCode);
			}
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}

		return REDIRECT_PREFIX + "/checkout/quoteOrderConfirmation/" + orderCode;
	}

	protected void setUpCommentIsEmptyError(final QuoteOrderForm quoteOrderForm, final Model model) throws CMSItemNotFoundException
	{
		quoteOrderForm.setNegotiateQuote(true);
		model.addAttribute("quoteOrderDecisionForm", quoteOrderForm);
		GlobalMessages.addErrorMessage(model, "text.quote.empty");
	}

	@RequestMapping(value = "/my-replenishment", method = RequestMethod.GET)
	@RequireHardLogIn
	public String myReplenishment(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{
		final PageableData pageableData = createPageableData(page, 20, sortCode, showMode);
		final SearchPageData<? extends ScheduledCartData> searchPageData = orderFacade.getPagedReplenishmentHistory(pageableData);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.manageReplenishment"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountReplenishmentSchedule;
	}

	@RequestMapping(value = "/my-replenishment/" + JOB_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String replenishmentDetails(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, @PathVariable("jobCode") final String jobCode,
			final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("scheduleData",
				orderFacade.getReplenishmentOrderDetailsForCode(jobCode, customerFacade.getCurrentCustomer().getUid()));
		final PageableData pageableData = createPageableData(page, 20, sortCode, showMode);
		final SearchPageData<? extends OrderHistoryData> searchPageData = orderFacade.getPagedReplenishmentOrderHistory(jobCode,
				pageableData);
		populateModel(model, searchPageData, showMode);
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/my-replenishment", getMessageSource().getMessage(
				"text.account.manageReplenishment", null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(String.format("/my-account/my-replenishment/%s/", jobCode), getMessageSource().getMessage(
				"text.account.replenishment.replenishmentBreadcrumb", new Object[]
				{ jobCode }, "Replenishment Orders {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountReplenishmentScheduleDetails;
	}

	@RequestMapping(value = "/my-replenishment/cancel/" + JOB_CODE_PATH_VARIABLE_PATTERN, method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String cancelReplenishment(@PathVariable("jobCode") final String jobCode, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		this.orderFacade.cancelReplenishment(jobCode, customerFacade.getCurrentCustomer().getUid());
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.replenishment.confirmation.canceled");
		return REDIRECT_TO_MYREPLENISHMENTS_PAGE;
	}

	@RequestMapping(value = "/my-replenishment/detail/cancel/" + JOB_CODE_PATH_VARIABLE_PATTERN, method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String cancelReplenishmentFromDetailPage(@PathVariable("jobCode") final String jobCode, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		this.orderFacade.cancelReplenishment(jobCode, customerFacade.getCurrentCustomer().getUid());
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.replenishment.confirmation.canceled");
		return String.format(REDIRECT_TO_MYREPLENISHMENTS_DETAIL_PAGE, jobCode);
	}

	@RequestMapping(value = "/my-replenishment/detail/confirmation/cancel/" + JOB_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String confirmCancelReplenishmentFromDetailsPage(@PathVariable("jobCode") final String jobCode, final Model model,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/my-replenishment", getMessageSource().getMessage(
				"text.account.manageReplenishment", null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(String.format("/my-account/my-replenishment/%s/", jobCode), getMessageSource().getMessage(
				"text.account.replenishment.replenishmentBreadcrumb", new Object[]
				{ jobCode }, "Replenishment Orders {0}", getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(
				"text.account.manageReplenishment.confirm.cancel.breadcrumb", new Object[]
				{ jobCode }, "Remove Replenishment Schedule {0}", getI18nService().getCurrentLocale()), null));

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s", jobCode));
		model.addAttribute("page", "replenishment");
		model.addAttribute("disableUrl",
				String.format("%s/my-account/my-replenishment/detail/cancel/%s", request.getContextPath(), jobCode));
		model.addAttribute("cancelUrl", String.format("%s/my-account/my-replenishment/%s", request.getContextPath(), jobCode));
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountCancelActionConfirmationPage;
	}

	@RequestMapping(value = "/my-replenishment/confirmation/cancel/" + JOB_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String confirmCancelReplenishment(@PathVariable("jobCode") final String jobCode, final Model model,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-account/my-replenishment", getMessageSource().getMessage(
				"text.account.manageReplenishment", null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(
				"text.account.manageReplenishment.confirm.cancel.breadcrumb", new Object[]
				{ jobCode }, "Remove Replenishment Schedule {0}", getI18nService().getCurrentLocale()), null));

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s", jobCode));
		model.addAttribute("page", "replenishment");
		model.addAttribute("disableUrl",
				String.format("%s/my-account/my-replenishment/cancel/%s", request.getContextPath(), jobCode));
		model.addAttribute("cancelUrl", String.format("%s/my-account/my-replenishment/%s", request.getContextPath(), jobCode));
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountCancelActionConfirmationPage;
	}
	
	/*@RequestMapping(value = "/approval-dashboard", method ={ RequestMethod.GET }) 
	@RequireHardLogIn
	public String orderApprovalDashboard(@RequestParam(value = "page", defaultValue = "0") final int page,
			@Valid final NeorisOrderApprovalSearchForm searchForm, final Model model)
			throws CMSItemNotFoundException
	{
		ShowMode showMode = ShowMode.Page;
		
		//create PageableData
		final PageableData pageableData = createPageableData(page, 20, null, showMode);

		// save search form on model
		model.addAttribute("neorisOrderApprovalSearchForm", searchForm);
		
		
		// create and fill the search parameters
		NeorisOrderApprovalSearchParameters searchParameters = new NeorisOrderApprovalSearchParameters(); 
		searchParameters.setPageableData(pageableData);
		
		//searchParameters.setUser(neorisFacade.getCurrentCustomer().getPk().toString());
		
		
		
		if (searchForm.getB2bUnit() != null && searchForm.getB2bUnit().length() > 0)
			searchParameters.setB2bUnits(searchForm.getB2bUnit().split("-"));

		// get and set sort information
		
				String sortCode = "Quote Number-asc";
				if (sortCodeParameter != null && sortCodeParameter.length() > 0)
				{
					sortCode = sortCodeParameter;
				}else if (searchForm.getSort() != null && searchForm.getSort().length() > 0)
				{
					sortCode = searchForm.getSort();
				}
				
				String sortCode = searchForm.getSort(); 
				String[] sortInfo = getSortByAndOrderFrom(sortCode);
				
				
				if (sortInfo != null)
				{
					if("all".equalsIgnoreCase(b2bUnitsParameter) &&"all".equalsIgnoreCase(searchForm.getB2bUnit()))
					{
						searchParameters.setB2bUnits(null);
					}else
					{
						//if (searchForm.getB2bUnit() != null && searchForm.getB2bUnit().length() > 0)
							searchParameters.setB2bUnits(searchForm.getB2bUnit().split("-"));
						//else if (b2bUnitsParameter != null && b2bUnitsParameter.length() > 0)
						//	searchParameters.setB2bUnits(b2bUnitsParameter.split("-"));
					}
					
					if ("Order Number".equals(sortInfo[0]))
					{
						searchParameters.setSortBy("code");
					}
					else if("Order Date".equals(sortInfo[0]))
					{
						searchParameters.setSortBy("creationTime");
					}
					searchParameters.setSortOrder(sortInfo[1]);
				}
		
				// add sorts to searchPageData
				String[] sortProps = new String[] { "Order Number","Order Date" };
				List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "Order Number-asc" : sortCode, "{0}-(asc)", "{0}-(desc)");
				model.addAttribute("sorts", sorts);
		
				SearchPageData<? extends B2BOrderApprovalData> searchPageData = new SearchPageData<B2BOrderApprovalData>();
				searchPageData.setPagination(new PaginationData());
		
		if (sortCode != null)
		{
			try
			{

				searchPageData = neorisB2BOrderFacade.getPagedOrdersForApproval(new WorkflowActionType[]{ WorkflowActionType.START }, pageableData, searchParameters);
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve Order Approvals", ex);

				GlobalMessages.addErrorMessage(model, "text.account.orderApproval.list.error=Error in Order Approval");
			}
			
		}
		
		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "false");

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderApprovalDashboard"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_APPROVAL_DASHBOARD_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_APPROVAL_DASHBOARD_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountOrderApprovalDashboardPage;
		
	}*/

	// NEORIS_CHANGE #103 Added ability to search order approvals for b2bunit
		@RequestMapping(value = "/approval-dashboard", method = RequestMethod.GET)
		@RequireHardLogIn
		public String orderApprovalDashboard(
				@RequestParam(value = "page", defaultValue = "0") final int page,
				@Valid final NeorisOrderApprovalSearchForm searchForm, final Model model, final HttpServletRequest request,final HttpServletResponse response) throws CMSItemNotFoundException
		{
			// changing page send parameters instead of form
			String b2bUnitsParameter = request.getParameter("b2bUnit");
			String sortCodeParameter = request.getParameter("sort");
			ShowMode showMode = ShowMode.Page;
			
			//create PageableData
			final PageableData pageableData = createPageableData(page, 20, null, showMode);

			// save search form on model
			model.addAttribute("neorisOrderApprovalSearchForm", searchForm);
			
			final List<String> listB2BUnitCode = new ArrayList<String>();
			
			// create and fill the search parameters
			NeorisOrderApprovalSearchParameters searchParameters = new NeorisOrderApprovalSearchParameters(); 
			searchParameters.setPageableData(pageableData);
			
			searchParameters.setUser(neorisFacade.getCurrentCustomer().getPk().toString());
			
			
			
			if("all".equalsIgnoreCase(b2bUnitsParameter) &&"all".equalsIgnoreCase(searchForm.getB2bUnit()))
			{
				searchParameters.setB2bUnits(null);
			}else
			{
				if (searchForm.getB2bUnit() != null && searchForm.getB2bUnit().length() > 0)
					searchParameters.setB2bUnits(searchForm.getB2bUnit().split("-"));
				else if (b2bUnitsParameter != null && b2bUnitsParameter.length() > 0)
					searchParameters.setB2bUnits(b2bUnitsParameter.split("-"));
			}
			
			
			// get and set sort information
			/*
			String sortCode = "Quote Number-asc";
			if (sortCodeParameter != null && sortCodeParameter.length() > 0)
			{
				sortCode = sortCodeParameter;
			}else if (searchForm.getSort() != null && searchForm.getSort().length() > 0)
			{
				sortCode = searchForm.getSort();
			}
			*/
			String sortCode = searchForm.getSort(); 
			String[] sortInfo = getSortByAndOrderFrom(sortCode);
			
			
			if (sortInfo != null)
			{
				if ("Order Number".equals(sortInfo[0]))
				{
					searchParameters.setSortBy("code");
				}
				else if("Order Date".equals(sortInfo[0]))
				{
					searchParameters.setSortBy("creationTime");
				}
				searchParameters.setSortOrder(sortInfo[1]);
			}
			
			// add sorts to searchPageData
			String[] sortProps = new String[] { "Order Number","Order Date" };
			List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "Order Number-asc" : sortCode, "{0}-(asc)", "{0}-(desc)");
			model.addAttribute("sorts", sorts);
			
			SearchPageData<? extends B2BOrderApprovalData> searchPageData = new SearchPageData<B2BOrderApprovalData>();
			searchPageData.setPagination(new PaginationData());
			
			if (sortCode != null  )
			{
				try
				{
					searchPageData = neorisB2BOrderFacade.getPagedOrdersForApproval(new WorkflowActionType[]{ WorkflowActionType.START }, pageableData, searchParameters);
				}
				catch (Exception ex)
				{
					// if other type of error
					LOG.error("Unable to retrieve Order Approvals", ex);

					GlobalMessages.addErrorMessage(model, "text.account.orderApproval.list.error=Error in Order Approval");
				}
				
			}
			
			populateModel(model, searchPageData, showMode);
			model.addAttribute("isShowAllAllowed", "false");
			model.addAttribute("isShowPageAllowed", "false");

			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderApprovalDashboard"));
			storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_APPROVAL_DASHBOARD_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_APPROVAL_DASHBOARD_CMS_PAGE));
			model.addAttribute("metaRobots", "no-index,no-follow");
			return ControllerConstants.Views.Pages.Account.AccountOrderApprovalDashboardPage;
		}

	@RequestMapping(value = "/my-replenishment/" + JOB_CODE_PATH_VARIABLE_PATTERN + "/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String replenishmentOrderDetail(@PathVariable("jobCode") final String jobCode,
			@PathVariable("orderCode") final String orderCode, final Model model) throws CMSItemNotFoundException
	{
		try
		{
			model.addAttribute("orderData", orderFacade.getOrderDetailsForCode(orderCode));
			model.addAttribute("scheduleData",
					orderFacade.getReplenishmentOrderDetailsForCode(jobCode, customerFacade.getCurrentCustomer().getUid()));
			model.addAttribute(new ReorderForm());

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/my-replenishment", getMessageSource().getMessage(
					"text.account.manageReplenishment", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb(String.format("/my-account/my-replenishment/%s/", jobCode), getMessageSource()
					.getMessage("text.account.replenishment.replenishmentBreadcrumb", new Object[]
					{ jobCode }, "Replenishment {0}", getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb(String.format("/my-account/my-replenishment/%s/%s/", jobCode, orderCode),
					getMessageSource().getMessage("text.account.replenishment.replenishmentOrderDetailBreadcrumb", new Object[]
					{ orderCode }, "Order {0}", getI18nService().getCurrentLocale()), null));
			model.addAttribute("breadcrumbs", breadcrumbs);
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_REPLENISHMENT_ORDERS_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountOrderPage;
	}

	@RequestMapping(value = "/orderApproval/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderApproval(@PathVariable("orderCode") final String orderCode, final Model model)
			throws CMSItemNotFoundException
	{
		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			model.addAttribute("orderData", orderDetails);

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/orders", getMessageSource().getMessage(
					"text.account.orderApprovalDashboard", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.order.orderBreadcrumb", new Object[]
			{ orderDetails.getCode() }, "Order {0}", getI18nService().getCurrentLocale()), null));
			model.addAttribute("breadcrumbs", breadcrumbs);
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			return REDIRECT_MY_ACCOUNT;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.AccountOrderApprovalDetailsPage;
	}
	
	public List<AddressData> getDeliveryAdrressesForSelect(List<B2BUnitModel> listb2bUnitModel) 
	{
		List <AddressData> deliveryAddresses = new ArrayList <AddressData>();
		
		for(B2BUnitModel b2bUnitModel: listb2bUnitModel)
			deliveryAddresses.addAll(neorisFacade.getShippingAddress(b2bUnitModel));
		
		return deliveryAddresses;
	}
	
	
	
	public class B2BUnitAddresses
	{
		public List<AddressData> billingAddressList;
		public List<AddressData> shippingAddressList;
		/**
		 * @return the billingAddressList
		 */
		public List<AddressData> getBillingAddressList()
		{
			return billingAddressList;
		}
		/**
		 * @param billingAddressList the billingAddressList to set
		 */
		public void setBillingAddressList(List<AddressData> billingAddressList)
		{
			this.billingAddressList = billingAddressList;
		}
		/**
		 * @return the shippingAddressList
		 */
		public List<AddressData> getShippingAddressList()
		{
			return shippingAddressList;
		}
		/**
		 * @param shippingAddressList the shippingAddressList to set
		 */
		public void setShippingAddressList(List<AddressData> shippingAddressList)
		{
			this.shippingAddressList = shippingAddressList;
		}
	}
		
}
