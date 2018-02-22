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
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.product.Product ProlamsaProduct}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedProlamsaProduct extends Product
{
	/** Qualifier of the <code>ProlamsaProduct.mtEquiv</code> attribute **/
	public static final String MTEQUIV = "mtEquiv";
	/** Qualifier of the <code>ProlamsaProduct.family</code> attribute **/
	public static final String FAMILY = "family";
	/** Qualifier of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute **/
	public static final String BUNDLELBEQUIV = "bundleLbEquiv";
	/** Qualifier of the <code>ProlamsaProduct.dimB</code> attribute **/
	public static final String DIMB = "dimB";
	/** Qualifier of the <code>ProlamsaProduct.codeForFavorites</code> attribute **/
	public static final String CODEFORFAVORITES = "codeForFavorites";
	/** Qualifier of the <code>ProlamsaProduct.strategy</code> attribute **/
	public static final String STRATEGY = "strategy";
	/** Qualifier of the <code>ProlamsaProduct.pcLbEquiv</code> attribute **/
	public static final String PCLBEQUIV = "pcLbEquiv";
	/** Qualifier of the <code>ProlamsaProduct.norm</code> attribute **/
	public static final String NORM = "norm";
	/** Qualifier of the <code>ProlamsaProduct.grade</code> attribute **/
	public static final String GRADE = "grade";
	/** Qualifier of the <code>ProlamsaProduct.ftKgEquiv</code> attribute **/
	public static final String FTKGEQUIV = "ftKgEquiv";
	/** Qualifier of the <code>ProlamsaProduct.piecesPerBundle</code> attribute **/
	public static final String PIECESPERBUNDLE = "piecesPerBundle";
	/** Qualifier of the <code>ProlamsaProduct.section</code> attribute **/
	public static final String SECTION = "section";
	/** Qualifier of the <code>ProlamsaProduct.drift</code> attribute **/
	public static final String DRIFT = "drift";
	/** Qualifier of the <code>ProlamsaProduct.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>ProlamsaProduct.dimA</code> attribute **/
	public static final String DIMA = "dimA";
	/** Qualifier of the <code>ProlamsaProduct.kgEquiv</code> attribute **/
	public static final String KGEQUIV = "kgEquiv";
	/** Qualifier of the <code>ProlamsaProduct.ftLbEquiv</code> attribute **/
	public static final String FTLBEQUIV = "ftLbEquiv";
	/** Qualifier of the <code>ProlamsaProduct.pcKgEquiv</code> attribute **/
	public static final String PCKGEQUIV = "pcKgEquiv";
	/** Qualifier of the <code>ProlamsaProduct.finish</code> attribute **/
	public static final String FINISH = "finish";
	/** Qualifier of the <code>ProlamsaProduct.idScarf</code> attribute **/
	public static final String IDSCARF = "idScarf";
	/** Qualifier of the <code>ProlamsaProduct.deburr</code> attribute **/
	public static final String DEBURR = "deburr";
	/** Qualifier of the <code>ProlamsaProduct.subFamily</code> attribute **/
	public static final String SUBFAMILY = "subFamily";
	/** Qualifier of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute **/
	public static final String BUNDLEKGEQUIV = "bundleKgEquiv";
	/** Qualifier of the <code>ProlamsaProduct.fullDescription</code> attribute **/
	public static final String FULLDESCRIPTION = "fullDescription";
	/** Qualifier of the <code>ProlamsaProduct.coating</code> attribute **/
	public static final String COATING = "coating";
	/** Qualifier of the <code>ProlamsaProduct.approvalVisibility</code> attribute **/
	public static final String APPROVALVISIBILITY = "approvalVisibility";
	/** Qualifier of the <code>ProlamsaProduct.relatedProductCodes</code> attribute **/
	public static final String RELATEDPRODUCTCODES = "relatedProductCodes";
	/** Qualifier of the <code>ProlamsaProduct.lbEquiv</code> attribute **/
	public static final String LBEQUIV = "lbEquiv";
	/** Qualifier of the <code>ProlamsaProduct.endFinish</code> attribute **/
	public static final String ENDFINISH = "endFinish";
	/** Qualifier of the <code>ProlamsaProduct.steel</code> attribute **/
	public static final String STEEL = "steel";
	/** Qualifier of the <code>ProlamsaProduct.lengthmm</code> attribute **/
	public static final String LENGTHMM = "lengthmm";
	/** Qualifier of the <code>ProlamsaProduct.length</code> attribute **/
	public static final String LENGTH = "length";
	/** Qualifier of the <code>ProlamsaProduct.endFacer</code> attribute **/
	public static final String ENDFACER = "endFacer";
	/** Qualifier of the <code>ProlamsaProduct.materialType</code> attribute **/
	public static final String MATERIALTYPE = "materialType";
	/** Qualifier of the <code>ProlamsaProduct.manufacturingDescription</code> attribute **/
	public static final String MANUFACTURINGDESCRIPTION = "manufacturingDescription";
	/** Qualifier of the <code>ProlamsaProduct.thread</code> attribute **/
	public static final String THREAD = "thread";
	/** Qualifier of the <code>ProlamsaProduct.ftEquiv</code> attribute **/
	public static final String FTEQUIV = "ftEquiv";
	/** Qualifier of the <code>ProlamsaProduct.covering</code> attribute **/
	public static final String COVERING = "covering";
	/** Qualifier of the <code>ProlamsaProduct.wallThickness</code> attribute **/
	public static final String WALLTHICKNESS = "wallThickness";
	/** Qualifier of the <code>ProlamsaProduct.packing</code> attribute **/
	public static final String PACKING = "packing";
	/** Qualifier of the <code>ProlamsaProduct.tnEquiv</code> attribute **/
	public static final String TNEQUIV = "tnEquiv";
	/** Qualifier of the <code>ProlamsaProduct.standard</code> attribute **/
	public static final String STANDARD = "standard";
	/** Qualifier of the <code>ProlamsaProduct.lengthCode</code> attribute **/
	public static final String LENGTHCODE = "lengthCode";
	/** Qualifier of the <code>ProlamsaProduct.ftTnEquiv</code> attribute **/
	public static final String FTTNEQUIV = "ftTnEquiv";
	/** Qualifier of the <code>ProlamsaProduct.quality</code> attribute **/
	public static final String QUALITY = "quality";
	/** Qualifier of the <code>ProlamsaProduct.baseCode</code> attribute **/
	public static final String BASECODE = "baseCode";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(Product.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(MTEQUIV, AttributeMode.INITIAL);
		tmp.put(FAMILY, AttributeMode.INITIAL);
		tmp.put(BUNDLELBEQUIV, AttributeMode.INITIAL);
		tmp.put(DIMB, AttributeMode.INITIAL);
		tmp.put(CODEFORFAVORITES, AttributeMode.INITIAL);
		tmp.put(STRATEGY, AttributeMode.INITIAL);
		tmp.put(PCLBEQUIV, AttributeMode.INITIAL);
		tmp.put(NORM, AttributeMode.INITIAL);
		tmp.put(GRADE, AttributeMode.INITIAL);
		tmp.put(FTKGEQUIV, AttributeMode.INITIAL);
		tmp.put(PIECESPERBUNDLE, AttributeMode.INITIAL);
		tmp.put(SECTION, AttributeMode.INITIAL);
		tmp.put(DRIFT, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(DIMA, AttributeMode.INITIAL);
		tmp.put(KGEQUIV, AttributeMode.INITIAL);
		tmp.put(FTLBEQUIV, AttributeMode.INITIAL);
		tmp.put(PCKGEQUIV, AttributeMode.INITIAL);
		tmp.put(FINISH, AttributeMode.INITIAL);
		tmp.put(IDSCARF, AttributeMode.INITIAL);
		tmp.put(DEBURR, AttributeMode.INITIAL);
		tmp.put(SUBFAMILY, AttributeMode.INITIAL);
		tmp.put(BUNDLEKGEQUIV, AttributeMode.INITIAL);
		tmp.put(FULLDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(COATING, AttributeMode.INITIAL);
		tmp.put(APPROVALVISIBILITY, AttributeMode.INITIAL);
		tmp.put(RELATEDPRODUCTCODES, AttributeMode.INITIAL);
		tmp.put(LBEQUIV, AttributeMode.INITIAL);
		tmp.put(ENDFINISH, AttributeMode.INITIAL);
		tmp.put(STEEL, AttributeMode.INITIAL);
		tmp.put(LENGTHMM, AttributeMode.INITIAL);
		tmp.put(LENGTH, AttributeMode.INITIAL);
		tmp.put(ENDFACER, AttributeMode.INITIAL);
		tmp.put(MATERIALTYPE, AttributeMode.INITIAL);
		tmp.put(MANUFACTURINGDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(THREAD, AttributeMode.INITIAL);
		tmp.put(FTEQUIV, AttributeMode.INITIAL);
		tmp.put(COVERING, AttributeMode.INITIAL);
		tmp.put(WALLTHICKNESS, AttributeMode.INITIAL);
		tmp.put(PACKING, AttributeMode.INITIAL);
		tmp.put(TNEQUIV, AttributeMode.INITIAL);
		tmp.put(STANDARD, AttributeMode.INITIAL);
		tmp.put(LENGTHCODE, AttributeMode.INITIAL);
		tmp.put(FTTNEQUIV, AttributeMode.INITIAL);
		tmp.put(QUALITY, AttributeMode.INITIAL);
		tmp.put(BASECODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.approvalVisibility</code> attribute.
	 * @return the approvalVisibility
	 */
	public String getApprovalVisibility(final SessionContext ctx)
	{
		return (String)getProperty( ctx, APPROVALVISIBILITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.approvalVisibility</code> attribute.
	 * @return the approvalVisibility
	 */
	public String getApprovalVisibility()
	{
		return getApprovalVisibility( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.approvalVisibility</code> attribute. 
	 * @param value the approvalVisibility
	 */
	public void setApprovalVisibility(final SessionContext ctx, final String value)
	{
		setProperty(ctx, APPROVALVISIBILITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.approvalVisibility</code> attribute. 
	 * @param value the approvalVisibility
	 */
	public void setApprovalVisibility(final String value)
	{
		setApprovalVisibility( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.baseCode</code> attribute.
	 * @return the baseCode
	 */
	public String getBaseCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BASECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.baseCode</code> attribute.
	 * @return the baseCode
	 */
	public String getBaseCode()
	{
		return getBaseCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.baseCode</code> attribute. 
	 * @param value the baseCode
	 */
	public void setBaseCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BASECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.baseCode</code> attribute. 
	 * @param value the baseCode
	 */
	public void setBaseCode(final String value)
	{
		setBaseCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute.
	 * @return the bundleKgEquiv
	 */
	public Double getBundleKgEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BUNDLEKGEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute.
	 * @return the bundleKgEquiv
	 */
	public Double getBundleKgEquiv()
	{
		return getBundleKgEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute. 
	 * @return the bundleKgEquiv
	 */
	public double getBundleKgEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getBundleKgEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute. 
	 * @return the bundleKgEquiv
	 */
	public double getBundleKgEquivAsPrimitive()
	{
		return getBundleKgEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute. 
	 * @param value the bundleKgEquiv
	 */
	public void setBundleKgEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BUNDLEKGEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute. 
	 * @param value the bundleKgEquiv
	 */
	public void setBundleKgEquiv(final Double value)
	{
		setBundleKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute. 
	 * @param value the bundleKgEquiv
	 */
	public void setBundleKgEquiv(final SessionContext ctx, final double value)
	{
		setBundleKgEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleKgEquiv</code> attribute. 
	 * @param value the bundleKgEquiv
	 */
	public void setBundleKgEquiv(final double value)
	{
		setBundleKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute.
	 * @return the bundleLbEquiv
	 */
	public Double getBundleLbEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BUNDLELBEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute.
	 * @return the bundleLbEquiv
	 */
	public Double getBundleLbEquiv()
	{
		return getBundleLbEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute. 
	 * @return the bundleLbEquiv
	 */
	public double getBundleLbEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getBundleLbEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute. 
	 * @return the bundleLbEquiv
	 */
	public double getBundleLbEquivAsPrimitive()
	{
		return getBundleLbEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute. 
	 * @param value the bundleLbEquiv
	 */
	public void setBundleLbEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BUNDLELBEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute. 
	 * @param value the bundleLbEquiv
	 */
	public void setBundleLbEquiv(final Double value)
	{
		setBundleLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute. 
	 * @param value the bundleLbEquiv
	 */
	public void setBundleLbEquiv(final SessionContext ctx, final double value)
	{
		setBundleLbEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.bundleLbEquiv</code> attribute. 
	 * @param value the bundleLbEquiv
	 */
	public void setBundleLbEquiv(final double value)
	{
		setBundleLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.coating</code> attribute.
	 * @return the coating
	 */
	public String getCoating(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COATING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.coating</code> attribute.
	 * @return the coating
	 */
	public String getCoating()
	{
		return getCoating( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.coating</code> attribute. 
	 * @param value the coating
	 */
	public void setCoating(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COATING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.coating</code> attribute. 
	 * @param value the coating
	 */
	public void setCoating(final String value)
	{
		setCoating( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.codeForFavorites</code> attribute.
	 * @return the codeForFavorites
	 */
	public String getCodeForFavorites(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODEFORFAVORITES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.codeForFavorites</code> attribute.
	 * @return the codeForFavorites
	 */
	public String getCodeForFavorites()
	{
		return getCodeForFavorites( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.codeForFavorites</code> attribute. 
	 * @param value the codeForFavorites
	 */
	public void setCodeForFavorites(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODEFORFAVORITES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.codeForFavorites</code> attribute. 
	 * @param value the codeForFavorites
	 */
	public void setCodeForFavorites(final String value)
	{
		setCodeForFavorites( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.covering</code> attribute.
	 * @return the covering
	 */
	public String getCovering(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COVERING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.covering</code> attribute.
	 * @return the covering
	 */
	public String getCovering()
	{
		return getCovering( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.covering</code> attribute. 
	 * @param value the covering
	 */
	public void setCovering(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COVERING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.covering</code> attribute. 
	 * @param value the covering
	 */
	public void setCovering(final String value)
	{
		setCovering( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.deburr</code> attribute.
	 * @return the deburr
	 */
	public String getDeburr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEBURR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.deburr</code> attribute.
	 * @return the deburr
	 */
	public String getDeburr()
	{
		return getDeburr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.deburr</code> attribute. 
	 * @param value the deburr
	 */
	public void setDeburr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEBURR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.deburr</code> attribute. 
	 * @param value the deburr
	 */
	public void setDeburr(final String value)
	{
		setDeburr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimA</code> attribute.
	 * @return the dimA
	 */
	public Double getDimA(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, DIMA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimA</code> attribute.
	 * @return the dimA
	 */
	public Double getDimA()
	{
		return getDimA( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimA</code> attribute. 
	 * @return the dimA
	 */
	public double getDimAAsPrimitive(final SessionContext ctx)
	{
		Double value = getDimA( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimA</code> attribute. 
	 * @return the dimA
	 */
	public double getDimAAsPrimitive()
	{
		return getDimAAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimA</code> attribute. 
	 * @param value the dimA
	 */
	public void setDimA(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, DIMA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimA</code> attribute. 
	 * @param value the dimA
	 */
	public void setDimA(final Double value)
	{
		setDimA( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimA</code> attribute. 
	 * @param value the dimA
	 */
	public void setDimA(final SessionContext ctx, final double value)
	{
		setDimA( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimA</code> attribute. 
	 * @param value the dimA
	 */
	public void setDimA(final double value)
	{
		setDimA( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimB</code> attribute.
	 * @return the dimB
	 */
	public Double getDimB(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, DIMB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimB</code> attribute.
	 * @return the dimB
	 */
	public Double getDimB()
	{
		return getDimB( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimB</code> attribute. 
	 * @return the dimB
	 */
	public double getDimBAsPrimitive(final SessionContext ctx)
	{
		Double value = getDimB( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.dimB</code> attribute. 
	 * @return the dimB
	 */
	public double getDimBAsPrimitive()
	{
		return getDimBAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimB</code> attribute. 
	 * @param value the dimB
	 */
	public void setDimB(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, DIMB,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimB</code> attribute. 
	 * @param value the dimB
	 */
	public void setDimB(final Double value)
	{
		setDimB( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimB</code> attribute. 
	 * @param value the dimB
	 */
	public void setDimB(final SessionContext ctx, final double value)
	{
		setDimB( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.dimB</code> attribute. 
	 * @param value the dimB
	 */
	public void setDimB(final double value)
	{
		setDimB( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.drift</code> attribute.
	 * @return the drift
	 */
	public String getDrift(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DRIFT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.drift</code> attribute.
	 * @return the drift
	 */
	public String getDrift()
	{
		return getDrift( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.drift</code> attribute. 
	 * @param value the drift
	 */
	public void setDrift(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DRIFT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.drift</code> attribute. 
	 * @param value the drift
	 */
	public void setDrift(final String value)
	{
		setDrift( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.endFacer</code> attribute.
	 * @return the endFacer
	 */
	public String getEndFacer(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ENDFACER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.endFacer</code> attribute.
	 * @return the endFacer
	 */
	public String getEndFacer()
	{
		return getEndFacer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.endFacer</code> attribute. 
	 * @param value the endFacer
	 */
	public void setEndFacer(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ENDFACER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.endFacer</code> attribute. 
	 * @param value the endFacer
	 */
	public void setEndFacer(final String value)
	{
		setEndFacer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.endFinish</code> attribute.
	 * @return the endFinish
	 */
	public String getEndFinish(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ENDFINISH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.endFinish</code> attribute.
	 * @return the endFinish
	 */
	public String getEndFinish()
	{
		return getEndFinish( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.endFinish</code> attribute. 
	 * @param value the endFinish
	 */
	public void setEndFinish(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ENDFINISH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.endFinish</code> attribute. 
	 * @param value the endFinish
	 */
	public void setEndFinish(final String value)
	{
		setEndFinish( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.family</code> attribute.
	 * @return the family
	 */
	public String getFamily(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FAMILY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.family</code> attribute.
	 * @return the family
	 */
	public String getFamily()
	{
		return getFamily( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.family</code> attribute. 
	 * @param value the family
	 */
	public void setFamily(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FAMILY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.family</code> attribute. 
	 * @param value the family
	 */
	public void setFamily(final String value)
	{
		setFamily( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.finish</code> attribute.
	 * @return the finish
	 */
	public String getFinish(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FINISH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.finish</code> attribute.
	 * @return the finish
	 */
	public String getFinish()
	{
		return getFinish( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.finish</code> attribute. 
	 * @param value the finish
	 */
	public void setFinish(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FINISH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.finish</code> attribute. 
	 * @param value the finish
	 */
	public void setFinish(final String value)
	{
		setFinish( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftEquiv</code> attribute.
	 * @return the ftEquiv
	 */
	public Double getFtEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, FTEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftEquiv</code> attribute.
	 * @return the ftEquiv
	 */
	public Double getFtEquiv()
	{
		return getFtEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftEquiv</code> attribute. 
	 * @return the ftEquiv
	 */
	public double getFtEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getFtEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftEquiv</code> attribute. 
	 * @return the ftEquiv
	 */
	public double getFtEquivAsPrimitive()
	{
		return getFtEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftEquiv</code> attribute. 
	 * @param value the ftEquiv
	 */
	public void setFtEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, FTEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftEquiv</code> attribute. 
	 * @param value the ftEquiv
	 */
	public void setFtEquiv(final Double value)
	{
		setFtEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftEquiv</code> attribute. 
	 * @param value the ftEquiv
	 */
	public void setFtEquiv(final SessionContext ctx, final double value)
	{
		setFtEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftEquiv</code> attribute. 
	 * @param value the ftEquiv
	 */
	public void setFtEquiv(final double value)
	{
		setFtEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute.
	 * @return the ftKgEquiv
	 */
	public Double getFtKgEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, FTKGEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute.
	 * @return the ftKgEquiv
	 */
	public Double getFtKgEquiv()
	{
		return getFtKgEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute. 
	 * @return the ftKgEquiv
	 */
	public double getFtKgEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getFtKgEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute. 
	 * @return the ftKgEquiv
	 */
	public double getFtKgEquivAsPrimitive()
	{
		return getFtKgEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute. 
	 * @param value the ftKgEquiv
	 */
	public void setFtKgEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, FTKGEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute. 
	 * @param value the ftKgEquiv
	 */
	public void setFtKgEquiv(final Double value)
	{
		setFtKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute. 
	 * @param value the ftKgEquiv
	 */
	public void setFtKgEquiv(final SessionContext ctx, final double value)
	{
		setFtKgEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftKgEquiv</code> attribute. 
	 * @param value the ftKgEquiv
	 */
	public void setFtKgEquiv(final double value)
	{
		setFtKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute.
	 * @return the ftLbEquiv
	 */
	public Double getFtLbEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, FTLBEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute.
	 * @return the ftLbEquiv
	 */
	public Double getFtLbEquiv()
	{
		return getFtLbEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute. 
	 * @return the ftLbEquiv
	 */
	public double getFtLbEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getFtLbEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute. 
	 * @return the ftLbEquiv
	 */
	public double getFtLbEquivAsPrimitive()
	{
		return getFtLbEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute. 
	 * @param value the ftLbEquiv
	 */
	public void setFtLbEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, FTLBEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute. 
	 * @param value the ftLbEquiv
	 */
	public void setFtLbEquiv(final Double value)
	{
		setFtLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute. 
	 * @param value the ftLbEquiv
	 */
	public void setFtLbEquiv(final SessionContext ctx, final double value)
	{
		setFtLbEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftLbEquiv</code> attribute. 
	 * @param value the ftLbEquiv
	 */
	public void setFtLbEquiv(final double value)
	{
		setFtLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute.
	 * @return the ftTnEquiv
	 */
	public Double getFtTnEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, FTTNEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute.
	 * @return the ftTnEquiv
	 */
	public Double getFtTnEquiv()
	{
		return getFtTnEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute. 
	 * @return the ftTnEquiv
	 */
	public double getFtTnEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getFtTnEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute. 
	 * @return the ftTnEquiv
	 */
	public double getFtTnEquivAsPrimitive()
	{
		return getFtTnEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute. 
	 * @param value the ftTnEquiv
	 */
	public void setFtTnEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, FTTNEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute. 
	 * @param value the ftTnEquiv
	 */
	public void setFtTnEquiv(final Double value)
	{
		setFtTnEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute. 
	 * @param value the ftTnEquiv
	 */
	public void setFtTnEquiv(final SessionContext ctx, final double value)
	{
		setFtTnEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.ftTnEquiv</code> attribute. 
	 * @param value the ftTnEquiv
	 */
	public void setFtTnEquiv(final double value)
	{
		setFtTnEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.fullDescription</code> attribute.
	 * @return the fullDescription
	 */
	public String getFullDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProlamsaProduct.getFullDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, FULLDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.fullDescription</code> attribute.
	 * @return the fullDescription
	 */
	public String getFullDescription()
	{
		return getFullDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.fullDescription</code> attribute. 
	 * @return the localized fullDescription
	 */
	public Map<Language,String> getAllFullDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,FULLDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.fullDescription</code> attribute. 
	 * @return the localized fullDescription
	 */
	public Map<Language,String> getAllFullDescription()
	{
		return getAllFullDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.fullDescription</code> attribute. 
	 * @param value the fullDescription
	 */
	public void setFullDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProlamsaProduct.setFullDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, FULLDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.fullDescription</code> attribute. 
	 * @param value the fullDescription
	 */
	public void setFullDescription(final String value)
	{
		setFullDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.fullDescription</code> attribute. 
	 * @param value the fullDescription
	 */
	public void setAllFullDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,FULLDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.fullDescription</code> attribute. 
	 * @param value the fullDescription
	 */
	public void setAllFullDescription(final Map<Language,String> value)
	{
		setAllFullDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.grade</code> attribute.
	 * @return the grade
	 */
	public String getGrade(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GRADE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.grade</code> attribute.
	 * @return the grade
	 */
	public String getGrade()
	{
		return getGrade( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.grade</code> attribute. 
	 * @param value the grade
	 */
	public void setGrade(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GRADE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.grade</code> attribute. 
	 * @param value the grade
	 */
	public void setGrade(final String value)
	{
		setGrade( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.idScarf</code> attribute.
	 * @return the idScarf
	 */
	public String getIdScarf(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IDSCARF);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.idScarf</code> attribute.
	 * @return the idScarf
	 */
	public String getIdScarf()
	{
		return getIdScarf( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.idScarf</code> attribute. 
	 * @param value the idScarf
	 */
	public void setIdScarf(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IDSCARF,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.idScarf</code> attribute. 
	 * @param value the idScarf
	 */
	public void setIdScarf(final String value)
	{
		setIdScarf( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.kgEquiv</code> attribute.
	 * @return the kgEquiv
	 */
	public Double getKgEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, KGEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.kgEquiv</code> attribute.
	 * @return the kgEquiv
	 */
	public Double getKgEquiv()
	{
		return getKgEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.kgEquiv</code> attribute. 
	 * @return the kgEquiv
	 */
	public double getKgEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getKgEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.kgEquiv</code> attribute. 
	 * @return the kgEquiv
	 */
	public double getKgEquivAsPrimitive()
	{
		return getKgEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.kgEquiv</code> attribute. 
	 * @param value the kgEquiv
	 */
	public void setKgEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, KGEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.kgEquiv</code> attribute. 
	 * @param value the kgEquiv
	 */
	public void setKgEquiv(final Double value)
	{
		setKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.kgEquiv</code> attribute. 
	 * @param value the kgEquiv
	 */
	public void setKgEquiv(final SessionContext ctx, final double value)
	{
		setKgEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.kgEquiv</code> attribute. 
	 * @param value the kgEquiv
	 */
	public void setKgEquiv(final double value)
	{
		setKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lbEquiv</code> attribute.
	 * @return the lbEquiv
	 */
	public Double getLbEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LBEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lbEquiv</code> attribute.
	 * @return the lbEquiv
	 */
	public Double getLbEquiv()
	{
		return getLbEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lbEquiv</code> attribute. 
	 * @return the lbEquiv
	 */
	public double getLbEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getLbEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lbEquiv</code> attribute. 
	 * @return the lbEquiv
	 */
	public double getLbEquivAsPrimitive()
	{
		return getLbEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lbEquiv</code> attribute. 
	 * @param value the lbEquiv
	 */
	public void setLbEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LBEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lbEquiv</code> attribute. 
	 * @param value the lbEquiv
	 */
	public void setLbEquiv(final Double value)
	{
		setLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lbEquiv</code> attribute. 
	 * @param value the lbEquiv
	 */
	public void setLbEquiv(final SessionContext ctx, final double value)
	{
		setLbEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lbEquiv</code> attribute. 
	 * @param value the lbEquiv
	 */
	public void setLbEquiv(final double value)
	{
		setLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.length</code> attribute.
	 * @return the length - Material length in ft
	 */
	public Double getLength(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LENGTH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.length</code> attribute.
	 * @return the length - Material length in ft
	 */
	public Double getLength()
	{
		return getLength( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.length</code> attribute. 
	 * @return the length - Material length in ft
	 */
	public double getLengthAsPrimitive(final SessionContext ctx)
	{
		Double value = getLength( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.length</code> attribute. 
	 * @return the length - Material length in ft
	 */
	public double getLengthAsPrimitive()
	{
		return getLengthAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.length</code> attribute. 
	 * @param value the length - Material length in ft
	 */
	public void setLength(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LENGTH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.length</code> attribute. 
	 * @param value the length - Material length in ft
	 */
	public void setLength(final Double value)
	{
		setLength( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.length</code> attribute. 
	 * @param value the length - Material length in ft
	 */
	public void setLength(final SessionContext ctx, final double value)
	{
		setLength( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.length</code> attribute. 
	 * @param value the length - Material length in ft
	 */
	public void setLength(final double value)
	{
		setLength( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lengthCode</code> attribute.
	 * @return the lengthCode
	 */
	public String getLengthCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LENGTHCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lengthCode</code> attribute.
	 * @return the lengthCode
	 */
	public String getLengthCode()
	{
		return getLengthCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lengthCode</code> attribute. 
	 * @param value the lengthCode
	 */
	public void setLengthCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LENGTHCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lengthCode</code> attribute. 
	 * @param value the lengthCode
	 */
	public void setLengthCode(final String value)
	{
		setLengthCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lengthmm</code> attribute.
	 * @return the lengthmm - Material length in mm
	 */
	public Double getLengthmm(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LENGTHMM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lengthmm</code> attribute.
	 * @return the lengthmm - Material length in mm
	 */
	public Double getLengthmm()
	{
		return getLengthmm( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lengthmm</code> attribute. 
	 * @return the lengthmm - Material length in mm
	 */
	public double getLengthmmAsPrimitive(final SessionContext ctx)
	{
		Double value = getLengthmm( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.lengthmm</code> attribute. 
	 * @return the lengthmm - Material length in mm
	 */
	public double getLengthmmAsPrimitive()
	{
		return getLengthmmAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lengthmm</code> attribute. 
	 * @param value the lengthmm - Material length in mm
	 */
	public void setLengthmm(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LENGTHMM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lengthmm</code> attribute. 
	 * @param value the lengthmm - Material length in mm
	 */
	public void setLengthmm(final Double value)
	{
		setLengthmm( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lengthmm</code> attribute. 
	 * @param value the lengthmm - Material length in mm
	 */
	public void setLengthmm(final SessionContext ctx, final double value)
	{
		setLengthmm( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.lengthmm</code> attribute. 
	 * @param value the lengthmm - Material length in mm
	 */
	public void setLengthmm(final double value)
	{
		setLengthmm( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.location</code> attribute.
	 * @return the location
	 */
	public EnumerationValue getLocation(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.location</code> attribute.
	 * @return the location
	 */
	public EnumerationValue getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final EnumerationValue value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute.
	 * @return the manufacturingDescription
	 */
	public String getManufacturingDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProlamsaProduct.getManufacturingDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, MANUFACTURINGDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute.
	 * @return the manufacturingDescription
	 */
	public String getManufacturingDescription()
	{
		return getManufacturingDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute. 
	 * @return the localized manufacturingDescription
	 */
	public Map<Language,String> getAllManufacturingDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,MANUFACTURINGDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute. 
	 * @return the localized manufacturingDescription
	 */
	public Map<Language,String> getAllManufacturingDescription()
	{
		return getAllManufacturingDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute. 
	 * @param value the manufacturingDescription
	 */
	public void setManufacturingDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProlamsaProduct.setManufacturingDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MANUFACTURINGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute. 
	 * @param value the manufacturingDescription
	 */
	public void setManufacturingDescription(final String value)
	{
		setManufacturingDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute. 
	 * @param value the manufacturingDescription
	 */
	public void setAllManufacturingDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,MANUFACTURINGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.manufacturingDescription</code> attribute. 
	 * @param value the manufacturingDescription
	 */
	public void setAllManufacturingDescription(final Map<Language,String> value)
	{
		setAllManufacturingDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.materialType</code> attribute.
	 * @return the materialType
	 */
	public String getMaterialType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MATERIALTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.materialType</code> attribute.
	 * @return the materialType
	 */
	public String getMaterialType()
	{
		return getMaterialType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.materialType</code> attribute. 
	 * @param value the materialType
	 */
	public void setMaterialType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MATERIALTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.materialType</code> attribute. 
	 * @param value the materialType
	 */
	public void setMaterialType(final String value)
	{
		setMaterialType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.mtEquiv</code> attribute.
	 * @return the mtEquiv
	 */
	public Double getMtEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, MTEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.mtEquiv</code> attribute.
	 * @return the mtEquiv
	 */
	public Double getMtEquiv()
	{
		return getMtEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.mtEquiv</code> attribute. 
	 * @return the mtEquiv
	 */
	public double getMtEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getMtEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.mtEquiv</code> attribute. 
	 * @return the mtEquiv
	 */
	public double getMtEquivAsPrimitive()
	{
		return getMtEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.mtEquiv</code> attribute. 
	 * @param value the mtEquiv
	 */
	public void setMtEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, MTEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.mtEquiv</code> attribute. 
	 * @param value the mtEquiv
	 */
	public void setMtEquiv(final Double value)
	{
		setMtEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.mtEquiv</code> attribute. 
	 * @param value the mtEquiv
	 */
	public void setMtEquiv(final SessionContext ctx, final double value)
	{
		setMtEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.mtEquiv</code> attribute. 
	 * @param value the mtEquiv
	 */
	public void setMtEquiv(final double value)
	{
		setMtEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.norm</code> attribute.
	 * @return the norm
	 */
	public String getNorm(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NORM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.norm</code> attribute.
	 * @return the norm
	 */
	public String getNorm()
	{
		return getNorm( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.norm</code> attribute. 
	 * @param value the norm
	 */
	public void setNorm(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NORM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.norm</code> attribute. 
	 * @param value the norm
	 */
	public void setNorm(final String value)
	{
		setNorm( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.packing</code> attribute.
	 * @return the packing
	 */
	public String getPacking(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PACKING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.packing</code> attribute.
	 * @return the packing
	 */
	public String getPacking()
	{
		return getPacking( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.packing</code> attribute. 
	 * @param value the packing
	 */
	public void setPacking(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PACKING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.packing</code> attribute. 
	 * @param value the packing
	 */
	public void setPacking(final String value)
	{
		setPacking( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute.
	 * @return the pcKgEquiv
	 */
	public Double getPcKgEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PCKGEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute.
	 * @return the pcKgEquiv
	 */
	public Double getPcKgEquiv()
	{
		return getPcKgEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute. 
	 * @return the pcKgEquiv
	 */
	public double getPcKgEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getPcKgEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute. 
	 * @return the pcKgEquiv
	 */
	public double getPcKgEquivAsPrimitive()
	{
		return getPcKgEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute. 
	 * @param value the pcKgEquiv
	 */
	public void setPcKgEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PCKGEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute. 
	 * @param value the pcKgEquiv
	 */
	public void setPcKgEquiv(final Double value)
	{
		setPcKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute. 
	 * @param value the pcKgEquiv
	 */
	public void setPcKgEquiv(final SessionContext ctx, final double value)
	{
		setPcKgEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcKgEquiv</code> attribute. 
	 * @param value the pcKgEquiv
	 */
	public void setPcKgEquiv(final double value)
	{
		setPcKgEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute.
	 * @return the pcLbEquiv
	 */
	public Double getPcLbEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PCLBEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute.
	 * @return the pcLbEquiv
	 */
	public Double getPcLbEquiv()
	{
		return getPcLbEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute. 
	 * @return the pcLbEquiv
	 */
	public double getPcLbEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getPcLbEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute. 
	 * @return the pcLbEquiv
	 */
	public double getPcLbEquivAsPrimitive()
	{
		return getPcLbEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute. 
	 * @param value the pcLbEquiv
	 */
	public void setPcLbEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PCLBEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute. 
	 * @param value the pcLbEquiv
	 */
	public void setPcLbEquiv(final Double value)
	{
		setPcLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute. 
	 * @param value the pcLbEquiv
	 */
	public void setPcLbEquiv(final SessionContext ctx, final double value)
	{
		setPcLbEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.pcLbEquiv</code> attribute. 
	 * @param value the pcLbEquiv
	 */
	public void setPcLbEquiv(final double value)
	{
		setPcLbEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute.
	 * @return the piecesPerBundle
	 */
	public Double getPiecesPerBundle(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PIECESPERBUNDLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute.
	 * @return the piecesPerBundle
	 */
	public Double getPiecesPerBundle()
	{
		return getPiecesPerBundle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute. 
	 * @return the piecesPerBundle
	 */
	public double getPiecesPerBundleAsPrimitive(final SessionContext ctx)
	{
		Double value = getPiecesPerBundle( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute. 
	 * @return the piecesPerBundle
	 */
	public double getPiecesPerBundleAsPrimitive()
	{
		return getPiecesPerBundleAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute. 
	 * @param value the piecesPerBundle
	 */
	public void setPiecesPerBundle(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PIECESPERBUNDLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute. 
	 * @param value the piecesPerBundle
	 */
	public void setPiecesPerBundle(final Double value)
	{
		setPiecesPerBundle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute. 
	 * @param value the piecesPerBundle
	 */
	public void setPiecesPerBundle(final SessionContext ctx, final double value)
	{
		setPiecesPerBundle( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.piecesPerBundle</code> attribute. 
	 * @param value the piecesPerBundle
	 */
	public void setPiecesPerBundle(final double value)
	{
		setPiecesPerBundle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.quality</code> attribute.
	 * @return the quality
	 */
	public String getQuality(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUALITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.quality</code> attribute.
	 * @return the quality
	 */
	public String getQuality()
	{
		return getQuality( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.quality</code> attribute. 
	 * @param value the quality
	 */
	public void setQuality(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUALITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.quality</code> attribute. 
	 * @param value the quality
	 */
	public void setQuality(final String value)
	{
		setQuality( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.relatedProductCodes</code> attribute.
	 * @return the relatedProductCodes
	 */
	public List<String> getRelatedProductCodes(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, RELATEDPRODUCTCODES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.relatedProductCodes</code> attribute.
	 * @return the relatedProductCodes
	 */
	public List<String> getRelatedProductCodes()
	{
		return getRelatedProductCodes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.relatedProductCodes</code> attribute. 
	 * @param value the relatedProductCodes
	 */
	public void setRelatedProductCodes(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, RELATEDPRODUCTCODES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.relatedProductCodes</code> attribute. 
	 * @param value the relatedProductCodes
	 */
	public void setRelatedProductCodes(final List<String> value)
	{
		setRelatedProductCodes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.section</code> attribute.
	 * @return the section
	 */
	public String getSection(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SECTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.section</code> attribute.
	 * @return the section
	 */
	public String getSection()
	{
		return getSection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.section</code> attribute. 
	 * @param value the section
	 */
	public void setSection(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SECTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.section</code> attribute. 
	 * @param value the section
	 */
	public void setSection(final String value)
	{
		setSection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.standard</code> attribute.
	 * @return the standard
	 */
	public String getStandard(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STANDARD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.standard</code> attribute.
	 * @return the standard
	 */
	public String getStandard()
	{
		return getStandard( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.standard</code> attribute. 
	 * @param value the standard
	 */
	public void setStandard(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STANDARD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.standard</code> attribute. 
	 * @param value the standard
	 */
	public void setStandard(final String value)
	{
		setStandard( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.steel</code> attribute.
	 * @return the steel
	 */
	public String getSteel(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STEEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.steel</code> attribute.
	 * @return the steel
	 */
	public String getSteel()
	{
		return getSteel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.steel</code> attribute. 
	 * @param value the steel
	 */
	public void setSteel(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STEEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.steel</code> attribute. 
	 * @param value the steel
	 */
	public void setSteel(final String value)
	{
		setSteel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.strategy</code> attribute.
	 * @return the strategy
	 */
	public Double getStrategy(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, STRATEGY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.strategy</code> attribute.
	 * @return the strategy
	 */
	public Double getStrategy()
	{
		return getStrategy( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.strategy</code> attribute. 
	 * @return the strategy
	 */
	public double getStrategyAsPrimitive(final SessionContext ctx)
	{
		Double value = getStrategy( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.strategy</code> attribute. 
	 * @return the strategy
	 */
	public double getStrategyAsPrimitive()
	{
		return getStrategyAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.strategy</code> attribute. 
	 * @param value the strategy
	 */
	public void setStrategy(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, STRATEGY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.strategy</code> attribute. 
	 * @param value the strategy
	 */
	public void setStrategy(final Double value)
	{
		setStrategy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.strategy</code> attribute. 
	 * @param value the strategy
	 */
	public void setStrategy(final SessionContext ctx, final double value)
	{
		setStrategy( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.strategy</code> attribute. 
	 * @param value the strategy
	 */
	public void setStrategy(final double value)
	{
		setStrategy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.subFamily</code> attribute.
	 * @return the subFamily
	 */
	public String getSubFamily(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SUBFAMILY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.subFamily</code> attribute.
	 * @return the subFamily
	 */
	public String getSubFamily()
	{
		return getSubFamily( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.subFamily</code> attribute. 
	 * @param value the subFamily
	 */
	public void setSubFamily(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SUBFAMILY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.subFamily</code> attribute. 
	 * @param value the subFamily
	 */
	public void setSubFamily(final String value)
	{
		setSubFamily( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.thread</code> attribute.
	 * @return the thread
	 */
	public String getThread(final SessionContext ctx)
	{
		return (String)getProperty( ctx, THREAD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.thread</code> attribute.
	 * @return the thread
	 */
	public String getThread()
	{
		return getThread( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.thread</code> attribute. 
	 * @param value the thread
	 */
	public void setThread(final SessionContext ctx, final String value)
	{
		setProperty(ctx, THREAD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.thread</code> attribute. 
	 * @param value the thread
	 */
	public void setThread(final String value)
	{
		setThread( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.tnEquiv</code> attribute.
	 * @return the tnEquiv
	 */
	public Double getTnEquiv(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, TNEQUIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.tnEquiv</code> attribute.
	 * @return the tnEquiv
	 */
	public Double getTnEquiv()
	{
		return getTnEquiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.tnEquiv</code> attribute. 
	 * @return the tnEquiv
	 */
	public double getTnEquivAsPrimitive(final SessionContext ctx)
	{
		Double value = getTnEquiv( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.tnEquiv</code> attribute. 
	 * @return the tnEquiv
	 */
	public double getTnEquivAsPrimitive()
	{
		return getTnEquivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.tnEquiv</code> attribute. 
	 * @param value the tnEquiv
	 */
	public void setTnEquiv(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, TNEQUIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.tnEquiv</code> attribute. 
	 * @param value the tnEquiv
	 */
	public void setTnEquiv(final Double value)
	{
		setTnEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.tnEquiv</code> attribute. 
	 * @param value the tnEquiv
	 */
	public void setTnEquiv(final SessionContext ctx, final double value)
	{
		setTnEquiv( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.tnEquiv</code> attribute. 
	 * @param value the tnEquiv
	 */
	public void setTnEquiv(final double value)
	{
		setTnEquiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.wallThickness</code> attribute.
	 * @return the wallThickness
	 */
	public Double getWallThickness(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, WALLTHICKNESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.wallThickness</code> attribute.
	 * @return the wallThickness
	 */
	public Double getWallThickness()
	{
		return getWallThickness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.wallThickness</code> attribute. 
	 * @return the wallThickness
	 */
	public double getWallThicknessAsPrimitive(final SessionContext ctx)
	{
		Double value = getWallThickness( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaProduct.wallThickness</code> attribute. 
	 * @return the wallThickness
	 */
	public double getWallThicknessAsPrimitive()
	{
		return getWallThicknessAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.wallThickness</code> attribute. 
	 * @param value the wallThickness
	 */
	public void setWallThickness(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, WALLTHICKNESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.wallThickness</code> attribute. 
	 * @param value the wallThickness
	 */
	public void setWallThickness(final Double value)
	{
		setWallThickness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.wallThickness</code> attribute. 
	 * @param value the wallThickness
	 */
	public void setWallThickness(final SessionContext ctx, final double value)
	{
		setWallThickness( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaProduct.wallThickness</code> attribute. 
	 * @param value the wallThickness
	 */
	public void setWallThickness(final double value)
	{
		setWallThickness( getSession().getSessionContext(), value );
	}
	
}
