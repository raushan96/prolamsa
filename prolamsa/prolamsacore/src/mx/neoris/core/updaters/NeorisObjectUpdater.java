/**
 * 
 */
package mx.neoris.core.updaters;

import java.io.File;


/**
 * @author fdeutsch
 * 
 */
public interface NeorisObjectUpdater
{
	void updateFromFile(final File file) throws Exception;
}
