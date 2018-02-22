/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import mx.neoris.core.model.InvoiceModel;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisInvoiceService
{
	SearchPageData<InvoiceModel> getPagedInvoices(final InvoiceSearchParameters searchParameters) throws Exception;

	SearchPageData<InvoiceModel> getPagedInvoicesByRange(final InvoiceSearchParameters searchParameters, final String range)
			throws Exception;

	SearchPageData<InvoiceModel> getInvoicesByB2BUnit(List<B2BUnitData> listB2BUnitData) throws Exception;

	String getPDFDocument(String invoice, String customer) throws Exception;
}
