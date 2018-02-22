/**
 * 
 */
package mx.neoris.core.interceptors;

import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import javax.annotation.Resource;

import mx.neoris.core.event.CustomerProductReferenceOnChangeEvent;
import mx.neoris.core.model.CustomerProductReferenceModel;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisCustomerProductReferencePrepareInterceptor implements PrepareInterceptor
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisCustomerProductReferencePrepareInterceptor.class);

	@Resource
	private EventService eventService;

	@Override
	public void onPrepare(final Object object, final InterceptorContext context) throws InterceptorException
	{
		LOG.debug("Preparing CustomeProductReference to save it....");

		if (object instanceof CustomerProductReferenceModel)
		{
			final CustomerProductReferenceModel customerProductReferenceModel = (CustomerProductReferenceModel) object;

			final CustomerProductReferenceOnChangeEvent event = new CustomerProductReferenceOnChangeEvent();
			event.setCustomerProductReferenceModel(customerProductReferenceModel);

			eventService.publishEvent(event);
		}

		LOG.debug("CustomeProductReference saved....");
	}
}
