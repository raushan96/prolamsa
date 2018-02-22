/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo.btg;

import de.hybris.platform.btg.jalo.BTGAbstractOrderOperand;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Currency;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link mx.neoris.core.jalo.btg.BTGOrganizationTotalSpentInCurrencyLastYearOperand BTGOrganizationTotalSpentInCurrencyLastYearOperand}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBTGOrganizationTotalSpentInCurrencyLastYearOperand extends BTGAbstractOrderOperand
{
	/** Qualifier of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.productCatalogId</code> attribute **/
	public static final String PRODUCTCATALOGID = "productCatalogId";
	/** Qualifier of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.categoryCode</code> attribute **/
	public static final String CATEGORYCODE = "categoryCode";
	/** Qualifier of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.currency</code> attribute **/
	public static final String CURRENCY = "currency";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(BTGAbstractOrderOperand.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCTCATALOGID, AttributeMode.INITIAL);
		tmp.put(CATEGORYCODE, AttributeMode.INITIAL);
		tmp.put(CURRENCY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.categoryCode</code> attribute.
	 * @return the categoryCode
	 */
	public String getCategoryCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CATEGORYCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.categoryCode</code> attribute.
	 * @return the categoryCode
	 */
	public String getCategoryCode()
	{
		return getCategoryCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.categoryCode</code> attribute. 
	 * @param value the categoryCode
	 */
	public void setCategoryCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CATEGORYCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.categoryCode</code> attribute. 
	 * @param value the categoryCode
	 */
	public void setCategoryCode(final String value)
	{
		setCategoryCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.currency</code> attribute.
	 * @return the currency
	 */
	public Currency getCurrency(final SessionContext ctx)
	{
		return (Currency)getProperty( ctx, CURRENCY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.currency</code> attribute.
	 * @return the currency
	 */
	public Currency getCurrency()
	{
		return getCurrency( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final SessionContext ctx, final Currency value)
	{
		setProperty(ctx, CURRENCY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final Currency value)
	{
		setCurrency( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.productCatalogId</code> attribute.
	 * @return the productCatalogId
	 */
	public String getProductCatalogId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCATALOGID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.productCatalogId</code> attribute.
	 * @return the productCatalogId
	 */
	public String getProductCatalogId()
	{
		return getProductCatalogId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.productCatalogId</code> attribute. 
	 * @param value the productCatalogId
	 */
	public void setProductCatalogId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCATALOGID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BTGOrganizationTotalSpentInCurrencyLastYearOperand.productCatalogId</code> attribute. 
	 * @param value the productCatalogId
	 */
	public void setProductCatalogId(final String value)
	{
		setProductCatalogId( getSession().getSessionContext(), value );
	}
	
}
