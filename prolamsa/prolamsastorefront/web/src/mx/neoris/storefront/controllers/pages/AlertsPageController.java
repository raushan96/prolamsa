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

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.data.AlertConfigurationData;
import de.hybris.platform.cronjob.enums.DayOfWeek;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.enums.NoticeType;
import mx.neoris.core.model.AlertConfigurationModel;
import mx.neoris.core.model.AlertModel;
import mx.neoris.core.services.AlertsConfigurationParameters;
import mx.neoris.facades.NeorisAlertsFacade;
import mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisNoticesFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.pages.checkout.SingleStepCheckoutController.SelectOption;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.AlertConfigurationSearchForm;
import mx.neoris.storefront.forms.AlertsConfigurationForm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller for home page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/my-account/alerts")
public class AlertsPageController extends AbstractSearchPageController
{
	
	// CMS Pages
	private static final String ALERTS_CMS_PAGE = "AlertsListPage";
	
	private static final String SUNDAY_NUMBER="1";
	private static final String MONDAY_NUMBER="2";
	private static final String TUESDAY_NUMBER="3";
	private static final String WEDNESDAY_NUMBER="4";
	private static final String THURSDAY_NUMBER="5";
	private static final String FRIDAY_NUMBER="6";
	private static final String SATURDAY_NUMBER="7";
	
	private static final String SUNDAY_NAME="SUNDAY";
	private static final String MONDAY_NAME="MONDAY";
	private static final String TUESDAY_NAME="TUESDAY";
	private static final String WEDNESDAY_NAME="WEDNESDAY";
	private static final String THURSDAY_NAME="THURSDAY";
	private static final String FRIDAY_NAME="FRIDAY";
	private static final String SATURDAY_NAME="SATURDAY";
	
	private static final Logger LOG = Logger.getLogger(AlertsPageController.class);
	
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "dateFormatter_yyyy-MM-dd")	
	private SimpleDateFormat dateFormatter;
	
	@Resource(name = "neorisNoticesFacade")
	private NeorisNoticesFacade neorisNoticesFacade;
	
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource
	private MediaService mediaService;
	
	@Resource(name="neorisB2BCustomerDefaultSettingsFacade")
	NeorisB2BCustomerDefaultSettingsFacade neorisB2BCustomerDefaultSettingsFacade;
	
	@Resource(name = "neorisAlertsFacade")
	private NeorisAlertsFacade neorisAlertsFacade;
	
	@ModelAttribute("noticeTypes")
	public List<NoticeType> getNoticeTypes()
	{
		return enumerationService.getEnumerationValues(NoticeType.class);
	}
	
	@ModelAttribute("daysOfWeek")
	public List<SelectOption> getDaysOfWeek()
	{
		final List<SelectOption> daysOfWeek = new ArrayList<SelectOption>();
		
		daysOfWeek.add(new SelectOption(SUNDAY_NUMBER, SUNDAY_NAME));
		daysOfWeek.add(new SelectOption(MONDAY_NUMBER, MONDAY_NAME));
		daysOfWeek.add(new SelectOption(TUESDAY_NUMBER, TUESDAY_NAME));
		daysOfWeek.add(new SelectOption(WEDNESDAY_NUMBER, WEDNESDAY_NAME));
		daysOfWeek.add(new SelectOption(THURSDAY_NUMBER, THURSDAY_NAME));
		daysOfWeek.add(new SelectOption(FRIDAY_NUMBER, FRIDAY_NAME));
		daysOfWeek.add(new SelectOption(SATURDAY_NUMBER, SATURDAY_NAME));
		
		return daysOfWeek;
	}
	
	@ModelAttribute("hours")
	public List<SelectOption> getHours()
	{
		final List<SelectOption> hours = new ArrayList<SelectOption>();

		hours.add(new SelectOption("00AM", "12:00 AM"));
		hours.add(new SelectOption("01AM", "01:00 AM"));
		hours.add(new SelectOption("02AM", "02:00 AM"));
		hours.add(new SelectOption("03AM", "03:00 AM"));
		hours.add(new SelectOption("04AM", "04:00 AM"));
		hours.add(new SelectOption("05AM", "05:00 AM"));
		hours.add(new SelectOption("06AM", "06:00 AM"));
		hours.add(new SelectOption("07AM", "07:00 AM"));
		hours.add(new SelectOption("08AM", "08:00 AM"));
		hours.add(new SelectOption("09AM", "09:00 AM"));
		hours.add(new SelectOption("10AM", "10:00 AM"));
		hours.add(new SelectOption("11AM", "11:00 AM"));
		
		hours.add(new SelectOption("12PM", "12:00 PM"));
		hours.add(new SelectOption("01PM", "01:00 PM"));
		hours.add(new SelectOption("02PM", "02:00 PM"));
		hours.add(new SelectOption("03PM", "03:00 PM"));
		hours.add(new SelectOption("04PM", "04:00 PM"));
		hours.add(new SelectOption("05PM", "05:00 PM"));
		hours.add(new SelectOption("06PM", "06:00 PM"));
		hours.add(new SelectOption("07PM", "07:00 PM"));
		hours.add(new SelectOption("08PM", "08:00 PM"));
		hours.add(new SelectOption("09PM", "09:00 PM"));
		hours.add(new SelectOption("10PM", "10:00 PM"));
		hours.add(new SelectOption("11PM", "11:00 PM"));
		
		return hours;
	}

	
	@RequestMapping(value = "/list",  method = { RequestMethod.GET })
	@RequireHardLogIn
	public String alertlist(	final Model model)throws CMSItemNotFoundException
	{
		String b2BUnitId = getRootUnit().getUid();
		
		AlertConfigurationSearchForm alertConfigurationSearchForm = new AlertConfigurationSearchForm();
		alertConfigurationSearchForm.setName(b2BUnitId);
		model.addAttribute("alertConfigurationSearchForm", alertConfigurationSearchForm);
		
		AlertsConfigurationParameters parameters = new AlertsConfigurationParameters();
		parameters.setB2BUnitId(b2BUnitId);
		
		List<AlertConfigurationModel> alertConfigurationModels = neorisAlertsFacade.getAlertsConfigurations(parameters);
		model.addAttribute("alertConfigurations", alertConfigurationModels);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(ALERTS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ALERTS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("alerts.list.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/get",  method = { RequestMethod.GET })
	@RequireHardLogIn
	public String getSettings(AlertConfigurationSearchForm alertConfigurationSearchForm,	final Model model)throws CMSItemNotFoundException
	{
		model.addAttribute("alertConfigurationSearchForm", alertConfigurationSearchForm);
		
		String b2BUnitId = alertConfigurationSearchForm.getName();
		
		AlertsConfigurationParameters parameters = new AlertsConfigurationParameters();
		parameters.setB2BUnitId(b2BUnitId);
		
		List<AlertConfigurationModel> alertConfigurationModels = neorisAlertsFacade.getAlertsConfigurations(parameters);
		model.addAttribute("alertConfigurations", alertConfigurationModels);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(ALERTS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ALERTS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("alerts.list.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/save",  method = { RequestMethod.POST })
	@RequireHardLogIn
	public String saveSettings(AlertsConfigurationForm alertsConfigurationForm,	final Model model,final RedirectAttributes redirectModel)throws CMSItemNotFoundException
	{
		model.addAttribute("alertsConfigurationForm", alertsConfigurationForm);
		EmailValidator.getInstance().isValid("");
		
		boolean formHasErrors = false;
		
		if(alertsConfigurationForm != null && alertsConfigurationForm.getAlertsConfigurations() != null)
		{
			for(AlertConfigurationData eachConfData : alertsConfigurationForm.getAlertsConfigurations())
			{
				if(eachConfData.getNotify() != null && eachConfData.getNotify() == true )
				{
					
					if(StringUtils.isNotBlank(eachConfData.getCcEmail()))
					{
						boolean isvalidEmail = EmailValidator.getInstance().isValid(eachConfData.getCcEmail());
						
						if(!isvalidEmail)
						{
							GlobalMessages.addErrorMessage(model, "alerts.save.email.error");
							formHasErrors = true;
						}
					}
					
					AlertModel alertModel = neorisAlertsFacade.getAlertForCurrentStore(eachConfData.getAlertCode());
					
					if(alertModel.getHasProductOptions()!= null && alertModel.getHasProductOptions()==true && StringUtils.isBlank(eachConfData.getProductOptions()))
					{
						GlobalMessages.addErrorMessage(model, "alerts.save.noProdOpts.error");
						formHasErrors = true;
					}
					
					if("weekly".equalsIgnoreCase(eachConfData.getPeriodicity()) && StringUtils.isBlank(eachConfData.getDaysOfWeek()))
					{
						GlobalMessages.addErrorMessage(model, "alerts.save.noWeekDays.error");
						formHasErrors = true;
					}
				}
			}
		}
		
		AlertConfigurationSearchForm alertConfigurationSearchForm = new AlertConfigurationSearchForm();
		alertConfigurationSearchForm.setName(alertsConfigurationForm.getB2bUnitId());
		model.addAttribute("alertConfigurationSearchForm", alertConfigurationSearchForm);
		
		String b2BUnitId = alertsConfigurationForm.getB2bUnitId();
		List<AlertConfigurationData> alertConfigurationDatas = alertsConfigurationForm.getAlertsConfigurations();
		
		AlertsConfigurationParameters parameters = new AlertsConfigurationParameters();
		parameters.setB2BUnitId(b2BUnitId);
		parameters.setAlertConfigurationDatas(alertConfigurationDatas);
		
			
		
		if(!formHasErrors)
		{		
			List<AlertConfigurationModel> alertConfigurationModels = neorisAlertsFacade.saveAlertsConfigurations(parameters);
			model.addAttribute("alertConfigurations", alertConfigurationModels);
			
			if(alertConfigurationModels != null)
			{
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
						"alerts.save.success", new Object[]
								{ alertsConfigurationForm.getB2bUnitId() });
				
				return REDIRECT_PREFIX + "/my-account/alerts/get?name="+alertsConfigurationForm.getB2bUnitId();
			}else
			{
				alertConfigurationModels = neorisAlertsFacade.getAlertsConfigurations(parameters);
				
				GlobalMessages.addErrorMessage(model, "alerts.save.error");
				
				// populate data from form datas to models to show the data entered by user into the form
				populateModelsFromDatas(alertConfigurationDatas, alertConfigurationModels);
				
				model.addAttribute("alertConfigurations", alertConfigurationModels);
			}
			
			
		}else
		{
			List<AlertConfigurationModel> alertConfigurationModels = neorisAlertsFacade.getAlertsConfigurations(parameters);
			
			// populate data from form datas to models to show the data entered by user into the form
			populateModelsFromDatas(alertConfigurationDatas, alertConfigurationModels);
			
			model.addAttribute("alertConfigurations", alertConfigurationModels);
		}
			
		storeCmsPageInModel(model, getContentPageForLabelOrId(ALERTS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ALERTS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("alerts.list.title"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return getViewForPage(model);
	}	
	
	protected void populateModelsFromDatas(List<AlertConfigurationData> datas, List<AlertConfigurationModel> models)
	{
		for(AlertConfigurationModel eachModel : models)
		{
			for(AlertConfigurationData eachData : datas)
			{
				if(eachModel.getAlert().getCode().equalsIgnoreCase(eachData.getAlertCode()))
				{
					eachModel.setNotify(eachData.getNotify());
					eachModel.setPeriodicity(eachData.getPeriodicity());
					eachModel.setTime(eachData.getTime());
					eachModel.setDayOfMonth( eachData.getDayOfMonth());
					eachModel.setDaysOfWeek(eachData.getDaysOfWeek());
					eachModel.setCcEmail(eachData.getCcEmail());
					eachModel.setIncludeMTR(eachData.getIncludeMTR());
					eachModel.setProductOptions(eachData.getProductOptions());
					break;
				}
			}
		}
	}
}
