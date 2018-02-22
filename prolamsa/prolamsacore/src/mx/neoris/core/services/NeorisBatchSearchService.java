/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import mx.neoris.core.model.BatchSearchModel;


/**
 * @author christian.loredo
 * 
 */
public interface NeorisBatchSearchService
{
	SearchPageData<BatchSearchModel> getPagedBatch(final BatchSearchParameters searchParameters) throws Exception;
}
