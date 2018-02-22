/**
 * 
 */
package mx.neoris.core.attributehandlers;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

import java.util.List;

import mx.neoris.core.model.ProlamsaProductModel;

import org.springframework.stereotype.Component;


/**
 * @author eduardo.carrillo
 * 
 */


@Component
public class HasAPIProductsAttributeHandler extends AbstractDynamicAttributeHandler<Boolean, AbstractOrderModel>
{
	@Override
	public Boolean get(final AbstractOrderModel abstractOrderModel)
	{
		final List<AbstractOrderEntryModel> entries = abstractOrderModel.getEntries();

		if (entries == null || entries.size() == 0)
		{
			return false;
		}

		for (final AbstractOrderEntryModel eachEntryModel : entries)
		{
			final ProlamsaProductModel productModel = (ProlamsaProductModel) eachEntryModel.getProduct();

			if (productModel.getIsAPI())
			{
				return true;
			}
		}

		return false;
	}
}