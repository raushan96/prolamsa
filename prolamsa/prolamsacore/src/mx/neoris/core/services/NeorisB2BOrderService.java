/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;


public interface NeorisB2BOrderService
{

	public SearchPageData<OrderModel> getPagedQuotesWithStatuses(final NeorisQuoteSearchParameters searchParameters,
			OrderStatus[] statuses) throws Exception;

	public SearchPageData<OrderModel> getPagedQuotesWithStatusesByB2BUnits(final List<String> b2bUnits, final String store,
			final List<String> statuses) throws Exception;

	public boolean isPOAlreadyUsed(final String purchaseOrderNumber, final B2BUnitModel currentB2BUnit);
}