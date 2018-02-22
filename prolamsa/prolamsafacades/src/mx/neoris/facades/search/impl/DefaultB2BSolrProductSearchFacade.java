/**
 * 
 */
package mx.neoris.facades.search.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.search.solrfacetsearch.impl.DefaultSolrProductSearchFacade;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.commercefacades.search.NeorisProductSearchFacade;
import mx.neoris.core.commerceservices.search.NeorisProductSearchService;


public class DefaultB2BSolrProductSearchFacade<ITEM extends ProductData> extends DefaultSolrProductSearchFacade<ITEM>
implements NeorisProductSearchFacade<ITEM>
{
	@Resource(name = "b2bProductSearchUtil")
	private DefaultB2BProductSearchUtil<ITEM> b2bProductSearchUtil;

	@Resource(name = "defaultNeorisSolrProductSearchService")
	private NeorisProductSearchService<SolrSearchQueryData, SearchResultValueData, ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>> neorisProductSearchService;

	@Override
	public ProductSearchPageData<SearchStateData, ITEM> textSearch(final String text)
	{
		final ProductSearchPageData<SearchStateData, ITEM> pageData = super.textSearch(text);
		b2bProductSearchUtil.populateVariantProducts(pageData);
		return pageData;
	}

	@Override
	public ProductSearchPageData<SearchStateData, ITEM> textSearch(final SearchStateData searchState,
			final PageableData pageableData)
	{
		final ProductSearchPageData<SearchStateData, ITEM> pageData = super.textSearch(searchState, pageableData);
		b2bProductSearchUtil.populateVariantProducts(pageData);
		return pageData;
	}

	@Override
	public ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData> categorySearch(final String categoryCode)
	{
		final ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData> pageData = super.categorySearch(categoryCode);
		b2bProductSearchUtil.populateVariantProducts(pageData);
		return pageData;
	}

	@Override
	public ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData> categorySearch(final String categoryCode,
			final SearchStateData searchState, final PageableData pageableData)
	{
		final ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData> pageData = super.categorySearch(categoryCode,
				searchState, pageableData);
		b2bProductSearchUtil.populateVariantProducts(pageData);
		return pageData;
	}

	@Override
	public ProductSearchPageData<SearchStateData, ITEM> skusSearch(final List<String> skus, final SearchStateData searchState, final PageableData pageableData)
	{
		final ProductSearchPageData<SearchStateData, ITEM> pageData = getThreadContextService().executeInContext(new ThreadContextService.Executor<ProductSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
		{
			@Override
			public ProductSearchPageData<SearchStateData, ITEM> execute()
			{
				final SolrSearchQueryData searchQueryData = (SolrSearchQueryData) getSearchQueryDecoder().convert(searchState.getQuery());

				return (ProductSearchPageData<SearchStateData, ITEM>) getProductCategorySearchPageConverter().convert(neorisProductSearchService.skusSearch(skus, searchQueryData, pageableData));
			}
		});

		b2bProductSearchUtil.populateVariantProducts(pageData);

		return pageData;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.core.commercefacades.search.NeorisProductSearchFacade#codesSearch(java.util.List, de.hybris.platform.commercefacades.search.data.SearchStateData, de.hybris.platform.commerceservices.search.pagedata.PageableData)
	 */
	@Override
	public ProductSearchPageData<SearchStateData, ITEM> codesSearch(
			final List<String> codes, final SearchStateData searchState,
			final PageableData pageableData) {
		final ProductSearchPageData<SearchStateData, ITEM> pageData = getThreadContextService().executeInContext(new ThreadContextService.Executor<ProductSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
		{
			@Override
			public ProductSearchPageData<SearchStateData, ITEM> execute()
			{
				final SolrSearchQueryData searchQueryData = (SolrSearchQueryData) getSearchQueryDecoder().convert(searchState.getQuery());

				return (ProductSearchPageData<SearchStateData, ITEM>) getProductCategorySearchPageConverter().convert(neorisProductSearchService.codesSearch(codes, searchQueryData, pageableData));
			}
		});

		b2bProductSearchUtil.populateVariantProducts(pageData);

		return pageData;
	}
}
