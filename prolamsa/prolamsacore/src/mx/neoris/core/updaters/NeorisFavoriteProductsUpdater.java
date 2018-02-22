/**
 * 
 */
package mx.neoris.core.updaters;

import mx.neoris.core.model.CustomerProductReferenceModel;


/**
 * @author e-hicastaneda
 * 
 */
public interface NeorisFavoriteProductsUpdater
{
	void updateFavoriteProductsWith(final CustomerProductReferenceModel customerProductReferenceModel) throws Exception;
}
