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
package mx.neoris.storefront.controllers.misc;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.store.BaseStoreModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.exception.NeorisValidationError;
import mx.neoris.core.services.B2BUnitSearchParameters;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.flow.B2BCheckoutFlowFacade;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.B2BUnitSearchForm;
import mx.neoris.storefront.forms.OrderHistorySearchForm;

import org.apache.log4j.Logger;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("tenant")
public class AdvancedClientSearchController extends
		AbstractSearchPageController {
	protected static final Logger LOG = Logger
			.getLogger(AdvancedClientSearchController.class);

	// CMS Pages
	private static final String B2BUNIT_CMS_PAGE = "search";
	private static final String RETURN_CMS_PAGE = "fragments/client/search";
	private static final String RETURN_ADDRESS_CMS_PAGE = "fragments/client/searchAddress";
	private static final String RETURN_ADDRESS_ORDER_HISTORY_CMS_PAGE = "fragments/client/searchAddressOrderHistory";

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name = "b2bCheckoutFlowFacade")
	private B2BCheckoutFlowFacade b2bCheckoutFlowFacade;
	
	public AdvancedClientSearchController() {
		super();
	}

	@RequestMapping(value = "/client/search/list", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getSearchPage(
			@RequestParam(value = "page", defaultValue = "0")
			final int page, @Valid
			final B2BUnitSearchForm searchForm, final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException {

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);

		ShowMode showMode = ShowMode.Page;

		// create PageableData
		final PageableData pageableData = createPageableData(page, 5, null,
				showMode);

		// save search form on model
		model.addAttribute("b2bUnitSearchForm", searchForm);

		// create and fill the search parameters
		B2BUnitSearchParameters searchParameters = new B2BUnitSearchParameters();
		searchParameters.setPageableData(pageableData);
		searchParameters.setUid(searchForm.getUid());
		searchParameters.setName(searchForm.getName());

		// get and set sort information
		String sortCode = searchForm.getSort();

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
		
		// add current customer to search parameters
		B2BCustomerModel currentCustomer = neorisFacade.getCurrentCustomer();
		if (currentCustomer != null)
		{
			searchParameters.setCurrentUserPk(currentCustomer.getPk().toString());
		}

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

				GlobalMessages.addErrorMessage(model,
						"advanced-client-search.error");

				node.put("status", AJAX_STATUS_ERROR);
				node.put("message", ex.getMessage());
			}

		}

		populateModel(model, searchPageData, showMode);
		model.addAttribute("isShowAllAllowed", "false");
		model.addAttribute("isShowPageAllowed", "flase");

		storeCmsPageInModel(model, getContentPageForLabelOrId(B2BUNIT_CMS_PAGE));
		setUpMetaDataForContentPage(model,
				getContentPageForLabelOrId(B2BUNIT_CMS_PAGE));

		return node;
	}

	@RequestMapping(value = "/client/search", method = RequestMethod.GET)
	public String showSearchPage(
			@RequestParam(value = "page", defaultValue = "0")
			final int page, final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException {

		storeCmsPageInModel(model, getContentPageForLabelOrId(B2BUNIT_CMS_PAGE));
		setUpMetaDataForContentPage(model,
				getContentPageForLabelOrId(B2BUNIT_CMS_PAGE));

		return RETURN_CMS_PAGE;
	}

	@RequestMapping(value = "/client/searchAddress/list", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getAddressSearchPage(@Valid
	final B2BUnitSearchForm searchForm, final Model model,
			final HttpServletRequest request,
			final HttpServletResponse response)
			throws CMSItemNotFoundException {

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);

		// save search form on model
		model.addAttribute("b2bUnitSearchForm", searchForm);

		// create and fill the search parameters
		B2BUnitSearchParameters searchParameters = new B2BUnitSearchParameters();
		searchParameters.setUid(searchForm.getUid());
		searchParameters.setName(searchForm.getName());
		searchParameters.setTransportationMode(searchForm.getTransportationMode());		
		
		searchParameters.setPk(getRootUnit().getPk().toString());
		
		List<AddressData> listAddress = new ArrayList<AddressData>();

		try {	
			
			listAddress = neorisFacade.getAddresessForSearch(searchParameters);
			
			// fill up ONLY Shipping address
			if (listAddress.size() > 0) {

				for (AddressData eachAddress : listAddress) {
					ObjectNode unit = JsonNodeFactory.instance.objectNode();

					if (eachAddress.isShippingAddress()) {
						unit.put("id", eachAddress.getId());
						unit.put("code", eachAddress.getCode());
						unit.put("name", eachAddress.getShortName());
						unit.put("city", eachAddress.getTown());
						String state = eachAddress.getRegion() == null ? "" : eachAddress.getRegion().getName();
						unit.put("state", state);
						unit.put("transportationMode", eachAddress.getTransportationMode() == null ? "" : eachAddress.getTransportationMode().getName());
						result.add(unit);
					} else {
						unit.put("id", "");
						unit.put("code", "");
						unit.put("name", "");
						unit.put("city", "");
						unit.put("state", "");
						unit.put("transportationMode", "");

						result.add(unit);
					}

				}

				node.put("units", result);
			}
		} catch (Exception ex) {
			// if other type of error
			LOG.error("Unable to retrieve B2B Units", ex);

			GlobalMessages.addErrorMessage(model,
					"advanced-address-search.error");

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}
	
	@RequestMapping(value = "/client/searchAddress/list/orderHistory", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getAddressOrderHistorySearchPage(
			String customerHistory,
	        @Valid	final B2BUnitSearchForm searchForm, final Model model,
			final HttpServletRequest request,
			final HttpServletResponse response)			
			throws CMSItemNotFoundException {

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);

		// save search form on model
		model.addAttribute("b2bUnitSearchForm", searchForm);

		// create and fill the search parameters
		B2BUnitSearchParameters searchParameters = new B2BUnitSearchParameters();
		searchParameters.setUid(searchForm.getUid());
		searchParameters.setName(searchForm.getName());
		searchParameters.setTransportationMode(searchForm.getTransportationMode());

		List<B2BUnitData> historyB2BUnitData = null;
		List<String> listData = new ArrayList<String>();

		if (customerHistory != null && customerHistory != "") {
			
			if (customerHistory.equalsIgnoreCase("all")) {
				historyB2BUnitData = (List<B2BUnitData>) model.asMap().get("formattedB2BUnits");

				for (B2BUnitData data : historyB2BUnitData) {
					listData.add(neorisFacade.getB2BUnitWithUid(data.getUid()).getPk().toString());
				}
			} else {
				searchParameters.setPk(neorisFacade.getB2BUnitWithUid(customerHistory).getPk().toString());
			}			
			
		} else 
		{
			searchParameters.setPk(getRootUnit().getPk().toString());
		}

		List<AddressData> listAddress = new ArrayList<AddressData>();

		try {
			
			listAddress = neorisFacade.getAddresessForSearch(searchParameters, listData);
			
			// fill up ONLY Shipping address
			if (listAddress.size() > 0) {

				for (AddressData eachAddress : listAddress) {
					ObjectNode unit = JsonNodeFactory.instance.objectNode();

					if (eachAddress.isShippingAddress()) {
						unit.put("id", eachAddress.getId());
						unit.put("code", eachAddress.getCode());
						unit.put("name", eachAddress.getShortName());
						unit.put("city", eachAddress.getTown());
						String state = eachAddress.getRegion() == null ? "" : eachAddress.getRegion().getName();
						unit.put("state", state);
						if (eachAddress.getTransportationMode()!= null) {
							unit.put("transportationMode", eachAddress.getTransportationMode().getName());	
						} else  {
							unit.put("transportationMode", "-");
						}

						result.add(unit);
					} else {
						unit.put("id", "");
						unit.put("code", "");
						unit.put("name", "");
						unit.put("city", "");
						unit.put("state", "");
						unit.put("transportationMode", "");

						result.add(unit);
					}

				}

				node.put("units", result);
			}
		} catch (Exception ex) {
			// if other type of error
			LOG.error("Unable to retrieve B2B Units", ex);

			GlobalMessages.addErrorMessage(model,
					"advanced-address-search.error");

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/client/searchAddress", method = RequestMethod.GET)
	public String showAddressSearchPage(
			@RequestParam(value = "page", defaultValue = "0")
			final int page, Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws CMSItemNotFoundException {

		storeCmsPageInModel(model, getContentPageForLabelOrId(B2BUNIT_CMS_PAGE));
		setUpMetaDataForContentPage(model,
				getContentPageForLabelOrId(B2BUNIT_CMS_PAGE));

		return RETURN_ADDRESS_CMS_PAGE;
	}

	@RequestMapping(value = "/client/searchAddressHistory", method = RequestMethod.GET)
	public String showAddressHistorySearchPage(@RequestParam(value = "customerHistory", defaultValue = "all") final String customerHistory,
			final Model model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException {


		return RETURN_ADDRESS_ORDER_HISTORY_CMS_PAGE;
	}
	
	@RequestMapping(value = "/client/searchAddress/updateAddressSelect", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object updateAddressSelect(@Valid
	final OrderHistorySearchForm searchForm, final Model model,	final HttpServletRequest request, final HttpServletResponse response, String customerHistory)
			throws CMSItemNotFoundException {

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);

		// save search form on model
		model.addAttribute("orderHistorySearchForm", searchForm);

		// create and fill the search parameters
		OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters();
		searchParameters.setCustomer(searchForm.getCustomer());
		
		List<B2BUnitData> historyB2BUnitData = new ArrayList<B2BUnitData>();		
		List<AddressData> listAddressData = new ArrayList<AddressData>();

		
		
		try {
		
			if (customerHistory != null && customerHistory != "")
			{
				
				if (customerHistory.equalsIgnoreCase("all"))
				{
					historyB2BUnitData = (List<B2BUnitData>) model.asMap().get("formattedB2BUnits");
	
					for (B2BUnitData data : historyB2BUnitData) 
					{
						B2BUnitModel b2bUnit = neorisFacade.getB2BUnitWithUid(data.getUid().toString());
						listAddressData.addAll(neorisFacade.getShippingAddress(b2bUnit));
					}
				}
			     else 
			   {
				B2BUnitModel b2bUnit = neorisFacade.getB2BUnitWithUid(customerHistory);
				listAddressData = neorisFacade.getShippingAddress(b2bUnit);			
			   }
			}							
			
			
			// fill up ONLY Shipping address
			if (listAddressData.size() > 0) {

				for (AddressData eachAddress : listAddressData) {
					ObjectNode unit = JsonNodeFactory.instance.objectNode();
					
					unit.put("code", eachAddress.getCode());
					unit.put("name", eachAddress.getFormattedAddress());										
					result.add(unit);				
				}

			}
			node.put("units", result);
		} catch (Exception ex) {
			// if other type of error
			LOG.error("Unable to retrieve B2B Units", ex);

			GlobalMessages.addErrorMessage(model,
					"advanced-address-search.error");

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}
	
	//OrderHistory deliveryAddress mas completo 20052015
	@RequestMapping(value = "/client/searchAddress/updateAddressSelectComplete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object updateAddressSelectComplete(@Valid
	final OrderHistorySearchForm searchForm, final Model model,	final HttpServletRequest request, final HttpServletResponse response, String customerHistory)
			throws CMSItemNotFoundException {

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);

		// save search form on model
		model.addAttribute("orderHistorySearchForm", searchForm);

		// create and fill the search parameters
		OrderHistorySearchParameters searchParameters = new OrderHistorySearchParameters();
		searchParameters.setCustomer(searchForm.getCustomer());
		
		List<B2BUnitData> historyB2BUnitData = new ArrayList<B2BUnitData>();		
		List<AddressData> listAddressData = new ArrayList<AddressData>();

		
		
		try {
		
			if (customerHistory != null && customerHistory != "")
			{
				
				if (customerHistory.equalsIgnoreCase("all"))
				{
					historyB2BUnitData = (List<B2BUnitData>) model.asMap().get("formattedB2BUnits");
	
					for (B2BUnitData data : historyB2BUnitData) 
					{
						B2BUnitModel b2bUnit = neorisFacade.getB2BUnitWithUid(data.getUid().toString());
						listAddressData.addAll(neorisFacade.getShippingAddress(b2bUnit));
					}
				}
			     else 
			   {
				B2BUnitModel b2bUnit = neorisFacade.getB2BUnitWithUid(customerHistory);
				listAddressData = neorisFacade.getShippingAddress(b2bUnit);			
			   }
			}							
			
			
			// fill up ONLY Shipping address
			if (listAddressData.size() > 0) {

				for (AddressData eachAddress : listAddressData) {
					ObjectNode unit = JsonNodeFactory.instance.objectNode();
					
					unit.put("code", eachAddress.getCode());
					
					//////////////////////////////////////
					BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
					String nameBaseStore = baseStore.getUid();

					if (nameBaseStore != null && nameBaseStore.equals("1000")){
						unit.put("name", eachAddress.getFormattedAddress());
					} else if (nameBaseStore != null && nameBaseStore.equals("2000")){
						unit.put("name", eachAddress.getName() + " (" +eachAddress.getCode() + ")" );
					}
					/////////////////////////////////////
					
					unit.put("street", eachAddress.getLine1());
					unit.put("city", eachAddress.getTown());
					String state = eachAddress.getRegion() == null ? "" : eachAddress.getRegion().getName();
					unit.put("state", state);
					result.add(unit);				
				}

			}
			node.put("units", result);
		} catch (Exception ex) {
			// if other type of error
			LOG.error("Unable to retrieve B2B Units", ex);

			GlobalMessages.addErrorMessage(model,
					"advanced-address-search.error");

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}
}
