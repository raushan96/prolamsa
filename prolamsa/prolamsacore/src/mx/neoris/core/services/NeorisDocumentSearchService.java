/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import mx.neoris.core.model.DocumentSearchModel;


/**
 * @author christian.loredo
 * 
 */
public interface NeorisDocumentSearchService
{
	SearchPageData<DocumentSearchModel> getPagedDocuments(final DocumentSearchParameters searchParameters) throws Exception;
}
