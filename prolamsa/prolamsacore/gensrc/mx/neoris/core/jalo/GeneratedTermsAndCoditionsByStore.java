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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem TermsAndCoditionsByStore}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTermsAndCoditionsByStore extends GenericItem
{
	/** Qualifier of the <code>TermsAndCoditionsByStore.baseStoreId</code> attribute **/
	public static final String BASESTOREID = "baseStoreId";
	/** Qualifier of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute **/
	public static final String TERMSANDCONDITIONSCHECKED = "termsAndConditionsChecked";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(BASESTOREID, AttributeMode.INITIAL);
		tmp.put(TERMSANDCONDITIONSCHECKED, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TermsAndCoditionsByStore.baseStoreId</code> attribute.
	 * @return the baseStoreId
	 */
	public String getBaseStoreId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BASESTOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TermsAndCoditionsByStore.baseStoreId</code> attribute.
	 * @return the baseStoreId
	 */
	public String getBaseStoreId()
	{
		return getBaseStoreId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TermsAndCoditionsByStore.baseStoreId</code> attribute. 
	 * @param value the baseStoreId
	 */
	public void setBaseStoreId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BASESTOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TermsAndCoditionsByStore.baseStoreId</code> attribute. 
	 * @param value the baseStoreId
	 */
	public void setBaseStoreId(final String value)
	{
		setBaseStoreId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute.
	 * @return the termsAndConditionsChecked
	 */
	public Boolean isTermsAndConditionsChecked(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, TERMSANDCONDITIONSCHECKED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute.
	 * @return the termsAndConditionsChecked
	 */
	public Boolean isTermsAndConditionsChecked()
	{
		return isTermsAndConditionsChecked( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute. 
	 * @return the termsAndConditionsChecked
	 */
	public boolean isTermsAndConditionsCheckedAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isTermsAndConditionsChecked( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute. 
	 * @return the termsAndConditionsChecked
	 */
	public boolean isTermsAndConditionsCheckedAsPrimitive()
	{
		return isTermsAndConditionsCheckedAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, TERMSANDCONDITIONSCHECKED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final Boolean value)
	{
		setTermsAndConditionsChecked( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final SessionContext ctx, final boolean value)
	{
		setTermsAndConditionsChecked( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TermsAndCoditionsByStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final boolean value)
	{
		setTermsAndConditionsChecked( getSession().getSessionContext(), value );
	}
	
}
