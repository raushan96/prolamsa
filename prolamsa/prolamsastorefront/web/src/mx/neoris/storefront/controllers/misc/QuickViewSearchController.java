package mx.neoris.storefront.controllers.misc;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.product.ProductModel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.controllers.AbstractController;

import org.apache.log4j.Logger;
import org.fest.util.Collections;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;

@Controller
@Scope("tenant")
public class QuickViewSearchController extends AbstractController
{
	protected static final Logger LOG = Logger.getLogger(QuickViewSearchController.class);
	
	public static final String QUICK_VIEW = "fragments/description/quickView";
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;
	
	@Resource(name = "productConverter")
	private AbstractConverter<ProductModel, ProductData> productConverter;
	
	@RequestMapping(value = "/product/quickView", method = RequestMethod.GET)
	public String load(@RequestParam("code") final String code,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		ProductModel product = new ProductModel();
		ProlamsaProductModel prolamsaProduct = new ProlamsaProductModel();
		
		try
		{
			product = neorisProductFacade.getProductByCode(code);

			if (product instanceof ProlamsaProductModel)
			{
				prolamsaProduct = (ProlamsaProductModel) product;
				
				ProductData productData = productConverter.convert(prolamsaProduct);

				neorisProductFacade.injectCustomerNameAndDescriptionDataOn(Collections.list(productData));

				model.addAttribute("productData", productData);
				model.addAttribute("productInfo", product);				
				
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while getting product for quickView", ex);
		}
		
		return QUICK_VIEW;
	}
}
