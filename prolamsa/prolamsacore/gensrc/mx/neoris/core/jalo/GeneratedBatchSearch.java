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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem BatchSearch}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBatchSearch extends GenericItem
{
	/** Qualifier of the <code>BatchSearch.dateInvoice</code> attribute **/
	public static final String DATEINVOICE = "dateInvoice";
	/** Qualifier of the <code>BatchSearch.tolly</code> attribute **/
	public static final String TOLLY = "tolly";
	/** Qualifier of the <code>BatchSearch.debitNote</code> attribute **/
	public static final String DEBITNOTE = "debitNote";
	/** Qualifier of the <code>BatchSearch.creditNote</code> attribute **/
	public static final String CREDITNOTE = "creditNote";
	/** Qualifier of the <code>BatchSearch.remission</code> attribute **/
	public static final String REMISSION = "remission";
	/** Qualifier of the <code>BatchSearch.po</code> attribute **/
	public static final String PO = "po";
	/** Qualifier of the <code>BatchSearch.invoice</code> attribute **/
	public static final String INVOICE = "invoice";
	/** Qualifier of the <code>BatchSearch.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>BatchSearch.quote</code> attribute **/
	public static final String QUOTE = "quote";
	/** Qualifier of the <code>BatchSearch.boL</code> attribute **/
	public static final String BOL = "boL";
	/** Qualifier of the <code>BatchSearch.so</code> attribute **/
	public static final String SO = "so";
	/** Qualifier of the <code>BatchSearch.mtr</code> attribute **/
	public static final String MTR = "mtr";
	/** Qualifier of the <code>BatchSearch.name</code> attribute **/
	public static final String NAME = "name";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(DATEINVOICE, AttributeMode.INITIAL);
		tmp.put(TOLLY, AttributeMode.INITIAL);
		tmp.put(DEBITNOTE, AttributeMode.INITIAL);
		tmp.put(CREDITNOTE, AttributeMode.INITIAL);
		tmp.put(REMISSION, AttributeMode.INITIAL);
		tmp.put(PO, AttributeMode.INITIAL);
		tmp.put(INVOICE, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(QUOTE, AttributeMode.INITIAL);
		tmp.put(BOL, AttributeMode.INITIAL);
		tmp.put(SO, AttributeMode.INITIAL);
		tmp.put(MTR, AttributeMode.INITIAL);
		tmp.put(NAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.boL</code> attribute.
	 * @return the boL
	 */
	public String getBoL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BOL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.boL</code> attribute.
	 * @return the boL
	 */
	public String getBoL()
	{
		return getBoL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.boL</code> attribute. 
	 * @param value the boL
	 */
	public void setBoL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BOL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.boL</code> attribute. 
	 * @param value the boL
	 */
	public void setBoL(final String value)
	{
		setBoL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.creditNote</code> attribute.
	 * @return the creditNote
	 */
	public String getCreditNote(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITNOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.creditNote</code> attribute.
	 * @return the creditNote
	 */
	public String getCreditNote()
	{
		return getCreditNote( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.creditNote</code> attribute. 
	 * @param value the creditNote
	 */
	public void setCreditNote(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITNOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.creditNote</code> attribute. 
	 * @param value the creditNote
	 */
	public void setCreditNote(final String value)
	{
		setCreditNote( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.dateInvoice</code> attribute.
	 * @return the dateInvoice
	 */
	public String getDateInvoice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATEINVOICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.dateInvoice</code> attribute.
	 * @return the dateInvoice
	 */
	public String getDateInvoice()
	{
		return getDateInvoice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.dateInvoice</code> attribute. 
	 * @param value the dateInvoice
	 */
	public void setDateInvoice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATEINVOICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.dateInvoice</code> attribute. 
	 * @param value the dateInvoice
	 */
	public void setDateInvoice(final String value)
	{
		setDateInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.debitNote</code> attribute.
	 * @return the debitNote
	 */
	public String getDebitNote(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEBITNOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.debitNote</code> attribute.
	 * @return the debitNote
	 */
	public String getDebitNote()
	{
		return getDebitNote( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.debitNote</code> attribute. 
	 * @param value the debitNote
	 */
	public void setDebitNote(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEBITNOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.debitNote</code> attribute. 
	 * @param value the debitNote
	 */
	public void setDebitNote(final String value)
	{
		setDebitNote( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.invoice</code> attribute.
	 * @return the invoice
	 */
	public String getInvoice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.invoice</code> attribute.
	 * @return the invoice
	 */
	public String getInvoice()
	{
		return getInvoice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.invoice</code> attribute. 
	 * @param value the invoice
	 */
	public void setInvoice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.invoice</code> attribute. 
	 * @param value the invoice
	 */
	public void setInvoice(final String value)
	{
		setInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.mtr</code> attribute.
	 * @return the mtr
	 */
	public String getMtr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MTR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.mtr</code> attribute.
	 * @return the mtr
	 */
	public String getMtr()
	{
		return getMtr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.mtr</code> attribute. 
	 * @param value the mtr
	 */
	public void setMtr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MTR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.mtr</code> attribute. 
	 * @param value the mtr
	 */
	public void setMtr(final String value)
	{
		setMtr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.po</code> attribute.
	 * @return the po
	 */
	public String getPo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.po</code> attribute.
	 * @return the po
	 */
	public String getPo()
	{
		return getPo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.po</code> attribute. 
	 * @param value the po
	 */
	public void setPo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.po</code> attribute. 
	 * @param value the po
	 */
	public void setPo(final String value)
	{
		setPo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.quote</code> attribute.
	 * @return the quote
	 */
	public String getQuote(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.quote</code> attribute.
	 * @return the quote
	 */
	public String getQuote()
	{
		return getQuote( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.quote</code> attribute. 
	 * @param value the quote
	 */
	public void setQuote(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.quote</code> attribute. 
	 * @param value the quote
	 */
	public void setQuote(final String value)
	{
		setQuote( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.remission</code> attribute.
	 * @return the remission
	 */
	public String getRemission(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REMISSION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.remission</code> attribute.
	 * @return the remission
	 */
	public String getRemission()
	{
		return getRemission( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.remission</code> attribute. 
	 * @param value the remission
	 */
	public void setRemission(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REMISSION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.remission</code> attribute. 
	 * @param value the remission
	 */
	public void setRemission(final String value)
	{
		setRemission( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.so</code> attribute.
	 * @return the so
	 */
	public String getSo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.so</code> attribute.
	 * @return the so
	 */
	public String getSo()
	{
		return getSo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.so</code> attribute. 
	 * @param value the so
	 */
	public void setSo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.so</code> attribute. 
	 * @param value the so
	 */
	public void setSo(final String value)
	{
		setSo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.tolly</code> attribute.
	 * @return the tolly
	 */
	public String getTolly(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOLLY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchSearch.tolly</code> attribute.
	 * @return the tolly
	 */
	public String getTolly()
	{
		return getTolly( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.tolly</code> attribute. 
	 * @param value the tolly
	 */
	public void setTolly(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOLLY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchSearch.tolly</code> attribute. 
	 * @param value the tolly
	 */
	public void setTolly(final String value)
	{
		setTolly( getSession().getSessionContext(), value );
	}
	
}
