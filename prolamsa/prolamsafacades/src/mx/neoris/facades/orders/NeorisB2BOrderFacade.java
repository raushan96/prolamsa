/**
 * 
 */
package mx.neoris.facades.orders;

import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.workflow.enums.WorkflowActionType;

import java.util.List;

import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisQuoteSearchParameters;

/**
 * 
 */
public interface NeorisB2BOrderFacade
{
	public SearchPageData<OrderHistoryData> getPagedOrderHistoryForStatuses(final NeorisQuoteSearchParameters searchParameters,OrderStatus[] statuses) throws Exception;

	SearchPageData<B2BOrderApprovalData> getPagedOrdersForApproval(WorkflowActionType[] actionTypes, PageableData pageableData, NeorisOrderApprovalSearchParameters searchParameters);
	
	SearchPageData<OrderHistoryData> getPagedQuotesWithStatusesByB2BUnits(final List<String> b2bUnits, 	final List<String> statuses) throws Exception;

	String cloneAndSetNewOrderFromApprovedQuote(String orderCode, String comment, String shippingInstructions) throws Exception;
}
