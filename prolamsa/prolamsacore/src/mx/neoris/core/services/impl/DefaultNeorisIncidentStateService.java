/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.IncidentStateModel;
import mx.neoris.core.services.NeorisIncidentStateService;
import mx.neoris.core.services.NeorisService;


/**
 * @author e-jecarrilloi
 * 
 */
public class DefaultNeorisIncidentStateService implements NeorisIncidentStateService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource
	private UserService userService;

	@Resource(name = "modelService")
	private ModelService modelService;



	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisIncidentStateService#getAll()
	 */
	@Override
	public List<IncidentStateModel> getAll()
	{
		final String query = "SELECT {is.pk} FROM {IncidentState as is}";

		return flexibleSearchService.<IncidentStateModel> search(query).getResult();


	}

	@Override
	public IncidentStateModel getState(final String code)
	{
		final String query = "SELECT {is.pk} FROM {IncidentState as is} WHERE {is.code}='" + code + "'";

		final List<IncidentStateModel> result = flexibleSearchService.<IncidentStateModel> search(query).getResult();

		if (result.isEmpty())
		{
			return null;
		}

		return result.get(0);
	}
}
