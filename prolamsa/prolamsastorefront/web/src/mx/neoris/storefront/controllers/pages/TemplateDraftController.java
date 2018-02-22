package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.enums.Wishlist2Type;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.wishlist2entry.data.Wishlist2EntryData;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessages;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
@Scope("tenant")
@RequestMapping(value = "/templates")
public class TemplateDraftController extends AbstractPageController
{
	public static final String TEMPLATE_LIST_CMS_PAGE = "TemplateListPage";
	public static final String TEMPLATE_DETAILS_CMS_PAGE = "TemplateDetailPage";

	public static final String LIST_PAGE = "pages/templates/templateListPage";
	public static final String DETAILS_PAGE = "pages/templates/templateDetailPage";
	public static final String POPUP_PAGE = "pages/templates/templatePopup";
	public static final String LIST_URL = "/templates/list";

	public static final String LOAD_TEMPLATE = "pages/templates/loadTemplateToCart";
	
	
	private static final String PROLAMSA_INTERNAL_GROUP = "prolamsa_internal";

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder breadcrumbBuilder;

	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade cartFacade;
	
	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name="neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	@Resource(name = "neorisProductConverter")
	private AbstractConverter<ProductModel, ProductData> neorisProductConverter;
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	private static final Logger LOG = Logger.getLogger(TemplateDraftController.class);

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public String showPopup(final Model model, final HttpServletRequest request)
	{
		return POPUP_PAGE;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam("name")
	final String name, final HttpServletRequest request)
	{
		return "redirect:" + LIST_URL;
	}

	@RequestMapping(value = "/saveAjax", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object saveAjax(@RequestParam("name")
	final String name,
	// @RequestParam("type") final String type,
			final HttpServletRequest request)
	{
		// TODO
		// retrieves the Cart from the session and save it as a WishList2 object
		// assign current user and current B2BUnit

		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", getMessageWithDefaultContext("templates.message.entry_successfully_saved"));
		node.put("status", AJAX_STATUS_OK);

		try
		{
			CartModel currentCart = (CartModel) sessionService.getAttribute("cart");
			B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
			UnitModel currentUnit = getUnit();
			UserModel currentUser = (UserModel) sessionService.getAttribute("user");

			// Boolean existWishlist = false;
			// existWishlist = neorisFacade.getExistWishlist(name, currentUser,
			// currentB2BUnit);

			// if (!existWishlist)
			// {
			// Wishlist2Model wishList = neorisFacade.saveWishlist(name, type,
			// currentUser, currentUnit, currentB2BUnit, currentCart);
			Wishlist2Model wishList = neorisFacade.saveWishlist(name, currentUser, currentUnit, currentB2BUnit, currentCart);

			// If the template is a draft then we save the relationship
			if (wishList != null && wishList.getType().equals(Wishlist2Type.DRAFT))
				sessionService.setAttribute(ControllerConstants.Templates.DRAFT, wishList);
			// }
			// else
			// {
			// throw new
			// Exception(getMessageWithDefaultContext("templates.error.entry_with_same_name_already_exists"));
			// No manda error, se hizo asi para mostrar el siguiente paso:
			// Confirmar actualización.
			// node.put("status", AJAX_STATUS_ERROR);
			// }

		}
		catch (Exception ex)
		{
			LOG.error("error while saving Wishlist", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/saveAjaxNode", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object saveAjaxNode(@RequestBody
	final JsonNode requestNode, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "OK");
		node.put("status", 1);

		return node;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final ContentPageModel page = getContentPageForLabelOrId(TEMPLATE_LIST_CMS_PAGE);

		storeContentPageTitleInModel(model, page.getName());
		storeCmsPageInModel(model, page);
		setUpMetaDataForContentPage(model, page);

		model.addAttribute("breadcrumbs", breadcrumbBuilder.getBreadcrumbs("templates.list.link"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		// TODO
		// Retrieves all wishlist associated to the customer and current b2b
		// unit

		B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
		UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		List<Wishlist2Model> templates = new ArrayList<Wishlist2Model>();

		try
		{
			templates = neorisFacade.getWishlistForUserAndB2BUnit(currentUser, currentB2BUnit);
		}
		catch (Exception ex)
		{
			LOG.error("error while getting Wishlist", ex);
		}

		model.addAttribute("templates", templates);

		return LIST_PAGE;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String showDetails(@RequestParam(value = "wishlist")
	final String wishlist, final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final ContentPageModel page = getContentPageForLabelOrId(TEMPLATE_DETAILS_CMS_PAGE);

		storeContentPageTitleInModel(model, page.getName());
		storeCmsPageInModel(model, page);
		setUpMetaDataForContentPage(model, page);

		model.addAttribute("breadcrumbs", breadcrumbBuilder.getBreadcrumbs("templates.list.detail.link"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("hasCart", cartFacade.getMiniCart().getTotalUnitCount());

		// Retrives wishlist
		B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
		UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		List<Wishlist2Model> wishlist2 = new ArrayList<Wishlist2Model>();
		List<B2BUnitModel> listB2BUnitsModel = neorisFacade.getB2BUnitModelsFromCustomer(neorisFacade.getCurrentCustomer());
		
		wishlist2 = neorisFacade.getWishlistForUserAndAllB2BUnits(currentUser, listB2BUnitsModel);
		for (Wishlist2Model wl : wishlist2)
		{
			if (wl.getPk().toString().equals(wishlist))
			{
				model.addAttribute("wishlist", wl);
			}
		}

		// Retrieves wishlist Entry associated to the wishlist
		List<Wishlist2EntryData> wishlistEntry = new ArrayList<Wishlist2EntryData>();

		try
		{
			wishlistEntry = neorisFacade.getWishlistEntry(wishlist);
			if(wishlistEntry != null && !wishlistEntry.isEmpty())
			{
				//Modificado Chrisitan Loredo 26022016, debido a que hacia una llamada a la funcion stock de SAP por cada producto del draft.
				//Con este cambio, se crea un listado de productos y se utiliza para ejecutar los 3 metodos que se encuentran mas abajo.
				
				List<ProductData> productList = new ArrayList<ProductData>();
				for(Wishlist2EntryData eachEntry : wishlistEntry)
				{
					productList.add(eachEntry.getProduct());
					//neorisProductFacade.injectProductInventoryEntriesOn(Lists.newArrayList(eachEntry.getProduct()), neorisFacade.getCurrentCustomerType());
					//neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(neorisFacade.getCurrentUnit(), Lists.newArrayList(eachEntry.getProduct()));
					//neorisProductFacade.injectCustomerNameAndDescriptionDataOn(Lists.newArrayList(eachEntry.getProduct()));
				}
				
				neorisProductFacade.injectProductInventoryEntriesOn(productList, neorisFacade.getCurrentCustomerType());
				neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(neorisFacade.getCurrentUnit(), productList);
				neorisProductFacade.injectCustomerNameAndDescriptionDataOn(productList);
				
			}
			
//			if(!"prolamsa_no_inventory".equalsIgnoreCase(neorisFacade.getCurrentCustomerType()))
//				validateWishlist(model,Long.valueOf(wishlist));
		}
		catch (Exception ex)
		{
			LOG.error("error while getting Wishlist", ex);
		}

		model.addAttribute("wishlistEntry", wishlistEntry);

		return DETAILS_PAGE;
	}
	
	@RequestMapping(value = "/setTemplateOnSession", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object setTemplateOnSession(@RequestParam("wishlistPK")
	final String wishlistPK, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "Template Load");
		node.put("status", AJAX_STATUS_OK);

		LOG.debug("Wishlist PK " + wishlistPK);

		try
		{
			// Retrives wishlist
			B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
			UserModel currentUser = (UserModel) sessionService.getAttribute("user");
			List<Wishlist2Model> userWishlists = new ArrayList<Wishlist2Model>();
			List<B2BUnitModel> listB2BUnitsModel = neorisFacade.getB2BUnitModelsFromCustomer(neorisFacade.getCurrentCustomer());
			
			userWishlists = neorisFacade.getWishlistForUserAndAllB2BUnits(currentUser, listB2BUnitsModel);
			Wishlist2Model wishList = null;
			for (Wishlist2Model wl : userWishlists)
			{
				if (wl.getPk().toString().equals(wishlistPK))
				{
					wishList = wl;
					neorisFacade.setRootUnitWithId(wl.getB2bUnit().getUid());
					break;
				}
			}

			// if wish list loaded
			if (wishList != null)
			{
				// if the template is a draft then we save it on the session
				if (wishList.getType().equals(Wishlist2Type.DRAFT))
				{
					sessionService.setAttribute(ControllerConstants.Templates.DRAFT, wishList);
				}
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while load template", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/loadTemplateToCart", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object loadTemplateToCart(@RequestParam("wishlistPK")
	final String wishlistPK, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "Template Load");
		node.put("status", AJAX_STATUS_OK);

		LOG.debug("Wishlist PK " + wishlistPK);

		try
		{
			Wishlist2Model wishList = neorisFacade.loadCartFromWishlist(Long.parseLong(wishlistPK));

			// if wish list loaded
			if (wishList != null)
			{
				// if the template is a draft then we save it on the session
				if (wishList.getType().equals(Wishlist2Type.DRAFT))
				{
					sessionService.setAttribute(ControllerConstants.Templates.DRAFT, wishList);
				}

				// if there is a unit set on the wishlist, assign it on the
				// current session
				UnitModel wishListUnit = wishList.getUnit();
				if (wishListUnit != null)
				{
					neorisFacade.setUnitWithId(wishListUnit.getCode());					
				}
				
				// Changing B2BUnit
				B2BUnitModel wishListB2BUnit = wishList.getB2bUnit();
				if (wishListB2BUnit != null)
				{
					neorisFacade.setRootUnitWithId(wishListB2BUnit.getUid());
				}
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while load template", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/deleteTemplate", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object deleteTemplate(@RequestParam("wishlistPK")
	final String wishlistPK, final HttpServletRequest request)
	{

		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "Template Removed");
		node.put("status", AJAX_STATUS_OK);

		LOG.debug("Wishlist PK " + wishlistPK);

		try
		{
			Wishlist2Model currentWishList = sessionService.getAttribute(ControllerConstants.Templates.DRAFT);

			if (currentWishList != null && currentWishList.getPk().getLongValueAsString().equals(wishlistPK))
				sessionService.removeAttribute(ControllerConstants.Templates.DRAFT);

			neorisFacade.deleteWishlist(Long.parseLong(wishlistPK));
		}
		catch (Exception ex)
		{
			LOG.error("error while delete template", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}
		return node;

	}

	@RequestMapping(value = "/updateDraft", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object updateAjax(@RequestParam("name")
	final String name, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", getMessageWithDefaultContext("templates.message.entry_successfully_saved"));
		node.put("status", AJAX_STATUS_OK);

		List<Wishlist2Model> wishlist = new ArrayList<Wishlist2Model>();
		B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();

		try
		{
			wishlist = neorisFacade.getWishlistForNameAndB2BUnit(name, currentB2BUnit);
			for (Wishlist2Model wl : wishlist)
			{
				Wishlist2Model wishListUpdated = neorisFacade.updateTemplateOrder(name, wl);
			}

		}
		catch (Exception ex)
		{
			LOG.error("error while updating name from order template", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/checkExistDraft", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getExistTemplate(@RequestParam("name")
	final String name, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);

		B2BUnitModel currentB2BUnit = neorisFacade.getRootUnit();
		UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		Boolean existWishlist = false;
		existWishlist = neorisFacade.getExistWishlist(name, currentUser, currentB2BUnit);

		if (existWishlist == false)
		{
			node.put("message", "OK");
			node.put("status", AJAX_STATUS_OK);
		}
		else
		{
			// No manda error, se hizo asi para mostrar el siguiente paso:
			// Confirmar actualización.
			node.put("status", AJAX_STATUS_ERROR);
		}

		ObjectNode unit = JsonNodeFactory.instance.objectNode();

		unit.put("existWishlist", existWishlist);

		result.add(unit);

		node.put("exist", result);

		return node;
	}

	private void validateWishlist(Model model, Long wishlistPK) throws Exception
	{
		// search wishlist by its PK
				Wishlist2Model wishList = (Wishlist2Model) modelService.get(PK.fromLong(wishlistPK));

				// always protect objects being found by PK or id
				if (wishList == null)
					throw new Exception("wishlist not found for pk " + wishlistPK);

				UnitModel currentUnit = neorisFacade.getCurrentUnit();

				// add wishlist entries to current cart
				for (Wishlist2EntryModel eachWishlistEntry : wishList.getEntries())
				{
					ProductModel product = eachWishlistEntry.getProduct();

					String productCode = eachWishlistEntry.getComment();
					
					// Bundles o pieces quntity
					Long quantity = Long.valueOf(eachWishlistEntry.getDesired());
					

					if (product instanceof ProlamsaProductModel)
					{
						ProlamsaProductModel prolamsaProduct = (ProlamsaProductModel) product;

						ProductLocation productLocation = prolamsaProduct.getLocation();
						Date rollingScheduleDate = eachWishlistEntry.getRollingScheduleWeek();
						ProlamsaAPIProductConfigurationModel config = eachWishlistEntry.getApiProductConfiguration();
						
						// get product data
						ProductData productData = neorisProductConverter.convert(prolamsaProduct);
						
						Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnit, quantity, productData);

						// if rolling schedule defined, only add the product to the cart
						// if rolling schedule date exists on the actual inventory
						// information
						
						// fill inventory information
						neorisProductFacade.injectProductInventoryEntriesOn(Lists.newArrayList(productData), neorisFacade.getCurrentCustomerType());
						neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(currentUnit, Lists.newArrayList(productData));
						neorisProductFacade.injectCustomerNameAndDescriptionDataOn(Lists.newArrayList(productData));
						
						ProductInventoryEntry eachInventoryEntry = productData.getInventoryEntry();
						
						if (rollingScheduleDate != null)
						{
							// if wishlist entry location matches
							if (productLocation.equals(eachInventoryEntry.getLocation()))
							{
								if(eachInventoryEntry.getRollingScheduleDates() == null 
										|| eachInventoryEntry.getRollingScheduleDates().isEmpty())// if there is not available week ignore it
								{
									GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.noScheduleWeek",new Object[]{productData.getVisibleCode()});
									continue;
								}else if (!eachInventoryEntry.getRollingScheduleDates().contains(rollingScheduleDate))// if the date is not valid adjust it
								{
									GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.adjustScheduleWeek",new Object[]{productData.getVisibleCode()});
								}
								
								
								List <Double>quantities =  eachInventoryEntry.getRollingScheduleBundlesCol();
								
								if(!hasValidInventory(quantities))
								{
									GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.noRollingStock",new Object[]{productData.getVisibleCode()});
								}else if(!eachInventoryEntry.getRollingScheduleBundlesCol().contains(convertedQuantity))
								{
									GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.adjustQuantity",new Object[]{productData.getVisibleCode()});
								}
																								
							}
						}
						else
						{
							
							if (productLocation.equals(eachInventoryEntry.getLocation()))
							{
								List <Double> quantities;
								if (PROLAMSA_INTERNAL_GROUP.equalsIgnoreCase(neorisFacade.getCurrentCustomerType()))
									quantities= eachInventoryEntry.getAvailableStockBundlesColInternal();
								else
									quantities= eachInventoryEntry.getAvailableStockBundlesCol();
								
								if(hasValidInventory(quantities))// there is valid inventory
								{
									if(!quantities.contains(convertedQuantity))// if there is not enough inventory adjust it to the max!
									{
										GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.adjustQuantity",new Object[]{productData.getVisibleCode()});
									}
									
								}else // there is no stock then create rolling schedule
								{
									if(eachInventoryEntry.getRollingScheduleDates() == null 
											|| eachInventoryEntry.getRollingScheduleDates().isEmpty())// if there is not rolling ignore the entry
									{
										GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.noStock",new Object[]{productData.getVisibleCode()});
									}else
									{
										quantities = eachInventoryEntry.getRollingScheduleBundlesCol();
										if(hasValidInventory(quantities))
										{
											GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "wishlist.page.error.fromStockToRolling",new Object[]{productData.getVisibleCode()});
										}
									}
								}
								
							}
						}
						
					}

				}
				
				
		
	}
	
	protected boolean hasValidInventory(List<Double> inventory)
	{
		if(inventory == null)
			return false;
		
		if(inventory.size()==0)
			return false;
		
		if(inventory.size() == 1 && inventory.get(0)==0.0)
			return false;
		
		return true;
	}
}

