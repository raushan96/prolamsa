/**
 * 
 */
package mx.neoris.core.services;

import mx.neoris.core.invoice.ValidateInvoiceResponse;



/**
 * @author e-lacantu
 * 
 */

public interface NeorisValidateInvoice
{
	public ValidateInvoiceResponse validateInvoice(String invoiceNumber, String customer) throws Exception;

}