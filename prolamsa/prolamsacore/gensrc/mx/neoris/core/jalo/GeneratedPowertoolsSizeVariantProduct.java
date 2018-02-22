/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.variants.jalo.VariantProduct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link mx.neoris.core.jalo.PowertoolsSizeVariantProduct PowertoolsSizeVariantProduct}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedPowertoolsSizeVariantProduct extends VariantProduct
{
	/** Qualifier of the <code>PowertoolsSizeVariantProduct.size</code> attribute **/
	public static final String SIZE = "size";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(VariantProduct.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(SIZE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PowertoolsSizeVariantProduct.size</code> attribute.
	 * @return the size - Size of the product.
	 */
	public String getSize(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPowertoolsSizeVariantProduct.getSize requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SIZE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PowertoolsSizeVariantProduct.size</code> attribute.
	 * @return the size - Size of the product.
	 */
	public String getSize()
	{
		return getSize( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PowertoolsSizeVariantProduct.size</code> attribute. 
	 * @return the localized size - Size of the product.
	 */
	public Map<Language,String> getAllSize(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SIZE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PowertoolsSizeVariantProduct.size</code> attribute. 
	 * @return the localized size - Size of the product.
	 */
	public Map<Language,String> getAllSize()
	{
		return getAllSize( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PowertoolsSizeVariantProduct.size</code> attribute. 
	 * @param value the size - Size of the product.
	 */
	public void setSize(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPowertoolsSizeVariantProduct.setSize requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SIZE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PowertoolsSizeVariantProduct.size</code> attribute. 
	 * @param value the size - Size of the product.
	 */
	public void setSize(final String value)
	{
		setSize( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PowertoolsSizeVariantProduct.size</code> attribute. 
	 * @param value the size - Size of the product.
	 */
	public void setAllSize(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SIZE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PowertoolsSizeVariantProduct.size</code> attribute. 
	 * @param value the size - Size of the product.
	 */
	public void setAllSize(final Map<Language,String> value)
	{
		setAllSize( getSession().getSessionContext(), value );
	}
	
}
