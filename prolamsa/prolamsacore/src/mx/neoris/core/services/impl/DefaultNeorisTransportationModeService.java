/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.services.NeorisTransportationModeService;




/**
 * @author e-lacantu
 * 
 */
public class DefaultNeorisTransportationModeService implements NeorisTransportationModeService
{
	private FlexibleSearchService flexibleSearchService;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisTransportationModeService#getTransportationModeForCode(java.lang.String)
	 */
	@Override
	public TransportationModeModel getTransportationModeForCode(final String code) throws Exception
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {pk} FROM {TransportationMode} WHERE {code} ='" + code
				+ "'");

		final SearchResult<TransportationModeModel> result = flexibleSearchService.search(fQuery);

		return result.getResult().get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisTransportationModeService#getTransportationModeForCodeForXML(java.lang.String)
	 */
	@Override
	public TransportationModeModel getTransportationModeForCodeForXML(final String code, final String incoterm) throws Exception
	{
		String queryString = "SELECT {" + TransportationModeModel.PK + "} FROM {" + TransportationModeModel._TYPECODE + "} WHERE {"
				+ TransportationModeModel.CODE + "} = ?code  ";

		if (incoterm != null || incoterm != "")
		{
			queryString += " AND {" + TransportationModeModel.INCOTERMCODE + "} = ?incoterm ";
		}

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", code);

		if (incoterm != null || incoterm != "")
		{
			query.addQueryParameter("incoterm", incoterm);
		}

		final SearchResult<TransportationModeModel> result = flexibleSearchService.search(query);

		return result.getResult().get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisTransportationModeService#getTransportationModesForCode(java.lang.String)
	 */
	@Override
	public List<TransportationModeModel> getTransportationModesForCode(final String code) throws Exception
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {pk} FROM {TransportationMode} WHERE {code} =" + code);

		final SearchResult<TransportationModeModel> result = flexibleSearchService.search(fQuery);

		return result.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisTransportationModeService#getTransportationModeForInternalCode(java.lang.String)
	 */
	@Override
	public TransportationModeModel getTransportationModeForInternalCode(final String internalCode) throws Exception
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {pk} FROM {TransportationMode} WHERE {"
				+ TransportationModeModel.INTERNALCODE + "} ='" + internalCode + "'");

		final SearchResult<TransportationModeModel> result = flexibleSearchService.search(fQuery);

		return result.getResult().get(0);
	}

}
