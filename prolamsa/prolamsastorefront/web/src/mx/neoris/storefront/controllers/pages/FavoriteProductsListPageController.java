package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.core.commercefacades.search.NeorisProductSearchFacade;
import de.hybris.platform.store.BaseStoreModel;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.services.FavoriteProductSearchParameters;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessage;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.ProductSearchForm;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope("tenant")
@RequestMapping("/favorite-products/list")
public class FavoriteProductsListPageController extends FavoriteProductsAbstractPageController
{
	private static final Logger LOG = Logger.getLogger(FavoriteProductsListPageController.class);
	
	private static final String CUSTOMERS_FAVORITE_CUSTOMERS_LIST_CMS_PAGE = "CustomerFavoriteProductsListPage";
	
	public static final String PRODCUT_CATALOG = "ProductCatalog";
	public static final String CATALOG_VERSION = "Online";
	
	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;
	
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name = "productSearchFacade")
	private NeorisProductSearchFacade<ProductData> productSearchFacade;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "b2bCustomerFacade")
	protected CustomerFacade customerFacade;
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String favoriteProductsList(
			@RequestParam(value = "q", required = false) String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{		
		List<String> favoriteProductCodes = null;
		FavoriteProductSearchParameters searchParameters = buildSearchParameters(model, page, showMode, sortCode, false);
		
		try 
		{
			favoriteProductCodes = neorisFavoriteProductFacade.getFavoriteProductCodes(searchParameters);
		} 
		catch (Exception e) 
		{
			LOG.error("Error while getting favorite product codes due to: " + e.getMessage());
			
			GlobalMessages.addErrorMessage(model, "text.account.customerFavoriteProducts.list.general.error");
			
			storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMERS_FAVORITE_CUSTOMERS_LIST_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CUSTOMERS_FAVORITE_CUSTOMERS_LIST_CMS_PAGE));
			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.customerFavoriteProductsList"));
			model.addAttribute("metaRobots", "no-index,no-follow");

			return getViewForPage(model);
		}
		
		// facet auto-selection
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
		
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = createEmptySearchPageData();
		
		if(CollectionUtils.isNotEmpty(favoriteProductCodes))
		{				
			SearchStateData searchState = new SearchStateData();
			searchState.setQuery(searchQueryData);
			
			// search product favorites using solr to get facet information
			searchPageData = productSearchFacade.codesSearch(favoriteProductCodes, searchState, searchParameters.getPageableData());
			
			// change url from searchPage to favorite prods
			searchPageData.getCurrentQuery().setUrl(searchPageData.getCurrentQuery().getUrl().replace("search", "favorite-products/list"));
			
			neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());
			neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());				
			neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());
		}
		
		populateModel(model, searchPageData, showMode);
						
		model.addAttribute("userLocation", customerLocationService.getUserLocation());
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMERS_FAVORITE_CUSTOMERS_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CUSTOMERS_FAVORITE_CUSTOMERS_LIST_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.customerFavoriteProductsList"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
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
