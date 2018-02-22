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

import de.hybris.platform.acceleratorcms.data.RequestContextData;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.breadcrumb.impl.SearchBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.util.MetaSanitizerUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller for a category page.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/**/c")
public class CategoryPageController extends AbstractSearchPageController
{
	protected static final Logger LOG = Logger.getLogger(CategoryPageController.class);

	private static final String PRODUCT_GRID_PAGE = "category/productGridPage";
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String CATEGORY_CODE_PATH_VARIABLE_PATTERN = "/{categoryCode:.*}";

	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "commerceCategoryService")
	private CommerceCategoryService commerceCategoryService;

	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;

	@Resource(name = "categoryModelUrlResolver")
	private UrlResolver<CategoryModel> categoryModelUrlResolver;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;
	
	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@Resource(name = "b2bCustomerFacade")
	protected CustomerFacade customerFacade;
	

	@RequestMapping(value = CATEGORY_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String category(@PathVariable("categoryCode") final String categoryCode,
			@RequestParam(value = "q", required = false) String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException
	{
		final CategoryModel category = commerceCategoryService.getCategoryForCode(categoryCode);

		final String redirection = checkRequestUrl(request, response, categoryModelUrlResolver.resolve(category));
		if (StringUtils.isNotEmpty(redirection))
		{
			return redirection;
		}
		
		if ((searchQuery == null || searchQuery.isEmpty())) 
		{
			searchQuery = ControllerConstants.ProductFacetSelection.FACET_DESCRIPTION_ORDER_ASC;
			
			if(neorisFacade.isFacetAutoSelectionEnabledForCurrentUser())
			{
				searchQuery += ControllerConstants.ProductFacetSelection.FACET_MATERIALTYPE_AUTOSELECTION_SEARCH_QUERY;				
			}
			
			//CILS 04Oct2016 Add query location
			
			//*******************************
			BaseStoreModel currentBaseStoreModel = neorisFacade.getCurrentBaseStore();
			
			if(currentBaseStoreModel.isAllowCategoryVisibility())
			{
				B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
				String ABC = null;
				if(currentCustomerModel != null)
				{
					if(currentCustomerModel.getCurrentLocation() == null)
					{
						//ABC = neorisFacade.getCurrentLocationABC();
						//System.out.println("ABC1 " + ABC);
						
						//Si es null la locacion, se redirecciona al Home
						return REDIRECT_PREFIX + "/";

					}else
					{
						String sessionActual = sessionService.getCurrentSession().getSessionId();
						System.out.println("sessionActual-CategoryPageController: " +sessionActual);
						System.out.println("currentCustomerModel.getCurrentLocation()-CategoryPageController: " +currentCustomerModel.getCurrentLocation());
						String[] splitter = currentCustomerModel.getCurrentLocation().toString().split("-");
						
						//if(splitter[1].toString().equalsIgnoreCase(sessionActual))
						//{
							ABC = splitter[0].toString();
							System.out.println("ABC2 " + ABC);
						//}else
						if(ABC == null)
						{
							if(currentBaseStoreModel.getActivateFunctionalityLocationDefault())
							{
								if(currentBaseStoreModel.getUid().equalsIgnoreCase("1000"))
								{
									ABC = "_1100"; //neorisFacade.getCurrentLocationABC();
								}else
								{
									ABC = "_2200"; //neorisFacade.getCurrentLocationABC();
								}
							//ABC = "_1100"; //neorisFacade.getCurrentLocationABC();
							System.out.println("ABC3 " + ABC);	
							}else
							{
								final CustomerData customerData = customerFacade.getCurrentCustomer();
								model.addAttribute("customerData", customerData);
								
								try {
									storeCmsPageInModel(model, getContentPageForLabelOrId("profile"));
								} catch (CMSItemNotFoundException e) {
									// YTODO Auto-generated catch block
									e.printStackTrace();
								}
								try {
									setUpMetaDataForContentPage(model, getContentPageForLabelOrId("profile"));
								} catch (CMSItemNotFoundException e) {
									// YTODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile"));
								model.addAttribute("metaRobots", "no-index,no-follow");
								return ControllerConstants.Views.Pages.Account.AccountProfilePage;
							}
						}
					}
				}else
				{
					//NADA
				}
				if(ABC != null)
				{
					String locacionDefault = ControllerConstants.ProductFacetSelection.FACET_LOCATION_AUTOSELECTION_SEARCH_QUERY + ABC;
					searchQuery += locacionDefault;	
				}	
			}else
			{
				//NADA
			}
			
			//*******************************
			
			
			//17Mzo2017 CILS ==INICIO
			if(baseStoreService.getCurrentBaseStore().getActivateDefaultSettingLocation())
			{
				NeorisB2BCustomerDefaultSettingModel defaultSettings = neorisFacade.getDefaultSettings();
				if(defaultSettings != null && defaultSettings.getLocation() != null)
				{
					searchQuery += ControllerConstants.ProductFacetSelection.FACET_LOCATION_AUTOSELECTION_SEARCH_QUERY + defaultSettings.getLocation().getName();
				}
			}
			//==FIN
		}
		
		CategoryPageModel categoryPage = getCategoryPage(category);

		final ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData;
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		if (searchQueryData.getValue() == null)
		{
			// Direct category link without filtering
			searchPageData = productSearchFacade.categorySearch(categoryCode);
		}
		else
		{
			// We have some search filtering
			if (categoryPage == null || Boolean.FALSE.equals(categoryPage.getDefaultPage()))
			{
				// Load the default category page
				categoryPage = getDefaultCategoryPage();
			}

			final SearchStateData searchState = new SearchStateData();
			searchState.setQuery(searchQueryData);

			final PageableData pageableData = createPageableData(page, 0, sortCode, showMode);
			searchPageData = productSearchFacade.categorySearch(categoryCode, searchState, pageableData);
		}

		// NEORIS_CHANGE #38
		neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());

		// NEORIS_CHANGE #75
		neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());

		//NEORIS_CHANGE #55
		neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());
		
		
		storeCmsPageInModel(model, categoryPage);
		storeContinueUrl(request);

		final boolean showCategoriesOnly = searchQueryData.getValue() == null && categoryPage != null
				&& Boolean.FALSE.equals(categoryPage.getDefaultPage()) && searchPageData.getSubCategories() != null
				&& !searchPageData.getSubCategories().isEmpty();

		populateModel(model, searchPageData, showMode);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(categoryCode, searchPageData));
		model.addAttribute("showCategoriesOnly", Boolean.valueOf(showCategoriesOnly));
		model.addAttribute("pageType", PageType.CATEGORY.name());
		model.addAttribute("userLocation", customerLocationService.getUserLocation());
		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		updatePageTitle(category, searchPageData.getBreadcrumbs(), model);
		final RequestContextData requestContextData = getRequestContextData(request);
		requestContextData.setCategory(category);
		requestContextData.setSearch(searchPageData);

		if (searchQueryData.getValue() != null)
		{
			model.addAttribute("metaRobots", "no-index,follow");
		}
		
		// add flag to include rate js library for product favorites
		model.addAttribute("activateJQRateFronEndLibraries", true);

		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(category.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		return getViewPage(categoryPage);
	}

	@RequestMapping(value = "/{categoryCode}/results", method = RequestMethod.GET)
	public String searchResults(@PathVariable("categoryCode") final String categoryCode,
			@RequestParam("q") final String searchQuery, @RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{

		final ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = performSearch(
				categoryCode, searchQuery, page, showMode, sortCode, getSearchPageSize());
		final SearchResultsData<ProductData> searchResultsData = new SearchResultsData<ProductData>();
		searchResultsData.setResults(searchPageData.getResults());
		searchResultsData.setPagination(searchPageData.getPagination());

		model.addAttribute("searchResultsData", searchResultsData);

		return ControllerConstants.Views.Fragments.Product.ProductLister;
	}

	protected ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> performSearch(final String categoryCode,
			final String searchQuery, final int page, final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return productSearchFacade.categorySearch(categoryCode, searchState, pageableData);
	}

	protected CategoryPageModel getCategoryPage(final CategoryModel category)
	{
		try
		{
			return getCmsPageService().getPageForCategory(category);
		}
		catch (final CMSItemNotFoundException ignore)
		{
			// Ignore
		}
		return null;
	}

	protected CategoryPageModel getDefaultCategoryPage()
	{
		try
		{
			return getCmsPageService().getPageForCategory(null);
		}
		catch (final CMSItemNotFoundException ignore)
		{
			// Ignore
		}
		return null;
	}

	protected <QUERY> void updatePageTitle(final CategoryModel category, final List<BreadcrumbData<QUERY>> appliedFacets,
			final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveCategoryPageTitle(category, appliedFacets));
	}

	protected String getViewPage(final CategoryPageModel categoryPage)
	{
		if (categoryPage != null)
		{
			final String targetPage = getViewForPage(categoryPage);
			if (targetPage != null && !targetPage.isEmpty())
			{
				return targetPage;
			}
		}
		return PAGE_ROOT + PRODUCT_GRID_PAGE;
	}

	@ExceptionHandler(UnknownIdentifierException.class)
	public String handleUnknownIdentifierException(final UnknownIdentifierException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
	}
}
