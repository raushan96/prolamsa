/**
 * 
 */
package mx.neoris.core.orders;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import mx.neoris.core.constants.NeorisServiceConstants;
import mx.neoris.core.model.ProlamsaProductModel;

import org.apache.log4j.Logger;


/**
 * @author Tulum
 * 
 */
public class DefaultNeorisHSSOrderSplitter implements NeorisHSSOrderSplitter
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisHSSOrderSplitter.class);

	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public OrderModel split(final OrderModel orderModel)
	{
		if (orderModel.getIsHSSOrder() != null && orderModel.getIsHSSOrder() == true)
		{
			boolean hasMixedProducts = false;

			for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
			{
				final ProlamsaProductModel entryProduct = (ProlamsaProductModel) eachEntryModel.getProduct();

				if (!entryProduct.getIsHSS())
				{
					hasMixedProducts = true;
					break;
				}
			}

			if (!hasMixedProducts)
			{
				return null;
			}


			final OrderModel hssOrderModel = modelService.clone(orderModel, OrderModel.class);
			hssOrderModel.setCode(orderModel.getCode() + " - hss");
			hssOrderModel.setIsHSSOrder(Boolean.TRUE);
			modelService.save(hssOrderModel);

			orderModel.setHssOrder(hssOrderModel);
			orderModel.setIsHSSOrder(Boolean.FALSE);
			modelService.save(orderModel);

			int hssEntriesIndex = 0;
			for (final AbstractOrderEntryModel eachEntryModel : hssOrderModel.getEntries())
			{
				final ProlamsaProductModel entryProduct = (ProlamsaProductModel) eachEntryModel.getProduct();

				if (!entryProduct.getIsHSS())
				{
					modelService.remove(eachEntryModel);
				}
				else
				{
					eachEntryModel.setEntryNumber(hssEntriesIndex);
					eachEntryModel.setSapEntryNumber((hssEntriesIndex + 1) + 10);
					modelService.save(eachEntryModel);
					hssEntriesIndex++;
				}

			}
			modelService.save(hssOrderModel);
			recalculateTotals(hssOrderModel);
			modelService.save(hssOrderModel);

			int regularEntriesIndex = 0;
			for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
			{
				final ProlamsaProductModel entryProduct = (ProlamsaProductModel) eachEntryModel.getProduct();

				if (entryProduct.getIsHSS())
				{
					modelService.remove(eachEntryModel);
				}
				else
				{
					eachEntryModel.setEntryNumber(regularEntriesIndex);
					eachEntryModel.setSapEntryNumber((regularEntriesIndex + 1) + 10);
					modelService.save(eachEntryModel);
					regularEntriesIndex++;
				}

			}
			modelService.save(orderModel);
			recalculateTotals(orderModel);
			modelService.save(orderModel);

			return hssOrderModel;

		}
		return null;
	}

	public void recalculateTotals(final OrderModel orderModel)
	{

		//initialize totals price
		Double coldRolledTotalPrice = 0.00d;
		Double hotRolledTotalPrice = 0.00d;
		Double galvanizedTotalPrice = 0.00d;
		Double aluminizedTotalPrice = 0.00d;
		Double galvametalTotalPrice = 0.00d;

		//initialize totals Weight
		Double coldRolledTotalWeight = 0.00d;
		Double hotRolledTotalWeight = 0.00d;
		Double galvanizedTotalWeight = 0.00d;
		Double aluminizedTotalWeight = 0.00d;
		Double galvametalTotalWeight = 0.00d;

		Double totalWeightAll = 0.00d;
		Double totalPriceAll = 0.00d;
		Double subtotalPriceAll = 0.00d;

		Double totalAssurance = 0.00d;
		Double totalTaxas = 0.00d;
		Double totalDeliveryCost = 0.00d;

		for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
		{
			LOG.info("Order entry #" + eachEntryModel.getEntryNumber());

			// get steel category
			final String varCondition = eachEntryModel.getSteelCategory();

			// get taxes
			final Double entryTax = eachEntryModel.getTotalTaxas();

			// get assurance
			final Double entryAssurance = eachEntryModel.getTotalAssurance();

			// get delivery cost
			final Double entryDeliveryCost = eachEntryModel.getTotalDeliveryCost();

			// get weight
			final Double totalWeight = eachEntryModel.getWeight();

			// accumulate assurance, taxes and delivery cost for order data
			totalAssurance += entryAssurance;
			totalTaxas += entryTax;
			totalDeliveryCost += entryDeliveryCost;

			final Double totalNetPrice = eachEntryModel.getNetPriceWOTaxes();

			if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.COLD_ROLLED))
			{
				coldRolledTotalPrice += totalNetPrice;
				coldRolledTotalWeight += totalWeight;
			}
			else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.HOT_ROLLED))
			{
				hotRolledTotalPrice += totalNetPrice;
				hotRolledTotalWeight += totalWeight;
			}
			else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.ALUMINIZED))
			{
				aluminizedTotalPrice += totalNetPrice;
				aluminizedTotalWeight += totalWeight;
			}
			else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVANIZED))
			{
				galvanizedTotalPrice += totalNetPrice;
				galvanizedTotalWeight += totalWeight;
			}
			else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVAMETAL))
			{
				galvametalTotalPrice += totalNetPrice;
				galvametalTotalWeight += totalWeight;
			}

			totalWeightAll = coldRolledTotalWeight + hotRolledTotalWeight + aluminizedTotalWeight + galvanizedTotalWeight
					+ galvametalTotalWeight;
			totalPriceAll = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice + galvanizedTotalPrice
					+ galvametalTotalPrice + totalAssurance + totalDeliveryCost + totalTaxas;
			subtotalPriceAll = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice + galvanizedTotalPrice
					+ galvametalTotalPrice + totalAssurance + totalDeliveryCost;


		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//set weights categories
		orderModel.setHotRolledWeight(hotRolledTotalWeight);
		orderModel.setColdRolledWeight(coldRolledTotalWeight);
		orderModel.setGalvanizedWeight(galvanizedTotalWeight);
		orderModel.setAluminizedWeight(aluminizedTotalWeight);
		orderModel.setGalvametalWeight(galvametalTotalWeight);
		orderModel.setTotalWeight(totalWeightAll);

		//set prices categories
		orderModel.setHotRolledPrice(hotRolledTotalPrice);
		orderModel.setColdRolledPrice(coldRolledTotalPrice);
		orderModel.setGalvanizedPrice(galvanizedTotalPrice);
		orderModel.setGalvametalPrice(galvametalTotalPrice);
		orderModel.setAluminizedPrice(aluminizedTotalPrice);
		orderModel.setSapTotalPrice(totalPriceAll);
		orderModel.setSapSubtotalPrice(subtotalPriceAll);

		//set taxas, delivery cost and assurance totals
		orderModel.setTotalAssurance(totalAssurance);
		orderModel.setTotalDeliveryCost(totalDeliveryCost);
		orderModel.setTotalTaxas(totalTaxas);
	}
}
