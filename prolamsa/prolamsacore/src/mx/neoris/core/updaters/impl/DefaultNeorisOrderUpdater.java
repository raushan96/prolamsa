package mx.neoris.core.updaters.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.basecommerce.enums.OrderEntryStatus;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.constants.NeorisServiceConstants;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.services.NeorisAddressService;
import mx.neoris.core.services.NeorisCommerceCheckoutService;
import mx.neoris.core.services.NeorisTransportationModeService;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.core.updaters.NeorisObjectUpdater;
import mx.neoris.core.xml.orders.NeorisXMLOrder;
import mx.neoris.core.xml.orders.NeorisXMLOrderEntry;
import mx.neoris.core.xml.orders.NeorisXMLOrderEntryCol;
import mx.neoris.core.xml.shippingInstructions.NeorisXMLShippingInstructionsLine;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisOrderUpdater implements NeorisObjectUpdater
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisOrderUpdater.class);

	public static final String STATUS_UPDATE_ACTION = "Status";
	public static final String ORDER_UPDATE_ACTION = "Update";

	public static final String OBJECT_TYPE_ORDER = "S";
	public static final String OBJECT_TYPE_QUOTE = "Q";

	public static final String STATUS_DELETE = "X";
	public static final String STATUS_OVERDUE = "O";

	public static final String PRODCUT_CATALOG = "ProductCatalog";

	public static final String CATALOG_VERSION = "Online";

	public static final String DEFAULT_TYPE = "Z000";

	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name = "neorisAddressService")
	private NeorisAddressService addressService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "b2bUnitService")
	B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "neorisTransportationModeService")
	private NeorisTransportationModeService neorisTransportationModeService;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	@Resource(name = "productConverter")
	private AbstractConverter<ProductModel, ProductData> productConverter;

	@Resource(name = "orderConverter")
	private Converter<OrderModel, OrderData> orderConverter;

	@Resource(name = "neorisCartPriceCalculator")
	private NeorisCartPriceCalculator neorisCartPriceCalculator;

	@Resource(name = "neorisDefaultCommerceCheckoutService")
	private NeorisCommerceCheckoutService neorisDefaultCommerceCheckoutService;

	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private Map<String, String> orderStatusMap;
	private Map<String, String> orderEntryStatusMap;

	@Override
	public void updateFromFile(final File file) throws Exception
	{
		FileInputStream fileStream = null;

		try
		{
			fileStream = new FileInputStream(file);

			final JAXBContext jaxbContext = JAXBContext.newInstance(NeorisXMLOrder.class, NeorisXMLOrderEntry.class,
					NeorisXMLOrderEntryCol.class);

			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			final NeorisXMLOrder orderXML = (NeorisXMLOrder) jaxbUnmarshaller.unmarshal(file);

			if (orderXML == null)
			{
				throw new Exception("order not found in file");
			}
			Registry.activateMasterTenant();
			//Logica para hacer insert o update
			OrderModel orderModel = new OrderModel();

			//if (STATUS_UPDATE_ACTION.equalsIgnoreCase(orderXML.getAction()))
			//{
			//	updateOrderStatus(orderXML);
			//}
			//else

			//Comentado por CILS 17032015
			/*
			 * if (STATUS_DELETE.equalsIgnoreCase(orderXML.getStatus()) ||
			 * STATUS_OVERDUE.equalsIgnoreCase(orderXML.getStatus())) { try { updateOrderStatus(orderXML);
			 * updateOrderEntryStatus(orderXML); } catch (final Exception e) { throw new
			 * Exception("UPDATE ORDER STATUS FAILED"); } } else
			 */
			if (ORDER_UPDATE_ACTION.equalsIgnoreCase(orderXML.getAction()))
			{
				orderModel = b2bOrderService.getOrderForCode(orderXML.getId());
				if (orderModel == null)
				{
					createOrder(orderXML);
				}
				else
				{
					updateOrder(orderModel, orderXML);
				}
			}
			else
			{
				throw new Exception("Unknown action: " + orderXML.getAction());
			}
		}
		finally
		{
			IOUtils.closeQuietly(fileStream);
		}
	}

	public void updateOrderStatus(final NeorisXMLOrder orderXML) throws Exception
	{
		Registry.activateMasterTenant();

		final OrderModel orderModel = b2bOrderService.getOrderForCode(orderXML.getId());

		if (orderModel == null)
		{
			throw new Exception("Unknown order id: " + orderXML.getId());
		}
		else
		{
			setObjectStatus(orderXML, orderModel);
		}

		modelService.save(orderModel);
	}

	public void updateOrderEntryStatus(final NeorisXMLOrder orderXML) throws Exception
	{
		Registry.activateMasterTenant();

		final OrderModel orderModel = b2bOrderService.getOrderForCode(orderXML.getId());

		if (orderModel == null)
		{
			throw new Exception("Unknown order id: " + orderXML.getId() + " wish update order entry status");
		}
		else
		{
			setObjectEntryStatus(orderXML, orderModel);
		}

		modelService.save(orderModel);
	}

	private void setObjectEntryStatus(final NeorisXMLOrder orderXML, final OrderModel orderModel) throws Exception
	{
		final String orderStatusXML = orderXML.getStatus();
		final String orderStatus = this.getOrderEntryStatusMap().get(orderStatusXML);

		if (orderStatus == null)
		{
			throw new Exception("Exception Updating OrderEntry with Order Number " + orderXML.getId() + " Unknown OrderStatusCode "
					+ orderXML.getStatus());
		}

		for (final AbstractOrderEntryModel entries : orderModel.getEntries())
		{
			entries.setStatus(OrderEntryStatus.valueOf(orderStatus));
		}
	}

	private void setObjectStatus(final NeorisXMLOrder orderXML, final OrderModel orderModel) throws Exception
	{
		// get order status from XML
		String orderStatusXML = orderXML.getStatus();

		// set the appropriate encoded status for the object type
		if (OBJECT_TYPE_QUOTE.equalsIgnoreCase(orderXML.getType()))
		{
			orderStatusXML = "QUOTE_" + orderXML.getStatus();
		}

		// get the order status enumeration code from the mapping
		final String orderStatus = this.getOrderStatusMap().get(orderStatusXML);

		// if not found
		if (orderStatus == null)
		{
			throw new Exception("Exception Updating Order " + orderXML.getId() + " Unknown OrderStatusCode " + orderXML.getStatus());
		}

		// set the order status
		orderModel.setStatus(OrderStatus.valueOf(orderStatus));
	}

	public void updateOrder(final OrderModel orderModel, final NeorisXMLOrder orderXML) throws Exception
	{
		Double coldRolledTotalPrice = 0.00d;
		Double hotRolledTotalPrice = 0.00d;
		Double galvanizedTotalPrice = 0.00d;
		Double galvametalTotalPrice = 0.00d;
		Double aluminizedTotalPrice = 0.00d;

		//initialize totals Weight
		Double coldRolledTotalWeight = 0.00d;
		Double hotRolledTotalWeight = 0.00d;
		Double galvanizedTotalWeight = 0.00d;
		Double galvametalTotalWeight = 0.00d;
		Double aluminizedTotalWeight = 0.00d;

		Double totalAssurance = 0.00d;
		Double totalTaxas = 0.00d;
		Double totalDeliveryCost = 0.00d;

		Double totalWeightAll = 0.00d;
		Double totalPriceAll = 0.00d;
		Double subtotalPrice = 0.00d;

		Registry.activateMasterTenant();

		LOG.info("Starting update order with id: " + orderXML.getId());

		orderModel.setDate(orderXML.getCreationDate());

		//NEORIS_CHANGE # Include Requested Delivery Date
		orderModel.setRequestedDeliveryDate(orderXML.getRequestedDeliveryDate());

		final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(orderXML.getSalesOrganization());
		if (baseStoreModel == null)
		{
			throw new Exception("BaseStore not found with id: " + orderXML.getSalesOrganization());
		}
		orderModel.setStore(baseStoreModel);

		final B2BUnitModel b2bUnitModel = b2bUnitService.getUnitForUid(orderXML.getBillTo());
		if (b2bUnitModel == null)
		{
			throw new Exception("B2BUnit not found with id: " + orderXML.getBillTo());
		}
		orderModel.setUnit(b2bUnitModel);

		AddressModel addressModel = null; // AddressModel addressModel =
		addressService.getAddressWithCode(orderXML.getDeliveryAddress());
		for (final AddressModel eachAddress : b2bUnitModel.getAddresses())
		{
			if (eachAddress.getCode().equals(orderXML.getDeliveryAddress()))
			{
				addressModel = eachAddress;
				break;
			}
		}
		if (addressModel == null)
		{
			throw new Exception("Address not found with id: " + orderXML.getDeliveryAddress());
		}
		orderModel.setDeliveryAddress(addressModel);

		final AddressModel billAddressModel = addressService.getAddressWithCode(orderXML.getBillTo());
		if (billAddressModel == null || billAddressModel.getBillingAddress() == false)
		{
			throw new Exception("Bill Address not found with id: " + orderXML.getBillTo() + "... or not is a billing address");
		}
		orderModel.setBillingAddress(billAddressModel);

		/*
		 * AddressModel addressModel = null; // AddressModel addressModel =
		 * addressService.getAddressWithCode(orderXML.getDeliveryAddress()); for (final AddressModel eachAddress :
		 * b2bUnitModel.getAddresses()) { if (eachAddress.getCode().equals(orderXML.getDeliveryAddress())) { addressModel
		 * = eachAddress; break; } } if (addressModel == null) { throw new Exception("Address not found with id: " +
		 * orderXML.getDeliveryAddress()); } orderModel.setDeliveryAddress(addressModel);
		 * 
		 * AddressModel addressModelBill = null; for (final AddressModel eachAddress : b2bUnitModel.getBillingAddresses())
		 * { if (eachAddress.getCode().equals(orderXML.getBillTo())) { addressModelBill = eachAddress; break; } } if
		 * (addressModelBill == null) { throw new Exception("Address Bill not found with id: " + orderXML.getBillTo()); }
		 * 
		 * orderModel.setBillingAddress(addressModelBill);
		 */
		setObjectStatus(orderXML, orderModel);

		orderModel.setPurchaseOrderNumber(orderXML.getPo());

		orderModel.setUPrice(orderXML.getuPrecio());

		if (orderXML.getTransportationMode() == null || orderXML.getIncoterm() == null)
		{
			throw new Exception("Could not search TransportationMode");
		}
		final TransportationModeModel tranportationModel = neorisTransportationModeService.getTransportationModeForCodeForXML(
				orderXML.getTransportationMode(), orderXML.getIncoterm());
		if (tranportationModel == null)
		{
			throw new Exception("TransportationMode not found with id: " + orderXML.getTransportationMode() + orderXML.getIncoterm());
		}
		orderModel.setTransportationMode(tranportationModel);

		/*
		 * String TPago = ""; final String primerDigito = orderXML.getTipoPago().substring(0, 1);
		 * if(primerDigito.equals("0")) { TPago = "_" + orderXML.getTipoPago(); }else { TPago = orderXML.getTipoPago(); }
		 */
		String TPago = "";

		final Character c = orderXML.getTipoPago().charAt(0);
		if (Character.isDigit(c))
		{
			TPago = "_" + orderXML.getTipoPago();
		}
		else
		{
			TPago = orderXML.getTipoPago();
		}

		final HybrisEnumValue enumValue = enumerationService.getEnumerationValue("PaymentTermsType", TPago);
		orderModel.setTPago((PaymentTermsType) enumValue);


		//Se comento de acuerdo a lo platicado con e-phinojosa 05092014
		//final UserModel userModel = userService.getUserForUID(orderXML.getUser());
		//if (userModel == null)
		//{
		//	throw new Exception("User not found with id: " + orderXML.getUser());
		//}
		//orderModel.setUser(userModel);

		orderModel.setCurrency(baseStoreModel.getDefaultCurrency());

		final StringBuffer shippingIntructions = new StringBuffer();
		for (final NeorisXMLShippingInstructionsLine shippingInstLine : orderXML.getShippingInstructionsCol().getLines())
		{
			String lineValue = shippingInstLine.getLineValue();

			if (orderXML.getShippingInstructionsCol().getLines().size() > 1)
			{
				if (lineValue.trim().length() > 0)
				{
					final char lastCharOnLine = lineValue.charAt(lineValue.length() - 1);
					if (Character.isWhitespace(lastCharOnLine))
					{
						lineValue += "\r\n";
					}
				}
			}

			shippingIntructions.append(lineValue);
		}

		orderModel.setShippingInstructions(shippingIntructions.toString());

		final List<AbstractOrderEntryModel> newOrderEntries = new ArrayList<AbstractOrderEntryModel>();

		for (final NeorisXMLOrderEntry entryXML : orderXML.getOrderEntryCol().getEntries())
		{
			catalogVersionService.setSessionCatalogVersion(orderXML.getSalesOrganization() + PRODCUT_CATALOG, CATALOG_VERSION);

			final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(orderXML.getSalesOrganization()
					+ PRODCUT_CATALOG, CATALOG_VERSION);
			if (catalogVersionModel == null)
			{
				throw new Exception("CatalogVersion not found with: " + orderXML.getSalesOrganization() + PRODCUT_CATALOG
						+ CATALOG_VERSION);
			}

			final OrderEntryModel entryModel = new OrderEntryModel();

			final ProductModel productModel = productService.getProductForCode(catalogVersionModel, entryXML.getProductId() + "_"
					+ entryXML.getLocation());
			if (productModel == null)
			{
				throw new Exception("Product not found with: " + catalogVersionModel + entryXML.getProductId() + "_"
						+ entryXML.getLocation());
			}

			entryModel.setProduct(productModel);

			final ProlamsaProductModel prolamsaProductModel = (ProlamsaProductModel) productModel;



			// Verify if quantity is a valid factor of piece/bundle
			Long quantity = 0L;
			if (!prolamsaProductModel.getIsAPI())
			{
				// hybris is handling only bundles for mx and usa
				if (prolamsaProductModel.getPiecesPerBundle() == 0)
				{
					throw new Exception("ProlamsaProductModel not found or pieces per bundle is zero ");
				}

				// Verify if quantity is a valid factor of piece/bundle
				final double divRemainder = entryXML.getQuantity() % prolamsaProductModel.getPiecesPerBundle();


				if (divRemainder == 0.00d)
				{
					entryModel.setIsQuantityInPieces(false);
					quantity = Math.round((entryXML.getQuantity() / prolamsaProductModel.getPiecesPerBundle()));
				}
				else
				{
					entryModel.setIsQuantityInPieces(true);
					quantity = Math.round(entryXML.getQuantity());
				}

				entryModel.setQuantity(quantity);
			}
			else
			{
				quantity = Math.round(entryXML.getQuantity());
				entryModel.setIsQuantityInPieces(false);
				entryModel.setQuantity(Math.round(entryXML.getQuantity()));
			}

			entryModel.setQuantity(quantity);

			final ProductData productData = productConverter.convert(productModel);
			if (productData == null)
			{
				throw new Exception("ProductData not found");
			}

			// UoM to use on conversion

			UnitModel orderUoM = orderModel.getUnitWhenPlaced();
			if (orderUoM == null)
			{
				orderUoM = baseStoreModel.getUnit();
			}

			if (!entryModel.getIsQuantityInPieces())
			{
				final double convertedQuantity = neorisUoMQuantityConverter
						.convertOutputQuantityFrom(orderUoM, quantity, productData);

				entryModel.setConvertedQuantity(convertedQuantity);
			}

			entryModel.setRollingScheduleWeek(entryXML.getReadyToShip());

			entryModel.setReadyToShip(entryXML.getReadyToShip());

			//Fase 9 CILS 07Dic2016
			//entryModel.setWeight(entryXML.getWeight());
			if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
			{
				entryModel.setWeight(entryXML.getWeightPerTon());
			}
			else
			{
				entryModel.setWeight(entryXML.getWeight());
			}

			entryModel.setPricePerFeet(entryXML.getPricePerFeet());

			if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
			{
				entryModel.setPricePerTon(entryXML.getPricePerTon());
			}

			entryModel.setPricePerPc(entryXML.getPricePerPc());

			entryModel.setNetPriceWOTaxes(entryXML.getNetPriceWOTaxes());

			boolean hasConfiguration = false;
			if (StringUtils.isNotBlank(entryXML.getPressure()) || StringUtils.isNotBlank(entryXML.getDuration())
					|| StringUtils.isNotBlank(entryXML.getSpecificStencil()) || StringUtils.isNotBlank(entryXML.getSpecialDrifter())
					|| StringUtils.isNotBlank(entryXML.getSpecialDrifter()) || StringUtils.isNotBlank(entryXML.getSpecialLength())
					|| StringUtils.isNotBlank(entryXML.getCharpyTemperature())
					|| StringUtils.isNotBlank(entryXML.getCharpyDirection()) || StringUtils.isNotBlank(entryXML.getCharpySize()))
			{
				hasConfiguration = true;
			}

			if (hasConfiguration)
			{
				final ProlamsaAPIProductConfigurationModel configModel = new ProlamsaAPIProductConfigurationModel();

				if (StringUtils.isNotBlank(entryXML.getPressure()))
				{
					configModel.setPressure(Integer.valueOf(entryXML.getPressure()));
				}

				if (StringUtils.isNotBlank(entryXML.getDuration()))
				{
					configModel.setDuration(Integer.valueOf(entryXML.getDuration()));
				}

				configModel.setSpecificStencil(entryXML.getSpecificStencil());

				if (StringUtils.isNotBlank(entryXML.getSpecialDrifter()))
				{
					configModel.setSpecialDrifter(Double.valueOf(entryXML.getSpecialDrifter()));
				}

				if (StringUtils.isNotBlank(entryXML.getSpecialLength()))
				{
					configModel.setSpecificLength(Double.valueOf(entryXML.getSpecialLength()));
				}

				if (StringUtils.isNotBlank(entryXML.getCharpyTemperature()))
				{
					configModel.setTestTemp(Integer.valueOf(entryXML.getCharpyTemperature()));
				}

				configModel.setLocationOfTest(entryXML.getCharpy());

				configModel.setSampleDirection(entryXML.getCharpyDirection());

				configModel.setSampleSize(entryXML.getCharpySize());

				//modelService.save(configModel);

				entryModel.setApiProductConfiguration(configModel);
			}

			if (StringUtils.isNotBlank(entryXML.getStatus()))
			{
				final String orderEntryStatus = this.getOrderEntryStatusMap().get(entryXML.getStatus());

				if (orderEntryStatus == null)
				{
					throw new Exception("Exception Updating Order " + orderXML.getId() + " Unknown OrderEntryStatusCode "
							+ entryXML.getStatus());
				}
				else
				{
					entryModel.setStatus(OrderEntryStatus.valueOf(orderEntryStatus));
				}
			}

			entryModel.setOrder(orderModel);

			entryModel.setUnit(orderUoM);

			///INICIO////////////////////////////////////////////////////////////////////////////////////////////
			if (entryXML.getStatus().equals("D") || entryXML.getStatus().equals("R") || entryXML.getStatus().equals("X")
					|| entryXML.getStatus().equals("O"))
			{
				//No hacer nada
			}
			else
			{
				final Double totalNetPrice = entryXML.getNetPriceWOTaxes();
				//final Double totalWeight = entryXML.getWeight();
				//Fase 9 CILS 07Dic2016
				Double totalWeight = 0.00d;
				if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
				{
					totalWeight = entryXML.getWeightPerTon();
				}
				else
				{
					totalWeight = entryXML.getWeight();
				}

				if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.COLD_ROLLED))
				{
					coldRolledTotalPrice += totalNetPrice;
					coldRolledTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.HOT_ROLLED))
				{
					hotRolledTotalPrice += totalNetPrice;
					hotRolledTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.ALUMINIZED))
				{
					aluminizedTotalPrice += totalNetPrice;
					aluminizedTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVANIZED))
				{
					galvanizedTotalPrice += totalNetPrice;
					galvanizedTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVAMETAL))
				{
					galvametalTotalPrice += totalNetPrice;
					galvametalTotalWeight += totalWeight;
				}

				final Double entryTax = entryXML.getTaxas();
				final Double entryAssurance = entryXML.getAssurance();
				final Double entryDeliveryCost = entryXML.getDelvieryCost();


				totalAssurance += entryAssurance;
				totalTaxas += entryTax;
				totalDeliveryCost += entryDeliveryCost;

				totalWeightAll = coldRolledTotalWeight + hotRolledTotalWeight + aluminizedTotalWeight + galvanizedTotalWeight
						+ galvametalTotalWeight;
				totalPriceAll = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice + galvanizedTotalPrice
						+ galvametalTotalPrice + totalAssurance + totalDeliveryCost + totalTaxas;

				subtotalPrice = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice + galvanizedTotalPrice
						+ galvametalTotalPrice + totalAssurance + totalDeliveryCost;

			}

			Double baseTotal = 0.00d;

			// avoid division with zero
			if (quantity != 0L)
			{
				baseTotal = entryXML.getNetPriceWOTaxes() / quantity;

				final String[] splitter = baseTotal.toString().split("\\.");
				//splitter[0].length();   // Before Decimal Count
				//splitter[1].length();   // After  Decimal Count

				if (splitter[1].length() > 4)
				{
					baseTotal = Math.round(baseTotal * 1000) / 1000.0d;
				}

				entryModel.setBasePrice(baseTotal);
			}

			entryModel.setTotalPrice(entryXML.getNetPriceWOTaxes());

			entryModel.setEntryNumber(entryXML.getLineItem());
			entryModel.setSapEntryNumber(entryXML.getLineItem());

			entryModel.setSteelCategory(entryXML.getSteel());
			entryModel.setTotalTaxas(entryXML.getTaxas());
			entryModel.setTotalAssurance(entryXML.getAssurance());
			entryModel.setTotalDeliveryCost(entryXML.getDelvieryCost());
			//Fase 9 CILS 07Dic2016
			//entryModel.setWeight(entryXML.getWeight());
			if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
			{
				entryModel.setWeight(entryXML.getWeightPerTon());
			}
			else
			{
				entryModel.setWeight(entryXML.getWeight());
			}
			///FIN///////////////////////////////////////////////////////////////////////////////////////////

			//modelService.save(entryModel);

			newOrderEntries.add(entryModel);

		}

		//modelService.save(orderModel);

		//////////////////////////////////

		for (final AbstractOrderEntryModel entryes : orderModel.getEntries())
		{
			modelService.remove(entryes);
		}

		orderModel.setEntries(newOrderEntries);

		final OrderData orderData = orderConverter.convert(orderModel);

		this.getPriceOrderXML(orderData, totalWeightAll, coldRolledTotalWeight, hotRolledTotalWeight, aluminizedTotalWeight,
				galvanizedTotalWeight, totalPriceAll, coldRolledTotalPrice, hotRolledTotalPrice, aluminizedTotalPrice,
				galvanizedTotalPrice, galvametalTotalPrice, galvametalTotalWeight, totalAssurance, totalDeliveryCost, totalTaxas,
				subtotalPrice);

		orderModel.setHotRolledWeight(hotRolledTotalWeight);

		orderModel.setColdRolledWeight(coldRolledTotalWeight);

		orderModel.setGalvanizedWeight(galvanizedTotalWeight);

		orderModel.setAluminizedWeight(aluminizedTotalWeight);

		orderModel.setTotalWeight(totalWeightAll);

		//set prices categories
		orderModel.setHotRolledPrice(hotRolledTotalPrice);

		orderModel.setColdRolledPrice(coldRolledTotalPrice);

		orderModel.setGalvanizedPrice(galvanizedTotalPrice);

		orderModel.setAluminizedPrice(aluminizedTotalPrice);

		orderModel.setSapTotalPrice(totalPriceAll);

		orderModel.setSapSubtotalPrice(subtotalPrice);

		orderModel.setTotalAssurance(totalAssurance);

		orderModel.setTotalDeliveryCost(totalDeliveryCost);

		orderModel.setTotalTaxas(totalTaxas);
		//orderData.setTotalPrice(totalPriceAll);

		//neorisCartPriceCalculator.calculatePrices(orderData);

		//neorisCartPriceHelper.sortEntriesByReadyToShipDate(orderData);
		neorisCartPriceHelper.setGroupingOfOrderData(orderData);

		neorisCartPriceHelper.setFormattedSapData(orderData);

		//neorisDefaultCommerceCheckoutService.saveSAPWeight(orderModel, orderData);
		//neorisDefaultCommerceCheckoutService.saveSAPPrices(orderModel, orderData);

		if (baseStoreModel.getAPIFunctionaliatyEnabled() != null && baseStoreModel.getAPIFunctionaliatyEnabled().booleanValue())
		{
			validateNotMixedProductsForAPI(orderModel);
		}

		if (baseStoreModel.getHSSFunctionaliatyEnabled() != null && baseStoreModel.getHSSFunctionaliatyEnabled().booleanValue())
		{
			validateNotMixedProductsForHSS(orderModel);
		}

		modelService.save(orderModel);

		LOG.info("Finished update order with id: " + orderXML.getId());
	}

	public void createOrder(final NeorisXMLOrder orderXML) throws Exception
	{

		Double coldRolledTotalPrice = 0.00d;
		Double hotRolledTotalPrice = 0.00d;
		Double galvanizedTotalPrice = 0.00d;
		Double galvametalTotalPrice = 0.00d;
		Double aluminizedTotalPrice = 0.00d;

		//initialize totals Weight
		Double coldRolledTotalWeight = 0.00d;
		Double hotRolledTotalWeight = 0.00d;
		Double galvanizedTotalWeight = 0.00d;
		Double galvametalTotalWeight = 0.00d;
		Double aluminizedTotalWeight = 0.00d;

		Double totalAssurance = 0.00d;
		Double totalTaxas = 0.00d;
		Double totalDeliveryCost = 0.00d;

		Double totalWeightAll = 0.00d;
		Double totalPriceAll = 0.00d;
		Double subtotalPrice = 0.00d;

		String orderCurrency = null;

		Registry.activateMasterTenant();

		OrderModel orderModel = b2bOrderService.getOrderForCode(orderXML.getId());
		if (orderModel == null)
		{
			LOG.info("Starting create order with id: " + orderXML.getId());
			orderModel = new OrderModel();
		}
		orderModel.setCode(orderXML.getId());
		orderModel.setDate(orderXML.getCreationDate());

		//NEORIS_CHANGE # Include Requested Delivery Date
		orderModel.setRequestedDeliveryDate(orderXML.getRequestedDeliveryDate());


		final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(orderXML.getSalesOrganization());
		if (baseStoreModel == null)
		{
			throw new Exception("BaseStore not found with id: " + orderXML.getSalesOrganization());
		}
		orderModel.setStore(baseStoreModel);

		final B2BUnitModel b2bUnitModel = b2bUnitService.getUnitForUid(orderXML.getBillTo());
		if (b2bUnitModel == null)
		{
			throw new Exception("B2BUnit not found with id: " + orderXML.getBillTo());
		}
		orderModel.setUnit(b2bUnitModel);

		AddressModel addressModel = null; // AddressModel addressModel =
		addressService.getAddressWithCode(orderXML.getDeliveryAddress());
		for (final AddressModel eachAddress : b2bUnitModel.getAddresses())
		{
			if (eachAddress.getCode().equals(orderXML.getDeliveryAddress()))
			{
				addressModel = eachAddress;
				break;
			}
		}
		if (addressModel == null)
		{
			throw new Exception("Address not found with id: " + orderXML.getDeliveryAddress());
		}
		orderModel.setDeliveryAddress(addressModel);

		final AddressModel billAddressModel = addressService.getAddressWithCode(orderXML.getBillTo());
		if (billAddressModel == null || billAddressModel.getBillingAddress() == false)
		{
			throw new Exception("Bill Address not found with id: " + orderXML.getBillTo() + "... or not is a billing address");
		}
		orderModel.setBillingAddress(billAddressModel);

		/*
		 * AddressModel addressModelBill = null; // AddressModel addressModel =
		 * addressService.getAddressWithCode(orderXML.getBillTo()); for (final AddressModel eachAddressBill :
		 * b2bUnitModel.getBillingAddresses()) { if (eachAddressBill.getCode().equals(orderXML.getBillTo())) {
		 * addressModelBill = eachAddressBill; break; } } if (addressModelBill == null) { throw new
		 * Exception("Address Bill not found with id: " + orderXML.getBillTo()); }
		 * 
		 * orderModel.setBillingAddress(addressModelBill);
		 */
		setObjectStatus(orderXML, orderModel);

		orderModel.setPurchaseOrderNumber(orderXML.getPo());

		orderModel.setUPrice(orderXML.getuPrecio());

		//if (orderXML.getTransportationMode() == null || orderXML.getIncoterm() == null)
		//{
		//	throw new Exception("Could not search TransportationMode");
		//}

		final TransportationModeModel tranportationModel = neorisTransportationModeService.getTransportationModeForCodeForXML(
				orderXML.getTransportationMode(), orderXML.getIncoterm());
		if (tranportationModel == null)
		{
			throw new Exception("TransportationMode not found with id: " + orderXML.getTransportationMode() + orderXML.getIncoterm());
		}
		orderModel.setTransportationMode(tranportationModel);

		String TPago = "";

		final Character c = orderXML.getTipoPago().charAt(0);
		if (Character.isDigit(c))
		{
			TPago = "_" + orderXML.getTipoPago();
		}
		else
		{
			TPago = orderXML.getTipoPago();
		}

		final HybrisEnumValue enumValue = enumerationService.getEnumerationValue("PaymentTermsType", TPago);

		orderModel.setTPago((PaymentTermsType) enumValue);

		final String userToOrder = configurationService.getConfiguration().getString("customer.create.order");
		final UserModel userModel = userService.getUserForUID(userToOrder);

		if (userModel == null)
		{
			throw new Exception("User not found with id: " + orderXML.getUser());
		}
		orderModel.setUser(userModel);

		orderModel.setCurrency(baseStoreModel.getDefaultCurrency());

		orderModel.setUnitWhenPlaced(baseStoreModel.getUnit());

		final StringBuffer shippingIntructions = new StringBuffer();
		for (final NeorisXMLShippingInstructionsLine shippingInstLine : orderXML.getShippingInstructionsCol().getLines())
		{
			String lineValue = shippingInstLine.getLineValue();

			if (orderXML.getShippingInstructionsCol().getLines().size() > 1)
			{
				if (lineValue.trim().length() > 0)
				{
					final char lastCharOnLine = lineValue.charAt(lineValue.length() - 1);
					if (Character.isWhitespace(lastCharOnLine))
					{
						lineValue += "\r\n";
					}
				}
			}

			shippingIntructions.append(lineValue);
		}

		orderModel.setShippingInstructions(shippingIntructions.toString());

		final List<AbstractOrderEntryModel> newOrderEntries = new ArrayList<AbstractOrderEntryModel>();

		for (final NeorisXMLOrderEntry entryXML : orderXML.getOrderEntryCol().getEntries())
		{
			catalogVersionService.setSessionCatalogVersion(orderXML.getSalesOrganization() + PRODCUT_CATALOG, CATALOG_VERSION);

			final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(orderXML.getSalesOrganization()
					+ PRODCUT_CATALOG, CATALOG_VERSION);
			if (catalogVersionModel == null)
			{
				throw new Exception("CatalogVersion not found with: " + orderXML.getSalesOrganization() + PRODCUT_CATALOG
						+ CATALOG_VERSION);
			}

			final OrderEntryModel entryModel = new OrderEntryModel();

			final ProductModel productModel = productService.getProductForCode(catalogVersionModel, entryXML.getProductId() + "_"
					+ entryXML.getLocation());
			if (productModel == null)
			{
				throw new Exception("Product not found with: " + catalogVersionModel + entryXML.getProductId() + "_"
						+ entryXML.getLocation());
			}
			entryModel.setProduct(productModel);

			final ProlamsaProductModel prolamsaProductModel = (ProlamsaProductModel) productModel;



			// define order currency based on entries
			if (StringUtils.isEmpty(orderCurrency))
			{
				orderCurrency = entryXML.getCurrency();
			}
			Long quantity = 0L;
			if (!prolamsaProductModel.getIsAPI())
			{
				// hybris is handling only bundles for mx and usa
				if (prolamsaProductModel.getPiecesPerBundle() == 0)
				{
					throw new Exception("ProlamsaProductModel not found or pieces per bundle is zero ");
				}

				// Verify if quantity is a valid factor of piece/bundle
				final double divRemainder = entryXML.getQuantity() % prolamsaProductModel.getPiecesPerBundle();


				if (divRemainder == 0.00d)
				{
					entryModel.setIsQuantityInPieces(false);
					quantity = Math.round((entryXML.getQuantity() / prolamsaProductModel.getPiecesPerBundle()));
				}
				else
				{
					entryModel.setIsQuantityInPieces(true);
					quantity = Math.round(entryXML.getQuantity());
				}

				entryModel.setQuantity(quantity);
			}
			else
			{
				quantity = Math.round(entryXML.getQuantity());
				entryModel.setIsQuantityInPieces(false);
				entryModel.setQuantity(Math.round(entryXML.getQuantity()));
			}
			final ProductData productData = productConverter.convert(productModel);
			if (productData == null)
			{
				throw new Exception("ProductData not found");
			}

			// UoM to use on conversion

			UnitModel orderUoM = orderModel.getUnitWhenPlaced();
			if (orderUoM == null)
			{
				orderUoM = baseStoreModel.getUnit();
			}

			//final double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(orderUoM, quantityBundles,
			//		productData);

			//entryModel.setConvertedQuantity(convertedQuantity);

			//18092014
			entryModel.setConvertedQuantity(entryXML.getQuantity());

			entryModel.setRollingScheduleWeek(entryXML.getReadyToShip());

			entryModel.setReadyToShip(entryXML.getReadyToShip());
			
			//Fase 9 CILS 07Dic2016
			//entryModel.setWeight(entryXML.getWeight());
			if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
			{
				entryModel.setWeight(entryXML.getWeightPerTon());
			}
			else
			{
				entryModel.setWeight(entryXML.getWeight());
			}

			entryModel.setPricePerFeet(entryXML.getPricePerFeet());

			if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
			{
				entryModel.setPricePerTon(entryXML.getPricePerTon());
			}

			entryModel.setPricePerPc(entryModel.getPricePerPc());

			entryModel.setNetPriceWOTaxes(entryXML.getNetPriceWOTaxes());

			boolean hasConfiguration = false;
			if (StringUtils.isNotBlank(entryXML.getPressure()) || StringUtils.isNotBlank(entryXML.getDuration())
					|| StringUtils.isNotBlank(entryXML.getSpecificStencil()) || StringUtils.isNotBlank(entryXML.getSpecialDrifter())
					|| StringUtils.isNotBlank(entryXML.getSpecialDrifter()) || StringUtils.isNotBlank(entryXML.getSpecialLength())
					|| StringUtils.isNotBlank(entryXML.getCharpyTemperature())
					|| StringUtils.isNotBlank(entryXML.getCharpyDirection()) || StringUtils.isNotBlank(entryXML.getCharpySize()))
			{
				hasConfiguration = true;
			}

			if (hasConfiguration)
			{
				final ProlamsaAPIProductConfigurationModel configModel = new ProlamsaAPIProductConfigurationModel();

				if (StringUtils.isNotBlank(entryXML.getPressure()))
				{
					configModel.setPressure(Integer.valueOf(entryXML.getPressure()));
				}

				if (StringUtils.isNotBlank(entryXML.getDuration()))
				{
					configModel.setDuration(Integer.valueOf(entryXML.getDuration()));
				}

				configModel.setSpecificStencil(entryXML.getSpecificStencil());

				if (StringUtils.isNotBlank(entryXML.getSpecialDrifter()))
				{
					configModel.setSpecialDrifter(Double.valueOf(entryXML.getSpecialDrifter()));
				}

				if (StringUtils.isNotBlank(entryXML.getSpecialLength()))
				{
					configModel.setSpecificLength(Double.valueOf(entryXML.getSpecialLength()));
				}

				if (StringUtils.isNotBlank(entryXML.getCharpyTemperature()))
				{
					configModel.setTestTemp(Integer.valueOf(entryXML.getCharpyTemperature()));
				}

				configModel.setLocationOfTest(entryXML.getCharpy());

				configModel.setSampleDirection(entryXML.getCharpyDirection());

				configModel.setSampleSize(entryXML.getCharpySize());



				//modelService.save(configModel);

				entryModel.setApiProductConfiguration(configModel);
			}

			if (StringUtils.isNotBlank(entryXML.getStatus()))
			{
				final String orderEntryStatus = this.getOrderEntryStatusMap().get(entryXML.getStatus());

				if (orderEntryStatus == null)
				{
					throw new Exception("Exception Updating Order " + orderXML.getId() + " Unknown OrderEntryStatusCode "
							+ entryXML.getStatus());
				}
				else
				{
					entryModel.setStatus(OrderEntryStatus.valueOf(orderEntryStatus));
				}
			}

			entryModel.setOrder(orderModel);

			entryModel.setUnit(orderUoM);

			///INICIO////////////////////////////////////////////////////////////////////////////////////////////
			if (entryXML.getStatus().equals("D") || entryXML.getStatus().equals("R") || entryXML.getStatus().equals("X")
					|| entryXML.getStatus().equals("O"))
			{
				//No hacer nada
			}
			else
			{
				final Double totalNetPrice = entryXML.getNetPriceWOTaxes();
				//final Double totalWeight = entryXML.getWeight();
				//Fase 9 CILS 07Dic2016
				Double totalWeight = 0.00d;
				if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
				{
					totalWeight = entryXML.getWeightPerTon();
				}
				else
				{
					totalWeight = entryXML.getWeight();
				}

				if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.COLD_ROLLED))
				{
					coldRolledTotalPrice += totalNetPrice;
					coldRolledTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.HOT_ROLLED))
				{
					hotRolledTotalPrice += totalNetPrice;
					hotRolledTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.ALUMINIZED))
				{
					aluminizedTotalPrice += totalNetPrice;
					aluminizedTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVANIZED))
				{
					galvanizedTotalPrice += totalNetPrice;
					galvanizedTotalWeight += totalWeight;
				}
				else if (entryXML.getSteel().equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVAMETAL))
				{
					galvametalTotalPrice += totalNetPrice;
					galvametalTotalWeight += totalWeight;
				}

				final Double entryTax = entryXML.getTaxas();
				final Double entryAssurance = entryXML.getAssurance();
				final Double entryDeliveryCost = entryXML.getDelvieryCost();


				totalAssurance += entryAssurance;
				totalTaxas += entryTax;
				totalDeliveryCost += entryDeliveryCost;

				totalWeightAll = coldRolledTotalWeight + hotRolledTotalWeight + aluminizedTotalWeight + galvanizedTotalWeight
						+ galvametalTotalWeight;
				totalPriceAll = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice + galvanizedTotalPrice
						+ galvametalTotalPrice + totalAssurance + totalDeliveryCost + totalTaxas;
				subtotalPrice = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice + galvanizedTotalPrice
						+ galvametalTotalPrice + totalAssurance + totalDeliveryCost;

			}

			Double baseTotal = 0.00d;

			// avoid division with zero
			if (quantity != 0L)
			{
				baseTotal = entryXML.getNetPriceWOTaxes() / quantity;

				final String[] splitter = baseTotal.toString().split("\\.");
				//splitter[0].length();   // Before Decimal Count
				//splitter[1].length();   // After  Decimal Count

				if (splitter[1].length() > 4)
				{
					baseTotal = Math.round(baseTotal * 1000) / 1000.0d;
				}

				entryModel.setBasePrice(baseTotal);
			}

			entryModel.setTotalPrice(entryXML.getNetPriceWOTaxes());

			entryModel.setEntryNumber(entryXML.getLineItem());
			entryModel.setSapEntryNumber(entryXML.getLineItem());

			entryModel.setSteelCategory(entryXML.getSteel());
			entryModel.setTotalTaxas(entryXML.getTaxas());
			entryModel.setTotalAssurance(entryXML.getAssurance());
			entryModel.setTotalDeliveryCost(entryXML.getDelvieryCost());
			//Fase 9 CILS 07Dic2016
			//entryModel.setWeight(entryXML.getWeight());
			if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
			{
				entryModel.setWeight(entryXML.getWeightPerTon());
			}
			else
			{
				entryModel.setWeight(entryXML.getWeight());
			}
			///FIN///////////////////////////////////////////////////////////////////////////////////////////


			newOrderEntries.add(entryModel);
		}

		orderModel.setSapCurrency(orderCurrency);

		orderModel.setEntries(newOrderEntries);

		//final OrderData orderData = orderConverter.convert(orderModel);
		this.getPriceOrderXMLCreate(orderModel, totalWeightAll, coldRolledTotalWeight, hotRolledTotalWeight, aluminizedTotalWeight,
				galvanizedTotalWeight, totalPriceAll, coldRolledTotalPrice, hotRolledTotalPrice, aluminizedTotalPrice,
				galvanizedTotalPrice, galvametalTotalPrice, galvametalTotalWeight, totalAssurance, totalDeliveryCost, totalTaxas,
				subtotalPrice);
		//neorisCartPriceCalculator.calculatePrices(orderData);

		//*neorisDefaultCommerceCheckoutService.saveSAPWeight(orderModel, orderData);

		//neorisDefaultCommerceCheckoutService.saveSAPPrices(orderModel, orderData);

		modelService.save(orderModel);

		final OrderData orderData = orderConverter.convert(orderModel);
		setGroupingOfOrderDataCreate(orderData);
		setFormattedSapDataCreate(orderData);

		if (baseStoreModel.getAPIFunctionaliatyEnabled() != null && baseStoreModel.getAPIFunctionaliatyEnabled().booleanValue())
		{
			validateNotMixedProductsForAPI(orderModel);
			setIsAPIOrder(orderModel);
		}

		if (baseStoreModel.getHSSFunctionaliatyEnabled() != null && baseStoreModel.getHSSFunctionaliatyEnabled().booleanValue())
		{
			validateNotMixedProductsForHSS(orderModel);
			setIsHSSOrder(orderModel);
		}

		modelService.save(orderModel);

		LOG.info("Finished create order with id: " + orderXML.getId());
	}

	private void validateNotMixedProductsForAPI(final OrderModel orderModel) throws Exception
	{
		boolean hasAPIProducts = false;
		boolean hasRegularProducts = false;
		for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
		{
			final ProlamsaProductModel productModel = (ProlamsaProductModel) eachEntryModel.getProduct();
			if (productModel.getIsAPI())
			{
				hasAPIProducts = true;
			}
			else
			{
				hasRegularProducts = true;
			}
		}
		if (hasAPIProducts && hasRegularProducts)
		{
			throw new Exception("API funcionality: Mixed products on Order: " + orderModel.getCode());
		}
	}

	private void validateNotMixedProductsForHSS(final OrderModel orderModel) throws Exception
	{
		boolean hasHSSProducts = false;
		boolean hasRegularProducts = false;
		for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
		{
			final ProlamsaProductModel productModel = (ProlamsaProductModel) eachEntryModel.getProduct();
			if (productModel.getIsHSS())
			{
				hasHSSProducts = true;
			}
			else
			{
				hasRegularProducts = true;
			}
		}
		if (hasHSSProducts && hasRegularProducts)
		{
			throw new Exception("HSS funcionality: Mixed products on Order: " + orderModel.getCode());
		}
	}

	public void setIsAPIOrder(final OrderModel orderModel)
	{
		boolean hasAPIProducts = false;
		for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
		{
			final ProlamsaProductModel productModel = (ProlamsaProductModel) eachEntryModel.getProduct();
			if (productModel.getIsAPI())
			{
				hasAPIProducts = true;
				break;
			}
		}
		if (hasAPIProducts)
		{
			orderModel.setIsAPIOrder(true);
			modelService.save(orderModel);
		}
	}

	public void setIsHSSOrder(final OrderModel orderModel)
	{
		boolean hasHSSProducts = false;
		for (final AbstractOrderEntryModel eachEntryModel : orderModel.getEntries())
		{
			final ProlamsaProductModel productModel = (ProlamsaProductModel) eachEntryModel.getProduct();
			if (productModel.getIsHSS())
			{
				hasHSSProducts = true;
				break;
			}
		}
		if (hasHSSProducts)
		{
			orderModel.setIsHSSOrder(true);
			modelService.save(orderModel);
		}
	}

	public void setGroupingOfOrderDataCreate(final AbstractOrderData orderData)
	{
		// order entry index for SAP
		int index = 0;
		//long transportationCapacityKilos = 0L;
		//System.out.println("Antes de transportationCapacityKilos");
		final Double transportationCapacityKilos = orderData.getTransportationMode().getMaxCapacity() * 1000;
		//System.out.println("Despues de transportationCapacityKilos");
		// transportation capacity

		/*
		 * if (orderData.getTransportationMode().getCapacity() != null) { transportationCapacityKilos =
		 * orderData.getTransportationMode().getCapacity() * 1000; } else { if
		 * (orderData.getTransportationMode().getCode().equalsIgnoreCase("01")) { transportationCapacityKilos = 80 * 1000;
		 * } else { transportationCapacityKilos = 20 * 1000; } }
		 */

		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		// used capacity
		Double usedCapacity = 0d;

		// transportation group
		int groupTransportation = 1;

		// entries on current group
		List<OrderEntryData> entriesOnCurrentGroup = new ArrayList<OrderEntryData>();

		// for each order entry
		for (final OrderEntryData orderEntry : orderData.getEntries())
		{
			// set group index
			orderEntry.setGroupNumber(groupTransportation);

			Double entryCapacity = 0.00d;

			// compute the order entry ton capacity, remember quantities are on bundles
			if (!orderEntry.getProduct().getIsAPI())
			{
				entryCapacity = (orderEntry.getProduct().getBundleKgEquiv()) * orderEntry.getQuantity();
			}
			else
			{
				entryCapacity = (orderEntry.getProduct().getFtKgEquiv()) * orderEntry.getQuantity();
			}
			// if the used capacity and entry is great that transportation capacity
			if (usedCapacity + entryCapacity >= transportationCapacityKilos)
			{
				// mark entry as the last on transportation group
				orderEntry.setIsLastOnTraspotation(true);

				// mark transportation group as full
				orderEntry.setIsTransportationGroupFull(true);

				// for each entry on the current group
				for (final OrderEntryData eachEntryOnCurrentGroup : entriesOnCurrentGroup)
				{
					// set the shipping date to the last date of the group
					eachEntryOnCurrentGroup.setReadyToShip(orderEntry.getReadyToShip());

					if (baseStoreModel != null)
					{
						if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
						{
							eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(orderEntry
									.getReadyToShip()));
						}
						else
						{
							eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(orderEntry
									.getReadyToShip()));
						}
					}
					else
					{
						eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(orderEntry
								.getReadyToShip()));
					}

					// mark each entry of the group as transportation group full
					eachEntryOnCurrentGroup.setIsTransportationGroupFull(true);
				}

				// reset the entries in current group
				entriesOnCurrentGroup = new ArrayList<OrderEntryData>();
				// increment the transportation group index
				groupTransportation++;
				// rest used capacity
				usedCapacity = 0d;
				// set the SAP entry for the order entry
				orderEntry.setSapEntryNumber(index);
				// increment the sap entry
				index++;
				// go back to the loop
				continue;
			}
			else
			{
				// include the order entry on the current group
				entriesOnCurrentGroup.add(orderEntry);
				// mark for now as not full
				orderEntry.setIsTransportationGroupFull(false);
				// mark for now as not last on transportation group
				orderEntry.setIsLastOnTraspotation(false);

				// if this is the last entry of the order
				if (index + 1 == orderData.getEntries().size())
				{
					// mark it as the last on transportation group
					orderEntry.setIsLastOnTraspotation(true);

					// for each entry on the current group
					for (final OrderEntryData eachEntryOnCurrentGroup : entriesOnCurrentGroup)
					{
						// set the shipping date to the last date of the group
						eachEntryOnCurrentGroup.setReadyToShip(orderEntry.getReadyToShip());
						if (orderEntry.getReadyToShip() != null)
						{
							if (baseStoreModel != null)
							{
								if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
								{
									eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(orderEntry
											.getReadyToShip()));
								}
								else
								{
									eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(orderEntry
											.getReadyToShip()));
								}
							}
							else
							{
								eachEntryOnCurrentGroup.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(orderEntry
										.getReadyToShip()));
							}

						}

					}

					// reset the entries in current group
					entriesOnCurrentGroup = new ArrayList<OrderEntryData>();
				}
			}

			// set the SAP entry for the order entry
			orderEntry.setSapEntryNumber(index);
			// increment the sap entry
			index++;

			// increment the used capacity
			usedCapacity += entryCapacity;
		}
	}

	public void setFormattedSapDataCreate(final AbstractOrderData orderData)
	{
		final String priceFormat = new String("$ %1$,.2f");
		final String pricePerFeetFormat = new String("$ %1$,.2f");
		final DecimalFormat weightFormatter = new DecimalFormat("###,###,###,###.000");

		if (orderData.getColdRolledWeight() != null)
		{
			orderData.setFormattedColdRolledWeight(weightFormatter.format(orderData.getColdRolledWeight()));
		}

		if (orderData.getHotRolledWeight() != null)
		{
			orderData.setFormattedHotRolledWeight(weightFormatter.format(orderData.getHotRolledWeight()));
		}

		if (orderData.getGalvanizedWeight() != null)
		{
			orderData.setFormattedGalvanizedWeight(weightFormatter.format(orderData.getGalvanizedWeight()));
		}

		if (orderData.getAluminizedWeight() != null)
		{
			orderData.setFormattedAluminizedWeight(weightFormatter.format(orderData.getAluminizedWeight()));
		}

		if (orderData.getTotalWeight() != null)
		{
			orderData.setFormattedTotalWeight(weightFormatter.format(orderData.getTotalWeight()));
		}


		if (orderData.getColdRolledPrice() != null)
		{
			orderData.setFormattedColdRolledPrice(String.format(priceFormat, orderData.getColdRolledPrice()));
		}

		if (orderData.getHotRolledPrice() != null)
		{
			orderData.setFormattedHotRolledPrice(String.format(priceFormat, orderData.getHotRolledPrice()));
		}

		if (orderData.getGalvanizedPrice() != null)
		{
			orderData.setFormattedGalvanizedPrice(String.format(priceFormat, orderData.getGalvanizedPrice()));
		}

		if (orderData.getGalvametalPrice() != null)
		{
			orderData.setFormattedGalvametalPrice(String.format(priceFormat, orderData.getGalvametalPrice()));
		}

		if (orderData.getAluminizedPrice() != null)
		{
			orderData.setFormattedAluminizedPrice(String.format(priceFormat, orderData.getAluminizedPrice()));
		}

		if (orderData.getSapTotalPrice() != null)
		{
			orderData.setFormattedSapTotalPrice(String.format(priceFormat, orderData.getSapTotalPrice()));
		}

		if (orderData.getSapSubtotalPrice() != null)
		{
			orderData.setFormattedSapSubtotalPrice(String.format(priceFormat, orderData.getSapSubtotalPrice()));
		}

		if (orderData.getTotalAssurance() != null)
		{
			orderData.setFormattedTotalAssurance(String.format(priceFormat, orderData.getTotalAssurance()));
		}

		if (orderData.getTotalDeliveryCost() != null)
		{
			orderData.setFormattedTotalDelvieryCost(String.format(priceFormat, orderData.getTotalDeliveryCost()));
		}

		if (orderData.getTotalTaxas() != null)
		{
			orderData.setFormattedTotalTaxas(String.format(priceFormat, orderData.getTotalTaxas()));
		}

		for (final OrderEntryData eachEntry : orderData.getEntries())
		{
			eachEntry.setFormattedPricePerFeet(String.format(pricePerFeetFormat, eachEntry.getPricePerFeet()));
			eachEntry.setFormattedNetPriceWOTaxes(String.format(priceFormat, eachEntry.getNetPriceWOTaxes()));
			eachEntry.setFormattedPricePerTon(String.format(pricePerFeetFormat, eachEntry.getPricePerTon()));

			eachEntry.setFormattedPricePerPc(String.format(priceFormat, eachEntry.getPricePerPc()));

			if (eachEntry.getReadyToShip() != null)
			{
				final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

				if (baseStoreModel != null)
				{
					if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
					{
						eachEntry.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(eachEntry.getReadyToShip()));
					}
					else
					{
						eachEntry.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(eachEntry.getReadyToShip()));
					}
				}
				else
				{
					eachEntry.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(eachEntry.getReadyToShip()));
				}
			}

			final HybrisEnumValue enumValue = eachEntry.getProduct().getLocation();
			final String formattedLocation = enumerationService.getEnumerationName(enumValue, i18nService.getCurrentLocale());

			eachEntry.setFormattedLocation(formattedLocation);


		}
	}


	public void getPriceOrderXML(final AbstractOrderData orderData, final Double totalWeightAll,
			final Double coldRolledTotalWeight, final Double hotRolledTotalWeight, final Double aluminizedTotalWeight,
			final Double galvanizedTotalWeight, final Double totalPriceAll, final Double coldRolledTotalPrice,
			final Double hotRolledTotalPrice, final Double aluminizedTotalPrice, final Double galvanizedTotalPrice,
			final Double galvametalTotalPrice, final Double galvametalTotalWeight, final Double totalAssurance,
			final Double totalDeliveryCost, final Double totalTaxas, final Double subtotal)
	{
		orderData.setHotRolledWeight(hotRolledTotalWeight);

		orderData.setColdRolledWeight(coldRolledTotalWeight);

		orderData.setGalvanizedWeight(galvanizedTotalWeight);

		orderData.setGalvametalWeight(galvametalTotalWeight);

		orderData.setAluminizedWeight(aluminizedTotalWeight);

		orderData.setTotalWeight(totalWeightAll);

		//set prices categories
		orderData.setHotRolledPrice(hotRolledTotalPrice);

		orderData.setColdRolledPrice(coldRolledTotalPrice);

		orderData.setGalvanizedPrice(galvanizedTotalPrice);

		orderData.setGalvametalPrice(galvametalTotalPrice);

		orderData.setAluminizedPrice(aluminizedTotalPrice);

		orderData.setSapTotalPrice(totalPriceAll);

		orderData.setTotalAssurance(totalAssurance);

		orderData.setTotalDeliveryCost(totalDeliveryCost);

		orderData.setTotalTaxas(totalTaxas);

		orderData.setSapSubtotalPrice(subtotal);

		//orderData.setTotalPrice(totalPriceAll);

	}

	public void getPriceOrderXMLCreate(final OrderModel orderModel, final Double totalWeightAll,
			final Double coldRolledTotalWeight, final Double hotRolledTotalWeight, final Double aluminizedTotalWeight,
			final Double galvanizedTotalWeight, final Double totalPriceAll, final Double coldRolledTotalPrice,
			final Double hotRolledTotalPrice, final Double aluminizedTotalPrice, final Double galvanizedTotalPrice,
			final Double galvametalTotalPrice, final Double galvametalTotalWeight, final Double totalAssurance,
			final Double totalDeliveryCost, final Double totalTaxas, final Double subtotal)
	{
		orderModel.setHotRolledWeight(hotRolledTotalWeight);

		orderModel.setColdRolledWeight(coldRolledTotalWeight);

		orderModel.setGalvanizedWeight(galvanizedTotalWeight);

		orderModel.setGalvametalWeight(galvametalTotalWeight);

		orderModel.setAluminizedWeight(aluminizedTotalWeight);

		orderModel.setTotalWeight(totalWeightAll);

		//set prices categories
		orderModel.setHotRolledPrice(hotRolledTotalPrice);

		orderModel.setColdRolledPrice(coldRolledTotalPrice);

		orderModel.setGalvanizedPrice(galvanizedTotalPrice);

		orderModel.setGalvametalPrice(galvametalTotalPrice);

		orderModel.setAluminizedPrice(aluminizedTotalPrice);

		orderModel.setSapTotalPrice(totalPriceAll);

		orderModel.setTotalPrice(totalPriceAll);

		orderModel.setTotalAssurance(totalAssurance);

		orderModel.setTotalDeliveryCost(totalDeliveryCost);

		orderModel.setTotalTaxas(totalTaxas);

		orderModel.setSapSubtotalPrice(subtotal);

	}


	public Map<String, String> getOrderEntryStatusMap()
	{
		return orderEntryStatusMap;
	}

	public void setOrderEntryStatusMap(final Map<String, String> orderEntryStatusMap)
	{
		this.orderEntryStatusMap = orderEntryStatusMap;
	}

	public Map<String, String> getOrderStatusMap()
	{
		return orderStatusMap;
	}

	public void setOrderStatusMap(final Map<String, String> orderStatusMap)
	{
		this.orderStatusMap = orderStatusMap;
	}
}
