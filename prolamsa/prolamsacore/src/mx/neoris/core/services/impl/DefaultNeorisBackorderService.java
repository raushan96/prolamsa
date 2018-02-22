/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import mx.neoris.core.model.BackorderDetailModel;
import mx.neoris.core.model.BackorderModel;
import mx.neoris.core.services.BackorderDetailSearchParameter;
import mx.neoris.core.services.BackorderSearchParameters;
import mx.neoris.core.services.NeorisBackorderService;


/**
 * @author christian.loredo
 * 
 */
public class DefaultNeorisBackorderService implements NeorisBackorderService
{
	private FlexibleSearchService flexibleSearchService;
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	public SearchPageData<BackorderModel> getPagedBackorder(final BackorderSearchParameters searchParameters) throws Exception
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

		String query = "SELECT {b." + BackorderModel.PK + "} FROM {" + BackorderModel._TYPECODE + " AS b JOIN "
				+ B2BUnitModel._TYPECODE + " as b2b ON CAST({b." + BackorderModel.CUSTOMER + "} AS VARCHAR(15))=CAST({b2b."
				+ B2BUnitModel.PK + "} AS VARCHAR(15)) } ";

		if (searchParameters.getCustomer() != null && searchParameters.getCustomer().length() > 0)
		{
			query += " WHERE {b2b." + B2BUnitModel.UID + "} IN (CAST(" + searchParameters.getCustomer() + " as varchar(15))) ";
		}

		query += " " + sortQuery.toString();

		return getPagedFlexibleSearchService().search(query, queryParams, searchParameters.getPageableData());
	}

	//DETAIL

	public SearchPageData<BackorderDetailModel> getPagedBackorderDetail(final BackorderDetailSearchParameter searchParameters)
			throws Exception
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

		final String query = "SELECT {PK} FROM {BackorderDetail} WHERE 1 = 1" + sortQuery.toString();

		return getPagedFlexibleSearchService().search(query, queryParams, searchParameters.getPageableData());
	}

	public SearchPageData<BackorderModel> getPagedBackorderDetailExcel(final BackorderSearchParameters searchParameters)
			throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final StringBuilder sortQuery = new StringBuilder();
		/*
		 * if (searchParameters.getSortBy() != null) { sortQuery.append(" ORDER BY {");
		 * sortQuery.append(searchParameters.getSortBy()); sortQuery.append("} ");
		 * sortQuery.append(searchParameters.getSortOrder()); }
		 */
		String query = "SELECT {b." + BackorderModel.PK + "} FROM {" + BackorderModel._TYPECODE + " AS b JOIN "
				+ B2BUnitModel._TYPECODE + " as b2b ON CAST({b." + BackorderModel.CUSTOMER + "} AS VARCHAR(15))=CAST({b2b."
				+ B2BUnitModel.PK + "} AS VARCHAR(15)) } ";

		if (searchParameters.getCustomer() != null && searchParameters.getCustomer().length() > 0)
		{
			query += " WHERE {b2b." + B2BUnitModel.UID + "} IN (CAST(" + searchParameters.getCustomer() + " as varchar(15))) ";
		}

		query += " " + sortQuery.toString();

		return getPagedFlexibleSearchService().search(query, queryParams, searchParameters.getPageableData());
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/**
	 * @return the pagedFlexibleSearchService
	 */
	public PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	/**
	 * @param pagedFlexibleSearchService
	 *           the pagedFlexibleSearchService to set
	 */
	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
	}

}
