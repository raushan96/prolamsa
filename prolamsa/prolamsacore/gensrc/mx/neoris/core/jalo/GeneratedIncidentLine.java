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
import de.hybris.platform.jalo.product.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem IncidentLine}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedIncidentLine extends GenericItem
{
	/** Qualifier of the <code>IncidentLine.product</code> attribute **/
	public static final String PRODUCT = "product";
	/** Qualifier of the <code>IncidentLine.weightUnit</code> attribute **/
	public static final String WEIGHTUNIT = "weightUnit";
	/** Qualifier of the <code>IncidentLine.quantityToClaim</code> attribute **/
	public static final String QUANTITYTOCLAIM = "quantityToClaim";
	/** Qualifier of the <code>IncidentLine.salesUnit</code> attribute **/
	public static final String SALESUNIT = "salesUnit";
	/** Qualifier of the <code>IncidentLine.shipToDescription</code> attribute **/
	public static final String SHIPTODESCRIPTION = "shipToDescription";
	/** Qualifier of the <code>IncidentLine.productDescription</code> attribute **/
	public static final String PRODUCTDESCRIPTION = "productDescription";
	/** Qualifier of the <code>IncidentLine.shipTo</code> attribute **/
	public static final String SHIPTO = "shipTo";
	/** Qualifier of the <code>IncidentLine.sorder_p</code> attribute **/
	public static final String SORDER_P = "sorder_p";
	/** Qualifier of the <code>IncidentLine.batch</code> attribute **/
	public static final String BATCH = "batch";
	/** Qualifier of the <code>IncidentLine.invoice</code> attribute **/
	public static final String INVOICE = "invoice";
	/** Qualifier of the <code>IncidentLine.productBaseCode</code> attribute **/
	public static final String PRODUCTBASECODE = "productBaseCode";
	/** Qualifier of the <code>IncidentLine.sorder</code> attribute **/
	public static final String SORDER = "sorder";
	/** Qualifier of the <code>IncidentLine.invoice_p</code> attribute **/
	public static final String INVOICE_P = "invoice_p";
	/** Qualifier of the <code>IncidentLine.netweight</code> attribute **/
	public static final String NETWEIGHT = "netweight";
	/** Qualifier of the <code>IncidentLine.plant</code> attribute **/
	public static final String PLANT = "plant";
	/** Qualifier of the <code>IncidentLine.quantity</code> attribute **/
	public static final String QUANTITY = "quantity";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PRODUCT, AttributeMode.INITIAL);
		tmp.put(WEIGHTUNIT, AttributeMode.INITIAL);
		tmp.put(QUANTITYTOCLAIM, AttributeMode.INITIAL);
		tmp.put(SALESUNIT, AttributeMode.INITIAL);
		tmp.put(SHIPTODESCRIPTION, AttributeMode.INITIAL);
		tmp.put(PRODUCTDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(SHIPTO, AttributeMode.INITIAL);
		tmp.put(SORDER_P, AttributeMode.INITIAL);
		tmp.put(BATCH, AttributeMode.INITIAL);
		tmp.put(INVOICE, AttributeMode.INITIAL);
		tmp.put(PRODUCTBASECODE, AttributeMode.INITIAL);
		tmp.put(SORDER, AttributeMode.INITIAL);
		tmp.put(INVOICE_P, AttributeMode.INITIAL);
		tmp.put(NETWEIGHT, AttributeMode.INITIAL);
		tmp.put(PLANT, AttributeMode.INITIAL);
		tmp.put(QUANTITY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.batch</code> attribute.
	 * @return the batch
	 */
	public Integer getBatch(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, BATCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.batch</code> attribute.
	 * @return the batch
	 */
	public Integer getBatch()
	{
		return getBatch( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.batch</code> attribute. 
	 * @return the batch
	 */
	public int getBatchAsPrimitive(final SessionContext ctx)
	{
		Integer value = getBatch( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.batch</code> attribute. 
	 * @return the batch
	 */
	public int getBatchAsPrimitive()
	{
		return getBatchAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.batch</code> attribute. 
	 * @param value the batch
	 */
	public void setBatch(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, BATCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.batch</code> attribute. 
	 * @param value the batch
	 */
	public void setBatch(final Integer value)
	{
		setBatch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.batch</code> attribute. 
	 * @param value the batch
	 */
	public void setBatch(final SessionContext ctx, final int value)
	{
		setBatch( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.batch</code> attribute. 
	 * @param value the batch
	 */
	public void setBatch(final int value)
	{
		setBatch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.invoice</code> attribute.
	 * @return the invoice
	 */
	public String getInvoice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.invoice</code> attribute.
	 * @return the invoice
	 */
	public String getInvoice()
	{
		return getInvoice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.invoice</code> attribute. 
	 * @param value the invoice
	 */
	public void setInvoice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.invoice</code> attribute. 
	 * @param value the invoice
	 */
	public void setInvoice(final String value)
	{
		setInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.invoice_p</code> attribute.
	 * @return the invoice_p
	 */
	public String getInvoice_p(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICE_P);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.invoice_p</code> attribute.
	 * @return the invoice_p
	 */
	public String getInvoice_p()
	{
		return getInvoice_p( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.invoice_p</code> attribute. 
	 * @param value the invoice_p
	 */
	public void setInvoice_p(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICE_P,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.invoice_p</code> attribute. 
	 * @param value the invoice_p
	 */
	public void setInvoice_p(final String value)
	{
		setInvoice_p( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.netweight</code> attribute.
	 * @return the netweight
	 */
	public Double getNetweight(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, NETWEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.netweight</code> attribute.
	 * @return the netweight
	 */
	public Double getNetweight()
	{
		return getNetweight( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.netweight</code> attribute. 
	 * @return the netweight
	 */
	public double getNetweightAsPrimitive(final SessionContext ctx)
	{
		Double value = getNetweight( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.netweight</code> attribute. 
	 * @return the netweight
	 */
	public double getNetweightAsPrimitive()
	{
		return getNetweightAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.netweight</code> attribute. 
	 * @param value the netweight
	 */
	public void setNetweight(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, NETWEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.netweight</code> attribute. 
	 * @param value the netweight
	 */
	public void setNetweight(final Double value)
	{
		setNetweight( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.netweight</code> attribute. 
	 * @param value the netweight
	 */
	public void setNetweight(final SessionContext ctx, final double value)
	{
		setNetweight( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.netweight</code> attribute. 
	 * @param value the netweight
	 */
	public void setNetweight(final double value)
	{
		setNetweight( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.plant</code> attribute.
	 * @return the plant
	 */
	public String getPlant(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PLANT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.plant</code> attribute.
	 * @return the plant
	 */
	public String getPlant()
	{
		return getPlant( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.plant</code> attribute. 
	 * @param value the plant
	 */
	public void setPlant(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PLANT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.plant</code> attribute. 
	 * @param value the plant
	 */
	public void setPlant(final String value)
	{
		setPlant( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.product</code> attribute.
	 * @return the product
	 */
	public Product getProduct(final SessionContext ctx)
	{
		return (Product)getProperty( ctx, PRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.product</code> attribute.
	 * @return the product
	 */
	public Product getProduct()
	{
		return getProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.product</code> attribute. 
	 * @param value the product
	 */
	public void setProduct(final SessionContext ctx, final Product value)
	{
		setProperty(ctx, PRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.product</code> attribute. 
	 * @param value the product
	 */
	public void setProduct(final Product value)
	{
		setProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.productBaseCode</code> attribute.
	 * @return the productBaseCode
	 */
	public String getProductBaseCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTBASECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.productBaseCode</code> attribute.
	 * @return the productBaseCode
	 */
	public String getProductBaseCode()
	{
		return getProductBaseCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.productBaseCode</code> attribute. 
	 * @param value the productBaseCode
	 */
	public void setProductBaseCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTBASECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.productBaseCode</code> attribute. 
	 * @param value the productBaseCode
	 */
	public void setProductBaseCode(final String value)
	{
		setProductBaseCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.productDescription</code> attribute.
	 * @return the productDescription
	 */
	public String getProductDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.productDescription</code> attribute.
	 * @return the productDescription
	 */
	public String getProductDescription()
	{
		return getProductDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.productDescription</code> attribute. 
	 * @param value the productDescription
	 */
	public void setProductDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.productDescription</code> attribute. 
	 * @param value the productDescription
	 */
	public void setProductDescription(final String value)
	{
		setProductDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantity</code> attribute.
	 * @return the quantity
	 */
	public Integer getQuantity(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, QUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantity</code> attribute.
	 * @return the quantity
	 */
	public Integer getQuantity()
	{
		return getQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantity</code> attribute. 
	 * @return the quantity
	 */
	public int getQuantityAsPrimitive(final SessionContext ctx)
	{
		Integer value = getQuantity( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantity</code> attribute. 
	 * @return the quantity
	 */
	public int getQuantityAsPrimitive()
	{
		return getQuantityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, QUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final Integer value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final int value)
	{
		setQuantity( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final int value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantityToClaim</code> attribute.
	 * @return the quantityToClaim
	 */
	public Integer getQuantityToClaim(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, QUANTITYTOCLAIM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantityToClaim</code> attribute.
	 * @return the quantityToClaim
	 */
	public Integer getQuantityToClaim()
	{
		return getQuantityToClaim( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantityToClaim</code> attribute. 
	 * @return the quantityToClaim
	 */
	public int getQuantityToClaimAsPrimitive(final SessionContext ctx)
	{
		Integer value = getQuantityToClaim( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.quantityToClaim</code> attribute. 
	 * @return the quantityToClaim
	 */
	public int getQuantityToClaimAsPrimitive()
	{
		return getQuantityToClaimAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantityToClaim</code> attribute. 
	 * @param value the quantityToClaim
	 */
	public void setQuantityToClaim(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, QUANTITYTOCLAIM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantityToClaim</code> attribute. 
	 * @param value the quantityToClaim
	 */
	public void setQuantityToClaim(final Integer value)
	{
		setQuantityToClaim( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantityToClaim</code> attribute. 
	 * @param value the quantityToClaim
	 */
	public void setQuantityToClaim(final SessionContext ctx, final int value)
	{
		setQuantityToClaim( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.quantityToClaim</code> attribute. 
	 * @param value the quantityToClaim
	 */
	public void setQuantityToClaim(final int value)
	{
		setQuantityToClaim( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.salesUnit</code> attribute.
	 * @return the salesUnit
	 */
	public String getSalesUnit(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SALESUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.salesUnit</code> attribute.
	 * @return the salesUnit
	 */
	public String getSalesUnit()
	{
		return getSalesUnit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.salesUnit</code> attribute. 
	 * @param value the salesUnit
	 */
	public void setSalesUnit(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SALESUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.salesUnit</code> attribute. 
	 * @param value the salesUnit
	 */
	public void setSalesUnit(final String value)
	{
		setSalesUnit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.shipTo</code> attribute.
	 * @return the shipTo
	 */
	public String getShipTo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHIPTO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.shipTo</code> attribute.
	 * @return the shipTo
	 */
	public String getShipTo()
	{
		return getShipTo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.shipTo</code> attribute. 
	 * @param value the shipTo
	 */
	public void setShipTo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHIPTO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.shipTo</code> attribute. 
	 * @param value the shipTo
	 */
	public void setShipTo(final String value)
	{
		setShipTo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.shipToDescription</code> attribute.
	 * @return the shipToDescription
	 */
	public String getShipToDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHIPTODESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.shipToDescription</code> attribute.
	 * @return the shipToDescription
	 */
	public String getShipToDescription()
	{
		return getShipToDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.shipToDescription</code> attribute. 
	 * @param value the shipToDescription
	 */
	public void setShipToDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHIPTODESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.shipToDescription</code> attribute. 
	 * @param value the shipToDescription
	 */
	public void setShipToDescription(final String value)
	{
		setShipToDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.sorder</code> attribute.
	 * @return the sorder
	 */
	public String getSorder(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.sorder</code> attribute.
	 * @return the sorder
	 */
	public String getSorder()
	{
		return getSorder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.sorder</code> attribute. 
	 * @param value the sorder
	 */
	public void setSorder(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.sorder</code> attribute. 
	 * @param value the sorder
	 */
	public void setSorder(final String value)
	{
		setSorder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.sorder_p</code> attribute.
	 * @return the sorder_p
	 */
	public String getSorder_p(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SORDER_P);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.sorder_p</code> attribute.
	 * @return the sorder_p
	 */
	public String getSorder_p()
	{
		return getSorder_p( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.sorder_p</code> attribute. 
	 * @param value the sorder_p
	 */
	public void setSorder_p(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SORDER_P,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.sorder_p</code> attribute. 
	 * @param value the sorder_p
	 */
	public void setSorder_p(final String value)
	{
		setSorder_p( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.weightUnit</code> attribute.
	 * @return the weightUnit
	 */
	public String getWeightUnit(final SessionContext ctx)
	{
		return (String)getProperty( ctx, WEIGHTUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>IncidentLine.weightUnit</code> attribute.
	 * @return the weightUnit
	 */
	public String getWeightUnit()
	{
		return getWeightUnit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.weightUnit</code> attribute. 
	 * @param value the weightUnit
	 */
	public void setWeightUnit(final SessionContext ctx, final String value)
	{
		setProperty(ctx, WEIGHTUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>IncidentLine.weightUnit</code> attribute. 
	 * @param value the weightUnit
	 */
	public void setWeightUnit(final String value)
	{
		setWeightUnit( getSession().getSessionContext(), value );
	}
	
}
