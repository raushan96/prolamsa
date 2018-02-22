/**
 * 
 */
package mx.neoris.core.xml.shippingInstructions;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author e-hicastaneda
 * 
 */
@XmlRootElement(name = "SHIPINST")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeorisXMLShippingInstructionsCol
{
	@XmlElement(name = "LINE")
	List<NeorisXMLShippingInstructionsLine> lines;

	/**
	 * @return the lines
	 */
	public List<NeorisXMLShippingInstructionsLine> getLines()
	{
		return lines;
	}

	/**
	 * @param lines
	 *           the lines to set
	 */
	public void setLines(final List<NeorisXMLShippingInstructionsLine> lines)
	{
		this.lines = lines;
	}
}
