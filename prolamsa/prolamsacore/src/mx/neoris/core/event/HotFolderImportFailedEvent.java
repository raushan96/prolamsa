/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author fdeutsch
 * 
 */
public class HotFolderImportFailedEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String filename;
	String stackTrace;

	/**
	 * @return the filename
	 */
	public String getFilename()
	{
		return filename;
	}

	/**
	 * @param filename
	 *           the filename to set
	 */
	public void setFilename(final String filename)
	{
		this.filename = filename;
	}

	/**
	 * @return the stackTrace
	 */
	public String getStackTrace()
	{
		return stackTrace;
	}

	/**
	 * @param stackTrace
	 *           the stackTrace to set
	 */
	public void setStackTrace(final String stackTrace)
	{
		this.stackTrace = stackTrace;
	}
}
