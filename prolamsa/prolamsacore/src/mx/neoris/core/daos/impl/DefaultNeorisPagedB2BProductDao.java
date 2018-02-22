/**
 * 
 */
package mx.neoris.core.daos.impl;

import de.hybris.platform.b2bacceleratorservices.dao.PagedB2BProductDao;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisPagedB2BProductDao extends DefaultPagedGenericDao<ProductModel> implements
		PagedB2BProductDao<ProductModel>
{
	private static final String FIND_PRODUCTS_BY_SKUS = "SELECT DISTINCT query.PK FROM "
			+ "("
			+ "   {{"
			+ "       SELECT {PK} AS PK FROM {Product} WHERE {code} IN (?skulist) "
			+ "   }}"
			+ "   UNION ALL "
			+ "   {{"
			+ "       SELECT {p:PK} AS PK FROM {GenericVariantProduct AS v JOIN Product AS p ON {v:baseproduct} = {p:PK} } WHERE {v:code} IN (?skulist) "
			+ "   }}" + ") query";

	private static final String FIND_PRODUCTS_BY_SKUS_AND_BASECODE = "SELECT DISTINCT query.PK FROM "
			+ "("
			+ "   {{"
			+ "       SELECT {PK} AS PK FROM {ProlamsaProduct} WHERE {code} IN (?skulist) OR {baseCode} IN (?skulist)"
			+ "   }}"
			+ "   UNION ALL "
			+ "   {{"
			+ "       SELECT {p:PK} AS PK FROM {GenericVariantProduct AS v JOIN ProlamsaProduct AS p ON {v:baseproduct} = {p:PK} } WHERE {v:code} IN (?skulist) "
			+ "   }}" + ") query";

	public DefaultNeorisPagedB2BProductDao(final String typeCode)
	{
		super(typeCode);
	}

	public SearchPageData<ProductModel> findPagedProductsForSkus(final Collection<String> skus, final PageableData pageableData)
	{
		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("skulist", skus);

		return getPagedFlexibleSearchService().search(FIND_PRODUCTS_BY_SKUS_AND_BASECODE, params, pageableData);
	}
}
