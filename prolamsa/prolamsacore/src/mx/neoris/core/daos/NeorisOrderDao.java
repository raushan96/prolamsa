/**
 *
 */
package mx.neoris.core.daos;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.daos.OrderDao;

import java.util.Date;
import java.util.List;


/**
 * @author fdeutsch
 *
 */
public interface NeorisOrderDao extends OrderDao
{
	List<AbstractOrderEntryModel> findEntriesByProduct(String entryTypeCode, final AbstractOrderModel order,
			final ProductModel product, final Date rollingScheduleWeek);

	List<OrderModel> findUnPushedOrderByDate(final Date lastSuccessFullDate);

}
