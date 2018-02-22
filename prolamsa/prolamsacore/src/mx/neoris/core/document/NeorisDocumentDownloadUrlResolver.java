/**
 * 
 */
package mx.neoris.core.document;



/**
 * @author fdeutsch
 * 
 */
public interface NeorisDocumentDownloadUrlResolver
{
	String urlDownloadDocumentFor(String organization, String documentType, String billTo, String documentNumber);
}
