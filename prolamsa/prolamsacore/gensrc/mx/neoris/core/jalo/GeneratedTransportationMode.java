/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem TransportationMode}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTransportationMode extends GenericItem
{
	/** Qualifier of the <code>TransportationMode.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>TransportationMode.internalCode</code> attribute **/
	public static final String INTERNALCODE = "internalCode";
	/** Qualifier of the <code>TransportationMode.incotermCode</code> attribute **/
	public static final String INCOTERMCODE = "incotermCode";
	/** Qualifier of the <code>TransportationMode.maxCapacity</code> attribute **/
	public static final String MAXCAPACITY = "maxCapacity";
	/** Qualifier of the <code>TransportationMode.incotermDescription</code> attribute **/
	public static final String INCOTERMDESCRIPTION = "incotermDescription";
	/** Qualifier of the <code>TransportationMode.name</code> attribute **/
	public static final String NAME = "name";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(INTERNALCODE, AttributeMode.INITIAL);
		tmp.put(INCOTERMCODE, AttributeMode.INITIAL);
		tmp.put(MAXCAPACITY, AttributeMode.INITIAL);
		tmp.put(INCOTERMDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(NAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.incotermCode</code> attribute.
	 * @return the incotermCode
	 */
	public String getIncotermCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INCOTERMCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.incotermCode</code> attribute.
	 * @return the incotermCode
	 */
	public String getIncotermCode()
	{
		return getIncotermCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.incotermCode</code> attribute. 
	 * @param value the incotermCode
	 */
	public void setIncotermCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INCOTERMCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.incotermCode</code> attribute. 
	 * @param value the incotermCode
	 */
	public void setIncotermCode(final String value)
	{
		setIncotermCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.incotermDescription</code> attribute.
	 * @return the incotermDescription
	 */
	public String getIncotermDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTransportationMode.getIncotermDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, INCOTERMDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.incotermDescription</code> attribute.
	 * @return the incotermDescription
	 */
	public String getIncotermDescription()
	{
		return getIncotermDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.incotermDescription</code> attribute. 
	 * @return the localized incotermDescription
	 */
	public Map<Language,String> getAllIncotermDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,INCOTERMDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.incotermDescription</code> attribute. 
	 * @return the localized incotermDescription
	 */
	public Map<Language,String> getAllIncotermDescription()
	{
		return getAllIncotermDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.incotermDescription</code> attribute. 
	 * @param value the incotermDescription
	 */
	public void setIncotermDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTransportationMode.setIncotermDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, INCOTERMDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.incotermDescription</code> attribute. 
	 * @param value the incotermDescription
	 */
	public void setIncotermDescription(final String value)
	{
		setIncotermDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.incotermDescription</code> attribute. 
	 * @param value the incotermDescription
	 */
	public void setAllIncotermDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,INCOTERMDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.incotermDescription</code> attribute. 
	 * @param value the incotermDescription
	 */
	public void setAllIncotermDescription(final Map<Language,String> value)
	{
		setAllIncotermDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.internalCode</code> attribute.
	 * @return the internalCode
	 */
	public String getInternalCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INTERNALCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.internalCode</code> attribute.
	 * @return the internalCode
	 */
	public String getInternalCode()
	{
		return getInternalCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.internalCode</code> attribute. 
	 * @param value the internalCode
	 */
	public void setInternalCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INTERNALCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.internalCode</code> attribute. 
	 * @param value the internalCode
	 */
	public void setInternalCode(final String value)
	{
		setInternalCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.maxCapacity</code> attribute.
	 * @return the maxCapacity
	 */
	public Double getMaxCapacity(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, MAXCAPACITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.maxCapacity</code> attribute.
	 * @return the maxCapacity
	 */
	public Double getMaxCapacity()
	{
		return getMaxCapacity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.maxCapacity</code> attribute. 
	 * @return the maxCapacity
	 */
	public double getMaxCapacityAsPrimitive(final SessionContext ctx)
	{
		Double value = getMaxCapacity( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.maxCapacity</code> attribute. 
	 * @return the maxCapacity
	 */
	public double getMaxCapacityAsPrimitive()
	{
		return getMaxCapacityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.maxCapacity</code> attribute. 
	 * @param value the maxCapacity
	 */
	public void setMaxCapacity(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, MAXCAPACITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.maxCapacity</code> attribute. 
	 * @param value the maxCapacity
	 */
	public void setMaxCapacity(final Double value)
	{
		setMaxCapacity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.maxCapacity</code> attribute. 
	 * @param value the maxCapacity
	 */
	public void setMaxCapacity(final SessionContext ctx, final double value)
	{
		setMaxCapacity( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.maxCapacity</code> attribute. 
	 * @param value the maxCapacity
	 */
	public void setMaxCapacity(final double value)
	{
		setMaxCapacity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTransportationMode.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.name</code> attribute. 
	 * @return the localized name
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TransportationMode.name</code> attribute. 
	 * @return the localized name
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTransportationMode.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.name</code> attribute. 
	 * @param value the name
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TransportationMode.name</code> attribute. 
	 * @param value the name
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
}
