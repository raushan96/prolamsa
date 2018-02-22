/**
 * 
 */
package mx.neoris.core.impex.decorators;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;


/**
 * @author fdeutsch
 * 
 */
public class NeorisEnumeratorDecorator implements CSVCellDecorator
{
	private static final String ENUM_FIRST_ALPHANUMERIC = "_";

	public String decorate(final int position, final Map<Integer, String> srcLine)
	{
		final String parsedValue = srcLine.get(position);

		if (parsedValue.length() > 0)
		{
			final Character c = parsedValue.charAt(0);

			if (Character.isDigit(c))
			{
				return ENUM_FIRST_ALPHANUMERIC + parsedValue;
			}
		}

		return parsedValue;
	}
}
