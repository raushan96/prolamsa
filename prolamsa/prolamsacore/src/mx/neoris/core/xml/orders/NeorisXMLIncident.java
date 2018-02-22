/**
 * 
 */
package mx.neoris.core.xml.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author hector.castaneda
 * 
 */

@XmlRootElement(name = "INCIDENTDATA")
@XmlType(name = "INCIDENTDATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeorisXMLIncident
{
	@XmlAttribute(name = "ACTION")
	String action;

	@XmlAttribute(name = "ID")
	String id;

	@XmlAttribute(name = "STATUS")
	String status;

	//
	//	@XmlAttribute(name = "SALESORGANIZATION")
	//	String salesOrganization;
	//
	//	@XmlAttribute(name = "BILLTO")
	//	String billTo;

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

	//	/**
	//	 * @return the salesOrganization
	//	 */
	//	public String getSalesOrganization()
	//	{
	//		return salesOrganization;
	//	}
	//
	//	/**
	//	 * @param salesOrganization
	//	 *           the salesOrganization to set
	//	 */
	//	public void setSalesOrganization(final String salesOrganization)
	//	{
	//		this.salesOrganization = salesOrganization;
	//	}
	//
	//	/**
	//	 * @return the billTo
	//	 */
	//	public String getBillTo()
	//	{
	//		return billTo;
	//	}
	//
	//	/**
	//	 * @param billTo
	//	 *           the billTo to set
	//	 */
	//	public void setBillTo(final String billTo)
	//	{
	//		this.billTo = billTo;
	//	}
}
