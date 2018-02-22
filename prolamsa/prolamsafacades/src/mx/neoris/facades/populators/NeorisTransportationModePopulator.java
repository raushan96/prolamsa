/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.commercefacades.user.data.TransportationModeData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.TransportationModeModel;


public class NeorisTransportationModePopulator implements Populator<TransportationModeModel, TransportationModeData>
{
	@Override
	public void populate(final TransportationModeModel source, final TransportationModeData target) throws ConversionException
	{
		target.setInternalCode(source.getInternalCode());
		target.setCode(source.getCode());
		target.setIncotermCode(source.getIncotermCode());
		target.setIncotermDescription(source.getIncotermDescription());
		target.setMaxCapacity(source.getMaxCapacity());
		target.setName(source.getName());
	}
}
