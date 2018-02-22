/**
 * 
 */
package mx.neoris.core.jobs;


import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.services.NeorisB2BUnitSearchService;
import mx.neoris.core.services.NeorisB2BUnitSettingByStoreService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class NeorisB2BUnitMultisiteUpdaterJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(NeorisB2BUnitMultisiteUpdaterJob.class);

	@Resource(name = "neorisB2BUnitSearchService")
	private NeorisB2BUnitSearchService neorisB2BUnitSearchService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisB2BUnitSettingByStoreService")
	private NeorisB2BUnitSettingByStoreService neorisB2BUnitSettingByStoreService;

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
		LOG.info("Starting job");

		try
		{
			final List<B2BUnitModel> allB2BUnits = neorisB2BUnitSearchService.getAllB2BUnitsOnSystem();

			for (final B2BUnitModel b2bUnit : allB2BUnits)
			{
				final Set<BaseStoreModel> baseStores = b2bUnit.getBaseStores();

				if (baseStores == null || baseStores.isEmpty())
				{
					LOG.error("Skipping B2BUnit: " + b2bUnit.getUid() + " has not base stores assigned.");
				}
				else if (baseStores.size() > 1)
				{
					LOG.error("Skipping B2BUnit: " + b2bUnit.getUid() + " has more than one store assigned.");
				}
				else
				{
					// B2BUnit has only one store

					// Getting base store assigned
					final BaseStoreModel baseStore = baseStores.iterator().next();

					// Getting information to migrate
					final String currency = (b2bUnit.getCurrency() != null) ? b2bUnit.getCurrency().getIsocode() : "";
					final String salesRep = b2bUnit.getSalesRep();
					final String industry = b2bUnit.getIndustry();
					final String tPago = (b2bUnit.getTPago() != null) ? b2bUnit.getTPago().getCode() : "";
					final String salesArea = b2bUnit.getSalesArea();
					final String backOffice = b2bUnit.getBackOfficeAccount();
					final Collection<AddressModel> addresses = b2bUnit.getAddresses();

					// Setting for currency
					if (StringUtils.isNotEmpty(currency))
					{
						final String settingUid = baseStore.getUid() + "_" + currency + "_" + b2bUnit.getUid();

						// check if setting already exists
						NeorisB2BUnitSettingByStoreModel b2bUnitSettingForCurrency = neorisB2BUnitSettingByStoreService
								.getSettingByUid(settingUid);

						if (b2bUnitSettingForCurrency == null)
						{
							b2bUnitSettingForCurrency = generateB2BUnitSettingFor(baseStore, currency, b2bUnit);
						}

						// Add setting to b2bunit
						final Set<NeorisB2BUnitSettingByStoreModel> currencyForStoreSet = new HashSet<NeorisB2BUnitSettingByStoreModel>();
						currencyForStoreSet.add(b2bUnitSettingForCurrency);
						b2bUnit.setCurrencyForStore(currencyForStoreSet);
					}

					// Setting for sales rep
					if (StringUtils.isNotEmpty(salesRep))
					{
						final String settingUid = baseStore.getUid() + "_" + salesRep + "_" + b2bUnit.getUid();

						// check if setting already exists
						NeorisB2BUnitSettingByStoreModel b2bUnitSettingForSalesRep = neorisB2BUnitSettingByStoreService
								.getSettingByUid(settingUid);

						if (b2bUnitSettingForSalesRep == null)
						{
							b2bUnitSettingForSalesRep = generateB2BUnitSettingFor(baseStore, salesRep, b2bUnit);
						}

						// Add setting to b2bunit
						final Set<NeorisB2BUnitSettingByStoreModel> salesRepForStoreSet = new HashSet<NeorisB2BUnitSettingByStoreModel>();
						salesRepForStoreSet.add(b2bUnitSettingForSalesRep);
						b2bUnit.setSalesRepForStore(salesRepForStoreSet);
					}

					// Setting for industry
					if (StringUtils.isNotEmpty(industry))
					{
						final String settingUid = baseStore.getUid() + "_" + industry + "_" + b2bUnit.getUid();

						// check if setting already exists
						NeorisB2BUnitSettingByStoreModel b2bUnitSettingForIndustry = neorisB2BUnitSettingByStoreService
								.getSettingByUid(settingUid);

						if (b2bUnitSettingForIndustry == null)
						{
							b2bUnitSettingForIndustry = generateB2BUnitSettingFor(baseStore, industry, b2bUnit);
						}

						// Add setting to b2bunit
						final Set<NeorisB2BUnitSettingByStoreModel> industryForStoreSet = new HashSet<NeorisB2BUnitSettingByStoreModel>();
						industryForStoreSet.add(b2bUnitSettingForIndustry);
						b2bUnit.setIndustryForStore(industryForStoreSet);
					}

					// Setting for TPago
					if (StringUtils.isNotEmpty(tPago))
					{
						final String settingUid = baseStore.getUid() + "_" + tPago + "_" + b2bUnit.getUid();

						// check if setting already exists
						NeorisB2BUnitSettingByStoreModel b2bUnitSettingForTPago = neorisB2BUnitSettingByStoreService
								.getSettingByUid(settingUid);

						if (b2bUnitSettingForTPago == null)
						{
							b2bUnitSettingForTPago = generateB2BUnitSettingFor(baseStore, tPago, b2bUnit);
						}

						// Add setting to b2bunit
						final Set<NeorisB2BUnitSettingByStoreModel> tPagoForStoreSet = new HashSet<NeorisB2BUnitSettingByStoreModel>();
						tPagoForStoreSet.add(b2bUnitSettingForTPago);
						b2bUnit.setTPagoForStore(tPagoForStoreSet);
					}

					// Setting for sales area
					if (StringUtils.isNotEmpty(salesArea))
					{
						final String settingUid = baseStore.getUid() + "_" + salesArea + "_" + b2bUnit.getUid();

						// check if setting already exists
						NeorisB2BUnitSettingByStoreModel b2bUnitSettingForSalesArea = neorisB2BUnitSettingByStoreService
								.getSettingByUid(settingUid);

						if (b2bUnitSettingForSalesArea == null)
						{
							b2bUnitSettingForSalesArea = generateB2BUnitSettingFor(baseStore, salesArea, b2bUnit);
						}

						// Add setting to b2bunit
						final Set<NeorisB2BUnitSettingByStoreModel> salesAreaForStoreSet = new HashSet<NeorisB2BUnitSettingByStoreModel>();
						salesAreaForStoreSet.add(b2bUnitSettingForSalesArea);
						b2bUnit.setSalesAreaForStore(salesAreaForStoreSet);
					}

					// Setting for back office
					if (StringUtils.isNotEmpty(backOffice))
					{
						final String settingUid = baseStore.getUid() + "_" + backOffice + "_" + b2bUnit.getUid();

						// check if setting already exists
						NeorisB2BUnitSettingByStoreModel b2bUnitSettingForBackOffice = neorisB2BUnitSettingByStoreService
								.getSettingByUid(settingUid);

						if (b2bUnitSettingForBackOffice == null)
						{
							b2bUnitSettingForBackOffice = generateB2BUnitSettingFor(baseStore, backOffice, b2bUnit);
						}

						// Add setting to b2bunit
						final Set<NeorisB2BUnitSettingByStoreModel> backOfficeForStoreSet = new HashSet<NeorisB2BUnitSettingByStoreModel>();
						backOfficeForStoreSet.add(b2bUnitSettingForBackOffice);
						b2bUnit.setBackOfficeAccountForStore(backOfficeForStoreSet);
					}

					// Addresses
					if (addresses != null && !addresses.isEmpty())
					{
						for (final Iterator<AddressModel> iterator = addresses.iterator(); iterator.hasNext();)
						{
							final AddressModel address = iterator.next();

							final Set<BaseStoreModel> baseStoreForAddressSet = new HashSet<BaseStoreModel>();
							baseStoreForAddressSet.add(baseStore);
							address.setBaseStores(baseStoreForAddressSet);

							try
							{
								modelService.save(address);
							}
							catch (final ModelSavingException e)
							{
								LOG.error("Error while trying to save B2BUnit address: " + " due to: " + e.getMessage());
							}
						}
					}

					try
					{
						// Save changes on b2bunit
						modelService.save(b2bUnit);
					}
					catch (final ModelSavingException e)
					{
						LOG.error("Error while trying to save B2BUnit: " + b2bUnit.getUid() + " due to: " + e.getMessage());
					}
				}
			}

			return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
		}
		catch (final Exception e)
		{
			LOG.error("Error while getting all b2bunits on system: " + e.getMessage());
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}
	}

	protected NeorisB2BUnitSettingByStoreModel generateB2BUnitSettingFor(final BaseStoreModel baseStore, final String setting,
			final B2BUnitModel b2bUnit)
	{
		final NeorisB2BUnitSettingByStoreModel b2bUnitSetting = new NeorisB2BUnitSettingByStoreModel();
		b2bUnitSetting.setUid(baseStore.getUid() + "_" + setting + "_" + b2bUnit.getUid());
		b2bUnitSetting.setStoreId(baseStore.getUid());
		b2bUnitSetting.setSetting(setting);
		b2bUnitSetting.setB2bUnitOwner(b2bUnit);

		return b2bUnitSetting;
	}
}
