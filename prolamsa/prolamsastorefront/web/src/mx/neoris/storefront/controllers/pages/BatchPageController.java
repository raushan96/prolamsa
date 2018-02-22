package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.services.BatchSearchParameters;
import mx.neoris.facades.NeorisBatchSearchFacade;
import mx.neoris.facades.document.data.BatchSearchData;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.breadcrumb.impl.DefaultResourceBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.BatchSearchForm;
import mx.neoris.storefront.forms.DocumentSearchForm;
import mx.neoris.storefront.forms.NeorisQuoteSearchForm;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope("tenant")
@RequestMapping(value = "/batch")
public class BatchPageController extends AbstractSearchPageController
{
	protected static final Logger LOG = Logger.getLogger(BatchPageController.class);

	// CMS Pages
	private static final String BATCH_LIST_CMS_PAGE = "BatchListPage";

	@Resource(name = "simpleBreadcrumbBuilder")
	private DefaultResourceBreadcrumbBuilder breadcrumbBuilder;
	
	@Resource(name = "neorisBatchFacade")
	private NeorisBatchSearchFacade neorisBatchFacade;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String searchBatch(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(BATCH_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BATCH_LIST_CMS_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbBuilder.getBreadcrumbs("header.link.batchPage"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/show",  method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String showBatch(@RequestParam(value = "page", defaultValue = "0") final int page,
			BatchSearchForm searchForm,	final Model model,	final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		
		ShowMode showMode = ShowMode.Page;

		// create PageableData
		Integer sapPagSize = SAPConstants.Page.PAGE_SIZE;
		
		final PageableData pageableData = createPageableData(page, sapPagSize, null, showMode);
		
		// add search form on model				
		model.addAttribute("batchSearchForm", searchForm);
		
		//Fase 9 CILS 07Dic2016
		if (StringUtils.isBlank(searchForm.getReference()))
		{
			LOG.error("Unable to retrieve BatchList.");
			GlobalMessages.addErrorMessage(model, "documentSearch.list.reference.batchNumber.mandatory");
			model.addAttribute("batchSearchForm", searchForm);
			
			storeCmsPageInModel(model, getContentPageForLabelOrId(BATCH_LIST_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BATCH_LIST_CMS_PAGE));
			model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbBuilder.getBreadcrumbs("header.link.batchPage"));
			model.addAttribute("metaRobots", "no-index,no-follow");

			return getViewForPage(model);
		}
		
		// set the current base store
		searchForm.setBaseStore(neorisFacade.getCurrentBaseStore().getUid());
		
		// create and fill the search parameters
		BatchSearchParameters searchParameters = new BatchSearchParameters();		
		searchParameters.setBaseStore(searchForm.getBaseStore());
		
		//validate for ALL selection, "" means All customer
		if (searchForm.getCustomer()!= null && searchForm.getCustomer().equals(""))
		{
			List<String> list = new ArrayList<String>();
			for(B2BUnitData b2bUnitData : getFormattedB2BUnits())
				{
					list.add(b2bUnitData.getUid());
				}	
			searchParameters.setListCustomer(list);
		}
		
		searchParameters.setPageableData(pageableData);
		searchParameters.setCustomer(searchForm.getCustomer());
		searchParameters.setBatchType("L");
		searchParameters.setReference(searchForm.getReference());
		
		// get and set sort information
		String sortCode = searchForm.getSort();
		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null)
		{
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}
		
		// add sorts to searchPageData
		String[] sortProps = new String[] {"document.sortby.invoice","document.sortby.mtr","document.sortby.remission"};
		List<SortData> sorts = getSortListFor(sortProps, sortCode == null ? "document.sortby.invoice-asc" : "document.sortby."+sortCode, "{0}-(asc)", "{0}-(desc)");
			
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<BatchSearchData> searchPageData = new SearchPageData<BatchSearchData>();
		searchPageData.setPagination(new PaginationData());
		
		// if sort set, means search has been clicked, do the search now                                                                                                                                                                                                                                                                                                               
		if (sortCode != null)
		{
			try
			{
				searchPageData = neorisBatchFacade.getPagedBatch(searchParameters);
				neorisBatchFacade.injectSOAttachmentsOn(searchPageData.getResults());
			}
			catch (Exception ex)
			{
				// if other type of error
				LOG.error("Unable to retrieve batch search", ex);

				GlobalMessages.addErrorMessage(model, "documentSearch.list.error");
			}
		}
		
		
		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "flase");
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(BATCH_LIST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BATCH_LIST_CMS_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbBuilder.getBreadcrumbs("header.link.batchPage"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
		
		
	}
}
