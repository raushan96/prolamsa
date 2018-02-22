/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.cronjob.jalo.CronJob LostOrderJob}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedLostOrderJob extends CronJob
{
	/** Qualifier of the <code>LostOrderJob.lastSuccessfulDate</code> attribute **/
	public static final String LASTSUCCESSFULDATE = "lastSuccessfulDate";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CronJob.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LASTSUCCESSFULDATE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LostOrderJob.lastSuccessfulDate</code> attribute.
	 * @return the lastSuccessfulDate - this date will get updated when job will finish successfully
	 */
	public Date getLastSuccessfulDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, LASTSUCCESSFULDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LostOrderJob.lastSuccessfulDate</code> attribute.
	 * @return the lastSuccessfulDate - this date will get updated when job will finish successfully
	 */
	public Date getLastSuccessfulDate()
	{
		return getLastSuccessfulDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LostOrderJob.lastSuccessfulDate</code> attribute. 
	 * @param value the lastSuccessfulDate - this date will get updated when job will finish successfully
	 */
	public void setLastSuccessfulDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, LASTSUCCESSFULDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LostOrderJob.lastSuccessfulDate</code> attribute. 
	 * @param value the lastSuccessfulDate - this date will get updated when job will finish successfully
	 */
	public void setLastSuccessfulDate(final Date value)
	{
		setLastSuccessfulDate( getSession().getSessionContext(), value );
	}
	
}
