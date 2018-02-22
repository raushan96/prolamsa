/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.TransportationModeData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.media.data.NeorisMediaData;

public class NeorisCartPopulator implements Populator<AbstractOrderModel, AbstractOrderData>
{

	@Resource (name="addressConverter")
	private Converter<AddressModel, AddressData> addressConverter;

	@Resource (name="neorisTransportationModeConverter")
	private Converter<TransportationModeModel, TransportationModeData> tmConverter;
	
	@Resource (name="neorisMediaConverter")
	private Converter<NeorisMediaModel, NeorisMediaData> neorisMediaConverter;
	
	@Resource(name = "neorisFacade")
	protected NeorisFacade neorisFacade;

	@Override
	public void populate(final AbstractOrderModel source, final AbstractOrderData target) throws ConversionException
	{
		if (source.getBillingAddress() != null)
			target.setBillingAddress(addressConverter.convert(source.getBillingAddress()));
		
		// NEORIS_CHANGE #74 Added to load transportation mode data
		TransportationModeData transportationModeData = new TransportationModeData();
		if (source.getTransportationMode() != null)
		{
			transportationModeData = tmConverter.convert(source.getTransportationMode());
		}
		target.setTransportationMode(transportationModeData);	
		target.setRequestedDeliveryDate(source.getRequestedDeliveryDate());
		
		target.setSemaphoreCredit(source.getSemaphoreCredit());
		target.setCreditScoreCard(source.getCreditScoreCard());
		
		// Adding attachments data
		List<NeorisMediaData> neorisMedia = Converters.convertAll(source.getAttachmentsPO(), neorisMediaConverter);
		target.setAttachmentsPO(neorisMedia);
		
		target.setIsAPIOrder(source.getIsAPIOrder());
		target.setHasAPIProducts(source.getHasAPIProducts());
		
		target.setIsHSSOrder(source.getIsHSSOrder());
		target.setHasHSSProducts(source.getHasHSSProducts());
		
		if(target instanceof CartData && source instanceof CartModel)
		{
			((CartData)target).setIsFullyHSS(((CartModel)source).isIsFullyHSS());
		}
		
		//Determine if the cart belongs to a internal customer
		UserModel userModel = source.getUser();
		
		if(userModel != null)
			target.setIsInternalCartOrder(neorisFacade.isInternalCustomer());
	}
}
