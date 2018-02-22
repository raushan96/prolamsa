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
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem BalanceStatementDetail}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBalanceStatementDetail extends GenericItem
{
	/** Qualifier of the <code>BalanceStatementDetail.currentInvoice</code> attribute **/
	public static final String CURRENTINVOICE = "currentInvoice";
	/** Qualifier of the <code>BalanceStatementDetail.currentBalance</code> attribute **/
	public static final String CURRENTBALANCE = "currentBalance";
	/** Qualifier of the <code>BalanceStatementDetail.current</code> attribute **/
	public static final String CURRENT = "current";
	/** Qualifier of the <code>BalanceStatementDetail.overduePayment</code> attribute **/
	public static final String OVERDUEPAYMENT = "overduePayment";
	/** Qualifier of the <code>BalanceStatementDetail.pastDue1_30</code> attribute **/
	public static final String PASTDUE1_30 = "pastDue1_30";
	/** Qualifier of the <code>BalanceStatementDetail.overdueCharge</code> attribute **/
	public static final String OVERDUECHARGE = "overdueCharge";
	/** Qualifier of the <code>BalanceStatementDetail.pastDue31_60</code> attribute **/
	public static final String PASTDUE31_60 = "pastDue31_60";
	/** Qualifier of the <code>BalanceStatementDetail.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	/** Qualifier of the <code>BalanceStatementDetail.currentCharge</code> attribute **/
	public static final String CURRENTCHARGE = "currentCharge";
	/** Qualifier of the <code>BalanceStatementDetail.currentPayment</code> attribute **/
	public static final String CURRENTPAYMENT = "currentPayment";
	/** Qualifier of the <code>BalanceStatementDetail.currentCredit</code> attribute **/
	public static final String CURRENTCREDIT = "currentCredit";
	/** Qualifier of the <code>BalanceStatementDetail.salVal</code> attribute **/
	public static final String SALVAL = "salVal";
	/** Qualifier of the <code>BalanceStatementDetail.creditAvailable</code> attribute **/
	public static final String CREDITAVAILABLE = "creditAvailable";
	/** Qualifier of the <code>BalanceStatementDetail.overdueCredit</code> attribute **/
	public static final String OVERDUECREDIT = "overdueCredit";
	/** Qualifier of the <code>BalanceStatementDetail.pastDue61_90</code> attribute **/
	public static final String PASTDUE61_90 = "pastDue61_90";
	/** Qualifier of the <code>BalanceStatementDetail.pastDue</code> attribute **/
	public static final String PASTDUE = "pastDue";
	/** Qualifier of the <code>BalanceStatementDetail.overdueAmount</code> attribute **/
	public static final String OVERDUEAMOUNT = "overdueAmount";
	/** Qualifier of the <code>BalanceStatementDetail.balance</code> attribute **/
	public static final String BALANCE = "balance";
	/** Qualifier of the <code>BalanceStatementDetail.overdueInvoice</code> attribute **/
	public static final String OVERDUEINVOICE = "overdueInvoice";
	/** Qualifier of the <code>BalanceStatementDetail.creditLimit</code> attribute **/
	public static final String CREDITLIMIT = "creditLimit";
	/** Qualifier of the <code>BalanceStatementDetail.pastDueMore90</code> attribute **/
	public static final String PASTDUEMORE90 = "pastDueMore90";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CURRENTINVOICE, AttributeMode.INITIAL);
		tmp.put(CURRENTBALANCE, AttributeMode.INITIAL);
		tmp.put(CURRENT, AttributeMode.INITIAL);
		tmp.put(OVERDUEPAYMENT, AttributeMode.INITIAL);
		tmp.put(PASTDUE1_30, AttributeMode.INITIAL);
		tmp.put(OVERDUECHARGE, AttributeMode.INITIAL);
		tmp.put(PASTDUE31_60, AttributeMode.INITIAL);
		tmp.put(CUSTOMER, AttributeMode.INITIAL);
		tmp.put(CURRENTCHARGE, AttributeMode.INITIAL);
		tmp.put(CURRENTPAYMENT, AttributeMode.INITIAL);
		tmp.put(CURRENTCREDIT, AttributeMode.INITIAL);
		tmp.put(SALVAL, AttributeMode.INITIAL);
		tmp.put(CREDITAVAILABLE, AttributeMode.INITIAL);
		tmp.put(OVERDUECREDIT, AttributeMode.INITIAL);
		tmp.put(PASTDUE61_90, AttributeMode.INITIAL);
		tmp.put(PASTDUE, AttributeMode.INITIAL);
		tmp.put(OVERDUEAMOUNT, AttributeMode.INITIAL);
		tmp.put(BALANCE, AttributeMode.INITIAL);
		tmp.put(OVERDUEINVOICE, AttributeMode.INITIAL);
		tmp.put(CREDITLIMIT, AttributeMode.INITIAL);
		tmp.put(PASTDUEMORE90, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.balance</code> attribute.
	 * @return the balance
	 */
	public Double getBalance(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.balance</code> attribute.
	 * @return the balance
	 */
	public Double getBalance()
	{
		return getBalance( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.balance</code> attribute. 
	 * @return the balance
	 */
	public double getBalanceAsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.balance</code> attribute. 
	 * @return the balance
	 */
	public double getBalanceAsPrimitive()
	{
		return getBalanceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final Double value)
	{
		setBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final SessionContext ctx, final double value)
	{
		setBalance( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final double value)
	{
		setBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditAvailable</code> attribute.
	 * @return the creditAvailable
	 */
	public Double getCreditAvailable(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CREDITAVAILABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditAvailable</code> attribute.
	 * @return the creditAvailable
	 */
	public Double getCreditAvailable()
	{
		return getCreditAvailable( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditAvailable</code> attribute. 
	 * @return the creditAvailable
	 */
	public double getCreditAvailableAsPrimitive(final SessionContext ctx)
	{
		Double value = getCreditAvailable( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditAvailable</code> attribute. 
	 * @return the creditAvailable
	 */
	public double getCreditAvailableAsPrimitive()
	{
		return getCreditAvailableAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditAvailable</code> attribute. 
	 * @param value the creditAvailable
	 */
	public void setCreditAvailable(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CREDITAVAILABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditAvailable</code> attribute. 
	 * @param value the creditAvailable
	 */
	public void setCreditAvailable(final Double value)
	{
		setCreditAvailable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditAvailable</code> attribute. 
	 * @param value the creditAvailable
	 */
	public void setCreditAvailable(final SessionContext ctx, final double value)
	{
		setCreditAvailable( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditAvailable</code> attribute. 
	 * @param value the creditAvailable
	 */
	public void setCreditAvailable(final double value)
	{
		setCreditAvailable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditLimit</code> attribute.
	 * @return the creditLimit
	 */
	public Double getCreditLimit(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CREDITLIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditLimit</code> attribute.
	 * @return the creditLimit
	 */
	public Double getCreditLimit()
	{
		return getCreditLimit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditLimit</code> attribute. 
	 * @return the creditLimit
	 */
	public double getCreditLimitAsPrimitive(final SessionContext ctx)
	{
		Double value = getCreditLimit( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.creditLimit</code> attribute. 
	 * @return the creditLimit
	 */
	public double getCreditLimitAsPrimitive()
	{
		return getCreditLimitAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CREDITLIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final Double value)
	{
		setCreditLimit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final SessionContext ctx, final double value)
	{
		setCreditLimit( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final double value)
	{
		setCreditLimit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.current</code> attribute.
	 * @return the current
	 */
	public Double getCurrent(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.current</code> attribute.
	 * @return the current
	 */
	public Double getCurrent()
	{
		return getCurrent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.current</code> attribute. 
	 * @return the current
	 */
	public double getCurrentAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrent( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.current</code> attribute. 
	 * @return the current
	 */
	public double getCurrentAsPrimitive()
	{
		return getCurrentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.current</code> attribute. 
	 * @param value the current
	 */
	public void setCurrent(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.current</code> attribute. 
	 * @param value the current
	 */
	public void setCurrent(final Double value)
	{
		setCurrent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.current</code> attribute. 
	 * @param value the current
	 */
	public void setCurrent(final SessionContext ctx, final double value)
	{
		setCurrent( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.current</code> attribute. 
	 * @param value the current
	 */
	public void setCurrent(final double value)
	{
		setCurrent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentBalance</code> attribute.
	 * @return the currentBalance
	 */
	public Double getCurrentBalance(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENTBALANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentBalance</code> attribute.
	 * @return the currentBalance
	 */
	public Double getCurrentBalance()
	{
		return getCurrentBalance( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentBalance</code> attribute. 
	 * @return the currentBalance
	 */
	public double getCurrentBalanceAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrentBalance( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentBalance</code> attribute. 
	 * @return the currentBalance
	 */
	public double getCurrentBalanceAsPrimitive()
	{
		return getCurrentBalanceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentBalance</code> attribute. 
	 * @param value the currentBalance
	 */
	public void setCurrentBalance(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENTBALANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentBalance</code> attribute. 
	 * @param value the currentBalance
	 */
	public void setCurrentBalance(final Double value)
	{
		setCurrentBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentBalance</code> attribute. 
	 * @param value the currentBalance
	 */
	public void setCurrentBalance(final SessionContext ctx, final double value)
	{
		setCurrentBalance( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentBalance</code> attribute. 
	 * @param value the currentBalance
	 */
	public void setCurrentBalance(final double value)
	{
		setCurrentBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCharge</code> attribute.
	 * @return the currentCharge
	 */
	public Double getCurrentCharge(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENTCHARGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCharge</code> attribute.
	 * @return the currentCharge
	 */
	public Double getCurrentCharge()
	{
		return getCurrentCharge( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCharge</code> attribute. 
	 * @return the currentCharge
	 */
	public double getCurrentChargeAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrentCharge( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCharge</code> attribute. 
	 * @return the currentCharge
	 */
	public double getCurrentChargeAsPrimitive()
	{
		return getCurrentChargeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCharge</code> attribute. 
	 * @param value the currentCharge
	 */
	public void setCurrentCharge(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENTCHARGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCharge</code> attribute. 
	 * @param value the currentCharge
	 */
	public void setCurrentCharge(final Double value)
	{
		setCurrentCharge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCharge</code> attribute. 
	 * @param value the currentCharge
	 */
	public void setCurrentCharge(final SessionContext ctx, final double value)
	{
		setCurrentCharge( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCharge</code> attribute. 
	 * @param value the currentCharge
	 */
	public void setCurrentCharge(final double value)
	{
		setCurrentCharge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCredit</code> attribute.
	 * @return the currentCredit
	 */
	public Double getCurrentCredit(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENTCREDIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCredit</code> attribute.
	 * @return the currentCredit
	 */
	public Double getCurrentCredit()
	{
		return getCurrentCredit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCredit</code> attribute. 
	 * @return the currentCredit
	 */
	public double getCurrentCreditAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrentCredit( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentCredit</code> attribute. 
	 * @return the currentCredit
	 */
	public double getCurrentCreditAsPrimitive()
	{
		return getCurrentCreditAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCredit</code> attribute. 
	 * @param value the currentCredit
	 */
	public void setCurrentCredit(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENTCREDIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCredit</code> attribute. 
	 * @param value the currentCredit
	 */
	public void setCurrentCredit(final Double value)
	{
		setCurrentCredit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCredit</code> attribute. 
	 * @param value the currentCredit
	 */
	public void setCurrentCredit(final SessionContext ctx, final double value)
	{
		setCurrentCredit( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentCredit</code> attribute. 
	 * @param value the currentCredit
	 */
	public void setCurrentCredit(final double value)
	{
		setCurrentCredit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentInvoice</code> attribute.
	 * @return the currentInvoice
	 */
	public Double getCurrentInvoice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENTINVOICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentInvoice</code> attribute.
	 * @return the currentInvoice
	 */
	public Double getCurrentInvoice()
	{
		return getCurrentInvoice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentInvoice</code> attribute. 
	 * @return the currentInvoice
	 */
	public double getCurrentInvoiceAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrentInvoice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentInvoice</code> attribute. 
	 * @return the currentInvoice
	 */
	public double getCurrentInvoiceAsPrimitive()
	{
		return getCurrentInvoiceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentInvoice</code> attribute. 
	 * @param value the currentInvoice
	 */
	public void setCurrentInvoice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENTINVOICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentInvoice</code> attribute. 
	 * @param value the currentInvoice
	 */
	public void setCurrentInvoice(final Double value)
	{
		setCurrentInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentInvoice</code> attribute. 
	 * @param value the currentInvoice
	 */
	public void setCurrentInvoice(final SessionContext ctx, final double value)
	{
		setCurrentInvoice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentInvoice</code> attribute. 
	 * @param value the currentInvoice
	 */
	public void setCurrentInvoice(final double value)
	{
		setCurrentInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentPayment</code> attribute.
	 * @return the currentPayment
	 */
	public Double getCurrentPayment(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENTPAYMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentPayment</code> attribute.
	 * @return the currentPayment
	 */
	public Double getCurrentPayment()
	{
		return getCurrentPayment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentPayment</code> attribute. 
	 * @return the currentPayment
	 */
	public double getCurrentPaymentAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrentPayment( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.currentPayment</code> attribute. 
	 * @return the currentPayment
	 */
	public double getCurrentPaymentAsPrimitive()
	{
		return getCurrentPaymentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentPayment</code> attribute. 
	 * @param value the currentPayment
	 */
	public void setCurrentPayment(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENTPAYMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentPayment</code> attribute. 
	 * @param value the currentPayment
	 */
	public void setCurrentPayment(final Double value)
	{
		setCurrentPayment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentPayment</code> attribute. 
	 * @param value the currentPayment
	 */
	public void setCurrentPayment(final SessionContext ctx, final double value)
	{
		setCurrentPayment( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.currentPayment</code> attribute. 
	 * @param value the currentPayment
	 */
	public void setCurrentPayment(final double value)
	{
		setCurrentPayment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, CUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, CUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final B2BUnit value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueAmount</code> attribute.
	 * @return the overdueAmount
	 */
	public Double getOverdueAmount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, OVERDUEAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueAmount</code> attribute.
	 * @return the overdueAmount
	 */
	public Double getOverdueAmount()
	{
		return getOverdueAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueAmount</code> attribute. 
	 * @return the overdueAmount
	 */
	public double getOverdueAmountAsPrimitive(final SessionContext ctx)
	{
		Double value = getOverdueAmount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueAmount</code> attribute. 
	 * @return the overdueAmount
	 */
	public double getOverdueAmountAsPrimitive()
	{
		return getOverdueAmountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueAmount</code> attribute. 
	 * @param value the overdueAmount
	 */
	public void setOverdueAmount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, OVERDUEAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueAmount</code> attribute. 
	 * @param value the overdueAmount
	 */
	public void setOverdueAmount(final Double value)
	{
		setOverdueAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueAmount</code> attribute. 
	 * @param value the overdueAmount
	 */
	public void setOverdueAmount(final SessionContext ctx, final double value)
	{
		setOverdueAmount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueAmount</code> attribute. 
	 * @param value the overdueAmount
	 */
	public void setOverdueAmount(final double value)
	{
		setOverdueAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCharge</code> attribute.
	 * @return the overdueCharge
	 */
	public Double getOverdueCharge(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, OVERDUECHARGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCharge</code> attribute.
	 * @return the overdueCharge
	 */
	public Double getOverdueCharge()
	{
		return getOverdueCharge( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCharge</code> attribute. 
	 * @return the overdueCharge
	 */
	public double getOverdueChargeAsPrimitive(final SessionContext ctx)
	{
		Double value = getOverdueCharge( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCharge</code> attribute. 
	 * @return the overdueCharge
	 */
	public double getOverdueChargeAsPrimitive()
	{
		return getOverdueChargeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCharge</code> attribute. 
	 * @param value the overdueCharge
	 */
	public void setOverdueCharge(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, OVERDUECHARGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCharge</code> attribute. 
	 * @param value the overdueCharge
	 */
	public void setOverdueCharge(final Double value)
	{
		setOverdueCharge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCharge</code> attribute. 
	 * @param value the overdueCharge
	 */
	public void setOverdueCharge(final SessionContext ctx, final double value)
	{
		setOverdueCharge( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCharge</code> attribute. 
	 * @param value the overdueCharge
	 */
	public void setOverdueCharge(final double value)
	{
		setOverdueCharge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCredit</code> attribute.
	 * @return the overdueCredit
	 */
	public Double getOverdueCredit(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, OVERDUECREDIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCredit</code> attribute.
	 * @return the overdueCredit
	 */
	public Double getOverdueCredit()
	{
		return getOverdueCredit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCredit</code> attribute. 
	 * @return the overdueCredit
	 */
	public double getOverdueCreditAsPrimitive(final SessionContext ctx)
	{
		Double value = getOverdueCredit( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueCredit</code> attribute. 
	 * @return the overdueCredit
	 */
	public double getOverdueCreditAsPrimitive()
	{
		return getOverdueCreditAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCredit</code> attribute. 
	 * @param value the overdueCredit
	 */
	public void setOverdueCredit(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, OVERDUECREDIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCredit</code> attribute. 
	 * @param value the overdueCredit
	 */
	public void setOverdueCredit(final Double value)
	{
		setOverdueCredit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCredit</code> attribute. 
	 * @param value the overdueCredit
	 */
	public void setOverdueCredit(final SessionContext ctx, final double value)
	{
		setOverdueCredit( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueCredit</code> attribute. 
	 * @param value the overdueCredit
	 */
	public void setOverdueCredit(final double value)
	{
		setOverdueCredit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute.
	 * @return the overdueInvoice
	 */
	public Double getOverdueInvoice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, OVERDUEINVOICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute.
	 * @return the overdueInvoice
	 */
	public Double getOverdueInvoice()
	{
		return getOverdueInvoice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute. 
	 * @return the overdueInvoice
	 */
	public double getOverdueInvoiceAsPrimitive(final SessionContext ctx)
	{
		Double value = getOverdueInvoice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute. 
	 * @return the overdueInvoice
	 */
	public double getOverdueInvoiceAsPrimitive()
	{
		return getOverdueInvoiceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute. 
	 * @param value the overdueInvoice
	 */
	public void setOverdueInvoice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, OVERDUEINVOICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute. 
	 * @param value the overdueInvoice
	 */
	public void setOverdueInvoice(final Double value)
	{
		setOverdueInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute. 
	 * @param value the overdueInvoice
	 */
	public void setOverdueInvoice(final SessionContext ctx, final double value)
	{
		setOverdueInvoice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overdueInvoice</code> attribute. 
	 * @param value the overdueInvoice
	 */
	public void setOverdueInvoice(final double value)
	{
		setOverdueInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overduePayment</code> attribute.
	 * @return the overduePayment
	 */
	public Double getOverduePayment(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, OVERDUEPAYMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overduePayment</code> attribute.
	 * @return the overduePayment
	 */
	public Double getOverduePayment()
	{
		return getOverduePayment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overduePayment</code> attribute. 
	 * @return the overduePayment
	 */
	public double getOverduePaymentAsPrimitive(final SessionContext ctx)
	{
		Double value = getOverduePayment( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.overduePayment</code> attribute. 
	 * @return the overduePayment
	 */
	public double getOverduePaymentAsPrimitive()
	{
		return getOverduePaymentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overduePayment</code> attribute. 
	 * @param value the overduePayment
	 */
	public void setOverduePayment(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, OVERDUEPAYMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overduePayment</code> attribute. 
	 * @param value the overduePayment
	 */
	public void setOverduePayment(final Double value)
	{
		setOverduePayment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overduePayment</code> attribute. 
	 * @param value the overduePayment
	 */
	public void setOverduePayment(final SessionContext ctx, final double value)
	{
		setOverduePayment( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.overduePayment</code> attribute. 
	 * @param value the overduePayment
	 */
	public void setOverduePayment(final double value)
	{
		setOverduePayment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue</code> attribute.
	 * @return the pastDue
	 */
	public Double getPastDue(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PASTDUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue</code> attribute.
	 * @return the pastDue
	 */
	public Double getPastDue()
	{
		return getPastDue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue</code> attribute. 
	 * @return the pastDue
	 */
	public double getPastDueAsPrimitive(final SessionContext ctx)
	{
		Double value = getPastDue( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue</code> attribute. 
	 * @return the pastDue
	 */
	public double getPastDueAsPrimitive()
	{
		return getPastDueAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PASTDUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final Double value)
	{
		setPastDue( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final SessionContext ctx, final double value)
	{
		setPastDue( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final double value)
	{
		setPastDue( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute.
	 * @return the pastDue1_30
	 */
	public Double getPastDue1_30(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PASTDUE1_30);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute.
	 * @return the pastDue1_30
	 */
	public Double getPastDue1_30()
	{
		return getPastDue1_30( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute. 
	 * @return the pastDue1_30
	 */
	public double getPastDue1_30AsPrimitive(final SessionContext ctx)
	{
		Double value = getPastDue1_30( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute. 
	 * @return the pastDue1_30
	 */
	public double getPastDue1_30AsPrimitive()
	{
		return getPastDue1_30AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute. 
	 * @param value the pastDue1_30
	 */
	public void setPastDue1_30(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PASTDUE1_30,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute. 
	 * @param value the pastDue1_30
	 */
	public void setPastDue1_30(final Double value)
	{
		setPastDue1_30( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute. 
	 * @param value the pastDue1_30
	 */
	public void setPastDue1_30(final SessionContext ctx, final double value)
	{
		setPastDue1_30( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue1_30</code> attribute. 
	 * @param value the pastDue1_30
	 */
	public void setPastDue1_30(final double value)
	{
		setPastDue1_30( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute.
	 * @return the pastDue31_60
	 */
	public Double getPastDue31_60(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PASTDUE31_60);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute.
	 * @return the pastDue31_60
	 */
	public Double getPastDue31_60()
	{
		return getPastDue31_60( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute. 
	 * @return the pastDue31_60
	 */
	public double getPastDue31_60AsPrimitive(final SessionContext ctx)
	{
		Double value = getPastDue31_60( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute. 
	 * @return the pastDue31_60
	 */
	public double getPastDue31_60AsPrimitive()
	{
		return getPastDue31_60AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute. 
	 * @param value the pastDue31_60
	 */
	public void setPastDue31_60(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PASTDUE31_60,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute. 
	 * @param value the pastDue31_60
	 */
	public void setPastDue31_60(final Double value)
	{
		setPastDue31_60( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute. 
	 * @param value the pastDue31_60
	 */
	public void setPastDue31_60(final SessionContext ctx, final double value)
	{
		setPastDue31_60( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue31_60</code> attribute. 
	 * @param value the pastDue31_60
	 */
	public void setPastDue31_60(final double value)
	{
		setPastDue31_60( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute.
	 * @return the pastDue61_90
	 */
	public Double getPastDue61_90(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PASTDUE61_90);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute.
	 * @return the pastDue61_90
	 */
	public Double getPastDue61_90()
	{
		return getPastDue61_90( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute. 
	 * @return the pastDue61_90
	 */
	public double getPastDue61_90AsPrimitive(final SessionContext ctx)
	{
		Double value = getPastDue61_90( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute. 
	 * @return the pastDue61_90
	 */
	public double getPastDue61_90AsPrimitive()
	{
		return getPastDue61_90AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute. 
	 * @param value the pastDue61_90
	 */
	public void setPastDue61_90(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PASTDUE61_90,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute. 
	 * @param value the pastDue61_90
	 */
	public void setPastDue61_90(final Double value)
	{
		setPastDue61_90( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute. 
	 * @param value the pastDue61_90
	 */
	public void setPastDue61_90(final SessionContext ctx, final double value)
	{
		setPastDue61_90( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDue61_90</code> attribute. 
	 * @param value the pastDue61_90
	 */
	public void setPastDue61_90(final double value)
	{
		setPastDue61_90( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute.
	 * @return the pastDueMore90
	 */
	public Double getPastDueMore90(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PASTDUEMORE90);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute.
	 * @return the pastDueMore90
	 */
	public Double getPastDueMore90()
	{
		return getPastDueMore90( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute. 
	 * @return the pastDueMore90
	 */
	public double getPastDueMore90AsPrimitive(final SessionContext ctx)
	{
		Double value = getPastDueMore90( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute. 
	 * @return the pastDueMore90
	 */
	public double getPastDueMore90AsPrimitive()
	{
		return getPastDueMore90AsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute. 
	 * @param value the pastDueMore90
	 */
	public void setPastDueMore90(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PASTDUEMORE90,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute. 
	 * @param value the pastDueMore90
	 */
	public void setPastDueMore90(final Double value)
	{
		setPastDueMore90( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute. 
	 * @param value the pastDueMore90
	 */
	public void setPastDueMore90(final SessionContext ctx, final double value)
	{
		setPastDueMore90( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.pastDueMore90</code> attribute. 
	 * @param value the pastDueMore90
	 */
	public void setPastDueMore90(final double value)
	{
		setPastDueMore90( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.salVal</code> attribute.
	 * @return the salVal
	 */
	public Double getSalVal(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, SALVAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.salVal</code> attribute.
	 * @return the salVal
	 */
	public Double getSalVal()
	{
		return getSalVal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.salVal</code> attribute. 
	 * @return the salVal
	 */
	public double getSalValAsPrimitive(final SessionContext ctx)
	{
		Double value = getSalVal( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatementDetail.salVal</code> attribute. 
	 * @return the salVal
	 */
	public double getSalValAsPrimitive()
	{
		return getSalValAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.salVal</code> attribute. 
	 * @param value the salVal
	 */
	public void setSalVal(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, SALVAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.salVal</code> attribute. 
	 * @param value the salVal
	 */
	public void setSalVal(final Double value)
	{
		setSalVal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.salVal</code> attribute. 
	 * @param value the salVal
	 */
	public void setSalVal(final SessionContext ctx, final double value)
	{
		setSalVal( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatementDetail.salVal</code> attribute. 
	 * @param value the salVal
	 */
	public void setSalVal(final double value)
	{
		setSalVal( getSession().getSessionContext(), value );
	}
	
}
