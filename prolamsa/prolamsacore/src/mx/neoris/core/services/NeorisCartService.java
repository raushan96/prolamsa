/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;

import java.util.Date;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisCartService
{
	AbstractOrderEntryModel addNewEntry(final AbstractOrderModel order, final ProductModel product,
			final ProlamsaAPIProductConfigurationModel config, final Date rollingScheduleDate, final long bundlesQuantity,
			final UnitModel unit, final int number, final boolean addToPresent, final double convertedQuantity,
			final String stockOuts);
}
