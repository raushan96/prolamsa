/**
 * 
 */
package mx.neoris.core.commerceservices.search;

import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;

import java.util.List;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisProductSearchService<STATE, ITEM, RESULT extends ProductSearchPageData<STATE, ITEM>>
{
	public abstract RESULT skusSearch(List<String> skus, SolrSearchQueryData solrSearchQueryData, PageableData paramPageableData);

	public abstract RESULT codesSearch(List<String> codes, SolrSearchQueryData solrSearchQueryData, PageableData paramPageableData);
}
