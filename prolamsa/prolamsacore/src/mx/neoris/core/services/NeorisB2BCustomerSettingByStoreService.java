/**
 * 
 */
package mx.neoris.core.services;

import mx.neoris.core.model.NeorisB2BCustomerSettingByStoreModel;


/**
 * @author hector.castaneda
 * 
 */
public interface NeorisB2BCustomerSettingByStoreService
{
	NeorisB2BCustomerSettingByStoreModel getSettingByUid(String uid);
}
