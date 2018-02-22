/**
 * 
 */
package mx.neoris.core.uom;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.UnitModel;

import java.util.List;



/**
 * @author fdeutsch
 * 
 */
public interface NeorisUoMQuantityConverter
{
	Long convertInputQuantityFrom(UnitModel uom, double quantity, ProductData productData);

	Double convertOutputQuantityFrom(UnitModel uom, Long quantity, ProductData productData);

	Double convertOutputQuantityFrom(UnitModel uom, Long quantity, ProductData productData, Boolean isQuantityInPieces);

	List<Double> convertOutputQuantiesFrom(UnitModel uom, Long availableQty, ProductData productData);

	void injectInventoryConvertedQuantitiesOn(UnitModel uom, List<ProductData> col);
}