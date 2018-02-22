/**
 * 
 */
package mx.neoris.core.services.sap.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.NeorisProductInventoryService;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author fdeutsch
 * @modified Christian.Loredo
 * 
 */
public class DefaultSAPNeorisProductInventoryService implements NeorisProductInventoryService
{
	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisProductInventoryService.class);

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	public Map<String, ProductInventoryEntry> getProductInventoryEntriesFor(final List<String> productCodes,
			final List<String> locationCodes, final String customerType) throws Exception
	{


		if (customerType.equalsIgnoreCase("prolamsa_no_inventory"))
		{
			return getProductInventoryForNoInventoryRole(productCodes, locationCodes);
		}

		final Map<String, ProductInventoryEntry> map = new HashMap<String, ProductInventoryEntry>();

		String customerTypeNumber = "1";
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		try
		{
			LOG.info("SAP CONNECTION: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX 
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.PRODUCT_INVENTORY.FUNCTION_NAME, custDest);

			LOG.info("SAP CONNECTION: Successfull");

			if (customerType.equalsIgnoreCase("prolamsa_internal"))
			{
				customerTypeNumber = "2";
			}

			//System.out.println("RFC: " + SAPConstants.RFC.PRODUCT_INVENTORY.FUNCTION_NAME);
			//System.out.println("Customer Type: " + customerTypeNumber);
			LOG.info("RFC: " + SAPConstants.RFC.PRODUCT_INVENTORY.FUNCTION_NAME);
			LOG.info("Customer Type: " + customerTypeNumber);

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PRODUCT_INVENTORY.CUSTOMER_TYPE, customerTypeNumber);

			//fill up the fields
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.PRODUCT_INVENTORY.INPUT_TABLE);

			final Integer size = productCodes.size();

			for (int i = 0; i < size; i++)
			{
				inputTable.appendRow();
				inputTable.setValue(SAPConstants.RFC.PRODUCT_INVENTORY.PRODUCT_CODE, SAPUtils.rellenaZeros(productCodes.get(i), 18));
				LOG.info("Product " + SAPUtils.rellenaZeros(productCodes.get(i), 18));
				inputTable.setValue(SAPConstants.RFC.PRODUCT_INVENTORY.LOCATION_CODE, locationCodes.get(i).substring(1, 5));
				LOG.info("Location " + locationCodes.get(i).substring(1, 5));
			}


			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PRODUCT_INVENTORY.FUNCTION_NAME,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PRODUCT_INVENTORY.FUNCTION_NAME,
					currentUser.getUid(), false, sessionActual));

			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.PRODUCT_INVENTORY.OUTPUT_TABLE);
			LOG.info("Result " + SAPConstants.RFC.PRODUCT_INVENTORY.OUTPUT_TABLE);
			LOG.info("Result Nums " + resultTable.getNumRows());

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final Date hoy = new Date();
				final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				final String datetimeStr = format.format(hoy);
				final Date parseDate = format.parse(datetimeStr);
				final Date rollingScheduleDate2 = resultTable.getDate(SAPConstants.RFC.PRODUCT_INVENTORY.ROLLING_SCHEDULE);
				final String rollingScheduleDate3 = format.format(rollingScheduleDate2);
				final Date parseDateNow = format.parse(rollingScheduleDate3);

				if (parseDateNow.compareTo(parseDate) != 0)
				{
					continue;
				}

				final String productCode = resultTable.getString(SAPConstants.RFC.PRODUCT_INVENTORY.PRODUCT_CODE);
				final String locationCode = resultTable.getString(SAPConstants.RFC.PRODUCT_INVENTORY.LOCATION_CODE);

				final BigDecimal x = resultTable.getBigDecimal(SAPConstants.RFC.PRODUCT_INVENTORY.STOCK_IN_BUNDLES);
				final int x2 = x.setScale(0, RoundingMode.UP).intValue();
				final Integer stockInBundles = x2;

				//LOG.info("Product: " + productCode + "_" + locationCode + " WKBST: " + x + " WKBST_ROUND: " + stockInBundles);

				// get the location from the code
				final ProductLocation location = ProductLocation.valueOf("_" + locationCode);
				final ProductInventoryEntry inventoryEntry = new ProductInventoryEntry();

				if (stockInBundles == 0)
				{
					//LOG.info("Product without stock: " + productCode + "_" + locationCode);
					inventoryEntry.setStockOuts("SO");
				}

				//LOG.info("Product stockouts: " + productCode + "_" + locationCode + "_" + inventoryEntry.getStockOuts());

				inventoryEntry.setLocation(location);
				inventoryEntry.setAvailableStockBundles(0);
				inventoryEntry.setAvailableStockBundlesInternal(0);
				inventoryEntry.setNoInventoyRoleBundles(0);

				if (SAPConstants.RFC.PRODUCT_INVENTORY.PROLAMSA_INTERNAL.equalsIgnoreCase(customerTypeNumber))
				{
					inventoryEntry.setAvailableStockBundlesInternal(stockInBundles);
				}
				else
				{
					inventoryEntry.setAvailableStockBundles(stockInBundles);
				}

				final JCoTable resultDateTable = sapFunc.getTableParameterList().getTable(
						SAPConstants.RFC.PRODUCT_INVENTORY.OUTPUT_TABLE);

				//final List<Date> rollingScheduleDates2 = new ArrayList<Date>();
				final Map<String, Date> mapDate = new HashMap<String, Date>();

				for (int j = 0; j < resultDateTable.getNumRows(); j++)
				{
					resultDateTable.setRow(j);
					if ((productCode.equalsIgnoreCase(resultDateTable.getString(SAPConstants.RFC.PRODUCT_INVENTORY.PRODUCT_CODE)))
							&& (locationCode.equalsIgnoreCase(resultDateTable
									.getString(SAPConstants.RFC.PRODUCT_INVENTORY.LOCATION_CODE))))
					{
						final Date rollingScheduleDateX = resultDateTable.getDate(SAPConstants.RFC.PRODUCT_INVENTORY.ROLLING_SCHEDULE);

						//NEORIS_CHANGE #validate not include today schedule
						if (!format.format(rollingScheduleDateX).equals(datetimeStr))
						{
							LOG.info("Rolling Sched: " + rollingScheduleDateX);
							mapDate.put(productCode.substring(12, 18) + location.getCode() + rollingScheduleDateX, rollingScheduleDateX);
						}
						continue;
					}
					else
					{
						continue;
					}
				}

				final List<Date> rollingScheduleDates2 = new ArrayList<Date>(mapDate.values());

				//13Sep2016 CILS
				//Ordena las fechas para que se muestre la más proxima
				Collections.sort(rollingScheduleDates2);

				inventoryEntry.setRollingScheduleDates(rollingScheduleDates2);
				inventoryEntry.setRollingScheduleBundles(20);

				map.put(productCode.substring(12, 18) + location.getCode(), inventoryEntry);
			}
		}
		catch (final Exception ex)
		{
			LOG.error("error while retriving product inventory information from SAP", ex);
		}

		return map;
	}

	/**
	 * @param productCodes
	 * @param locationCodes
	 * @return
	 */
	private Map<String, ProductInventoryEntry> getProductInventoryForNoInventoryRole(final List<String> productCodes,
			final List<String> locationCodes)
	{
		final Map<String, ProductInventoryEntry> map = new HashMap<String, ProductInventoryEntry>();

		final Integer size = productCodes.size();

		for (int i = 0; i < size; i++)
		{
			final ProductInventoryEntry entry = new ProductInventoryEntry();
			entry.setAvailableStockBundles(0);
			entry.setAvailableStockBundlesInternal(0);
			entry.setLocation(ProductLocation.valueOf(locationCodes.get(i)));
			entry.setRollingScheduleDates(Collections.<Date> emptyList());
			entry.setRollingScheduleBundles(0);

			// At this point the number of bundles for a user with no stock visibility is 20
			// it may change to a configurable attribute by base store or something like that.

			final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

			int maxQtyComboOptions = 20;
			if (baseStoreModel != null)
			{
				maxQtyComboOptions = baseStoreModel.getMaxBundlesInventory();
			}
			entry.setNoInventoyRoleBundles(maxQtyComboOptions);

			map.put(productCodes.get(i) + locationCodes.get(i), entry);

		}

		return map;
	}
}
