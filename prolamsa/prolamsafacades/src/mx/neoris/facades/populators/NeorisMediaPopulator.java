/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.facades.media.data.NeorisMediaData;

/**
 * @author hector.castaneda
 *
 */
public class NeorisMediaPopulator implements Populator<NeorisMediaModel, NeorisMediaData>{

	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(NeorisMediaModel source, NeorisMediaData target)
			throws ConversionException {
		target.setName(source.getRealFileName());
		target.setUrl(source.getURL());
		target.setCode(source.getCode());
	}
}
