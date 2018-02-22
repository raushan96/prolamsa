/**
 * 
 */
package mx.neoris.core.commerceservices.order.impl;

import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCartCalculationStrategy;
import de.hybris.platform.core.model.order.CartModel;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisCommerceCartCalculationStrategy extends DefaultCommerceCartCalculationStrategy
{
	@Override
	public boolean calculateCart(final CartModel cartModel)
	{
		return true;
	}
}
