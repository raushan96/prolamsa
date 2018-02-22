package mx.neoris.facades.product.populators;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.model.GenericVariantProductModel;
import de.hybris.platform.b2b.model.VariantCategoryModel;
import de.hybris.platform.b2b.model.VariantValueCategoryModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.variants.model.VariantProductModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;


/**
 * @author Hybris
 */
@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class ProductVariantMatrixPopulatorTest
{
	@Mock
	UrlResolver<ProductModel> productModelUrlResolver;

	@Mock
	CommercePriceService commercePriceService;

	@Mock
	CategoryService categoryService;

	@Mock
	PriceDataFactory priceDataFactory;

	@Mock
	VariantCategoryModel colorModel;
	@Mock
	VariantValueCategoryModel blueModel;

	@Mock
	VariantCategoryModel fitModel;
	@Mock
	VariantValueCategoryModel narrowModel;
	@Mock
	VariantValueCategoryModel regularModel;

	@Mock
	VariantCategoryModel sizeModel;
	@Mock
	VariantValueCategoryModel size75Model;
	@Mock
	VariantValueCategoryModel size80Model;
	@Mock
	VariantValueCategoryModel size95Model;

	@Mock
	PriceInformation priceInformation;

	ProductVariantMatrixPopulator<ProductModel, ProductData> populator;

	@Before
	public void setup()
	{
		final PriceValue priceValue = new PriceValue("", 5, false);
		final PriceInformation priceInformation = new PriceInformation(priceValue);

		when(productModelUrlResolver.resolve(any(ProductModel.class))).thenReturn("/url/<sku>");
		when(commercePriceService.getWebPriceForProduct(any(VariantProductModel.class))).thenReturn(priceInformation);
		when(priceDataFactory.create(any(PriceDataType.class), any(BigDecimal.class), any(String.class))).thenReturn(
				new PriceData());

		final CategoryModel rootCategory = new CategoryModel();
		when(categoryService.getPathForCategory(any(VariantValueCategoryModel.class))).thenReturn(Lists.newArrayList(rootCategory));
		when(categoryService.isRoot(rootCategory)).thenReturn(true);

		populator = new ProductVariantMatrixPopulator<ProductModel, ProductData>();
		populator.setProductModelUrlResolver(productModelUrlResolver);
		populator.setCommercePriceService(commercePriceService);
		populator.setPriceDataFactory(priceDataFactory);
		populator.setCategoryService(categoryService);
	}

	@Test
	public void shouldBuildUpABasicStructureWith3Dimensions()
	{
		final GenericVariantProductModel productModel = create3DimensionalModelTree(false);
		final ProductData productData = new ProductData();

		populator.populate(productModel, productData);

		assertFalse(CollectionUtils.isEmpty(productData.getVariantMatrix()));

		final VariantMatrixElementData blueData = productData.getVariantMatrix().get(0);
		assertEquals(blueModel.getName(), blueData.getVariantValueCategory().getName());

		final VariantMatrixElementData narrowData = blueData.getElements().get(0);
		assertEquals(narrowModel.getName(), narrowData.getVariantValueCategory().getName());

		final VariantMatrixElementData size75nData = narrowData.getElements().get(0);
		assertEquals(size75Model.getName(), size75nData.getVariantValueCategory().getName());

		final VariantMatrixElementData size80nData = narrowData.getElements().get(1);
		assertEquals(size80Model.getName(), size80nData.getVariantValueCategory().getName());

		final VariantMatrixElementData regularData = blueData.getElements().get(1);
		assertEquals(regularModel.getName(), regularData.getVariantValueCategory().getName());

		final VariantMatrixElementData size80rData = regularData.getElements().get(0);
		assertEquals(size80Model.getName(), size80rData.getVariantValueCategory().getName());

		final VariantMatrixElementData size95rData = regularData.getElements().get(1);
		assertEquals(size95Model.getName(), size95rData.getVariantValueCategory().getName());
	}

	@Test
	public void shouldBuildUpABasicStructureWith2Dimensions()
	{
		final GenericVariantProductModel productModel = create2DimensionalModelTree(false);
		final ProductData productData = new ProductData();

		populator.populate(productModel, productData);

		assertFalse(CollectionUtils.isEmpty(productData.getVariantMatrix()));

		final VariantMatrixElementData blueData = productData.getVariantMatrix().get(0);
		assertEquals(blueModel.getName(), blueData.getVariantValueCategory().getName());

		final VariantMatrixElementData narrowData = blueData.getElements().get(0);
		assertEquals(narrowModel.getName(), narrowData.getVariantValueCategory().getName());

		final VariantMatrixElementData regularData = blueData.getElements().get(1);
		assertEquals(regularModel.getName(), regularData.getVariantValueCategory().getName());
	}

	@Test
	public void shouldBuildUpABasicStructureWith1Dimensions()
	{
		final GenericVariantProductModel productModel = create1DimensionalModelTree(false);
		final ProductData productData = new ProductData();

		final PriceValue priceValue = new PriceValue("", 5, false);
		final PriceInformation priceInformation = new PriceInformation(priceValue);

		when(productModelUrlResolver.resolve(any(ProductModel.class))).thenReturn("/url/<sku>");
		when(commercePriceService.getWebPriceForProduct(any(VariantProductModel.class))).thenReturn(priceInformation);
		when(priceDataFactory.create(any(PriceDataType.class), any(BigDecimal.class), any(String.class))).thenReturn(
				new PriceData());

		populator = new ProductVariantMatrixPopulator<ProductModel, ProductData>();
		populator.setProductModelUrlResolver(productModelUrlResolver);
		populator.setCommercePriceService(commercePriceService);
		populator.setPriceDataFactory(priceDataFactory);
		populator.populate(productModel, productData);

		assertFalse(CollectionUtils.isEmpty(productData.getVariantMatrix()));

		final VariantMatrixElementData blueData = productData.getVariantMatrix().get(0);
		assertEquals(narrowModel.getName(), blueData.getVariantValueCategory().getName());
	}

	private GenericVariantProductModel create1DimensionalModelTree(final boolean isBasicProduct)
	{
		when(fitModel.getName()).thenReturn("fit");
		when(fitModel.getSupercategories()).thenReturn(new ArrayList<CategoryModel>());

		stubModel(fitModel, "narrow", narrowModel, 1);
		stubModel(fitModel, "regular", regularModel, 2);

		final GenericVariantProductModel variantModel1 = new GenericVariantProductModel();
		variantModel1.setSupercategories(toList(narrowModel));
		variantModel1.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel1.setCode("code1");

		final ProductModel baseProductModelOfVariant1 = mock(ProductModel.class);
		when(baseProductModelOfVariant1.getName()).thenReturn("Base Product Name Of Variant 1");
		when(baseProductModelOfVariant1.getSummary()).thenReturn("Base Product Summary Of Variant 1");
		variantModel1.setBaseProduct(baseProductModelOfVariant1);

		final GenericVariantProductModel variantModel2 = new GenericVariantProductModel();
		variantModel2.setSupercategories(toList(regularModel));
		variantModel2.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel2.setCode("code2");

		final ProductModel baseProductModelOfVariant2 = mock(ProductModel.class);
		when(baseProductModelOfVariant2.getName()).thenReturn("Base Product Name Of Variant 2");
		when(baseProductModelOfVariant2.getSummary()).thenReturn("Base Product Summary Of Variant 2");
		variantModel2.setBaseProduct(baseProductModelOfVariant2);

		final GenericVariantProductModel productModel = mock(GenericVariantProductModel.class);
		final ProductModel baseProductModel = mock(ProductModel.class);
		when(productModel.getBaseProduct()).thenReturn(baseProductModel);

		when(productModel.getBaseProduct().getVariants()).thenReturn(toList(variantModel1, variantModel2));

		if (isBasicProduct)
		{
			when(productModel.getCode()).thenReturn("codeBasic");
		}
		else
		{
			when(productModel.getCode()).thenReturn("code3");
		}
		return productModel;
	}

	private List<CategoryModel> toList(final CategoryModel... categories)
	{
		return Arrays.asList(categories);
	}

	private List<VariantProductModel> toList(final VariantProductModel... variants)
	{
		return Arrays.asList(variants);
	}

	private GenericVariantProductModel create3DimensionalModelTree(final boolean isBasicProduct)
	{

		when(colorModel.getName()).thenReturn("color");
		when(colorModel.getSupercategories()).thenReturn(new ArrayList<CategoryModel>());

		stubModel(colorModel, "blue", blueModel, 1);

		when(fitModel.getName()).thenReturn("fit");
		when(fitModel.getSupercategories()).thenReturn(toList(colorModel));

		stubModel(fitModel, "narrow", narrowModel, 1);
		stubModel(fitModel, "regular", regularModel, 2);

		when(sizeModel.getName()).thenReturn("size");
		when(sizeModel.getSupercategories()).thenReturn(toList(fitModel));

		stubModel(sizeModel, "7.5", size75Model, 1);
		stubModel(sizeModel, "8.0", size80Model, 2);
		stubModel(sizeModel, "9.5", size95Model, 3);


		final GenericVariantProductModel variantModel1 = new GenericVariantProductModel();
		variantModel1.setSupercategories(toList(blueModel, narrowModel, size75Model));
		variantModel1.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel1.setCode("code1");

		final ProductModel baseProductModelOfVariant1 = mock(ProductModel.class);
		when(baseProductModelOfVariant1.getName()).thenReturn("Base Product Name Of Variant 1");
		when(baseProductModelOfVariant1.getSummary()).thenReturn("Base Product Summary Of Variant 1");
		variantModel1.setBaseProduct(baseProductModelOfVariant1);

		final GenericVariantProductModel variantModel2 = new GenericVariantProductModel();
		variantModel2.setSupercategories(toList(blueModel, narrowModel, size80Model));
		variantModel2.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel2.setCode("code2");

		final ProductModel baseProductModelOfVariant2 = mock(ProductModel.class);
		when(baseProductModelOfVariant2.getName()).thenReturn("Base Product Name Of Variant 2");
		when(baseProductModelOfVariant2.getSummary()).thenReturn("Base Product Summary Of Variant 2");
		variantModel2.setBaseProduct(baseProductModelOfVariant2);

		final GenericVariantProductModel variantModel3 = new GenericVariantProductModel();
		variantModel3.setSupercategories(toList(blueModel, regularModel, size80Model));
		variantModel3.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel3.setCode("code3");

		final ProductModel baseProductModelOfVariant3 = mock(ProductModel.class);
		when(baseProductModelOfVariant3.getName()).thenReturn("Base Product Name Of Variant 3");
		when(baseProductModelOfVariant3.getSummary()).thenReturn("Base Product Summary Of Variant 3");
		variantModel3.setBaseProduct(baseProductModelOfVariant3);

		final GenericVariantProductModel variantModel4 = new GenericVariantProductModel();
		variantModel4.setSupercategories(toList(blueModel, regularModel, size95Model));
		variantModel4.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel4.setCode("code4");

		final ProductModel baseProductModelOfVariant4 = mock(ProductModel.class);
		when(baseProductModelOfVariant4.getName()).thenReturn("Base Product Name Of Variant 4");
		when(baseProductModelOfVariant4.getSummary()).thenReturn("Base Product Summary Of Variant 4");
		variantModel4.setBaseProduct(baseProductModelOfVariant4);

		final GenericVariantProductModel productModel = mock(GenericVariantProductModel.class);
		final ProductModel baseProductModel = mock(ProductModel.class);
		when(productModel.getBaseProduct()).thenReturn(baseProductModel);
		when(productModel.getBaseProduct().getVariants()).thenReturn(
				toList(variantModel1, variantModel2, variantModel3, variantModel4));

		if (isBasicProduct)
		{
			when(productModel.getCode()).thenReturn("codeBasic");
		}
		else
		{
			when(productModel.getCode()).thenReturn("code3");
		}

		return productModel;
	}

	private GenericVariantProductModel create2DimensionalModelTree(final boolean isBasicProduct)
	{

		when(colorModel.getName()).thenReturn("color");
		when(colorModel.getSupercategories()).thenReturn(new ArrayList<CategoryModel>());

		stubModel(colorModel, "blue", blueModel, 1);

		when(fitModel.getName()).thenReturn("fit");
		when(fitModel.getSupercategories()).thenReturn(toList(colorModel));

		stubModel(fitModel, "narrow", narrowModel, 1);
		stubModel(fitModel, "regular", regularModel, 2);


		final GenericVariantProductModel variantModel1 = new GenericVariantProductModel();
		variantModel1.setSupercategories(toList(blueModel, narrowModel));
		variantModel1.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel1.setCode("code1");

		final ProductModel baseProductModelOfVariant1 = mock(ProductModel.class);
		when(baseProductModelOfVariant1.getName()).thenReturn("Base Product Name Of Variant 1");
		when(baseProductModelOfVariant1.getSummary()).thenReturn("Base Product Summary Of Variant 1");
		variantModel1.setBaseProduct(baseProductModelOfVariant1);

		final GenericVariantProductModel variantModel2 = new GenericVariantProductModel();
		variantModel2.setSupercategories(toList(blueModel, regularModel));
		variantModel2.setStockLevels(new TreeSet<StockLevelModel>());
		variantModel2.setCode("code2");

		final ProductModel baseProductModelOfVariant2 = mock(ProductModel.class);
		when(baseProductModelOfVariant2.getName()).thenReturn("Base Product Name Of Variant 2");
		when(baseProductModelOfVariant2.getSummary()).thenReturn("Base Product Summary Of Variant 2");
		variantModel2.setBaseProduct(baseProductModelOfVariant2);

		final GenericVariantProductModel productModel = mock(GenericVariantProductModel.class);
		final ProductModel baseProductModel = mock(ProductModel.class);
		when(productModel.getBaseProduct()).thenReturn(baseProductModel);
		when(productModel.getBaseProduct().getVariants()).thenReturn(toList(variantModel1, variantModel2));

		if (isBasicProduct)
		{
			when(productModel.getCode()).thenReturn("codeBasic");
		}
		else
		{
			when(productModel.getCode()).thenReturn("code3");
		}

		return productModel;
	}

	/**
	 * @param parentCategory
	 * @param modelValue
	 * @param modelToMock
	 */
	private void stubModel(final VariantCategoryModel parentCategory, final String modelValue,
			final VariantValueCategoryModel modelToMock, final int sequence)
	{
		when(modelToMock.getName()).thenReturn(modelValue);
		when(modelToMock.getSequence()).thenReturn(sequence);
		when(modelToMock.getSupercategories()).thenReturn(toList(parentCategory));
	}
}
