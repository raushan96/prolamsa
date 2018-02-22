package mx.neoris.core.updaters.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.File;

import javax.annotation.Resource;

import mx.neoris.core.event.HotFolderImportFailedEvent;

import org.apache.log4j.Logger;


/**
 * 
 */

/**
 * @author fdeutsch
 * 
 */
public abstract class NeorisEmailNotifier
{
	private static final Logger LOG = Logger.getLogger(NeorisEmailNotifier.class);

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "userService")
	private UserService userService;

	private String storeUid;
	private String siteUid;

	private String notificationUserUid;

	protected void sendEmailMessageWith(final File file, final String content)
	{
		final String hotFolderAccount = getNotificationUserUid();

		if (hotFolderAccount != null)
		{
			Registry.activateMasterTenant();

			final B2BCustomerModel customerModel = (B2BCustomerModel) userService.getUserForUID(hotFolderAccount);

			if (customerModel != null)
			{
				final HotFolderImportFailedEvent event = new HotFolderImportFailedEvent();

				event.setStackTrace(content);

				event.setFilename(file.getName());
				event.setBaseStore(getBaseStoreService().getBaseStoreForUid(getStoreUid()));
				event.setSite(getBaseSiteService().getBaseSiteForUID(getSiteUid()));
				event.setCustomer(customerModel);
				getEventService().publishEvent(event);
			}
			else
			{
				LOG.error("Hot Folder user account not found for " + hotFolderAccount + ". No email warning sent");
			}
		}
		else
		{
			LOG.error("Hot Folder user account not defined. No email warnings will be sent");
		}
	}

	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the storeUid
	 */
	public String getStoreUid()
	{
		return storeUid;
	}

	/**
	 * @param storeUid
	 *           the storeUid to set
	 */
	public void setStoreUid(final String storeUid)
	{
		this.storeUid = storeUid;
	}

	/**
	 * @return the siteUid
	 */
	public String getSiteUid()
	{
		return siteUid;
	}

	/**
	 * @param siteUid
	 *           the siteUid to set
	 */
	public void setSiteUid(final String siteUid)
	{
		this.siteUid = siteUid;
	}

	/**
	 * @return the notificationUserUid
	 */
	public String getNotificationUserUid()
	{
		return notificationUserUid;
	}

	/**
	 * @param notificationUserUid
	 *           the notificationUserUid to set
	 */
	public void setNotificationUserUid(final String notificationUserUid)
	{
		this.notificationUserUid = notificationUserUid;
	}
}
