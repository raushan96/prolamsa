package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.services.B2BUnitSearchParameters;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.B2BUnitSearchForm;

import org.apache.log4j.Logger;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FavoriteCustomersPageController extends AbstractSearchPageController
{
	private static final Logger LOG = Logger.getLogger(FavoriteCustomersPageController.class);
	
	private static final String FAVORITE_CUSTOMERS_CMS_PAGE = "AccountFavoriteCustomersPage";
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@RequestMapping(value = "/my-account/favorite-customers", method = { RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String getFavoriteCustomers(
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode, 
			final Model model,
			final B2BUnitSearchForm b2bFavoriteUnitsForm,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		B2BUnitSearchForm b2bUnitSearchForm = new B2BUnitSearchForm();
		model.addAttribute("b2bUnitSearchForm", b2bUnitSearchForm);
		
		if (!model.containsAttribute("b2bFavoriteUnitsForm"))
		{
			model.addAttribute("b2bFavoriteUnitsForm", new B2BUnitSearchForm());			
		}
		
		String sortCode = (b2bFavoriteUnitsForm != null) ? b2bFavoriteUnitsForm.getSort() : null;
		populateFavoriteCustomers(model, page, showMode, sortCode);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(FAVORITE_CUSTOMERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FAVORITE_CUSTOMERS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.favoriteCustomers"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return getViewForPage(model);
	}
	
	private void populateFavoriteCustomers(final Model model, final int page, final ShowMode showMode, String sortCode)
	{
		// initialize clean pagination
		SearchPageData<B2BUnitData> searchPageData = new SearchPageData<B2BUnitData>();
		searchPageData.setPagination(new PaginationData());

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 20, null,
				showMode);

		B2BUnitSearchParameters searchParameters = new B2BUnitSearchParameters();
		searchParameters.setPageableData(pageableData);
		
		if (sortCode == null)
			sortCode = "uid-asc";

		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null) 
		{
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}

		// add sorts to searchPageData
		String[] sortProps = new String[] {"uid" , "locName"};
		List<SortData> sorts = getSortListFor(sortProps,
				sortCode == null ? "uid-asc" : sortCode, "{0}-(asc)",
				"{0}-(desc)");
		model.addAttribute("sorts", sorts);
		
		// add current customer to search parameters
		B2BCustomerModel currentCustomer = neorisFacade.getCurrentCustomer();
		if (currentCustomer != null)
		{
			searchParameters.setCurrentUserPk(currentCustomer.getPk().toString());
		}
		
		try 
		{
			searchPageData = neorisFacade.getB2BUnitBySearch(searchParameters);
		} 
		catch (Exception e) 
		{
			LOG.error("Unable to retrieve Favorite Customers", e);
			GlobalMessages.addErrorMessage(model, "orderHistory.list.error");
		}
		
		B2BUnitModel rootUnit = neorisFacade.getRootUnit();
		if (rootUnit != null)
		{
			model.addAttribute("currentRootUnitUID", rootUnit.getUid());
		}
		
		populateModel(model, searchPageData, showMode);
	}
	
	@RequestMapping(value = "/my-account/favorite-customers/search", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object searchCustomers(
			@RequestParam(value = "page", defaultValue = "0")
			final int page, @Valid
			final B2BUnitSearchForm unitSearchForm, final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException {

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);

		ShowMode showMode = ShowMode.Page;

		// create PageableData
		final PageableData pageableData = createPageableData(page, 20, null,
				showMode);

		// save search form on model
		model.addAttribute("b2bUnitSearchForm", unitSearchForm);

		// create and fill the search parameters
		B2BUnitSearchParameters searchParameters = new B2BUnitSearchParameters();
		searchParameters.setPageableData(pageableData);
		searchParameters.setUid(unitSearchForm.getUid());
		searchParameters.setName(unitSearchForm.getName());

		// get and set sort information
		String sortCode = unitSearchForm.getSort();

		if (sortCode == null)
			sortCode = "uid-asc";

		String[] sortInfo = getSortByAndOrderFrom(sortCode);
		if (sortInfo != null) {
			searchParameters.setSortBy(sortInfo[0]);
			searchParameters.setSortOrder(sortInfo[1]);
		}

		// add sorts to searchPageData
		String[] sortProps = new String[] { "uid" };
		List<SortData> sorts = getSortListFor(sortProps,
				sortCode == null ? "uid-asc" : sortCode, "{0}-(asc)",
				"{0}-(desc)");
		model.addAttribute("sorts", sorts);

		// initialize clean pagination
		SearchPageData<B2BUnitData> searchPageData = new SearchPageData<B2BUnitData>();
		searchPageData.setPagination(new PaginationData());

		// if sort set, means search has been clicked, do the search now
		if (sortCode != null) {
			try {
				searchParameters.getPageableData().setCurrentPage(0);
				searchParameters.getPageableData().setPageSize(
						Integer.MAX_VALUE);

				searchPageData = neorisFacade
						.getB2BUnitBySearch(searchParameters);

				if (searchPageData.getResults().size() > 0) {
					List<B2BUnitData> units = searchPageData.getResults();

					for (B2BUnitData eachUnit : units) {
						ObjectNode unit = JsonNodeFactory.instance.objectNode();

						unit.put("uid", eachUnit.getUid());
						unit.put("name", eachUnit.getName());

						if (eachUnit.getAddresses().size() > 0) {
							AddressData address = eachUnit.getAddresses().get(0);
							unit.put("city", address.getTown());
							String state = address.getRegion() == null ? "" : address.getRegion().getName();
							unit.put("state", state);
						} else {
							unit.put("city", "");
							unit.put("state", "");
						}

						result.add(unit);
					}

					node.put("units", result);
				}
			} catch (NeorisValidationError exVal) {
				// if validation error
				GlobalMessages.addErrorMessage(model,
						getMessageWithDefaultContext(exVal.getMessage()));
			} catch (Exception ex) {
				// if other type of error
				LOG.error("Unable to retrieve B2B Units", ex);

				node.put("status", AJAX_STATUS_ERROR);
				node.put("message", ex.getMessage());
			}
		}

		return node;
	}
	
	@RequestMapping(value = "/my-account/favorite-customers/remove", method = { RequestMethod.POST })
	public String removeFromFavorites(
			final Model model,
			final String unitUid,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		String errorMessageKey = neorisFacade.removeB2BUnitFromCurrentUser(unitUid);

		if (errorMessageKey == null)
		{
			GlobalMessages.addMessage(model, GlobalMessages.INFO_MESSAGES_HOLDER, "text.account.favoriteCustomers.remove.success", new Object[]{unitUid});
		}
		else
		{
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, errorMessageKey, new Object[]{unitUid});
		}
		
		populateFavoriteCustomers(model, 0, ShowMode.Page, null);
		return ControllerConstants.Views.Fragments.FavoriteCustomers.FavoriteCustomersList;
	}
	
	@RequestMapping(value = "/my-account/favorite-customers/add", method = { RequestMethod.POST })
	public String addToFavorites(
			final Model model,
			final String unitUid,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException 
	{
		String errorMessageKey = neorisFacade.addB2BUnitToCurrentUser(unitUid);
		
		if (errorMessageKey == null)
		{
			GlobalMessages.addMessage(model, GlobalMessages.INFO_MESSAGES_HOLDER, "text.account.favoriteCustomers.add.success", new Object[]{unitUid});
		}
		else
		{
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, errorMessageKey, new Object[]{unitUid});
		}
		
		populateFavoriteCustomers(model, 0, ShowMode.Page, null);
		return ControllerConstants.Views.Fragments.FavoriteCustomers.FavoriteCustomersList;
	}
}
