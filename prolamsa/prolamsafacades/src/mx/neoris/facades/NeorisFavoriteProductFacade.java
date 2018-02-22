/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.FavoriteProductSearchParameters;

/**
 * @author e-hicastaneda
 *
 */
public interface NeorisFavoriteProductFacade 
{
	String resetFavoriteProducts(String location) throws Exception;
	
	SearchPageData<ProductData> getPagedFavoriteProducts(FavoriteProductSearchParameters searchParameters) throws Exception;
	
	String removeFavoriteProduct(String productCode);
	
	List<ProductData> searchFavoriteCandidateProduct(FavoriteProductSearchParameters searchParameters) throws Exception;
	
	String addFavoriteProduct(String productCode);
	
	void injectIsFavoriteProductOn(List<ProductData> products);
	
	List<String> getFavoriteProductCodes(FavoriteProductSearchParameters searchParameters) throws Exception;
	
	long getFavoriteProductsCount(FavoriteProductSearchParameters searchParameters);
}
