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
package mx.neoris.storefront.controllers.misc;

import de.hybris.platform.b2bacceleratorfacades.product.data.ProductQuantityData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.services.NeorisCreditScoreCard;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractPageController;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.AddToCartForm;
import mx.neoris.storefront.forms.AddToCartOrderForm;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for Add to Cart functionality which is not specific to a certain
 * page.
 */
@Controller
@Scope("tenant")
public class AddToCartController extends AbstractPageController
{
	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";
	private static final String BASKET_QUANTITY_ERROR_KEY = "basket.error.quantity.invalid";
	private static final String BASKET_QUANTITY_MULTIPLE_ERROR_KEY = "basket.error.quantity.multiple.invalid";
	private static final String BASKET_QUANTITY_NOITEMADDED_ERROR_PREFIX_KEY = "basket.information.quantity.noItemsAdded.";
	private static final String BASKET_QUANTITY_REDUCED_NUMBER_PREFIX_KEY = "basket.information.quantity.reducedNumberOfItemsAdded.";

	protected static final Logger LOG = Logger.getLogger(AddToCartController.class);
	private static final Integer MINIMUM_SINGLE_SKU_ADD_CART = 0;
	private static final String SHOWN_PRODUCT_COUNT = "storefront.minicart.shownProductCount";
	
	@Resource
	CartService cartService;
	
	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name="neorisSapCreditScoreCard")
	private NeorisCreditScoreCard neorisSapCreditScoreCard;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "dateFormatter_MM-dd-yyyy")
	private SimpleDateFormat dateFormatter;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@InitBinder
	public void initBinder(final WebDataBinder binder)
	{
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
	}

	// NEORIS_CHANGE #64
	protected List<ProductData> addBatchToCart(ArrayNode batchProduct)
	{
		final List<ProductData> productDataList = new ArrayList<ProductData>();

		for (JsonNode eachNode : batchProduct)
		{
			String productCode = eachNode.get("productCode").asText();
			Double stockQty = eachNode.get("stockQty").asDouble();
			String rollingScheduleString = new String();
			Double rollingScheduleQty = 0d;
			
			String stockOuts = "";
			String stockQtyS = eachNode.get("stockQty").asText();
			//String stockOuts = eachNode.get("stockOuts").asText();
			
			if(stockQtyS.equalsIgnoreCase("0"))
			{
				stockOuts = "SO";
			}
			
			if(eachNode.get("rollingScheduleDate") != null)
				rollingScheduleString = eachNode.get("rollingScheduleDate").asText();
			
			if(eachNode.get("rollingScheduleQty") != null)
				rollingScheduleQty = eachNode.get("rollingScheduleQty").asDouble();

			ProlamsaAPIProductConfigurationModel configModel = neorisFacade.getProductConfigurationModelFromSessionWithCode(productCode);

			try
			{
				CartModificationData cartModification;

				if (stockQty != 0)
				{
					cartModification = neorisCartFacade.addToCart(productCode, stockQty, configModel, null, getUnit(), stockOuts);
					if (null != cartModification)
						productDataList.add(cartModification.getEntry().getProduct());
				}

				if (rollingScheduleQty != 0)
				{
					Date rollingScheduleDate = dateFormatter.parse(rollingScheduleString);
					cartModification = neorisCartFacade.addToCart(productCode, rollingScheduleQty, configModel, rollingScheduleDate, getUnit(), stockOuts);
					if (null != cartModification)
						productDataList.add(cartModification.getEntry().getProduct());
				}

			}
			catch (Exception ex)
			{
				LOG.error("error while processing cart", ex);
			}
		}

		return productDataList;
	}

	@RequestMapping(value = "/cart/addBatch", method = RequestMethod.POST, produces = "application/json")
	public String addBatchToCart(final Model model, final HttpServletRequest request)
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory();
		JsonNode batchData = null;
		try
		{
			JsonParser jp = factory.createJsonParser(request.getParameter("jsonObject"));
			batchData = mapper.readTree(jp);
		}
		catch (Exception ex)
		{
			LOG.error("error while parsing batch data", ex);
		}

		Boolean clearCart = batchData.get("clearCurrentCart").asBoolean();

		if (clearCart)
		{
			CartModel currentCart = neorisFacade.getCurrentCart();

			if (currentCart != null)
			{
				try
				{
					neorisFacade.emptyCartFromSession(currentCart);
				}
				catch (Exception ex)
				{
					LOG.error("error while clearing cart", ex);
				}
			}
		}

		final List<ProductData> productDataList = new ArrayList<ProductData>();

		productDataList.addAll(addBatchToCart((ArrayNode) batchData.get("entries")));

		neorisProductFacade.injectCustomerNameAndDescriptionDataOn(productDataList);

		model.addAttribute("products", productDataList);
		
		//Se obtiene el cartModel para guardar los datos del semaforo y scorecard
		CartModel cartModel = cartService.getSessionCart();

		CartData cartData = neorisCartFacade.getSessionCartWithEntryOrdering(true);
		
		//NEORIS_CAHNGE#Credit Score Card: LCC
		//get the Credit Score Card
		try {
			neorisSapCreditScoreCard.calculateCreditScoreCard(cartData);
			
			Integer scoreLowLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardLowLimit();
			Integer scoreHighLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardHighLimit();
			
			model.addAttribute("creditScoreCard", cartData.getCreditScoreCard());
			model.addAttribute("semaphoreCredit", cartData.getSemaphoreCredit());
			
			cartModel.setCreditScoreCard(cartData.getCreditScoreCard());
			cartModel.setSemaphoreCredit(cartData.getSemaphoreCredit());
			modelService.save(cartModel);
			
			if (scoreLowLimit!=null && scoreHighLimit!=null && scoreHighLimit > scoreLowLimit) {
				model.addAttribute("scoreLowLimit", scoreLowLimit);
				model.addAttribute("scoreHighLimit", scoreHighLimit);
			}  else {
				model.addAttribute("scoreLowLimit", 0);
				model.addAttribute("scoreHighLimit", 0);
			}
			
		} catch (Exception e) {
			LOG.error("Error AddToCartController: CalculateCreditScoreCard: error: " + e.getMessage());
			model.addAttribute("creditScoreCard", 0.0);
			model.addAttribute("scoreLowLimit", 0);
			model.addAttribute("scoreHighLimit", 0);
		}
		
		neorisProductFacade.injectCustomerNameAndDescriptionDataOnOrderEntries(cartData.getEntries());
		
		List<OrderEntryData> entries = new ArrayList<OrderEntryData>();
		for (int i = 0; i < productDataList.size(); i++)
		{
			entries.add(cartData.getEntries().get(i));
		}

		model.addAttribute("entries", entries);
		
		//added to set cart ownership
		neorisCartFacade.setCartOwnership(getRootUnit());

		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	@RequestMapping(value = "/cart/add", method = RequestMethod.POST, produces = "application/json")
	public String addToCart(@RequestParam("productCodePost")
	final String code, final Model model, @Valid
	final AddToCartForm form, final BindingResult bindingErrors)
	{
		ProductData product = null;

		if (bindingErrors.hasErrors())
		{
			return getViewWithBindingErrorMessages(model, bindingErrors);
		}

		final ProductQuantityData productQuantity = new ProductQuantityData();
		productQuantity.setQuantity(form.getQty());
		productQuantity.setSku(code);

		if (productQuantity.getQuantity() > MINIMUM_SINGLE_SKU_ADD_CART)
		{
			product = addToCart(model, productQuantity);
		}
		else
		{
			model.addAttribute(ERROR_MSG_TYPE, BASKET_QUANTITY_ERROR_KEY);
		}

		final List<ProductData> productDataList = new ArrayList<ProductData>();

		if (product != null)
		{
			productDataList.add(product);
		}

		model.addAttribute("products", productDataList);
		
		//added to set cart ownership
		neorisCartFacade.setCartOwnership(getRootUnit());

		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	@RequestMapping(value = "/cart/addGrid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final String addGridToCart(@RequestBody
	final AddToCartOrderForm form, final Model model)
	{
		addToCart(model, form.getProductQuantities());
		
		//added to set cart ownership
		neorisCartFacade.setCartOwnership(getRootUnit());

		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected ProductData addToCart(final Model model, final ProductQuantityData productQuantity)
	{
		ProductData product = null;

		try
		{
			final CartModificationData cartModification = cartFacade.addToCart(productQuantity.getSku(), productQuantity.getQuantity());

			product = cartModification.getEntry().getProduct();
			model.addAttribute("entry", cartModification.getEntry());
			model.addAttribute("numberShowing", Config.getInt(SHOWN_PRODUCT_COUNT, 3));

			if (cartModification.getQuantityAdded() <= 0L)
			{
				if (!model.containsAttribute(ERROR_MSG_TYPE))
				{
					final String msg = BASKET_QUANTITY_NOITEMADDED_ERROR_PREFIX_KEY + cartModification.getStatusCode();
					model.addAttribute(ERROR_MSG_TYPE, msg);
					GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, msg, new Object[] { product.getName() });
				}
			}
			else
				if (cartModification.getQuantityAdded() < productQuantity.getQuantity())
				{
					if (!model.containsAttribute(ERROR_MSG_TYPE))
					{
						final String msg = BASKET_QUANTITY_REDUCED_NUMBER_PREFIX_KEY + cartModification.getStatusCode();
						model.addAttribute(ERROR_MSG_TYPE, msg);
						GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, msg, new Object[] { product.getName() });
					}

				}
		}
		catch (final CommerceCartModificationException ex)
		{
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "basket.error.occurred", null);
		}

		return product;

	}

	protected void addToCart(final Model model, final List<ProductQuantityData> productQuantities)
	{
		int index = 0;
		final List<ProductData> productDataList = new ArrayList<ProductData>();

		for (final ProductQuantityData productQuantity : productQuantities)
		{
			if (productQuantity.getQuantity() != null)
			{
				// Ignore all skus with
				if (productQuantity.getQuantity() > MINIMUM_SINGLE_SKU_ADD_CART)
				{
					final ProductData product = addToCart(model, productQuantity);
					if (product != null)
					{
						productDataList.add(product);
					}
				}
				else
					if (productQuantity.getQuantity() < MINIMUM_SINGLE_SKU_ADD_CART)
					{
						model.addAttribute(ERROR_MSG_TYPE, BASKET_QUANTITY_MULTIPLE_ERROR_KEY);
						GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, BASKET_QUANTITY_MULTIPLE_ERROR_KEY, null);
					}

			}

			if (productQuantity.getQuantity() == null || productQuantity.getQuantity() == MINIMUM_SINGLE_SKU_ADD_CART)
			{
				index++;
			}

		}

		if (index == productQuantities.size())
		{
			model.addAttribute(ERROR_MSG_TYPE, BASKET_QUANTITY_ERROR_KEY);
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, BASKET_QUANTITY_ERROR_KEY, null);
		}

		model.addAttribute("products", productDataList);
	}

	protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (isTypeMismatchError(error))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected boolean isTypeMismatchError(final ObjectError error)
	{
		return error.getCode().equals(TYPE_MISMATCH_ERROR_CODE);
	}
}
