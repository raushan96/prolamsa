/**
 * 
 */
package mx.neoris.converters;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.user.AddressModel;


/**
 * @author fdeutsch
 * 
 */
public class NeorisB2BUnitBillingAddressConverter extends AbstractConverter<AddressModel, AddressData>
{
	@Override
	protected AddressData createTarget()
	{
		return new AddressData();
	}

	@Override
	public void populate(final AddressModel source, final AddressData target)
	{
		final B2BUnitModel b2bUnit = (B2BUnitModel) source.getOwner();

		target.setId(b2bUnit.getUid());
		target.setFormattedAddress(b2bUnit.getName());
	}
}
