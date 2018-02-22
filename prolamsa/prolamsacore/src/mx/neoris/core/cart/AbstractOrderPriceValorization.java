/**
 * 
 */
package mx.neoris.core.cart;

import java.util.Map;


/**
 * @author fdeutsch
 * 
 */
public class AbstractOrderPriceValorization
{
	Double hotRolledWeight;
	Double galvanizedWeight;
	Double totalWeight;

	Double hotRolledPrice;
	Double galvanizedPrice;
	Double totalPrice;

	Map<Integer, AbstractOrderEntryPriceValorization> entries;

	/**
	 * @return the hotRolledWeight
	 */
	public Double getHotRolledWeight()
	{
		return hotRolledWeight;
	}

	/**
	 * @param hotRolledWeight
	 *           the hotRolledWeight to set
	 */
	public void setHotRolledWeight(final Double hotRolledWeight)
	{
		this.hotRolledWeight = hotRolledWeight;
	}

	/**
	 * @return the galvanizedWeight
	 */
	public Double getGalvanizedWeight()
	{
		return galvanizedWeight;
	}

	/**
	 * @param galvanizedWeight
	 *           the galvanizedWeight to set
	 */
	public void setGalvanizedWeight(final Double galvanizedWeight)
	{
		this.galvanizedWeight = galvanizedWeight;
	}

	/**
	 * @return the totalWeight
	 */
	public Double getTotalWeight()
	{
		return totalWeight;
	}

	/**
	 * @param totalWeight
	 *           the totalWeight to set
	 */
	public void setTotalWeight(final Double totalWeight)
	{
		this.totalWeight = totalWeight;
	}

	/**
	 * @return the hotRolledPrice
	 */
	public Double getHotRolledPrice()
	{
		return hotRolledPrice;
	}

	/**
	 * @param hotRolledPrice
	 *           the hotRolledPrice to set
	 */
	public void setHotRolledPrice(final Double hotRolledPrice)
	{
		this.hotRolledPrice = hotRolledPrice;
	}

	/**
	 * @return the galvanizedPrice
	 */
	public Double getGalvanizedPrice()
	{
		return galvanizedPrice;
	}

	/**
	 * @param galvanizedPrice
	 *           the galvanizedPrice to set
	 */
	public void setGalvanizedPrice(final Double galvanizedPrice)
	{
		this.galvanizedPrice = galvanizedPrice;
	}

	/**
	 * @return the totalPrice
	 */
	public Double getTotalPrice()
	{
		return totalPrice;
	}

	/**
	 * @param totalPrice
	 *           the totalPrice to set
	 */
	public void setTotalPrice(final Double totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the entries
	 */
	public Map<Integer, AbstractOrderEntryPriceValorization> getEntries()
	{
		return entries;
	}

	/**
	 * @param entries
	 *           the entries to set
	 */
	public void setEntries(final Map<Integer, AbstractOrderEntryPriceValorization> entries)
	{
		this.entries = entries;
	}

}
