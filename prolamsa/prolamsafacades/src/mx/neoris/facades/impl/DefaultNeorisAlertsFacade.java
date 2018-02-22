/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.data.AlertConfigurationData;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.neoris.core.model.AlertConfigurationChangeModel;
import mx.neoris.core.model.AlertConfigurationModel;
import mx.neoris.core.model.AlertModel;
import mx.neoris.core.services.AlertsConfigurationParameters;
import mx.neoris.core.services.NeorisAlertConfigurationService;
import mx.neoris.core.services.NeorisB2BCustomerDefaultSettingsService;
import mx.neoris.facades.NeorisAlertsFacade;
import mx.neoris.facades.NeorisFacade;

/**
 * @author hector.castaneda
 *
 */
public class DefaultNeorisAlertsFacade implements NeorisAlertsFacade{
	
	private static final Logger LOG = Logger.getLogger(DefaultNeorisAlertsFacade.class);
	
	@Resource(name="neorisB2BCustomerDefaultSettingsService")
	NeorisB2BCustomerDefaultSettingsService neorisB2BCustomerDefaultSettingsService;
	
	@Resource(name="baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name="neorisFacade")
	NeorisFacade neorisFacade;
	
	@Resource(name="modelService")
	ModelService modelService;

	@Resource(name = "neorisSAPAlertConfigurationsService")
	private NeorisAlertConfigurationService neorisSAPAlertConfigurationsService;
	/* (non-Javadoc)
	 * @see mx.neoris.facades.AlertsFacade#getAlertsForCurrentStore()
	 */
	@Override
	public List<AlertModel> getAlertsForCurrentStore() {
		// YTODO Auto-generated method stub
		
		BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();
		
		return currentBaseStoreModel.getAlerts();
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisAlertsFacade#getAlertsConfigurations(mx.neoris.core.services.AlertsConfigurationParameters)
	 */
	@Override
	public List<AlertConfigurationModel> getAlertsConfigurations(
			AlertsConfigurationParameters parameters) {

		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		BaseStoreModel currentStoreModel = baseStoreService.getCurrentBaseStore();
		B2BUnitModel b2BUnitModel = neorisFacade.getB2BUnitWithUid(parameters.getB2BUnitId());
		
		List<AlertModel> alertModels = currentStoreModel.getAlerts();
		
		if(alertModels == null ||alertModels.size() == 0)
			return null;
		List<AlertConfigurationModel> alertConfigurationModels = new ArrayList<AlertConfigurationModel>();
		
		for(AlertModel eachAlertModel: alertModels)
		{
			AlertConfigurationModel eachAlertConfiguration = null;
			
			List<AlertConfigurationModel> customerAlertConfigurationModels = currentCustomerModel.getAlertsConfiguration();
			
			if(customerAlertConfigurationModels != null && customerAlertConfigurationModels.size() > 0)
			{
				for(AlertConfigurationModel eachCustomerAlertConfigurationModel : customerAlertConfigurationModels)
				{
					if(eachCustomerAlertConfigurationModel.getBaseStore().getUid().equalsIgnoreCase(currentStoreModel.getUid())
							&& eachCustomerAlertConfigurationModel.getB2bcustomer().getUid().equalsIgnoreCase(currentCustomerModel.getUid())
								&& eachCustomerAlertConfigurationModel.getB2bunit().getUid().equalsIgnoreCase(b2BUnitModel.getUid())
									&&eachCustomerAlertConfigurationModel.getAlert().getCode().equalsIgnoreCase(eachAlertModel.getCode()))
					{
						eachAlertConfiguration=eachCustomerAlertConfigurationModel;
						break;
					}
						
				}
			}
			
			if(eachAlertConfiguration == null)
			{
				eachAlertConfiguration = new AlertConfigurationModel();
				eachAlertConfiguration.setAlert(eachAlertModel);
				eachAlertConfiguration.setB2bcustomer(currentCustomerModel);
				eachAlertConfiguration.setB2bunit(b2BUnitModel);
				eachAlertConfiguration.setBaseStore(currentStoreModel);
				
			}
			
			alertConfigurationModels.add(eachAlertConfiguration);
			
		}
		return alertConfigurationModels;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisAlertsFacade#setAlertsConfigurations(mx.neoris.core.services.AlertsConfigurationParameters)
	 */
	@Override
	public List<AlertConfigurationModel> saveAlertsConfigurations(
			AlertsConfigurationParameters parameters) {
		// YTODO Auto-generated method stub
		
		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		parameters.setCustomerModel(currentCustomerModel);
		
		BaseStoreModel currentStoreModel = baseStoreService.getCurrentBaseStore();
		parameters.setBaseStoreModel(currentStoreModel);
		
		B2BUnitModel b2BUnitModel = neorisFacade.getB2BUnitWithUid(parameters.getB2BUnitId());
		
		List<AlertConfigurationModel> customerAlertConfigurationModels = currentCustomerModel.getAlertsConfiguration();
		
		List<AlertConfigurationModel> updatedcustomerAlertConfigurationModels = new ArrayList<AlertConfigurationModel>();
		
		if(customerAlertConfigurationModels !=null && customerAlertConfigurationModels.size() >0)
		{
			//Filer alert configurations for current store
			for(AlertConfigurationModel eachConfModel : customerAlertConfigurationModels)
			{
					updatedcustomerAlertConfigurationModels.add(eachConfModel);
			}
			
		}
		
		
		for(AlertConfigurationData eachConfData : parameters.getAlertConfigurationDatas())
		{
			AlertConfigurationModel confModel = null;
			
			for(AlertConfigurationModel eachConfModel : updatedcustomerAlertConfigurationModels)
			{
				if(eachConfModel.getBaseStore().getUid().equalsIgnoreCase(currentStoreModel.getUid()) 
						&& eachConfModel.getB2bunit().getUid().equalsIgnoreCase(b2BUnitModel.getUid())
							&&eachConfModel.getAlert().getCode().equalsIgnoreCase(eachConfData.getAlertCode()))
				{
					confModel= eachConfModel;
					saveConfigurationChanges(eachConfModel, eachConfData);
					break;
				}
			}
			
			if(confModel == null)
			{
				confModel = new AlertConfigurationModel();
			}
			
			
			AlertModel alertModel = this.getAlertForCurrentStore(eachConfData.getAlertCode());
			
			if(alertModel == null)
			{
				LOG.error("There is not alert with code: "+eachConfData.getAlertCode());
				continue;
			}
			
			confModel.setAlert(alertModel);
			confModel.setB2bcustomer(currentCustomerModel);
			confModel.setB2bunit(b2BUnitModel);
			confModel.setBaseStore(currentStoreModel);
			confModel.setDaysOfWeek(eachConfData.getDaysOfWeek());
			confModel.setDayOfMonth(eachConfData.getDayOfMonth());
			confModel.setPeriodicity(eachConfData.getPeriodicity());
			confModel.setProductOptions(eachConfData.getProductOptions());
			confModel.setTime(eachConfData.getTime());
			confModel.setNotify(eachConfData.getNotify());
			confModel.setCcEmail(eachConfData.getCcEmail());
			confModel.setIncludeMTR(eachConfData.getIncludeMTR());
			
			if(confModel.getItemModelContext().isNew())
				updatedcustomerAlertConfigurationModels.add(confModel);
			else
				modelService.save(confModel);
		}
		
		currentCustomerModel.setAlertsConfiguration(updatedcustomerAlertConfigurationModels);
		
		boolean successPublish = neorisSAPAlertConfigurationsService.publisAlertConfigurations(parameters);
		
		if(successPublish)
			modelService.save(currentCustomerModel);
		else
		{
			return null;
		}
		
		return updatedcustomerAlertConfigurationModels;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisAlertsFacade#getAlertForCurrentStore(java.lang.String)
	 */
	@Override
	public AlertModel getAlertForCurrentStore(String code) {
		
		BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();
		List<AlertModel> alertModels = currentBaseStoreModel.getAlerts();
		
		if(alertModels == null || alertModels.size()==0)
			return null;
		
		for(AlertModel eachAlertModel : alertModels)
		{
			if(eachAlertModel.getCode().equalsIgnoreCase(code))
			{
				return eachAlertModel;
			}
		}
		
		return null;
	}

	private void saveConfigurationChanges(AlertConfigurationModel confModel,AlertConfigurationData confData)
	{
		Set<AlertConfigurationChangeModel> currentChanges = confModel.getCustomerChanges();
		
		Set<AlertConfigurationChangeModel> updatedChanges = new HashSet<AlertConfigurationChangeModel>();
		
		if(currentChanges != null && currentChanges.size()>0)
		{
			updatedChanges.addAll(currentChanges);
		}
		
		AlertConfigurationChangeModel eachChange = null;
		
		eachChange = saveConfigurationChange("daysOfWeek", confData.getDaysOfWeek(), confModel.getDaysOfWeek());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange =saveConfigurationChange("dayOfMonth", confData.getDayOfMonth(), confModel.getDayOfMonth());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange =saveConfigurationChange("periodicity", confData.getPeriodicity(), confModel.getPeriodicity());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange =saveConfigurationChange("productOptions", confData.getProductOptions(), confModel.getProductOptions());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange =saveConfigurationChange("time", confData.getTime(), confModel.getTime());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange =saveConfigurationChange("notify", confData.getNotify(), confModel.getNotify());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange =saveConfigurationChange("ccEmail", confData.getCcEmail(), confModel.getCcEmail());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
		
		eachChange = saveConfigurationChange("includeMTR", confData.getIncludeMTR(), confModel.getIncludeMTR());
		
		if(eachChange !=null)
			updatedChanges.add(eachChange);
	
		confModel.setCustomerChanges(updatedChanges);
		
		modelService.save(confModel);
	}
	
	protected AlertConfigurationChangeModel saveConfigurationChange(String attributeName, String newValue, String oldValue)
	{
		if(StringUtils.isNotBlank(newValue) || StringUtils.isNotBlank(oldValue))
		{
			AlertConfigurationChangeModel change = new AlertConfigurationChangeModel();
			
			if(StringUtils.isNotBlank(newValue) && StringUtils.isNotBlank(oldValue))
			{
				if(!newValue.equalsIgnoreCase(oldValue))
				{
					change.setAttribute(attributeName);
					change.setNewValue(newValue);
					change.setPreviuosValue(oldValue);
					change.setChangedDate(new Date());
					
					return change;
				}
			}else
			{
				change.setAttribute(attributeName);
				change.setNewValue(newValue);
				change.setPreviuosValue(oldValue);
				change.setChangedDate(new Date());
				
				return change;
			}
		}
		return null;
	}
	
	protected AlertConfigurationChangeModel saveConfigurationChange(String attributeName, Boolean newValue, Boolean oldValue)
	{
		if(newValue == null)
			newValue = false;
		
		if(oldValue == null)
			oldValue = false;
		
		AlertConfigurationChangeModel change = new AlertConfigurationChangeModel();
		
		if(newValue != oldValue)
		{
			change.setAttribute(attributeName);
			change.setNewValue(String.valueOf(newValue));
			change.setPreviuosValue(String.valueOf(oldValue));
			change.setChangedDate(new Date());
			
			return change;
		}
			
		return null;
	}
	
}
