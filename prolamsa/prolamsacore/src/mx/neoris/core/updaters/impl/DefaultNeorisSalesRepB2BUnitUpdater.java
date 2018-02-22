/**
 * 
 */
package mx.neoris.core.updaters.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.dao.impl.DefaultNeorisUserDao;
import mx.neoris.core.updaters.NeorisSalesRepB2BUnitUpdater;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisSalesRepB2BUnitUpdater implements NeorisSalesRepB2BUnitUpdater
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisSalesRepB2BUnitUpdater.class);

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource(name = "neorisUserDao")
	private DefaultNeorisUserDao neorisUserDao;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void updateWith(final B2BUnitModel b2bUnitModel, final String originalSalesRep) throws Exception
	{
		B2BCustomerModel b2bCustomerModel = null;

		if (originalSalesRep != null)
		{
			final String newSalesRep = b2bUnitModel.getSalesRep();
			if (!newSalesRep.equals(originalSalesRep))
			{
				b2bCustomerModel = neorisService.getCustomerBySalesRepId(originalSalesRep);
				if (b2bCustomerModel != null)
				{
					removeB2BCustomerFromB2BUnit(b2bUnitModel, b2bCustomerModel);
				}
			}
		}

		b2bCustomerModel = neorisService.getCustomerBySalesRepId(b2bUnitModel.getSalesRep());
		if (b2bCustomerModel != null)
		{
			addB2BCustomerToB2BUnit(b2bUnitModel, b2bCustomerModel);
		}
	}

	private void removeB2BCustomerFromB2BUnit(final B2BUnitModel b2bUnitModel, final B2BCustomerModel b2bCustomerModel)
	{
		if (b2bCustomerModel == null)
		{
			return;
		}
		final Set<PrincipalModel> members = b2bUnitModel.getMembers();
		final Set<PrincipalModel> newMembers = new HashSet<PrincipalModel>();

		if (members != null)
		{
			for (final PrincipalModel member : members)
			{
				if (member instanceof B2BCustomerModel)
				{
					if (!member.getUid().equals(b2bCustomerModel.getUid()))
					{
						newMembers.add(member);
					}
				}
				else
				{
					newMembers.add(member);
				}
			}

			b2bUnitModel.setMembers(newMembers);
		}
	}

	private void addB2BCustomerToB2BUnit(final B2BUnitModel b2bUnitModel, final B2BCustomerModel b2bCustomerModel)
	{
		if (b2bCustomerModel == null)
		{
			return;
		}

		final Set<PrincipalModel> members = b2bUnitModel.getMembers();
		final Set<PrincipalModel> newMembers = new HashSet<PrincipalModel>();

		if (members != null)
		{
			for (final PrincipalModel member : members)
			{
				newMembers.add(member);
			}
		}

		newMembers.add(b2bCustomerModel);
		b2bUnitModel.setMembers(newMembers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.updaters.NeorisSalesRepB2BUnitUpdater#updateWith(de.hybris.platform.b2b.model.B2BUnitModel,
	 * java.util.List)
	 */
	@Override
	public void updateWith(final B2BUnitModel b2bUnitModel, final Set<NeorisB2BUnitSettingByStoreModel> originalSettings)
			throws Exception
	{
		B2BCustomerModel b2bCustomerModel = null;
		final Set<NeorisB2BUnitSettingByStoreModel> newSettings = b2bUnitModel.getSalesRepForStore();
		if (originalSettings != null && originalSettings.size() > 0)
		{

			for (final NeorisB2BUnitSettingByStoreModel eachOriginalSetting : originalSettings)
			{
				final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(eachOriginalSetting.getStoreId());
				b2bCustomerModel = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(eachOriginalSetting.getSetting(),
						baseStoreModel);

				removeB2BCustomerFromB2BUnit(b2bUnitModel, b2bCustomerModel);

			}

		}
		if (newSettings != null && newSettings.size() > 0)
		{
			for (final NeorisB2BUnitSettingByStoreModel eachOriginalSetting : newSettings)
			{
				final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(eachOriginalSetting.getStoreId());
				b2bCustomerModel = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(eachOriginalSetting.getSetting(),
						baseStoreModel);

				addB2BCustomerToB2BUnit(b2bUnitModel, b2bCustomerModel);
			}
		}

	}

	@Override
	public void updateWith(final B2BUnitModel b2BUnitOwner, final String storeId, final String originalSetting,
			final String newSetting) throws Exception
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(storeId);
		B2BCustomerModel b2bCustomerModel = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(originalSetting, baseStoreModel);

		removeB2BCustomerFromB2BUnit(b2BUnitOwner, b2bCustomerModel);

		b2bCustomerModel = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(newSetting, baseStoreModel);

		addB2BCustomerToB2BUnit(b2BUnitOwner, b2bCustomerModel);

		modelService.save(b2BUnitOwner);
	}

	@Override
	public Boolean validateSettingIsSalesRep(final String storeId, final String newSetting) throws Exception
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(storeId);
		final B2BCustomerModel b2bCustomerModel = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(newSetting, baseStoreModel);

		if (b2bCustomerModel == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}