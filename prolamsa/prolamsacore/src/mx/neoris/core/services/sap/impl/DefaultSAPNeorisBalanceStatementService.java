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
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.impl.SearchResultImpl;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.model.BalanceStatementDetailModel;
import mx.neoris.core.model.BalanceStatementModel;
import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.services.NeorisBalanceStatementService;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author e-lacantu
 * 
 */

public class DefaultSAPNeorisBalanceStatementService implements NeorisBalanceStatementService
{

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisBalanceStatementService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisBalanceStatementService#getPagedInvoices(mx.neoris.core.services.
	 * BalanceStatementSearchParameters)
	 */
	@Override
	public SearchPageData<BalanceStatementDetailModel> getPagedBalanceStatement(
			final BalanceStatementSearchParameters searchParameters) throws Exception
	{
		//SAP Implementation
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		LOG.info("SAP CONNECTION: Initialize");
		final SearchPageData<BalanceStatementDetailModel> pageData = new SearchPageData<BalanceStatementDetailModel>();

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1, custDest);

			LOG.info("SAP CONNECTION: Successfull");
			// input table parameter 1.16 BALANCE STATEMENT LEVEL 1

			LOG.info("Operation SAP : RFC - " + SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1);

			//fill up the fields
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ACCOUNT_BALANCE.INPUT_TABLE);

			LOG.info("Table Parameter : RFC - " + SAPConstants.RFC.ACCOUNT_BALANCE.INPUT_TABLE);
			LOG.info("Input Parameter RFC");

			//validate for All selection, "" means All selection
			if (searchParameters.getCustomer() != null && searchParameters.getCustomer().equals(""))
			{

				for (final String customer : searchParameters.getListCustomer())
				{
					inputTable.appendRow();
					inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE, searchParameters.getBaseStore());
					inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER, SAPUtils.getFormattedUID(customer));

					LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE + " Value = "
							+ searchParameters.getBaseStore());
					LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER + " Value = "
							+ SAPUtils.getFormattedUID(customer));
				}

			}
			else
			{
				inputTable.appendRow();
				inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE, searchParameters.getBaseStore());
				inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER,
						SAPUtils.getFormattedUID(searchParameters.getCustomer()));

				LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE + " Value = " + searchParameters.getBaseStore());
				LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER + " Value = "
						+ SAPUtils.getFormattedUID(searchParameters.getCustomer()));
			}

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1,
					currentUser.getUid(), false, sessionActual));

			// get the result table	
			final List<BalanceStatementModel> list = new ArrayList<BalanceStatementModel>();
			final List<BalanceStatementDetailModel> listDetail = new ArrayList<BalanceStatementDetailModel>();
			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ACCOUNT_BALANCE.OUTPUT_TABLE);

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final BalanceStatementModel balanceStatementModel = new BalanceStatementModel();
				final BalanceStatementDetailModel balanceStatementDetailModel = new BalanceStatementDetailModel();

				B2BUnitModel b2bUnitModel = new B2BUnitModel();

				final String uid = resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER).substring(5);
				b2bUnitModel = b2bUnitService.getUnitForUid(uid);

				if (b2bUnitModel != null)
				{
					balanceStatementModel.setCustomer(b2bUnitModel);
				}

				//Info ACCOUNT BALANCE1
				balanceStatementModel.setCurrentAmount(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CURRENT)));
				balanceStatementModel.setBalance(Double.parseDouble(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.BALANCE)));
				balanceStatementModel
						.setPastDue(Double.parseDouble(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE)));
				balanceStatementModel.setCreditLimit(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_LIMIT)));

				list.add(balanceStatementModel);

				//Info ACCOUNT BALANCE2
				balanceStatementDetailModel.setCustomer(b2bUnitModel);
				balanceStatementDetailModel.setBalance(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.BALANCE)));
				balanceStatementDetailModel.setCurrent(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CURRENT)));
				balanceStatementDetailModel.setPastDue(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE)));
				balanceStatementDetailModel.setCreditLimit(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_LIMIT)));
				balanceStatementDetailModel.setCreditAvailable(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_AVAILABLE)));

				balanceStatementDetailModel.setOverdueCharge(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CHARGE_OVERDUE)));
				balanceStatementDetailModel.setOverdueCredit(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_OVERDUE)));
				//balanceStatementDetailModel.setOverdueInvoice(Double.parseDouble((resultTable
				//		.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_OVERDUE)) != "" ? resultTable
				//		.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_OVERDUE) : "0.00"));
				balanceStatementDetailModel.setOverdueInvoice(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_OVERDUE)));
				balanceStatementDetailModel.setOverduePayment(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAYMENT_OVERDUE)));

				balanceStatementDetailModel.setCurrentCharge(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CHARGE_CURRENT)));
				balanceStatementDetailModel.setCurrentCredit(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_CURRENT)));
				balanceStatementDetailModel.setCurrentInvoice(Double.parseDouble((resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_CURRENT) != "") ? resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_CURRENT) : "0.00"));
				balanceStatementDetailModel.setCurrentPayment(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAYMENT_CURRENT)));

				balanceStatementDetailModel.setPastDue1_30(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_1_30)));
				balanceStatementDetailModel.setPastDue31_60(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_31_60)));
				balanceStatementDetailModel.setPastDue61_90(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_61_90)));
				balanceStatementDetailModel.setPastDueMore90(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_MORE_90)));

				balanceStatementDetailModel.setSalVal(Double.parseDouble(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.SALVAL)));

				listDetail.add(balanceStatementDetailModel);

			}

			// NEORIS_CHANGE #Incidencia 17 JCVM 21/08/2014 Se agrega el ordenado de la lista despues de haber obtenido los datos
			// de SAP de acuerdo a la consulta realizada.
			if (searchParameters.getSortBy() != null)
			{
				Collections.sort(listDetail, new Comparator<BalanceStatementDetailModel>()
				{
					@Override
					public int compare(final BalanceStatementDetailModel object1, final BalanceStatementDetailModel object2)
					{
						int compareTo = 0;

						if (searchParameters.getSortBy().equals("customer"))
						{
							if (object1.getCustomer() != null && object2.getCustomer() != null)
							{
								compareTo = object1.getCustomer().getUid().compareTo(object2.getCustomer().getUid());
							}
						}
						else if (searchParameters.getSortBy().equals("CompanyName")
								|| searchParameters.getSortBy().equals("b2b.shortName"))
						{
							if (object1.getCustomer() != null && object2.getCustomer() != null)
							{
								compareTo = object1.getCustomer().getShortName().compareTo(object2.getCustomer().getShortName());
							}
						}
						else if (searchParameters.getSortBy().equals("balance"))
						{
							compareTo = object1.getBalance().compareTo(object2.getBalance());
						}

						return compareTo;
					}

				});
				if (searchParameters.getSortOrder() != null && searchParameters.getSortOrder().equals("desc"))
				{
					Collections.reverse(listDetail);
				}
			}


			//set the result pagination
			final Integer sapPagSize = searchParameters.getPageableData().getPageSize();

			final int currentPage = searchParameters.getPageableData().getCurrentPage();
			final SearchResult<BalanceStatementDetailModel> result = new SearchResultImpl<BalanceStatementDetailModel>(
					listDetail.subList(currentPage * sapPagSize,
							((currentPage * sapPagSize) + sapPagSize) >= listDetail.size() ? listDetail.size()
									: (currentPage * sapPagSize) + sapPagSize), listDetail.size(), sapPagSize, 0);

			pageData.setResults(result.getResult());

			final PaginationData paginationData = new PaginationData();
			final PageableData pageableData = searchParameters.getPageableData();

			paginationData.setPageSize(pageableData.getPageSize());
			paginationData.setSort(pageableData.getSort());
			paginationData.setTotalNumberOfResults(listDetail.size());
			paginationData
					.setNumberOfPages((int) Math.ceil(Float.parseFloat(String.valueOf(paginationData.getTotalNumberOfResults()))
							/ paginationData.getPageSize()));

			paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

			pageData.setPagination(paginationData);



		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to ZHSD_ACCOUNT_BALANCE1 SAP", ex);
		}

		return pageData;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisBalanceStatementService#getBalanceStatementDetail(java.lang.String)
	 */
	@Override
	public SearchResult<BalanceStatementDetailModel> getBalanceStatementDetail(
			final BalanceStatementSearchParameters searchParameters, final Integer row) throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		//SAP Implementation

		LOG.info("SAP CONNECTION: Initialize");
		final SearchPageData<BalanceStatementDetailModel> pageData = new SearchPageData<BalanceStatementDetailModel>();
		SearchResult<BalanceStatementDetailModel> result = null;

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1, custDest);

			LOG.info("SAP CONNECTION: Successfull");
			// input table parameter 1.16 BALANCE STATEMENT LEVEL 1

			//fill up the fields
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ACCOUNT_BALANCE.INPUT_TABLE);


			//validate for All selection, "" means All selection
			if (searchParameters.getCustomer() != null && searchParameters.getCustomer().equals(""))
			{

				for (final String customer : searchParameters.getListCustomer())
				{
					inputTable.appendRow();
					inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE, searchParameters.getBaseStore());
					inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER, SAPUtils.getFormattedUID(customer));
				}

			}
			else
			{
				inputTable.appendRow();
				inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE, searchParameters.getBaseStore());
				inputTable.setValue(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER,
						SAPUtils.getFormattedUID(searchParameters.getCustomer()));
			}


			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE1,
					currentUser.getUid(), false, sessionActual));

			// get the result table	
			final List<BalanceStatementModel> list = new ArrayList<BalanceStatementModel>();
			final List<BalanceStatementDetailModel> listDetail = new ArrayList<BalanceStatementDetailModel>();
			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ACCOUNT_BALANCE.OUTPUT_TABLE);


			resultTable.setRow(row);

			final BalanceStatementModel balanceStatementModel = new BalanceStatementModel();
			final BalanceStatementDetailModel balanceStatementDetailModel = new BalanceStatementDetailModel();

			B2BUnitModel b2bUnitModel = new B2BUnitModel();

			final String uid = resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER).substring(5);
			b2bUnitModel = b2bUnitService.getUnitForUid(uid);

			if (b2bUnitModel != null)
			{
				balanceStatementModel.setCustomer(b2bUnitModel);
			}


			//Info ACCOUNT BALANCE2
			balanceStatementDetailModel.setCustomer(b2bUnitModel);
			balanceStatementDetailModel.setBalance(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.BALANCE)));
			balanceStatementDetailModel.setCurrent(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CURRENT)));
			balanceStatementDetailModel.setPastDue(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE)));
			balanceStatementDetailModel.setCreditLimit(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_LIMIT)));
			balanceStatementDetailModel.setCreditAvailable(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_AVAILABLE)));

			balanceStatementDetailModel.setOverdueCharge(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CHARGE_OVERDUE)));
			balanceStatementDetailModel.setOverdueCredit(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_OVERDUE)));
			balanceStatementDetailModel.setOverdueInvoice(Double.parseDouble((resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_OVERDUE)) != "" ? resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_OVERDUE) : "0.00"));
			balanceStatementDetailModel.setOverduePayment(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAYMENT_OVERDUE)));
			balanceStatementDetailModel.setOverdueAmount(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE)));

			balanceStatementDetailModel.setCurrentCharge(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CHARGE_CURRENT)));
			balanceStatementDetailModel.setCurrentCredit(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CREDIT_CURRENT)));
			balanceStatementDetailModel.setCurrentInvoice(Double.parseDouble((resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_CURRENT) != "") ? resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_CURRENT) : "0.00"));
			balanceStatementDetailModel.setCurrentPayment(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAYMENT_CURRENT)));
			balanceStatementDetailModel.setCurrentBalance(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CURRENT)));

			balanceStatementDetailModel.setPastDue1_30(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_1_30)));
			balanceStatementDetailModel.setPastDue31_60(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_31_60)));
			balanceStatementDetailModel.setPastDue61_90(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_61_90)));
			balanceStatementDetailModel.setPastDueMore90(Double.parseDouble(resultTable
					.getString(SAPConstants.RFC.ACCOUNT_BALANCE.PAST_DUE_MORE_90)));

			balanceStatementDetailModel
					.setSalVal(Double.parseDouble(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.SALVAL)));

			listDetail.add(balanceStatementDetailModel);


			result = new SearchResultImpl<BalanceStatementDetailModel>(listDetail, listDetail.size(), 5, 0);

		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to ZHSD_ACCOUNT_BALANCE1 SAP", ex);
		}

		return result;

	}


	public SearchPageData<InvoiceModel> getPagedBalanceStatementInvoices(final InvoiceSearchParameters searchParameters)
			throws Exception
	{
		//SAP Implementation
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		LOG.info("SAP CONNECTION: Initialize");
		final SearchPageData<InvoiceModel> pageData = new SearchPageData<InvoiceModel>();

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE2, custDest);

			LOG.info("SAP CONNECTION: Successfull");
			// input table parameter 1.17 BALANCE STATEMENT LEVEL 2
			LOG.info("Operation SAP : RFC - " + SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE2);

			//fill up the fields
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE_INPUT,
					searchParameters.getBaseStore());
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER_INPUT,
					SAPUtils.getFormattedUID(searchParameters.getCustomer()));

			//Agregado por Christian 26082014
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.ACCOUNT_BALANCE.ESTATUS_INPUT,
					searchParameters.getTypeInvoice());
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.ACCOUNT_BALANCE.TIPO_DOCUMENTO_INPUT,
					searchParameters.getTypeDocto());

			LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.BASE_STORE_INPUT + " Value = "
					+ searchParameters.getBaseStore());
			LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER_INPUT + " Value = "
					+ SAPUtils.getFormattedUID(searchParameters.getCustomer()));
			LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.ESTATUS_INPUT + " Value = " + searchParameters.getTypeInvoice());
			LOG.info("Parameter " + SAPConstants.RFC.ACCOUNT_BALANCE.TIPO_DOCUMENTO_INPUT + " Value = "
					+ searchParameters.getTypeDocto());

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE2,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ACCOUNT_BALANCE.ACCOUNT_BALANCE2,
					currentUser.getUid(), false, sessionActual));

			// get the result table	
			final List<InvoiceModel> list = new ArrayList<InvoiceModel>();
			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ACCOUNT_BALANCE.OUTPUT_TABLE);

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final InvoiceModel invoiceModel = new InvoiceModel();

				B2BUnitModel b2bUnitModel = new B2BUnitModel();

				final String uid = resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.CUSTOMER).substring(5);
				b2bUnitModel = b2bUnitService.getUnitForUid(uid);

				if (b2bUnitModel != null)
				{
					invoiceModel.setCustomer(b2bUnitModel);
				}

				//Info ACCOUNT BALANCE2
				invoiceModel.setCustomer(b2bUnitModel);

				invoiceModel.setNumber(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_NUMBER));

				invoiceModel.setInvoiceDate(SAPUtils.convertSAPtoHybrisDate(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_DATE)));

				invoiceModel.setDueDate(SAPUtils.convertSAPtoHybrisDate(resultTable
						.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_DUE_DATE)));

				invoiceModel.setOriginalAmount(Double.parseDouble(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.NET_VALUE)));

				invoiceModel.setBalanceAmount(Double.parseDouble(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.BALANCE)));


				list.add(invoiceModel);

			}


			//set the result pagination
			final Integer sapPagSize = searchParameters.getPageableData().getPageSize();

			final int currentPage = searchParameters.getPageableData().getCurrentPage();
			final SearchResult<InvoiceModel> result = new SearchResultImpl<InvoiceModel>(list.subList(currentPage
					* ((sapPagSize > list.size()) ? list.size() : sapPagSize),
					((currentPage * sapPagSize) + sapPagSize) >= list.size() ? list.size() : (currentPage * sapPagSize) + sapPagSize),
					list.size(), sapPagSize, 0);

			pageData.setResults(result.getResult());

			final PaginationData paginationData = new PaginationData();
			final PageableData pageableData = searchParameters.getPageableData();

			paginationData.setPageSize(pageableData.getPageSize());
			paginationData.setSort(pageableData.getSort());
			paginationData.setTotalNumberOfResults(list.size());
			paginationData
					.setNumberOfPages((int) Math.ceil(Float.parseFloat(String.valueOf(paginationData.getTotalNumberOfResults()))
							/ paginationData.getPageSize()));

			paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

			pageData.setPagination(paginationData);

		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to ZHSD_ACCOUNT_BALANCE2 SAP", ex);
		}

		return pageData;
	}

}
