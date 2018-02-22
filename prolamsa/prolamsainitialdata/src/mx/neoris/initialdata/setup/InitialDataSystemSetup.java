/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.initialdata.setup;

import de.hybris.platform.acceleratorservices.setup.AbstractSystemSetup;
import de.hybris.platform.acceleratorservices.setup.data.ImportData;
import de.hybris.platform.acceleratorservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.neoris.core.setup.CoreSystemSetup;
import mx.neoris.initialdata.constants.ProlamsaInitialDataConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




/**
 * This class provides hooks into the system's initialization and update processes.
 * 
 * @see "https://wiki.hybris.com/display/release4/Hooks+for+Initialization+and+Update+Process"
 */
@SystemSetup(extension = ProlamsaInitialDataConstants.EXTENSIONNAME)
public class InitialDataSystemSetup extends AbstractSystemSetup
{
	private static final String BTG_EXTENSION_NAME = "btg";

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(InitialDataSystemSetup.class);

	private static final String IMPORT_SAMPLE_DATA = "importSampleData";
	private static final String SAMPLE_DATA_IMPORT_FOLDER = "b2bacceleratorsampledata";

	public static final String POWERTOOLS = "powertools";

	private static final String PROLAMSA_IMPORT_FOLDER = "prolamsainitialdata";
	public static final String IMPORT_PROLAMSA_MEX = "prolamsamex";
	public static final String IMPORT_PROLAMSA_USA = "prolamsausa";
	public static final String IMPORT_AXIS = "axis";
	public static final String IMPORT_A4C = "a4c";

	public static final String IMPORT_PROLAMSA_DEFAULTS = "importProlamsaDefaults";
	public static final String IMPORT_PROLAMSA_CATEGORIES_PRODUCTS = "importCategoriesAndProducts";
	public static final String IMPORT_PROLAMSA_UNITS_ORGANIZATION = "importUnitsOrganization";
	public static final String IMPORT_PROLAMSA_USERS = "importUsers";
	public static final String IMPORT_PROLAMSA_CUSTOMER_PRODUCT_REFERENCE = "importCustomerProductReference";

	public static final String IMPORT_CMS_CONTENT = "importCmsContent";
	//public static final String IMPORT_EMAIL_CONTENT = "importEmailContent";
	public static final String IMPORT_MOCKUPS = "importMockups";

	/**
	 * Generates the Dropdown and Multi-select boxes for the project data import
	 */
	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

		params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
		params.add(createBooleanSystemSetupParameter(CoreSystemSetup.ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));
		// Add more Parameters here as your require

		//Prolamsa parameters
		params.add(createBooleanSystemSetupParameter(IMPORT_PROLAMSA_DEFAULTS, "Import Prolamsa Defaults", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_PROLAMSA_CATEGORIES_PRODUCTS, "Import Prolamsa Categories & Prodcuts",
				true));
		params.add(createBooleanSystemSetupParameter(IMPORT_PROLAMSA_UNITS_ORGANIZATION, "Import Prolamsa Units Organization", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_PROLAMSA_USERS, "Import Prolamsa Users", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_PROLAMSA_CUSTOMER_PRODUCT_REFERENCE,
				"Import Prolamsa Customer Product Reference", true));

		params.add(createBooleanSystemSetupParameter(IMPORT_CMS_CONTENT, "Import Prolamsa CMS Content", true));
		//params.add(createBooleanSystemSetupParameter(IMPORT_EMAIL_CONTENT, "Import Prolamsa Email Content", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_MOCKUPS, "Import Prolamsa Mockups", true));

		return params;
	}

	/**
	 * Implement this method to create initial objects. This method will be called by system creator during
	 * initialization and system update. Be sure that this method can be called repeatedly.
	 * 
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.ESSENTIAL, process = Process.ALL)
	public void createEssentialData(final SystemSetupContext context)
	{
		// Add Essential Data here as you require

	}

	/**
	 * Implement this method to create data that is used in your project. This method will be called during the system
	 * initialization.
	 * 
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.PROJECT, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		// Add Store imports here as you require
		// importCommonData(context, "prolamsainitialdata");
		// This would import a standard store: (one basestore, one cmssite, one product catalog, one content catalog)
		// importStoreInitialData(context, "prolamsainitialdata", "yb2baccelerator", "yb2baccelerator", Collections.singletonList("yb2baccelerator"));

		createProlamsaData(context);

		// NEORIS_CHANGE
		if (false /* getBooleanSystemSetupParameter(context, IMPORT_SAMPLE_DATA) */)
		{
			importCommonData(context, SAMPLE_DATA_IMPORT_FOLDER);

			importStoreInitialData(context, SAMPLE_DATA_IMPORT_FOLDER, "powertools", "powertools",
					Collections.singletonList("powertools"));

			final ImportData powertoolsImportData = new ImportData();
			powertoolsImportData.setProductCatalogName(POWERTOOLS);
			powertoolsImportData.setContentCatalogNames(Arrays.asList(POWERTOOLS));
			powertoolsImportData.setStoreNames(Arrays.asList(POWERTOOLS));
			// Send an event to notify any AddOns that the initial data import is complete
			getEventService().publishEvent(new SampleDataImportedEvent(context, Arrays.asList(powertoolsImportData)));
		}

	}

	protected void createProlamsaData(final SystemSetupContext context)
	{
		final String importRoot = "/" + PROLAMSA_IMPORT_FOLDER + "/import";

		if (getBooleanSystemSetupParameter(context, IMPORT_PROLAMSA_DEFAULTS))
		{
			importImpexFile(context, importRoot + "/common/00-defaults.impex");
		}

		if (getBooleanSystemSetupParameter(context, IMPORT_PROLAMSA_CATEGORIES_PRODUCTS))
		{
			importProlamsaCategoriesAndProducts(context, IMPORT_PROLAMSA_MEX);
			importProlamsaCategoriesAndProducts(context, IMPORT_PROLAMSA_USA);
			importProlamsaCategoriesAndProducts(context, IMPORT_AXIS);
			importProlamsaCategoriesAndProducts(context, IMPORT_A4C);
		}

		if (getBooleanSystemSetupParameter(context, IMPORT_PROLAMSA_UNITS_ORGANIZATION))
		{
			logInfo(context, "Begin importing Prolamsa Units Organization");

			importImpexFile(context, importRoot + "/common/04-business-organization.impex", false);

			logInfo(context, "Done importing Prolamsa Units Organization");
		}

		if (getBooleanSystemSetupParameter(context, IMPORT_PROLAMSA_USERS))
		{
			logInfo(context, "Begin importing Prolamsa Users");

			importImpexFile(context, importRoot + "/common/05-users.impex", false);
			importImpexFile(context, importRoot + "/common/05-users-organization.impex", false);

			logInfo(context, "Done importing Prolamsa Users");
		}

		if (getBooleanSystemSetupParameter(context, IMPORT_PROLAMSA_CUSTOMER_PRODUCT_REFERENCE))
		{
			logInfo(context, "Begin importing Prolamsa Customer Product Reference");

			importImpexFile(context, importRoot + "/common/06-customer-product-reference.impex", false);

			logInfo(context, "Done importing Prolamsa Customer Product Reference");
		}

		// Import Categories products visibility Impex
		if (getBooleanSystemSetupParameter(context, IMPORT_PROLAMSA_CATEGORIES_PRODUCTS))
		{
			importProlamsaCategoriesAndProductsVisibility(context, IMPORT_PROLAMSA_MEX);
			importProlamsaCategoriesAndProductsVisibility(context, IMPORT_PROLAMSA_USA);
			importProlamsaCategoriesAndProductsVisibility(context, IMPORT_AXIS);
			importProlamsaCategoriesAndProductsVisibility(context, IMPORT_A4C);
		}

		// Initialize SOLR jobs
		if (getBooleanSystemSetupParameter(context, CoreSystemSetup.ACTIVATE_SOLR_CRON_JOBS))
		{
			logInfo(context, "Begin SOLR re-index");

			executeSolrIndexerCronJob("1000" + "Index", true);
			executeSolrIndexerCronJob("2000" + "Index", true);
			executeSolrIndexerCronJob("5000" + "Index", true);
			executeSolrIndexerCronJob("6000" + "Index", true);

			logInfo(context, "Done SOLR re-index");
		}

		if (getBooleanSystemSetupParameter(context, IMPORT_CMS_CONTENT))
		{
			importContentCatalog(context, PROLAMSA_IMPORT_FOLDER, IMPORT_PROLAMSA_USA);
			importContentCatalog(context, PROLAMSA_IMPORT_FOLDER, IMPORT_PROLAMSA_MEX);
			importContentCatalog(context, PROLAMSA_IMPORT_FOLDER, IMPORT_AXIS);
			importContentCatalog(context, PROLAMSA_IMPORT_FOLDER, IMPORT_A4C);
			
			logInfo(context, "Done importing Content Catalogs");
		}


		if (getBooleanSystemSetupParameter(context, IMPORT_MOCKUPS))
		{
			logInfo(context, "Begin importing Prolamsa Mockups");

			importImpexFile(context, importRoot + "/common/50-mockups.impex", false);

			logInfo(context, "Done importing Prolamsa Mockups");
		}

		final ImportData importData = new ImportData();
		importData.setStoreNames(Arrays.asList(new String[]
		{ "1000", "2000", "5000", "6000" }));

		// Send an event to notify any AddOns that the initial data import is complete
		getEventService().publishEvent(new SampleDataImportedEvent(context, Arrays.asList(importData)));
	}

	protected void importProlamsaCategoriesAndProducts(final SystemSetupContext context, final String storeName)
	{
		logInfo(context, "Begin importing store categories & products [" + storeName + "]");

		importImpexFile(context, "/" + PROLAMSA_IMPORT_FOLDER + "/import/stores/" + storeName + "/01-metadata.impex");
		importImpexFile(context, "/" + PROLAMSA_IMPORT_FOLDER + "/import/stores/" + storeName + "/02-categories.impex");
		importImpexFile(context, "/" + PROLAMSA_IMPORT_FOLDER + "/import/stores/" + storeName + "/03-products.impex");
		importImpexFile(context, "/" + PROLAMSA_IMPORT_FOLDER + "/import/stores/" + storeName + "/04-category-visibility.impex");

		logInfo(context, "Done importing store categories & products [" + storeName + "]");
	}

	protected void importProlamsaCategoriesAndProductsVisibility(final SystemSetupContext context, final String storeName)
	{
		logInfo(context, "Begin importing store categories & products visibility [" + storeName + "]");

		importImpexFile(context, "/" + PROLAMSA_IMPORT_FOLDER + "/import/stores/" + storeName
				+ "/07-categories-products-visibility.impex");

		logInfo(context, "Done importing store categories & products visibility [" + storeName + "]");
	}

	/**
	 * Use this method to import a standard setup store.
	 * 
	 * @param context
	 *           the context provides the selected parameters and values
	 * @param storeName
	 *           the name of the store
	 * @param productCatalog
	 *           the name of the product catalog
	 * @param contentCatalogs
	 *           the list of content catalogs
	 */
	protected void importStoreInitialData(final SystemSetupContext context, final String importDirectory, final String storeName,
			final String productCatalog, final List<String> contentCatalogs)
	{
		logInfo(context, "Begin importing store [" + storeName + "]");

		importProductCatalog(context, importDirectory, productCatalog);

		for (final String contentCatalog : contentCatalogs)
		{
			importContentCatalog(context, importDirectory, contentCatalog);
		}

		logInfo(context, "Begin importing advanced personalization rules for [" + storeName + "]");

		final String importRoot = "/" + importDirectory + "/import";

		if (isExtensionLoaded(BTG_EXTENSION_NAME))
		{
			importImpexFile(context, importRoot + "/stores/" + storeName + "/btg.impex", false);
		}

		logInfo(context, "Begin importing warehouses for [" + storeName + "]");

		importImpexFile(context, importRoot + "/stores/" + storeName + "/warehouses.impex", false);

		// create product and content sync jobs
		synchronizeProductCatalog(context, productCatalog, false);
		for (final String contentCatalog : contentCatalogs)
		{
			synchronizeContentCatalog(context, contentCatalog, false);
		}
		assignDependent(context, contentCatalogs, productCatalog);

		// perform product sync job
		final boolean productSyncSuccess = synchronizeProductCatalog(context, productCatalog, true);
		if (!productSyncSuccess)
		{
			logInfo(context, "Product catalog synchronization for [" + productCatalog
					+ "] did not complete successfully, that's ok, we will rerun it after the content catalog sync.");
		}

		importImpexFile(context, importRoot + "/stores/" + storeName + "/solr.impex", false);

		// perform content sync jobs
		for (final String contentCatalog : contentCatalogs)
		{
			synchronizeContentCatalog(context, contentCatalog, true);
		}

		if (!productSyncSuccess)
		{
			// Rerun the product sync if required
			logInfo(context, "Rerunning product catalog synchronization for [" + productCatalog + "]");
			if (!synchronizeProductCatalog(context, productCatalog, true))
			{
				logError(context, "Rerunning product catalog synchronization for [" + productCatalog
						+ "], failed please consult logs for more details.", null);
			}
		}

		// Load promotions after synchronization is done
		importImpexFile(context, "/" + importDirectory + "/import/stores/" + storeName + "/promotions.impex", false);

		logInfo(context, "Begin importing store [" + storeName + "]");

		importImpexFile(context, importRoot + "/stores/" + storeName + "/points-of-service-media.impex", false);
		importImpexFile(context, importRoot + "/stores/" + storeName + "/points-of-service.impex", false);

		logInfo(context, "Done importing store [" + storeName + "]");

		// Index product data
		logInfo(context, "Begin SOLR re-index [" + storeName + "]");
		executeSolrIndexerCronJob(storeName + "Index", true);
		logInfo(context, "Done SOLR re-index [" + storeName + "]");

		if (getBooleanSystemSetupParameter(context, CoreSystemSetup.ACTIVATE_SOLR_CRON_JOBS))
		{
			logInfo(context, "Activating SOLR index job for [" + productCatalog + "]");
			activateSolrIndexerCronJobs(storeName + "Index");
		}
	}

	protected boolean isExtensionLoaded(final String extensionNameToCheck)
	{
		final List<String> loadedExtensionNames = getLoadedExtensionNames();
		return loadedExtensionNames.contains(extensionNameToCheck);
	}

	protected List<String> getLoadedExtensionNames()
	{
		final List<String> loadedExtensionNames = Registry.getCurrentTenant().getTenantSpecificExtensionNames();
		return loadedExtensionNames;
	}

	protected boolean isExtensionLoaded(final List<String> loadedExtensionNames, final String extensionNameToCheck)
	{
		return loadedExtensionNames.contains(extensionNameToCheck);
	}

	protected void importProductCatalog(final SystemSetupContext context, final String importDirectory, final String catalogName)
	{
		logInfo(context, "Begin importing Product Catalog [" + catalogName + "]");

		// Load Units
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/classifications-units.impex", false);

		// Load Categories
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/categories.impex", false);

		//Load Categories for Multidimensional Products 
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/dimension-categories.impex", false);
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/categories-media.impex", false);
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/categories-classifications.impex", false);

		// Load Suppliers
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/suppliers.impex", false);

		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/suppliers-media.impex", false);

		// Load Products
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName + "ProductCatalog/products.impex");

		//Load Multidimensional Products
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/dimension-products.impex");

		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/products-media.impex", false);

		//Load Multidimensional Products Media
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/dimension-products-media.impex", false);

		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/products-classifications.impex", false);

		// Load Products Relations
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/products-relations.impex", false);

		// Load Prices
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/products-prices.impex", false);

		//Load Product for Multidimensional Products
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/dimension-products-prices.impex", false);

		// Load Stock Levels
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/products-stocklevels.impex", false);

		// Load Stock Levels for Multidimensional Products
		importImpexFile(context, "/" + importDirectory + "/import/productCatalogs/" + catalogName
				+ "ProductCatalog/dimension-products-stock-levels.impex", false);
	}

	protected boolean synchronizeProductCatalog(final SystemSetupContext context, final String catalogName, final boolean sync)
	{
		logInfo(context, "Begin synchronizing Product Catalog [" + catalogName + "] - "
				+ (sync ? "synchronizing" : "initializing job"));

		createProductCatalogSyncJob(context, catalogName + "ProductCatalog");

		boolean result = true;

		if (sync)
		{
			final PerformResult syncCronJobResult = executeCatalogSyncJob(context, catalogName + "ProductCatalog");
			if (isSyncRerunNeeded(syncCronJobResult))
			{
				logInfo(context, "Product catalog [" + catalogName + "] sync has issues.");
				result = false;
			}
		}

		logInfo(context, "Done " + (sync ? "synchronizing" : "initializing job") + " Product Catalog [" + catalogName + "]");
		return result;
	}


	protected void importContentCatalog(final SystemSetupContext context, final String importDirectory, final String catalogName)
	{
		logInfo(context, "Begin importing Content Catalog [" + catalogName + "]");

		final String importRoot = "/" + importDirectory + "/import";

		importImpexFile(context, importRoot + "/contentCatalogs/" + catalogName + "ContentCatalog/base-cms-content.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/" + catalogName + "ContentCatalog/cms-content.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/" + catalogName + "ContentCatalog/email-content.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/" + catalogName + "ContentCatalog/cms-content-restrictions.impex",
				false);

		logInfo(context, "Done importing Content Catalog [" + catalogName + "]");
	}

	protected void importProlamsaCmsContentData(final SystemSetupContext context, final String importDirectory)
	{
		logInfo(context, "Begin importing Prolamsa CMS Content Data ");

		final String importRoot = "/" + importDirectory + "/import";

		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/10-base-cms-content.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/10-base-cms-content_en.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/10-base-cms-content_es.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/11-cms-content.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/11-cms-content_en.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/11-cms-content_es.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/12-cms-content-restrictions.impex", false);

		logInfo(context, "Done importing Prolamsa CMS Content Data ");
	}

	protected void importProlamsaEmailContentData(final SystemSetupContext context, final String importDirectory)
	{
		logInfo(context, "Begin importing Prolamsa Email Content Data ");

		final String importRoot = "/" + importDirectory + "/import";

		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/20-email-content.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/20-email-content_en.impex", false);
		importImpexFile(context, importRoot + "/contentCatalogs/catalogName/20-email-content_es.impex", false);

		logInfo(context, "Done importing Prolamsa Email Content Data ");
	}

	protected boolean synchronizeContentCatalog(final SystemSetupContext context, final String catalogName, final boolean sync)
	{
		logInfo(context, "Begin synchronizing Content Catalog [" + catalogName + "] - "
				+ (sync ? "synchronizing" : "initializing job"));

		createContentCatalogSyncJob(context, catalogName + "ContentCatalog");

		boolean result = true;

		if (sync)
		{
			final PerformResult syncCronJobResult = executeCatalogSyncJob(context, catalogName + "ContentCatalog");
			if (isSyncRerunNeeded(syncCronJobResult))
			{
				logInfo(context, "Catalog catalog [" + catalogName + "] sync has issues.");
				result = false;
			}
		}

		logInfo(context, "Done " + (sync ? "synchronizing" : "initializing job") + " Content Catalog [" + catalogName + "]");
		return result;
	}

	/**
	 * Imports Common Data
	 */
	protected void importCommonData(final SystemSetupContext context, final String importDirectory)
	{
		logInfo(context, "Importing Common Data...");

		final String importRoot = "/" + importDirectory + "/import";

		importImpexFile(context, importRoot + "/common/user-groups.impex", false);

		final List<String> loadedExtensionNames = getLoadedExtensionNames();
		if (isExtensionLoaded(loadedExtensionNames, "cmscockpit"))
		{
			importImpexFile(context, importRoot + "/cockpits/cmscockpit/cmscockpit-users.impex");
		}

		if (isExtensionLoaded(loadedExtensionNames, "productcockpit"))
		{
			importImpexFile(context, importRoot + "/cockpits/productcockpit/productcockpit-users.impex");
		}

		if (isExtensionLoaded(loadedExtensionNames, "cscockpit"))
		{
			importImpexFile(context, importRoot + "/cockpits/cscockpit/cscockpit-users.impex");
		}
	}

	protected void assignDependent(final SystemSetupContext context, final List<String> dependentContents,
			final String dependsOnProduct)
	{
		if (CollectionUtils.isNotEmpty(dependentContents) && StringUtils.isNotBlank(dependsOnProduct))
		{
			final Set<String> dependentSyncJobsNames = new HashSet<String>();
			for (final String content : dependentContents)
			{
				dependentSyncJobsNames.add(content + "ContentCatalog");
			}

			getSetupSyncJobService().assignDependentSyncJobs(dependsOnProduct + "ProductCatalog", dependentSyncJobsNames);
		}
	}

}
