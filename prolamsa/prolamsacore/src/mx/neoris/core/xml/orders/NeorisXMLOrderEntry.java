/**
 * 
 */
package mx.neoris.core.xml.orders;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * @author fdeutsch
 * 
 */
@XmlRootElement(name = "ORDERENTRY")
@XmlType(name = "ORDERENTRY")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeorisXMLOrderEntry
{
	@XmlAttribute(name = "LINEITEM")
	Integer lineItem;

	@XmlAttribute(name = "PRODUCTID")
	String productId;

	@XmlAttribute(name = "STATUS")
	String status;

	@XmlAttribute(name = "QUANTITY")
	Double quantity;

	@XmlAttribute(name = "READYTOSHIP")
	@XmlSchemaType(name = "READYTOSHIP")
	Date readyToShip;

	@XmlAttribute(name = "WEIGHT")
	Double weight;

	@XmlAttribute(name = "LOCATION")
	String location;

	@XmlAttribute(name = "PRICEPERFEET")
	Double pricePerFeet;

	@XmlAttribute(name = "PRICEPERTON")
	Double pricePerTon;

	@XmlAttribute(name = "PRICEPERPIEZA")
	Double pricePerPc;

	@XmlAttribute(name = "NETPRICEWOTAXES")
	Double netPriceWOTaxes;

	@XmlAttribute(name = "SEGURO")
	Double assurance;

	@XmlAttribute(name = "FLETE")
	Double delvieryCost;

	@XmlAttribute(name = "IVA")
	Double taxas;

	@XmlAttribute(name = "PRESSURE")
	String pressure;

	@XmlAttribute(name = "DURATION")
	String duration;

	@XmlAttribute(name = "SPECIFICSTENCIL")
	String specificStencil;

	@XmlAttribute(name = "SPECIALDRIFTER")
	String specialDrifter;

	@XmlAttribute(name = "SPECIALLENGTH")
	String specialLength;

	@XmlAttribute(name = "CHARPY")
	String charpy;

	@XmlAttribute(name = "CHARPYTEMPERATURE")
	String charpyTemperature;

	@XmlAttribute(name = "CHARPYDIRECTION")
	String charpyDirection;

	@XmlAttribute(name = "CHARPYSIZE")
	String charpySize;

	@XmlAttribute(name = "STEEL")
	String steel;

	@XmlAttribute(name = "CURRENCY")
	String currency;
	
	@XmlAttribute(name = "WEIGHTPERTON")
	Double weightPerTon;

	/**
	 * @return the weightPerTon
	 */
	public Double getWeightPerTon()
	{
		return weightPerTon;
	}

	/**
	 * @param weightPerTon
	 *           the weightPerTon to set
	 */
	public void setWeightPerTon(final Double weightPerTon)
	{
		this.weightPerTon = weightPerTon;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency()
	{
		return currency;
	}

	/**
	 * @param currency
	 *           the currency to set
	 */
	public void setCurrency(final String currency)
	{
		this.currency = currency;
	}

	/**
	 * @return the steel
	 */
	public String getSteel()
	{
		return steel;
	}

	/**
	 * @param steel
	 *           the steel to set
	 */
	public void setSteel(final String steel)
	{
		this.steel = steel;
	}

	/**
	 * @return the lineItem
	 */
	public Integer getLineItem()
	{
		return lineItem;
	}

	/**
	 * @param lineItem
	 *           the lineItem to set
	 */
	public void setLineItem(final Integer lineItem)
	{
		this.lineItem = lineItem;
	}

	/**
	 * @return the productId
	 */
	public String getProductId()
	{
		return productId;
	}

	/**
	 * @param productId
	 *           the productId to set
	 */
	public void setProductId(final String productId)
	{
		this.productId = productId;
	}

	/**
	 * @return the quantity
	 */
	public Double getQuantity()
	{
		return quantity;
	}

	/**
	 * @param quantity
	 *           the quantity to set
	 */
	public void setQuantity(final Double quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * @return the readyToShip
	 */
	public Date getReadyToShip()
	{
		return readyToShip;
	}

	/**
	 * @param readyToShip
	 *           the readyToShip to set
	 */
	public void setReadyToShip(final Date readyToShip)
	{
		this.readyToShip = readyToShip;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight()
	{
		return weight;
	}

	/**
	 * @param weight
	 *           the weight to set
	 */
	public void setWeight(final Double weight)
	{
		this.weight = weight;
	}

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *           the location to set
	 */
	public void setLocation(final String location)
	{
		this.location = location;
	}

	/**
	 * @return the pricePerFeet
	 */
	public Double getPricePerFeet()
	{
		return pricePerFeet;
	}

	/**
	 * @param pricePerFeet
	 *           the pricePerFeet to set
	 */
	public void setPricePerFeet(final Double pricePerFeet)
	{
		this.pricePerFeet = pricePerFeet;
	}

	/**
	 * @return the pricePerTon
	 */
	public Double getPricePerTon()
	{
		return pricePerTon;
	}

	/**
	 * @param pricePerTon
	 *           the pricePerTon to set
	 */
	public void setPricePerTon(final Double pricePerTon)
	{
		this.pricePerTon = pricePerTon;
	}



	/**
	 * @return the pricePerPc
	 */
	public Double getPricePerPc()
	{
		return pricePerPc;
	}

	/**
	 * @param pricePerPc
	 *           the pricePerPc to set
	 */
	public void setPricePerPc(final Double pricePerPc)
	{
		this.pricePerPc = pricePerPc;
	}

	/**
	 * @return the netPriceWOTaxes
	 */
	public Double getNetPriceWOTaxes()
	{
		return netPriceWOTaxes;
	}

	/**
	 * @param netPriceWOTaxes
	 *           the netPriceWOTaxes to set
	 */
	public void setNetPriceWOTaxes(final Double netPriceWOTaxes)
	{
		this.netPriceWOTaxes = netPriceWOTaxes;
	}

	/**
	 * @return the pressure
	 */
	public String getPressure()
	{
		return pressure;
	}

	/**
	 * @param pressure
	 *           the pressure to set
	 */
	public void setPressure(final String pressure)
	{
		this.pressure = pressure;
	}

	/**
	 * @return the duration
	 */
	public String getDuration()
	{
		return duration;
	}

	/**
	 * @param duration
	 *           the duration to set
	 */
	public void setDuration(final String duration)
	{
		this.duration = duration;
	}

	/**
	 * @return the specificStencil
	 */
	public String getSpecificStencil()
	{
		return specificStencil;
	}

	/**
	 * @param specificStencil
	 *           the specificStencil to set
	 */
	public void setSpecificStencil(final String specificStencil)
	{
		this.specificStencil = specificStencil;
	}

	/**
	 * @return the specialDrifter
	 */
	public String getSpecialDrifter()
	{
		return specialDrifter;
	}

	/**
	 * @param specialDrifter
	 *           the specialDrifter to set
	 */
	public void setSpecialDrifter(final String specialDrifter)
	{
		this.specialDrifter = specialDrifter;
	}

	/**
	 * @return the specialLength
	 */
	public String getSpecialLength()
	{
		return specialLength;
	}

	/**
	 * @param specialLength
	 *           the specialLength to set
	 */
	public void setSpecialLength(final String specialLength)
	{
		this.specialLength = specialLength;
	}

	/**
	 * @return the charpy
	 */
	public String getCharpy()
	{
		return charpy;
	}

	/**
	 * @param charpy
	 *           the charpy to set
	 */
	public void setCharpy(final String charpy)
	{
		this.charpy = charpy;
	}

	/**
	 * @return the charpyTemperature
	 */
	public String getCharpyTemperature()
	{
		return charpyTemperature;
	}

	/**
	 * @param charpyTemperature
	 *           the charpyTemperature to set
	 */
	public void setCharpyTemperature(final String charpyTemperature)
	{
		this.charpyTemperature = charpyTemperature;
	}

	/**
	 * @return the charpyDirection
	 */
	public String getCharpyDirection()
	{
		return charpyDirection;
	}

	/**
	 * @param charpyDirection
	 *           the charpyDirection to set
	 */
	public void setCharpyDirection(final String charpyDirection)
	{
		this.charpyDirection = charpyDirection;
	}

	/**
	 * @return the charpySize
	 */
	public String getCharpySize()
	{
		return charpySize;
	}

	/**
	 * @param charpySize
	 *           the charpySize to set
	 */
	public void setCharpySize(final String charpySize)
	{
		this.charpySize = charpySize;
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
	 * @return the assurance
	 */
	public Double getAssurance()
	{
		return assurance;
	}

	/**
	 * @param assurance
	 *           the assurance to set
	 */
	public void setAssurance(final Double assurance)
	{
		this.assurance = assurance;
	}

	/**
	 * @return the delvieryCost
	 */
	public Double getDelvieryCost()
	{
		return delvieryCost;
	}

	/**
	 * @param delvieryCost
	 *           the delvieryCost to set
	 */
	public void setDelvieryCost(final Double delvieryCost)
	{
		this.delvieryCost = delvieryCost;
	}

	/**
	 * @return the taxas
	 */
	public Double getTaxas()
	{
		return taxas;
	}

	/**
	 * @param taxas
	 *           the taxas to set
	 */
	public void setTaxas(final Double taxas)
	{
		this.taxas = taxas;
	}


}
