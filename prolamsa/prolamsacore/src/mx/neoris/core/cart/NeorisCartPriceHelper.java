/**
 * 
 */
package mx.neoris.core.cart;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.core.model.product.UnitModel;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisCartPriceHelper
{
	public void sortEntriesBySapOrderEntryNumber(final AbstractOrderData orderData);

	public void sortEntriesByReadyToShipDate(final AbstractOrderData orderData);

	public void setGroupingOfOrderData(final AbstractOrderData orderData);

	public void setDefaultGroupingOfOrderData(final AbstractOrderData orderData);

	public void setFormattedSapData(final AbstractOrderData orderData);

	public String getPriceNumberPattern();

	public String getWeightNumberPattern();

	public String getUnitPriceNumberPattern();

	public String getQuantityPattern(final UnitModel unitModel);

	public String getOrderWeightPatterFor(final AbstractOrderData orderData);

	public String getUnitPricePatternFor(AbstractOrderData orderData);

	public String getUnitPriceWOCurrencyPatternFor(AbstractOrderData orderData);

	public void setFormattedEntriesQuantity(final AbstractOrderData orderData);

	public String getWeightKgNumberPattern();

	public String getTonPattern();
}