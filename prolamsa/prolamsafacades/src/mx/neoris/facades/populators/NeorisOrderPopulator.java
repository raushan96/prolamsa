/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.TransportationModeData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.facades.media.data.NeorisMediaData;




public class NeorisOrderPopulator implements Populator<AbstractOrderModel, AbstractOrderData>
{

	@Resource (name="addressConverter")
	private Converter<AddressModel, AddressData> addressConverter;
	
	@Resource(name = "neorisCartPriceCalculator")
	private NeorisCartPriceCalculator neorisCartPriceCalculator;
	
	@Resource(name = "neorisB2BUnitConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter;

	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	
	@Resource (name="neorisTransportationModeConverter")
	private Converter<TransportationModeModel, TransportationModeData> tmConverter;
	
	@Resource (name="neorisMediaConverter")
	private Converter<NeorisMediaModel, NeorisMediaData> neorisMediaConverter;

	@Override
	public void populate(final AbstractOrderModel source, final AbstractOrderData target) throws ConversionException
	{
		if (source.getBillingAddress() != null)
			target.setBillingAddress(addressConverter.convert(source.getBillingAddress()));
		
		if (source.getDeliveryAddress() != null)
			target.setDeliveryAddress(addressConverter.convert(source.getDeliveryAddress()));
		
		if(source.getAttachedPO() != null)
			target.setAttachmentName(source.getAttachedPO().getRealFileName());
		
		target.setHotRolledPrice(source.getHotRolledPrice());
		target.setHotRolledWeight(source.getHotRolledWeight());

		target.setColdRolledPrice(source.getColdRolledPrice());
		target.setColdRolledWeight(source.getColdRolledWeight());

		target.setAluminizedPrice(source.getAluminizedPrice());
		target.setAluminizedWeight(source.getAluminizedWeight());

		target.setGalvanizedPrice(source.getGalvanizedPrice());
		target.setGalvanizedWeight(source.getGalvanizedWeight());
		
		target.setGalvametalPrice(source.getGalvametalPrice());
		target.setGalvametalWeight(source.getGalvametalWeight());
		
		target.setTotalAssurance(source.getTotalAssurance());
		target.setTotalDeliveryCost(source.getTotalDeliveryCost());
		target.setTotalTaxas(source.getTotalTaxas());

		target.setSapTotalPrice(source.getSapTotalPrice());
		target.setSapSubtotalPrice(source.getSapSubtotalPrice());
		target.setTotalWeight(source.getTotalWeight());
		
		target.setSapCurrency(source.getSapCurrency());
		target.setSapWeightUnit(source.getSapWeightUnit());
		target.setSapLabelWeightUnit(source.getSapLabelWeightUnit());
		
		target.setTPago(source.getTPago());
		target.setRequestedDeliveryDate(source.getRequestedDeliveryDate());		
		
		target.setIsAPIOrder(source.getIsAPIOrder());
		target.setHasAPIProducts(source.getHasAPIProducts());
		target.setHasNegPrices(source.getHasNegPrices());
		
		target.setIsHSSOrder(source.getIsHSSOrder());
		target.setHasHSSProducts(source.getHasHSSProducts());
		
		neorisCartPriceHelper.setFormattedSapData(target);
		
		target.setSapOrderId(source.getSapOrderId());
		
		// NEORIS_CHANGE #74 Added to load transportation mode data
		TransportationModeData transportationModeData = new TransportationModeData();
		if (source.getTransportationMode() != null)
		{
			transportationModeData = tmConverter.convert(source.getTransportationMode());
		}
		target.setTransportationMode(transportationModeData);
		
		UnitModel unit = new UnitModel();
		if(source.getStore() != null)
		{
			target.setBaseStoreUnit(source.getStore().getUnit());
		}else
		{
			target.setBaseStoreUnit(unit);
		}
		
		target.setUnitWhenPlaced(source.getUnitWhenPlaced());
		//target.setBaseStoreUnit(source.getStore().getUnit());

		//NEORIS_CHANGE #108 Only ShortName to avoid overflow with B2BUnit Populator
		B2BUnitData b2bUnitData = new B2BUnitData();
		b2bUnitData.setUid(source.getUnit().getUid().toString());		
		b2bUnitData.setShortName(source.getUnit().getShortName());
		b2bUnitData.setName(source.getUnit().getLocName());	
		//b2bUnitConverter.convert(source.getUnit(), b2bUnitData);
		target.setUnit(b2bUnitData);
		
		target.setCreditScoreCard(source.getCreditScoreCard());
		
		target.setUnidadPrecio(source.getUPrice());
		
		target.setShippingInstructions(source.getShippingInstructions() != null ? 
				source.getShippingInstructions().replace("\r\n", "<br/>") : 
					source.getShippingInstructions());
		
		target.setSemaphoreCredit(source.getSemaphoreCredit());
		
		// Adding attachments data
		List<NeorisMediaData> neorisMedia = Converters.convertAll(source.getAttachmentsPO(), neorisMediaConverter);
		target.setAttachmentsPO(neorisMedia);
		
		
	}
}
