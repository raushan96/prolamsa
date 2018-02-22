/**
 * 
 */
package mx.neoris.facades.search;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;


/**
 * General methods used by b2b search facades.
 */
public interface B2BProductSearchUtil<ITEM extends ProductData>
{
	/**
	 * Add extra product information (including variant matrix) to all the ProductData in the pageData results that has
	 * variants.
	 * 
	 * @param pageData
	 *           The {@link ProductSearchPageData} containing the results to be populated.
	 */
	void populateVariantProducts(final ProductSearchPageData<SearchStateData, ITEM> pageData);
}
