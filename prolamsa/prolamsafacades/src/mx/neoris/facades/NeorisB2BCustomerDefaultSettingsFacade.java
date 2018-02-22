/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.store.BaseStoreModel;

import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.services.B2BCustomerSaveSettingParameters;
import mx.neoris.core.services.B2BCustomerSettingParameters;

/**
 * @author hector.castaneda
 *
 */
public interface NeorisB2BCustomerDefaultSettingsFacade {
	NeorisB2BCustomerDefaultSettingModel getSetting(B2BCustomerSettingParameters parameters);
	NeorisB2BCustomerDefaultSettingModel getSetting(B2BCustomerModel b2bCustomerModel, B2BUnitModel b2bUnitModel, BaseStoreModel baseStoreModel);
	NeorisB2BCustomerDefaultSettingModel saveSetting(B2BCustomerSaveSettingParameters parameters);
}
