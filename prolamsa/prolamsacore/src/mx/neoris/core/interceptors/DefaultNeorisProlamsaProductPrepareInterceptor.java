/**
 * 
 */
package mx.neoris.core.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import mx.neoris.core.model.ProlamsaProductModel;

import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class DefaultNeorisProlamsaProductPrepareInterceptor implements PrepareInterceptor
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisProlamsaProductPrepareInterceptor.class);

	@Override
	public void onPrepare(final Object object, final InterceptorContext context) throws InterceptorException
	{
		LOG.debug("Preparing ProlamsaProduct to save it....");

		if (object instanceof ProlamsaProductModel)
		{
			if (context.isNew(object))
			{
				final ProlamsaProductModel prolamsaProductModel = (ProlamsaProductModel) object;
				// copy code value into codeForFavorites property
				prolamsaProductModel.setCodeForFavorites(prolamsaProductModel.getCode());
			}
		}

		LOG.debug("ProlamsaProduct saved....");
	}
}