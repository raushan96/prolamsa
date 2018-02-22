/**
 * 
 */
package mx.neoris.core.jobs;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisB2BCustomerSettingByStoreModel;
import mx.neoris.core.services.NeorisB2BCustomerSettingByStoreService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class NeorisB2BCustomerMultisiteUpdaterJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(NeorisB2BCustomerMultisiteUpdaterJob.class);

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2BCustomerService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisB2BCustomerSettingByStoreService")
	private NeorisB2BCustomerSettingByStoreService neorisB2BCustomerSettingByStoreService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		final List<B2BCustomerModel> allCUstomerModels = b2BCustomerService.getAllUsers();

		for (final B2BCustomerModel b2bCustomer : allCUstomerModels)
		{
			final Set<BaseStoreModel> baseStores = b2bCustomer.getBaseStores();

			if (baseStores == null || baseStores.isEmpty())
			{
				LOG.error("Skipping B2BCustomer: " + b2bCustomer.getUid() + " has not base stores assigned.");
			}
			else if (baseStores.size() > 1)
			{
				LOG.error("Skipping B2BCustomer: " + b2bCustomer.getUid() + " has more than one store assigned.");
			}
			else
			{
				// B2BCustomer has only one store

				// Getting base store assigned
				final BaseStoreModel baseStore = baseStores.iterator().next();

				// Getting information to migrate
				final String salesRep = b2bCustomer.getSalesRep();
				final String backOffice = b2bCustomer.getBackofficeAccount();

				// Setting for sales rep
				if (StringUtils.isNotEmpty(salesRep))
				{
					final String settingUid = baseStore.getUid() + "_" + salesRep + "_" + b2bCustomer.getUid();

					// check if setting already exists
					NeorisB2BCustomerSettingByStoreModel b2bCustomerSettingForSalesRep = neorisB2BCustomerSettingByStoreService
							.getSettingByUid(settingUid);

					if (b2bCustomerSettingForSalesRep == null)
					{
						b2bCustomerSettingForSalesRep = generateB2BCustomerSettingFor(baseStore, salesRep, b2bCustomer);
					}

					// Add setting to b2bcustomer
					final List<NeorisB2BCustomerSettingByStoreModel> salesRepForStoreList = new ArrayList<NeorisB2BCustomerSettingByStoreModel>();
					salesRepForStoreList.add(b2bCustomerSettingForSalesRep);
					b2bCustomer.setSalesRepForStore(salesRepForStoreList);
				}

				// Setting for back office
				if (StringUtils.isNotEmpty(backOffice))
				{
					final String settingUid = baseStore.getUid() + "_" + backOffice + "_" + b2bCustomer.getUid();

					// check if setting already exists
					NeorisB2BCustomerSettingByStoreModel b2bCustomerSettingForBackOffice = neorisB2BCustomerSettingByStoreService
							.getSettingByUid(settingUid);

					if (b2bCustomerSettingForBackOffice == null)
					{
						b2bCustomerSettingForBackOffice = generateB2BCustomerSettingFor(baseStore, backOffice, b2bCustomer);
					}

					// Add setting to b2bcustomer
					final List<NeorisB2BCustomerSettingByStoreModel> backOfficeForStoreList = new ArrayList<NeorisB2BCustomerSettingByStoreModel>();
					backOfficeForStoreList.add(b2bCustomerSettingForBackOffice);
					b2bCustomer.setBackofficeAccountForStore(backOfficeForStoreList);
				}

				try
				{
					// Save changes on b2bcustomer
					modelService.save(b2bCustomer);
				}
				catch (final ModelSavingException e)
				{
					LOG.error("Error while trying to save B2BCustomer: " + b2bCustomer.getUid() + " due to: " + e.getMessage());
				}
			}
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected NeorisB2BCustomerSettingByStoreModel generateB2BCustomerSettingFor(final BaseStoreModel baseStore,
			final String setting, final B2BCustomerModel b2bCustomer)
	{
		final NeorisB2BCustomerSettingByStoreModel b2bCustomerSetting = new NeorisB2BCustomerSettingByStoreModel();
		b2bCustomerSetting.setUid(baseStore.getUid() + "_" + setting + "_" + b2bCustomer.getUid());
		b2bCustomerSetting.setStoreId(baseStore.getUid());
		b2bCustomerSetting.setSetting(setting);
		b2bCustomerSetting.setB2bCustomerOwner(b2bCustomer);

		return b2bCustomerSetting;
	}
}
