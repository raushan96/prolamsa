/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.updaters.NeorisBackofficeB2BUnitUpdater;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author christian.loredo
 * 
 */
public class B2BUnitBackofficeOnChangeEventListener extends AbstractEventListener<B2BUnitBackofficeOnChangeEvent>
{
	private static final Logger LOG = Logger.getLogger(B2BUnitBackofficeOnChangeEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Resource(name = "neorisBackofficeB2BUnitUpdater")
	private NeorisBackofficeB2BUnitUpdater backofficeB2BUnitUpdater;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	protected void onEvent(final B2BUnitBackofficeOnChangeEvent event)
	{
		LOG.info("Processing B2BUnit Backoffice OnChange Event");

		final B2BUnitModel b2bUnitModel = event.getB2bUnitModel();
		final String originalBackoffice = event.getOriginalBackofficeId();

		try
		{
			backofficeB2BUnitUpdater.updateWith(b2bUnitModel, originalBackoffice);
		}
		catch (final Exception e)
		{
			LOG.error("Error while preparing backoffice b2b unit due to: " + e.getMessage());
		}
	}

}
