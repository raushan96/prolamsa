/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.FavoriteProductSearchParameters;
import mx.neoris.core.services.NeorisFavoriteProductService;
import mx.neoris.core.services.impl.DefaultNeorisFavoriteProductService;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisFavoriteProductFacade;

/**
 * @author e-hicastaneda
 *
 */
public class DefaultNeorisFavoriteProductFacade implements NeorisFavoriteProductFacade 
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisFavoriteProductFacade.class);
	
	public static final String PRODCUT_CATALOG = "ProductCatalog";

	@Resource(name="neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name="neorisFavoriteProductService")
	private NeorisFavoriteProductService neorisFavoriteProductService;
	
	@Resource(name = "neorisProductConverter")
	private AbstractConverter<ProductModel, ProductData> neorisProductConverter;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Override
	public String resetFavoriteProducts(final String location) throws Exception 
	{
		B2BUnitModel customer = neorisFacade.getRootUnit();
		
		ProductLocation productLocation = ProductLocation.valueOf(location);
		
		// clean favorite products list
//		neorisFavoriteProductService.cleanCustomerFavoriteProducts(customer);
		
		// reload favorite products from customer product references
		return neorisFavoriteProductService.reloadCustomerFavoriteProducts(customer, productLocation);
	}

	@Override
	public SearchPageData<ProductData> getPagedFavoriteProducts(
			FavoriteProductSearchParameters searchParameters) throws Exception 
	{
		SearchPageData<ProlamsaProductModel> searchResult = neorisFavoriteProductService
				.getPagedFavoriteProducts(searchParameters);
		
		List<ProductData> resultConverted = convertListModelToListData(searchResult.getResults());
		return convertToPageData(searchResult, resultConverted);
	}
	
	@Override
	public String removeFavoriteProduct(String productCode)
	{
		B2BUnitModel customer = neorisFacade.getRootUnit();
		return neorisFavoriteProductService.removeFavoriteProductWith(customer, productCode);
	}
	
	@Override
	public String addFavoriteProduct(String productCode)
	{
		B2BUnitModel customer = neorisFacade.getRootUnit();
		final String productCatalogId = baseStoreService.getCurrentBaseStore().getUid() + PRODCUT_CATALOG;
		
		return neorisFavoriteProductService.addFavoriteProductWith(customer, productCode, productCatalogId);
	}
	
	@Override
	public List<ProductData> searchFavoriteCandidateProduct(
			FavoriteProductSearchParameters searchParameters) throws Exception 
	{
		List<ProlamsaProductModel> findResult = neorisFavoriteProductService
				.findFavoriteCandidateProducts(searchParameters);
		return convertListModelToListData(findResult);
	}

	private SearchPageData<ProductData> convertToPageData(
			final SearchPageData<ProlamsaProductModel> objData,
			final List<ProductData> products) 
	{
		final SearchPageData<ProductData> result = new SearchPageData<ProductData>();
		
		result.setPagination(objData.getPagination());
		result.setResults(products);
		result.setSorts(objData.getSorts());

		return result;
	}
	
	private List<ProductData> convertListModelToListData(List<ProlamsaProductModel> productList)
	{
		List<ProductData> result = new ArrayList<ProductData>();
		
		for (final ProlamsaProductModel productModel : productList) 
		{
			ProductData productData = new ProductData();
			productData = neorisProductConverter.convert(productModel);
			
			result.add(productData);
		}
		
		return result;
	}

	@Override
	public void injectIsFavoriteProductOn(List<ProductData> products) 
	{
		B2BUnitModel customer = neorisFacade.getRootUnit();
		
		// convert set to map for a faster access
		Map<String, Integer> favoriteProductsMap = new HashMap<String, Integer>();
		int index = 0;
		for (ProlamsaProductModel eachProductModel : customer.getFavoriteProducts())
		{
			favoriteProductsMap.put(eachProductModel.getCode(), new Integer(index));
			index++;
		}
		
		if (favoriteProductsMap.size() > 0)
		{
			// iterate product data
			for (ProductData eachProductData : products)
			{
				if (favoriteProductsMap.containsKey(eachProductData.getCode()))
				{
					eachProductData.setIsFavoriteProduct(true);
				}
			}					
		}
	}

	@Override
	public List<String> getFavoriteProductCodes(FavoriteProductSearchParameters searchParameters) throws Exception 
	{
		List<String> codes = new ArrayList<String>();
		List<ProlamsaProductModel> favoriteProducts = neorisFavoriteProductService.getFavoriteProducts(searchParameters);
		
		// getting product codes
		for(ProlamsaProductModel eachProduct : favoriteProducts)
		{
			codes.add(eachProduct.getCode());
		}
		
		return codes;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisFavoriteProductFacade#getFavoriteProductsCount(mx.neoris.core.services.FavoriteProductSearchParameters)
	 */
	@Override
	public long getFavoriteProductsCount(
			FavoriteProductSearchParameters searchParameters) {
		// YTODO Auto-generated method stub
		return neorisFavoriteProductService.getFavoriteProductsCount(searchParameters);
	}
}
