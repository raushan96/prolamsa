package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.B2BOrderFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("tenant")
@RequestMapping(value = "/templatesOrder")
public class TemplateOrderController extends AbstractPageController
{
	public static final String POPUP_PAGE = "pages/templatesOrder/templatePopup";

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder breadcrumbBuilder;

	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade cartFacade;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "b2bOrderFacade")
	private B2BOrderFacade b2bOrderFacade;

	private static final Logger LOG = Logger.getLogger(TemplateOrderController.class);

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	// public String showPopup(@RequestParam("orderCode") final String
	// orderCode,final Model model, final HttpServletRequest request)
	public String showPopup(final Model model, final HttpServletRequest request)
	{
		String orderCode = request.getParameter("codeOrder");
		model.addAttribute("order", orderCode);
		return POPUP_PAGE;
	}

	@RequestMapping(value = "/saveTemplate", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object saveAjax(@RequestParam("name")
	final String name, @RequestParam("orderCode")
	final String orderCode, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", getMessageWithDefaultContext("templates.message.entry_successfully_saved"));
		node.put("status", AJAX_STATUS_OK);

		final OrderModel orderModel = neorisFacade.getOrderFromCode(orderCode);
		B2BUnitModel b2bUnit = getRootUnit();
		UserModel user = (UserModel) sessionService.getAttribute("user");
		UnitModel unit = getUnit();

		try
		{
			Wishlist2Model wishList = neorisFacade.saveTemplateOrder(name, orderCode, user, b2bUnit, unit, orderModel);
		}
		catch (Exception ex)
		{
			LOG.error("error while saving Wishlist", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/checkExistTemplate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getExistTemplate(@RequestParam("name")
	final String name, @RequestParam("orderCode")
	final String orderCode, final Model model, final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{

		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		Boolean existTemplate = false;

		existTemplate = neorisFacade.getExistTemplate(orderCode);

		if (existTemplate == false)
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

		unit.put("existTemplate", existTemplate);

		result.add(unit);

		node.put("exist", result);

		return node;
	}

	@RequestMapping(value = "/updateTemplate", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object updateAjax(@RequestParam("name")
	final String name, @RequestParam("orderCode")
	final String orderCode, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", getMessageWithDefaultContext("templates.message.entry_successfully_saved"));
		node.put("status", AJAX_STATUS_OK);

		List<Wishlist2Model> wishlist = new ArrayList<Wishlist2Model>();
		B2BUnitModel currentB2BUnit = getRootUnit();

		try
		{
			wishlist = neorisFacade.getWishlistForOrderCodeAndB2BUnit(orderCode, currentB2BUnit);
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

}
