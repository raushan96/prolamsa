/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCheckoutService;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import javax.annotation.Resource;

import mx.neoris.core.services.NeorisCommerceCheckoutService;



public class DefaultNeorisCommerceCheckoutService extends DefaultCommerceCheckoutService implements NeorisCommerceCheckoutService
{

	@Resource(name = "modelService")
	private ModelService modelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisCommerceCheckoutService#setBillingAddress(de.hybris.platform.core.model.order.CartModel
	 * , de.hybris.platform.core.model.user.AddressModel)
	 */
	@Override
	public boolean setBillingAddress(final CartModel cartModel, final AddressModel addressModel)
	{
		ServicesUtil.validateParameterNotNull(cartModel, "Cart model cannot be null");
		ServicesUtil.validateParameterNotNull(addressModel, "Billing addres model cannot be null");

		cartModel.setBillingAddress(addressModel);
		getCommerceCartCalculationStrategy().calculateCart(cartModel);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisCommerceCheckoutService#setSapPricesAndWeight(de.hybris.platform.core.model.order
	 * .OrderModel, de.hybris.platform.commercefacades.order.data.CartData)
	 */
	@Override
	public boolean saveSAPPrices(final AbstractOrderModel orderModel, final AbstractOrderData cartData)
	{
		orderModel.setHotRolledPrice(cartData.getHotRolledPrice());
		orderModel.setHotRolledWeight(cartData.getHotRolledWeight());

		orderModel.setColdRolledPrice(cartData.getColdRolledPrice());
		orderModel.setColdRolledWeight(cartData.getColdRolledWeight());

		orderModel.setAluminizedPrice(cartData.getAluminizedPrice());
		orderModel.setAluminizedWeight(cartData.getAluminizedWeight());

		orderModel.setGalvanizedPrice(cartData.getGalvanizedPrice());
		orderModel.setGalvanizedWeight(cartData.getGalvanizedWeight());

		orderModel.setGalvametalPrice(cartData.getGalvametalPrice());
		orderModel.setGalvametalWeight(cartData.getGalvametalWeight());

		orderModel.setTotalAssurance(cartData.getTotalAssurance());
		orderModel.setTotalDeliveryCost(cartData.getTotalDeliveryCost());
		orderModel.setTotalTaxas(cartData.getTotalTaxas());

		orderModel.setTotalPrice(cartData.getSapTotalPrice());
		orderModel.setSapTotalPrice(cartData.getSapTotalPrice());
		orderModel.setSapSubtotalPrice(cartData.getSapSubtotalPrice());
		orderModel.setTotalWeight(cartData.getTotalWeight());

		orderModel.setSapLabelWeightUnit(cartData.getSapLabelWeightUnit());
		orderModel.setSapWeightUnit(cartData.getSapWeightUnit());
		orderModel.setSapCurrency(cartData.getSapCurrency());

		orderModel.setSemaphoreCredit(cartData.getSemaphoreCredit());

		return true;
	}

	@Override
	public boolean saveSAPWeight(final AbstractOrderModel orderModel, final AbstractOrderData cartData)
	{
		//				int entryNumber = 0;
		for (final OrderEntryData eachEntryData : cartData.getEntries())
		{
			final int entryNumber = eachEntryData.getEntryNumber();

			final AbstractOrderEntryModel orderEntryModel = orderModel.getEntries().get(entryNumber);

			orderEntryModel.setPricePerFeet(eachEntryData.getPricePerFeet());
			orderEntryModel.setPricePerTon(eachEntryData.getPricePerTon());
			orderEntryModel.setPricePerPc(eachEntryData.getPricePerPc());
			orderEntryModel.setNetPriceWOTaxes(eachEntryData.getNetPriceWOTaxes());
			orderEntryModel.setReadyToShip(eachEntryData.getReadyToShip());

			orderEntryModel.setIsLastOnTraspotation(eachEntryData.getIsLastOnTraspotation());
			orderEntryModel.setIsTransportationGroupFull(eachEntryData.getIsTransportationGroupFull());
			orderEntryModel.setGroupNumber(eachEntryData.getGroupNumber());

			//Commented to keep the order of the items
			//orderEntryModel.setSapEntryNumber(eachEntryData.getSapEntryNumber());
			orderEntryModel.setSapEntryNumber((entryNumber + 1) * 10);

			modelService.save(orderEntryModel);
			//			entryNumber++;
		}

		return true;
	}
}
