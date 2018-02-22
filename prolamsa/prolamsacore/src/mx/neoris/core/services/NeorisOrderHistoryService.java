/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;


/**
 * @author e-lacantu fdeutsch
 * 
 */
public interface NeorisOrderHistoryService
{
	SearchPageData<OrderModel> getPagedOrderHistory(OrderHistorySearchParameters searchParameters, B2BCustomerModel customer,
			List<OrderStatus> statusList) throws Exception;

	SearchPageData<OrderData> getOrderHistoryByB2BUnitModel(final List<Long> listB2BUnits) throws Exception;

	List<OrderModel> getAllOrdersAndQuotes() throws Exception;
}
