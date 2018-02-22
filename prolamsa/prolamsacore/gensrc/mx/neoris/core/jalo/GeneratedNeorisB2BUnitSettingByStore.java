/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.b2b.jalo.B2BUnit;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem NeorisB2BUnitSettingByStore}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedNeorisB2BUnitSettingByStore extends GenericItem
{
	/** Qualifier of the <code>NeorisB2BUnitSettingByStore.storeId</code> attribute **/
	public static final String STOREID = "storeId";
	/** Qualifier of the <code>NeorisB2BUnitSettingByStore.uid</code> attribute **/
	public static final String UID = "uid";
	/** Qualifier of the <code>NeorisB2BUnitSettingByStore.setting</code> attribute **/
	public static final String SETTING = "setting";
	/** Qualifier of the <code>NeorisB2BUnitSettingByStore.b2bUnitOwner</code> attribute **/
	public static final String B2BUNITOWNER = "b2bUnitOwner";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(STOREID, AttributeMode.INITIAL);
		tmp.put(UID, AttributeMode.INITIAL);
		tmp.put(SETTING, AttributeMode.INITIAL);
		tmp.put(B2BUNITOWNER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.b2bUnitOwner</code> attribute.
	 * @return the b2bUnitOwner
	 */
	public B2BUnit getB2bUnitOwner(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, B2BUNITOWNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.b2bUnitOwner</code> attribute.
	 * @return the b2bUnitOwner
	 */
	public B2BUnit getB2bUnitOwner()
	{
		return getB2bUnitOwner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.b2bUnitOwner</code> attribute. 
	 * @param value the b2bUnitOwner
	 */
	protected void setB2bUnitOwner(final SessionContext ctx, final B2BUnit value)
	{
		// initial-only attribute: make sure this attribute can be set during item creation only
		if ( ctx.getAttribute( "core.types.creation.initial") != Boolean.TRUE )
		{
			throw new JaloInvalidParameterException( "attribute '"+B2BUNITOWNER+"' is not changeable", 0 );
		}
		setProperty(ctx, B2BUNITOWNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.b2bUnitOwner</code> attribute. 
	 * @param value the b2bUnitOwner
	 */
	protected void setB2bUnitOwner(final B2BUnit value)
	{
		setB2bUnitOwner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.setting</code> attribute.
	 * @return the setting
	 */
	public String getSetting(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SETTING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.setting</code> attribute.
	 * @return the setting
	 */
	public String getSetting()
	{
		return getSetting( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.setting</code> attribute. 
	 * @param value the setting
	 */
	public void setSetting(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SETTING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.setting</code> attribute. 
	 * @param value the setting
	 */
	public void setSetting(final String value)
	{
		setSetting( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return getStoreId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.storeId</code> attribute. 
	 * @param value the storeId
	 */
	protected void setStoreId(final SessionContext ctx, final String value)
	{
		// initial-only attribute: make sure this attribute can be set during item creation only
		if ( ctx.getAttribute( "core.types.creation.initial") != Boolean.TRUE )
		{
			throw new JaloInvalidParameterException( "attribute '"+STOREID+"' is not changeable", 0 );
		}
		setProperty(ctx, STOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.storeId</code> attribute. 
	 * @param value the storeId
	 */
	protected void setStoreId(final String value)
	{
		setStoreId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.uid</code> attribute.
	 * @return the uid
	 */
	public String getUid(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BUnitSettingByStore.uid</code> attribute.
	 * @return the uid
	 */
	public String getUid()
	{
		return getUid( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.uid</code> attribute. 
	 * @param value the uid
	 */
	protected void setUid(final SessionContext ctx, final String value)
	{
		// initial-only attribute: make sure this attribute can be set during item creation only
		if ( ctx.getAttribute( "core.types.creation.initial") != Boolean.TRUE )
		{
			throw new JaloInvalidParameterException( "attribute '"+UID+"' is not changeable", 0 );
		}
		setProperty(ctx, UID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BUnitSettingByStore.uid</code> attribute. 
	 * @param value the uid
	 */
	protected void setUid(final String value)
	{
		setUid( getSession().getSessionContext(), value );
	}
	
}
