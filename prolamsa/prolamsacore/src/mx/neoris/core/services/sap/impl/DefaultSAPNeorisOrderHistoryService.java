/**
 * 
 */
package mx.neoris.core.services.sap.impl;



/**
 * @author christian.loredo
 * 
 */

public class DefaultSAPNeorisOrderHistoryService //implements NeorisOrderHistoryService
{
	/*
	 * private FlexibleSearchService flexibleSearchService; private PagedFlexibleSearchService
	 * pagedFlexibleSearchService; private OrderStatus[] exlusiveOrderStatus = { OrderStatus.OPEN, OrderStatus.COMPLETED,
	 * OrderStatus.CANCELLED, OrderStatus.IN_PROCESS, OrderStatus.PENDING_APPROVAL, OrderStatus.ASSIGNED_TO_ADMIN,
	 * OrderStatus.APPROVED, OrderStatus.CREATED };
	 * 
	 * private OrderStatus[] exlusiveQuoteStatus = { OrderStatus.PENDING_QUOTE, OrderStatus.REJECTED_QUOTE,
	 * OrderStatus.APPROVED_QUOTE, OrderStatus.USED_QUOTE };
	 * 
	 * private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisOrderHistoryService.class);
	 * 
	 * @Resource(name = "neorisAddressConverter") private Converter<AddressModel, AddressData> addressConverter;
	 * 
	 * @Resource(name = "SAPConnectionManager") private SAPConnectionManager sapConnection;
	 * 
	 * @Resource(name = "baseStoreService") private BaseStoreService baseStoreService;
	 * 
	 * 
	 * public FlexibleSearchService getFlexibleSearchService() { return flexibleSearchService; }
	 * 
	 * 
	 * public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
	 * this.flexibleSearchService = flexibleSearchService; }
	 * 
	 * 
	 * public PagedFlexibleSearchService getPagedFlexibleSearchService() { return pagedFlexibleSearchService; }
	 * 
	 * 
	 * public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService) {
	 * this.pagedFlexibleSearchService = pagedFlexibleSearchService; }
	 * 
	 * 
	 * @Override public SearchPageData<OrderModel> getPagedOrderHistory(final OrderHistorySearchParameters
	 * searchParameters, final B2BUnitModel unit, final List<B2BUnitModel> listB2BUnits, final List<AddressData>
	 * deliveryAddresses, final OrderStatus... statuses) throws Exception { final Map<String, Serializable> queryParams =
	 * new HashMap<String, Serializable>();
	 * 
	 * final StringBuilder textQuery = new StringBuilder(); final StringBuilder dateQuery = new StringBuilder(); final
	 * List<String> listCode = new ArrayList<String>(); final List<String> listAddress = new ArrayList<String>(); final
	 * List<String> listAddressCode = new ArrayList<String>(); final List<OrderStatus> listStatus = new
	 * ArrayList<OrderStatus>();
	 * 
	 * if (searchParameters.getCustomer() != null && StringUtils.isNotBlank(searchParameters.getCustomer())) {
	 * 
	 * //validate the Customer field for All selection if (searchParameters.getCustomer().equalsIgnoreCase("all")) { for
	 * (final B2BUnitModel b2bUnit : listB2BUnits) { final Set<AbstractOrderModel> order = b2bUnit.getOrders();
	 * 
	 * if (order.size() > 0) { final Iterator<AbstractOrderModel> iterator = order.iterator(); while (iterator.hasNext())
	 * { final AbstractOrderModel setElement = iterator.next(); if (setElement.getCode() != null) {
	 * listCode.add(setElement.getCode()); listAddress.add(setElement.getDeliveryAddress().getPk().toString());
	 * listAddressCode.add(setElement.getDeliveryAddress().getCode()); listStatus.add(setElement.getStatus()); } } } }
	 * 
	 * } else { final Set<AbstractOrderModel> order = unit.getOrders();
	 * 
	 * if (order != null && order.size() > 0) { final Iterator<AbstractOrderModel> iterator = order.iterator(); while
	 * (iterator.hasNext()) { final AbstractOrderModel setElement = iterator.next(); if (setElement.getCode() != null) {
	 * listCode.add(setElement.getCode()); listAddress.add(setElement.getDeliveryAddress().getPk().toString());
	 * listAddressCode.add(setElement.getDeliveryAddress().getCode()); listStatus.add(setElement.getStatus()); } } } }
	 * 
	 * 
	 * //validate the Delivery Address field for All selection if
	 * (!searchParameters.getDeliveryAddress().equalsIgnoreCase("all")) { int x = 0; for (final String list :
	 * listAddressCode) { if (list.equals(searchParameters.getDeliveryAddress())) { final String data =
	 * listAddress.get(x); listAddress.clear(); listAddress.add(data); break; } x++; } }
	 * 
	 * //validate the Status field for All selection if (!searchParameters.getStatus().equalsIgnoreCase("all")) {
	 * listStatus.clear(); OrderStatus status = null; for (final OrderStatus orderStatus : statuses) { if
	 * (orderStatus.getCode().equals(searchParameters.getStatus())) { status = orderStatus; break; } }
	 * listStatus.add(status); }
	 * 
	 * //check if exist orders for the B2BUnit, if not assign a false statement ( 2 = 1 ) to return an empty query if
	 * (listCode.size() > 0) { textQuery.append(" AND {order." + OrderModel.CODE + "} IN (?listCode) ");
	 * queryParams.put("listCode", (Serializable) listCode); } else { textQuery.append(" AND 2 = 1 "); }
	 * 
	 * }
	 * 
	 * 
	 * if (searchParameters.getDeliveryAddress() != null &&
	 * StringUtils.isNotBlank(searchParameters.getDeliveryAddress())) { if (listAddress.size() > 0) {
	 * textQuery.append(" AND {order." + OrderModel.DELIVERYADDRESS + "} IN (?deliveryAddress) ");
	 * queryParams.put("deliveryAddress", (Serializable) listAddress); }
	 * 
	 * }
	 * 
	 * 
	 * if (searchParameters.getStatus() != null && StringUtils.isNotBlank(searchParameters.getStatus())) { if
	 * (listStatus.size() > 0) { textQuery.append(" AND {order." + OrderModel.STATUS + "} IN (?status) ");
	 * queryParams.put("status", (Serializable) listStatus); }
	 * 
	 * }
	 * 
	 * 
	 * if (searchParameters.getOrderNumber() != null && StringUtils.isNotBlank(searchParameters.getOrderNumber())) {
	 * textQuery.append(" AND {order." + OrderModel.CODE + "} = ?orderNumber "); queryParams.put("orderNumber",
	 * searchParameters.getOrderNumber()); }
	 * 
	 * if (searchParameters.getPoNumber() != null && StringUtils.isNotBlank(searchParameters.getPoNumber())) {
	 * textQuery.append(" AND {order." + OrderModel.PURCHASEORDERNUMBER + "} = ?poNumber "); queryParams.put("poNumber",
	 * searchParameters.getPoNumber()); }
	 * 
	 * if (searchParameters.getInitialDate() != null &&
	 * StringUtils.isNotBlank(searchParameters.getInitialDate().toString())) { dateQuery.append(" AND {order." +
	 * OrderModel.CREATIONTIME + "} >= ?initialDate "); queryParams.put("initialDate",
	 * searchParameters.getInitialDate()); }
	 * 
	 * if (searchParameters.getFinalDate() != null && StringUtils.isNotBlank(searchParameters.getFinalDate().toString()))
	 * { dateQuery.append(" AND {order." + OrderModel.CREATIONTIME + "} <= ?finalDate "); queryParams.put("finalDate",
	 * searchParameters.getFinalDate()); }
	 * 
	 * 
	 * final StringBuilder sortQuery = new StringBuilder(); if (searchParameters.getSortBy() != null) {
	 * sortQuery.append(" ORDER BY {"); sortQuery.append(searchParameters.getSortBy()); sortQuery.append("} ");
	 * sortQuery.append(searchParameters.getSortOrder()); }
	 * 
	 * final StringBuilder query = new StringBuilder();
	 * 
	 * 
	 * 
	 * query.append("SELECT {order." + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE + " AS order } ");
	 * query.append("WHERE 1=1 AND {order." + OrderModel.VERSIONID + "} IS NULL "); query.append(textQuery.toString());
	 * query.append(dateQuery.toString()); query.append(sortQuery.toString());
	 * 
	 * 
	 * return getPagedFlexibleSearchService().search(query.toString(), queryParams, searchParameters.getPageableData());
	 * 
	 * }
	 * 
	 * 
	 * @Override public SearchPageData<OrderModel> getOrderHistoryByB2BUnit(final List<B2BUnitData> listB2BUnits) throws
	 * Exception { //SAP Implementation
	 * 
	 * LOG.info("SAP CONNECTION: Initialize"); final SearchPageData<OrderModel> pageData = new
	 * SearchPageData<OrderModel>(); final List<String> listCode = new ArrayList<String>();
	 * 
	 * try { final JCoDestination sapDest = sapConnection.getDestination(); final JCoFunction sapFunc =
	 * sapConnection.createFunction(SAPConstants.RFC.ORDER_STATUS.ORDER_STATUS, sapDest);
	 * 
	 * LOG.info("SAP CONNECTION: Successfull");
	 * 
	 * sapFunc.getImportParameterList().setValue(SAPConstants.RFC.ORDER_STATUS.BASE_STORE,
	 * baseStoreService.getCurrentBaseStore().getUid());
	 * 
	 * //get the orders for all B2B Units of the current customer for (final B2BUnitData b2bUnit : listB2BUnits) { final
	 * Collection<AbstractOrderData> order = b2bUnit.getOrders();
	 * 
	 * if (order != null && order.size() > 0) {
	 * 
	 * final Iterator<AbstractOrderData> iterator = order.iterator(); while (iterator.hasNext()) { final
	 * AbstractOrderData setElement = iterator.next(); if (setElement.getCode() != null) {
	 * listCode.add(setElement.getCode()); } } } }
	 * 
	 * final JCoTable inputTablePedido =
	 * sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ORDER_STATUS.TI_PEDIDO);
	 * 
	 * final Integer size = listCode.size();
	 * 
	 * for (int i = 0; i < size; i++) { inputTablePedido.appendRow();
	 * inputTablePedido.setValue(SAPConstants.RFC.ORDER_STATUS.PEDIDO, listCode.get(i)); }
	 * 
	 * final JCoTable inputTablePartida =
	 * sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ORDER_STATUS.TI_PARTIDA); inputTablePartida.appendRow();
	 * inputTablePartida.setValue(SAPConstants.RFC.ORDER_STATUS.PARTIDA, "");
	 * 
	 * //execute sapConnection.executeFunction(sapFunc, sapDest);
	 * 
	 * // get the result table final List<OrderModel> list = new ArrayList<OrderModel>(); final JCoTable resultTable =
	 * sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ORDER_STATUS.TO_STATUS_PEDIDO);
	 * 
	 * for (int i = 0; i < resultTable.getNumRows(); i++) { resultTable.setRow(i);
	 * 
	 * final OrderModel orderModel = new OrderModel(); //final B2BUnitModel b2bUnitModel = new B2BUnitModel();
	 * 
	 * //final String uid = resultTable.getString(SAPConstants.RFC.INVOICE.CUSTOMER).substring(4);
	 * //b2bUnitModel.setUid(uid);
	 * 
	 * //final B2BUnitModel b2bModel = b2bUnitService.getUnitForUid(uid);
	 * 
	 * //if (b2bModel != null) //{ // b2bUnitModel.setName(b2bModel.getName()); //
	 * b2bUnitModel.setShortName(b2bModel.getShortName()); //}
	 * 
	 * 
	 * 
	 * orderModel.setCode(resultTable.getString(SAPConstants.RFC.ORDER_STATUS.PEDIDO));
	 * orderModel.setStatusInfo(resultTable.getString(SAPConstants.RFC.ORDER_STATUS.STATUS_HEAD_ENG));
	 * 
	 * list.add(orderModel);
	 * 
	 * }
	 * 
	 * //set the result final SearchResult<OrderModel> result = new SearchResultImpl<OrderModel>(list, list.size(), -1,
	 * 0);
	 * 
	 * pageData.setResults(result.getResult());
	 * 
	 * final PaginationData paginationData = new PaginationData(); final PageableData pageableData = new PageableData();
	 * pageableData.setCurrentPage(0); pageableData.setPageSize(10); pageableData.setSort(null);
	 * 
	 * paginationData.setPageSize(pageableData.getPageSize()); paginationData.setSort(pageableData.getSort());
	 * paginationData.setTotalNumberOfResults(result.getTotalCount()); paginationData .setNumberOfPages((int)
	 * Math.ceil(paginationData.getTotalNumberOfResults() / paginationData.getPageSize()));
	 * paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(),
	 * pageableData.getCurrentPage())));
	 * 
	 * pageData.setPagination(paginationData);
	 * 
	 * 
	 * 
	 * } catch (final Exception ex) { LOG.error("Error while connecting to SAP", ex); }
	 * 
	 * return pageData; }
	 * 
	 * 
	 * 
	 * 
	 * public OrderStatus[] getExlusiveOrderStatus() { return exlusiveOrderStatus; }
	 * 
	 * public void setExlusiveOrderStatus(final OrderStatus[] exlusiveOrderStatus) { this.exlusiveOrderStatus =
	 * exlusiveOrderStatus; }
	 * 
	 * public OrderStatus[] getExlusiveQuoteStatus() { return exlusiveQuoteStatus; }
	 * 
	 * public void setExlusiveQuoteStatus(final OrderStatus[] exlusiveQuoteStatus) { this.exlusiveQuoteStatus =
	 * exlusiveQuoteStatus; }
	 */

}
