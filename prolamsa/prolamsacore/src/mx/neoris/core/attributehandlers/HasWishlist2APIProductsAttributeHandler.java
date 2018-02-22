/**
 * 
 */
package mx.neoris.core.attributehandlers;

import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.List;

import mx.neoris.core.model.ProlamsaProductModel;

import org.springframework.stereotype.Component;


/**
 * @author eduardo.carrillo
 * 
 */


@Component
public class HasWishlist2APIProductsAttributeHandler extends AbstractDynamicAttributeHandler<Boolean, Wishlist2Model>
{
	@Override
	public Boolean get(final Wishlist2Model wishlist2Model)
	{
		final List<Wishlist2EntryModel> entries = wishlist2Model.getEntries();

		if (entries == null || entries.size() == 0)
		{
			return false;
		}

		for (final Wishlist2EntryModel eachWishlist2EntryModel : entries)
		{
			final ProlamsaProductModel productModel = (ProlamsaProductModel) eachWishlist2EntryModel.getProduct();

			if (productModel.getIsAPI())
			{
				return true;
			}
		}

		return false;
	}
}