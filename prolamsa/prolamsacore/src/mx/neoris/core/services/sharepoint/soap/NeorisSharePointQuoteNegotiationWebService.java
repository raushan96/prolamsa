/**
 * 
 */
package mx.neoris.core.services.sharepoint.soap;

import de.hybris.platform.core.model.order.OrderModel;


/**
 * @author hector.castaneda
 * 
 */
public interface NeorisSharePointQuoteNegotiationWebService
{
	String placeQuoteNegotiationToSharePoint(OrderModel orderModel) throws Exception;
}
