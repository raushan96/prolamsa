/**
 * 
 */
package mx.neoris.core.services.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.user.daos.AddressDao;

import java.util.List;


/**
 * @author LilianaOW
 * 
 */
public interface NeorisAddressDao extends AddressDao
{

	List<AddressModel> getCurrentCustomerB2BUnitAddresses(final B2BCustomerModel b2bCustomer, final String sortBy,
			final String sortType);

	List<AddressModel> getCurrentCustomerB2BUnitAddresses(final String selectedUnitUid, final String sortBy, final String sortType);
}
