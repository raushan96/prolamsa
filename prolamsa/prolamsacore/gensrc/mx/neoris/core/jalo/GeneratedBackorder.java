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
import de.hybris.platform.jalo.user.Address;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem Backorder}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBackorder extends GenericItem
{
	/** Qualifier of the <code>Backorder.pendingQty2</code> attribute **/
	public static final String PENDINGQTY2 = "pendingQty2";
	/** Qualifier of the <code>Backorder.uom2</code> attribute **/
	public static final String UOM2 = "uom2";
	/** Qualifier of the <code>Backorder.readyQty2</code> attribute **/
	public static final String READYQTY2 = "readyQty2";
	/** Qualifier of the <code>Backorder.readyQty3</code> attribute **/
	public static final String READYQTY3 = "readyQty3";
	/** Qualifier of the <code>Backorder.loadingQty3</code> attribute **/
	public static final String LOADINGQTY3 = "loadingQty3";
	/** Qualifier of the <code>Backorder.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	/** Qualifier of the <code>Backorder.pendingKilos</code> attribute **/
	public static final String PENDINGKILOS = "pendingKilos";
	/** Qualifier of the <code>Backorder.loadingKilos</code> attribute **/
	public static final String LOADINGKILOS = "loadingKilos";
	/** Qualifier of the <code>Backorder.balance3</code> attribute **/
	public static final String BALANCE3 = "balance3";
	/** Qualifier of the <code>Backorder.balanceKilos</code> attribute **/
	public static final String BALANCEKILOS = "balanceKilos";
	/** Qualifier of the <code>Backorder.loadingQty2</code> attribute **/
	public static final String LOADINGQTY2 = "loadingQty2";
	/** Qualifier of the <code>Backorder.orderQty2</code> attribute **/
	public static final String ORDERQTY2 = "orderQty2";
	/** Qualifier of the <code>Backorder.readyKilos</code> attribute **/
	public static final String READYKILOS = "readyKilos";
	/** Qualifier of the <code>Backorder.uom3</code> attribute **/
	public static final String UOM3 = "uom3";
	/** Qualifier of the <code>Backorder.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>Backorder.pendingQty3</code> attribute **/
	public static final String PENDINGQTY3 = "pendingQty3";
	/** Qualifier of the <code>Backorder.kgsOrder</code> attribute **/
	public static final String KGSORDER = "kgsOrder";
	/** Qualifier of the <code>Backorder.balance2</code> attribute **/
	public static final String BALANCE2 = "balance2";
	/** Qualifier of the <code>Backorder.orderQty3</code> attribute **/
	public static final String ORDERQTY3 = "orderQty3";
	/** Qualifier of the <code>Backorder.pcsOrder</code> attribute **/
	public static final String PCSORDER = "pcsOrder";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PENDINGQTY2, AttributeMode.INITIAL);
		tmp.put(UOM2, AttributeMode.INITIAL);
		tmp.put(READYQTY2, AttributeMode.INITIAL);
		tmp.put(READYQTY3, AttributeMode.INITIAL);
		tmp.put(LOADINGQTY3, AttributeMode.INITIAL);
		tmp.put(CUSTOMER, AttributeMode.INITIAL);
		tmp.put(PENDINGKILOS, AttributeMode.INITIAL);
		tmp.put(LOADINGKILOS, AttributeMode.INITIAL);
		tmp.put(BALANCE3, AttributeMode.INITIAL);
		tmp.put(BALANCEKILOS, AttributeMode.INITIAL);
		tmp.put(LOADINGQTY2, AttributeMode.INITIAL);
		tmp.put(ORDERQTY2, AttributeMode.INITIAL);
		tmp.put(READYKILOS, AttributeMode.INITIAL);
		tmp.put(UOM3, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(PENDINGQTY3, AttributeMode.INITIAL);
		tmp.put(KGSORDER, AttributeMode.INITIAL);
		tmp.put(BALANCE2, AttributeMode.INITIAL);
		tmp.put(ORDERQTY3, AttributeMode.INITIAL);
		tmp.put(PCSORDER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance2</code> attribute.
	 * @return the balance2
	 */
	public Double getBalance2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance2</code> attribute.
	 * @return the balance2
	 */
	public Double getBalance2()
	{
		return getBalance2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance2</code> attribute. 
	 * @return the balance2
	 */
	public double getBalance2AsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance2</code> attribute. 
	 * @return the balance2
	 */
	public double getBalance2AsPrimitive()
	{
		return getBalance2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final Double value)
	{
		setBalance2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final SessionContext ctx, final double value)
	{
		setBalance2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final double value)
	{
		setBalance2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance3</code> attribute.
	 * @return the balance3
	 */
	public Double getBalance3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance3</code> attribute.
	 * @return the balance3
	 */
	public Double getBalance3()
	{
		return getBalance3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance3</code> attribute. 
	 * @return the balance3
	 */
	public double getBalance3AsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balance3</code> attribute. 
	 * @return the balance3
	 */
	public double getBalance3AsPrimitive()
	{
		return getBalance3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final Double value)
	{
		setBalance3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final SessionContext ctx, final double value)
	{
		setBalance3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final double value)
	{
		setBalance3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balanceKilos</code> attribute.
	 * @return the balanceKilos
	 */
	public Double getBalanceKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCEKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balanceKilos</code> attribute.
	 * @return the balanceKilos
	 */
	public Double getBalanceKilos()
	{
		return getBalanceKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balanceKilos</code> attribute. 
	 * @return the balanceKilos
	 */
	public double getBalanceKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getBalanceKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.balanceKilos</code> attribute. 
	 * @return the balanceKilos
	 */
	public double getBalanceKilosAsPrimitive()
	{
		return getBalanceKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCEKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final Double value)
	{
		setBalanceKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final SessionContext ctx, final double value)
	{
		setBalanceKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final double value)
	{
		setBalanceKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.customer</code> attribute.
	 * @return the customer
	 */
	public Address getCustomer(final SessionContext ctx)
	{
		return (Address)getProperty( ctx, CUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.customer</code> attribute.
	 * @return the customer
	 */
	public Address getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final Address value)
	{
		setProperty(ctx, CUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final Address value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.kgsOrder</code> attribute.
	 * @return the kgsOrder
	 */
	public Double getKgsOrder(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, KGSORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.kgsOrder</code> attribute.
	 * @return the kgsOrder
	 */
	public Double getKgsOrder()
	{
		return getKgsOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.kgsOrder</code> attribute. 
	 * @return the kgsOrder
	 */
	public double getKgsOrderAsPrimitive(final SessionContext ctx)
	{
		Double value = getKgsOrder( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.kgsOrder</code> attribute. 
	 * @return the kgsOrder
	 */
	public double getKgsOrderAsPrimitive()
	{
		return getKgsOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, KGSORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final Double value)
	{
		setKgsOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final SessionContext ctx, final double value)
	{
		setKgsOrder( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final double value)
	{
		setKgsOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingKilos</code> attribute.
	 * @return the loadingKilos
	 */
	public Double getLoadingKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LOADINGKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingKilos</code> attribute.
	 * @return the loadingKilos
	 */
	public Double getLoadingKilos()
	{
		return getLoadingKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingKilos</code> attribute. 
	 * @return the loadingKilos
	 */
	public double getLoadingKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getLoadingKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingKilos</code> attribute. 
	 * @return the loadingKilos
	 */
	public double getLoadingKilosAsPrimitive()
	{
		return getLoadingKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LOADINGKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final Double value)
	{
		setLoadingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final SessionContext ctx, final double value)
	{
		setLoadingKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final double value)
	{
		setLoadingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty2</code> attribute.
	 * @return the loadingQty2
	 */
	public Double getLoadingQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LOADINGQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty2</code> attribute.
	 * @return the loadingQty2
	 */
	public Double getLoadingQty2()
	{
		return getLoadingQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty2</code> attribute. 
	 * @return the loadingQty2
	 */
	public double getLoadingQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getLoadingQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty2</code> attribute. 
	 * @return the loadingQty2
	 */
	public double getLoadingQty2AsPrimitive()
	{
		return getLoadingQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LOADINGQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final Double value)
	{
		setLoadingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final SessionContext ctx, final double value)
	{
		setLoadingQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final double value)
	{
		setLoadingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty3</code> attribute.
	 * @return the loadingQty3
	 */
	public Double getLoadingQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LOADINGQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty3</code> attribute.
	 * @return the loadingQty3
	 */
	public Double getLoadingQty3()
	{
		return getLoadingQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty3</code> attribute. 
	 * @return the loadingQty3
	 */
	public double getLoadingQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getLoadingQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.loadingQty3</code> attribute. 
	 * @return the loadingQty3
	 */
	public double getLoadingQty3AsPrimitive()
	{
		return getLoadingQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LOADINGQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final Double value)
	{
		setLoadingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final SessionContext ctx, final double value)
	{
		setLoadingQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final double value)
	{
		setLoadingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty2</code> attribute.
	 * @return the orderQty2
	 */
	public Double getOrderQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORDERQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty2</code> attribute.
	 * @return the orderQty2
	 */
	public Double getOrderQty2()
	{
		return getOrderQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty2</code> attribute. 
	 * @return the orderQty2
	 */
	public double getOrderQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getOrderQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty2</code> attribute. 
	 * @return the orderQty2
	 */
	public double getOrderQty2AsPrimitive()
	{
		return getOrderQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORDERQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final Double value)
	{
		setOrderQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final SessionContext ctx, final double value)
	{
		setOrderQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final double value)
	{
		setOrderQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty3</code> attribute.
	 * @return the orderQty3
	 */
	public Double getOrderQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORDERQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty3</code> attribute.
	 * @return the orderQty3
	 */
	public Double getOrderQty3()
	{
		return getOrderQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty3</code> attribute. 
	 * @return the orderQty3
	 */
	public double getOrderQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getOrderQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.orderQty3</code> attribute. 
	 * @return the orderQty3
	 */
	public double getOrderQty3AsPrimitive()
	{
		return getOrderQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORDERQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final Double value)
	{
		setOrderQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final SessionContext ctx, final double value)
	{
		setOrderQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final double value)
	{
		setOrderQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pcsOrder</code> attribute.
	 * @return the pcsOrder
	 */
	public String getPcsOrder(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PCSORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pcsOrder</code> attribute.
	 * @return the pcsOrder
	 */
	public String getPcsOrder()
	{
		return getPcsOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pcsOrder</code> attribute. 
	 * @param value the pcsOrder
	 */
	public void setPcsOrder(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PCSORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pcsOrder</code> attribute. 
	 * @param value the pcsOrder
	 */
	public void setPcsOrder(final String value)
	{
		setPcsOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingKilos</code> attribute.
	 * @return the pendingKilos
	 */
	public Double getPendingKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PENDINGKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingKilos</code> attribute.
	 * @return the pendingKilos
	 */
	public Double getPendingKilos()
	{
		return getPendingKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingKilos</code> attribute. 
	 * @return the pendingKilos
	 */
	public double getPendingKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getPendingKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingKilos</code> attribute. 
	 * @return the pendingKilos
	 */
	public double getPendingKilosAsPrimitive()
	{
		return getPendingKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PENDINGKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final Double value)
	{
		setPendingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final SessionContext ctx, final double value)
	{
		setPendingKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final double value)
	{
		setPendingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty2</code> attribute.
	 * @return the pendingQty2
	 */
	public Double getPendingQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PENDINGQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty2</code> attribute.
	 * @return the pendingQty2
	 */
	public Double getPendingQty2()
	{
		return getPendingQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty2</code> attribute. 
	 * @return the pendingQty2
	 */
	public double getPendingQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getPendingQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty2</code> attribute. 
	 * @return the pendingQty2
	 */
	public double getPendingQty2AsPrimitive()
	{
		return getPendingQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PENDINGQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final Double value)
	{
		setPendingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final SessionContext ctx, final double value)
	{
		setPendingQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final double value)
	{
		setPendingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty3</code> attribute.
	 * @return the pendingQty3
	 */
	public Double getPendingQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PENDINGQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty3</code> attribute.
	 * @return the pendingQty3
	 */
	public Double getPendingQty3()
	{
		return getPendingQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty3</code> attribute. 
	 * @return the pendingQty3
	 */
	public double getPendingQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getPendingQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.pendingQty3</code> attribute. 
	 * @return the pendingQty3
	 */
	public double getPendingQty3AsPrimitive()
	{
		return getPendingQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PENDINGQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final Double value)
	{
		setPendingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final SessionContext ctx, final double value)
	{
		setPendingQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final double value)
	{
		setPendingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyKilos</code> attribute.
	 * @return the readyKilos
	 */
	public Double getReadyKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, READYKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyKilos</code> attribute.
	 * @return the readyKilos
	 */
	public Double getReadyKilos()
	{
		return getReadyKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyKilos</code> attribute. 
	 * @return the readyKilos
	 */
	public double getReadyKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getReadyKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyKilos</code> attribute. 
	 * @return the readyKilos
	 */
	public double getReadyKilosAsPrimitive()
	{
		return getReadyKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, READYKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final Double value)
	{
		setReadyKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final SessionContext ctx, final double value)
	{
		setReadyKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final double value)
	{
		setReadyKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty2</code> attribute.
	 * @return the readyQty2
	 */
	public Double getReadyQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, READYQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty2</code> attribute.
	 * @return the readyQty2
	 */
	public Double getReadyQty2()
	{
		return getReadyQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty2</code> attribute. 
	 * @return the readyQty2
	 */
	public double getReadyQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getReadyQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty2</code> attribute. 
	 * @return the readyQty2
	 */
	public double getReadyQty2AsPrimitive()
	{
		return getReadyQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, READYQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final Double value)
	{
		setReadyQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final SessionContext ctx, final double value)
	{
		setReadyQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final double value)
	{
		setReadyQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty3</code> attribute.
	 * @return the readyQty3
	 */
	public Double getReadyQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, READYQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty3</code> attribute.
	 * @return the readyQty3
	 */
	public Double getReadyQty3()
	{
		return getReadyQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty3</code> attribute. 
	 * @return the readyQty3
	 */
	public double getReadyQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getReadyQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.readyQty3</code> attribute. 
	 * @return the readyQty3
	 */
	public double getReadyQty3AsPrimitive()
	{
		return getReadyQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, READYQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final Double value)
	{
		setReadyQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final SessionContext ctx, final double value)
	{
		setReadyQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final double value)
	{
		setReadyQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.uom2</code> attribute.
	 * @return the uom2
	 */
	public String getUom2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.uom2</code> attribute.
	 * @return the uom2
	 */
	public String getUom2()
	{
		return getUom2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.uom2</code> attribute. 
	 * @param value the uom2
	 */
	public void setUom2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.uom2</code> attribute. 
	 * @param value the uom2
	 */
	public void setUom2(final String value)
	{
		setUom2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.uom3</code> attribute.
	 * @return the uom3
	 */
	public String getUom3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Backorder.uom3</code> attribute.
	 * @return the uom3
	 */
	public String getUom3()
	{
		return getUom3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.uom3</code> attribute. 
	 * @param value the uom3
	 */
	public void setUom3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Backorder.uom3</code> attribute. 
	 * @param value the uom3
	 */
	public void setUom3(final String value)
	{
		setUom3( getSession().getSessionContext(), value );
	}
	
}
