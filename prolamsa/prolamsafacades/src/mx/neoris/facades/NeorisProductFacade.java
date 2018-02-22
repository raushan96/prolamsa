/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;
import java.util.Map;

import mx.neoris.core.product.ProductInventoryEntry;

/**
 * @author fdeutsch
 *
 */
public interface NeorisProductFacade
{
	// inventory
	void injectProductInventoryEntriesOn(List<ProductData> data, String customerType);
	Map<String,ProductInventoryEntry> getProductInventoryEntriesFor(final List<String> productCodes, List<String> locationCodes, String customerType) throws Exception;

	// customized customer name and description
	void injectCustomerNameAndDescriptionDataOn(List<ProductData> prolamsaProductDatas);
	void injectCustomerNameAndDescriptionDataOnOrderEntries(List<OrderEntryData> prolamsaOrderEntriesData);
	// NEORIS_CHANGE #74 when creating an order confirmation email we need to pass the b2bunit to get the customer product reference
	void injectCustomerNameAndDescriptionDataOn(List<OrderEntryData> orderEntryDatas, B2BUnitModel b2bUnitModel);

	// NEORIS_CHANGE #74
	String getProductLocationCodeForName(String name);

	//NEORIS_CHANGE #79
	ProductModel getProductByCode(String code);
	
	//Agrega al producto la información que se obtiene desde el UploadExcel
	void injectProductInfoExcel(List<ProductData> productDataList, Map<String,Double> quantityMap, List<String> skuList);
	
	void injectCustomerNameAndDescriptionDataOnEmail(List<OrderEntryData> orderEntryDatas, B2BUnitModel b2bUnitModel);
	
	
	
}