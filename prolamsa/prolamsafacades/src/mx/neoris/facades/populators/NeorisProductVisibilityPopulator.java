/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.commercefacades.user.data.ProductVisibilityData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.ProductVisibilityModel;


public class NeorisProductVisibilityPopulator implements Populator<ProductVisibilityModel, ProductVisibilityData>
{
	@Override
	public void populate(final ProductVisibilityModel source, final ProductVisibilityData target) throws ConversionException
	{
		//target.setCode(source.getCode());
		//target.setCategory(source.getCategory()); 
		//target.setLocation(source.getLocation());		
		//target.setMaterialType(source.getMaterialType());
	}
}
