/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.BackorderDetailModel;
import mx.neoris.facades.backorder.data.BackorderDetailData;

/**
 * @author christian.loredo
 *
 */
public class NeorisBackorderDetailPopulator implements Populator<BackorderDetailModel, BackorderDetailData>

{
	@Override
	public void populate(final BackorderDetailModel source, final BackorderDetailData target) throws ConversionException
	{
		target.setCustomer(source.getCustomer());
		target.setAddress(source.getAddress());
		target.setCustomerPO(source.getCustomerPO());
		target.setDeliveryDate(source.getDeliveryDate());
		target.setOrder(source.getOrder());
		target.setOrderDate(source.getOrderDate());
		target.setPartNumber(source.getPartNumber());
		target.setPieces(source.getPieces());
		target.setBalanceKilos(source.getBalanceKilos());
		target.setDescription(source.getDescription());
		target.setLoadingKilos(source.getLoadingKilos());
		target.setPendingKilos(source.getPendingKilos());
		target.setReadyKilos(source.getReadyKilos());
		target.setEstatusEsp(source.getEstatusEsp());
		target.setEstatusEng(source.getEstatusEng());
		target.setPartida(source.getPartida());
		target.setPlant(source.getPlant());
		
		target.setOrderQty(source.getOrderQty());
		target.setUomOrderQty(source.getUomOrderQty());
		target.setBalance(source.getBalance());
		target.setUomBalance(source.getUomBalance());
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
		target.setLogistic(source.getLogistic());
		target.setLocation(source.getLocation());
	}
}
