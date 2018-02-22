/**
 * 
 */
package mx.neoris.core.interceptors;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.event.B2BUnitSalesRepOnChangeEvent;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisB2BUnitPrepareInterceptor implements PrepareInterceptor
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisB2BUnitPrepareInterceptor.class);

	@Resource
	private EventService eventService;

	@Override
	public void onPrepare(final Object object, final InterceptorContext context) throws InterceptorException
	{
		LOG.debug("Preparing B2BUnit to save it....");

		if (object instanceof B2BUnitModel)
		{
			final B2BUnitModel b2bUnitModel = (B2BUnitModel) object;

			final B2BUnitSalesRepOnChangeEvent event = new B2BUnitSalesRepOnChangeEvent();
			event.setB2bUnitModel(b2bUnitModel);

			//final B2BUnitBackofficeOnChangeEvent event2 = new B2BUnitBackofficeOnChangeEvent();
			//event2.setB2bUnitModel(b2bUnitModel);

			if (context.isNew(b2bUnitModel))
			{
				// insert a new B2BUnit
				LOG.debug("Prepare B2BUnit to insert a new B2BUnit: " + b2bUnitModel.getUid());
			}
			else
			{
				// update an existing B2BUnit
				LOG.debug("Prepare B2BUnit to update an existing B2BUnit: " + b2bUnitModel.getUid());
				final Set<NeorisB2BUnitSettingByStoreModel> originalSalesRepSettings = b2bUnitModel.getItemModelContext()
						.getOriginalValue(B2BUnitModel.SALESREPFORSTORE);
				event.setOriginalSettings(originalSalesRepSettings);

				//final String backoffice = b2bUnitModel.getItemModelContext().getOriginalValue(B2BUnitModel.BACKOFFICEACCOUNT);
				//event2.setOriginalBackofficeId(backoffice);

			}

			eventService.publishEvent(event);
			//eventService.publishEvent(event2);
		}
	}
}
