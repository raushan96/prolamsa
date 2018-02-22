/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.acceleratorservices.email.dao.EmailAddressDao;
import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailGenerationService;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageTemplateModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commons.model.renderer.RendererTemplateModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.BaseStoreModel;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.model.AddedIncidentProcessModel;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.model.QuoteRequestedProcessModel;
import mx.neoris.core.model.SalesAreaModel;
import mx.neoris.core.services.dao.impl.DefaultNeorisUserDao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;


/**
 * @author e-jecarrilloi
 * @modified CLS
 * 
 */
public class DefaultNeorisEmailGenerationService extends DefaultEmailGenerationService
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisEmailGenerationService.class);

	@Resource(name = "emailAddressDao")
	private EmailAddressDao emailAddressDao;

	@Resource(name = "neorisUserDao")
	private DefaultNeorisUserDao neorisUserDao;

	@Override
	public EmailMessageModel generate(final BusinessProcessModel businessProcessModel, final EmailPageModel emailPageModel)
			throws RuntimeException
	{
		ServicesUtil.validateParameterNotNull(emailPageModel, "EmailPageModel cannot be null");
		Assert.isInstanceOf(EmailPageTemplateModel.class, emailPageModel.getMasterTemplate(),
				"MasterTemplate associated with EmailPageModel should be EmailPageTemplate");

		final EmailPageTemplateModel emailPageTemplateModel = (EmailPageTemplateModel) emailPageModel.getMasterTemplate();
		final RendererTemplateModel bodyRenderTemplate = emailPageTemplateModel.getHtmlTemplate();
		Assert.notNull(bodyRenderTemplate, "HtmlTemplate associated with MasterTemplate of EmailPageModel cannot be null");
		final RendererTemplateModel subjectRenderTemplate = emailPageTemplateModel.getSubject();
		Assert.notNull(subjectRenderTemplate, "Subject associated with MasterTemplate of EmailPageModel cannot be null");

		final EmailMessageModel emailMessageModel;
		//This call creates the context to be used for rendering of subject and body templates.  
		final AbstractEmailContext<BusinessProcessModel> emailContext = getEmailContextFactory().create(businessProcessModel,
				emailPageModel, bodyRenderTemplate);

		if (emailContext == null)
		{
			LOG.error("Failed to create email context for businessProcess [" + businessProcessModel + "]");
			throw new RuntimeException("Failed to create email context for businessProcess [" + businessProcessModel + "]");
		}
		else
		{
			if (!validate(emailContext))
			{
				LOG.error("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
						+ ReflectionToStringBuilder.toString(emailContext));
				throw new RuntimeException("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
						+ ReflectionToStringBuilder.toString(emailContext));
			}

			final StringWriter subject = new StringWriter();
			getRendererService().render(subjectRenderTemplate, emailContext, subject);

			final StringWriter body = new StringWriter();
			getRendererService().render(bodyRenderTemplate, emailContext, body);

			if (businessProcessModel instanceof OrderProcessModel)
			{
				emailMessageModel = createEmailMessageOrder(subject.toString(), body.toString(), emailContext, businessProcessModel);
			}
			else if (businessProcessModel instanceof QuoteRequestedProcessModel)
			{
				emailMessageModel = createEmailMessageQuote(subject.toString(), body.toString(), emailContext, businessProcessModel);
			}
			else if (businessProcessModel instanceof AddedIncidentProcessModel)
			{
				emailMessageModel = createEmailMessage(subject.toString(), body.toString(), emailContext, businessProcessModel);
			}
			else
			{
				emailMessageModel = super.createEmailMessage(subject.toString(), body.toString(), emailContext);
			}

			if (LOG.isDebugEnabled())
			{
				LOG.debug("Email Subject: " + emailMessageModel.getSubject());
				LOG.debug("Email Body: " + emailMessageModel.getBody());
			}

		}

		return emailMessageModel;
	}

	protected EmailMessageModel createEmailMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext, final BusinessProcessModel businessProcessModel)
	{
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		EmailAddressModel toAddress = emailAddressDao.findEmailAddressByEmailAndDisplayName(
				((AddedIncidentProcessModel) businessProcessModel).getIncident().getEmail(),
				((AddedIncidentProcessModel) businessProcessModel).getIncident().getEmail());

		if (toAddress == null)
		{
			toAddress = new EmailAddressModel();
			toAddress.setEmailAddress(((AddedIncidentProcessModel) businessProcessModel).getIncident().getEmail());
			toAddress.setDisplayName(((AddedIncidentProcessModel) businessProcessModel).getIncident().getEmail());
		}

		toEmails.add(toAddress);

		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
				emailContext.getFromDisplayName());

		final EmailAddressModel incidentCreator = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());

		final List<EmailAddressModel> ccEmails = new ArrayList<EmailAddressModel>();
		final String emailAddressString = ((AddedIncidentProcessModel) businessProcessModel).getStore().getQualityEmail();

		EmailAddressModel ccAddressQuality = new EmailAddressModel();
		//EmailAddressModel ccAddressCreatorIncident = new EmailAddressModel();
		Boolean qualityIn = false;
		//Boolean creatorIncidentIn = false;

		if (emailAddressString != null)
		{
			ccAddressQuality = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailAddressString, emailAddressString);
			qualityIn = true;
		}

		if (qualityIn)
		{
			if (ccAddressQuality == null)
			{
				ccAddressQuality = new EmailAddressModel();
				ccAddressQuality.setEmailAddress(emailAddressString);
				ccAddressQuality.setDisplayName(emailAddressString);

				ccEmails.add(ccAddressQuality);
			}
			else
			{
				ccEmails.add(ccAddressQuality);
			}
		}

		//Se agrega copia de correo al creador del incidente
		/*
		 * EmailAddressModel ccFromAddress = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailAddressString,
		 * emailAddressString);
		 * 
		 * if (ccAddress == null) { ccAddress = new EmailAddressModel(); ccAddress.setEmailAddress(emailAddressString);
		 * ccAddress.setDisplayName(emailAddressString); } ccEmails.add(ccAddress);
		 */
		ccEmails.add(incidentCreator);
		//

		return getEmailService().createEmailMessage(toEmails, ccEmails, new ArrayList<EmailAddressModel>(), fromAddress,
				emailContext.getFromEmail(), emailSubject, emailBody, null);
	}

	protected String getSalesRepFor(final OrderModel orderModel)
	{
		final BaseStoreModel orderBaseStore = orderModel.getStore();
		final B2BUnitModel orderB2BUnit = orderModel.getUnit();
		final Set<NeorisB2BUnitSettingByStoreModel> salesRepIds = orderB2BUnit.getSalesRepForStore();

		String salesRepId = "00000";

		for (final NeorisB2BUnitSettingByStoreModel eachSaleRepId : salesRepIds)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase(eachSaleRepId.getStoreId()))
			{
				salesRepId = eachSaleRepId.getSetting();
			}
		}



		return salesRepId;
	}

	protected String getBackOfficeAccountFor(final OrderModel orderModel)
	{
		final BaseStoreModel orderBaseStore = orderModel.getStore();
		final B2BUnitModel orderB2BUnit = orderModel.getUnit();
		final Set<NeorisB2BUnitSettingByStoreModel> backofficeAccounts = orderB2BUnit.getBackOfficeAccountForStore();

		String backofficeID = "00000";

		for (final NeorisB2BUnitSettingByStoreModel eachBackoffice : backofficeAccounts)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase(eachBackoffice.getStoreId()))
			{
				backofficeID = eachBackoffice.getSetting();
			}
		}



		return backofficeID;
	}

	protected EmailMessageModel createEmailMessageOrder(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext, final BusinessProcessModel businessProcessModel)
	{
		LOG.info("Generating Email for order: " + ((OrderProcessModel) businessProcessModel).getOrder().getCode() + " store: "
				+ ((OrderProcessModel) businessProcessModel).getOrder().getStore().getUid() + " b2bunit: "
				+ ((OrderProcessModel) businessProcessModel).getOrder().getUnit().getUid() + " customer: "
				+ ((OrderProcessModel) businessProcessModel).getOrder().getUser().getUid());
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());
		toEmails.add(toAddress);
		if (toAddress != null)
		{
			LOG.info("toAddress: " + toAddress.getEmailAddress());
		}

		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
				emailContext.getFromDisplayName());
		if (fromAddress != null)
		{
			LOG.info("fromAddress: " + fromAddress.getEmailAddress());
		}


		//		final String salesRep = ((OrderProcessModel) businessProcessModel).getOrder().getUnit().getSalesRep();
		final String salesRep = this.getSalesRepFor(((OrderProcessModel) businessProcessModel).getOrder());
		LOG.info("Sales rep Id from B2BUnit: " + salesRep);
		//		final String backoffice = ((OrderProcessModel) businessProcessModel).getOrder().getUnit().getBackOfficeAccount();
		final String backoffice = this.getBackOfficeAccountFor(((OrderProcessModel) businessProcessModel).getOrder());
		LOG.info("backoffice from B2BUnit: " + backoffice);
		final String emailGenericNotification = ((OrderProcessModel) businessProcessModel).getOrder().getStore()
				.getGenericNotificationEmail();
		LOG.info("emailGenericNotification: " + emailGenericNotification);
		final List<EmailAddressModel> ccEmails = new ArrayList<EmailAddressModel>();
		String emailSalesRep = null;
		String emailBackoffice = null;


		try
		{
			final Boolean salesRepIsZero = numberCodeisZero(salesRep);
			if (!salesRepIsZero)
			{
				emailSalesRep = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(salesRep,
						((OrderProcessModel) businessProcessModel).getOrder().getStore()).getEmail();
				LOG.info("Sales rep User: " + emailSalesRep);
			}
		}
		catch (final Exception e)
		{
			//nothing to do
		}

		try
		{
			final Boolean backofficeIsZero = numberCodeisZero(backoffice);
			if (!backofficeIsZero)
			{
				emailBackoffice = neorisUserDao.getB2bCustomerModelByBackofficeAndStore(backoffice,
						((OrderProcessModel) businessProcessModel).getOrder().getStore()).getEmail();
				LOG.info("Backoffice User: " + emailBackoffice);
			}
		}
		catch (final Exception e)
		{
			//nothing to do
		}

		EmailAddressModel ccAddressSalesRep = new EmailAddressModel();
		EmailAddressModel ccAddressBackoffice = new EmailAddressModel();
		EmailAddressModel ccAddressGenericEmail = new EmailAddressModel();
		Boolean salesRepIn = false;
		Boolean backofficeIn = false;
		Boolean genericNotificationIn = false;

		if (emailSalesRep != null)
		{
			ccAddressSalesRep = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailSalesRep, emailSalesRep);
			salesRepIn = true;
		}
		if (emailBackoffice != null)
		{
			ccAddressBackoffice = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailBackoffice, emailBackoffice);
			backofficeIn = true;
		}
		if (emailGenericNotification != null)
		{
			ccAddressGenericEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailGenericNotification,
					emailGenericNotification);
			genericNotificationIn = true;
		}

		if (salesRepIn)
		{
			if (ccAddressSalesRep == null)
			{
				ccAddressSalesRep = new EmailAddressModel();
				ccAddressSalesRep.setEmailAddress(emailSalesRep);
				ccAddressSalesRep.setDisplayName(emailSalesRep);

				ccEmails.add(ccAddressSalesRep);
			}
			else
			{
				ccEmails.add(ccAddressSalesRep);
			}
		}

		if (backofficeIn)
		{
			if (ccAddressBackoffice == null)
			{
				ccAddressBackoffice = new EmailAddressModel();
				ccAddressBackoffice.setEmailAddress(emailBackoffice);
				ccAddressBackoffice.setDisplayName(emailBackoffice);

				ccEmails.add(ccAddressBackoffice);
			}
			else
			{
				ccEmails.add(ccAddressBackoffice);
			}
		}

		if (genericNotificationIn)
		{
			if (ccAddressGenericEmail == null)
			{
				ccAddressGenericEmail = new EmailAddressModel();
				ccAddressGenericEmail.setEmailAddress(emailGenericNotification);
				ccAddressGenericEmail.setDisplayName(emailGenericNotification);

				ccEmails.add(ccAddressGenericEmail);
			}
			else
			{
				ccEmails.add(ccAddressGenericEmail);
			}
		}

		final EmailAddressModel emailManager = getSalesAreaManagerEmail(businessProcessModel);

		if (emailManager != null)
		{
			ccEmails.add(emailManager);
		}
		
		//******************INICIO***************
	//11Ene2017 Se agrega copia de la notificacion al correo de contacto en el B2BUnit
	if (((OrderProcessModel) businessProcessModel).getOrder().getStore().getActiveSendNotificationToContactClient())
	{ 
		final String emailContactB2BUnitNotification = ((OrderProcessModel) businessProcessModel).getOrder().getUnit()
				.getEmailNotification();
		EmailAddressModel ccAddressContactB2BUnitEmail = new EmailAddressModel();
		Boolean contactB2BUnitNotificationIn = false;

		if (emailContactB2BUnitNotification != null)
		{
			ccAddressContactB2BUnitEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailContactB2BUnitNotification,
					emailContactB2BUnitNotification);
			contactB2BUnitNotificationIn = true;
		}

		if (contactB2BUnitNotificationIn)
		{
			if (ccAddressContactB2BUnitEmail == null)
			{
				ccAddressContactB2BUnitEmail = new EmailAddressModel();
				ccAddressContactB2BUnitEmail.setEmailAddress(emailContactB2BUnitNotification);
				ccAddressContactB2BUnitEmail.setDisplayName(emailContactB2BUnitNotification);

				ccEmails.add(ccAddressContactB2BUnitEmail);
			}
			else
			{
				ccEmails.add(ccAddressContactB2BUnitEmail);
			}
		}
	}
	//******************FIN***************
	//CILS 27Feb2017 Se agrega con copia oculta Jose Gonzalez/MonicaRodriguez
	if (((OrderProcessModel) businessProcessModel).getOrder().getStore().getSendNotificactionOrderHiddenJGzzMRdz())
	{
		final List<EmailAddressModel> ccoEmails = new ArrayList<EmailAddressModel>();

		EmailAddressModel ccoAddressJGzzEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(
			"raushan10dm087@gmail.com", "raushan10dm087@gmail.com");
		EmailAddressModel ccoAddressMRdzEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(
			"monica.rodriguez@prolamsa.com", "monica.rodriguez@prolamsa.com");

		//Jose Gonzalez
		if (ccoAddressJGzzEmail == null)
		{
			ccoAddressJGzzEmail = new EmailAddressModel();
			ccoAddressJGzzEmail.setEmailAddress("jose.gonzalez@prolamsa.com");
			ccoAddressJGzzEmail.setDisplayName("jose.gonzalez@prolamsa.com");

			ccoEmails.add(ccoAddressJGzzEmail);
		}
		else
		{
			ccoEmails.add(ccoAddressJGzzEmail);
		}

		//Monica Rodriguez
		if (ccoAddressMRdzEmail == null)
		{
			ccoAddressMRdzEmail = new EmailAddressModel();
			ccoAddressMRdzEmail.setEmailAddress("monica.rodriguez@prolamsa.com");
			ccoAddressMRdzEmail.setDisplayName("monica.rodriguez@prolamsa.com");

			ccoEmails.add(ccoAddressMRdzEmail);
		}
		else
		{
			ccoEmails.add(ccoAddressMRdzEmail);
		}

		return getEmailService().createEmailMessage(toEmails, ccEmails, ccoEmails, fromAddress, emailContext.getFromEmail(),
			emailSubject, emailBody, null);
	}
	else
	{
		return getEmailService().createEmailMessage(toEmails, ccEmails, new ArrayList<EmailAddressModel>(), fromAddress,
			emailContext.getFromEmail(), emailSubject, emailBody, null);
	}
}

	protected EmailAddressModel getSalesAreaManagerEmail(final BusinessProcessModel businessProcessModel)
	{
		OrderModel orderModel = new OrderModel();

		if (businessProcessModel instanceof OrderProcessModel)
		{
			orderModel = ((OrderProcessModel) businessProcessModel).getOrder();
		}
		else if (businessProcessModel instanceof QuoteRequestedProcessModel)
		{
			orderModel = ((QuoteRequestedProcessModel) businessProcessModel).getOrder();
		}

		final BaseStoreModel baseStoreModel = orderModel.getStore();
		final B2BUnitModel b2bUnitModel = orderModel.getUnit();
		B2BCustomerModel salesAreaManager = null;

		String salesAreaCode = null;

		final Set<NeorisB2BUnitSettingByStoreModel> salesAreaSettings = b2bUnitModel.getSalesAreaForStore();

		if (salesAreaSettings != null)
		{
			for (final NeorisB2BUnitSettingByStoreModel eachSetting : salesAreaSettings)
			{
				if (eachSetting.getStoreId().equalsIgnoreCase(baseStoreModel.getUid()))
				{
					salesAreaCode = eachSetting.getSetting();
				}
			}
		}

		if (baseStoreModel.getSalesAreaManagers() != null)
		{
			for (final SalesAreaModel eachSalesArea : baseStoreModel.getSalesAreaManagers())
			{
				if (eachSalesArea.getCode().equalsIgnoreCase(salesAreaCode))
				{
					salesAreaManager = eachSalesArea.getManager();
				}
			}
		}

		if (salesAreaManager != null && salesAreaManager.getActive())
		{
			if (StringUtils.isNotBlank(salesAreaManager.getEmail()))
			{
				final EmailAddressModel emailAddress = emailAddressDao.findEmailAddressByEmailAndDisplayName(
						salesAreaManager.getEmail(), salesAreaManager.getEmail());

				if (emailAddress == null)
				{
					final EmailAddressModel emailManager = new EmailAddressModel();
					emailManager.setEmailAddress(salesAreaManager.getEmail());
					emailManager.setDisplayName(salesAreaManager.getEmail());

					return emailManager;
				}
				else
				{
					return emailAddress;
				}
			}

		}


		return null;
	}

	protected EmailMessageModel createEmailMessageQuote(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext, final BusinessProcessModel businessProcessModel)
	{

		//TODO: Se agrega codigo de debugeo para issue 462, remover cuando no sea necesario
		LOG.info("Generating Email for quote: " + ((QuoteRequestedProcessModel) businessProcessModel).getOrder().getCode()
				+ " store: " + ((QuoteRequestedProcessModel) businessProcessModel).getOrder().getStore().getUid() + " b2bunit: "
				+ ((QuoteRequestedProcessModel) businessProcessModel).getOrder().getUnit().getUid() + " customer: "
				+ ((QuoteRequestedProcessModel) businessProcessModel).getOrder().getUser().getUid());
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());
		toEmails.add(toAddress);
		if (toAddress != null)
		{
			LOG.info("toAddress: " + toAddress.getEmailAddress());
		}

		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
				emailContext.getFromDisplayName());
		if (fromAddress != null)
		{
			LOG.info("fromAddress: " + fromAddress.getEmailAddress());
		}

		final String salesRep = this.getSalesRepFor(((QuoteRequestedProcessModel) businessProcessModel).getOrder());
		LOG.info("Sales rep Id from B2BUnit: " + salesRep);

		final String backoffice = this.getBackOfficeAccountFor(((QuoteRequestedProcessModel) businessProcessModel).getOrder());
		LOG.info("backoffice from B2BUnit: " + backoffice);

		final String emailGenericNotification = ((QuoteRequestedProcessModel) businessProcessModel).getOrder().getStore()
				.getGenericNotificationEmail();
		LOG.info("emailGenericNotification: " + emailGenericNotification);

		final List<EmailAddressModel> ccEmails = new ArrayList<EmailAddressModel>();
		String emailSalesRep = null;
		String emailBackoffice = null;

		try
		{
			final Boolean salesRepIsZero = numberCodeisZero(salesRep);
			if (!salesRepIsZero)
			{
				emailSalesRep = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(salesRep,
						((QuoteRequestedProcessModel) businessProcessModel).getOrder().getStore()).getEmail();
				LOG.info("Sales rep User: " + emailSalesRep);
			}
		}
		catch (final Exception e)
		{
			//nothing to do
		}

		try
		{
			final Boolean backofficeIsZero = numberCodeisZero(backoffice);
			if (!backofficeIsZero)
			{
				emailBackoffice = neorisUserDao.getB2bCustomerModelByBackofficeAndStore(backoffice,
						((QuoteRequestedProcessModel) businessProcessModel).getOrder().getStore()).getEmail();
				LOG.info("Backoffice User: " + emailBackoffice);
			}
		}
		catch (final Exception e)
		{
			//nothing to do
		}

		EmailAddressModel ccAddressSalesRep = new EmailAddressModel();
		EmailAddressModel ccAddressBackoffice = new EmailAddressModel();
		EmailAddressModel ccAddressGenericEmail = new EmailAddressModel();
		Boolean salesRepIn = false;
		Boolean backofficeIn = false;
		Boolean genericNotificationIn = false;

		if (emailSalesRep != null)
		{
			ccAddressSalesRep = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailSalesRep, emailSalesRep);
			salesRepIn = true;
		}
		if (emailBackoffice != null)
		{
			ccAddressBackoffice = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailBackoffice, emailBackoffice);
			backofficeIn = true;
		}
		if (emailGenericNotification != null)
		{
			ccAddressGenericEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailGenericNotification,
					emailGenericNotification);
			genericNotificationIn = true;
		}

		if (salesRepIn)
		{
			if (ccAddressSalesRep == null)
			{
				ccAddressSalesRep = new EmailAddressModel();
				ccAddressSalesRep.setEmailAddress(emailSalesRep);
				ccAddressSalesRep.setDisplayName(emailSalesRep);

				ccEmails.add(ccAddressSalesRep);
			}
			else
			{
				ccEmails.add(ccAddressSalesRep);
			}
		}

		if (backofficeIn)
		{
			if (ccAddressBackoffice == null)
			{
				ccAddressBackoffice = new EmailAddressModel();
				ccAddressBackoffice.setEmailAddress(emailBackoffice);
				ccAddressBackoffice.setDisplayName(emailBackoffice);

				ccEmails.add(ccAddressBackoffice);
			}
			else
			{
				ccEmails.add(ccAddressBackoffice);
			}
		}

		if (genericNotificationIn)
		{
			if (ccAddressGenericEmail == null)
			{
				ccAddressGenericEmail = new EmailAddressModel();
				ccAddressGenericEmail.setEmailAddress(emailGenericNotification);
				ccAddressGenericEmail.setDisplayName(emailGenericNotification);

				ccEmails.add(ccAddressGenericEmail);
			}
			else
			{
				ccEmails.add(ccAddressGenericEmail);
			}
		}

		final EmailAddressModel emailManager = getSalesAreaManagerEmail(businessProcessModel);

		if (emailManager != null)
		{
			ccEmails.add(emailManager);
		}
		
		//******************INICIO***************
	//11Ene2017 Se agrega copia de la notificacion al correo de contacto en el B2BUnit
	if (((QuoteRequestedProcessModel) businessProcessModel).getOrder().getStore().getActiveSendNotificationToContactClient())
	{ 
final String emailContactB2BUnitNotification = ((QuoteRequestedProcessModel) businessProcessModel).getOrder().getUnit()
				.getEmailNotification();
		EmailAddressModel ccAddressContactB2BUnitEmail = new EmailAddressModel();
		Boolean contactB2BUnitNotificationIn = false;

		if (emailContactB2BUnitNotification != null)
		{
			ccAddressContactB2BUnitEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(emailContactB2BUnitNotification,
					emailContactB2BUnitNotification);
			contactB2BUnitNotificationIn = true;
		}

		if (contactB2BUnitNotificationIn)
		{
			if (ccAddressContactB2BUnitEmail == null)
			{
				ccAddressContactB2BUnitEmail = new EmailAddressModel();
				ccAddressContactB2BUnitEmail.setEmailAddress(emailContactB2BUnitNotification);
				ccAddressContactB2BUnitEmail.setDisplayName(emailContactB2BUnitNotification);

				ccEmails.add(ccAddressContactB2BUnitEmail);
			}
			else
			{
				ccEmails.add(ccAddressContactB2BUnitEmail);
			}
		}
	}
	//******************FIN***************
	//CILS 27Feb2017 Se agrega con copia oculta Jose Gonzalez/MonicaRodriguez
	if (((QuoteRequestedProcessModel) businessProcessModel).getOrder().getStore().getSendNotificactionOrderHiddenJGzzMRdz())
	{
		final List<EmailAddressModel> ccoEmails = new ArrayList<EmailAddressModel>();
		EmailAddressModel ccoAddressJGzzEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(
			"jose.gonzalez@prolamsa.com", "jose.gonzalez@prolamsa.com");
		EmailAddressModel ccoAddressMRdzEmail = emailAddressDao.findEmailAddressByEmailAndDisplayName(
			"monica.rodriguez@prolamsa.com", "monica.rodriguez@prolamsa.com");

		//Jose Gonzalez
		if (ccoAddressJGzzEmail == null)
		{
			ccoAddressJGzzEmail = new EmailAddressModel();
			ccoAddressJGzzEmail.setEmailAddress("jose.gonzalez@prolamsa.com");
			ccoAddressJGzzEmail.setDisplayName("jose.gonzalez@prolamsa.com");

			ccoEmails.add(ccoAddressJGzzEmail);
		}
		else
		{
			ccoEmails.add(ccoAddressJGzzEmail);
		}

		//Monica Rodriguez
		if (ccoAddressMRdzEmail == null)
		{
			ccoAddressMRdzEmail = new EmailAddressModel();
			ccoAddressMRdzEmail.setEmailAddress("monica.rodriguez@prolamsa.com");
			ccoAddressMRdzEmail.setDisplayName("monica.rodriguez@prolamsa.com");

			ccoEmails.add(ccoAddressMRdzEmail);
		}
		else
		{
			ccoEmails.add(ccoAddressMRdzEmail);
		}

		return getEmailService().createEmailMessage(toEmails, ccEmails, ccoEmails, fromAddress, emailContext.getFromEmail(),
			emailSubject, emailBody, null);
	}
	else
	{
		return getEmailService().createEmailMessage(toEmails, ccEmails, new ArrayList<EmailAddressModel>(), fromAddress,
			emailContext.getFromEmail(), emailSubject, emailBody, null);
	}
}

	public Boolean numberCodeisZero(final String valueString)
	{
		final Integer lengthValue = valueString.length();
		Integer flag = 0;

		for (int i = 0; i < lengthValue; i++)
		{
			if (valueString.charAt(i) == '0')
			{
				flag++;
			}
		}

		if (lengthValue == flag)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
