/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.b2b.jalo.B2BUnit;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.store.BaseStore;
import de.hybris.platform.util.Utilities;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.jalo.Alert;
import mx.neoris.core.jalo.AlertConfigurationChange;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem AlertConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAlertConfiguration extends GenericItem
{
	/** Qualifier of the <code>AlertConfiguration.b2bcustomer</code> attribute **/
	public static final String B2BCUSTOMER = "b2bcustomer";
	/** Qualifier of the <code>AlertConfiguration.time</code> attribute **/
	public static final String TIME = "time";
	/** Qualifier of the <code>AlertConfiguration.b2bunit</code> attribute **/
	public static final String B2BUNIT = "b2bunit";
	/** Qualifier of the <code>AlertConfiguration.daysOfWeek</code> attribute **/
	public static final String DAYSOFWEEK = "daysOfWeek";
	/** Qualifier of the <code>AlertConfiguration.dayOfMonth</code> attribute **/
	public static final String DAYOFMONTH = "dayOfMonth";
	/** Qualifier of the <code>AlertConfiguration.productOptions</code> attribute **/
	public static final String PRODUCTOPTIONS = "productOptions";
	/** Qualifier of the <code>AlertConfiguration.includeMTR</code> attribute **/
	public static final String INCLUDEMTR = "includeMTR";
	/** Qualifier of the <code>AlertConfiguration.alert</code> attribute **/
	public static final String ALERT = "alert";
	/** Qualifier of the <code>AlertConfiguration.ccEmail</code> attribute **/
	public static final String CCEMAIL = "ccEmail";
	/** Qualifier of the <code>AlertConfiguration.baseStore</code> attribute **/
	public static final String BASESTORE = "baseStore";
	/** Qualifier of the <code>AlertConfiguration.customerChanges</code> attribute **/
	public static final String CUSTOMERCHANGES = "customerChanges";
	/** Relation ordering override parameter constants for AlertConfiguration2Change from ((prolamsacore))*/
	protected static String ALERTCONFIGURATION2CHANGE_SRC_ORDERED = "relation.AlertConfiguration2Change.source.ordered";
	protected static String ALERTCONFIGURATION2CHANGE_TGT_ORDERED = "relation.AlertConfiguration2Change.target.ordered";
	/** Relation disable markmodifed parameter constants for AlertConfiguration2Change from ((prolamsacore))*/
	protected static String ALERTCONFIGURATION2CHANGE_MARKMODIFIED = "relation.AlertConfiguration2Change.markmodified";
	/** Qualifier of the <code>AlertConfiguration.notify</code> attribute **/
	public static final String NOTIFY = "notify";
	/** Qualifier of the <code>AlertConfiguration.periodicity</code> attribute **/
	public static final String PERIODICITY = "periodicity";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(B2BCUSTOMER, AttributeMode.INITIAL);
		tmp.put(TIME, AttributeMode.INITIAL);
		tmp.put(B2BUNIT, AttributeMode.INITIAL);
		tmp.put(DAYSOFWEEK, AttributeMode.INITIAL);
		tmp.put(DAYOFMONTH, AttributeMode.INITIAL);
		tmp.put(PRODUCTOPTIONS, AttributeMode.INITIAL);
		tmp.put(INCLUDEMTR, AttributeMode.INITIAL);
		tmp.put(ALERT, AttributeMode.INITIAL);
		tmp.put(CCEMAIL, AttributeMode.INITIAL);
		tmp.put(BASESTORE, AttributeMode.INITIAL);
		tmp.put(NOTIFY, AttributeMode.INITIAL);
		tmp.put(PERIODICITY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.alert</code> attribute.
	 * @return the alert
	 */
	public Alert getAlert(final SessionContext ctx)
	{
		return (Alert)getProperty( ctx, ALERT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.alert</code> attribute.
	 * @return the alert
	 */
	public Alert getAlert()
	{
		return getAlert( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.alert</code> attribute. 
	 * @param value the alert
	 */
	public void setAlert(final SessionContext ctx, final Alert value)
	{
		setProperty(ctx, ALERT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.alert</code> attribute. 
	 * @param value the alert
	 */
	public void setAlert(final Alert value)
	{
		setAlert( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.b2bcustomer</code> attribute.
	 * @return the b2bcustomer
	 */
	public B2BCustomer getB2bcustomer(final SessionContext ctx)
	{
		return (B2BCustomer)getProperty( ctx, B2BCUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.b2bcustomer</code> attribute.
	 * @return the b2bcustomer
	 */
	public B2BCustomer getB2bcustomer()
	{
		return getB2bcustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.b2bcustomer</code> attribute. 
	 * @param value the b2bcustomer
	 */
	public void setB2bcustomer(final SessionContext ctx, final B2BCustomer value)
	{
		setProperty(ctx, B2BCUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.b2bcustomer</code> attribute. 
	 * @param value the b2bcustomer
	 */
	public void setB2bcustomer(final B2BCustomer value)
	{
		setB2bcustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.b2bunit</code> attribute.
	 * @return the b2bunit
	 */
	public B2BUnit getB2bunit(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, B2BUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.b2bunit</code> attribute.
	 * @return the b2bunit
	 */
	public B2BUnit getB2bunit()
	{
		return getB2bunit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.b2bunit</code> attribute. 
	 * @param value the b2bunit
	 */
	public void setB2bunit(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, B2BUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.b2bunit</code> attribute. 
	 * @param value the b2bunit
	 */
	public void setB2bunit(final B2BUnit value)
	{
		setB2bunit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.baseStore</code> attribute.
	 * @return the baseStore
	 */
	public BaseStore getBaseStore(final SessionContext ctx)
	{
		return (BaseStore)getProperty( ctx, BASESTORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.baseStore</code> attribute.
	 * @return the baseStore
	 */
	public BaseStore getBaseStore()
	{
		return getBaseStore( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.baseStore</code> attribute. 
	 * @param value the baseStore
	 */
	public void setBaseStore(final SessionContext ctx, final BaseStore value)
	{
		setProperty(ctx, BASESTORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.baseStore</code> attribute. 
	 * @param value the baseStore
	 */
	public void setBaseStore(final BaseStore value)
	{
		setBaseStore( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.ccEmail</code> attribute.
	 * @return the ccEmail
	 */
	public String getCcEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CCEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.ccEmail</code> attribute.
	 * @return the ccEmail
	 */
	public String getCcEmail()
	{
		return getCcEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.ccEmail</code> attribute. 
	 * @param value the ccEmail
	 */
	public void setCcEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CCEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.ccEmail</code> attribute. 
	 * @param value the ccEmail
	 */
	public void setCcEmail(final String value)
	{
		setCcEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.customerChanges</code> attribute.
	 * @return the customerChanges
	 */
	public Set<AlertConfigurationChange> getCustomerChanges(final SessionContext ctx)
	{
		final List<AlertConfigurationChange> items = getLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ALERTCONFIGURATION2CHANGE,
			null,
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_TGT_ORDERED, true)
		);
		return new LinkedHashSet<AlertConfigurationChange>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.customerChanges</code> attribute.
	 * @return the customerChanges
	 */
	public Set<AlertConfigurationChange> getCustomerChanges()
	{
		return getCustomerChanges( getSession().getSessionContext() );
	}
	
	public long getCustomerChangesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ALERTCONFIGURATION2CHANGE,
			null
		);
	}
	
	public long getCustomerChangesCount()
	{
		return getCustomerChangesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.customerChanges</code> attribute. 
	 * @param value the customerChanges
	 */
	public void setCustomerChanges(final SessionContext ctx, final Set<AlertConfigurationChange> value)
	{
		setLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ALERTCONFIGURATION2CHANGE,
			null,
			value,
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(ALERTCONFIGURATION2CHANGE_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.customerChanges</code> attribute. 
	 * @param value the customerChanges
	 */
	public void setCustomerChanges(final Set<AlertConfigurationChange> value)
	{
		setCustomerChanges( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customerChanges. 
	 * @param value the item to add to customerChanges
	 */
	public void addToCustomerChanges(final SessionContext ctx, final AlertConfigurationChange value)
	{
		addLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ALERTCONFIGURATION2CHANGE,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(ALERTCONFIGURATION2CHANGE_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customerChanges. 
	 * @param value the item to add to customerChanges
	 */
	public void addToCustomerChanges(final AlertConfigurationChange value)
	{
		addToCustomerChanges( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customerChanges. 
	 * @param value the item to remove from customerChanges
	 */
	public void removeFromCustomerChanges(final SessionContext ctx, final AlertConfigurationChange value)
	{
		removeLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ALERTCONFIGURATION2CHANGE,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ALERTCONFIGURATION2CHANGE_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(ALERTCONFIGURATION2CHANGE_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customerChanges. 
	 * @param value the item to remove from customerChanges
	 */
	public void removeFromCustomerChanges(final AlertConfigurationChange value)
	{
		removeFromCustomerChanges( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.dayOfMonth</code> attribute.
	 * @return the dayOfMonth
	 */
	public String getDayOfMonth(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DAYOFMONTH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.dayOfMonth</code> attribute.
	 * @return the dayOfMonth
	 */
	public String getDayOfMonth()
	{
		return getDayOfMonth( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.dayOfMonth</code> attribute. 
	 * @param value the dayOfMonth
	 */
	public void setDayOfMonth(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DAYOFMONTH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.dayOfMonth</code> attribute. 
	 * @param value the dayOfMonth
	 */
	public void setDayOfMonth(final String value)
	{
		setDayOfMonth( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.daysOfWeek</code> attribute.
	 * @return the daysOfWeek
	 */
	public String getDaysOfWeek(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DAYSOFWEEK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.daysOfWeek</code> attribute.
	 * @return the daysOfWeek
	 */
	public String getDaysOfWeek()
	{
		return getDaysOfWeek( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.daysOfWeek</code> attribute. 
	 * @param value the daysOfWeek
	 */
	public void setDaysOfWeek(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DAYSOFWEEK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.daysOfWeek</code> attribute. 
	 * @param value the daysOfWeek
	 */
	public void setDaysOfWeek(final String value)
	{
		setDaysOfWeek( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.includeMTR</code> attribute.
	 * @return the includeMTR
	 */
	public Boolean isIncludeMTR(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, INCLUDEMTR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.includeMTR</code> attribute.
	 * @return the includeMTR
	 */
	public Boolean isIncludeMTR()
	{
		return isIncludeMTR( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.includeMTR</code> attribute. 
	 * @return the includeMTR
	 */
	public boolean isIncludeMTRAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIncludeMTR( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.includeMTR</code> attribute. 
	 * @return the includeMTR
	 */
	public boolean isIncludeMTRAsPrimitive()
	{
		return isIncludeMTRAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.includeMTR</code> attribute. 
	 * @param value the includeMTR
	 */
	public void setIncludeMTR(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, INCLUDEMTR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.includeMTR</code> attribute. 
	 * @param value the includeMTR
	 */
	public void setIncludeMTR(final Boolean value)
	{
		setIncludeMTR( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.includeMTR</code> attribute. 
	 * @param value the includeMTR
	 */
	public void setIncludeMTR(final SessionContext ctx, final boolean value)
	{
		setIncludeMTR( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.includeMTR</code> attribute. 
	 * @param value the includeMTR
	 */
	public void setIncludeMTR(final boolean value)
	{
		setIncludeMTR( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.notify</code> attribute.
	 * @return the notify
	 */
	public Boolean isNotify(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, NOTIFY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.notify</code> attribute.
	 * @return the notify
	 */
	public Boolean isNotify()
	{
		return isNotify( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.notify</code> attribute. 
	 * @return the notify
	 */
	public boolean isNotifyAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isNotify( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.notify</code> attribute. 
	 * @return the notify
	 */
	public boolean isNotifyAsPrimitive()
	{
		return isNotifyAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.notify</code> attribute. 
	 * @param value the notify
	 */
	public void setNotify(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, NOTIFY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.notify</code> attribute. 
	 * @param value the notify
	 */
	public void setNotify(final Boolean value)
	{
		setNotify( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.notify</code> attribute. 
	 * @param value the notify
	 */
	public void setNotify(final SessionContext ctx, final boolean value)
	{
		setNotify( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.notify</code> attribute. 
	 * @param value the notify
	 */
	public void setNotify(final boolean value)
	{
		setNotify( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.periodicity</code> attribute.
	 * @return the periodicity
	 */
	public String getPeriodicity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PERIODICITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.periodicity</code> attribute.
	 * @return the periodicity
	 */
	public String getPeriodicity()
	{
		return getPeriodicity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.periodicity</code> attribute. 
	 * @param value the periodicity
	 */
	public void setPeriodicity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PERIODICITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.periodicity</code> attribute. 
	 * @param value the periodicity
	 */
	public void setPeriodicity(final String value)
	{
		setPeriodicity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.productOptions</code> attribute.
	 * @return the productOptions
	 */
	public String getProductOptions(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTOPTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.productOptions</code> attribute.
	 * @return the productOptions
	 */
	public String getProductOptions()
	{
		return getProductOptions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.productOptions</code> attribute. 
	 * @param value the productOptions
	 */
	public void setProductOptions(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTOPTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.productOptions</code> attribute. 
	 * @param value the productOptions
	 */
	public void setProductOptions(final String value)
	{
		setProductOptions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.time</code> attribute.
	 * @return the time
	 */
	public String getTime(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AlertConfiguration.time</code> attribute.
	 * @return the time
	 */
	public String getTime()
	{
		return getTime( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.time</code> attribute. 
	 * @param value the time
	 */
	public void setTime(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AlertConfiguration.time</code> attribute. 
	 * @param value the time
	 */
	public void setTime(final String value)
	{
		setTime( getSession().getSessionContext(), value );
	}
	
}
