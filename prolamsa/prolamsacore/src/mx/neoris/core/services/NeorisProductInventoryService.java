/**
 * 
 */
package mx.neoris.core.services;

import java.util.List;
import java.util.Map;

import mx.neoris.core.product.ProductInventoryEntry;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisProductInventoryService
{
	Map<String, ProductInventoryEntry> getProductInventoryEntriesFor(List<String> productCodes, List<String> locationCodes,
			String customerType) throws Exception;
}
