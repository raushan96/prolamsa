/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;


public interface NeorisCommerceCheckoutService
{
	boolean setBillingAddress(CartModel cartModel, AddressModel addressModel);

	boolean saveSAPPrices(AbstractOrderModel orderModel, AbstractOrderData cartData);

	boolean saveSAPWeight(AbstractOrderModel orderModel, AbstractOrderData cartData);
}
