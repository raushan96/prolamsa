/**
 * 
 */
package mx.neoris.core.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.services.NeorisProductInventoryService;


/**
 * @author fdeutsch
 * 
 */
public class MockupNeorisProductInventoryService implements NeorisProductInventoryService
{
	public Map<String, ProductInventoryEntry> getProductInventoryEntriesFor(final List<String> productCodes,
			final List<String> locationCodes, final String customerType) throws Exception
	{
		final Map<String, ProductInventoryEntry> map = new HashMap<String, ProductInventoryEntry>();

		final int size = productCodes.size();

		// iterate through the product codes
		for (int i = 0; i < size; i++)
		{
			final String eachProductCode = productCodes.get(i);
			final String eachLocationCode = locationCodes.get(i);

			// get the location from the code
			final ProductLocation location = ProductLocation.valueOf(locationCodes.get(i));
			// set the entry to the code
			map.put(eachProductCode + eachLocationCode, getProductInventoryEntryFor(eachProductCode, location, customerType));
		}

		return map;
	}

	private ProductInventoryEntry getProductInventoryEntryFor(final String productCode, final ProductLocation location,
			final String customerType) throws Exception
	{
		final List<Date> dates = new ArrayList<Date>();

		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.set(Calendar.MONTH, 5);

		cal.set(Calendar.DAY_OF_MONTH, 6);
		dates.add(cal.getTime());

		cal.add(Calendar.DAY_OF_MONTH, 7);
		dates.add(cal.getTime());

		cal.add(Calendar.DAY_OF_MONTH, 7);
		dates.add(cal.getTime());

		cal.add(Calendar.DAY_OF_MONTH, 7);
		dates.add(cal.getTime());

		final ProductInventoryEntry entry = new ProductInventoryEntry();
		entry.setRollingScheduleDates(dates);

		entry.setLocation(location);

		entry.setAvailableStockBundles(0);
		entry.setAvailableStockBundlesInternal(0);

		if ("prolamsa_internal".equals(customerType))
		{
			entry.setAvailableStockBundles(50);
			entry.setAvailableStockBundlesInternal(120);
		}
		else
		{
			entry.setAvailableStockBundles(4);
		}

		// fake no inventory for product 300225
		if ("300225".equals(productCode))
		{
			entry.setAvailableStockBundles(0);
		}

		entry.setRollingScheduleBundles(150);

		return entry;
	}
}
