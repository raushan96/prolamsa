/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem NeorisB2BCustomerSettingByStore}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedNeorisB2BCustomerSettingByStore extends GenericItem
{
	/** Qualifier of the <code>NeorisB2BCustomerSettingByStore.storeId</code> attribute **/
	public static final String STOREID = "storeId";
	/** Qualifier of the <code>NeorisB2BCustomerSettingByStore.uid</code> attribute **/
	public static final String UID = "uid";
	/** Qualifier of the <code>NeorisB2BCustomerSettingByStore.setting</code> attribute **/
	public static final String SETTING = "setting";
	/** Qualifier of the <code>NeorisB2BCustomerSettingByStore.b2bCustomerOwner</code> attribute **/
	public static final String B2BCUSTOMEROWNER = "b2bCustomerOwner";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(STOREID, AttributeMode.INITIAL);
		tmp.put(UID, AttributeMode.INITIAL);
		tmp.put(SETTING, AttributeMode.INITIAL);
		tmp.put(B2BCUSTOMEROWNER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.b2bCustomerOwner</code> attribute.
	 * @return the b2bCustomerOwner
	 */
	public B2BCustomer getB2bCustomerOwner(final SessionContext ctx)
	{
		return (B2BCustomer)getProperty( ctx, B2BCUSTOMEROWNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.b2bCustomerOwner</code> attribute.
	 * @return the b2bCustomerOwner
	 */
	public B2BCustomer getB2bCustomerOwner()
	{
		return getB2bCustomerOwner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.b2bCustomerOwner</code> attribute. 
	 * @param value the b2bCustomerOwner
	 */
	protected void setB2bCustomerOwner(final SessionContext ctx, final B2BCustomer value)
	{
		// initial-only attribute: make sure this attribute can be set during item creation only
		if ( ctx.getAttribute( "core.types.creation.initial") != Boolean.TRUE )
		{
			throw new JaloInvalidParameterException( "attribute '"+B2BCUSTOMEROWNER+"' is not changeable", 0 );
		}
		setProperty(ctx, B2BCUSTOMEROWNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.b2bCustomerOwner</code> attribute. 
	 * @param value the b2bCustomerOwner
	 */
	protected void setB2bCustomerOwner(final B2BCustomer value)
	{
		setB2bCustomerOwner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.setting</code> attribute.
	 * @return the setting
	 */
	public String getSetting(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SETTING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.setting</code> attribute.
	 * @return the setting
	 */
	public String getSetting()
	{
		return getSetting( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.setting</code> attribute. 
	 * @param value the setting
	 */
	public void setSetting(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SETTING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.setting</code> attribute. 
	 * @param value the setting
	 */
	public void setSetting(final String value)
	{
		setSetting( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return getStoreId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.storeId</code> attribute. 
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
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.storeId</code> attribute. 
	 * @param value the storeId
	 */
	protected void setStoreId(final String value)
	{
		setStoreId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.uid</code> attribute.
	 * @return the uid
	 */
	public String getUid(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerSettingByStore.uid</code> attribute.
	 * @return the uid
	 */
	public String getUid()
	{
		return getUid( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.uid</code> attribute. 
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
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerSettingByStore.uid</code> attribute. 
	 * @param value the uid
	 */
	protected void setUid(final String value)
	{
		setUid( getSession().getSessionContext(), value );
	}
	
}
