/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.UnitModel;

import java.util.Date;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;

/**
 * @author fdeutsch
 *
 */
public interface NeorisCartFacade extends CartFacade
{
	CartModificationData addToCart(String code, double quantity, ProlamsaAPIProductConfigurationModel configModel, Date rollingSchedule, UnitModel uom, String stockOuts) throws CommerceCartModificationException;
	
	public CartModificationData updateCartEntry(long entryNumber, double quantity) throws CommerceCartModificationException;
	
	public void updateAPIProductConfigurationForEntry(AbstractOrderEntryModel entry, ProlamsaAPIProductConfigurationModel configModel);
	
	void removeConfigurationFromOrderEntry (Integer entryNumber);
    void updateRollingSchedule(int entryNumber, Date rollingScheduleWeek);
    
    CartData getSessionCartCheckStock();
    
    void saveCalculatePrice(CartData cartData);
    
    void setCartOwnership(B2BUnitModel owner);
    
    //void updateCartPlaceOrder();
    
}
