/**
 * 
 */
package mx.neoris.core.services.dao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.product.daos.ProductDao;

import java.util.List;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaProductModel;


/**
 * @author e-hicastaneda
 * 
 */
public interface NeorisProductDao extends ProductDao
{
	List<ProlamsaProductModel> findProductsByBaseCode(String baseCode);

	List<ProlamsaProductModel> findProductsByBaseCodeAndLocation(String baseCode, ProductLocation location);

	List<ProlamsaProductModel> findAllProductsFor(CatalogVersionModel catalogVersionModel);
}
