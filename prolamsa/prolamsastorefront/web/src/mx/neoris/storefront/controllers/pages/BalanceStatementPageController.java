package mx.neoris.storefront.controllers.pages;

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
import java.text.DateFormat;
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
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.BalanceStatementSearchForm;
import mx.neoris.storefront.jasper.JasperDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BalanceStatementPageController extends AbstractSearchPageController
{
	//NEORIS_CHANGE #61
	private static final String BALANCE_STATEMENT_CMS_PAGE = "AccountBalanceStatementListPage";
	//NEORIS_CHANGE #62
	private static final String BALANCE_STATEMENT_DETAIL_CMS_PAGE = "AccountBalanceStatementDetailPage";

	private static final Logger LOG = Logger.getLogger(BalanceStatementPageController.class);

	private static final String HTTP_PREFIX = "http://";
	
	private static final String PROTOCOL = ":9001";	
	
	private static final String SITE_THEME_PREFIX = "theme-";
	
	private static final String STATIC_CONTENT_HOST_CONFIG_KEY = "apache.server.staticContent.host";
	
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	//NEORIS_CHANGE #61
	@Resource(name = "neorisBalanceStatementFacade")
	private NeorisBalanceStatementFacade balanceStatementFacade;
	
	@Resource(name = "neorisInvoiceFacade")
	private NeorisInvoiceFacade invoiceFacade;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	//NEORIS_CHANGE #61
	@RequestMapping(value = "/my-account/balance-statement", method ={ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String getBalanceStatement(
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@Valid final BalanceStatementSearchForm searchForm, final Model model, final HttpServletRequest request,final HttpServletResponse response) throws CMSItemNotFoundException
	{
		
		if(searchForm.getSort() == null)
		{
			B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
			String customerTrick = currentB2BUnit.getUid();
			searchForm.setCustomer(customerTrick);
			
			searchForm.setSort("");
		}
		
		ShowMode showMode = ShowMode.Page;

		// create PageableData
		Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

		if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
		{
			sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
		}
		
		final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);


		// set user code from current user
		searchForm.setUser(neorisFacade.getCurrentCustomer().getUid());

		// set the current base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());

		// save search form on model
		model.addAttribute("balanceStatementSearchForm", searchForm);

		// create and fill the search parameters
		BalanceStatementSearchParameters searchParameters = new BalanceStatementSearchParameters();
		searchParameters.setUser(searchForm.getUser());
		searchParameters.setBaseStore(searchForm.getBaseStore());

		//validate for ALL selection, "" means All customer
		if (searchForm.getCustomer()!= null && searchForm.getCustomer().equals("")){
			List<String> list = new ArrayList<String>();
			for(B2BUnitData b2bUnitData : getFormattedB2BUnits()){
				list.add(b2bUnitData.getUid());
			}
			searchParameters.setListCustomer(list);
		}

		searchParameters.setPageableData(pageableData);
		if (searchForm.getCustomer() != null)
			searchParameters.setCustomer(searchForm.getCustomer());

		// get and set sort information
		String sortCode = searchForm.getSort();
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
		{
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}

		// add sorts to searchPageData
		String[] sortProps = new String[] {"balance.sortby.customer","balance.sortby.b2b.shortName","balance.sortby.balance"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "balance.sortby.customer-asc" : "balance.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<BalanceStatementDetailData> searchPageData = new SearchPageData<BalanceStatementDetailData>();
		searchPageData.setPagination(new PaginationData());

		// if export
		String exportTo = request.getParameter(ControllerConstants.Export.ExportParam);
		
		// if sort set, means search has been clicked, do the search now
		if (sortCode != null)
		{
			try
			{
				//searchParameters.validateInformation();

				// retrieve all elements
				if (StringUtils.isNotBlank(exportTo))
				{
					searchParameters.getPageableData().setCurrentPage(0);
					searchParameters.getPageableData().setPageSize(Integer.MAX_VALUE);
				}

				searchPageData = balanceStatementFacade.getPagedBalanceStatement(searchParameters);
			}
			catch (NeorisValidationError exVal)
			{
				// if validation error
				GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve Balance Statements", ex);

				GlobalMessages.addErrorMessage(model, "balanceStatement.list.error");
			}

			// export PDF
			if (ControllerConstants.Export.PDF.equalsIgnoreCase(exportTo))
			{
				/* NEORIS_CHANGE #Incidencia 173 JCVM 28/08/2014 Se cambia el objecto enviado a escribir cuando se toma la información de la tabla. */
				pdfExportBalanceStatementPage(request, response, searchPageData.getResults());
				return null;
			}

			// export XLS
			if (ControllerConstants.Export.XLS.equalsIgnoreCase(exportTo))
			{
            /* NEORIS_CHANGE #Incidencia 172 JCVM 28/08/2014 Se cambia el objecto enviado a escribir cuando se toma la información de la tabla. */
				excelExportBalanceStatementPage(response, searchPageData.getResults());
				return null;
			}
		}

		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "false");

		storeCmsPageInModel(model, getContentPageForLabelOrId(BALANCE_STATEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BALANCE_STATEMENT_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.balanceStatement.list"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("searchPageData",searchPageData);

		//return ControllerConstants.Views.Pages.Account.AccountBalanceStatementListPage;
		return getViewForPage(model);
	}

	//NEORIS_CHANGE #62
	@RequestMapping(value = "/my-account/balance-statement-detail", method ={ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String getBalanceStatementDetail(
			@RequestParam(value = "customer") final String customer,		
			@RequestParam(value = "row") final Integer row,
			@Valid final BalanceStatementSearchForm searchForm,
			final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		
		ShowMode showMode = ShowMode.Page;
		int page=1;

		// create PageableData
		Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

		if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
		{
			sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
		}
		
		final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);
		
		// set user code from current user
		searchForm.setUser(neorisFacade.getCurrentCustomer().getUid());
		

		// set the current base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());

		// save search form on model
		model.addAttribute("balanceStatementSearchForm", searchForm);

		// create and fill the search parameters
		BalanceStatementSearchParameters searchParameters = new BalanceStatementSearchParameters();
		InvoiceSearchParameters searchParametersInvoice = new InvoiceSearchParameters();
		searchParameters.setUser(searchForm.getUser());
		searchParameters.setBaseStore(searchForm.getBaseStore());
		searchParameters.setPageableData(pageableData);

		//validate for ALL selection, "" means All customer
		if (searchForm.getCustomer()!= null && searchForm.getCustomer().equals("")){
			List<String> list = new ArrayList<String>();
			for(B2BUnitData b2bUnitData : getFormattedB2BUnits()){
				list.add(b2bUnitData.getUid());
			}
			searchParameters.setListCustomer(list);
		}

		if (searchForm.getCustomer() != null)
			searchParameters.setCustomer(searchForm.getCustomer());

		BalanceStatementDetailData searchData = new BalanceStatementDetailData();

		// if export
		String exportTo = request.getParameter(ControllerConstants.Export.ExportParam);

		try
		{
			searchData = balanceStatementFacade.getBalanceStatementDetail(searchParameters, row);
			
			searchParametersInvoice.setBaseStore(searchForm.getBaseStore());
			searchParametersInvoice.setCustomer(searchForm.getCustomer());
			searchParametersInvoice.setTypeInvoice("2");
			searchParametersInvoice.setTypeDocto("F");
			searchParametersInvoice.setPageableData(pageableData);

			if (searchForm.getCustomer() != null)
			{
				searchParametersInvoice.setCustomer(searchForm.getCustomer());
			}
		}
        catch (Exception ex)
		{
			// if other type of error
			LOG.error("Unable to retrieve Balance Statements", ex);

				GlobalMessages.addErrorMessage(model, "balanceStatement.list.error");
		}

		// export PDF
		if (ControllerConstants.Export.PDF.equalsIgnoreCase(exportTo))
		{
			pdfExportBalanceStatementDetailPage(request,response, searchData);
			return null;
		}

		// export XLS
		if (ControllerConstants.Export.XLS.equalsIgnoreCase(exportTo))
		{
			try
			{
				SearchPageData<InvoiceData> searchPageDataInvoiceCurrent = invoiceFacade.getPagedInvoicesByRange(searchParametersInvoice, "0");
				SearchPageData<InvoiceData> searchPageDataInvoice30Day = invoiceFacade.getPagedInvoicesByRange(searchParametersInvoice, "1_30");
				SearchPageData<InvoiceData> searchPageDataInvoice60Day = invoiceFacade.getPagedInvoicesByRange(searchParametersInvoice, "31_60");
				SearchPageData<InvoiceData> searchPageDataInvoice90Day = invoiceFacade.getPagedInvoicesByRange(searchParametersInvoice, "61_90");
				SearchPageData<InvoiceData> searchPageDataInvoiceMoreThan90Day = invoiceFacade.getPagedInvoicesByRange(searchParametersInvoice, "91_*");
				
				excelExportBalanceStatementDetailPage(response, searchData, searchPageDataInvoiceCurrent.getResults(), 
					                                searchPageDataInvoice30Day.getResults(), 
					                                searchPageDataInvoice60Day.getResults(),
					                                searchPageDataInvoice90Day.getResults(),
					                                searchPageDataInvoiceMoreThan90Day.getResults());
			} catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve Balance Statements", ex);

					GlobalMessages.addErrorMessage(model, "balanceStatement.list.error");
			}
			//return "redirect:/invoice/by-customer?customer="+customer+"&_export=xls_by_customer";
			return null;
		}

		model.addAttribute("searchData", searchData);
		storeCmsPageInModel(model, getContentPageForLabelOrId(BALANCE_STATEMENT_DETAIL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BALANCE_STATEMENT_DETAIL_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.balanceStatement.detail"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Account.AccountBalanceStatementDetailPage;
	}

    /* NEORIS_CHANGE #Incidencia 172 JCVM 28/08/2014 Se cambia el objecto enviado a escribir cuando se toma la información de la tabla. */
	public void excelExportBalanceStatementPage(final HttpServletResponse response, List<BalanceStatementDetailData> col) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File xlsFile = null;

		try
		{
			
			
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatement.xls")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementMX.xls")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatement.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementAC4.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementAxis.xls")).getInputStream();
			}

			XLSTransformer transformer = new XLSTransformer();
			transformer.getConfiguration().getDigester().setClassLoader(Thread.currentThread().getContextClassLoader());
			Map<String,Object> map = new HashMap<String,Object>();			
			map.put("balanceStatement", col);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");					
			map.put("creationDate",dateFormat.format(new Date()).toString());

			Workbook w = transformer.transformXLS(input, map);

			xlsFile = File.createTempFile("xls-", ".xls");

			output = new FileOutputStream(xlsFile);

			w.write(output);

			input.close();

			IOUtils.closeQuietly(output);

			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.balanceStatement.name") + ".xls\";");
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

	/* NEORIS_CHANGE #Incidencia 173 JCVM 28/08/2014 Se cambia el objecto enviado a escribir cuando se toma la información de la tabla. */
	public void pdfExportBalanceStatementPage(final HttpServletRequest request,
			final HttpServletResponse response, List<BalanceStatementDetailData> col) throws CMSItemNotFoundException
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
			input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatement.pdf")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				//input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementMX.pdf")).getInputStream();
				pathReport = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementMX.pdf.jrxml")).getURL().getPath();
				flag = true;
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatement.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementAC4.pdf")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementAxis.pdf")).getInputStream();
			}
			
			//Solución temporal, mientras se modifica el jasper
			if(flag)
			{
				jasReport = JasperCompileManager.compileReport(pathReport);
			}else
			{
				jasReport = (JasperReport) JRLoader.loadObject(input);
			}
			
			input.close(); 

			Map<String,Object> map = new HashMap<String,Object>();
			
			String path = request.getContextPath();			
			String local = Config.getString(STATIC_CONTENT_HOST_CONFIG_KEY, "localhost:9001"); //request.getLocalAddr();	
			String themeName = getThemeNameForCurrentSite();
									
			map.put("path", HTTP_PREFIX + local + path);
			map.put("themeName", SITE_THEME_PREFIX + themeName);
			
			LOG.info("Path PDF Image:"+ HTTP_PREFIX + local + path);	

			JasperDataSource source = new JasperDataSource(BalanceStatementData.class, col);

			JasperPrint jasper_print = JasperFillManager.fillReport(jasReport, map, source);

			pdfFile = File.createTempFile("pdf-", ".pdf");

			output = new FileOutputStream(pdfFile);
			JasperExportManager.exportReportToPdfStream(jasper_print, output);
			IOUtils.closeQuietly(output);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.balanceStatement.name") + ".pdf\";");
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

	
	public void excelExportBalanceStatementDetailPage(final HttpServletResponse response, BalanceStatementDetailData col, List<InvoiceData> invoiceCurrent, 
			                                            List<InvoiceData> invoice30Day, List<InvoiceData> invoice60Day, List<InvoiceData> invoice90Day, List<InvoiceData> invoiceMoreThan90Day) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File xlsFile = null;

		try
		{
			
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.xls")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailMX.xls")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailAC4.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailAxis.xls")).getInputStream();
			}

			
			XLSTransformer transformer = new XLSTransformer();
			transformer.getConfiguration().getDigester().setClassLoader(Thread.currentThread().getContextClassLoader());
			Map<String,Object> map = new HashMap<String,Object>();			
			map.put("balanceStatementDetail", col);
			map.put("invoicesCurrent", invoiceCurrent);
			map.put("invoices30Day", invoice30Day);
			map.put("invoices60Day", invoice60Day);
			map.put("invoices90Day", invoice90Day);
			map.put("invoicesMoreThan90Day", invoiceMoreThan90Day);

			Workbook w = transformer.transformXLS(input, map);

			xlsFile = File.createTempFile("xls-", ".xls");

			output = new FileOutputStream(xlsFile);

			w.write(output);

			input.close();

			IOUtils.closeQuietly(output);

			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.name") + ".xls\";");
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

	public void pdfExportBalanceStatementDetailPage(final HttpServletRequest request,
			final HttpServletResponse response, BalanceStatementDetailData col) throws CMSItemNotFoundException
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
			input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.pdf")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				//input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailMX.pdf")).getInputStream();
				pathReport = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailMX.pdf.jrxml")).getURL().getPath();
				flag = true;
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				//input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.pdf")).getInputStream();
				pathReport = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.pdf.jrxml")).getURL().getPath();
				flag=true;
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
//				input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailAC4.pdf")).getInputStream();
				pathReport = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailAC4.pdf.jrxml")).getURL().getPath();
				flag=true;
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				//input = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailAxis.pdf")).getInputStream();
				pathReport = resourceLoader.getResource(ControllerConstants.Export.PdfTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.balanceStatementDetailAxis.pdf.jrxml")).getURL().getPath();
				flag=true;
			}

			//Solución temporal, mientras se modifica el jasper
			if(flag)
			{
				jasReport = JasperCompileManager.compileReport(pathReport);
			}else
			{
				jasReport = (JasperReport) JRLoader.loadObject(input);
			}

			input.close();

			Map<String,Object> map = new HashMap<String,Object>();
			//map.put("Sample_Parameter_Value", "Prolamsa Parameter Value Sample");

			String path = request.getContextPath();			
			String local = Config.getString(STATIC_CONTENT_HOST_CONFIG_KEY, "localhost:9001"); //request.getLocalAddr();		
			String themeName = getThemeNameForCurrentSite();
						
			map.put("path", HTTP_PREFIX + local + path);
			map.put("themeName", SITE_THEME_PREFIX + themeName);
			
			LOG.info("Path PDF Image:"+ HTTP_PREFIX + local + path);
			
			List<BalanceStatementDetailData> data = new ArrayList<BalanceStatementDetailData>();
			data.add(col);

			JasperDataSource source = new JasperDataSource(BalanceStatementDetailData.class, data);

			JasperPrint jasper_print = JasperFillManager.fillReport(jasReport, map, source);

			pdfFile = File.createTempFile("pdf-", ".pdf");

			output = new FileOutputStream(pdfFile);
			JasperExportManager.exportReportToPdfStream(jasper_print, output);
			IOUtils.closeQuietly(output);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.balanceStatementDetail.name") + ".pdf\";");
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
