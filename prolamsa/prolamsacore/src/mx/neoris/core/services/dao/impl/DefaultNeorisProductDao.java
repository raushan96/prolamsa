/**
 * 
 */
package mx.neoris.core.services.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.product.daos.impl.DefaultProductDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.dao.NeorisProductDao;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisProductDao extends DefaultProductDao implements NeorisProductDao
{
	public DefaultNeorisProductDao(final String typecode)
	{
		super(typecode);
	}

	@Override
	public List<ProlamsaProductModel> findProductsByBaseCode(final String baseCode)
	{
		validateParameterNotNull(baseCode, "Product base code must not be null!");

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + ProlamsaProductModel.PK + "}  ");
		queryString.append("FROM {" + ProlamsaProductModel._TYPECODE + " AS p } ");
		queryString.append("WHERE {p." + ProlamsaProductModel.BASECODE + "} = '" + baseCode + "' ");

		final SearchResult<ProlamsaProductModel> result = getFlexibleSearchService().search(queryString.toString());

		if (result == null || result.getCount() == 0)
		{
			return null;
		}
		else
		{
			return result.getResult();
		}
	}

	public List<ProlamsaProductModel> findProductsByBaseCodeAndLocation(final String baseCode, final ProductLocation location)
	{
		validateParameterNotNull(baseCode, "Product base code must not be null!");
		validateParameterNotNull(location, "Product location  must not be null!");

		final StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT {p." + ProlamsaProductModel.PK + "}  ");
		queryString.append("FROM {" + ProlamsaProductModel._TYPECODE + " AS p } ");
		queryString.append("WHERE {p." + ProlamsaProductModel.BASECODE + "} = ?baseCode ");
		queryString.append("AND {p." + ProlamsaProductModel.LOCATION + "} = ?location");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("baseCode", baseCode);
		query.addQueryParameter("location", location);

		final SearchResult<ProlamsaProductModel> result = getFlexibleSearchService().search(query);

		if (result == null || result.getCount() == 0)
		{
			return null;
		}
		else
		{
			return result.getResult();
		}
	}


	@Override
	public List<ProlamsaProductModel> findAllProductsFor(final CatalogVersionModel catalogVersionModel)
	{
		final StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT {p." + ProlamsaProductModel.PK + "}  ");
		queryString.append("FROM {" + ProlamsaProductModel._TYPECODE + " AS p } ");
		queryString.append("WHERE {p." + ProlamsaProductModel.CATALOGVERSION + "} = ?catalogVersion ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("catalogVersion", catalogVersionModel);

		final SearchResult<ProlamsaProductModel> result = getFlexibleSearchService().search(query);

		if (result == null || result.getCount() == 0)
		{
			return null;
		}
		else
		{
			return result.getResult();
		}
	}
}
