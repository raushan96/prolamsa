/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import mx.neoris.core.model.BalanceStatementDetailModel;
import mx.neoris.core.model.BalanceStatementModel;
import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.services.NeorisBalanceStatementService;


/**
 * @author e-lacantu
 * 
 */
public class DefaultNeorisBalanceStatementService implements NeorisBalanceStatementService
{

	private FlexibleSearchService flexibleSearchService;
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisBalanceStatementService#getPagedInvoices(mx.neoris.core.services.
	 * BalanceStatementSearchParameters)
	 */
	@Override
	public SearchPageData<BalanceStatementDetailModel> getPagedBalanceStatement(
			final BalanceStatementSearchParameters searchParameters) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final StringBuilder sortQuery = new StringBuilder();
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append(" ORDER BY {");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());
		}

		String query = "SELECT {b." + BalanceStatementModel.PK + "} FROM {" + BalanceStatementModel._TYPECODE + " AS b JOIN "
				+ B2BUnitModel._TYPECODE + " as b2b ON CAST({b." + BalanceStatementModel.CUSTOMER + "} AS VARCHAR(15))=CAST({b2b."
				+ B2BUnitModel.PK + "} AS VARCHAR(15)) }";

		if (searchParameters.getCustomer() != null)
		{
			//query = query + " WHERE {b2b." + B2BUnitModel.UID + "} = " + searchParameters.getCustomer();
		}

		if (searchParameters.getSortBy() != null)
		{
			query = query + " " + sortQuery.toString();
		}

		return getPagedFlexibleSearchService().search(query, queryParams, searchParameters.getPageableData());
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	public PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisBalanceStatementService#getBalanceStatementDetail(java.lang.String)
	 */
	@Override
	public SearchResult<BalanceStatementDetailModel> getBalanceStatementDetail(
			final BalanceStatementSearchParameters searchParameters, final Integer row) throws Exception
	{
		final StringBuilder query = new StringBuilder();
		query.append("SELECT {b." + BalanceStatementDetailModel.PK + "} FROM {" + BalanceStatementDetailModel._TYPECODE
				+ " AS b JOIN ");
		query.append(B2BUnitModel._TYPECODE + " as b2b ON CAST({b." + BalanceStatementDetailModel.CUSTOMER
				+ "} AS VARCHAR(15))=CAST({b2b.");
		query.append(B2BUnitModel.PK + "} AS VARCHAR(15)) } WHERE {b2b." + B2BUnitModel.UID + "} = "
				+ searchParameters.getCustomer());

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);

		return getFlexibleSearchService().search(strQuery);

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisBalanceStatementService#getPagedBalanceStatementInvoices(mx.neoris.core.services
	 * .InvoiceSearchParameters)
	 */
	@Override
	public SearchPageData<InvoiceModel> getPagedBalanceStatementInvoices(final InvoiceSearchParameters searchParameters)
			throws Exception
	{
		// YTODO Auto-generated method stub
		return null;
	}



}
