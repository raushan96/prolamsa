/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.services.NeorisAddressService;
import mx.neoris.core.services.NeorisOrderHistoryService;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.OrderHistorySearchParameters;

import org.apache.commons.lang.StringUtils;




/**
 * @author e-lacantu modified by fdeutsch
 * 
 */
public class DefaultNeorisOrderHistoryService implements NeorisOrderHistoryService
{
	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource(name = "neorisAddressService")
	private NeorisAddressService neorisAddressService;

	private FlexibleSearchService flexibleSearchService;
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	private OrderStatus[] exlusiveOrderStatus =
	{ //OrderStatus.OPEN, OrderStatus.CREATED, OrderStatus.ASSIGNED_TO_ADMIN,
	OrderStatus.COMPLETED, OrderStatus.CANCELLED, OrderStatus.IN_PROCESS, OrderStatus.PENDING_APPROVAL, OrderStatus.APPROVED };

	private OrderStatus[] exlusiveQuoteStatus =
	{ OrderStatus.PENDING_QUOTE, OrderStatus.REJECTED_QUOTE, OrderStatus.APPROVED_QUOTE, OrderStatus.USED_QUOTE,
			OrderStatus.CANCELLED_QUOTE };


	@Resource(name = "neorisAddressConverter")
	private Converter<AddressModel, AddressData> addressConverter;

	public SearchPageData<OrderModel> getPagedOrderHistory(final OrderHistorySearchParameters searchParameters,
			final B2BCustomerModel customer, final List<OrderStatus> statusList) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		final StringBuilder textQuery = new StringBuilder();
		final StringBuilder dateQuery = new StringBuilder();
		final List<String> listB2BUnitCode = new ArrayList<String>();
		final List<String> listAddressCode = new ArrayList<String>();
		final List<OrderStatus> listStatus = new ArrayList<OrderStatus>();

		// customer filter
		if (StringUtils.isNotBlank(searchParameters.getCustomer()))
		{
			if (searchParameters.getCustomer().equalsIgnoreCase("all"))
			{
				final List<B2BUnitModel> allUnits = neorisService.getB2BUnitModelsForCustomerAndCurrentStore(customer);

				for (final B2BUnitModel eachB2BUnit : allUnits)
				{
					listB2BUnitCode.add(eachB2BUnit.getPk().getLongValueAsString());
				}
			}
			else
			{
				final B2BUnitModel unit = neorisService.getB2BUnitForUID(searchParameters.getCustomer());

				if (unit != null)
				{
					listB2BUnitCode.add(unit.getPk().getLongValueAsString());
				}
			}
		}
		else
		{

		}

		// address
		if (StringUtils.isNotBlank(searchParameters.getDeliveryAddress()))
		{
			if (!searchParameters.getDeliveryAddress().equalsIgnoreCase("all"))
			{
				listAddressCode.add(searchParameters.getDeliveryAddress());

				//				final AddressModel addressModel = neorisAddressService.getAddressWithCode(searchParameters.getDeliveryAddress());
				//
				//				if (addressModel != null)
				//				{
				//					listAddressCode.add(addressModel.getPk().getLongValueAsString());
				//				}
			}
		}

		// status
		if (StringUtils.isNotBlank(searchParameters.getStatus()) && !searchParameters.getStatus().equalsIgnoreCase("all"))
		{
			final OrderStatus status = OrderStatus.valueOf(searchParameters.getStatus());

			if (status != null)
			{
				listStatus.add(status);
			}
			else
			{
				listStatus.addAll(statusList);
			}
		}
		else
		{
			listStatus.addAll(statusList);
		}

		if (listB2BUnitCode.size() > 0)
		{
			//			textQuery.append(" AND {order." + OrderModel.UNIT + "} IN (?listCode) ");
			//			queryParams.put("listCode", (Serializable) listB2BUnitCode);

			textQuery.append(" AND ({order." + OrderModel.UNIT + "} = '");
			//queryParams.put("listCode", (Serializable) listB2BUnitCode);

			for (final String eachCode : listB2BUnitCode)
			{
				textQuery.append(eachCode + "' OR {" + OrderModel.UNIT + "}='");
			}
			textQuery.delete(textQuery.length() - 11, textQuery.length());
			textQuery.append(" ) ");
		}

		//		if (listAddressCode.size() > 0)
		//		{
		//			textQuery.append(" AND {order." + OrderModel.DELIVERYADDRESS + "} IN (?deliveryAddress) ");
		//			queryParams.put("deliveryAddress", (Serializable) listAddressCode);
		//		}

		if (listStatus.size() > 0)
		{
			textQuery.append(" AND {order." + OrderModel.STATUS + "} IN (?status) ");
			queryParams.put("status", (Serializable) listStatus);
		}

		if (StringUtils.isNotBlank(searchParameters.getOrderNumber()))
		{
			textQuery.append(" AND {order." + OrderModel.CODE + "} LIKE (?orderNumber) ");
			queryParams.put("orderNumber", "%" + searchParameters.getOrderNumber() + "%");
		}

		if (StringUtils.isNotBlank(searchParameters.getPoNumber()))
		{
			textQuery.append(" AND {order." + OrderModel.PURCHASEORDERNUMBER + "} = ?poNumber ");
			queryParams.put("poNumber", searchParameters.getPoNumber());
		}

		if (searchParameters.getInitialDate() != null)
		{
			dateQuery.append(" AND {order." + OrderModel.CREATIONTIME + "} >= ?initialDate ");
			queryParams.put("initialDate", searchParameters.getInitialDate());
		}

		if (searchParameters.getFinalDate() != null)
		{
			dateQuery.append(" AND {order." + OrderModel.CREATIONTIME + "} <= ?finalDate ");
			queryParams.put("finalDate", searchParameters.getFinalDate());
		}

		final StringBuilder sortQuery = new StringBuilder();
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append(" ORDER BY {");

			if (searchParameters.getSortBy().equalsIgnoreCase("deliveryAddress"))
			{
				sortQuery.append("address.shortName");
			}
			else
			{
				sortQuery.append("order." + searchParameters.getSortBy());
			}

			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());
		}

		final StringBuilder query = new StringBuilder();

		query.append("SELECT {order." + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE + " AS order ");

		if (listAddressCode.size() > 0)
		{
			query.append("join ");
			query.append(AddressModel._TYPECODE);
			query.append(" as address on {order.");
			query.append(OrderModel.DELIVERYADDRESS);
			query.append("} = {address.");
			query.append(AddressModel.PK);
			query.append("} and {address.");
			query.append(AddressModel.CODE);
			query.append("} IN (?deliveryAddress) ");

			queryParams.put("deliveryAddress", (Serializable) listAddressCode);

		}
		else if (searchParameters.getSortBy() != null && searchParameters.getSortBy().equalsIgnoreCase("deliveryAddress"))
		{

			query.append("join ");
			query.append(AddressModel._TYPECODE);
			query.append(" as address on {order.");
			query.append(OrderModel.DELIVERYADDRESS);
			query.append("} = {address.");
			query.append(AddressModel.PK);
			query.append("} ");
		}


		query.append("}");

		query.append("WHERE 1=1 AND {order." + OrderModel.VERSIONID + "} IS NULL ");

		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		if (currentBaseStoreModel != null)
		{
			query.append("AND {order." + OrderModel.STORE + "} = '" + currentBaseStoreModel.getPk() + "'");
		}


		query.append(textQuery.toString());
		query.append(dateQuery.toString());
		query.append(sortQuery.toString());

		return getPagedFlexibleSearchService().search(query.toString(), queryParams, searchParameters.getPageableData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisOrderHistoryService#getOrderHistoryByB2BUnitModel(java.util.List)
	 */
	@Override
	public SearchPageData<OrderData> getOrderHistoryByB2BUnitModel(final List<Long> listB2BUnits) throws Exception
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();
		final StringBuilder textQuery = new StringBuilder();
		final StringBuilder query = new StringBuilder();

		query.append("SELECT {order." + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE
				+ " AS order JOIN OrderStatus as oStatus ON  {order.status}={oStatus.pk} } ");
		query.append("WHERE {order." + OrderModel.VERSIONID + "} IS NULL ");
		query.append("AND {order." + OrderModel.UNIT + "} IN (?listB2BUnits) ");
		queryParams.put("listB2BUnits", (Serializable) listB2BUnits);

		final StringBuilder statusesQuery = new StringBuilder();

		statusesQuery.append("AND {oStatus.code} IN (");
		for (final OrderStatus orderStatus : this.getExlusiveOrderStatus())
		{
			statusesQuery.append("'");
			statusesQuery.append(orderStatus.getCode());
			statusesQuery.append("'");
			statusesQuery.append(",");
		}
		statusesQuery.deleteCharAt(statusesQuery.length() - 1);
		statusesQuery.append(") ");

		query.append(statusesQuery);

		query.append(textQuery.toString());
		query.append("ORDER BY {" + OrderModel.CREATIONTIME + "} desc");


		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(0);
		pageableData.setPageSize(10);
		pageableData.setSort(null);

		return getPagedFlexibleSearchService().search(query.toString(), queryParams, pageableData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisOrderHistoryService#getAllOrdersAndQuotes()
	 */
	@Override
	public List<OrderModel> getAllOrdersAndQuotes() throws Exception
	{
		final StringBuilder query = new StringBuilder();

		final List<OrderStatus> listStatus = enumerationService.getEnumerationValues(OrderStatus._TYPECODE);

		query.append("SELECT {order." + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE + " AS order} ");
		query.append("WHERE {order." + OrderModel.VERSIONID + "} IS NULL ");
		query.append("AND {order." + OrderModel.STATUS + "} IN (?status) ");

		final FlexibleSearchQuery queryFlex = new FlexibleSearchQuery(query);
		queryFlex.addQueryParameter("status", listStatus);

		final SearchResult<OrderModel> searchResult = flexibleSearchService.search(queryFlex);

		return searchResult.getResult();
	}

	public OrderStatus[] getExlusiveOrderStatus()
	{
		return exlusiveOrderStatus;
	}

	public void setExlusiveOrderStatus(final OrderStatus[] exlusiveOrderStatus)
	{
		this.exlusiveOrderStatus = exlusiveOrderStatus;
	}

	public OrderStatus[] getExlusiveQuoteStatus()
	{
		return exlusiveQuoteStatus;
	}

	public void setExlusiveQuoteStatus(final OrderStatus[] exlusiveQuoteStatus)
	{
		this.exlusiveQuoteStatus = exlusiveQuoteStatus;
	}


	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/**
	 * @return the pagedFlexibleSearchService
	 */
	public PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	/**
	 * @param pagedFlexibleSearchService
	 *           the pagedFlexibleSearchService to set
	 */
	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
	}
}
