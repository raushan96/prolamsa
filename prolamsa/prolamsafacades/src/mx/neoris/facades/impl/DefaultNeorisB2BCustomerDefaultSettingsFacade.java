/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.store.BaseStoreModel;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.services.B2BCustomerSaveSettingParameters;
import mx.neoris.core.services.B2BCustomerSettingParameters;
import mx.neoris.core.services.NeorisB2BCustomerDefaultSettingsService;
import mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade;

/**
 * @author hector.castaneda
 *
 */
public class DefaultNeorisB2BCustomerDefaultSettingsFacade implements NeorisB2BCustomerDefaultSettingsFacade{
	
	private static final Logger LOG = Logger.getLogger(DefaultNeorisB2BCustomerDefaultSettingsFacade.class);
	
	@Resource(name="neorisB2BCustomerDefaultSettingsService")
	NeorisB2BCustomerDefaultSettingsService neorisB2BCustomerDefaultSettingsService;

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade#getSetting(mx.neoris.core.services.B2BCustomerSettingParameters)
	 */
	@Override
	public NeorisB2BCustomerDefaultSettingModel getSetting(B2BCustomerSettingParameters parameters) {
		return neorisB2BCustomerDefaultSettingsService.getSetting(parameters);
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade#getSetting(de.hybris.platform.b2b.model.B2BCustomerModel, de.hybris.platform.b2b.model.B2BUnitModel, de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public NeorisB2BCustomerDefaultSettingModel getSetting(
			B2BCustomerModel b2bCustomerModel, B2BUnitModel b2bUnitModel,
			BaseStoreModel baseStoreModel) {
		B2BCustomerSettingParameters parameters = new B2BCustomerSettingParameters();
		parameters.setB2bCustomerModel(b2bCustomerModel);
		parameters.setB2bUnitModel(b2bUnitModel);
		parameters.setBaseStoreModel(baseStoreModel);
		
		return neorisB2BCustomerDefaultSettingsService.getSetting(parameters);
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade#saveSetting(mx.neoris.core.services.B2BCustomerSettingParameters)
	 */
	@Override
	public NeorisB2BCustomerDefaultSettingModel saveSetting(
			B2BCustomerSaveSettingParameters parameters) {
		return neorisB2BCustomerDefaultSettingsService.saveSetting(parameters);
	}
}
