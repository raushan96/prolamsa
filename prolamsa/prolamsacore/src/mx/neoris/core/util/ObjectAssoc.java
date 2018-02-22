package mx.neoris.core.util;

public class ObjectAssoc
{
	String value;
	String label;


	public ObjectAssoc()
	{
		super();
	}

	public ObjectAssoc(final String valueParam, final String labelParam)
	{
		super();

		value = valueParam;
		label = labelParam;
	}

	public String getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *           the value to set
	 */
	public void setValue(final String value)
	{
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel()
	{
		if (label == null)
		{
			return value;
		}

		return label;
	}

	/**
	 * @param label
	 *           the label to set
	 */
	public void setLabel(final String label)
	{
		this.label = label;
	}
}
