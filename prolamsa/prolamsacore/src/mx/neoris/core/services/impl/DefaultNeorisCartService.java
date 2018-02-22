/**
 * 
 */
package mx.neoris.core.services.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.acceleratorservices.order.impl.DefaultCartServiceForAccelerator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.daos.NeorisOrderDao;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.services.NeorisCartService;

import org.apache.log4j.Logger;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisCartService extends DefaultCartServiceForAccelerator implements NeorisCartService
{
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger.getLogger(DefaultNeorisCartService.class);

	private static final int APPEND_AS_LAST = -1;

	@Resource(name = "neorisOrderDao")
	private NeorisOrderDao neorisOrderDao;

	public AbstractOrderEntryModel addNewEntry(final AbstractOrderModel order, final ProductModel product,
			final ProlamsaAPIProductConfigurationModel configModel, final Date rollingScheduleDate, final long bundlesQuantity,
			final UnitModel unit, final int number, final boolean addToPresent, final double convertedQuantity,
			final String stockOuts)
	{
		final ComposedTypeModel entryType = getAbstractOrderEntryTypeService().getAbstractOrderEntryType(order);

		validateParameterNotNullStandardMessage("entryType", entryType);
		validateParameterNotNullStandardMessage("product", product);
		validateParameterNotNullStandardMessage("order", order);
		if (bundlesQuantity <= 0)
		{
			throw new IllegalArgumentException("Quantity must be a positive non-zero value");
		}
		if (number < APPEND_AS_LAST)
		{
			throw new IllegalArgumentException("Number must be greater or equal -1");
		}
		UnitModel usedUnit = unit;
		if (usedUnit == null)
		{
			LOG.debug("No unit passed, trying to get product unit");
			usedUnit = product.getUnit();
			validateParameterNotNullStandardMessage("usedUnit", usedUnit);
		}

		AbstractOrderEntryModel ret = null;
		// search for present entries for this product if needed
		if (addToPresent)
		{
			for (final AbstractOrderEntryModel e : getEntriesForProduct(order, product, rollingScheduleDate))
			{
				/*
				 * Check if the entrymodel has Point of service and if "Yes" then compare the entry number as we might have
				 * multiple POS for same product and update should happen for right entry model with right POS. Else if the
				 * POS is null and since we always pass -1 from DefaultCommerceCartService, we pass -1 only for addnew for
				 * POS, which means it's a shipping mode entry.
				 */
				if (((e.getDeliveryPointOfService() == null && number == APPEND_AS_LAST)) || e.getDeliveryPointOfService() != null
						&& number == e.getEntryNumber().intValue())
				{
					//Ensure that order entry is not a 'give away', and has same units
					if (Boolean.FALSE.equals(e.getGiveAway()) && usedUnit.equals(e.getUnit()))
					{
						e.setQuantity(Long.valueOf(e.getQuantity().longValue() + bundlesQuantity));
						e.setConvertedQuantity(convertedQuantity);
						ret = e;
						break;
					}
				}
			}
		}

		if (ret == null)
		{
			ret = getAbstractOrderEntryService().createEntry(entryType, order);
			ret.setQuantity(Long.valueOf(bundlesQuantity));
			ret.setConvertedQuantity(convertedQuantity);
			ret.setProduct(product);
			ret.setUnit(usedUnit);
			ret.setRollingScheduleWeek(rollingScheduleDate);
			ret.setApiProductConfiguration(configModel);
			ret.setStockOuts(stockOuts);
			addEntryAtPosition(order, ret, number);
		}
		order.setCalculated(Boolean.FALSE);

		return ret;
	}

	public List<AbstractOrderEntryModel> getEntriesForProduct(final AbstractOrderModel order, final ProductModel product,
			final Date rollingScheduleDate)
	{
		validateParameterNotNullStandardMessage("order", order);
		validateParameterNotNullStandardMessage("product", product);

		final List<AbstractOrderEntryModel> col = neorisOrderDao.findEntriesByProduct(getEntryTypeCode(order), order, product,
				rollingScheduleDate);

		return col;
	}
}
