/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;

/**
 * @author fdeutsch
 *
 */
public class NeorisB2BUnitPopulator implements Populator<B2BUnitModel, B2BUnitData>
{
	@Resource(name = "neorisOrderConverter")
	private Converter<AbstractOrderModel, AbstractOrderData> neorisOrderConverter;
	
	@Override
	public void populate(final B2BUnitModel source, final B2BUnitData target) throws ConversionException
	{
		target.setUid(source.getUid());
		target.setShortName(source.getShortName());

		target.setName(source.getLocName() + " (" + source.getUid() + ")");
		
		//TODO check if is required to convert all existing orders and carts to data
/*
		Collection<AbstractOrderData> listData = new ArrayList<AbstractOrderData>();
		for(AbstractOrderModel order: source.getOrders()){
			AbstractOrderData orderData = neorisOrderConverter.convert(order);			
			listData.add(orderData);
		}
		
		target.setOrders(listData);
*/
	}
}
