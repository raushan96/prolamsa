/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.updaters.NeorisTermsAndConditionsCheckedB2BCustomerUpdater;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author e-jecarrilloi
 * 
 */
public class CMSParagraphComponentContentOnChangeEventListener extends
		AbstractEventListener<CMSParagraphComponentContentOnChangeEvent>
{
	private static final Logger LOG = Logger.getLogger(CMSParagraphComponentContentOnChangeEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Resource(name = "neorisTermsAndConditionsCheckedB2BCustomerUpdater")
	private NeorisTermsAndConditionsCheckedB2BCustomerUpdater neorisTermsAndConditionsCheckedB2BCustomerUpdater;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2BCustomerService;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.event.impl.AbstractEventListener#onEvent(de.hybris.platform.servicelayer.event
	 * .events.AbstractEvent)
	 */
	@Override
	protected void onEvent(final CMSParagraphComponentContentOnChangeEvent event)
	{
		neorisTermsAndConditionsCheckedB2BCustomerUpdater.updateAllB2BCustomerWith(event.getCmsParagraphComponentModel());
	}
}
