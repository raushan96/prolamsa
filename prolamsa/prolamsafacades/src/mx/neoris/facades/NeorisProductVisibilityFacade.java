/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Map;

import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.model.ProlamsaProductModel;

/**
 * @author e-lacantu
 *
 */
public interface NeorisProductVisibilityFacade {

	ProlamsaProductModel getProductByCode(final String code) throws Exception;

	SearchResult<ProlamsaProductModel> getProductByAttribute(String baseStore, Map<String, Object> mapAttribute) throws Exception;

	SearchResult<ProductVisibilityModel> getAllProductVisibility(String baseStore) throws Exception;

	ProductVisibilityModel getProductVisibilityByCode(String baseStore, String code) throws Exception;

	ProductVisibilityModel getProductVisibilityByCategory(String baseStore, String category) throws Exception;
}
