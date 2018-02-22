/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.data.AlertConfigurationData;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;

import mx.neoris.core.model.AlertConfigurationModel;
import mx.neoris.core.model.AlertModel;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.services.AlertsConfigurationParameters;
import mx.neoris.core.services.B2BCustomerSaveSettingParameters;
import mx.neoris.core.services.B2BCustomerSettingParameters;

/**
 * @author hector.castaneda
 *
 */
public interface NeorisAlertsFacade {
	List<AlertModel> getAlertsForCurrentStore();
	AlertModel getAlertForCurrentStore(String code);
	List<AlertConfigurationModel> getAlertsConfigurations(AlertsConfigurationParameters parameters);
	List<AlertConfigurationModel> saveAlertsConfigurations(AlertsConfigurationParameters parameters);
}
