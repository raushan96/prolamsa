/**
 * 
 */
package mx.neoris.core.services;



import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collection;
import java.util.Map;

import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.model.ProlamsaProductModel;


/**
 * @author e-lacantu
 * 
 */
public interface NeorisProductVisibilityService
{
	ProlamsaProductModel getProductByCode(final String code) throws Exception;

	SearchResult<ProlamsaProductModel> getProductByAttribute(String baseStore, Map<String, Object> mapAttribute) throws Exception;

	SearchResult<ProductVisibilityModel> getAllProductVisibility(String baseStore) throws Exception;

	ProductVisibilityModel getProductVisibilityByCode(String baseStore, String code) throws Exception;

	ProductVisibilityModel getProductVisibilityByCategory(String baseStore, String category) throws Exception;

	SearchResult<B2BUnitModel> getB2BUnitByIndustry(final String baseStore, final Map<String, Object> mapAttribute)
			throws Exception;

	SearchResult<UserGroupModel> getUserGroupByName(final String baseStore, final String userGroup) throws Exception;

	/**
	 * @param catalogId
	 * @param catalogVersionName
	 * @return
	 */
	Collection<CatalogVersionModel> findCatalogVersions(String catalogId, String catalogVersionName);
}
