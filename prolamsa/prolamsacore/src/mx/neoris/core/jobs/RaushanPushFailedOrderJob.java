/**
 *
 */
package mx.neoris.core.jobs;

import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import mx.neoris.core.model.LostOrderJobModel;


/**
 * @author raushankumar
 *
 */
public class RaushanPushFailedOrderJob extends AbstractJobPerformable<LostOrderJobModel>
{

	/*
	 * This Job Will TakeCare to Publish all Lost Order If SAP backed was down Intend to write this job : Few Clients are
	 * facing issue that there whole order getting lost if backed get down but it is available in Hybris System. This Job
	 * will Will run from Last successful Import and will try to import with current time stamp.
	 *
	 */

	private static final Logger LOG = Logger.getLogger(RaushanPushFailedOrderJob.class);

	@Override
	public PerformResult perform(final LostOrderJobModel lostOrderJob)
	{
		LOG.info("Starting Job to Import all orders which having Null Sap Orders");

		//Get All Orders which having SapOrderId is null


		return null;
	}



}
