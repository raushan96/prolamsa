/**
 * 
 */
package mx.neoris.core.updaters.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;

import javax.annotation.Resource;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.services.NeorisFavoriteProductService;
import mx.neoris.core.updaters.NeorisFavoriteProductsUpdater;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisFavoriteProductsUpdater implements NeorisFavoriteProductsUpdater
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisFavoriteProductsUpdater.class);

	@Resource(name = "neorisFavoriteProductService")
	NeorisFavoriteProductService neorisFavoriteProductService;

	@Override
	public void updateFavoriteProductsWith(final CustomerProductReferenceModel customerProductReferenceModel) throws Exception
	{
		LOG.info("Favorite Products Updater...");

		final B2BUnitModel b2bUnitModel = customerProductReferenceModel.getCustomer();
		final ProductLocation productLocation = customerProductReferenceModel.getLocation();
		final String productBaseCode = customerProductReferenceModel.getProductCode();
		final String productCatalogId = customerProductReferenceModel.getCatalogId();

		String productCode = null;
		if (productLocation != null && productBaseCode != null && !productBaseCode.isEmpty())
		{
			productCode = productBaseCode + productLocation.getCode();
		}

		if (productCode != null && b2bUnitModel != null && productCatalogId != null)
		{
			final String response = neorisFavoriteProductService.addFavoriteProductWith(b2bUnitModel, productCode, productCatalogId);
			if (response != null)
			{
				LOG.info("Unable to add product: " + productCode + " to favorite products due to: " + response);
			}
		}
		else
		{
			throw new Exception("Incomplete information to update customer favorite products");
		}
	}
}
