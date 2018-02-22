/**
 * 
 */
package mx.neoris.core.xml.shippingInstructions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * @author e-hicastaneda
 * 
 */
@XmlRootElement(name = "LINE")
@XmlType(name = "LINE")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeorisXMLShippingInstructionsLine
{
	@XmlValue
	String lineValue;

	/**
	 * @return the lineValue
	 */
	public String getLineValue()
	{
		return lineValue;
	}

	/**
	 * @param lineValue
	 *           the lineValue to set
	 */
	public void setLineValue(final String lineValue)
	{
		this.lineValue = lineValue;
	}
}
