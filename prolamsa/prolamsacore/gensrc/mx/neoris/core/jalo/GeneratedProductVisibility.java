/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem ProductVisibility}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedProductVisibility extends GenericItem
{
	/** Qualifier of the <code>ProductVisibility.active</code> attribute **/
	public static final String ACTIVE = "active";
	/** Qualifier of the <code>ProductVisibility.family</code> attribute **/
	public static final String FAMILY = "family";
	/** Qualifier of the <code>ProductVisibility.salesAreaWildCardVisibility</code> attribute **/
	public static final String SALESAREAWILDCARDVISIBILITY = "salesAreaWildCardVisibility";
	/** Qualifier of the <code>ProductVisibility.category</code> attribute **/
	public static final String CATEGORY = "category";
	/** Qualifier of the <code>ProductVisibility.salesArea</code> attribute **/
	public static final String SALESAREA = "salesArea";
	/** Qualifier of the <code>ProductVisibility.materialType</code> attribute **/
	public static final String MATERIALTYPE = "materialType";
	/** Qualifier of the <code>ProductVisibility.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>ProductVisibility.finish</code> attribute **/
	public static final String FINISH = "finish";
	/** Qualifier of the <code>ProductVisibility.section</code> attribute **/
	public static final String SECTION = "section";
	/** Qualifier of the <code>ProductVisibility.covering</code> attribute **/
	public static final String COVERING = "covering";
	/** Qualifier of the <code>ProductVisibility.industry</code> attribute **/
	public static final String INDUSTRY = "industry";
	/** Qualifier of the <code>ProductVisibility.steel</code> attribute **/
	public static final String STEEL = "steel";
	/** Qualifier of the <code>ProductVisibility.salesRep</code> attribute **/
	public static final String SALESREP = "salesRep";
	/** Qualifier of the <code>ProductVisibility.catalog</code> attribute **/
	public static final String CATALOG = "catalog";
	/** Qualifier of the <code>ProductVisibility.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>ProductVisibility.quality</code> attribute **/
	public static final String QUALITY = "quality";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ACTIVE, AttributeMode.INITIAL);
		tmp.put(FAMILY, AttributeMode.INITIAL);
		tmp.put(SALESAREAWILDCARDVISIBILITY, AttributeMode.INITIAL);
		tmp.put(CATEGORY, AttributeMode.INITIAL);
		tmp.put(SALESAREA, AttributeMode.INITIAL);
		tmp.put(MATERIALTYPE, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(FINISH, AttributeMode.INITIAL);
		tmp.put(SECTION, AttributeMode.INITIAL);
		tmp.put(COVERING, AttributeMode.INITIAL);
		tmp.put(INDUSTRY, AttributeMode.INITIAL);
		tmp.put(STEEL, AttributeMode.INITIAL);
		tmp.put(SALESREP, AttributeMode.INITIAL);
		tmp.put(CATALOG, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(QUALITY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.active</code> attribute.
	 * @return the active
	 */
	public Boolean isActive(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ACTIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.active</code> attribute.
	 * @return the active
	 */
	public Boolean isActive()
	{
		return isActive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.active</code> attribute. 
	 * @return the active
	 */
	public boolean isActiveAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isActive( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.active</code> attribute. 
	 * @return the active
	 */
	public boolean isActiveAsPrimitive()
	{
		return isActiveAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ACTIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final Boolean value)
	{
		setActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final SessionContext ctx, final boolean value)
	{
		setActive( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final boolean value)
	{
		setActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.catalog</code> attribute.
	 * @return the catalog
	 */
	public List<Catalog> getCatalog(final SessionContext ctx)
	{
		List<Catalog> coll = (List<Catalog>)getProperty( ctx, CATALOG);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.catalog</code> attribute.
	 * @return the catalog
	 */
	public List<Catalog> getCatalog()
	{
		return getCatalog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.catalog</code> attribute. 
	 * @param value the catalog
	 */
	public void setCatalog(final SessionContext ctx, final List<Catalog> value)
	{
		setProperty(ctx, CATALOG,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.catalog</code> attribute. 
	 * @param value the catalog
	 */
	public void setCatalog(final List<Catalog> value)
	{
		setCatalog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.category</code> attribute.
	 * @return the category
	 */
	public List<Category> getCategory(final SessionContext ctx)
	{
		List<Category> coll = (List<Category>)getProperty( ctx, CATEGORY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.category</code> attribute.
	 * @return the category
	 */
	public List<Category> getCategory()
	{
		return getCategory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.category</code> attribute. 
	 * @param value the category
	 */
	public void setCategory(final SessionContext ctx, final List<Category> value)
	{
		setProperty(ctx, CATEGORY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.category</code> attribute. 
	 * @param value the category
	 */
	public void setCategory(final List<Category> value)
	{
		setCategory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.covering</code> attribute.
	 * @return the covering
	 */
	public List<String> getCovering(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, COVERING);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.covering</code> attribute.
	 * @return the covering
	 */
	public List<String> getCovering()
	{
		return getCovering( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.covering</code> attribute. 
	 * @param value the covering
	 */
	public void setCovering(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, COVERING,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.covering</code> attribute. 
	 * @param value the covering
	 */
	public void setCovering(final List<String> value)
	{
		setCovering( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.family</code> attribute.
	 * @return the family
	 */
	public List<String> getFamily(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, FAMILY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.family</code> attribute.
	 * @return the family
	 */
	public List<String> getFamily()
	{
		return getFamily( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.family</code> attribute. 
	 * @param value the family
	 */
	public void setFamily(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, FAMILY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.family</code> attribute. 
	 * @param value the family
	 */
	public void setFamily(final List<String> value)
	{
		setFamily( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.finish</code> attribute.
	 * @return the finish
	 */
	public List<String> getFinish(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, FINISH);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.finish</code> attribute.
	 * @return the finish
	 */
	public List<String> getFinish()
	{
		return getFinish( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.finish</code> attribute. 
	 * @param value the finish
	 */
	public void setFinish(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, FINISH,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.finish</code> attribute. 
	 * @param value the finish
	 */
	public void setFinish(final List<String> value)
	{
		setFinish( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.industry</code> attribute.
	 * @return the industry
	 */
	public List<String> getIndustry(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, INDUSTRY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.industry</code> attribute.
	 * @return the industry
	 */
	public List<String> getIndustry()
	{
		return getIndustry( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.industry</code> attribute. 
	 * @param value the industry
	 */
	public void setIndustry(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, INDUSTRY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.industry</code> attribute. 
	 * @param value the industry
	 */
	public void setIndustry(final List<String> value)
	{
		setIndustry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.location</code> attribute.
	 * @return the location
	 */
	public List<EnumerationValue> getLocation(final SessionContext ctx)
	{
		List<EnumerationValue> coll = (List<EnumerationValue>)getProperty( ctx, LOCATION);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.location</code> attribute.
	 * @return the location
	 */
	public List<EnumerationValue> getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final List<EnumerationValue> value)
	{
		setProperty(ctx, LOCATION,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final List<EnumerationValue> value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.materialType</code> attribute.
	 * @return the materialType
	 */
	public List<String> getMaterialType(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, MATERIALTYPE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.materialType</code> attribute.
	 * @return the materialType
	 */
	public List<String> getMaterialType()
	{
		return getMaterialType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.materialType</code> attribute. 
	 * @param value the materialType
	 */
	public void setMaterialType(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, MATERIALTYPE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.materialType</code> attribute. 
	 * @param value the materialType
	 */
	public void setMaterialType(final List<String> value)
	{
		setMaterialType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.quality</code> attribute.
	 * @return the quality
	 */
	public List<String> getQuality(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, QUALITY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.quality</code> attribute.
	 * @return the quality
	 */
	public List<String> getQuality()
	{
		return getQuality( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.quality</code> attribute. 
	 * @param value the quality
	 */
	public void setQuality(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, QUALITY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.quality</code> attribute. 
	 * @param value the quality
	 */
	public void setQuality(final List<String> value)
	{
		setQuality( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.salesArea</code> attribute.
	 * @return the salesArea
	 */
	public List<String> getSalesArea(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, SALESAREA);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.salesArea</code> attribute.
	 * @return the salesArea
	 */
	public List<String> getSalesArea()
	{
		return getSalesArea( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.salesArea</code> attribute. 
	 * @param value the salesArea
	 */
	public void setSalesArea(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, SALESAREA,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.salesArea</code> attribute. 
	 * @param value the salesArea
	 */
	public void setSalesArea(final List<String> value)
	{
		setSalesArea( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.salesAreaWildCardVisibility</code> attribute.
	 * @return the salesAreaWildCardVisibility
	 */
	public List<String> getSalesAreaWildCardVisibility(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, SALESAREAWILDCARDVISIBILITY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.salesAreaWildCardVisibility</code> attribute.
	 * @return the salesAreaWildCardVisibility
	 */
	public List<String> getSalesAreaWildCardVisibility()
	{
		return getSalesAreaWildCardVisibility( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.salesAreaWildCardVisibility</code> attribute. 
	 * @param value the salesAreaWildCardVisibility
	 */
	public void setSalesAreaWildCardVisibility(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, SALESAREAWILDCARDVISIBILITY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.salesAreaWildCardVisibility</code> attribute. 
	 * @param value the salesAreaWildCardVisibility
	 */
	public void setSalesAreaWildCardVisibility(final List<String> value)
	{
		setSalesAreaWildCardVisibility( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.salesRep</code> attribute.
	 * @return the salesRep
	 */
	public List<String> getSalesRep(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, SALESREP);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.salesRep</code> attribute.
	 * @return the salesRep
	 */
	public List<String> getSalesRep()
	{
		return getSalesRep( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.salesRep</code> attribute. 
	 * @param value the salesRep
	 */
	public void setSalesRep(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, SALESREP,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.salesRep</code> attribute. 
	 * @param value the salesRep
	 */
	public void setSalesRep(final List<String> value)
	{
		setSalesRep( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.section</code> attribute.
	 * @return the section
	 */
	public List<String> getSection(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, SECTION);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.section</code> attribute.
	 * @return the section
	 */
	public List<String> getSection()
	{
		return getSection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.section</code> attribute. 
	 * @param value the section
	 */
	public void setSection(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, SECTION,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.section</code> attribute. 
	 * @param value the section
	 */
	public void setSection(final List<String> value)
	{
		setSection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.steel</code> attribute.
	 * @return the steel
	 */
	public List<String> getSteel(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, STEEL);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductVisibility.steel</code> attribute.
	 * @return the steel
	 */
	public List<String> getSteel()
	{
		return getSteel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.steel</code> attribute. 
	 * @param value the steel
	 */
	public void setSteel(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, STEEL,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductVisibility.steel</code> attribute. 
	 * @param value the steel
	 */
	public void setSteel(final List<String> value)
	{
		setSteel( getSession().getSessionContext(), value );
	}
	
}
