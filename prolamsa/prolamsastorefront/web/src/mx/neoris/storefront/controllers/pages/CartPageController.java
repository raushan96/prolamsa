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
package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.CartRestorationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import mx.neoris.core.services.NeorisCreditScoreCard;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.flow.impl.SessionOverrideB2BCheckoutFlowFacade;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.constants.WebConstants;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.UpdateQuantityForm;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for cart page
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/cart")
public class CartPageController extends AbstractPageController
{
	protected static final Logger LOG = Logger.getLogger(CartPageController.class);

	private static final String CART_CMS_PAGE = "cartPage";

	private static final String CONTINUE_URL = "continueUrl";

    private SimpleDateFormat DATE_PARSER = new SimpleDateFormat("MM-dd-yyyy");

    @Resource(name = "neorisSapCreditScoreCard")
	private NeorisCreditScoreCard neorisSapCreditScoreCard;
    
	// NEORIS_CHANGE #74
	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder resourceBreadcrumbBuilder;
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@RequestMapping(method = RequestMethod.GET)
	public String showCart(final Model model) throws CMSItemNotFoundException
	{
		prepareDataForPage(model);
		return ControllerConstants.Views.Pages.Cart.CartPage;
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	@RequireHardLogIn
	public String cartCheck(final Model model, final RedirectAttributes redirectModel) throws CommerceCartModificationException
	{
		SessionOverrideB2BCheckoutFlowFacade.resetSessionOverrides();

		if (!cartFacade.hasSessionCart() || cartFacade.getSessionCart().getEntries().isEmpty())
		{
			LOG.info("Missing or empty cart");

			// No session cart or empty session cart. Bounce back to the cart page.
			return REDIRECT_PREFIX + "/cart";
		}
		if (validateCart(redirectModel))
		{
			return REDIRECT_PREFIX + "/cart";
		}

		// Redirect to the start of the checkout flow to begin the checkout process
		// We just redirect to the generic '/checkout' page which will actually select the checkout flow
		// to use. The customer is not necessarily logged in on this request, but will be forced to login
		// when they arrive on the '/checkout' page.
		return REDIRECT_PREFIX + "/checkout";
	}

	protected boolean validateCart(final RedirectAttributes redirectModel) throws CommerceCartModificationException
	{
		// Validate the cart
		final List<CartModificationData> modifications = cartFacade.validateCartData();
		if (!modifications.isEmpty())
		{
			redirectModel.addFlashAttribute("validationData", modifications);

			// Invalid cart. Bounce back to the cart page.
			return true;
		}
		return false;
	}
	
	//NEORIS_CHANGE #67 Added method to update cart
	@RequestMapping(value = "/updateBatch", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@RequireHardLogIn
	public CartData addBatchToCart(final Model model, final HttpServletRequest request)
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

		ArrayNode entries = (ArrayNode) batchData.get("entries");

		List<JsonNode> updatedEntries = new ArrayList<JsonNode>();
		for (JsonNode eachNode : entries)
			updatedEntries.add(eachNode);

		for (JsonNode eachNode : entries)
		{
			Long cartEntryNumber = eachNode.get("orderEntryNumber").asLong();
			Double stockQty = eachNode.get("stockQty").asDouble();
			
			try
			{
				final CartModificationData cartModification = neorisCartFacade.updateCartEntry(cartEntryNumber, stockQty);
				
				// update entries entry number if required
				updateCartEntryNodesForEntryNode(updatedEntries, eachNode);
			}
			catch (Exception ex)
			{
				LOG.error("error while processing cart", ex);
				return null;
			}
		}
		return neorisCartFacade.getSessionCart();
		//return neorisCartFacade.getSessionCartCheckStock(); 
	}

    @RequestMapping(value = "/updateRollingScheduleWeek", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @RequireHardLogIn
    public CartData updateRollingScheduleWeek(final Model model, final HttpServletRequest request) {
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

        ArrayNode entries = (ArrayNode) batchData.get("entries");

        List<JsonNode> updatedEntries = new ArrayList<JsonNode>();
        for (JsonNode eachNode : entries)
            updatedEntries.add(eachNode);

        for (JsonNode eachNode : entries)
        {
            Integer cartEntryNumber = eachNode.get("orderEntryNumber").asInt();
            String rollingScheduleWeek = eachNode.get("rollingScheduleWeek").asText();

            try
            {
                neorisCartFacade.updateRollingSchedule(cartEntryNumber, DATE_PARSER.parse(rollingScheduleWeek));
            }
            catch (Exception ex)
            {
                LOG.error("error while processing cart", ex);
                return null;
            }
        }

        //return neorisCartFacade.getSessionCartCheckStock();
        return neorisCartFacade.getSessionCart();
    }

	private void updateCartEntryNodesForEntryNode(List<JsonNode> entries, JsonNode entryNode)
	{
		Long stockQty = entryNode.get("stockQty").asLong();

		// if quantity != 0, exit
		if (stockQty != 0l)
			return;

		// remove the entry
		entries.remove(entryNode);

		// get the entry number of the deleted entry
		Long cartEntryNumber = entryNode.get("orderEntryNumber").asLong();

		// update the entry index
		for (JsonNode eachNode : entries)
		{
			ObjectNode node = (ObjectNode) eachNode;
			
			Long eachEntryNumber = node.get("orderEntryNumber").asLong();

			// 
			if (eachEntryNumber > cartEntryNumber)
				node.put("orderEntryNumber", eachEntryNumber - 1);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updateCartQuantities(@RequestParam("entryNumber") final long entryNumber, final Model model,
			@Valid final UpdateQuantityForm form, final BindingResult bindingResult, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			for (final ObjectError error : bindingResult.getAllErrors())
			{
				if (error.getCode().equals("typeMismatch"))
				{
					GlobalMessages.addErrorMessage(model, "basket.error.quantity.invalid");
				}
				else
				{
					GlobalMessages.addErrorMessage(model, error.getDefaultMessage());
				}
			}
		}
		else if (cartFacade.getSessionCart().getEntries() != null)
		{
			try
			{
				final CartModificationData cartModification = cartFacade.updateCartEntry(entryNumber, form.getQuantity()
						.longValue());
				if (cartModification.getQuantity() == form.getQuantity().longValue())
				{
					// Success

					if (cartModification.getQuantity() == 0)
					{
						// Success in removing entry
						GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
								"basket.page.message.remove");
					}
					else
					{
						// Success in update quantity
						GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
								"basket.page.message.update");
					}
				}
				else
				{
					// Less than successful
					GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
							"basket.information.quantity.reducedNumberOfItemsAdded." + cartModification.getStatusCode());
				}

				// Redirect to the cart page on update success so that the browser doesn't re-post again
				return REDIRECT_PREFIX + "/cart";
			}
			catch (final CommerceCartModificationException ex)
			{
				LOG.warn("Couldn't update product with the entry number: " + entryNumber + ".", ex);
			}
		}

		prepareDataForPage(model);
		return ControllerConstants.Views.Pages.Cart.CartPage;
	}

	protected void createProductList(final Model model) throws CMSItemNotFoundException
	{
		final CartData cartData = neorisCartFacade.getSessionCartCheckStock();
		//NEORIS_CHANGE #74 
		//reverseCartProductsOrder(cartData.getEntries());
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				final UpdateQuantityForm uqf = new UpdateQuantityForm();
				uqf.setQuantity(entry.getQuantity());				
				model.addAttribute("updateQuantityForm" + entry.getEntryNumber(), uqf);
			}
		}
		model.addAttribute("cartData", cartData);
		
		//NEORIS Modificacion Christian Loredo 20112015
		//El llamado a la funcion Valorizacion se hace solo 1 vez (Al agregar el producto al carrito)
		try
		{
			Integer scoreLowLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardLowLimit();
			Integer scoreHighLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardHighLimit();
			
			model.addAttribute("creditScoreCard", cartData.getCreditScoreCard());
			model.addAttribute("semaphoreCredit", cartData.getSemaphoreCredit());
			
			if (scoreLowLimit!=null && scoreHighLimit!=null && scoreHighLimit > scoreLowLimit) {
				model.addAttribute("scoreLowLimit", scoreLowLimit);
				model.addAttribute("scoreHighLimit", scoreHighLimit);
			}  else {
				model.addAttribute("scoreLowLimit", 0);
				model.addAttribute("scoreHighLimit", 0);
			}
		}catch (Exception e) {
			LOG.error("Error CartPageController: getCreditScoreCard: error: " + e.getMessage());
			model.addAttribute("creditScoreCard", 0.0);
			model.addAttribute("scoreLowLimit", 0);
			model.addAttribute("scoreHighLimit", 0);
		}
		
		//NEORIS_CAHNGE#Credit Score Card: LCC
		//get the Credit Score Card
		/*try {
			neorisSapCreditScoreCard.calculateCreditScoreCard(cartData);
			
			Integer scoreLowLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardLowLimit();
			Integer scoreHighLimit = baseStoreService.getCurrentBaseStore().getCreditScoreCardHighLimit();
			
			model.addAttribute("creditScoreCard", cartData.getCreditScoreCard());
			model.addAttribute("semaphoreCredit", cartData.getSemaphoreCredit());
			
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
		}*/

		storeCmsPageInModel(model, getContentPageForLabelOrId(CART_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CART_CMS_PAGE));
	}

	protected void reverseCartProductsOrder(final List<OrderEntryData> entries)
	{
		if (entries != null)
		{
			Collections.reverse(entries);
		}
	}

	protected void prepareDataForPage(final Model model) throws CMSItemNotFoundException
	{
		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);

		final CartRestorationData restorationData = (CartRestorationData) sessionService
				.getAttribute(WebConstants.CART_RESTORATION);
		model.addAttribute("restorationData", restorationData);
		// NEORIS_CHANGE #74 added to limit the number of options on qty lists
		model.addAttribute("maxItemsOnQTYCombos", baseStoreService.getCurrentBaseStore().getMaxBundlesInventory());
		
		createProductList(model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("breadcrumb.cart"));
		model.addAttribute("pageType", PageType.CART.name());
	}
	
	//NEORIS_CHANGE #104
	@RequestMapping(value = "/deleteCart", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object deleteCart(final HttpServletRequest request)
	{

		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "Current Cart Removed");
		node.put("status", AJAX_STATUS_OK);		

		try
		{
			CartModel cart = neorisFacade.getCurrentCart();
			
			if  (cart.getEntries().size() > 0)
			     neorisFacade.emptyCartFromSession(cart);
		}
		catch (Exception ex)
		{
			LOG.error("error while delete cart", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}
		return node;

	}
}
