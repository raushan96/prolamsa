/**
 * 
 */
package mx.neoris.core.services.dao;

import de.hybris.platform.b2b.model.B2BPermissionModel;
import de.hybris.platform.b2bacceleratorservices.dao.impl.DefaultPagedB2BPermissionDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisPagedB2BPermissionDao extends DefaultPagedB2BPermissionDao
{

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;


	private static final String FIND_PERMISSION_BY_PARENT_UNIT = "SELECT {B2BPermission:pk} "
			+ "FROM { B2BPermission as B2BPermission 														  "
			+ "JOIN   B2BUnit 		as B2BUnit 			ON  {B2BPermission:unit} = {B2BUnit:pk} }"
			+ "ORDER BY {B2BUnit:name}																			  ";

	private static final String FIND_BY_PARENT_UNIT_AND_STORE_UNIT_NAME = "SELECT {B2BPermission:pk}"
			+ "FROM	{ B2BPermission as B2BPermission " + "JOIN B2BUnit as B2BUnit ON {B2BPermission:unit} = {B2BUnit:pk} "
			+ "JOIN B2BUnits2BaseStores as B2BUnits2BaseStores ON {B2BUnits2BaseStores:source} = {B2BUnit:pk}} "
			+ "WHERE {B2BUnits2BaseStores:target} = ?storePk ORDER BY {B2BUnit:name}";

	private static final String FIND_BY_PARENT_UNIT_AND_STORE_PERMISSION_NAME = "SELECT {B2BPermission:pk}"
			+ "FROM	{ B2BPermission as B2BPermission " + "JOIN B2BUnit as B2BUnit ON {B2BPermission:unit} = {B2BUnit:pk} "
			+ "JOIN B2BUnits2BaseStores as B2BUnits2BaseStores ON {B2BUnits2BaseStores:source} = {B2BUnit:pk}} "
			+ "WHERE {B2BUnits2BaseStores:target} = ?storePk ORDER BY {B2BPermission:code}";


	/**
	 * @param typeCode
	 */
	public DefaultNeorisPagedB2BPermissionDao(final String typeCode)
	{
		super(typeCode);
		// YTODO Auto-generated constructor stub
	}

	@Override
	public SearchPageData<B2BPermissionModel> findPagedPermissions(final String sortCode, final PageableData pageableData)
	{
		final BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();

		if (currentBaseStore == null)
		{
			final List<SortQueryData> sortQueries = Arrays.asList(
					createSortQueryData("byUnitName", FIND_PERMISSION_BY_PARENT_UNIT),
					createSortQueryData("byName", new HashMap<String, Object>(),
							SortParameters.singletonAscending(B2BPermissionModel.CODE), pageableData));
			return getPagedFlexibleSearchService().search(sortQueries, sortCode, new HashMap<String, Object>(), pageableData);
		}
		else
		{
			final String queryByUnitName = "SELECT {B2BPermission:pk}" + "FROM	{ B2BPermission as B2BPermission "
					+ "JOIN B2BUnit as B2BUnit ON {B2BPermission:unit} = {B2BUnit:pk} "
					+ "JOIN B2BUnits2BaseStores as B2BUnits2BaseStores ON {B2BUnits2BaseStores:source} = {B2BUnit:pk}} "
					+ "WHERE {B2BUnits2BaseStores:target} ='" + currentBaseStore.getPk() + "' ORDER BY {B2BUnit:name}";

			final String queryByName = "SELECT {B2BPermission:pk}" + "FROM	{ B2BPermission as B2BPermission "
					+ "JOIN B2BUnit as B2BUnit ON {B2BPermission:unit} = {B2BUnit:pk} "
					+ "JOIN B2BUnits2BaseStores as B2BUnits2BaseStores ON {B2BUnits2BaseStores:source} = {B2BUnit:pk}} "
					+ "WHERE {B2BUnits2BaseStores:target} = '" + currentBaseStore.getPk() + "' ORDER BY {B2BPermission:code}";

			final HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("storePk", currentBaseStore.getPk());
			final List<SortQueryData> sortQueries = Arrays.asList(createSortQueryData("byUnitName", queryByUnitName),
					createSortQueryData("byName", queryByName));
			return getPagedFlexibleSearchService().search(sortQueries, sortCode, new HashMap<String, Object>(), pageableData);
		}

	}
}
