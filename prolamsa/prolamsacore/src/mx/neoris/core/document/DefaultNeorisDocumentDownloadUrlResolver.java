/**
 * 
 */
package mx.neoris.core.document;



/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisDocumentDownloadUrlResolver implements NeorisDocumentDownloadUrlResolver
{
	@Override
	public String urlDownloadDocumentFor(final String organization, final String documentType, final String billTo,
			final String documentNumber)
	{
		//TODO build the proper url for download the file
		return "http://www.objectwave.com/fdeutsch/sample.pdf";
	}
}
