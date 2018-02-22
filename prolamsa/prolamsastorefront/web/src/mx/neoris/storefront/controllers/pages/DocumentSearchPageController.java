package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.store.BaseStoreModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.document.NeorisDocumentDownloadUrlResolver;
import mx.neoris.core.enums.DocumentSearchType;
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.DocumentSearchParameters;
import mx.neoris.core.util.NeorisURLFileDownloader;
import mx.neoris.core.util.StreamUtils;
import mx.neoris.facades.NeorisDocumentSearchFacade;
import mx.neoris.facades.document.data.DocumentSearchData;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.DocumentSearchForm;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;


/**
 * Controller for DocumentSearch page.
 * 
 * @author christian.loredo
 */

@Controller
@Scope("tenant")
@RequestMapping("/my-account/document")
public class DocumentSearchPageController extends AbstractSearchPageController
{
	protected static final Logger LOG = Logger.getLogger(DocumentSearchPageController.class);

	// CMS Pages
	private static final String DOCUMENT_LIST_CMS_PAGE = "DocumentSearchListPage";

	// BreadCrum Resources
	private static final String DOCUMENT_LIST_BREADCRUMB = "header.link.documentSearch.list";

	@Resource(name = "neorisDocumentFacade")
	private NeorisDocumentSearchFacade neorisDocumentFacade;

	@Resource(name = "dateFormatter_MM-dd-yyyy")
	private SimpleDateFormat dateFormatter;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "neorisURLFileDownloader")
	private NeorisURLFileDownloader fileDownloader;

	@Resource(name = "neorisSAPUrlDocumentDownloadResolver")
	private NeorisDocumentDownloadUrlResolver urlResolver;
	
	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "emailService")
	private EmailService emailService;
	
	@Resource
	private MediaService mediaService;
	
	private static final String DOCUMENT_TYPE_PDF = "F";
	private static final String DOCUMENT_TYPE_XML = "M";
	private static final String DOCUMENT_TYPE_QUOTE = "S";
	private static final String DOCUMENT_TRANSFER_BY_DOWNLOAD = "DOWNLOAD";
	private static final String DOCUMENT_ORDER_DOWNLOAD_ALL = "ORDER-ALL";
	
	private static final int DOCUMENT_SEARCH_DAYS_PERIOD = -30;
	
	public DocumentSearchPageController()
	{
		super();
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showDocumentListPage(
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@Valid final DocumentSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{

		if(searchForm.getSort() == null)
		{
			B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
			String customerTrick = currentB2BUnit.getUid();
			searchForm.setCustomer(customerTrick);
			
			searchForm.setSort("");
			
			// setting dates to show documents into the last 30 days (Prolamsa MX and A4C)
			BaseStoreModel currentBaseStoreModel = neorisFacade.getCurrentBaseStore();
			if ("1000".equals(currentBaseStoreModel.getUid())
					|| "5000".equals(currentBaseStoreModel.getUid()))
			{
				SimpleDateFormat formatter =  new SimpleDateFormat("MM-dd-yyyy");
				Calendar today = Calendar.getInstance();
				searchForm.setFinalDate(formatter.format(today.getTime()));
				today.add(Calendar.DATE, DOCUMENT_SEARCH_DAYS_PERIOD);
				searchForm.setInitialDate(formatter.format(today.getTime()));				
			}
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
		model.addAttribute("documentSearchForm", searchForm);				
		
		// set the current base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());		
		
		// create and fill the search parameters
		DocumentSearchParameters searchParameters = new DocumentSearchParameters();		
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
		searchParameters.setCustomer(searchForm.getCustomer());
		searchParameters.setDocumentType(searchForm.getDocumentType());
		searchParameters.setReference(searchForm.getReference());

		if (searchForm.getCustomer()!=null && !searchForm.getCustomer().equals("")) {
			B2BUnitModel b2bUnitModel = new B2BUnitModel();
			b2bUnitModel = b2bUnitService.getUnitForUid(searchForm.getCustomer());	
			// save search form on model
			model.addAttribute("b2bUnitModel", b2bUnitModel);		
		}
		
		boolean existInitialDate = false;
		// process dates
		String param = searchForm.getInitialDate();
		if (param != null && param.length() > 0)
			try
			{
				searchParameters.setInitialDate(dateFormatter.parse(param));
				existInitialDate = true;
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "documentSearch.list.invalid.dates.format");
			}
		param = searchForm.getFinalDate();
		if(existInitialDate && param.equalsIgnoreCase(""))
		{
			final Date hoy = new Date();
			final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			final String datetimeStr = format.format(hoy);
			param = datetimeStr;
		}
		
		if (param != null && param.length() > 0)
			try
			{
				searchParameters.setFinalDate(dateFormatter.parse(param));
			}
			catch (ParseException ex)
			{
				GlobalMessages.addErrorMessage(model, "documentSearch.list.invalid.dates.format");
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
		//String[] sortProps = new String[] { "customer Id","Customer Name","invoice","mtr","remission","po","so","quote" };
		//List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "customer-asc" : sortCode, "{0}-(asc)", "{0}-(desc)");
		String[] sortProps = new String[] {/*"document.sortby.code","document.sortby.name",*/"document.sortby.invoice","document.sortby.mtr","document.sortby.remission","document.sortby.po","document.sortby.so","document.sortby.quote"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "document.sortby.invoice-asc" : "document.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<DocumentSearchData> searchPageData = new SearchPageData<DocumentSearchData>();
		searchPageData.setPagination(new PaginationData());

		// if sort set, means search has been clicked, do the search now                                                                                                                                                                                                                                                                                                               
		if (sortCode != null)
		{
			try
			{
				//searchParameters.validateInformation();
				
				searchPageData = neorisDocumentFacade.getPagedDocuments(searchParameters);
				neorisDocumentFacade.injectSOAttachmentsOn(searchPageData.getResults());
			}
			catch (NeorisValidationError exVal)
			{ 
				// if validation error
				GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve document search", ex);

				GlobalMessages.addErrorMessage(model, "documentSearch.list.error");
			}
		}
		
		model.addAttribute("EnumTypes", DocumentSearchType.values());
		//model.addAttribute("EnumTypeInvoice", DocumentSearchType.valueOf("INVOICE"));
		
		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "flase");
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.documentSearch.list"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}
	
	//////////INICIO////////////////////
	@RequestMapping(value = "/listMaterial", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showDocumentListMaterialPage(
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@Valid final DocumentSearchForm searchForm, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		
		if(searchForm.getDocumentType() != null && searchForm.getDocumentType().length() > 0)
		{
			if (searchForm.getReference().equals(""))
			{
				GlobalMessages.addErrorMessage(model, "documentSearch.list.invalid.reference");
				model.addAttribute("EnumTypeInvoice", DocumentSearchType.valueOf("INVOICE"));
				storeCmsPageInModel(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
				model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.documentSearch.list"));
				model.addAttribute("metaRobots", "no-index,no-follow");
				return "pages/document/documentSearchListPage";
				
			}
		}
		
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
		model.addAttribute("documentSearchForm", searchForm);				
		
		// set the current base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());		
		
		// create and fill the search parameters
		DocumentSearchParameters searchParameters = new DocumentSearchParameters();		
		searchParameters.setBaseStore(searchForm.getBaseStore());
		
		if(searchForm.getDocumentType() != null && searchForm.getDocumentType().length() > 0)
		{
			if (!searchForm.getCustomer().equals(""))
			{
				GlobalMessages.addErrorMessage(model, "documentSearch.list.invalid.reference");
				model.addAttribute("EnumTypeInvoice", DocumentSearchType.valueOf("INVOICE"));
				storeCmsPageInModel(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
				model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.documentSearch.list"));
				model.addAttribute("metaRobots", "no-index,no-follow");
				return "pages/document/documentSearchListPage";
			}
		}
		
		//validate for ALL selection, "" means All customer
		if (searchForm.getCustomer()!= null && searchForm.getCustomer().equals("")){
			List<String> list = new ArrayList<String>();
			for(B2BUnitData b2bUnitData : getFormattedB2BUnits()){
				list.add(b2bUnitData.getUid());
			}	
			searchParameters.setListCustomer(list);
		}
			
		searchParameters.setPageableData(pageableData);
		searchParameters.setCustomer(searchForm.getCustomer());
		searchParameters.setDocumentType(searchForm.getDocumentType());
		searchParameters.setReference(searchForm.getReference());

		if (searchForm.getCustomer()!=null && !searchForm.getCustomer().equals("")) {
			B2BUnitModel b2bUnitModel = new B2BUnitModel();
			b2bUnitModel = b2bUnitService.getUnitForUid(searchForm.getCustomer());	
			// save search form on model
			model.addAttribute("b2bUnitModel", b2bUnitModel);		
		}
		
		
		
		// process dates
		if(searchForm.getDocumentType() != null && searchForm.getDocumentType().length() > 0)
		{
			if (!searchForm.getInitialDate().equals(""))
				{
					GlobalMessages.addErrorMessage(model, "documentSearch.list.invalid.initdate.empty");
					model.addAttribute("EnumTypeInvoice", DocumentSearchType.valueOf("INVOICE"));
					storeCmsPageInModel(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
					model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.documentSearch.list"));
					model.addAttribute("metaRobots", "no-index,no-follow");
					return "pages/document/documentSearchListPage";
				}
		}
		if(searchForm.getDocumentType() != null && searchForm.getDocumentType().length() > 0)
		{
			if (!searchForm.getFinalDate().equals(""))
				{
					GlobalMessages.addErrorMessage(model, "documentSearch.list.invalid.finaldate.empty");
					model.addAttribute("EnumTypeInvoice", DocumentSearchType.valueOf("INVOICE"));
					storeCmsPageInModel(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
					model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.documentSearch.list"));
					model.addAttribute("metaRobots", "no-index,no-follow");
					return "pages/document/documentSearchListPage";
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
		//String[] sortProps = new String[] { "customer Id","Customer Name","invoice","mtr","remission","po","so","quote" };
		//List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "customer-asc" : sortCode, "{0}-(asc)", "{0}-(desc)");
		String[] sortProps = new String[] {"document.sortby.material.mtr","document.sortby.material.remission"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "document.sortby.material.mtr-asc" : "document.sortby.material."+sortCode, "{0}-(asc)", "{0}-(desc)");
		
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<DocumentSearchData> searchPageData = new SearchPageData<DocumentSearchData>();
		searchPageData.setPagination(new PaginationData());

		// if sort set, means search has been clicked, do the search now                                                                                                                                                                                                                                                                                                               
		if (sortCode != null)
		{
			try
			{
				//searchParameters.validateInformation();
				
				searchPageData = neorisDocumentFacade.getPagedDocuments(searchParameters);
			}
			catch (NeorisValidationError exVal)
			{ 
				// if validation error
				GlobalMessages.addErrorMessage(model, getMessageWithDefaultContext(exVal.getMessage()));
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve document search", ex);

				GlobalMessages.addErrorMessage(model, "documentSearch.list.error");
			}
		}
		
		//model.addAttribute("EnumTypes", DocumentSearchType.values());
		model.addAttribute("EnumTypeInvoice", DocumentSearchType.valueOf("INVOICE"));
		
		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "flase");
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DOCUMENT_LIST_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("header.link.documentSearch.list"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}
	
	////////FINAL////////////////////////
	
	
	@RequestMapping(value = "/transferDocument", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public void transferDocument(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String documentNumber = request.getParameter("documentNumber");
		String documentOwner = request.getParameter("documentOwner");
		String transferType = request.getParameter("transferType");
		
		// specify the type of document to transfer
		String documentType = request.getParameter("documentType");
		
		BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();
		
		String invoicePdfFilePath = "";
		String invoiceXmlFilePath = "";
		String genericTransferPath = "";
		boolean compressOnZip = true;
		
		if (documentType != null && !documentType.isEmpty()) 
		{
			// resolve the path for document type specified
			genericTransferPath = urlResolver.urlDownloadDocumentFor(currentBaseStore.getUid(), documentType, documentOwner, documentNumber);
			// since is a single document, we dont need compress on a zip file
			compressOnZip = false;
		}
		else
		{
			// transfer invoice composed by two files: pdf and xml
			invoicePdfFilePath = urlResolver.urlDownloadDocumentFor(currentBaseStore.getUid(), DOCUMENT_TYPE_PDF, documentOwner, documentNumber);
			invoiceXmlFilePath = urlResolver.urlDownloadDocumentFor(currentBaseStore.getUid(), DOCUMENT_TYPE_XML, documentOwner, documentNumber);
			// compress on a zip file
			compressOnZip = true;
		}
		
		File tempFile = null;
		InputStream input = null;
		OutputStream output = null;
		
		try
		{
//			invoicePdfFilePath = "D:\\Dev\\prolamsa\\downloads\\PROLAMSA_INC_F_1200053463.pdf";
//			invoiceXmlFilePath = "D:\\Dev\\prolamsa\\downloads\\PROLAMSA_INC_F_1200053463.xml";
//			genericTransferPath = "D:\\Dev\\prolamsa\\downloads\\PROLAMSA_INC_F_1200053463.pdf";
			
			if (compressOnZip) 
			{
				List<String> filePaths = new ArrayList<>();
				
				if (!StringUtils.isEmpty(invoiceXmlFilePath) && !invoiceXmlFilePath.equalsIgnoreCase("NO_FOUND")
						&& !invoiceXmlFilePath.equalsIgnoreCase("CUST_NO_FOUND"))
				{
					filePaths.add(invoiceXmlFilePath);
				}	
				
				if(!StringUtils.isEmpty(invoicePdfFilePath) && !invoicePdfFilePath.equalsIgnoreCase("NO_FOUND")
						&& !invoicePdfFilePath.equalsIgnoreCase("CUST_NO_FOUND"))
				{
					filePaths.add(invoicePdfFilePath);
				}
				
				
				if (filePaths.isEmpty())
				{
					throw new Exception("Error while getting documents(s) from SAP, no files to compress.");
				}
				else
				{
					LOG.info("Total documents to compress: " + filePaths.size());
					// download paths and compress on a zip file
					tempFile = fileDownloader.downloadAndZipFilesFromPath(filePaths);					
				}
			}
			else
			{
				if (StringUtils.isEmpty(genericTransferPath) || genericTransferPath.equalsIgnoreCase("NO_FOUND") || 
						genericTransferPath.equalsIgnoreCase("CUST_NO_FOUND"))
				{
					throw new Exception("Error while getting document from SAP.");
				}
				
				// download path
				tempFile = fileDownloader.downloadFileFromPath(genericTransferPath);				
			}
			
			// validate temp file
			if(tempFile == null)
			{
				throw new Exception("Error while generating temp file.");
			}
			
			// define file name and mimeType
			String fileName = "";
			String mimeType = "";
			
			if (compressOnZip) 
			{
				String prefix = resolveMessagePrefixBy(documentType);
				String attachPrefix = getMessageSource().getMessage(prefix + ".file.prefix", null,
						getI18nService().getCurrentLocale());
				
				fileName = attachPrefix + documentNumber + "-" + (new Date()).getTime() + ".zip";
				mimeType = "application/zip";
			}
			else
			{
				fileName =  documentType + "-" + documentNumber + ".pdf";
				mimeType = "application/pdf";
			}
			
			// define mode to transfer document(s)
			if (DOCUMENT_TRANSFER_BY_DOWNLOAD.equalsIgnoreCase(transferType)) 
			{
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
				response.setContentLength((int) tempFile.length());
				
				input = new FileInputStream(tempFile);
				output = response.getOutputStream();
				
				IOUtils.copyLarge(input, output);
			}
			else
			{
				transferDocumentByEmail(tempFile, fileName, documentNumber, documentType, mimeType);
			}
			
		}
		catch (Exception ex)
		{
			LOG.error("Could not download document(s) due to: " + ex.getMessage());
		}
		finally
		{
			if (tempFile != null)
				tempFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
			
			deleteFile(invoicePdfFilePath);
			deleteFile(invoiceXmlFilePath);
			
			deleteFile(genericTransferPath);
		}
	}
	
	private void deleteFile(String path)
	{
		if(StringUtils.isEmpty(path) || path.equalsIgnoreCase("NO_FOUND") || 
				path.equalsIgnoreCase("CUST_NO_FOUND"))
		{
			return;
		}
		
		final String extractPath = configurationService.getConfiguration().getString("sap.invoices.temporaryDirectory.extract");
		String newPath = extractPath + path.substring(path.lastIndexOf("\\") + 1);
		
		File fileToBeDeleted= new File(newPath);
		
		if(fileToBeDeleted.exists())
		{
			if(fileToBeDeleted.delete())
			{
				LOG.info("Deleted file: " + newPath);
			}
			else
			{
				LOG.info("There was an error when trying to delete file: " + newPath);
			}
		}else
		{
			LOG.info("Could not read file: " + newPath);
		}
	}
	
	private void transferDocumentByEmail(final File document, final String fileName, final String documentNumber, final String documentType, final String mimeType) throws Exception
	{
		List<EmailAddressModel> toAddresses = new ArrayList<EmailAddressModel>();
		String to = neorisFacade.getCurrentCustomer().getUid();
		EmailAddressModel emailAddress = emailService.getOrCreateEmailAddressForEmail(to, "");
		emailAddress.setEmailAddress(to);
		toAddresses.add(emailAddress);
		
		String prefix = resolveMessagePrefixBy(documentType);
		
		String subject = getMessageWithDefaultContext(prefix + ".transfer.email.subject", new Object[]{documentNumber});
		String body = getMessageWithDefaultContext(prefix + ".transfer.email.body", new Object[]{documentNumber});
		
		List<EmailAttachmentModel> attachments = new ArrayList<EmailAttachmentModel>();
		EmailAttachmentModel attachmentModel = emailService.createEmailAttachment(new DataInputStream(new FileInputStream(document)), fileName, mimeType);
		attachments.add(attachmentModel);
		
		EmailMessageModel message = emailService.createEmailMessage(toAddresses, null, null, emailAddress, "no-reply@prolamsa.com", subject, body, attachments);
		emailService.send(message);
	}
	
	private String resolveMessagePrefixBy(final String documentType)
	{
		String preffix = "invoice";
		
		if(documentType != null && DOCUMENT_TYPE_QUOTE.equalsIgnoreCase(documentType))
		{
			preffix = "quote";
		}
		
		return preffix;
	}
	
	@RequestMapping(value = "/downloadDocument", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public void downloadDocument(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();
		
		String documentType = request.getParameter("documentType");
		String documentNumber = request.getParameter("documentNumber");
		String documentOwner = request.getParameter("documentOwner");

		String filePath = urlResolver.urlDownloadDocumentFor(currentBaseStore.getUid(), documentType, documentOwner, documentNumber);
		
		InputStream input = null;
		OutputStream output = null;
		File tempFile = null;
		
		try
		{
//			filePath="d:/TMPAXIS_F_1600000053.pdf";
//			filePath="D:\\Dev\\prolamsa\\downloads\\PROLAMSA_INC_F_1200053463.pdf";
			
			if (StringUtils.isEmpty(filePath) || filePath.equalsIgnoreCase("NO_FOUND") ||
					filePath.equalsIgnoreCase("CUST_NO_FOUND"))
			{									        
				throw new Exception("Error while getting document from SAP.");			
			}
			
			tempFile = fileDownloader.downloadFileFromPath(filePath);
			
			if(tempFile == null)
			{
				throw new Exception("Error while generating temp file.");
			}
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + documentType + "-" + documentNumber + ".pdf\";");
			response.setContentLength((int) tempFile.length());

			input = new FileInputStream(tempFile);
			output = response.getOutputStream();

			IOUtils.copyLarge(input, output);
		}
		catch (Exception ex)
		{
			LOG.error("Could not download document due to: " + ex.getMessage());
		}
		finally
		{
			if (tempFile != null)
				tempFile.delete();

			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
			
			deleteFile(filePath);
			
		}
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/downloadMassiveDocuments.json", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public Object downloadMassiveDocuments(@RequestParam("documentsToDownload")
	final String documentsToDownload, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("message", "Template Load");
		node.put("status", AJAX_STATUS_OK);
		
		BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();
		
		System.out.println(documentsToDownload);
		
		String allDownloads = documentsToDownload; //request.getParameter("documentsToDownload");
		
		String[] downloads = allDownloads.split(",");
		
		File zipTempFile = null;
		InputStream input = null;
		OutputStream output = null;
		
		List<String> filePaths = new ArrayList<>();
		
		for(String eachDownload :downloads )
		{
			if(StringUtils.isBlank(eachDownload))
				continue;
			
			String [] docTypeAndNumber = eachDownload.split("_");
			
			String docNumber = docTypeAndNumber[0];
			String docOwner = docTypeAndNumber[1];
			String docType  = docTypeAndNumber[2];
			
			if(StringUtils.isNotBlank(docType) && StringUtils.isNotBlank(docNumber) && StringUtils.isNotBlank(docOwner))
			{
				String filePath = urlResolver.urlDownloadDocumentFor(currentBaseStore.getUid(), docType, docOwner, docNumber);
				
				if (StringUtils.isEmpty(filePath) || filePath.equalsIgnoreCase("NO_FOUND") ||
						filePath.equalsIgnoreCase("CUST_NO_FOUND"))
				{
					LOG.error("Error while getting document " + docNumber + " type " + docType);
					continue;
				}	
				
				filePaths.add(filePath);
			}
		}
		
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(filePaths);
		filePaths.clear();
		filePaths.addAll(hs);
		
		if(filePaths.isEmpty())
		{
			LOG.error("Error while getting documents(s) from SAP, no files to compress.");
			node.put("status", AJAX_STATUS_ERROR);
			
		}
		else
		{
			try
			{
				zipTempFile = fileDownloader.downloadAndZipFilesFromPath(filePaths);
				
				String attachPrefix = getMessageSource().getMessage("invoice.file.prefix", null,
						getI18nService().getCurrentLocale());
				final String fileName = "docs.zip";
				
					response.setContentType("application/zip");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
					response.setContentLength((int) zipTempFile.length());
					
					input = new FileInputStream(zipTempFile);
					output = response.getOutputStream();
					
					IOUtils.copyLarge(input, output);
			}
			catch (Exception ex)
			{
				LOG.error("Could not download document(s) due to: " + ex.getMessage());
				node.put("status", AJAX_STATUS_ERROR);
				node.put("message", ex.getMessage());
			}
			finally
			{
				if (zipTempFile != null)
					zipTempFile.delete();
	
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
				
				for(String eachFilePAth :filePaths)
				{
					deleteFile(eachFilePAth);
				}
			}
		}
		
		
		
		return node;
	}
	
*/
	@RequestMapping(value = "/downloadMassiveDocuments", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public void downloadMassiveDocuments(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();
		
		String allDownloads = request.getParameter("documentsToDownload");
		
		String[] downloads = allDownloads.split(",");
		
		File zipTempFile = null;
		InputStream input = null;
		OutputStream output = null;
		
		List<String> filePaths = new ArrayList<>();
		
		for(String eachDownload :downloads )
		{
			if(StringUtils.isBlank(eachDownload))
				continue;
			
			String [] docTypeAndNumber = eachDownload.split("_");
			
			String docNumber = docTypeAndNumber[0];
			String docOwner = docTypeAndNumber[1];
			String docType  = docTypeAndNumber[2];
			
			if(StringUtils.isNotBlank(docType) && StringUtils.isNotBlank(docNumber) && StringUtils.isNotBlank(docOwner))
			{
				String filePath = null;
						
				if (DOCUMENT_ORDER_DOWNLOAD_ALL.equals(docType))
				{
					// for files attached to the order
					filePath = docNumber + "_" + docType;
				}
				else
				{
					// for files from SAP
					filePath = urlResolver.urlDownloadDocumentFor(currentBaseStore.getUid(), docType, docOwner, docNumber);					
				}
				
				if (StringUtils.isEmpty(filePath) || filePath.equalsIgnoreCase("NO_FOUND") ||
						filePath.equalsIgnoreCase("CUST_NO_FOUND"))
				{
					LOG.error("Error while getting document " + docNumber + " type " + docType);
					continue;
				}	
				
				filePaths.add(filePath);
			}
		}
		
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(filePaths);
		filePaths.clear();
		filePaths.addAll(hs);
		
		if(filePaths.isEmpty())
		{
			LOG.error("Error while getting documents(s) from SAP, no files to compress.");
		}
		else
		{
			try
			{
				zipTempFile = fileDownloader.downloadAndZipFilesFromPath(filePaths);
				
				String attachPrefix = getMessageSource().getMessage("invoice.file.prefix", null,
						getI18nService().getCurrentLocale());
				final String fileName = "docs.zip";
				
					response.setContentType("application/zip");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
					response.setContentLength((int) zipTempFile.length());
					
					input = new FileInputStream(zipTempFile);
					output = response.getOutputStream();
					
					IOUtils.copyLarge(input, output);
			}
			catch (Exception ex)
			{
				LOG.error("Could not download document(s) due to: " + ex.getMessage());
			}
			finally
			{
				if (zipTempFile != null)
					zipTempFile.delete();
	
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
				
				for(String eachFilePAth :filePaths)
				{
					deleteFile(eachFilePAth);
				}	
			}
		}					 
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public void getOrderDocument(HttpServletRequest request, HttpServletResponse response)
	{
		String code = request.getParameter("orderCode");
		String documentCode = request.getParameter("attachmentCode");
		
		if (StringUtils.isEmpty(code)|| StringUtils.isEmpty(documentCode))
			return;

		OrderModel order = neorisFacade.getOrderFromCode(code);
		
		if (order == null)
		{
			LOG.error("Order " + code + " not found.");
			return;			
		}

		List<NeorisMediaModel> orderAttachments = order.getAttachmentsPO();
		InputStream inputStream = null;
		String documentName = null;
		String documentType = null;
		Long documentSize = null;
		
		if (DOCUMENT_ORDER_DOWNLOAD_ALL.equals(documentCode))
		{
			// download all files attached to the order, then pack into zip
			try 
			{
				File zipFile = fileDownloader.packageZipFromMediaList(orderAttachments);
				inputStream = new FileInputStream(zipFile);
				
				documentType = "application/zip";
				documentName = code + "-attachments.zip";
				documentSize = zipFile.length();
			} 
			catch (Exception e) 
			{
				LOG.error("Error while downloading files due to " + e.getMessage());
				return;
			}
		}
		else
		{
			// download particular file attached to the order
			NeorisMediaModel mediaToDownload = null;
			
			if(orderAttachments.size() > 0)
			{
				for(NeorisMediaModel media : orderAttachments)
				{
					if(documentCode.equals(media.getCode()))
					{
						mediaToDownload = media;
						break;
					}
				}
			}
			
			if (mediaToDownload == null)
			{
				LOG.error("Order attachment not found, order " + code);
				return;			
			}
			
			inputStream = mediaService.getStreamFromMedia(mediaToDownload);
			documentType = mediaToDownload.getMime();
			documentName = mediaToDownload.getRealFileName();
			documentSize = mediaToDownload.getSize();
		}
		
		OutputStream outputStream = null;

		try
		{
			response.setContentType(documentType);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + documentName + "\";");
			response.setContentLength(documentSize.intValue());
			
			outputStream = response.getOutputStream();

			IOUtils.copy(inputStream, outputStream);
		}
		catch (Exception ex)
		{
			LOG.error("error while getting document", ex);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	/**
	 * @return the fileDownloader
	 */
	public NeorisURLFileDownloader getFileDownloader()
	{
		return fileDownloader;
	}

	/**
	 * @param fileDownloader the fileDownloader to set
	 */
	public void setFileDownloader(NeorisURLFileDownloader fileDownloader)
	{
		this.fileDownloader = fileDownloader;
	}

	/**
	 * @return the urlResolver
	 */
	public NeorisDocumentDownloadUrlResolver getUrlResolver()
	{
		return urlResolver;
	}

	/**
	 * @param urlResolver the urlResolver to set
	 */
	public void setUrlResolver(NeorisDocumentDownloadUrlResolver urlResolver)
	{
		this.urlResolver = urlResolver;
	}
}
