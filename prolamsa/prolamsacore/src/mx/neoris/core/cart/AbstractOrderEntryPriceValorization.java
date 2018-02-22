/**
 * 
 */
package mx.neoris.core.cart;

import java.util.Date;

import mx.neoris.core.enums.ProductLocation;


/**
 * @author fdeutsch
 * 
 */
public class AbstractOrderEntryPriceValorization
{
	Integer entryNumber;
	String productCode;
	Long quantity;
	ProductLocation location;
	Date rollingSchedule;
	Double weight;
	Double price;

	/**
	 * @return the entryNumber
	 */
	public Integer getEntryNumber()
	{
		return entryNumber;
	}

	/**
	 * @param entryNumber
	 *           the entryNumber to set
	 */
	public void setEntryNumber(final Integer entryNumber)
	{
		this.entryNumber = entryNumber;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return productCode;
	}

	/**
	 * @param productCode
	 *           the productCode to set
	 */
	public void setProductCode(final String productCode)
	{
		this.productCode = productCode;
	}

	/**
	 * @return the quantity
	 */
	public Long getQuantity()
	{
		return quantity;
	}

	/**
	 * @param quantity
	 *           the quantity to set
	 */
	public void setQuantity(final Long quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * @return the location
	 */
	public ProductLocation getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *           the location to set
	 */
	public void setLocation(final ProductLocation location)
	{
		this.location = location;
	}

	/**
	 * @return the rollingSchedule
	 */
	public Date getRollingSchedule()
	{
		return rollingSchedule;
	}

	/**
	 * @param rollingSchedule
	 *           the rollingSchedule to set
	 */
	public void setRollingSchedule(final Date rollingSchedule)
	{
		this.rollingSchedule = rollingSchedule;
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
	 * @return the price
	 */
	public Double getPrice()
	{
		return price;
	}

	/**
	 * @param price
	 *           the price to set
	 */
	public void setPrice(final Double price)
	{
		this.price = price;
	}
}
