/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.facades.balancestatement.data.BalanceStatementData;
import mx.neoris.facades.balancestatement.data.BalanceStatementDetailData;
import mx.neoris.facades.invoice.data.InvoiceData;


/**
 * @author e-lacantu
 *
 */
public interface NeorisBalanceStatementFacade {
	SearchPageData<BalanceStatementDetailData> getPagedBalanceStatement(final BalanceStatementSearchParameters searchParameters) throws Exception;
	
	BalanceStatementDetailData getBalanceStatementDetail(final BalanceStatementSearchParameters searchParameters, Integer row) throws Exception;
	
	SearchPageData<InvoiceData> getPagedBalanceStatementInvoices(final InvoiceSearchParameters searchParameters) throws Exception;
}
