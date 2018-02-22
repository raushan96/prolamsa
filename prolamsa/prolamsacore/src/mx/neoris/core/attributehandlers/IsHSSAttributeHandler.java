/**
 * 
 */
package mx.neoris.core.attributehandlers;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.Collection;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaProductModel;

import org.springframework.stereotype.Component;


/**
 * @author eduardo.carrillo
 * 
 */


@Component
public class IsHSSAttributeHandler extends AbstractDynamicAttributeHandler<Boolean, ProlamsaProductModel>
{

	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	public static String HSS = "HSS";

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

		// evaluate base store has HSS functionality enabled
		if (baseStore == null || baseStore.getHSSFunctionaliatyEnabled() == null
				|| !baseStore.getHSSFunctionaliatyEnabled().booleanValue())
		{
			return false;
		}

		//this line was necessary because when this code was reach from the order process there was no catalog on session.
		catalogVersionService.addSessionCatalogVersion(productModel.getCatalogVersion());

		final Collection<CategoryModel> productFamilies = productModel.getSupercategories();

		try
		{
			for (final CategoryModel eachProductFamilyModel : productFamilies)
			{

				if (eachProductFamilyModel.getCode().contains(IsHSSAttributeHandler.HSS))
				{
					return true;
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