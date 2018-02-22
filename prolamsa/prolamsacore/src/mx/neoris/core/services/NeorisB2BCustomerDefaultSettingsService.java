/**
 * 
 */
package mx.neoris.core.services;

import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;


/**
 * @author hector.castaneda
 * 
 */
public interface NeorisB2BCustomerDefaultSettingsService
{
	NeorisB2BCustomerDefaultSettingModel getSetting(B2BCustomerSettingParameters parameters);

	NeorisB2BCustomerDefaultSettingModel saveSetting(B2BCustomerSaveSettingParameters parameters);
}
