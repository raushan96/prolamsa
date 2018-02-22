package mx.neoris.storefront.controllers.misc;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.product.ProductVariantConfiguration;
import mx.neoris.core.util.ObjectAssoc;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.controllers.pages.AbstractPageController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.fest.util.Collections;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("tenant")
public class ProductVariantConfigurationController extends AbstractPageController
{
	protected static final Logger LOG = Logger.getLogger(ProductVariantConfigurationController.class);
	
	public static final String POPUPVIEW = "fragments/product/variantConfigurationPopup";
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;
	
	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade neorisCartFacade;

	@Resource(name = "productConverter")
	private AbstractConverter<ProductModel, ProductData> productConverter;

	/* collections */
	@Resource(name = "SpecificStencilCol_5L")
	List<ObjectAssoc> specificStencilCol_5L;

	@Resource(name = "SpecificStencilCol_5CT")
	List<ObjectAssoc> specificStencilCol_5CT;

	@Resource(name = "SpecialDrifterCol")
	List<ObjectAssoc> specialDrifterCol;

	@Resource(name = "LocationOfTestCol")
	List<ObjectAssoc> locationOfTestCol;

	@Resource(name = "SampleDirectionCol")
	List<ObjectAssoc> sampleDirectionCol;

	@Resource(name = "SampleSizeCol")
	List<ObjectAssoc> sampleSizeCol;

	@RequestMapping(value = "/product/variantConfigurationPopup", method = RequestMethod.GET)
	public String load(final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		ProductModel product = null;
		ProductVariantConfiguration configuration = null;

		String productCode = request.getParameter("productCode");
		String cartEntryNumber = request.getParameter("cartEntryNumber");

		try
		{
			// load from session
			if (StringUtils.isNotBlank(productCode))
			{
				product = neorisProductFacade.getProductByCode(productCode);

				// Get the configuration map, not using the read/write method as we only retrieve the information
				Map<String,ProductVariantConfiguration> map = neorisFacade.getProductVariantConfigurationMap();
				
				// if map exists, set the configuration info if any to the model
				if (map != null)
				{
					configuration = map.get(product.getCode());
				}
			}
			
			// load from cart entry
			if (StringUtils.isNotBlank(cartEntryNumber))
			{
				CartModel cartModel = neorisFacade.getCurrentCart();
				
				AbstractOrderEntryModel entry = cartModel.getEntries().get(Integer.parseInt(cartEntryNumber));
				
				product = entry.getProduct();
				
				ProlamsaAPIProductConfigurationModel configModel = entry.getApiProductConfiguration();
				
				configuration = new ProductVariantConfiguration();

				// if configuration exists, retrieve the values
				if (configModel != null)
				{
					configuration = neorisFacade.getProductConfigurationFrom(configModel);
				}
				
				// always set cart entry
				configuration.setOrderEntryNumber(cartEntryNumber);
			}
			
			populateDropdownsForProduct(model, product);
			model.addAttribute("configuration", configuration);

			if (product instanceof ProlamsaProductModel)
			{
				//prolamsaProduct = (ProlamsaProductModel) product;
				
				ProductData productData = productConverter.convert(product);

				neorisProductFacade.injectCustomerNameAndDescriptionDataOn(Collections.list(productData));

				model.addAttribute("productData", productData);
				model.addAttribute("productInfo", product);
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while getting product for ProductVariantConfiguration", ex);
		}
		
		return POPUPVIEW;
	}
	
	private void populateDropdownsForProduct(Model model, ProductModel productModel)
	{
		List<ObjectAssoc> col = null;

		// get the proper collection from the product category
		for (CategoryModel category : productModel.getSupercategories())
		{
			if (category.getCode().contains("5CT"))
				col = specificStencilCol_5CT;
			else
				col = specificStencilCol_5L;
			
			// just check the first category
			break;
		}

		model.addAttribute("stencilCol", col);
		model.addAttribute("drifterCol", specialDrifterCol);
		model.addAttribute("locationTestCol", locationOfTestCol);
		model.addAttribute("sampleDirectionCol", sampleDirectionCol);
		model.addAttribute("sampleSizeCol", sampleSizeCol);
	}
	
	@RequestMapping(value = "/product/variantConfigurationSave", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object variantConfigurationSave(ProductVariantConfiguration config, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", getMessageWithDefaultContext("templates.message.entry_successfully_saved"));
		node.put("status", AJAX_STATUS_OK);

		try
		{
			if (StringUtils.isNotBlank(config.getOrderEntryNumber()))
			{
				// save on cart entry
				CartModel cartModel = neorisFacade.getCurrentCart();

				AbstractOrderEntryModel entry = cartModel.getEntries().get(Integer.parseInt(config.getOrderEntryNumber()));

				ProlamsaAPIProductConfigurationModel configModel = neorisFacade.getProductConfigurationModelFrom(config);
				
				neorisCartFacade.updateAPIProductConfigurationForEntry(entry, configModel);
			}
			else
			{
				// save on session
				Map<String,ProductVariantConfiguration> map = neorisFacade.getReadWriteProductVariantConfigurationMap();
				map.put(config.getProductCode(), config);
	
				neorisFacade.setProductVariantConfigurationMap(map);
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while saving Product Variant Configuration", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}

	@RequestMapping(value = "/product/variantConfigurationDelete", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object variantConfigurationDelete(ProductVariantConfiguration config, final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", getMessageWithDefaultContext("templates.message.entry_successfully_saved"));
		node.put("status", AJAX_STATUS_OK);

		try
		{
			if (StringUtils.isNotBlank(config.getOrderEntryNumber()))
			{
				// remove from cart entry
				CartModel cartModel = neorisFacade.getCurrentCart();

				AbstractOrderEntryModel entry = cartModel.getEntries().get(Integer.parseInt(config.getOrderEntryNumber()));

				neorisCartFacade.updateAPIProductConfigurationForEntry(entry, null);
			}
			else
			{
				// save from session
				Map<String,ProductVariantConfiguration> map = neorisFacade.getReadWriteProductVariantConfigurationMap();
				map.remove(config.getProductCode());
				
				neorisFacade.setProductVariantConfigurationMap(map);
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while deleting Product Variant Configuration", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;
	}
}
