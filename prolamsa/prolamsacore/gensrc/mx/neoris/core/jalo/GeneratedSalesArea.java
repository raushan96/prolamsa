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
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem SalesArea}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedSalesArea extends GenericItem
{
	/** Qualifier of the <code>SalesArea.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>SalesArea.manager</code> attribute **/
	public static final String MANAGER = "manager";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(MANAGER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SalesArea.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SalesArea.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SalesArea.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SalesArea.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SalesArea.manager</code> attribute.
	 * @return the manager
	 */
	public B2BCustomer getManager(final SessionContext ctx)
	{
		return (B2BCustomer)getProperty( ctx, MANAGER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SalesArea.manager</code> attribute.
	 * @return the manager
	 */
	public B2BCustomer getManager()
	{
		return getManager( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SalesArea.manager</code> attribute. 
	 * @param value the manager
	 */
	public void setManager(final SessionContext ctx, final B2BCustomer value)
	{
		setProperty(ctx, MANAGER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SalesArea.manager</code> attribute. 
	 * @param value the manager
	 */
	public void setManager(final B2BCustomer value)
	{
		setManager( getSession().getSessionContext(), value );
	}
	
}
