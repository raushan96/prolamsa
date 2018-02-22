/**
 * 
 */
package mx.neoris.core.dataimport.batch.task;

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchException;
import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;

import java.io.File;

import mx.neoris.core.updaters.NeorisObjectUpdater;
import mx.neoris.core.updaters.impl.NeorisEmailNotifier;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;


/**
 * @author fdeutsch
 * 
 */
public class NeorisObjectUpdaterRunnerTask extends NeorisEmailNotifier
{
	public static final Integer MAX_STACK_TRACE_SIZE = 4096;

	private static final Logger LOG = Logger.getLogger(NeorisObjectUpdaterRunnerTask.class);

	private String storeBaseDirectory;
	private NeorisObjectUpdater objectUpdater;

	public BatchHeader execute(final File file) throws Exception
	{
		final String message = "Process file [" + file.getName() + "]...";

		final BatchHeader result = new BatchHeader();
		result.setFile(file);
		result.setStoreBaseDirectory(storeBaseDirectory);

		try
		{
			objectUpdater.updateFromFile(file);

			LOG.info(message + " SUCCESS");
		}
		catch (final Exception ex)
		{
			final String errorMsg = ExceptionUtils.getStackTrace(ex);
			final String truncatedMessage = errorMsg.substring(0, Math.min(MAX_STACK_TRACE_SIZE, errorMsg.length() - 1));

			// send email with error message 
			sendEmailMessageWith(file, truncatedMessage);

			// do not log error stack, is done on the code previously called
			LOG.info(message + " FAILED");
			LOG.info(truncatedMessage);

			// throw exception so file will be moved to proper error directory
			throw new BatchException(message, result, ex);
		}

		return result;
	}

	/**
	 * @return the storeBaseDirectory
	 */
	public String getStoreBaseDirectory()
	{
		return storeBaseDirectory;
	}

	/**
	 * @param storeBaseDirectory
	 *           the storeBaseDirectory to set
	 */
	public void setStoreBaseDirectory(final String storeBaseDirectory)
	{
		this.storeBaseDirectory = storeBaseDirectory;
	}

	/**
	 * @return the objectUpdater
	 */
	public NeorisObjectUpdater getObjectUpdater()
	{
		return objectUpdater;
	}

	/**
	 * @param objectUpdater
	 *           the objectUpdater to set
	 */
	public void setObjectUpdater(final NeorisObjectUpdater objectUpdater)
	{
		this.objectUpdater = objectUpdater;
	}
}
