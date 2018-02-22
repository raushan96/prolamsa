/**
 * 
 */
package mx.neoris.core.dataimport.batch.task;

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchException;
import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.CronJobService;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.NeorisProductVisibilityService;
import mx.neoris.core.updaters.impl.NeorisEmailNotifier;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.jenkov.io.MapTokenResolver;
import com.jenkov.io.TokenReplacingReader;


/**
 * @author fdeutsch
 * 
 */
public class NeorisImpexPatternRunnerTask extends NeorisEmailNotifier
{
	public static final Integer MAX_STACK_TRACE_SIZE = 4096;

	private static final Logger LOG = Logger.getLogger(NeorisImpexPatternRunnerTask.class);

	@Resource(name = "importService")
	private ImportService importService;

	@Resource(name = "cronJobService")
	private CronJobService cronJobService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "categoryService")
	private CategoryService categoryService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisProductVisibilityService")
	private NeorisProductVisibilityService neorisProductVisibilityService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;


	private String fileEncoding = "UTF-8";
	private String storeBaseDirectory;
	private ImportConfig importConfig;
	private Boolean executeSOLRIndexer = false;

	private Map<String, String> replacePattern;

	private String modeSOLRIndexer = "update";

	public BatchHeader execute(final File file) throws FileNotFoundException
	{
		final String message = "Importing [" + file.getName() + "]...";

		LOG.info(message + "START");

		final BatchHeader result = new BatchHeader();
		result.setFile(file);
		result.setStoreBaseDirectory(storeBaseDirectory);

		InputStreamReader reader = null;
		File impexFile = null;
		FileInputStream impexFileStream = null;
		Writer writer = null;
		FileOutputStream output = null;

		final ImportResult importResult;

		try
		{
			LOG.info("Creating stream for parser processing");
			reader = new InputStreamReader(new FileInputStream(file), getFileEncoding());

			// create temp file
			impexFile = File.createTempFile("NeorisImpexPattern-", ".impex");
			output = new FileOutputStream(impexFile);

			// create writer file based on output stream
			writer = new BufferedWriter(new OutputStreamWriter(output, getFileEncoding()));

			// process received file with defined patterns
			processStream(reader, writer);

			// close streams and writer
			reader.close();
			output.close();
			writer.close();

			LOG.info("Parser processing done");

			// open converted Impex file
			impexFileStream = new FileInputStream(impexFile);

			// set file as script
			importConfig.setScript(new StreamBasedImpExResource(impexFileStream, getFileEncoding()));

			// import Impex
			importResult = importService.importData(importConfig);

			// if Impex has unresolved lines, show on the logs
			if (importResult.hasUnresolvedLines())
			{
				final String errorMsg = importResult.getUnresolvedLines().getPreview();

				LOG.error(errorMsg);

				//new

				/*
				 * final CSVReader reader1 = new CSVReader("input.csv", "utf-8"); final Importer importer = new
				 * Importer(reader1); Item item = null; do { item = importer.importNext(); //System.out.println(
				 * "Processed items: " + getProcessedItemsCountOverall() ); } while (item != null);
				 */
				//end new

				throw new Exception(errorMsg);
			}

			// if Impex has error throw Exception
			if (importResult.isError())
			{
				final String errorMsg = importResult.getCronJob().getLogText();
				throw new Exception(errorMsg);
			}

			if (getExecuteSOLRIndexer())
			{
				// close the impex
				impexFileStream.close();
				// open again
				impexFileStream = new FileInputStream(impexFile);

				// protected the executeSOLR of any errors, as the impex file has been successfully loaded
				try
				{
					// extract store uid from impex file
					final String storeUid = extractStoreUidFromImpex(impexFileStream);

					//NEORIS_CHANGE# Execute Visibility
					executeUpdateVisibility(storeUid);

					// run solr indexer
					executeSOLRIndexerFrom(storeUid);
				}
				catch (final Exception ex)
				{
					// do nothing
				}
			}

			LOG.info(message + "SUCCESS");
		}
		catch (final Exception ex)
		{
			String errorMsg = ex.getMessage();
			if (errorMsg == null)
			{
				errorMsg = ExceptionUtils.getFullStackTrace(ex);
			}

			//final String truncatedMessage = errorMsg.substring(0, Math.min(MAX_STACK_TRACE_SIZE, errorMsg.length() - 1));
			final String truncatedMessage = errorMsg;

			// send email with error message 
			sendEmailMessageWith(file, truncatedMessage);

			// do not log error stack, is done on the code previously called
			LOG.info(message + " FAILED");

			// throw exception so file will be moved to proper error directory
			throw new BatchException(message, result, ex);
		}
		finally
		{
			// close all resources
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(impexFileStream);

			// delete created Impex file
			if (impexFile != null)
			{
				impexFile.delete();
			}
		}

		return result;
	}


	//NEORIS_CHANGE#Update Visibility of the products
	public void executeUpdateVisibility(final String storeUid)
	{
		if (StringUtils.isEmpty(storeUid))
		{
			return;
		}

		final Map<String, Object> mapAttributes = new HashMap<String, Object>();


		try
		{
			LOG.info("NeorisImpexPatterRunnerTask: Getting Product Visibility Records...");
			final SearchResult<ProductVisibilityModel> listProductVisibility = neorisProductVisibilityService
					.getAllProductVisibility(storeUid);

			if (listProductVisibility.getResult() != null && listProductVisibility.getResult().size() > 0)
			{

				for (final ProductVisibilityModel productVisibility : listProductVisibility.getResult())
				{
					final CatalogModel catalog = catalogVersionService.getCatalogVersion(storeUid + "ProductCatalog", "Online")
							.getCatalog();

					// only for product visibility for store
					if (productVisibility.getCatalog().contains(catalog))
					{
						//only the active Visibilities
						if (productVisibility.getActive().equals(true) && productVisibility.getCatalog().contains(catalog))
						{
							LOG.info(productVisibility.getCode() + " is Active... processing");

							final List<CategoryModel> listCategories = productVisibility.getCategory();

							final List<ProductLocation> listLocations = productVisibility.getLocation();

							final List<String> listMaterialTypes = productVisibility.getMaterialType();

							final List<String> listSteel = productVisibility.getSteel();

							final List<String> listFinish = productVisibility.getFinish();

							final List<String> listCovering = productVisibility.getCovering();

							final List<String> listFamily = productVisibility.getFamily();

							final List<String> listSection = productVisibility.getSection();

							final List<String> listQuality = productVisibility.getQuality();

							final List<String> listIndustry = productVisibility.getIndustry();

							final List<String> listSalesArea = productVisibility.getSalesArea();

							final List<String> listSalesRep = productVisibility.getSalesRep();

							final List<String> listSalesAreaWildcards = productVisibility.getSalesAreaWildCardVisibility();

							mapAttributes.put("materialType", listMaterialTypes);
							mapAttributes.put("location", listLocations);
							mapAttributes.put("steel", listSteel);
							mapAttributes.put("finish", listFinish);
							mapAttributes.put("covering", listCovering);
							mapAttributes.put("family", listFamily);
							mapAttributes.put("section", listSection);
							mapAttributes.put("quality", listQuality);

							//Set products
							final SearchResult<ProlamsaProductModel> listProducts = neorisProductVisibilityService
									.getProductByAttribute(storeUid, mapAttributes);

							if (listProducts.getResult() != null && listProducts.getResult().size() > 0)
							{
								final List<ProlamsaProductModel> products = listProducts.getResult();

								for (final CategoryModel categories : listCategories)
								{
									CategoryModel category;

									//get only the current base store
									if (categories.getCatalogVersion().getCatalog().getId().contains(storeUid))
									{
										//final List<ProductModel> categoryProducts = category.getProducts();
										final List<ProductModel> newProducts = new ArrayList<ProductModel>();

										//newProducts.addAll(categoryProducts);
										try
										{
											category = categoryService.getCategoryForCode(categories.getCode());
										}
										catch (final Exception e)
										{
											category = null;
										}

										if (category != null)
										{

											for (final ProlamsaProductModel product : products)
											{
												if (newProducts.indexOf(product) == -1)
												{
													newProducts.add(product);
												}
											}

											category.setProducts(newProducts);
											modelService.save(category);

										}

									}

								}
							}

							//Set B2BUnit visibility

							mapAttributes.put("industry", listIndustry);
							mapAttributes.put("salesArea", listSalesArea);
							mapAttributes.put("salesRep", listSalesRep);
							mapAttributes.put("salesAreaWilcards", listSalesAreaWildcards);

							final SearchResult<B2BUnitModel> listB2BUnits = neorisProductVisibilityService.getB2BUnitByIndustry(
									storeUid, mapAttributes);

							if (listB2BUnits.getResult() != null && listB2BUnits.getResult().size() > 0)
							{
								final List<B2BUnitModel> b2bUnits = listB2BUnits.getResult();

								for (final CategoryModel categories : listCategories)
								{
									//get only the current basestore
									if (categories.getCatalogVersion().getCatalog().getId().contains(storeUid))
									{
										final CategoryModel category = categoryService.getCategoryForCode(categories.getCode());

										if (category != null)
										{

											//final List<PrincipalModel> categoryB2BUnits = category.getAllowedPrincipals();
											final List<PrincipalModel> newB2BUnits = new ArrayList<PrincipalModel>();

											//newB2BUnits.addAll(categoryB2BUnits);


											for (final B2BUnitModel b2bUnit : b2bUnits)
											{
												if (newB2BUnits.indexOf(b2bUnit) == -1)
												{
													newB2BUnits.add(b2bUnit);
												}
											}

											//add CustomerGroup
											final SearchResult<UserGroupModel> listUserGroup = neorisProductVisibilityService
													.getUserGroupByName(storeUid, "customergroup");

											if (listUserGroup.getResult() != null && listUserGroup.getResult().size() > 0)
											{
												final UserGroupModel userGroup = listUserGroup.getResult().get(0);
												if (newB2BUnits.indexOf(userGroup) == -1)
												{
													newB2BUnits.add(userGroup);
												}
											}

											//add Category Code Visibility
											final SearchResult<UserGroupModel> listCategoryGroup = neorisProductVisibilityService
													.getUserGroupByName(storeUid, productVisibility.getCode());

											if (listCategoryGroup.getResult() != null && listCategoryGroup.getResult().size() > 0)
											{
												final UserGroupModel userGroup = listCategoryGroup.getResult().get(0);
												if (newB2BUnits.indexOf(userGroup) == -1)
												{
													newB2BUnits.add(userGroup);
												}
											}

											category.setAllowedPrincipals(newB2BUnits);
											modelService.save(category);
										}
									}
								}
							}
						}
						else
						{
							// cleaning up
							LOG.info(productVisibility.getCode() + " is Inactive... cleaning up");

							final List<CategoryModel> listCategories = productVisibility.getCategory();
							for (final CategoryModel categoryModel : listCategories)
							{
								final List<PrincipalModel> newAllowedPrincipals = new ArrayList<PrincipalModel>();

								//add CustomerGroup
								final SearchResult<UserGroupModel> listUserGroup = neorisProductVisibilityService.getUserGroupByName(
										storeUid, "customergroup");

								if (listUserGroup.getResult() != null && listUserGroup.getResult().size() > 0)
								{
									final UserGroupModel userGroup = listUserGroup.getResult().get(0);
									newAllowedPrincipals.add(userGroup);
								}

								categoryModel.setAllowedPrincipals(newAllowedPrincipals);

								modelService.save(categoryModel);
							}
						}
					}
				}

				LOG.info("NeorisImpexPatterRunnerTask: Getting products successfully !!!");

			}
			else
			{
				LOG.info("NeorisImpexPatterRunnerTask: Not Found Product Visibility Records...");
			}
		}
		catch (final Exception e)
		{
			LOG.error("NeorisImpexPatterRunnerTask: (executeUpdateVisibility) Error getting products: " + e.getMessage());
		}
	}

	public void executeSOLRIndexerFrom(final String storeUid)
	{
		if (!StringUtils.isEmpty(storeUid))
		{
			final String cronJobName = modeSOLRIndexer + "-" + storeUid + "Index-cronJob";
			final CronJobModel cronJobNode1 = cronJobService.getCronJob(cronJobName);
			CronJobModel cronJobNode2 = null;

			final boolean clusterMode = configurationService.getConfiguration().getBoolean("clustermode");

			if (clusterMode)
			{
				cronJobNode2 = cronJobService.getCronJob(cronJobName);

				cronJobNode1.setNodeID(0);
				cronJobNode2.setNodeID(1);
			}


			if (cronJobNode1 != null)
			{
				LOG.info("Executing SOLR indexer nodeId 0 " + cronJobName);
				cronJobService.performCronJob(cronJobNode1, false);
				LOG.info("Finished SOLR indexer nodeId 0 " + cronJobName);
			}
			if (cronJobNode2 != null)
			{
				LOG.info("Executing SOLR indexer nodeId 1 " + cronJobName);
				cronJobService.performCronJob(cronJobNode2, false);
				LOG.info("Finished SOLR indexer nodeId 1 " + cronJobName);
			}
		}
	}

	private String extractStoreUidFromImpex(final InputStream stream)
	{
		final Scanner scanner = new Scanner(stream, getFileEncoding());

		String storeUid = "";
		while (scanner.hasNext() && storeUid.indexOf("$storeUid=") == -1)
		{
			storeUid = scanner.nextLine();
		}

		final String[] split = storeUid.split("=");

		if (split.length == 2)
		{
			storeUid = split[1];
		}

		scanner.close();

		return storeUid;
	}

	// check classes used, file content replace is memory and performance optimized
	// https://github.com/jjenkov/TokenReplacingReader
	public void processStream(final Reader source, final Writer writer) throws Exception
	{
		final MapTokenResolver resolver = new MapTokenResolver(replacePattern);

		final Reader reader = new TokenReplacingReader(source, resolver);

		int data = reader.read();
		while (data != -1)
		{
			writer.append((char) data);
			data = reader.read();
		}

		writer.flush();
		reader.close();
	}

	/**
	 * @return the fileEncoding
	 */
	public String getFileEncoding()
	{
		return fileEncoding;
	}

	/**
	 * @param fileEncoding
	 *           the fileEncoding to set
	 */
	public void setFileEncoding(final String fileEncoding)
	{
		this.fileEncoding = fileEncoding;
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
	 * @return the importConfig
	 */
	public ImportConfig getImportConfig()
	{
		return importConfig;
	}

	/**
	 * @param importConfig
	 *           the importConfig to set
	 */
	public void setImportConfig(final ImportConfig importConfig)
	{
		this.importConfig = importConfig;
	}

	/**
	 * @return the replacePattern
	 */
	public Map<String, String> getReplacePattern()
	{
		return replacePattern;
	}

	/**
	 * @param replacePattern
	 *           the replacePattern to set
	 */
	public void setReplacePattern(final Map<String, String> replacePattern)
	{
		this.replacePattern = replacePattern;
	}

	/**
	 * @return the importService
	 */
	public ImportService getImportService()
	{
		return importService;
	}

	/**
	 * @param importService
	 *           the importService to set
	 */
	public void setImportService(final ImportService importService)
	{
		this.importService = importService;
	}

	/**
	 * @return the executeSOLRIndexer
	 */
	public Boolean getExecuteSOLRIndexer()
	{
		return executeSOLRIndexer;
	}

	/**
	 * @param executeSOLRIndexer
	 *           the executeSOLRIndexer to set
	 */
	public void setExecuteSOLRIndexer(final Boolean executeSOLRIndexer)
	{
		this.executeSOLRIndexer = executeSOLRIndexer;
	}


	/**
	 * @return the neorisProductVisibilityService
	 */
	public NeorisProductVisibilityService getNeorisProductVisibilityService()
	{
		return neorisProductVisibilityService;
	}


	/**
	 * @param neorisProductVisibilityService
	 *           the neorisProductVisibilityService to set
	 */
	public void setNeorisProductVisibilityService(final NeorisProductVisibilityService neorisProductVisibilityService)
	{
		this.neorisProductVisibilityService = neorisProductVisibilityService;
	}


	/**
	 * @return the modeSOLRIndexer
	 */
	public String getModeSOLRIndexer()
	{
		return modeSOLRIndexer;
	}


	/**
	 * @param modeSOLRIndexer
	 *           the modeSOLRIndexer to set
	 */
	public void setModeSOLRIndexer(final String modeSOLRIndexer)
	{
		this.modeSOLRIndexer = modeSOLRIndexer;
	}
}