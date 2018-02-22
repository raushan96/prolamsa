/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.model.AddedIncidentProcessModel;

import org.apache.log4j.Logger;


/**
 * @author e-jecarrilloi
 * 
 */
public class IncidentAddedEventListener extends AbstractSiteEventListener<IncidentAddedEvent>
{
	private static final Logger LOG = Logger.getLogger(IncidentAddedEventListener.class);

	@Resource(name = "businessProcessService")
	private BusinessProcessService businessProcessService;

	@Resource(name = "modelService")
	private ModelService modelService;



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.commerceservices.event.AbstractSiteEventListener#onSiteEvent(de.hybris.platform.servicelayer
	 * .event.events.AbstractEvent)
	 */
	@Override
	protected void onSiteEvent(final IncidentAddedEvent event)
	{
		final AddedIncidentProcessModel processModel = (AddedIncidentProcessModel) businessProcessService.createProcess(
				"addedIncident " + "- " + event.getIncidentModel().getCode() + " - " + System.currentTimeMillis(),
				"addedIncidentEmailProcess");

		processModel.setIncident(event.getIncidentModel());
		processModel.setSite(event.getBaseSiteModel());
		processModel.setStore(event.getBaseStoreModel());
		processModel.setCustomer(event.getCustomerModel());
		processModel.setLanguage(event.getLanguageModel());
		processModel.setUser(event.getCustomerModel());
		modelService.save(processModel);

		businessProcessService.startProcess(processModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.commerceservices.event.AbstractSiteEventListener#shouldHandleEvent(de.hybris.platform.servicelayer
	 * .event.events.AbstractEvent)
	 */
	@Override
	protected boolean shouldHandleEvent(final IncidentAddedEvent event)
	{
		return true;
	}



}
