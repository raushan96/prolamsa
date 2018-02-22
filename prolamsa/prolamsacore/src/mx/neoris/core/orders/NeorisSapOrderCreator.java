/**
 * 
 */
package mx.neoris.core.orders;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;


/**
 * @author Tulum
 * 
 */
public interface NeorisSapOrderCreator
{
	public abstract void createSapOrderFor(OrderModel orderModel) throws Exception;

	public abstract void createSapQuoteOrderFor(OrderModel orderModel) throws Exception;

	public abstract void rejectOrAcceptSapOrderForCode(String orderCode, String comment, String rejectOrAccept) throws Exception;

	public abstract void createSapQuoteOrderWithCart(OrderModel orderModel, CartModel cartModel) throws Exception;

	public abstract void createSapOrderByQuote(OrderModel orderModel, OrderModel quote) throws Exception;

	public void createSapQuoteFromOrder(final OrderModel orderModel) throws Exception;
}
