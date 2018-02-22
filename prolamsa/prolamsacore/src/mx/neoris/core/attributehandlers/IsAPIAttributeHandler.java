/**
 * 
 */
package mx.neoris.core.attributehandlers;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;
import de.hybris.platform.store.BaseStoreModel;

import java.util.Collection;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaProductModel;

import org.springframework.stereotype.Component;


/**
 * @author eduardo.carrillo
 * 
 */


@Component
public class IsAPIAttributeHandler extends AbstractDynamicAttributeHandler<Boolean, ProlamsaProductModel>
{
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Override
	public Boolean get(final ProlamsaProductModel productModel)
	{
		// get base store model from product model
		final Collection<BaseStoreModel> baseStores = productModel.getCatalogVersion().getCatalog().getBaseStores();
		BaseStoreModel baseStore = null;
		for (final BaseStoreModel eachBaseStore : baseStores)
		{
			if (productModel.getCatalogVersion().getCatalog().getId().contains(eachBaseStore.getUid()))
			{
				baseStore = eachBaseStore;
				break;
			}
		}

		// evaluate base store has API functionality enabled
		if (baseStore == null || baseStore.getAPIFunctionaliatyEnabled() == null
				|| !baseStore.getAPIFunctionaliatyEnabled().booleanValue())
		{
			return false;
		}

		// this line was necessary because when this code was reach from the order process there was no catalog on session.
		catalogVersionService.addSessionCatalogVersion(productModel.getCatalogVersion());

		final Collection<CategoryModel> APIFamilies = baseStore.getAPICategories();

		final Collection<CategoryModel> productFamilies = productModel.getSupercategories();

		try
		{
			if (!productFamilies.isEmpty() && !APIFamilies.isEmpty())
			{
				for (final CategoryModel eachProductFamilyModel : productFamilies)
				{

					if (APIFamilies.contains(eachProductFamilyModel))
					{
						return true;
					}
				}
			}
			return false;
		}
		catch (final IllegalStateException e)
		{
			return false;
		}
	}


}