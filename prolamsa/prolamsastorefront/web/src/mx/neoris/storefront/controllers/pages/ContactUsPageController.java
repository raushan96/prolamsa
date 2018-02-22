/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.jalo.email.EmailMessage;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.localization.Localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.neoris.core.model.AddedIncidentProcessModel;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.BalanceStatementSearchForm;
import mx.neoris.storefront.forms.ContactUsForm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Contact Us Controller. Handles login and register for the account flow.
 */
@Controller
@RequestMapping(value = "/contactUs")
public class ContactUsPageController extends AbstractPageController
{
	
	@Resource(name="emailService")
	private EmailService emailService;
	
	@Resource(name="configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	private static final String CONTACT_US_CMS_PAGE = "ContactUs";
	
	private static final Logger LOG = Logger.getLogger(ContactUsPageController.class);


	@RequestMapping(value = "/show", method = {RequestMethod.GET,RequestMethod.POST})
	public String showPage(	
			final ContactUsForm contactUsForm,
		    final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		
		// save form on model
		model.addAttribute("contactUsForm", contactUsForm);
		storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
		
		return ControllerConstants.Views.Pages.Misc.ContactUsPage;
		
	}		
	
	
	@RequestMapping(value = "/sendEmail", method = {RequestMethod.GET,RequestMethod.POST})
	public String sendEmail(	
			final ContactUsForm contactUsForm,
		    final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception
	{
		
		
		if(contactUsForm.getContactArea().equals("0"))
		{
			LOG.error("Unable to retrieve Contact Area Department.");
			GlobalMessages.addErrorMessage(model, "text.contact.us.nocontact.area.error");
			model.addAttribute("contactUsForm", contactUsForm);
			storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
			return ControllerConstants.Views.Pages.Misc.ContactUsPage;
		}
		
		String eol = "<br>";
		String contactUs = null;
		String contactAreaName = null;
		String emailFrom = null;
		
		// save form on model
		//model.addAttribute("contactUsForm", contactUsForm);
		
		List<EmailAddressModel> listSendTo = new ArrayList<EmailAddressModel>();
		
		if(contactUsForm.getContactArea().equals("1"))
		{
			contactUs = baseStoreService.getCurrentBaseStore().getSalesDepartmentEmail();
			contactAreaName = getMessageWithDefaultContext("text.contact.option1");
		}else if(contactUsForm.getContactArea().equals("2"))
		{
			contactUs = baseStoreService.getCurrentBaseStore().getProductSpecsEmail();
			contactAreaName = getMessageWithDefaultContext("text.contact.option2");
		}else if(contactUsForm.getContactArea().equals("3"))
		{
			contactUs = baseStoreService.getCurrentBaseStore().getTechinicalSupportEmail();
			contactAreaName = getMessageWithDefaultContext("text.contact.option3");
		}else
		{
			contactUs = null;
		}
		
		if(contactUs == null)
		{
			LOG.error("Unable to retrieve Contact Us Email.");
			GlobalMessages.addErrorMessage(model, "text.contact.us.noemail.error");
			model.addAttribute("contactUsForm", contactUsForm);
			storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
			return ControllerConstants.Views.Pages.Misc.ContactUsPage;
		}
			
		EmailAddressModel emailAddressTo = emailService.getOrCreateEmailAddressForEmail(contactUs, "");
		emailAddressTo.setEmailAddress(contactUs);
		
		emailFrom = configurationService.getConfiguration().getString("contactUs.email");
		
		EmailAddressModel emailAddressFrom = emailService.getOrCreateEmailAddressForEmail(emailFrom, "");
		emailAddressFrom.setEmailAddress(emailFrom);
		
		
		//set the Customer Data for Email
		String subject = getMessageWithDefaultContext("text.contact.email.subject"); 
		//
		String nameLabel = getMessageWithDefaultContext("text.contact.email.body.customer.name");
		String nameData = contactUsForm.getName() + " " + contactUsForm.getLastName();
				
		String emailLabel = getMessageWithDefaultContext("text.contact.email.body.customer.email");
		String emailData = contactUsForm.getEmail();
	
		String phoneLabel = getMessageWithDefaultContext("text.contact.email.body.customer.phone");
		String phoneData = contactUsForm.getPhone();
	
		String altPhoneLabel = getMessageWithDefaultContext("text.contact.email.body.customer.altPhone");
		String altPhoneData = contactUsForm.getAltPhone();
		
		String contactAreaLabel = getMessageWithDefaultContext("text.contact.email.body.customer.contactArea");
		String contactAreaData = contactAreaName;
		
		String commentLabel = getMessageWithDefaultContext("text.contact.email.body.customer.comment");
		String commentData = contactUsForm.getComment();
		
		StringBuilder body = new StringBuilder();
		body.append(nameLabel + nameData +  eol + eol);		
		body.append(emailLabel + emailData + eol + eol);
		body.append(phoneLabel + phoneData + eol + eol);
		body.append(altPhoneLabel + altPhoneData + eol +eol);
		body.append(contactAreaLabel + contactAreaData + eol +eol );
		body.append(commentLabel + commentData);
				
		listSendTo.add(emailAddressTo);
		
		try
		{
			EmailMessageModel message = emailService.createEmailMessage(listSendTo, null, null, emailAddressFrom, "no-reply@prolamsa.com", subject, body.toString(), null);
			
			emailService.send(message);	
		}
		catch (Exception ex)
		{
			LOG.error("error when send email contactus", ex);
			GlobalMessages.addErrorMessage(model, "text.contact.us.send.email.error");
			model.addAttribute("contactUsForm", contactUsForm);
			storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
			return ControllerConstants.Views.Pages.Misc.ContactUsPage;
		}
									
		
		LOG.info("Send email contact us : SUCCESSFULL");
		ContactUsForm contactUsForm2 = new ContactUsForm();
		model.addAttribute("contactUsForm", contactUsForm2);
		GlobalMessages.addConfMessage(model, "text.contact.us.send.email.successfull");
		storeCmsPageInModel(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CONTACT_US_CMS_PAGE));
		return ControllerConstants.Views.Pages.Misc.ContactUsPage;
		
		//return REDIRECT_PREFIX + ROOT;
		
	}
}
