/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.util.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.util.StreamUtils;
import mx.neoris.facades.NeorisBalanceStatementFacade;
import mx.neoris.facades.NeorisInvoiceFacade;
import mx.neoris.facades.balancestatement.data.BalanceStatementData;
import mx.neoris.facades.balancestatement.data.BalanceStatementDetailData;
import mx.neoris.facades.invoice.data.InvoiceData;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.Breadcrumb;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.InvoiceSearchForm;
import mx.neoris.storefront.jasper.JasperDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jxls.transformer.XLSTransformer;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for Invoice page.
 * 
 * @author fdeutsch
 * @version $Revision: 1.0 $
 */
@Controller
@Scope("tenant")
@RequestMapping("/invoice")
public class InvoicePageController extends AbstractSearchPageController
{
	protected static final Logger LOG = Logger.getLogger(InvoicePageController.class);

	// CMS Pages
	private static final String INVOICE_LIST_CMS_PAGE = "InvoiceListPage";
	private static final String INVOICE_BY_CUSTOMER_CMS_PAGE = "InvoiceByCustomerPage";

	// BreadCrum Resources
	private static final String INVOICE_LIST_BREADCRUMB = "header.link.invoice.list";
	
	private static final String INVOICE_BY_CUSTOMER_BREADCRUMB = "header.link.invoice.ByCustomer";

	@Resource(name = "neorisInvoiceFacade")
	private NeorisInvoiceFacade invoiceFacade;

	@Resource(name = "neorisBalanceStatementFacade")
	private NeorisBalanceStatementFacade balanceStatementFacade;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "dateFormatter_MM-dd-yyyy")
	private SimpleDateFormat dateFormatter;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	private static final String HTTP_PREFIX = "http://";
	private static final String PROTOCOL = ":9001";
	private static final String SITE_THEME_PREFIX = "theme-";
	private static final String STATIC_CONTENT_HOST_CONFIG_KEY = "apache.server.staticContent.host";

	public InvoicePageController()
	{
		super();
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showInvoiceListPage(
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@Valid final InvoiceSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		// add breadcrumb info
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(INVOICE_LIST_BREADCRUMB, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

	    ShowMode showMode = ShowMode.Page;
			
		// create PageableData
		Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

		if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
		{
			sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
		}
		
		final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);

		
		String baseStore = neorisFacade.getCurrentBaseStore().getName();
		searchForm.setBaseStore(baseStore);

		// save search form on model
		model.addAttribute("invoiceSearchForm", searchForm);
		
		// create and fill the search parameters
		InvoiceSearchParameters searchParameters = new InvoiceSearchParameters();
		searchParameters.setPageableData(pageableData);
		searchParameters.setNumber(searchForm.getNumber());
		searchParameters.setBaseStore(baseStore);

		// process dates
		String param = searchForm.getInitialDate();
		if (param != null && param.length() > 0)
			try
			{
				searchParameters.setInitialDate(dateFormatter.parse(param));
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
		param = searchForm.getFinalDate();
		if (param != null && param.length() > 0)
			try
			{
				searchParameters.setFinalDate(dateFormatter.parse(param));
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}

		// get and set sort information
		String sortCode = searchForm.getSort();
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
		{
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}

		// add sorts to searchPageData
		String[] sortProps = new String[] { "number", "dueDate" };
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "dueDate-desc" : sortCode, "{0}-(asc)", "{0}-(desc)");
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<InvoiceData> searchPageData = new SearchPageData<InvoiceData>();
		searchPageData.setPagination(new PaginationData());

		// if export
		String exportTo = request.getParameter(ControllerConstants.Export.ExportParam);

		// if sort set, means search has been clicked, do the search now
		if (sortCode != null)
		{
			try
			{
				searchParameters.validateInformation();
				
				// retrieve all elements
				if (!StringUtils.isBlank(exportTo))
				{
					searchParameters.getPageableData().setCurrentPage(0);
					searchParameters.getPageableData().setPageSize(Integer.MAX_VALUE);
				}

				searchPageData = invoiceFacade.getPagedInvoices(searchParameters);
			}
			catch (NeorisValidationError exVal)
			{
				// if validation error
				GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve invoices", ex);

				GlobalMessages.addErrorMessage(model, "invoice.list.error");
			}
			
			// export PDF
			if (ControllerConstants.Export.PDF.equalsIgnoreCase(exportTo))
			{
				pdfExportInvoiceListPage(request,response, searchPageData.getResults());
				return null;
			}

			// export XLS
			if (ControllerConstants.Export.XLS.equalsIgnoreCase(exportTo))
			{
				excelExportInvoiceListPage(response, searchPageData.getResults());
				return null;
			}

		}
		
		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "flase");

		storeCmsPageInModel(model, getContentPageForLabelOrId(INVOICE_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(INVOICE_LIST_CMS_PAGE));

		return getViewForPage(model);
	}

	@RequestMapping(value = "/by-customer", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showInvoiceByCustomerPage(
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@RequestParam(value = "customer") final String customer,
		@RequestParam(value = "typeInvoice") final String typeInvoice,
		@RequestParam(value = "typeDocto") final String typeDocto,
		@Valid final InvoiceSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
									
		ShowMode showMode = ShowMode.Page;		

		// create PageableData
		Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

		if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
		{
			sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
		}
		
		final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);
			
		
		// create and fill the search parameters
		InvoiceSearchParameters searchParameters = new InvoiceSearchParameters();
		//set the base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());		
		searchParameters.setBaseStore(searchForm.getBaseStore());
		searchParameters.setPageableData(pageableData);
		
		//Agregado por Christian Loredo 26082014
		searchParameters.setTypeInvoice(typeInvoice);
		searchParameters.setTypeDocto(typeDocto);
											
		if (searchForm.getCustomer() != null)
			searchParameters.setCustomer(searchForm.getCustomer());
		
		B2BUnitModel b2bunit = neorisFacade.getB2BUnitWithUid(customer);

		// save search form on model
		model.addAttribute("b2bunit", b2bunit);	
		model.addAttribute("typeInvoice", typeInvoice);
		model.addAttribute("typeDocto", typeDocto);
		
		SearchPageData<InvoiceData> searchPageData = new SearchPageData<InvoiceData>();	
		
		// if export
		String exportTo = request.getParameter(ControllerConstants.Export.ExportParam);

		// retrieve all elements
		if (StringUtils.isNotBlank(exportTo))
		{
			searchParameters.getPageableData().setCurrentPage(0);
			searchParameters.getPageableData().setPageSize(Integer.MAX_VALUE);
		}

		// get and set sort information
		String sortCode = searchForm.getSort();
		if (sortCode==null) 
		 	sortCode="number-asc";		
		
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
		{
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}

		try
		{		 					
			searchPageData = balanceStatementFacade.getPagedBalanceStatementInvoices(searchParameters);
		}		
		catch (Exception ex)
		{
			// if other type of error
			LOG.error("Unable to retrieve Balance Statements", ex);

			GlobalMessages.addErrorMessage(model, "balanceStatement.list.error");
		}
		
		//add the search data to the model
		model.addAttribute("searchPageData", searchPageData);
		
		// add breadcrumb info
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(INVOICE_BY_CUSTOMER_BREADCRUMB, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		// save search form on model
		model.addAttribute("invoiceSearchForm", searchForm);

		// create and fill the search parameters
		//InvoiceSearchParameters searchParameters = new InvoiceSearchParameters();
		searchParameters.setPageableData(pageableData);
		searchParameters.setCustomer(searchForm.getCustomer());
		
		// add sorts to searchPageData
		String[] sortProps = new String[] {"invoice.sortby.number","invoice.sortby.invoiceDate","invoice.sortby.dueDate","invoice.sortby.balanceAmount"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "invoice.sortby.number-asc" : "invoice.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<InvoiceData> searchPageData0 = new SearchPageData<InvoiceData>();
		SearchPageData<InvoiceData> searchPageData1_30 = new SearchPageData<InvoiceData>();
		SearchPageData<InvoiceData> searchPageData31_60 = new SearchPageData<InvoiceData>();
		BalanceStatementDetailData searchDataDetail = new BalanceStatementDetailData();
		//searchPageData.setPagination(new PaginationData());


		// if sort set, means search has been clicked, do the search now
		if (sortCode != null)
		{
			// export PDF SAP Invoice
			if (ControllerConstants.Export.PDF_SAP.equalsIgnoreCase(exportTo))
			{
				pdfExportInvoiceSAPPage(request,response, searchPageData.getResults().get(0));
				return null;
			}
			
			// export PDF By Customer
			if (ControllerConstants.Export.PDF_BY_CUSTOMER.equalsIgnoreCase(exportTo))
			{
				
				pdfExportInvoiceByCustomerPage(request, response, searchPageData.getResults(), customer);
				return null;
			}
			
			// export XLS By Customer
			if (ControllerConstants.Export.XLS_BY_CUSTOMER.equalsIgnoreCase(exportTo))
			{
				try
				{
					BalanceStatementSearchParameters searchParametersDetail = new BalanceStatementSearchParameters();
					searchParametersDetail.setBaseStore(searchForm.getBaseStore());

					//validate for ALL selection, "" means All customer
					if (searchForm.getCustomer()!= null && searchForm.getCustomer().equals("")){
						List<String> list = new ArrayList<String>();
						for(B2BUnitData b2bUnitData : getFormattedB2BUnits()){
							list.add(b2bUnitData.getUid());
						}
						searchParametersDetail.setListCustomer(list);
					}

					if (searchForm.getCustomer() != null)
						searchParameters.setCustomer(searchForm.getCustomer());
					searchDataDetail = balanceStatementFacade.getBalanceStatementDetail(searchParametersDetail, 0);
				
								
					excelExportInvoiceByCustomerPage(response, searchPageData.getResults(),customer);
					
					
				} catch (Exception ex)
				{
					// if other type of error
					LOG.error("Unable to retrieve Invoice", ex);

						GlobalMessages.addErrorMessage(model, "Invoice.list.error");
				}
				return null;
			}
			
			
			// export PDF
			if (ControllerConstants.Export.PDF.equalsIgnoreCase(exportTo))
			{
				pdfExportInvoiceListPage(request, response, searchPageData.getResults());
				return null;
			}

			// export XLS
			if (ControllerConstants.Export.XLS.equalsIgnoreCase(exportTo))
			{
				excelExportInvoiceListPage(response, searchPageData.getResults());
				return null;
			}

		}
	
		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "flase");

		storeCmsPageInModel(model, getContentPageForLabelOrId(INVOICE_BY_CUSTOMER_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(INVOICE_BY_CUSTOMER_CMS_PAGE));

		return getViewForPage(model);
	}
	
	
	@RequestMapping(value = "/by-invoice", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showInvoicePage(
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@RequestParam(value = "invoice") final String invoice,
		@Valid final InvoiceSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		
		ShowMode showMode = ShowMode.Page;	

		// create PageableData
		Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

		if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
		{
			sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
		}
		
		final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);
		
		InvoiceData searchData = new InvoiceData();
							
		//add the search data to the model
		model.addAttribute("searchData", searchData);
			
		// save search form on model
		model.addAttribute("invoiceSearchForm", searchForm);

		// create and fill the search parameters
		InvoiceSearchParameters searchParameters = new InvoiceSearchParameters();		
		searchParameters.setNumber(invoice);
	

		// create and fill the search parameters		
		searchParameters.setPageableData(pageableData);		
		searchParameters.getPageableData().setCurrentPage(0);
		searchParameters.getPageableData().setPageSize(Integer.MAX_VALUE);
		
		// get and set sort information
		String sortCode = searchForm.getSort();
		if (sortCode==null) 
		 	sortCode="number-asc";		
		
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
		{
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}
						
		try
		{
 			// retrieve all elements			
			searchData = invoiceFacade.getPagedInvoices(searchParameters).getResults().get(0);
		}
		catch (NeorisValidationError exVal)
		{
			// if validation error
			GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
		}
		catch (Exception ex)
		{
		// if other type of error
		LOG.error("Unable to retrieve invoices", ex);

		GlobalMessages.addErrorMessage(model, "invoice.list.error");
		}
					
		pdfExportInvoiceSAPPage(request,response, searchData);
		return null;
						
	}
	
	
	@RequestMapping(value = "/by-SAP-invoice", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showSAPInvoicePage(		
		@RequestParam(value = "invoice") final String invoice,
		@RequestParam(value = "customer") final String customer,
		final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		
       String urlPDF = "";
       
       try {
			urlPDF = invoiceFacade.getPDFDocument(invoice, customer);
	   } catch (Exception e) {			
			e.printStackTrace();
	   }
       
		return "redirect:" + urlPDF;
						
	}
	
	public void excelExportInvoiceListPage(final HttpServletResponse response, List<InvoiceData> col) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File xlsFile = null;

		try
		{
			
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoices.xls")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoicesMX.xls")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoices.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoicesAC4.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoicesAxis.xls")).getInputStream();
			}

			
			XLSTransformer transformer = new XLSTransformer();
			transformer.getConfiguration().getDigester().setClassLoader(Thread.currentThread().getContextClassLoader());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("Sample_Parameter_Value", "Prolamsa Parameter Value Sample");
			map.put("invoices", col);

			Workbook w = transformer.transformXLS(input, map);

			xlsFile = File.createTempFile("xls-", ".xls");

			output = new FileOutputStream(xlsFile);

			w.write(output);
			
			input.close();

			IOUtils.closeQuietly(output);

			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.invoices.name") + ".xls\";");
			response.setContentLength((int) xlsFile.length());

			input = new FileInputStream(xlsFile);
			output = response.getOutputStream();

			StreamUtils.copyStream(input, output, StreamUtils.BUFFER_SIZE);

			xlsFile.delete();
		}
		catch (Exception ex)
		{
			LOG.error("error while printing report", ex);
		}
		finally
		{
			if (xlsFile != null)
				xlsFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	public void pdfExportInvoiceListPage(final HttpServletRequest request,final HttpServletResponse response, List<InvoiceData> col) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File pdfFile = null;

		try
		{
			
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoices.pdf")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoicesMX.pdf")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoices.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoicesAC4.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoicesAxis.pdf")).getInputStream();
			}

			
			JasperReport jasReport = (JasperReport) JRLoader.loadObject(input);
			
			input.close(); 
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("Sample_Parameter_Value", "Prolamsa Parameter Value Sample");
			
			String path = request.getContextPath();			
			String local = Config.getString(STATIC_CONTENT_HOST_CONFIG_KEY, "localhost:9001"); //request.getLocalAddr();					
						
			map.put("path", HTTP_PREFIX + local + path);
			
			LOG.info("Path PDF Image:"+ HTTP_PREFIX + local + path);	

			JasperDataSource source = new JasperDataSource(InvoiceData.class, col);

			JasperPrint jasper_print = JasperFillManager.fillReport(jasReport, map, source);

			pdfFile = File.createTempFile("pdf-", ".pdf");

			output = new FileOutputStream(pdfFile);
			JasperExportManager.exportReportToPdfStream(jasper_print, output);
			IOUtils.closeQuietly(output);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.invoices.name") + ".pdf\";");
			response.setContentLength((int) pdfFile.length());

			input = new FileInputStream(pdfFile);
			output = response.getOutputStream();

			StreamUtils.copyStream(input, output, StreamUtils.BUFFER_SIZE);

			pdfFile.delete();
		}
		catch (Exception ex)
		{
			LOG.error("error while printing report", ex);
		}
		finally
		{
			if (pdfFile != null)
				pdfFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
	
	public void excelExportInvoiceByCustomerPage(final HttpServletResponse response, List<InvoiceData> col,			
			String customer) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File xlsFile = null;

		try
		{
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomer.xls")).getInputStream();

			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomer.xls")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerMX.xls")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomer.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerAC4.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerAxis.xls")).getInputStream();
			}

			
			XLSTransformer transformer = new XLSTransformer();
			transformer.getConfiguration().getDigester().setClassLoader(Thread.currentThread().getContextClassLoader());
			Map<String,Object> map = new HashMap<String,Object>();
			
			
			map.put("invoices", col);
			map.put("creationDate", new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(new Date()));
			

			Workbook w = transformer.transformXLS(input, map);

			xlsFile = File.createTempFile("xls-", ".xls");

			output = new FileOutputStream(xlsFile);

			w.write(output);
			
			input.close();

			IOUtils.closeQuietly(output);

			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.invoiceCustomer.name") + ".xls\";");
			response.setContentLength((int) xlsFile.length());

			input = new FileInputStream(xlsFile);
			output = response.getOutputStream();

			StreamUtils.copyStream(input, output, StreamUtils.BUFFER_SIZE);

			xlsFile.delete();
		}
		catch (Exception ex)
		{
			LOG.error("error while printing report", ex);
		}
		finally
		{
			if (xlsFile != null)
				xlsFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	
	public void pdfExportInvoiceByCustomerPage(
			final HttpServletRequest request,
			final HttpServletResponse response, List<InvoiceData> col,String customer) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File pdfFile = null;
		String pathReport = null;
		Boolean flag = false;
		JasperReport jasReport = null;

		try
		{
			
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomer.pdf")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				//input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerMX.pdf")).getInputStream();
				pathReport = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerMX.pdf.jrxml")).getURL().getPath();
				flag = true;
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomer.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerAC4.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceCustomerAxis.pdf")).getInputStream();
			}

			//Solución temporal, mientras se modifica el jasper
			//TO DO: No olvidar colocar el import de JasperCompileManager
			if(flag)
			{
				jasReport = JasperCompileManager.compileReport(pathReport);
			}else
			{
				jasReport = (JasperReport) JRLoader.loadObject(input);
			}
			
			input.close(); 
			
			//set search parameters data
			int page=0;
			ShowMode showMode = ShowMode.Page;

			// create PageableData
			Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

			if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
			{
				sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
			}
			
			final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);
			
			BalanceStatementSearchParameters searchParameters = new BalanceStatementSearchParameters();						
			searchParameters.setCustomer(customer);
			searchParameters.setBaseStore(nameBaseStore);
			searchParameters.setPageableData(pageableData);
						
			BalanceStatementDetailData balanceData = balanceStatementFacade.getPagedBalanceStatement(searchParameters).getResults().get(0);
						
			Map<String,Object> map = new HashMap<String,Object>();			
			map.put("overdue", balanceData.getPastDue());
			map.put("current", balanceData.getCurrent());
			
			String path = request.getContextPath();			
			String local = Config.getString(STATIC_CONTENT_HOST_CONFIG_KEY, "localhost:9001"); //request.getLocalAddr();					
			String themeName = getThemeNameForCurrentSite();
									
			map.put("path", HTTP_PREFIX + local + path);
			map.put("themeName", SITE_THEME_PREFIX + themeName);
			
			LOG.info("Path PDF Image:"+ HTTP_PREFIX + local + path);		
																					
			JasperDataSource source = new JasperDataSource(InvoiceData.class, col);
			

			JasperPrint jasper_print = JasperFillManager.fillReport(jasReport, map, source);
			

			pdfFile = File.createTempFile("pdf-", ".pdf");

			output = new FileOutputStream(pdfFile);
			JasperExportManager.exportReportToPdfStream(jasper_print, output);
			IOUtils.closeQuietly(output);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.invoiceCustomer.name") + ".pdf\";");
			response.setContentLength((int) pdfFile.length());

			input = new FileInputStream(pdfFile);
			output = response.getOutputStream();

			StreamUtils.copyStream(input, output, StreamUtils.BUFFER_SIZE);

			pdfFile.delete();
		}
		catch (Exception ex)
		{
			LOG.error("error while printing report", ex);
		}
		finally
		{
			if (pdfFile != null)
				pdfFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
	
	public void pdfExportInvoiceSAPPage(final HttpServletRequest request,final HttpServletResponse response, InvoiceData col) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File pdfFile = null;

		try
		{
	
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceSAP.pdf")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceSAPMX.pdf")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceSAP.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceSAPAC4.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.invoiceSAPAxis.pdf")).getInputStream();
			}

			
			JasperReport jasReport = (JasperReport) JRLoader.loadObject(input);
			
			input.close(); 
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<InvoiceData> data = new ArrayList<InvoiceData>();
			data.add(col);
			
			String path = request.getContextPath();			
			String local = Config.getString(STATIC_CONTENT_HOST_CONFIG_KEY, "localhost:9001"); //request.getLocalAddr();					
						
			map.put("path", HTTP_PREFIX + local + path);
			
			LOG.info("Path PDF Image:"+ HTTP_PREFIX + local + path);	
			
			JasperDataSource source = new JasperDataSource(InvoiceData.class, data);

			JasperPrint jasper_print = JasperFillManager.fillReport(jasReport, map, source);

			pdfFile = File.createTempFile("pdf-", ".pdf");

			output = new FileOutputStream(pdfFile);
			JasperExportManager.exportReportToPdfStream(jasper_print, output);
			IOUtils.closeQuietly(output);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.invoiceSAP.name") + ".pdf\";");
			response.setContentLength((int) pdfFile.length());

			input = new FileInputStream(pdfFile);
			output = response.getOutputStream();

			StreamUtils.copyStream(input, output, StreamUtils.BUFFER_SIZE);

			pdfFile.delete();
		}
		catch (Exception ex)
		{
			LOG.error("error while printing report", ex);
		}
		finally
		{
			if (pdfFile != null)
				pdfFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
	
	
}
