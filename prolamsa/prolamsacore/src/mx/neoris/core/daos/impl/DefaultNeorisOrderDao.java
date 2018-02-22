/**
 *
 */
package mx.neoris.core.daos.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.daos.impl.DefaultOrderDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import mx.neoris.core.daos.NeorisOrderDao;


/**
 * @author fdeutsch
 *
 */
public class DefaultNeorisOrderDao extends DefaultOrderDao implements NeorisOrderDao
{

	private static final Logger LOG = Logger.getLogger(DefaultNeorisOrderDao.class);

	@Override
	public List<AbstractOrderEntryModel> findEntriesByProduct(final String entryTypeCode, final AbstractOrderModel order,
			final ProductModel product, final Date rollingScheduleWeek)
	{
		validateParameterNotNull(entryTypeCode, "entryTypeCode must not be null!");
		validateParameterNotNull(order, "order must not be null!");
		validateParameterNotNull(product, "product must not be null!");

		final Map<String, Object> values = new HashMap<String, Object>();
		values.put("o", order);
		values.put("product", product);
		values.put("rollingScheduleWeek", rollingScheduleWeek);

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {").append(AbstractOrderEntryModel.PK).append("} FROM {").append(entryTypeCode)
				.append("} WHERE {").append(AbstractOrderEntryModel.ORDER).append("} = ?o AND {")
				.append(AbstractOrderEntryModel.PRODUCT).append("} = ?product AND {");

		if (rollingScheduleWeek != null)
		{
			queryString.append(AbstractOrderEntryModel.ROLLINGSCHEDULEWEEK).append("} = ?rollingScheduleWeek");
		}
		else
		{
			queryString.append(AbstractOrderEntryModel.ROLLINGSCHEDULEWEEK).append("} IS NULL");
		}

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString(), values);
		query.setResultClassList(Collections.singletonList(AbstractOrderEntryModel.class));
		final SearchResult<AbstractOrderEntryModel> res = getFlexibleSearchService().search(query);
		final List<AbstractOrderEntryModel> result = res.getResult();
		return result == null ? Collections.EMPTY_LIST : result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderModel> findUnPushedOrderByDate(final Date lastSuccessFullDate)
	{
		LOG.info("Calling findUnPushedOrderByDate method to get list of all unpushed order: ");
		validateParameterNotNull(lastSuccessFullDate, "lastSuccessFullDate must not be null!");
		//Flexible Search Query to Retrive all orders which got failed to get created in SAP.
		final Map<String, Object> values = new HashMap<String, Object>();
		values.put("lastSuccessFullDate", lastSuccessFullDate);
		values.put("endDate", new Date());

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {").append(OrderModel.PK).append("} FROM {").append(OrderModel._TYPECODE).append("} WHERE {")
				.append(OrderModel.SAPORDERID).append("} IS NULL AND {").append(OrderModel.CREATIONTIME).append("} BETWEEN")
				.append("?lastSuccessFullDate").append("AND").append("?endDate");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString(), values);
		LOG.info("Query to get all unpushed orders ::" + query);
		query.setResultClassList(Collections.singletonList(OrderModel.class));
		final SearchResult<OrderModel> res = getFlexibleSearchService().search(query);
		final List<OrderModel> result = res.getResult();

		return result == null ? Collections.EMPTY_LIST : result;


	}
}
