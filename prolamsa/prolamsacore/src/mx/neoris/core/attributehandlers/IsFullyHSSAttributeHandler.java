/**
 * 
 */
package mx.neoris.core.attributehandlers;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

import java.util.List;

import mx.neoris.core.model.ProlamsaProductModel;

import org.springframework.stereotype.Component;


/**
 * @author hector.castaneda
 * 
 */


@Component
public class IsFullyHSSAttributeHandler extends AbstractDynamicAttributeHandler<Boolean, CartModel>
{
	@Override
	public Boolean get(final CartModel cartModel)
	{
		if (cartModel.getHasHSSProducts())
		{
			final List<AbstractOrderEntryModel> entries = cartModel.getEntries();

			for (final AbstractOrderEntryModel eachEntryModel : entries)
			{
				final ProlamsaProductModel productModel = (ProlamsaProductModel) eachEntryModel.getProduct();

				if (!productModel.getIsHSS())
				{
					return false;
				}
			}

			return true;
		}

		return false;
	}
}