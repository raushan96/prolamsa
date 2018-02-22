/**
 * 
 */
package mx.neoris.core.updaters;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.store.BaseStoreModel;


/**
 * @author e-jecarrilloi
 * 
 */
public interface NeorisTermsAndConditionsCheckedB2BCustomerUpdater
{
	void updateAllB2BCustomerWith(final CMSParagraphComponentModel cmsParagraphComponentModel);

	void updateFor(final B2BCustomerModel b2bCustomerModel, final BaseStoreModel baseStoreModel);
}
