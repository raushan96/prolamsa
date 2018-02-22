package mx.neoris.core.services.sap.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.invoice.ValidateInvoiceLineResponse;
import mx.neoris.core.invoice.ValidateInvoiceResponse;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.services.NeorisAddressService;
import mx.neoris.core.services.NeorisIncidentService;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.NeorisValidateInvoice;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author lacantu
 * 
 */
public class DefaultSAPNeorisInvoiceValidate implements NeorisValidateInvoice
{

	public final static String B2BUNIT_SLOT = "rootunit";
	public final static String USER_SLOT = "user";
	public final static String PRODUCTUNIT_SLOT = "productUnit";
	public final static String DIST_CHANNEL_SLOT = "10";

	@Resource(name = "SAPConnectionManager")
	SAPConnectionManager sapConnection;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name = "b2bUnitService")
	B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "neorisAddressService")
	private NeorisAddressService addressService;

	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "neorisIncidentService")
	private NeorisIncidentService neorisIncidentService;

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisInvoiceValidate.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisValidateInvoice#validateInvoice(java.lang.String)
	 */
	@Override
	public ValidateInvoiceResponse validateInvoice(final String invoiceNumber, final String customer) throws Exception
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		final B2BUnitModel b2bUnit = b2bUnitService.getUnitForUid(customer);

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.VALIDATE_INVOICE.FUNCTION, custDest);

			LOG.info("SAP CONNECTION: Successfull");

			LOG.info("RFC :" + SAPConstants.RFC.VALIDATE_INVOICE.FUNCTION);

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.VALIDATE_INVOICE.INPUT_BASE_STORE, baseStoreModel.getUid());

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.VALIDATE_INVOICE.INPUT_CUSTOMER, b2bUnit.getUid());

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.VALIDATE_INVOICE.INPUT_INVOICE, invoiceNumber);

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			//execute
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.VALIDATE_INVOICE.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.VALIDATE_INVOICE.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			// invoice output table
			final JCoTable invoceOutputTable = sapFunc.getTableParameterList().getTable(
					SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_TABLE);

			final ValidateInvoiceResponse response = new ValidateInvoiceResponse();

			if (invoceOutputTable.getNumRows() > 0)
			{
				invoceOutputTable.setRow(0);

				final String country = invoceOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_COUNTRY);
				final String plant = invoceOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_PLANT);
				final String inconterm = invoceOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_INCOTERM);

				String incotermDesciption = "";

				final List<TransportationModeModel> transportationModes = baseStoreService.getCurrentBaseStore()
						.getTransportationModes();
				for (final TransportationModeModel eachTransportation : transportationModes)
				{
					if (eachTransportation.getIncotermCode().equals(inconterm))
					{
						incotermDesciption = eachTransportation.getIncotermDescription();
						break;
					}
				}

				// country
				response.setCountry(country);
				response.setCountryDisplay(country);

				// incoterm
				response.setIncoterm(inconterm);
				response.setIncotermDisplay(inconterm + " - " + incotermDesciption);

				// location
				response.setLocationCode(enumerationService.getEnumerationValue(ProductLocation.class, "_" + plant));
				response.setLocation(enumerationService.getEnumerationName(enumerationService.getEnumerationValue(
						ProductLocation.class, "_" + plant)));
			}

			// message table
			final JCoTable messageTable = sapFunc.getTableParameterList().getTable("MESSAGE");

			if (messageTable.getNumRows() > 0)
			{
				response.setMessage(messageTable.getString("MESSAGE"));
			}

			// invoice detail output table
			final JCoTable invoiceDetailOutputTable = sapFunc.getTableParameterList().getTable(
					SAPConstants.RFC.VALIDATE_INVOICE.DETAIL_OUTPUT_TABLE);

			final List<ValidateInvoiceLineResponse> lines = new ArrayList<ValidateInvoiceLineResponse>();

			if (invoiceDetailOutputTable.getNumRows() > 0)
			{
				for (int i = 0; i < invoiceDetailOutputTable.getNumRows(); i++)
				{
					final ValidateInvoiceLineResponse line = new ValidateInvoiceLineResponse();

					invoiceDetailOutputTable.setRow(i);

					final String shipToId = invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_SHIPTO);
					final AddressModel shipToModel = addressService.getAddressWithCode(shipToId);

					if (shipToModel != null)
					{
						line.setShipTo(shipToId.substring(shipToId.length() - 5));
						line.setShipToDescription(shipToModel.getStreetname());
					}
					else
					{
						LOG.error("there is not shipTo=" + shipToId + " for B2BUnit=" + b2bUnit.getUid());
					}

					line.setCustomerName(b2bUnit.getLocName());
					line.setInvoice(invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_INVOICE));
					line.setInvoice_p(invoiceDetailOutputTable.getInt(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_INVOICE_P));
					line.setSorder(invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_SORDER));
					line.setSorder_p(invoiceDetailOutputTable.getInt(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_SORDER_P));


					final String productCode = invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_PRODUCT);
					final CustomerProductReferenceModel customerProductReferenceModel = customerProductReferenceService
							.getWithProductCodeAndB2BUnit(productCode.substring(productCode.length() - 6), b2bUnit,
									baseStoreModel.getUid() + "ProductCatalog");

					if (customerProductReferenceModel != null)
					{
						line.setProduct(customerProductReferenceModel.getCode());
						line.setProductDescription(customerProductReferenceModel.getDescription());
					}
					else
					{
						try
						{
							final ProductModel productModel = productService.getProductForCode((productCode.substring(productCode
									.length() - 6) + "_" + invoiceDetailOutputTable
									.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_PLANT)));
							line.setProduct(productCode.substring(productCode.length() - 6));
							line.setProductDescription(productModel.getName());
						}
						catch (final Exception ex)
						{
							LOG.error(ex.getMessage());
							line.setProduct(productCode.substring(productCode.length() - 6));
							line.setProductDescription(invoiceDetailOutputTable
									.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_DESCRIPTION));
						}

					}

					line.setBatch(invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_BATCH));
					line.setNetweight(invoiceDetailOutputTable.getDouble(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_WEIGHT));
					line.setWeightUnit(invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_WEIGHT_UNIT));
					line.setQuantity(invoiceDetailOutputTable.getInt(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_QUANTITY));
					line.setSalesUnit(invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_SALES_UNIT));
					line.setPlant(invoiceDetailOutputTable.getString(SAPConstants.RFC.VALIDATE_INVOICE.OUTPUT_PLANT));

					lines.add(line);



				}

				response.setInvoiceLines(lines);

				response.setHasIncidentReports(neorisIncidentService.hasIncidentReports(invoiceNumber));

			}

			return response;

		}
		catch (final Exception ex)
		{
			LOG.error("Error while executing RFC: " + SAPConstants.RFC.VALIDATE_INVOICE.FUNCTION + " With values VKORG="
					+ SAPConstants.RFC.VALIDATE_INVOICE.INPUT_BASE_STORE + " KUNNR="
					+ SAPConstants.RFC.VALIDATE_INVOICE.INPUT_CUSTOMER + " INVOICE=" + invoiceNumber, ex);

		}
		return null;
	}
}
