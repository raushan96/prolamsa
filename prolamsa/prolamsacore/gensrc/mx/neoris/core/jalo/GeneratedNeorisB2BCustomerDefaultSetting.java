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
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.store.BaseStore;
import de.hybris.platform.storelocator.jalo.PointOfService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.jalo.TransportationMode;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem NeorisB2BCustomerDefaultSetting}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedNeorisB2BCustomerDefaultSetting extends GenericItem
{
	/** Qualifier of the <code>NeorisB2BCustomerDefaultSetting.transportationMode</code> attribute **/
	public static final String TRANSPORTATIONMODE = "transportationMode";
	/** Qualifier of the <code>NeorisB2BCustomerDefaultSetting.uom</code> attribute **/
	public static final String UOM = "uom";
	/** Qualifier of the <code>NeorisB2BCustomerDefaultSetting.b2bunit</code> attribute **/
	public static final String B2BUNIT = "b2bunit";
	/** Qualifier of the <code>NeorisB2BCustomerDefaultSetting.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>NeorisB2BCustomerDefaultSetting.baseStore</code> attribute **/
	public static final String BASESTORE = "baseStore";
	/** Qualifier of the <code>NeorisB2BCustomerDefaultSetting.shippingAddress</code> attribute **/
	public static final String SHIPPINGADDRESS = "shippingAddress";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(TRANSPORTATIONMODE, AttributeMode.INITIAL);
		tmp.put(UOM, AttributeMode.INITIAL);
		tmp.put(B2BUNIT, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(BASESTORE, AttributeMode.INITIAL);
		tmp.put(SHIPPINGADDRESS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.b2bunit</code> attribute.
	 * @return the b2bunit
	 */
	public B2BUnit getB2bunit(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, B2BUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.b2bunit</code> attribute.
	 * @return the b2bunit
	 */
	public B2BUnit getB2bunit()
	{
		return getB2bunit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.b2bunit</code> attribute. 
	 * @param value the b2bunit
	 */
	public void setB2bunit(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, B2BUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.b2bunit</code> attribute. 
	 * @param value the b2bunit
	 */
	public void setB2bunit(final B2BUnit value)
	{
		setB2bunit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.baseStore</code> attribute.
	 * @return the baseStore
	 */
	public BaseStore getBaseStore(final SessionContext ctx)
	{
		return (BaseStore)getProperty( ctx, BASESTORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.baseStore</code> attribute.
	 * @return the baseStore
	 */
	public BaseStore getBaseStore()
	{
		return getBaseStore( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.baseStore</code> attribute. 
	 * @param value the baseStore
	 */
	public void setBaseStore(final SessionContext ctx, final BaseStore value)
	{
		setProperty(ctx, BASESTORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.baseStore</code> attribute. 
	 * @param value the baseStore
	 */
	public void setBaseStore(final BaseStore value)
	{
		setBaseStore( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.location</code> attribute.
	 * @return the location
	 */
	public PointOfService getLocation(final SessionContext ctx)
	{
		return (PointOfService)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.location</code> attribute.
	 * @return the location
	 */
	public PointOfService getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final PointOfService value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final PointOfService value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.shippingAddress</code> attribute.
	 * @return the shippingAddress
	 */
	public Address getShippingAddress(final SessionContext ctx)
	{
		return (Address)getProperty( ctx, SHIPPINGADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.shippingAddress</code> attribute.
	 * @return the shippingAddress
	 */
	public Address getShippingAddress()
	{
		return getShippingAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.shippingAddress</code> attribute. 
	 * @param value the shippingAddress
	 */
	public void setShippingAddress(final SessionContext ctx, final Address value)
	{
		setProperty(ctx, SHIPPINGADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.shippingAddress</code> attribute. 
	 * @param value the shippingAddress
	 */
	public void setShippingAddress(final Address value)
	{
		setShippingAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.transportationMode</code> attribute.
	 * @return the transportationMode
	 */
	public TransportationMode getTransportationMode(final SessionContext ctx)
	{
		return (TransportationMode)getProperty( ctx, TRANSPORTATIONMODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.transportationMode</code> attribute.
	 * @return the transportationMode
	 */
	public TransportationMode getTransportationMode()
	{
		return getTransportationMode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.transportationMode</code> attribute. 
	 * @param value the transportationMode
	 */
	public void setTransportationMode(final SessionContext ctx, final TransportationMode value)
	{
		setProperty(ctx, TRANSPORTATIONMODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.transportationMode</code> attribute. 
	 * @param value the transportationMode
	 */
	public void setTransportationMode(final TransportationMode value)
	{
		setTransportationMode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.uom</code> attribute.
	 * @return the uom
	 */
	public Unit getUom(final SessionContext ctx)
	{
		return (Unit)getProperty( ctx, UOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NeorisB2BCustomerDefaultSetting.uom</code> attribute.
	 * @return the uom
	 */
	public Unit getUom()
	{
		return getUom( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.uom</code> attribute. 
	 * @param value the uom
	 */
	public void setUom(final SessionContext ctx, final Unit value)
	{
		setProperty(ctx, UOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NeorisB2BCustomerDefaultSetting.uom</code> attribute. 
	 * @param value the uom
	 */
	public void setUom(final Unit value)
	{
		setUom( getSession().getSessionContext(), value );
	}
	
}
