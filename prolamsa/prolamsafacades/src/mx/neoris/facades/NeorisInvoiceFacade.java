/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.facades.invoice.data.InvoiceData;

/**
 * @author fdeutsch
 *
 */
public interface NeorisInvoiceFacade
{
	SearchPageData<InvoiceData> getPagedInvoices(final InvoiceSearchParameters searchParameters) throws Exception;
	SearchPageData<InvoiceData> getPagedInvoicesByRange(final InvoiceSearchParameters searchParameters, String range) throws Exception;
	List<InvoiceData> getInvoicesByB2BUnit(final List<B2BUnitData> listB2BUnitData) throws Exception;	
	String getPDFDocument(String invoice, String customer) throws Exception;
}