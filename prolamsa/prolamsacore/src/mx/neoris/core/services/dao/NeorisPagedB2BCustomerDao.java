/**
 * 
 */
package mx.neoris.core.services.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bacceleratorservices.dao.impl.DefaultPagedB2BCustomerDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author LilianaOW
 * 
 */
public class NeorisPagedB2BCustomerDao extends DefaultPagedB2BCustomerDao
{

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;


	/**
	 * @param typeCode
	 */
	public NeorisPagedB2BCustomerDao(final String typeCode)
	{
		super(typeCode);
		// YTODO Auto-generated constructor stub
	}

	@Override
	public SearchPageData<B2BCustomerModel> findPagedCustomers(final String sortCode, final PageableData pageableData)
	{
		final BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();


		if (currentBaseStore == null)
		{
			final List<SortQueryData> sortQueries = Arrays.asList(
					createSortQueryData("byName", new HashMap<String, Object>(), SortParameters.singletonAscending(UserModel.NAME),
							pageableData),
					createSortQueryData("byStatus", new HashMap<String, Object>(),
							SortParameters.singletonDescending(B2BCustomerModel.ACTIVE), pageableData));

			return getPagedFlexibleSearchService().search(sortQueries, sortCode, new HashMap<String, Object>(), pageableData);
		}
		else
		{
			final String queryByName = "SELECT DISTINCT {c.pk}, {c.name} FROM {B2BCustomer as c "
					+ "JOIN PrincipalGroupRelation as pgr ON {c.pk} = {pgr.source} "
					+ "JOIN B2BUnits2BaseStores as u2br ON {pgr.target} = {u2br.source}} " + "WHERE {u2br.target}='"
					+ currentBaseStore.getPk() + "' ORDER BY {c.name}";

			final String queryByStatus = "SELECT DISTINCT {c.pk}, {c.Active} FROM {B2BCustomer as c "
					+ "JOIN PrincipalGroupRelation as pgr ON {c.pk} = {pgr.source} "
					+ "JOIN B2BUnits2BaseStores as u2br ON {pgr.target} = {u2br.source}} " + "WHERE {u2br.target}='"
					+ currentBaseStore.getPk() + "' ORDER BY {c.Active}";

			final List<SortQueryData> sortQueries = Arrays.asList(createSortQueryData("byName", queryByName),
					createSortQueryData("byStatus", queryByStatus));


			return getPagedFlexibleSearchService().search(sortQueries, sortCode, new HashMap<String, Object>(), pageableData);
		}


	}
}
