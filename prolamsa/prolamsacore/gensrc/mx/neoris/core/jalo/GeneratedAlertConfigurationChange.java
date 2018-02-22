/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem AlertConfigurationChange}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAlertConfigurationChange extends GenericItem
{
	/** Qualifier of the <code>AlertConfigurationChange.changedDate</code> attribute **/
	public static final String CHANGEDDATE = "changedDate";
	/** Qualifier of the <code>AlertConfigurationChange.attribute</code> attribute **/
	public static final String ATTRIBUTE = "attribute";
	/** Qualifier of the <code>AlertConfigurationChange.newValue</code> attribute **/
	public static final String NEWVALUE = "newValue";
	/** Qualifier of the <code>AlertConfigurationChange.previuosValue</code> attribute **/
	public static final String PREVIUOSVALUE = "previuosValue";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CHANGEDDATE, AttributeMode.INITIAL);
		tmp.put(ATTRIBUTE, AttributeMode.INITIAL);
		tmp.put(NEWVALUE, AttributeMode.INITIAL);
		tmp.put(PREVIUOSVALUE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.attribute</code> attribute.
	 * @return the attribute
	 */
	public String getAttribute(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATTRIBUTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.attribute</code> attribute.
	 * @return the attribute
	 */
	public String getAttribute()
	{
		return getAttribute( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.attribute</code> attribute. 
	 * @param value the attribute
	 */
	public void setAttribute(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATTRIBUTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.attribute</code> attribute. 
	 * @param value the attribute
	 */
	public void setAttribute(final String value)
	{
		setAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.changedDate</code> attribute.
	 * @return the changedDate - The date for which to gather order total information
	 */
	public Date getChangedDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, CHANGEDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.changedDate</code> attribute.
	 * @return the changedDate - The date for which to gather order total information
	 */
	public Date getChangedDate()
	{
		return getChangedDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.changedDate</code> attribute. 
	 * @param value the changedDate - The date for which to gather order total information
	 */
	public void setChangedDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, CHANGEDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.changedDate</code> attribute. 
	 * @param value the changedDate - The date for which to gather order total information
	 */
	public void setChangedDate(final Date value)
	{
		setChangedDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.newValue</code> attribute.
	 * @return the newValue
	 */
	public String getNewValue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NEWVALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.newValue</code> attribute.
	 * @return the newValue
	 */
	public String getNewValue()
	{
		return getNewValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.newValue</code> attribute. 
	 * @param value the newValue
	 */
	public void setNewValue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NEWVALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.newValue</code> attribute. 
	 * @param value the newValue
	 */
	public void setNewValue(final String value)
	{
		setNewValue( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.previuosValue</code> attribute.
	 * @return the previuosValue
	 */
	public String getPreviuosValue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PREVIUOSVALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfigurationChange.previuosValue</code> attribute.
	 * @return the previuosValue
	 */
	public String getPreviuosValue()
	{
		return getPreviuosValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.previuosValue</code> attribute. 
	 * @param value the previuosValue
	 */
	public void setPreviuosValue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PREVIUOSVALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfigurationChange.previuosValue</code> attribute. 
	 * @param value the previuosValue
	 */
	public void setPreviuosValue(final String value)
	{
		setPreviuosValue( getSession().getSessionContext(), value );
	}
	
}
