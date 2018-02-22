/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import mx.neoris.core.model.ProductVisibilityModel;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisService
{
	List<B2BUnitModel> getB2BUnitModelsForCustomerAndCurrentStore(B2BCustomerModel b2bCustomer);

	List<B2BUnitModel> getB2BUnitModelsFromCustomer(B2BCustomerModel b2bCustomer);

	List<B2BUnitModel> getB2BUnitModelsFromCustomerAll(B2BCustomerModel b2bCustomer);

	B2BUnitModel getB2BUnitForUID(final String uid);

	List<AddressModel> getCurrentCustomerB2BUnitAddresses(final B2BCustomerModel b2bCustomer, final String sortBy,
			final String sortType);

	List<AddressModel> getCurrentCustomerB2BUnitAddresses(final String selectedUnitUid, final String sortBy, final String sortType);

	B2BCustomerModel getCustomerBySalesRepId(final String salesRepId) throws Exception;

	B2BCustomerModel getCustomerByBackOfficeId(final String backofficeId) throws Exception;

	SearchResult<ProductVisibilityModel> getAllProductVisibility(String baseStore);

	List<CategoryModel> getCategoryCustomerVisibilityForB2BUnit(final String cliente);

}
