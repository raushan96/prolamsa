/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.product.impl.DefaultProductService;

import java.util.List;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.NeorisProductService;
import mx.neoris.core.services.dao.NeorisProductDao;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisProductService extends DefaultProductService implements NeorisProductService
{
	private NeorisProductDao neorisProductDao;

	@Override
	public List<ProlamsaProductModel> searchProductsByBaseCode(final String baseCode)
	{
		return getNeorisProductDao().findProductsByBaseCode(baseCode);
	}

	@Override
	public List<ProlamsaProductModel> searchProductsByBaseCodeAndLocation(final String baseCode, final ProductLocation location)
	{
		return getNeorisProductDao().findProductsByBaseCodeAndLocation(baseCode, location);
	}

	/**
	 * @return the neorisProductDao
	 */
	public NeorisProductDao getNeorisProductDao()
	{
		return neorisProductDao;
	}

	/**
	 * @param neorisProductDao
	 *           the neorisProductDao to set
	 */
	public void setNeorisProductDao(final NeorisProductDao neorisProductDao)
	{
		this.neorisProductDao = neorisProductDao;
	}

	@Override
	public List<ProlamsaProductModel> findAllProductsFor(final CatalogVersionModel catalogVersionModel)
	{
		return neorisProductDao.findAllProductsFor(catalogVersionModel);
	}
}
