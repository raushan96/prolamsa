/**
 * 
 */
package mx.neoris.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author e-lacantu
 * 
 */
public class SAPUtils
{
	private static String formattedUID;

	/**
	 * @param uid
	 * @return formattedUID SAP
	 */
	public static final String getFormattedUID(final String uid)
	{

		formattedUID = "";

		if (uid != null && uid != "")
		{

			for (int i = 0; i < (10 - uid.length()); i++)
			{
				formattedUID += "0";
			}

			return formattedUID + uid;
		}

		return "";
	}

	/**
	 * @param sapDate
	 * @return Hybris Date format
	 */
	public static final Date convertSAPtoHybrisDate(final String sapDate)
	{

		if (sapDate != null && sapDate != "")
		{
			final String year = sapDate.substring(0, 4);
			final String month = sapDate.substring(5, 7);
			final String day = sapDate.substring(8, 10);

			final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = null;

			try
			{
				date = sdf.parse(month + "/" + day + "/" + year);
				return date;
			}
			catch (final ParseException e)
			{
				e.printStackTrace();
			}

		}
		return null;

	}


	/**
	 * @param hybrisDate
	 * @return SAP Date format
	 */
	public static final String convertHybristoSAPDate(final Date hybrisDate)
	{

		if (hybrisDate != null)
		{

			final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			final String date = sdf.format(hybrisDate);

			final String year = date.substring(7, 10);
			final String month = date.substring(1, 2);
			final String day = date.substring(4, 5);

			return year + month + day;

		}
		return null;

	}

	/**
	 * @param hybrisDate
	 *           yyyy/MM/dd
	 * @return SAP Date format dd/MM/yyyy
	 */
	public static final Date convertSAPDate(final String hybrisDate)
	{

		if (hybrisDate != null)
		{
			final String year = hybrisDate.substring(0, 4);
			final String month = hybrisDate.substring(5, 7);
			final String day = hybrisDate.substring(8, 10);

			final Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			cal.set(Calendar.MONTH, Integer.parseInt(month));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
			return cal.getTime();
		}
		return null;

	}

	/**
	 * @param String
	 *           hybrisDate MM/dd/yyyy
	 * @return Date format MM/dd/yyyy
	 */
	public static final Date convertMM_DD_YYYY(final String hybrisDate)
	{

		if (hybrisDate != null)
		{
			final String year = hybrisDate.substring(6, 10);
			final String day = hybrisDate.substring(3, 5);
			final String month = hybrisDate.substring(0, 2);


			final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = null;

			try
			{
				date = sdf.parse(month + "/" + day + "/" + year);
			}
			catch (final ParseException e)
			{
				e.printStackTrace();
			}
			return date;

		}
		return null;

	}


	public static final String rellenaZeros(final String valor, final Integer longitud)
	{

		formattedUID = "";

		if (valor != null && valor != "")
		{

			for (int i = 0; i < (longitud - valor.length()); i++)
			{
				formattedUID += "0";
			}

			return formattedUID + valor;
		}

		return "";
	}

	public static final String getTimeStamExecutionRFC(final String prefix, final String rfc, final String user,
			final Boolean isBefore, final String sessionActual)
	{
		final Date date = new Date();
		final long time1 = date.getTime();
		final Timestamp ts = new Timestamp(time1);

		String sufix = "after execution";
		final String delimiter = "/";
		if (isBefore)
		{
			sufix = "before execution";
		}

		return prefix + delimiter + rfc + delimiter + user + delimiter + sessionActual + delimiter + sufix + delimiter + ts;
	}

	public static final String[] splitByNumber(final String s, final int size)
	{
		if (s == null || size <= 0)
		{
			return null;
		}

		final int chunks = s.length() / size + ((s.length() % size > 0) ? 1 : 0);
		final String[] arr = new String[chunks];

		for (int i = 0, j = 0, l = s.length(); i < l; i += size, j++)
		{
			arr[j] = s.substring(i, Math.min(l, i + size));
		}

		return arr;
	}
}
