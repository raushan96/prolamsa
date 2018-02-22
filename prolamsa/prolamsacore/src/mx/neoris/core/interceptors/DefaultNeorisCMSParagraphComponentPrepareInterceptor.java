/**
 * 
 */
package mx.neoris.core.interceptors;

import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import java.util.Locale;

import javax.annotation.Resource;

import mx.neoris.core.event.CMSParagraphComponentContentOnChangeEvent;

import org.apache.log4j.Logger;


/**
 * @author @author e-jecarrilloi
 * 
 */
public class DefaultNeorisCMSParagraphComponentPrepareInterceptor implements PrepareInterceptor
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisCMSParagraphComponentPrepareInterceptor.class);

	static String ID_TERMS_PARAGRAPH = "termsAndConditionsTextParagraph";
	static String ID_ORDER_CONDS_PARAGRAPH = "orderConditionsTextParagraph";



	@Resource
	private EventService eventService;

	@Override
	public void onPrepare(final Object object, final InterceptorContext context) throws InterceptorException
	{
		LOG.debug("Preparing CMSParagraphComponent to save it....");

		if (object instanceof CMSParagraphComponentModel)
		{
			if (context.isNew(object))
			{
				return;
			}

			final CMSParagraphComponentModel cmsParagraphComponentModel = (CMSParagraphComponentModel) object;

			if (cmsParagraphComponentModel.getUid().equalsIgnoreCase(ID_ORDER_CONDS_PARAGRAPH)
					|| cmsParagraphComponentModel.getUid().equalsIgnoreCase(ID_TERMS_PARAGRAPH))
			{


				final Boolean hasToUpdateCustomers = !(cmsParagraphComponentModel.getItemModelContext()
						.getOriginalValue("content", new Locale("en")).equals(cmsParagraphComponentModel.getContent(new Locale("en"))) && cmsParagraphComponentModel
						.getItemModelContext().getOriginalValue("content", new Locale("es"))
						.equals(cmsParagraphComponentModel.getContent(new Locale("es"))));
				if (hasToUpdateCustomers)
				{
					LOG.debug("The content has changed, update customers");
					final CMSParagraphComponentContentOnChangeEvent event = new CMSParagraphComponentContentOnChangeEvent();
					event.setCmsParagraphComponentModel(cmsParagraphComponentModel);

					eventService.publishEvent(event);
				}
			}
		}

		LOG.debug("CMSParagraphComponent saved....");

	}
}
