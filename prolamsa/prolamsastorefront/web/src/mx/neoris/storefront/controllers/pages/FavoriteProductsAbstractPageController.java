package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.services.FavoriteProductSearchParameters;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFavoriteProductFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.util.GlobalMessages;

import org.springframework.ui.Model;

public abstract class FavoriteProductsAbstractPageController extends AbstractSearchPageController
{	
	public static final String PRODCUT_CATALOG = "ProductCatalog";
	public static final String CATALOG_VERSION = "Online";
	
	@Resource(name = "neorisFavoriteProductFacade")
	protected NeorisFavoriteProductFacade neorisFavoriteProductFacade;
	
	@Resource(name = "accountBreadcrumbBuilder")
	protected ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@Resource(name = "catalogVersionService")
	protected CatalogVersionService catalogVersionService;
	
	@Resource(name = "neorisProductFacade")
	protected NeorisProductFacade neorisProductFacade;
	
	@Resource(name = "neorisUoMQuantityConverter")
	protected NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	private static final int PRODUCTS_PER_PAGE = 20;
	
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
	
	protected void populateFavoriteProducts(final Model model, final int page, final ShowMode showMode, String sortCode, final boolean inyectInventory)
	{
		// initialize clean pagination
		SearchPageData<ProductData> searchPageData = new SearchPageData<ProductData>();
		searchPageData.setPagination(new PaginationData());
		
		FavoriteProductSearchParameters searchParameters = buildSearchParameters(model, page, showMode, sortCode, true);
		
		try 
		{
			searchPageData = neorisFavoriteProductFacade.getPagedFavoriteProducts(searchParameters);
			
			if (inyectInventory) 
			{
				neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());
				neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());				
			}

			neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());
		} 
		catch (Exception e) 
		{
			GlobalMessages.addErrorMessage(model, "text.account.customerFavoriteProducts.list.error");
		}
		
		populateModel(model, searchPageData, showMode);
	}
	
	protected FavoriteProductSearchParameters buildSearchParameters(final Model model, final int page, final ShowMode showMode, String sortCode, boolean includeSortData)
	{
		// Handle paged search results
		final PageableData pageableData = createPageableData(page, PRODUCTS_PER_PAGE, sortCode,
				showMode);
		
		FavoriteProductSearchParameters searchParameters = new FavoriteProductSearchParameters();
		searchParameters.setPageableData(pageableData);
		
		if(includeSortData)
		{
			if (sortCode == null || sortCode.isEmpty())
				sortCode = "Description-asc";
			
			String[] sortInfo = getSortByAndOrderFrom(sortCode);
			if (sortInfo != null) 
			{
				String sortBy = "Description".equalsIgnoreCase(sortInfo[0]) ? "name" : "code";
				searchParameters.setSortBy(sortBy);
				searchParameters.setSortOrder(sortInfo[1]);
			}
			
			// add sorts to searchPageData
			String[] sortProps = new String[] {"Description", "Part"};
			List<SortData> sorts = getSortListFor(sortProps,
					sortCode == null ? "Description-asc" : sortCode, "{0}-(asc)",
					"{0}-(desc)");
			model.addAttribute("sorts", sorts);
		}
		
		B2BUnitModel customer = neorisFacade.getRootUnit();
		searchParameters.setCustomer(customer);
		
		final String catalogId = neorisFacade.getCurrentBaseStore().getUid() + PRODCUT_CATALOG;
		CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(catalogId, CATALOG_VERSION);
		searchParameters.setCatalogVersion(catalogVersion);
		
		return searchParameters;
	}
}
