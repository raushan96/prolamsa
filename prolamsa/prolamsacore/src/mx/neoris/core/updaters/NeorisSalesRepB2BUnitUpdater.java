/**
 * 
 */
package mx.neoris.core.updaters;

import de.hybris.platform.b2b.model.B2BUnitModel;

import java.util.Set;

import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;


/**
 * @author e-hicastaneda
 * 
 */
public interface NeorisSalesRepB2BUnitUpdater
{
	void updateWith(final B2BUnitModel b2bUnit, final String originalSalesRep) throws Exception;

	void updateWith(final B2BUnitModel b2bUnit, final Set<NeorisB2BUnitSettingByStoreModel> originalSettings) throws Exception;

	void updateWith(B2BUnitModel b2bUnitOwner, String storeId, String originalSetting, String newSetting) throws Exception;

	Boolean validateSettingIsSalesRep(String storeId, String newSetting) throws Exception;
}
