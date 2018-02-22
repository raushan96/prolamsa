/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.List;


/**
 * @author e-lacantu
 * 
 */
public interface NeorisB2BUnitSearchService
{
	SearchPageData<B2BUnitModel> getB2BUnitBySearch(B2BUnitSearchParameters searchParameters) throws Exception;

	List<AddressModel> getAddresessForSearch(B2BUnitSearchParameters searchParameters);

	List<AddressModel> getAddresessForSearch(B2BUnitSearchParameters searchParameters, List<String> listPk);

	List<B2BUnitModel> getAllB2BUnitsOnSystem() throws Exception;
}
