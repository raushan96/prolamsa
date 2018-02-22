/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.TransportationModeData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;

import mx.neoris.core.model.TransportationModeModel;


/**
 * @author fdeutsch
 * 
 */
public class NeorisAddressPopulator implements Populator<AddressModel, AddressData>
{
	@Resource (name="neorisTransportationModeConverter")
	private Converter<TransportationModeModel, TransportationModeData> tmConverter;

	@Override
	public void populate(final AddressModel source, final AddressData target) throws ConversionException
	{
		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setShortName(source.getShortName());
		target.setLine1(source.getStreetname());
		
		if (null != source.getOwner())
		{
			if (source.getOwner() instanceof B2BUnitModel)
			{
				target.setOwnerUid(((B2BUnitModel)source.getOwner()).getUid());
			}
		}
		
		// NEORIS_CHANGE #91 Change getLine1() by getShortName()
		target.setFormattedAddress(source.getShortName() +" (" + source.getCode() + ")");
		target.setFormattedAddress2(source.getCode() + " - " + source.getLine1() + ", " + source.getTown());

		// NEORIS_CHANGE #74 Added to load transportation mode data
		if (source.getTransportationMode() != null)
		{
			target.setTransportationMode(tmConverter.convert(source.getTransportationMode()));
		}
	}
}
