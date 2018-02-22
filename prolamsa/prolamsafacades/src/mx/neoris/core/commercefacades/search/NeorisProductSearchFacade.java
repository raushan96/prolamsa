/**
 * 
 */
package mx.neoris.core.commercefacades.search;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.List;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisProductSearchFacade<ITEM extends ProductData> extends ProductSearchFacade<ITEM>
{
	ProductSearchPageData<SearchStateData, ITEM> skusSearch(final List<String> skus, final SearchStateData searchState, final PageableData pageableData);
	ProductSearchPageData<SearchStateData, ITEM> codesSearch(final List<String> codes, final SearchStateData searchState, final PageableData pageableData);
}
