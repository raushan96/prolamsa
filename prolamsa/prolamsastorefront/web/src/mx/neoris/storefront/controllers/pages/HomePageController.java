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
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderDataHomePage;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.constants.GeneratedProlamsaCoreConstants.Enumerations.MeasurementSystemType;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.facades.NeorisInvoiceFacade;
import mx.neoris.facades.invoice.data.InvoiceData;
import mx.neoris.facades.orders.NeorisB2BOrderFacade;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.checkout.SingleStepCheckoutController.SelectOption;
import mx.neoris.storefront.forms.UpdatePasswordForm;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for home page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/")
public class HomePageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(AccountPageController.class);
	private static final String SYSTEM_UNIT_SLOT = "systemUnit";
	private static final String PROFILE_CMS_PAGE = "profile";

	@Resource(name = "neorisInvoiceFacade")
	private NeorisInvoiceFacade neorisInvoicFacade;

	@Resource(name = "neorisB2BOrderFacade")
	private NeorisB2BOrderFacade neorisB2BOrderFacade;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@Resource(name = "userFacade")
	protected UserFacade userFacade;

	@Resource(name = "b2bCustomerFacade")
	protected CustomerFacade customerFacade;
	
	
	@ModelAttribute("systemList")
	public List<HybrisEnumValue> getSystemList()
	{					
		List<HybrisEnumValue> MeasurementSystemType = enumerationService.getEnumerationValues("MeasurementSystemType");					
		return  MeasurementSystemType;
	}
	
	@ModelAttribute("currentSystem")
	public HybrisEnumValue getCurrentSystem()
	{				
		HybrisEnumValue MeasurementSystemType = baseStoreService.getCurrentBaseStore().getDefaultMeasurementSystem();	
		
		if (sessionService.getAttribute(SYSTEM_UNIT_SLOT) == null){
			sessionService.setAttribute(SYSTEM_UNIT_SLOT, MeasurementSystemType);
			return  MeasurementSystemType;
		}
		
		HybrisEnumValue enumValue = sessionService.getAttribute(SYSTEM_UNIT_SLOT);
		sessionService.setAttribute(SYSTEM_UNIT_SLOT, enumValue);
		return  enumValue;
	}
	
	
	@ModelAttribute("unitsByEnglishSystem")
	public List<UnitModel> getUnitsByEnglishSystem()
	{
		return neorisFacade.getUnitBySystem("English");
	}
	
	@ModelAttribute("unitsByMetricSystem")
	public List<UnitModel> getUnitsByMetricSystem()
	{
		return neorisFacade.getUnitBySystem("Metric");
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "logout", defaultValue = "false")
	final boolean logout, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (logout)
		{
			//GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER, "account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}
		
		B2BCustomerModel user = neorisFacade.getCurrentCustomer();
		B2BUnitModel rootUnit = neorisFacade.getRootUnit();
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		if(user.isIsFirstAccesPortal())
		{
			//Update isFirstAcccesPortal
			neorisFacade.updateFirstAccesPortal(user);
			
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
		
		List<B2BUnitData> listB2BUnits = getFormattedB2BUnits();
		
		if(baseStore.isShowOrdersAndQuotes())
		{
			// get recent orders
			try
			{
				final List<OrderStatus> validStatus = neorisFacade.getValidStatusForOrdersOnly();
				
				OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters();
				
				PageableData pageableData = new PageableData();
				pageableData.setCurrentPage(0);
				pageableData.setPageSize(10);
				pageableData.setSort(null);
				
				searchParameters.setPageableData(pageableData);
				searchParameters.setSortOrder("desc");
				searchParameters.setSortBy("date");
				searchParameters.setCustomer("all");
				
				SearchPageData<OrderDataHomePage> recentOrders = neorisFacade.getPagedOrderHistoryForHomePage(searchParameters, user, validStatus);
				
				// neorisFacade.getXMLUpdateOrderStatus(recentOrders);
				model.addAttribute("recentOrders", recentOrders.getResults());
			}
			catch (Exception e)
			{
				LOG.error(e);
			}
			
			// get recent quotes
			try
			{
				final List<OrderStatus> validStatus = neorisFacade.getValidStatusForQuotesOnly();

				OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters();

				PageableData pageableData = new PageableData();
				pageableData.setCurrentPage(0);
				pageableData.setPageSize(10);
				pageableData.setSort(null);

				searchParameters.setPageableData(pageableData);
				searchParameters.setSortOrder("desc");
				searchParameters.setSortBy("date");
				searchParameters.setCustomer("all");

				SearchPageData<OrderDataHomePage> recentOrders = neorisFacade.getPagedOrderHistoryForHomePage(searchParameters, user, validStatus);

				model.addAttribute("recentQuotes", recentOrders.getResults());
			}
			catch (Exception e)
			{
				LOG.error(e);
			}
			
			// get recent templates/draft
			List<Wishlist2Model> recentTemplates = new ArrayList<Wishlist2Model>();

			try
			{
				List<B2BUnitModel> listB2BUnitsModel = neorisFacade.getB2BUnitModelsFromCustomer(user);
				recentTemplates = neorisFacade.getWishlistForUserAndAllB2BUnits(user, listB2BUnitsModel);
				model.addAttribute("recentTemplates", recentTemplates);
			}
			catch (Exception e)
			{
				LOG.error(e);
			}
		}

		// get recent invoices
		try
		{
			List<InvoiceData> recentInvoices = neorisInvoicFacade.getInvoicesByB2BUnit(listB2BUnits);
			model.addAttribute("recentInvoices", recentInvoices);
		}
		catch (Exception e)
		{
			LOG.error(e);
		}

		/*storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));
		*/
		
		storeCmsPageInModel(model, getContentPageForLabelOrId("homepage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("homepage"));
		updatePageTitle(model, getContentPageForLabelOrId("homepage"));
		
		return getViewForPage(model);
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
}