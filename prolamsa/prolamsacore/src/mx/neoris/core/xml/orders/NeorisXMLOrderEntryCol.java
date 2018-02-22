package mx.neoris.core.xml.orders;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 */

/**
 * @author fdeutsch
 * 
 */
@XmlRootElement(name = "ENTRIES")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeorisXMLOrderEntryCol
{
	@XmlElement(name = "ORDERENTRY")
	List<NeorisXMLOrderEntry> entries;

	/**
	 * @return the entries
	 */
	public List<NeorisXMLOrderEntry> getEntries()
	{
		return entries;
	}

	/**
	 * @param entries
	 *           the entries to set
	 */
	public void setEntries(final List<NeorisXMLOrderEntry> entries)
	{
		this.entries = entries;
	}
}
