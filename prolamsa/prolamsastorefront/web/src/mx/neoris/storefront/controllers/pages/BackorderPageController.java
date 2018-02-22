package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.enums.DocumentSearchType;
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.services.BackorderDetailSearchParameter;
import mx.neoris.core.services.BackorderSearchParameters;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.util.StreamUtils;
import mx.neoris.facades.NeorisBackorderFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.backorder.data.BackorderData;
import mx.neoris.facades.backorder.data.BackorderDetailData;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.Breadcrumb;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.BackorderDetailSearchForm;
import mx.neoris.storefront.forms.BackorderSearchForm;
import mx.neoris.storefront.forms.ReorderForm;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for Backorder page.
 * 
 * @author christian.loredo
 */

@Controller
@Scope("tenant")
@RequestMapping("/my-account/backorder")
public class BackorderPageController extends AbstractSearchPageController
{

	protected static final Logger LOG = Logger.getLogger(BackorderPageController.class);

	// CMS Pages
	private static final String BACKORDER_LIST_CMS_PAGE = "BackorderListPage";
	private static final String BACKORDER_DETAIL_CMS_PAGE = "BackorderDetailPage";
	private static final String BACKORDER_ITEM_CMS_PAGE = "BackorderItemPage";

	// BreadCrum Resources
	private static final String BACKORDER_LIST_BREADCRUMB = "header.link.backorder.list";
	private static final String BACKORDER_DETAIL_BREADCRUMB = "header.link.backorder.detail";

	@Resource(name = "neorisBackorderFacade")
	private NeorisBackorderFacade backorderFacade;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "dateFormatter_MMM-dd-yyyy")
	private SimpleDateFormat dateFormatter;
	
	@Resource(name = "dateFormatter_dd-MMM-yyyy")
	private SimpleDateFormat dateFormatterMX;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@ModelAttribute("metricTonsPattern")
	public String getMetricTonsPattern()
	{
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		
		if (baseStoreModel.getUid().equalsIgnoreCase("2000"))
		{
			return "###,###,###,###,##0.000";
		}else if (baseStoreModel.getUid().equalsIgnoreCase("1000"))
		{
			return "###,###,###,###,##0.00";
		}else if (baseStoreModel.getUid().equalsIgnoreCase("5000"))
		{
			return "###,###,###,###,##0.00";
		}
		
		return "###,###,###,###,##0.000";
		
	}

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	public BackorderPageController()
	{
		super();
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	// public String getBalanceStatement(
	public String getBackorder(@RequestParam(value = "page", defaultValue = "0")
	final int page, @Valid
	final BackorderSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
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

		// save search form on model
		model.addAttribute("BackorderSearchForm", searchForm);

		// create and fill the search parameters
		BackorderSearchParameters searchParameters = new BackorderSearchParameters();
		// set user code from current user
		searchParameters.setUser(neorisFacade.getCurrentCustomer().getUid());
		searchParameters.setPageableData(pageableData);
		searchParameters.setCustomer(searchForm.getCustomer());				
		
		////
					
		// set the current base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());		
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
  	    // NEORIS_CHANGE #Incidencia JCVM 21/08/2014 Se elimina el filtro de la lista dado que se elimina la columna descripcion.
		// 26/10/015 CILS Se modifica llenado de lista del sort by
		List<SortData> sorts = null;
		
		String[] sortProps = new String[] {"backorder.sortby.customer"};
		sorts = getSortListFor(sortProps, sortCode == null ? "backorder.sortby.customer-asc" : "backorder.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<BackorderData> searchPageData = new SearchPageData<BackorderData>();
		searchPageData.setPagination(new PaginationData());

		// if export
		String exportTo = request.getParameter(ControllerConstants.Export.ExportParam);

		// if sort set, means search has been clicked, do the search now
		if (sortCode != null)
		{
			try
			{
				// retrieve all elements
				if (StringUtils.isNotBlank(exportTo))
				{
					searchParameters.getPageableData().setCurrentPage(0);
					searchParameters.getPageableData().setPageSize(Integer.MAX_VALUE);
				}

				searchPageData = backorderFacade.getPagedBackorder(searchParameters);
			}
			catch (NeorisValidationError exVal)
			{
				// if validation error
				GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve Backorder", ex);

				GlobalMessages.addErrorMessage(model, "backorder.list.error");
			}

			// export XLS
			if (ControllerConstants.Export.XLS.equalsIgnoreCase(exportTo))
			{
				excelExportBackorderListPage(response, searchPageData.getResults());
				return null;
			}
		}
		
		if(searchPageData.getPagination() != null)
		{
			populateModel(model, searchPageData, showMode);			
		}
		
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "false");

		storeCmsPageInModel(model, getContentPageForLabelOrId(BACKORDER_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BACKORDER_LIST_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.backorder.list"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}

	public void excelExportBackorderListPage(final HttpServletResponse response, List<BackorderData> col) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File xlsFile = null;

		try
		{
			
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorder.xls")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderMX.xls")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorder.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderAC4.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderAxis.xls")).getInputStream();
			}
			
			XLSTransformer transformer = new XLSTransformer();
			transformer.getConfiguration().getDigester().setClassLoader(Thread.currentThread().getContextClassLoader());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Sample_Parameter_Value", "Prolamsa Parameter Value Sample");
			map.put("backorder", col);

			Workbook w = transformer.transformXLS(input, map);

			xlsFile = File.createTempFile("xls-", ".xls");

			output = new FileOutputStream(xlsFile);

			w.write(output);

			input.close();

			IOUtils.closeQuietly(output);

			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.backorder.name") + ".xls\";");
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

	// START BACKORDER DETAIL
	@RequestMapping(value = "/detail", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String getBackorderDetail(@RequestParam(value = "page", defaultValue = "0")
	final int page, @Valid final BackorderDetailSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{

		if(searchForm.getSort() == null)
		{
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

		// save search form on model
		model.addAttribute("BackorderDetailSearchForm", searchForm);

		// create and fill the search parameters
		BackorderDetailSearchParameter searchParameters = new BackorderDetailSearchParameter();
		BackorderSearchParameters searchParametersExcel = new BackorderSearchParameters();
		// set user code from current user
		searchParameters.setUser(neorisFacade.getCurrentCustomer().getUid());
		searchParameters.setPageableData(pageableData);
		searchParameters.setOrder(searchForm.getOrder());
		searchParameters.setCustomerPO(searchForm.getCustomerPO());
		searchParameters.setCustomer(searchForm.getCustomer());
		searchParametersExcel.setCustomer(searchForm.getCustomer());
		searchParametersExcel.setPageableData(pageableData);
		
		// set the current base store
		searchParameters.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());

		// only set the address if required
		if (searchForm.getAddress() != null && searchForm.getAddress().length() > 0)
			searchParameters.setAddress(searchForm.getAddress());
		
		String baseStoreFormatDate = "USA";
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
		{
			baseStoreFormatDate = "MX";
		}
		
		String currentLanguage = neorisFacade.getCurrentUser().getSessionLanguage().getIsocode();
		
		
		// process dates
		String param = searchForm.getInitialDate();
		
		if (param != null && param.length() > 0){
			
		
			try
			{
				if(baseStoreFormatDate.equalsIgnoreCase("USA"))
				{
					String month = param.substring(0, 3);
					String day = param.substring(4, 6);
					String year = param.substring(7, 11);
					String monthSpanish = month;
					
					if(currentLanguage.equalsIgnoreCase("en"))
					{
						switch(month){
						case "Jan": monthSpanish = "Ene"; break;
						case "Apr": monthSpanish = "Abr"; break;
						case "Aug": monthSpanish = "Ago"; break;
						case "Dec": monthSpanish = "Dic"; break;
						//default: monthSpanish = month; break;
						}
					}
					
					String newDate = monthSpanish+"-"+day+"-"+year;
					searchParameters.setInitialDate(dateFormatter.parse(newDate));
				}else
				{
					//01-Mar-2016
					String day = param.substring(0, 2);
					String month = param.substring(3, 6);
					String year = param.substring(7, 11);
					String monthSpanish = month;
					
					if(currentLanguage.equalsIgnoreCase("en"))
					{
						switch(month){
						case "Jan": monthSpanish = "Ene"; break;
						case "Apr": monthSpanish = "Abr"; break;
						case "Aug": monthSpanish = "Ago"; break;
						case "Dec": monthSpanish = "Dic"; break;
						//default: monthSpanish = month; break;
						}
					}
					
					String newDate = day+"-"+monthSpanish+"-"+year;
					searchParameters.setInitialDate(dateFormatterMX.parse(newDate));
				}
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
		}
		param = searchForm.getFinalDate();
		
				
		if (param != null && param.length() > 0){
			
			
			try
			{
				if(baseStoreFormatDate.equalsIgnoreCase("USA"))
				{
					String month = param.substring(0, 3);
					String day = param.substring(4, 6);
					String year = param.substring(7, 11);
					String monthSpanish = month;
					
					if(currentLanguage.equalsIgnoreCase("en"))
					{
						switch(month){
						case "Jan": monthSpanish = "Ene"; break;
						case "Apr": monthSpanish = "Abr"; break;
						case "Aug": monthSpanish = "Ago"; break;
						case "Dec": monthSpanish = "Dic"; break;
						//default: monthSpanish = month; break;
						}
					}
					String newDate = monthSpanish+"-"+day+"-"+year;
					
					searchParameters.setFinalDate(dateFormatter.parse(newDate));
				}else
				{
					//01-Mar-2016
					String day = param.substring(0, 2);
					String month = param.substring(3, 6);
					String year = param.substring(7, 11);
					String monthSpanish = month;
					
					if(currentLanguage.equalsIgnoreCase("en"))
					{
						switch(month){
						case "Jan": monthSpanish = "Ene"; break;
						case "Apr": monthSpanish = "Abr"; break;
						case "Aug": monthSpanish = "Ago"; break;
						case "Dec": monthSpanish = "Dic"; break;
						//default: monthSpanish = month; break;
						}
					}
					
					String newDate = day+"-"+monthSpanish+"-"+year;
					searchParameters.setFinalDate(dateFormatterMX.parse(newDate));
				}
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "invoice.list.invalid.dates.format");
			}
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
		//String[] sortProps = new String[] { "address","orderDate"};
		//List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "address-asc" : sortCode, "{0}-(asc)", "{0}-(desc)");
		String[] sortProps = new String[] {"backorderDetail.sortby.address","backorderDetail.sortby.orderDate"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "backorderDetail.sortby.address-asc" : "backorderDetail.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<BackorderDetailData> searchPageData = new SearchPageData<BackorderDetailData>();
		searchPageData.setPagination(new PaginationData());
		
		// Combo address detail
		B2BUnitModel preDetailModel = new B2BUnitModel();
		preDetailModel = b2bUnitService.getUnitForUid(searchForm.getCustomer());

		model.addAttribute("modelDetail", preDetailModel);

		List<AddressData> listDataB2B = neorisFacade.getShippingAddress(preDetailModel);
		model.addAttribute("listDataB2B", listDataB2B);
		
		// if export
		String exportTo = request.getParameter(ControllerConstants.Export.ExportParam);

		// if sort set, means search has been clicked, do the search now
		if (sortCode != null)
		{
			try
			{
				// retrieve all elements
				if (StringUtils.isNotBlank(exportTo))
				{
					searchParametersExcel.getPageableData().setCurrentPage(0);// getPageableData().setCurrentPage(0);
					searchParametersExcel.getPageableData().setPageSize(Integer.MAX_VALUE);
				// -NEORIS_CHANGE #Incidencia JCVM 22/08/2014 Se comentan las lineas dado que ese metodo esta vacio.	
				//	searchPageDataExcel = backorderFacade.getPagedBackorderDetailExcel(searchParametersExcel);
				}//else
				//{
					searchPageData = backorderFacade.getPagedBackorderDetail(searchParameters);
					
					customerProductReferenceService.injectCustomerProductReferencesOn( searchPageData.getResults());
					
					//load Customer Product References
				//}
			}
			catch (NeorisValidationError exVal)
			{
				// if validation error
				GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve Backorder", ex);

				GlobalMessages.addErrorMessage(model, "backorder.list.error");
			}
			
			try
			{
				// export XLS
				if (ControllerConstants.Export.XLS.equalsIgnoreCase(exportTo))
				{
					excelExportBackorderDetailPage(response, searchPageData.getResults());
					return null;
				}else
				{
					populateModel(model, searchPageData, showMode);
				}
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to Download Excel Backorder Detail", ex);

				GlobalMessages.addErrorMessage(model, "backorder.list.error");
			}
		}
		else
		{
			// if(preDetailModel.getShippingAddresses().//)

			// final Collection<AddressModel> addresses =
			// preDetailModel.getAddresses();
			// addresses.
			// final AddressModel shipAddress = addresses.iterator().next();
			// verifyAddress(shipAddress, buildDeliveryAddress());

			// Map<String,Object> mapaB2B = model.asMap();
			// List<AddressData> listDataB2B= (List<AddressData>)
			// mapaB2B.get("formattedB2BUnits");

		}

		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "false");

		storeCmsPageInModel(model, getContentPageForLabelOrId(BACKORDER_DETAIL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BACKORDER_DETAIL_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.backorderDetail.list"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}
	
	
	public void excelExportBackorderDetailPage(final HttpServletResponse response, List<BackorderDetailData> col) throws CMSItemNotFoundException
	{
		OutputStream output = null;
		InputStream input = null;
		File xlsFile = null;

		try
		{
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			String nameBaseStore = baseStore.getUid();

			//default
			input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderdetail.xls")).getInputStream();
			
			if (nameBaseStore != null && nameBaseStore.equals("1000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderdetailMX.xls")).getInputStream();	
			}  else if (nameBaseStore != null && nameBaseStore.equals("2000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderdetail.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("5000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderdetailAC4.xls")).getInputStream();
			}  else if (nameBaseStore != null && nameBaseStore.equals("6000")){
				input = resourceLoader.getResource(ControllerConstants.Export.XlsTemplatesFolder + "/" + getMessageWithDefaultContext("report.templates.backorderdetailAxis.xls")).getInputStream();
			}
			
			

			XLSTransformer transformer = new XLSTransformer();
			transformer.getConfiguration().getDigester().setClassLoader(Thread.currentThread().getContextClassLoader());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Sample_Parameter_Value", "Prolamsa Parameter Value Sample");
			map.put("backorder", col);

			Workbook w = transformer.transformXLS(input, map);

			xlsFile = File.createTempFile("xls-", ".xls");

			output = new FileOutputStream(xlsFile);

			w.write(output);

			input.close();

			IOUtils.closeQuietly(output);

			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getMessageWithDefaultContext("report.templates.backorder.name") + ".xls\";");
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
	
	//CILS 26Nov2014
		@RequestMapping(value = "/detailItem", method = RequestMethod.GET)
		@RequireHardLogIn
		public String orderMobile(@RequestParam("order") final String order,@RequestParam("customer") final String customer,@RequestParam("partNum") final String partNum, final Model model) throws CMSItemNotFoundException
		{
			ShowMode showMode = ShowMode.Page;
			// create PageableData
			Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;

			if (!configurationService.getConfiguration().getString("product.search.pageSize").equals(""))
			{
				sapPagSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.pageSize"));
			}

			final PageableData pageableData = createPageableData(0, sapPagSize, null, showMode);

				//final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
				BackorderDetailSearchParameter searchParameters = new BackorderDetailSearchParameter();
				// set user code from current user
				searchParameters.setUser(neorisFacade.getCurrentCustomer().getUid());
				searchParameters.setOrder(order);
				searchParameters.setCustomer(customer);
				searchParameters.setPageableData(pageableData);
				// set the current base store
				searchParameters.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());

				
				
				model.addAttribute("orderCode", order);
				model.addAttribute("partNum", partNum);
				model.addAttribute("customer", customer);
				
				SearchPageData<BackorderDetailData> searchPageData = new SearchPageData<BackorderDetailData>();
				searchPageData.setPagination(new PaginationData());
				
				
				
				try
				{
					// retrieve all elements
					
						searchPageData = backorderFacade.getPagedBackorderDetail(searchParameters);
					
				}
				catch (NeorisValidationError exVal)
				{
					// if validation error
					GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
				}
				catch (Exception ex)
				{
					// if other type of error
					LOG.error("Unable to retrieve Backorder", ex);

					GlobalMessages.addErrorMessage(model, "backorder.list.error");
				}
				//model.addAttribute("numPart", part);
				//model.addAttribute(new ReorderForm());
				
	   		// NEORIS_CHANGE #96
	   		//neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(orderDetails.getEntries());
	   		//neorisCartPriceHelper.sortEntriesByReadyToShipDate(orderDetails);

				//final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
				//breadcrumbs.add(new Breadcrumb("/my-account/orders", getMessageSource().getMessage("text.account.orderHistory", null,
				//		getI18nService().getCurrentLocale()), null));
				//breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.order.orderBreadcrumb", new Object[]
				//{ orderDetails.getCode() }, "Order {0}", getI18nService().getCurrentLocale()), null));
				//model.addAttribute("breadcrumbs", breadcrumbs);
				//NEORIS_CHANGE #96
				//model.addAttribute("defaultUoM", baseStoreService.getCurrentBaseStore().getUnit());

			
				populateModel(model, searchPageData, showMode);
				model.addAttribute("searchPageData", searchPageData);	
			storeCmsPageInModel(model, getContentPageForLabelOrId(BACKORDER_LIST_CMS_PAGE));
			model.addAttribute("metaRobots", "no-index,no-follow");
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BACKORDER_LIST_CMS_PAGE));
			return ControllerConstants.Views.Pages.Backorder.BackorderItemPageMobile;
			
			/*
			storeCmsPageInModel(model, getContentPageForLabelOrId(BACKORDER_ITEM_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BACKORDER_ITEM_CMS_PAGE));
			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.backorder.list"));
			model.addAttribute("metaRobots", "no-index,no-follow");

			return getViewForPage(model);*/
			
			
			//storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
			//model.addAttribute("metaRobots", "no-index,no-follow");
			//setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
			//return ControllerConstants.Views.Pages.Account.AccountOrderItemPageMobile;
		}
	

}
