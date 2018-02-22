/**
 * 
 */
package mx.neoris.core.services.search.solrfacetsearch.querybuilder.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.impl.DefaultFreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.util.ClientUtils;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisFreeTextQueryBuilder extends DefaultFreeTextQueryBuilder
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisFreeTextQueryBuilder.class);

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String value,
			final double boost)
	{
		final String field = indexedProperty.getName();
		// use the value as it comes, no toUppercase or toLowercase, as it cannot find the products
		if (!(indexedProperty.isFacet()))
		{
			if ("text".equalsIgnoreCase(indexedProperty.getType()))
			{
				addFreeTextQuery(searchQuery, field, value, "", boost);
				addFreeTextQuery(searchQuery, field, value, "*", boost / 2.0D);
				// remove this query part as it seems to resolve products that are not a real match for the text search
				//addFreeTextQuery(searchQuery, field, value, "~", boost / 4.0D);
			}
			else if (("text_prolamsa".equalsIgnoreCase(indexedProperty.getType())))
			{
				addFreeTextQueryByProlamsa(searchQuery, field, value, "", boost / 2.0D);
			}
			else
			{
				addFreeTextQueryByProlamsa(searchQuery, field, value, "*", boost / 2.0D);
				// remove this query part as it seems to resolve products that are not a real match for the text search
				//addFreeTextQuery(searchQuery, field, value, "", boost);
				//addFreeTextQuery(searchQuery, field, value, "*", boost / 2.0D);
			}
		}
		else
		{
			LOG.warn("Not searching " + indexedProperty + ". Field Name: " + indexedProperty.getName() + ". Value: " + value
					+ ". Free text search not available in facet property. Configure an additional text property for searching.");
		}
	}

	protected void addFreeTextQueryByProlamsa(final SearchQuery searchQuery, final String field, final String value,
			final String suffixOp, final double boost)
	{
		searchQuery.searchInField(field, ClientUtils.escapeQueryChars(value) + suffixOp + "^" + boost, SearchQuery.Operator.AND);
	}
}
