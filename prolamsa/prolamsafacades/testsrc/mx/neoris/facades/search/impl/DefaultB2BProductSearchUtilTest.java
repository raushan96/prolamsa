/**
 * 
 */
package mx.neoris.facades.search.impl;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.b2b.model.GenericVariantProductModel;
import de.hybris.platform.commercefacades.converter.ConfigurablePopulator;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Unit tests for {@link DefaultB2BProductSearchUtil}.
 */
@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultB2BProductSearchUtilTest
{

	private final DefaultB2BProductSearchUtil<ProductData> b2bProductSearchUtil = new DefaultB2BProductSearchUtil();

	@Mock
	private ProductFacade productFacade;

	@Mock
	private ProductService productService;

	@Mock
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Before
	public void setUp()
	{
		b2bProductSearchUtil.setProductFacade(productFacade);
		b2bProductSearchUtil.setProductService(productService);
		b2bProductSearchUtil.setProductConfiguredPopulator(productConfiguredPopulator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void populateVariantProductsNullPage()
	{
		b2bProductSearchUtil.populateVariantProducts(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void populateVariantProductsNullResults()
	{
		final ProductSearchPageData<SearchStateData, ProductData> pageData = new ProductSearchPageData();
		// pageData.results is null, so should throw exception
		b2bProductSearchUtil.populateVariantProducts(pageData);
	}

	@Test
	public void populateVariantProductsWithVariantShouldChangeUrl()
	{
		final String skuId = "sku";
		final String urlOld = "oldurl";
		final String urlNew = "newurl";
		final ProductSearchPageData<SearchStateData, ProductData> pageData = new ProductSearchPageData();
		final List<ProductData> results = createResults(new String[]
		{ skuId }, new String[]
		{ urlOld });
		pageData.setResults(results);

		mockServiceFacadeCalls(skuId, urlNew, true);

		b2bProductSearchUtil.populateVariantProducts(pageData);

		Assert.assertNotNull(pageData);
		Assert.assertNotNull(pageData.getResults());
		Assert.assertEquals(1, pageData.getResults().size());
		final ProductData result = pageData.getResults().get(0);
		// url should be the new one
		Assert.assertEquals(urlNew, result.getUrl());
	}

	@Test
	public void populateVariantProductsWithVariantShouldCallPopulator()
	{
		final String skuId = "sku";
		final String urlOld = "oldurl";
		final String urlNew = "newurl";
		final ProductSearchPageData<SearchStateData, ProductData> pageData = new ProductSearchPageData();
		final List<ProductData> results = createResults(new String[]
		{ skuId }, new String[]
		{ urlOld });
		pageData.setResults(results);

		mockServiceFacadeCalls(skuId, urlNew, true);

		b2bProductSearchUtil.populateVariantProducts(pageData);

		Assert.assertNotNull(pageData);
		Assert.assertNotNull(pageData.getResults());
		Assert.assertEquals(1, pageData.getResults().size());
		// populator should be called
		Mockito.verify(productConfiguredPopulator).populate(Mockito.any(GenericVariantProductModel.class),
				Mockito.any(ProductData.class), Mockito.anyCollection());
	}

	@Test
	public void populateVariantProductsWithoutVariantShouldKeepUrl()
	{
		final String skuId = "sku";
		final String urlOld = "oldurl";
		final ProductSearchPageData<SearchStateData, ProductData> pageData = new ProductSearchPageData();
		final List<ProductData> results = createResults(new String[]
		{ skuId }, new String[]
		{ urlOld });
		pageData.setResults(results);

		mockServiceFacadeCalls(skuId, null, false);

		b2bProductSearchUtil.populateVariantProducts(pageData);

		Assert.assertNotNull(pageData);
		Assert.assertNotNull(pageData.getResults());
		Assert.assertEquals(1, pageData.getResults().size());
		final ProductData result = pageData.getResults().get(0);
		// url should be the original one
		Assert.assertEquals(urlOld, result.getUrl());
	}

	@Ignore
	@Test
	public void populateVariantProductsWithoutVariantShouldNotCallPopulator()
	{
		final String skuId = "sku";
		final String urlOld = "oldurl";
		final ProductSearchPageData<SearchStateData, ProductData> pageData = new ProductSearchPageData();
		final List<ProductData> results = createResults(new String[]
		{ skuId }, new String[]
		{ urlOld });
		pageData.setResults(results);

		mockServiceFacadeCalls(skuId, null, false);

		b2bProductSearchUtil.populateVariantProducts(pageData);

		Assert.assertNotNull(pageData);
		Assert.assertNotNull(pageData.getResults());
		Assert.assertEquals(1, pageData.getResults().size());
		// populator should not be called (0 times)
		Mockito.verify(productConfiguredPopulator, Mockito.times(0)).populate(Mockito.any(GenericVariantProductModel.class),
				Mockito.any(ProductData.class), Mockito.anyCollection());
	}

	@Ignore
	@Test
	public void populateVariantProductsSomeVariantSomeNotVariant()
	{
		final String[] skuIds = new String[]
		{ "skuVar1", "skuVar2", "skuNotVar1", "skuVar3", "skuNotVar2" };
		final String[] urlOlds = new String[]
		{ "oldurl-Var1", "oldurl-Var2", "oldurl-NotVar1", "oldurl-Var3", "oldurl-NotVar2" };
		final String[] urlNews = new String[]
		{ "newurl-Var1", "newurl-Var2", "newurl-NotVar1", "newurl-Var3", "newurl-NotVar2" };
		final ProductSearchPageData<SearchStateData, ProductData> pageData = new ProductSearchPageData();
		final List<ProductData> results = createResults(skuIds, urlOlds);
		pageData.setResults(results);

		final boolean[] hasVariants = new boolean[]
		{ true, true, false, true, false };
		mockServiceFacadeCalls(skuIds, urlNews, hasVariants);

		// call method in test
		b2bProductSearchUtil.populateVariantProducts(pageData);

		// verify results
		Assert.assertNotNull(pageData);
		Assert.assertNotNull(pageData.getResults());
		Assert.assertEquals(skuIds.length, pageData.getResults().size());

		for (int i = 0; i < pageData.getResults().size(); i++)
		{
			final ProductData result = pageData.getResults().get(i);
			// non-variant product should keep url
			if (result.getCode().indexOf("NotVar") > 0)
			{
				Assert.assertEquals(urlOlds[i], result.getUrl());
			}
			else
			{
				Assert.assertEquals(urlNews[i], result.getUrl());
			}
		}

		// Populator should be called only 3 times (for the multi-D products). It should not be called for the 
		// 2 non-variant product.
		Mockito.verify(productConfiguredPopulator, Mockito.times(3)).populate(Mockito.any(GenericVariantProductModel.class),
				Mockito.any(ProductData.class), Mockito.anyCollection());
	}

	private void mockServiceFacadeCalls(final String skuId, final String urlNew, final boolean hasVariants)
	{
		this.mockServiceFacadeCalls(new String[]
		{ skuId }, new String[]
		{ urlNew }, new boolean[]
		{ hasVariants });
	}

	private void mockServiceFacadeCalls(final String[] skuIds, final String[] urlNews, final boolean[] hasVariants)
	{
		if ((skuIds == null) || (urlNews == null) || (hasVariants == null) || (skuIds.length != urlNews.length)
				|| (skuIds.length != hasVariants.length))
		{
			throw new IllegalArgumentException("codes: " + skuIds + "\turls: " + urlNews + "\turls: " + hasVariants);
		}

		for (int i = 0; i < skuIds.length; i++)
		{
			final boolean hasVariant = hasVariants[i];

			// mock service call
			final ProductModel pm = createProductModel(hasVariant);
			Mockito.when(productService.getProductForCode(skuIds[i])).thenReturn(pm);

			// if product has variants, mock facade call
			if (hasVariant)
			{
				final ProductData firstVariantData = createProductData(skuIds[i], urlNews[i]);
				final ProductModel firstVariantModel = pm.getVariants().iterator().next();
				Mockito.when(productFacade.getProductForOptions(Matchers.eq(firstVariantModel), Mockito.anyCollection())).thenReturn(
						firstVariantData);
			}
		}
	}

	private List<ProductData> createResults(final String[] codes, final String[] urls)
	{
		if ((codes == null) || (urls == null) || (codes.length != urls.length))
		{
			throw new IllegalArgumentException("codes: " + codes + "\turls: " + urls);
		}
		final List<ProductData> results = new ArrayList();
		for (int i = 0; i < codes.length; i++)
		{
			results.add(createProductData(codes[i], urls[i]));
		}
		return results;
	}

	private ProductData createProductData(final String code, final String url)
	{
		final ProductData productData = new ProductData();
		productData.setCode(code);
		productData.setUrl(url);
		return productData;
	}

	private ProductModel createProductModel(final boolean hasVariants)
	{
		final ProductModel model = new ProductModel();
		if (hasVariants)
		{
			final Set<VariantProductModel> variants = new HashSet();
			variants.add(new GenericVariantProductModel());
			model.setVariants(variants);
		}
		return model;
	}
}
