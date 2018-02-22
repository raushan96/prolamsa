/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.product.ProductService;

import java.util.List;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaProductModel;


/**
 * @author e-hicastaneda
 * 
 */
public interface NeorisProductService extends ProductService
{
	List<ProlamsaProductModel> searchProductsByBaseCode(String baseCode);

	List<ProlamsaProductModel> searchProductsByBaseCodeAndLocation(String baseCode, ProductLocation location);

	List<ProlamsaProductModel> findAllProductsFor(CatalogVersionModel catalogVersionModel);
}
