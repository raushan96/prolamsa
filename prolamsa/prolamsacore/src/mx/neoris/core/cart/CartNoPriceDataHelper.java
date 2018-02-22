/**
 *
 */
package mx.neoris.core.cart;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


/**
 * @author raushankumar
 *
 */
public class CartNoPriceDataHelper
{
	public List<OrderEntryData> getNoPriceCartEntries(final CartData cartData)
	{
		List<OrderEntryData> noPriceEntries = null;
		if (CollectionUtils.isNotEmpty(cartData.getEntries()))
		{
			for (final OrderEntryData oe : cartData.getEntries())
			{
				if (null == oe.getPricePerFeet() && null == oe.getPricePerPc() && null == oe.getPricePerTon())
				{
					if (null == noPriceEntries)
					{
						noPriceEntries = new ArrayList<>();
					}
					noPriceEntries.add(oe);
				}
			}
		}
		return noPriceEntries;
	}
}
