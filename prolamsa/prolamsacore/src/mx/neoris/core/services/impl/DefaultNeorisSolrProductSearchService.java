/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.search.solrfacetsearch.impl.DefaultSolrProductSearchService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.commerceservices.search.NeorisProductSearchService;
import mx.neoris.core.search.solrfacetsearch.DefaultNeorisAllCategoriesSetFacetInjector;


/**
 * @author fdeutsch
 * 
 */
@SuppressWarnings(
{ "rawtypes", "unchecked" })
public class DefaultNeorisSolrProductSearchService<ITEM> extends DefaultSolrProductSearchService<ITEM>
		implements
		NeorisProductSearchService<SolrSearchQueryData, ITEM, ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel>>
{
	@Resource(name = "neorisAllCategoriesFacetInjector")
	DefaultNeorisAllCategoriesSetFacetInjector neorisAllCategoriesFacetInjector;

	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	private static final int SEARCH_QUERY_DEFAULT_PAGE_SIZE = 100;

	@Override
	protected ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> doSearch(
			final SolrSearchQueryData searchQueryData, final PageableData pageableData)
	{
		ServicesUtil.validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");

		final SearchQueryPageableData searchQueryPageableData = buildSearchQueryPageableData(searchQueryData, pageableData);

		final SolrSearchRequest solrSearchRequest = getSearchQueryPageableConverter().convert(searchQueryPageableData);

		final SearchQuery searchQuery = (SearchQuery) solrSearchRequest.getSearchQuery();

		if (pageableData != null)
		{
			searchQuery.setPageSize(pageableData.getPageSize());
		}
		else
		{
			searchQuery.setPageSize(SEARCH_QUERY_DEFAULT_PAGE_SIZE);
		}

		if (baseStoreService.getCurrentBaseStore() != null && baseStoreService.getCurrentBaseStore().isAllowCategoryVisibility())
		{
			neorisAllCategoriesFacetInjector.injectFacetsToQuery(searchQuery);
		}

		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);

		final ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> res = getSearchResponseConverter().convert(
				solrSearchResponse);

		return res;
	}

	@Override
	public ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> skusSearch(final List<String> skus,
			final SolrSearchQueryData searchQueryData, final PageableData pageableData)
	{
		ServicesUtil.validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");

		final SearchQueryPageableData searchQueryPageableData = buildSearchQueryPageableData(searchQueryData, pageableData);

		final SolrSearchRequest solrSearchRequest = getSearchQueryPageableConverter().convert(searchQueryPageableData);

		final SearchQuery searchQuery = (SearchQuery) solrSearchRequest.getSearchQuery();

		if (pageableData != null)
		{
			searchQuery.setPageSize(pageableData.getPageSize());
		}

		if (baseStoreService.getCurrentBaseStore() != null && baseStoreService.getCurrentBaseStore().isAllowCategoryVisibility())
		{
			neorisAllCategoriesFacetInjector.injectFacetsToQuery(searchQuery);
		}

		for (final String eachSku : skus)
		{
			searchQuery.addFacetValue("baseCode", eachSku);
		}

		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);

		final ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> res = getSearchResponseConverter().convert(
				solrSearchResponse);

		return res;
	}


	@Override
	public ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> codesSearch(final List<String> codes,
			final SolrSearchQueryData searchQueryData, final PageableData pageableData)
	{
		ServicesUtil.validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");

		final SearchQueryPageableData searchQueryPageableData = buildSearchQueryPageableData(searchQueryData, pageableData);

		final SolrSearchRequest solrSearchRequest = getSearchQueryPageableConverter().convert(searchQueryPageableData);

		final SearchQuery searchQuery = (SearchQuery) solrSearchRequest.getSearchQuery();

		if (pageableData != null)
		{
			searchQuery.setPageSize(pageableData.getPageSize());
		}

		if (baseStoreService.getCurrentBaseStore() != null && baseStoreService.getCurrentBaseStore().isAllowCategoryVisibility())
		{
			neorisAllCategoriesFacetInjector.injectFacetsToQuery(searchQuery);
		}

		for (final String eachCode : codes)
		{
			searchQuery.addFacetValue("codeForFavorites", eachCode);
		}

		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);

		final ProductCategorySearchPageData<SolrSearchQueryData, ITEM, CategoryModel> res = getSearchResponseConverter().convert(
				solrSearchResponse);

		return res;
	}
}
