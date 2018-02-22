/**
 * 
 */
package mx.neoris.core.jobs;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.NeorisProductService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class NeorisProductFullDescriptionUpdaterJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(NeorisProductFullDescriptionUpdaterJob.class);

	@Resource(name = "neorisProductService")
	NeorisProductService neorisProductService;

	@Resource(name = "modelService")
	ModelService modelService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	public static final String CATALOG_VERSION = "Online";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		final String CATALOGS[] =
		{ "1000ProductCatalog", "2000ProductCatalog", "5000ProductCatalog", "6000ProductCatalog" };

		try
		{
			for (final String eachCatalogId : CATALOGS)
			{
				LOG.info("Start updating producto for catalog " + eachCatalogId);
				final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(eachCatalogId,
						CATALOG_VERSION);

				final List<ProlamsaProductModel> allProducts = neorisProductService.findAllProductsFor(catalogVersionModel);

				if (allProducts == null || allProducts.size() == 0)
				{
					LOG.info("There are no products for catalog " + eachCatalogId + " due to: there are no products.");
					continue;
				}

				for (final ProlamsaProductModel eachProductModel : allProducts)
				{

					StringBuilder fullDescription = new StringBuilder();
					String comercialDescription = eachProductModel.getName(Locale.ENGLISH);
					if (StringUtils.isNotBlank(comercialDescription))
					{
						fullDescription.append(comercialDescription);
						fullDescription.append(" ");
					}

					String manufacturingDescription = eachProductModel.getManufacturingDescription(Locale.ENGLISH);
					if (StringUtils.isNotBlank(manufacturingDescription))
					{
						fullDescription.append(manufacturingDescription);
					}

					eachProductModel.setFullDescription(fullDescription.toString(), Locale.ENGLISH);

					fullDescription = new StringBuilder();
					comercialDescription = eachProductModel.getName(new Locale("ES"));
					if (StringUtils.isNotBlank(comercialDescription))
					{
						fullDescription.append(comercialDescription);
						fullDescription.append(" ");
					}
					manufacturingDescription = eachProductModel.getManufacturingDescription(new Locale("ES"));
					if (StringUtils.isNotBlank(manufacturingDescription))
					{
						fullDescription.append(manufacturingDescription);
					}

					eachProductModel.setFullDescription(fullDescription.toString(), new Locale("ES"));

					modelService.save(eachProductModel);
				}

				LOG.info("Done updating producto for catalog " + eachCatalogId);
			}


		}
		catch (final Exception e)
		{
			LOG.error("Error while migrating order attachments due to: " + e.getMessage());
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
}
