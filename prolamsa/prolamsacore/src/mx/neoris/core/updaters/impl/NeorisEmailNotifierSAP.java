/**
 * 
 */
package mx.neoris.core.updaters.impl;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.List;

//import java.io.File;
import javax.annotation.Resource;

import org.apache.log4j.Logger;


/**
 * @author christian.loredo
 * 
 */
public class NeorisEmailNotifierSAP
{
	private static final Logger LOG = Logger.getLogger(NeorisEmailNotifierSAP.class);

	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private String storeUid;
	private String siteUid;

	private String notificationUserUid;

	@Resource(name = "emailService")
	private EmailService emailService;



	public void sendEmailMessageWith(final String subject, final String content)
	{
		final String eol = "<br>";

		final List<EmailAddressModel> listSendTo = new ArrayList<EmailAddressModel>();
		final String to = configurationService.getConfiguration().getString("errorRFC.email");
		final EmailAddressModel emailAddress = emailService.getOrCreateEmailAddressForEmail(to, "");
		emailAddress.setEmailAddress(to);

		listSendTo.add(emailAddress);
		final EmailMessageModel message = emailService.createEmailMessage(listSendTo, null, null, emailAddress,
				"no-reply@prolamsa.com", subject, content, null);

		emailService.send(message);

	}
	
	public void sendEmailMessageWith(final String subject, final String content, final String salesRep, final String backoffice,
			final String userCreator)
	{
		final String eol = "<br>";

		final List<EmailAddressModel> listSendTo = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccEmails = new ArrayList<EmailAddressModel>();
		final String to = configurationService.getConfiguration().getString("error.create.order.quote.email");
		final EmailAddressModel emailAddress = emailService.getOrCreateEmailAddressForEmail(to, "");
		emailAddress.setEmailAddress(to);
		listSendTo.add(emailAddress);

		if (salesRep != "" || salesRep != null)
		{
			final EmailAddressModel emailAddressSales = emailService.getOrCreateEmailAddressForEmail(salesRep, "");
			emailAddressSales.setEmailAddress(salesRep);
			ccEmails.add(emailAddressSales);
		}

		if (backoffice != "" || backoffice != null)
		{
			final EmailAddressModel emailAddressBackoffice = emailService.getOrCreateEmailAddressForEmail(backoffice, "");
			emailAddressBackoffice.setEmailAddress(backoffice);
			ccEmails.add(emailAddressBackoffice);
		}

		if (userCreator != "" || userCreator != null)
		{
			final EmailAddressModel emailAddressUser = emailService.getOrCreateEmailAddressForEmail(userCreator, "");
			emailAddressUser.setEmailAddress(userCreator);
			ccEmails.add(emailAddressUser);
		}


		final EmailMessageModel message = emailService.createEmailMessage(listSendTo, ccEmails, null, emailAddress,
				"no-reply@prolamsa.com", subject, content, null);

		emailService.send(message);

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
