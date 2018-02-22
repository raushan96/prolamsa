/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaProductModel;


/**
 * @author e-hicastaneda
 * 
 */
public interface NeorisFavoriteProductService
{
	void cleanCustomerFavoriteProducts(B2BUnitModel customer) throws Exception;

	String reloadCustomerFavoriteProducts(B2BUnitModel customer, ProductLocation location) throws Exception;

	SearchPageData<ProlamsaProductModel> getPagedFavoriteProducts(FavoriteProductSearchParameters searchParameters)
			throws Exception;

	String removeFavoriteProductWith(B2BUnitModel customer, String productCode);

	List<ProlamsaProductModel> findFavoriteCandidateProducts(FavoriteProductSearchParameters searchParameters) throws Exception;

	String addFavoriteProductWith(B2BUnitModel customer, String productCode, String productCatalogId);

	List<ProlamsaProductModel> getFavoriteProducts(FavoriteProductSearchParameters searchParameters) throws Exception;

	long getFavoriteProductsCount(FavoriteProductSearchParameters searchParameters);
}
