package mx.neoris.core.services.sap.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.impl.SearchResultImpl;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.services.NeorisInvoiceService;
//import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author lacantu
 * 
 */
public class DefaultSAPNeorisInvoiceService implements NeorisInvoiceService
{

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "dateFormatter_yyyyMMdd")
	private SimpleDateFormat dateFormatter;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "userService")
	private UserService userService;

	//	@Resource(name = "neorisEmailNotifierSAP")
	//	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	private String temporaryDirectory = "d:\\tmp\\";

	private static final String PROLAMSA_INCIDENT_USER = "incident_user";

	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisInvoiceService.class);

	public SearchPageData<InvoiceModel> getPagedInvoices(final InvoiceSearchParameters searchParameters) throws Exception
	{
		//SAP Implementation					
		return new SearchPageData<InvoiceModel>();
	}

	public SearchPageData<InvoiceModel> getPagedInvoicesByRange(final InvoiceSearchParameters searchParameters, final String range)
			throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		//SAP Implementation
		LOG.info("SAP CONNECTION: Initialize");
		final SearchPageData<InvoiceModel> pageData = new SearchPageData<InvoiceModel>();

		final String[] intervalRange = range.split("_");
		Boolean addToList = Boolean.TRUE;

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
				if (intervalRange.length > 1)
				{
					final long different = getDayDifferent(SAPUtils.convertSAPtoHybrisDate(resultTable
							.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_DUE_DATE)));

					//LOG.info("Dias de diferencia: " + different);

					if ("*".equals(intervalRange[1]))
					{
						if (different >= Long.parseLong(intervalRange[0]))
						{
							addToList = Boolean.TRUE;
						}
						else
						{
							addToList = Boolean.FALSE;
						}
					}
					else
					{
						if (different >= Long.parseLong(intervalRange[0]) && different <= Long.parseLong(intervalRange[1]))
						{
							addToList = Boolean.TRUE;
						}
						else
						{
							addToList = Boolean.FALSE;
						}
					}
				}
				else
				{

					final long different = getDayDifferent(SAPUtils.convertSAPtoHybrisDate(resultTable
							.getString(SAPConstants.RFC.ACCOUNT_BALANCE.INVOICE_DUE_DATE)));

					LOG.info("Dias de diferencia: " + different);

					if (different <= Long.parseLong(intervalRange[0]))
					{
						addToList = Boolean.TRUE;
					}
					else
					{
						addToList = Boolean.FALSE;
					}
				}

				if (addToList)
				{
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

					invoiceModel.setOriginalAmount(Double.parseDouble(resultTable
							.getString(SAPConstants.RFC.ACCOUNT_BALANCE.NET_VALUE)));

					invoiceModel.setBalanceAmount(Double.parseDouble(resultTable.getString(SAPConstants.RFC.ACCOUNT_BALANCE.BALANCE)));


					list.add(invoiceModel);
				}


			}

			//set the result
			final SearchResult<InvoiceModel> result = new SearchResultImpl<InvoiceModel>(list, list.size(), 5, 0);

			pageData.setResults(result.getResult());

			final PaginationData paginationData = new PaginationData();
			final PageableData pageableData = new PageableData();
			pageableData.setCurrentPage(0);
			pageableData.setPageSize(5);
			pageableData.setSort(searchParameters.getSortBy());

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
			LOG.error("Error while connecting to ZHSD_ACCOUNT_BALANCE2 SAP", ex);
		}

		return pageData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisInvoiceService#getInvoicesByB2BUnit(java.util.List)
	 */
	@Override
	public SearchPageData<InvoiceModel> getInvoicesByB2BUnit(final List<B2BUnitData> listB2BUnitData) throws Exception
	{
		//SAP Implementation

		final SearchPageData<InvoiceModel> pageData = new SearchPageData<InvoiceModel>();
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		final UserGroupModel prolamsaFacetAutoselectionGroup = userService.getUserGroupForUID(PROLAMSA_INCIDENT_USER);

		if (userService.isMemberOfGroup(currentUser, prolamsaFacetAutoselectionGroup))
		{
			LOG.info("El usuario " + currentUser.getUid() + " tiene rol incident_user, no se buscaran las ultimas facturas");
			return pageData;
		}

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

			final JCoFunction sapFunc = sapConnection.createFunctionCustom(SAPConstants.RFC.INVOICE.HOMEPAGE_INVOICE, custDest);

			LOG.info("SAP CONNECTION: Successfull");
			// input parameter header 1.18 HOMEPAGE_INVOICE	

			LOG.info("RFC : " + SAPConstants.RFC.INVOICE.HOMEPAGE_INVOICE);
			LOG.info("-- INPUT PARAMETERS --");
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.INVOICE.CCODE,
					baseStoreService.getCurrentBaseStore().getUid());
			LOG.info(SAPConstants.RFC.INVOICE.CCODE + ": " + baseStoreService.getCurrentBaseStore().getUid());

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.INVOICE.CREATE_DATE, "");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.INVOICE.ROWS, SAPConstants.RFC.INVOICE.NO_ROWS);
			LOG.info(SAPConstants.RFC.INVOICE.ROWS + ": " + SAPConstants.RFC.INVOICE.NO_ROWS);

			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.INVOICE.INPUT_TABLE);
			//fill up the fields

			//get the list of B2B units
			for (final B2BUnitData listSAP : listB2BUnitData)
			{
				inputTable.appendRow();

				inputTable.setValue(SAPConstants.RFC.INVOICE.CUSTOMER, SAPUtils.getFormattedUID(listSAP.getUid()));
				LOG.info(SAPConstants.RFC.INVOICE.CUSTOMER + ": " + SAPUtils.getFormattedUID(listSAP.getUid()));

			}

			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.INVOICE.HOMEPAGE_INVOICE, currentUser.getUid(),
					true, sessionActual));
			sapConnection.executeFunctionCustom(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.INVOICE.HOMEPAGE_INVOICE, currentUser.getUid(),
					false, sessionActual));

			// get the result table	
			final List<InvoiceModel> list = new ArrayList<InvoiceModel>();
			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.INVOICE.RESULT_TABLE);

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final InvoiceModel invoiceModel = new InvoiceModel();
				final B2BUnitModel b2bUnitModel = new B2BUnitModel();

				final String uid = resultTable.getString(SAPConstants.RFC.INVOICE.CUSTOMER).substring(5);
				b2bUnitModel.setUid(uid);

				final B2BUnitModel b2bModel = b2bUnitService.getUnitForUid(uid);

				if (b2bModel != null)
				{
					b2bUnitModel.setName(b2bModel.getName());
					b2bUnitModel.setShortName(b2bModel.getShortName());
				}

				invoiceModel.setCustomer(b2bUnitModel);
				invoiceModel.setNumber(resultTable.getString(SAPConstants.RFC.INVOICE.INVOICE_NUMBER));
				invoiceModel.setInvoiceDate(SAPUtils.convertSAPtoHybrisDate(resultTable
						.getString(SAPConstants.RFC.INVOICE.INVOICE_DATE)));
				invoiceModel.setDueDate(new Date());
				invoiceModel.setOriginalAmount(0.00);
				invoiceModel.setBalanceAmount(0.00);
				invoiceModel.setDocumentNumber("");
				invoiceModel.setDocumentDescription("");
				invoiceModel.setCurrency("");
				invoiceModel.setCredits(0.00);
				invoiceModel.setDebits(0.00);

				list.add(invoiceModel);

			}

			//set the result
			final SearchResult<InvoiceModel> result = new SearchResultImpl<InvoiceModel>(list, list.size(), -1, 0);

			pageData.setResults(result.getResult());

			final PaginationData paginationData = new PaginationData();
			final PageableData pageableData = new PageableData();
			pageableData.setCurrentPage(0);
			pageableData.setPageSize(10);
			pageableData.setSort(null);

			paginationData.setPageSize(pageableData.getPageSize());
			paginationData.setSort(pageableData.getSort());
			paginationData.setTotalNumberOfResults(result.getTotalCount());
			paginationData
					.setNumberOfPages((int) Math.ceil(paginationData.getTotalNumberOfResults() / paginationData.getPageSize()));
			paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

			pageData.setPagination(paginationData);

		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to SAP", ex);
		}

		return pageData;

	}


	@Override
	public String getPDFDocument(final String invoice, final String customer) throws Exception
	{
		//SAP Implementation
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		String urlPDF = "";
		LOG.info("SAP CONNECTION: Initialize");

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.PDF.DOCUMENT_PDF, custDest);

			LOG.info("SAP CONNECTION: Successfull");
			// input parameter header 1.13 DOCUMENT PDF	

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.CLIENTE, customer);
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.DOCUMENTO, invoice);
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.TIPO_DOCTO, "F");
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.RUTA, getTemporaryDirectory());
			//sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.RUTA,
			//		"C:\\Users\\christian.loredo\\Desktop\\New folder\\");

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			//execute
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PDF.DOCUMENT_PDF, currentUser.getUid(), true,
					sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PDF.DOCUMENT_PDF, currentUser.getUid(), false,
					sessionActual));

			// get the export result
			urlPDF = sapFunc.getExportParameterList().getValue(SAPConstants.RFC.PDF.EXPORT_ARCHIVO).toString();

			LOG.info("SAP EXECUTION: Successfull");

		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to DOCUMENT_PDF SAP", ex);
		}

		return urlPDF;

	}

	/**
	 * @return the temporaryDirectory
	 */
	public String getTemporaryDirectory()
	{
		return temporaryDirectory;
	}

	/**
	 * @param temporaryDirectory
	 *           the temporaryDirectory to set
	 */
	public void setTemporaryDirectory(final String temporaryDirectory)
	{
		this.temporaryDirectory = temporaryDirectory;
	}

	private long getDayDifferent(final Date fFinal)
	{
		long different = 0;
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
		final Date today = new Date(); //Fecha de hoy 

		different = (today.getTime() - fFinal.getTime()) / MILLSECS_PER_DAY;

		return different;
	}


}
