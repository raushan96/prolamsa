/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.dao.NeorisAddressDao;
import mx.neoris.core.services.dao.NeorisUserDao;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisService implements NeorisService
{
	@Resource(name = "b2bUnitService")
	B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "neorisAddressDao")
	NeorisAddressDao addressDao;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "catalogService")
	private CatalogService catalogService;

	@Resource
	private UserService userService;

	@Resource
	private DefaultNeorisB2BUnitService defaultNeorisB2BUnitService;

	@Resource
	private BaseStoreService baseStoreService;

	@Resource(name = "neorisUserDao")
	NeorisUserDao userDao;

	public List<B2BUnitModel> getB2BUnitModelsFromCustomer(final B2BCustomerModel b2bCustomer)
	{
		List<B2BUnitModel> col = new ArrayList<B2BUnitModel>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups())
		{
			if (group.getUid().equalsIgnoreCase("AllB2BUnit"))
			{
				final B2BCustomerModel customer = (B2BCustomerModel) userService.getUserForUID("customer.b2bunit@prolamsa.com");
				col = new ArrayList<B2BUnitModel>();
				col = this.getB2BUnitModelsFromCustomerAll(customer);
				break;
			}
			else
			{
				if (group instanceof B2BUnitModel)
				{
					final B2BUnitModel b2bUnit = (B2BUnitModel) group;
					col.add(b2bUnit);
				}
			}
		}

		return col;
	}

	public List<B2BUnitModel> getB2BUnitModelsFromCustomerAll(final B2BCustomerModel b2bCustomer)
	{
		final List<B2BUnitModel> col = new ArrayList<B2BUnitModel>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups())
		{
			if (group instanceof B2BUnitModel)
			{
				final B2BUnitModel b2bUnit = (B2BUnitModel) group;
				col.add(b2bUnit);
			}
		}

		return col;
	}

	public B2BUnitModel getB2BUnitForUID(final String uid)
	{
		return b2bUnitService.getUnitForUid(uid);
	}


	public List<AddressModel> getCurrentCustomerB2BUnitAddresses(final B2BCustomerModel b2bCustomer, final String sortBy,
			final String sortType)
	{
		return addressDao.getCurrentCustomerB2BUnitAddresses(b2bCustomer, sortBy, sortType);
	}

	public List<AddressModel> getCurrentCustomerB2BUnitAddresses(final String selectedUnitUid, final String sortBy,
			final String sortType)
	{
		return addressDao.getCurrentCustomerB2BUnitAddresses(selectedUnitUid, sortBy, sortType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisService#getB2BUnitModelsForCustomerAndCurrentStore(de.hybris.platform.b2b.model.
	 * B2BCustomerModel)
	 */
	@Override
	public List<B2BUnitModel> getB2BUnitModelsForCustomerAndCurrentStore(final B2BCustomerModel b2bCustomer)
	{
		final List<B2BUnitModel> col = new ArrayList<B2BUnitModel>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups())
		{
			if (group instanceof B2BUnitModel)
			{
				if (((B2BUnitModel) group).getBaseStores().contains(baseStoreService.getCurrentBaseStore()))
				{
					final B2BUnitModel b2bUnit = (B2BUnitModel) group;
					col.add(b2bUnit);
				}
			}
		}

		return col;
	}

	@Override
	public B2BCustomerModel getCustomerBySalesRepId(final String salesRepId) throws Exception
	{
		return userDao.getB2bCustomerModelBySalesRep(salesRepId);
	}

	@Override
	public B2BCustomerModel getCustomerByBackOfficeId(final String backofficeId) throws Exception
	{
		return userDao.getB2bCustomerModelByBackoffice(backofficeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisService#getAllProductVisibility(java.lang.String)
	 */

	@Override
	public SearchResult<ProductVisibilityModel> getAllProductVisibility(final String baseStore)
	{

		catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");


		final StringBuilder query = new StringBuilder();
		query.append("SELECT {p." + ProductVisibilityModel.PK + "} FROM {" + ProductVisibilityModel._TYPECODE + " AS p } ");

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);

		return flexibleSearchService.search(strQuery);
	}

	@Override
	public List<CategoryModel> getCategoryCustomerVisibilityForB2BUnit(final String cliente)
	{
		final List<CategoryModel> categorias = userDao.getCategoryCustomerVisibilityForB2BUnit(cliente);
		return categorias == null ? Collections.<CategoryModel> emptyList() : categorias;
	}

}
