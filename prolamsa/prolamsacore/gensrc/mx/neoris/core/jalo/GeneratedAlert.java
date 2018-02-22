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
import java.util.List;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.jalo.AlertProductOption;
import mx.neoris.core.jalo.AlertTimeOption;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem Alert}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAlert extends GenericItem
{
	/** Qualifier of the <code>Alert.hasMonthlyOption</code> attribute **/
	public static final String HASMONTHLYOPTION = "hasMonthlyOption";
	/** Qualifier of the <code>Alert.hasDayOptions</code> attribute **/
	public static final String HASDAYOPTIONS = "hasDayOptions";
	/** Qualifier of the <code>Alert.hasCCEmailOptions</code> attribute **/
	public static final String HASCCEMAILOPTIONS = "hasCCEmailOptions";
	/** Qualifier of the <code>Alert.hasTimeOptions</code> attribute **/
	public static final String HASTIMEOPTIONS = "hasTimeOptions";
	/** Qualifier of the <code>Alert.hasMTROption</code> attribute **/
	public static final String HASMTROPTION = "hasMTROption";
	/** Qualifier of the <code>Alert.productOptions</code> attribute **/
	public static final String PRODUCTOPTIONS = "productOptions";
	/** Qualifier of the <code>Alert.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>Alert.hasWeeklyOption</code> attribute **/
	public static final String HASWEEKLYOPTION = "hasWeeklyOption";
	/** Qualifier of the <code>Alert.hasProductOptions</code> attribute **/
	public static final String HASPRODUCTOPTIONS = "hasProductOptions";
	/** Qualifier of the <code>Alert.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>Alert.timeOptions</code> attribute **/
	public static final String TIMEOPTIONS = "timeOptions";
	/** Qualifier of the <code>Alert.hasDailyOption</code> attribute **/
	public static final String HASDAILYOPTION = "hasDailyOption";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(HASMONTHLYOPTION, AttributeMode.INITIAL);
		tmp.put(HASDAYOPTIONS, AttributeMode.INITIAL);
		tmp.put(HASCCEMAILOPTIONS, AttributeMode.INITIAL);
		tmp.put(HASTIMEOPTIONS, AttributeMode.INITIAL);
		tmp.put(HASMTROPTION, AttributeMode.INITIAL);
		tmp.put(PRODUCTOPTIONS, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(HASWEEKLYOPTION, AttributeMode.INITIAL);
		tmp.put(HASPRODUCTOPTIONS, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(TIMEOPTIONS, AttributeMode.INITIAL);
		tmp.put(HASDAILYOPTION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAlert.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAlert.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasCCEmailOptions</code> attribute.
	 * @return the hasCCEmailOptions
	 */
	public Boolean isHasCCEmailOptions(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASCCEMAILOPTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasCCEmailOptions</code> attribute.
	 * @return the hasCCEmailOptions
	 */
	public Boolean isHasCCEmailOptions()
	{
		return isHasCCEmailOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasCCEmailOptions</code> attribute. 
	 * @return the hasCCEmailOptions
	 */
	public boolean isHasCCEmailOptionsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasCCEmailOptions( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasCCEmailOptions</code> attribute. 
	 * @return the hasCCEmailOptions
	 */
	public boolean isHasCCEmailOptionsAsPrimitive()
	{
		return isHasCCEmailOptionsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasCCEmailOptions</code> attribute. 
	 * @param value the hasCCEmailOptions
	 */
	public void setHasCCEmailOptions(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASCCEMAILOPTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasCCEmailOptions</code> attribute. 
	 * @param value the hasCCEmailOptions
	 */
	public void setHasCCEmailOptions(final Boolean value)
	{
		setHasCCEmailOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasCCEmailOptions</code> attribute. 
	 * @param value the hasCCEmailOptions
	 */
	public void setHasCCEmailOptions(final SessionContext ctx, final boolean value)
	{
		setHasCCEmailOptions( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasCCEmailOptions</code> attribute. 
	 * @param value the hasCCEmailOptions
	 */
	public void setHasCCEmailOptions(final boolean value)
	{
		setHasCCEmailOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDailyOption</code> attribute.
	 * @return the hasDailyOption
	 */
	public Boolean isHasDailyOption(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASDAILYOPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDailyOption</code> attribute.
	 * @return the hasDailyOption
	 */
	public Boolean isHasDailyOption()
	{
		return isHasDailyOption( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDailyOption</code> attribute. 
	 * @return the hasDailyOption
	 */
	public boolean isHasDailyOptionAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasDailyOption( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDailyOption</code> attribute. 
	 * @return the hasDailyOption
	 */
	public boolean isHasDailyOptionAsPrimitive()
	{
		return isHasDailyOptionAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDailyOption</code> attribute. 
	 * @param value the hasDailyOption
	 */
	public void setHasDailyOption(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASDAILYOPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDailyOption</code> attribute. 
	 * @param value the hasDailyOption
	 */
	public void setHasDailyOption(final Boolean value)
	{
		setHasDailyOption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDailyOption</code> attribute. 
	 * @param value the hasDailyOption
	 */
	public void setHasDailyOption(final SessionContext ctx, final boolean value)
	{
		setHasDailyOption( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDailyOption</code> attribute. 
	 * @param value the hasDailyOption
	 */
	public void setHasDailyOption(final boolean value)
	{
		setHasDailyOption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDayOptions</code> attribute.
	 * @return the hasDayOptions
	 */
	public Boolean isHasDayOptions(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASDAYOPTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDayOptions</code> attribute.
	 * @return the hasDayOptions
	 */
	public Boolean isHasDayOptions()
	{
		return isHasDayOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDayOptions</code> attribute. 
	 * @return the hasDayOptions
	 */
	public boolean isHasDayOptionsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasDayOptions( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasDayOptions</code> attribute. 
	 * @return the hasDayOptions
	 */
	public boolean isHasDayOptionsAsPrimitive()
	{
		return isHasDayOptionsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDayOptions</code> attribute. 
	 * @param value the hasDayOptions
	 */
	public void setHasDayOptions(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASDAYOPTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDayOptions</code> attribute. 
	 * @param value the hasDayOptions
	 */
	public void setHasDayOptions(final Boolean value)
	{
		setHasDayOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDayOptions</code> attribute. 
	 * @param value the hasDayOptions
	 */
	public void setHasDayOptions(final SessionContext ctx, final boolean value)
	{
		setHasDayOptions( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasDayOptions</code> attribute. 
	 * @param value the hasDayOptions
	 */
	public void setHasDayOptions(final boolean value)
	{
		setHasDayOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMonthlyOption</code> attribute.
	 * @return the hasMonthlyOption
	 */
	public Boolean isHasMonthlyOption(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASMONTHLYOPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMonthlyOption</code> attribute.
	 * @return the hasMonthlyOption
	 */
	public Boolean isHasMonthlyOption()
	{
		return isHasMonthlyOption( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMonthlyOption</code> attribute. 
	 * @return the hasMonthlyOption
	 */
	public boolean isHasMonthlyOptionAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasMonthlyOption( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMonthlyOption</code> attribute. 
	 * @return the hasMonthlyOption
	 */
	public boolean isHasMonthlyOptionAsPrimitive()
	{
		return isHasMonthlyOptionAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMonthlyOption</code> attribute. 
	 * @param value the hasMonthlyOption
	 */
	public void setHasMonthlyOption(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASMONTHLYOPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMonthlyOption</code> attribute. 
	 * @param value the hasMonthlyOption
	 */
	public void setHasMonthlyOption(final Boolean value)
	{
		setHasMonthlyOption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMonthlyOption</code> attribute. 
	 * @param value the hasMonthlyOption
	 */
	public void setHasMonthlyOption(final SessionContext ctx, final boolean value)
	{
		setHasMonthlyOption( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMonthlyOption</code> attribute. 
	 * @param value the hasMonthlyOption
	 */
	public void setHasMonthlyOption(final boolean value)
	{
		setHasMonthlyOption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMTROption</code> attribute.
	 * @return the hasMTROption
	 */
	public Boolean isHasMTROption(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASMTROPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMTROption</code> attribute.
	 * @return the hasMTROption
	 */
	public Boolean isHasMTROption()
	{
		return isHasMTROption( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMTROption</code> attribute. 
	 * @return the hasMTROption
	 */
	public boolean isHasMTROptionAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasMTROption( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasMTROption</code> attribute. 
	 * @return the hasMTROption
	 */
	public boolean isHasMTROptionAsPrimitive()
	{
		return isHasMTROptionAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMTROption</code> attribute. 
	 * @param value the hasMTROption
	 */
	public void setHasMTROption(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASMTROPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMTROption</code> attribute. 
	 * @param value the hasMTROption
	 */
	public void setHasMTROption(final Boolean value)
	{
		setHasMTROption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMTROption</code> attribute. 
	 * @param value the hasMTROption
	 */
	public void setHasMTROption(final SessionContext ctx, final boolean value)
	{
		setHasMTROption( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasMTROption</code> attribute. 
	 * @param value the hasMTROption
	 */
	public void setHasMTROption(final boolean value)
	{
		setHasMTROption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasProductOptions</code> attribute.
	 * @return the hasProductOptions
	 */
	public Boolean isHasProductOptions(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASPRODUCTOPTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasProductOptions</code> attribute.
	 * @return the hasProductOptions
	 */
	public Boolean isHasProductOptions()
	{
		return isHasProductOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasProductOptions</code> attribute. 
	 * @return the hasProductOptions
	 */
	public boolean isHasProductOptionsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasProductOptions( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasProductOptions</code> attribute. 
	 * @return the hasProductOptions
	 */
	public boolean isHasProductOptionsAsPrimitive()
	{
		return isHasProductOptionsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasProductOptions</code> attribute. 
	 * @param value the hasProductOptions
	 */
	public void setHasProductOptions(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASPRODUCTOPTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasProductOptions</code> attribute. 
	 * @param value the hasProductOptions
	 */
	public void setHasProductOptions(final Boolean value)
	{
		setHasProductOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasProductOptions</code> attribute. 
	 * @param value the hasProductOptions
	 */
	public void setHasProductOptions(final SessionContext ctx, final boolean value)
	{
		setHasProductOptions( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasProductOptions</code> attribute. 
	 * @param value the hasProductOptions
	 */
	public void setHasProductOptions(final boolean value)
	{
		setHasProductOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasTimeOptions</code> attribute.
	 * @return the hasTimeOptions
	 */
	public Boolean isHasTimeOptions(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASTIMEOPTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasTimeOptions</code> attribute.
	 * @return the hasTimeOptions
	 */
	public Boolean isHasTimeOptions()
	{
		return isHasTimeOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasTimeOptions</code> attribute. 
	 * @return the hasTimeOptions
	 */
	public boolean isHasTimeOptionsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasTimeOptions( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasTimeOptions</code> attribute. 
	 * @return the hasTimeOptions
	 */
	public boolean isHasTimeOptionsAsPrimitive()
	{
		return isHasTimeOptionsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasTimeOptions</code> attribute. 
	 * @param value the hasTimeOptions
	 */
	public void setHasTimeOptions(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASTIMEOPTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasTimeOptions</code> attribute. 
	 * @param value the hasTimeOptions
	 */
	public void setHasTimeOptions(final Boolean value)
	{
		setHasTimeOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasTimeOptions</code> attribute. 
	 * @param value the hasTimeOptions
	 */
	public void setHasTimeOptions(final SessionContext ctx, final boolean value)
	{
		setHasTimeOptions( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasTimeOptions</code> attribute. 
	 * @param value the hasTimeOptions
	 */
	public void setHasTimeOptions(final boolean value)
	{
		setHasTimeOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasWeeklyOption</code> attribute.
	 * @return the hasWeeklyOption
	 */
	public Boolean isHasWeeklyOption(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASWEEKLYOPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasWeeklyOption</code> attribute.
	 * @return the hasWeeklyOption
	 */
	public Boolean isHasWeeklyOption()
	{
		return isHasWeeklyOption( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasWeeklyOption</code> attribute. 
	 * @return the hasWeeklyOption
	 */
	public boolean isHasWeeklyOptionAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasWeeklyOption( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.hasWeeklyOption</code> attribute. 
	 * @return the hasWeeklyOption
	 */
	public boolean isHasWeeklyOptionAsPrimitive()
	{
		return isHasWeeklyOptionAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasWeeklyOption</code> attribute. 
	 * @param value the hasWeeklyOption
	 */
	public void setHasWeeklyOption(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASWEEKLYOPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasWeeklyOption</code> attribute. 
	 * @param value the hasWeeklyOption
	 */
	public void setHasWeeklyOption(final Boolean value)
	{
		setHasWeeklyOption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasWeeklyOption</code> attribute. 
	 * @param value the hasWeeklyOption
	 */
	public void setHasWeeklyOption(final SessionContext ctx, final boolean value)
	{
		setHasWeeklyOption( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.hasWeeklyOption</code> attribute. 
	 * @param value the hasWeeklyOption
	 */
	public void setHasWeeklyOption(final boolean value)
	{
		setHasWeeklyOption( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.productOptions</code> attribute.
	 * @return the productOptions
	 */
	public List<AlertProductOption> getProductOptions(final SessionContext ctx)
	{
		List<AlertProductOption> coll = (List<AlertProductOption>)getProperty( ctx, PRODUCTOPTIONS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.productOptions</code> attribute.
	 * @return the productOptions
	 */
	public List<AlertProductOption> getProductOptions()
	{
		return getProductOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.productOptions</code> attribute. 
	 * @param value the productOptions
	 */
	public void setProductOptions(final SessionContext ctx, final List<AlertProductOption> value)
	{
		setProperty(ctx, PRODUCTOPTIONS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.productOptions</code> attribute. 
	 * @param value the productOptions
	 */
	public void setProductOptions(final List<AlertProductOption> value)
	{
		setProductOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.timeOptions</code> attribute.
	 * @return the timeOptions
	 */
	public List<AlertTimeOption> getTimeOptions(final SessionContext ctx)
	{
		List<AlertTimeOption> coll = (List<AlertTimeOption>)getProperty( ctx, TIMEOPTIONS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Alert.timeOptions</code> attribute.
	 * @return the timeOptions
	 */
	public List<AlertTimeOption> getTimeOptions()
	{
		return getTimeOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.timeOptions</code> attribute. 
	 * @param value the timeOptions
	 */
	public void setTimeOptions(final SessionContext ctx, final List<AlertTimeOption> value)
	{
		setProperty(ctx, TIMEOPTIONS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Alert.timeOptions</code> attribute. 
	 * @param value the timeOptions
	 */
	public void setTimeOptions(final List<AlertTimeOption> value)
	{
		setTimeOptions( getSession().getSessionContext(), value );
	}
	
}
