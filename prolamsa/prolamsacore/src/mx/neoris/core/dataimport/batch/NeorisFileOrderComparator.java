/**
 * 
 */
package mx.neoris.core.dataimport.batch;

import java.io.File;
import java.util.Comparator;


/**
 * @author fdeutsch
 * 
 */
public class NeorisFileOrderComparator implements Comparator<File>
{
	@Override
	public int compare(final File file, final File otherFile)
	{
		return file.getName().compareTo(otherFile.getName());
	}
}
