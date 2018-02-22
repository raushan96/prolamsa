/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;
import mx.neoris.core.model.NeorisB2BCustomerSettingByStoreModel;
import mx.neoris.core.services.NeorisB2BCustomerSettingByStoreService;

import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class DefaultNeorisB2BCustomerSettingByStoreService implements NeorisB2BCustomerSettingByStoreService
{
	protected static final Logger LOG = Logger.getLogger(DefaultNeorisB2BCustomerSettingByStoreService.class);

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisB2BCustomerSettingByStoreService#getSettingByUid(java.lang.String)
	 */
	@Override
	public NeorisB2BCustomerSettingByStoreModel getSettingByUid(final String uid)
	{
		Assert.assertNotNull("uid parameter cannot be null", uid);

		final StringBuilder query = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		// general query
		query.append("SELECT {ns." + NeorisB2BCustomerSettingByStoreModel.PK + "} FROM {"
				+ NeorisB2BCustomerSettingByStoreModel._TYPECODE + " as ns} ");
		query.append("WHERE {ns." + NeorisB2BCustomerSettingByStoreModel.UID + "} = ?uid ");

		// adding parameter
		queryParams.put("uid", uid);

		final SearchResult<NeorisB2BCustomerSettingByStoreModel> searchResult = flexibleSearchService
				.search(new FlexibleSearchQuery(query.toString(), queryParams));

		if (searchResult.getResult().size() == 1)
		{
			return searchResult.getResult().get(0);
		}
		else if (searchResult.getResult().size() > 1)
		{
			LOG.error("More than one setting found, uid: " + uid);
			return searchResult.getResult().get(0);
		}

		return null;
	}
}
