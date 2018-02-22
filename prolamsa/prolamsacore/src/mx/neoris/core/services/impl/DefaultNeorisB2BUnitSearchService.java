/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import mx.neoris.core.services.B2BUnitSearchParameters;
import mx.neoris.core.services.NeorisB2BUnitSearchService;


/**
 * @author e-lacantu
 * 
 */
public class DefaultNeorisB2BUnitSearchService implements NeorisB2BUnitSearchService
{

	private FlexibleSearchService flexibleSearchService;
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisB2BUnitSearchService#getB2BUnitBySearch(java.lang.String, java.lang.String)
	 */
	@Override
	public SearchPageData<B2BUnitModel> getB2BUnitBySearch(final B2BUnitSearchParameters searchParameters) throws Exception
	{
		final StringBuilder query = new StringBuilder();
		final StringBuilder sortQuery = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		Assert.assertNotNull("Base store Pk cannot be null", searchParameters.getBaseStorePk());

		query.append("SELECT {b2b." + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE + " as b2b ");

		boolean filterByUser = false;
		if (searchParameters.getCurrentUserPk() != null)
		{
			// Filter by specific b2b customer pk
			query.append("JOIN PrincipalGroupRelation as p ON ");
			query.append("{p.target} = {b2b.pk} ");

			filterByUser = true;
		}

		// Filter by current base store
		query.append("JOIN B2BUnits2BaseStores as u2br ON ");
		query.append("{b2b." + B2BUnitModel.PK + "} = {u2br.source}} ");
		query.append("WHERE {u2br.target} = ?storePk ");
		// Adding base store pk parameter
		queryParams.put("storePk", searchParameters.getBaseStorePk());


		if (filterByUser)
		{
			query.append("AND {p.source} = ?userPk ");
			// Adding customer pk parameter
			queryParams.put("userPk", searchParameters.getCurrentUserPk());
		}

		if (searchParameters.getUid() != null && searchParameters.getUid().trim().length() > 0)
		{
			query.append("AND {b2b." + B2BUnitModel.UID + "} LIKE (?uid) ");
			if (searchParameters.getName() != null && searchParameters.getName().trim().length() > 0)
			{
				query.append("OR {b2b." + B2BUnitModel.LOCNAME + "} LIKE (?name) ");
			}
		}
		else
		{
			if (searchParameters.getName() != null && searchParameters.getName().trim().length() > 0)
			{
				query.append("AND {b2b." + B2BUnitModel.LOCNAME + "} LIKE (?name) ");
			}
		}

		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append("ORDER BY {b2b.");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());
		}

		query.append(sortQuery);

		queryParams.put("uid", "%" + searchParameters.getUid() + "%");
		queryParams.put("name", "%" + searchParameters.getName() + "%");

		return getPagedFlexibleSearchService().search(query.toString(), queryParams, searchParameters.getPageableData());
	}

	@Override
	public List<B2BUnitModel> getAllB2BUnitsOnSystem() throws Exception
	{
		final StringBuilder query = new StringBuilder();
		query.append("SELECT {b2b." + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE + " as b2b}");

		final FlexibleSearchQuery queryFlex = new FlexibleSearchQuery(query);
		final SearchResult<B2BUnitModel> searchResult = flexibleSearchService.search(queryFlex);

		return searchResult.getResult();
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
	 * @see mx.neoris.core.services.NeorisB2BUnitSearchService#getAddresessForSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public List<AddressModel> getAddresessForSearch(final B2BUnitSearchParameters searchParameters)
	{
		final StringBuilder query = new StringBuilder();
		final StringBuilder sortQuery = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		query.append("SELECT {a." + AddressModel.PK + "} FROM {" + AddressModel._TYPECODE + " as a } ");
		query.append("WHERE {" + AddressModel.SHIPPINGADDRESS + "} = '1' ");

		if (searchParameters.getUid() != "")
		{
			query.append("AND {a." + AddressModel.CODE + "} LIKE (?uid) ");
			if (searchParameters.getName() != null && searchParameters.getName().trim() != "")
			{
				query.append("OR {a." + AddressModel.SHORTNAME + "} LIKE (?name) ");
			}
		}
		else
		{
			if (searchParameters.getName() != null && searchParameters.getName().trim() != "")
			{
				query.append("AND {a." + AddressModel.SHORTNAME + "} LIKE (?name) ");
			}
			else
			{
				query.append("AND 1 = 2 ");
			}
		}


		if (searchParameters.getPk() != null && searchParameters.getPk() != "")
		{
			query.append(" AND {a." + AddressModel.OWNER + "} IN (?pk) ");
		}

		sortQuery.append(" ORDER BY {a." + AddressModel.CODE + "} ");
		query.append(sortQuery);

		queryParams.put("uid", "%" + searchParameters.getUid() + "%");
		queryParams.put("name", "%" + searchParameters.getName() + "%");
		queryParams.put("pk", searchParameters.getPk());


		final FlexibleSearchQuery queryFlex = new FlexibleSearchQuery(query);
		queryFlex.addQueryParameters(queryParams);

		final SearchResult<AddressModel> searchResult = flexibleSearchService.search(queryFlex);

		return searchResult.getResult();

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisB2BUnitSearchService#getAddresessForSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public List<AddressModel> getAddresessForSearch(final B2BUnitSearchParameters searchParameters, final List<String> listPk)
	{
		final StringBuilder query = new StringBuilder();
		final StringBuilder sortQuery = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		query.append("SELECT {a." + AddressModel.PK + "} FROM {" + AddressModel._TYPECODE + " as a } ");
		query.append("WHERE {a." + AddressModel.SHIPPINGADDRESS + "} = '1' ");

		if (searchParameters.getUid() != "")
		{
			query.append("AND {a." + AddressModel.CODE + "} LIKE (?uid) ");
			if (searchParameters.getName() != null && searchParameters.getName().trim() != "")
			{
				query.append("OR {a." + AddressModel.SHORTNAME + "} LIKE (?name) ");
			}
		}
		else
		{
			if (searchParameters.getName() != null && searchParameters.getName().trim() != "")
			{
				query.append("AND {a." + AddressModel.SHORTNAME + "} LIKE (?name) ");
			}
			else
			{
				query.append("AND 1 = 2 ");
			}
		}


		if (searchParameters.getPk() != null && searchParameters.getPk() != "")
		{
			//condition for order history selection
			if (searchParameters.getPk().equalsIgnoreCase("all"))
			{
				query.append(" AND {a." + AddressModel.OWNER + "} IN (?pk) ");
				queryParams.put("pk", (Serializable) listPk);
			}
			else
			{
				query.append(" AND {a." + AddressModel.OWNER + "} = ?pk ");
				queryParams.put("pk", searchParameters.getPk());
			}

		}


		sortQuery.append(" ORDER BY {a." + AddressModel.CODE + "} ");
		query.append(sortQuery);

		queryParams.put("uid", "%" + searchParameters.getUid() + "%");
		queryParams.put("name", "%" + searchParameters.getName() + "%");


		final FlexibleSearchQuery queryFlex = new FlexibleSearchQuery(query);
		queryFlex.addQueryParameters(queryParams);

		final SearchResult<AddressModel> searchResult = flexibleSearchService.search(queryFlex);

		return searchResult.getResult();

	}

}
