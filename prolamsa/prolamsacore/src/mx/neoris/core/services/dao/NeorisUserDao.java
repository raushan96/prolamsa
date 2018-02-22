/**
 * 
 */
package mx.neoris.core.services.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.user.daos.UserDao;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;


/**
 * @author e-hicastaneda
 * 
 */
public interface NeorisUserDao extends UserDao
{
	B2BCustomerModel getB2bCustomerModelBySalesRep(final String salesRepId) throws Exception;

	B2BCustomerModel getB2bCustomerModelByBackoffice(final String backofficeId) throws Exception;

	B2BCustomerModel getB2bCustomerModelBySalesRepAndStore(final String salesRepId, BaseStoreModel storeModel) throws Exception;

	B2BCustomerModel getB2bCustomerModelByBackofficeAndStore(final String backofficeId, BaseStoreModel storeModel)
			throws Exception;

	List<CategoryModel> getCategoryCustomerVisibilityForB2BUnit(final String cliente);
}
