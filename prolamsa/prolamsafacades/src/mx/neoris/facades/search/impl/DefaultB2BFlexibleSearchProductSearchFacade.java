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
package mx.neoris.facades.search.impl;

import de.hybris.platform.b2bacceleratorservices.product.B2BProductService;
import de.hybris.platform.commercefacades.converter.ConfigurablePopulator;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.util.Config;
import mx.neoris.facades.search.B2BProductSearchFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;


/**
 * B2B facade used for offering FlexibleSearch capabilities to the AdvancedSearch feature in B2B.
 */
public class DefaultB2BFlexibleSearchProductSearchFacade<ITEM extends ProductData> implements B2BProductSearchFacade<ITEM>
{
	private B2BProductService b2BProductService;
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER = "storefront.advancedsearch.delimiter";

	@Resource(name = "b2bProductSearchUtil")
	private DefaultB2BProductSearchUtil<ITEM> b2bProductSearchUtil;

	@Required
	public void setB2BProductService(final B2BProductService b2BProductService)
	{
		this.b2BProductService = b2BProductService;
	}

	@Required
	public void setProductConfiguredPopulator(
			final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

	@Override
	public ProductSearchPageData<SearchStateData, ITEM> searchForSkus(final Collection<String> skus,
			final PageableData pageableData)
	{
//		final Collection<ProductOption> options = Arrays.asList(ProductOption.BASIC, ProductOption.CATEGORIES,
//				ProductOption.PROMOTIONS, ProductOption.VARIANT_FULL, ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE,
//				ProductOption.URL, ProductOption.IMAGES, ProductOption.REVIEW, ProductOption.STOCK, ProductOption.PRICE);

		// NEORIS_CHANGE only include the required product information for Prolamsa 
		final Collection<ProductOption> options = Arrays.asList(ProductOption.BASIC, ProductOption.CATEGORIES,
				ProductOption.VARIANT_FULL, ProductOption.URL, ProductOption.PROLAMSA_ATTRIBUTES);

		final List<ProductData> productDataList = new ArrayList<>();

		final SearchPageData searchPageData = b2BProductService.findProductsForSkus(skus, pageableData);
		final List<ProductModel> productModelList = searchPageData.getResults();

		for (final ProductModel productModel : productModelList)
		{
			final ProductData productData = new ProductData();

			if (this.productConfiguredPopulator != null && productModel != null)
			{
				this.productConfiguredPopulator.populate(productModel, productData, options);
			}

			productDataList.add(productData);
		}

		final ProductSearchPageData productSearchPageData = new ProductSearchPageData();

		productSearchPageData.setResults(productDataList);
		productSearchPageData.setPagination(searchPageData.getPagination());
		productSearchPageData.setSorts(searchPageData.getSorts());

		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(StringUtils.join(skus.toArray(), Config.getParameter(ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER)));

		final SearchStateData searchStateData = new SearchStateData();
		searchStateData.setQuery(searchQueryData);

		productSearchPageData.setCurrentQuery(searchStateData);

		b2bProductSearchUtil.populateVariantProducts(productSearchPageData);

		return productSearchPageData;
	}
}
