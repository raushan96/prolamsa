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
package mx.neoris.facades.product.populators;

import de.hybris.platform.b2b.jalo.VariantCategory;
import de.hybris.platform.b2b.model.GenericVariantProductModel;
import de.hybris.platform.b2b.model.VariantCategoryModel;
import de.hybris.platform.b2b.model.VariantValueCategoryModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.ImageFormatMapping;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.product.data.VariantCategoryData;
import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.commercefacades.product.data.VariantValueCategoryData;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.variants.model.VariantProductModel;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author Hybris
 * 
 *         Populate the multi-dimensional variant matrix for a given product.
 */
public class ProductVariantMatrixPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends
		AbstractProductPopulator<SOURCE, TARGET>
{

	@Resource
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Resource
	private CommercePriceService commercePriceService;

	@Resource
	private PriceDataFactory priceDataFactory;

	@Resource
	private ImageFormatMapping acceleratorImageFormatMapping;

	@Autowired
	private CategoryService categoryService;

	private List<String> imageFormats;


	@Override
	public void populate(final ProductModel productModel, final ProductData productData) throws ConversionException
	{
		final Map<VariantProductModel, List<VariantValueCategoryModel>> modelCategories = new HashMap<>();

		final Collection<VariantProductModel> variants = getVariants(productModel);

		final boolean isMultidimensional = CollectionUtils.isNotEmpty(variants);
		productData.setMultidimensional(Boolean.valueOf(isMultidimensional));

		if (isMultidimensional)
		{
			for (final VariantProductModel variantProductModel : variants)
			{
				if (variantProductModel instanceof GenericVariantProductModel)
				{
					final List<VariantValueCategoryModel> variantValuesCategories = getVariantValuesCategories((GenericVariantProductModel) variantProductModel);
					modelCategories.put(variantProductModel, variantValuesCategories);
				}

				if (productModel.getCode().equals(variantProductModel.getCode()))
				{
					setBaseProductNameSummary(productData, variantProductModel.getBaseProduct());
				}
			}

			final List<VariantMatrixElementData> variantMatrix = createMatrix(modelCategories, productModel);
			productData.setVariantMatrix(variantMatrix);
		}

	}

	private Collection<VariantProductModel> getVariants(final ProductModel productModel)
	{
		Collection<VariantProductModel> variants = null;
		if (productModel instanceof GenericVariantProductModel)
		{
			variants = ((GenericVariantProductModel) productModel).getBaseProduct().getVariants();
		}

		return variants;
	}

	/**
	 * Get the list of {@link VariantValueCategoryModel} related to a specific {@link GenericVariantProductModel}.
	 * 
	 * @param productModel
	 *           the variant product.
	 * @return The variant value categories, ordered by variant category priority.
	 */
	private List<VariantValueCategoryModel> getVariantValuesCategories(final GenericVariantProductModel productModel)
	{
		final List<VariantValueCategoryModel> variantValueCategories = new ArrayList<>();
		for (final CategoryModel categoryProductModel : productModel.getSupercategories())
		{
			if (categoryProductModel instanceof VariantValueCategoryModel)
			{
				variantValueCategories.add((VariantValueCategoryModel) categoryProductModel);
			}
		}
		Collections.sort(variantValueCategories, new VariantValueCategoryModelPriorityComparator());

		return variantValueCategories;
	}

	/**
	 * Create a list of {@link VariantMatrixElementData} representing the matrix of variants for a specific base product,
	 * given the product variants and the categories related to each product variant.
	 * 
	 * @param modelCategories
	 *           A map of product variant (@link VariantProductModel) to its list of variant categories (
	 *           {@link VariantValueCategoryModel}).
	 * @param productModel
	 *           The current product (the one being populated)
	 * @return The resulting matrix, modeled as a group of nested lists, where the outer list will have some
	 *         {@link VariantMatrixElementData} representing the categories with priority_1, and each of those contains a
	 *         list of {@link VariantMatrixElementData} representing the categories with priority_2, and so on.<br>
	 *         e.g.: A product with dimensions color/fit/size would return a list organized like this:
	 *         <BROWN:<normal:<7;8;9>; wide:<8;9;10>>; BLACK<normal:<7;8>>>
	 */
	private List<VariantMatrixElementData> createMatrix(
			final Map<VariantProductModel, List<VariantValueCategoryModel>> modelCategories, final ProductModel productModel)
	{
		int dimensions = 0;

		final List<VariantMatrixElementData> theMatrix = new ArrayList<>();

		for (final Map.Entry<VariantProductModel, List<VariantValueCategoryModel>> entry : modelCategories.entrySet())
		{
			final VariantProductModel variantProductModel = entry.getKey();
			final List<VariantValueCategoryModel> valueCategories = entry.getValue();
			dimensions = valueCategories.size();

			VariantMatrixElementData matrixElement = new VariantMatrixElementData();

			if (dimensions == 1)
			{
				createLeafElementData(valueCategories.get(dimensions - 1), theMatrix, variantProductModel);
			}
			else if (dimensions == 2)
			{
				final VariantMatrixElementData upperElementData = createUpperElementData(valueCategories.get(0), theMatrix);
				createLeafElementData(valueCategories.get(dimensions - 1), upperElementData.getElements(), variantProductModel);
			}
			else
			{
				for (int i = 0; i < valueCategories.size(); i++)
				{
					final VariantValueCategoryModel valueCategory = valueCategories.get(i);

					if (i == 0)
					{ // level 1
						matrixElement = createUpperElementData(valueCategory, theMatrix);
					}

					if (0 < i && i < valueCategories.size() - 1)
					{ // level i+1...n-1
						matrixElement = createUpperElementData(valueCategory, matrixElement.getElements());
					}

					if (i == valueCategories.size() - 1)
					{ // level n
						createLeafElementData(valueCategory, matrixElement.getElements(), variantProductModel);
					}
				}
			}
		}

		orderMatrix(theMatrix);
		if (dimensions > 1)
		{
			setUrlOnParents(theMatrix, productModel.getCode());
		}

		return theMatrix;
	}

	/**
	 * Set name and summary from base product into the product data.
	 * 
	 * @param productData
	 *           The {@link ProductData} to be set.
	 * @param baseProduct
	 *           The base product.
	 */
	private void setBaseProductNameSummary(final ProductData productData, final ProductModel baseProduct)
	{
		if (StringUtils.isNotBlank(baseProduct.getName()))
		{
			productData.setName(baseProduct.getName());
		}

		if (StringUtils.isNotBlank(baseProduct.getSummary()))
		{
			productData.setSummary(baseProduct.getSummary());
		}
	}

	private VariantMatrixElementData createUpperElementData(final VariantValueCategoryModel variantValueCategoryModel,
			final List<VariantMatrixElementData> parentList)
	{
		VariantMatrixElementData matrixElementData = this.getMatrixElementData(parentList, variantValueCategoryModel);
		if (matrixElementData == null)
		{
			matrixElementData = createNode(variantValueCategoryModel, null);
			parentList.add(matrixElementData);
			matrixElementData.setIsLeaf(false);
			matrixElementData.setElements(new ArrayList<VariantMatrixElementData>());
		}

		return matrixElementData;
	}

	private VariantMatrixElementData createLeafElementData(final VariantValueCategoryModel categoryValue,
			final List<VariantMatrixElementData> parentList, final VariantProductModel variantProductModel)
	{
		final VariantMatrixElementData matrixElement = createNode(categoryValue, variantProductModel);
		parentList.add(matrixElement);
		matrixElement.setIsLeaf(true);
		return matrixElement;
	}

	/**
	 * Sort the matrix on each level, by matrix element sequence
	 */
	private void orderMatrix(final List<VariantMatrixElementData> elementsList)
	{
		for (final VariantMatrixElementData element : elementsList)
		{
			if (CollectionUtils.isNotEmpty(element.getElements()))
			{
				orderMatrix(element.getElements());
			}
		}
		Collections.sort(elementsList, new VariantMatrixElementDataSequenceComparator());
	}

	/**
	 * Method to set info from the leaves to the parents in the category tree.
	 * 
	 * @param level
	 *           List of {@link VariantMatrixElementData}, corresponding to one level in the category tree.
	 * @param productCode
	 *           Code of the current selected product.
	 * @return Values from the desired leaf to be set on its ancestors.
	 */
	private LeafLevelInfo setUrlOnParents(final List<VariantMatrixElementData> level, final String productCode)
	{
		LeafLevelInfo leafInfo = null;
		for (final VariantMatrixElementData elemLevel : level)
		{
			if (!elemLevel.getIsLeaf())
			{
				final List<VariantMatrixElementData> childLevel = elemLevel.getElements();
				// if it is the last before the leaf, get the info from leaf
				if (childLevel.get(0).getIsLeaf())
				{
					leafInfo = getLeafLevelInfo(elemLevel, productCode);
				}
				// else, continue recursively
				else
				{
					leafInfo = setUrlOnParents(childLevel, productCode);
				}
				setCodeAndUrlFromSelectedProduct(elemLevel, leafInfo, childLevel.get(0).getVariantOption());
			}
		}
		return leafInfo;
	}

	private LeafLevelInfo getLeafLevelInfo(final VariantMatrixElementData elemLevelParent, final String productCode)
	{
		String urlCurrent = null;
		String codeCurrent = null;
		final List<VariantMatrixElementData> levelLeaf = elemLevelParent.getElements();

		// current product values should be set for upper levels
		for (final VariantMatrixElementData elemLevelLeaf : levelLeaf)
		{
			if (productCode.equals(elemLevelLeaf.getVariantOption().getCode()))
			{
				urlCurrent = elemLevelLeaf.getVariantOption().getUrl();
				codeCurrent = elemLevelLeaf.getVariantOption().getCode();
				break;
			}
		}

		return new LeafLevelInfo(urlCurrent, codeCurrent);
	}

	/**
	 * If using product variant, should have a selected code otherwise, if using base produce, no code should be
	 * selected, get the one from the 1st element.
	 * 
	 * @param parent
	 *           The parent element (the one that will be set).
	 * @param leafInfo
	 *           The info from the leaf to be populated on the parent.
	 * @param firstChildData
	 *           The data of the first child of the parent.
	 */
	private void setCodeAndUrlFromSelectedProduct(final VariantMatrixElementData parent, final LeafLevelInfo leafInfo,
			final VariantOptionData firstChildData)
	{
		if (leafInfo.getUrl() == null)
		{
			setCodeAndUrl(parent, firstChildData.getCode(), firstChildData.getUrl());
		}
		else
		{
			setCodeAndUrl(parent, leafInfo.getCode(), leafInfo.getUrl());
		}
		parent.getVariantOption().setVariantOptionQualifiers(firstChildData.getVariantOptionQualifiers());
	}

	private void setCodeAndUrl(final VariantMatrixElementData element, final String code, final String url)
	{
		element.getVariantOption().setUrl(url);
		element.getVariantOption().setCode(code);
	}

	/**
	 * Return "false" right away, if the name in the model is the same of one in the list. NOTE: no need to check if
	 * getVariantValueCategory() because it is created internally in createNode(), so it is granted it will not be null.
	 */
	private VariantMatrixElementData getMatrixElementData(final List<VariantMatrixElementData> variantMatrixElementsData,
			final VariantValueCategoryModel model)
	{
		for (final VariantMatrixElementData current : variantMatrixElementsData)
		{
			if (current.getVariantValueCategory().getName().equals(model.getName()))
			{
				return current;
			}
		}
		return null;
	}

	private VariantMatrixElementData createNode(final VariantValueCategoryModel variantValueCategory,
			final VariantProductModel variantProductModel)
	{
		final VariantCategoryData parent = new VariantCategoryData();

		final VariantCategoryModel variantCategoryModel = (VariantCategoryModel) variantValueCategory.getSupercategories().get(0);
		parent.setName(variantCategoryModel.getName());
		parent.setHasImage(variantCategoryModel.getHasImage());

		final VariantValueCategoryData data = new VariantValueCategoryData();
		data.setName(variantValueCategory.getName());
		data.setSequence(variantValueCategory.getSequence());

		final VariantMatrixElementData variantMatrixElementData = new VariantMatrixElementData();
		variantMatrixElementData.setVariantValueCategory(data);
		variantMatrixElementData.setParentVariantCategory(parent);

		final VariantOptionData variantOptionData = new VariantOptionData();
		final VariantOptionQualifierData variantOptionQualifierData = new VariantOptionQualifierData();
		variantOptionQualifierData.setImage(new ImageData());
		variantOptionData.setVariantOptionQualifiers(Arrays.asList(new VariantOptionQualifierData[]
		{ variantOptionQualifierData }));

		if (variantProductModel != null)
		{
			createNodeLeafSpecific(variantProductModel, variantOptionData);
		}

		variantMatrixElementData.setVariantOption(variantOptionData);

		return variantMatrixElementData;
	}

	/**
	 * @param variantProductModel
	 * @param variantOptionData
	 */
	private void createNodeLeafSpecific(final VariantProductModel variantProductModel, final VariantOptionData variantOptionData)
	{
		populateMediaData(variantProductModel, variantOptionData);

		final PriceInformation priceInformation = commercePriceService.getWebPriceForProduct(variantProductModel);

		PriceData priceData;
		if (priceInformation != null && priceInformation.getPriceValue() != null)
		{
			priceData = priceDataFactory.create(PriceDataType.FROM, new BigDecimal(priceInformation.getPriceValue().getValue()),
					priceInformation.getPriceValue().getCurrencyIso());
		}
		else
		{
			priceData = new PriceData();
			priceData.setValue(BigDecimal.ZERO);
			priceData.setFormattedValue("0");
		}
		variantOptionData.setPriceData(priceData);
		variantOptionData.setCode(variantProductModel.getCode());
		variantOptionData.setUrl(productModelUrlResolver.resolve(variantProductModel));

		long stockLevel = 0;
		if (CollectionUtils.isNotEmpty(variantProductModel.getStockLevels()))
		{
			for (final Iterator<StockLevelModel> stockLevelIter = variantProductModel.getStockLevels().iterator(); stockLevelIter
					.hasNext();)
			{
				final StockLevelModel stockLevelModel = stockLevelIter.next();
				stockLevel += stockLevelModel.getAvailable();
			}
		}

		final StockData stock = new StockData();
		stock.setStockLevel(stockLevel);
		variantOptionData.setStock(stock);
	}

	private void populateMediaData(final VariantProductModel variantProductModel, final VariantOptionData variantOptionData)
	{
		if (variantProductModel.getOthers() != null)
		{
			final Collection<VariantOptionQualifierData> qualifierDataList = new ArrayList<VariantOptionQualifierData>();


			for (final Iterator<MediaModel> mediaModelIter = variantProductModel.getOthers().iterator(); mediaModelIter.hasNext();)
			{
				final MediaModel mediaModel = mediaModelIter.next();

				final ImageData imageData = new ImageData();
				imageData.setUrl(mediaModel.getURL());
				imageData.setFormat(getMediaFormat(mediaModel.getMediaFormat().getName()));

				final VariantOptionQualifierData qualifierData = new VariantOptionQualifierData();
				qualifierData.setImage(imageData);

				qualifierDataList.add(qualifierData);

				variantOptionData.setVariantOptionQualifiers(qualifierDataList);

			}
		}
	}

	private String getMediaFormat(final String format)
	{
		for (final String imageFormat : imageFormats)
		{
			if (format.equals(acceleratorImageFormatMapping.getMediaFormatQualifierForImageFormat(imageFormat)))
			{
				return imageFormat;
			}
		}
		return null;
	}

	class VariantValueCategoryModelPriorityComparator implements Comparator<VariantValueCategoryModel>
	{
		@Override
		public int compare(final VariantValueCategoryModel variantValueCategory1, final VariantValueCategoryModel variantValueCategory2)
		{
			final LinkedList<CategoryModel> pathToRoot1 = getPathToRoot(variantValueCategory1);
			final LinkedList<CategoryModel> pathToRoot2 = getPathToRoot(variantValueCategory2);

			return Integer.compare(pathToRoot1.size(), pathToRoot2.size());
		}

		private LinkedList<CategoryModel> getPathToRoot(final VariantValueCategoryModel variantValueCategory)
		{
			final LinkedList<CategoryModel> pathToRoot = new LinkedList<>(getCategoryService().getPathForCategory(variantValueCategory));

			while (!getCategoryService().isRoot(pathToRoot.get(0)))
			{
				pathToRoot.addAll(0, getCategoryService().getPathForCategory(pathToRoot.get(0)));
			}

			return pathToRoot;
		}
	}

	class VariantMatrixElementDataSequenceComparator implements Comparator<VariantMatrixElementData>
	{
		@Override
		public int compare(final VariantMatrixElementData elementData1, final VariantMatrixElementData elementData2)
		{
			return elementData1.getVariantValueCategory().getSequence() - elementData2.getVariantValueCategory().getSequence();
		}
	}

	/**
	 * Inner class to help with bubbling up info from the leaves to the parents in the category tree.
	 */
	class LeafLevelInfo
	{
		private String url;
		private String code;
		private Collection<VariantOptionQualifierData> productImages;

		public LeafLevelInfo(final String urlCurrent, final String codeCurrent)
		{
			super();
			this.url = urlCurrent;
			this.code = codeCurrent;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(final String url)
		{
			this.url = url;
		}

		public String getCode()
		{
			return code;
		}

		public void setCode(final String code)
		{
			this.code = code;
		}

		public Collection<VariantOptionQualifierData> getProductImages()
		{
			return productImages;
		}

		public void setProductImages(final Collection<VariantOptionQualifierData> productImages)
		{
			this.productImages = productImages;
		}

	}

	public void setProductModelUrlResolver(final UrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

	public void setCommercePriceService(final CommercePriceService commercePriceService)
	{
		this.commercePriceService = commercePriceService;
	}

	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	public List<String> getImageFormats()
	{
		return imageFormats;
	}

	@Required
	public void setImageFormats(final List<String> imageFormats)
	{
		this.imageFormats = imageFormats;
	}

}