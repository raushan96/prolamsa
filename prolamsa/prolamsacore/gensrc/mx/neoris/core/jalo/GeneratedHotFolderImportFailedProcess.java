/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess HotFolderImportFailedProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedHotFolderImportFailedProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>HotFolderImportFailedProcess.filename</code> attribute **/
	public static final String FILENAME = "filename";
	/** Qualifier of the <code>HotFolderImportFailedProcess.stackTrace</code> attribute **/
	public static final String STACKTRACE = "stackTrace";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FILENAME, AttributeMode.INITIAL);
		tmp.put(STACKTRACE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HotFolderImportFailedProcess.filename</code> attribute.
	 * @return the filename
	 */
	public String getFilename(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HotFolderImportFailedProcess.filename</code> attribute.
	 * @return the filename
	 */
	public String getFilename()
	{
		return getFilename( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HotFolderImportFailedProcess.filename</code> attribute. 
	 * @param value the filename
	 */
	public void setFilename(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HotFolderImportFailedProcess.filename</code> attribute. 
	 * @param value the filename
	 */
	public void setFilename(final String value)
	{
		setFilename( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HotFolderImportFailedProcess.stackTrace</code> attribute.
	 * @return the stackTrace
	 */
	public String getStackTrace(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STACKTRACE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HotFolderImportFailedProcess.stackTrace</code> attribute.
	 * @return the stackTrace
	 */
	public String getStackTrace()
	{
		return getStackTrace( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HotFolderImportFailedProcess.stackTrace</code> attribute. 
	 * @param value the stackTrace
	 */
	public void setStackTrace(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STACKTRACE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HotFolderImportFailedProcess.stackTrace</code> attribute. 
	 * @param value the stackTrace
	 */
	public void setStackTrace(final String value)
	{
		setStackTrace( getSession().getSessionContext(), value );
	}
	
}
