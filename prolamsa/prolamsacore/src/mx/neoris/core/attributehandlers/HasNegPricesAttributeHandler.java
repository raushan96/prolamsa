/**
 * 
 */
package mx.neoris.core.attributehandlers;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


/**
 * @author eduardo.carrillo
 * 
 */


@Component
public class HasNegPricesAttributeHandler extends AbstractDynamicAttributeHandler<Boolean, AbstractOrderModel>
{

	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	@Override
	public Boolean get(final AbstractOrderModel abstractOrderModel)
	{

		final List<AbstractOrderEntryModel> entries = abstractOrderModel.getEntries();

		if (abstractOrderModel.getStore() == null || !abstractOrderModel.getStore().getUid().contains("6000"))
		{
			return false;
		}

		if (entries == null || entries.size() == 0)
		{
			return false;
		}

		boolean hasNegPrices = false;
		for (final AbstractOrderEntryModel eachEntryModel : entries)
		{
			if (eachEntryModel.getNegotiablePrice() != null && eachEntryModel.getNegotiablePrice() != 0)
			{
				hasNegPrices = true;
			}
		}

		return hasNegPrices;

	}


}