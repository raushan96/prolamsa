/**
 * 
 */
package mx.neoris.core.services.sharepoint.soap.impl;

import de.hybris.platform.b2b.model.B2BCommentModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointConstants;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointListWebServiceManager;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointQuoteNegotiationWebService;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.microsoft.schemas.sharepoint.soap.UpdateListItems.Updates;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsResponse.UpdateListItemsResult;


/**
 * @author hector.castaneda
 * 
 */
public class DefaultNeorisSharePointQuoteNegotiationWebService implements NeorisSharePointQuoteNegotiationWebService
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisSharePointQuoteNegotiationWebService.class);

	private static final String PROP_QUOTE_NEGOTIATION_LIST_NAME_KEY = "prolamsa.sharepoint.quotesNegotiation.list.name";
	private static final String PROP_QUOTE_NEGOTIATION_CAML_KEY = "prolamsa.sharepoint.quotesNegotiation.newQuoteNegotiationCAML";
	private static final String PROP_QUOTE_NEGOTIATION_MATERIAL_ENTRY_CAML_KEY = "prolamsa.sharepoint.quotesNegotiation.newQuoteNegotiationMaterialEntryCAML";
	private static final String PROP_QUOTE_NEGOTIATION_CART_SUMMARY_CAML_KEY = "prolamsa.sharepoint.quotesNegotiation.newQuoteNegotiationCartSummaryCAML";
	private static final String PROP_QUOTE_NEGOTIATION_CART_SUMMARY_API_CAML_KEY = "prolamsa.sharepoint.quotesNegotiation.newQuoteNegotiationCartSummaryAPICAML";
	private static final String PROP_QUOTE_NEGOTIATION_ORDER_SUMMARY_ENTRY_CAML_KEY = "prolamsa.sharepoint.quotesNegotiation.newQuoteNegotiationOrderSummaryEntryCAML";
	private static final String PROP_QUOTE_NEGOTIATION_ORDER_ORDER_TOTALS_CAML_KEY = "prolamsa.sharepoint.quotesNegotiation.newQuoteNegotiationOrderTotalsEntryCAML";

	private static final String PROP_LISTS_WSDL_KEY = "prolamsa.sharepoint.quotesNegotiation.lists.wsdl";
	private static final String PROP_LISTS_ENDPOINT_KEY = "prolamsa.sharepoint.quotesNegotiation.lists.endpoint";

	private String newQuoteNegotiationCAML;
	private String newQuoteNegotiationMaterialEntryCAML;
	private String newQuoteNegotiationCartSummaryCAML;
	private String newQuoteNegotiationCartSummaryAPICAML;
	private String newQuoteNegotiationOrderSummaryEntryCAML;
	private String newQuoteNegotiationOrderTotalsEntryCAML;

	@Resource(name = "configurationService")
	public ConfigurationService configurationService;

	@Resource(name = "neorisSharePointListWebServiceManager")
	public NeorisSharePointListWebServiceManager neorisSharePointListWebServiceManager;

	public void init() throws Exception
	{
		newQuoteNegotiationCAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this.getClass()
				.getClassLoader().getResource(configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_CAML_KEY))
				.toURI())));

		newQuoteNegotiationMaterialEntryCAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this.getClass()
				.getClassLoader()

				.getResource(configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_MATERIAL_ENTRY_CAML_KEY))
				.toURI())));

		newQuoteNegotiationCartSummaryCAML = new String(
				neorisSharePointListWebServiceManager.readAll(new File(this.getClass().getClassLoader()
						.getResource(configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_CART_SUMMARY_CAML_KEY))
						.toURI())));

		newQuoteNegotiationCartSummaryAPICAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this.getClass()
				.getClassLoader()
				.getResource(configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_CART_SUMMARY_API_CAML_KEY))
				.toURI())));

		newQuoteNegotiationOrderSummaryEntryCAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this
				.getClass().getClassLoader()
				.getResource(configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_ORDER_SUMMARY_ENTRY_CAML_KEY))
				.toURI())));

		newQuoteNegotiationOrderTotalsEntryCAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this.getClass()
				.getClassLoader()
				.getResource(configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_ORDER_ORDER_TOTALS_CAML_KEY))
				.toURI())));
	}

	@Override
	public String placeQuoteNegotiationToSharePoint(final OrderModel orderModel) throws Exception
	{
		final String negotiationXMLString = createXMLStringForNegotiation(orderModel);

		if (negotiationXMLString == null)
		{
			throw new Exception("Error while publishing quote negotiation in Sharepoint");
		}

		final Node newItemNode = neorisSharePointListWebServiceManager.createSharePointCAMLNode(negotiationXMLString);

		final Updates updates = new Updates();
		updates.getContent().add(newItemNode);

		final UpdateListItemsResult result = neorisSharePointListWebServiceManager.getListWebService(
				configurationService.getConfiguration().getString(PROP_LISTS_WSDL_KEY),
				configurationService.getConfiguration().getString(PROP_LISTS_ENDPOINT_KEY)).updateListItems(
				configurationService.getConfiguration().getString(PROP_QUOTE_NEGOTIATION_LIST_NAME_KEY), updates);

		final Element element = (Element) result.getContent().get(0);
		final NodeList nl = element.getElementsByTagName("z:row");

		String negotiationId = null;

		// getting element id
		for (int i = 0; i < nl.getLength(); i++)
		{
			final Node node = nl.item(i);
			negotiationId = node.getAttributes().getNamedItem("ows_ID").getNodeValue();
		}

		// getting error
		if (negotiationId == null)
		{
			final NodeList errorCodeNodeList = element.getElementsByTagName("ErrorCode");
			final NodeList errorTextNodeList = element.getElementsByTagName("ErrorText");

			String errorCode = "";
			String errorMessage = "";

			if (errorCodeNodeList.getLength() > 0)
			{
				final Node errorCodeNode = errorCodeNodeList.item(0);
				if (errorCodeNode.getFirstChild() != null)
				{
					errorCode = "Error code:" + errorCodeNode.getFirstChild().getNodeValue();
					LOG.error(errorCode);
				}
			}

			if (errorTextNodeList.getLength() > 0)
			{
				final Node errorTextNode = errorTextNodeList.item(0);
				if (errorTextNode.getFirstChild() != null)
				{
					errorMessage = "Error message:" + errorTextNode.getFirstChild().getNodeValue();
					LOG.error(errorMessage);
				}
			}

			throw new Exception("Error while publishing quote negotiation: " + orderModel.getCode() + " to Sharepoint due to: "
					+ errorCode + " " + errorMessage);
		}

		return negotiationId;
	}

	protected String createXMLStringForNegotiation(final OrderModel orderModel)
	{
		String negotiationXMLString = null;

		final String purchaseOrderId = orderModel.getPurchaseOrderNumber();
		final String quoteNumber = orderModel.getCode();
		final String placementDate = new SimpleDateFormat(NeorisSharePointConstants.DATE_FORMAT).format(orderModel.getDate());
		final String placedBy = StringEscapeUtils.escapeXml(orderModel.getUser().getName());
		final String contact = StringEscapeUtils.escapeXml(orderModel.getUser().getUid());
		final String unitOfMeasure = orderModel.getUnitWhenPlaced().getName();

		// comments
		final StringBuilder commentBuilder = new StringBuilder();
		final Iterator<B2BCommentModel> itr = orderModel.getB2bcomments().iterator();
		while (itr.hasNext())
		{
			final B2BCommentModel eachComment = itr.next();
			commentBuilder.append(StringEscapeUtils.escapeXml(eachComment.getComment()));

			if (itr.hasNext())
			{
				commentBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}
		}
		final String comment = StringEscapeUtils.escapeXml(commentBuilder.toString());

		// billing address
		final StringBuilder billingAddressBuilder = new StringBuilder();
		final AddressModel billingAddressModel = orderModel.getBillingAddress();
		if (billingAddressModel != null)
		{
			if (StringUtils.isNotEmpty(billingAddressModel.getShortName()))
			{
				billingAddressBuilder.append(billingAddressModel.getShortName() + " ");
			}

			if (StringUtils.isNotEmpty(billingAddressModel.getCode()))
			{
				billingAddressBuilder.append("(" + billingAddressModel.getCode() + ")");
			}

			billingAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);

			if (StringUtils.isNotEmpty(billingAddressModel.getName()))
			{
				billingAddressBuilder.append(billingAddressModel.getName());
				billingAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}

			if (StringUtils.isNotEmpty(billingAddressModel.getLine1()))
			{
				billingAddressBuilder.append(billingAddressModel.getLine1());
				billingAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}

			if (StringUtils.isNotEmpty(billingAddressModel.getLine2()))
			{
				billingAddressBuilder.append(billingAddressModel.getLine2());
				billingAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}

			if (StringUtils.isNotEmpty(billingAddressModel.getTown()))
			{
				billingAddressBuilder.append(billingAddressModel.getTown() + ", ");
			}

			if (billingAddressModel.getRegion() != null && StringUtils.isNotEmpty(billingAddressModel.getRegion().getName()))
			{
				billingAddressBuilder.append(billingAddressModel.getRegion().getName() + ", ");
			}

			if (StringUtils.isNotEmpty(billingAddressModel.getPostalcode()))
			{
				billingAddressBuilder.append(billingAddressModel.getPostalcode());
			}

			if (billingAddressModel.getCountry() != null && StringUtils.isNotEmpty(billingAddressModel.getCountry().getName()))
			{
				billingAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
				billingAddressBuilder.append(billingAddressModel.getCountry().getName());
			}
		}
		final String billingAddress = StringEscapeUtils.escapeXml(billingAddressBuilder.toString());

		// delivery address
		final StringBuilder deliveryAddressBuilder = new StringBuilder();
		final AddressModel deliveryAddressModel = orderModel.getDeliveryAddress();
		if (deliveryAddressModel != null)
		{
			if (StringUtils.isNotEmpty(deliveryAddressModel.getShortName()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getShortName() + " ");
			}

			if (StringUtils.isNotEmpty(deliveryAddressModel.getCode()))
			{
				deliveryAddressBuilder.append("(" + deliveryAddressModel.getCode() + ")");
			}

			deliveryAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);

			if (StringUtils.isNotEmpty(deliveryAddressModel.getName()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getName());
				deliveryAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}

			if (StringUtils.isNotEmpty(deliveryAddressModel.getLine1()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getLine1());
				deliveryAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}

			if (StringUtils.isNotEmpty(deliveryAddressModel.getLine2()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getLine2());
				deliveryAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
			}

			if (StringUtils.isNotEmpty(deliveryAddressModel.getTown()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getTown() + ", ");
			}

			if (deliveryAddressModel.getRegion() != null && StringUtils.isNotEmpty(deliveryAddressModel.getRegion().getName()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getRegion().getName() + ", ");
			}

			if (StringUtils.isNotEmpty(deliveryAddressModel.getPostalcode()))
			{
				deliveryAddressBuilder.append(deliveryAddressModel.getPostalcode());
			}

			if (deliveryAddressModel.getCountry() != null && StringUtils.isNotEmpty(deliveryAddressModel.getCountry().getName()))
			{
				deliveryAddressBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);
				deliveryAddressBuilder.append(deliveryAddressModel.getCountry().getName());
			}
		}
		final String deliveryAddress = StringEscapeUtils.escapeXml(deliveryAddressBuilder.toString());

		// transportation mode
		final StringBuilder transportationModeBuilder = new StringBuilder();
		if (orderModel.getTransportationMode() != null)
		{
			if (StringUtils.isNotEmpty(orderModel.getTransportationMode().getName()))
			{
				transportationModeBuilder.append(orderModel.getTransportationMode().getName() + " ");
			}

			if (orderModel.getTransportationMode().getMaxCapacity() != null)
			{
				transportationModeBuilder.append("(" + orderModel.getTransportationMode().getMaxCapacity().doubleValue() + " Tons)");
			}

			transportationModeBuilder.append(NeorisSharePointConstants.LINE_SEPARATOR);

			if (StringUtils.isNotEmpty(orderModel.getTransportationMode().getIncotermCode()))
			{
				transportationModeBuilder.append(orderModel.getTransportationMode().getIncotermCode() + " - ");
			}

			if (StringUtils.isNotEmpty(orderModel.getTransportationMode().getIncotermDescription()))
			{
				transportationModeBuilder.append(orderModel.getTransportationMode().getIncotermDescription());
			}
		}
		final String transportationMode = StringEscapeUtils.escapeXml(transportationModeBuilder.toString());

		// request delivery
		final String requestDeliveryDate = new SimpleDateFormat(NeorisSharePointConstants.DATE_FORMAT).format(orderModel
				.getRequestedDeliveryDate());

		final String materialDetails = createXMLStringForMaterialDetails(orderModel);
		final String cartSummary = createXMLStringForCartSummary(orderModel);
		final String orderSummary = createXMLStringForOrderSummary(orderModel);
		final String orderTotals = createXMLStringForOrderTotals(orderModel);

		final String clientName = StringEscapeUtils.escapeXml(orderModel.getUnit().getLocName());
		final String clientNumber = orderModel.getUnit().getUid();

		negotiationXMLString = String.format(newQuoteNegotiationCAML, purchaseOrderId, quoteNumber, placementDate, placedBy,
				contact, unitOfMeasure, comment, billingAddress, deliveryAddress, transportationMode, requestDeliveryDate,
				materialDetails, cartSummary, orderSummary, orderTotals, clientName, clientNumber);

		return negotiationXMLString;
	}

	protected String createXMLStringForMaterialDetails(final OrderModel orderModel)
	{
		final DecimalFormat decimalFormat = new DecimalFormat(NeorisSharePointConstants.DECIMAL_FORMAT);
		final DecimalFormat decimalFormatNoDecimals = new DecimalFormat(NeorisSharePointConstants.DECIMAL_FORMAT_FT_LB_PC_KS);

		final SimpleDateFormat dateFormat = new SimpleDateFormat(NeorisSharePointConstants.DATE_FORMAT_MMDDYYYY);

		final StringBuilder materials = new StringBuilder();

		for (final AbstractOrderEntryModel entryModel : orderModel.getEntries())
		{
			final ProlamsaProductModel product = (ProlamsaProductModel) entryModel.getProduct();

			// material number
			final String materialNumber = product.getBaseCode();
			// product description
			final String productDescription = StringEscapeUtils.escapeXml(product.getName());

			// pieces per bundle
			String piecesPerBundle = "-";
			if (orderModel.getIsAPIOrder() == null || !orderModel.getIsAPIOrder())
			{
				piecesPerBundle = decimalFormat.format(product.getPiecesPerBundle().doubleValue());
			}

			// quantity
			final String quantity = decimalFormatNoDecimals.format(entryModel.getConvertedQuantity().doubleValue());

			// weight
			double weightDouble = 0.0d;
			if (orderModel.getIsAPIOrder() != null && orderModel.getIsAPIOrder())
			{
				weightDouble = entryModel.getQuantity() * product.getFtLbEquiv();
			}
			else
			{
				weightDouble = entryModel.getQuantity() * product.getBundleLbEquiv();
			}
			final String weight = decimalFormatNoDecimals.format(weightDouble) + " Lb";

			// location
			String location = "";
			final String locationCode = product.getLocation().getCode();

			if ("_6100".equals(locationCode))
			{
				location = NeorisSharePointConstants.QuoteNegotiationIntegration.Plant._6100;
			}

			// price per feet
			final String pricePerFeer = "$ " + decimalFormat.format(entryModel.getPricePerFeet());

			// negotiable price
			double negotiableAmount = 0d;

			if (entryModel.getNegotiablePrice() != null)
			{
				negotiableAmount = entryModel.getNegotiablePrice().doubleValue();
			}

			final double negPriceDouble = entryModel.getNetPriceWOTaxes().doubleValue() + negotiableAmount;
			final String negPrice = "$ " + decimalFormat.format(negPriceDouble);

			// net price
			final String netPrice = "$ " + decimalFormat.format(entryModel.getNetPriceWOTaxes().doubleValue());

			// ready to ship
			final String readyToShip = dateFormat.format(entryModel.getReadyToShip());

			final String eachLineXMLString = String.format(newQuoteNegotiationMaterialEntryCAML, materialNumber, productDescription,
					piecesPerBundle, quantity, weight, location, pricePerFeer, negPrice, netPrice, readyToShip);

			materials.append(eachLineXMLString);
		}

		return materials.toString();
	}

	protected String createXMLStringForCartSummary(final OrderModel orderModel)
	{
		final DecimalFormat decimalFormatNoDecimals = new DecimalFormat(NeorisSharePointConstants.DECIMAL_FORMAT_FT_LB_PC_KS);
		final DecimalFormat decimalFormatTon = new DecimalFormat(NeorisSharePointConstants.DECIMAL_FORMAT_TON);

		double totalFtOrKgs = 0.0d;
		double totalTonOrPcs = 0.0d;
		double stockFtOrKgs = 0.0d;
		double stockTonOrPcs = 0.0d;
		double rollingFtOrKgs = 0.0d;
		double rollingTonOrPcs = 0.0d;

		for (final AbstractOrderEntryModel entryModel : orderModel.getEntries())
		{
			final ProlamsaProductModel product = (ProlamsaProductModel) entryModel.getProduct();

			// API order: Ft and Ton
			if (orderModel.getIsAPIOrder() != null && orderModel.getIsAPIOrder())
			{
				if (entryModel.getRollingScheduleWeek() == null)
				{
					stockFtOrKgs += entryModel.getQuantity().doubleValue();
					stockTonOrPcs += entryModel.getQuantity() * product.getFtTnEquiv();
				}
				else
				{
					rollingFtOrKgs += entryModel.getQuantity().doubleValue();
					rollingTonOrPcs += entryModel.getQuantity() * product.getFtTnEquiv();
				}
			}
			else
			// NO API order: Kgs and Pcs
			{
				if (entryModel.getRollingScheduleWeek() == null)
				{
					stockFtOrKgs += entryModel.getQuantity() * product.getBundleKgEquiv();
					stockTonOrPcs += entryModel.getQuantity() * product.getPiecesPerBundle();
				}
				else
				{
					rollingFtOrKgs += entryModel.getQuantity() * product.getBundleKgEquiv();
					rollingTonOrPcs += entryModel.getQuantity() * product.getPiecesPerBundle();
				}
			}
		}

		// calculate totals
		totalFtOrKgs = stockFtOrKgs + rollingFtOrKgs;
		totalTonOrPcs = stockTonOrPcs + rollingTonOrPcs;

		// formatting
		String totalFtOrKgsStr = "";
		String totalTonOrPcsStr = "";
		String stockFtOrKgsStr = "";
		String stockTonOrPcsStr = "";
		String rollingFtOrKgsStr = "";
		String rollingTonOrPcsStr = "";

		if (orderModel.getIsAPIOrder() != null && orderModel.getIsAPIOrder())
		{
			totalFtOrKgsStr = decimalFormatNoDecimals.format(totalFtOrKgs);
			totalTonOrPcsStr = decimalFormatTon.format(totalTonOrPcs);
			stockFtOrKgsStr = decimalFormatNoDecimals.format(stockFtOrKgs);
			stockTonOrPcsStr = decimalFormatTon.format(stockTonOrPcs);
			rollingFtOrKgsStr = decimalFormatNoDecimals.format(rollingFtOrKgs);
			rollingTonOrPcsStr = decimalFormatTon.format(rollingTonOrPcs);
		}
		else
		{
			totalFtOrKgsStr = decimalFormatNoDecimals.format(totalFtOrKgs);
			totalTonOrPcsStr = decimalFormatNoDecimals.format(totalTonOrPcs);
			stockFtOrKgsStr = decimalFormatNoDecimals.format(stockFtOrKgs);
			stockTonOrPcsStr = decimalFormatNoDecimals.format(stockTonOrPcs);
			rollingFtOrKgsStr = decimalFormatNoDecimals.format(rollingFtOrKgs);
			rollingTonOrPcsStr = decimalFormatNoDecimals.format(rollingTonOrPcs);
		}


		String cartSummaryXMLString = "";
		if (orderModel.getIsAPIOrder() != null && orderModel.getIsAPIOrder())
		{
			cartSummaryXMLString = String.format(newQuoteNegotiationCartSummaryAPICAML, totalFtOrKgsStr, totalTonOrPcsStr,
					stockFtOrKgsStr, stockTonOrPcsStr, rollingFtOrKgsStr, rollingTonOrPcsStr);
		}
		else
		{
			cartSummaryXMLString = String.format(newQuoteNegotiationCartSummaryCAML, totalFtOrKgsStr, totalTonOrPcsStr,
					stockFtOrKgsStr, stockTonOrPcsStr, rollingFtOrKgsStr, rollingTonOrPcsStr);
		}

		return cartSummaryXMLString;
	}

	protected String createXMLStringForOrderSummary(final OrderModel orderModel)
	{
		final DecimalFormat decimalFormatNoDecimals = new DecimalFormat(NeorisSharePointConstants.DECIMAL_FORMAT_FT_LB_PC_KS);
		final String unit = orderModel.getSapWeightUnit() != null ? orderModel.getSapWeightUnit() : "Lb";


		final StringBuilder orderSummary = new StringBuilder();

		// Hot rolled weight
		if (orderModel.getHotRolledWeight() != null && orderModel.getHotRolledWeight().doubleValue() > 0)
		{
			final String hotRolledWeight = decimalFormatNoDecimals.format(orderModel.getHotRolledWeight().doubleValue()) + " "
					+ unit;
			final String hotRolledXMLString = String.format(newQuoteNegotiationOrderSummaryEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.HOT_ROLLED, hotRolledWeight);

			orderSummary.append(hotRolledXMLString);
		}

		// Cold rolled weight
		if (orderModel.getColdRolledWeight() != null && orderModel.getColdRolledWeight().doubleValue() > 0)
		{
			final String coldRolledWeight = decimalFormatNoDecimals.format(orderModel.getColdRolledWeight().doubleValue()) + " "
					+ unit;
			final String coldRolledXMLString = String.format(newQuoteNegotiationOrderSummaryEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.COLD_ROLLED, coldRolledWeight);

			orderSummary.append(coldRolledXMLString);
		}

		// Galvanized weight
		if (orderModel.getGalvanizedWeight() != null && orderModel.getGalvanizedWeight().doubleValue() > 0)
		{
			final String galvanizedWeight = decimalFormatNoDecimals.format(orderModel.getGalvanizedWeight().doubleValue()) + " "
					+ unit;
			final String galvanizedXMLString = String.format(newQuoteNegotiationOrderSummaryEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.GALVANIZED, galvanizedWeight);

			orderSummary.append(galvanizedXMLString);
		}

		// Galvametal weight
		if (orderModel.getGalvametalWeight() != null && orderModel.getGalvametalWeight().doubleValue() > 0)
		{
			final String galvametalWeight = decimalFormatNoDecimals.format(orderModel.getGalvametalWeight().doubleValue()) + " "
					+ unit;
			final String galvametalXMLString = String.format(newQuoteNegotiationOrderSummaryEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.GALVAMETAL, galvametalWeight);

			orderSummary.append(galvametalXMLString);
		}

		// Aluminized weight
		if (orderModel.getAluminizedWeight() != null && orderModel.getAluminizedWeight().doubleValue() > 0)
		{
			final String aluminizedWeight = decimalFormatNoDecimals.format(orderModel.getAluminizedWeight().doubleValue()) + " "
					+ unit;
			final String aluminizedXMLString = String.format(newQuoteNegotiationOrderSummaryEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.ALUMINIZED, aluminizedWeight);

			orderSummary.append(aluminizedXMLString);
		}

		// Total weight
		final String totalWeight = decimalFormatNoDecimals.format(orderModel.getTotalWeight().doubleValue()) + " " + unit;
		final String totalXMLString = String.format(newQuoteNegotiationOrderSummaryEntryCAML,
				NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.TOTALS, totalWeight);
		orderSummary.append(totalXMLString);

		return orderSummary.toString();
	}

	protected String createXMLStringForOrderTotals(final OrderModel orderModel)
	{
		final DecimalFormat decimalFormat = new DecimalFormat(NeorisSharePointConstants.DECIMAL_FORMAT);
		final String currency = orderModel.getSapCurrency() != null ? orderModel.getSapCurrency() : "USD";

		final StringBuilder orderTotals = new StringBuilder();

		// Hot rolled price
		if (orderModel.getHotRolledPrice() != null && orderModel.getHotRolledPrice().doubleValue() > 0)
		{
			final String hotRolledPrice = "$ " + decimalFormat.format(orderModel.getHotRolledPrice().doubleValue()) + " " + currency;
			final String hotRolledXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.HOT_ROLLED, hotRolledPrice);

			orderTotals.append(hotRolledXMLString);
		}

		// Cold rolled price
		if (orderModel.getColdRolledPrice() != null && orderModel.getColdRolledPrice().doubleValue() > 0)
		{
			final String coldRolledPrice = "$ " + decimalFormat.format(orderModel.getColdRolledPrice().doubleValue()) + " "
					+ currency;
			final String coldRolledXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.COLD_ROLLED, coldRolledPrice);

			orderTotals.append(coldRolledXMLString);
		}

		// Galvanized price
		if (orderModel.getGalvanizedPrice() != null && orderModel.getGalvanizedPrice().doubleValue() > 0)
		{
			final String galvanizedPrice = "$ " + decimalFormat.format(orderModel.getGalvanizedPrice().doubleValue()) + " "
					+ currency;
			final String galvanizedXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.GALVANIZED, galvanizedPrice);

			orderTotals.append(galvanizedXMLString);
		}

		// Galvametal price
		if (orderModel.getGalvametalPrice() != null && orderModel.getGalvametalPrice().doubleValue() > 0)
		{
			final String galvametalPrice = "$ " + decimalFormat.format(orderModel.getGalvametalPrice().doubleValue()) + " "
					+ currency;
			final String galvametalXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.GALVAMETAL, galvametalPrice);

			orderTotals.append(galvametalXMLString);
		}

		// Aluminized price
		if (orderModel.getAluminizedPrice() != null && orderModel.getAluminizedPrice().doubleValue() > 0)
		{
			final String aluminizedPrice = "$ " + decimalFormat.format(orderModel.getAluminizedPrice().doubleValue()) + " "
					+ currency;
			final String aluminizedXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.ALUMINIZED, aluminizedPrice);

			orderTotals.append(aluminizedXMLString);
		}

		// Delivery price
		if (orderModel.getTotalDeliveryCost() != null && orderModel.getTotalDeliveryCost().doubleValue() > 0)
		{
			final String deliveryPrice = "$ " + decimalFormat.format(orderModel.getTotalDeliveryCost().doubleValue()) + " "
					+ currency;
			final String deliveryXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.DELIVERY_COST, deliveryPrice);

			orderTotals.append(deliveryXMLString);
		}

		// Assurance price
		if (orderModel.getTotalAssurance() != null && orderModel.getTotalAssurance().doubleValue() > 0)
		{
			final String assurancePrice = "$ " + decimalFormat.format(orderModel.getTotalAssurance().doubleValue()) + " " + currency;
			final String assuranceXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.INSURANCE, assurancePrice);

			orderTotals.append(assuranceXMLString);
		}

		// Tax
		if (orderModel.getTotalTaxas() != null && orderModel.getTotalTaxas().doubleValue() > 0)
		{
			final String taxPrice = "$ " + decimalFormat.format(orderModel.getTotalTaxas().doubleValue()) + " " + currency;
			final String taxXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
					NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.TAX, taxPrice);

			orderTotals.append(taxXMLString);
		}

		// Total
		final String totalPrice = "$ " + decimalFormat.format(orderModel.getTotalPrice().doubleValue()) + " " + currency;
		final String totalXMLString = String.format(newQuoteNegotiationOrderTotalsEntryCAML,
				NeorisSharePointConstants.QuoteNegotiationIntegration.OrderConcept.TOTALS, totalPrice);
		orderTotals.append(totalXMLString);

		return orderTotals.toString();
	}
}
