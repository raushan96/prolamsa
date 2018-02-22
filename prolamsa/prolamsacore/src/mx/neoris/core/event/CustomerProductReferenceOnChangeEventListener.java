/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.updaters.NeorisFavoriteProductsUpdater;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author e-hicastaneda
 * 
 */
public class CustomerProductReferenceOnChangeEventListener extends AbstractEventListener<CustomerProductReferenceOnChangeEvent>
{
	private static final Logger LOG = Logger.getLogger(CustomerProductReferenceOnChangeEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	@Resource(name = "neorisFavoriteProductsUpdater")
	private NeorisFavoriteProductsUpdater neorisFavoriteProductsUpdater;

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
	protected void onEvent(final CustomerProductReferenceOnChangeEvent event)
	{
		try
		{
			LOG.info("Update favorite products from CustomerProductReference");
			neorisFavoriteProductsUpdater.updateFavoriteProductsWith(event.getCustomerProductReferenceModel());
			LOG.info("favorite products updated...");
		}
		catch (final Exception e)
		{
			LOG.error("Error while update favorite products with CustomerProductReference: " + e.getMessage());
		}
	}
}
