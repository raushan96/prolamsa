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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem Invoice}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedInvoice extends GenericItem
{
	/** Qualifier of the <code>Invoice.credits</code> attribute **/
	public static final String CREDITS = "credits";
	/** Qualifier of the <code>Invoice.documentNumber</code> attribute **/
	public static final String DOCUMENTNUMBER = "documentNumber";
	/** Qualifier of the <code>Invoice.dueDate</code> attribute **/
	public static final String DUEDATE = "dueDate";
	/** Qualifier of the <code>Invoice.currency</code> attribute **/
	public static final String CURRENCY = "currency";
	/** Qualifier of the <code>Invoice.balanceAmount</code> attribute **/
	public static final String BALANCEAMOUNT = "balanceAmount";
	/** Qualifier of the <code>Invoice.documentDescription</code> attribute **/
	public static final String DOCUMENTDESCRIPTION = "documentDescription";
	/** Qualifier of the <code>Invoice.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	/** Qualifier of the <code>Invoice.debits</code> attribute **/
	public static final String DEBITS = "debits";
	/** Qualifier of the <code>Invoice.originalAmount</code> attribute **/
	public static final String ORIGINALAMOUNT = "originalAmount";
	/** Qualifier of the <code>Invoice.invoiceDate</code> attribute **/
	public static final String INVOICEDATE = "invoiceDate";
	/** Qualifier of the <code>Invoice.number</code> attribute **/
	public static final String NUMBER = "number";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CREDITS, AttributeMode.INITIAL);
		tmp.put(DOCUMENTNUMBER, AttributeMode.INITIAL);
		tmp.put(DUEDATE, AttributeMode.INITIAL);
		tmp.put(CURRENCY, AttributeMode.INITIAL);
		tmp.put(BALANCEAMOUNT, AttributeMode.INITIAL);
		tmp.put(DOCUMENTDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(CUSTOMER, AttributeMode.INITIAL);
		tmp.put(DEBITS, AttributeMode.INITIAL);
		tmp.put(ORIGINALAMOUNT, AttributeMode.INITIAL);
		tmp.put(INVOICEDATE, AttributeMode.INITIAL);
		tmp.put(NUMBER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.balanceAmount</code> attribute.
	 * @return the balanceAmount
	 */
	public Double getBalanceAmount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BALANCEAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.balanceAmount</code> attribute.
	 * @return the balanceAmount
	 */
	public Double getBalanceAmount()
	{
		return getBalanceAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.balanceAmount</code> attribute. 
	 * @return the balanceAmount
	 */
	public double getBalanceAmountAsPrimitive(final SessionContext ctx)
	{
		Double value = getBalanceAmount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.balanceAmount</code> attribute. 
	 * @return the balanceAmount
	 */
	public double getBalanceAmountAsPrimitive()
	{
		return getBalanceAmountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.balanceAmount</code> attribute. 
	 * @param value the balanceAmount
	 */
	public void setBalanceAmount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BALANCEAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.balanceAmount</code> attribute. 
	 * @param value the balanceAmount
	 */
	public void setBalanceAmount(final Double value)
	{
		setBalanceAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.balanceAmount</code> attribute. 
	 * @param value the balanceAmount
	 */
	public void setBalanceAmount(final SessionContext ctx, final double value)
	{
		setBalanceAmount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.balanceAmount</code> attribute. 
	 * @param value the balanceAmount
	 */
	public void setBalanceAmount(final double value)
	{
		setBalanceAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.credits</code> attribute.
	 * @return the credits
	 */
	public Double getCredits(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, CREDITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.credits</code> attribute.
	 * @return the credits
	 */
	public Double getCredits()
	{
		return getCredits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.credits</code> attribute. 
	 * @return the credits
	 */
	public double getCreditsAsPrimitive(final SessionContext ctx)
	{
		Double value = getCredits( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.credits</code> attribute. 
	 * @return the credits
	 */
	public double getCreditsAsPrimitive()
	{
		return getCreditsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.credits</code> attribute. 
	 * @param value the credits
	 */
	public void setCredits(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, CREDITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.credits</code> attribute. 
	 * @param value the credits
	 */
	public void setCredits(final Double value)
	{
		setCredits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.credits</code> attribute. 
	 * @param value the credits
	 */
	public void setCredits(final SessionContext ctx, final double value)
	{
		setCredits( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.credits</code> attribute. 
	 * @param value the credits
	 */
	public void setCredits(final double value)
	{
		setCredits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.currency</code> attribute.
	 * @return the currency
	 */
	public String getCurrency(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CURRENCY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.currency</code> attribute.
	 * @return the currency
	 */
	public String getCurrency()
	{
		return getCurrency( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CURRENCY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final String value)
	{
		setCurrency( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, CUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.customer</code> attribute.
	 * @return the customer
	 */
	public B2BUnit getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, CUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final B2BUnit value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.debits</code> attribute.
	 * @return the debits
	 */
	public Double getDebits(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, DEBITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.debits</code> attribute.
	 * @return the debits
	 */
	public Double getDebits()
	{
		return getDebits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.debits</code> attribute. 
	 * @return the debits
	 */
	public double getDebitsAsPrimitive(final SessionContext ctx)
	{
		Double value = getDebits( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.debits</code> attribute. 
	 * @return the debits
	 */
	public double getDebitsAsPrimitive()
	{
		return getDebitsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.debits</code> attribute. 
	 * @param value the debits
	 */
	public void setDebits(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, DEBITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.debits</code> attribute. 
	 * @param value the debits
	 */
	public void setDebits(final Double value)
	{
		setDebits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.debits</code> attribute. 
	 * @param value the debits
	 */
	public void setDebits(final SessionContext ctx, final double value)
	{
		setDebits( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.debits</code> attribute. 
	 * @param value the debits
	 */
	public void setDebits(final double value)
	{
		setDebits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.documentDescription</code> attribute.
	 * @return the documentDescription
	 */
	public String getDocumentDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DOCUMENTDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.documentDescription</code> attribute.
	 * @return the documentDescription
	 */
	public String getDocumentDescription()
	{
		return getDocumentDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.documentDescription</code> attribute. 
	 * @param value the documentDescription
	 */
	public void setDocumentDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DOCUMENTDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.documentDescription</code> attribute. 
	 * @param value the documentDescription
	 */
	public void setDocumentDescription(final String value)
	{
		setDocumentDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.documentNumber</code> attribute.
	 * @return the documentNumber
	 */
	public String getDocumentNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DOCUMENTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.documentNumber</code> attribute.
	 * @return the documentNumber
	 */
	public String getDocumentNumber()
	{
		return getDocumentNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.documentNumber</code> attribute. 
	 * @param value the documentNumber
	 */
	public void setDocumentNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DOCUMENTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.documentNumber</code> attribute. 
	 * @param value the documentNumber
	 */
	public void setDocumentNumber(final String value)
	{
		setDocumentNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.dueDate</code> attribute.
	 * @return the dueDate
	 */
	public Date getDueDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, DUEDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.dueDate</code> attribute.
	 * @return the dueDate
	 */
	public Date getDueDate()
	{
		return getDueDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.dueDate</code> attribute. 
	 * @param value the dueDate
	 */
	public void setDueDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, DUEDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.dueDate</code> attribute. 
	 * @param value the dueDate
	 */
	public void setDueDate(final Date value)
	{
		setDueDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.invoiceDate</code> attribute.
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, INVOICEDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.invoiceDate</code> attribute.
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate()
	{
		return getInvoiceDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.invoiceDate</code> attribute. 
	 * @param value the invoiceDate
	 */
	public void setInvoiceDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, INVOICEDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.invoiceDate</code> attribute. 
	 * @param value the invoiceDate
	 */
	public void setInvoiceDate(final Date value)
	{
		setInvoiceDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.number</code> attribute.
	 * @return the number
	 */
	public String getNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.number</code> attribute.
	 * @return the number
	 */
	public String getNumber()
	{
		return getNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.number</code> attribute. 
	 * @param value the number
	 */
	public void setNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.number</code> attribute. 
	 * @param value the number
	 */
	public void setNumber(final String value)
	{
		setNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.originalAmount</code> attribute.
	 * @return the originalAmount
	 */
	public Double getOriginalAmount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORIGINALAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.originalAmount</code> attribute.
	 * @return the originalAmount
	 */
	public Double getOriginalAmount()
	{
		return getOriginalAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.originalAmount</code> attribute. 
	 * @return the originalAmount
	 */
	public double getOriginalAmountAsPrimitive(final SessionContext ctx)
	{
		Double value = getOriginalAmount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Invoice.originalAmount</code> attribute. 
	 * @return the originalAmount
	 */
	public double getOriginalAmountAsPrimitive()
	{
		return getOriginalAmountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.originalAmount</code> attribute. 
	 * @param value the originalAmount
	 */
	public void setOriginalAmount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORIGINALAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.originalAmount</code> attribute. 
	 * @param value the originalAmount
	 */
	public void setOriginalAmount(final Double value)
	{
		setOriginalAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.originalAmount</code> attribute. 
	 * @param value the originalAmount
	 */
	public void setOriginalAmount(final SessionContext ctx, final double value)
	{
		setOriginalAmount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Invoice.originalAmount</code> attribute. 
	 * @param value the originalAmount
	 */
	public void setOriginalAmount(final double value)
	{
		setOriginalAmount( getSession().getSessionContext(), value );
	}
	
}
