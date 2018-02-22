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

import mx.neoris.core.model.IncidentTypeModel;
import mx.neoris.core.services.NeorisIncidentTypeService;
import mx.neoris.core.services.NeorisService;


/**
 * @author e-jecarrilloi
 * 
 */
public class DefaultNeorisIncidentTypeService implements NeorisIncidentTypeService
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
	 * @see mx.neoris.core.services.NeorisIncidentTypeService#getAll()
	 */
	@Override
	public List<IncidentTypeModel> getAll()
	{
		final String query = "SELECT {is.pk} FROM {IncidentType as is}";

		return flexibleSearchService.<IncidentTypeModel> search(query).getResult();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisIncidentTypeService#getType(java.lang.String)
	 */
	@Override
	public IncidentTypeModel getType(final String code)
	{
		final String query = "SELECT {is.pk} FROM {IncidentType as is} WHERE {is.code}='" + code + "'";

		return flexibleSearchService.<IncidentTypeModel> search(query).getResult().get(0);
	}

}
