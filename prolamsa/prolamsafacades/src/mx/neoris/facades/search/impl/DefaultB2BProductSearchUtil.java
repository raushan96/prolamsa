/**
 * 
 */
package mx.neoris.facades.search.impl;

import de.hybris.platform.b2b.model.GenericVariantProductModel;
import de.hybris.platform.commercefacades.converter.ConfigurablePopulator;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.facades.search.B2BProductSearchUtil;
import mx.neoris.sap.implementors.DefaultSAPNeorisCartPriceCalculator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

/**
 * Default implementation of {@link B2BProductSearchUtil}.
 */
public class DefaultB2BProductSearchUtil<ITEM extends ProductData> implements B2BProductSearchUtil<ITEM>
{

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "b2bProductConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ITEM, ProductOption> productConfiguredPopulator;
	
	private static final Logger LOG = Logger.getLogger(DefaultB2BProductSearchUtil.class);

	@Override
	public void populateVariantProducts(final ProductSearchPageData<SearchStateData, ITEM> pageData)
	{
		if ((pageData != null) && (pageData.getResults() != null))
		{
			if (CollectionUtils.isNotEmpty(pageData.getResults()))
			{
				// final Collection<ProductOption> optionsWithVariants =
				// Arrays.asList(ProductOption.BASIC, ProductOption.PRICE,
				// ProductOption.SUMMARY, ProductOption.DESCRIPTION,
				// ProductOption.GALLERY, ProductOption.CATEGORIES,
				// ProductOption.REVIEW, ProductOption.PROMOTIONS,
				// ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL,
				// ProductOption.STOCK, ProductOption.VOLUME_PRICES,
				// ProductOption.PRICE_RANGE, ProductOption.VARIANT_MATRIX);

				// NEORIS_CHANGE only include the required product information
				// for Prolamsa
				final Collection<ProductOption> optionsWithVariants = Arrays.asList(ProductOption.BASIC, ProductOption.DESCRIPTION, ProductOption.CATEGORIES, ProductOption.VARIANT_FULL, ProductOption.VARIANT_MATRIX, ProductOption.PROLAMSA_ATTRIBUTES);

				// final Collection<ProductOption> optionsWithoutVariants =
				// Arrays.asList(ProductOption.STOCK);
				final Collection<ProductOption> optionsWithoutVariants = Arrays.asList(ProductOption.PROLAMSA_ATTRIBUTES);

				List<ITEM> newPageResults = new ArrayList<ITEM>();

				for (final ITEM productData : pageData.getResults())
				{
					ProductModel productModel = null;

					// NEORIS_CHANGE
					// catch any product not found, as products can be hidden by
					// the category product restriction
					try
					{
						productModel = productService.getProductForCode(productData.getCode());
					}
					
					catch (UnknownIdentifierException unknownExc)
					{
						productModel = null;
					}
					
					// NEORIS_CHANGE
					// if product not found, just continue with the process
					if (productModel == null)
						continue;
					//Raushan Changes
					//Custom Product Visibility Material Visibiity
                    if(productModel instanceof ProlamsaProductModel && "N".equalsIgnoreCase(((ProlamsaProductModel) productModel).getApprovalVisibility())) {
                    	
                    	LOG.info("Skipping Product to display on Category Pages::"+productModel.getCode());
                    	continue;
                    }

					if (CollectionUtils.isNotEmpty(productModel.getVariants()))
					{
						// check if product has at least one generic variant
						GenericVariantProductModel firstVariant = null;
						for (final VariantProductModel variant : productModel.getVariants())
						{
							if (variant instanceof GenericVariantProductModel)
							{
								firstVariant = (GenericVariantProductModel) variant;
								break;
							}
						}

						if (firstVariant != null)
						{
							// NEORIS_CHANGE
							newPageResults.add(productData);

							final ProductData firstVariantData = productFacade.getProductForOptions(firstVariant, optionsWithVariants);
							this.productConfiguredPopulator.populate(firstVariant, productData, optionsWithVariants);
							// set url from first variant into base product, to
							// enable links to product details and order form
							productData.setUrl(firstVariantData.getUrl());
						}
					}
					else
					{
						// NEORIS_CHANGE
						newPageResults.add(productData);

						this.productConfiguredPopulator.populate(productModel, productData, optionsWithoutVariants);
					}
				}

				// NEORIS_CHANGE #38
				// overwrite the pageData product results with the filtered
				// results
				pageData.setResults(newPageResults);
				// TODO not sure what numberOfResults to set, as the
				// totalNumberOfResults comes from the facets search
				// and not from the filtered products by the category visibility
				// restriction
/*
				Integer filteredResultsCount = newPageResults.size();
				Integer pageSize = pageData.getPagination().getPageSize();

				if (filteredResultsCount < pageSize)
				{
					// set the new total number of results
					pageData.getPagination().setTotalNumberOfResults(filteredResultsCount);
					// Calculate the number of pages
					pageData.getPagination().setNumberOfPages((int) Math.ceil(((double) pageData.getPagination().getTotalNumberOfResults()) / pageData.getPagination().getPageSize()));
				}
*/
			}
		}
		else
		{
			throw new IllegalArgumentException("Cannot populate ProductSearchPageData with null value or null results.");
		}
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	public void setProductConfiguredPopulator(final ConfigurablePopulator<ProductModel, ITEM, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

}
