/**
 * 
 */
package mx.neoris.core.orders;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Random;

import javax.annotation.Resource;


/**
 * @author Tulum
 * 
 */
public class DefaultNeorisSapOrderCreator implements NeorisSapOrderCreator
{
	@Resource(name = "modelService")
	private ModelService modelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.orders.NeorisSapOrdersCreator#createSapOrderFor(de.hybris.platform.core.model.order.OrderModel)
	 */
	@Override
	public void createSapOrderFor(final OrderModel orderModel) throws Exception
	{
		// YTODO Auto-generated method stub

		final Random rnd = new Random();

		final String sapOrderID = String.valueOf(rnd.nextInt());

		orderModel.setCode(sapOrderID);
		orderModel.setSapOrderId(sapOrderID);

		//		if (orderModel.getEntries().size() > 4)
		//		{
		//			throw new Exception("simulated order registry failed");
		//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.orders.NeorisSapOrdersCreator#createSapQuoteOrderFor(de.hybris.platform.core.model.order.OrderModel
	 * )
	 */
	@Override
	public void createSapQuoteOrderFor(final OrderModel orderModel) throws Exception
	{
		// YTODO Auto-generated method stub
		final Random rnd = new Random();

		final String sapOrderID = String.valueOf(rnd.nextInt());

		orderModel.setCode(sapOrderID);
		orderModel.setSapOrderId(sapOrderID);

		//		if (orderModel.getEntries().size() > 4)
		//		{
		//			throw new Exception("simulated order registry failed");
		//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.orders.NeorisSapOrderCreator#rejectOrAcceptSapOrderForCode(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void rejectOrAcceptSapOrderForCode(final String orderCode, final String comment, final String rejectOrAccept)
			throws Exception
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.orders.NeorisSapOrderCreator#createSapQuoteOrderWithCart(de.hybris.platform.core.model.order.OrderModel
	 * , de.hybris.platform.core.model.order.CartModel)
	 */
	@Override
	public void createSapQuoteOrderWithCart(final OrderModel orderModel, final CartModel cartModel) throws Exception
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.orders.NeorisSapOrderCreator#createSapOrderByQuote(de.hybris.platform.core.model.order.OrderModel,
	 * de.hybris.platform.core.model.order.OrderModel)
	 */
	@Override
	public void createSapOrderByQuote(final OrderModel orderModel, final OrderModel quote) throws Exception
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.orders.NeorisSapOrderCreator#createSapQuoteFromOrder(de.hybris.platform.core.model.order.OrderModel
	 * )
	 */
	@Override
	public void createSapQuoteFromOrder(final OrderModel orderModel) throws Exception
	{
		// YTODO Auto-generated method stub

	}


}
