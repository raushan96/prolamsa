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

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.acceleratorcms.data.RequestContextData;
import de.hybris.platform.acceleratorservices.config.HostConfigService;
import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.storefront.data.MetaElementData;
import de.hybris.platform.acceleratorservices.storefront.util.PageTitleResolver;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.pages.PageTemplateModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.storesession.data.LanguageData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.enums.SiteTheme;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.enums.MeasurementSystemType;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.FavoriteProductSearchParameters;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisFavoriteProductFacade;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.AbstractController;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.ThirdPartyConstants;
import mx.neoris.storefront.controllers.pages.AbstractSearchPageController.ShowMode;
import mx.neoris.storefront.controllers.util.GlobalMessages;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.fest.util.Collections;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.View;


/**
 * Base controller for all page controllers. Provides common functionality for all page controllers.
 */
public abstract class AbstractPageController extends AbstractController
{
	public static final String PAGE_ROOT = "pages/";
	public static final String ROOT = "/";

	public static final String CMS_PAGE_MODEL = "cmsPage";
	public static final String CMS_PAGE_TITLE = "pageTitle";
	
	// NEORIS_CHANGE #54
	public static final String AJAX_STATUS_OK = "0";
	public static final String AJAX_STATUS_ERROR = "1";
	
	// NEORIS_CHANGE #89
	protected static final String CALLBACK_FUNCTION_NAME_PARAMETER = "_callbackFunctionName";

	public static final String PRODCUT_CATALOG = "ProductCatalog";
	public static final String CATALOG_VERSION = "Online";
	
	@Resource(name = "cmsSiteService")
	private CMSSiteService cmsSiteService;

	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "pageTitleResolver")
	private PageTitleResolver pageTitleResolver;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "hostConfigService")
	private HostConfigService hostConfigService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	//NEORIS_CHANGE #48
	@Resource(name = "neorisFacade")
	protected NeorisFacade neorisFacade;
	
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	
	@Resource(name = "catalogVersionService")
	protected CatalogVersionService catalogVersionService;
	
	@Resource(name = "neorisFavoriteProductFacade")
	protected NeorisFavoriteProductFacade neorisFavoriteProductFacade;

	protected CMSSiteService getCmsSiteService()
	{
		return cmsSiteService;
	}

	protected CMSPageService getCmsPageService()
	{
		return cmsPageService;
	}

	protected StoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	protected UserFacade getUserFacade()
	{
		return userFacade;
	}

	protected SessionService getSessionService()
	{
		return sessionService;
	}

	protected MessageSource getMessageSource()
	{
		return messageSource;
	}

	protected I18NService getI18nService()
	{
		return i18nService;
	}

	protected HostConfigService getHostConfigService()
	{
		return hostConfigService;
	}

	protected CustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}

	protected SiteConfigService getSiteConfigService()
	{
		return siteConfigService;
	}
	
	@ModelAttribute("languages")
	public Collection<LanguageData> getLanguages()
	{
		return storeSessionFacade.getAllLanguages();
	}
	
	@ModelAttribute("favoriteProductsCount")
	public long getFavoriteProdcutsCount()
	{	
		FavoriteProductSearchParameters searchParameters = new FavoriteProductSearchParameters();
		B2BUnitModel customer = neorisFacade.getRootUnit();
		if(customer == null)
			return 0;

		searchParameters.setCustomer(customer);
		
		final String catalogId = neorisFacade.getCurrentBaseStore().getUid() + PRODCUT_CATALOG;
		CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(catalogId, CATALOG_VERSION);
		searchParameters.setCatalogVersion(catalogVersion);
		
		return neorisFavoriteProductFacade.getFavoriteProductsCount(searchParameters);
	}
	@ModelAttribute("isInternalCustomer")
	public boolean isInternalCustomer()
	{
		return neorisFacade.isInternalCustomer();
	}
	
	@ModelAttribute("cartProductLocation")
	public String getCartProductLocation()
	{
		CartModel cart = neorisFacade.getCurrentCart();
		
		if (cart == null)
			return "";
		
		if (cart.getEntries().size() == 0)
			return "";
		
		ProlamsaProductModel product = (ProlamsaProductModel) cart.getEntries().get(0).getProduct();
		
		return product.getLocation().getCode();
	}
	
	@ModelAttribute("cartHasAPIProducts")
	public Boolean getCartHasAPIProducts()
	{
		CartModel cart = neorisFacade.getCurrentCart();
		
		if (cart == null)
			return false;
		
		if (cart.getEntries().size() == 0)
			return false;
		
		return cart.getHasAPIProducts();
	}

	@ModelAttribute("uiIdleTimeoutSeconds")
	public Integer getUIIdleTimeoutSeconds()
	{
		return getSiteConfigService().getInt(ControllerConstants.UIIdleTimeoutSeconds, 60 * 5);
	}

	@ModelAttribute("currencies")
	public Collection<CurrencyData> getCurrencies()
	{
		return storeSessionFacade.getAllCurrencies();
	}

	@ModelAttribute("currentDraftName")
	public String getDraftName()
	{
		Wishlist2Model wishlist = sessionService.getAttribute(ControllerConstants.Templates.DRAFT);
		
		if (wishlist == null)
			return "";
		else
			return wishlist.getName();
	}

	@ModelAttribute("currentLanguage")
	public LanguageData getCurrentLanguage()
	{
		if(baseStoreService.getCurrentBaseStore().getUid().equalsIgnoreCase("6000"))
		{
			storeSessionFacade.setCurrentLanguage(baseStoreService.getCurrentBaseStore().getDefaultLanguage().getIsocode());
		}else
		{
			B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
			if(currentCustomerModel != null)
			{
				if(currentCustomerModel.getCustomerCurrentLenguaje() != null)
				{
					storeSessionFacade.setCurrentLanguage(currentCustomerModel.getCustomerCurrentLenguaje());
				}
			}
			
			
		}
		return storeSessionFacade.getCurrentLanguage();
	}

	@ModelAttribute("currentCurrency")
	public CurrencyModel getCurrentCurrency()
	{
		return neorisFacade.getCurrentCurrency();
	}

	@ModelAttribute("siteName")
	public String getSiteName()
	{
		final CMSSiteModel site = cmsSiteService.getCurrentSite();
		return site != null ? site.getName() : "";
	}

	@ModelAttribute("user")
	public CustomerData getUser()
	{
		return customerFacade.getCurrentCustomer();
	}
	
	
	@ModelAttribute("inventoryQtyAPIMin")
	public Integer getInventoryQtyAPIMin()
	{
		BaseStoreModel currentBaseStoreModel = neorisFacade.getCurrentBaseStore();
		if(currentBaseStoreModel != null && currentBaseStoreModel.getInventoryQtyFtAPIMin() != null)
		{
			return currentBaseStoreModel.getInventoryQtyFtAPIMin();
		}
		
		return 0;
	}
	
	@ModelAttribute("inventoryQtyAPIMax")
	public Integer getInventoryQtyAPIMax()
	{
		BaseStoreModel currentBaseStoreModel = neorisFacade.getCurrentBaseStore();
		if(currentBaseStoreModel != null && currentBaseStoreModel.getInventoryQtyFtAPIMax() != null)
		{
			return currentBaseStoreModel.getInventoryQtyFtAPIMax();
		}
		
		return 0;
	}
	
	@ModelAttribute("rollingQtyAPIMin")
	public Integer getRollingQtyAPIMin()
	{
		BaseStoreModel currentBaseStoreModel = neorisFacade.getCurrentBaseStore();
		if(currentBaseStoreModel != null && currentBaseStoreModel.getRollingQtyFtAPIMin() != null)
		{
			return currentBaseStoreModel.getRollingQtyFtAPIMin();
		}
		
		return 0;
	}
	
	@ModelAttribute("rollingQtyAPIMax")
	public Integer getRollingQtyAPIMax()
	{
		BaseStoreModel currentBaseStoreModel = neorisFacade.getCurrentBaseStore();
		if(currentBaseStoreModel != null && currentBaseStoreModel.getRollingQtyFtAPIMax() != null)
		{
			return currentBaseStoreModel.getRollingQtyFtAPIMax();
		}
		
		return 0;
	}

	//NEORIS_CHANGE #54
	public String getMessageWithDefaultContext(String resourceKey)
	{
		return getMessageWithDefaultContext(resourceKey, null);
	}

	public String getMessageWithDefaultContext(String resourceKey, Object[] params)
	{
		String message = resourceKey;
		
		try
		{
			message = messageSource.getMessage(resourceKey, params, getI18nService().getCurrentLocale());
		}
		catch (Exception ex)
		{
			// do not raise any error on messages not found
		}
		
		return message;
	}

	//NEORIS_CHANGE #102
	@ModelAttribute("baseStore")
	public BaseStoreModel getCurrentBaseStore()
	{
		return neorisFacade.getCurrentBaseStore();
	}

	//NEORIS_CHANGE #48
	@ModelAttribute("formattedB2BUnits")
	public List<B2BUnitData> getFormattedB2BUnits()
	{
		return neorisFacade.getFormattedB2BUnits();
	}
	
	//NEORIS_CHANGE #48
	@ModelAttribute("rootunit")
	public B2BUnitModel getRootUnit()
	{
		return sessionService.getAttribute(NeorisFacade.B2BUNIT_SLOT);
	}

	//NEORIS_CHANGE #41
	@ModelAttribute("units")
	public List<UnitModel> getAllUnits()
	{
		return neorisFacade.getAllUnits();
	}
	
	@ModelAttribute("currentSystem")
	public HybrisEnumValue getCurrentSystem()
	{
		UnitModel currentUnit = neorisFacade.getCurrentUnit();
		
		if(currentUnit != null)
		{
			if(neorisFacade.getUnitBySystem("English").contains(currentUnit))
				return MeasurementSystemType.ENGLISH;
			else if(neorisFacade.getUnitBySystem("Metric").contains(currentUnit))
				return MeasurementSystemType.METRIC;
		}
		
		return neorisFacade.getCurrentSystem();
	}
	
	@ModelAttribute("systemList")
	public List<HybrisEnumValue> getSystemList()
	{					
		List<HybrisEnumValue> MeasurementSystemType = enumerationService.getEnumerationValues("MeasurementSystemType");					
		return  MeasurementSystemType;
	}
	
		
	@ModelAttribute("unitsByEnglishSystem")
	public List<UnitModel> getUnitsByEnglishSystem()
	{
		return neorisFacade.getUnitBySystem("English");
	}
	
	@ModelAttribute("unitsByMetricSystem")
	public List<UnitModel> getUnitsByMetricSystem()
	{
		return neorisFacade.getUnitBySystem("Metric");
	}

	
	//NEORIS_CHANGE #41
	@ModelAttribute("unit")
	public UnitModel getUnit()
	{
		return neorisFacade.getCurrentUnit();
	}
	
	@ModelAttribute("priceNumberPattern")
	public String getPriceNumberPattern()
	{
		return neorisCartPriceHelper.getPriceNumberPattern();
	}
	
	@ModelAttribute("unitPriceNumberPattern")
	public String unitPriceNumberPattern()
	{
		return neorisCartPriceHelper.getUnitPriceNumberPattern();
	}
	
	@ModelAttribute("weightNumberPattern")
	public String getWeightNumberPattern()
	{
		return neorisCartPriceHelper.getWeightNumberPattern();
	}
	
	@ModelAttribute("quantityPattern")
	public String getQuantityPattern()
	{
		return neorisCartPriceHelper.getQuantityPattern(neorisFacade.getCurrentUnit());
	}
	
	protected void addOrderWeightPatterToModel(final Model model, final OrderData orderData)
	{
		model.addAttribute("orderWeightPattern", neorisCartPriceHelper.getOrderWeightPatterFor(orderData));
	}
	
	protected void addOrderQuantityPatternToModel(final Model model, final OrderData orderData)
	{
		model.addAttribute("orderQtyPattern", neorisCartPriceHelper.getQuantityPattern(orderData.getUnitWhenPlaced()));
	}
	
	public String getThemeNameForCurrentSite()
	{		
		final CMSSiteModel site = cmsSiteService.getCurrentSite();
		
		if(site != null)
		{
			final SiteTheme theme = site.getTheme();
			if (theme != null)
			{
				final String themeCode = theme.getCode();
				if (themeCode != null && !themeCode.isEmpty())
				{
					return themeCode;
				}
			}
		}
		
		return "new";
	}
	
	// get sort info from sort code
	// like name-asc, name-desc, description-asc, description-desc
	public String[] getSortByAndOrderFrom(String sortCode)
	{
		String[] res = null;

		if (sortCode != null)
		{
			res = sortCode.split("-");

			if (res.length < 2)
				res = null;
		}

		return res;
	}

	// get the sort data elements for possible sort properties
	// default include ascending, descending
	// provide a localized strings for ascending, descending messages patterns
	public List<SortData> getSortListFor(final String[] sortProperties, final String selectedSortCode,
			final String ascString, final String descString)
	{
		final List<SortData> sorts = new ArrayList<SortData>();

		final List<String> sortList = Collections.list(sortProperties);

		for (final String eachProp : sortList)
		{
			String[] codeAndLabel = eachProp.split(",");
			
			String property = codeAndLabel[0];
			String label = property;

			if (codeAndLabel.length > 1)
			{
				label = codeAndLabel[1];
			}

			Object[] params = new Object[] {getMessageWithDefaultContext(label)};

			SortData sortData = null;
			sortData = new SortData();
			sortData.setName(getMessageWithDefaultContext(ascString, params));
			String code = property + "-asc";
			sortData.setCode(code);
			sortData.setSelected(code.equals(selectedSortCode));
			sorts.add(sortData);
			sortData = new SortData();
			sortData.setName(getMessageWithDefaultContext(descString, params));
			code = property + "-desc";
			sortData.setCode(code);
			sortData.setSelected(code.equals(selectedSortCode));
			sorts.add(sortData);
		}

		return sorts;
	}

	@ModelAttribute("googleAnalyticsTrackingId")
	public String getGoogleAnalyticsTrackingId(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Google.ANALYTICS_TRACKING_ID, request.getServerName());
	}


	@ModelAttribute("jirafeApiUrl")
	public String getJirafeApiUrl(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.API_URL, request.getServerName());
	}

	@ModelAttribute("jirafeApiToken")
	public String getJirafeApitoken(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.API_TOKEN, request.getServerName());
	}

	@ModelAttribute("jirafeApplicationId")
	public String getJirafeApplicationId(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.APPLICATION_ID, request.getServerName());
	}

	@ModelAttribute("jirafeSiteId")
	public String getJirafeSiteId(final HttpServletRequest request)
	{
		String propertyValue = getHostConfigService().getProperty(
				ThirdPartyConstants.Jirafe.SITE_ID + "." + storeSessionFacade.getCurrentCurrency().getIsocode().toLowerCase(),
				request.getServerName());
		if ("".equals(propertyValue))
		{
			propertyValue = getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.SITE_ID, request.getServerName());
		}
		return propertyValue;
	}

	@ModelAttribute("jirafeVersion")
	public String getJirafeVersion(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.VERSION, request.getServerName());
	}

	@ModelAttribute("jirafeDataUrl")
	public String getJirafeDataUrl(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.DATA_URL, request.getServerName());
	}

	protected void storeCmsPageInModel(final Model model, final AbstractPageModel cmsPage)
	{
		if (model != null && cmsPage != null)
		{
			model.addAttribute(CMS_PAGE_MODEL, cmsPage);
			if (cmsPage instanceof ContentPageModel)
			{
				storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(cmsPage.getTitle()));
			}
		}
	}

	protected void storeContentPageTitleInModel(final Model model, final String title)
	{
		model.addAttribute(CMS_PAGE_TITLE, title);
	}

	protected String getViewForPage(final Model model)
	{
		if (model.containsAttribute(CMS_PAGE_MODEL))
		{
			final AbstractPageModel page = (AbstractPageModel) model.asMap().get(CMS_PAGE_MODEL);
			if (page != null)
			{
				return getViewForPage(page);
			}
		}
		return null;
	}

	protected String getViewForPage(final AbstractPageModel page)
	{
		if (page != null)
		{
			final PageTemplateModel masterTemplate = page.getMasterTemplate();
			if (masterTemplate != null)
			{
				final String targetPage = cmsPageService.getFrontendTemplateName(masterTemplate);
				if (targetPage != null && !targetPage.isEmpty())
				{
					return PAGE_ROOT + targetPage;
				}
			}
		}
		return null;
	}

	/**
	 * Checks request URL against properly resolved URL and returns null if url is proper or redirection string if not.
	 * 
	 * @param request
	 *           - request that contains current URL
	 * @param response
	 *           - response to write "301 Moved Permanently" status to if redirected
	 * @param resolvedUrlPath
	 *           - properly resolved URL
	 * @return null if url is properly resolved or redirection string if not
	 * @throws UnsupportedEncodingException
	 */
	protected String checkRequestUrl(final HttpServletRequest request, final HttpServletResponse response,
			final String resolvedUrlPath) throws UnsupportedEncodingException
	{
		try
		{
			final String resolvedUrl = response.encodeURL(request.getContextPath() + resolvedUrlPath);
			final String requestURI = URIUtil.decode(request.getRequestURI(), "utf-8");
			final String decoded = URIUtil.decode(resolvedUrl, "utf-8");
			if (StringUtils.isNotEmpty(requestURI) && requestURI.endsWith(decoded))
			{
				return null;
			}
			else
			{
				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.MOVED_PERMANENTLY);
				final String queryString = request.getQueryString();
				if (queryString != null && !queryString.isEmpty())
				{
					return "redirect:" + resolvedUrlPath + "?" + queryString;
				}
				return "redirect:" + resolvedUrlPath;
			}
		}
		catch (final URIException e)
		{
			throw new UnsupportedEncodingException();
		}
	}

	protected ContentPageModel getContentPageForLabelOrId(final String labelOrId) throws CMSItemNotFoundException
	{
		String key = labelOrId;
		if (StringUtils.isEmpty(labelOrId))
		{
			// Fallback to site home page
			final ContentPageModel homePage = cmsPageService.getHomepage();
			if (homePage != null)
			{
				key = cmsPageService.getLabelOrId(homePage);
			}
			else
			{
				// Fallback to site start page label
				final CMSSiteModel site = cmsSiteService.getCurrentSite();
				if (site != null)
				{
					key = cmsSiteService.getStartPageLabelOrId(site);
				}
			}
		}

		// Actually resolve the label or id - running cms restrictions
		return cmsPageService.getPageForLabelOrId(key);
	}

	protected PageTitleResolver getPageTitleResolver()
	{
		return pageTitleResolver;
	}

	protected void storeContinueUrl(final HttpServletRequest request)
	{
		final StringBuilder url = new StringBuilder();
		url.append(request.getServletPath());
		final String queryString = request.getQueryString();
		if (queryString != null && !queryString.isEmpty())
		{
			url.append('?').append(queryString);
		}
		getSessionService().setAttribute(WebConstants.CONTINUE_URL, url.toString());
	}

	protected void setUpMetaData(final Model model, final String metaKeywords, final String metaDescription)
	{
		final List<MetaElementData> metadata = new LinkedList<MetaElementData>();
		metadata.add(createMetaElement("keywords", metaKeywords));
		metadata.add(createMetaElement("description", metaDescription));
		model.addAttribute("metatags", metadata);
	}

	protected MetaElementData createMetaElement(final String name, final String content)
	{
		final MetaElementData element = new MetaElementData();
		element.setName(name);
		element.setContent(content);
		return element;
	}

	protected void setUpMetaDataForContentPage(final Model model, final ContentPageModel contentPage)
	{
		setUpMetaData(model, contentPage.getKeywords(), contentPage.getDescription());
	}

	protected RequestContextData getRequestContextData(final HttpServletRequest request)
	{
		return getBean(request, "requestContextData", RequestContextData.class);
	}
	
	// NEORIS_CHANGE #89
	public String getCallbackFunctionNameFrom(HttpServletRequest request)
	{
		return request.getParameter(CALLBACK_FUNCTION_NAME_PARAMETER);
	}

	public void populateModelForIFrame(String functionName, Object object, Map<String,Object> model) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";

        jsonStr = mapper.writeValueAsString(object);

        model.put("functionName", functionName);
        model.put("parameter", jsonStr);
    }
	
	@ModelAttribute("weightKgPattern")
	public String getWeightKgPattern()
	{
		return neorisCartPriceHelper.getWeightKgNumberPattern();
	}
	
	@ModelAttribute("weightTonPattern")
	public String getWeightTonPattern()
	{
		return neorisCartPriceHelper.getTonPattern();
	}
}
