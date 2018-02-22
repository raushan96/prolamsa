/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import mx.neoris.core.services.BatchSearchParameters;
import mx.neoris.facades.document.data.BatchSearchData;
import mx.neoris.facades.document.data.DocumentSearchData;


/**
 * @author christian.loredo
 *
 */
public interface NeorisBatchSearchFacade {
	SearchPageData<BatchSearchData> getPagedBatch(final BatchSearchParameters searchParameters) throws Exception;
	void injectSOAttachmentsOn(List<BatchSearchData> searchDataResult);
}
