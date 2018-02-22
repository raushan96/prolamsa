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

//import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.data.ProductData;
//import de.hybris.platform.commercefacades.search.data.AutocompleteResultData;
//import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import de.hybris.platform.b2b.model.B2BCustomerModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.core.commercefacades.search.NeorisProductSearchFacade;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.search.B2BProductSearchFacade;
import mx.neoris.storefront.breadcrumb.impl.DefaultResourceBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.forms.AdvancedSearchForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.services.CustomerProductReferenceService;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Controller for search page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/uploadExcel")
public class UploadExcelController extends AbstractSearchPageController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SearchPageController.class);

	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER = "storefront.advancedsearch.delimiter";
	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER_DEFAULT = ",";

	private static final String ADVANCED_SEARCH_RESULT_TYPE_CATALOG = "catalog";
	private static final String ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM = "order-form";

	private static final String NO_RESULTS_ADVANCED_PAGE_ID = "ExcelUploadPage";

	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";

	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	@Resource(name = "productSearchFacade")
	private NeorisProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "b2bProductFlexibleSearchFacade")
	private B2BProductSearchFacade<ProductData> b2bProductFlexibleSearchFacade;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

	@Resource(name = "simpleBreadcrumbBuilder")
	private DefaultResourceBreadcrumbBuilder breadcrumbBuilder;
	
	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name="cartFacade")
	private CartFacade cartFacade;

	protected ProductSearchPageData<SearchStateData, ProductData> performSearch(final String searchQuery, final int page, final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return productSearchFacade.textSearch(searchState, pageableData);
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showPage(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbBuilder.getBreadcrumbs("header.link.uploadExcel"));
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
		return getViewForPage(model);
	}

	@RequestMapping(value = "/advanced", method = { RequestMethod.POST, RequestMethod.GET })
	public String advanceSearchResults(Model model, HttpServletRequest request, HttpServletResponse response) throws CMSItemNotFoundException, IOException

	{
		ShowMode showMode = ShowMode.Page;
		model.addAttribute("errorDisplay", "");

		MultipartHttpServletRequest multipartRequest = null;

		if (!(request instanceof MultipartHttpServletRequest))
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));

			return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
		}

		multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mp = multipartRequest.getFile("file");

		String searchQuery = StringUtils.EMPTY;
		final String searchResultType = ADVANCED_SEARCH_RESULT_TYPE_CATALOG;
		final boolean inStockOnly = false;
		final boolean onlyProductIds = true;
		final boolean isCreateOrderForm = false;
		Integer page = 0;

		Row row;
		List<String> skuList = new ArrayList<String>();
		List<String> locationList = new ArrayList<String>();
		Map<String, Double> quantityMap = new LinkedHashMap<String, Double>();
		String skuD = "";
		String skuSearchCustomer = "";
		String skuExcel = "";
		String location = "";
		Double quantity = 0.0d;
		String sku2 = "";
		String plant = "";
		
		if(neorisFacade.getCurrentBaseStore().isAllowCategoryVisibility())
		{
			B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
			if(currentCustomerModel != null)
			{
				if(!currentCustomerModel.getCurrentLocation().equalsIgnoreCase(""))
				{
					String[] splitter = currentCustomerModel.getCurrentLocation().toString().split("-");
					plant = splitter[0].toString();
				}
			}
		}

		if (mp == null)
		{
			// throw new
			// RuntimeException(getMessageWithDefaultContext("checkout.documentupload.no_file_set"));
			model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.no_file_set"));
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
			return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
		}

		if (mp.getSize() == 0)
		{
			// throw new
			// RuntimeException(getMessageWithDefaultContext("checkout.documentupload.file_is_empty"));
			model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.file_is_empty"));
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
			return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
		}

		// get the extension file (xls or xlsx)
		String fileExtn = mp.getOriginalFilename().substring(mp.getOriginalFilename().lastIndexOf(".") + 1);

		Workbook wb_xssf; // Declare XSSF WorkBook
		Workbook wb_hssf; // Declare HSSF WorkBook
		Sheet sheet = null;
		// create a temporary file to store the uploaded file
		File tempFile = null;

		if (fileExtn.equalsIgnoreCase("xls"))
		{
			tempFile = File.createTempFile("document_upload", ".xls");
		}
		else
			if (fileExtn.equalsIgnoreCase("xlsx"))
			{
				tempFile = File.createTempFile("document_upload", ".xlsx");
			}
			else
			{
				model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.error_while_uploading_file"));
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
				return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
			}

		mp.transferTo(tempFile);

		InputStream input = new FileInputStream(tempFile.getAbsolutePath());

		if (fileExtn.equalsIgnoreCase("xlsx"))
		{
			wb_xssf = new XSSFWorkbook(input);
			sheet = wb_xssf.getSheetAt(0);
		}

		if (fileExtn.equalsIgnoreCase("xls"))
		{
			POIFSFileSystem fs = new POIFSFileSystem(input);
			wb_hssf = new HSSFWorkbook(fs);
			sheet = wb_hssf.getSheetAt(0);
		}
		
		int empty = 0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++)
		{
			row = sheet.getRow(i);
			
			if(row == null)
			{
				continue;
			}

			try
			{
				skuD = "";
				skuSearchCustomer = "";
				int cellType2;

				int cellType = row.getCell(0).getCellType();
				
				if(Cell.CELL_TYPE_BLANK == cellType)
				{
					empty += 1;
					if(empty == 3)
					{
						break;
					}
					continue;
				}else
				{
					cellType2 = row.getCell(1).getCellType();
				}

				if (Cell.CELL_TYPE_STRING == cellType)
				{
					skuSearchCustomer = row.getCell(0).getStringCellValue().replaceAll("\\s","");
					skuD = row.getCell(0).getStringCellValue();
				}

				if (Cell.CELL_TYPE_NUMERIC == cellType)
				{
					Double cellNumVal = row.getCell(0).getNumericCellValue();
					skuSearchCustomer = String.valueOf(cellNumVal.intValue()).replaceAll("\\s","");
					skuD = String.valueOf(cellNumVal.intValue());
				}
				
				if (Cell.CELL_TYPE_STRING == cellType2)
				{
					continue;
				}
				
				//CILS04Oct2016 comentado porque ahora la locacion del uploadExcel será por medio del combo
				//**if(row.getCell(1).getNumericCellValue() <= 0.0 || row.getCell(2).getStringCellValue().equalsIgnoreCase(""))
				if(row.getCell(1).getNumericCellValue() <= 0.0)
				{
					continue;
				}

				quantity = row.getCell(1).getNumericCellValue();
								
				//CILS 15Nov2016, ajuste para que tome en cuenta la tercer columna del excel cuando en el baseStore esté apagado la opcion de visibilidad
				//if(neorisFacade.getCurrentBaseStore().isAllowCategoryVisibility())
				//{
				//	plant = neorisFacade.getLocationSlot();
				//}else
				if(!neorisFacade.getCurrentBaseStore().isAllowCategoryVisibility())
				{
					if(row.getCell(2).getStringCellValue().equalsIgnoreCase(""))
					{
						continue;
					}
					
					location = row.getCell(2).getStringCellValue();
					//Se eliminan espacios en blanco al principio y al final de la locacion
					final String locationWOSpace = location.trim();
					plant =  neorisProductFacade.getProductLocationCodeForName(locationWOSpace.toUpperCase());
				}
								
				if(plant == null)
				{
					if(baseStoreService.getCurrentBaseStore().getActivateFunctionalityLocationDefault())
					{
						if(baseStoreService.getCurrentBaseStore().getUid().equalsIgnoreCase("1000"))
						{
							plant= "_1100";
						}else
						{
							plant= "_2200";
						}
					}else
					{
						model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.invalid_location",new Object[]{skuD}));
						storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
						return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.invalid_format_file"));
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
				return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
			}
						
			//check if the SKU is a Cliente SKU, to convert Prolamsa SKU
			String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";
			//String codeRes = customerProductReferenceService.getWithProductCodeLocationAndB2BUnit(skuD, getRootUnit(), catalogId, plant).getProductCode();
			//CustomerProductReferenceModel customerProductReference = customerProductReferences.get(eachProductData.getBaseCode()+eachProductData.getLocation().getCode());
			CustomerProductReferenceModel customerProductReference = customerProductReferenceService.getWithProductCodeLocationAndB2BUnitExcel(skuSearchCustomer, getRootUnit(), catalogId, plant.replace("_", ""));
			
			//if found a Client SKU change by Prolamsa SKU
			//if (!codeRes.equals("")){
			//	skuD = codeRes;	
			//}
			
			if(customerProductReference != null)
			{
				skuD = customerProductReference.getProductCode() ;	
			}
			
			skuList.add(skuD);

			locationList.add(plant);
			
			
			String fullSkud = skuD + plant;

			Boolean flag = false;
			
			Iterator it = quantityMap.keySet().iterator();
			while(it.hasNext())
			{
			  String keyMap = (String) it.next();
			  
			  if(keyMap.equalsIgnoreCase(fullSkud))
			  {
				  quantity +=  quantityMap.get(keyMap);
				  quantityMap.put(fullSkud, quantity);
				  //quantityMap.put(fullSkud, quantity-1.0d);
				  flag = true;
			  }
			}
			
			if(flag == false)
			{
				quantityMap.put(fullSkud, quantity);
				//quantityMap.put(fullSkud, quantity-1.0d);
			}
		}
		
		//System.out.println(locationList.size());
		if(locationList.size() > 1)
		{	
		try{
		int l=1;	
		for(int k = 0; l < locationList.size(); k++)
		{
			l++;
			if(!locationList.get(k).equalsIgnoreCase(locationList.get(k+1)))
			{
				throw new Exception();
			}
		}
		
		}//end try for
		
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.invalid_locations"));
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));
			return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
		}
		}
		
		
		
		

		for (int j = 0; j < skuList.size(); j++)
		{
			if (j != 0)
			{
				sku2 += ",";
			}
			String fullSku = skuList.get(j);
			sku2 += fullSku;
		}
		
		
		/*sku2="";
		int z=0;
		for (Map.Entry<String, Double> entry : quantityMap.entrySet()) {
		    if (z!=0) {
		    	sku2 += ",";
		    }
			String key = entry.getKey();		    
		    sku2 += key;
		    z++;
		}*/

		if (StringUtils.isNotBlank(sku2))
		{
			searchQuery = sku2;
		}
		else
		{
			if (StringUtils.isNotBlank(searchQuery))
			{
				sku2 = StringUtils.split(searchQuery, ":")[0];
			}
		}

		performAdvancedSearch(searchQuery, onlyProductIds, isCreateOrderForm, page, showMode, null, model, quantityMap, skuList, plant);

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbBuilder.getBreadcrumbs("header.link.uploadExcel"));

		final AdvancedSearchForm form = new AdvancedSearchForm();
		form.setOnlyProductIds(Boolean.valueOf(onlyProductIds));
		form.setInStockOnly(Boolean.valueOf(inStockOnly));
		form.setKeywords(sku2);
		form.setCreateOrderForm(isCreateOrderForm);

		if (isCreateOrderForm)
		{
			form.setSearchResultType(ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM);
			final List<String> filterSkus = splitSkusAsList(sku2);
			form.setFilterSkus(filterSkus);
			form.setCreateOrderForm(Boolean.valueOf(false));
			form.setOnlyProductIds(Boolean.valueOf(true));
		}
		else
		{
			form.setSearchResultType(searchResultType);
		}

		tempFile.deleteOnExit();

		model.addAttribute("advancedSearchForm", form);
		model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));

		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());

		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));

		return ControllerConstants.Views.Pages.UploadExcel.UploadExcel;
	}

	protected ProductSearchPageData<SearchStateData, ProductData> performAdvancedSearch(final String keywords, final boolean onlyProductIds, final boolean isCreateOrderForm, final int page, final ShowMode showMode, final String sortCode,
			final Model model, Map<String, Double> quantityMap, List<String> skuList, String plant)
	{
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = createEmptySearchPageData();
		List<ProductData> listProduct = new ArrayList<ProductData>();
		
		if (StringUtils.isNotBlank(keywords))
		{
			if (onlyProductIds || isCreateOrderForm)
			{
				// search using flexible search
				final List<String> productIdsList = splitSkusAsList(keywords);
				List<String> productIdsListEdit = new ArrayList<String>(productIdsList);
				List<String> listNotFound = new ArrayList<String>();
				
				//CLS 10032016
				for(int m=0; m<productIdsList.size(); m++)
				{
					if(productIdsList.get(m).split(" ").length > 1)
					{
						listNotFound.add(productIdsList.get(m));
						productIdsListEdit.remove(m);
					}
				}
				
				//Christian Loredo 28052015
				//Integer uploadExcelPageSize = Integer.parseInt(configurationService.getConfiguration().getString("product.search.uploadExcel.pageSize"));
				// TODO: Find a way to search by code and location and that way avoid to reserve more memory than needed
				//Integer uploadExcelPageSize = skuList.size();
				Integer uploadExcelPageSize = 500;
				final PageableData pageableData = createPageableData(page, uploadExcelPageSize, sortCode, showMode);

				//Christian Loredo 12022015
				//Add parameter CatlogId to search CustomerProductReference
				String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";
				
				// call facade to get the real skus
				// NEORIS_CHANGE #55
				final List<String> productProlamsaList = neorisFacade.getProlamsaSkuFromSku(productIdsListEdit, getRootUnit(), catalogId);

				SearchStateData searchState = new SearchStateData();
				searchPageData = productSearchFacade.skusSearch(productProlamsaList, searchState, pageableData);
				
				//remove all the locations that are not included in excel file
				for(String skuCode : quantityMap.keySet())
				{
					for(ProductData product : searchPageData.getResults()){
						if (skuCode.equals(product.getBaseCode() + product.getLocation().getCode())) {
							listProduct.add(product);
						}
					}
				}
				
//				for(ProductData product : searchPageData.getResults()){
//					
//					if (quantityMap.containsKey(product.getBaseCode() + product.getLocation().getCode())) {
//						listProduct.add(product);
//					}
//					
//				}
								
				//List<String> listNotFound = new ArrayList<String>();

				// identify which skus where not found and add them to the list
				Boolean addList = false;
				for (int i = 0; i < productIdsListEdit.size(); i++)
				{
					addList = false;
					for (ProductData data : listProduct)
					{
						if (!data.getBaseCode().equals(productProlamsaList.get(i)))
						{
							addList = true;
						}
						else
						{
							addList = false;
							break;
						}
					}
					if (addList)
					{
						listNotFound.add(productIdsListEdit.get(i));
					}
				}
				
				

				model.addAttribute("numberSKU", listNotFound.size());
				model.addAttribute("listNotFound", Arrays.toString(listNotFound.toArray()).replaceAll("[\\[\\]]", ""));

				// NEORIS_CHANGE #38
				//neorisProductFacade.injectProductInventoryEntriesOn(searchPageData.getResults(), neorisFacade.getCurrentCustomerType());
				neorisProductFacade.injectProductInventoryEntriesOn(listProduct, neorisFacade.getCurrentCustomerType());
				// NEORIS_CHANGE #75
				//neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), searchPageData.getResults());
				neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), listProduct);
				// NEORIS_CHANGE #55
				//neorisProductFacade.injectCustomerNameAndDescriptionDataOn(searchPageData.getResults());
				neorisProductFacade.injectCustomerNameAndDescriptionDataOn(listProduct);
				// Add information for UploadExcel
				//neorisProductFacade.injectProductInfoExcel(searchPageData.getResults(), quantityMap, skuList);
				neorisProductFacade.injectProductInfoExcel(listProduct, quantityMap, skuList);
				
				//List<String> listQuantityExact = neorisFacade.getlistQuantityExact(listProduct, quantityMap);
				List<String> listQuantityExact = new  ArrayList<String>();
				model.addAttribute("listQuantityExact", Arrays.toString(listQuantityExact.toArray()).replaceAll("[\\[\\]]", ""));
			}
			else
			{
				// search using solr.
				searchPageData = performSearch(keywords, page, showMode, sortCode, getSearchPageSize());
			}

		}
		
		if (listProduct.size()==0) 
		{			
			model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.notFound"));
		}

		final ProductSearchPageData<SearchStateData, ProductData> productSearchPageData = new ProductSearchPageData<SearchStateData, ProductData>();
		productSearchPageData.setResults(listProduct);
		final PaginationData pagination = new PaginationData();
		pagination.setTotalNumberOfResults(0);
		productSearchPageData.setPagination(pagination);
		productSearchPageData.setSorts(new ArrayList<SortData>());
	
		Boolean mixedCartAndFileProducts = false;
		
		Boolean fileHasAPIProducts = false;
		Boolean fileHasNoAPIProducts = false;
		
		Boolean mixedFileProducts = false;
		
		if(baseStoreService.getCurrentBaseStore().getAPIFunctionaliatyEnabled() != null &&
				baseStoreService.getCurrentBaseStore().getAPIFunctionaliatyEnabled().booleanValue())
		{
			CartData cartData = cartFacade.getSessionCart();
			
			if(cartData.getEntries() != null && cartData.getEntries().size()>0)
			{
				Boolean orderHasAPIProducts = cartData.getHasAPIProducts();
				
				if(listProduct.size()>0)
				{
					
					for(ProductData eachProductData:listProduct)
					{
						if(eachProductData.getIsAPI())
							fileHasAPIProducts=true;
						else
							fileHasNoAPIProducts=true;
					}
					
					if(fileHasAPIProducts && fileHasNoAPIProducts)
						mixedFileProducts=true;
				}
				
				if((orderHasAPIProducts && !fileHasAPIProducts) ||(!orderHasAPIProducts && fileHasAPIProducts))
				{
					mixedCartAndFileProducts=true;
				}
				
			}
		}
		
		for(ProductData eachProductData:listProduct)
		{
			if(eachProductData.getIsAPI())
				fileHasAPIProducts=true;
			else
				fileHasNoAPIProducts=true;
		}
		
		if(fileHasAPIProducts && fileHasNoAPIProducts)
			mixedFileProducts=true;
		
		if(mixedFileProducts)
		{
			productSearchPageData.setResults(null);
			model.addAttribute("mixedFileProducts", mixedFileProducts);
			model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.mixedFileProducts"));
		}
		
		if(mixedCartAndFileProducts)
		{
			productSearchPageData.setResults(null);
			model.addAttribute("mixedCartAndFileProducts", mixedCartAndFileProducts);
			model.addAttribute("errorDisplay", getMessageWithDefaultContext("checkout.documentupload.mixedCartProducts"));
		}
		
		populateModel(model, productSearchPageData, showMode);
		
		return searchPageData;
	}

	private ProductSearchPageData<SearchStateData, ProductData> createEmptySearchPageData()
	{
		final ProductSearchPageData<SearchStateData, ProductData> productSearchPageData = new ProductSearchPageData<SearchStateData, ProductData>();

		productSearchPageData.setResults(new ArrayList<ProductData>());
		final PaginationData pagination = new PaginationData();
		pagination.setTotalNumberOfResults(0);
		productSearchPageData.setPagination(pagination);
		productSearchPageData.setSorts(new ArrayList<SortData>());

		return productSearchPageData;
	}

	protected List<String> splitSkusAsList(final String skus)
	{
		return Arrays.asList(StringUtils.split(skus, Config.getString(ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER, ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER_DEFAULT)));
	}

	private Locale getCurrentLocale()
	{
		return getI18nService().getCurrentLocale();
	}

	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(getMessageSource().getMessage("search.meta.title", null, getCurrentLocale()) + " " + searchText));
	}
}
