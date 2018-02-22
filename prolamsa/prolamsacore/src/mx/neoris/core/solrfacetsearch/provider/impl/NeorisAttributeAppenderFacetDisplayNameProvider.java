/**
 * 
 */
package mx.neoris.core.solrfacetsearch.provider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractFacetValueDisplayNameProvider;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import java.util.Locale;


/**
 * @author fdeutsch
 * 
 */
public class NeorisAttributeAppenderFacetDisplayNameProvider extends AbstractFacetValueDisplayNameProvider
{
	public static final String DEFAULT_SEPARATOR = "_";

	private String separator = DEFAULT_SEPARATOR;

	private Locale getLocale(final String isoCode)
	{
		final String[] splitted_code = isoCode.split("_");

		Locale result;

		if (splitted_code.length == 1)
		{
			result = new Locale(splitted_code[0]);
		}
		else
		{
			result = new Locale(splitted_code[0], splitted_code[1]);
		}

		return result;
	}

	@Override
	public String getDisplayName(final SearchQuery paramSearchQuery, final IndexedProperty paramIndexedProperty,
			final String paramString)
	{
		//final Locale locale = getLocale(paramSearchQuery.getLanguage());
		return paramIndexedProperty.getName() + getSeparator() + paramString;
	}

	/**
	 * @return the separator
	 */
	public String getSeparator()
	{
		return separator;
	}

	/**
	 * @param separator
	 *           the separator to set
	 */
	public void setSeparator(final String separator)
	{
		this.separator = separator;
	}
}
