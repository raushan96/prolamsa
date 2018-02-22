/**
 * 
 */
package mx.neoris.core.cart;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisCartPriceCalculator implements NeorisCartPriceCalculator
{

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.impl.NeorisCartPriceCalculator#calculateCart(de.hybris.platform.core.model.order.AbstractOrderModel
	 * )
	 */
	@Override
	public void calculatePrices(final AbstractOrderData orderData) throws Exception
	{
		orderData.setHotRolledWeight(3211.74d);
		orderData.setColdRolledWeight(0d);
		orderData.setGalvanizedWeight(3211.74d);
		orderData.setAluminizedWeight(3678.828d);

		orderData.setTotalWeight(orderData.getHotRolledWeight() + orderData.getColdRolledWeight() + orderData.getGalvanizedWeight()
				+ orderData.getAluminizedWeight());

		orderData.setHotRolledPrice(3598.55d);
		orderData.setColdRolledPrice(0d);
		orderData.setGalvanizedPrice(624.25d);
		orderData.setAluminizedPrice(3598.55);

		orderData.setSapTotalPrice(orderData.getHotRolledPrice() + orderData.getColdRolledPrice() + orderData.getGalvanizedPrice()
				+ orderData.getAluminizedPrice());

		int index = 0;
		for (final OrderEntryData eachEntry : orderData.getEntries())
		{
			eachEntry.setPricePerFeet(32.43D);
			eachEntry.setNetPriceWOTaxes(27.51D);

			Date readyToShipDate = new Date();

			final Calendar c = Calendar.getInstance();
			c.setTime(readyToShipDate);
			c.add(Calendar.DATE, index);
			readyToShipDate = c.getTime();

			eachEntry.setReadyToShip(readyToShipDate);

			final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
			if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
			{
				eachEntry.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(readyToShipDate));
			}
			else
			{
				eachEntry.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(readyToShipDate));
			}

			//eachEntry.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(readyToShipDate));

			final HybrisEnumValue enumValue = eachEntry.getProduct().getLocation();
			final String formattedLocation = enumerationService.getEnumerationName(enumValue, i18nService.getCurrentLocale());

			eachEntry.setFormattedLocation(formattedLocation);

			index++;
		}
	}
}
