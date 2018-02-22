/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import mx.neoris.core.services.DocumentSearchParameters;
import mx.neoris.facades.document.data.DocumentSearchData;



/**
 * @author christian.loredo
 *
 */
public interface NeorisDocumentSearchFacade
{
	SearchPageData<DocumentSearchData> getPagedDocuments(final DocumentSearchParameters searchParameters) throws Exception;
	void injectSOAttachmentsOn(List<DocumentSearchData> searchDataResult);
}
