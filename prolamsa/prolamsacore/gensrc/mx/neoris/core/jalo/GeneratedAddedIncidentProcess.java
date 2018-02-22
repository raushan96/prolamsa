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
import de.hybris.platform.jalo.user.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.jalo.Incident;

/**
 * Generated class for type {@link de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess AddedIncidentProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAddedIncidentProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>AddedIncidentProcess.user</code> attribute **/
	public static final String USER = "user";
	/** Qualifier of the <code>AddedIncidentProcess.incident</code> attribute **/
	public static final String INCIDENT = "incident";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(USER, AttributeMode.INITIAL);
		tmp.put(INCIDENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AddedIncidentProcess.incident</code> attribute.
	 * @return the incident
	 */
	public Incident getIncident(final SessionContext ctx)
	{
		return (Incident)getProperty( ctx, INCIDENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AddedIncidentProcess.incident</code> attribute.
	 * @return the incident
	 */
	public Incident getIncident()
	{
		return getIncident( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AddedIncidentProcess.incident</code> attribute. 
	 * @param value the incident
	 */
	public void setIncident(final SessionContext ctx, final Incident value)
	{
		setProperty(ctx, INCIDENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AddedIncidentProcess.incident</code> attribute. 
	 * @param value the incident
	 */
	public void setIncident(final Incident value)
	{
		setIncident( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AddedIncidentProcess.user</code> attribute.
	 * @return the user
	 */
	public User getUser(final SessionContext ctx)
	{
		return (User)getProperty( ctx, USER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AddedIncidentProcess.user</code> attribute.
	 * @return the user
	 */
	public User getUser()
	{
		return getUser( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AddedIncidentProcess.user</code> attribute. 
	 * @param value the user
	 */
	public void setUser(final SessionContext ctx, final User value)
	{
		setProperty(ctx, USER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AddedIncidentProcess.user</code> attribute. 
	 * @param value the user
	 */
	public void setUser(final User value)
	{
		setUser( getSession().getSessionContext(), value );
	}
	
}
