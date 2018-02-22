/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.services.NeorisInvoiceService;

import org.apache.commons.lang.StringUtils;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisInvoiceService implements NeorisInvoiceService
{
	private FlexibleSearchService flexibleSearchService;
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	public SearchPageData<InvoiceModel> getPagedInvoices(final InvoiceSearchParameters searchParameters) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final StringBuilder numberQuery = new StringBuilder();
		final StringBuilder dateQuery = new StringBuilder();


		if (searchParameters.getCustomer() != null && StringUtils.isNotBlank(searchParameters.getCustomer()))
		{
			dateQuery.append(" AND {b2b." + B2BUnitModel.UID + "} = ?customer ");
			queryParams.put("customer", searchParameters.getCustomer());
		}


		if (StringUtils.isNotBlank(searchParameters.getNumber()))
		{
			numberQuery.append(" AND {" + InvoiceModel.NUMBER + "} = ?number ");
			queryParams.put("number", searchParameters.getNumber());
		}

		if (searchParameters.getInitialDate() != null)
		{
			dateQuery.append(" AND {" + InvoiceModel.DUEDATE + "} >= ?initialDate ");
			queryParams.put("initialDate", searchParameters.getInitialDate());
		}

		if (searchParameters.getFinalDate() != null)
		{
			dateQuery.append(" AND {" + InvoiceModel.DUEDATE + "} <= ?finalDate ");
			queryParams.put("finalDate", searchParameters.getFinalDate());
		}

		final StringBuilder sortQuery = new StringBuilder();
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append(" ORDER BY {");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());
		}

		final String query;
		if (searchParameters.getCustomer() != null && StringUtils.isNotBlank(searchParameters.getCustomer()))
		{
			query = "SELECT {b." + InvoiceModel.PK + "} FROM {" + InvoiceModel._TYPECODE + " AS b JOIN " + B2BUnitModel._TYPECODE
					+ " as b2b ON CAST({b." + InvoiceModel.CUSTOMER + "} AS VARCHAR(15))=CAST({b2b." + B2BUnitModel.PK
					+ "} AS VARCHAR(15)) } WHERE 1=1 " + dateQuery.toString() + numberQuery.toString() + sortQuery.toString();
		}
		else
		{
			query = "SELECT {" + InvoiceModel.PK + "} FROM {" + InvoiceModel._TYPECODE + "} WHERE 1 = 1" + dateQuery.toString()
					+ numberQuery.toString() + sortQuery.toString();
		}

		return getPagedFlexibleSearchService().search(query, queryParams, searchParameters.getPageableData());
	}


	public SearchPageData<InvoiceModel> getPagedInvoicesByRange(final InvoiceSearchParameters searchParameters, final String range)
			throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final StringBuilder numberQuery = new StringBuilder();
		final StringBuilder dateQuery = new StringBuilder();


		if (searchParameters.getCustomer() != null && StringUtils.isNotBlank(searchParameters.getCustomer()))
		{
			dateQuery.append(" AND {b2b." + B2BUnitModel.UID + "} = ?customer ");
			queryParams.put("customer", searchParameters.getCustomer());
		}


		if (StringUtils.isNotBlank(searchParameters.getNumber()))
		{
			numberQuery.append(" AND {" + InvoiceModel.NUMBER + "} = ?number ");
			queryParams.put("number", searchParameters.getNumber());
		}

		if (range.equalsIgnoreCase("0"))
		{
			dateQuery.append(" AND DATEDIFF(DAY,{" + InvoiceModel.DUEDATE + "},?today) <= 0  ");
			queryParams.put("today", new Date());
		}

		if (range.equalsIgnoreCase("1_30"))
		{
			dateQuery.append(" AND (DATEDIFF(DAY,{" + InvoiceModel.DUEDATE + "},?today) >= 1) AND (DATEDIFF(DAY,{"
					+ InvoiceModel.DUEDATE + "},?today) <= 30)  ");
			queryParams.put("today", new Date());
		}

		if (range.equalsIgnoreCase("31_60"))
		{
			dateQuery.append(" AND (DATEDIFF(DAY,{" + InvoiceModel.DUEDATE + "},?today) >= 31) AND (DATEDIFF(DAY,{"
					+ InvoiceModel.DUEDATE + "},?today) <= 60)  ");
			queryParams.put("today", new Date());
		}


		final StringBuilder sortQuery = new StringBuilder();
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append(" ORDER BY {");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());
		}

		final String query;
		if (searchParameters.getCustomer() != null && StringUtils.isNotBlank(searchParameters.getCustomer()))
		{
			query = "SELECT {b." + InvoiceModel.PK + "} FROM {" + InvoiceModel._TYPECODE + " AS b JOIN " + B2BUnitModel._TYPECODE
					+ " as b2b ON CAST({b." + InvoiceModel.CUSTOMER + "} AS VARCHAR(15))=CAST({b2b." + B2BUnitModel.PK
					+ "} AS VARCHAR(15)) } WHERE 1=1 " + dateQuery.toString() + numberQuery.toString() + sortQuery.toString();
		}
		else
		{
			query = "SELECT {" + InvoiceModel.PK + "} FROM {" + InvoiceModel._TYPECODE + "} WHERE 1 = 1" + dateQuery.toString()
					+ numberQuery.toString() + sortQuery.toString();
		}

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


	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisInvoiceService#getInvoicesByB2BUnit(java.util.List)
	 */
	@Override
	public SearchPageData<InvoiceModel> getInvoicesByB2BUnit(final List<B2BUnitData> listB2BUnitData) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final StringBuilder textQuery = new StringBuilder();
		final StringBuilder query = new StringBuilder();
		final StringBuilder sortQuery = new StringBuilder();

		final List<String> listB2BUnitUID = new ArrayList<String>();
		for (final B2BUnitData data : listB2BUnitData)
		{
			listB2BUnitUID.add(data.getUid());
		}

		//textQuery.append(" AND {" + InvoiceModel.CUSTOMER + "} IN (?customer) ");
		//queryParams.put("customer", (Serializable) listB2BUnitData);

		sortQuery.append(" ORDER BY {b." + InvoiceModel.INVOICEDATE + "} desc ");

		query.append("SELECT {b." + InvoiceModel.PK + "} FROM {" + InvoiceModel._TYPECODE + " AS b JOIN ");
		query.append(B2BUnitModel._TYPECODE + " as b2b ON CAST({b." + InvoiceModel.CUSTOMER + "} AS VARCHAR(15))=CAST({b2b."
				+ B2BUnitModel.PK + "} ");
		query.append("AS VARCHAR(15)) } WHERE 1=1 AND {b2b." + B2BUnitModel.UID + "} IN (?uidCustomer) ");
		queryParams.put("uidCustomer", (Serializable) listB2BUnitUID);

		query.append(textQuery.toString());
		query.append(sortQuery.toString());

		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(0);
		pageableData.setPageSize(10);
		pageableData.setSort(null);

		return getPagedFlexibleSearchService().search(query.toString(), queryParams, pageableData);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisInvoiceService#getPDFInvoicesByB2BUnit(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPDFDocument(final String invoice, final String customer) throws Exception
	{
		// YTODO Auto-generated method stub
		return null;
	}
}
