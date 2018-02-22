/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.impl.DefaultOrderService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.services.NeorisB2BOrderService;
import mx.neoris.core.services.NeorisQuoteSearchParameters;


public class DefaultNeorisB2BOrderService extends DefaultOrderService implements NeorisB2BOrderService
{

	private static final long serialVersionUID = 1L;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Override
	public SearchPageData<OrderModel> getPagedQuotesWithStatuses(final NeorisQuoteSearchParameters searchParameters,
			final OrderStatus[] statuses) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();


		String query = "SELECT {o.pk} FROM {Order as o JOIN OrderStatus as oStatus ON  {o.status}={oStatus.pk} JOIN B2BUnit as b2bunit ON {o.Unit} = {b2bunit.pk} } WHERE {o.versionID} IS NULL AND {o.store} = '"
				+ searchParameters.getStore() + "' ";

		final StringBuilder b2bunitsQuery = new StringBuilder();
		if (searchParameters.getB2bUnits() != null)
		{
			b2bunitsQuery.append("(");
			for (final String eachB2BUnit : searchParameters.getB2bUnits())
			{
				b2bunitsQuery.append("'");
				b2bunitsQuery.append(eachB2BUnit);
				b2bunitsQuery.append("',");

			}
			b2bunitsQuery.deleteCharAt(b2bunitsQuery.length() - 1);
			b2bunitsQuery.append(")");
			query = query + "AND  {b2bunit.uid} IN " + b2bunitsQuery.toString();
		}

		final StringBuilder statusesQuery = new StringBuilder();
		if (statuses != null & statuses.length > 0)
		{
			statusesQuery.append("AND {oStatus.code} IN (");
			for (final OrderStatus orderStatus : statuses)
			{
				statusesQuery.append("'");
				statusesQuery.append(orderStatus.getCode());
				statusesQuery.append("'");
				statusesQuery.append(",");
			}
			statusesQuery.deleteCharAt(statusesQuery.length() - 1);
			statusesQuery.append(")");

			query = query + " " + statusesQuery.toString();
		}

		final StringBuilder sortQuery = new StringBuilder();
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append(" ORDER BY {o.");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());

			query = query + " " + sortQuery.toString();
		}


		return pagedFlexibleSearchService.search(query, queryParams, searchParameters.getPageableData());
	}


	public SearchPageData<OrderModel> getPagedQuotesWithStatusesByB2BUnits(final List<String> b2bUnits, final String store,
			final List<String> statuses) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();
		final StringBuilder query = new StringBuilder();

		query.append("SELECT {o." + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE + " as o JOIN " + OrderStatus._TYPECODE
				+ " as oStatus ON  ");
		query.append(" {o." + OrderModel.STATUS + "} = {oStatus.pk} JOIN " + B2BUnitModel._TYPECODE + " as b2bunit ON ");
		query.append(" {o." + OrderModel.UNIT + "} = {b2bunit." + B2BUnitModel.PK + "} } WHERE ");
		query.append(" {o." + OrderModel.VERSIONID + "} IS NULL AND {o." + OrderModel.STORE + "} = " + store);

		final StringBuilder b2bunitsQuery = new StringBuilder();
		b2bunitsQuery.append(" AND {b2bunit." + B2BUnitModel.UID + "} IN (?b2bUnits) ");
		queryParams.put("b2bUnits", (Serializable) b2bUnits);

		final StringBuilder statusesQuery = new StringBuilder();
		statusesQuery.append("AND {oStatus.code} IN (?statuses) ");
		queryParams.put("statuses", (Serializable) statuses);
		query.append(statusesQuery);

		final StringBuilder sortQuery = new StringBuilder();
		sortQuery.append(" ORDER BY {o." + OrderModel.DATE + "} desc");
		query.append(sortQuery);

		final PageableData pageableDate = new PageableData();
		pageableDate.setCurrentPage(0);
		pageableDate.setPageSize(10);
		pageableDate.setSort(null);

		return pagedFlexibleSearchService.search(query.toString(), queryParams, pageableDate);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisB2BOrderService#isPOAlreadyUsed(java.lang.String)
	 */
	@Override
	public boolean isPOAlreadyUsed(final String purchaseOrderNumber, final B2BUnitModel currentB2BUnit)
	{
		final String currentStoreId = baseStoreService.getCurrentBaseStore().getPk().toString();

		final String query = "SELECT {o.pk} FROM {Order AS o} WHERE {o." + OrderModel.UNIT + "}=?currentB2BUnit AND {o."
				+ OrderModel.PURCHASEORDERNUMBER + "}=?purchaseOrderNumber AND {o." + OrderModel.VERSIONID + "} IS NULL AND {o."
				+ OrderModel.STORE + "} = '" + currentStoreId + "'";


		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		queryParams.put("currentB2BUnit", currentB2BUnit);
		queryParams.put("purchaseOrderNumber", purchaseOrderNumber);

		final SearchResult<IncidentModel> result = flexibleSearchService.search(query, queryParams);

		if (result != null && result.getTotalCount() > 0 && result.getResult().get(0) != null)
		{
			return true;

		}

		return false;
	}

}
