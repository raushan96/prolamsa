/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import mx.neoris.core.services.BackorderDetailSearchParameter;
import mx.neoris.core.services.BackorderSearchParameters;
import mx.neoris.facades.backorder.data.BackorderData;
import mx.neoris.facades.backorder.data.BackorderDetailData;

/**
 * @author christian.loredo
 * 
 */
public interface NeorisBackorderFacade
{

	SearchPageData<BackorderData> getPagedBackorder(final BackorderSearchParameters searchParameters) throws Exception;

	SearchPageData<BackorderDetailData> getPagedBackorderDetail(final BackorderDetailSearchParameter searchParameters) throws Exception;
	
	SearchPageData<BackorderData> getPagedBackorderDetailExcel(final BackorderSearchParameters searchParameters) throws Exception;
}
