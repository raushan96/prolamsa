/**
 * 
 */
package mx.neoris.core.services.sap.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.impl.SearchResultImpl;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.BackorderDetailModel;
import mx.neoris.core.model.BackorderModel;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.BackorderDetailSearchParameter;
import mx.neoris.core.services.BackorderSearchParameters;
import mx.neoris.core.services.NeorisAddressService;
import mx.neoris.core.services.NeorisBackorderService;
import mx.neoris.core.util.SAPUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author christian.loredo
 * 
 */
public class DefaultSAPNeorisBackorderService implements NeorisBackorderService
{

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "neorisAddressService")
	private NeorisAddressService addressService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisBackorderService.class);

	public SearchPageData<BackorderModel> getPagedBackorder(final BackorderSearchParameters searchParameters) throws Exception
	{

		//SAP Implementation

		LOG.info("SAP CONNECTION: Initialize");
		final SearchPageData<BackorderModel> pageData = new SearchPageData<BackorderModel>();
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		try
		{
			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX 
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.BACKORDER.BACKORDER, custDest);
			LOG.info("Operation SAP : RFC - " + SAPConstants.RFC.BACKORDER.BACKORDER);

			LOG.info("SAP CONNECTION: Successfull");
			// input table parameter 1.14 BACKORDER LEVEL 1

			//fill up the fields
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.BACKORDER.INPUT_TABLE);

			LOG.info("Table Parameter : RFC - " + SAPConstants.RFC.BACKORDER.INPUT_TABLE);
			LOG.info("Input Parameter RFC");


			//validate for All selection, "" means All selection
			if (searchParameters.getCustomer() != null && searchParameters.getCustomer().equals(""))
			{

				for (final String customer : searchParameters.getListCustomer())
				{
					inputTable.appendRow();
					inputTable.setValue(SAPConstants.RFC.BACKORDER.BASE_STORE, searchParameters.getBaseStore());
					inputTable.setValue(SAPConstants.RFC.BACKORDER.CUSTOMER, SAPUtils.getFormattedUID(customer));

					LOG.info("Parameter " + SAPConstants.RFC.BACKORDER.BASE_STORE + " Value = " + searchParameters.getBaseStore());
					LOG.info("Parameter " + SAPConstants.RFC.BACKORDER.CUSTOMER + " Value = " + SAPUtils.getFormattedUID(customer));
				}

			}
			else
			{
				inputTable.appendRow();
				inputTable.setValue(SAPConstants.RFC.BACKORDER.BASE_STORE, searchParameters.getBaseStore());
				inputTable.setValue(SAPConstants.RFC.BACKORDER.CUSTOMER, SAPUtils.getFormattedUID(searchParameters.getCustomer()));

				LOG.info("Parameter " + SAPConstants.RFC.BACKORDER.BASE_STORE + " Value = " + searchParameters.getBaseStore());
				LOG.info("Parameter " + SAPConstants.RFC.BACKORDER.CUSTOMER + " Value = "
						+ SAPUtils.getFormattedUID(searchParameters.getCustomer()));
			}

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.BACKORDER.BACKORDER, currentUser.getUid(),
					true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.BACKORDER.BACKORDER, currentUser.getUid(),
					false, sessionActual));

			// get the result table	
			final List<BackorderModel> list = new ArrayList<BackorderModel>();

			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.BACKORDER.OUTPUT_TABLE);

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final BackorderModel backorderModel = new BackorderModel();

				//B2BUnitModel b2bUnitModel = new B2BUnitModel();
				AddressModel addressModel = new AddressModel();

				final String uid = resultTable.getString(SAPConstants.RFC.BACKORDER.CUSTOMER).substring(5);
				/*
				 * b2bUnitModel = b2bUnitService.getUnitForUid(uid);
				 * 
				 * if (b2bUnitModel != null) { backorderModel.setCustomer(b2bUnitModel); }
				 */

				addressModel = addressService.getAddressWithCode(uid);
				if (addressModel != null)
				{
					backorderModel.setCustomer(addressModel);
				}

				//Info BACKORDER
				LOG.info("back order entry: " + i);
				LOG.info(SAPConstants.RFC.BACKORDER.UOM_LOADING + ": "
						+ resultTable.getString(SAPConstants.RFC.BACKORDER.UOM_LOADING));
				LOG.info(SAPConstants.RFC.BACKORDER.PC_ORDER + ": " + resultTable.getString(SAPConstants.RFC.BACKORDER.PC_ORDER));
				LOG.info(SAPConstants.RFC.BACKORDER.KGS_ORDER + ": " + resultTable.getString(SAPConstants.RFC.BACKORDER.KGS_ORDER));
				LOG.info(SAPConstants.RFC.BACKORDER.PENDING_KILOS + ": "
						+ resultTable.getString(SAPConstants.RFC.BACKORDER.PENDING_KILOS));
				LOG.info(SAPConstants.RFC.BACKORDER.KILOS_READY + ": "
						+ resultTable.getString(SAPConstants.RFC.BACKORDER.KILOS_READY));
				LOG.info(SAPConstants.RFC.BACKORDER.KILOS_LOADING + ": "
						+ resultTable.getString(SAPConstants.RFC.BACKORDER.KILOS_LOADING));
				LOG.info(SAPConstants.RFC.BACKORDER.BALAANCE_KILOS + ": "
						+ resultTable.getString(SAPConstants.RFC.BACKORDER.BALAANCE_KILOS));

				backorderModel.setPcsOrder(resultTable.getString(SAPConstants.RFC.BACKORDER.UOM_LOADING));
				backorderModel.setKgsOrder(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDER.KGS_ORDER)));
				backorderModel.setPendingKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDER.PENDING_KILOS)));
				backorderModel.setReadyKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDER.KILOS_READY)));
				backorderModel.setLoadingKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDER.KILOS_LOADING)));
				backorderModel.setBalanceKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDER.BALAANCE_KILOS)));

				// PZAS

				String orderQty2 = "0.0";
				String pendingQty2 = "0.0";
				String readyQty2 = "0.0";
				String loadingQty2 = "0.0";
				String balance2 = "0.0";
				String uom2 = "";

				try
				{
					orderQty2 = resultTable.getString(SAPConstants.RFC.BACKORDER.ORDER_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDER.ORDER_QTY_2 + ": " + orderQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.ORDER_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setOrderQty2(Double.parseDouble(orderQty2));
				}

				try
				{
					pendingQty2 = resultTable.getString(SAPConstants.RFC.BACKORDER.PENDING_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDER.PENDING_QTY_2 + ": " + pendingQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.PENDING_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setPendingQty2(Double.parseDouble(pendingQty2));
				}

				try
				{
					readyQty2 = resultTable.getString(SAPConstants.RFC.BACKORDER.READY_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDER.READY_QTY_2 + ": " + readyQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.READY_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setReadyQty2(Double.parseDouble(readyQty2));
				}

				try
				{
					loadingQty2 = resultTable.getString(SAPConstants.RFC.BACKORDER.LOADING_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDER.LOADING_QTY_2 + ": " + loadingQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.LOADING_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setLoadingQty2(Double.parseDouble(loadingQty2));
				}

				try
				{
					balance2 = resultTable.getString(SAPConstants.RFC.BACKORDER.BALANCE_2);
					LOG.info(SAPConstants.RFC.BACKORDER.BALANCE_2 + ": " + balance2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.BALANCE_2 + " due to: " + e.getMessage());
				}
				finally
				{
					backorderModel.setBalance2(Double.parseDouble(balance2));
				}

				try
				{
					uom2 = resultTable.getString(SAPConstants.RFC.BACKORDER.UOM_2);
					LOG.info(SAPConstants.RFC.BACKORDER.UOM_2 + ": " + uom2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.UOM_2 + " due to: " + e.getMessage());
				}
				finally
				{
					backorderModel.setUom2(uom2);
				}

				// FT

				String orderQty3 = "0.0";
				String pendingQty3 = "0.0";
				String readyQty3 = "0.0";
				String loadingQty3 = "0.0";
				String balance3 = "0.0";
				String uom3 = "";

				try
				{
					orderQty3 = resultTable.getString(SAPConstants.RFC.BACKORDER.ORDER_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDER.ORDER_QTY_3 + ": " + orderQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.ORDER_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setOrderQty3(Double.parseDouble(orderQty3));
				}

				try
				{
					pendingQty3 = resultTable.getString(SAPConstants.RFC.BACKORDER.PENDING_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDER.PENDING_QTY_3 + ": " + pendingQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.PENDING_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setPendingQty3(Double.parseDouble(pendingQty3));
				}

				try
				{
					readyQty3 = resultTable.getString(SAPConstants.RFC.BACKORDER.READY_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDER.READY_QTY_3 + ": " + readyQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.READY_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setReadyQty3(Double.parseDouble(readyQty3));
				}

				try
				{
					loadingQty3 = resultTable.getString(SAPConstants.RFC.BACKORDER.LOADING_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDER.LOADING_QTY_3 + ": " + loadingQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.LOADING_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderModel.setLoadingQty3(Double.parseDouble(loadingQty3));
				}

				try
				{
					balance3 = resultTable.getString(SAPConstants.RFC.BACKORDER.BALANCE_3);
					LOG.info(SAPConstants.RFC.BACKORDER.BALANCE_3 + ": " + balance3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.BALANCE_3 + " due to: " + e.getMessage());
				}
				finally
				{
					backorderModel.setBalance3(Double.parseDouble(balance3));
				}

				try
				{
					uom3 = resultTable.getString(SAPConstants.RFC.BACKORDER.UOM_3);
					LOG.info(SAPConstants.RFC.BACKORDER.UOM_3 + ": " + uom3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDER.UOM_3 + " due to: " + e.getMessage());
				}
				finally
				{
					backorderModel.setUom3(uom3);
				}

				list.add(backorderModel);
			}

			// -NEORIS_CHANGE #Incidencia 171 JCVM 21/08/2014 Se agrega el ordenado de la lista despues de haber obtenido los datos
			// de SAP de acuerdo a la consulta realizada.
			Collections.sort(list, new Comparator<BackorderModel>()
			{

				public int compare(final BackorderModel object1, final BackorderModel object2)
				{
					return new Integer(object1.getCustomer().getCode()).compareTo(new Integer(object2.getCustomer().getCode()));
				}

			});

			if (searchParameters.getSortOrder() != null && searchParameters.getSortOrder().equals("desc"))
			{
				Collections.reverse(list);
			}

			//set the result pagination
			final Integer sapPagSize = searchParameters.getPageableData().getPageSize();

			final int currentPage = searchParameters.getPageableData().getCurrentPage();
			final SearchResult<BackorderModel> result = new SearchResultImpl<BackorderModel>(list.subList(currentPage * sapPagSize,
					((currentPage * sapPagSize) + sapPagSize) >= list.size() ? list.size() : (currentPage * sapPagSize) + sapPagSize),
					list.size(), sapPagSize, 0);

			pageData.setResults(result.getResult());

			final PaginationData paginationData = new PaginationData();
			final PageableData pageableData = searchParameters.getPageableData();

			paginationData.setPageSize(pageableData.getPageSize());
			paginationData.setSort(pageableData.getSort());
			paginationData.setTotalNumberOfResults(result.getTotalCount());
			paginationData
					.setNumberOfPages((int) Math.ceil(Float.parseFloat(String.valueOf(paginationData.getTotalNumberOfResults()))
							/ paginationData.getPageSize()));

			paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

			pageData.setPagination(paginationData);

		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to ZHSD_BACKORDER", ex);
		}

		return pageData;

	}

	//DETAIL

	public SearchPageData<BackorderDetailModel> getPagedBackorderDetail(final BackorderDetailSearchParameter searchParameters)
			throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		final SearchPageData<BackorderDetailModel> pageData = new SearchPageData<BackorderDetailModel>();

		try
		{
			LOG.info("SAP CONNECTION: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX 
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.BACKORDERDETAIL.BACKORDER_DETAIL, custDest);

			LOG.info("SAP CONNECTION: Successfull");

			B2BUnitModel b2bUnitModel = new B2BUnitModel();
			b2bUnitModel = b2bUnitService.getUnitForUid(searchParameters.getCustomer());

			LOG.info("Operation SAP : RFC - " + SAPConstants.RFC.BACKORDERDETAIL.BACKORDER_DETAIL);
			LOG.info("Input Parameter RFC");

			final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_ERDAT, "");
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_ERDAT2, "");

			if (searchParameters.getInitialDate() != null)
			{
				final Date startDate = searchParameters.getInitialDate();
				final String datetimeStr1 = format.format(startDate);
				final Date parseStartDate = format.parse(datetimeStr1);
				sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_ERDAT, parseStartDate);

				LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_ERDAT + " Value = " + parseStartDate);
			}

			if (searchParameters.getFinalDate() != null)
			{
				final Date finalDate = searchParameters.getFinalDate();
				final String datetimeStr2 = format.format(finalDate);
				final Date parseFinalDate = format.parse(datetimeStr2);
				sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_ERDAT2, parseFinalDate);

				LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_ERDAT2 + " Value = " + parseFinalDate);
			}

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_VKORG, searchParameters.getBaseStore());
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_KUNNR,
					SAPUtils.getFormattedUID(searchParameters.getCustomer()));
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_VBELN,
					SAPUtils.getFormattedUID(searchParameters.getOrder()));
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_BSTKD, searchParameters.getCustomerPO());
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.BACKORDERDETAIL.PI_KUNN2,
					SAPUtils.rellenaZeros(searchParameters.getAddress(), 10));

			LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_VKORG + " Value = " + searchParameters.getBaseStore());
			LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_KUNNR + " Value = "
					+ SAPUtils.getFormattedUID(searchParameters.getCustomer()));
			LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_VBELN + " Value = "
					+ SAPUtils.getFormattedUID(searchParameters.getOrder()));
			LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_BSTKD + " Value = " + searchParameters.getCustomerPO());
			LOG.info("Parameter " + SAPConstants.RFC.BACKORDERDETAIL.PI_KUNN2 + " Value = "
					+ SAPUtils.rellenaZeros(searchParameters.getAddress(), 10));

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.BACKORDERDETAIL.BACKORDER_DETAIL,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.BACKORDERDETAIL.BACKORDER_DETAIL,
					currentUser.getUid(), false, sessionActual));

			final List<BackorderDetailModel> list = new ArrayList<BackorderDetailModel>();

			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.BACKORDERDETAIL.OUTPUT_TABLE);

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final BackorderDetailModel backorderDetailModel = new BackorderDetailModel();

				if (b2bUnitModel != null)
				{
					backorderDetailModel.setCustomer(b2bUnitModel);
				}

				//BACKORDER DETAIL 
				backorderDetailModel.setPartNumber(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.KDMAT));
				backorderDetailModel.setDescription(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.POSTX));
				backorderDetailModel.setAddress(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.KUNNR));
				backorderDetailModel.setDeliveryDate(resultTable.getDate(SAPConstants.RFC.BACKORDERDETAIL.EDATU));
				backorderDetailModel.setOrder(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.VBELN));
				backorderDetailModel.setOrderDate(resultTable.getDate(SAPConstants.RFC.BACKORDERDETAIL.AUDAT));
				backorderDetailModel.setCustomerPO(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.BSTKD));
				backorderDetailModel.setPcsOrder(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.KWMENG));
				backorderDetailModel
						.setPendingKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.PENDI)));
				backorderDetailModel.setReadyKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.READY)));
				backorderDetailModel.setLoadingKilos(Double.parseDouble(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.LOADI)));
				
				String estatusEsp = null;
				String statusEng = null;

				if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("TO CONFIRM"))
				{
					estatusEsp = "Confirmar Fabricaci\u00F3n";
					statusEng = "Production TBC";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("CREDITS"))
				{
					estatusEsp = "En Cr\u00E9ditos";
					statusEng = "Credit Review";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("ORDER BLOCK"))
				{
					estatusEsp = "En Proceso";
					statusEng = "Processing";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("PROCESSING"))
				{
					estatusEsp = "En Proceso";
					statusEng = "Processing";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("RELEASED"))
				{
					estatusEsp = "En Proceso";
					statusEng = "Processing";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("PENDING"))
				{
					estatusEsp = "En Proceso";
					statusEng = "Processing";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("PROGRAMMED"))
				{
					estatusEsp = "En Proceso";
					statusEng = "Processing";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("INVOICED"))
				{
					estatusEsp = "Facturado";
					statusEng = "Invoiced";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("PLANED DELIVERY"))
				{
					estatusEsp = "Listo Embarque";
					statusEng = "Shipment Ready";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN).equalsIgnoreCase("ASSIGNED"))
				{
					estatusEsp = "Listo Embarque";
					statusEng = "Shipment Ready";
				}
				else if (resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT).equalsIgnoreCase("VENTAS RECHAZADO"))
				{
					estatusEsp = "Rechazado";
					statusEng = "Rejected";
				}
				else
				{
					estatusEsp = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT);
					statusEng = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN);
				}

				//backorderDetailModel.setEstatusEsp(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT));
				//backorderDetailModel.setEstatusEng(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.DESC_SHORT_EN));

				backorderDetailModel.setEstatusEsp(estatusEsp);
				backorderDetailModel.setEstatusEng(statusEng);

				final String plant = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.PLANT);
				if (StringUtils.isNotBlank(plant))
				{
					backorderDetailModel.setPlant(enumerationService.getEnumerationName(ProductLocation.valueOf("_" + plant),
							i18nService.getCurrentLocale()));
				}

				backorderDetailModel.setPartida(resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.POSNR));

				String orderQty = "0.0";
				String uomQty = "";
				String balance = "0.0";
				String uomBalance = "";

				try
				{
					orderQty = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY + ": " + orderQty);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY + " due to: "
							+ e.getMessage());
				}
				finally
				{

					backorderDetailModel.setOrderQty(Double.parseDouble(orderQty));
				}

				try
				{
					uomQty = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.UOM_ORDER_QTY);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.UOM_ORDER_QTY + ": " + uomQty);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.UOM_ORDER_QTY + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setUomOrderQty(uomQty);
				}

				try
				{
					balance = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.BALANCE);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.BALANCE + ": " + balance);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.BALANCE + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setBalance(Double.parseDouble(balance));
				}

				try
				{
					uomBalance = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.UOM_BALANCE);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.UOM_BALANCE + ": " + uomBalance);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.UOM_BALANCE + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setUomBalance(uomBalance);

				}

				// PZAS

				String orderQty2 = "0.0";
				String pendingQty2 = "0.0";
				String readyQty2 = "0.0";
				String loadingQty2 = "0.0";
				String balance2 = "0.0";
				String uom2 = "";

				try
				{
					orderQty2 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY_2 + ": " + orderQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setOrderQty2(Double.parseDouble(orderQty2));
				}

				try
				{
					pendingQty2 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.PENDING_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.PENDING_QTY_2 + ": " + pendingQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.PENDING_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setPendingQty2(Double.parseDouble(pendingQty2));
				}

				try
				{
					readyQty2 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.READY_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.READY_QTY_2 + ": " + readyQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.READY_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setReadyQty2(Double.parseDouble(readyQty2));
				}

				try
				{
					loadingQty2 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.LOADING_QTY_2);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.LOADING_QTY_2 + ": " + loadingQty2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.LOADING_QTY_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setLoadingQty2(Double.parseDouble(loadingQty2));
				}

				try
				{
					balance2 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.BALANCE_2);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.BALANCE_2 + ": " + balance2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.BALANCE_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setBalance2(Double.parseDouble(balance2));
				}

				try
				{
					uom2 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.UOM_2);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.UOM_2 + ": " + uom2);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.UOM_2 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setUom2(uom2);
				}

				// FT

				String orderQty3 = "0.0";
				String pendingQty3 = "0.0";
				String readyQty3 = "0.0";
				String loadingQty3 = "0.0";
				String balance3 = "0.0";
				String uom3 = "";

				try
				{
					orderQty3 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY_3 + ": " + orderQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.ORDER_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setOrderQty3(Double.parseDouble(orderQty3));
				}

				try
				{
					pendingQty3 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.PENDING_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.PENDING_QTY_3 + ": " + pendingQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.PENDING_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setPendingQty3(Double.parseDouble(pendingQty3));
				}

				try
				{
					readyQty3 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.READY_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.READY_QTY_3 + ": " + readyQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.READY_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setReadyQty3(Double.parseDouble(readyQty3));
				}

				try
				{
					loadingQty3 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.LOADING_QTY_3);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.LOADING_QTY_3 + ": " + loadingQty3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.LOADING_QTY_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setLoadingQty3(Double.parseDouble(loadingQty3));
				}

				try
				{
					balance3 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.BALANCE_3);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.BALANCE_3 + ": " + balance3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.BALANCE_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setBalance3(Double.parseDouble(balance3));
				}

				try
				{
					uom3 = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.UOM_3);
					LOG.info(SAPConstants.RFC.BACKORDERDETAIL.UOM_3 + ": " + uom3);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.UOM_3 + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setUom3(uom3);
				}

				String logistic = "";
				String location = "";

				try
				{
					logistic = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.LOGISTICT);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.LOGISTICT + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setLogistic(logistic);
				}

				try
				{
					location = resultTable.getString(SAPConstants.RFC.BACKORDERDETAIL.LOCATION);
				}
				catch (final Exception e)
				{
					LOG.error("Error while fetching SAP field: " + SAPConstants.RFC.BACKORDERDETAIL.LOCATION + " due to: "
							+ e.getMessage());
				}
				finally
				{
					backorderDetailModel.setLocation(location);
				}

				list.add(backorderDetailModel);
			}

			// -NEORIS_CHANGE #Incidencia 171 JCVM 21/08/2014 Se agrega el ordenado de la lista despues de haber obtenido los datos
			// de SAP de acuerdo a la consulta realizada.
			if (searchParameters.getSortBy() != null)
			{
				Collections.sort(list, new Comparator<BackorderDetailModel>()
				{
					public int compare(final BackorderDetailModel object1, final BackorderDetailModel object2)
					{
						int compareTo = 0;

						if (searchParameters.getSortBy().equals("address"))
						{
							try
							{
								compareTo = new Integer(object1.getAddress()).compareTo(new Integer(object2.getAddress()));
							}
							catch (final Exception ex)
							{
								LOG.warn("The Address value is not numeric, but the comparison will be made by string");
								compareTo = object1.getAddress().toString().compareTo(object2.getAddress().toString());
							}
						}
						else if (searchParameters.getSortBy().equals("deliveryDate"))
						{
							compareTo = object1.getDeliveryDate().compareTo(object2.getDeliveryDate());
						}
						else if (searchParameters.getSortBy().equals("orderDate"))
						{
							compareTo = object1.getOrderDate().compareTo(object2.getOrderDate());
						}
						return compareTo;
					}

				});

				if (searchParameters.getSortOrder() != null && searchParameters.getSortOrder().equals("desc"))
				{
					Collections.reverse(list);
				}
			}


			//set the result pagination
			final Integer sapPagSize = searchParameters.getPageableData().getPageSize();

			final int currentPage = searchParameters.getPageableData().getCurrentPage();
			final SearchResult<BackorderDetailModel> result = new SearchResultImpl<BackorderDetailModel>(list.subList(currentPage
					* sapPagSize, ((currentPage * sapPagSize) + sapPagSize) >= list.size() ? list.size() : (currentPage * sapPagSize)
					+ sapPagSize), list.size(), sapPagSize, 0);

			pageData.setResults(result.getResult());

			final PaginationData paginationData = new PaginationData();
			final PageableData pageableData = searchParameters.getPageableData();

			paginationData.setPageSize(pageableData.getPageSize());
			paginationData.setSort(pageableData.getSort());
			paginationData.setTotalNumberOfResults(result.getTotalCount());
			paginationData
					.setNumberOfPages((int) Math.ceil(Float.parseFloat(String.valueOf(paginationData.getTotalNumberOfResults()))
							/ paginationData.getPageSize()));

			paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

			pageData.setPagination(paginationData);


		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to ZHSD_BACKORDER_DETAIL", ex);
		}

		return pageData;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisBackorderService#getPagedBackorderDetailExcel(mx.neoris.core.services.
	 * BackorderSearchParameters)
	 */
	@Override
	public SearchPageData<BackorderModel> getPagedBackorderDetailExcel(final BackorderSearchParameters searchParameters)
			throws Exception
	{
		// YTODO Auto-generated method stub
		return null;
	}

}
