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
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
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
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.workflow.enums.WorkflowActionType;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.incident.IncidentLine;
import mx.neoris.core.invoice.ValidateInvoiceResponse;
import mx.neoris.core.model.BalanceStatementDetailModel;
import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.model.IncidentStateModel;
import mx.neoris.core.model.IncidentTypeModel;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.IncidentSearchParameters;
import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisQuoteSearchParameters;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisIncidentFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.orders.NeorisB2BOrderFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.Breadcrumb;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.AddIncidentForm;
import mx.neoris.storefront.forms.AddressForm;
import mx.neoris.storefront.forms.IncidentSearchForm;
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
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller for home page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/incident")
public class IncidentPageController extends AbstractSearchPageController 
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
	
	private static final String ADD_INCIDENT_PAGE ="AddIncidentPage";
	private static final String INCIDENT_LIST_PAGE="IncidentPage";
	private static final String INCIDENT_DETAIL_PAGE="IncidentDetailPage";
	
	private static final Logger LOG = Logger.getLogger(IncidentPageController.class);
	
	//private static final long MAX_ALLOWED_FILE_SIZE = 5242880L; //5M
	//private static final String MAX_ALLOWED_FILE_SIZE_LABEL_MB = "5M";
	private static final long MAX_ALLOWED_FILE_SIZE = 10485760L; //10M
	private static final String MAX_ALLOWED_FILE_SIZE_LABEL_MB = "10M";
	
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
	
	@Resource
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;
	
	@Resource(name = "mediaService")
	private MediaService mediaService;
	
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
	
	@Resource(name="neorisIncidentFacade")
	private NeorisIncidentFacade neorisIncidentFacade; 
	
	@Resource(name = "userService")
	private UserService userService;
	
	@ModelAttribute("incidentTypes")
	public List<IncidentTypeModel> getIncidentTypes()
	{
		return baseStoreService.getCurrentBaseStore().getIncindentTypes();
	}
	
	@ModelAttribute("incidentStates")
	public List<IncidentStateModel> getIncidentStates()
	{
		return neorisIncidentFacade.getAllIncidentStates();
	}
	
	@RequestMapping(value = "/incidentAttach", method = RequestMethod.GET)
	public void getOrderDocument(HttpServletRequest request, HttpServletResponse response)
	{
		String incidentCode = request.getParameter("incidentCode");
		String attachIndex= request.getParameter("attachIndex");
		
		if (incidentCode == null)
			return;

		IncidentModel incidentModel = neorisIncidentFacade.getIncident(incidentCode);
		
		if (incidentModel == null)
			return;
		
		MediaModel media = new MediaModel();
		if(attachIndex.equalsIgnoreCase("1"))
			media = incidentModel.getAttachement1();
		if(attachIndex.equalsIgnoreCase("2"))
			media = incidentModel.getAttachement2();
		if(attachIndex.equalsIgnoreCase("3"))
			media = incidentModel.getAttachement3();
		if(attachIndex.equalsIgnoreCase("4"))
			media = incidentModel.getAttachement4();
		if(attachIndex.equalsIgnoreCase("5"))
			media = incidentModel.getAttachement5();
		
		if (media == null)
			return;

		InputStream inputStream = mediaService.getStreamFromMedia(media);

		String documentName = media.getRealFileName();
		String documentType = media.getMime();

		OutputStream outputStream = null;

		try
		{
			response.setContentType(documentType);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + documentName + "\";");

			outputStream = response.getOutputStream();

			IOUtils.copy(inputStream, outputStream);
		}
		catch (Exception ex)
		{
			LOG.error("error while getting document", ex);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	@RequestMapping(value = "/incidentList", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String incidentList(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,			
			IncidentSearchForm searchForm,	final Model model,	final HttpServletRequest request, final HttpServletResponse response)
			throws Exception
	{
		
		if( request.getParameter("customer")!=null)
		{
			String initialDate =request.getParameter("initialDate"); 
			String finalDate= request.getParameter("finalDate");
			String type = request.getParameter("type");
			String state = request.getParameter("state");
			String customer = request.getParameter("customer");
			searchForm = new IncidentSearchForm();
			searchForm.setInitialDate(initialDate);
			searchForm.setFinalDate(finalDate);
			searchForm.setType(type);
			searchForm.setState(state);
			searchForm.setCustomer(customer);
		}
		
		if(searchForm != null && StringUtils.isNotBlank(searchForm.getCustomer()))
		{
			PageableData pageableData = new PageableData();
			pageableData.setPageSize(20);
			pageableData.setCurrentPage(page);
			
			
			IncidentSearchParameters searchParameters = new IncidentSearchParameters();
			searchParameters.setPageableData(pageableData);
			searchParameters.setCustomer(searchForm.getCustomer());
			searchParameters.setNumber(searchForm.getNumber());
			searchParameters.setState(searchForm.getState());
			searchParameters.setType(searchForm.getType());
			searchParameters.setUser(userService.getCurrentUser().getUid());
			searchParameters.setBaseStore(getCurrentBaseStore());
			
			String initialDate= searchForm.getInitialDate();
			if(StringUtils.isNotBlank(initialDate))
				searchParameters.setInitialDate(dateFormatter.parse(initialDate));
			
			String finalDate= searchForm.getFinalDate();
			if(StringUtils.isNotBlank(finalDate))
				searchParameters.setFinalDate(dateFormatter.parse(finalDate));
			
			SearchPageData<IncidentModel> searchPageData = neorisIncidentFacade.getPagedIncidents(searchParameters);
			if(searchPageData != null)
			{
				populateModel(model,searchPageData,showMode);				
			}
		}
		
		model.addAttribute("searchForm", searchForm);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(INCIDENT_LIST_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(INCIDENT_LIST_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("incident.list.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Incidents.IncidentList;
	}
	
	
	@RequestMapping(value = "/addIncident", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String addIncident(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,			
			@Valid final AddIncidentForm addIncidentForm,final BindingResult bindingResult,	final Model model,final RedirectAttributes redirectModel,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		model.addAttribute("addIncidentForm", addIncidentForm);
		
		
		
		if(request.getMethod().equalsIgnoreCase("POST") )
		{
			if (bindingResult.hasErrors())
			{
				for (final ObjectError error : bindingResult.getAllErrors())
				{
					{
						GlobalMessages.addErrorMessage(model, error.getDefaultMessage());
					}
				}
			}else
			{
				AddIncidentParameters parameters = new AddIncidentParameters();
								
				boolean errorOnAttachments = false;
				
				if(addIncidentForm.getAtach1()!=null && addIncidentForm.getAtach1().getSize()>0)
				{
					if(isValidMultipartFileSizeOnForm(addIncidentForm.getAtach1(), model, 1))
					{
						parameters.setAtach1(addIncidentForm.getAtach1());
						parameters.setAtach1Description(addIncidentForm.getAtach1Description());						
					}
					else
					{
						errorOnAttachments = true;
					}
				}
					
				if(addIncidentForm.getAtach2()!=null && addIncidentForm.getAtach2().getSize()>0)
				{
					if(isValidMultipartFileSizeOnForm(addIncidentForm.getAtach2(), model, 2))
					{
						parameters.setAtach2(addIncidentForm.getAtach2());
						parameters.setAtach2Description(addIncidentForm.getAtach2Description());						
					}
					else
					{
						errorOnAttachments = true;
					}
				}
				
				if(addIncidentForm.getAtach3()!=null && addIncidentForm.getAtach3().getSize()>0)
				{
					if(isValidMultipartFileSizeOnForm(addIncidentForm.getAtach3(), model, 3))
					{
						parameters.setAtach3(addIncidentForm.getAtach3());
						parameters.setAtach3Description(addIncidentForm.getAtach3Description());						
					}
					else
					{
						errorOnAttachments = true;
					}
				}
				
				if(addIncidentForm.getAtach4()!=null && addIncidentForm.getAtach4().getSize()>0)
				{
					if(isValidMultipartFileSizeOnForm(addIncidentForm.getAtach4(), model, 4))
					{
						parameters.setAtach4(addIncidentForm.getAtach4());
						parameters.setAtach4Description(addIncidentForm.getAtach4Description());						
					}
					else
					{
						errorOnAttachments = true;
					}
				}
				
				if(addIncidentForm.getAtach5()!=null && addIncidentForm.getAtach5().getSize()>0)
				{
					if(isValidMultipartFileSizeOnForm(addIncidentForm.getAtach5(), model, 5))
					{
						parameters.setAtach5(addIncidentForm.getAtach5());
						parameters.setAtach5Description(addIncidentForm.getAtach5Description());						
					}
					else
					{
						errorOnAttachments = true;
					}
				}
				
				if (!errorOnAttachments)
				{					
					parameters.setCustomer(addIncidentForm.getCustomer());
					parameters.setContactName(addIncidentForm.getContactName());
					parameters.setAlternateContactName(addIncidentForm.getAlternateContactName());
					parameters.setInvoice(addIncidentForm.getInvoice());
					
					parameters.setCustomerIncidentNumber(addIncidentForm.getCustomerIncidentNumber());
					parameters.setDescription(addIncidentForm.getDescription());
					parameters.setEmail(addIncidentForm.getEmail());
					parameters.setPhone(addIncidentForm.getPhone());
					parameters.setType(addIncidentForm.getType());
					parameters.setShipTo(addIncidentForm.getShipTo());
					parameters.setBaseStore(getCurrentBaseStore());
					
					parameters.setProductLocation(addIncidentForm.getProductLocation());
					parameters.setCountry(addIncidentForm.getCountry());
					parameters.setIncoterm(addIncidentForm.getIncoterm());
					
					if(StringUtils.isNotBlank(addIncidentForm.getIncidentLines()))
						getIncidentLines(parameters, addIncidentForm.getIncidentLines());
					
					IncidentModel incidentModel =neorisIncidentFacade.addIncident(parameters);
					
					GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
							"incident.add.success", new Object[]
									{ incidentModel.getCode() });
					
					return REDIRECT_PREFIX + "/incident/incidentDetail?incidentCode="+incidentModel.getCode();
				}
			}
		}
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_INCIDENT_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_INCIDENT_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("incident.add.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Incidents.AddIncident;
	}
	
	private boolean isValidMultipartFileSizeOnForm(final MultipartFile attachment, final Model model, final int attachIndex)
	{
		if(attachment.getSize() > MAX_ALLOWED_FILE_SIZE)
		{
			// add error message on model
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "incident.add.file.exceededSize.error", new Object[]{attachIndex, MAX_ALLOWED_FILE_SIZE_LABEL_MB});
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = "/incidentDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String incidentDetail(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,			
			OrderHistorySearchForm searchForm,	final Model model,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		String incidentCode = request.getParameter("incidentCode");
		
		IncidentModel incident = neorisIncidentFacade.getIncident(incidentCode);
		
		//change value to display for incoterm
		String incotermDesciption = "";

		final List<TransportationModeModel> transportationModes = baseStoreService.getCurrentBaseStore()
				.getTransportationModes();
		for (final TransportationModeModel eachTransportation : transportationModes)
		{
			if (eachTransportation.getIncotermCode().equals(incident.getIncoterm()))
			{
				incotermDesciption = eachTransportation.getIncotermDescription();
				break;
			}
		}
		
		incident.setIncoterm(incident.getIncoterm() + " - " + incotermDesciption);
		
		model.addAttribute("incident", incident);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(INCIDENT_DETAIL_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(INCIDENT_DETAIL_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("incident.detail.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Incidents.IncidentDetail;
	}
	
	@RequestMapping(value = "/validateInvoice", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ValidateInvoiceResponse validateInvoice(@RequestParam(value = "invoiceNumber") final String invoiceNumber,@RequestParam(value = "customer") final String customer,final Model model,	final HttpServletRequest request, final HttpServletResponse response)
			throws Exception
	{
		return neorisIncidentFacade.validateInvoice(invoiceNumber,customer);
	}
	
	protected void getIncidentLines(AddIncidentParameters parameters, String incidentLineJSON)
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory();
		JsonNode incidentLinesNode = null;
		try
		{
			JsonParser jp = factory.createJsonParser(incidentLineJSON);
			incidentLinesNode = mapper.readTree(jp);
		}
		catch (Exception ex)
		{
			LOG.error("error while parsing incidentLines data", ex);
		}
		
		final List<IncidentLine> incidentLineList = new ArrayList<IncidentLine>();

		for (JsonNode eachNode : incidentLinesNode)
		{
			String plant = eachNode.get("plant").asText();
			String salesUnit =eachNode.get("salesUnit").asText();
			Integer quantity=eachNode.get("quantity").asInt();
			String weightUnit = eachNode.get("weightUnit").asText();
			Double netweight = eachNode.get("netweight").asDouble();
			Integer batch = eachNode.get("batch").asInt();
			String sorder_p = eachNode.get("sorder_p").asText();
			String sorder = eachNode.get("sorder").asText();
			String product = eachNode.get("product").asText();
			String invoice = eachNode.get("invoice").asText();
			String invoice_p = eachNode.get("invoice_p").asText();
			Integer quantityToClaim = eachNode.get("quantityToClaim").asInt();
			String shipTo = eachNode.get("shipTo").asText();
			String productDescription = eachNode.get("productDescription").asText();
			
			IncidentLine eachIncidentLine = new IncidentLine();

			eachIncidentLine.setPlant(plant);
			eachIncidentLine.setSalesUnit(salesUnit);
			eachIncidentLine.setQuantity(quantity);
			eachIncidentLine.setWeightUnit(weightUnit);
			eachIncidentLine.setNetweight(netweight);
			eachIncidentLine.setBatch(batch);
			eachIncidentLine.setSorder(sorder);
			eachIncidentLine.setSorder_p(sorder_p);
			eachIncidentLine.setProduct(product);
			eachIncidentLine.setInvoice(invoice);
			eachIncidentLine.setInvoice_p(invoice_p);
			eachIncidentLine.setQuantityToClaim(quantityToClaim);
			eachIncidentLine.setShipTo(shipTo);
			eachIncidentLine.setProdcutDescription(productDescription);
			
			incidentLineList.add(eachIncidentLine);

		}
		
		parameters.setLines(incidentLineList);
	}
	
	@RequestMapping(value = "/resendSharepoint.json", method =
		{ RequestMethod.GET, RequestMethod.POST })
		@RequireHardLogIn
		public Object resendSharepoint(final String incidentPK, final HttpServletRequest request,
				final HttpServletResponse response) 
		{
			final ObjectNode node = JsonNodeFactory.instance.objectNode();
			node.put("status", AJAX_STATUS_OK);
			
			try
			{
				neorisIncidentFacade.resendSharepoint(Long.parseLong(incidentPK));
			}
			catch (final Exception e)
			{
				/*final String message = e.getMessage().substring(0, Math.min(MAX_STACK_TRACE_SIZE, e.getMessage().length() - 1));
				newIncidentModel.setSharePointErrorTrace(message);
				modelService.save(newIncidentModel);*/

				LOG.error("Error: " + e.getMessage());
				
				node.put("status", AJAX_STATUS_ERROR);
				node.put("message", e.getMessage());
			}
			
			return node;
		}
}
