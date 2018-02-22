package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.commercefacades.search.NeorisProductSearchFacade;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.IncidentTypeModel;
import mx.neoris.core.services.FavoriteProductSearchParameters;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.ProductSearchForm;
import mx.neoris.storefront.util.XSSFilterUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("tenant")
@RequestMapping("/favorite-products/manage")
public class FavoriteProductsManagePageController extends FavoriteProductsAbstractPageController
{
	private static final Logger LOG = Logger.getLogger(FavoriteProductsManagePageController.class);
	
	private static final String CUSTOMERS_FAVORITE_CUSTOMERS_MANAGE_CMS_PAGE = "CustomerFavoriteProductsManagePage";
	private static final String LOCATION_FACET_NAME=":description-asc:location:";
	
	private static final String EDIT_PRODUCT_LIST_ADD = "add";
	private static final String EDIT_PRODUCT_LIST_REMOVE = "remove";
	
	public static final String POPUP_PAGE = "pages/favoriteProducts/favoriteProductsLocationsPopup";
	
	public static final int MAX_PAGE_LIMIT=20000;
	
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name = "productSearchFacade")
	private NeorisProductSearchFacade<ProductData> productSearchFacade;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String favoriteProductsManage(
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode, 
			final Model model,
			final ProductSearchForm favoriteProductSearchForm,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		String sortCode = (favoriteProductSearchForm != null) ? favoriteProductSearchForm.getSort() : null;
		
		populateFavoriteProducts(model, page, showMode, sortCode, false);
		
		if (!model.containsAttribute("favoriteProductSearchForm"))
		{
			ProductSearchForm newFavoriteProductSearchForm = new ProductSearchForm();
			if(sortCode != null)
			{
				newFavoriteProductSearchForm.setSort(sortCode);
			}
			model.addAttribute("favoriteProductSearchForm", newFavoriteProductSearchForm);
		}
		
		ProductSearchForm productSearchForm = new ProductSearchForm();
		model.addAttribute("productSearchForm", productSearchForm);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMERS_FAVORITE_CUSTOMERS_MANAGE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CUSTOMERS_FAVORITE_CUSTOMERS_MANAGE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.customerFavoriteProductsManage"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/reset", method = { RequestMethod.POST })
	public String resetFavoriteProducts(
			final Model model,
			final String location,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{	
		try 
		{
			String newFavoriteProductsAdded = neorisFavoriteProductFacade.resetFavoriteProducts(location);
			
			GlobalMessages.addMessage(model,
					GlobalMessages.INFO_MESSAGES_HOLDER,
					"text.account.customerFavoriteProducts.reset.success",
					new Object[]{newFavoriteProductsAdded});
		} 
		catch (Exception e) 
		{
			LOG.error("Error while reseting customer product favorites due to: " + e.getMessage());
			
			GlobalMessages.addMessage(model,
					GlobalMessages.ERROR_MESSAGES_HOLDER, 
					"text.account.customerFavoriteProducts.reset.general.error",
					null);
		}
		
		populateFavoriteProducts(model, 0, ShowMode.Page, null, false);
		return ControllerConstants.Views.Fragments.FavoriteProducts.FavoriteProductsManageList;
	}
	
	@RequestMapping(value = "/remove", method = { RequestMethod.POST })
	public String removeProductFromFavorites(
			final Model model,
			final String productCode,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		String errorMessageKey = neorisFavoriteProductFacade
				.removeFavoriteProduct(productCode);
		
		if (errorMessageKey == null) {
			GlobalMessages.addMessage(model,
					GlobalMessages.INFO_MESSAGES_HOLDER,
					"text.account.customerFavoriteProducts.remove.success",
					new Object[] { productCode });
		} else {
			GlobalMessages.addMessage(model,
					GlobalMessages.ERROR_MESSAGES_HOLDER, errorMessageKey,
					new Object[] { productCode });
		}
		
		populateFavoriteProducts(model, 0, ShowMode.Page, null, false);
		return ControllerConstants.Views.Fragments.FavoriteProducts.FavoriteProductsManageList;
	}
	
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public String addProductToFavorites(
			final Model model,
			final String productCode,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		String errorMessageKey = neorisFavoriteProductFacade
				.addFavoriteProduct(productCode);
		
		if (errorMessageKey == null) {
			GlobalMessages.addMessage(model,
					GlobalMessages.INFO_MESSAGES_HOLDER,
					"text.account.customerFavoriteProducts.add.success",
					new Object[] { productCode });
		} else {
			GlobalMessages.addMessage(model,
					GlobalMessages.ERROR_MESSAGES_HOLDER, errorMessageKey,
					new Object[] { productCode });
		}
		
		populateFavoriteProducts(model, 0, ShowMode.Page, null, false);
		return ControllerConstants.Views.Fragments.FavoriteProducts.FavoriteProductsManageList;
	}
	
	@RequestMapping(value = "/edit-product-list", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object editProductList(
			final Model model,
			final String productCode,
			final String action,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		
		String errorMessageKey = null;
		
		if (EDIT_PRODUCT_LIST_ADD.equals(action))
		{
			errorMessageKey = neorisFavoriteProductFacade
			.addFavoriteProduct(productCode);
		}
		else
		{
			errorMessageKey = neorisFavoriteProductFacade
					.removeFavoriteProduct(productCode);
		}
		
		if (errorMessageKey == null)
		{
			node.put("message", "OK");
			node.put("status", AJAX_STATUS_OK);
		}
		else
		{
			node.put("status", AJAX_STATUS_ERROR);
		}
		
		return node;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object searchFavoriteCandidatesProducts(
			@RequestParam(value = "page", defaultValue = "0")
			final int page, @Valid
			final ProductSearchForm productSearchForm, final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		
		FavoriteProductSearchParameters searchParameters = new FavoriteProductSearchParameters();
		searchParameters.setCode(productSearchForm.getCode());
		searchParameters.setDescription(productSearchForm.getDescription());
		
		ProductLocation productLocation = null;
		if (productSearchForm.getLocation() != null && !productSearchForm.getLocation().isEmpty()) 
		{
			productLocation = ProductLocation.valueOf(productSearchForm.getLocation());
		}
		searchParameters.setLocation(productLocation);
		
		B2BUnitModel customer = neorisFacade.getRootUnit();
		searchParameters.setCustomer(customer);
		
		final String catalogId = neorisFacade.getCurrentBaseStore().getUid() + PRODCUT_CATALOG;
		CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(catalogId, CATALOG_VERSION);
		searchParameters.setCatalogVersion(catalogVersion);
		
		List<ProductData> searchResult = new ArrayList<ProductData>();
		
		try 
		{
			String searchText = productSearchForm.getDescription();
			
			ProductSearchPageData<SearchStateData, ProductData> searchPageData = createEmptySearchPageData();
			
			final PageableData pageableData = createPageableData(0, MAX_PAGE_LIMIT, null, ShowMode.All);
			pageableData.setPageSize(MAX_PAGE_LIMIT);
			final SearchStateData searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			
			String formLocation = productSearchForm.getLocation();
			String facetLocationValue = new String();
			if(StringUtils.isNotBlank(formLocation))
			{
				facetLocationValue = LOCATION_FACET_NAME +formLocation;
			}
			
			if (StringUtils.isNotBlank(searchText))
			{
					searchQueryData.setValue(XSSFilterUtil.filter(searchText) + facetLocationValue);
			}else
			{
				searchQueryData.setValue(""+facetLocationValue);
			}
			
			searchState.setQuery(searchQueryData);
			
			searchPageData = productSearchFacade.textSearch(searchState, pageableData);
			
			searchResult = searchPageData.getResults();
//			searchResult = neorisFavoriteProductFacade.searchFavoriteCandidateProduct(searchParameters);
			//neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchResult);
		} 
		catch (Exception e) 
		{
			LOG.error("Error while searching favorite candidate products, due to: " + e.getMessage());
			
			node.put("message", getMessageWithDefaultContext("text.account.customerFavoriteProducts.search.general.error"));
			node.put("status", AJAX_STATUS_ERROR);
			
			return node;  
		}
		
		for (final ProductData productData : searchResult)
		{
			ObjectNode product = JsonNodeFactory.instance.objectNode();
			
			product.put("code", productData.getCode());
			product.put("baseCode", productData.getBaseCode());
			product.put("description", productData.getDescription());
			product.put("name", productData.getName());
			
			String location = getMessageWithDefaultContext(productData.getLocation().getCode());
			product.put("location", location);
			
			result.add(product);
		}

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);
		node.put("products", result);
		
		return node;
	}
	
	@ModelAttribute("productLocations")
	public List<PointOfServiceModel> getProductLocations()
	{
		return baseStoreService.getCurrentBaseStore().getPointOfServices();
	}
	
	@RequestMapping(value = "/product-location-popup", method = RequestMethod.GET)
	public String showShippingInstructionsPopup(final Model model, final HttpServletRequest request)
	{
		return POPUP_PAGE;
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
}
