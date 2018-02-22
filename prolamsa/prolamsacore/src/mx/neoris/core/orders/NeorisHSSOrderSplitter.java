/**
 * 
 */
package mx.neoris.core.orders;

import de.hybris.platform.core.model.order.OrderModel;


/**
 * @author Tulum
 * 
 */
public interface NeorisHSSOrderSplitter
{

	public OrderModel split(OrderModel orderModel);
}
