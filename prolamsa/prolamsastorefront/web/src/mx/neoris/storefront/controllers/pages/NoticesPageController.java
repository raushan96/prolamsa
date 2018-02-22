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
import mx.neoris.core.model.NoticeModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.AddNoticeParameters;
import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisQuoteSearchParameters;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
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
@RequestMapping("/my-account/notices")
public class NoticesPageController extends AbstractSearchPageController
{
	
	// CMS Pages
	private static final String NOTICES_CMS_PAGE = "NoticeListPage";
	
	private static final long MEDIA_MAX_SIZE = 5000000;
	
	private static final Logger LOG = Logger.getLogger(NoticesPageController.class);
	
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
	
	@ModelAttribute("noticeTypes")
	public List<NoticeType> getNoticeTypes()
	{
		return enumerationService.getEnumerationValues(NoticeType.class);
	}

	
	@RequestMapping(value = "/list",  method = { RequestMethod.GET })
	@RequireHardLogIn
	public String noticesList(	final Model model)throws CMSItemNotFoundException
	{
		List<NoticeData> noticeDatas = neorisNoticesFacade.getNoticesForCurrentStore(); 
		
		model.addAttribute("notices",noticeDatas);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(NOTICES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(NOTICES_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("notices.list.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/create", method = {  RequestMethod.POST })
	@RequireHardLogIn
	public String createNotice(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,			
			@Valid final CreateNoticeForm createNoticeForm,final BindingResult bindingResult,	final Model model,final RedirectAttributes redirectModel,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		model.addAttribute("createNoticeForm", createNoticeForm);
		
		boolean formHasErrors= false;
		
		if(StringUtils.isBlank(createNoticeForm.getName()))
		{
			formHasErrors = true;
			GlobalMessages.addErrorMessage(model, "notices.error.name");
		}
			
		if(StringUtils.isBlank(createNoticeForm.getType()))
		{
			formHasErrors = true;
			GlobalMessages.addErrorMessage(model, "notices.error.type");
		}
		
		if(createNoticeForm.getMedia() ==null || createNoticeForm.getMedia().getSize() == 0 ||createNoticeForm.getMedia().getSize() > MEDIA_MAX_SIZE||createNoticeForm.getMedia().getOriginalFilename().contains(".exe"))
		{
			formHasErrors = true;
			GlobalMessages.addErrorMessage(model, "notices.error.file");
		}
				
		
		if(!formHasErrors)
		{
			AddNoticeParameters parameters = new AddNoticeParameters();
			parameters.setMedia(createNoticeForm.getMedia());
			parameters.setName(createNoticeForm.getName());
			parameters.setType(createNoticeForm.getType());
			parameters.setPublicationDate(new Date());
			
			NoticeData newNoticeData = neorisNoticesFacade.addNotice(parameters);
			
			if(newNoticeData.getCode() == null)
			{
				LOG.error("There was an error creating notice");
				GlobalMessages.addErrorMessage(model, "notices.error.creation");
			}else
			{		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
						"notices.create.success", new Object[]
								{ newNoticeData.getName() });
				
				return REDIRECT_PREFIX + "/my-account/notices/list";
			}
			
		}
		
		List<NoticeData> noticeDatas = neorisNoticesFacade.getNoticesForCurrentStore(); 
		
		model.addAttribute("notices",noticeDatas);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(NOTICES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(NOTICES_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("notices.list.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/media", method = RequestMethod.GET)
	public void getMedia(HttpServletRequest request, HttpServletResponse response)
	{
		String noticeCode = request.getParameter("noticeCode");
		
		if (noticeCode == null)
			return;

		NoticeModel noticeModel = neorisNoticesFacade.getNotice(noticeCode);
		
		if (noticeModel == null)
			return;
		
		MediaModel media = new MediaModel();
		
		media = noticeModel.getMedia();
		
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
			LOG.error("error while getting media", ex);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(final Model model,final RedirectAttributes redirectModel,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		String noticeCode = request.getParameter("noticeCode");
		
		boolean hasError=false;
		boolean deleted=false;
		
		if (noticeCode == null)
		{	
			GlobalMessages.addErrorMessage(model, "notices.delete.error");
			hasError=true;
		}

		NoticeModel noticeModel = neorisNoticesFacade.getNotice(noticeCode);
		
		if (noticeModel == null)
		{	
			GlobalMessages.addErrorMessage(model, "notices.delete.error");
			hasError=true;
		}
		
		if(!hasError)
		{
			deleted = neorisNoticesFacade.deleteNotice(noticeCode);
			
			if(deleted)
			{GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
					"notices.delete.success", new Object[]
							{ noticeCode });}
			else
			{GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"notices.delete.error", new Object[]
							{ noticeCode });}
			
			
		}
		
		return REDIRECT_PREFIX + "/my-account/notices/list";
	}
		
}
