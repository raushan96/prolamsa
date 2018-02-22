/**
 * 
 */
package mx.neoris.core.interceptors;

import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import javax.annotation.Resource;

import mx.neoris.core.event.B2BUnitSettingByStoreOnChangeEvent;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisB2BUnitSettingByStorePrepareInterceptor implements PrepareInterceptor
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisB2BUnitSettingByStorePrepareInterceptor.class);

	@Resource
	private EventService eventService;

	@Override
	public void onPrepare(final Object object, final InterceptorContext context) throws InterceptorException
	{
		LOG.debug("Preparing B2BUnit to save it....");

		if (object instanceof NeorisB2BUnitSettingByStoreModel)
		{

			final NeorisB2BUnitSettingByStoreModel neorisB2BUnitSettingByStoreModel = (NeorisB2BUnitSettingByStoreModel) object;

			if (!context.isNew(neorisB2BUnitSettingByStoreModel))
			{

				final B2BUnitSettingByStoreOnChangeEvent event = new B2BUnitSettingByStoreOnChangeEvent();

				event.setB2BUnitOwner(neorisB2BUnitSettingByStoreModel.getB2bUnitOwner());
				event.setStoreId(neorisB2BUnitSettingByStoreModel.getStoreId());
				event.setSetting(neorisB2BUnitSettingByStoreModel.getSetting());
				event.setOriginalSetting(String.valueOf(neorisB2BUnitSettingByStoreModel.getItemModelContext().getOriginalValue(
						NeorisB2BUnitSettingByStoreModel.SETTING)));


				eventService.publishEvent(event);

			}
		}
	}
}
