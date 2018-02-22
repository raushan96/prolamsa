/**
 * 
 */
package mx.neoris.core.event;


import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;


/**
 * 
 */
public class QuoteRequestedEvent extends AbstractCommerceUserEvent<BaseSiteModel> implements ClusterAwareEvent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderModel order;

	public QuoteRequestedEvent(final OrderModel order)
	{
		this.order = order;
	}

	public OrderModel getOrder()
	{
		return this.order;
	}

	public void setOrder(final OrderModel order)
	{
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.event.ClusterAwareEvent#publish(int, int)
	 */
	@Override
	public boolean publish(final int paramInt1, final int paramInt2)
	{
		// YTODO Auto-generated method stub
		return paramInt1 == paramInt2;
	}

}
