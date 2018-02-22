/**
 * 
 */
package mx.neoris.core.services.dao.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.daos.impl.DefaultUserDao;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.services.dao.NeorisUserDao;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisUserDao extends DefaultUserDao implements NeorisUserDao
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisUserDao.class);

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public B2BCustomerModel getB2bCustomerModelBySalesRep(final String salesRepId) throws Exception
	{
		if (salesRepId == null)
		{
			LOG.info("Sales Rep value cannot be null");
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {b2bc.pk} FROM {B2BCustomer as b2bc} ");
		sb.append("WHERE {b2bc.salesRep} = ?salesRep");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameter("salesRep", salesRepId);

		final List<B2BCustomerModel> result = flexibleSearchService.<B2BCustomerModel> search(query).getResult();

		if (result.isEmpty())
		{
			LOG.info("Not found B2BCustomerModel with sales rep: " + salesRepId);
			return null;
		}
		else if (result.size() == 1)
		{
			if (result.get(0).getActive())
			{
				return result.get(0);
			}
			else
			{
				LOG.info("salesRepId inactive for: " + salesRepId);
				return null;
			}
		}
		else
		{
			throw new Exception("Error, multiple B2BCustomers found with same sales rep: " + salesRepId);
		}
	}

	@Override
	public B2BCustomerModel getB2bCustomerModelByBackoffice(final String backofficeId) throws Exception
	{
		if (backofficeId == null)
		{
			LOG.info("Backoffice value cannot be null");
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {b2bc.pk} FROM {B2BCustomer as b2bc} ");
		sb.append("WHERE {b2bc.backofficeAccount} = ?backoffice");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameter("backoffice", backofficeId);

		final List<B2BCustomerModel> result = flexibleSearchService.<B2BCustomerModel> search(query).getResult();

		if (result.isEmpty())
		{
			LOG.info("Not found B2BCustomerModel with backofficeAccount: " + backofficeId);
			return null;
		}
		else if (result.size() == 1)
		{
			if (result.get(0).getActive())
			{
				return result.get(0);
			}
			else
			{
				LOG.info("backofficeId inactive for: " + backofficeId);
				return null;
			}
		}
		else
		{
			throw new Exception("Error, multiple B2BCustomers found with same backofficeAccount: " + backofficeId);
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.dao.NeorisUserDao#getB2bCustomerModelByBackofficeAndStore(java.lang.String,
	 * de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public B2BCustomerModel getB2bCustomerModelByBackofficeAndStore(final String backofficeId, final BaseStoreModel storeModel)
			throws Exception
	{
		if (backofficeId == null)
		{
			LOG.info("Backoffice value cannot be null");
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {b2bc.pk} FROM {B2BCustomer as b2bc JOIN NeorisB2BCustomerSettingByStore as nss ");
		sb.append("ON {b2bc.backOfficeAccountForStore}  LIKE CONCAT('%',CONCAT({nss.pk},'%'))}");
		sb.append("WHERE {nss.storeId}=" + storeModel.getUid() + " AND {nss.setting}=" + backofficeId);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());

		final List<B2BCustomerModel> result = flexibleSearchService.<B2BCustomerModel> search(query).getResult();

		if (result.isEmpty())
		{
			LOG.info("Not found B2BCustomerModel with backofficeAccount: " + backofficeId + " and store: " + storeModel.getUid());
			return null;
		}
		else if (result.size() == 1)
		{
			if (result.get(0).getActive())
			{
				return result.get(0);
			}
			else
			{
				LOG.info("backofficeId inactive for: " + backofficeId + " and store: " + storeModel.getUid());
				return null;
			}
		}
		else
		{
			throw new Exception("Error, multiple B2BCustomers found with same backofficeAccount: " + backofficeId + " and store: "
					+ storeModel.getUid());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.dao.NeorisUserDao#getB2bCustomerModelBySalesRepAndStore(java.lang.String,
	 * de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public B2BCustomerModel getB2bCustomerModelBySalesRepAndStore(final String salesRepId, final BaseStoreModel storeModel)
			throws Exception
	{
		if (salesRepId == null)
		{
			LOG.info("Sales Rep value cannot be null");
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {b2bc.pk} FROM {B2BCustomer as b2bc JOIN NeorisB2BCustomerSettingByStore as nss ");
		sb.append("ON {b2bc.salesRepForStore} LIKE CONCAT('%',CONCAT({nss.pk},'%'))} ");
		sb.append("WHERE {nss.storeId}=" + storeModel.getUid() + " AND {nss.setting}=" + salesRepId);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());

		final List<B2BCustomerModel> result = flexibleSearchService.<B2BCustomerModel> search(query).getResult();

		if (result.isEmpty())
		{
			LOG.info("Not found B2BCustomerModel with sales rep: " + salesRepId + " and store: " + storeModel.getUid());
			return null;
		}
		else if (result.size() == 1)
		{
			if (result.get(0).getActive())
			{
				return result.get(0);
			}
			else
			{
				LOG.info("sales rep inactive for: " + salesRepId + " and store: " + storeModel.getUid());
				return null;
			}
		}
		else
		{
			throw new Exception("Error, multiple B2BCustomers found with same sales rep: " + salesRepId + " and store: "
					+ storeModel.getUid());
		}
	}

	@Override
	public List<CategoryModel> getCategoryCustomerVisibilityForB2BUnit(final String cliente)
	{
		if (cliente == null)
		{
			LOG.info("B2BUnit value cannot be null to search customer category");
			return null;
		}

		final String LIKE_CHARACTER = "%";

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {cat.pk} FROM {Category as cat} ");
		sb.append("WHERE {cat.code} like ?cliente");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameter("cliente", LIKE_CHARACTER + cliente + LIKE_CHARACTER);

		final List<CategoryModel> result = flexibleSearchService.<CategoryModel> search(query).getResult();

		if (result.isEmpty())
		{
			LOG.info("Not found CategoryModel type (Visibility) with unit: " + cliente);
			return null;
		}
		else
		{
			return result;
		}
	}
}