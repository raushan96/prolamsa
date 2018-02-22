/**
 * 
 */
package mx.neoris.core.hmc;

/**
 * @author e-lacantu
 *
 */

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.hmc.util.action.ActionEvent;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.util.action.ItemAction;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.neoris.core.jalo.ProductVisibility;

import org.apache.log4j.Logger;


/**
 * NeorisUpdateProductVisibility
 * 
 * @version
 */
public class NeorisUpdateProductVisibility extends ItemAction
{

	private static final long serialVersionUID = 4316020281980415513L;
	private static final Logger LOG = Logger.getLogger(NeorisUpdateProductVisibility.class);


	private static final String baseDirectory = "/hybris/data/acceleratorservices/import/";

	@Override
	public ActionResult perform(final ActionEvent e) throws JaloBusinessException
	{

		final Item item = getItem(e);
		if (item != null)
		{
			final ProductVisibility productVisibility = (ProductVisibility) item;

			if (productVisibility != null && productVisibility.getCatalog() != null && productVisibility.getCatalog().size() > 0)
			{

				final String storeUid = productVisibility.getCatalog().get(0).getId().substring(0, 4);

				executeUpdateVisibility(storeUid);

				return new ActionResult(ActionResult.OK, "Changes can take 1 or 2 minutes more", true);

			}
		}

		return new ActionResult(ActionResult.FAILED, "Catalog Field is Required", true);

	}


	//NEORIS_CHANGE#Update Visibility of the products
	public void executeUpdateVisibility(final String storeUid)
	{

		try
		{
			createManualImpexFile(storeUid);
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.error("NeorisUpdateProductVisibility: (executeUpdateVisibility), error: " + e.getMessage());
		}

	}

	public void createManualImpexFile(final String storeUid) throws CMSItemNotFoundException
	{
		try
		{

			final DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
			final String creationDate = dateFormat.format(new Date()).toString();

			final File file = File.createTempFile("prod-vis-manual-update-", ".impex");

			final BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write("# ImpEx for Manual Update of Product Visibility\n");
			output.write("\n");
			output.write("$storeUid=" + storeUid);
			output.close();

			if (file.renameTo(new File(baseDirectory + "master/prolamsa/" + file.getName())))
			{
				LOG.info("File is moved successful!");
			}
			else
			{
				LOG.error("NeorisUpdateProductVisibility: (createManualImpexFile), ERROR: File is failed to move!");
			}
		}
		catch (final IOException e)
		{
			LOG.error("NeorisUpdateProductVisibility: (createManualImpexFile), ERROR: " + e.getMessage());
		}
	}


}
