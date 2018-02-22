/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.SearchResult;

import mx.neoris.core.model.BalanceStatementDetailModel;
import mx.neoris.core.model.InvoiceModel;


/**
 * @author e-lacantu
 * 
 */
public interface NeorisBalanceStatementService
{
	SearchPageData<BalanceStatementDetailModel> getPagedBalanceStatement(final BalanceStatementSearchParameters searchParameters)
			throws Exception;

	SearchResult<BalanceStatementDetailModel> getBalanceStatementDetail(BalanceStatementSearchParameters searchParameters,
			Integer row) throws Exception;

	SearchPageData<InvoiceModel> getPagedBalanceStatementInvoices(final InvoiceSearchParameters searchParameters) throws Exception;

}
