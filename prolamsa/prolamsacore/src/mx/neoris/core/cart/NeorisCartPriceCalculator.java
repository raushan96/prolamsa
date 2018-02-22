/**
 * 
 */
package mx.neoris.core.cart;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisCartPriceCalculator
{
	public abstract void calculatePrices(AbstractOrderData orderData) throws Exception;

}