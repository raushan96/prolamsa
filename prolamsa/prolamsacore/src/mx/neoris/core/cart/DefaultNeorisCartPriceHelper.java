/**
 * 
 */
package mx.neoris.core.cart;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.uom.NeorisUoMQuantityConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisCartPriceHelper implements NeorisCartPriceHelper
{

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	// NEORIS_CHANGE #74 Set formatted data from sap to show on pages 
	@Override
	public void setFormattedSapData(final AbstractOrderData orderData)
	{
		final String totalPriceFormat = new String("$ %1$,.2f");
		final String unitPriceFormat = new String(getUnitPricePatternFor(orderData));
		final String unitPriceWOCurrencyFormat = new String(getUnitPriceWOCurrencyPatternFor(orderData));

		// this depend on the unit of measure
		final DecimalFormat weightFormatter = new DecimalFormat(getWeightNumberPattern());

		// this depend on the system of measure
		//TODO: Check if the format for weight totals is diferrent for MX store
		final DecimalFormat totalWeightFormatter = new DecimalFormat("###,###,###,###");

		if (orderData.getColdRolledWeight() != null)
		{
			orderData.setFormattedColdRolledWeight(totalWeightFormatter.format(orderData.getColdRolledWeight()));
		}

		if (orderData.getHotRolledWeight() != null)
		{
			orderData.setFormattedHotRolledWeight(totalWeightFormatter.format(orderData.getHotRolledWeight()));
		}

		if (orderData.getGalvanizedWeight() != null)
		{
			orderData.setFormattedGalvanizedWeight(totalWeightFormatter.format(orderData.getGalvanizedWeight()));
		}

		if (orderData.getGalvametalWeight() != null)
		{
			orderData.setFormattedGalvametalWeight(totalWeightFormatter.format(orderData.getGalvametalWeight()));
		}

		if (orderData.getAluminizedWeight() != null)
		{
			orderData.setFormattedAluminizedWeight(totalWeightFormatter.format(orderData.getAluminizedWeight()));
		}

		if (orderData.getTotalWeight() != null)
		{
			orderData.setFormattedTotalWeight(totalWeightFormatter.format(orderData.getTotalWeight()));
		}


		if (orderData.getColdRolledPrice() != null)
		{
			orderData.setFormattedColdRolledPrice(String.format(totalPriceFormat, orderData.getColdRolledPrice()));
		}

		if (orderData.getHotRolledPrice() != null)
		{
			orderData.setFormattedHotRolledPrice(String.format(totalPriceFormat, orderData.getHotRolledPrice()));
		}

		if (orderData.getGalvanizedPrice() != null)
		{
			orderData.setFormattedGalvanizedPrice(String.format(totalPriceFormat, orderData.getGalvanizedPrice()));
		}

		if (orderData.getGalvametalPrice() != null)
		{
			orderData.setFormattedGalvametalPrice(String.format(totalPriceFormat, orderData.getGalvametalPrice()));
		}

		if (orderData.getAluminizedPrice() != null)
		{
			orderData.setFormattedAluminizedPrice(String.format(totalPriceFormat, orderData.getAluminizedPrice()));
		}

		if (orderData.getSapSubtotalPrice() != null)
		{
			orderData.setFormattedSapSubtotalPrice(String.format(totalPriceFormat, orderData.getSapSubtotalPrice()));
		}

		if (orderData.getSapTotalPrice() != null)
		{
			orderData.setFormattedSapTotalPrice(String.format(totalPriceFormat, orderData.getSapTotalPrice()));
		}

		if (orderData.getTotalAssurance() != null)
		{
			orderData.setFormattedTotalAssurance(String.format(totalPriceFormat, orderData.getTotalAssurance()));
		}

		if (orderData.getTotalDeliveryCost() != null)
		{
			orderData.setFormattedTotalDelvieryCost(String.format(totalPriceFormat, orderData.getTotalDeliveryCost()));
		}

		if (orderData.getTotalTaxas() != null)
		{
			orderData.setFormattedTotalTaxas(String.format(totalPriceFormat, orderData.getTotalTaxas()));
		}
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		for (final OrderEntryData eachEntry : orderData.getEntries())
		{
			eachEntry.setFormattedPricePerFeet(String.format(unitPriceFormat, eachEntry.getPricePerFeet()));
			eachEntry.setFormattedPricePerFeetWOCurrency(String.format(unitPriceWOCurrencyFormat, eachEntry.getPricePerFeet()));
			eachEntry.setFormattedNetPriceWOTaxes(String.format(totalPriceFormat, eachEntry.getNetPriceWOTaxes()));
			//Fase 9 CILS
			//eachEntry.setFormattedPricePerTon(String.format(unitPriceFormat, eachEntry.getPricePerTon()));
			if (baseStoreModel != null)
			{
				if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
				{
					eachEntry.setFormattedPricePerTon(String.format("%1$,." + 2 + "f", eachEntry.getPricePerTon()));
				}
				else
				{
					eachEntry.setFormattedPricePerTon(String.format(unitPriceFormat, eachEntry.getPricePerTon()));
				}
			}
			
			eachEntry.setFormattedPricePerPc(String.format(totalPriceFormat, eachEntry.getPricePerPc()));

			if (orderData.getHasAPIProducts())
			{
				setAPIEntryFormattedWeight(eachEntry);
			}
			else
			{
				setEntryFormattedWeight(eachEntry);
			}

			if (eachEntry.getReadyToShip() != null)
			{

				if (baseStoreModel != null)
				{

					if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
					{
						eachEntry.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(eachEntry.getReadyToShip()));
					}
					else
					{
						eachEntry.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(eachEntry.getReadyToShip()));
					}
				}
				else
				{
					eachEntry.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(eachEntry.getReadyToShip()));
				}

				//eachEntry.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(eachEntry.getReadyToShip()));
			}

			final HybrisEnumValue enumValue = eachEntry.getProduct().getLocation();
			final String formattedLocation = enumerationService.getEnumerationName(enumValue, i18nService.getCurrentLocale());

			eachEntry.setFormattedLocation(formattedLocation);


		}
	}

	@Override
	public void setFormattedEntriesQuantity(final AbstractOrderData orderData)
	{
		final UnitModel currentUnitModel = (UnitModel) (sessionService.getAttribute("productUnit"));

		if (currentUnitModel == null)
		{
			return;
		}

		for (final OrderEntryData entryData : orderData.getEntries())
		{
			Double convertedQuantity = 0.0d;
			convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnitModel, entryData.getQuantity(),
					entryData.getProduct());
			entryData.setConvertedQuantity(convertedQuantity);

			final DecimalFormat quantityFormatter = new DecimalFormat(getQuantityPattern(currentUnitModel));
			entryData.setFormattedConvertedQuantity(quantityFormatter.format(entryData.getConvertedQuantity()));
		}
	}

	private void setEntryFormattedWeight(final OrderEntryData entryData)
	{
		final StringBuilder pattern = new StringBuilder("###,###,###,###,##0");
		final UnitModel currentUnitModel = (UnitModel) (sessionService.getAttribute("productUnit"));
		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		int weightDecimalsNumber = 0;
		double entryWeight = 0d;

		if (currentUnitModel != null && currentBaseStoreModel != null)
		{
			if (currentBaseStoreModel.getUid().equals("2000"))
			{
				if (currentUnitModel.getCode().contains("ft") || currentUnitModel.getCode().contains("lb"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getLbEquiv();
				}

				if (currentUnitModel.getCode().contains("mt") || currentUnitModel.getCode().contains("kg"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getKgEquiv();
				}


				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 3;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getTnEquiv();
				}


				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}
			else if (currentBaseStoreModel.getUid().equals("1000"))
			{
				if (currentUnitModel.getCode().contains("ft") || currentUnitModel.getCode().contains("lb"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getLbEquiv();
				}

				if (currentUnitModel.getCode().contains("mt") || currentUnitModel.getCode().contains("kg"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getKgEquiv();
				}


				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getTnEquiv();
				}


				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}
			else if (currentBaseStoreModel.getUid().equals("5000"))
			{
				if (currentUnitModel.getCode().contains("ft") || currentUnitModel.getCode().contains("lb"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getLbEquiv();
				}

				if (currentUnitModel.getCode().contains("mt") || currentUnitModel.getCode().contains("kg"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getKgEquiv();
				}


				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getTnEquiv();
				}


				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}
			else if (currentBaseStoreModel.getUid().equals("6000"))
			{
				if (currentUnitModel.getCode().contains("ft") || currentUnitModel.getCode().contains("lb"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getLbEquiv();
				}

				if (currentUnitModel.getCode().contains("mt") || currentUnitModel.getCode().contains("kg"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getKgEquiv();
				}


				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getTnEquiv();
				}


				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}


			final DecimalFormat weightFormatter = new DecimalFormat(pattern.toString());

			entryData.setFormattedWeight(weightFormatter.format(entryWeight));

		}

	}


	private void setAPIEntryFormattedWeight(final OrderEntryData entryData)
	{
		final StringBuilder pattern = new StringBuilder("###,###,###,###,##0");
		final UnitModel currentUnitModel = (UnitModel) (sessionService.getAttribute("productUnit"));
		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		int weightDecimalsNumber = 0;
		double entryWeight = 0d;

		if (currentUnitModel != null && currentBaseStoreModel != null)
		{
			if (currentBaseStoreModel.getUid().equals("6000"))
			{
				if (currentUnitModel.getDefaultMeasurementSystem().getCode().equalsIgnoreCase("English"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getFtLbEquiv();
				}

				if (currentUnitModel.getDefaultMeasurementSystem().getCode().equalsIgnoreCase("Metric"))
				{
					weightDecimalsNumber = 0;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getFtKgEquiv();
				}


				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;
					entryWeight = entryData.getQuantity() * entryData.getProduct().getFtTnEquiv();
				}


				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}


			final DecimalFormat weightFormatter = new DecimalFormat(pattern.toString());

			entryData.setFormattedWeight(weightFormatter.format(entryWeight));

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.cart.NeorisCartPriceHelper#getPriceNumberPattern()
	 */
	@Override
	public String getPriceNumberPattern()
	{
		final StringBuilder defaultPattern = new StringBuilder("$ %1$,.2f");

		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		if (currentBaseStoreModel != null)
		{
			if (currentBaseStoreModel.getUid().equalsIgnoreCase("2000"))
			{
				return "$ %1$,." + 2 + "f";
			}
			else if (currentBaseStoreModel.getUid().equalsIgnoreCase("1000"))
			{
				return "$ %1$,." + 2 + "f";
			}
			else if (currentBaseStoreModel.getUid().equalsIgnoreCase("5000"))
			{
				return "$ %1$,." + 2 + "f";
			}
		}
		return defaultPattern.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.cart.NeorisCartPriceHelper#getPriceNumberPattern()
	 */
	@Override
	public String getUnitPriceNumberPattern()
	{
		final StringBuilder defaultPattern = new StringBuilder("$ %1$,.4f");

		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		if (currentBaseStoreModel != null)
		{
			if (currentBaseStoreModel.getUid().equalsIgnoreCase("2000"))
			{
				return "$ %1$,." + 4 + "f";
			}
			else if (currentBaseStoreModel.getUid().equalsIgnoreCase("1000"))
			{
				return "$ %1$,." + 2 + "f";
			}
			else if (currentBaseStoreModel.getUid().equalsIgnoreCase("5000"))
			{
				return "$ %1$,." + 2 + "f";
			}
		}
		return defaultPattern.toString();
	}

	@Override
	public String getUnitPricePatternFor(final AbstractOrderData orderData)
	{
		final StringBuilder defaultPattern = new StringBuilder("$ %1$,.4f");

		BaseStoreModel orderBaseStore;

		if (StringUtils.isNotBlank(orderData.getStore()))
		{
			orderBaseStore = baseStoreService.getBaseStoreForUid(orderData.getStore());
		}
		else
		{
			orderBaseStore = baseStoreService.getCurrentBaseStore();
		}

		if (baseStoreService == null)
		{
			return defaultPattern.toString();
		}

		if (orderBaseStore != null)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase("2000"))
			{
				return "$ %1$,." + 4 + "f";
			}
			else if (orderBaseStore.getUid().equalsIgnoreCase("1000"))
			{
				return "$ %1$,." + 2 + "f";
			}
			else if (orderBaseStore.getUid().equalsIgnoreCase("5000"))
			{
				return "$ %1$,." + 2 + "f";
			}
		}
		return defaultPattern.toString();
	}

	@Override
	public String getUnitPriceWOCurrencyPatternFor(final AbstractOrderData orderData)
	{
		final StringBuilder defaultPattern = new StringBuilder("%1$,.4f");

		BaseStoreModel orderBaseStore;

		if (StringUtils.isNotBlank(orderData.getStore()))
		{
			orderBaseStore = baseStoreService.getBaseStoreForUid(orderData.getStore());
		}
		else
		{
			orderBaseStore = baseStoreService.getCurrentBaseStore();
		}

		if (baseStoreService == null)
		{
			return defaultPattern.toString();
		}

		if (orderBaseStore != null)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase("2000"))
			{
				return "%1$,." + 4 + "f";
			}
			else if (orderBaseStore.getUid().equalsIgnoreCase("1000"))
			{
				return "%1$,." + 2 + "f";
			}
			else if (orderBaseStore.getUid().equalsIgnoreCase("5000"))
			{
				return "%1$,." + 2 + "f";
			}
		}
		return defaultPattern.toString();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.cart.NeorisCartPriceHelper#getWeightNumberPattern()
	 */
	@Override
	public String getWeightNumberPattern()
	{
		final StringBuilder pattern = new StringBuilder("###,###,###,###,##0");
		final UnitModel currentUnitModel = (UnitModel) (sessionService.getAttribute("productUnit"));
		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		int weightDecimalsNumber = 0;//Default

		if (currentUnitModel != null && currentBaseStoreModel != null)
		{
			if (currentBaseStoreModel.getUid().equals("2000"))
			{
				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 3;

				}
				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}
			else if (currentBaseStoreModel.getUid().equals("1000"))
			{
				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;

				}
				
				if (currentUnitModel.getCode().contains("kg"))
				{
					weightDecimalsNumber = 2;
				}

				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}
			}
			else if (currentBaseStoreModel.getUid().equals("5000"))
			{
				if (currentUnitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;

				}
				if (currentUnitModel.getCode().contains("kg"))
				{
					weightDecimalsNumber = 2;

				}
				if (weightDecimalsNumber > 0)
				{
					pattern.append(".");
				}

				for (int i = 0; i < weightDecimalsNumber; i++)
				{
					pattern.append("0");
				}

			}



		}


		// YTODO Auto-generated method stub
		return pattern.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.cart.NeorisCartPriceHelper#getWeightNumberPattern()
	 */
	@Override
	public String getQuantityPattern(final UnitModel unitModel)
	{
		final StringBuilder pattern = new StringBuilder("###,###,###,###,##0");
		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		int weightDecimalsNumber = 0;//Default

		if (unitModel != null && currentBaseStoreModel != null)
		{
			if (currentBaseStoreModel.getUid().equals("2000"))
			{

				if (unitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 3;
				}
				else if (unitModel.getCode().contains("ft"))
				{
					weightDecimalsNumber = 2;
				}
				else if (unitModel.getCode().contains("mt"))
				{
					weightDecimalsNumber = 2;
				}
			}
			else if (currentBaseStoreModel.getUid().equals("1000"))
			{
				if (unitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;
				}
				else if (unitModel.getCode().contains("ft"))
				{
					weightDecimalsNumber = 0;
				}
				else if (unitModel.getCode().contains("prolamsa_kg"))
				{
					weightDecimalsNumber = 2;
				}
				else if (unitModel.getCode().contains("mt"))
				{
					weightDecimalsNumber = 2;
				}
			}
			else if (currentBaseStoreModel.getUid().equals("5000"))
			{

				if (unitModel.getCode().contains("ton"))
				{
					weightDecimalsNumber = 2;
				}
				else if (unitModel.getCode().contains("prolamsa_kg"))
				{
					weightDecimalsNumber = 2;
				}
				else if (unitModel.getCode().contains("mt"))
				{
					weightDecimalsNumber = 2;
				}
			}


			if (weightDecimalsNumber > 0)
			{
				pattern.append(".");
			}

			for (int i = 0; i < weightDecimalsNumber; i++)
			{
				pattern.append("0");
			}

		}


		// YTODO Auto-generated method stub
		return pattern.toString();
	}


	public void sortEntriesBySapOrderEntryNumber(final AbstractOrderData orderData)
	{
		Collections.sort(orderData.getEntries(), new OrderEntryDataComparatorBySapOrdeEntryNumber());
	}

	public class OrderEntryDataComparatorBySapOrdeEntryNumber extends AbstractComparator<OrderEntryData>
	{
		@Override
		protected int compareInstances(final OrderEntryData a, final OrderEntryData b)
		{
			return compareValues(a.getSapEntryNumber(), b.getSapEntryNumber());
		}
	}

	public void sortEntriesByReadyToShipDate(final AbstractOrderData orderData)
	{
		Collections.sort(orderData.getEntries(), new OrderEntryDataComparatorByReadyToShipDate());
	}

	// NEORIS_CHANGE #67 Added inner class to sort cart entries
	public class OrderEntryDataComparatorByReadyToShipDate extends AbstractComparator<OrderEntryData>
	{
		@Override
		protected int compareInstances(final OrderEntryData a, final OrderEntryData b)
		{
			return compareValues(a.getReadyToShip(), b.getReadyToShip());
		}
	}

	@Override
	public void setDefaultGroupingOfOrderData(final AbstractOrderData orderData)
	{
		int index = 0;
		for (final OrderEntryData orderEntry : orderData.getEntries())
		{
			orderEntry.setGroupNumber(1);
			orderEntry.setIsLastOnTraspotation(false);
			orderEntry.setIsTransportationGroupFull(false);
			orderEntry.setIsTransportationGroupFull(false);
			orderEntry.setSapEntryNumber((index + 1) * 10);
			index++;
		}
	}

	public void setGroupingOfOrderData(final AbstractOrderData orderData)
	{
		// order entry index for SAP
		int index = 0;
		//long transportationCapacityKilos = 0L;
		//System.out.println("Antes de transportationCapacityKilos");
		final Double transportationCapacityKilos = orderData.getTransportationMode().getMaxCapacity() * 1000;
		//System.out.println("Despues de transportationCapacityKilos");
		// transportation capacity

		/*
		 * if (orderData.getTransportationMode().getCapacity() != null) { transportationCapacityKilos =
		 * orderData.getTransportationMode().getCapacity() * 1000; } else { if
		 * (orderData.getTransportationMode().getCode().equalsIgnoreCase("01")) { transportationCapacityKilos = 80 * 1000;
		 * } else { transportationCapacityKilos = 20 * 1000; } }
		 */

		// used capacity
		Double usedCapacity = 0d;

		// transportation group
		int groupTransportation = 1;

		// entries on current group
		List<OrderEntryData> entriesOnCurrentGroup = new ArrayList<OrderEntryData>();

		// for each order entry
		for (final OrderEntryData orderEntry : orderData.getEntries())
		{
			// set group index
			orderEntry.setGroupNumber(groupTransportation);

			Double entryCapacity = 0.00d;

			// compute the order entry ton capacity, remember quantities are on bundles
			if (!orderEntry.getProduct().getIsAPI())
			{
				entryCapacity = (orderEntry.getProduct().getBundleKgEquiv()) * orderEntry.getQuantity();
			}
			else
			{
				entryCapacity = (orderEntry.getProduct().getFtKgEquiv()) * orderEntry.getQuantity();
			}

			final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

			// if the used capacity and entry is great that transportation capacity
			if (usedCapacity + entryCapacity >= transportationCapacityKilos)
			{
				// mark entry as the last on transportation group
				orderEntry.setIsLastOnTraspotation(true);

				// mark transportation group as full
				orderEntry.setIsTransportationGroupFull(true);

				// for each entry on the current group
				for (final OrderEntryData eachEntryOnCurrentGroup : entriesOnCurrentGroup)
				{
					// set the shipping date to the last date of the group
					eachEntryOnCurrentGroup.setReadyToShip(orderEntry.getReadyToShip());
					if (baseStoreModel != null)
					{
						if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
						{
							eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(orderEntry
									.getReadyToShip()));
						}
						else
						{
							eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(orderEntry
									.getReadyToShip()));
						}
					}
					else
					{
						eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(orderEntry
								.getReadyToShip()));
					}

					//eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(orderEntry.getReadyToShip()));

					// mark each entry of the group as transportation group full
					eachEntryOnCurrentGroup.setIsTransportationGroupFull(true);
				}

				// reset the entries in current group
				entriesOnCurrentGroup = new ArrayList<OrderEntryData>();
				// increment the transportation group index
				groupTransportation++;
				// rest used capacity
				usedCapacity = 0d;
				// set the SAP entry for the order entry
				orderEntry.setSapEntryNumber(index);
				// increment the sap entry
				index++;
				// go back to the loop
				continue;
			}
			else
			{
				// include the order entry on the current group
				entriesOnCurrentGroup.add(orderEntry);
				// mark for now as not full
				orderEntry.setIsTransportationGroupFull(false);
				// mark for now as not last on transportation group
				orderEntry.setIsLastOnTraspotation(false);

				// if this is the last entry of the order
				if (index + 1 == orderData.getEntries().size())
				{
					// mark it as the last on transportation group
					orderEntry.setIsLastOnTraspotation(true);

					// for each entry on the current group
					for (final OrderEntryData eachEntryOnCurrentGroup : entriesOnCurrentGroup)
					{
						// set the shipping date to the last date of the group
						eachEntryOnCurrentGroup.setReadyToShip(orderEntry.getReadyToShip());
						if (orderEntry.getReadyToShip() != null)
						{
							if (baseStoreModel != null)
							{
								if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
								{
									eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(orderEntry
											.getReadyToShip()));
								}
								else
								{
									eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(orderEntry
											.getReadyToShip()));
								}
								//eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(orderEntry.getReadyToShip()));
							}
							else
							{
								eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(orderEntry
										.getReadyToShip()));
							}
						}

					}

					// reset the entries in current group
					entriesOnCurrentGroup = new ArrayList<OrderEntryData>();
				}
			}

			// set the SAP entry for the order entry
			orderEntry.setSapEntryNumber(index);
			// increment the sap entry
			index++;

			// increment the used capacity
			usedCapacity += entryCapacity;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.cart.NeorisCartPriceHelper#getOrderWeightPatterFor(de.hybris.platform.commercefacades.order.data
	 * .AbstractOrderData)
	 */
	@Override
	public String getOrderWeightPatterFor(final AbstractOrderData orderData)
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(orderData.getStore());

		if (baseStoreModel == null || orderData.getUnitWhenPlaced() == null)
		{
			return "###,###,###,##0.000";
		}

		if (baseStoreModel.getUid().equalsIgnoreCase("1000"))
		{
			final UnitModel unitModel = orderData.getUnitWhenPlaced();

			if (unitModel.getCode().contains("ton"))
			{
				return "###,###,###,##0.00";
			}
			else
			{
				return "###,###,###,##0";
			}
		}
		else if (baseStoreModel.getUid().equalsIgnoreCase("2000"))
		{
			final UnitModel unitModel = orderData.getUnitWhenPlaced();

			if (unitModel.getCode().contains("ton"))
			{
				return "###,###,###,##0.000";
			}
			else
			{
				return "###,###,###,##0";
			}



		}
		return "###,###,###,##0.000";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.cart.NeorisCartPriceHelper#getWeightKgNumberPattern()
	 */
	@Override
	public String getWeightKgNumberPattern()
	{
		final StringBuilder pattern = new StringBuilder("###,###,###,###,##0");
		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		//Default
		int weightDecimalsNumber = 0;

		if (currentBaseStoreModel.getUid().equals("2000"))
		{
			// nothing to do

		}
		else if (currentBaseStoreModel.getUid().equals("1000"))
		{
			// nothing to do
			weightDecimalsNumber = 2;

			if (weightDecimalsNumber > 0)
			{
				pattern.append(".");
			}

			for (int i = 0; i < weightDecimalsNumber; i++)
			{
				pattern.append("0");
			}
		}
		else if (currentBaseStoreModel.getUid().equals("5000"))
		{
			weightDecimalsNumber = 2;

			if (weightDecimalsNumber > 0)
			{
				pattern.append(".");
			}

			for (int i = 0; i < weightDecimalsNumber; i++)
			{
				pattern.append("0");
			}
		}

		return pattern.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.cart.NeorisCartPriceHelper#getTonPattern()
	 */
	@Override
	public String getTonPattern()
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		// YTODO Auto-generated method stub
		if (baseStoreModel == null)
		{
			return "###,###,###,##0.000";
		}

		if (baseStoreModel.getUid().equalsIgnoreCase("1000"))
		{
			return "###,###,###,##0.00";

		}
		else if (baseStoreModel.getUid().equalsIgnoreCase("2000"))
		{
			return "###,###,###,##0.000";
		}
		return "###,###,###,##0.000";

	}
}
