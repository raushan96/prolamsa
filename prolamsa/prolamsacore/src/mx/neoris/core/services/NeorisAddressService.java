/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.user.AddressService;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisAddressService extends AddressService
{
	AddressModel getAddressWithCode(String code);
}
