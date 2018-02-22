/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.impl.SearchResultImpl;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.model.BatchSearchModel;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.BatchSearchParameters;
import mx.neoris.core.services.NeorisBatchSearchService;
import mx.neoris.core.services.sap.impl.DefaultSAPNeorisDocumentSearchService;
import mx.neoris.core.util.SAPUtils;

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
public class DefaultSAPNeorisBatchSearchService implements NeorisBatchSearchService
{
	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisDocumentSearchService.class);

	@Override
	public SearchPageData<BatchSearchModel> getPagedBatch(final BatchSearchParameters searchParameters) throws Exception
	{
		//SAP Implementation
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		LOG.info("SAP CONNECTION: Initialize (Search Batch)");
		final SearchPageData<BatchSearchModel> pageData = new SearchPageData<BatchSearchModel>();

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_SEARCH, custDest);

			LOG.info("Operation SAP : RFC - " + SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_SEARCH);

			LOG.info("SAP CONNECTION: Successfull (Search Batch)");
			// input table parameter DOCUMENT SEARCH

			//fill up the fields
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.DOCUMENT_SEARCH.INPUT_TABLE);
			LOG.info("Table Parameter (Search Batch) : RFC - " + SAPConstants.RFC.DOCUMENT_SEARCH.INPUT_TABLE);
			LOG.info("Input Parameter RFC");

			//validate for All selection, "" means All selection
			if (searchParameters.getCustomer() != null && searchParameters.getCustomer().equals(""))
			{

				for (final String customer : searchParameters.getListCustomer())
				{
					inputTable.appendRow();
					inputTable.setValue(SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER, SAPUtils.getFormattedUID(customer));

					LOG.info("Parameter " + SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER + " Value = "
							+ SAPUtils.getFormattedUID(customer));
				}

			}
			else
			{
				inputTable.appendRow();
				inputTable.setValue(SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER,
						SAPUtils.getFormattedUID(searchParameters.getCustomer()));

				LOG.info("Parameter " + SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER + " Value = "
						+ SAPUtils.getFormattedUID(searchParameters.getCustomer()));

			}

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.DOCUMENT_SEARCH.BASE_STORE, searchParameters.getBaseStore());
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT, searchParameters.getReference());
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_TYPE,
					searchParameters.getBatchType());

			LOG.info("Parameter " + SAPConstants.RFC.DOCUMENT_SEARCH.BASE_STORE + " Value = " + searchParameters.getBaseStore());
			LOG.info("Parameter " + SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT + " Value = " + searchParameters.getReference());
			LOG.info("Parameter " + SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_TYPE + " Value = " + searchParameters.getBatchType());

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_SEARCH,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_SEARCH,
					currentUser.getUid(), false, sessionActual));

			// get the result table	
			final List<BatchSearchModel> list = new ArrayList<BatchSearchModel>();

			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.DOCUMENT_SEARCH.OUTPUT_TABLE);

			//for (int i = 0; i < resultTable.getNumRows(); i++)
			for (int i = 0; i < 1; i++)
			{
				resultTable.setRow(i);

				final BatchSearchModel batchSearchModel = new BatchSearchModel();

				//Info BATCH_SEARCH
				try
				{
					batchSearchModel.setCode(new Integer(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER_ID))
							.toString());
				}
				catch (final Exception ex)
				{
					batchSearchModel.setCode(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER_ID));
				}
				batchSearchModel.setName(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.CUSTOMER_NAME));
				batchSearchModel.setInvoice(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_INVOICE));
				batchSearchModel.setMtr(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_MTR));
				batchSearchModel.setRemission(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_REMISSION));
				batchSearchModel.setDebitNote(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_DEBIT_NOTE));
				batchSearchModel.setCreditNote(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_CREDIT_NOTE));
				batchSearchModel.setTolly(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_TALLY));
				batchSearchModel.setBoL(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_BoL));
				batchSearchModel.setPo(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_PO));
				batchSearchModel.setSo(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_SO));
				batchSearchModel.setQuote(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DOCUMENT_QUOTE));
				batchSearchModel.setDateInvoice(resultTable.getString(SAPConstants.RFC.DOCUMENT_SEARCH.DATE_INVOICE));

				list.add(batchSearchModel);

			}

			if (searchParameters.getSortBy() != null)
			{
				Collections.sort(list, new Comparator<BatchSearchModel>()
				{
					public int compare(final BatchSearchModel object1, final BatchSearchModel object2)
					{
						int compareTo = 0;

						if (searchParameters.getSortBy().equals("customer Id"))
						{
							compareTo = new Integer(object1.getCode()).compareTo(new Integer(object2.getCode()));
						}
						else if (searchParameters.getSortBy().equals("Customer Name"))
						{
							compareTo = object1.getName().compareTo(object2.getName());
						}
						else if (searchParameters.getSortBy().equals("invoice"))
						{
							if ((object1.getInvoice() != null && object1.getInvoice() != "")
									&& (object2.getInvoice() != null && object2.getInvoice() != ""))
							{
								compareTo = new Integer(object1.getInvoice()).compareTo(new Integer(object2.getInvoice()));
							}
							else if (object1.getInvoice() != null && object1.getInvoice() != "")
							{
								compareTo = 1;
							}
							else if (object2.getInvoice() != null && object2.getInvoice() != "")
							{
								compareTo = -1;
							}
						}
						else if (searchParameters.getSortBy().equals("mtr"))
						{
							if ((object1.getMtr() != null && object1.getMtr() != "")
									&& (object2.getMtr() != null && object2.getMtr() != ""))
							{
								compareTo = new Integer(object1.getMtr()).compareTo(new Integer(object2.getMtr()));
							}
							else if (object1.getMtr() != null && object1.getMtr() != "")
							{
								compareTo = 1;
							}
							else if (object2.getMtr() != null && object2.getMtr() != "")
							{
								compareTo = -1;
							}
						}
						else if (searchParameters.getSortBy().equals("remission"))
						{
							if ((object1.getRemission() != null && object1.getRemission() != "")
									&& (object2.getRemission() != null && object2.getRemission() != ""))
							{
								compareTo = new Integer(object1.getRemission()).compareTo(new Integer(object2.getRemission()));
							}
							else if (object1.getRemission() != null && object1.getRemission() != "")
							{
								compareTo = 1;
							}
							else if (object2.getRemission() != null && object2.getRemission() != "")
							{
								compareTo = -1;
							}
						}
						else if (searchParameters.getSortBy().equals("po"))
						{
							if ((object1.getPo() != null && object1.getPo() != "") && (object2.getPo() != null && object2.getPo() != ""))
							{
								compareTo = object1.getPo().compareTo(object2.getPo());
							}
							else if (object1.getPo() != null && object1.getPo() != "")
							{
								compareTo = 1;
							}
							else if (object2.getPo() != null && object2.getPo() != "")
							{
								compareTo = -1;
							}
						}
						else if (searchParameters.getSortBy().equals("so"))
						{
							if ((object1.getSo() != null && object1.getSo() != "") && (object2.getSo() != null && object2.getSo() != ""))
							{
								compareTo = new Integer(object1.getSo()).compareTo(new Integer(object2.getSo()));
							}
							else if (object1.getSo() != null && object1.getSo() != "")
							{
								compareTo = 1;
							}
							else if (object2.getSo() != null && object2.getSo() != "")
							{
								compareTo = -1;
							}
						}
						else if (searchParameters.getSortBy().equals("quote"))
						{
							if ((object1.getQuote() != null && object1.getQuote() != "")
									&& (object2.getQuote() != null && object2.getQuote() != ""))
							{
								compareTo = new Integer(object1.getQuote()).compareTo(new Integer(object2.getQuote()));
							}
							else if (object1.getQuote() != null && object1.getQuote() != "")
							{
								compareTo = 1;
							}
							else if (object2.getQuote() != null && object2.getQuote() != "")
							{
								compareTo = -1;
							}
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
			final SearchResult<BatchSearchModel> result = new SearchResultImpl<BatchSearchModel>(list.subList(currentPage
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
			LOG.error("Error while connecting to ZHSD_DOCUMENT_SEARCH, Search Batch", ex);
			throw new Exception("Error while connecting to ZHSD_DOCUMENT_SEARCH, Search Batch");

		}

		return pageData;
	}

}
