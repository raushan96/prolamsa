/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import mx.neoris.core.model.BackorderDetailModel;
import mx.neoris.core.model.BackorderModel;


/**
 * @author christian.loredo
 * 
 */
public interface NeorisBackorderService
{
	SearchPageData<BackorderModel> getPagedBackorder(final BackorderSearchParameters searchParameters) throws Exception;

	SearchPageData<BackorderDetailModel> getPagedBackorderDetail(final BackorderDetailSearchParameter searchParameters)
			throws Exception;

	SearchPageData<BackorderModel> getPagedBackorderDetailExcel(final BackorderSearchParameters searchParameters) throws Exception;

}