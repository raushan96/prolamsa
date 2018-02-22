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
package mx.neoris.facades.flow.impl;

import de.hybris.platform.b2b.model.B2BCommentModel;
import de.hybris.platform.b2b.model.B2BCostCenterModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BCostCenterData;
import de.hybris.platform.b2bacceleratorfacades.order.impl.DefaultB2BCheckoutFacade;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import mx.neoris.core.cart.CartNoPriceDataHelper;
import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.cart.NeorisCartPriceHelper;
import mx.neoris.core.checkout.flow.B2BCheckoutFlowStrategy;
import mx.neoris.core.checkout.pci.B2BCheckoutPciStrategy;
import mx.neoris.core.enums.B2BCheckoutFlowEnum;
import mx.neoris.core.enums.B2BCheckoutPciOptionEnum;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.event.QuoteRequestedEvent;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.NeorisCommerceCheckoutService;
import mx.neoris.core.updaters.NeorisTermsAndConditionsCheckedB2BCustomerUpdater;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.flow.B2BCheckoutFlowFacade;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.multipart.MultipartFile;

/**
 * Default implementation of the {@link B2BCheckoutFlowFacade}. Delegates
 * resolving the checkout flow to an injected {@link B2BCheckoutFlowStrategy}.
 * 
 * @since 4.6
 * @spring.bean checkoutFlowFacade
 */
public class DefaultB2BCheckoutFlowFacade extends DefaultB2BCheckoutFacade implements B2BCheckoutFlowFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultB2BCheckoutFlowFacade.class);

	private static final String PROLAMSA_MEDIA_CATALOG = "prolamsaMediaCatalog";
	private static final String ONLINE = "Online";

	private B2BCheckoutFlowStrategy checkoutFlowStrategy;
	private B2BCheckoutPciStrategy b2BCheckoutPciStrategy;
	
	@Resource(name = "neorisTermsAndConditionsCheckedB2BCustomerUpdater")
	private NeorisTermsAndConditionsCheckedB2BCustomerUpdater neorisTermsAndConditionsCheckedB2BCustomerUpdater;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	// NEORIS_CHANGE #74
	@Resource(name = "neorisDefaultCommerceCheckoutService")
	private NeorisCommerceCheckoutService neorisDefaultCommerceCheckoutService;

	// NEORIS_CHANGE #74
	@Resource(name = "neorisCartPriceCalculator")
	private NeorisCartPriceCalculator neorisCartPriceCalculator;

	// NEORIS_CHANGE #48
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	// NEORIS_CHANGE #74
	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;

	@Resource(name = "neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrdersCreator;

	@Resource(name = "neorisCartPriceHelper")
	private NeorisCartPriceHelper neorisCartPriceHelper;
	
	@Resource(name="cartNoPriceEntries")
	private CartNoPriceDataHelper cartNoPriceEntries;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource
	private MediaService mediaService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;
	
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Override
	public List<B2BCostCenterData> getActiveVisibleCostCenters()
	{
		// NEORIS_CHANGE #48
		// Get the costCenters from the current selected root unit and current
		// currency
		@SuppressWarnings("unchecked")
		final Collection<B2BCostCenterModel> costCenters = CollectionUtils.select(getB2bCostCenterService().getCostCentersForUnitBranch(neorisFacade.getRootUnit(), neorisFacade.getCurrentCurrency()), new Predicate()
		{
			@Override
			public boolean evaluate(final Object object)
			{
				return ((B2BCostCenterModel) object).getActive().booleanValue();
			}
		});

		return Converters.convertAll(costCenters, getB2bCostCenterConverter());
	}

	// NEORIS_CHANGE #74
	@Override
	public List<? extends AddressData> getDeliveryAdrressesFor(B2BUnitModel b2bUnitModel)
	{
		Collection<AddressModel> addresses = b2bUnitModel.getAddresses();

		List<AddressData> deliveryAddresses = new ArrayList<AddressData>();

		for (AddressModel eachAddressModel : addresses)
		{
			if (eachAddressModel.getShippingAddress())
				if(eachAddressModel.getBaseStores().contains(neorisFacade.getCurrentBaseStore()))
					deliveryAddresses.add(getAddressConverter().convert(eachAddressModel));
		}

		return deliveryAddresses;
	}
	
	//NEORIS_CHANGE	
	@Override
	public List<? extends AddressData> getBillingAdrressesFor(
			B2BUnitModel b2bUnitModel) {
		// YTODO Auto-generated method stub
		Collection<AddressModel> addresses = b2bUnitModel.getAddresses();

		List<AddressData> billingAddresses = new ArrayList<AddressData>();

		for (AddressModel eachAddressModel : addresses)
		{
			if (eachAddressModel.getBillingAddress())
				if(eachAddressModel.getBaseStores().contains(neorisFacade.getCurrentBaseStore()))
					billingAddresses.add(getAddressConverter().convert(eachAddressModel));
		}

		return billingAddresses;
	}
	
	
	

	// NEORIS_CHANGE #74
	@Override
	public CartData getCheckoutCart()
	{
		final CartData cartData = neorisCartFacade.getSessionCart(); 
		if (cartData != null)
		{
			cartData.setDeliveryAddress(getDeliveryAddress());
			cartData.setDeliveryMode(getDeliveryMode());
			cartData.setPaymentInfo(getPaymentDetails());	
			cartData.setTPago(getPaymentTerms());			
		}

		return cartData;
	}

	@Override
	public PaymentTermsType getPaymentTerms()
	{
		final CartModel cart = getCart();
		if (cart != null)
		{
			return cart.getTPago();
		}

		return null;
	}
	
	// NEORIS_CHANGE #74
	public boolean setDeliveryAddressIfAvailableFor()
	{
		final CartModel cartModel = getCart();
		
		// verify if cart already has a delivery address selected
		if (cartModel.getDeliveryAddress() != null)
			return false;
		
		// try to load delivery address from default settings
		final NeorisB2BCustomerDefaultSettingModel defaultSettings = neorisFacade.getDefaultSettings();
		if (defaultSettings != null && defaultSettings.getShippingAddress() != null)
		{
			return getCommerceCheckoutService().setDeliveryAddress(cartModel, defaultSettings.getShippingAddress()); 
		}
		
		// try to load delivery address by selecting action
		B2BUnitModel b2bUnitModel = neorisFacade.getRootUnit();

		Collection<AddressModel> addresses = b2bUnitModel.getAddresses();

		List<AddressData> deliveryAddresses = new ArrayList<AddressData>();

		for (AddressModel eachAddressModel : addresses)
		{
			if (eachAddressModel.getShippingAddress())
				deliveryAddresses.add(getAddressConverter().convert(eachAddressModel));
		}

		// if the list of delivery address is bigger than one the user need to
		// select the address
		if (deliveryAddresses.size() == 0 || deliveryAddresses.size() > 1)
			return false;
		
		// else select the unique one
		for (AddressModel eachAddressModel : addresses)
		{
			if (deliveryAddresses.get(0).getCode().equals(eachAddressModel.getCode()))
				return getCommerceCheckoutService().setDeliveryAddress(cartModel, eachAddressModel);
		}

		return false;
	}
	
	@Override
	public boolean setTransportationModelIfAvailableFor() 
	{
		final CartModel cartModel = getCart();
		
		// verify if cart already has a transportation mode selected
		if (cartModel.getTransportationMode() != null)
			return false;
		
		// try to load delivery address from default settings
		final NeorisB2BCustomerDefaultSettingModel defaultSettings = neorisFacade.getDefaultSettings();
		if (defaultSettings != null && defaultSettings.getTransportationMode() != null)
		{
			return setTransportationMode(defaultSettings.getTransportationMode());
		}
		
		return false;
	}

	// NEORIS_CHANGE #74
	@Override
	public boolean setDeliveryModeIfAvailable()
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final Set<DeliveryModeModel> deliveryModeModels = getBaseStoreService().getCurrentBaseStore().getDeliveryModes();

			// It's supposed that there will be only one delivery mode for
			// baseStore
			final Iterator<DeliveryModeModel> deliveryModesIterator = deliveryModeModels.iterator();

			DeliveryModeModel deliveryModeModel = null;
			if (deliveryModesIterator.hasNext())
			{
				deliveryModeModel = deliveryModeModels.iterator().next();
			}

			if (null != deliveryModeModel)
				return getCommerceCheckoutService().setDeliveryMode(cartModel, deliveryModeModel);

		}
		return false;
	}
	

	// NEORIS_CHANGE #74
	@Override
	public boolean calculateCart(CartData cartData) throws Exception
	{

		if (cartData != null)
		{
			neorisCartPriceCalculator.calculatePrices(cartData);
			
			//helper will iterate cartData and fetch the list of items which have no price data.
			cartData.setNoPriceEntries(cartNoPriceEntries.getNoPriceCartEntries(cartData));
			//Change to remove gruopind data and keep the order en in the entries
			//neorisCartPriceHelper.sortEntriesByReadyToShipDate(cartData);
			//neorisCartPriceHelper.setGroupingOfOrderData(cartData);
			neorisCartPriceHelper.setFormattedSapData(cartData);
			
			neorisCartPriceHelper.setFormattedEntriesQuantity(cartData);

			return true;
		}
		return false; 

	}

	// NEORIS_CHANGE #74
	@Override
	public boolean setBillingAddressIfAvailable()
	{
		final CartModel cartModel = getCart();
		final B2BUnitModel rootUnitModel = neorisFacade.getRootUnit();

		if (cartModel != null)
		{
			AddressModel addressModel = neorisFacade.getBillingAddress(rootUnitModel);

			if (addressModel != null)
			{
				neorisDefaultCommerceCheckoutService.setBillingAddress(cartModel, addressModel);
				getModelService().save(cartModel);
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	@Override
	public boolean setPaymentTerms()
	{
		final CartModel cartModel = getCart();
		final B2BUnitModel rootUnitModel = neorisFacade.getRootUnit();

		if (cartModel != null && rootUnitModel != null)
		{
			
			PaymentTermsType tpago = null;
			//Get TPAGO Cart Store
			if(cartModel.getStore()!=null)
			{
				if(neorisFacade.getTPagoFor(rootUnitModel, cartModel.getStore()) != null)
					tpago = neorisFacade.getTPagoFor(rootUnitModel, cartModel.getStore());
				else
					LOG.warn("No TPago for: "+rootUnitModel.getUid()+" and store cart"+cartModel.getStore().getUid());
			}
			else if(baseStoreService.getCurrentBaseStore()!=null)
			{
				//Getting TPAGO for current Store
				if(neorisFacade.getTPagoFor(rootUnitModel, baseStoreService.getCurrentBaseStore())!=null)
					tpago=neorisFacade.getTPagoFor(rootUnitModel, baseStoreService.getCurrentBaseStore());
				else
					LOG.warn("No TPago for: "+rootUnitModel.getUid()+" and current store: "+ baseStoreService.getCurrentBaseStore());
			}
			
			if(tpago == null)
			{
				//Getting default TPAGO
				tpago=PaymentTermsType.Z000;
				LOG.warn("Setting Default TPago for: "+rootUnitModel.getUid()+" and current store: "+baseStoreService.getCurrentBaseStore());
			}
			
			cartModel.setTPago(tpago);
			getModelService().save(cartModel);
			return true;	
		
		} else 
		{
			return false;
		}//neorisFacade.getRootUnit().getTPago();						
	}
	


	@Override
	public OrderData placeOrderWith(MultipartFile multipartFile) throws InvalidCartException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getCurrentUserForCheckout();
			if (cartModel.getUser().equals(currentUser) || getCheckoutCustomerStrategy().isAnonymousCheckout())
			{
				if(cartModel.isCartProcessingOrderOrQuote())
				{
					throw new InvalidCartException("Cart proccessing to order/quote");
				}
				
				cartModel.setCartProcessingOrderOrQuote(true);
				getModelService().save(cartModel);
				
				try{
					
					beforePlaceOrder(cartModel);
	
					OrderModel orderModel = placeOrder(cartModel);
					
					orderModel.setTotalPrice(cartModel.getTotalPrice());
					
					orderModel.setIsHSSOrder(cartModel.getHasHSSProducts());
					
					getModelService().save(orderModel);
			
					afterPlaceOrder(cartModel, orderModel);
	
	//				saveDocumentInOrder(multipartFile, orderModel);	
					
					orderModel.setStatus(OrderStatus.IN_PROCESS);
					orderModel.setTPago(cartModel.getTPago());
					
					// Convert the order to an order data
					if (orderModel != null)
					{
						return getOrderConverter().convert(orderModel);
					}
				}catch (Exception e)
				{
					cartModel.setCartProcessingOrderOrQuote(false);
					getModelService().save(cartModel);
				}
			}
		}

		return null;
	}

	protected void saveDocumentInOrder(final MultipartFile multipartFile, final OrderModel orderModel)
	{
		// if no container defined, exit
		if (multipartFile == null)
			return;

		try
		{
			// retrieve the file content
			InputStream stream = multipartFile.getInputStream();

			if (stream == null)
				return;

			NeorisMediaModel model = new NeorisMediaModel();					
								
			model.setCode(orderModel.getCode());
			model.setCreationtime(new Date());

			CatalogVersionModel catalog = catalogVersionService.getCatalogVersion(PROLAMSA_MEDIA_CATALOG, ONLINE);
			model.setCatalogVersion(catalog);

			getModelService().save(model);

			mediaService.setStreamForMedia(model, stream, multipartFile.getOriginalFilename(), multipartFile.getContentType());

			IOUtils.closeQuietly(stream);

			// set the blob container to the order
			orderModel.setAttachedPO(model);
			// save the order
			getModelService().save(orderModel);
		}
		catch (Exception ex)
		{
			LOG.error("error while saving file", ex);
		}
	}

	/*
	 * (non-Javadoc) NEORIS_CHANGE #74 Added to set the transportation mode to
	 * the order.
	 * 
	 * @see
	 * mx.neoris.facades.flow.B2BCheckoutFlowFacade#setTransportationMode(mx
	 * .neoris.core.model.TransportationModeModel)
	 */
	@Override
	public boolean setTransportationMode(TransportationModeModel transportationModeModel)
	{
		final CartModel cartModel = getCart();

		if (cartModel != null)
		{
			cartModel.setTransportationMode(transportationModeModel);

			getModelService().save(cartModel);

			return true;
		}
		return false;
	}
	
	@Override
	public boolean setRequestedDeliveryDate(Date requestedDeliveryDate)
	{
		final CartModel cartModel = getCart();
//		LOG.info("En DefaultB2BCheckoutFlowFacade setRequestedDeliveryDate");
		if (cartModel != null)
		{
//			LOG.info("-Dentro de if- En DefaultB2BCheckoutFlowFacade setRequestedDeliveryDate");
			cartModel.setRequestedDeliveryDate(requestedDeliveryDate);
//			LOG.info("-requestedDeliveryDate- En DefaultB2BCheckoutFlowFacade setRequestedDeliveryDate " + requestedDeliveryDate);

			getModelService().save(cartModel);
//			LOG.info("-Se guardo y regresa true- En DefaultB2BCheckoutFlowFacade setRequestedDeliveryDate");

			return true;
		}
		return false;
	}

	protected void beforePlaceOrder(final CartModel cartModel)
	{
		super.beforePlaceOrder(cartModel);

		final B2BUnitModel rootUnitModel = neorisFacade.getRootUnit();

		// NEORIS_CHANGE #74 Set the b2bunit to the cart, so it does not take
		// the b2bunit from the customer parentUnit
		if (cartModel != null)
		{
			cartModel.setUnit(rootUnitModel);
		}
	}

	protected OrderModel placeOrder(final CartModel cartModel) throws InvalidCartException
	{
		//NEORIS_CHANGE
		String sapLabelWeightUnit = sessionService.getCurrentSession().getAttribute("sapLabelWeightUnit");
		String sapWeightUnit = sessionService.getCurrentSession().getAttribute("sapWeightUnit");
		String sapCurrency = sessionService.getCurrentSession().getAttribute("sapCurrency");
				
		cartModel.setSapLabelWeightUnit(sapLabelWeightUnit);
		cartModel.setSapCurrency(sapCurrency);
		cartModel.setSapWeightUnit(sapWeightUnit);
				
		return getCommerceCheckoutService().placeOrder(cartModel);
	}

	protected void afterPlaceOrder(final CartModel cartModel, final OrderModel orderModel)
	{
		if (orderModel != null)
		{
			try
			{
				final boolean isQuoteOrder = !cartModel.getB2bcomments().isEmpty();
				if (isQuoteOrder)
				{
					orderModel.setStatus(OrderStatus.QUOTE_PENDING_SAP_ID);
					getModelService().save(orderModel);
					
					final QuoteRequestedEvent event = new QuoteRequestedEvent(orderModel);
					event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
					event.setSite(getBaseSiteService().getCurrentBaseSite());
					event.setCustomer(getCurrentUserForCheckout());
					event.setLanguage(getCommonI18NService().getCurrentLanguage());
					event.setCurrency(getCommonI18NService().getCurrentCurrency());
					getEventService().publishEvent(event);
				}

				// Remove cart
				getCartService().removeSessionCart();
				getModelService().refresh(orderModel);
				// clean previous card because it can be restored after next
				// login
				getCartFacade().cleanSavedCart();
				
				neorisTermsAndConditionsCheckedB2BCustomerUpdater.updateFor((B2BCustomerModel)(getCurrentUserForCheckout()), getBaseStoreService().getCurrentBaseStore());

			}
			catch (Exception e)
			{
				LOG.error("error while afterPlaceOrder", e);
			}
		}
	}

	@Override
	public B2BCheckoutFlowEnum getCheckoutFlow()
	{
		return getCheckoutFlowStrategy().getCheckoutFlow();
	}

	@Override
	public B2BCheckoutPciOptionEnum getSubscriptionPciOption()
	{
		return getCheckoutPciStrategy().getSubscriptionPciOption();
	}

	protected B2BCheckoutFlowStrategy getCheckoutFlowStrategy()
	{
		return checkoutFlowStrategy;
	}

	@Required
	public void setCheckoutFlowStrategy(final B2BCheckoutFlowStrategy strategy)
	{
		this.checkoutFlowStrategy = strategy;
	}

	protected B2BCheckoutPciStrategy getCheckoutPciStrategy()
	{
		return this.b2BCheckoutPciStrategy;
	}

	@Required
	public void setCheckoutPciStrategy(final B2BCheckoutPciStrategy strategy)
	{
		this.b2BCheckoutPciStrategy = strategy;
	}

	@Override
	public void setShippingInstructions(String shippingInstructions) 
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			cartModel.setShippingInstructions(shippingInstructions);
			getModelService().save(cartModel);
		}
	}

	@Override
	public void setQuoteNegotiablePrices(String negotiablePrices) 
	{
		Map<Integer, Double> map = processQuoteNegotiablePrices(negotiablePrices);
		
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			List<AbstractOrderEntryModel> cartEntries = cartModel.getEntries();
			
			for (AbstractOrderEntryModel entry : cartEntries)
			{
				Integer entryNumber = entry.getEntryNumber();
				if (map.containsKey(entryNumber))
				{
					Double negotiablePrice = map.get(entryNumber);
					
					entry.setIsAvailableToNegotiatePrice(Boolean.TRUE);
					entry.setNegotiablePrice(negotiablePrice);
				}
			}
			
			getModelService().save(cartModel);
		}
	}
	
	private Map<Integer, Double> processQuoteNegotiablePrices(String negotiablePrices)
	{
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory();
		JsonNode negotiablePricesData = null;
		
		try
		{
			JsonParser jp = factory.createJsonParser(negotiablePrices);
			negotiablePricesData = mapper.readTree(jp);
		}
		catch (Exception e)
		{
			LOG.error("Error while parsing negotiable prices data: " + e.getMessage());
		}

		ArrayNode negotiablePricesArrayNode = (ArrayNode) negotiablePricesData.get("negotiablePrices");
		
		for (JsonNode eachNode : negotiablePricesArrayNode)
		{
			Integer entryNumber = eachNode.get("entryNumber").asInt();
			Double negotiablePrice = eachNode.get("negPrice").asDouble();
			
			map.put(entryNumber, negotiablePrice);
		}
		
		return map;
	}

	@Override
	public CartData addAttachment(MultipartFile multipartFile) throws IOException 
	{
		final CartModel cartModel = getCart();
		
		if (cartModel != null)
		{
			InputStream stream = multipartFile.getInputStream();

			if (stream == null)
				throw new IOException();

			NeorisMediaModel newNeorisMediaModel = new NeorisMediaModel();
			newNeorisMediaModel.setCode(UUID.randomUUID().toString());
			newNeorisMediaModel.setCreationtime(new Date());

			CatalogVersionModel catalog = catalogVersionService
					.getCatalogVersion(PROLAMSA_MEDIA_CATALOG, ONLINE);
			newNeorisMediaModel.setCatalogVersion(catalog);

			getModelService().save(newNeorisMediaModel);
			mediaService.setStreamForMedia(newNeorisMediaModel, stream,
					multipartFile.getOriginalFilename(),
					multipartFile.getContentType());

			IOUtils.closeQuietly(stream);

			List<NeorisMediaModel> attachments = cartModel.getAttachmentsPO();
			List<NeorisMediaModel> newAttachments = new ArrayList<NeorisMediaModel>();
			
			for(NeorisMediaModel neorisMediaModel : attachments)
			{
				newAttachments.add(neorisMediaModel);
			}

			newAttachments.add(newNeorisMediaModel);
			
			cartModel.setAttachmentsPO(newAttachments);

			getModelService().save(cartModel);
		}
		
		return getCheckoutCart();
	}

	@Override
	public CartData deleteAttachment(String mediaCode) throws Exception 
	{
		final CartModel cartModel = getCart();
		
		if (cartModel != null)
		{
			List<NeorisMediaModel> attachments = cartModel.getAttachmentsPO();
			List<NeorisMediaModel> newAttachments = new ArrayList<NeorisMediaModel>();
			NeorisMediaModel neorisMediaModelToDelete = null; 
			
			for(NeorisMediaModel neorisMediaModel : attachments)
			{
				if (mediaCode.equals(neorisMediaModel.getCode()))
				{
					neorisMediaModelToDelete = neorisMediaModel;
				}
				else
				{
					newAttachments.add(neorisMediaModel);
				}
			}
			
			getModelService().remove(neorisMediaModelToDelete);
			
			cartModel.setAttachmentsPO(newAttachments);

			getModelService().save(cartModel);
		}
		
		return getCheckoutCart();
	}

	@Override
	public NeorisMediaModel getAttachment(String mediaCode) throws Exception 
	{
		final CartModel cartModel = getCart();
		
		if (cartModel != null)
		{
			List<NeorisMediaModel> attachments = cartModel.getAttachmentsPO();
			
			for(NeorisMediaModel neorisMediaModel : attachments)
			{
				if (mediaCode.equals(neorisMediaModel.getCode()))
				{
					return neorisMediaModel;
				}
			}
		}
		
		return null;
	}
	

	@Override
	public void setIsAPIOrder() 
	{
		final CartModel cartModel = getCart();
		cartModel.setIsAPIOrder(cartModel.getHasAPIProducts());
		
		getModelService().save(cartModel);
	}
}
