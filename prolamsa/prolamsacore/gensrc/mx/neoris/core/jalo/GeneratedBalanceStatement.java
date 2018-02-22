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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem BalanceStatement}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBalanceStatement extends GenericItem
{
	/** Qualifier of the <code>BalanceStatement.pastDue</code> attribute **/
	public static final String PASTDUE = "pastDue";
	/** Qualifier of the <code>BalanceStatement.balance</code> attribute **/
	public static final String BALANCE = "balance";
	/** Qualifier of the <code>BalanceStatement.creditLimit</code> attribute **/
	public static final String CREDITLIMIT = "creditLimit";
	/** Qualifier of the <code>BalanceStatement.currentAmount</code> attribute **/
	public static final String CURRENTAMOUNT = "currentAmount";
	/** Qualifier of the <code>BalanceStatement.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PASTDUE, AttributeMode.INITIAL);
		tmp.put(BALANCE, AttributeMode.INITIAL);
		tmp.put(CREDITLIMIT, AttributeMode.INITIAL);
		tmp.put(CURRENTAMOUNT, AttributeMode.INITIAL);
		tmp.put(CUSTOMER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.balance</code> attribute.
	 * @return the balance
	 */
	public Double getBalance(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.balance</code> attribute.
	 * @return the balance
	 */
	public Double getBalance()
	{
		return getBalance( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.balance</code> attribute. 
	 * @return the balance
	 */
	public double getBalanceAsPrimitive(final SessionContext ctx)
	{
		Double value = getBalance( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.balance</code> attribute. 
	 * @return the balance
	 */
	public double getBalanceAsPrimitive()
	{
		return getBalanceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final Double value)
	{
		setBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final SessionContext ctx, final double value)
	{
		setBalance( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.balance</code> attribute. 
	 * @param value the balance
	 */
	public void setBalance(final double value)
	{
		setBalance( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.creditLimit</code> attribute.
	 * @return the creditLimit
	 */
	public Double getCreditLimit(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CREDITLIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.creditLimit</code> attribute.
	 * @return the creditLimit
	 */
	public Double getCreditLimit()
	{
		return getCreditLimit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.creditLimit</code> attribute. 
	 * @return the creditLimit
	 */
	public double getCreditLimitAsPrimitive(final SessionContext ctx)
	{
		Double value = getCreditLimit( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.creditLimit</code> attribute. 
	 * @return the creditLimit
	 */
	public double getCreditLimitAsPrimitive()
	{
		return getCreditLimitAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CREDITLIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final Double value)
	{
		setCreditLimit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final SessionContext ctx, final double value)
	{
		setCreditLimit( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.creditLimit</code> attribute. 
	 * @param value the creditLimit
	 */
	public void setCreditLimit(final double value)
	{
		setCreditLimit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.currentAmount</code> attribute.
	 * @return the currentAmount
	 */
	public Double getCurrentAmount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CURRENTAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.currentAmount</code> attribute.
	 * @return the currentAmount
	 */
	public Double getCurrentAmount()
	{
		return getCurrentAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.currentAmount</code> attribute. 
	 * @return the currentAmount
	 */
	public double getCurrentAmountAsPrimitive(final SessionContext ctx)
	{
		Double value = getCurrentAmount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.currentAmount</code> attribute. 
	 * @return the currentAmount
	 */
	public double getCurrentAmountAsPrimitive()
	{
		return getCurrentAmountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.currentAmount</code> attribute. 
	 * @param value the currentAmount
	 */
	public void setCurrentAmount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CURRENTAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.currentAmount</code> attribute. 
	 * @param value the currentAmount
	 */
	public void setCurrentAmount(final Double value)
	{
		setCurrentAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.currentAmount</code> attribute. 
	 * @param value the currentAmount
	 */
	public void setCurrentAmount(final SessionContext ctx, final double value)
	{
		setCurrentAmount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.currentAmount</code> attribute. 
	 * @param value the currentAmount
	 */
	public void setCurrentAmount(final double value)
	{
		setCurrentAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, CUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, CUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final B2BUnit value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.pastDue</code> attribute.
	 * @return the pastDue
	 */
	public Double getPastDue(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PASTDUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.pastDue</code> attribute.
	 * @return the pastDue
	 */
	public Double getPastDue()
	{
		return getPastDue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.pastDue</code> attribute. 
	 * @return the pastDue
	 */
	public double getPastDueAsPrimitive(final SessionContext ctx)
	{
		Double value = getPastDue( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BalanceStatement.pastDue</code> attribute. 
	 * @return the pastDue
	 */
	public double getPastDueAsPrimitive()
	{
		return getPastDueAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PASTDUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final Double value)
	{
		setPastDue( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final SessionContext ctx, final double value)
	{
		setPastDue( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BalanceStatement.pastDue</code> attribute. 
	 * @param value the pastDue
	 */
	public void setPastDue(final double value)
	{
		setPastDue( getSession().getSessionContext(), value );
	}
	
}
