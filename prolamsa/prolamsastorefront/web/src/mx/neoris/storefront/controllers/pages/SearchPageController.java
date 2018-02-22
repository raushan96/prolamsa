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

import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.AutocompleteResultData;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import mx.neoris.core.commercefacades.search.NeorisProductSearchFacade;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.search.B2BProductSearchFacade;
import mx.neoris.storefront.breadcrumb.impl.SearchBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.forms.AdvancedSearchForm;
import mx.neoris.storefront.util.MetaSanitizerUtil;
import mx.neoris.storefront.util.XSSFilterUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for search page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/search")
public class SearchPageController extends AbstractSearchPageController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SearchPageController.class);

	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER = "storefront.advancedsearch.delimiter";
	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER_DEFAULT = ",";

	private static final String COMPONENT_UID_PATH_VARIABLE_PATTERN = "{componentUid:.*}";

	private static final String ADVANCED_SEARCH_RESULT_TYPE_CATALOG = "catalog";
	private static final String ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM = "order-form";

	private static final String SEARCH_CMS_PAGE_ID = "search";
	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";

	private static final String NO_RESULTS_ADVANCED_PAGE_ID = "searchAdvancedEmpty";

	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";

	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	@Resource(name = "productSearchFacade")
	private NeorisProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "b2bProductFlexibleSearchFacade")
	private B2BProductSearchFacade<ProductData> b2bProductFlexibleSearchFacade;

	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;
	

	@RequestMapping(method = RequestMethod.GET, params = "!q")
	public String textSearch(@RequestParam(value = "text", defaultValue = StringUtils.EMPTY)
	final String searchText, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		Integer productPageSize = getSiteConfigService().getInt("product.search.pageSize", 18);

		if (StringUtils.isNotBlank(searchText))
		{
			// search using flexible search
			// NEORIS_CHANGE #55
			// convert the client SKU to Prolamsa SKU
			ProductSearchPageData<SearchStateData, ProductData> searchPageData = createEmptySearchPageData();
			final List<String> productIdsList = splitSkusAsList(searchText);
			final PageableData pageableData = createPageableData(0, productPageSize, null, ShowMode.Page);
			
			//Christian Loredo 12022015
			//Add parameter CatlogId to search CustomerProductReference
			String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";

			// call facade to get the real skus
			final List<String> productProlamsaList = neorisFacade.getProlamsaSkuFromSku(productIdsList, neorisFacade.getRootUnit(), catalogId);
			final SearchStateData searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			
			String facetAutoSelection = ControllerConstants.ProductFacetSelection.FACET_DESCRIPTION_ORDER_ASC;
			
			if (productProlamsaList.get(0).equals(""))
			{
				searchQueryData.setValue(XSSFilterUtil.filter(searchText) + facetAutoSelection);
			}
			else
			{
				searchQueryData.setValue(XSSFilterUtil.filter(productProlamsaList.get(0) + facetAutoSelection));
			}

			searchState.setQuery(searchQueryData);
/*			searchPageData = b2bProductFlexibleSearchFacade.searchForSkus(productProlamsaList, pageableData);

			// check if exist any SKU in the search, if not go the text search
			if (searchPageData.getResults().size() > 0)
			{
				populateModel(model, searchPageData, ShowMode.Page);
				ProductData pd = searchPageData.getResults().get(0);
				String url = pd.getUrl();
				// if the search engine returns a redirect, just
				return "redirect:" + url;
			}
			else
			{
				searchPageData = productSearchFacade.textSearch(searchState, pageableData);
			}
*/
			searchPageData = productSearchFacade.textSearch(searchState, pageableData);

			populateModel(model, searchPageData, ShowMode.Page);

			// END NEORIS_CHANGE #55

			if (searchPageData == null)
			{
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
			}
			else
				if (searchPageData.getKeywordRedirectUrl() != null)
				{
					// if the search engine returns a redirect, just
					return "redirect:" + searchPageData.getKeywordRedirectUrl();
				}
				else
					// NEORIS_CHANGE, include condition to check if there are no real results
					if (searchPageData.getPagination().getTotalNumberOfResults() == 0 || searchPageData.getResults().size() == 0)
					{
						model.addAttribute("searchPageData", searchPageData);
						storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
						updatePageTitle(searchText, model);
					}
					else
					{
						// NEORIS_CHANGE #38
						neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());
						// NEORIS_CHANGE #75
						neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());
						// NEORIS_CHANGE #55
						neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());

						storeContinueUrl(request);
						populateModel(model, searchPageData, ShowMode.Page);
						storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
						updatePageTitle(searchText, model);
					}
			getRequestContextData(request).setSearch(searchPageData);
			model.addAttribute(
					WebConstants.BREADCRUMBS_KEY,
					searchBreadcrumbBuilder.getBreadcrumbs(null, searchText,
							CollectionUtils.isEmpty(searchPageData.getBreadcrumbs())));
		}
		else
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		}
		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		
		addMetaData(model, "search.meta.description.results", searchText, "search.meta.description.on", PageType.PRODUCTSEARCH,
				"no-index,follow");
		
		// add flag to include rate js library for product favorites
		model.addAttribute("activateJQRateFronEndLibraries", true);

		return getViewForPage(model);
	}

	@RequestMapping(method = RequestMethod.GET, params = "q")
	public String refineSearch(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "text", required = false)
	final String searchText, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performSearch(searchQuery, page, showMode,
				sortCode, getSearchPageSize());

		populateModel(model, searchPageData, showMode);
		model.addAttribute("userLocation", customerLocationService.getUserLocation());

		if (searchPageData.getPagination().getTotalNumberOfResults() == 0)
		{
			updatePageTitle(searchPageData.getFreeTextSearch(), model);
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		}
		else
		{
			// NEORIS_CHANGE #38
			neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());
			// NEORIS_CHANGE #75
			neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());
			// NEORIS_CHANGE #55
			neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());

			storeContinueUrl(request);
			updatePageTitle(searchPageData.getFreeTextSearch(), model);
			storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
		}
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(null, searchPageData));
	// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		addMetaData(model, "search.meta.description.results", searchText, "search.meta.description.on", PageType.PRODUCTSEARCH,
				"no-index,follow");
		
		// add flag to include rate js library for product favorites
		model.addAttribute("activateJQRateFronEndLibraries", true);

		return getViewForPage(model);
	}

	protected ProductSearchPageData<SearchStateData, ProductData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return productSearchFacade.textSearch(searchState, pageableData);
	}

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String productListerSearchResults(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "searchResultType", required = false)
	final String searchResultType, @RequestParam(value = "skuIndex", required = false, defaultValue = "0")
	final int skuIndex, @RequestParam(value = "isOrderForm", required = false, defaultValue = "false")
	final boolean isOrderForm, @RequestParam(value = "isOnlyProductIds", required = false, defaultValue = "false")
	final boolean isOnlyProductIds, @RequestParam(value = "isCreateOrderForm", required = false, defaultValue = "false")
	final boolean isCreateOrderForm, final Model model) throws CMSItemNotFoundException
	{

		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performAdvancedSearch(searchQuery,
				isOnlyProductIds, isCreateOrderForm, page, showMode, sortCode, model);

		final SearchResultsData<ProductData> searchResultsData = new SearchResultsData<ProductData>();

		searchResultsData.setResults(searchPageData.getResults());
		searchResultsData.setPagination(searchPageData.getPagination());

		model.addAttribute("searchResultsData", searchResultsData);
		model.addAttribute("skuIndex", Integer.valueOf(skuIndex));
		model.addAttribute("isOrderForm", Boolean.valueOf(isOrderForm));
		model.addAttribute("isCreateOrderForm", Boolean.valueOf(isCreateOrderForm));

		if (isCreateOrderForm)
		{
			model.addAttribute("searchResultType", ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM);
			final List<String> filterSkus = splitSkusAsList(searchQuery);
			model.addAttribute("filterSkus", filterSkus);
		}
		else
		{
			model.addAttribute("searchResultType", searchResultType);
		}
		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		return ControllerConstants.Views.Fragments.Product.ProductLister;
	}

	@RequestMapping(value = "/advanced", method = RequestMethod.GET)
	public String advanceSearchResults(@RequestParam(value = "keywords", required = false, defaultValue = StringUtils.EMPTY)
	String keywords,
			@RequestParam(value = "searchResultType", required = false, defaultValue = ADVANCED_SEARCH_RESULT_TYPE_CATALOG)
			final String searchResultType, @RequestParam(value = "inStockOnly", required = false, defaultValue = "false")
			final boolean inStockOnly, @RequestParam(value = "onlyProductIds", required = false, defaultValue = "false")
			final boolean onlyProductIds, @RequestParam(value = "isCreateOrderForm", required = false, defaultValue = "false")
			final boolean isCreateOrderForm, @RequestParam(value = "q", defaultValue = StringUtils.EMPTY)
			String searchQuery, @RequestParam(value = "page", defaultValue = "0")
			final int page, @RequestParam(value = "show", defaultValue = "Page")
			final ShowMode showMode, @RequestParam(value = "sort", required = false)
			final String sortCode, final Model model) throws CMSItemNotFoundException
	{

		if (StringUtils.isNotBlank(keywords))
		{
			searchQuery = keywords;
		}
		else
		{
			if (StringUtils.isNotBlank(searchQuery))
			{
				keywords = StringUtils.split(searchQuery, ":")[0];
			}
		}

		// NEORIS_CHANGE, enforce the ShowMode all to show all results from the OrderForm
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = performAdvancedSearch(searchQuery, onlyProductIds, isCreateOrderForm, page, ShowMode.All /*showMode*/, sortCode, model);
		
		//Added code to order the array results if the search comes from order form
		if(onlyProductIds)
		{
			List<ProductData> orderedProducts = new ArrayList<ProductData>();
			
			final List<String> productIdsList = splitSkusAsList(keywords);
			
			Map<String,String> uniqueIdMap = new LinkedHashMap<String,String>();
			for(String eachId: productIdsList)
			{
				uniqueIdMap.put(eachId, eachId);
			}
			for(String eachId: uniqueIdMap.keySet())
			{
				for (ProductData eachProduct : searchPageData.getResults())
				{
					if(eachProduct.getBaseCode().equals(eachId))
					{
						orderedProducts.add(eachProduct);
					}
				}
			}
			if(orderedProducts.size()>0)
				searchPageData.setResults(orderedProducts);
		}
		
						

		String metaInfoText = null;
		if (StringUtils.isEmpty(keywords))
		{
			metaInfoText = MetaSanitizerUtil.sanitizeDescription(getMessageSource().getMessage(
					"search.advanced.meta.description.title", null, getCurrentLocale()));
		}
		else
		{
			metaInfoText = MetaSanitizerUtil.sanitizeDescription(keywords);
		}

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(null, metaInfoText, false));

		final AdvancedSearchForm form = new AdvancedSearchForm();
		form.setOnlyProductIds(Boolean.valueOf(onlyProductIds));
		form.setInStockOnly(Boolean.valueOf(inStockOnly));
		form.setKeywords(keywords);
		form.setCreateOrderForm(isCreateOrderForm);

		if (isCreateOrderForm)
		{
			form.setSearchResultType(ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM);
			final List<String> filterSkus = splitSkusAsList(keywords);
			form.setFilterSkus(filterSkus);
			form.setCreateOrderForm(Boolean.valueOf(false));
			form.setOnlyProductIds(Boolean.valueOf(true));
		}
		else
		{
			form.setSearchResultType(searchResultType);
		}

		model.addAttribute("advancedSearchForm", form);
		model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));

		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));

		addMetaData(model, "search.meta.description.results", metaInfoText, "search.meta.description.on",
				PageType.PRODUCTSEARCH, "no-index,follow");
		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		
		
		return getViewForPage(model);
	}

	protected ProductSearchPageData<SearchStateData, ProductData> performAdvancedSearch(final String keywords,
			final boolean onlyProductIds, final boolean isCreateOrderForm, final int page, final ShowMode showMode,
			final String sortCode, final Model model)
	{
		Integer productPageSize = getSiteConfigService().getInt("product.search.pageSize", 18);

		ProductSearchPageData<SearchStateData, ProductData> searchPageData = createEmptySearchPageData();

		if (StringUtils.isNotBlank(keywords))
		{
			if (onlyProductIds || isCreateOrderForm)
			{
				// search using flexible search
				final List<String> productIdsList = splitSkusAsList(keywords);
				final PageableData pageableData = createPageableData(page, productPageSize, sortCode, showMode);

				//Christian Loredo 12022015
				//Add parameter CatlogId to search CustomerProductReference
				String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";
				
				// call facade to get the real skus
				// NEORIS_CHANGE #55
				final List<String> productProlamsaList = neorisFacade.getProlamsaSkuFromSku(productIdsList, neorisFacade.getRootUnit(), catalogId);
				List<String> productProlamsaListEdit = new ArrayList<String>(productProlamsaList);
				List<String> listNotFound = new ArrayList<String>();
				
				//CLS 15032016
				for(int m=0; m<productProlamsaList.size(); m++)
				{
					if(productProlamsaList.get(m).split(" ").length > 1)
					{
						listNotFound.add(productProlamsaList.get(m));
						productProlamsaListEdit.remove(m);
					}
				}
				
				SearchStateData searchState = new SearchStateData();
				
				if(productProlamsaListEdit.size() == 0)
				{
					populateModel(model, searchPageData, showMode);

					return searchPageData;
				}
				
				searchPageData = productSearchFacade.skusSearch(productProlamsaListEdit, searchState, pageableData);
				//searchPageData = b2bProductFlexibleSearchFacade.searchForSkus(productProlamsaList, pageableData);

				List<ProductData> listProduct = searchPageData.getResults();
				//List<String> listNotFound = new ArrayList<String>();
				
				//Aqui se removeran del searchPageData los productos con las locaciones que no corresponden al combo superior
				//CILS 08Dic2016 Se obtiene la locacion del combo superior
				String enumValueString = neorisFacade.getLocationSlot();
				ArrayList<ProductData> productosRemover = new ArrayList<ProductData>();
				
				if(enumValueString != null)
				{
					for (int z = 0; z < searchPageData.getResults().size(); z++)
					{
						if(!searchPageData.getResults().get(z).getLocation().getCode().equalsIgnoreCase(enumValueString))
						{
							productosRemover.add(searchPageData.getResults().get(z));
						}
					}
				}
				searchPageData.getResults().removeAll(productosRemover);

				// identify which skus where not found and add them to the list
				if(searchPageData.getResults().isEmpty())
				{
					listNotFound = new ArrayList<String>(productProlamsaList);
				}
				else{
					Boolean addList = false;
					for (int i = 0; i < productProlamsaListEdit.size(); i++)
					{
						addList = false;
						//for (ProductData data : listProduct)
						for (ProductData data : searchPageData.getResults())
						{
							// compare to base code and not the full product code
							if (!data.getBaseCode().equals(productProlamsaListEdit.get(i)))
							{
								addList = true;
							}
							else
							{
								addList = false;
								break;
							}
						}
						if (addList)
						{
							listNotFound.add(productProlamsaListEdit.get(i));
						}
					}
				}
				
				model.addAttribute("numberSKU", listNotFound.size());
				model.addAttribute("listNotFound", Arrays.toString(listNotFound.toArray()).replaceAll("[\\[\\]]", ""));
				
				// NEORIS_CHANGE #38
				neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());
				// NEORIS_CHANGE #75
				neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());
				// NEORIS_CHANGE #55
				neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());
			}
			else
			{
				// search using solr.
				searchPageData = performSearch(keywords, page, showMode, sortCode, getSearchPageSize());
			}

		}
		
		
		populateModel(model, searchPageData, showMode);

		return searchPageData;
	}

	private ProductSearchPageData<SearchStateData, ProductData> createEmptySearchPageData()
	{
		final ProductSearchPageData<SearchStateData, ProductData> productSearchPageData = new ProductSearchPageData<SearchStateData, ProductData>();

		productSearchPageData.setResults(new ArrayList<ProductData>());
		final PaginationData pagination = new PaginationData();
		pagination.setTotalNumberOfResults(0);
		productSearchPageData.setPagination(pagination);
		productSearchPageData.setSorts(new ArrayList<SortData>());

		return productSearchPageData;
	}

	protected List<String> splitSkusAsList(final String skus)
	{
		return Arrays.asList(StringUtils.split(skus.toUpperCase(),
				Config.getString(ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER, ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER_DEFAULT)));
	}

	private Locale getCurrentLocale()
	{
		return getI18nService().getCurrentLocale();
	}

	@ResponseBody
	@RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
	public List<String> getAutocompleteSuggestions(@RequestParam("term")
	final String term)
	{
		final List<String> terms = new ArrayList<String>();
		for (final AutocompleteSuggestionData termData : productSearchFacade.getAutocompleteSuggestions(term))
		{
			terms.add(termData.getTerm());
		}
		return terms;
	}

	@ResponseBody
	@RequestMapping(value = "/autocomplete/" + COMPONENT_UID_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public AutocompleteResultData getAutocompleteSuggestions(@PathVariable
	final String componentUid, @RequestParam("term")
	final String term) throws CMSItemNotFoundException
	{
		final AutocompleteResultData resultData = new AutocompleteResultData();

		final SearchBoxComponentModel component = (SearchBoxComponentModel) cmsComponentService
				.getSimpleCMSComponent(componentUid);

		if (component.isDisplaySuggestions())
		{
			resultData.setSuggestions(subList(productSearchFacade.getAutocompleteSuggestions(term),
					component.getMaxSuggestions()));
		}

		if (component.isDisplayProducts())
		{
			resultData.setProducts(subList(removedProductsWithDuplicatedBaseCodes(productSearchFacade.textSearch(term).getResults()), component.getMaxProducts()));
//			resultData.setProducts(subList(productSearchFacade.textSearch(term).getResults(), component.getMaxProducts()));
		}

		return resultData;
	}
	
	protected List<ProductData> removedProductsWithDuplicatedBaseCodes(List<ProductData> searchResult)
	{
		List<ProductData> cleanedSearchResult = new ArrayList<ProductData>();
		
		for(ProductData eachProduct : searchResult)
		{
			String baseCode = eachProduct.getBaseCode();
			boolean isBaseCodeDuplicated = false;
			
			for(ProductData eachProduct1 :  cleanedSearchResult)
			{
				if(baseCode.equals(eachProduct1.getBaseCode()))
				{
					isBaseCodeDuplicated = true;
					break;
				}
			}
			
			if(!isBaseCodeDuplicated)
			{
				cleanedSearchResult.add(eachProduct);
			}
		}
				
		return cleanedSearchResult;
	}

	protected <E> List<E> subList(final List<E> list, final int maxElements)
	{
		if (CollectionUtils.isEmpty(list))
		{
			return Collections.emptyList();
		}

		if (list.size() > maxElements)
		{
			return list.subList(0, maxElements);
		}

		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/autocompleteSecure", method = RequestMethod.GET)
	public List<String> getAutocompleteSuggestionsSecure(@RequestParam("term")
	final String term)
	{
		return getAutocompleteSuggestions(term);
	}

	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(
				model,
				getPageTitleResolver().resolveContentPageTitle(
						getMessageSource().getMessage("search.meta.title", null, getCurrentLocale()) + " " + searchText));
	}

	private void addMetaData(final Model model, final String metaPrefixKey, final String searchText,
			final String metaPostfixKey, final PageType pageType, final String robotsBehaviour)
	{
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(getMessageSource().getMessage(metaPrefixKey, null,
				getCurrentLocale())
				+ " "
				+ searchText
				+ " "
				+ getMessageSource().getMessage(metaPostfixKey, null, getCurrentLocale())
				+ " "
				+ getSiteName());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(searchText);
		setUpMetaData(model, metaKeywords, metaDescription);

		model.addAttribute("pageType", pageType.name());
		model.addAttribute("metaRobots", robotsBehaviour);
	}
}
