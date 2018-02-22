/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.updaters.NeorisSalesRepB2BUnitUpdater;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author e-hicastaneda
 * 
 */
public class B2BUnitSalesRepOnChangeEventListener extends AbstractEventListener<B2BUnitSalesRepOnChangeEvent>
{
	private static final Logger LOG = Logger.getLogger(B2BUnitSalesRepOnChangeEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Resource(name = "neorisSalesRepB2BUnitUpdater")
	private NeorisSalesRepB2BUnitUpdater salesRepB2BUnitUpdater;

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
	protected void onEvent(final B2BUnitSalesRepOnChangeEvent event)
	{
		LOG.info("Processing B2BUnit SalesRep OnChange Event");

		final Set<NeorisB2BUnitSettingByStoreModel> originalSettings = event.getOriginalSettings();
		final B2BUnitModel b2bUnitModel = event.getB2bUnitModel();
		//		final String originalSalesRep = event.getOriginalSalesRepId();

		try
		{
			salesRepB2BUnitUpdater.updateWith(b2bUnitModel, originalSettings);
		}
		catch (final Exception e)
		{
			LOG.error("Error while preparing sales rep b2b unit due to: " + e.getMessage());
		}
	}
}
