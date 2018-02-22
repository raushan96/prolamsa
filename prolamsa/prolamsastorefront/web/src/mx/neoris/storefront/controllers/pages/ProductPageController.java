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

import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.b2bacceleratorfacades.futurestock.B2BFutureStockFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.FutureStockData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.core.commercefacades.search.NeorisProductSearchFacade;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.search.B2BProductSearchFacade;
import mx.neoris.storefront.breadcrumb.impl.ProductBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.FutureStockForm;
import mx.neoris.storefront.forms.ReviewForm;
import mx.neoris.storefront.util.MetaSanitizerUtil;
import mx.neoris.storefront.util.XSSFilterUtil;
import mx.neoris.storefront.variants.VariantSortStrategy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;

/**
 * Controller for product details page.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/**/p")
public class ProductPageController extends AbstractPageController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ProductPageController.class);

	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a
	 * Uri value is incorrectly extracted if it contains on or more '.'
	 * characters. Please see https://jira.springsource.org/browse/SPR-6164 for
	 * a discussion on the issue and future resolution.
	 */
	private static final String PRODUCT_CODE_PATH_VARIABLE_PATTERN = "/{productCode:.*}";
	private static final String REVIEWS_PATH_VARIABLE_PATTERN = "{numberOfReviews:.*}";

	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";
	private static final String STOCK_SERVICE_UNAVAILABLE = "basket.page.viewFuture.unavailable";
	private static final String NOT_MULTISKU_ITEM_ERROR = "basket.page.viewFuture.not.multisku";

	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name = "productModelUrlResolver")
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "productBreadcrumbBuilder")
	private ProductBreadcrumbBuilder productBreadcrumbBuilder;

	@Resource(name = "variantSortStrategy")
	private VariantSortStrategy variantSortStrategy;

	@Resource(name = "b2bFutureStockFacade")
	private B2BFutureStockFacade b2bFutureStockFacade;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	@Resource(name = "b2bProductFlexibleSearchFacade")
	private B2BProductSearchFacade<ProductData> b2bProductFlexibleSearchFacade;
	
	@Resource(name = "productConverter")
	private AbstractConverter<ProductModel, ProductData> productConverter;
	
	@Resource(name = "productSearchFacade")
	private NeorisProductSearchFacade<ProductData> productSearchFacade;

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String productDetail(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException, UnsupportedEncodingException
	{
		final ProductModel productModel = productService.getProductForCode(productCode);

		final String redirection = checkRequestUrl(request, response, productModelUrlResolver.resolve(productModel));
		if (StringUtils.isNotEmpty(redirection))
		{
			return redirection;
		}

		// check the selLocation parameter
		String selLocation = request.getParameter("selLocation");

		if (selLocation == null)
		{
			ProlamsaProductModel pp = (ProlamsaProductModel) productModel;
			selLocation = pp.getLocation().getCode();
		}

		model.addAttribute("selLocation", selLocation);

		updatePageTitle(productModel, model);
		populateProductDetailForDisplay(productModel, model, request);
		model.addAttribute(new ReviewForm());
		model.addAttribute("pageType", PageType.PRODUCT.name());
		model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));
		
		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		
		// add flag to include rate js library for product favorites
		model.addAttribute("activateJQRateFronEndLibraries", true);
		
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);

		return getViewForPage(model);
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/orderForm", method = RequestMethod.GET)
	public String productOrderForm(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final ProductModel productModel = productService.getProductForCode(productCode);

		updatePageTitle(productModel, model);
		populateProductDetailForDisplay(productModel, model, request);

		if (!model.containsAttribute(WebConstants.MULTI_DIMENSIONAL_PRODUCT))
		{
			return REDIRECT_PREFIX + productModelUrlResolver.resolve(productModel);
		}

		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);

		return ControllerConstants.Views.Pages.Product.OrderForm;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/futureStock", method = RequestMethod.GET)
	public String productFutureStock(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request, final HttpServletResponse response)
	{
		final boolean futureStockEnabled = Config.getBoolean(FUTURE_STOCK_ENABLED, false);
		if (futureStockEnabled)
		{
			final ProductModel productModel = productService.getProductForCode(productCode);
			final ProductData productData = productFacade.getProductForOptions(productModel, Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.CATEGORIES, ProductOption.PROMOTIONS,
					ProductOption.STOCK, ProductOption.REVIEW, ProductOption.VOLUME_PRICES, ProductOption.VARIANT_MATRIX));

			populateProductData(productData, model, request);

			final List<FutureStockData> futureStockList = b2bFutureStockFacade.getFutureAvailability(productModel);
			if (futureStockList == null)
			{
				GlobalMessages.addErrorMessage(model, STOCK_SERVICE_UNAVAILABLE);
			}
			else
				if (futureStockList.isEmpty())
				{
					GlobalMessages.addErrorMessage(model, "product.product.details.future.nostock");
				}

			model.addAttribute("futureStocks", futureStockList);

			final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
			final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
			setUpMetaData(model, metaKeywords, metaDescription);

			return ControllerConstants.Views.Fragments.Product.FutureStockPopup;
		}
		else
		{
			return ControllerConstants.Views.Pages.Error.ErrorNotFoundPage;
		}

	}

	@ResponseBody
	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/grid/skusFutureStock", method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public final Map<String, Object> productSkusFutureStock(final FutureStockForm form, final Model model)
	{
		final String productCode = form.getProductCode();
		final List<String> skus = form.getSkus();
		final boolean futureStockEnabled = Config.getBoolean(FUTURE_STOCK_ENABLED, false);

		Map<String, Object> result = new HashMap<>();
		if (futureStockEnabled && CollectionUtils.isNotEmpty(skus) && StringUtils.isNotBlank(productCode))
		{
			// retrieve the variants list
			final Set<String> skusSet = new HashSet<String>(skus);
			final ProductModel productModel = productService.getProductForCode(productCode);
			List<ProductModel> variantsList = null;
			if (CollectionUtils.isNotEmpty(productModel.getVariants()))
			{
				variantsList = getSelectedProductModels(skusSet, productModel.getVariants());
			}
			else
				if (productModel instanceof VariantProductModel)
				{
					variantsList = getSelectedProductModels(skusSet, ((VariantProductModel) productModel).getBaseProduct().getVariants());
				}

			if (CollectionUtils.isNotEmpty(variantsList))
			{
				final Map<String, List<FutureStockData>> futureStockData = b2bFutureStockFacade.getFutureAvailability(variantsList);
				if (futureStockData == null)
				{
					// future availability service is down, we show this to the
					// user
					result = Maps.newHashMap();
					final String errorMessage = getMessageSource().getMessage(STOCK_SERVICE_UNAVAILABLE, null, getI18nService().getCurrentLocale());
					result.put(STOCK_SERVICE_UNAVAILABLE, errorMessage);
				}
				else
				{
					for (final String key : futureStockData.keySet())
					{
						result.put(key, futureStockData.get(key));
					}
				}
			}
			else
			{
				GlobalMessages.addErrorMessage(model, NOT_MULTISKU_ITEM_ERROR);
			}
		}
		return result;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/zoomImages", method = RequestMethod.GET)
	public String showZoomImages(@PathVariable("productCode")
	final String productCode, @RequestParam(value = "galleryPosition", required = false)
	final String galleryPosition, final Model model)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final ProductData productData = productFacade.getProductForOptions(productModel, Collections.singleton(ProductOption.GALLERY));
		final List<Map<String, ImageData>> images = getGalleryImages(productData);
		model.addAttribute("galleryImages", images);
		model.addAttribute("product", productData);
		if (galleryPosition != null)
		{
			try
			{
				model.addAttribute("zoomImageUrl", images.get(Integer.parseInt(galleryPosition)).get("zoom").getUrl());
			}
			catch (final IndexOutOfBoundsException ignore)
			{
				model.addAttribute("zoomImageUrl", "");
			}
			catch (final NumberFormatException ignore)
			{
				model.addAttribute("zoomImageUrl", "");
			}
		}
		return ControllerConstants.Views.Fragments.Product.ZoomImagesPopup;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/quickView", method = RequestMethod.GET)
	public String showQuickView(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final ProductData productData = productFacade.getProductForOptions(productModel,
				Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.CATEGORIES, ProductOption.PROMOTIONS, ProductOption.STOCK, ProductOption.REVIEW, ProductOption.VOLUME_PRICES));

		populateProductData(productData, model, request);
		getRequestContextData(request).setProduct(productModel);

		return ControllerConstants.Views.Fragments.Product.QuickViewPopup;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/review", method = RequestMethod.POST)
	public String postReview(@PathVariable
	final String productCode, @Valid
	final ReviewForm form, final BindingResult result, final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttrs) throws CMSItemNotFoundException
	{
		final ProductModel productModel = productService.getProductForCode(productCode);

		if (result.hasErrors())
		{
			updatePageTitle(productModel, model);
			GlobalMessages.addErrorMessage(model, "review.general.error");
			model.addAttribute("showReviewForm", Boolean.TRUE);
			populateProductDetailForDisplay(productModel, model, request);
			storeCmsPageInModel(model, getPageForProduct(productModel));
			return getViewForPage(model);
		}

		final ReviewData review = new ReviewData();
		review.setHeadline(XSSFilterUtil.filter(form.getHeadline()));
		review.setComment(XSSFilterUtil.filter(form.getComment()));
		review.setRating(form.getRating());
		review.setAlias(XSSFilterUtil.filter(form.getAlias()));
		productFacade.postReview(productCode, review);
		GlobalMessages.addFlashMessage(redirectAttrs, GlobalMessages.CONF_MESSAGES_HOLDER, "review.confirmation.thank.you.title");
		return "redirect:" + productModelUrlResolver.resolve(productModel);
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/reviewhtml/" + REVIEWS_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String reviewHtml(@PathVariable("productCode")
	final String productCode, @PathVariable("numberOfReviews")
	final String numberOfReviews, final Model model, final HttpServletRequest request)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final List<ReviewData> reviews;

		if ("all".equals(numberOfReviews))
		{
			reviews = productFacade.getReviews(productCode);
		}
		else
		{
			reviews = productFacade.getReviews(productCode, Integer.valueOf(numberOfReviews));
		}

		getRequestContextData(request).setProduct(productModel);
		model.addAttribute("reviews", reviews);
		model.addAttribute("reviewsTotal", productModel.getNumberOfReviews());
		model.addAttribute(new ReviewForm());

		return ControllerConstants.Views.Fragments.Product.ReviewsTab;
	}

	protected void updatePageTitle(final ProductModel productModel, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveProductPageTitle(productModel));
	}

	@ExceptionHandler(UnknownIdentifierException.class)
	public String handleUnknownIdentifierException(final UnknownIdentifierException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
	}

	protected void populateProductDetailForDisplay(final ProductModel productModel, final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		getRequestContextData(request).setProduct(productModel);

		storeCmsPageInModel(model, getPageForProduct(productModel));
		// NEORIS_CHANGE #57
		model.addAttribute("productDetail", productModel);
		// final Locale currentLocale =
		// sessionService.getCurrentSession().getAttribute("locale");

		ProductData productData = null;
		List<ProductData> productDataList = new ArrayList<ProductData>();

		// if prolamsa product, search for the products with the same base code, as they will be shown on the product page
		if (productModel instanceof ProlamsaProductModel)
		{
			ProlamsaProductModel pProduct = (ProlamsaProductModel) productModel;
			
			// search products with the same base code
			final List<String> baseSkuList = new ArrayList<String>();
			baseSkuList.add(pProduct.getBaseCode());
			
			PageableData pageableData = new PageableData();
			pageableData.setCurrentPage(0);
			pageableData.setPageSize(500);
//			pageableData.setPageSize(Integer.MAX_VALUE);
			
			//Christian Loredo 12022015
			//Add parameter CatlogId to search CustomerProductReference
			String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";
			
			// get products from the baseCode using SOLR instead flexible search
			
//			List<String> productProlamsaList = neorisFacade.getProlamsaSkuFromSku(baseSkuList, getRootUnit(), catalogId);
//			ProductSearchPageData<SearchStateData, ProductData> searchPageData = b2bProductFlexibleSearchFacade.searchForSkus(productProlamsaList, pageableData);
			
			List<String> baseCodeList = new ArrayList<String>();
			baseCodeList.add(pProduct.getBaseCode());			
			ProductSearchPageData<SearchStateData, ProductData> searchPageData = productSearchFacade.skusSearch(baseCodeList, new SearchStateData(), pageableData);
						
			productDataList = searchPageData.getResults();
			
			// set the productData variable to the product that matches the exact product code
			for (ProductData eachProductData : productDataList)
			{
				if (eachProductData.getCode().equals(productModel.getCode()))
					productData = eachProductData;
			}

			// get the related products from the product model
			List<ProductData> relatedProductDataList = new ArrayList<ProductData>();
			if (pProduct.getRelatedProductCodes().size() > 0)
			{
				searchPageData = b2bProductFlexibleSearchFacade.searchForSkus(pProduct.getRelatedProductCodes(), pageableData);
				relatedProductDataList = searchPageData.getResults();
			}
			model.addAttribute("relatedProductList", relatedProductDataList);
		}
		else
		{
			// if no prolamsa product, do the regular search
			productData = productFacade.getProductForOptions(productModel, Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES,
					ProductOption.REVIEW, ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL, ProductOption.STOCK, ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE, ProductOption.VARIANT_MATRIX));
		
		}
		
		sortVariantOptionData(productData);

		// NEORIS_CHANGE #38
		neorisProductFacade.injectProductInventoryEntriesOn(productDataList, neorisFacade.getCurrentCustomerType());

		// NEORIS_CHANGE #75
		neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(getUnit(), productDataList);

		// NEORIS_CHANGE #55
		neorisProductFacade.injectCustomerNameAndDescriptionDataOn(productDataList);

		model.addAttribute("productList", productDataList);
		
		populateProductData(productData, model, request);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, productBreadcrumbBuilder.getBreadcrumbs(productModel));

		if (CollectionUtils.isNotEmpty(productData.getVariantMatrix()))
		{
			model.addAttribute(WebConstants.MULTI_DIMENSIONAL_PRODUCT, Boolean.valueOf(CollectionUtils.isNotEmpty(productData.getVariantMatrix())));
		}
	}

	protected void populateProductData(final ProductData productData, final Model model, final HttpServletRequest request)
	{
		model.addAttribute("galleryImages", getGalleryImages(productData));
		model.addAttribute("product", productData);
	}

	protected void sortVariantOptionData(final ProductData productData)
	{
		if (CollectionUtils.isNotEmpty(productData.getBaseOptions()))
		{
			for (final BaseOptionData baseOptionData : productData.getBaseOptions())
			{
				if (CollectionUtils.isNotEmpty(baseOptionData.getOptions()))
				{
					Collections.sort(baseOptionData.getOptions(), variantSortStrategy);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(productData.getVariantOptions()))
		{
			Collections.sort(productData.getVariantOptions(), variantSortStrategy);
		}
	}

	protected AbstractPageModel getPageForProduct(final ProductModel product) throws CMSItemNotFoundException
	{
		return getCmsPageService().getPageForProduct(product);
	}

	protected List<Map<String, ImageData>> getGalleryImages(final ProductData productData)
	{
		final List<Map<String, ImageData>> galleryImages = new ArrayList<Map<String, ImageData>>();
		if (CollectionUtils.isNotEmpty(productData.getImages()))
		{
			final List<ImageData> images = new ArrayList<ImageData>();
			for (final ImageData image : productData.getImages())
			{
				if (ImageDataType.GALLERY.equals(image.getImageType()))
				{
					images.add(image);
				}
			}
			Collections.sort(images, new Comparator<ImageData>()
			{
				@Override
				public int compare(final ImageData image1, final ImageData image2)
				{
					return image1.getGalleryIndex().compareTo(image2.getGalleryIndex());
				}
			});

			if (CollectionUtils.isNotEmpty(images))
			{
				int currentIndex = images.get(0).getGalleryIndex().intValue();
				Map<String, ImageData> formats = new HashMap<String, ImageData>();
				for (final ImageData image : images)
				{
					if (currentIndex != image.getGalleryIndex().intValue())
					{
						galleryImages.add(formats);
						formats = new HashMap<String, ImageData>();
						currentIndex = image.getGalleryIndex().intValue();
					}
					formats.put(image.getFormat(), image);
				}
				if (!formats.isEmpty())
				{
					galleryImages.add(formats);
				}
			}
		}
		return galleryImages;
	}

	protected List<ProductModel> getSelectedProductModels(final Set<String> skus, final Collection<VariantProductModel> productModels)
	{
		final List<ProductModel> selectedProductModels = new ArrayList<ProductModel>();
		for (final ProductModel productModel : productModels)
		{
			if (skus.contains(productModel.getCode()))
			{
				selectedProductModels.add(productModel);
			}

		}
		return selectedProductModels;
	}

}
