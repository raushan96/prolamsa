/**
 * 
 */
package mx.neoris.core.jobs;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.services.NeorisB2BUnitSearchService;
import mx.neoris.core.updaters.NeorisBackofficeB2BUnitUpdater;

import org.apache.log4j.Logger;


/**
 * @author christian.loredo
 * 
 */
public class NeorisBackofficeB2BUnitUpdaterJob extends AbstractJobPerformable<CronJobModel>
{

	private static final Logger LOG = Logger.getLogger(NeorisBackofficeB2BUnitUpdaterJob.class);

	@Resource(name = "neorisBackofficeB2BUnitUpdater")
	private NeorisBackofficeB2BUnitUpdater backofficeB2BUnitUpdater;

	@Resource(name = "neorisB2BUnitSearchService")
	private NeorisB2BUnitSearchService neorisB2BUnitSearchService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public PerformResult perform(final CronJobModel cronJobModel)
	{
		LOG.info("Starting job");

		try
		{
			final List<B2BUnitModel> allB2BUnits = neorisB2BUnitSearchService.getAllB2BUnitsOnSystem();

			for (final B2BUnitModel b2bUnit : allB2BUnits)
			{
				// Call save model service in order to updating b2bunit through interceptor execution
				try
				{
					modelService.save(b2bUnit);
				}
				catch (final ModelSavingException e)
				{
					LOG.error("Error while trying to save b2bUnit: " + e.getMessage());
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

}
