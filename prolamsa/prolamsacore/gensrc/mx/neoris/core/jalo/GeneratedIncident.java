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
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.store.BaseStore;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.jalo.IncidentLine;
import mx.neoris.core.jalo.IncidentState;
import mx.neoris.core.jalo.IncidentType;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem Incident}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedIncident extends GenericItem
{
	/** Qualifier of the <code>Incident.phone</code> attribute **/
	public static final String PHONE = "phone";
	/** Qualifier of the <code>Incident.attachement3</code> attribute **/
	public static final String ATTACHEMENT3 = "attachement3";
	/** Qualifier of the <code>Incident.incidentLines</code> attribute **/
	public static final String INCIDENTLINES = "incidentLines";
	/** Qualifier of the <code>Incident.atach4Description</code> attribute **/
	public static final String ATACH4DESCRIPTION = "atach4Description";
	/** Qualifier of the <code>Incident.date</code> attribute **/
	public static final String DATE = "date";
	/** Qualifier of the <code>Incident.shipTo</code> attribute **/
	public static final String SHIPTO = "shipTo";
	/** Qualifier of the <code>Incident.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>Incident.contactName</code> attribute **/
	public static final String CONTACTNAME = "contactName";
	/** Qualifier of the <code>Incident.atach2Description</code> attribute **/
	public static final String ATACH2DESCRIPTION = "atach2Description";
	/** Qualifier of the <code>Incident.attachement1</code> attribute **/
	public static final String ATTACHEMENT1 = "attachement1";
	/** Qualifier of the <code>Incident.invoiceNumber</code> attribute **/
	public static final String INVOICENUMBER = "invoiceNumber";
	/** Qualifier of the <code>Incident.sharePointId</code> attribute **/
	public static final String SHAREPOINTID = "sharePointId";
	/** Qualifier of the <code>Incident.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>Incident.attachement2</code> attribute **/
	public static final String ATTACHEMENT2 = "attachement2";
	/** Qualifier of the <code>Incident.incoterm</code> attribute **/
	public static final String INCOTERM = "incoterm";
	/** Qualifier of the <code>Incident.alternateContactName</code> attribute **/
	public static final String ALTERNATECONTACTNAME = "alternateContactName";
	/** Qualifier of the <code>Incident.type</code> attribute **/
	public static final String TYPE = "type";
	/** Qualifier of the <code>Incident.account</code> attribute **/
	public static final String ACCOUNT = "account";
	/** Qualifier of the <code>Incident.atach3Description</code> attribute **/
	public static final String ATACH3DESCRIPTION = "atach3Description";
	/** Qualifier of the <code>Incident.atach1Description</code> attribute **/
	public static final String ATACH1DESCRIPTION = "atach1Description";
	/** Qualifier of the <code>Incident.customerCode</code> attribute **/
	public static final String CUSTOMERCODE = "customerCode";
	/** Qualifier of the <code>Incident.country</code> attribute **/
	public static final String COUNTRY = "country";
	/** Qualifier of the <code>Incident.sharePointErrorTrace</code> attribute **/
	public static final String SHAREPOINTERRORTRACE = "sharePointErrorTrace";
	/** Qualifier of the <code>Incident.attachement4</code> attribute **/
	public static final String ATTACHEMENT4 = "attachement4";
	/** Qualifier of the <code>Incident.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>Incident.email</code> attribute **/
	public static final String EMAIL = "email";
	/** Qualifier of the <code>Incident.state</code> attribute **/
	public static final String STATE = "state";
	/** Qualifier of the <code>Incident.descriptionMax</code> attribute **/
	public static final String DESCRIPTIONMAX = "descriptionMax";
	/** Qualifier of the <code>Incident.baseStore</code> attribute **/
	public static final String BASESTORE = "baseStore";
	/** Qualifier of the <code>Incident.attachement5</code> attribute **/
	public static final String ATTACHEMENT5 = "attachement5";
	/** Qualifier of the <code>Incident.atach5Description</code> attribute **/
	public static final String ATACH5DESCRIPTION = "atach5Description";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PHONE, AttributeMode.INITIAL);
		tmp.put(ATTACHEMENT3, AttributeMode.INITIAL);
		tmp.put(INCIDENTLINES, AttributeMode.INITIAL);
		tmp.put(ATACH4DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(DATE, AttributeMode.INITIAL);
		tmp.put(SHIPTO, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(CONTACTNAME, AttributeMode.INITIAL);
		tmp.put(ATACH2DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(ATTACHEMENT1, AttributeMode.INITIAL);
		tmp.put(INVOICENUMBER, AttributeMode.INITIAL);
		tmp.put(SHAREPOINTID, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(ATTACHEMENT2, AttributeMode.INITIAL);
		tmp.put(INCOTERM, AttributeMode.INITIAL);
		tmp.put(ALTERNATECONTACTNAME, AttributeMode.INITIAL);
		tmp.put(TYPE, AttributeMode.INITIAL);
		tmp.put(ACCOUNT, AttributeMode.INITIAL);
		tmp.put(ATACH3DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(ATACH1DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(CUSTOMERCODE, AttributeMode.INITIAL);
		tmp.put(COUNTRY, AttributeMode.INITIAL);
		tmp.put(SHAREPOINTERRORTRACE, AttributeMode.INITIAL);
		tmp.put(ATTACHEMENT4, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(EMAIL, AttributeMode.INITIAL);
		tmp.put(STATE, AttributeMode.INITIAL);
		tmp.put(DESCRIPTIONMAX, AttributeMode.INITIAL);
		tmp.put(BASESTORE, AttributeMode.INITIAL);
		tmp.put(ATTACHEMENT5, AttributeMode.INITIAL);
		tmp.put(ATACH5DESCRIPTION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.account</code> attribute.
	 * @return the account
	 */
	public B2BUnit getAccount(final SessionContext ctx)
	{
		return (B2BUnit)getProperty( ctx, ACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.account</code> attribute.
	 * @return the account
	 */
	public B2BUnit getAccount()
	{
		return getAccount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.account</code> attribute. 
	 * @param value the account
	 */
	public void setAccount(final SessionContext ctx, final B2BUnit value)
	{
		setProperty(ctx, ACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.account</code> attribute. 
	 * @param value the account
	 */
	public void setAccount(final B2BUnit value)
	{
		setAccount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.alternateContactName</code> attribute.
	 * @return the alternateContactName
	 */
	public String getAlternateContactName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ALTERNATECONTACTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.alternateContactName</code> attribute.
	 * @return the alternateContactName
	 */
	public String getAlternateContactName()
	{
		return getAlternateContactName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.alternateContactName</code> attribute. 
	 * @param value the alternateContactName
	 */
	public void setAlternateContactName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ALTERNATECONTACTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.alternateContactName</code> attribute. 
	 * @param value the alternateContactName
	 */
	public void setAlternateContactName(final String value)
	{
		setAlternateContactName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach1Description</code> attribute.
	 * @return the atach1Description
	 */
	public String getAtach1Description(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATACH1DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach1Description</code> attribute.
	 * @return the atach1Description
	 */
	public String getAtach1Description()
	{
		return getAtach1Description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach1Description</code> attribute. 
	 * @param value the atach1Description
	 */
	public void setAtach1Description(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATACH1DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach1Description</code> attribute. 
	 * @param value the atach1Description
	 */
	public void setAtach1Description(final String value)
	{
		setAtach1Description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach2Description</code> attribute.
	 * @return the atach2Description
	 */
	public String getAtach2Description(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATACH2DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach2Description</code> attribute.
	 * @return the atach2Description
	 */
	public String getAtach2Description()
	{
		return getAtach2Description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach2Description</code> attribute. 
	 * @param value the atach2Description
	 */
	public void setAtach2Description(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATACH2DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach2Description</code> attribute. 
	 * @param value the atach2Description
	 */
	public void setAtach2Description(final String value)
	{
		setAtach2Description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach3Description</code> attribute.
	 * @return the atach3Description
	 */
	public String getAtach3Description(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATACH3DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach3Description</code> attribute.
	 * @return the atach3Description
	 */
	public String getAtach3Description()
	{
		return getAtach3Description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach3Description</code> attribute. 
	 * @param value the atach3Description
	 */
	public void setAtach3Description(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATACH3DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach3Description</code> attribute. 
	 * @param value the atach3Description
	 */
	public void setAtach3Description(final String value)
	{
		setAtach3Description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach4Description</code> attribute.
	 * @return the atach4Description
	 */
	public String getAtach4Description(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATACH4DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach4Description</code> attribute.
	 * @return the atach4Description
	 */
	public String getAtach4Description()
	{
		return getAtach4Description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach4Description</code> attribute. 
	 * @param value the atach4Description
	 */
	public void setAtach4Description(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATACH4DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach4Description</code> attribute. 
	 * @param value the atach4Description
	 */
	public void setAtach4Description(final String value)
	{
		setAtach4Description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach5Description</code> attribute.
	 * @return the atach5Description
	 */
	public String getAtach5Description(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATACH5DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.atach5Description</code> attribute.
	 * @return the atach5Description
	 */
	public String getAtach5Description()
	{
		return getAtach5Description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach5Description</code> attribute. 
	 * @param value the atach5Description
	 */
	public void setAtach5Description(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATACH5DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.atach5Description</code> attribute. 
	 * @param value the atach5Description
	 */
	public void setAtach5Description(final String value)
	{
		setAtach5Description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement1</code> attribute.
	 * @return the attachement1
	 */
	public Media getAttachement1(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, ATTACHEMENT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement1</code> attribute.
	 * @return the attachement1
	 */
	public Media getAttachement1()
	{
		return getAttachement1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement1</code> attribute. 
	 * @param value the attachement1
	 */
	public void setAttachement1(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, ATTACHEMENT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement1</code> attribute. 
	 * @param value the attachement1
	 */
	public void setAttachement1(final Media value)
	{
		setAttachement1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement2</code> attribute.
	 * @return the attachement2
	 */
	public Media getAttachement2(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, ATTACHEMENT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement2</code> attribute.
	 * @return the attachement2
	 */
	public Media getAttachement2()
	{
		return getAttachement2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement2</code> attribute. 
	 * @param value the attachement2
	 */
	public void setAttachement2(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, ATTACHEMENT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement2</code> attribute. 
	 * @param value the attachement2
	 */
	public void setAttachement2(final Media value)
	{
		setAttachement2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement3</code> attribute.
	 * @return the attachement3
	 */
	public Media getAttachement3(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, ATTACHEMENT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement3</code> attribute.
	 * @return the attachement3
	 */
	public Media getAttachement3()
	{
		return getAttachement3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement3</code> attribute. 
	 * @param value the attachement3
	 */
	public void setAttachement3(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, ATTACHEMENT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement3</code> attribute. 
	 * @param value the attachement3
	 */
	public void setAttachement3(final Media value)
	{
		setAttachement3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement4</code> attribute.
	 * @return the attachement4
	 */
	public Media getAttachement4(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, ATTACHEMENT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement4</code> attribute.
	 * @return the attachement4
	 */
	public Media getAttachement4()
	{
		return getAttachement4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement4</code> attribute. 
	 * @param value the attachement4
	 */
	public void setAttachement4(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, ATTACHEMENT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement4</code> attribute. 
	 * @param value the attachement4
	 */
	public void setAttachement4(final Media value)
	{
		setAttachement4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement5</code> attribute.
	 * @return the attachement5
	 */
	public Media getAttachement5(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, ATTACHEMENT5);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.attachement5</code> attribute.
	 * @return the attachement5
	 */
	public Media getAttachement5()
	{
		return getAttachement5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement5</code> attribute. 
	 * @param value the attachement5
	 */
	public void setAttachement5(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, ATTACHEMENT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.attachement5</code> attribute. 
	 * @param value the attachement5
	 */
	public void setAttachement5(final Media value)
	{
		setAttachement5( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.baseStore</code> attribute.
	 * @return the baseStore
	 */
	public BaseStore getBaseStore(final SessionContext ctx)
	{
		return (BaseStore)getProperty( ctx, BASESTORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.baseStore</code> attribute.
	 * @return the baseStore
	 */
	public BaseStore getBaseStore()
	{
		return getBaseStore( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.baseStore</code> attribute. 
	 * @param value the baseStore
	 */
	public void setBaseStore(final SessionContext ctx, final BaseStore value)
	{
		setProperty(ctx, BASESTORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.baseStore</code> attribute. 
	 * @param value the baseStore
	 */
	public void setBaseStore(final BaseStore value)
	{
		setBaseStore( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.contactName</code> attribute.
	 * @return the contactName
	 */
	public String getContactName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.contactName</code> attribute.
	 * @return the contactName
	 */
	public String getContactName()
	{
		return getContactName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.contactName</code> attribute. 
	 * @param value the contactName
	 */
	public void setContactName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.contactName</code> attribute. 
	 * @param value the contactName
	 */
	public void setContactName(final String value)
	{
		setContactName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.country</code> attribute.
	 * @return the country
	 */
	public String getCountry(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COUNTRY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.country</code> attribute.
	 * @return the country
	 */
	public String getCountry()
	{
		return getCountry( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.country</code> attribute. 
	 * @param value the country
	 */
	public void setCountry(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COUNTRY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.country</code> attribute. 
	 * @param value the country
	 */
	public void setCountry(final String value)
	{
		setCountry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.customerCode</code> attribute.
	 * @return the customerCode
	 */
	public String getCustomerCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.customerCode</code> attribute.
	 * @return the customerCode
	 */
	public String getCustomerCode()
	{
		return getCustomerCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.customerCode</code> attribute. 
	 * @param value the customerCode
	 */
	public void setCustomerCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.customerCode</code> attribute. 
	 * @param value the customerCode
	 */
	public void setCustomerCode(final String value)
	{
		setCustomerCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.date</code> attribute.
	 * @return the date
	 */
	public Date getDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, DATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.date</code> attribute.
	 * @return the date
	 */
	public Date getDate()
	{
		return getDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, DATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final Date value)
	{
		setDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.descriptionMax</code> attribute.
	 * @return the descriptionMax
	 */
	public String getDescriptionMax(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTIONMAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.descriptionMax</code> attribute.
	 * @return the descriptionMax
	 */
	public String getDescriptionMax()
	{
		return getDescriptionMax( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.descriptionMax</code> attribute. 
	 * @param value the descriptionMax
	 */
	public void setDescriptionMax(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTIONMAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.descriptionMax</code> attribute. 
	 * @param value the descriptionMax
	 */
	public void setDescriptionMax(final String value)
	{
		setDescriptionMax( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.email</code> attribute.
	 * @return the email
	 */
	public String getEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.email</code> attribute.
	 * @return the email
	 */
	public String getEmail()
	{
		return getEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final String value)
	{
		setEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.incidentLines</code> attribute.
	 * @return the incidentLines
	 */
	public List<IncidentLine> getIncidentLines(final SessionContext ctx)
	{
		List<IncidentLine> coll = (List<IncidentLine>)getProperty( ctx, INCIDENTLINES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.incidentLines</code> attribute.
	 * @return the incidentLines
	 */
	public List<IncidentLine> getIncidentLines()
	{
		return getIncidentLines( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.incidentLines</code> attribute. 
	 * @param value the incidentLines
	 */
	public void setIncidentLines(final SessionContext ctx, final List<IncidentLine> value)
	{
		setProperty(ctx, INCIDENTLINES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.incidentLines</code> attribute. 
	 * @param value the incidentLines
	 */
	public void setIncidentLines(final List<IncidentLine> value)
	{
		setIncidentLines( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.incoterm</code> attribute.
	 * @return the incoterm
	 */
	public String getIncoterm(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INCOTERM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.incoterm</code> attribute.
	 * @return the incoterm
	 */
	public String getIncoterm()
	{
		return getIncoterm( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.incoterm</code> attribute. 
	 * @param value the incoterm
	 */
	public void setIncoterm(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INCOTERM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.incoterm</code> attribute. 
	 * @param value the incoterm
	 */
	public void setIncoterm(final String value)
	{
		setIncoterm( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber()
	{
		return getInvoiceNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final String value)
	{
		setInvoiceNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.location</code> attribute.
	 * @return the location
	 */
	public EnumerationValue getLocation(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.location</code> attribute.
	 * @return the location
	 */
	public EnumerationValue getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final EnumerationValue value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.phone</code> attribute.
	 * @return the phone
	 */
	public String getPhone(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.phone</code> attribute.
	 * @return the phone
	 */
	public String getPhone()
	{
		return getPhone( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.phone</code> attribute. 
	 * @param value the phone
	 */
	public void setPhone(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.phone</code> attribute. 
	 * @param value the phone
	 */
	public void setPhone(final String value)
	{
		setPhone( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.sharePointErrorTrace</code> attribute.
	 * @return the sharePointErrorTrace
	 */
	public String getSharePointErrorTrace(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHAREPOINTERRORTRACE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.sharePointErrorTrace</code> attribute.
	 * @return the sharePointErrorTrace
	 */
	public String getSharePointErrorTrace()
	{
		return getSharePointErrorTrace( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.sharePointErrorTrace</code> attribute. 
	 * @param value the sharePointErrorTrace
	 */
	public void setSharePointErrorTrace(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHAREPOINTERRORTRACE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.sharePointErrorTrace</code> attribute. 
	 * @param value the sharePointErrorTrace
	 */
	public void setSharePointErrorTrace(final String value)
	{
		setSharePointErrorTrace( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.sharePointId</code> attribute.
	 * @return the sharePointId
	 */
	public String getSharePointId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHAREPOINTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.sharePointId</code> attribute.
	 * @return the sharePointId
	 */
	public String getSharePointId()
	{
		return getSharePointId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.sharePointId</code> attribute. 
	 * @param value the sharePointId
	 */
	public void setSharePointId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHAREPOINTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.sharePointId</code> attribute. 
	 * @param value the sharePointId
	 */
	public void setSharePointId(final String value)
	{
		setSharePointId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.shipTo</code> attribute.
	 * @return the shipTo
	 */
	public String getShipTo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHIPTO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.shipTo</code> attribute.
	 * @return the shipTo
	 */
	public String getShipTo()
	{
		return getShipTo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.shipTo</code> attribute. 
	 * @param value the shipTo
	 */
	public void setShipTo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHIPTO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.shipTo</code> attribute. 
	 * @param value the shipTo
	 */
	public void setShipTo(final String value)
	{
		setShipTo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.state</code> attribute.
	 * @return the state
	 */
	public IncidentState getState(final SessionContext ctx)
	{
		return (IncidentState)getProperty( ctx, STATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.state</code> attribute.
	 * @return the state
	 */
	public IncidentState getState()
	{
		return getState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final SessionContext ctx, final IncidentState value)
	{
		setProperty(ctx, STATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final IncidentState value)
	{
		setState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.type</code> attribute.
	 * @return the type
	 */
	public IncidentType getType(final SessionContext ctx)
	{
		return (IncidentType)getProperty( ctx, TYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Incident.type</code> attribute.
	 * @return the type
	 */
	public IncidentType getType()
	{
		return getType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.type</code> attribute. 
	 * @param value the type
	 */
	public void setType(final SessionContext ctx, final IncidentType value)
	{
		setProperty(ctx, TYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Incident.type</code> attribute. 
	 * @param value the type
	 */
	public void setType(final IncidentType value)
	{
		setType( getSession().getSessionContext(), value );
	}
	
}
