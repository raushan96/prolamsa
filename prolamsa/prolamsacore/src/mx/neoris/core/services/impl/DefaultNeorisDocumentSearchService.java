/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import mx.neoris.core.model.DocumentSearchModel;
import mx.neoris.core.services.DocumentSearchParameters;
import mx.neoris.core.services.NeorisDocumentSearchService;


/**
 * @author christian.loredo
 * 
 */
public class DefaultNeorisDocumentSearchService implements NeorisDocumentSearchService
{
	private FlexibleSearchService flexibleSearchService;
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisDocumentSearchService#getPagedDocuments(mx.neoris.core.services.DocumentSearchParameters
	 * )
	 */
	@Override
	public SearchPageData<DocumentSearchModel> getPagedDocuments(final DocumentSearchParameters searchParameters) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final String query = "SELECT {PK} FROM {DocumentSearch} WHERE 1 = 1";

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
