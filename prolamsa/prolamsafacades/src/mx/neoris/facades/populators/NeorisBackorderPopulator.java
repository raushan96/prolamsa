/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.BackorderModel;
import mx.neoris.facades.backorder.data.BackorderData;

/**
 * @author christian.loredo
 *
 */
public class NeorisBackorderPopulator implements Populator<BackorderModel, BackorderData>
{

	@Override
	public void populate(final BackorderModel source, final BackorderData target) throws ConversionException
	{
		target.setCustomer(source.getCustomer());
		
		target.setBalanceKilos(source.getBalanceKilos());
		target.setDescription(source.getDescription());
		target.setKgsOrder(source.getKgsOrder());
		target.setLoadingKilos(source.getLoadingKilos());
		target.setPcsOrder(source.getPcsOrder());
		target.setPendingKilos(source.getPendingKilos());
		target.setReadyKilos(source.getReadyKilos());
		
		target.setOrderQty2(source.getOrderQty2());
		target.setPendingQty2(source.getPendingQty2());
		target.setReadyQty2(source.getReadyQty2());
		target.setLoadingQty2(source.getLoadingQty2());
		target.setBalance2(source.getBalance2());
		target.setUom2(source.getUom2());
		
		target.setOrderQty3(source.getOrderQty3());
		target.setPendingQty3(source.getPendingQty3());
		target.setReadyQty3(source.getReadyQty3());
		target.setLoadingQty3(source.getLoadingQty3());
		target.setBalance3(source.getBalance3());
		target.setUom3(source.getUom3());
	}
}
