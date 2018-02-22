/**
 * 
 */
package mx.neoris.storefront.jasper;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.commons.beanutils.PropertyUtils;


public class JasperDataSource implements JRDataSource
{
	@SuppressWarnings("unused")
	private Class<?> objectClass = null;
	private List<?> data = null;
	private Integer index = null;

	public JasperDataSource(final Class<?> aClass, final List<?> col)
	{
		objectClass = aClass;
		data = col;
	}

	public Object getFieldValue(final JRField field) throws JRException
	{
		final Object object = data.get(index);
		Object value = null;

		try
		{
			value = PropertyUtils.getProperty(object, field.getName());
		}
		catch (final Exception e)
		{
			throw new JRException(e.getMessage());
		}

		return value;
	}

	public boolean next() throws JRException
	{
		if (index == null)
		{
			index = 0;
		}
		else
		{
			index++;
		}

		return (index < data.size());
	}
}