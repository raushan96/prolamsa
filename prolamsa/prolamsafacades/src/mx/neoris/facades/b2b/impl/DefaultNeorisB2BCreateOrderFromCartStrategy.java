/**
 * 
 */
package mx.neoris.facades.b2b.impl;

import de.hybris.platform.b2b.strategies.impl.DefaultB2BCreateOrderFromCartStrategy;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.NeorisCommerceCheckoutService;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointQuoteNegotiationWebService;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.flow.impl.DefaultB2BCheckoutFlowFacade;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisB2BCreateOrderFromCartStrategy extends DefaultB2BCreateOrderFromCartStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisB2BCreateOrderFromCartStrategy.class);

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name="defaultB2BCheckoutFlowFacade")
	private DefaultB2BCheckoutFlowFacade defaultB2BCheckoutFlowFacade;

	@Resource(name = "neorisDefaultCommerceCheckoutService")
	private NeorisCommerceCheckoutService neorisDefaultCommerceCheckoutService;

	@Resource(name = "neorisCartPriceCalculator")
	private NeorisCartPriceCalculator neorisCartPriceCalculator;

	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;

	@Resource(name = "neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrdersCreator;
	
	@Resource(name="modelService")
	private ModelService modelService;
	
	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;
	
	@Resource(name = "unitService")
	private UnitService unitService;
	
	@Resource(name = "neorisSharePointQuoteNegotiationWebService")
	private NeorisSharePointQuoteNegotiationWebService neorisSharePointQuoteNegotiationWebService;
	
	public static final Integer MAX_STACK_TRACE_SIZE = 4096;

	@Override
	protected void postProcessOrder(final CartModel cartModel, final OrderModel orderModel)
	{
		// write to SAP
		final CartData cartData = neorisCartFacade.getSessionCart();

		UnitModel currentUnit = null;
		
		if(orderModel.getIsAPIOrder() != null && orderModel.getIsAPIOrder())
		{
			// For API orders always place Ft as unit
			currentUnit = unitService.getUnitForCode("prolamsa_ft");
		}
		else
		{
			currentUnit = neorisFacade.getCurrentUnit();			
		}
		
		orderModel.setUnitWhenPlaced(currentUnit);
		orderModel.setTPago(cartData.getTPago());

		try
		{
			// NEORIS_CHANGE #74
			//neorisCartPriceCalculator.calculatePrices(cartData);
			
			//Change to remove gruopind data and keep the order en in the entries
			//neorisCartPriceHelper.sortEntriesByReadyToShipDate(cartData);
			//neorisCartPriceHelper.setGroupingOfOrderData(cartData);
			neorisCartPriceHelper.setDefaultGroupingOfOrderData(cartData);
			neorisCartPriceHelper.setFormattedSapData(cartData);

			//neorisDefaultCommerceCheckoutService.saveSAPWeight(orderModel, cartData);
			//neorisDefaultCommerceCheckoutService.saveSAPPrices(orderModel, cartData);

//			final boolean isQuoteOrder = !cartModel.getB2bcomments().isEmpty();
//			
//			if (isQuoteOrder)
//			{
//				// publishing quote to SAP
//				neorisSapOrdersCreator.createSapQuoteOrderWithCart(orderModel, cartModel);
//				
//				// publishing negotiation quote to Sharepoint
//				if (orderModel.getHasNegPrices())
//				{
//					try
//					{
//						final String negotiationSharePointId = neorisSharePointQuoteNegotiationWebService
//								.placeQuoteNegotiationToSharePoint(orderModel);
//
//						if (StringUtils.isNotBlank(negotiationSharePointId))
//						{
//							LOG.info("Quote negotation: " + orderModel.getCode() + " published on Sharepoint with id: " + negotiationSharePointId);
//							orderModel.setSharePointId(negotiationSharePointId);
//						}
//					}
//					catch (final Exception e)
//					{
//						final String message = e.getMessage().substring(0, Math.min(MAX_STACK_TRACE_SIZE, e.getMessage().length() - 1));
//						orderModel.setSharePointErrorTrace(message);
//						
//						LOG.error("Error publishing negotiation to Sharepoint due to: " + e.getMessage());
//					}
//				}
//			}
//			else
//			{
//				// publishing order to SAP
//				neorisSapOrdersCreator.createSapOrderFor(orderModel);
//			}
			
			modelService.save(orderModel);
		}
		catch (final Exception e)
		{
			LOG.error("error while postProcessOrder", e);
			
			throw new RuntimeException(e);
		}

		if (LOG.isDebugEnabled())
		{
			LOG.debug(String.format("Post processing a b2b order %s created from cart %s", new Object[]
			{ orderModel, cartModel }));
		}

		createB2BBusinessProcess(orderModel);
	}
}
