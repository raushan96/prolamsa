/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.impl.DefaultAddressService;

import java.util.List;

import mx.neoris.core.services.NeorisAddressService;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisAddressService extends DefaultAddressService implements NeorisAddressService
{
	private FlexibleSearchService flexibleSearchService;

	public AddressModel getAddressWithCode(final String code)
	{
		final StringBuilder query = new StringBuilder();
		query.append("SELECT {a.");
		query.append(AddressModel.PK);
		query.append("} FROM {");
		query.append(AddressModel._TYPECODE);
		query.append(" AS a} WHERE {a.");
		query.append(AddressModel.CODE);
		query.append("} = ?code");

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);
		strQuery.addQueryParameter("code", code.substring(code.length() - 5));

		final List<Object> result = getFlexibleSearchService().search(strQuery).getResult();

		if (result.size() > 0)
		{
			return (AddressModel) result.get(0);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}
}
