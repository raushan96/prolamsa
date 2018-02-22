package mx.neoris.core.xml.orders;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import mx.neoris.core.xml.shippingInstructions.NeorisXMLShippingInstructionsCol;




/**
 * @author fdeutsch
 * 
 */

@XmlRootElement(name = "ORDERDATA")
@XmlType(name = "ORDERDATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeorisXMLOrder
{
	@XmlAttribute(name = "ACTION")
	String action;

	@XmlAttribute(name = "TYPEOP")
	String type;

	@XmlAttribute(name = "ID")
	String id;

	@XmlAttribute(name = "SALESORGANIZATION")
	String salesOrganization;

	@XmlAttribute(name = "BILLTO")
	String billTo;

	@XmlAttribute(name = "DELIVERYADDRESS")
	String deliveryAddress;

	@XmlAttribute(name = "STATUS")
	String status;

	@XmlAttribute(name = "CREATIONDATE")
	@XmlSchemaType(name = "CREATIONDATE")
	Date creationDate;

	@XmlAttribute(name = "PO")
	String po;

	@XmlAttribute(name = "TRANSPORTATIONMODE")
	String transportationMode;

	@XmlAttribute(name = "USER")
	String user;

	@XmlAttribute(name = "UOM")
	String uom;

	@XmlAttribute(name = "INCOTERM")
	String incoterm;

	@XmlAttribute(name = "TPAGO")
	String tipoPago;

	@XmlElement(name = "COMMENTS")
	String comments;

	@XmlElement(name = "ENTRIES")
	NeorisXMLOrderEntryCol orderEntryCol;

	@XmlAttribute(name = "REQUESTEDDELIVERYDATE")
	Date requestedDeliveryDate;

	@XmlAttribute(name = "UPRECIO")
	String uPrecio;

	@XmlElement(name = "SHIPINST")
	NeorisXMLShippingInstructionsCol shippingInstructionsCol;

	/**
	 * @return the tipoPago
	 */
	public String getTipoPago()
	{
		return tipoPago;
	}

	/**
	 * @param tipoPago
	 *           the tipoPago to set
	 */
	public void setTipoPago(final String tipoPago)
	{
		this.tipoPago = tipoPago;
	}

	/**
	 * @return the incoterm
	 */
	public String getIncoterm()
	{
		return incoterm;
	}

	/**
	 * @param incoterm
	 *           the incoterm to set
	 */
	public void setIncoterm(final String incoterm)
	{
		this.incoterm = incoterm;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *           the id to set
	 */
	public void setId(final String id)
	{
		this.id = id;
	}

	/**
	 * @return the salesOrganization
	 */
	public String getSalesOrganization()
	{
		return salesOrganization;
	}

	/**
	 * @param salesOrganization
	 *           the salesOrganization to set
	 */
	public void setSalesOrganization(final String salesOrganization)
	{
		this.salesOrganization = salesOrganization;
	}

	/**
	 * @return the billTo
	 */
	public String getBillTo()
	{
		return billTo;
	}

	/**
	 * @param billTo
	 *           the billTo to set
	 */
	public void setBillTo(final String billTo)
	{
		this.billTo = billTo;
	}

	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress()
	{
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress
	 *           the deliveryAddress to set
	 */
	public void setDeliveryAddress(final String deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate
	 *           the creationDate to set
	 */
	public void setCreationDate(final Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return the po
	 */
	public String getPo()
	{
		return po;
	}

	/**
	 * @param po
	 *           the po to set
	 */
	public void setPo(final String po)
	{
		this.po = po;
	}

	/**
	 * @return the transportationMode
	 */
	public String getTransportationMode()
	{
		return transportationMode;
	}

	/**
	 * @param transportationMode
	 *           the transportationMode to set
	 */
	public void setTransportationMode(final String transportationMode)
	{
		this.transportationMode = transportationMode;
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *           the user to set
	 */
	public void setUser(final String user)
	{
		this.user = user;
	}

	/**
	 * @return the uom
	 */
	public String getUom()
	{
		return uom;
	}

	/**
	 * @param uom
	 *           the uom to set
	 */
	public void setUom(final String uom)
	{
		this.uom = uom;
	}

	/**
	 * @return the orderEntryCol
	 */
	public NeorisXMLOrderEntryCol getOrderEntryCol()
	{
		return orderEntryCol;
	}

	/**
	 * @param orderEntryCol
	 *           the orderEntryCol to set
	 */
	public void setOrderEntryCol(final NeorisXMLOrderEntryCol orderEntryCol)
	{
		this.orderEntryCol = orderEntryCol;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *           the type to set
	 */
	public void setType(final String type)
	{
		this.type = type;
	}

	/**
	 * @return the comments
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * @param comments
	 *           the comments to set
	 */
	public void setComments(final String comments)
	{
		this.comments = comments;
	}

	/**
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * @param action
	 *           the action to set
	 */
	public void setAction(final String action)
	{
		this.action = action;
	}

	/**
	 * @return the requestedDeliveryDate
	 */
	public Date getRequestedDeliveryDate()
	{
		return requestedDeliveryDate;
	}

	/**
	 * @param requestedDeliveryDate
	 *           the requestedDeliveryDate to set
	 */
	public void setRequestedDeliveryDate(final Date requestedDeliveryDate)
	{
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	/**
	 * @return the uPrecio
	 */
	public String getuPrecio()
	{
		return uPrecio;
	}

	/**
	 * @param uPrecio
	 *           the uPrecio to set
	 */
	public void setuPrecio(final String uPrecio)
	{
		this.uPrecio = uPrecio;
	}

	/**
	 * @return the shippingInstructionsCol
	 */
	public NeorisXMLShippingInstructionsCol getShippingInstructionsCol()
	{
		return shippingInstructionsCol;
	}

	/**
	 * @param shippingInstructionsCol
	 *           the shippingInstructionsCol to set
	 */
	public void setShippingInstructionsCol(final NeorisXMLShippingInstructionsCol shippingInstructionsCol)
	{
		this.shippingInstructionsCol = shippingInstructionsCol;
	}
}
