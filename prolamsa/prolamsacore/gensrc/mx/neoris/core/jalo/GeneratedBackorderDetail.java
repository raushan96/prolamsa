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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem BackorderDetail}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBackorderDetail extends GenericItem
{
	/** Qualifier of the <code>BackorderDetail.pendingQty2</code> attribute **/
	public static final String PENDINGQTY2 = "pendingQty2";
	/** Qualifier of the <code>BackorderDetail.readyQty2</code> attribute **/
	public static final String READYQTY2 = "readyQty2";
	/** Qualifier of the <code>BackorderDetail.readyQty3</code> attribute **/
	public static final String READYQTY3 = "readyQty3";
	/** Qualifier of the <code>BackorderDetail.address</code> attribute **/
	public static final String ADDRESS = "address";
	/** Qualifier of the <code>BackorderDetail.pieces</code> attribute **/
	public static final String PIECES = "pieces";
	/** Qualifier of the <code>BackorderDetail.balance3</code> attribute **/
	public static final String BALANCE3 = "balance3";
	/** Qualifier of the <code>BackorderDetail.partNumber</code> attribute **/
	public static final String PARTNUMBER = "partNumber";
	/** Qualifier of the <code>BackorderDetail.orderDate</code> attribute **/
	public static final String ORDERDATE = "orderDate";
	/** Qualifier of the <code>BackorderDetail.uomOrderQty</code> attribute **/
	public static final String UOMORDERQTY = "uomOrderQty";
	/** Qualifier of the <code>BackorderDetail.balance</code> attribute **/
	public static final String BALANCE = "balance";
	/** Qualifier of the <code>BackorderDetail.customerPO</code> attribute **/
	public static final String CUSTOMERPO = "customerPO";
	/** Qualifier of the <code>BackorderDetail.uom3</code> attribute **/
	public static final String UOM3 = "uom3";
	/** Qualifier of the <code>BackorderDetail.pendingQty3</code> attribute **/
	public static final String PENDINGQTY3 = "pendingQty3";
	/** Qualifier of the <code>BackorderDetail.logistic</code> attribute **/
	public static final String LOGISTIC = "logistic";
	/** Qualifier of the <code>BackorderDetail.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>BackorderDetail.orderQty3</code> attribute **/
	public static final String ORDERQTY3 = "orderQty3";
	/** Qualifier of the <code>BackorderDetail.pcsOrder</code> attribute **/
	public static final String PCSORDER = "pcsOrder";
	/** Qualifier of the <code>BackorderDetail.uom2</code> attribute **/
	public static final String UOM2 = "uom2";
	/** Qualifier of the <code>BackorderDetail.orderQty</code> attribute **/
	public static final String ORDERQTY = "orderQty";
	/** Qualifier of the <code>BackorderDetail.estatusEng</code> attribute **/
	public static final String ESTATUSENG = "estatusEng";
	/** Qualifier of the <code>BackorderDetail.loadingQty3</code> attribute **/
	public static final String LOADINGQTY3 = "loadingQty3";
	/** Qualifier of the <code>BackorderDetail.estatusEsp</code> attribute **/
	public static final String ESTATUSESP = "estatusEsp";
	/** Qualifier of the <code>BackorderDetail.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	/** Qualifier of the <code>BackorderDetail.pendingKilos</code> attribute **/
	public static final String PENDINGKILOS = "pendingKilos";
	/** Qualifier of the <code>BackorderDetail.loadingKilos</code> attribute **/
	public static final String LOADINGKILOS = "loadingKilos";
	/** Qualifier of the <code>BackorderDetail.order</code> attribute **/
	public static final String ORDER = "order";
	/** Qualifier of the <code>BackorderDetail.balanceKilos</code> attribute **/
	public static final String BALANCEKILOS = "balanceKilos";
	/** Qualifier of the <code>BackorderDetail.loadingQty2</code> attribute **/
	public static final String LOADINGQTY2 = "loadingQty2";
	/** Qualifier of the <code>BackorderDetail.partida</code> attribute **/
	public static final String PARTIDA = "partida";
	/** Qualifier of the <code>BackorderDetail.orderQty2</code> attribute **/
	public static final String ORDERQTY2 = "orderQty2";
	/** Qualifier of the <code>BackorderDetail.readyKilos</code> attribute **/
	public static final String READYKILOS = "readyKilos";
	/** Qualifier of the <code>BackorderDetail.deliveryDate</code> attribute **/
	public static final String DELIVERYDATE = "deliveryDate";
	/** Qualifier of the <code>BackorderDetail.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>BackorderDetail.kgsOrder</code> attribute **/
	public static final String KGSORDER = "kgsOrder";
	/** Qualifier of the <code>BackorderDetail.balance2</code> attribute **/
	public static final String BALANCE2 = "balance2";
	/** Qualifier of the <code>BackorderDetail.plant</code> attribute **/
	public static final String PLANT = "plant";
	/** Qualifier of the <code>BackorderDetail.uomBalance</code> attribute **/
	public static final String UOMBALANCE = "uomBalance";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PENDINGQTY2, AttributeMode.INITIAL);
		tmp.put(READYQTY2, AttributeMode.INITIAL);
		tmp.put(READYQTY3, AttributeMode.INITIAL);
		tmp.put(ADDRESS, AttributeMode.INITIAL);
		tmp.put(PIECES, AttributeMode.INITIAL);
		tmp.put(BALANCE3, AttributeMode.INITIAL);
		tmp.put(PARTNUMBER, AttributeMode.INITIAL);
		tmp.put(ORDERDATE, AttributeMode.INITIAL);
		tmp.put(UOMORDERQTY, AttributeMode.INITIAL);
		tmp.put(BALANCE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERPO, AttributeMode.INITIAL);
		tmp.put(UOM3, AttributeMode.INITIAL);
		tmp.put(PENDINGQTY3, AttributeMode.INITIAL);
		tmp.put(LOGISTIC, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(ORDERQTY3, AttributeMode.INITIAL);
		tmp.put(PCSORDER, AttributeMode.INITIAL);
		tmp.put(UOM2, AttributeMode.INITIAL);
		tmp.put(ORDERQTY, AttributeMode.INITIAL);
		tmp.put(ESTATUSENG, AttributeMode.INITIAL);
		tmp.put(LOADINGQTY3, AttributeMode.INITIAL);
		tmp.put(ESTATUSESP, AttributeMode.INITIAL);
		tmp.put(CUSTOMER, AttributeMode.INITIAL);
		tmp.put(PENDINGKILOS, AttributeMode.INITIAL);
		tmp.put(LOADINGKILOS, AttributeMode.INITIAL);
		tmp.put(ORDER, AttributeMode.INITIAL);
		tmp.put(BALANCEKILOS, AttributeMode.INITIAL);
		tmp.put(LOADINGQTY2, AttributeMode.INITIAL);
		tmp.put(PARTIDA, AttributeMode.INITIAL);
		tmp.put(ORDERQTY2, AttributeMode.INITIAL);
		tmp.put(READYKILOS, AttributeMode.INITIAL);
		tmp.put(DELIVERYDATE, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(KGSORDER, AttributeMode.INITIAL);
		tmp.put(BALANCE2, AttributeMode.INITIAL);
		tmp.put(PLANT, AttributeMode.INITIAL);
		tmp.put(UOMBALANCE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.address</code> attribute.
	 * @return the address
	 */
	public String getAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.address</code> attribute.
	 * @return the address
	 */
	public String getAddress()
	{
		return getAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final String value)
	{
		setAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance</code> attribute.
	 * @return the balance
	 */
	public Double getBalance(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance</code> attribute.
	 * @return the balance
	 */
	public Double getBalance()
	{
		return getBalance( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance</code> attribute. 
	 * @return the balance
	 */
	public double getBalanceAsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance</code> attribute. 
	 * @return the balance
	 */
	public double getBalanceAsPrimitive()
	{
		return getBalanceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final Double value)
	{
		setBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final SessionContext ctx, final double value)
	{
		setBalance( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final double value)
	{
		setBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance2</code> attribute.
	 * @return the balance2
	 */
	public Double getBalance2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance2</code> attribute.
	 * @return the balance2
	 */
	public Double getBalance2()
	{
		return getBalance2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance2</code> attribute. 
	 * @return the balance2
	 */
	public double getBalance2AsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance2</code> attribute. 
	 * @return the balance2
	 */
	public double getBalance2AsPrimitive()
	{
		return getBalance2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final Double value)
	{
		setBalance2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final SessionContext ctx, final double value)
	{
		setBalance2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance2</code> attribute. 
	 * @param value the balance2
	 */
	public void setBalance2(final double value)
	{
		setBalance2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance3</code> attribute.
	 * @return the balance3
	 */
	public Double getBalance3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance3</code> attribute.
	 * @return the balance3
	 */
	public Double getBalance3()
	{
		return getBalance3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance3</code> attribute. 
	 * @return the balance3
	 */
	public double getBalance3AsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balance3</code> attribute. 
	 * @return the balance3
	 */
	public double getBalance3AsPrimitive()
	{
		return getBalance3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final Double value)
	{
		setBalance3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final SessionContext ctx, final double value)
	{
		setBalance3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balance3</code> attribute. 
	 * @param value the balance3
	 */
	public void setBalance3(final double value)
	{
		setBalance3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balanceKilos</code> attribute.
	 * @return the balanceKilos
	 */
	public Double getBalanceKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCEKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balanceKilos</code> attribute.
	 * @return the balanceKilos
	 */
	public Double getBalanceKilos()
	{
		return getBalanceKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balanceKilos</code> attribute. 
	 * @return the balanceKilos
	 */
	public double getBalanceKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getBalanceKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.balanceKilos</code> attribute. 
	 * @return the balanceKilos
	 */
	public double getBalanceKilosAsPrimitive()
	{
		return getBalanceKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCEKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final Double value)
	{
		setBalanceKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final SessionContext ctx, final double value)
	{
		setBalanceKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.balanceKilos</code> attribute. 
	 * @param value the balanceKilos
	 */
	public void setBalanceKilos(final double value)
	{
		setBalanceKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, CUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, CUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final B2BUnit value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.customerPO</code> attribute.
	 * @return the customerPO
	 */
	public String getCustomerPO(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERPO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.customerPO</code> attribute.
	 * @return the customerPO
	 */
	public String getCustomerPO()
	{
		return getCustomerPO( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.customerPO</code> attribute. 
	 * @param value the customerPO
	 */
	public void setCustomerPO(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERPO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.customerPO</code> attribute. 
	 * @param value the customerPO
	 */
	public void setCustomerPO(final String value)
	{
		setCustomerPO( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.deliveryDate</code> attribute.
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, DELIVERYDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.deliveryDate</code> attribute.
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate()
	{
		return getDeliveryDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.deliveryDate</code> attribute. 
	 * @param value the deliveryDate
	 */
	public void setDeliveryDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, DELIVERYDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.deliveryDate</code> attribute. 
	 * @param value the deliveryDate
	 */
	public void setDeliveryDate(final Date value)
	{
		setDeliveryDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.estatusEng</code> attribute.
	 * @return the estatusEng
	 */
	public String getEstatusEng(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ESTATUSENG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.estatusEng</code> attribute.
	 * @return the estatusEng
	 */
	public String getEstatusEng()
	{
		return getEstatusEng( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.estatusEng</code> attribute. 
	 * @param value the estatusEng
	 */
	public void setEstatusEng(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ESTATUSENG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.estatusEng</code> attribute. 
	 * @param value the estatusEng
	 */
	public void setEstatusEng(final String value)
	{
		setEstatusEng( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.estatusEsp</code> attribute.
	 * @return the estatusEsp
	 */
	public String getEstatusEsp(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ESTATUSESP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.estatusEsp</code> attribute.
	 * @return the estatusEsp
	 */
	public String getEstatusEsp()
	{
		return getEstatusEsp( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.estatusEsp</code> attribute. 
	 * @param value the estatusEsp
	 */
	public void setEstatusEsp(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ESTATUSESP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.estatusEsp</code> attribute. 
	 * @param value the estatusEsp
	 */
	public void setEstatusEsp(final String value)
	{
		setEstatusEsp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.kgsOrder</code> attribute.
	 * @return the kgsOrder
	 */
	public Double getKgsOrder(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, KGSORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.kgsOrder</code> attribute.
	 * @return the kgsOrder
	 */
	public Double getKgsOrder()
	{
		return getKgsOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.kgsOrder</code> attribute. 
	 * @return the kgsOrder
	 */
	public double getKgsOrderAsPrimitive(final SessionContext ctx)
	{
		Double value = getKgsOrder( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.kgsOrder</code> attribute. 
	 * @return the kgsOrder
	 */
	public double getKgsOrderAsPrimitive()
	{
		return getKgsOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, KGSORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final Double value)
	{
		setKgsOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final SessionContext ctx, final double value)
	{
		setKgsOrder( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.kgsOrder</code> attribute. 
	 * @param value the kgsOrder
	 */
	public void setKgsOrder(final double value)
	{
		setKgsOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingKilos</code> attribute.
	 * @return the loadingKilos
	 */
	public Double getLoadingKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LOADINGKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingKilos</code> attribute.
	 * @return the loadingKilos
	 */
	public Double getLoadingKilos()
	{
		return getLoadingKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingKilos</code> attribute. 
	 * @return the loadingKilos
	 */
	public double getLoadingKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getLoadingKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingKilos</code> attribute. 
	 * @return the loadingKilos
	 */
	public double getLoadingKilosAsPrimitive()
	{
		return getLoadingKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LOADINGKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final Double value)
	{
		setLoadingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final SessionContext ctx, final double value)
	{
		setLoadingKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingKilos</code> attribute. 
	 * @param value the loadingKilos
	 */
	public void setLoadingKilos(final double value)
	{
		setLoadingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty2</code> attribute.
	 * @return the loadingQty2
	 */
	public Double getLoadingQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LOADINGQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty2</code> attribute.
	 * @return the loadingQty2
	 */
	public Double getLoadingQty2()
	{
		return getLoadingQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty2</code> attribute. 
	 * @return the loadingQty2
	 */
	public double getLoadingQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getLoadingQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty2</code> attribute. 
	 * @return the loadingQty2
	 */
	public double getLoadingQty2AsPrimitive()
	{
		return getLoadingQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LOADINGQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final Double value)
	{
		setLoadingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final SessionContext ctx, final double value)
	{
		setLoadingQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty2</code> attribute. 
	 * @param value the loadingQty2
	 */
	public void setLoadingQty2(final double value)
	{
		setLoadingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty3</code> attribute.
	 * @return the loadingQty3
	 */
	public Double getLoadingQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LOADINGQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty3</code> attribute.
	 * @return the loadingQty3
	 */
	public Double getLoadingQty3()
	{
		return getLoadingQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty3</code> attribute. 
	 * @return the loadingQty3
	 */
	public double getLoadingQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getLoadingQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.loadingQty3</code> attribute. 
	 * @return the loadingQty3
	 */
	public double getLoadingQty3AsPrimitive()
	{
		return getLoadingQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LOADINGQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final Double value)
	{
		setLoadingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final SessionContext ctx, final double value)
	{
		setLoadingQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.loadingQty3</code> attribute. 
	 * @param value the loadingQty3
	 */
	public void setLoadingQty3(final double value)
	{
		setLoadingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.location</code> attribute.
	 * @return the location
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.location</code> attribute.
	 * @return the location
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.logistic</code> attribute.
	 * @return the logistic
	 */
	public String getLogistic(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOGISTIC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.logistic</code> attribute.
	 * @return the logistic
	 */
	public String getLogistic()
	{
		return getLogistic( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.logistic</code> attribute. 
	 * @param value the logistic
	 */
	public void setLogistic(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOGISTIC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.logistic</code> attribute. 
	 * @param value the logistic
	 */
	public void setLogistic(final String value)
	{
		setLogistic( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.order</code> attribute.
	 * @return the order
	 */
	public String getOrder(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.order</code> attribute.
	 * @return the order
	 */
	public String getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.order</code> attribute. 
	 * @param value the order
	 */
	public void setOrder(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.order</code> attribute. 
	 * @param value the order
	 */
	public void setOrder(final String value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderDate</code> attribute.
	 * @return the orderDate
	 */
	public Date getOrderDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, ORDERDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderDate</code> attribute.
	 * @return the orderDate
	 */
	public Date getOrderDate()
	{
		return getOrderDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderDate</code> attribute. 
	 * @param value the orderDate
	 */
	public void setOrderDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, ORDERDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderDate</code> attribute. 
	 * @param value the orderDate
	 */
	public void setOrderDate(final Date value)
	{
		setOrderDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty</code> attribute.
	 * @return the orderQty
	 */
	public Double getOrderQty(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORDERQTY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty</code> attribute.
	 * @return the orderQty
	 */
	public Double getOrderQty()
	{
		return getOrderQty( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty</code> attribute. 
	 * @return the orderQty
	 */
	public double getOrderQtyAsPrimitive(final SessionContext ctx)
	{
		Double value = getOrderQty( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty</code> attribute. 
	 * @return the orderQty
	 */
	public double getOrderQtyAsPrimitive()
	{
		return getOrderQtyAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty</code> attribute. 
	 * @param value the orderQty
	 */
	public void setOrderQty(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORDERQTY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty</code> attribute. 
	 * @param value the orderQty
	 */
	public void setOrderQty(final Double value)
	{
		setOrderQty( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty</code> attribute. 
	 * @param value the orderQty
	 */
	public void setOrderQty(final SessionContext ctx, final double value)
	{
		setOrderQty( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty</code> attribute. 
	 * @param value the orderQty
	 */
	public void setOrderQty(final double value)
	{
		setOrderQty( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty2</code> attribute.
	 * @return the orderQty2
	 */
	public Double getOrderQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORDERQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty2</code> attribute.
	 * @return the orderQty2
	 */
	public Double getOrderQty2()
	{
		return getOrderQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty2</code> attribute. 
	 * @return the orderQty2
	 */
	public double getOrderQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getOrderQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty2</code> attribute. 
	 * @return the orderQty2
	 */
	public double getOrderQty2AsPrimitive()
	{
		return getOrderQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORDERQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final Double value)
	{
		setOrderQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final SessionContext ctx, final double value)
	{
		setOrderQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty2</code> attribute. 
	 * @param value the orderQty2
	 */
	public void setOrderQty2(final double value)
	{
		setOrderQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty3</code> attribute.
	 * @return the orderQty3
	 */
	public Double getOrderQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORDERQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty3</code> attribute.
	 * @return the orderQty3
	 */
	public Double getOrderQty3()
	{
		return getOrderQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty3</code> attribute. 
	 * @return the orderQty3
	 */
	public double getOrderQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getOrderQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.orderQty3</code> attribute. 
	 * @return the orderQty3
	 */
	public double getOrderQty3AsPrimitive()
	{
		return getOrderQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORDERQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final Double value)
	{
		setOrderQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final SessionContext ctx, final double value)
	{
		setOrderQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.orderQty3</code> attribute. 
	 * @param value the orderQty3
	 */
	public void setOrderQty3(final double value)
	{
		setOrderQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.partida</code> attribute.
	 * @return the partida
	 */
	public String getPartida(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PARTIDA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.partida</code> attribute.
	 * @return the partida
	 */
	public String getPartida()
	{
		return getPartida( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.partida</code> attribute. 
	 * @param value the partida
	 */
	public void setPartida(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PARTIDA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.partida</code> attribute. 
	 * @param value the partida
	 */
	public void setPartida(final String value)
	{
		setPartida( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.partNumber</code> attribute.
	 * @return the partNumber
	 */
	public String getPartNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PARTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.partNumber</code> attribute.
	 * @return the partNumber
	 */
	public String getPartNumber()
	{
		return getPartNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.partNumber</code> attribute. 
	 * @param value the partNumber
	 */
	public void setPartNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PARTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.partNumber</code> attribute. 
	 * @param value the partNumber
	 */
	public void setPartNumber(final String value)
	{
		setPartNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pcsOrder</code> attribute.
	 * @return the pcsOrder
	 */
	public String getPcsOrder(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PCSORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pcsOrder</code> attribute.
	 * @return the pcsOrder
	 */
	public String getPcsOrder()
	{
		return getPcsOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pcsOrder</code> attribute. 
	 * @param value the pcsOrder
	 */
	public void setPcsOrder(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PCSORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pcsOrder</code> attribute. 
	 * @param value the pcsOrder
	 */
	public void setPcsOrder(final String value)
	{
		setPcsOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingKilos</code> attribute.
	 * @return the pendingKilos
	 */
	public Double getPendingKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PENDINGKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingKilos</code> attribute.
	 * @return the pendingKilos
	 */
	public Double getPendingKilos()
	{
		return getPendingKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingKilos</code> attribute. 
	 * @return the pendingKilos
	 */
	public double getPendingKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getPendingKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingKilos</code> attribute. 
	 * @return the pendingKilos
	 */
	public double getPendingKilosAsPrimitive()
	{
		return getPendingKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PENDINGKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final Double value)
	{
		setPendingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final SessionContext ctx, final double value)
	{
		setPendingKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingKilos</code> attribute. 
	 * @param value the pendingKilos
	 */
	public void setPendingKilos(final double value)
	{
		setPendingKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty2</code> attribute.
	 * @return the pendingQty2
	 */
	public Double getPendingQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PENDINGQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty2</code> attribute.
	 * @return the pendingQty2
	 */
	public Double getPendingQty2()
	{
		return getPendingQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty2</code> attribute. 
	 * @return the pendingQty2
	 */
	public double getPendingQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getPendingQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty2</code> attribute. 
	 * @return the pendingQty2
	 */
	public double getPendingQty2AsPrimitive()
	{
		return getPendingQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PENDINGQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final Double value)
	{
		setPendingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final SessionContext ctx, final double value)
	{
		setPendingQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty2</code> attribute. 
	 * @param value the pendingQty2
	 */
	public void setPendingQty2(final double value)
	{
		setPendingQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty3</code> attribute.
	 * @return the pendingQty3
	 */
	public Double getPendingQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PENDINGQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty3</code> attribute.
	 * @return the pendingQty3
	 */
	public Double getPendingQty3()
	{
		return getPendingQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty3</code> attribute. 
	 * @return the pendingQty3
	 */
	public double getPendingQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getPendingQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pendingQty3</code> attribute. 
	 * @return the pendingQty3
	 */
	public double getPendingQty3AsPrimitive()
	{
		return getPendingQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PENDINGQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final Double value)
	{
		setPendingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final SessionContext ctx, final double value)
	{
		setPendingQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pendingQty3</code> attribute. 
	 * @param value the pendingQty3
	 */
	public void setPendingQty3(final double value)
	{
		setPendingQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pieces</code> attribute.
	 * @return the pieces
	 */
	public Integer getPieces(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, PIECES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pieces</code> attribute.
	 * @return the pieces
	 */
	public Integer getPieces()
	{
		return getPieces( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pieces</code> attribute. 
	 * @return the pieces
	 */
	public int getPiecesAsPrimitive(final SessionContext ctx)
	{
		Integer value = getPieces( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.pieces</code> attribute. 
	 * @return the pieces
	 */
	public int getPiecesAsPrimitive()
	{
		return getPiecesAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pieces</code> attribute. 
	 * @param value the pieces
	 */
	public void setPieces(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, PIECES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pieces</code> attribute. 
	 * @param value the pieces
	 */
	public void setPieces(final Integer value)
	{
		setPieces( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pieces</code> attribute. 
	 * @param value the pieces
	 */
	public void setPieces(final SessionContext ctx, final int value)
	{
		setPieces( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.pieces</code> attribute. 
	 * @param value the pieces
	 */
	public void setPieces(final int value)
	{
		setPieces( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.plant</code> attribute.
	 * @return the plant
	 */
	public String getPlant(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PLANT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.plant</code> attribute.
	 * @return the plant
	 */
	public String getPlant()
	{
		return getPlant( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.plant</code> attribute. 
	 * @param value the plant
	 */
	public void setPlant(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PLANT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.plant</code> attribute. 
	 * @param value the plant
	 */
	public void setPlant(final String value)
	{
		setPlant( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyKilos</code> attribute.
	 * @return the readyKilos
	 */
	public Double getReadyKilos(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, READYKILOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyKilos</code> attribute.
	 * @return the readyKilos
	 */
	public Double getReadyKilos()
	{
		return getReadyKilos( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyKilos</code> attribute. 
	 * @return the readyKilos
	 */
	public double getReadyKilosAsPrimitive(final SessionContext ctx)
	{
		Double value = getReadyKilos( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyKilos</code> attribute. 
	 * @return the readyKilos
	 */
	public double getReadyKilosAsPrimitive()
	{
		return getReadyKilosAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, READYKILOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final Double value)
	{
		setReadyKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final SessionContext ctx, final double value)
	{
		setReadyKilos( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyKilos</code> attribute. 
	 * @param value the readyKilos
	 */
	public void setReadyKilos(final double value)
	{
		setReadyKilos( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty2</code> attribute.
	 * @return the readyQty2
	 */
	public Double getReadyQty2(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, READYQTY2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty2</code> attribute.
	 * @return the readyQty2
	 */
	public Double getReadyQty2()
	{
		return getReadyQty2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty2</code> attribute. 
	 * @return the readyQty2
	 */
	public double getReadyQty2AsPrimitive(final SessionContext ctx)
	{
		Double value = getReadyQty2( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty2</code> attribute. 
	 * @return the readyQty2
	 */
	public double getReadyQty2AsPrimitive()
	{
		return getReadyQty2AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, READYQTY2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final Double value)
	{
		setReadyQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final SessionContext ctx, final double value)
	{
		setReadyQty2( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty2</code> attribute. 
	 * @param value the readyQty2
	 */
	public void setReadyQty2(final double value)
	{
		setReadyQty2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty3</code> attribute.
	 * @return the readyQty3
	 */
	public Double getReadyQty3(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, READYQTY3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty3</code> attribute.
	 * @return the readyQty3
	 */
	public Double getReadyQty3()
	{
		return getReadyQty3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty3</code> attribute. 
	 * @return the readyQty3
	 */
	public double getReadyQty3AsPrimitive(final SessionContext ctx)
	{
		Double value = getReadyQty3( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.readyQty3</code> attribute. 
	 * @return the readyQty3
	 */
	public double getReadyQty3AsPrimitive()
	{
		return getReadyQty3AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, READYQTY3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final Double value)
	{
		setReadyQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final SessionContext ctx, final double value)
	{
		setReadyQty3( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.readyQty3</code> attribute. 
	 * @param value the readyQty3
	 */
	public void setReadyQty3(final double value)
	{
		setReadyQty3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uom2</code> attribute.
	 * @return the uom2
	 */
	public String getUom2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uom2</code> attribute.
	 * @return the uom2
	 */
	public String getUom2()
	{
		return getUom2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uom2</code> attribute. 
	 * @param value the uom2
	 */
	public void setUom2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uom2</code> attribute. 
	 * @param value the uom2
	 */
	public void setUom2(final String value)
	{
		setUom2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uom3</code> attribute.
	 * @return the uom3
	 */
	public String getUom3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uom3</code> attribute.
	 * @return the uom3
	 */
	public String getUom3()
	{
		return getUom3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uom3</code> attribute. 
	 * @param value the uom3
	 */
	public void setUom3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uom3</code> attribute. 
	 * @param value the uom3
	 */
	public void setUom3(final String value)
	{
		setUom3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uomBalance</code> attribute.
	 * @return the uomBalance
	 */
	public String getUomBalance(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOMBALANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uomBalance</code> attribute.
	 * @return the uomBalance
	 */
	public String getUomBalance()
	{
		return getUomBalance( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uomBalance</code> attribute. 
	 * @param value the uomBalance
	 */
	public void setUomBalance(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOMBALANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uomBalance</code> attribute. 
	 * @param value the uomBalance
	 */
	public void setUomBalance(final String value)
	{
		setUomBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uomOrderQty</code> attribute.
	 * @return the uomOrderQty
	 */
	public String getUomOrderQty(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOMORDERQTY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BackorderDetail.uomOrderQty</code> attribute.
	 * @return the uomOrderQty
	 */
	public String getUomOrderQty()
	{
		return getUomOrderQty( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uomOrderQty</code> attribute. 
	 * @param value the uomOrderQty
	 */
	public void setUomOrderQty(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOMORDERQTY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BackorderDetail.uomOrderQty</code> attribute. 
	 * @param value the uomOrderQty
	 */
	public void setUomOrderQty(final String value)
	{
		setUomOrderQty( getSession().getSessionContext(), value );
	}
	
}
