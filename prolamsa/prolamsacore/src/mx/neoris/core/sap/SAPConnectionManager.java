/**
 * 
 */
package mx.neoris.core.sap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;


/**
 * basic examples for Java to ABAP communication
 */
public class SAPConnectionManager
{
	static String DESTINATION_NAME = "HYBRIS_ABAP_AS_WITH_POOL";

	private static final Logger LOG = Logger.getLogger(SAPConnectionManager.class);

	Properties properties;

	public void initialize()
	{
		try
		{
			createDestinationDataFile(DESTINATION_NAME, properties);
		}
		catch (final Exception ex)
		{
			LOG.error("error while creating SAP properties file", ex);
		}
	}

	private void createDestinationDataFile(final String destinationName, final Properties connectProperties) throws Exception
	{

		final File destCfg = new File(destinationName + ".jcoDestination");
		final FileOutputStream fos = new FileOutputStream(destCfg, false);
		connectProperties.store(fos, destinationName);
		fos.close();
	}

	public JCoDestination getDestination() throws Exception
	{

		JCoDestination dest = null;
		dest = JCoDestinationManager.getDestination(DESTINATION_NAME);
		return dest;
	}

	public JCoFunction createFunction(final String name, final JCoDestination destination) throws Exception
	{
		JCoContext.begin(destination);
		JCoFunction function = null;
		function = destination.getRepository().getFunction(name);
		return function;
	}

	public JCoFunction createFunctionCustom(final String name, final JCoCustomDestination destination) throws Exception
	{
		JCoContext.begin(destination);
		JCoFunction function = null;
		function = destination.getRepository().getFunction(name);
		return function;
	}

	//

	public JCoFunction executeFunction(final JCoFunction function, final JCoDestination destination) throws Exception
	{
		long start = 0;
		try
		{
			LOG.info(String.format("Start SAP function: %s", function.getName()));
			start = System.currentTimeMillis();
			function.execute(destination);
		}
		finally
		{
			releaseFunction(destination);
			final long time = System.currentTimeMillis() - start;
			LOG.info(String.format("End SAP function: %s", function.getName()));
			LOG.info(String.format("Average log time was %,d ms%n", time));
		}
		return function;
	}

	public JCoFunction executeFunctionCustom(final JCoFunction function, final JCoCustomDestination destination) throws Exception
	{
		long start = 0;
		try
		{
			LOG.info(String.format("Start SAP function: %s", function.getName()));
			start = System.currentTimeMillis();
			function.execute(destination);
		}
		finally
		{
			releaseFunction(destination);
			final long time = System.currentTimeMillis() - start;
			LOG.info(String.format("End SAP function: %s", function.getName()));
			LOG.info(String.format("Average log time was %,d ms%n", time));
		}
		return function;
	}

	public void releaseFunction(final JCoDestination destination)
	{
		try
		{
			JCoContext.end(destination);
		}
		catch (final JCoException e)
		{
			LOG.error(e);
		}
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * @param properties
	 *           the properties to set
	 */
	public void setProperties(final Properties properties)
	{
		this.properties = properties;
	}
}
