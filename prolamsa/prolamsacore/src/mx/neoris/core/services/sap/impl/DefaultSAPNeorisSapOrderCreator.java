/**
 *
 */
package mx.neoris.core.services.sap.impl;

import de.hybris.platform.b2b.model.B2BCommentModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.dao.impl.DefaultNeorisUserDao;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import mx.neoris.core.util.SAPUtils;


/**
 * @author Christian.Loredo
 *
 */
public class DefaultSAPNeorisSapOrderCreator implements NeorisSapOrderCreator
{
	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisSapOrderCreator.class);


	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2bCustomerService;

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "modelService")
	protected ModelService modelService;

	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "neorisUserDao")
	private DefaultNeorisUserDao neorisUserDao;

	@Override
	public void createSapOrderFor(final OrderModel orderModel) throws Exception
	{
		//since now this process is done from accApproval Process we are taking the user from the order
		//final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		final UserModel currentUser = orderModel.getUser();

		//Structure
		final String salesOrganization = orderModel.getStore().getUid();
		final String distributionChannel = configurationService.getConfiguration().getString("sap.parameter.distribution-channel");
		final String poType = "HYSO";
		final String incoterm = orderModel.getTransportationMode().getIncotermCode();
		final String incoterm2 = "";
		final String transportationModeCode = orderModel.getTransportationMode().getCode();
		final String purch = orderModel.getPurchaseOrderNumber();
		final String userName = orderModel.getUser().getName();
		final String uom = "PC";
		final String cartId = "";
		final String hybrisSalesOrder = "";
		final StringBuilder emailDesc = new StringBuilder();
		final String shippingInstructions = orderModel.getShippingInstructions();
		final String eol = "<br>";
		String purchAtach = "";
		if (orderModel.getAttachedPO() != null)
		{
			purchAtach = orderModel.getAttachedPO().getRealFileName();
		}

		String decisionStatusBudget = "";
		//		final B2BCustomerModel currentUser2 = (B2BCustomerModel) sessionService.getAttribute("user");
		//
		//		if (currentUser2.getApprovers().size() > 0)
		//		{
		//			decisionStatusBudget = "P";
		//		}


		//if the order is not blocked yet review permissions.
		if (!"P".equalsIgnoreCase(decisionStatusBudget))
		{
			if (isPendingOrder(orderModel))
			{
				decisionStatusBudget = "P";
			}
		}



		final String commentQuote = "";
		final String buildTo = orderModel.getUnit().getUid();
		final String deliveryAddressCode = orderModel.getDeliveryAddress().getCode();

		try
		{
			final Date creationDate = orderModel.getDate();
			final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			final String datetimeStr = format.format(creationDate);
			final Date parseDate = format.parse(datetimeStr);

			Date parseRequestedDeliveryDate = new Date();

			if (orderModel.getRequestedDeliveryDate() != null)
			{
				final Date requestedDeliveryDate = orderModel.getRequestedDeliveryDate();
				final String datetimeStrReq = format.format(requestedDeliveryDate);
				parseRequestedDeliveryDate = format.parse(datetimeStrReq);
			}
			else
			{
				throw new Exception("RequestedDeliveryDate cannot be blank");
			}

			LOG.info("SAP CONNECTION ORDER CREATOR: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX
			final JCoCustomDestination custDest = sapDest.createCustomDestination();
			//since now the publication is from a process action the current store is not available, now we need to take it from the order
			//baseStoreService.getCurrentBaseStore()

			final BaseStoreModel baseStoreModel = orderModel.getStore();
			if (baseStoreModel.getUid().equals("1000") || baseStoreModel.getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					custDest);

			LOG.info("SAP CONNECTION ORDER CREATOR: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TYPEOP,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ORDER);
			LOG.info("typeOp " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ORDER);
			emailDesc.append("<b>Type OP:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ORDER);

			//cuando es budget se manda el estatus "P"
			//cuando el pedido se crea normal se manda estatus Vacio ""
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.STATUS, decisionStatusBudget);
			LOG.info("status " + decisionStatusBudget);
			emailDesc.append("<b>status:</b> " + decisionStatusBudget);

			// input structure
			final JCoStructure structure = sapFunc.getImportParameterList()
					.getStructure(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_STRUCTURE);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALES, salesOrganization);
			LOG.info("salesOrganization " + salesOrganization);
			emailDesc.append("<b>Sales Organization:</b>" + salesOrganization + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.DISTR_CHAN, distributionChannel);
			LOG.info("distributionChannel " + distributionChannel);
			emailDesc.append("<b>Distribution Channel:</b> " + distributionChannel + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_METH_S, poType);
			LOG.info("poMethS " + poType);
			emailDesc.append("<b>Po MethS:</b> " + poType + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS1, incoterm);
			LOG.info("incoterm " + incoterm);
			emailDesc.append("<b>Incoterm:</b> " + incoterm + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS2, incoterm2);
			LOG.info("incoterm2: " + incoterm2);
			emailDesc.append("<b>Incoterm 2:</b> " + incoterm + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TRATY, transportationModeCode);
			LOG.info("transportationModeCode " + transportationModeCode);
			emailDesc.append("<b>TransportationMode Code:</b> " + transportationModeCode + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PURCH_NO_C, purch);
			LOG.info("purch " + purch);
			emailDesc.append("<b>Purch:</b> " + purch + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_ATT, purchAtach);
			LOG.info("purchAtach " + purchAtach);
			emailDesc.append("<b>Purch Atachment:</b> " + purchAtach + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ERDAT, parseDate);
			LOG.info("parseDate " + parseDate);
			emailDesc.append("<b>Parse Date:</b> " + parseDate + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.BNAME_V, userName);
			LOG.info("userName " + userName);
			emailDesc.append("<b>UserName:</b> " + userName + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
			LOG.info("uom " + uom);
			emailDesc.append("<b>UOM:</b> " + uom + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REF_1_S, cartId);
			LOG.info("cartId " + cartId);
			emailDesc.append("<b>Cart Id:</b> " + cartId + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VBELN_REF, hybrisSalesOrder);
			LOG.info("hybrisSalesOrder " + hybrisSalesOrder);
			emailDesc.append("<b>Hybris Sales Order:</b> " + hybrisSalesOrder + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.COMM_QUOTE, commentQuote);
			LOG.info("commentQuote " + commentQuote);
			emailDesc.append("<b>Comment Quote:</b> " + commentQuote + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE_H, parseRequestedDeliveryDate);
			LOG.info("requestedDeliveryDate " + parseRequestedDeliveryDate);
			emailDesc.append("<b>Requested Delivery Date:</b> " + parseRequestedDeliveryDate + eol);

			String priceGroup = "";
			if (orderModel.getIsHSSOrder() != null && orderModel.getIsHSSOrder().booleanValue())
			{
				priceGroup = "01";
			}
			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KONDA, priceGroup);
			LOG.info("pricegroup " + priceGroup);
			emailDesc.append("<b>Price Group:</b> " + priceGroup);

			final JCoTable inputTable1 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE1);

			// input detail
			for (final AbstractOrderEntryModel entry : orderModel.getEntries())
			{
				final ProlamsaProductModel product = (ProlamsaProductModel) entry.getProduct();

				inputTable1.appendRow();
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MATERIAL,
						SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				LOG.info("product.getBaseCode " + SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				emailDesc.append("<b>Product BaseCode:</b> " + SAPUtils.rellenaZeros(product.getBaseCode(), 18) + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PLANT,
						product.getLocation().getCode().substring(1, 5));
				LOG.info("Location " + product.getLocation().getCode().substring(1, 5));
				emailDesc.append("<b>Location:</b> " + product.getLocation().getCode().substring(1, 5) + eol);


				Double quantity = 0.0d;
				if (!product.getIsAPI())
				{
					quantity = entry.getQuantity() * product.getPiecesPerBundle();
				}
				else
				{
					quantity = entry.getQuantity().doubleValue();
				}

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_QTY, quantity);
				LOG.info("Quantity " + quantity);
				emailDesc.append("<b>Quantity:</b> " + quantity + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
				LOG.info("Unit " + uom);
				emailDesc.append("<b>Unit:</b> " + uom + eol);

				if (entry.getReadyToShip() != null)
				{
					final Date readyToShip = entry.getReadyToShip();
					final SimpleDateFormat formatRS = new SimpleDateFormat("yyyyMMdd");
					final String datetimeStrRS = formatRS.format(readyToShip);
					final Date readyToShipDate = formatRS.parse(datetimeStrRS);

					inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, readyToShipDate);
					LOG.info("REQ_DATE " + readyToShipDate);
					emailDesc.append("<b>Requested Date:</b> " + readyToShipDate + eol);
				}
				else
				{
					LOG.info("entry.getReadyToShip()[REQ_DATE] was null");
				}

				if (entry.getRollingScheduleWeek() != null)
				{
					final Date rollingDate = entry.getRollingScheduleWeek();
					final SimpleDateFormat formatRol = new SimpleDateFormat("yyyyMMdd");
					final String datetimeStrRol = formatRol.format(rollingDate);
					final Date parseDateRol = formatRol.parse(datetimeStrRol);

					inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, parseDateRol);
					LOG.info("REQ_DATE/RolllingSched: " + parseDateRol);
					emailDesc.append("<b>Parse Date Rolling:</b> " + parseDateRol + eol);
				}

				//01042016
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.IHREZ, entry.getStockOuts());
				LOG.info("IHREZ " + entry.getStockOuts());
				emailDesc.append("<b>IHREZ:</b> " + entry.getStockOuts() + eol);

			}

			final JCoTable inputTable3 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE3);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SOLDTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB, SAPUtils.rellenaZeros(buildTo, 10));
			LOG.info("SOLDTO " + SAPUtils.rellenaZeros(buildTo, 10));
			emailDesc.append("<b>Sold To:</b> " + SAPUtils.rellenaZeros(buildTo, 10) + eol);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SHIPTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB,
					SAPUtils.rellenaZeros(deliveryAddressCode, 10)); //  "0000030357");
			LOG.info("SHIPTO " + SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			emailDesc.append("<b>Ship To:</b> " + SAPUtils.rellenaZeros(deliveryAddressCode, 10) + eol);

			// shipping instructions
			final JCoTable inputTable4 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE4);

			final String[] shipInstSplit = SAPUtils.splitByNumber(shippingInstructions,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SHIP_INST_MAX_CHAR_PER_LINE);

			if (shipInstSplit != null)
			{
				for (int i = 0; i < shipInstSplit.length; i++)
				{
					inputTable4.appendRow();
					inputTable4.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TDLINE, shipInstSplit[i]);
				}

				LOG.info("shippingInstructions " + shippingInstructions);
				emailDesc.append("<b>Shipping Instructions:</b> " + shippingInstructions + eol);
			}

			LOG.info("INICIA 1");

			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			final JCoTable resultTable = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.OUTPUT_TABLE);

			String mensaje = "";

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);
				mensaje = mensaje + resultTable.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MESSAGE) + eol;
			}

			LOG.info("MESSAGE: " + mensaje);
			emailDesc.append("<b>SAP Message :</b> " + mensaje + eol);

			final String sapOrderID = sapFunc.getExportParameterList()
					.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALESDOCUMENT);

			if (sapOrderID == "" || sapOrderID == null)
			{
				throw new Exception("SAP Registry Failed");
			}
			//Get SAP UNIT INSIDE METHOD and SET
			setSapWeightUnit(orderModel, sapFunc);

			orderModel.setCode(sapOrderID);
			orderModel.setSapOrderId(sapOrderID);

			// update order status after SAP ID has been set
			if (orderModel.getStatus() == OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID)
			{
				orderModel.setStatus(OrderStatus.APPROVED);
			}
			else if (orderModel.getStatus() == OrderStatus.ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID)
			{
				orderModel.setStatus(OrderStatus.PENDING_APPROVAL);
			}
			else
			{
				orderModel.setStatus(OrderStatus.IN_PROCESS);
			}

			modelService.save(orderModel);

			sessionService.getCurrentSession().setAttribute("sapLabelWeightUnit", orderModel.getSapLabelWeightUnit());
			sessionService.getCurrentSession().setAttribute("sapWeightUnit", orderModel.getSapWeightUnit());
			sessionService.getCurrentSession().setAttribute("sapCurrency", orderModel.getSapCurrency());
		}
		catch (final Exception ex)
		{

			LOG.error("Error while posting order to SAP", ex);
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//Set New Subject
			subjectData.append(orderModel.getStore().getName() + " | ");
			subjectData.append("Error create order/quote | ");
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//Set New Body
			final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
			if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
			{
				bodyData.append("Buen día." + eol + eol);
				bodyData.append(
						"Se tuvo problemas al momento de crear el pedido del cliente " + orderModel.getUnit().getUid() + "." + eol);
				bodyData.append("Favor de contactar a su asesor de ventas." + eol + eol);
				bodyData.append("Saludos!" + eol);
			}
			else
			{
				bodyData.append("Good day." + eol + eol);
				bodyData.append("There was a problem creating the order client " + orderModel.getUnit().getUid() + "." + eol);
				bodyData.append("Please contact you Sales Representative." + eol + eol);
				bodyData.append("Regards!" + eol);
			}

			if (!orderModel.getStore().getSendNotificationToUserDocument())
			{
				bodyData.append("<b>Description:</b>" + eol);
				bodyData.append(eol + ex.getMessage());
			}

			B2BCustomerModel salesRep = new B2BCustomerModel();
			B2BCustomerModel backoffice = new B2BCustomerModel();
			String salesRepS = "";
			String backofficeS = "";
			String userCreator = "";

			if (orderModel.getStore().getSendNotificationToUserDocument())
			{
				userCreator = orderModel.getUser().getUid();
				LOG.info("userCreator: " + userCreator);
			}

			if (orderModel.getStore().getSendNotificationToProlamsaInternal())
			{
				salesRep = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(this.getSalesRepFor(orderModel),
						orderModel.getStore());
				if (salesRep != null)
				{
					LOG.info("Sales rep Id from B2BUnit: " + salesRep.getEmail());
					salesRepS = salesRep.getEmail();
				}


				backoffice = neorisUserDao.getB2bCustomerModelByBackofficeAndStore(this.getBackOfficeAccountFor(orderModel),
						orderModel.getStore());
				if (backoffice != null)
				{
					LOG.info("backoffice from B2BUnit: " + backoffice.getEmail());
					backofficeS = backoffice.getEmail();
				}

			}

			if (orderModel.getStore().getSendNotificationToUserDocument()
					|| orderModel.getStore().getSendNotificationToProlamsaInternal())
			{
				neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString(), salesRepS, backofficeS,
						userCreator);
			}
			else
			{
				neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());
			}

			throw new Exception("SAP Registry Failed");

		}
	}

	/**
	 * @param orderModel
	 * @param sapFunc
	 */
	private void setSapWeightUnit(final OrderModel orderModel, final JCoFunction sapFunc)
	{
		final JCoTable itemTable = sapFunc.getTableParameterList()
				.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.OUTPUT_ITEM_TABLE);

		for (int i = 0; i < itemTable.getNumRows(); i++)
		{
			itemTable.setRow(i);
			//SET SAP UNIT to order
			orderModel.setSapWeightUnit(itemTable.getString(SAPConstants.RFC.CREDIT_SCORE_CARD.UOM));
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.neoris.core.orders.NeorisSapOrdersCreator#createSapQuoteOrderFor(de.hybris.platform.core.model.order.OrderModel )
	 */
	@Override
	public void createSapQuoteOrderFor(final OrderModel orderModel) throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		//Structure
		final String salesOrganization = orderModel.getStore().getUid();
		final String distributionChannel = configurationService.getConfiguration().getString("sap.parameter.distribution-channel");
		final String poType = "HYSO";
		final String incoterm = orderModel.getTransportationMode().getIncotermCode();
		final String incoterm2 = "";
		final String transportationModeCode = orderModel.getTransportationMode().getCode();
		final String purch = orderModel.getPurchaseOrderNumber();
		final String userName = orderModel.getUser().getName();
		final String uom = "PC";
		final String cartId = "";
		final String hybrisSalesOrder = "";
		final String commentQuote = "";
		final StringBuilder emailDesc = new StringBuilder();
		final String eol = "<b>";
		String purchAtach = "";
		if (orderModel.getAttachedPO() != null)
		{
			purchAtach = orderModel.getAttachedPO().getRealFileName();
		}


		final String buildTo = orderModel.getUnit().getUid();
		final String deliveryAddressCode = orderModel.getDeliveryAddress().getCode();

		try
		{
			final Date creationDate = orderModel.getDate();
			//final SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
			final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			final String datetimeStr = format.format(creationDate);
			final Date parseDate = format.parse(datetimeStr);

			final Date requestedDeliveryDate = orderModel.getRequestedDeliveryDate();
			final String datetimeStrReq = format.format(requestedDeliveryDate);
			final Date parseRequestedDeliveryDate = format.parse(datetimeStrReq);


			LOG.info("SAP CONNECTION ORDER CREATOR: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			//since now the publication is from a process action the current store is not available, now we need to take it from the order
			//baseStoreService.getCurrentBaseStore()

			final BaseStoreModel baseStoreModel = orderModel.getStore();
			if (baseStoreModel.getUid().equals("1000") || baseStoreModel.getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					custDest);

			LOG.info("SAP CONNECTION ORDER CREATOR: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TYPEOP,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.STATUS, "P");

			// input structure
			final JCoStructure structure = sapFunc.getImportParameterList()
					.getStructure(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_STRUCTURE);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALES, salesOrganization);
			LOG.info("salesOrganization " + salesOrganization);
			emailDesc.append("<b>Sales Organization:</b> " + salesOrganization + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.DISTR_CHAN, distributionChannel);
			LOG.info("distributionChannel " + distributionChannel);
			emailDesc.append("<b>Distribution Channel:</b> " + distributionChannel + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_METH_S, poType);
			LOG.info("poType " + poType);
			emailDesc.append("<b>Po Type:</b> " + poType + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS1, incoterm);
			LOG.info("incoterm " + incoterm);
			emailDesc.append("<b>Incoterm:</b> " + incoterm + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS2, incoterm2);
			LOG.info("incoterm2 " + incoterm2);
			emailDesc.append("<b>Incoterm 2:</b> " + incoterm2 + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TRATY, transportationModeCode);
			LOG.info("transportationModeCode " + transportationModeCode);
			emailDesc.append("<b>TransportationMode Code:</b> " + transportationModeCode + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PURCH_NO_C, purch);
			LOG.info("purch " + purch);
			emailDesc.append("<b>Purch:</b> " + purch + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_ATT, purchAtach);
			LOG.info("purchAtach " + purchAtach);
			emailDesc.append("<b>Purch Atach:</b> " + purchAtach + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ERDAT, parseDate);
			LOG.info("parseDate " + parseDate);
			emailDesc.append("<b>Parse Date:</b> " + parseDate + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.BNAME_V, userName);
			LOG.info("userName " + userName);
			emailDesc.append("<b>User Name:</b> " + userName + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
			LOG.info("uom " + uom);
			emailDesc.append("<b>UOM:</b> " + uom + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REF_1_S, cartId);
			LOG.info("cartId " + cartId);
			emailDesc.append("<b>Cart Id:</b> " + cartId + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VBELN_REF, hybrisSalesOrder);
			LOG.info("hybrisSalesOrder " + hybrisSalesOrder);
			emailDesc.append("<b>Hybris Sales Order:</b> " + hybrisSalesOrder + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.COMM_QUOTE, commentQuote);
			LOG.info("commentQuote " + commentQuote);
			emailDesc.append("<b>Comment Quote:</b> " + commentQuote + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE_H, parseRequestedDeliveryDate);
			LOG.info("requestedDeliveryDate " + parseRequestedDeliveryDate);
			emailDesc.append("<b>Requested Delivery Date:</b> " + parseRequestedDeliveryDate + eol);

			final JCoTable inputTable1 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE1);

			// input detail
			for (final AbstractOrderEntryModel entry : orderModel.getEntries())
			{
				final ProlamsaProductModel product = (ProlamsaProductModel) entry.getProduct();

				inputTable1.appendRow();
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MATERIAL,
						SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				LOG.info("product.getBaseCode " + SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				emailDesc.append("<b>Product BaseCode:</b> " + SAPUtils.rellenaZeros(product.getBaseCode(), 18) + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PLANT,
						product.getLocation().getCode().substring(1, 5));
				LOG.info("Location " + product.getLocation().getCode().substring(1, 5));
				emailDesc.append("<b>Location:</b> " + product.getLocation().getCode().substring(1, 5) + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_QTY,
						(entry.getQuantity() * product.getPiecesPerBundle()));
				LOG.info("Quantity " + (entry.getQuantity() * product.getPiecesPerBundle()));
				emailDesc.append("<b>Quantity:</b> " + (entry.getQuantity() * product.getPiecesPerBundle()) + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
				LOG.info("Unit " + uom);
				emailDesc.append("<b>Unit:</b> " + uom + eol);

				final Date readyToShip = entry.getReadyToShip();
				final SimpleDateFormat formatRS = new SimpleDateFormat("yyyyMMdd");
				final String datetimeStrRS = formatRS.format(readyToShip);
				final Date readyToShipDate = formatRS.parse(datetimeStrRS);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, readyToShipDate);
				LOG.info("REQ_DATE " + readyToShipDate);
				emailDesc.append("<b>Request Date:</b> " + readyToShipDate + eol);

				if (entry.getRollingScheduleWeek() != null)
				{

					final Date rollingDate = entry.getRollingScheduleWeek();
					final SimpleDateFormat formatRol = new SimpleDateFormat("yyyyMMdd");
					final String datetimeStrRol = formatRol.format(rollingDate);
					final Date parseDateRol = formatRol.parse(datetimeStrRol);

					inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, parseDateRol);
					LOG.info("REQ_DATE/RollingSched: " + parseDateRol);
					emailDesc.append("<b>Parse Date Rolling:</b> " + parseDateRol + eol);
				}

				//01042016
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.IHREZ, entry.getStockOuts());
				LOG.info("IHREZ " + entry.getStockOuts());
				emailDesc.append("<b>IHREZ:</b> " + entry.getStockOuts() + eol);

			}

			final JCoTable inputTable3 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE3);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SOLDTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB, SAPUtils.rellenaZeros(buildTo, 10));
			LOG.info("SOLDTO " + SAPUtils.rellenaZeros(buildTo, 10));
			emailDesc.append("<b>Sold To:</b> " + SAPUtils.rellenaZeros(buildTo, 10) + eol);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SHIPTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB,
					SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			LOG.info("SHIPTO " + SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			emailDesc.append("<b>Ship To:</b> " + SAPUtils.rellenaZeros(deliveryAddressCode, 10) + eol);

			LOG.info("INICIA 2");
			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			final JCoTable resultTable = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.OUTPUT_TABLE);

			String mensaje = "";

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);
				mensaje = mensaje + resultTable.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MESSAGE) + eol;
			}


			LOG.info("MESSAGE: " + mensaje);
			emailDesc.append("<b>SAP Message :</b> " + mensaje + eol);

			final String sapOrderID = sapFunc.getExportParameterList()
					.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALESDOCUMENT);

			if (sapOrderID == "" || sapOrderID == null)
			{
				throw new Exception("SAP Registry Failed");
			}
			//Get SAP UNIT INSIDE METHOD and SET
			setSapWeightUnit(orderModel, sapFunc);
			orderModel.setCode(sapOrderID);
			orderModel.setSapOrderId(sapOrderID);
			//orderModel.setOriginalUserOrder(orderModel.getUser().getUid());
		}
		catch (final Exception ex)
		{

			//LOG.error("Error while posting Quote to SAP", ex);
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(orderModel.getStore().getUid() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CREATE_ORDER.SUBJECT + " ");
			subjectData.append(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + " | ");
			subjectData.append(orderModel.getUser().getName() + " | ");

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//set the body
			bodyData.append("<b>Store name:</b> " + orderModel.getStore().getName() + eol + eol);
			bodyData.append("<b>User:</b> " + orderModel.getUser().getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Quote Creation" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());

			throw new Exception("SAP Registry Failed");
		}

	}

	protected String getSalesRepFor(final OrderModel orderModel)
	{
		final BaseStoreModel orderBaseStore = orderModel.getStore();
		final B2BUnitModel orderB2BUnit = orderModel.getUnit();
		final Set<NeorisB2BUnitSettingByStoreModel> salesRepIds = orderB2BUnit.getSalesRepForStore();

		String salesRepId = "00000";

		for (final NeorisB2BUnitSettingByStoreModel eachSaleRepId : salesRepIds)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase(eachSaleRepId.getStoreId()))
			{
				salesRepId = eachSaleRepId.getSetting();
			}
		}
		return salesRepId;
	}

	protected String getBackOfficeAccountFor(final OrderModel orderModel)
	{
		final BaseStoreModel orderBaseStore = orderModel.getStore();
		final B2BUnitModel orderB2BUnit = orderModel.getUnit();
		final Set<NeorisB2BUnitSettingByStoreModel> backofficeAccounts = orderB2BUnit.getBackOfficeAccountForStore();

		String backofficeID = "00000";

		for (final NeorisB2BUnitSettingByStoreModel eachBackoffice : backofficeAccounts)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase(eachBackoffice.getStoreId()))
			{
				backofficeID = eachBackoffice.getSetting();
			}
		}
		return backofficeID;
	}

	@Override
	public void rejectOrAcceptSapOrderForCode(final String orderCode, final String comment, final String rejectOrAccept)
			throws Exception
	{
		final StringBuilder emailDesc = new StringBuilder();
		final String eol = "<b>";

		final OrderModel orderModel = b2bOrderService.getOrderForCode(orderCode);

		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		if (orderModel == null)
		{
			throw new UnknownIdentifierException("text.quote.number.notexist");
		}

		try
		{
			LOG.info("SAP CONNECTION ORDER REJECT: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.REJECTED_QUOTE_ORDER.FUNCTION, custDest);

			LOG.info("SAP CONNECTION ORDER REJECT: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.REJECTED_QUOTE_ORDER.DOCUMENT,
					SAPUtils.rellenaZeros(orderCode, 10));
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.REJECTED_QUOTE_ORDER.STATUS, rejectOrAccept);
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.REJECTED_QUOTE_ORDER.COMMENTS, comment);

			LOG.info(SAPUtils.rellenaZeros(orderCode, 10));
			emailDesc.append("<b>Order :</b> " + SAPUtils.rellenaZeros(orderCode, 10) + eol);

			LOG.info(rejectOrAccept);
			emailDesc.append("<b>Status :</b> " + rejectOrAccept + eol);

			LOG.info(comment);
			emailDesc.append("<b>Comment :</b> " + comment + eol);

			LOG.info("INICIA 3");
			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.REJECTED_QUOTE_ORDER.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.REJECTED_QUOTE_ORDER.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			final String process = sapFunc.getExportParameterList().getString(SAPConstants.RFC.REJECTED_QUOTE_ORDER.PROCESS);

			if (process == "" || process == "NOK")
			{
				throw new Exception("text.quote.sap.error");
			}
		}
		catch (final Exception ex)
		{

			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(baseStoreService.getCurrentBaseStore().getName() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CREATE_ORDER.SUBJECT + " ");
			subjectData.append(SAPConstants.RFC.REJECTED_QUOTE_ORDER.FUNCTION + " | ");
			subjectData.append(b2bCustomerService.getCurrentB2BCustomer().getName() + " | ");

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//set the body
			bodyData.append("<b>Store name:</b> " + baseStoreService.getCurrentBaseStore().getName() + eol + eol);
			bodyData.append("<b>User:</b> " + b2bCustomerService.getCurrentB2BCustomer().getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.REJECTED_QUOTE_ORDER.FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error SAP change status FAILED" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());
			throw new Exception("text.quote.sap.error");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.neoris.core.orders.NeorisSapOrderCreator#createSapQuoteOrderWithCart(de.hybris.platform.core.model.order.
	 * OrderModel , de.hybris.platform.core.model.order.CartModel)
	 */
	@Override
	@Deprecated
	public void createSapQuoteOrderWithCart(final OrderModel orderModel, final CartModel cartModel) throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		//Structure
		final String salesOrganization = orderModel.getStore().getUid();
		final String distributionChannel = configurationService.getConfiguration().getString("sap.parameter.distribution-channel");
		final String poType = "HYSO";
		final String incoterm = orderModel.getTransportationMode().getIncotermCode();
		final String incoterm2 = "";
		final String transportationModeCode = orderModel.getTransportationMode().getCode();
		final String purch = orderModel.getPurchaseOrderNumber();
		final String userName = orderModel.getUser().getName();
		final String uom = "PC";
		final String cartId = "";
		final String hybrisSalesOrder = "";
		final StringBuilder emailDesc = new StringBuilder();
		final String eol = "<b>";
		String purchAtach = "";
		if (orderModel.getAttachedPO() != null)
		{
			purchAtach = orderModel.getAttachedPO().getRealFileName();
		}


		String commentX = "";

		for (final B2BCommentModel comentario : cartModel.getB2bcomments())
		{
			commentX = comentario.getComment();
		}

		final String commentQuote = commentX;

		final String buildTo = orderModel.getUnit().getUid();
		final String deliveryAddressCode = orderModel.getDeliveryAddress().getCode();

		try
		{
			final Date creationDate = orderModel.getDate();
			//final SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
			final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			final String datetimeStr = format.format(creationDate);
			final Date parseDate = format.parse(datetimeStr);

			final Date requestedDeliveryDate = orderModel.getRequestedDeliveryDate();
			final String datetimeStrReq = format.format(requestedDeliveryDate);
			final Date parseRequestedDeliveryDate = format.parse(datetimeStrReq);


			LOG.info("SAP CONNECTION ORDER CREATOR: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					custDest);

			LOG.info("SAP CONNECTION ORDER CREATOR: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TYPEOP,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);
			LOG.info("typeOp " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);
			emailDesc.append("<b>Type OP:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.STATUS, "P");
			LOG.info("status " + "P");
			emailDesc.append("<b>status:</b> " + "P");

			// input structure
			final JCoStructure structure = sapFunc.getImportParameterList()
					.getStructure(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_STRUCTURE);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALES, salesOrganization);
			LOG.info("salesOrganization " + salesOrganization);
			emailDesc.append("<b>Sales Organization:</b> " + salesOrganization + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.DISTR_CHAN, distributionChannel);
			LOG.info("distributionChannel " + distributionChannel);
			emailDesc.append("<b>Distribution Channel:</b> " + distributionChannel + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_METH_S, poType);
			LOG.info("poMethS " + poType);
			emailDesc.append("<b>Po MethS:</b> " + poType + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS1, incoterm);
			LOG.info("incoterm " + incoterm);
			emailDesc.append("<b>Incoterm:</b> " + incoterm + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS2, incoterm2);
			LOG.info("incoterm2 " + incoterm2);
			emailDesc.append("<b>Incoterm 2:</b> " + incoterm2 + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TRATY, transportationModeCode);
			LOG.info("transportationModeCode " + transportationModeCode);
			emailDesc.append("<b>TransportationMode Code:</b> " + transportationModeCode + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PURCH_NO_C, purch);
			LOG.info("purch " + purch);
			emailDesc.append("<b>Purch:</b> " + purch + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_ATT, purchAtach);
			LOG.info("purchAtach " + purchAtach);
			emailDesc.append("<b>Purch Atach:</b> " + purchAtach + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ERDAT, parseDate);
			LOG.info("parseDate " + parseDate);
			emailDesc.append("<b>Parse Date:</b> " + parseDate + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.BNAME_V, userName);
			LOG.info("userName " + userName);
			emailDesc.append("<b>UserName:</b> " + userName + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
			LOG.info("uom " + uom);
			emailDesc.append("<b>UOM:</b> " + uom + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REF_1_S, cartId);
			LOG.info("cartId " + cartId);
			emailDesc.append("<b>Cart Id:</b> " + cartId + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VBELN_REF, hybrisSalesOrder);
			LOG.info("hybrisSalesOrder " + hybrisSalesOrder);
			emailDesc.append("<b>Hybris Sales Order:</b> " + hybrisSalesOrder + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.COMM_QUOTE, commentQuote);
			LOG.info("commentQuote " + commentQuote);
			emailDesc.append("<b>Comment Quote:</b> " + commentQuote + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE_H, parseRequestedDeliveryDate);
			LOG.info("requestedDeliveryDate " + parseRequestedDeliveryDate);
			emailDesc.append("<b>Requested Delivery Date:</b> " + parseRequestedDeliveryDate + eol);

			final JCoTable inputTable1 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE1);

			int entryIndex = 0;
			// input detail
			for (final AbstractOrderEntryModel entry : orderModel.getEntries())
			{
				LOG.info("Order entry #" + entryIndex);
				final ProlamsaProductModel product = (ProlamsaProductModel) entry.getProduct();

				inputTable1.appendRow();
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MATERIAL,
						SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				LOG.info("product.getBaseCode " + SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				emailDesc.append("<b>Product BaseCode:</b> " + SAPUtils.rellenaZeros(product.getBaseCode(), 18) + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PLANT,
						product.getLocation().getCode().substring(1, 5));
				LOG.info("Location " + product.getLocation().getCode().substring(1, 5));
				emailDesc.append("<b>Location:</b> " + product.getLocation().getCode().substring(1, 5) + eol);

				Double quantity = 0.0d;
				if (!product.getIsAPI())
				{
					quantity = entry.getQuantity() * product.getPiecesPerBundle();
				}
				else
				{
					quantity = entry.getQuantity().doubleValue();
				}

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_QTY, quantity);
				LOG.info("Quantity " + quantity);
				emailDesc.append("<b>Quantity:</b> " + quantity + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
				LOG.info("Unit " + uom);
				emailDesc.append("<b>Unit:</b> " + uom + eol);

				final Date readyToShip = entry.getReadyToShip();
				final SimpleDateFormat formatRS = new SimpleDateFormat("yyyyMMdd");
				final String datetimeStrRS = formatRS.format(readyToShip);
				final Date readyToShipDate = formatRS.parse(datetimeStrRS);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, readyToShipDate);
				LOG.info("REQ_DATE " + readyToShipDate);
				emailDesc.append("<b>Requested Date:</b> " + readyToShipDate + eol);

				if (entry.getRollingScheduleWeek() != null)
				{

					final Date rollingDate = entry.getRollingScheduleWeek();
					final SimpleDateFormat formatRol = new SimpleDateFormat("yyyyMMdd");
					final String datetimeStrRol = formatRol.format(rollingDate);
					final Date parseDateRol = formatRol.parse(datetimeStrRol);

					inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, parseDateRol);
					LOG.info("REQ_DATE/RollingSched: " + parseDateRol);
					emailDesc.append("<b>Parse Date Rol:</b> " + parseDateRol + eol);

				}

				//01042016
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.IHREZ, entry.getStockOuts());
				LOG.info("IHREZ " + entry.getStockOuts());
				emailDesc.append("<b>IHREZ:</b> " + entry.getStockOuts() + eol);

				final BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();

				if (currentBaseStore.getPriceNegotiationEnabled() != null
						&& currentBaseStore.getPriceNegotiationEnabled().booleanValue())
				{
					if (entry.getIsAvailableToNegotiatePrice())
					{
						LOG.info("Entry has negotiable data: ");


						final UnitModel unitModel = sessionService.getAttribute("productUnit");
						String unitCode = unitModel.getSapCode();
						Double negotiablePrice = entry.getNegotiablePrice();
						if (unitModel.getCode().contains("ton"))
						{
							unitCode = "KG";
							negotiablePrice = entry.getNegotiablePrice() / 1000;
						}
						else if (unitModel.getCode().contains("bun"))
						{
							unitCode = "PC";
							negotiablePrice = entry.getNegotiablePrice() / product.getPiecesPerBundle();
						}
						else if (unitModel.getCode().equalsIgnoreCase("prolamsa_lb"))
						{
							unitCode = "KG";
							negotiablePrice = entry.getNegotiablePrice() * 2.20462;
						}

						// unit
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KMEIN, unitCode);
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KMEIN + ": " + unitCode);
						emailDesc.append("<b>Unit:</b> " + unitCode);

						// negotiable price
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.NETPR, negotiablePrice * 100);
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.NETPR + ": " + negotiablePrice * 100);
						emailDesc.append("<b>Negotiable Price:</b> " + negotiablePrice * 100);

						// currency
						final CurrencyModel currencyModel = commonI18NService.getCurrentCurrency();
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.WAERK, currencyModel.getIsocode());
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.WAERK + ": " + currencyModel.getIsocode());
						emailDesc.append("<b>Currency:</b> " + currencyModel.getIsocode());

						// base
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KPEIN, new Integer(100));
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KPEIN + ": " + new Integer(100));
						emailDesc.append("<b>Base:</b> " + new Integer(100));


					}
					else
					{
						LOG.info("Entry hasn't negotiable data.");
					}
				}

				if (orderModel.getStore().getUid().equalsIgnoreCase("6000"))
				{
					if (entry.getNegotiablePrice() != null && entry.getNegotiablePrice() != 0)
					{
						LOG.info("Entry" + entry.getEntryNumber() + " has negotiable amount for Axis");
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PR_NEG, (entry.getNegotiablePrice()));
						LOG.info("Neg. Price " + entry.getNegotiablePrice());
						emailDesc.append("<b>Neg. Price:</b> " + entry.getNegotiablePrice() + eol);
					}
				}

				entryIndex++;
			}

			final JCoTable inputTable3 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE3);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SOLDTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB, SAPUtils.rellenaZeros(buildTo, 10));
			LOG.info("SOLDTO " + SAPUtils.rellenaZeros(buildTo, 10));
			emailDesc.append("<b>Sold To:</b> " + SAPUtils.rellenaZeros(buildTo, 10) + eol);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SHIPTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB,
					SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			LOG.info("SHIPTO " + SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			emailDesc.append("<b>Ship To:</b> " + SAPUtils.rellenaZeros(deliveryAddressCode, 10) + eol);


			LOG.info("INICIA 4");
			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			final JCoTable resultTable = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.OUTPUT_TABLE);

			String mensaje = "";

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);
				mensaje = mensaje + resultTable.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MESSAGE) + eol;
			}

			LOG.info("MESSAGE: " + mensaje);
			emailDesc.append("<b>SAP Message:</b> " + mensaje + eol);

			final String sapOrderID = sapFunc.getExportParameterList()
					.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALESDOCUMENT);

			if (sapOrderID == "" || sapOrderID == null)
			{
				throw new Exception("SAP Registry Failed");
			}
			//GET SAP WEIGHT UNIT AND SET
			setSapWeightUnit(orderModel, sapFunc);
			orderModel.setCode(sapOrderID);
			orderModel.setSapOrderId(sapOrderID);
			//orderModel.setOriginalUserOrder(orderModel.getUser().getUid());
		}
		catch (final Exception ex)
		{
			// clean comments from cartModel after any exception
			final Collection<B2BCommentModel> b2bcomments = new ArrayList<B2BCommentModel>();
			cartModel.setB2bcomments(b2bcomments);
			modelService.save(cartModel);

			//LOG.error("error while posting order to SAP", ex);
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(baseStoreService.getCurrentBaseStore().getName() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CREATE_ORDER.SUBJECT + " ");
			subjectData.append(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + " | ");
			subjectData.append(b2bCustomerService.getCurrentB2BCustomer().getName() + " | ");

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//set the body
			bodyData.append("<b>Store name:</b> " + baseStoreService.getCurrentBaseStore().getName() + eol + eol);
			bodyData.append("<b>User:</b> " + b2bCustomerService.getCurrentB2BCustomer().getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Order/Quote Creation" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());

			throw new Exception("SAP Registry Failed");
		}
	}

	@Override
	public void createSapQuoteFromOrder(final OrderModel orderModel) throws Exception
	{
		//getting user from order
		//final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		final UserModel currentUser = orderModel.getUser();

		final BaseStoreModel baseStoreModel = orderModel.getStore();

		//Structure
		final String salesOrganization = orderModel.getStore().getUid();
		final String distributionChannel = configurationService.getConfiguration().getString("sap.parameter.distribution-channel");
		final String poType = "HYSO";
		final String incoterm = orderModel.getTransportationMode().getIncotermCode();
		final String incoterm2 = "";
		final String transportationModeCode = orderModel.getTransportationMode().getCode();
		final String purch = orderModel.getPurchaseOrderNumber();
		final String userName = orderModel.getUser().getName();
		final String uom = "PC";
		final String cartId = "";
		final String hybrisSalesOrder = "";
		final StringBuilder emailDesc = new StringBuilder();
		final String eol = "<b>";
		String purchAtach = "";
		if (orderModel.getAttachedPO() != null)
		{
			purchAtach = orderModel.getAttachedPO().getRealFileName();
		}


		String commentX = "";

		for (final B2BCommentModel comentario : orderModel.getB2bcomments())
		{
			commentX = comentario.getComment();
		}

		final String commentQuote = commentX;

		final String buildTo = orderModel.getUnit().getUid();
		final String deliveryAddressCode = orderModel.getDeliveryAddress().getCode();

		try
		{
			final Date creationDate = orderModel.getDate();
			//final SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
			final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			final String datetimeStr = format.format(creationDate);
			final Date parseDate = format.parse(datetimeStr);

			final Date requestedDeliveryDate = orderModel.getRequestedDeliveryDate();
			final String datetimeStrReq = format.format(requestedDeliveryDate);
			final Date parseRequestedDeliveryDate = format.parse(datetimeStrReq);


			LOG.info("SAP CONNECTION ORDER CREATOR: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreModel.getUid().equals("1000") || baseStoreModel.getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					custDest);

			LOG.info("SAP CONNECTION ORDER CREATOR: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TYPEOP,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);
			LOG.info("typeOp " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);
			emailDesc.append("<b>Type OP:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.QUOTE);

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.STATUS, "P");
			LOG.info("status " + "P");
			emailDesc.append("<b>status:</b> " + "P");

			// input structure
			final JCoStructure structure = sapFunc.getImportParameterList()
					.getStructure(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_STRUCTURE);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALES, salesOrganization);
			LOG.info("salesOrganization " + salesOrganization);
			emailDesc.append("<b>Sales Organization:</b> " + salesOrganization + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.DISTR_CHAN, distributionChannel);
			LOG.info("distributionChannel " + distributionChannel);
			emailDesc.append("<b>Distribution Channel:</b> " + distributionChannel + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_METH_S, poType);
			LOG.info("poMethS " + poType);
			emailDesc.append("<b>Po MethS:</b> " + poType + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS1, incoterm);
			LOG.info("incoterm " + incoterm);
			emailDesc.append("<b>Incoterm:</b> " + incoterm + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INCOTERMS2, incoterm2);
			LOG.info("incoterm2 " + incoterm2);
			emailDesc.append("<b>Incoterm 2:</b> " + incoterm2 + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TRATY, transportationModeCode);
			LOG.info("transportationModeCode " + transportationModeCode);
			emailDesc.append("<b>TransportationMode Code:</b> " + transportationModeCode + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PURCH_NO_C, purch);
			LOG.info("purch " + purch);
			emailDesc.append("<b>Purch:</b> " + purch + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PO_ATT, purchAtach);
			LOG.info("purchAtach " + purchAtach);
			emailDesc.append("<b>Purch Atach:</b> " + purchAtach + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ERDAT, parseDate);
			LOG.info("parseDate " + parseDate);
			emailDesc.append("<b>Parse Date:</b> " + parseDate + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.BNAME_V, userName);
			LOG.info("userName " + userName);
			emailDesc.append("<b>UserName:</b> " + userName + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
			LOG.info("uom " + uom);
			emailDesc.append("<b>UOM:</b> " + uom + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REF_1_S, cartId);
			LOG.info("cartId " + cartId);
			emailDesc.append("<b>Cart Id:</b> " + cartId + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VBELN_REF, hybrisSalesOrder);
			LOG.info("hybrisSalesOrder " + hybrisSalesOrder);
			emailDesc.append("<b>Hybris Sales Order:</b> " + hybrisSalesOrder + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.COMM_QUOTE, commentQuote);
			LOG.info("commentQuote " + commentQuote);
			emailDesc.append("<b>Comment Quote:</b> " + commentQuote + eol);

			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE_H, parseRequestedDeliveryDate);
			LOG.info("requestedDeliveryDate " + parseRequestedDeliveryDate);
			emailDesc.append("<b>Requested Delivery Date:</b> " + parseRequestedDeliveryDate + eol);

			final JCoTable inputTable1 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE1);

			int entryIndex = 0;
			// input detail
			for (final AbstractOrderEntryModel entry : orderModel.getEntries())
			{
				LOG.info("Order entry #" + entryIndex);
				final ProlamsaProductModel product = (ProlamsaProductModel) entry.getProduct();

				inputTable1.appendRow();
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MATERIAL,
						SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				LOG.info("product.getBaseCode " + SAPUtils.rellenaZeros(product.getBaseCode(), 18));
				emailDesc.append("<b>Product BaseCode:</b> " + SAPUtils.rellenaZeros(product.getBaseCode(), 18) + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PLANT,
						product.getLocation().getCode().substring(1, 5));
				LOG.info("Location " + product.getLocation().getCode().substring(1, 5));
				emailDesc.append("<b>Location:</b> " + product.getLocation().getCode().substring(1, 5) + eol);

				Double quantity = 0.0d;
				if (!product.getIsAPI())
				{
					quantity = entry.getQuantity() * product.getPiecesPerBundle();
				}
				else
				{
					quantity = entry.getQuantity().doubleValue();
				}

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_QTY, quantity);
				LOG.info("Quantity " + quantity);
				emailDesc.append("<b>Quantity:</b> " + quantity + eol);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VRKME, uom);
				LOG.info("Unit " + uom);
				emailDesc.append("<b>Unit:</b> " + uom + eol);

				final Date readyToShip = entry.getReadyToShip();
				final SimpleDateFormat formatRS = new SimpleDateFormat("yyyyMMdd");
				final String datetimeStrRS = formatRS.format(readyToShip);
				final Date readyToShipDate = formatRS.parse(datetimeStrRS);

				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, readyToShipDate);
				LOG.info("REQ_DATE " + readyToShipDate);
				emailDesc.append("<b>Requested Date:</b> " + readyToShipDate + eol);

				if (entry.getRollingScheduleWeek() != null)
				{

					final Date rollingDate = entry.getRollingScheduleWeek();
					final SimpleDateFormat formatRol = new SimpleDateFormat("yyyyMMdd");
					final String datetimeStrRol = formatRol.format(rollingDate);
					final Date parseDateRol = formatRol.parse(datetimeStrRol);

					inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.REQ_DATE, parseDateRol);
					LOG.info("REQ_DATE/RollingSched: " + parseDateRol);
					emailDesc.append("<b>Parse Date Rol:</b> " + parseDateRol + eol);

				}

				//01042016
				inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.IHREZ, entry.getStockOuts());
				LOG.info("IHREZ " + entry.getStockOuts());
				emailDesc.append("<b>IHREZ:</b> " + entry.getStockOuts() + eol);


				if (baseStoreModel.getPriceNegotiationEnabled() != null && baseStoreModel.getPriceNegotiationEnabled().booleanValue())
				{
					if (entry.getIsAvailableToNegotiatePrice())
					{
						LOG.info("Entry has negotiable data: ");


						final UnitModel unitModel = sessionService.getAttribute("productUnit");
						String unitCode = unitModel.getSapCode();
						Double negotiablePrice = entry.getNegotiablePrice();
						if (unitModel.getCode().contains("ton"))
						{
							unitCode = "KG";
							negotiablePrice = entry.getNegotiablePrice() / 1000;
						}
						else if (unitModel.getCode().contains("bun"))
						{
							unitCode = "PC";
							negotiablePrice = entry.getNegotiablePrice() / product.getPiecesPerBundle();
						}
						else if (unitModel.getCode().equalsIgnoreCase("prolamsa_lb"))
						{
							unitCode = "KG";
							negotiablePrice = entry.getNegotiablePrice() * 2.20462;
						}

						// unit
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KMEIN, unitCode);
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KMEIN + ": " + unitCode);
						emailDesc.append("<b>Unit:</b> " + unitCode);

						// negotiable price
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.NETPR, negotiablePrice * 100);
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.NETPR + ": " + negotiablePrice * 100);
						emailDesc.append("<b>Negotiable Price:</b> " + negotiablePrice * 100);

						// currency
						final CurrencyModel currencyModel = commonI18NService.getCurrentCurrency();
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.WAERK, currencyModel.getIsocode());
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.WAERK + ": " + currencyModel.getIsocode());
						emailDesc.append("<b>Currency:</b> " + currencyModel.getIsocode());

						// base
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KPEIN, new Integer(100));
						LOG.info(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.KPEIN + ": " + new Integer(100));
						emailDesc.append("<b>Base:</b> " + new Integer(100));


					}
					else
					{
						LOG.info("Entry hasn't negotiable data.");
					}
				}

				if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
				{
					if (entry.getNegotiablePrice() != null && entry.getNegotiablePrice() != 0)
					{
						LOG.info("Entry" + entry.getEntryNumber() + " has negotiable amount for Axis");
						inputTable1.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PR_NEG, (entry.getNegotiablePrice()));
						LOG.info("Neg. Price " + entry.getNegotiablePrice());
						emailDesc.append("<b>Neg. Price:</b> " + entry.getNegotiablePrice() + eol);
					}
				}

				entryIndex++;
			}

			final JCoTable inputTable3 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE3);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SOLDTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB, SAPUtils.rellenaZeros(buildTo, 10));
			LOG.info("SOLDTO " + SAPUtils.rellenaZeros(buildTo, 10));
			emailDesc.append("<b>Sold To:</b> " + SAPUtils.rellenaZeros(buildTo, 10) + eol);

			inputTable3.appendRow();
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_ROLE, "SHIPTO");
			inputTable3.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.PARTN_NUMB,
					SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			LOG.info("SHIPTO " + SAPUtils.rellenaZeros(deliveryAddressCode, 10));
			emailDesc.append("<b>Ship To:</b> " + SAPUtils.rellenaZeros(deliveryAddressCode, 10) + eol);


			LOG.info("INICIA 4");
			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			final JCoTable resultTable = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.OUTPUT_TABLE);

			String mensaje = "";

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);
				mensaje = mensaje + resultTable.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MESSAGE) + eol;
			}

			LOG.info("MESSAGE: " + mensaje);
			emailDesc.append("<b>SAP Message:</b> " + mensaje + eol);

			final String sapOrderID = sapFunc.getExportParameterList()
					.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALESDOCUMENT);

			if (sapOrderID == "" || sapOrderID == null)
			{
				throw new Exception("SAP Registry Failed");
			}

			setSapWeightUnit(orderModel, sapFunc);
			orderModel.setCode(sapOrderID);
			orderModel.setSapOrderId(sapOrderID);

			// update order status after SAP ID has been set
			orderModel.setStatus(OrderStatus.PENDING_QUOTE);

			modelService.save(orderModel);
		}
		catch (final Exception ex)
		{

			//LOG.error("error while posting order to SAP", ex);
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(baseStoreModel.getName() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CREATE_ORDER.SUBJECT + " ");
			subjectData.append(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + " | ");
			subjectData.append(currentUser.getName() + " | ");

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//set the body
			bodyData.append("<b>Store name:</b> " + baseStoreModel.getName() + eol + eol);
			bodyData.append("<b>User:</b> " + currentUser.getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Order/Quote Creation" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());

			throw new Exception("SAP Registry Failed");
		}
	}

	@Override
	public void createSapOrderByQuote(final OrderModel orderModel, final OrderModel quote) throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		String hybrisQuote = "";
		final StringBuilder emailDesc = new StringBuilder();
		final String shippingInstructions = orderModel.getShippingInstructions();
		final String eol = "<b>";


		if (quote.getCode() != "")
		{
			hybrisQuote = quote.getCode();
		}
		else
		{
			throw new Exception("text.quote.number.error");
		}

		try
		{
			LOG.info("SAP CONNECTION ORDER CREATOR: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					custDest);

			LOG.info("SAP CONNECTION ORDER CREATOR: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TYPEOP,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ORDER);
			LOG.info("typeOp " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ORDER);
			emailDesc.append("<b>Type OP:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.ORDER);

			// input structure
			final JCoStructure structure = sapFunc.getImportParameterList()
					.getStructure(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_STRUCTURE);

			// quote code
			structure.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.VBELN_REF, hybrisQuote);
			LOG.info("hybrisSalesOrder VBELN_REF " + hybrisQuote);
			emailDesc.append("<b>Hybris Sales Order VBELN_REF:</b> " + hybrisQuote + eol);

			// shipping instructions
			final JCoTable inputTable4 = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.INPUT_TABLE4);

			final String[] shipInstSplit = SAPUtils.splitByNumber(shippingInstructions,
					SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SHIP_INST_MAX_CHAR_PER_LINE);

			if (shipInstSplit != null)
			{
				for (int i = 0; i < shipInstSplit.length; i++)
				{
					inputTable4.appendRow();
					inputTable4.setValue(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.TDLINE, shipInstSplit[i]);
				}

				LOG.info("shippingInstructions " + shippingInstructions);
				emailDesc.append("<b>Shipping Instructions:</b> " + shippingInstructions + eol);
			}

			LOG.info("INICIA 5");

			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), true, sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION,
					currentUser.getUid(), false, sessionActual));

			final JCoTable resultTable = sapFunc.getTableParameterList()
					.getTable(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.OUTPUT_TABLE);

			String mensaje = "";

			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);
				mensaje = mensaje + resultTable.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.MESSAGE) + eol;
			}


			LOG.info("MESSAGE: " + mensaje);
			emailDesc.append("<b>SAP Message:</b> " + mensaje + eol);

			final String sapOrderID = sapFunc.getExportParameterList()
					.getString(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.SALESDOCUMENT);

			if (sapOrderID == "" || sapOrderID == null)
			{
				//sendEmail
				throw new Exception("text.quote.sap.error");
			}

			orderModel.setCode(sapOrderID);
			orderModel.setSapOrderId(sapOrderID);
			//orderModel.setOriginalUserOrder(orderModel.getUser().getUid());
		}
		catch (final Exception ex)
		{
			//LOG.error("error while posting order to SAP", ex);

			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(baseStoreService.getCurrentBaseStore().getName() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CREATE_ORDER.SUBJECT + " ");
			subjectData.append(SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + " | ");
			subjectData.append(b2bCustomerService.getCurrentB2BCustomer().getName() + " | ");

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//set the body
			bodyData.append("<b>Store name:</b> " + baseStoreService.getCurrentBaseStore().getName() + eol + eol);
			bodyData.append("<b>User:</b> " + b2bCustomerService.getCurrentB2BCustomer().getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.CREATE_ORDER_QUOTE_HYBRIS_SAP.FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Order Creation from Quote" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());
			throw new Exception("text.quote.sap.error");
		}

	}

	private boolean isPendingOrder(final OrderModel orderModel)
	{
		if (orderModel.getStore().getUid().equals("1000"))
		{
			Double MX_ORDER_LIMIT = 0.0d;
			if (orderModel.getStore().getMaxOrderLimit() != null)
			{
				MX_ORDER_LIMIT = orderModel.getStore().getMaxOrderLimit().doubleValue();
			}
			else
			{
				MX_ORDER_LIMIT = 15000000.0;
			}
			if (orderModel.getTotalPrice().doubleValue() > MX_ORDER_LIMIT)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (orderModel.getStore().getUid().equals("2000"))
		{
			Double USA_ORDER_LIMIT = 0.0d;
			if (orderModel.getStore().getMaxOrderLimit() != null)
			{
				USA_ORDER_LIMIT = orderModel.getStore().getMaxOrderLimit().doubleValue();
			}
			else
			{
				USA_ORDER_LIMIT = 500000.0;
			}
			if (orderModel.getTotalPrice().doubleValue() > USA_ORDER_LIMIT)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (orderModel.getStore().getUid().equals("5000"))
		{
			Double A4C_ORDER_LIMIT = 0.0d;
			if (orderModel.getStore().getMaxOrderLimit() != null)
			{
				A4C_ORDER_LIMIT = orderModel.getStore().getMaxOrderLimit().doubleValue();
			}
			else
			{
				A4C_ORDER_LIMIT = 5000000.0;
			}
			if (orderModel.getTotalPrice().doubleValue() > A4C_ORDER_LIMIT)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (orderModel.getStore().getUid().equals("6000"))
		{
			Double AXIS_ORDER_LIMIT = 0.0d;
			if (orderModel.getStore().getMaxOrderLimit() != null)
			{
				AXIS_ORDER_LIMIT = orderModel.getStore().getMaxOrderLimit().doubleValue();
			}
			else
			{
				AXIS_ORDER_LIMIT = 5000000.0;
			}
			if (orderModel.getTotalPrice().doubleValue() > AXIS_ORDER_LIMIT)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		return false;
	}
}
