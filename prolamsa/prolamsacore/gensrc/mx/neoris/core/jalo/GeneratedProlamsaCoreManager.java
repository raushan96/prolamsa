/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.b2b.jalo.B2BUnit;
import de.hybris.platform.catalog.jalo.Company;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.cms2.jalo.contents.components.CMSLinkComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.link.Link;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.Cart;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.store.BaseStore;
import de.hybris.platform.storelocator.jalo.PointOfService;
import de.hybris.platform.util.Utilities;
import de.hybris.platform.wishlist2.jalo.Wishlist2;
import de.hybris.platform.wishlist2.jalo.Wishlist2Entry;
import de.hybris.platform.workflow.jalo.AbstractWorkflowAction;
import de.hybris.platform.workflow.jalo.WorkflowAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.jalo.AddedIncidentProcess;
import mx.neoris.core.jalo.Alert;
import mx.neoris.core.jalo.AlertConfiguration;
import mx.neoris.core.jalo.AlertConfigurationChange;
import mx.neoris.core.jalo.AlertProductOption;
import mx.neoris.core.jalo.AlertTimeOption;
import mx.neoris.core.jalo.Backorder;
import mx.neoris.core.jalo.BackorderDetail;
import mx.neoris.core.jalo.BalanceStatement;
import mx.neoris.core.jalo.BalanceStatementDetail;
import mx.neoris.core.jalo.BatchSearch;
import mx.neoris.core.jalo.CustomerProductReference;
import mx.neoris.core.jalo.DocumentSearch;
import mx.neoris.core.jalo.HotFolderImportFailedProcess;
import mx.neoris.core.jalo.Incident;
import mx.neoris.core.jalo.IncidentLine;
import mx.neoris.core.jalo.IncidentState;
import mx.neoris.core.jalo.IncidentType;
import mx.neoris.core.jalo.Invoice;
import mx.neoris.core.jalo.LostOrderJob;
import mx.neoris.core.jalo.MultipleCatalogsSyncCronJob;
import mx.neoris.core.jalo.NeorisB2BCustomerDefaultSetting;
import mx.neoris.core.jalo.NeorisB2BCustomerSettingByStore;
import mx.neoris.core.jalo.NeorisB2BUnitSettingByStore;
import mx.neoris.core.jalo.NeorisMedia;
import mx.neoris.core.jalo.Notice;
import mx.neoris.core.jalo.PowertoolsSizeVariantProduct;
import mx.neoris.core.jalo.ProductVisibility;
import mx.neoris.core.jalo.ProlamsaAPIProductConfiguration;
import mx.neoris.core.jalo.ProlamsaProduct;
import mx.neoris.core.jalo.QuoteRequestedProcess;
import mx.neoris.core.jalo.SalesArea;
import mx.neoris.core.jalo.TermsAndCoditionsByStore;
import mx.neoris.core.jalo.TransportationMode;
import mx.neoris.core.jalo.btg.BTGOrganizationTotalSpentInCurrencyLastYearOperand;
import mx.neoris.core.jalo.btg.BTGOrganizationTotalSpentInCurrencyRelativeDatesOperand;
import mx.neoris.core.jalo.btg.OrganizationOrderStatistics;
import mx.neoris.core.jalo.btg.OrganizationOrdersReportingCronJob;

/**
 * Generated class for type <code>ProlamsaCoreManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedProlamsaCoreManager extends Extension
{
	/** Relation ordering override parameter constants for BaseStore2PointOfServs from ((prolamsacore))*/
	protected static String BASESTORE2POINTOFSERVS_SRC_ORDERED = "relation.BaseStore2PointOfServs.source.ordered";
	protected static String BASESTORE2POINTOFSERVS_TGT_ORDERED = "relation.BaseStore2PointOfServs.target.ordered";
	/** Relation disable markmodifed parameter constants for BaseStore2PointOfServs from ((prolamsacore))*/
	protected static String BASESTORE2POINTOFSERVS_MARKMODIFIED = "relation.BaseStore2PointOfServs.markmodified";
	/** Relation ordering override parameter constants for Address2BaseStores from ((prolamsacore))*/
	protected static String ADDRESS2BASESTORES_SRC_ORDERED = "relation.Address2BaseStores.source.ordered";
	protected static String ADDRESS2BASESTORES_TGT_ORDERED = "relation.Address2BaseStores.target.ordered";
	/** Relation disable markmodifed parameter constants for Address2BaseStores from ((prolamsacore))*/
	protected static String ADDRESS2BASESTORES_MARKMODIFIED = "relation.Address2BaseStores.markmodified";
	/** Relation ordering override parameter constants for B2BCustomers2BaseStores from ((prolamsacore))*/
	protected static String B2BCUSTOMERS2BASESTORES_SRC_ORDERED = "relation.B2BCustomers2BaseStores.source.ordered";
	protected static String B2BCUSTOMERS2BASESTORES_TGT_ORDERED = "relation.B2BCustomers2BaseStores.target.ordered";
	/** Relation disable markmodifed parameter constants for B2BCustomers2BaseStores from ((prolamsacore))*/
	protected static String B2BCUSTOMERS2BASESTORES_MARKMODIFIED = "relation.B2BCustomers2BaseStores.markmodified";
	/** Relation ordering override parameter constants for B2BUnits2ProlamsaProducts from ((prolamsacore))*/
	protected static String B2BUNITS2PROLAMSAPRODUCTS_SRC_ORDERED = "relation.B2BUnits2ProlamsaProducts.source.ordered";
	protected static String B2BUNITS2PROLAMSAPRODUCTS_TGT_ORDERED = "relation.B2BUnits2ProlamsaProducts.target.ordered";
	/** Relation disable markmodifed parameter constants for B2BUnits2ProlamsaProducts from ((prolamsacore))*/
	protected static String B2BUNITS2PROLAMSAPRODUCTS_MARKMODIFIED = "relation.B2BUnits2ProlamsaProducts.markmodified";
	/** Relation ordering override parameter constants for B2BUnits2BaseStores from ((prolamsacore))*/
	protected static String B2BUNITS2BASESTORES_SRC_ORDERED = "relation.B2BUnits2BaseStores.source.ordered";
	protected static String B2BUNITS2BASESTORES_TGT_ORDERED = "relation.B2BUnits2BaseStores.target.ordered";
	/** Relation disable markmodifed parameter constants for B2BUnits2BaseStores from ((prolamsacore))*/
	protected static String B2BUNITS2BASESTORES_MARKMODIFIED = "relation.B2BUnits2BaseStores.markmodified";
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute.
	 * @return the activateDefaultSettingLocation
	 */
	public Boolean isActivateDefaultSettingLocation(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVATEDEFAULTSETTINGLOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute.
	 * @return the activateDefaultSettingLocation
	 */
	public Boolean isActivateDefaultSettingLocation(final BaseStore item)
	{
		return isActivateDefaultSettingLocation( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute. 
	 * @return the activateDefaultSettingLocation
	 */
	public boolean isActivateDefaultSettingLocationAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isActivateDefaultSettingLocation( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute. 
	 * @return the activateDefaultSettingLocation
	 */
	public boolean isActivateDefaultSettingLocationAsPrimitive(final BaseStore item)
	{
		return isActivateDefaultSettingLocationAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute. 
	 * @param value the activateDefaultSettingLocation
	 */
	public void setActivateDefaultSettingLocation(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVATEDEFAULTSETTINGLOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute. 
	 * @param value the activateDefaultSettingLocation
	 */
	public void setActivateDefaultSettingLocation(final BaseStore item, final Boolean value)
	{
		setActivateDefaultSettingLocation( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute. 
	 * @param value the activateDefaultSettingLocation
	 */
	public void setActivateDefaultSettingLocation(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setActivateDefaultSettingLocation( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateDefaultSettingLocation</code> attribute. 
	 * @param value the activateDefaultSettingLocation
	 */
	public void setActivateDefaultSettingLocation(final BaseStore item, final boolean value)
	{
		setActivateDefaultSettingLocation( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute.
	 * @return the activateFunctionalityLocationDefault
	 */
	public Boolean isActivateFunctionalityLocationDefault(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVATEFUNCTIONALITYLOCATIONDEFAULT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute.
	 * @return the activateFunctionalityLocationDefault
	 */
	public Boolean isActivateFunctionalityLocationDefault(final BaseStore item)
	{
		return isActivateFunctionalityLocationDefault( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute. 
	 * @return the activateFunctionalityLocationDefault
	 */
	public boolean isActivateFunctionalityLocationDefaultAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isActivateFunctionalityLocationDefault( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute. 
	 * @return the activateFunctionalityLocationDefault
	 */
	public boolean isActivateFunctionalityLocationDefaultAsPrimitive(final BaseStore item)
	{
		return isActivateFunctionalityLocationDefaultAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute. 
	 * @param value the activateFunctionalityLocationDefault
	 */
	public void setActivateFunctionalityLocationDefault(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVATEFUNCTIONALITYLOCATIONDEFAULT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute. 
	 * @param value the activateFunctionalityLocationDefault
	 */
	public void setActivateFunctionalityLocationDefault(final BaseStore item, final Boolean value)
	{
		setActivateFunctionalityLocationDefault( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute. 
	 * @param value the activateFunctionalityLocationDefault
	 */
	public void setActivateFunctionalityLocationDefault(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setActivateFunctionalityLocationDefault( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activateFunctionalityLocationDefault</code> attribute. 
	 * @param value the activateFunctionalityLocationDefault
	 */
	public void setActivateFunctionalityLocationDefault(final BaseStore item, final boolean value)
	{
		setActivateFunctionalityLocationDefault( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute.
	 * @return the activeSendNotificationToContactClient
	 */
	public Boolean isActiveSendNotificationToContactClient(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVESENDNOTIFICATIONTOCONTACTCLIENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute.
	 * @return the activeSendNotificationToContactClient
	 */
	public Boolean isActiveSendNotificationToContactClient(final BaseStore item)
	{
		return isActiveSendNotificationToContactClient( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute. 
	 * @return the activeSendNotificationToContactClient
	 */
	public boolean isActiveSendNotificationToContactClientAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isActiveSendNotificationToContactClient( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute. 
	 * @return the activeSendNotificationToContactClient
	 */
	public boolean isActiveSendNotificationToContactClientAsPrimitive(final BaseStore item)
	{
		return isActiveSendNotificationToContactClientAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute. 
	 * @param value the activeSendNotificationToContactClient
	 */
	public void setActiveSendNotificationToContactClient(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVESENDNOTIFICATIONTOCONTACTCLIENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute. 
	 * @param value the activeSendNotificationToContactClient
	 */
	public void setActiveSendNotificationToContactClient(final BaseStore item, final Boolean value)
	{
		setActiveSendNotificationToContactClient( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute. 
	 * @param value the activeSendNotificationToContactClient
	 */
	public void setActiveSendNotificationToContactClient(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setActiveSendNotificationToContactClient( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeSendNotificationToContactClient</code> attribute. 
	 * @param value the activeSendNotificationToContactClient
	 */
	public void setActiveSendNotificationToContactClient(final BaseStore item, final boolean value)
	{
		setActiveSendNotificationToContactClient( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute.
	 * @return the activeValidateFileNameAttachIncident
	 */
	public Boolean isActiveValidateFileNameAttachIncident(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVEVALIDATEFILENAMEATTACHINCIDENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute.
	 * @return the activeValidateFileNameAttachIncident
	 */
	public Boolean isActiveValidateFileNameAttachIncident(final BaseStore item)
	{
		return isActiveValidateFileNameAttachIncident( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute. 
	 * @return the activeValidateFileNameAttachIncident
	 */
	public boolean isActiveValidateFileNameAttachIncidentAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isActiveValidateFileNameAttachIncident( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute. 
	 * @return the activeValidateFileNameAttachIncident
	 */
	public boolean isActiveValidateFileNameAttachIncidentAsPrimitive(final BaseStore item)
	{
		return isActiveValidateFileNameAttachIncidentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute. 
	 * @param value the activeValidateFileNameAttachIncident
	 */
	public void setActiveValidateFileNameAttachIncident(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ACTIVEVALIDATEFILENAMEATTACHINCIDENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute. 
	 * @param value the activeValidateFileNameAttachIncident
	 */
	public void setActiveValidateFileNameAttachIncident(final BaseStore item, final Boolean value)
	{
		setActiveValidateFileNameAttachIncident( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute. 
	 * @param value the activeValidateFileNameAttachIncident
	 */
	public void setActiveValidateFileNameAttachIncident(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setActiveValidateFileNameAttachIncident( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.activeValidateFileNameAttachIncident</code> attribute. 
	 * @param value the activeValidateFileNameAttachIncident
	 */
	public void setActiveValidateFileNameAttachIncident(final BaseStore item, final boolean value)
	{
		setActiveValidateFileNameAttachIncident( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.alerts</code> attribute.
	 * @return the alerts
	 */
	public List<Alert> getAlerts(final SessionContext ctx, final BaseStore item)
	{
		List<Alert> coll = (List<Alert>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ALERTS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.alerts</code> attribute.
	 * @return the alerts
	 */
	public List<Alert> getAlerts(final BaseStore item)
	{
		return getAlerts( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.alerts</code> attribute. 
	 * @param value the alerts
	 */
	public void setAlerts(final SessionContext ctx, final BaseStore item, final List<Alert> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ALERTS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.alerts</code> attribute. 
	 * @param value the alerts
	 */
	public void setAlerts(final BaseStore item, final List<Alert> value)
	{
		setAlerts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.alertsConfiguration</code> attribute.
	 * @return the alertsConfiguration
	 */
	public List<AlertConfiguration> getAlertsConfiguration(final SessionContext ctx, final B2BCustomer item)
	{
		List<AlertConfiguration> coll = (List<AlertConfiguration>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.ALERTSCONFIGURATION);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.alertsConfiguration</code> attribute.
	 * @return the alertsConfiguration
	 */
	public List<AlertConfiguration> getAlertsConfiguration(final B2BCustomer item)
	{
		return getAlertsConfiguration( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.alertsConfiguration</code> attribute. 
	 * @param value the alertsConfiguration
	 */
	public void setAlertsConfiguration(final SessionContext ctx, final B2BCustomer item, final List<AlertConfiguration> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.ALERTSCONFIGURATION,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.alertsConfiguration</code> attribute. 
	 * @param value the alertsConfiguration
	 */
	public void setAlertsConfiguration(final B2BCustomer item, final List<AlertConfiguration> value)
	{
		setAlertsConfiguration( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.allowCategoryVisibility</code> attribute.
	 * @return the allowCategoryVisibility
	 */
	public Boolean isAllowCategoryVisibility(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ALLOWCATEGORYVISIBILITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.allowCategoryVisibility</code> attribute.
	 * @return the allowCategoryVisibility
	 */
	public Boolean isAllowCategoryVisibility(final BaseStore item)
	{
		return isAllowCategoryVisibility( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.allowCategoryVisibility</code> attribute. 
	 * @return the allowCategoryVisibility
	 */
	public boolean isAllowCategoryVisibilityAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isAllowCategoryVisibility( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.allowCategoryVisibility</code> attribute. 
	 * @return the allowCategoryVisibility
	 */
	public boolean isAllowCategoryVisibilityAsPrimitive(final BaseStore item)
	{
		return isAllowCategoryVisibilityAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.allowCategoryVisibility</code> attribute. 
	 * @param value the allowCategoryVisibility
	 */
	public void setAllowCategoryVisibility(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ALLOWCATEGORYVISIBILITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.allowCategoryVisibility</code> attribute. 
	 * @param value the allowCategoryVisibility
	 */
	public void setAllowCategoryVisibility(final BaseStore item, final Boolean value)
	{
		setAllowCategoryVisibility( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.allowCategoryVisibility</code> attribute. 
	 * @param value the allowCategoryVisibility
	 */
	public void setAllowCategoryVisibility(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setAllowCategoryVisibility( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.allowCategoryVisibility</code> attribute. 
	 * @param value the allowCategoryVisibility
	 */
	public void setAllowCategoryVisibility(final BaseStore item, final boolean value)
	{
		setAllowCategoryVisibility( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedPrice</code> attribute.
	 * @return the aluminizedPrice
	 */
	public Double getAluminizedPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ALUMINIZEDPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedPrice</code> attribute.
	 * @return the aluminizedPrice
	 */
	public Double getAluminizedPrice(final AbstractOrder item)
	{
		return getAluminizedPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedPrice</code> attribute. 
	 * @return the aluminizedPrice
	 */
	public double getAluminizedPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getAluminizedPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedPrice</code> attribute. 
	 * @return the aluminizedPrice
	 */
	public double getAluminizedPriceAsPrimitive(final AbstractOrder item)
	{
		return getAluminizedPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedPrice</code> attribute. 
	 * @param value the aluminizedPrice
	 */
	public void setAluminizedPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ALUMINIZEDPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedPrice</code> attribute. 
	 * @param value the aluminizedPrice
	 */
	public void setAluminizedPrice(final AbstractOrder item, final Double value)
	{
		setAluminizedPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedPrice</code> attribute. 
	 * @param value the aluminizedPrice
	 */
	public void setAluminizedPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setAluminizedPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedPrice</code> attribute. 
	 * @param value the aluminizedPrice
	 */
	public void setAluminizedPrice(final AbstractOrder item, final double value)
	{
		setAluminizedPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedWeight</code> attribute.
	 * @return the aluminizedWeight
	 */
	public Double getAluminizedWeight(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ALUMINIZEDWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedWeight</code> attribute.
	 * @return the aluminizedWeight
	 */
	public Double getAluminizedWeight(final AbstractOrder item)
	{
		return getAluminizedWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedWeight</code> attribute. 
	 * @return the aluminizedWeight
	 */
	public double getAluminizedWeightAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getAluminizedWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.aluminizedWeight</code> attribute. 
	 * @return the aluminizedWeight
	 */
	public double getAluminizedWeightAsPrimitive(final AbstractOrder item)
	{
		return getAluminizedWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedWeight</code> attribute. 
	 * @param value the aluminizedWeight
	 */
	public void setAluminizedWeight(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ALUMINIZEDWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedWeight</code> attribute. 
	 * @param value the aluminizedWeight
	 */
	public void setAluminizedWeight(final AbstractOrder item, final Double value)
	{
		setAluminizedWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedWeight</code> attribute. 
	 * @param value the aluminizedWeight
	 */
	public void setAluminizedWeight(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setAluminizedWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.aluminizedWeight</code> attribute. 
	 * @param value the aluminizedWeight
	 */
	public void setAluminizedWeight(final AbstractOrder item, final double value)
	{
		setAluminizedWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.APICategories</code> attribute.
	 * @return the APICategories
	 */
	public Collection<Category> getAPICategories(final SessionContext ctx, final BaseStore item)
	{
		Collection<Category> coll = (Collection<Category>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.APICATEGORIES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.APICategories</code> attribute.
	 * @return the APICategories
	 */
	public Collection<Category> getAPICategories(final BaseStore item)
	{
		return getAPICategories( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.APICategories</code> attribute. 
	 * @param value the APICategories
	 */
	public void setAPICategories(final SessionContext ctx, final BaseStore item, final Collection<Category> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.APICATEGORIES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.APICategories</code> attribute. 
	 * @param value the APICategories
	 */
	public void setAPICategories(final BaseStore item, final Collection<Category> value)
	{
		setAPICategories( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute.
	 * @return the APIFunctionaliatyEnabled
	 */
	public Boolean isAPIFunctionaliatyEnabled(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.APIFUNCTIONALIATYENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute.
	 * @return the APIFunctionaliatyEnabled
	 */
	public Boolean isAPIFunctionaliatyEnabled(final BaseStore item)
	{
		return isAPIFunctionaliatyEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute. 
	 * @return the APIFunctionaliatyEnabled
	 */
	public boolean isAPIFunctionaliatyEnabledAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isAPIFunctionaliatyEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute. 
	 * @return the APIFunctionaliatyEnabled
	 */
	public boolean isAPIFunctionaliatyEnabledAsPrimitive(final BaseStore item)
	{
		return isAPIFunctionaliatyEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute. 
	 * @param value the APIFunctionaliatyEnabled
	 */
	public void setAPIFunctionaliatyEnabled(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.APIFUNCTIONALIATYENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute. 
	 * @param value the APIFunctionaliatyEnabled
	 */
	public void setAPIFunctionaliatyEnabled(final BaseStore item, final Boolean value)
	{
		setAPIFunctionaliatyEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute. 
	 * @param value the APIFunctionaliatyEnabled
	 */
	public void setAPIFunctionaliatyEnabled(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setAPIFunctionaliatyEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.APIFunctionaliatyEnabled</code> attribute. 
	 * @param value the APIFunctionaliatyEnabled
	 */
	public void setAPIFunctionaliatyEnabled(final BaseStore item, final boolean value)
	{
		setAPIFunctionaliatyEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.apiProductConfiguration</code> attribute.
	 * @return the apiProductConfiguration
	 */
	public ProlamsaAPIProductConfiguration getApiProductConfiguration(final SessionContext ctx, final Wishlist2Entry item)
	{
		return (ProlamsaAPIProductConfiguration)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Wishlist2Entry.APIPRODUCTCONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.apiProductConfiguration</code> attribute.
	 * @return the apiProductConfiguration
	 */
	public ProlamsaAPIProductConfiguration getApiProductConfiguration(final Wishlist2Entry item)
	{
		return getApiProductConfiguration( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.apiProductConfiguration</code> attribute. 
	 * @param value the apiProductConfiguration
	 */
	public void setApiProductConfiguration(final SessionContext ctx, final Wishlist2Entry item, final ProlamsaAPIProductConfiguration value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Wishlist2Entry.APIPRODUCTCONFIGURATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.apiProductConfiguration</code> attribute. 
	 * @param value the apiProductConfiguration
	 */
	public void setApiProductConfiguration(final Wishlist2Entry item, final ProlamsaAPIProductConfiguration value)
	{
		setApiProductConfiguration( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.apiProductConfiguration</code> attribute.
	 * @return the apiProductConfiguration
	 */
	public ProlamsaAPIProductConfiguration getApiProductConfiguration(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (ProlamsaAPIProductConfiguration)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.APIPRODUCTCONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.apiProductConfiguration</code> attribute.
	 * @return the apiProductConfiguration
	 */
	public ProlamsaAPIProductConfiguration getApiProductConfiguration(final AbstractOrderEntry item)
	{
		return getApiProductConfiguration( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.apiProductConfiguration</code> attribute. 
	 * @param value the apiProductConfiguration
	 */
	public void setApiProductConfiguration(final SessionContext ctx, final AbstractOrderEntry item, final ProlamsaAPIProductConfiguration value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.APIPRODUCTCONFIGURATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.apiProductConfiguration</code> attribute. 
	 * @param value the apiProductConfiguration
	 */
	public void setApiProductConfiguration(final AbstractOrderEntry item, final ProlamsaAPIProductConfiguration value)
	{
		setApiProductConfiguration( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.attachedPO</code> attribute.
	 * @return the attachedPO
	 */
	public NeorisMedia getAttachedPO(final SessionContext ctx, final AbstractOrder item)
	{
		return (NeorisMedia)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ATTACHEDPO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.attachedPO</code> attribute.
	 * @return the attachedPO
	 */
	public NeorisMedia getAttachedPO(final AbstractOrder item)
	{
		return getAttachedPO( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.attachedPO</code> attribute. 
	 * @param value the attachedPO
	 */
	public void setAttachedPO(final SessionContext ctx, final AbstractOrder item, final NeorisMedia value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ATTACHEDPO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.attachedPO</code> attribute. 
	 * @param value the attachedPO
	 */
	public void setAttachedPO(final AbstractOrder item, final NeorisMedia value)
	{
		setAttachedPO( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.attachmentsPO</code> attribute.
	 * @return the attachmentsPO
	 */
	public List<NeorisMedia> getAttachmentsPO(final SessionContext ctx, final AbstractOrder item)
	{
		List<NeorisMedia> coll = (List<NeorisMedia>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ATTACHMENTSPO);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.attachmentsPO</code> attribute.
	 * @return the attachmentsPO
	 */
	public List<NeorisMedia> getAttachmentsPO(final AbstractOrder item)
	{
		return getAttachmentsPO( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.attachmentsPO</code> attribute. 
	 * @param value the attachmentsPO
	 */
	public void setAttachmentsPO(final SessionContext ctx, final AbstractOrder item, final List<NeorisMedia> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ATTACHMENTSPO,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.attachmentsPO</code> attribute. 
	 * @param value the attachmentsPO
	 */
	public void setAttachmentsPO(final AbstractOrder item, final List<NeorisMedia> value)
	{
		setAttachmentsPO( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute.
	 * @return the automaticallyCheckTermsAndConditions
	 */
	public Boolean isAutomaticallyCheckTermsAndConditions(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.AUTOMATICALLYCHECKTERMSANDCONDITIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute.
	 * @return the automaticallyCheckTermsAndConditions
	 */
	public Boolean isAutomaticallyCheckTermsAndConditions(final BaseStore item)
	{
		return isAutomaticallyCheckTermsAndConditions( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute. 
	 * @return the automaticallyCheckTermsAndConditions
	 */
	public boolean isAutomaticallyCheckTermsAndConditionsAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isAutomaticallyCheckTermsAndConditions( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute. 
	 * @return the automaticallyCheckTermsAndConditions
	 */
	public boolean isAutomaticallyCheckTermsAndConditionsAsPrimitive(final BaseStore item)
	{
		return isAutomaticallyCheckTermsAndConditionsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute. 
	 * @param value the automaticallyCheckTermsAndConditions
	 */
	public void setAutomaticallyCheckTermsAndConditions(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.AUTOMATICALLYCHECKTERMSANDCONDITIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute. 
	 * @param value the automaticallyCheckTermsAndConditions
	 */
	public void setAutomaticallyCheckTermsAndConditions(final BaseStore item, final Boolean value)
	{
		setAutomaticallyCheckTermsAndConditions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute. 
	 * @param value the automaticallyCheckTermsAndConditions
	 */
	public void setAutomaticallyCheckTermsAndConditions(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setAutomaticallyCheckTermsAndConditions( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.automaticallyCheckTermsAndConditions</code> attribute. 
	 * @param value the automaticallyCheckTermsAndConditions
	 */
	public void setAutomaticallyCheckTermsAndConditions(final BaseStore item, final boolean value)
	{
		setAutomaticallyCheckTermsAndConditions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.b2bUnit</code> attribute.
	 * @return the b2bUnit
	 */
	public B2BUnit getB2bUnit(final SessionContext ctx, final Wishlist2 item)
	{
		return (B2BUnit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Wishlist2.B2BUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.b2bUnit</code> attribute.
	 * @return the b2bUnit
	 */
	public B2BUnit getB2bUnit(final Wishlist2 item)
	{
		return getB2bUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.b2bUnit</code> attribute. 
	 * @param value the b2bUnit
	 */
	public void setB2bUnit(final SessionContext ctx, final Wishlist2 item, final B2BUnit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Wishlist2.B2BUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.b2bUnit</code> attribute. 
	 * @param value the b2bUnit
	 */
	public void setB2bUnit(final Wishlist2 item, final B2BUnit value)
	{
		setB2bUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WorkflowAction.b2bUnit</code> attribute.
	 * @return the b2bUnit
	 */
	public B2BUnit getB2bUnit(final SessionContext ctx, final WorkflowAction item)
	{
		return (B2BUnit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.WorkflowAction.B2BUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WorkflowAction.b2bUnit</code> attribute.
	 * @return the b2bUnit
	 */
	public B2BUnit getB2bUnit(final WorkflowAction item)
	{
		return getB2bUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WorkflowAction.b2bUnit</code> attribute. 
	 * @param value the b2bUnit
	 */
	public void setB2bUnit(final SessionContext ctx, final WorkflowAction item, final B2BUnit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.WorkflowAction.B2BUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WorkflowAction.b2bUnit</code> attribute. 
	 * @param value the b2bUnit
	 */
	public void setB2bUnit(final WorkflowAction item, final B2BUnit value)
	{
		setB2bUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.backofficeAccount</code> attribute.
	 * @return the backofficeAccount
	 */
	public String getBackofficeAccount(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.BACKOFFICEACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.backofficeAccount</code> attribute.
	 * @return the backofficeAccount
	 */
	public String getBackofficeAccount(final B2BCustomer item)
	{
		return getBackofficeAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.backofficeAccount</code> attribute. 
	 * @param value the backofficeAccount
	 */
	public void setBackofficeAccount(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.BACKOFFICEACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.backofficeAccount</code> attribute. 
	 * @param value the backofficeAccount
	 */
	public void setBackofficeAccount(final B2BCustomer item, final String value)
	{
		setBackofficeAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.backOfficeAccount</code> attribute.
	 * @return the backOfficeAccount
	 */
	public String getBackOfficeAccount(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.BACKOFFICEACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.backOfficeAccount</code> attribute.
	 * @return the backOfficeAccount
	 */
	public String getBackOfficeAccount(final B2BUnit item)
	{
		return getBackOfficeAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.backOfficeAccount</code> attribute. 
	 * @param value the backOfficeAccount
	 */
	public void setBackOfficeAccount(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.BACKOFFICEACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.backOfficeAccount</code> attribute. 
	 * @param value the backOfficeAccount
	 */
	public void setBackOfficeAccount(final B2BUnit item, final String value)
	{
		setBackOfficeAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.backofficeAccountForStore</code> attribute.
	 * @return the backofficeAccountForStore
	 */
	public List<NeorisB2BCustomerSettingByStore> getBackofficeAccountForStore(final SessionContext ctx, final B2BCustomer item)
	{
		List<NeorisB2BCustomerSettingByStore> coll = (List<NeorisB2BCustomerSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.BACKOFFICEACCOUNTFORSTORE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.backofficeAccountForStore</code> attribute.
	 * @return the backofficeAccountForStore
	 */
	public List<NeorisB2BCustomerSettingByStore> getBackofficeAccountForStore(final B2BCustomer item)
	{
		return getBackofficeAccountForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.backofficeAccountForStore</code> attribute. 
	 * @param value the backofficeAccountForStore
	 */
	public void setBackofficeAccountForStore(final SessionContext ctx, final B2BCustomer item, final List<NeorisB2BCustomerSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.BACKOFFICEACCOUNTFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.backofficeAccountForStore</code> attribute. 
	 * @param value the backofficeAccountForStore
	 */
	public void setBackofficeAccountForStore(final B2BCustomer item, final List<NeorisB2BCustomerSettingByStore> value)
	{
		setBackofficeAccountForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.backOfficeAccountForStore</code> attribute.
	 * @return the backOfficeAccountForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getBackOfficeAccountForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.BACKOFFICEACCOUNTFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.backOfficeAccountForStore</code> attribute.
	 * @return the backOfficeAccountForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getBackOfficeAccountForStore(final B2BUnit item)
	{
		return getBackOfficeAccountForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.backOfficeAccountForStore</code> attribute. 
	 * @param value the backOfficeAccountForStore
	 */
	public void setBackOfficeAccountForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.BACKOFFICEACCOUNTFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.backOfficeAccountForStore</code> attribute. 
	 * @param value the backOfficeAccountForStore
	 */
	public void setBackOfficeAccountForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setBackOfficeAccountForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.baseStores</code> attribute.
	 * @return the baseStores
	 */
	public Set<BaseStore> getBaseStores(final SessionContext ctx, final Address item)
	{
		final List<BaseStore> items = item.getLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ADDRESS2BASESTORES,
			null,
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_TGT_ORDERED, true)
		);
		return new LinkedHashSet<BaseStore>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.baseStores</code> attribute.
	 * @return the baseStores
	 */
	public Set<BaseStore> getBaseStores(final Address item)
	{
		return getBaseStores( getSession().getSessionContext(), item );
	}
	
	public long getBaseStoresCount(final SessionContext ctx, final Address item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ADDRESS2BASESTORES,
			null
		);
	}
	
	public long getBaseStoresCount(final Address item)
	{
		return getBaseStoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.baseStores</code> attribute. 
	 * @param value the baseStores
	 */
	public void setBaseStores(final SessionContext ctx, final Address item, final Set<BaseStore> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ADDRESS2BASESTORES,
			null,
			value,
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(ADDRESS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.baseStores</code> attribute. 
	 * @param value the baseStores
	 */
	public void setBaseStores(final Address item, final Set<BaseStore> value)
	{
		setBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to baseStores. 
	 * @param value the item to add to baseStores
	 */
	public void addToBaseStores(final SessionContext ctx, final Address item, final BaseStore value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ADDRESS2BASESTORES,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(ADDRESS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to baseStores. 
	 * @param value the item to add to baseStores
	 */
	public void addToBaseStores(final Address item, final BaseStore value)
	{
		addToBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from baseStores. 
	 * @param value the item to remove from baseStores
	 */
	public void removeFromBaseStores(final SessionContext ctx, final Address item, final BaseStore value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.ADDRESS2BASESTORES,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(ADDRESS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(ADDRESS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from baseStores. 
	 * @param value the item to remove from baseStores
	 */
	public void removeFromBaseStores(final Address item, final BaseStore value)
	{
		removeFromBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.basestores</code> attribute.
	 * @return the basestores
	 */
	public List<BaseStore> getBasestores(final SessionContext ctx, final PointOfService item)
	{
		final List<BaseStore> items = item.getLinkedItems( 
			ctx,
			false,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true)
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.basestores</code> attribute.
	 * @return the basestores
	 */
	public List<BaseStore> getBasestores(final PointOfService item)
	{
		return getBasestores( getSession().getSessionContext(), item );
	}
	
	public long getBasestoresCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null
		);
	}
	
	public long getBasestoresCount(final PointOfService item)
	{
		return getBasestoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.basestores</code> attribute. 
	 * @param value the basestores
	 */
	public void setBasestores(final SessionContext ctx, final PointOfService item, final List<BaseStore> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			value,
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(BASESTORE2POINTOFSERVS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.basestores</code> attribute. 
	 * @param value the basestores
	 */
	public void setBasestores(final PointOfService item, final List<BaseStore> value)
	{
		setBasestores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to basestores. 
	 * @param value the item to add to basestores
	 */
	public void addToBasestores(final SessionContext ctx, final PointOfService item, final BaseStore value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(BASESTORE2POINTOFSERVS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to basestores. 
	 * @param value the item to add to basestores
	 */
	public void addToBasestores(final PointOfService item, final BaseStore value)
	{
		addToBasestores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from basestores. 
	 * @param value the item to remove from basestores
	 */
	public void removeFromBasestores(final SessionContext ctx, final PointOfService item, final BaseStore value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(BASESTORE2POINTOFSERVS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from basestores. 
	 * @param value the item to remove from basestores
	 */
	public void removeFromBasestores(final PointOfService item, final BaseStore value)
	{
		removeFromBasestores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.baseStores</code> attribute.
	 * @return the baseStores
	 */
	public Set<BaseStore> getBaseStores(final SessionContext ctx, final B2BCustomer item)
	{
		final List<BaseStore> items = item.getLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BCUSTOMERS2BASESTORES,
			null,
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_TGT_ORDERED, true)
		);
		return new LinkedHashSet<BaseStore>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.baseStores</code> attribute.
	 * @return the baseStores
	 */
	public Set<BaseStore> getBaseStores(final B2BCustomer item)
	{
		return getBaseStores( getSession().getSessionContext(), item );
	}
	
	public long getBaseStoresCount(final SessionContext ctx, final B2BCustomer item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BCUSTOMERS2BASESTORES,
			null
		);
	}
	
	public long getBaseStoresCount(final B2BCustomer item)
	{
		return getBaseStoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.baseStores</code> attribute. 
	 * @param value the baseStores
	 */
	public void setBaseStores(final SessionContext ctx, final B2BCustomer item, final Set<BaseStore> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BCUSTOMERS2BASESTORES,
			null,
			value,
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BCUSTOMERS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.baseStores</code> attribute. 
	 * @param value the baseStores
	 */
	public void setBaseStores(final B2BCustomer item, final Set<BaseStore> value)
	{
		setBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to baseStores. 
	 * @param value the item to add to baseStores
	 */
	public void addToBaseStores(final SessionContext ctx, final B2BCustomer item, final BaseStore value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BCUSTOMERS2BASESTORES,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BCUSTOMERS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to baseStores. 
	 * @param value the item to add to baseStores
	 */
	public void addToBaseStores(final B2BCustomer item, final BaseStore value)
	{
		addToBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from baseStores. 
	 * @param value the item to remove from baseStores
	 */
	public void removeFromBaseStores(final SessionContext ctx, final B2BCustomer item, final BaseStore value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BCUSTOMERS2BASESTORES,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BCUSTOMERS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BCUSTOMERS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from baseStores. 
	 * @param value the item to remove from baseStores
	 */
	public void removeFromBaseStores(final B2BCustomer item, final BaseStore value)
	{
		removeFromBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.baseStores</code> attribute.
	 * @return the baseStores
	 */
	public Set<BaseStore> getBaseStores(final SessionContext ctx, final B2BUnit item)
	{
		final List<BaseStore> items = item.getLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2BASESTORES,
			null,
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_TGT_ORDERED, true)
		);
		return new LinkedHashSet<BaseStore>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.baseStores</code> attribute.
	 * @return the baseStores
	 */
	public Set<BaseStore> getBaseStores(final B2BUnit item)
	{
		return getBaseStores( getSession().getSessionContext(), item );
	}
	
	public long getBaseStoresCount(final SessionContext ctx, final B2BUnit item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2BASESTORES,
			null
		);
	}
	
	public long getBaseStoresCount(final B2BUnit item)
	{
		return getBaseStoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.baseStores</code> attribute. 
	 * @param value the baseStores
	 */
	public void setBaseStores(final SessionContext ctx, final B2BUnit item, final Set<BaseStore> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2BASESTORES,
			null,
			value,
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BUNITS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.baseStores</code> attribute. 
	 * @param value the baseStores
	 */
	public void setBaseStores(final B2BUnit item, final Set<BaseStore> value)
	{
		setBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to baseStores. 
	 * @param value the item to add to baseStores
	 */
	public void addToBaseStores(final SessionContext ctx, final B2BUnit item, final BaseStore value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2BASESTORES,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BUNITS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to baseStores. 
	 * @param value the item to add to baseStores
	 */
	public void addToBaseStores(final B2BUnit item, final BaseStore value)
	{
		addToBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from baseStores. 
	 * @param value the item to remove from baseStores
	 */
	public void removeFromBaseStores(final SessionContext ctx, final B2BUnit item, final BaseStore value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2BASESTORES,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2BASESTORES_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BUNITS2BASESTORES_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from baseStores. 
	 * @param value the item to remove from baseStores
	 */
	public void removeFromBaseStores(final B2BUnit item, final BaseStore value)
	{
		removeFromBaseStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.billingAddress</code> attribute.
	 * @return the billingAddress
	 */
	public Address getBillingAddress(final SessionContext ctx, final AbstractOrder item)
	{
		return (Address)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.BILLINGADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.billingAddress</code> attribute.
	 * @return the billingAddress
	 */
	public Address getBillingAddress(final AbstractOrder item)
	{
		return getBillingAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.billingAddress</code> attribute. 
	 * @param value the billingAddress
	 */
	public void setBillingAddress(final SessionContext ctx, final AbstractOrder item, final Address value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.BILLINGADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.billingAddress</code> attribute. 
	 * @param value the billingAddress
	 */
	public void setBillingAddress(final AbstractOrder item, final Address value)
	{
		setBillingAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute.
	 * @return the cartProcessingOrderOrQuote
	 */
	public Boolean isCartProcessingOrderOrQuote(final SessionContext ctx, final Cart item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Cart.CARTPROCESSINGORDERORQUOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute.
	 * @return the cartProcessingOrderOrQuote
	 */
	public Boolean isCartProcessingOrderOrQuote(final Cart item)
	{
		return isCartProcessingOrderOrQuote( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute. 
	 * @return the cartProcessingOrderOrQuote
	 */
	public boolean isCartProcessingOrderOrQuoteAsPrimitive(final SessionContext ctx, final Cart item)
	{
		Boolean value = isCartProcessingOrderOrQuote( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute. 
	 * @return the cartProcessingOrderOrQuote
	 */
	public boolean isCartProcessingOrderOrQuoteAsPrimitive(final Cart item)
	{
		return isCartProcessingOrderOrQuoteAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute. 
	 * @param value the cartProcessingOrderOrQuote
	 */
	public void setCartProcessingOrderOrQuote(final SessionContext ctx, final Cart item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Cart.CARTPROCESSINGORDERORQUOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute. 
	 * @param value the cartProcessingOrderOrQuote
	 */
	public void setCartProcessingOrderOrQuote(final Cart item, final Boolean value)
	{
		setCartProcessingOrderOrQuote( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute. 
	 * @param value the cartProcessingOrderOrQuote
	 */
	public void setCartProcessingOrderOrQuote(final SessionContext ctx, final Cart item, final boolean value)
	{
		setCartProcessingOrderOrQuote( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.cartProcessingOrderOrQuote</code> attribute. 
	 * @param value the cartProcessingOrderOrQuote
	 */
	public void setCartProcessingOrderOrQuote(final Cart item, final boolean value)
	{
		setCartProcessingOrderOrQuote( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Address.CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final Address item)
	{
		return getCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Address.CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final Address item, final String value)
	{
		setCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledPrice</code> attribute.
	 * @return the coldRolledPrice
	 */
	public Double getColdRolledPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.COLDROLLEDPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledPrice</code> attribute.
	 * @return the coldRolledPrice
	 */
	public Double getColdRolledPrice(final AbstractOrder item)
	{
		return getColdRolledPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledPrice</code> attribute. 
	 * @return the coldRolledPrice
	 */
	public double getColdRolledPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getColdRolledPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledPrice</code> attribute. 
	 * @return the coldRolledPrice
	 */
	public double getColdRolledPriceAsPrimitive(final AbstractOrder item)
	{
		return getColdRolledPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledPrice</code> attribute. 
	 * @param value the coldRolledPrice
	 */
	public void setColdRolledPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.COLDROLLEDPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledPrice</code> attribute. 
	 * @param value the coldRolledPrice
	 */
	public void setColdRolledPrice(final AbstractOrder item, final Double value)
	{
		setColdRolledPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledPrice</code> attribute. 
	 * @param value the coldRolledPrice
	 */
	public void setColdRolledPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setColdRolledPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledPrice</code> attribute. 
	 * @param value the coldRolledPrice
	 */
	public void setColdRolledPrice(final AbstractOrder item, final double value)
	{
		setColdRolledPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledWeight</code> attribute.
	 * @return the coldRolledWeight
	 */
	public Double getColdRolledWeight(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.COLDROLLEDWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledWeight</code> attribute.
	 * @return the coldRolledWeight
	 */
	public Double getColdRolledWeight(final AbstractOrder item)
	{
		return getColdRolledWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledWeight</code> attribute. 
	 * @return the coldRolledWeight
	 */
	public double getColdRolledWeightAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getColdRolledWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.coldRolledWeight</code> attribute. 
	 * @return the coldRolledWeight
	 */
	public double getColdRolledWeightAsPrimitive(final AbstractOrder item)
	{
		return getColdRolledWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledWeight</code> attribute. 
	 * @param value the coldRolledWeight
	 */
	public void setColdRolledWeight(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.COLDROLLEDWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledWeight</code> attribute. 
	 * @param value the coldRolledWeight
	 */
	public void setColdRolledWeight(final AbstractOrder item, final Double value)
	{
		setColdRolledWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledWeight</code> attribute. 
	 * @param value the coldRolledWeight
	 */
	public void setColdRolledWeight(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setColdRolledWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.coldRolledWeight</code> attribute. 
	 * @param value the coldRolledWeight
	 */
	public void setColdRolledWeight(final AbstractOrder item, final double value)
	{
		setColdRolledWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute.
	 * @return the convertedQuantity
	 */
	public Double getConvertedQuantity(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.CONVERTEDQUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute.
	 * @return the convertedQuantity
	 */
	public Double getConvertedQuantity(final AbstractOrderEntry item)
	{
		return getConvertedQuantity( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute. 
	 * @return the convertedQuantity
	 */
	public double getConvertedQuantityAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getConvertedQuantity( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute. 
	 * @return the convertedQuantity
	 */
	public double getConvertedQuantityAsPrimitive(final AbstractOrderEntry item)
	{
		return getConvertedQuantityAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute. 
	 * @param value the convertedQuantity
	 */
	public void setConvertedQuantity(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.CONVERTEDQUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute. 
	 * @param value the convertedQuantity
	 */
	public void setConvertedQuantity(final AbstractOrderEntry item, final Double value)
	{
		setConvertedQuantity( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute. 
	 * @param value the convertedQuantity
	 */
	public void setConvertedQuantity(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setConvertedQuantity( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.convertedQuantity</code> attribute. 
	 * @param value the convertedQuantity
	 */
	public void setConvertedQuantity(final AbstractOrderEntry item, final double value)
	{
		setConvertedQuantity( getSession().getSessionContext(), item, value );
	}
	
	public AddedIncidentProcess createAddedIncidentProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ADDEDINCIDENTPROCESS );
			return (AddedIncidentProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AddedIncidentProcess : "+e.getMessage(), 0 );
		}
	}
	
	public AddedIncidentProcess createAddedIncidentProcess(final Map attributeValues)
	{
		return createAddedIncidentProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public Alert createAlert(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ALERT );
			return (Alert)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Alert : "+e.getMessage(), 0 );
		}
	}
	
	public Alert createAlert(final Map attributeValues)
	{
		return createAlert( getSession().getSessionContext(), attributeValues );
	}
	
	public AlertConfiguration createAlertConfiguration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ALERTCONFIGURATION );
			return (AlertConfiguration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AlertConfiguration : "+e.getMessage(), 0 );
		}
	}
	
	public AlertConfiguration createAlertConfiguration(final Map attributeValues)
	{
		return createAlertConfiguration( getSession().getSessionContext(), attributeValues );
	}
	
	public AlertConfigurationChange createAlertConfigurationChange(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ALERTCONFIGURATIONCHANGE );
			return (AlertConfigurationChange)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AlertConfigurationChange : "+e.getMessage(), 0 );
		}
	}
	
	public AlertConfigurationChange createAlertConfigurationChange(final Map attributeValues)
	{
		return createAlertConfigurationChange( getSession().getSessionContext(), attributeValues );
	}
	
	public AlertProductOption createAlertProductOption(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ALERTPRODUCTOPTION );
			return (AlertProductOption)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AlertProductOption : "+e.getMessage(), 0 );
		}
	}
	
	public AlertProductOption createAlertProductOption(final Map attributeValues)
	{
		return createAlertProductOption( getSession().getSessionContext(), attributeValues );
	}
	
	public AlertTimeOption createAlertTimeOption(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ALERTTIMEOPTION );
			return (AlertTimeOption)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AlertTimeOption : "+e.getMessage(), 0 );
		}
	}
	
	public AlertTimeOption createAlertTimeOption(final Map attributeValues)
	{
		return createAlertTimeOption( getSession().getSessionContext(), attributeValues );
	}
	
	public Backorder createBackorder(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BACKORDER );
			return (Backorder)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Backorder : "+e.getMessage(), 0 );
		}
	}
	
	public Backorder createBackorder(final Map attributeValues)
	{
		return createBackorder( getSession().getSessionContext(), attributeValues );
	}
	
	public BackorderDetail createBackorderDetail(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BACKORDERDETAIL );
			return (BackorderDetail)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BackorderDetail : "+e.getMessage(), 0 );
		}
	}
	
	public BackorderDetail createBackorderDetail(final Map attributeValues)
	{
		return createBackorderDetail( getSession().getSessionContext(), attributeValues );
	}
	
	public BalanceStatement createBalanceStatement(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BALANCESTATEMENT );
			return (BalanceStatement)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BalanceStatement : "+e.getMessage(), 0 );
		}
	}
	
	public BalanceStatement createBalanceStatement(final Map attributeValues)
	{
		return createBalanceStatement( getSession().getSessionContext(), attributeValues );
	}
	
	public BalanceStatementDetail createBalanceStatementDetail(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BALANCESTATEMENTDETAIL );
			return (BalanceStatementDetail)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BalanceStatementDetail : "+e.getMessage(), 0 );
		}
	}
	
	public BalanceStatementDetail createBalanceStatementDetail(final Map attributeValues)
	{
		return createBalanceStatementDetail( getSession().getSessionContext(), attributeValues );
	}
	
	public BatchSearch createBatchSearch(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BATCHSEARCH );
			return (BatchSearch)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BatchSearch : "+e.getMessage(), 0 );
		}
	}
	
	public BatchSearch createBatchSearch(final Map attributeValues)
	{
		return createBatchSearch( getSession().getSessionContext(), attributeValues );
	}
	
	public BTGOrganizationTotalSpentInCurrencyLastYearOperand createBTGOrganizationTotalSpentInCurrencyLastYearOperand(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BTGORGANIZATIONTOTALSPENTINCURRENCYLASTYEAROPERAND );
			return (BTGOrganizationTotalSpentInCurrencyLastYearOperand)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BTGOrganizationTotalSpentInCurrencyLastYearOperand : "+e.getMessage(), 0 );
		}
	}
	
	public BTGOrganizationTotalSpentInCurrencyLastYearOperand createBTGOrganizationTotalSpentInCurrencyLastYearOperand(final Map attributeValues)
	{
		return createBTGOrganizationTotalSpentInCurrencyLastYearOperand( getSession().getSessionContext(), attributeValues );
	}
	
	public BTGOrganizationTotalSpentInCurrencyRelativeDatesOperand createBTGOrganizationTotalSpentInCurrencyRelativeDatesOperand(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.BTGORGANIZATIONTOTALSPENTINCURRENCYRELATIVEDATESOPERAND );
			return (BTGOrganizationTotalSpentInCurrencyRelativeDatesOperand)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BTGOrganizationTotalSpentInCurrencyRelativeDatesOperand : "+e.getMessage(), 0 );
		}
	}
	
	public BTGOrganizationTotalSpentInCurrencyRelativeDatesOperand createBTGOrganizationTotalSpentInCurrencyRelativeDatesOperand(final Map attributeValues)
	{
		return createBTGOrganizationTotalSpentInCurrencyRelativeDatesOperand( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerProductReference createCustomerProductReference(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.CUSTOMERPRODUCTREFERENCE );
			return (CustomerProductReference)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerProductReference : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerProductReference createCustomerProductReference(final Map attributeValues)
	{
		return createCustomerProductReference( getSession().getSessionContext(), attributeValues );
	}
	
	public DocumentSearch createDocumentSearch(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.DOCUMENTSEARCH );
			return (DocumentSearch)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating DocumentSearch : "+e.getMessage(), 0 );
		}
	}
	
	public DocumentSearch createDocumentSearch(final Map attributeValues)
	{
		return createDocumentSearch( getSession().getSessionContext(), attributeValues );
	}
	
	public HotFolderImportFailedProcess createHotFolderImportFailedProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.HOTFOLDERIMPORTFAILEDPROCESS );
			return (HotFolderImportFailedProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HotFolderImportFailedProcess : "+e.getMessage(), 0 );
		}
	}
	
	public HotFolderImportFailedProcess createHotFolderImportFailedProcess(final Map attributeValues)
	{
		return createHotFolderImportFailedProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public Incident createIncident(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.INCIDENT );
			return (Incident)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Incident : "+e.getMessage(), 0 );
		}
	}
	
	public Incident createIncident(final Map attributeValues)
	{
		return createIncident( getSession().getSessionContext(), attributeValues );
	}
	
	public IncidentLine createIncidentLine(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.INCIDENTLINE );
			return (IncidentLine)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating IncidentLine : "+e.getMessage(), 0 );
		}
	}
	
	public IncidentLine createIncidentLine(final Map attributeValues)
	{
		return createIncidentLine( getSession().getSessionContext(), attributeValues );
	}
	
	public IncidentState createIncidentState(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.INCIDENTSTATE );
			return (IncidentState)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating IncidentState : "+e.getMessage(), 0 );
		}
	}
	
	public IncidentState createIncidentState(final Map attributeValues)
	{
		return createIncidentState( getSession().getSessionContext(), attributeValues );
	}
	
	public IncidentType createIncidentType(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.INCIDENTTYPE );
			return (IncidentType)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating IncidentType : "+e.getMessage(), 0 );
		}
	}
	
	public IncidentType createIncidentType(final Map attributeValues)
	{
		return createIncidentType( getSession().getSessionContext(), attributeValues );
	}
	
	public Invoice createInvoice(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.INVOICE );
			return (Invoice)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Invoice : "+e.getMessage(), 0 );
		}
	}
	
	public Invoice createInvoice(final Map attributeValues)
	{
		return createInvoice( getSession().getSessionContext(), attributeValues );
	}
	
	public LostOrderJob createLostOrderJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.LOSTORDERJOB );
			return (LostOrderJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LostOrderJob : "+e.getMessage(), 0 );
		}
	}
	
	public LostOrderJob createLostOrderJob(final Map attributeValues)
	{
		return createLostOrderJob( getSession().getSessionContext(), attributeValues );
	}
	
	public MultipleCatalogsSyncCronJob createMultipleCatalogsSyncCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.MULTIPLECATALOGSSYNCCRONJOB );
			return (MultipleCatalogsSyncCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating MultipleCatalogsSyncCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public MultipleCatalogsSyncCronJob createMultipleCatalogsSyncCronJob(final Map attributeValues)
	{
		return createMultipleCatalogsSyncCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public NeorisB2BCustomerDefaultSetting createNeorisB2BCustomerDefaultSetting(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.NEORISB2BCUSTOMERDEFAULTSETTING );
			return (NeorisB2BCustomerDefaultSetting)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NeorisB2BCustomerDefaultSetting : "+e.getMessage(), 0 );
		}
	}
	
	public NeorisB2BCustomerDefaultSetting createNeorisB2BCustomerDefaultSetting(final Map attributeValues)
	{
		return createNeorisB2BCustomerDefaultSetting( getSession().getSessionContext(), attributeValues );
	}
	
	public NeorisB2BCustomerSettingByStore createNeorisB2BCustomerSettingByStore(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.NEORISB2BCUSTOMERSETTINGBYSTORE );
			return (NeorisB2BCustomerSettingByStore)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NeorisB2BCustomerSettingByStore : "+e.getMessage(), 0 );
		}
	}
	
	public NeorisB2BCustomerSettingByStore createNeorisB2BCustomerSettingByStore(final Map attributeValues)
	{
		return createNeorisB2BCustomerSettingByStore( getSession().getSessionContext(), attributeValues );
	}
	
	public NeorisB2BUnitSettingByStore createNeorisB2BUnitSettingByStore(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.NEORISB2BUNITSETTINGBYSTORE );
			return (NeorisB2BUnitSettingByStore)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NeorisB2BUnitSettingByStore : "+e.getMessage(), 0 );
		}
	}
	
	public NeorisB2BUnitSettingByStore createNeorisB2BUnitSettingByStore(final Map attributeValues)
	{
		return createNeorisB2BUnitSettingByStore( getSession().getSessionContext(), attributeValues );
	}
	
	public NeorisMedia createNeorisMedia(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.NEORISMEDIA );
			return (NeorisMedia)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NeorisMedia : "+e.getMessage(), 0 );
		}
	}
	
	public NeorisMedia createNeorisMedia(final Map attributeValues)
	{
		return createNeorisMedia( getSession().getSessionContext(), attributeValues );
	}
	
	public Notice createNotice(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.NOTICE );
			return (Notice)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Notice : "+e.getMessage(), 0 );
		}
	}
	
	public Notice createNotice(final Map attributeValues)
	{
		return createNotice( getSession().getSessionContext(), attributeValues );
	}
	
	public OrganizationOrdersReportingCronJob createOrganizationOrdersReportingCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ORGANIZATIONORDERSREPORTINGCRONJOB );
			return (OrganizationOrdersReportingCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrganizationOrdersReportingCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public OrganizationOrdersReportingCronJob createOrganizationOrdersReportingCronJob(final Map attributeValues)
	{
		return createOrganizationOrdersReportingCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public OrganizationOrderStatistics createOrganizationOrderStatistics(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.ORGANIZATIONORDERSTATISTICS );
			return (OrganizationOrderStatistics)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrganizationOrderStatistics : "+e.getMessage(), 0 );
		}
	}
	
	public OrganizationOrderStatistics createOrganizationOrderStatistics(final Map attributeValues)
	{
		return createOrganizationOrderStatistics( getSession().getSessionContext(), attributeValues );
	}
	
	public PowertoolsSizeVariantProduct createPowertoolsSizeVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.POWERTOOLSSIZEVARIANTPRODUCT );
			return (PowertoolsSizeVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PowertoolsSizeVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public PowertoolsSizeVariantProduct createPowertoolsSizeVariantProduct(final Map attributeValues)
	{
		return createPowertoolsSizeVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ProductVisibility createProductVisibility(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.PRODUCTVISIBILITY );
			return (ProductVisibility)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProductVisibility : "+e.getMessage(), 0 );
		}
	}
	
	public ProductVisibility createProductVisibility(final Map attributeValues)
	{
		return createProductVisibility( getSession().getSessionContext(), attributeValues );
	}
	
	public ProlamsaAPIProductConfiguration createProlamsaAPIProductConfiguration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.PROLAMSAAPIPRODUCTCONFIGURATION );
			return (ProlamsaAPIProductConfiguration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProlamsaAPIProductConfiguration : "+e.getMessage(), 0 );
		}
	}
	
	public ProlamsaAPIProductConfiguration createProlamsaAPIProductConfiguration(final Map attributeValues)
	{
		return createProlamsaAPIProductConfiguration( getSession().getSessionContext(), attributeValues );
	}
	
	public ProlamsaProduct createProlamsaProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.PROLAMSAPRODUCT );
			return (ProlamsaProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProlamsaProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ProlamsaProduct createProlamsaProduct(final Map attributeValues)
	{
		return createProlamsaProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public QuoteRequestedProcess createQuoteRequestedProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.QUOTEREQUESTEDPROCESS );
			return (QuoteRequestedProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating QuoteRequestedProcess : "+e.getMessage(), 0 );
		}
	}
	
	public QuoteRequestedProcess createQuoteRequestedProcess(final Map attributeValues)
	{
		return createQuoteRequestedProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public SalesArea createSalesArea(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.SALESAREA );
			return (SalesArea)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SalesArea : "+e.getMessage(), 0 );
		}
	}
	
	public SalesArea createSalesArea(final Map attributeValues)
	{
		return createSalesArea( getSession().getSessionContext(), attributeValues );
	}
	
	public TermsAndCoditionsByStore createTermsAndCoditionsByStore(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.TERMSANDCODITIONSBYSTORE );
			return (TermsAndCoditionsByStore)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating TermsAndCoditionsByStore : "+e.getMessage(), 0 );
		}
	}
	
	public TermsAndCoditionsByStore createTermsAndCoditionsByStore(final Map attributeValues)
	{
		return createTermsAndCoditionsByStore( getSession().getSessionContext(), attributeValues );
	}
	
	public TransportationMode createTransportationMode(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( ProlamsaCoreConstants.TC.TRANSPORTATIONMODE );
			return (TransportationMode)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating TransportationMode : "+e.getMessage(), 0 );
		}
	}
	
	public TransportationMode createTransportationMode(final Map attributeValues)
	{
		return createTransportationMode( getSession().getSessionContext(), attributeValues );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.creditScoreCard</code> attribute.
	 * @return the creditScoreCard
	 */
	public Double getCreditScoreCard(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.CREDITSCORECARD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.creditScoreCard</code> attribute.
	 * @return the creditScoreCard
	 */
	public Double getCreditScoreCard(final AbstractOrder item)
	{
		return getCreditScoreCard( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.creditScoreCard</code> attribute. 
	 * @return the creditScoreCard
	 */
	public double getCreditScoreCardAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getCreditScoreCard( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.creditScoreCard</code> attribute. 
	 * @return the creditScoreCard
	 */
	public double getCreditScoreCardAsPrimitive(final AbstractOrder item)
	{
		return getCreditScoreCardAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.creditScoreCard</code> attribute. 
	 * @param value the creditScoreCard
	 */
	public void setCreditScoreCard(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.CREDITSCORECARD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.creditScoreCard</code> attribute. 
	 * @param value the creditScoreCard
	 */
	public void setCreditScoreCard(final AbstractOrder item, final Double value)
	{
		setCreditScoreCard( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.creditScoreCard</code> attribute. 
	 * @param value the creditScoreCard
	 */
	public void setCreditScoreCard(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setCreditScoreCard( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.creditScoreCard</code> attribute. 
	 * @param value the creditScoreCard
	 */
	public void setCreditScoreCard(final AbstractOrder item, final double value)
	{
		setCreditScoreCard( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute.
	 * @return the creditScoreCardHighLimit
	 */
	public Integer getCreditScoreCardHighLimit(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.CREDITSCORECARDHIGHLIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute.
	 * @return the creditScoreCardHighLimit
	 */
	public Integer getCreditScoreCardHighLimit(final BaseStore item)
	{
		return getCreditScoreCardHighLimit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute. 
	 * @return the creditScoreCardHighLimit
	 */
	public int getCreditScoreCardHighLimitAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getCreditScoreCardHighLimit( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute. 
	 * @return the creditScoreCardHighLimit
	 */
	public int getCreditScoreCardHighLimitAsPrimitive(final BaseStore item)
	{
		return getCreditScoreCardHighLimitAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute. 
	 * @param value the creditScoreCardHighLimit
	 */
	public void setCreditScoreCardHighLimit(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.CREDITSCORECARDHIGHLIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute. 
	 * @param value the creditScoreCardHighLimit
	 */
	public void setCreditScoreCardHighLimit(final BaseStore item, final Integer value)
	{
		setCreditScoreCardHighLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute. 
	 * @param value the creditScoreCardHighLimit
	 */
	public void setCreditScoreCardHighLimit(final SessionContext ctx, final BaseStore item, final int value)
	{
		setCreditScoreCardHighLimit( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardHighLimit</code> attribute. 
	 * @param value the creditScoreCardHighLimit
	 */
	public void setCreditScoreCardHighLimit(final BaseStore item, final int value)
	{
		setCreditScoreCardHighLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute.
	 * @return the creditScoreCardLowLimit
	 */
	public Integer getCreditScoreCardLowLimit(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.CREDITSCORECARDLOWLIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute.
	 * @return the creditScoreCardLowLimit
	 */
	public Integer getCreditScoreCardLowLimit(final BaseStore item)
	{
		return getCreditScoreCardLowLimit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute. 
	 * @return the creditScoreCardLowLimit
	 */
	public int getCreditScoreCardLowLimitAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getCreditScoreCardLowLimit( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute. 
	 * @return the creditScoreCardLowLimit
	 */
	public int getCreditScoreCardLowLimitAsPrimitive(final BaseStore item)
	{
		return getCreditScoreCardLowLimitAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute. 
	 * @param value the creditScoreCardLowLimit
	 */
	public void setCreditScoreCardLowLimit(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.CREDITSCORECARDLOWLIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute. 
	 * @param value the creditScoreCardLowLimit
	 */
	public void setCreditScoreCardLowLimit(final BaseStore item, final Integer value)
	{
		setCreditScoreCardLowLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute. 
	 * @param value the creditScoreCardLowLimit
	 */
	public void setCreditScoreCardLowLimit(final SessionContext ctx, final BaseStore item, final int value)
	{
		setCreditScoreCardLowLimit( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.creditScoreCardLowLimit</code> attribute. 
	 * @param value the creditScoreCardLowLimit
	 */
	public void setCreditScoreCardLowLimit(final BaseStore item, final int value)
	{
		setCreditScoreCardLowLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.currency</code> attribute.
	 * @return the currency
	 */
	public Currency getCurrency(final SessionContext ctx, final B2BUnit item)
	{
		return (Currency)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.CURRENCY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.currency</code> attribute.
	 * @return the currency
	 */
	public Currency getCurrency(final B2BUnit item)
	{
		return getCurrency( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final SessionContext ctx, final B2BUnit item, final Currency value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.CURRENCY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final B2BUnit item, final Currency value)
	{
		setCurrency( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.currencyForStore</code> attribute.
	 * @return the currencyForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getCurrencyForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.CURRENCYFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.currencyForStore</code> attribute.
	 * @return the currencyForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getCurrencyForStore(final B2BUnit item)
	{
		return getCurrencyForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.currencyForStore</code> attribute. 
	 * @param value the currencyForStore
	 */
	public void setCurrencyForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.CURRENCYFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.currencyForStore</code> attribute. 
	 * @param value the currencyForStore
	 */
	public void setCurrencyForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setCurrencyForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.currentLocation</code> attribute.
	 * @return the currentLocation
	 */
	public String getCurrentLocation(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CURRENTLOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.currentLocation</code> attribute.
	 * @return the currentLocation
	 */
	public String getCurrentLocation(final B2BCustomer item)
	{
		return getCurrentLocation( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.currentLocation</code> attribute. 
	 * @param value the currentLocation
	 */
	public void setCurrentLocation(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CURRENTLOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.currentLocation</code> attribute. 
	 * @param value the currentLocation
	 */
	public void setCurrentLocation(final B2BCustomer item, final String value)
	{
		setCurrentLocation( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentLenguaje</code> attribute.
	 * @return the customerCurrentLenguaje
	 */
	public String getCustomerCurrentLenguaje(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTLENGUAJE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentLenguaje</code> attribute.
	 * @return the customerCurrentLenguaje
	 */
	public String getCustomerCurrentLenguaje(final B2BCustomer item)
	{
		return getCustomerCurrentLenguaje( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentLenguaje</code> attribute. 
	 * @param value the customerCurrentLenguaje
	 */
	public void setCustomerCurrentLenguaje(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTLENGUAJE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentLenguaje</code> attribute. 
	 * @param value the customerCurrentLenguaje
	 */
	public void setCustomerCurrentLenguaje(final B2BCustomer item, final String value)
	{
		setCustomerCurrentLenguaje( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentMoneda</code> attribute.
	 * @return the customerCurrentMoneda
	 */
	public String getCustomerCurrentMoneda(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTMONEDA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentMoneda</code> attribute.
	 * @return the customerCurrentMoneda
	 */
	public String getCustomerCurrentMoneda(final B2BCustomer item)
	{
		return getCustomerCurrentMoneda( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentMoneda</code> attribute. 
	 * @param value the customerCurrentMoneda
	 */
	public void setCustomerCurrentMoneda(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTMONEDA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentMoneda</code> attribute. 
	 * @param value the customerCurrentMoneda
	 */
	public void setCustomerCurrentMoneda(final B2BCustomer item, final String value)
	{
		setCustomerCurrentMoneda( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentSMetrico</code> attribute.
	 * @return the customerCurrentSMetrico
	 */
	public String getCustomerCurrentSMetrico(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTSMETRICO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentSMetrico</code> attribute.
	 * @return the customerCurrentSMetrico
	 */
	public String getCustomerCurrentSMetrico(final B2BCustomer item)
	{
		return getCustomerCurrentSMetrico( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentSMetrico</code> attribute. 
	 * @param value the customerCurrentSMetrico
	 */
	public void setCustomerCurrentSMetrico(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTSMETRICO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentSMetrico</code> attribute. 
	 * @param value the customerCurrentSMetrico
	 */
	public void setCustomerCurrentSMetrico(final B2BCustomer item, final String value)
	{
		setCustomerCurrentSMetrico( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentUMedida</code> attribute.
	 * @return the customerCurrentUMedida
	 */
	public String getCustomerCurrentUMedida(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTUMEDIDA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.customerCurrentUMedida</code> attribute.
	 * @return the customerCurrentUMedida
	 */
	public String getCustomerCurrentUMedida(final B2BCustomer item)
	{
		return getCustomerCurrentUMedida( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentUMedida</code> attribute. 
	 * @param value the customerCurrentUMedida
	 */
	public void setCustomerCurrentUMedida(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.CUSTOMERCURRENTUMEDIDA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.customerCurrentUMedida</code> attribute. 
	 * @param value the customerCurrentUMedida
	 */
	public void setCustomerCurrentUMedida(final B2BCustomer item, final String value)
	{
		setCustomerCurrentUMedida( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute.
	 * @return the daysAddedDeliveryDate
	 */
	public Integer getDaysAddedDeliveryDate(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.DAYSADDEDDELIVERYDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute.
	 * @return the daysAddedDeliveryDate
	 */
	public Integer getDaysAddedDeliveryDate(final BaseStore item)
	{
		return getDaysAddedDeliveryDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute. 
	 * @return the daysAddedDeliveryDate
	 */
	public int getDaysAddedDeliveryDateAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getDaysAddedDeliveryDate( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute. 
	 * @return the daysAddedDeliveryDate
	 */
	public int getDaysAddedDeliveryDateAsPrimitive(final BaseStore item)
	{
		return getDaysAddedDeliveryDateAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute. 
	 * @param value the daysAddedDeliveryDate
	 */
	public void setDaysAddedDeliveryDate(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.DAYSADDEDDELIVERYDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute. 
	 * @param value the daysAddedDeliveryDate
	 */
	public void setDaysAddedDeliveryDate(final BaseStore item, final Integer value)
	{
		setDaysAddedDeliveryDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute. 
	 * @param value the daysAddedDeliveryDate
	 */
	public void setDaysAddedDeliveryDate(final SessionContext ctx, final BaseStore item, final int value)
	{
		setDaysAddedDeliveryDate( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.daysAddedDeliveryDate</code> attribute. 
	 * @param value the daysAddedDeliveryDate
	 */
	public void setDaysAddedDeliveryDate(final BaseStore item, final int value)
	{
		setDaysAddedDeliveryDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.defaultMeasurementSystem</code> attribute.
	 * @return the defaultMeasurementSystem
	 */
	public EnumerationValue getDefaultMeasurementSystem(final SessionContext ctx, final BaseStore item)
	{
		return (EnumerationValue)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.DEFAULTMEASUREMENTSYSTEM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.defaultMeasurementSystem</code> attribute.
	 * @return the defaultMeasurementSystem
	 */
	public EnumerationValue getDefaultMeasurementSystem(final BaseStore item)
	{
		return getDefaultMeasurementSystem( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.defaultMeasurementSystem</code> attribute. 
	 * @param value the defaultMeasurementSystem
	 */
	public void setDefaultMeasurementSystem(final SessionContext ctx, final BaseStore item, final EnumerationValue value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.DEFAULTMEASUREMENTSYSTEM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.defaultMeasurementSystem</code> attribute. 
	 * @param value the defaultMeasurementSystem
	 */
	public void setDefaultMeasurementSystem(final BaseStore item, final EnumerationValue value)
	{
		setDefaultMeasurementSystem( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Unit.defaultMeasurementSystem</code> attribute.
	 * @return the defaultMeasurementSystem
	 */
	public EnumerationValue getDefaultMeasurementSystem(final SessionContext ctx, final Unit item)
	{
		return (EnumerationValue)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Unit.DEFAULTMEASUREMENTSYSTEM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Unit.defaultMeasurementSystem</code> attribute.
	 * @return the defaultMeasurementSystem
	 */
	public EnumerationValue getDefaultMeasurementSystem(final Unit item)
	{
		return getDefaultMeasurementSystem( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Unit.defaultMeasurementSystem</code> attribute. 
	 * @param value the defaultMeasurementSystem
	 */
	public void setDefaultMeasurementSystem(final SessionContext ctx, final Unit item, final EnumerationValue value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Unit.DEFAULTMEASUREMENTSYSTEM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Unit.defaultMeasurementSystem</code> attribute. 
	 * @param value the defaultMeasurementSystem
	 */
	public void setDefaultMeasurementSystem(final Unit item, final EnumerationValue value)
	{
		setDefaultMeasurementSystem( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.defaultPlant</code> attribute.
	 * @return the defaultPlant
	 */
	public String getDefaultPlant(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Address.DEFAULTPLANT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.defaultPlant</code> attribute.
	 * @return the defaultPlant
	 */
	public String getDefaultPlant(final Address item)
	{
		return getDefaultPlant( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.defaultPlant</code> attribute. 
	 * @param value the defaultPlant
	 */
	public void setDefaultPlant(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Address.DEFAULTPLANT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.defaultPlant</code> attribute. 
	 * @param value the defaultPlant
	 */
	public void setDefaultPlant(final Address item, final String value)
	{
		setDefaultPlant( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.defaultSettings</code> attribute.
	 * @return the defaultSettings
	 */
	public List<NeorisB2BCustomerDefaultSetting> getDefaultSettings(final SessionContext ctx, final B2BCustomer item)
	{
		List<NeorisB2BCustomerDefaultSetting> coll = (List<NeorisB2BCustomerDefaultSetting>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.DEFAULTSETTINGS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.defaultSettings</code> attribute.
	 * @return the defaultSettings
	 */
	public List<NeorisB2BCustomerDefaultSetting> getDefaultSettings(final B2BCustomer item)
	{
		return getDefaultSettings( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.defaultSettings</code> attribute. 
	 * @param value the defaultSettings
	 */
	public void setDefaultSettings(final SessionContext ctx, final B2BCustomer item, final List<NeorisB2BCustomerDefaultSetting> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.DEFAULTSETTINGS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.defaultSettings</code> attribute. 
	 * @param value the defaultSettings
	 */
	public void setDefaultSettings(final B2BCustomer item, final List<NeorisB2BCustomerDefaultSetting> value)
	{
		setDefaultSettings( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.emailNotification</code> attribute.
	 * @return the emailNotification
	 */
	public String getEmailNotification(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.EMAILNOTIFICATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.emailNotification</code> attribute.
	 * @return the emailNotification
	 */
	public String getEmailNotification(final B2BUnit item)
	{
		return getEmailNotification( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.emailNotification</code> attribute. 
	 * @param value the emailNotification
	 */
	public void setEmailNotification(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.EMAILNOTIFICATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.emailNotification</code> attribute. 
	 * @param value the emailNotification
	 */
	public void setEmailNotification(final B2BUnit item, final String value)
	{
		setEmailNotification( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute.
	 * @return the favoriteCustomersMaxLimit
	 */
	public Integer getFavoriteCustomersMaxLimit(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.FAVORITECUSTOMERSMAXLIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute.
	 * @return the favoriteCustomersMaxLimit
	 */
	public Integer getFavoriteCustomersMaxLimit(final BaseStore item)
	{
		return getFavoriteCustomersMaxLimit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute. 
	 * @return the favoriteCustomersMaxLimit
	 */
	public int getFavoriteCustomersMaxLimitAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getFavoriteCustomersMaxLimit( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute. 
	 * @return the favoriteCustomersMaxLimit
	 */
	public int getFavoriteCustomersMaxLimitAsPrimitive(final BaseStore item)
	{
		return getFavoriteCustomersMaxLimitAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute. 
	 * @param value the favoriteCustomersMaxLimit
	 */
	public void setFavoriteCustomersMaxLimit(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.FAVORITECUSTOMERSMAXLIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute. 
	 * @param value the favoriteCustomersMaxLimit
	 */
	public void setFavoriteCustomersMaxLimit(final BaseStore item, final Integer value)
	{
		setFavoriteCustomersMaxLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute. 
	 * @param value the favoriteCustomersMaxLimit
	 */
	public void setFavoriteCustomersMaxLimit(final SessionContext ctx, final BaseStore item, final int value)
	{
		setFavoriteCustomersMaxLimit( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.favoriteCustomersMaxLimit</code> attribute. 
	 * @param value the favoriteCustomersMaxLimit
	 */
	public void setFavoriteCustomersMaxLimit(final BaseStore item, final int value)
	{
		setFavoriteCustomersMaxLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.favoriteProducts</code> attribute.
	 * @return the favoriteProducts
	 */
	public Set<ProlamsaProduct> getFavoriteProducts(final SessionContext ctx, final B2BUnit item)
	{
		final List<ProlamsaProduct> items = item.getLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2PROLAMSAPRODUCTS,
			null,
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_TGT_ORDERED, true)
		);
		return new LinkedHashSet<ProlamsaProduct>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.favoriteProducts</code> attribute.
	 * @return the favoriteProducts
	 */
	public Set<ProlamsaProduct> getFavoriteProducts(final B2BUnit item)
	{
		return getFavoriteProducts( getSession().getSessionContext(), item );
	}
	
	public long getFavoriteProductsCount(final SessionContext ctx, final B2BUnit item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2PROLAMSAPRODUCTS,
			null
		);
	}
	
	public long getFavoriteProductsCount(final B2BUnit item)
	{
		return getFavoriteProductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.favoriteProducts</code> attribute. 
	 * @param value the favoriteProducts
	 */
	public void setFavoriteProducts(final SessionContext ctx, final B2BUnit item, final Set<ProlamsaProduct> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2PROLAMSAPRODUCTS,
			null,
			value,
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BUNITS2PROLAMSAPRODUCTS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.favoriteProducts</code> attribute. 
	 * @param value the favoriteProducts
	 */
	public void setFavoriteProducts(final B2BUnit item, final Set<ProlamsaProduct> value)
	{
		setFavoriteProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to favoriteProducts. 
	 * @param value the item to add to favoriteProducts
	 */
	public void addToFavoriteProducts(final SessionContext ctx, final B2BUnit item, final ProlamsaProduct value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2PROLAMSAPRODUCTS,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BUNITS2PROLAMSAPRODUCTS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to favoriteProducts. 
	 * @param value the item to add to favoriteProducts
	 */
	public void addToFavoriteProducts(final B2BUnit item, final ProlamsaProduct value)
	{
		addToFavoriteProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from favoriteProducts. 
	 * @param value the item to remove from favoriteProducts
	 */
	public void removeFromFavoriteProducts(final SessionContext ctx, final B2BUnit item, final ProlamsaProduct value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.B2BUNITS2PROLAMSAPRODUCTS,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(B2BUNITS2PROLAMSAPRODUCTS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(B2BUNITS2PROLAMSAPRODUCTS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from favoriteProducts. 
	 * @param value the item to remove from favoriteProducts
	 */
	public void removeFromFavoriteProducts(final B2BUnit item, final ProlamsaProduct value)
	{
		removeFromFavoriteProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.finalOrder</code> attribute.
	 * @return the finalOrder
	 */
	public AbstractOrder getFinalOrder(final SessionContext ctx, final AbstractOrder item)
	{
		return (AbstractOrder)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.FINALORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.finalOrder</code> attribute.
	 * @return the finalOrder
	 */
	public AbstractOrder getFinalOrder(final AbstractOrder item)
	{
		return getFinalOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.finalOrder</code> attribute. 
	 * @param value the finalOrder
	 */
	public void setFinalOrder(final SessionContext ctx, final AbstractOrder item, final AbstractOrder value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.FINALORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.finalOrder</code> attribute. 
	 * @param value the finalOrder
	 */
	public void setFinalOrder(final AbstractOrder item, final AbstractOrder value)
	{
		setFinalOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalPrice</code> attribute.
	 * @return the galvametalPrice
	 */
	public Double getGalvametalPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVAMETALPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalPrice</code> attribute.
	 * @return the galvametalPrice
	 */
	public Double getGalvametalPrice(final AbstractOrder item)
	{
		return getGalvametalPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalPrice</code> attribute. 
	 * @return the galvametalPrice
	 */
	public double getGalvametalPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getGalvametalPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalPrice</code> attribute. 
	 * @return the galvametalPrice
	 */
	public double getGalvametalPriceAsPrimitive(final AbstractOrder item)
	{
		return getGalvametalPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalPrice</code> attribute. 
	 * @param value the galvametalPrice
	 */
	public void setGalvametalPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVAMETALPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalPrice</code> attribute. 
	 * @param value the galvametalPrice
	 */
	public void setGalvametalPrice(final AbstractOrder item, final Double value)
	{
		setGalvametalPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalPrice</code> attribute. 
	 * @param value the galvametalPrice
	 */
	public void setGalvametalPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setGalvametalPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalPrice</code> attribute. 
	 * @param value the galvametalPrice
	 */
	public void setGalvametalPrice(final AbstractOrder item, final double value)
	{
		setGalvametalPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalWeight</code> attribute.
	 * @return the galvametalWeight
	 */
	public Double getGalvametalWeight(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVAMETALWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalWeight</code> attribute.
	 * @return the galvametalWeight
	 */
	public Double getGalvametalWeight(final AbstractOrder item)
	{
		return getGalvametalWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalWeight</code> attribute. 
	 * @return the galvametalWeight
	 */
	public double getGalvametalWeightAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getGalvametalWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvametalWeight</code> attribute. 
	 * @return the galvametalWeight
	 */
	public double getGalvametalWeightAsPrimitive(final AbstractOrder item)
	{
		return getGalvametalWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalWeight</code> attribute. 
	 * @param value the galvametalWeight
	 */
	public void setGalvametalWeight(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVAMETALWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalWeight</code> attribute. 
	 * @param value the galvametalWeight
	 */
	public void setGalvametalWeight(final AbstractOrder item, final Double value)
	{
		setGalvametalWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalWeight</code> attribute. 
	 * @param value the galvametalWeight
	 */
	public void setGalvametalWeight(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setGalvametalWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvametalWeight</code> attribute. 
	 * @param value the galvametalWeight
	 */
	public void setGalvametalWeight(final AbstractOrder item, final double value)
	{
		setGalvametalWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedPrice</code> attribute.
	 * @return the galvanizedPrice
	 */
	public Double getGalvanizedPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVANIZEDPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedPrice</code> attribute.
	 * @return the galvanizedPrice
	 */
	public Double getGalvanizedPrice(final AbstractOrder item)
	{
		return getGalvanizedPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedPrice</code> attribute. 
	 * @return the galvanizedPrice
	 */
	public double getGalvanizedPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getGalvanizedPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedPrice</code> attribute. 
	 * @return the galvanizedPrice
	 */
	public double getGalvanizedPriceAsPrimitive(final AbstractOrder item)
	{
		return getGalvanizedPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedPrice</code> attribute. 
	 * @param value the galvanizedPrice
	 */
	public void setGalvanizedPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVANIZEDPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedPrice</code> attribute. 
	 * @param value the galvanizedPrice
	 */
	public void setGalvanizedPrice(final AbstractOrder item, final Double value)
	{
		setGalvanizedPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedPrice</code> attribute. 
	 * @param value the galvanizedPrice
	 */
	public void setGalvanizedPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setGalvanizedPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedPrice</code> attribute. 
	 * @param value the galvanizedPrice
	 */
	public void setGalvanizedPrice(final AbstractOrder item, final double value)
	{
		setGalvanizedPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedWeight</code> attribute.
	 * @return the galvanizedWeight
	 */
	public Double getGalvanizedWeight(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVANIZEDWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedWeight</code> attribute.
	 * @return the galvanizedWeight
	 */
	public Double getGalvanizedWeight(final AbstractOrder item)
	{
		return getGalvanizedWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedWeight</code> attribute. 
	 * @return the galvanizedWeight
	 */
	public double getGalvanizedWeightAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getGalvanizedWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.galvanizedWeight</code> attribute. 
	 * @return the galvanizedWeight
	 */
	public double getGalvanizedWeightAsPrimitive(final AbstractOrder item)
	{
		return getGalvanizedWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedWeight</code> attribute. 
	 * @param value the galvanizedWeight
	 */
	public void setGalvanizedWeight(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.GALVANIZEDWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedWeight</code> attribute. 
	 * @param value the galvanizedWeight
	 */
	public void setGalvanizedWeight(final AbstractOrder item, final Double value)
	{
		setGalvanizedWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedWeight</code> attribute. 
	 * @param value the galvanizedWeight
	 */
	public void setGalvanizedWeight(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setGalvanizedWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.galvanizedWeight</code> attribute. 
	 * @param value the galvanizedWeight
	 */
	public void setGalvanizedWeight(final AbstractOrder item, final double value)
	{
		setGalvanizedWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.genericNotificationEmail</code> attribute.
	 * @return the genericNotificationEmail
	 */
	public String getGenericNotificationEmail(final SessionContext ctx, final BaseStore item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.GENERICNOTIFICATIONEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.genericNotificationEmail</code> attribute.
	 * @return the genericNotificationEmail
	 */
	public String getGenericNotificationEmail(final BaseStore item)
	{
		return getGenericNotificationEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.genericNotificationEmail</code> attribute. 
	 * @param value the genericNotificationEmail
	 */
	public void setGenericNotificationEmail(final SessionContext ctx, final BaseStore item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.GENERICNOTIFICATIONEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.genericNotificationEmail</code> attribute. 
	 * @param value the genericNotificationEmail
	 */
	public void setGenericNotificationEmail(final BaseStore item, final String value)
	{
		setGenericNotificationEmail( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return ProlamsaCoreConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.groupNumber</code> attribute.
	 * @return the groupNumber
	 */
	public Integer getGroupNumber(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.GROUPNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.groupNumber</code> attribute.
	 * @return the groupNumber
	 */
	public Integer getGroupNumber(final AbstractOrderEntry item)
	{
		return getGroupNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.groupNumber</code> attribute. 
	 * @return the groupNumber
	 */
	public int getGroupNumberAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Integer value = getGroupNumber( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.groupNumber</code> attribute. 
	 * @return the groupNumber
	 */
	public int getGroupNumberAsPrimitive(final AbstractOrderEntry item)
	{
		return getGroupNumberAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.groupNumber</code> attribute. 
	 * @param value the groupNumber
	 */
	public void setGroupNumber(final SessionContext ctx, final AbstractOrderEntry item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.GROUPNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.groupNumber</code> attribute. 
	 * @param value the groupNumber
	 */
	public void setGroupNumber(final AbstractOrderEntry item, final Integer value)
	{
		setGroupNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.groupNumber</code> attribute. 
	 * @param value the groupNumber
	 */
	public void setGroupNumber(final SessionContext ctx, final AbstractOrderEntry item, final int value)
	{
		setGroupNumber( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.groupNumber</code> attribute. 
	 * @param value the groupNumber
	 */
	public void setGroupNumber(final AbstractOrderEntry item, final int value)
	{
		setGroupNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledPrice</code> attribute.
	 * @return the hotRolledPrice
	 */
	public Double getHotRolledPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.HOTROLLEDPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledPrice</code> attribute.
	 * @return the hotRolledPrice
	 */
	public Double getHotRolledPrice(final AbstractOrder item)
	{
		return getHotRolledPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledPrice</code> attribute. 
	 * @return the hotRolledPrice
	 */
	public double getHotRolledPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getHotRolledPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledPrice</code> attribute. 
	 * @return the hotRolledPrice
	 */
	public double getHotRolledPriceAsPrimitive(final AbstractOrder item)
	{
		return getHotRolledPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledPrice</code> attribute. 
	 * @param value the hotRolledPrice
	 */
	public void setHotRolledPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.HOTROLLEDPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledPrice</code> attribute. 
	 * @param value the hotRolledPrice
	 */
	public void setHotRolledPrice(final AbstractOrder item, final Double value)
	{
		setHotRolledPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledPrice</code> attribute. 
	 * @param value the hotRolledPrice
	 */
	public void setHotRolledPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setHotRolledPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledPrice</code> attribute. 
	 * @param value the hotRolledPrice
	 */
	public void setHotRolledPrice(final AbstractOrder item, final double value)
	{
		setHotRolledPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledWeight</code> attribute.
	 * @return the hotRolledWeight
	 */
	public Double getHotRolledWeight(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.HOTROLLEDWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledWeight</code> attribute.
	 * @return the hotRolledWeight
	 */
	public Double getHotRolledWeight(final AbstractOrder item)
	{
		return getHotRolledWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledWeight</code> attribute. 
	 * @return the hotRolledWeight
	 */
	public double getHotRolledWeightAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getHotRolledWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hotRolledWeight</code> attribute. 
	 * @return the hotRolledWeight
	 */
	public double getHotRolledWeightAsPrimitive(final AbstractOrder item)
	{
		return getHotRolledWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledWeight</code> attribute. 
	 * @param value the hotRolledWeight
	 */
	public void setHotRolledWeight(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.HOTROLLEDWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledWeight</code> attribute. 
	 * @param value the hotRolledWeight
	 */
	public void setHotRolledWeight(final AbstractOrder item, final Double value)
	{
		setHotRolledWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledWeight</code> attribute. 
	 * @param value the hotRolledWeight
	 */
	public void setHotRolledWeight(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setHotRolledWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hotRolledWeight</code> attribute. 
	 * @param value the hotRolledWeight
	 */
	public void setHotRolledWeight(final AbstractOrder item, final double value)
	{
		setHotRolledWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute.
	 * @return the HSSFunctionaliatyEnabled
	 */
	public Boolean isHSSFunctionaliatyEnabled(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.HSSFUNCTIONALIATYENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute.
	 * @return the HSSFunctionaliatyEnabled
	 */
	public Boolean isHSSFunctionaliatyEnabled(final BaseStore item)
	{
		return isHSSFunctionaliatyEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute. 
	 * @return the HSSFunctionaliatyEnabled
	 */
	public boolean isHSSFunctionaliatyEnabledAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isHSSFunctionaliatyEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute. 
	 * @return the HSSFunctionaliatyEnabled
	 */
	public boolean isHSSFunctionaliatyEnabledAsPrimitive(final BaseStore item)
	{
		return isHSSFunctionaliatyEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute. 
	 * @param value the HSSFunctionaliatyEnabled
	 */
	public void setHSSFunctionaliatyEnabled(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.HSSFUNCTIONALIATYENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute. 
	 * @param value the HSSFunctionaliatyEnabled
	 */
	public void setHSSFunctionaliatyEnabled(final BaseStore item, final Boolean value)
	{
		setHSSFunctionaliatyEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute. 
	 * @param value the HSSFunctionaliatyEnabled
	 */
	public void setHSSFunctionaliatyEnabled(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setHSSFunctionaliatyEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.HSSFunctionaliatyEnabled</code> attribute. 
	 * @param value the HSSFunctionaliatyEnabled
	 */
	public void setHSSFunctionaliatyEnabled(final BaseStore item, final boolean value)
	{
		setHSSFunctionaliatyEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hssOrder</code> attribute.
	 * @return the hssOrder
	 */
	public Order getHssOrder(final SessionContext ctx, final AbstractOrder item)
	{
		return (Order)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.HSSORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hssOrder</code> attribute.
	 * @return the hssOrder
	 */
	public Order getHssOrder(final AbstractOrder item)
	{
		return getHssOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hssOrder</code> attribute. 
	 * @param value the hssOrder
	 */
	public void setHssOrder(final SessionContext ctx, final AbstractOrder item, final Order value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.HSSORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hssOrder</code> attribute. 
	 * @param value the hssOrder
	 */
	public void setHssOrder(final AbstractOrder item, final Order value)
	{
		setHssOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.incindentTypes</code> attribute.
	 * @return the incindentTypes
	 */
	public List<IncidentType> getIncindentTypes(final SessionContext ctx, final BaseStore item)
	{
		List<IncidentType> coll = (List<IncidentType>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.INCINDENTTYPES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.incindentTypes</code> attribute.
	 * @return the incindentTypes
	 */
	public List<IncidentType> getIncindentTypes(final BaseStore item)
	{
		return getIncindentTypes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.incindentTypes</code> attribute. 
	 * @param value the incindentTypes
	 */
	public void setIncindentTypes(final SessionContext ctx, final BaseStore item, final List<IncidentType> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.INCINDENTTYPES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.incindentTypes</code> attribute. 
	 * @param value the incindentTypes
	 */
	public void setIncindentTypes(final BaseStore item, final List<IncidentType> value)
	{
		setIncindentTypes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.incoterm</code> attribute.
	 * @return the incoterm
	 */
	public String getIncoterm(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Address.INCOTERM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.incoterm</code> attribute.
	 * @return the incoterm
	 */
	public String getIncoterm(final Address item)
	{
		return getIncoterm( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.incoterm</code> attribute. 
	 * @param value the incoterm
	 */
	public void setIncoterm(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Address.INCOTERM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.incoterm</code> attribute. 
	 * @param value the incoterm
	 */
	public void setIncoterm(final Address item, final String value)
	{
		setIncoterm( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.industry</code> attribute.
	 * @return the industry
	 */
	public String getIndustry(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.INDUSTRY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.industry</code> attribute.
	 * @return the industry
	 */
	public String getIndustry(final B2BUnit item)
	{
		return getIndustry( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.industry</code> attribute. 
	 * @param value the industry
	 */
	public void setIndustry(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.INDUSTRY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.industry</code> attribute. 
	 * @param value the industry
	 */
	public void setIndustry(final B2BUnit item, final String value)
	{
		setIndustry( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.industryForStore</code> attribute.
	 * @return the industryForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getIndustryForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.INDUSTRYFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.industryForStore</code> attribute.
	 * @return the industryForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getIndustryForStore(final B2BUnit item)
	{
		return getIndustryForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.industryForStore</code> attribute. 
	 * @param value the industryForStore
	 */
	public void setIndustryForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.INDUSTRYFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.industryForStore</code> attribute. 
	 * @param value the industryForStore
	 */
	public void setIndustryForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setIndustryForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute.
	 * @return the inventoryQtyFtAPIMax
	 */
	public Integer getInventoryQtyFtAPIMax(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.INVENTORYQTYFTAPIMAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute.
	 * @return the inventoryQtyFtAPIMax
	 */
	public Integer getInventoryQtyFtAPIMax(final BaseStore item)
	{
		return getInventoryQtyFtAPIMax( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute. 
	 * @return the inventoryQtyFtAPIMax
	 */
	public int getInventoryQtyFtAPIMaxAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getInventoryQtyFtAPIMax( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute. 
	 * @return the inventoryQtyFtAPIMax
	 */
	public int getInventoryQtyFtAPIMaxAsPrimitive(final BaseStore item)
	{
		return getInventoryQtyFtAPIMaxAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute. 
	 * @param value the inventoryQtyFtAPIMax
	 */
	public void setInventoryQtyFtAPIMax(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.INVENTORYQTYFTAPIMAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute. 
	 * @param value the inventoryQtyFtAPIMax
	 */
	public void setInventoryQtyFtAPIMax(final BaseStore item, final Integer value)
	{
		setInventoryQtyFtAPIMax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute. 
	 * @param value the inventoryQtyFtAPIMax
	 */
	public void setInventoryQtyFtAPIMax(final SessionContext ctx, final BaseStore item, final int value)
	{
		setInventoryQtyFtAPIMax( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMax</code> attribute. 
	 * @param value the inventoryQtyFtAPIMax
	 */
	public void setInventoryQtyFtAPIMax(final BaseStore item, final int value)
	{
		setInventoryQtyFtAPIMax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute.
	 * @return the inventoryQtyFtAPIMin
	 */
	public Integer getInventoryQtyFtAPIMin(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.INVENTORYQTYFTAPIMIN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute.
	 * @return the inventoryQtyFtAPIMin
	 */
	public Integer getInventoryQtyFtAPIMin(final BaseStore item)
	{
		return getInventoryQtyFtAPIMin( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute. 
	 * @return the inventoryQtyFtAPIMin
	 */
	public int getInventoryQtyFtAPIMinAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getInventoryQtyFtAPIMin( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute. 
	 * @return the inventoryQtyFtAPIMin
	 */
	public int getInventoryQtyFtAPIMinAsPrimitive(final BaseStore item)
	{
		return getInventoryQtyFtAPIMinAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute. 
	 * @param value the inventoryQtyFtAPIMin
	 */
	public void setInventoryQtyFtAPIMin(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.INVENTORYQTYFTAPIMIN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute. 
	 * @param value the inventoryQtyFtAPIMin
	 */
	public void setInventoryQtyFtAPIMin(final BaseStore item, final Integer value)
	{
		setInventoryQtyFtAPIMin( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute. 
	 * @param value the inventoryQtyFtAPIMin
	 */
	public void setInventoryQtyFtAPIMin(final SessionContext ctx, final BaseStore item, final int value)
	{
		setInventoryQtyFtAPIMin( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.inventoryQtyFtAPIMin</code> attribute. 
	 * @param value the inventoryQtyFtAPIMin
	 */
	public void setInventoryQtyFtAPIMin(final BaseStore item, final int value)
	{
		setInventoryQtyFtAPIMin( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isAPIOrder</code> attribute.
	 * @return the isAPIOrder
	 */
	public Boolean isIsAPIOrder(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ISAPIORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isAPIOrder</code> attribute.
	 * @return the isAPIOrder
	 */
	public Boolean isIsAPIOrder(final AbstractOrder item)
	{
		return isIsAPIOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isAPIOrder</code> attribute. 
	 * @return the isAPIOrder
	 */
	public boolean isIsAPIOrderAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsAPIOrder( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isAPIOrder</code> attribute. 
	 * @return the isAPIOrder
	 */
	public boolean isIsAPIOrderAsPrimitive(final AbstractOrder item)
	{
		return isIsAPIOrderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isAPIOrder</code> attribute. 
	 * @param value the isAPIOrder
	 */
	public void setIsAPIOrder(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ISAPIORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isAPIOrder</code> attribute. 
	 * @param value the isAPIOrder
	 */
	public void setIsAPIOrder(final AbstractOrder item, final Boolean value)
	{
		setIsAPIOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isAPIOrder</code> attribute. 
	 * @param value the isAPIOrder
	 */
	public void setIsAPIOrder(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsAPIOrder( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isAPIOrder</code> attribute. 
	 * @param value the isAPIOrder
	 */
	public void setIsAPIOrder(final AbstractOrder item, final boolean value)
	{
		setIsAPIOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute.
	 * @return the isAvailableToNegotiatePrice
	 */
	public Boolean isIsAvailableToNegotiatePrice(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISAVAILABLETONEGOTIATEPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute.
	 * @return the isAvailableToNegotiatePrice
	 */
	public Boolean isIsAvailableToNegotiatePrice(final AbstractOrderEntry item)
	{
		return isIsAvailableToNegotiatePrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute. 
	 * @return the isAvailableToNegotiatePrice
	 */
	public boolean isIsAvailableToNegotiatePriceAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsAvailableToNegotiatePrice( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute. 
	 * @return the isAvailableToNegotiatePrice
	 */
	public boolean isIsAvailableToNegotiatePriceAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsAvailableToNegotiatePriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute. 
	 * @param value the isAvailableToNegotiatePrice
	 */
	public void setIsAvailableToNegotiatePrice(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISAVAILABLETONEGOTIATEPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute. 
	 * @param value the isAvailableToNegotiatePrice
	 */
	public void setIsAvailableToNegotiatePrice(final AbstractOrderEntry item, final Boolean value)
	{
		setIsAvailableToNegotiatePrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute. 
	 * @param value the isAvailableToNegotiatePrice
	 */
	public void setIsAvailableToNegotiatePrice(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsAvailableToNegotiatePrice( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isAvailableToNegotiatePrice</code> attribute. 
	 * @param value the isAvailableToNegotiatePrice
	 */
	public void setIsAvailableToNegotiatePrice(final AbstractOrderEntry item, final boolean value)
	{
		setIsAvailableToNegotiatePrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute.
	 * @return the isFirstAccesPortal
	 */
	public Boolean isIsFirstAccesPortal(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.ISFIRSTACCESPORTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute.
	 * @return the isFirstAccesPortal
	 */
	public Boolean isIsFirstAccesPortal(final B2BCustomer item)
	{
		return isIsFirstAccesPortal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute. 
	 * @return the isFirstAccesPortal
	 */
	public boolean isIsFirstAccesPortalAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsFirstAccesPortal( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute. 
	 * @return the isFirstAccesPortal
	 */
	public boolean isIsFirstAccesPortalAsPrimitive(final B2BCustomer item)
	{
		return isIsFirstAccesPortalAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute. 
	 * @param value the isFirstAccesPortal
	 */
	public void setIsFirstAccesPortal(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.ISFIRSTACCESPORTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute. 
	 * @param value the isFirstAccesPortal
	 */
	public void setIsFirstAccesPortal(final B2BCustomer item, final Boolean value)
	{
		setIsFirstAccesPortal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute. 
	 * @param value the isFirstAccesPortal
	 */
	public void setIsFirstAccesPortal(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsFirstAccesPortal( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstAccesPortal</code> attribute. 
	 * @param value the isFirstAccesPortal
	 */
	public void setIsFirstAccesPortal(final B2BCustomer item, final boolean value)
	{
		setIsFirstAccesPortal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHSSOrder</code> attribute.
	 * @return the isHSSOrder
	 */
	public Boolean isIsHSSOrder(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ISHSSORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHSSOrder</code> attribute.
	 * @return the isHSSOrder
	 */
	public Boolean isIsHSSOrder(final AbstractOrder item)
	{
		return isIsHSSOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHSSOrder</code> attribute. 
	 * @return the isHSSOrder
	 */
	public boolean isIsHSSOrderAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsHSSOrder( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHSSOrder</code> attribute. 
	 * @return the isHSSOrder
	 */
	public boolean isIsHSSOrderAsPrimitive(final AbstractOrder item)
	{
		return isIsHSSOrderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHSSOrder</code> attribute. 
	 * @param value the isHSSOrder
	 */
	public void setIsHSSOrder(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ISHSSORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHSSOrder</code> attribute. 
	 * @param value the isHSSOrder
	 */
	public void setIsHSSOrder(final AbstractOrder item, final Boolean value)
	{
		setIsHSSOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHSSOrder</code> attribute. 
	 * @param value the isHSSOrder
	 */
	public void setIsHSSOrder(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsHSSOrder( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHSSOrder</code> attribute. 
	 * @param value the isHSSOrder
	 */
	public void setIsHSSOrder(final AbstractOrder item, final boolean value)
	{
		setIsHSSOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute.
	 * @return the isLastOnTraspotation
	 */
	public Boolean isIsLastOnTraspotation(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISLASTONTRASPOTATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute.
	 * @return the isLastOnTraspotation
	 */
	public Boolean isIsLastOnTraspotation(final AbstractOrderEntry item)
	{
		return isIsLastOnTraspotation( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute. 
	 * @return the isLastOnTraspotation
	 */
	public boolean isIsLastOnTraspotationAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsLastOnTraspotation( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute. 
	 * @return the isLastOnTraspotation
	 */
	public boolean isIsLastOnTraspotationAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsLastOnTraspotationAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute. 
	 * @param value the isLastOnTraspotation
	 */
	public void setIsLastOnTraspotation(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISLASTONTRASPOTATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute. 
	 * @param value the isLastOnTraspotation
	 */
	public void setIsLastOnTraspotation(final AbstractOrderEntry item, final Boolean value)
	{
		setIsLastOnTraspotation( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute. 
	 * @param value the isLastOnTraspotation
	 */
	public void setIsLastOnTraspotation(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsLastOnTraspotation( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isLastOnTraspotation</code> attribute. 
	 * @param value the isLastOnTraspotation
	 */
	public void setIsLastOnTraspotation(final AbstractOrderEntry item, final boolean value)
	{
		setIsLastOnTraspotation( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute.
	 * @return the isQuantityInPieces
	 */
	public Boolean isIsQuantityInPieces(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISQUANTITYINPIECES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute.
	 * @return the isQuantityInPieces
	 */
	public Boolean isIsQuantityInPieces(final AbstractOrderEntry item)
	{
		return isIsQuantityInPieces( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute. 
	 * @return the isQuantityInPieces
	 */
	public boolean isIsQuantityInPiecesAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsQuantityInPieces( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute. 
	 * @return the isQuantityInPieces
	 */
	public boolean isIsQuantityInPiecesAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsQuantityInPiecesAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute. 
	 * @param value the isQuantityInPieces
	 */
	public void setIsQuantityInPieces(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISQUANTITYINPIECES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute. 
	 * @param value the isQuantityInPieces
	 */
	public void setIsQuantityInPieces(final AbstractOrderEntry item, final Boolean value)
	{
		setIsQuantityInPieces( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute. 
	 * @param value the isQuantityInPieces
	 */
	public void setIsQuantityInPieces(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsQuantityInPieces( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isQuantityInPieces</code> attribute. 
	 * @param value the isQuantityInPieces
	 */
	public void setIsQuantityInPieces(final AbstractOrderEntry item, final boolean value)
	{
		setIsQuantityInPieces( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute.
	 * @return the isTransportationGroupFull
	 */
	public Boolean isIsTransportationGroupFull(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISTRANSPORTATIONGROUPFULL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute.
	 * @return the isTransportationGroupFull
	 */
	public Boolean isIsTransportationGroupFull(final AbstractOrderEntry item)
	{
		return isIsTransportationGroupFull( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute. 
	 * @return the isTransportationGroupFull
	 */
	public boolean isIsTransportationGroupFullAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsTransportationGroupFull( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute. 
	 * @return the isTransportationGroupFull
	 */
	public boolean isIsTransportationGroupFullAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsTransportationGroupFullAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute. 
	 * @param value the isTransportationGroupFull
	 */
	public void setIsTransportationGroupFull(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ISTRANSPORTATIONGROUPFULL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute. 
	 * @param value the isTransportationGroupFull
	 */
	public void setIsTransportationGroupFull(final AbstractOrderEntry item, final Boolean value)
	{
		setIsTransportationGroupFull( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute. 
	 * @param value the isTransportationGroupFull
	 */
	public void setIsTransportationGroupFull(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsTransportationGroupFull( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isTransportationGroupFull</code> attribute. 
	 * @param value the isTransportationGroupFull
	 */
	public void setIsTransportationGroupFull(final AbstractOrderEntry item, final boolean value)
	{
		setIsTransportationGroupFull( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.language</code> attribute.
	 * @return the language
	 */
	public Language getLanguage(final SessionContext ctx, final B2BUnit item)
	{
		return (Language)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.LANGUAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.language</code> attribute.
	 * @return the language
	 */
	public Language getLanguage(final B2BUnit item)
	{
		return getLanguage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.language</code> attribute. 
	 * @param value the language
	 */
	public void setLanguage(final SessionContext ctx, final B2BUnit item, final Language value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.LANGUAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.language</code> attribute. 
	 * @param value the language
	 */
	public void setLanguage(final B2BUnit item, final Language value)
	{
		setLanguage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.locDisplayName</code> attribute.
	 * @return the locDisplayName
	 */
	public String getLocDisplayName(final SessionContext ctx, final PointOfService item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPointOfService.getLocDisplayName requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, ProlamsaCoreConstants.Attributes.PointOfService.LOCDISPLAYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.locDisplayName</code> attribute.
	 * @return the locDisplayName
	 */
	public String getLocDisplayName(final PointOfService item)
	{
		return getLocDisplayName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.locDisplayName</code> attribute. 
	 * @return the localized locDisplayName
	 */
	public Map<Language,String> getAllLocDisplayName(final SessionContext ctx, final PointOfService item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,ProlamsaCoreConstants.Attributes.PointOfService.LOCDISPLAYNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.locDisplayName</code> attribute. 
	 * @return the localized locDisplayName
	 */
	public Map<Language,String> getAllLocDisplayName(final PointOfService item)
	{
		return getAllLocDisplayName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.locDisplayName</code> attribute. 
	 * @param value the locDisplayName
	 */
	public void setLocDisplayName(final SessionContext ctx, final PointOfService item, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPointOfService.setLocDisplayName requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, ProlamsaCoreConstants.Attributes.PointOfService.LOCDISPLAYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.locDisplayName</code> attribute. 
	 * @param value the locDisplayName
	 */
	public void setLocDisplayName(final PointOfService item, final String value)
	{
		setLocDisplayName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.locDisplayName</code> attribute. 
	 * @param value the locDisplayName
	 */
	public void setAllLocDisplayName(final SessionContext ctx, final PointOfService item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,ProlamsaCoreConstants.Attributes.PointOfService.LOCDISPLAYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.locDisplayName</code> attribute. 
	 * @param value the locDisplayName
	 */
	public void setAllLocDisplayName(final PointOfService item, final Map<Language,String> value)
	{
		setAllLocDisplayName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxBundlesInventory</code> attribute.
	 * @return the maxBundlesInventory
	 */
	public Integer getMaxBundlesInventory(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.MAXBUNDLESINVENTORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxBundlesInventory</code> attribute.
	 * @return the maxBundlesInventory
	 */
	public Integer getMaxBundlesInventory(final BaseStore item)
	{
		return getMaxBundlesInventory( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxBundlesInventory</code> attribute. 
	 * @return the maxBundlesInventory
	 */
	public int getMaxBundlesInventoryAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getMaxBundlesInventory( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxBundlesInventory</code> attribute. 
	 * @return the maxBundlesInventory
	 */
	public int getMaxBundlesInventoryAsPrimitive(final BaseStore item)
	{
		return getMaxBundlesInventoryAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxBundlesInventory</code> attribute. 
	 * @param value the maxBundlesInventory
	 */
	public void setMaxBundlesInventory(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.MAXBUNDLESINVENTORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxBundlesInventory</code> attribute. 
	 * @param value the maxBundlesInventory
	 */
	public void setMaxBundlesInventory(final BaseStore item, final Integer value)
	{
		setMaxBundlesInventory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxBundlesInventory</code> attribute. 
	 * @param value the maxBundlesInventory
	 */
	public void setMaxBundlesInventory(final SessionContext ctx, final BaseStore item, final int value)
	{
		setMaxBundlesInventory( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxBundlesInventory</code> attribute. 
	 * @param value the maxBundlesInventory
	 */
	public void setMaxBundlesInventory(final BaseStore item, final int value)
	{
		setMaxBundlesInventory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxOrderLimit</code> attribute.
	 * @return the maxOrderLimit
	 */
	public Double getMaxOrderLimit(final SessionContext ctx, final BaseStore item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.MAXORDERLIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxOrderLimit</code> attribute.
	 * @return the maxOrderLimit
	 */
	public Double getMaxOrderLimit(final BaseStore item)
	{
		return getMaxOrderLimit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxOrderLimit</code> attribute. 
	 * @return the maxOrderLimit
	 */
	public double getMaxOrderLimitAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Double value = getMaxOrderLimit( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.maxOrderLimit</code> attribute. 
	 * @return the maxOrderLimit
	 */
	public double getMaxOrderLimitAsPrimitive(final BaseStore item)
	{
		return getMaxOrderLimitAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxOrderLimit</code> attribute. 
	 * @param value the maxOrderLimit
	 */
	public void setMaxOrderLimit(final SessionContext ctx, final BaseStore item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.MAXORDERLIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxOrderLimit</code> attribute. 
	 * @param value the maxOrderLimit
	 */
	public void setMaxOrderLimit(final BaseStore item, final Double value)
	{
		setMaxOrderLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxOrderLimit</code> attribute. 
	 * @param value the maxOrderLimit
	 */
	public void setMaxOrderLimit(final SessionContext ctx, final BaseStore item, final double value)
	{
		setMaxOrderLimit( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.maxOrderLimit</code> attribute. 
	 * @param value the maxOrderLimit
	 */
	public void setMaxOrderLimit(final BaseStore item, final double value)
	{
		setMaxOrderLimit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Address.NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.name</code> attribute.
	 * @return the name
	 */
	public String getName(final Address item)
	{
		return getName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Address.NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final Address item, final String value)
	{
		setName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSLinkComponent.navigationLinkFunction</code> attribute.
	 * @return the navigationLinkFunction
	 */
	public String getNavigationLinkFunction(final SessionContext ctx, final CMSLinkComponent item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.CMSLinkComponent.NAVIGATIONLINKFUNCTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSLinkComponent.navigationLinkFunction</code> attribute.
	 * @return the navigationLinkFunction
	 */
	public String getNavigationLinkFunction(final CMSLinkComponent item)
	{
		return getNavigationLinkFunction( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSLinkComponent.navigationLinkFunction</code> attribute. 
	 * @param value the navigationLinkFunction
	 */
	public void setNavigationLinkFunction(final SessionContext ctx, final CMSLinkComponent item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.CMSLinkComponent.NAVIGATIONLINKFUNCTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSLinkComponent.navigationLinkFunction</code> attribute. 
	 * @param value the navigationLinkFunction
	 */
	public void setNavigationLinkFunction(final CMSLinkComponent item, final String value)
	{
		setNavigationLinkFunction( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute.
	 * @return the negotiablePrice
	 */
	public Double getNegotiablePrice(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.NEGOTIABLEPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute.
	 * @return the negotiablePrice
	 */
	public Double getNegotiablePrice(final AbstractOrderEntry item)
	{
		return getNegotiablePrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute. 
	 * @return the negotiablePrice
	 */
	public double getNegotiablePriceAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getNegotiablePrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute. 
	 * @return the negotiablePrice
	 */
	public double getNegotiablePriceAsPrimitive(final AbstractOrderEntry item)
	{
		return getNegotiablePriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute. 
	 * @param value the negotiablePrice
	 */
	public void setNegotiablePrice(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.NEGOTIABLEPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute. 
	 * @param value the negotiablePrice
	 */
	public void setNegotiablePrice(final AbstractOrderEntry item, final Double value)
	{
		setNegotiablePrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute. 
	 * @param value the negotiablePrice
	 */
	public void setNegotiablePrice(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setNegotiablePrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.negotiablePrice</code> attribute. 
	 * @param value the negotiablePrice
	 */
	public void setNegotiablePrice(final AbstractOrderEntry item, final double value)
	{
		setNegotiablePrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute.
	 * @return the netPriceWOTaxes
	 */
	public Double getNetPriceWOTaxes(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.NETPRICEWOTAXES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute.
	 * @return the netPriceWOTaxes
	 */
	public Double getNetPriceWOTaxes(final AbstractOrderEntry item)
	{
		return getNetPriceWOTaxes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute. 
	 * @return the netPriceWOTaxes
	 */
	public double getNetPriceWOTaxesAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getNetPriceWOTaxes( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute. 
	 * @return the netPriceWOTaxes
	 */
	public double getNetPriceWOTaxesAsPrimitive(final AbstractOrderEntry item)
	{
		return getNetPriceWOTaxesAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute. 
	 * @param value the netPriceWOTaxes
	 */
	public void setNetPriceWOTaxes(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.NETPRICEWOTAXES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute. 
	 * @param value the netPriceWOTaxes
	 */
	public void setNetPriceWOTaxes(final AbstractOrderEntry item, final Double value)
	{
		setNetPriceWOTaxes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute. 
	 * @param value the netPriceWOTaxes
	 */
	public void setNetPriceWOTaxes(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setNetPriceWOTaxes( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.netPriceWOTaxes</code> attribute. 
	 * @param value the netPriceWOTaxes
	 */
	public void setNetPriceWOTaxes(final AbstractOrderEntry item, final double value)
	{
		setNetPriceWOTaxes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.notices</code> attribute.
	 * @return the notices
	 */
	public List<Notice> getNotices(final SessionContext ctx, final BaseStore item)
	{
		List<Notice> coll = (List<Notice>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.NOTICES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.notices</code> attribute.
	 * @return the notices
	 */
	public List<Notice> getNotices(final BaseStore item)
	{
		return getNotices( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.notices</code> attribute. 
	 * @param value the notices
	 */
	public void setNotices(final SessionContext ctx, final BaseStore item, final List<Notice> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.NOTICES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.notices</code> attribute. 
	 * @param value the notices
	 */
	public void setNotices(final BaseStore item, final List<Notice> value)
	{
		setNotices( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.orderCode</code> attribute.
	 * @return the orderCode
	 */
	public String getOrderCode(final SessionContext ctx, final Wishlist2 item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Wishlist2.ORDERCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.orderCode</code> attribute.
	 * @return the orderCode
	 */
	public String getOrderCode(final Wishlist2 item)
	{
		return getOrderCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.orderCode</code> attribute. 
	 * @param value the orderCode
	 */
	public void setOrderCode(final SessionContext ctx, final Wishlist2 item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Wishlist2.ORDERCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.orderCode</code> attribute. 
	 * @param value the orderCode
	 */
	public void setOrderCode(final Wishlist2 item, final String value)
	{
		setOrderCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.originalQuote</code> attribute.
	 * @return the originalQuote
	 */
	public AbstractOrder getOriginalQuote(final SessionContext ctx, final AbstractOrder item)
	{
		return (AbstractOrder)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ORIGINALQUOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.originalQuote</code> attribute.
	 * @return the originalQuote
	 */
	public AbstractOrder getOriginalQuote(final AbstractOrder item)
	{
		return getOriginalQuote( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.originalQuote</code> attribute. 
	 * @param value the originalQuote
	 */
	public void setOriginalQuote(final SessionContext ctx, final AbstractOrder item, final AbstractOrder value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ORIGINALQUOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.originalQuote</code> attribute. 
	 * @param value the originalQuote
	 */
	public void setOriginalQuote(final AbstractOrder item, final AbstractOrder value)
	{
		setOriginalQuote( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.originalUserOrder</code> attribute.
	 * @return the originalUserOrder
	 */
	public String getOriginalUserOrder(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ORIGINALUSERORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.originalUserOrder</code> attribute.
	 * @return the originalUserOrder
	 */
	public String getOriginalUserOrder(final AbstractOrder item)
	{
		return getOriginalUserOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.originalUserOrder</code> attribute. 
	 * @param value the originalUserOrder
	 */
	public void setOriginalUserOrder(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.ORIGINALUSERORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.originalUserOrder</code> attribute. 
	 * @param value the originalUserOrder
	 */
	public void setOriginalUserOrder(final AbstractOrder item, final String value)
	{
		setOriginalUserOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute.
	 * @return the placeOrderButtonEnabled
	 */
	public Boolean isPlaceOrderButtonEnabled(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.PLACEORDERBUTTONENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute.
	 * @return the placeOrderButtonEnabled
	 */
	public Boolean isPlaceOrderButtonEnabled(final BaseStore item)
	{
		return isPlaceOrderButtonEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute. 
	 * @return the placeOrderButtonEnabled
	 */
	public boolean isPlaceOrderButtonEnabledAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isPlaceOrderButtonEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute. 
	 * @return the placeOrderButtonEnabled
	 */
	public boolean isPlaceOrderButtonEnabledAsPrimitive(final BaseStore item)
	{
		return isPlaceOrderButtonEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute. 
	 * @param value the placeOrderButtonEnabled
	 */
	public void setPlaceOrderButtonEnabled(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.PLACEORDERBUTTONENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute. 
	 * @param value the placeOrderButtonEnabled
	 */
	public void setPlaceOrderButtonEnabled(final BaseStore item, final Boolean value)
	{
		setPlaceOrderButtonEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute. 
	 * @param value the placeOrderButtonEnabled
	 */
	public void setPlaceOrderButtonEnabled(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setPlaceOrderButtonEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.placeOrderButtonEnabled</code> attribute. 
	 * @param value the placeOrderButtonEnabled
	 */
	public void setPlaceOrderButtonEnabled(final BaseStore item, final boolean value)
	{
		setPlaceOrderButtonEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.pointOfServices</code> attribute.
	 * @return the pointOfServices
	 */
	public List<PointOfService> getPointOfServices(final SessionContext ctx, final BaseStore item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true)
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.pointOfServices</code> attribute.
	 * @return the pointOfServices
	 */
	public List<PointOfService> getPointOfServices(final BaseStore item)
	{
		return getPointOfServices( getSession().getSessionContext(), item );
	}
	
	public long getPointOfServicesCount(final SessionContext ctx, final BaseStore item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null
		);
	}
	
	public long getPointOfServicesCount(final BaseStore item)
	{
		return getPointOfServicesCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.pointOfServices</code> attribute. 
	 * @param value the pointOfServices
	 */
	public void setPointOfServices(final SessionContext ctx, final BaseStore item, final List<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			value,
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(BASESTORE2POINTOFSERVS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.pointOfServices</code> attribute. 
	 * @param value the pointOfServices
	 */
	public void setPointOfServices(final BaseStore item, final List<PointOfService> value)
	{
		setPointOfServices( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pointOfServices. 
	 * @param value the item to add to pointOfServices
	 */
	public void addToPointOfServices(final SessionContext ctx, final BaseStore item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(BASESTORE2POINTOFSERVS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pointOfServices. 
	 * @param value the item to add to pointOfServices
	 */
	public void addToPointOfServices(final BaseStore item, final PointOfService value)
	{
		addToPointOfServices( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pointOfServices. 
	 * @param value the item to remove from pointOfServices
	 */
	public void removeFromPointOfServices(final SessionContext ctx, final BaseStore item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			ProlamsaCoreConstants.Relations.BASESTORE2POINTOFSERVS,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_SRC_ORDERED, true),
			Utilities.getRelationOrderingOverride(BASESTORE2POINTOFSERVS_TGT_ORDERED, true),
			Utilities.getMarkModifiedOverride(BASESTORE2POINTOFSERVS_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pointOfServices. 
	 * @param value the item to remove from pointOfServices
	 */
	public void removeFromPointOfServices(final BaseStore item, final PointOfService value)
	{
		removeFromPointOfServices( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceDecimalsNumber</code> attribute.
	 * @return the priceDecimalsNumber
	 */
	public Integer getPriceDecimalsNumber(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.PRICEDECIMALSNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceDecimalsNumber</code> attribute.
	 * @return the priceDecimalsNumber
	 */
	public Integer getPriceDecimalsNumber(final BaseStore item)
	{
		return getPriceDecimalsNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceDecimalsNumber</code> attribute. 
	 * @return the priceDecimalsNumber
	 */
	public int getPriceDecimalsNumberAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getPriceDecimalsNumber( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceDecimalsNumber</code> attribute. 
	 * @return the priceDecimalsNumber
	 */
	public int getPriceDecimalsNumberAsPrimitive(final BaseStore item)
	{
		return getPriceDecimalsNumberAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceDecimalsNumber</code> attribute. 
	 * @param value the priceDecimalsNumber
	 */
	public void setPriceDecimalsNumber(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.PRICEDECIMALSNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceDecimalsNumber</code> attribute. 
	 * @param value the priceDecimalsNumber
	 */
	public void setPriceDecimalsNumber(final BaseStore item, final Integer value)
	{
		setPriceDecimalsNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceDecimalsNumber</code> attribute. 
	 * @param value the priceDecimalsNumber
	 */
	public void setPriceDecimalsNumber(final SessionContext ctx, final BaseStore item, final int value)
	{
		setPriceDecimalsNumber( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceDecimalsNumber</code> attribute. 
	 * @param value the priceDecimalsNumber
	 */
	public void setPriceDecimalsNumber(final BaseStore item, final int value)
	{
		setPriceDecimalsNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceNegotiationEnabled</code> attribute.
	 * @return the priceNegotiationEnabled
	 */
	public Boolean isPriceNegotiationEnabled(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.PRICENEGOTIATIONENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceNegotiationEnabled</code> attribute.
	 * @return the priceNegotiationEnabled
	 */
	public Boolean isPriceNegotiationEnabled(final BaseStore item)
	{
		return isPriceNegotiationEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceNegotiationEnabled</code> attribute. 
	 * @return the priceNegotiationEnabled
	 */
	public boolean isPriceNegotiationEnabledAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isPriceNegotiationEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.priceNegotiationEnabled</code> attribute. 
	 * @return the priceNegotiationEnabled
	 */
	public boolean isPriceNegotiationEnabledAsPrimitive(final BaseStore item)
	{
		return isPriceNegotiationEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceNegotiationEnabled</code> attribute. 
	 * @param value the priceNegotiationEnabled
	 */
	public void setPriceNegotiationEnabled(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.PRICENEGOTIATIONENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceNegotiationEnabled</code> attribute. 
	 * @param value the priceNegotiationEnabled
	 */
	public void setPriceNegotiationEnabled(final BaseStore item, final Boolean value)
	{
		setPriceNegotiationEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceNegotiationEnabled</code> attribute. 
	 * @param value the priceNegotiationEnabled
	 */
	public void setPriceNegotiationEnabled(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setPriceNegotiationEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.priceNegotiationEnabled</code> attribute. 
	 * @param value the priceNegotiationEnabled
	 */
	public void setPriceNegotiationEnabled(final BaseStore item, final boolean value)
	{
		setPriceNegotiationEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute.
	 * @return the pricePerFeet
	 */
	public Double getPricePerFeet(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.PRICEPERFEET);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute.
	 * @return the pricePerFeet
	 */
	public Double getPricePerFeet(final AbstractOrderEntry item)
	{
		return getPricePerFeet( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute. 
	 * @return the pricePerFeet
	 */
	public double getPricePerFeetAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getPricePerFeet( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute. 
	 * @return the pricePerFeet
	 */
	public double getPricePerFeetAsPrimitive(final AbstractOrderEntry item)
	{
		return getPricePerFeetAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute. 
	 * @param value the pricePerFeet
	 */
	public void setPricePerFeet(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.PRICEPERFEET,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute. 
	 * @param value the pricePerFeet
	 */
	public void setPricePerFeet(final AbstractOrderEntry item, final Double value)
	{
		setPricePerFeet( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute. 
	 * @param value the pricePerFeet
	 */
	public void setPricePerFeet(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setPricePerFeet( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerFeet</code> attribute. 
	 * @param value the pricePerFeet
	 */
	public void setPricePerFeet(final AbstractOrderEntry item, final double value)
	{
		setPricePerFeet( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerPc</code> attribute.
	 * @return the pricePerPc
	 */
	public Double getPricePerPc(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.PRICEPERPC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerPc</code> attribute.
	 * @return the pricePerPc
	 */
	public Double getPricePerPc(final AbstractOrderEntry item)
	{
		return getPricePerPc( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerPc</code> attribute. 
	 * @return the pricePerPc
	 */
	public double getPricePerPcAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getPricePerPc( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerPc</code> attribute. 
	 * @return the pricePerPc
	 */
	public double getPricePerPcAsPrimitive(final AbstractOrderEntry item)
	{
		return getPricePerPcAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerPc</code> attribute. 
	 * @param value the pricePerPc
	 */
	public void setPricePerPc(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.PRICEPERPC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerPc</code> attribute. 
	 * @param value the pricePerPc
	 */
	public void setPricePerPc(final AbstractOrderEntry item, final Double value)
	{
		setPricePerPc( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerPc</code> attribute. 
	 * @param value the pricePerPc
	 */
	public void setPricePerPc(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setPricePerPc( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerPc</code> attribute. 
	 * @param value the pricePerPc
	 */
	public void setPricePerPc(final AbstractOrderEntry item, final double value)
	{
		setPricePerPc( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerTon</code> attribute.
	 * @return the pricePerTon
	 */
	public Double getPricePerTon(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.PRICEPERTON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerTon</code> attribute.
	 * @return the pricePerTon
	 */
	public Double getPricePerTon(final AbstractOrderEntry item)
	{
		return getPricePerTon( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerTon</code> attribute. 
	 * @return the pricePerTon
	 */
	public double getPricePerTonAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getPricePerTon( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.pricePerTon</code> attribute. 
	 * @return the pricePerTon
	 */
	public double getPricePerTonAsPrimitive(final AbstractOrderEntry item)
	{
		return getPricePerTonAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerTon</code> attribute. 
	 * @param value the pricePerTon
	 */
	public void setPricePerTon(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.PRICEPERTON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerTon</code> attribute. 
	 * @param value the pricePerTon
	 */
	public void setPricePerTon(final AbstractOrderEntry item, final Double value)
	{
		setPricePerTon( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerTon</code> attribute. 
	 * @param value the pricePerTon
	 */
	public void setPricePerTon(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setPricePerTon( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.pricePerTon</code> attribute. 
	 * @param value the pricePerTon
	 */
	public void setPricePerTon(final AbstractOrderEntry item, final double value)
	{
		setPricePerTon( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.productSpecsEmail</code> attribute.
	 * @return the productSpecsEmail
	 */
	public String getProductSpecsEmail(final SessionContext ctx, final BaseStore item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.PRODUCTSPECSEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.productSpecsEmail</code> attribute.
	 * @return the productSpecsEmail
	 */
	public String getProductSpecsEmail(final BaseStore item)
	{
		return getProductSpecsEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.productSpecsEmail</code> attribute. 
	 * @param value the productSpecsEmail
	 */
	public void setProductSpecsEmail(final SessionContext ctx, final BaseStore item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.PRODUCTSPECSEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.productSpecsEmail</code> attribute. 
	 * @param value the productSpecsEmail
	 */
	public void setProductSpecsEmail(final BaseStore item, final String value)
	{
		setProductSpecsEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.qualityEmail</code> attribute.
	 * @return the qualityEmail
	 */
	public String getQualityEmail(final SessionContext ctx, final BaseStore item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUALITYEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.qualityEmail</code> attribute.
	 * @return the qualityEmail
	 */
	public String getQualityEmail(final BaseStore item)
	{
		return getQualityEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.qualityEmail</code> attribute. 
	 * @param value the qualityEmail
	 */
	public void setQualityEmail(final SessionContext ctx, final BaseStore item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUALITYEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.qualityEmail</code> attribute. 
	 * @param value the qualityEmail
	 */
	public void setQualityEmail(final BaseStore item, final String value)
	{
		setQualityEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute.
	 * @return the quickCheckoutConfirmationEnabled
	 */
	public Boolean isQuickCheckoutConfirmationEnabled(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUICKCHECKOUTCONFIRMATIONENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute.
	 * @return the quickCheckoutConfirmationEnabled
	 */
	public Boolean isQuickCheckoutConfirmationEnabled(final BaseStore item)
	{
		return isQuickCheckoutConfirmationEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute. 
	 * @return the quickCheckoutConfirmationEnabled
	 */
	public boolean isQuickCheckoutConfirmationEnabledAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isQuickCheckoutConfirmationEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute. 
	 * @return the quickCheckoutConfirmationEnabled
	 */
	public boolean isQuickCheckoutConfirmationEnabledAsPrimitive(final BaseStore item)
	{
		return isQuickCheckoutConfirmationEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute. 
	 * @param value the quickCheckoutConfirmationEnabled
	 */
	public void setQuickCheckoutConfirmationEnabled(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUICKCHECKOUTCONFIRMATIONENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute. 
	 * @param value the quickCheckoutConfirmationEnabled
	 */
	public void setQuickCheckoutConfirmationEnabled(final BaseStore item, final Boolean value)
	{
		setQuickCheckoutConfirmationEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute. 
	 * @param value the quickCheckoutConfirmationEnabled
	 */
	public void setQuickCheckoutConfirmationEnabled(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setQuickCheckoutConfirmationEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quickCheckoutConfirmationEnabled</code> attribute. 
	 * @param value the quickCheckoutConfirmationEnabled
	 */
	public void setQuickCheckoutConfirmationEnabled(final BaseStore item, final boolean value)
	{
		setQuickCheckoutConfirmationEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute.
	 * @return the quoteNegotiationDiscountMax
	 */
	public Double getQuoteNegotiationDiscountMax(final SessionContext ctx, final BaseStore item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUOTENEGOTIATIONDISCOUNTMAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute.
	 * @return the quoteNegotiationDiscountMax
	 */
	public Double getQuoteNegotiationDiscountMax(final BaseStore item)
	{
		return getQuoteNegotiationDiscountMax( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute. 
	 * @return the quoteNegotiationDiscountMax
	 */
	public double getQuoteNegotiationDiscountMaxAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Double value = getQuoteNegotiationDiscountMax( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute. 
	 * @return the quoteNegotiationDiscountMax
	 */
	public double getQuoteNegotiationDiscountMaxAsPrimitive(final BaseStore item)
	{
		return getQuoteNegotiationDiscountMaxAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute. 
	 * @param value the quoteNegotiationDiscountMax
	 */
	public void setQuoteNegotiationDiscountMax(final SessionContext ctx, final BaseStore item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUOTENEGOTIATIONDISCOUNTMAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute. 
	 * @param value the quoteNegotiationDiscountMax
	 */
	public void setQuoteNegotiationDiscountMax(final BaseStore item, final Double value)
	{
		setQuoteNegotiationDiscountMax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute. 
	 * @param value the quoteNegotiationDiscountMax
	 */
	public void setQuoteNegotiationDiscountMax(final SessionContext ctx, final BaseStore item, final double value)
	{
		setQuoteNegotiationDiscountMax( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMax</code> attribute. 
	 * @param value the quoteNegotiationDiscountMax
	 */
	public void setQuoteNegotiationDiscountMax(final BaseStore item, final double value)
	{
		setQuoteNegotiationDiscountMax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute.
	 * @return the quoteNegotiationDiscountMin
	 */
	public Double getQuoteNegotiationDiscountMin(final SessionContext ctx, final BaseStore item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUOTENEGOTIATIONDISCOUNTMIN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute.
	 * @return the quoteNegotiationDiscountMin
	 */
	public Double getQuoteNegotiationDiscountMin(final BaseStore item)
	{
		return getQuoteNegotiationDiscountMin( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute. 
	 * @return the quoteNegotiationDiscountMin
	 */
	public double getQuoteNegotiationDiscountMinAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Double value = getQuoteNegotiationDiscountMin( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute. 
	 * @return the quoteNegotiationDiscountMin
	 */
	public double getQuoteNegotiationDiscountMinAsPrimitive(final BaseStore item)
	{
		return getQuoteNegotiationDiscountMinAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute. 
	 * @param value the quoteNegotiationDiscountMin
	 */
	public void setQuoteNegotiationDiscountMin(final SessionContext ctx, final BaseStore item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.QUOTENEGOTIATIONDISCOUNTMIN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute. 
	 * @param value the quoteNegotiationDiscountMin
	 */
	public void setQuoteNegotiationDiscountMin(final BaseStore item, final Double value)
	{
		setQuoteNegotiationDiscountMin( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute. 
	 * @param value the quoteNegotiationDiscountMin
	 */
	public void setQuoteNegotiationDiscountMin(final SessionContext ctx, final BaseStore item, final double value)
	{
		setQuoteNegotiationDiscountMin( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.quoteNegotiationDiscountMin</code> attribute. 
	 * @param value the quoteNegotiationDiscountMin
	 */
	public void setQuoteNegotiationDiscountMin(final BaseStore item, final double value)
	{
		setQuoteNegotiationDiscountMin( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.readyToShip</code> attribute.
	 * @return the readyToShip
	 */
	public Date getReadyToShip(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Date)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.READYTOSHIP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.readyToShip</code> attribute.
	 * @return the readyToShip
	 */
	public Date getReadyToShip(final AbstractOrderEntry item)
	{
		return getReadyToShip( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.readyToShip</code> attribute. 
	 * @param value the readyToShip
	 */
	public void setReadyToShip(final SessionContext ctx, final AbstractOrderEntry item, final Date value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.READYTOSHIP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.readyToShip</code> attribute. 
	 * @param value the readyToShip
	 */
	public void setReadyToShip(final AbstractOrderEntry item, final Date value)
	{
		setReadyToShip( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.requestedDeliveryDate</code> attribute.
	 * @return the requestedDeliveryDate
	 */
	public Date getRequestedDeliveryDate(final SessionContext ctx, final AbstractOrder item)
	{
		return (Date)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.REQUESTEDDELIVERYDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.requestedDeliveryDate</code> attribute.
	 * @return the requestedDeliveryDate
	 */
	public Date getRequestedDeliveryDate(final AbstractOrder item)
	{
		return getRequestedDeliveryDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.requestedDeliveryDate</code> attribute. 
	 * @param value the requestedDeliveryDate
	 */
	public void setRequestedDeliveryDate(final SessionContext ctx, final AbstractOrder item, final Date value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.REQUESTEDDELIVERYDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.requestedDeliveryDate</code> attribute. 
	 * @param value the requestedDeliveryDate
	 */
	public void setRequestedDeliveryDate(final AbstractOrder item, final Date value)
	{
		setRequestedDeliveryDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute.
	 * @return the rollingQtyFtAPIMax
	 */
	public Integer getRollingQtyFtAPIMax(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ROLLINGQTYFTAPIMAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute.
	 * @return the rollingQtyFtAPIMax
	 */
	public Integer getRollingQtyFtAPIMax(final BaseStore item)
	{
		return getRollingQtyFtAPIMax( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute. 
	 * @return the rollingQtyFtAPIMax
	 */
	public int getRollingQtyFtAPIMaxAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getRollingQtyFtAPIMax( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute. 
	 * @return the rollingQtyFtAPIMax
	 */
	public int getRollingQtyFtAPIMaxAsPrimitive(final BaseStore item)
	{
		return getRollingQtyFtAPIMaxAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute. 
	 * @param value the rollingQtyFtAPIMax
	 */
	public void setRollingQtyFtAPIMax(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ROLLINGQTYFTAPIMAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute. 
	 * @param value the rollingQtyFtAPIMax
	 */
	public void setRollingQtyFtAPIMax(final BaseStore item, final Integer value)
	{
		setRollingQtyFtAPIMax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute. 
	 * @param value the rollingQtyFtAPIMax
	 */
	public void setRollingQtyFtAPIMax(final SessionContext ctx, final BaseStore item, final int value)
	{
		setRollingQtyFtAPIMax( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMax</code> attribute. 
	 * @param value the rollingQtyFtAPIMax
	 */
	public void setRollingQtyFtAPIMax(final BaseStore item, final int value)
	{
		setRollingQtyFtAPIMax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute.
	 * @return the rollingQtyFtAPIMin
	 */
	public Integer getRollingQtyFtAPIMin(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.ROLLINGQTYFTAPIMIN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute.
	 * @return the rollingQtyFtAPIMin
	 */
	public Integer getRollingQtyFtAPIMin(final BaseStore item)
	{
		return getRollingQtyFtAPIMin( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute. 
	 * @return the rollingQtyFtAPIMin
	 */
	public int getRollingQtyFtAPIMinAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getRollingQtyFtAPIMin( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute. 
	 * @return the rollingQtyFtAPIMin
	 */
	public int getRollingQtyFtAPIMinAsPrimitive(final BaseStore item)
	{
		return getRollingQtyFtAPIMinAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute. 
	 * @param value the rollingQtyFtAPIMin
	 */
	public void setRollingQtyFtAPIMin(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.ROLLINGQTYFTAPIMIN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute. 
	 * @param value the rollingQtyFtAPIMin
	 */
	public void setRollingQtyFtAPIMin(final BaseStore item, final Integer value)
	{
		setRollingQtyFtAPIMin( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute. 
	 * @param value the rollingQtyFtAPIMin
	 */
	public void setRollingQtyFtAPIMin(final SessionContext ctx, final BaseStore item, final int value)
	{
		setRollingQtyFtAPIMin( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.rollingQtyFtAPIMin</code> attribute. 
	 * @param value the rollingQtyFtAPIMin
	 */
	public void setRollingQtyFtAPIMin(final BaseStore item, final int value)
	{
		setRollingQtyFtAPIMin( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.rollingScheduleWeek</code> attribute.
	 * @return the rollingScheduleWeek
	 */
	public Date getRollingScheduleWeek(final SessionContext ctx, final Wishlist2Entry item)
	{
		return (Date)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Wishlist2Entry.ROLLINGSCHEDULEWEEK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.rollingScheduleWeek</code> attribute.
	 * @return the rollingScheduleWeek
	 */
	public Date getRollingScheduleWeek(final Wishlist2Entry item)
	{
		return getRollingScheduleWeek( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.rollingScheduleWeek</code> attribute. 
	 * @param value the rollingScheduleWeek
	 */
	public void setRollingScheduleWeek(final SessionContext ctx, final Wishlist2Entry item, final Date value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Wishlist2Entry.ROLLINGSCHEDULEWEEK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.rollingScheduleWeek</code> attribute. 
	 * @param value the rollingScheduleWeek
	 */
	public void setRollingScheduleWeek(final Wishlist2Entry item, final Date value)
	{
		setRollingScheduleWeek( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.rollingScheduleWeek</code> attribute.
	 * @return the rollingScheduleWeek
	 */
	public Date getRollingScheduleWeek(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Date)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ROLLINGSCHEDULEWEEK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.rollingScheduleWeek</code> attribute.
	 * @return the rollingScheduleWeek
	 */
	public Date getRollingScheduleWeek(final AbstractOrderEntry item)
	{
		return getRollingScheduleWeek( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.rollingScheduleWeek</code> attribute. 
	 * @param value the rollingScheduleWeek
	 */
	public void setRollingScheduleWeek(final SessionContext ctx, final AbstractOrderEntry item, final Date value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.ROLLINGSCHEDULEWEEK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.rollingScheduleWeek</code> attribute. 
	 * @param value the rollingScheduleWeek
	 */
	public void setRollingScheduleWeek(final AbstractOrderEntry item, final Date value)
	{
		setRollingScheduleWeek( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesArea</code> attribute.
	 * @return the salesArea
	 */
	public String getSalesArea(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESAREA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesArea</code> attribute.
	 * @return the salesArea
	 */
	public String getSalesArea(final B2BUnit item)
	{
		return getSalesArea( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesArea</code> attribute. 
	 * @param value the salesArea
	 */
	public void setSalesArea(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESAREA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesArea</code> attribute. 
	 * @param value the salesArea
	 */
	public void setSalesArea(final B2BUnit item, final String value)
	{
		setSalesArea( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesAreaForStore</code> attribute.
	 * @return the salesAreaForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getSalesAreaForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESAREAFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesAreaForStore</code> attribute.
	 * @return the salesAreaForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getSalesAreaForStore(final B2BUnit item)
	{
		return getSalesAreaForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesAreaForStore</code> attribute. 
	 * @param value the salesAreaForStore
	 */
	public void setSalesAreaForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESAREAFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesAreaForStore</code> attribute. 
	 * @param value the salesAreaForStore
	 */
	public void setSalesAreaForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setSalesAreaForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.salesAreaManagers</code> attribute.
	 * @return the salesAreaManagers
	 */
	public List<SalesArea> getSalesAreaManagers(final SessionContext ctx, final BaseStore item)
	{
		List<SalesArea> coll = (List<SalesArea>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SALESAREAMANAGERS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.salesAreaManagers</code> attribute.
	 * @return the salesAreaManagers
	 */
	public List<SalesArea> getSalesAreaManagers(final BaseStore item)
	{
		return getSalesAreaManagers( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.salesAreaManagers</code> attribute. 
	 * @param value the salesAreaManagers
	 */
	public void setSalesAreaManagers(final SessionContext ctx, final BaseStore item, final List<SalesArea> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SALESAREAMANAGERS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.salesAreaManagers</code> attribute. 
	 * @param value the salesAreaManagers
	 */
	public void setSalesAreaManagers(final BaseStore item, final List<SalesArea> value)
	{
		setSalesAreaManagers( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesAreaWildCardVisibilityForStore</code> attribute.
	 * @return the salesAreaWildCardVisibilityForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getSalesAreaWildCardVisibilityForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESAREAWILDCARDVISIBILITYFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesAreaWildCardVisibilityForStore</code> attribute.
	 * @return the salesAreaWildCardVisibilityForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getSalesAreaWildCardVisibilityForStore(final B2BUnit item)
	{
		return getSalesAreaWildCardVisibilityForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesAreaWildCardVisibilityForStore</code> attribute. 
	 * @param value the salesAreaWildCardVisibilityForStore
	 */
	public void setSalesAreaWildCardVisibilityForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESAREAWILDCARDVISIBILITYFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesAreaWildCardVisibilityForStore</code> attribute. 
	 * @param value the salesAreaWildCardVisibilityForStore
	 */
	public void setSalesAreaWildCardVisibilityForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setSalesAreaWildCardVisibilityForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.salesDepartmentEmail</code> attribute.
	 * @return the salesDepartmentEmail
	 */
	public String getSalesDepartmentEmail(final SessionContext ctx, final BaseStore item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SALESDEPARTMENTEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.salesDepartmentEmail</code> attribute.
	 * @return the salesDepartmentEmail
	 */
	public String getSalesDepartmentEmail(final BaseStore item)
	{
		return getSalesDepartmentEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.salesDepartmentEmail</code> attribute. 
	 * @param value the salesDepartmentEmail
	 */
	public void setSalesDepartmentEmail(final SessionContext ctx, final BaseStore item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SALESDEPARTMENTEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.salesDepartmentEmail</code> attribute. 
	 * @param value the salesDepartmentEmail
	 */
	public void setSalesDepartmentEmail(final BaseStore item, final String value)
	{
		setSalesDepartmentEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.salesRep</code> attribute.
	 * @return the salesRep
	 */
	public String getSalesRep(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.SALESREP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.salesRep</code> attribute.
	 * @return the salesRep
	 */
	public String getSalesRep(final B2BCustomer item)
	{
		return getSalesRep( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.salesRep</code> attribute. 
	 * @param value the salesRep
	 */
	public void setSalesRep(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.SALESREP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.salesRep</code> attribute. 
	 * @param value the salesRep
	 */
	public void setSalesRep(final B2BCustomer item, final String value)
	{
		setSalesRep( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesRep</code> attribute.
	 * @return the salesRep
	 */
	public String getSalesRep(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESREP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesRep</code> attribute.
	 * @return the salesRep
	 */
	public String getSalesRep(final B2BUnit item)
	{
		return getSalesRep( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesRep</code> attribute. 
	 * @param value the salesRep
	 */
	public void setSalesRep(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESREP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesRep</code> attribute. 
	 * @param value the salesRep
	 */
	public void setSalesRep(final B2BUnit item, final String value)
	{
		setSalesRep( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.salesRepForStore</code> attribute.
	 * @return the salesRepForStore
	 */
	public List<NeorisB2BCustomerSettingByStore> getSalesRepForStore(final SessionContext ctx, final B2BCustomer item)
	{
		List<NeorisB2BCustomerSettingByStore> coll = (List<NeorisB2BCustomerSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.SALESREPFORSTORE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.salesRepForStore</code> attribute.
	 * @return the salesRepForStore
	 */
	public List<NeorisB2BCustomerSettingByStore> getSalesRepForStore(final B2BCustomer item)
	{
		return getSalesRepForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.salesRepForStore</code> attribute. 
	 * @param value the salesRepForStore
	 */
	public void setSalesRepForStore(final SessionContext ctx, final B2BCustomer item, final List<NeorisB2BCustomerSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.SALESREPFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.salesRepForStore</code> attribute. 
	 * @param value the salesRepForStore
	 */
	public void setSalesRepForStore(final B2BCustomer item, final List<NeorisB2BCustomerSettingByStore> value)
	{
		setSalesRepForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesRepForStore</code> attribute.
	 * @return the salesRepForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getSalesRepForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESREPFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.salesRepForStore</code> attribute.
	 * @return the salesRepForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getSalesRepForStore(final B2BUnit item)
	{
		return getSalesRepForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesRepForStore</code> attribute. 
	 * @param value the salesRepForStore
	 */
	public void setSalesRepForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SALESREPFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.salesRepForStore</code> attribute. 
	 * @param value the salesRepForStore
	 */
	public void setSalesRepForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setSalesRepForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Unit.sapCode</code> attribute.
	 * @return the sapCode
	 */
	public String getSapCode(final SessionContext ctx, final Unit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Unit.SAPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Unit.sapCode</code> attribute.
	 * @return the sapCode
	 */
	public String getSapCode(final Unit item)
	{
		return getSapCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Unit.sapCode</code> attribute. 
	 * @param value the sapCode
	 */
	public void setSapCode(final SessionContext ctx, final Unit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Unit.SAPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Unit.sapCode</code> attribute. 
	 * @param value the sapCode
	 */
	public void setSapCode(final Unit item, final String value)
	{
		setSapCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapCurrency</code> attribute.
	 * @return the sapCurrency
	 */
	public String getSapCurrency(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPCURRENCY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapCurrency</code> attribute.
	 * @return the sapCurrency
	 */
	public String getSapCurrency(final AbstractOrder item)
	{
		return getSapCurrency( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapCurrency</code> attribute. 
	 * @param value the sapCurrency
	 */
	public void setSapCurrency(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPCURRENCY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapCurrency</code> attribute. 
	 * @param value the sapCurrency
	 */
	public void setSapCurrency(final AbstractOrder item, final String value)
	{
		setSapCurrency( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute.
	 * @return the sapEntryNumber
	 */
	public Integer getSapEntryNumber(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.SAPENTRYNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute.
	 * @return the sapEntryNumber
	 */
	public Integer getSapEntryNumber(final AbstractOrderEntry item)
	{
		return getSapEntryNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute. 
	 * @return the sapEntryNumber
	 */
	public int getSapEntryNumberAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Integer value = getSapEntryNumber( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute. 
	 * @return the sapEntryNumber
	 */
	public int getSapEntryNumberAsPrimitive(final AbstractOrderEntry item)
	{
		return getSapEntryNumberAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute. 
	 * @param value the sapEntryNumber
	 */
	public void setSapEntryNumber(final SessionContext ctx, final AbstractOrderEntry item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.SAPENTRYNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute. 
	 * @param value the sapEntryNumber
	 */
	public void setSapEntryNumber(final AbstractOrderEntry item, final Integer value)
	{
		setSapEntryNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute. 
	 * @param value the sapEntryNumber
	 */
	public void setSapEntryNumber(final SessionContext ctx, final AbstractOrderEntry item, final int value)
	{
		setSapEntryNumber( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.sapEntryNumber</code> attribute. 
	 * @param value the sapEntryNumber
	 */
	public void setSapEntryNumber(final AbstractOrderEntry item, final int value)
	{
		setSapEntryNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapExceptionMessage</code> attribute.
	 * @return the sapExceptionMessage
	 */
	public String getSapExceptionMessage(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPEXCEPTIONMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapExceptionMessage</code> attribute.
	 * @return the sapExceptionMessage
	 */
	public String getSapExceptionMessage(final AbstractOrder item)
	{
		return getSapExceptionMessage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapExceptionMessage</code> attribute. 
	 * @param value the sapExceptionMessage
	 */
	public void setSapExceptionMessage(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPEXCEPTIONMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapExceptionMessage</code> attribute. 
	 * @param value the sapExceptionMessage
	 */
	public void setSapExceptionMessage(final AbstractOrder item, final String value)
	{
		setSapExceptionMessage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapLabelWeightUnit</code> attribute.
	 * @return the sapLabelWeightUnit
	 */
	public String getSapLabelWeightUnit(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPLABELWEIGHTUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapLabelWeightUnit</code> attribute.
	 * @return the sapLabelWeightUnit
	 */
	public String getSapLabelWeightUnit(final AbstractOrder item)
	{
		return getSapLabelWeightUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapLabelWeightUnit</code> attribute. 
	 * @param value the sapLabelWeightUnit
	 */
	public void setSapLabelWeightUnit(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPLABELWEIGHTUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapLabelWeightUnit</code> attribute. 
	 * @param value the sapLabelWeightUnit
	 */
	public void setSapLabelWeightUnit(final AbstractOrder item, final String value)
	{
		setSapLabelWeightUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapOrderId</code> attribute.
	 * @return the sapOrderId
	 */
	public String getSapOrderId(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPORDERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapOrderId</code> attribute.
	 * @return the sapOrderId
	 */
	public String getSapOrderId(final AbstractOrder item)
	{
		return getSapOrderId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapOrderId</code> attribute. 
	 * @param value the sapOrderId
	 */
	public void setSapOrderId(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPORDERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapOrderId</code> attribute. 
	 * @param value the sapOrderId
	 */
	public void setSapOrderId(final AbstractOrder item, final String value)
	{
		setSapOrderId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute.
	 * @return the sapSubtotalPrice
	 */
	public Double getSapSubtotalPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPSUBTOTALPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute.
	 * @return the sapSubtotalPrice
	 */
	public Double getSapSubtotalPrice(final AbstractOrder item)
	{
		return getSapSubtotalPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute. 
	 * @return the sapSubtotalPrice
	 */
	public double getSapSubtotalPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getSapSubtotalPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute. 
	 * @return the sapSubtotalPrice
	 */
	public double getSapSubtotalPriceAsPrimitive(final AbstractOrder item)
	{
		return getSapSubtotalPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute. 
	 * @param value the sapSubtotalPrice
	 */
	public void setSapSubtotalPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPSUBTOTALPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute. 
	 * @param value the sapSubtotalPrice
	 */
	public void setSapSubtotalPrice(final AbstractOrder item, final Double value)
	{
		setSapSubtotalPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute. 
	 * @param value the sapSubtotalPrice
	 */
	public void setSapSubtotalPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setSapSubtotalPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapSubtotalPrice</code> attribute. 
	 * @param value the sapSubtotalPrice
	 */
	public void setSapSubtotalPrice(final AbstractOrder item, final double value)
	{
		setSapSubtotalPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapTotalPrice</code> attribute.
	 * @return the sapTotalPrice
	 */
	public Double getSapTotalPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPTOTALPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapTotalPrice</code> attribute.
	 * @return the sapTotalPrice
	 */
	public Double getSapTotalPrice(final AbstractOrder item)
	{
		return getSapTotalPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapTotalPrice</code> attribute. 
	 * @return the sapTotalPrice
	 */
	public double getSapTotalPriceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getSapTotalPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapTotalPrice</code> attribute. 
	 * @return the sapTotalPrice
	 */
	public double getSapTotalPriceAsPrimitive(final AbstractOrder item)
	{
		return getSapTotalPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapTotalPrice</code> attribute. 
	 * @param value the sapTotalPrice
	 */
	public void setSapTotalPrice(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPTOTALPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapTotalPrice</code> attribute. 
	 * @param value the sapTotalPrice
	 */
	public void setSapTotalPrice(final AbstractOrder item, final Double value)
	{
		setSapTotalPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapTotalPrice</code> attribute. 
	 * @param value the sapTotalPrice
	 */
	public void setSapTotalPrice(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setSapTotalPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapTotalPrice</code> attribute. 
	 * @param value the sapTotalPrice
	 */
	public void setSapTotalPrice(final AbstractOrder item, final double value)
	{
		setSapTotalPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapWeightUnit</code> attribute.
	 * @return the sapWeightUnit
	 */
	public String getSapWeightUnit(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPWEIGHTUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sapWeightUnit</code> attribute.
	 * @return the sapWeightUnit
	 */
	public String getSapWeightUnit(final AbstractOrder item)
	{
		return getSapWeightUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapWeightUnit</code> attribute. 
	 * @param value the sapWeightUnit
	 */
	public void setSapWeightUnit(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SAPWEIGHTUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sapWeightUnit</code> attribute. 
	 * @param value the sapWeightUnit
	 */
	public void setSapWeightUnit(final AbstractOrder item, final String value)
	{
		setSapWeightUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.semaphoreCredit</code> attribute.
	 * @return the semaphoreCredit
	 */
	public String getSemaphoreCredit(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SEMAPHORECREDIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.semaphoreCredit</code> attribute.
	 * @return the semaphoreCredit
	 */
	public String getSemaphoreCredit(final AbstractOrder item)
	{
		return getSemaphoreCredit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.semaphoreCredit</code> attribute. 
	 * @param value the semaphoreCredit
	 */
	public void setSemaphoreCredit(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SEMAPHORECREDIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.semaphoreCredit</code> attribute. 
	 * @param value the semaphoreCredit
	 */
	public void setSemaphoreCredit(final AbstractOrder item, final String value)
	{
		setSemaphoreCredit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute.
	 * @return the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public Boolean isSendNotificactionOrderHiddenJGzzMRdz(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SENDNOTIFICACTIONORDERHIDDENJGZZMRDZ);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute.
	 * @return the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public Boolean isSendNotificactionOrderHiddenJGzzMRdz(final BaseStore item)
	{
		return isSendNotificactionOrderHiddenJGzzMRdz( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute. 
	 * @return the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public boolean isSendNotificactionOrderHiddenJGzzMRdzAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isSendNotificactionOrderHiddenJGzzMRdz( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute. 
	 * @return the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public boolean isSendNotificactionOrderHiddenJGzzMRdzAsPrimitive(final BaseStore item)
	{
		return isSendNotificactionOrderHiddenJGzzMRdzAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute. 
	 * @param value the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public void setSendNotificactionOrderHiddenJGzzMRdz(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SENDNOTIFICACTIONORDERHIDDENJGZZMRDZ,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute. 
	 * @param value the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public void setSendNotificactionOrderHiddenJGzzMRdz(final BaseStore item, final Boolean value)
	{
		setSendNotificactionOrderHiddenJGzzMRdz( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute. 
	 * @param value the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public void setSendNotificactionOrderHiddenJGzzMRdz(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setSendNotificactionOrderHiddenJGzzMRdz( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificactionOrderHiddenJGzzMRdz</code> attribute. 
	 * @param value the sendNotificactionOrderHiddenJGzzMRdz
	 */
	public void setSendNotificactionOrderHiddenJGzzMRdz(final BaseStore item, final boolean value)
	{
		setSendNotificactionOrderHiddenJGzzMRdz( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute.
	 * @return the sendNotificationToProlamsaInternal
	 */
	public Boolean isSendNotificationToProlamsaInternal(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SENDNOTIFICATIONTOPROLAMSAINTERNAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute.
	 * @return the sendNotificationToProlamsaInternal
	 */
	public Boolean isSendNotificationToProlamsaInternal(final BaseStore item)
	{
		return isSendNotificationToProlamsaInternal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute. 
	 * @return the sendNotificationToProlamsaInternal
	 */
	public boolean isSendNotificationToProlamsaInternalAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isSendNotificationToProlamsaInternal( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute. 
	 * @return the sendNotificationToProlamsaInternal
	 */
	public boolean isSendNotificationToProlamsaInternalAsPrimitive(final BaseStore item)
	{
		return isSendNotificationToProlamsaInternalAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute. 
	 * @param value the sendNotificationToProlamsaInternal
	 */
	public void setSendNotificationToProlamsaInternal(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SENDNOTIFICATIONTOPROLAMSAINTERNAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute. 
	 * @param value the sendNotificationToProlamsaInternal
	 */
	public void setSendNotificationToProlamsaInternal(final BaseStore item, final Boolean value)
	{
		setSendNotificationToProlamsaInternal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute. 
	 * @param value the sendNotificationToProlamsaInternal
	 */
	public void setSendNotificationToProlamsaInternal(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setSendNotificationToProlamsaInternal( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToProlamsaInternal</code> attribute. 
	 * @param value the sendNotificationToProlamsaInternal
	 */
	public void setSendNotificationToProlamsaInternal(final BaseStore item, final boolean value)
	{
		setSendNotificationToProlamsaInternal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute.
	 * @return the sendNotificationToUserDocument
	 */
	public Boolean isSendNotificationToUserDocument(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SENDNOTIFICATIONTOUSERDOCUMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute.
	 * @return the sendNotificationToUserDocument
	 */
	public Boolean isSendNotificationToUserDocument(final BaseStore item)
	{
		return isSendNotificationToUserDocument( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute. 
	 * @return the sendNotificationToUserDocument
	 */
	public boolean isSendNotificationToUserDocumentAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isSendNotificationToUserDocument( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute. 
	 * @return the sendNotificationToUserDocument
	 */
	public boolean isSendNotificationToUserDocumentAsPrimitive(final BaseStore item)
	{
		return isSendNotificationToUserDocumentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute. 
	 * @param value the sendNotificationToUserDocument
	 */
	public void setSendNotificationToUserDocument(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SENDNOTIFICATIONTOUSERDOCUMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute. 
	 * @param value the sendNotificationToUserDocument
	 */
	public void setSendNotificationToUserDocument(final BaseStore item, final Boolean value)
	{
		setSendNotificationToUserDocument( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute. 
	 * @param value the sendNotificationToUserDocument
	 */
	public void setSendNotificationToUserDocument(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setSendNotificationToUserDocument( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.sendNotificationToUserDocument</code> attribute. 
	 * @param value the sendNotificationToUserDocument
	 */
	public void setSendNotificationToUserDocument(final BaseStore item, final boolean value)
	{
		setSendNotificationToUserDocument( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sharePointErrorTrace</code> attribute.
	 * @return the sharePointErrorTrace
	 */
	public String getSharePointErrorTrace(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SHAREPOINTERRORTRACE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sharePointErrorTrace</code> attribute.
	 * @return the sharePointErrorTrace
	 */
	public String getSharePointErrorTrace(final AbstractOrder item)
	{
		return getSharePointErrorTrace( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sharePointErrorTrace</code> attribute. 
	 * @param value the sharePointErrorTrace
	 */
	public void setSharePointErrorTrace(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SHAREPOINTERRORTRACE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sharePointErrorTrace</code> attribute. 
	 * @param value the sharePointErrorTrace
	 */
	public void setSharePointErrorTrace(final AbstractOrder item, final String value)
	{
		setSharePointErrorTrace( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sharePointId</code> attribute.
	 * @return the sharePointId
	 */
	public String getSharePointId(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SHAREPOINTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.sharePointId</code> attribute.
	 * @return the sharePointId
	 */
	public String getSharePointId(final AbstractOrder item)
	{
		return getSharePointId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sharePointId</code> attribute. 
	 * @param value the sharePointId
	 */
	public void setSharePointId(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SHAREPOINTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.sharePointId</code> attribute. 
	 * @param value the sharePointId
	 */
	public void setSharePointId(final AbstractOrder item, final String value)
	{
		setSharePointId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingInstructions</code> attribute.
	 * @return the shippingInstructions
	 */
	public String getShippingInstructions(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SHIPPINGINSTRUCTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingInstructions</code> attribute.
	 * @return the shippingInstructions
	 */
	public String getShippingInstructions(final AbstractOrder item)
	{
		return getShippingInstructions( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingInstructions</code> attribute. 
	 * @param value the shippingInstructions
	 */
	public void setShippingInstructions(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.SHIPPINGINSTRUCTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingInstructions</code> attribute. 
	 * @param value the shippingInstructions
	 */
	public void setShippingInstructions(final AbstractOrder item, final String value)
	{
		setShippingInstructions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.shortName</code> attribute.
	 * @return the shortName
	 */
	public String getShortName(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Address.SHORTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.shortName</code> attribute.
	 * @return the shortName
	 */
	public String getShortName(final Address item)
	{
		return getShortName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.shortName</code> attribute. 
	 * @param value the shortName
	 */
	public void setShortName(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Address.SHORTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.shortName</code> attribute. 
	 * @param value the shortName
	 */
	public void setShortName(final Address item, final String value)
	{
		setShortName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.shortName</code> attribute.
	 * @return the shortName
	 */
	public String getShortName(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SHORTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.shortName</code> attribute.
	 * @return the shortName
	 */
	public String getShortName(final B2BUnit item)
	{
		return getShortName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.shortName</code> attribute. 
	 * @param value the shortName
	 */
	public void setShortName(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.SHORTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.shortName</code> attribute. 
	 * @param value the shortName
	 */
	public void setShortName(final B2BUnit item, final String value)
	{
		setShortName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAccountBalance</code> attribute.
	 * @return the showAccountBalance
	 */
	public Boolean isShowAccountBalance(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWACCOUNTBALANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAccountBalance</code> attribute.
	 * @return the showAccountBalance
	 */
	public Boolean isShowAccountBalance(final BaseStore item)
	{
		return isShowAccountBalance( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAccountBalance</code> attribute. 
	 * @return the showAccountBalance
	 */
	public boolean isShowAccountBalanceAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowAccountBalance( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAccountBalance</code> attribute. 
	 * @return the showAccountBalance
	 */
	public boolean isShowAccountBalanceAsPrimitive(final BaseStore item)
	{
		return isShowAccountBalanceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAccountBalance</code> attribute. 
	 * @param value the showAccountBalance
	 */
	public void setShowAccountBalance(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWACCOUNTBALANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAccountBalance</code> attribute. 
	 * @param value the showAccountBalance
	 */
	public void setShowAccountBalance(final BaseStore item, final Boolean value)
	{
		setShowAccountBalance( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAccountBalance</code> attribute. 
	 * @param value the showAccountBalance
	 */
	public void setShowAccountBalance(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowAccountBalance( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAccountBalance</code> attribute. 
	 * @param value the showAccountBalance
	 */
	public void setShowAccountBalance(final BaseStore item, final boolean value)
	{
		setShowAccountBalance( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAlertsDashboard</code> attribute.
	 * @return the showAlertsDashboard
	 */
	public Boolean isShowAlertsDashboard(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWALERTSDASHBOARD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAlertsDashboard</code> attribute.
	 * @return the showAlertsDashboard
	 */
	public Boolean isShowAlertsDashboard(final BaseStore item)
	{
		return isShowAlertsDashboard( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAlertsDashboard</code> attribute. 
	 * @return the showAlertsDashboard
	 */
	public boolean isShowAlertsDashboardAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowAlertsDashboard( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showAlertsDashboard</code> attribute. 
	 * @return the showAlertsDashboard
	 */
	public boolean isShowAlertsDashboardAsPrimitive(final BaseStore item)
	{
		return isShowAlertsDashboardAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAlertsDashboard</code> attribute. 
	 * @param value the showAlertsDashboard
	 */
	public void setShowAlertsDashboard(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWALERTSDASHBOARD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAlertsDashboard</code> attribute. 
	 * @param value the showAlertsDashboard
	 */
	public void setShowAlertsDashboard(final BaseStore item, final Boolean value)
	{
		setShowAlertsDashboard( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAlertsDashboard</code> attribute. 
	 * @param value the showAlertsDashboard
	 */
	public void setShowAlertsDashboard(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowAlertsDashboard( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showAlertsDashboard</code> attribute. 
	 * @param value the showAlertsDashboard
	 */
	public void setShowAlertsDashboard(final BaseStore item, final boolean value)
	{
		setShowAlertsDashboard( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showBackorder</code> attribute.
	 * @return the showBackorder
	 */
	public Boolean isShowBackorder(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWBACKORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showBackorder</code> attribute.
	 * @return the showBackorder
	 */
	public Boolean isShowBackorder(final BaseStore item)
	{
		return isShowBackorder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showBackorder</code> attribute. 
	 * @return the showBackorder
	 */
	public boolean isShowBackorderAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowBackorder( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showBackorder</code> attribute. 
	 * @return the showBackorder
	 */
	public boolean isShowBackorderAsPrimitive(final BaseStore item)
	{
		return isShowBackorderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showBackorder</code> attribute. 
	 * @param value the showBackorder
	 */
	public void setShowBackorder(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWBACKORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showBackorder</code> attribute. 
	 * @param value the showBackorder
	 */
	public void setShowBackorder(final BaseStore item, final Boolean value)
	{
		setShowBackorder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showBackorder</code> attribute. 
	 * @param value the showBackorder
	 */
	public void setShowBackorder(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowBackorder( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showBackorder</code> attribute. 
	 * @param value the showBackorder
	 */
	public void setShowBackorder(final BaseStore item, final boolean value)
	{
		setShowBackorder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showDocumentSearch</code> attribute.
	 * @return the showDocumentSearch
	 */
	public Boolean isShowDocumentSearch(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWDOCUMENTSEARCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showDocumentSearch</code> attribute.
	 * @return the showDocumentSearch
	 */
	public Boolean isShowDocumentSearch(final BaseStore item)
	{
		return isShowDocumentSearch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showDocumentSearch</code> attribute. 
	 * @return the showDocumentSearch
	 */
	public boolean isShowDocumentSearchAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowDocumentSearch( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showDocumentSearch</code> attribute. 
	 * @return the showDocumentSearch
	 */
	public boolean isShowDocumentSearchAsPrimitive(final BaseStore item)
	{
		return isShowDocumentSearchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showDocumentSearch</code> attribute. 
	 * @param value the showDocumentSearch
	 */
	public void setShowDocumentSearch(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWDOCUMENTSEARCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showDocumentSearch</code> attribute. 
	 * @param value the showDocumentSearch
	 */
	public void setShowDocumentSearch(final BaseStore item, final Boolean value)
	{
		setShowDocumentSearch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showDocumentSearch</code> attribute. 
	 * @param value the showDocumentSearch
	 */
	public void setShowDocumentSearch(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowDocumentSearch( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showDocumentSearch</code> attribute. 
	 * @param value the showDocumentSearch
	 */
	public void setShowDocumentSearch(final BaseStore item, final boolean value)
	{
		setShowDocumentSearch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showIncidentsModule</code> attribute.
	 * @return the showIncidentsModule
	 */
	public Boolean isShowIncidentsModule(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWINCIDENTSMODULE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showIncidentsModule</code> attribute.
	 * @return the showIncidentsModule
	 */
	public Boolean isShowIncidentsModule(final BaseStore item)
	{
		return isShowIncidentsModule( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showIncidentsModule</code> attribute. 
	 * @return the showIncidentsModule
	 */
	public boolean isShowIncidentsModuleAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowIncidentsModule( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showIncidentsModule</code> attribute. 
	 * @return the showIncidentsModule
	 */
	public boolean isShowIncidentsModuleAsPrimitive(final BaseStore item)
	{
		return isShowIncidentsModuleAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showIncidentsModule</code> attribute. 
	 * @param value the showIncidentsModule
	 */
	public void setShowIncidentsModule(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWINCIDENTSMODULE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showIncidentsModule</code> attribute. 
	 * @param value the showIncidentsModule
	 */
	public void setShowIncidentsModule(final BaseStore item, final Boolean value)
	{
		setShowIncidentsModule( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showIncidentsModule</code> attribute. 
	 * @param value the showIncidentsModule
	 */
	public void setShowIncidentsModule(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowIncidentsModule( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showIncidentsModule</code> attribute. 
	 * @param value the showIncidentsModule
	 */
	public void setShowIncidentsModule(final BaseStore item, final boolean value)
	{
		setShowIncidentsModule( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showNoticesModule</code> attribute.
	 * @return the showNoticesModule
	 */
	public Boolean isShowNoticesModule(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWNOTICESMODULE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showNoticesModule</code> attribute.
	 * @return the showNoticesModule
	 */
	public Boolean isShowNoticesModule(final BaseStore item)
	{
		return isShowNoticesModule( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showNoticesModule</code> attribute. 
	 * @return the showNoticesModule
	 */
	public boolean isShowNoticesModuleAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowNoticesModule( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showNoticesModule</code> attribute. 
	 * @return the showNoticesModule
	 */
	public boolean isShowNoticesModuleAsPrimitive(final BaseStore item)
	{
		return isShowNoticesModuleAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showNoticesModule</code> attribute. 
	 * @param value the showNoticesModule
	 */
	public void setShowNoticesModule(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWNOTICESMODULE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showNoticesModule</code> attribute. 
	 * @param value the showNoticesModule
	 */
	public void setShowNoticesModule(final BaseStore item, final Boolean value)
	{
		setShowNoticesModule( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showNoticesModule</code> attribute. 
	 * @param value the showNoticesModule
	 */
	public void setShowNoticesModule(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowNoticesModule( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showNoticesModule</code> attribute. 
	 * @param value the showNoticesModule
	 */
	public void setShowNoticesModule(final BaseStore item, final boolean value)
	{
		setShowNoticesModule( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOptStudioReports</code> attribute.
	 * @return the showOptStudioReports
	 */
	public Boolean isShowOptStudioReports(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWOPTSTUDIOREPORTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOptStudioReports</code> attribute.
	 * @return the showOptStudioReports
	 */
	public Boolean isShowOptStudioReports(final BaseStore item)
	{
		return isShowOptStudioReports( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOptStudioReports</code> attribute. 
	 * @return the showOptStudioReports
	 */
	public boolean isShowOptStudioReportsAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowOptStudioReports( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOptStudioReports</code> attribute. 
	 * @return the showOptStudioReports
	 */
	public boolean isShowOptStudioReportsAsPrimitive(final BaseStore item)
	{
		return isShowOptStudioReportsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOptStudioReports</code> attribute. 
	 * @param value the showOptStudioReports
	 */
	public void setShowOptStudioReports(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWOPTSTUDIOREPORTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOptStudioReports</code> attribute. 
	 * @param value the showOptStudioReports
	 */
	public void setShowOptStudioReports(final BaseStore item, final Boolean value)
	{
		setShowOptStudioReports( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOptStudioReports</code> attribute. 
	 * @param value the showOptStudioReports
	 */
	public void setShowOptStudioReports(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowOptStudioReports( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOptStudioReports</code> attribute. 
	 * @param value the showOptStudioReports
	 */
	public void setShowOptStudioReports(final BaseStore item, final boolean value)
	{
		setShowOptStudioReports( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOrdersAndQuotes</code> attribute.
	 * @return the showOrdersAndQuotes
	 */
	public Boolean isShowOrdersAndQuotes(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWORDERSANDQUOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOrdersAndQuotes</code> attribute.
	 * @return the showOrdersAndQuotes
	 */
	public Boolean isShowOrdersAndQuotes(final BaseStore item)
	{
		return isShowOrdersAndQuotes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOrdersAndQuotes</code> attribute. 
	 * @return the showOrdersAndQuotes
	 */
	public boolean isShowOrdersAndQuotesAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isShowOrdersAndQuotes( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.showOrdersAndQuotes</code> attribute. 
	 * @return the showOrdersAndQuotes
	 */
	public boolean isShowOrdersAndQuotesAsPrimitive(final BaseStore item)
	{
		return isShowOrdersAndQuotesAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOrdersAndQuotes</code> attribute. 
	 * @param value the showOrdersAndQuotes
	 */
	public void setShowOrdersAndQuotes(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.SHOWORDERSANDQUOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOrdersAndQuotes</code> attribute. 
	 * @param value the showOrdersAndQuotes
	 */
	public void setShowOrdersAndQuotes(final BaseStore item, final Boolean value)
	{
		setShowOrdersAndQuotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOrdersAndQuotes</code> attribute. 
	 * @param value the showOrdersAndQuotes
	 */
	public void setShowOrdersAndQuotes(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setShowOrdersAndQuotes( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.showOrdersAndQuotes</code> attribute. 
	 * @param value the showOrdersAndQuotes
	 */
	public void setShowOrdersAndQuotes(final BaseStore item, final boolean value)
	{
		setShowOrdersAndQuotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (EnumerationValue)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus(final AbstractOrderEntry item)
	{
		return getStatus( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final AbstractOrderEntry item, final EnumerationValue value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final AbstractOrderEntry item, final EnumerationValue value)
	{
		setStatus( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.steelCategory</code> attribute.
	 * @return the steelCategory
	 */
	public String getSteelCategory(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.STEELCATEGORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.steelCategory</code> attribute.
	 * @return the steelCategory
	 */
	public String getSteelCategory(final AbstractOrderEntry item)
	{
		return getSteelCategory( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.steelCategory</code> attribute. 
	 * @param value the steelCategory
	 */
	public void setSteelCategory(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.STEELCATEGORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.steelCategory</code> attribute. 
	 * @param value the steelCategory
	 */
	public void setSteelCategory(final AbstractOrderEntry item, final String value)
	{
		setSteelCategory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.stockOuts</code> attribute.
	 * @return the stockOuts
	 */
	public String getStockOuts(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.STOCKOUTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.stockOuts</code> attribute.
	 * @return the stockOuts
	 */
	public String getStockOuts(final AbstractOrderEntry item)
	{
		return getStockOuts( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.stockOuts</code> attribute. 
	 * @param value the stockOuts
	 */
	public void setStockOuts(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.STOCKOUTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.stockOuts</code> attribute. 
	 * @param value the stockOuts
	 */
	public void setStockOuts(final AbstractOrderEntry item, final String value)
	{
		setStockOuts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.techinicalSupportEmail</code> attribute.
	 * @return the techinicalSupportEmail
	 */
	public String getTechinicalSupportEmail(final SessionContext ctx, final BaseStore item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.TECHINICALSUPPORTEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.techinicalSupportEmail</code> attribute.
	 * @return the techinicalSupportEmail
	 */
	public String getTechinicalSupportEmail(final BaseStore item)
	{
		return getTechinicalSupportEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.techinicalSupportEmail</code> attribute. 
	 * @param value the techinicalSupportEmail
	 */
	public void setTechinicalSupportEmail(final SessionContext ctx, final BaseStore item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.TECHINICALSUPPORTEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.techinicalSupportEmail</code> attribute. 
	 * @param value the techinicalSupportEmail
	 */
	public void setTechinicalSupportEmail(final BaseStore item, final String value)
	{
		setTechinicalSupportEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.TermsAndCoditionsByStore</code> attribute.
	 * @return the TermsAndCoditionsByStore
	 */
	public List<TermsAndCoditionsByStore> getTermsAndCoditionsByStore(final SessionContext ctx, final B2BCustomer item)
	{
		List<TermsAndCoditionsByStore> coll = (List<TermsAndCoditionsByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.TERMSANDCODITIONSBYSTORE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.TermsAndCoditionsByStore</code> attribute.
	 * @return the TermsAndCoditionsByStore
	 */
	public List<TermsAndCoditionsByStore> getTermsAndCoditionsByStore(final B2BCustomer item)
	{
		return getTermsAndCoditionsByStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.TermsAndCoditionsByStore</code> attribute. 
	 * @param value the TermsAndCoditionsByStore
	 */
	public void setTermsAndCoditionsByStore(final SessionContext ctx, final B2BCustomer item, final List<TermsAndCoditionsByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BCustomer.TERMSANDCODITIONSBYSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.TermsAndCoditionsByStore</code> attribute. 
	 * @param value the TermsAndCoditionsByStore
	 */
	public void setTermsAndCoditionsByStore(final B2BCustomer item, final List<TermsAndCoditionsByStore> value)
	{
		setTermsAndCoditionsByStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.termsAndConditionsChecked</code> attribute.
	 * @return the termsAndConditionsChecked
	 */
	public Boolean isTermsAndConditionsChecked(final SessionContext ctx, final BaseStore item)
	{
		return (Boolean)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.TERMSANDCONDITIONSCHECKED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.termsAndConditionsChecked</code> attribute.
	 * @return the termsAndConditionsChecked
	 */
	public Boolean isTermsAndConditionsChecked(final BaseStore item)
	{
		return isTermsAndConditionsChecked( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.termsAndConditionsChecked</code> attribute. 
	 * @return the termsAndConditionsChecked
	 */
	public boolean isTermsAndConditionsCheckedAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Boolean value = isTermsAndConditionsChecked( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.termsAndConditionsChecked</code> attribute. 
	 * @return the termsAndConditionsChecked
	 */
	public boolean isTermsAndConditionsCheckedAsPrimitive(final BaseStore item)
	{
		return isTermsAndConditionsCheckedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final SessionContext ctx, final BaseStore item, final Boolean value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.TERMSANDCONDITIONSCHECKED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final BaseStore item, final Boolean value)
	{
		setTermsAndConditionsChecked( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final SessionContext ctx, final BaseStore item, final boolean value)
	{
		setTermsAndConditionsChecked( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.termsAndConditionsChecked</code> attribute. 
	 * @param value the termsAndConditionsChecked
	 */
	public void setTermsAndConditionsChecked(final BaseStore item, final boolean value)
	{
		setTermsAndConditionsChecked( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalAssurance</code> attribute.
	 * @return the totalAssurance
	 */
	public Double getTotalAssurance(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALASSURANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalAssurance</code> attribute.
	 * @return the totalAssurance
	 */
	public Double getTotalAssurance(final AbstractOrder item)
	{
		return getTotalAssurance( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalAssurance</code> attribute. 
	 * @return the totalAssurance
	 */
	public double getTotalAssuranceAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getTotalAssurance( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalAssurance</code> attribute. 
	 * @return the totalAssurance
	 */
	public double getTotalAssuranceAsPrimitive(final AbstractOrder item)
	{
		return getTotalAssuranceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALASSURANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final AbstractOrder item, final Double value)
	{
		setTotalAssurance( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setTotalAssurance( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final AbstractOrder item, final double value)
	{
		setTotalAssurance( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalAssurance</code> attribute.
	 * @return the totalAssurance
	 */
	public Double getTotalAssurance(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.TOTALASSURANCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalAssurance</code> attribute.
	 * @return the totalAssurance
	 */
	public Double getTotalAssurance(final AbstractOrderEntry item)
	{
		return getTotalAssurance( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalAssurance</code> attribute. 
	 * @return the totalAssurance
	 */
	public double getTotalAssuranceAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getTotalAssurance( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalAssurance</code> attribute. 
	 * @return the totalAssurance
	 */
	public double getTotalAssuranceAsPrimitive(final AbstractOrderEntry item)
	{
		return getTotalAssuranceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.TOTALASSURANCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final AbstractOrderEntry item, final Double value)
	{
		setTotalAssurance( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setTotalAssurance( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalAssurance</code> attribute. 
	 * @param value the totalAssurance
	 */
	public void setTotalAssurance(final AbstractOrderEntry item, final double value)
	{
		setTotalAssurance( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDeliveryCost</code> attribute.
	 * @return the totalDeliveryCost
	 */
	public Double getTotalDeliveryCost(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALDELIVERYCOST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDeliveryCost</code> attribute.
	 * @return the totalDeliveryCost
	 */
	public Double getTotalDeliveryCost(final AbstractOrder item)
	{
		return getTotalDeliveryCost( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDeliveryCost</code> attribute. 
	 * @return the totalDeliveryCost
	 */
	public double getTotalDeliveryCostAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getTotalDeliveryCost( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDeliveryCost</code> attribute. 
	 * @return the totalDeliveryCost
	 */
	public double getTotalDeliveryCostAsPrimitive(final AbstractOrder item)
	{
		return getTotalDeliveryCostAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALDELIVERYCOST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final AbstractOrder item, final Double value)
	{
		setTotalDeliveryCost( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setTotalDeliveryCost( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final AbstractOrder item, final double value)
	{
		setTotalDeliveryCost( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute.
	 * @return the totalDeliveryCost
	 */
	public Double getTotalDeliveryCost(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.TOTALDELIVERYCOST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute.
	 * @return the totalDeliveryCost
	 */
	public Double getTotalDeliveryCost(final AbstractOrderEntry item)
	{
		return getTotalDeliveryCost( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute. 
	 * @return the totalDeliveryCost
	 */
	public double getTotalDeliveryCostAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getTotalDeliveryCost( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute. 
	 * @return the totalDeliveryCost
	 */
	public double getTotalDeliveryCostAsPrimitive(final AbstractOrderEntry item)
	{
		return getTotalDeliveryCostAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.TOTALDELIVERYCOST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final AbstractOrderEntry item, final Double value)
	{
		setTotalDeliveryCost( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setTotalDeliveryCost( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalDeliveryCost</code> attribute. 
	 * @param value the totalDeliveryCost
	 */
	public void setTotalDeliveryCost(final AbstractOrderEntry item, final double value)
	{
		setTotalDeliveryCost( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalTaxas</code> attribute.
	 * @return the totalTaxas
	 */
	public Double getTotalTaxas(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALTAXAS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalTaxas</code> attribute.
	 * @return the totalTaxas
	 */
	public Double getTotalTaxas(final AbstractOrder item)
	{
		return getTotalTaxas( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalTaxas</code> attribute. 
	 * @return the totalTaxas
	 */
	public double getTotalTaxasAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getTotalTaxas( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalTaxas</code> attribute. 
	 * @return the totalTaxas
	 */
	public double getTotalTaxasAsPrimitive(final AbstractOrder item)
	{
		return getTotalTaxasAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALTAXAS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final AbstractOrder item, final Double value)
	{
		setTotalTaxas( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setTotalTaxas( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final AbstractOrder item, final double value)
	{
		setTotalTaxas( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalTaxas</code> attribute.
	 * @return the totalTaxas
	 */
	public Double getTotalTaxas(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.TOTALTAXAS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalTaxas</code> attribute.
	 * @return the totalTaxas
	 */
	public Double getTotalTaxas(final AbstractOrderEntry item)
	{
		return getTotalTaxas( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalTaxas</code> attribute. 
	 * @return the totalTaxas
	 */
	public double getTotalTaxasAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getTotalTaxas( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totalTaxas</code> attribute. 
	 * @return the totalTaxas
	 */
	public double getTotalTaxasAsPrimitive(final AbstractOrderEntry item)
	{
		return getTotalTaxasAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.TOTALTAXAS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final AbstractOrderEntry item, final Double value)
	{
		setTotalTaxas( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setTotalTaxas( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totalTaxas</code> attribute. 
	 * @param value the totalTaxas
	 */
	public void setTotalTaxas(final AbstractOrderEntry item, final double value)
	{
		setTotalTaxas( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalWeight</code> attribute.
	 * @return the totalWeight
	 */
	public Double getTotalWeight(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalWeight</code> attribute.
	 * @return the totalWeight
	 */
	public Double getTotalWeight(final AbstractOrder item)
	{
		return getTotalWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalWeight</code> attribute. 
	 * @return the totalWeight
	 */
	public double getTotalWeightAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getTotalWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalWeight</code> attribute. 
	 * @return the totalWeight
	 */
	public double getTotalWeightAsPrimitive(final AbstractOrder item)
	{
		return getTotalWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalWeight</code> attribute. 
	 * @param value the totalWeight
	 */
	public void setTotalWeight(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TOTALWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalWeight</code> attribute. 
	 * @param value the totalWeight
	 */
	public void setTotalWeight(final AbstractOrder item, final Double value)
	{
		setTotalWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalWeight</code> attribute. 
	 * @param value the totalWeight
	 */
	public void setTotalWeight(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setTotalWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalWeight</code> attribute. 
	 * @param value the totalWeight
	 */
	public void setTotalWeight(final AbstractOrder item, final double value)
	{
		setTotalWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.TPago</code> attribute.
	 * @return the TPago
	 */
	public EnumerationValue getTPago(final SessionContext ctx, final AbstractOrder item)
	{
		return (EnumerationValue)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TPAGO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.TPago</code> attribute.
	 * @return the TPago
	 */
	public EnumerationValue getTPago(final AbstractOrder item)
	{
		return getTPago( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.TPago</code> attribute. 
	 * @param value the TPago
	 */
	public void setTPago(final SessionContext ctx, final AbstractOrder item, final EnumerationValue value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TPAGO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.TPago</code> attribute. 
	 * @param value the TPago
	 */
	public void setTPago(final AbstractOrder item, final EnumerationValue value)
	{
		setTPago( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.TPago</code> attribute.
	 * @return the TPago
	 */
	public EnumerationValue getTPago(final SessionContext ctx, final B2BUnit item)
	{
		return (EnumerationValue)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.TPAGO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.TPago</code> attribute.
	 * @return the TPago
	 */
	public EnumerationValue getTPago(final B2BUnit item)
	{
		return getTPago( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.TPago</code> attribute. 
	 * @param value the TPago
	 */
	public void setTPago(final SessionContext ctx, final B2BUnit item, final EnumerationValue value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.TPAGO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.TPago</code> attribute. 
	 * @param value the TPago
	 */
	public void setTPago(final B2BUnit item, final EnumerationValue value)
	{
		setTPago( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.TPagoForStore</code> attribute.
	 * @return the TPagoForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getTPagoForStore(final SessionContext ctx, final B2BUnit item)
	{
		Set<NeorisB2BUnitSettingByStore> coll = (Set<NeorisB2BUnitSettingByStore>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.B2BUnit.TPAGOFORSTORE);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.TPagoForStore</code> attribute.
	 * @return the TPagoForStore
	 */
	public Set<NeorisB2BUnitSettingByStore> getTPagoForStore(final B2BUnit item)
	{
		return getTPagoForStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.TPagoForStore</code> attribute. 
	 * @param value the TPagoForStore
	 */
	public void setTPagoForStore(final SessionContext ctx, final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.B2BUnit.TPAGOFORSTORE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.TPagoForStore</code> attribute. 
	 * @param value the TPagoForStore
	 */
	public void setTPagoForStore(final B2BUnit item, final Set<NeorisB2BUnitSettingByStore> value)
	{
		setTPagoForStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.transportationMode</code> attribute.
	 * @return the transportationMode
	 */
	public TransportationMode getTransportationMode(final SessionContext ctx, final AbstractOrder item)
	{
		return (TransportationMode)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TRANSPORTATIONMODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.transportationMode</code> attribute.
	 * @return the transportationMode
	 */
	public TransportationMode getTransportationMode(final AbstractOrder item)
	{
		return getTransportationMode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.transportationMode</code> attribute. 
	 * @param value the transportationMode
	 */
	public void setTransportationMode(final SessionContext ctx, final AbstractOrder item, final TransportationMode value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.TRANSPORTATIONMODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.transportationMode</code> attribute. 
	 * @param value the transportationMode
	 */
	public void setTransportationMode(final AbstractOrder item, final TransportationMode value)
	{
		setTransportationMode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.transportationMode</code> attribute.
	 * @return the transportationMode
	 */
	public TransportationMode getTransportationMode(final SessionContext ctx, final Address item)
	{
		return (TransportationMode)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Address.TRANSPORTATIONMODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.transportationMode</code> attribute.
	 * @return the transportationMode
	 */
	public TransportationMode getTransportationMode(final Address item)
	{
		return getTransportationMode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.transportationMode</code> attribute. 
	 * @param value the transportationMode
	 */
	public void setTransportationMode(final SessionContext ctx, final Address item, final TransportationMode value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Address.TRANSPORTATIONMODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.transportationMode</code> attribute. 
	 * @param value the transportationMode
	 */
	public void setTransportationMode(final Address item, final TransportationMode value)
	{
		setTransportationMode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.transportationModes</code> attribute.
	 * @return the transportationModes
	 */
	public List<TransportationMode> getTransportationModes(final SessionContext ctx, final BaseStore item)
	{
		List<TransportationMode> coll = (List<TransportationMode>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.TRANSPORTATIONMODES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.transportationModes</code> attribute.
	 * @return the transportationModes
	 */
	public List<TransportationMode> getTransportationModes(final BaseStore item)
	{
		return getTransportationModes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.transportationModes</code> attribute. 
	 * @param value the transportationModes
	 */
	public void setTransportationModes(final SessionContext ctx, final BaseStore item, final List<TransportationMode> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.TRANSPORTATIONMODES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.transportationModes</code> attribute. 
	 * @param value the transportationModes
	 */
	public void setTransportationModes(final BaseStore item, final List<TransportationMode> value)
	{
		setTransportationModes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.type</code> attribute.
	 * @return the type
	 */
	public EnumerationValue getType(final SessionContext ctx, final Wishlist2 item)
	{
		return (EnumerationValue)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Wishlist2.TYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.type</code> attribute.
	 * @return the type
	 */
	public EnumerationValue getType(final Wishlist2 item)
	{
		return getType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.type</code> attribute. 
	 * @param value the type
	 */
	public void setType(final SessionContext ctx, final Wishlist2 item, final EnumerationValue value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Wishlist2.TYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.type</code> attribute. 
	 * @param value the type
	 */
	public void setType(final Wishlist2 item, final EnumerationValue value)
	{
		setType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.unit</code> attribute.
	 * @return the unit
	 */
	public Unit getUnit(final SessionContext ctx, final BaseStore item)
	{
		return (Unit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.UNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.unit</code> attribute.
	 * @return the unit
	 */
	public Unit getUnit(final BaseStore item)
	{
		return getUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final SessionContext ctx, final BaseStore item, final Unit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.UNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final BaseStore item, final Unit value)
	{
		setUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.unit</code> attribute.
	 * @return the unit
	 */
	public B2BUnit getUnit(final SessionContext ctx, final AbstractOrder item)
	{
		return (B2BUnit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.UNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.unit</code> attribute.
	 * @return the unit
	 */
	public B2BUnit getUnit(final AbstractOrder item)
	{
		return getUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final SessionContext ctx, final AbstractOrder item, final B2BUnit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.UNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final AbstractOrder item, final B2BUnit value)
	{
		setUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.unit</code> attribute.
	 * @return the unit
	 */
	public Unit getUnit(final SessionContext ctx, final Wishlist2 item)
	{
		return (Unit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Wishlist2.UNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.unit</code> attribute.
	 * @return the unit
	 */
	public Unit getUnit(final Wishlist2 item)
	{
		return getUnit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final SessionContext ctx, final Wishlist2 item, final Unit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Wishlist2.UNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final Wishlist2 item, final Unit value)
	{
		setUnit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.unitOwner</code> attribute.
	 * @return the unitOwner - The unit owner of cart
	 */
	public B2BUnit getUnitOwner(final SessionContext ctx, final Cart item)
	{
		return (B2BUnit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.Cart.UNITOWNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.unitOwner</code> attribute.
	 * @return the unitOwner - The unit owner of cart
	 */
	public B2BUnit getUnitOwner(final Cart item)
	{
		return getUnitOwner( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.unitOwner</code> attribute. 
	 * @param value the unitOwner - The unit owner of cart
	 */
	public void setUnitOwner(final SessionContext ctx, final Cart item, final B2BUnit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.Cart.UNITOWNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.unitOwner</code> attribute. 
	 * @param value the unitOwner - The unit owner of cart
	 */
	public void setUnitOwner(final Cart item, final B2BUnit value)
	{
		setUnitOwner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.units</code> attribute.
	 * @return the units
	 */
	public List<Unit> getUnits(final SessionContext ctx, final BaseStore item)
	{
		List<Unit> coll = (List<Unit>)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.UNITS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.units</code> attribute.
	 * @return the units
	 */
	public List<Unit> getUnits(final BaseStore item)
	{
		return getUnits( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.units</code> attribute. 
	 * @param value the units
	 */
	public void setUnits(final SessionContext ctx, final BaseStore item, final List<Unit> value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.UNITS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.units</code> attribute. 
	 * @param value the units
	 */
	public void setUnits(final BaseStore item, final List<Unit> value)
	{
		setUnits( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.unitWhenPlaced</code> attribute.
	 * @return the unitWhenPlaced
	 */
	public Unit getUnitWhenPlaced(final SessionContext ctx, final AbstractOrder item)
	{
		return (Unit)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.UNITWHENPLACED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.unitWhenPlaced</code> attribute.
	 * @return the unitWhenPlaced
	 */
	public Unit getUnitWhenPlaced(final AbstractOrder item)
	{
		return getUnitWhenPlaced( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.unitWhenPlaced</code> attribute. 
	 * @param value the unitWhenPlaced
	 */
	public void setUnitWhenPlaced(final SessionContext ctx, final AbstractOrder item, final Unit value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.UNITWHENPLACED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.unitWhenPlaced</code> attribute. 
	 * @param value the unitWhenPlaced
	 */
	public void setUnitWhenPlaced(final AbstractOrder item, final Unit value)
	{
		setUnitWhenPlaced( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.uPrice</code> attribute.
	 * @return the uPrice
	 */
	public String getUPrice(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.UPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.uPrice</code> attribute.
	 * @return the uPrice
	 */
	public String getUPrice(final AbstractOrder item)
	{
		return getUPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.uPrice</code> attribute. 
	 * @param value the uPrice
	 */
	public void setUPrice(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrder.UPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.uPrice</code> attribute. 
	 * @param value the uPrice
	 */
	public void setUPrice(final AbstractOrder item, final String value)
	{
		setUPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.validationRegExAttachIncident</code> attribute.
	 * @return the validationRegExAttachIncident
	 */
	public String getValidationRegExAttachIncident(final SessionContext ctx, final BaseStore item)
	{
		return (String)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.VALIDATIONREGEXATTACHINCIDENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.validationRegExAttachIncident</code> attribute.
	 * @return the validationRegExAttachIncident
	 */
	public String getValidationRegExAttachIncident(final BaseStore item)
	{
		return getValidationRegExAttachIncident( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.validationRegExAttachIncident</code> attribute. 
	 * @param value the validationRegExAttachIncident
	 */
	public void setValidationRegExAttachIncident(final SessionContext ctx, final BaseStore item, final String value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.VALIDATIONREGEXATTACHINCIDENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.validationRegExAttachIncident</code> attribute. 
	 * @param value the validationRegExAttachIncident
	 */
	public void setValidationRegExAttachIncident(final BaseStore item, final String value)
	{
		setValidationRegExAttachIncident( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.weight</code> attribute.
	 * @return the weight
	 */
	public Double getWeight(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.WEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.weight</code> attribute.
	 * @return the weight
	 */
	public Double getWeight(final AbstractOrderEntry item)
	{
		return getWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.weight</code> attribute. 
	 * @return the weight
	 */
	public double getWeightAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getWeight( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.weight</code> attribute. 
	 * @return the weight
	 */
	public double getWeightAsPrimitive(final AbstractOrderEntry item)
	{
		return getWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.AbstractOrderEntry.WEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final AbstractOrderEntry item, final Double value)
	{
		setWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setWeight( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final AbstractOrderEntry item, final double value)
	{
		setWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute.
	 * @return the weightDecimalsNumberBunKg
	 */
	public Integer getWeightDecimalsNumberBunKg(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERBUNKG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute.
	 * @return the weightDecimalsNumberBunKg
	 */
	public Integer getWeightDecimalsNumberBunKg(final BaseStore item)
	{
		return getWeightDecimalsNumberBunKg( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute. 
	 * @return the weightDecimalsNumberBunKg
	 */
	public int getWeightDecimalsNumberBunKgAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberBunKg( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute. 
	 * @return the weightDecimalsNumberBunKg
	 */
	public int getWeightDecimalsNumberBunKgAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberBunKgAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute. 
	 * @param value the weightDecimalsNumberBunKg
	 */
	public void setWeightDecimalsNumberBunKg(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERBUNKG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute. 
	 * @param value the weightDecimalsNumberBunKg
	 */
	public void setWeightDecimalsNumberBunKg(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberBunKg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute. 
	 * @param value the weightDecimalsNumberBunKg
	 */
	public void setWeightDecimalsNumberBunKg(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberBunKg( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunKg</code> attribute. 
	 * @param value the weightDecimalsNumberBunKg
	 */
	public void setWeightDecimalsNumberBunKg(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberBunKg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute.
	 * @return the weightDecimalsNumberBunLb
	 */
	public Integer getWeightDecimalsNumberBunLb(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERBUNLB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute.
	 * @return the weightDecimalsNumberBunLb
	 */
	public Integer getWeightDecimalsNumberBunLb(final BaseStore item)
	{
		return getWeightDecimalsNumberBunLb( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute. 
	 * @return the weightDecimalsNumberBunLb
	 */
	public int getWeightDecimalsNumberBunLbAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberBunLb( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute. 
	 * @return the weightDecimalsNumberBunLb
	 */
	public int getWeightDecimalsNumberBunLbAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberBunLbAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute. 
	 * @param value the weightDecimalsNumberBunLb
	 */
	public void setWeightDecimalsNumberBunLb(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERBUNLB,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute. 
	 * @param value the weightDecimalsNumberBunLb
	 */
	public void setWeightDecimalsNumberBunLb(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberBunLb( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute. 
	 * @param value the weightDecimalsNumberBunLb
	 */
	public void setWeightDecimalsNumberBunLb(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberBunLb( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberBunLb</code> attribute. 
	 * @param value the weightDecimalsNumberBunLb
	 */
	public void setWeightDecimalsNumberBunLb(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberBunLb( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute.
	 * @return the weightDecimalsNumberFt
	 */
	public Integer getWeightDecimalsNumberFt(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERFT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute.
	 * @return the weightDecimalsNumberFt
	 */
	public Integer getWeightDecimalsNumberFt(final BaseStore item)
	{
		return getWeightDecimalsNumberFt( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute. 
	 * @return the weightDecimalsNumberFt
	 */
	public int getWeightDecimalsNumberFtAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberFt( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute. 
	 * @return the weightDecimalsNumberFt
	 */
	public int getWeightDecimalsNumberFtAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberFtAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute. 
	 * @param value the weightDecimalsNumberFt
	 */
	public void setWeightDecimalsNumberFt(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERFT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute. 
	 * @param value the weightDecimalsNumberFt
	 */
	public void setWeightDecimalsNumberFt(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberFt( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute. 
	 * @param value the weightDecimalsNumberFt
	 */
	public void setWeightDecimalsNumberFt(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberFt( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberFt</code> attribute. 
	 * @param value the weightDecimalsNumberFt
	 */
	public void setWeightDecimalsNumberFt(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberFt( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute.
	 * @return the weightDecimalsNumberKG
	 */
	public Integer getWeightDecimalsNumberKG(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERKG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute.
	 * @return the weightDecimalsNumberKG
	 */
	public Integer getWeightDecimalsNumberKG(final BaseStore item)
	{
		return getWeightDecimalsNumberKG( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute. 
	 * @return the weightDecimalsNumberKG
	 */
	public int getWeightDecimalsNumberKGAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberKG( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute. 
	 * @return the weightDecimalsNumberKG
	 */
	public int getWeightDecimalsNumberKGAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberKGAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute. 
	 * @param value the weightDecimalsNumberKG
	 */
	public void setWeightDecimalsNumberKG(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERKG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute. 
	 * @param value the weightDecimalsNumberKG
	 */
	public void setWeightDecimalsNumberKG(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberKG( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute. 
	 * @param value the weightDecimalsNumberKG
	 */
	public void setWeightDecimalsNumberKG(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberKG( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberKG</code> attribute. 
	 * @param value the weightDecimalsNumberKG
	 */
	public void setWeightDecimalsNumberKG(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberKG( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute.
	 * @return the weightDecimalsNumberLb
	 */
	public Integer getWeightDecimalsNumberLb(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERLB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute.
	 * @return the weightDecimalsNumberLb
	 */
	public Integer getWeightDecimalsNumberLb(final BaseStore item)
	{
		return getWeightDecimalsNumberLb( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute. 
	 * @return the weightDecimalsNumberLb
	 */
	public int getWeightDecimalsNumberLbAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberLb( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute. 
	 * @return the weightDecimalsNumberLb
	 */
	public int getWeightDecimalsNumberLbAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberLbAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute. 
	 * @param value the weightDecimalsNumberLb
	 */
	public void setWeightDecimalsNumberLb(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERLB,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute. 
	 * @param value the weightDecimalsNumberLb
	 */
	public void setWeightDecimalsNumberLb(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberLb( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute. 
	 * @param value the weightDecimalsNumberLb
	 */
	public void setWeightDecimalsNumberLb(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberLb( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberLb</code> attribute. 
	 * @param value the weightDecimalsNumberLb
	 */
	public void setWeightDecimalsNumberLb(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberLb( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute.
	 * @return the weightDecimalsNumberMt
	 */
	public Integer getWeightDecimalsNumberMt(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERMT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute.
	 * @return the weightDecimalsNumberMt
	 */
	public Integer getWeightDecimalsNumberMt(final BaseStore item)
	{
		return getWeightDecimalsNumberMt( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute. 
	 * @return the weightDecimalsNumberMt
	 */
	public int getWeightDecimalsNumberMtAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberMt( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute. 
	 * @return the weightDecimalsNumberMt
	 */
	public int getWeightDecimalsNumberMtAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberMtAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute. 
	 * @param value the weightDecimalsNumberMt
	 */
	public void setWeightDecimalsNumberMt(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERMT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute. 
	 * @param value the weightDecimalsNumberMt
	 */
	public void setWeightDecimalsNumberMt(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberMt( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute. 
	 * @param value the weightDecimalsNumberMt
	 */
	public void setWeightDecimalsNumberMt(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberMt( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberMt</code> attribute. 
	 * @param value the weightDecimalsNumberMt
	 */
	public void setWeightDecimalsNumberMt(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberMt( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute.
	 * @return the weightDecimalsNumberPcKg
	 */
	public Integer getWeightDecimalsNumberPcKg(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERPCKG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute.
	 * @return the weightDecimalsNumberPcKg
	 */
	public Integer getWeightDecimalsNumberPcKg(final BaseStore item)
	{
		return getWeightDecimalsNumberPcKg( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute. 
	 * @return the weightDecimalsNumberPcKg
	 */
	public int getWeightDecimalsNumberPcKgAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberPcKg( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute. 
	 * @return the weightDecimalsNumberPcKg
	 */
	public int getWeightDecimalsNumberPcKgAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberPcKgAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute. 
	 * @param value the weightDecimalsNumberPcKg
	 */
	public void setWeightDecimalsNumberPcKg(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERPCKG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute. 
	 * @param value the weightDecimalsNumberPcKg
	 */
	public void setWeightDecimalsNumberPcKg(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberPcKg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute. 
	 * @param value the weightDecimalsNumberPcKg
	 */
	public void setWeightDecimalsNumberPcKg(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberPcKg( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcKg</code> attribute. 
	 * @param value the weightDecimalsNumberPcKg
	 */
	public void setWeightDecimalsNumberPcKg(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberPcKg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute.
	 * @return the weightDecimalsNumberPcLb
	 */
	public Integer getWeightDecimalsNumberPcLb(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERPCLB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute.
	 * @return the weightDecimalsNumberPcLb
	 */
	public Integer getWeightDecimalsNumberPcLb(final BaseStore item)
	{
		return getWeightDecimalsNumberPcLb( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute. 
	 * @return the weightDecimalsNumberPcLb
	 */
	public int getWeightDecimalsNumberPcLbAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberPcLb( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute. 
	 * @return the weightDecimalsNumberPcLb
	 */
	public int getWeightDecimalsNumberPcLbAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberPcLbAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute. 
	 * @param value the weightDecimalsNumberPcLb
	 */
	public void setWeightDecimalsNumberPcLb(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERPCLB,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute. 
	 * @param value the weightDecimalsNumberPcLb
	 */
	public void setWeightDecimalsNumberPcLb(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberPcLb( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute. 
	 * @param value the weightDecimalsNumberPcLb
	 */
	public void setWeightDecimalsNumberPcLb(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberPcLb( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberPcLb</code> attribute. 
	 * @param value the weightDecimalsNumberPcLb
	 */
	public void setWeightDecimalsNumberPcLb(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberPcLb( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute.
	 * @return the weightDecimalsNumberTon
	 */
	public Integer getWeightDecimalsNumberTon(final SessionContext ctx, final BaseStore item)
	{
		return (Integer)item.getProperty( ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERTON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute.
	 * @return the weightDecimalsNumberTon
	 */
	public Integer getWeightDecimalsNumberTon(final BaseStore item)
	{
		return getWeightDecimalsNumberTon( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute. 
	 * @return the weightDecimalsNumberTon
	 */
	public int getWeightDecimalsNumberTonAsPrimitive(final SessionContext ctx, final BaseStore item)
	{
		Integer value = getWeightDecimalsNumberTon( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute. 
	 * @return the weightDecimalsNumberTon
	 */
	public int getWeightDecimalsNumberTonAsPrimitive(final BaseStore item)
	{
		return getWeightDecimalsNumberTonAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute. 
	 * @param value the weightDecimalsNumberTon
	 */
	public void setWeightDecimalsNumberTon(final SessionContext ctx, final BaseStore item, final Integer value)
	{
		item.setProperty(ctx, ProlamsaCoreConstants.Attributes.BaseStore.WEIGHTDECIMALSNUMBERTON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute. 
	 * @param value the weightDecimalsNumberTon
	 */
	public void setWeightDecimalsNumberTon(final BaseStore item, final Integer value)
	{
		setWeightDecimalsNumberTon( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute. 
	 * @param value the weightDecimalsNumberTon
	 */
	public void setWeightDecimalsNumberTon(final SessionContext ctx, final BaseStore item, final int value)
	{
		setWeightDecimalsNumberTon( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseStore.weightDecimalsNumberTon</code> attribute. 
	 * @param value the weightDecimalsNumberTon
	 */
	public void setWeightDecimalsNumberTon(final BaseStore item, final int value)
	{
		setWeightDecimalsNumberTon( getSession().getSessionContext(), item, value );
	}
	
}
