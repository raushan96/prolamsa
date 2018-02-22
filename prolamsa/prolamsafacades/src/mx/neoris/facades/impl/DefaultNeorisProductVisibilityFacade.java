/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.NeorisProductVisibilityService;
import mx.neoris.facades.NeorisProductVisibilityFacade;

/**
 * @author e-lacantu
 *
 */
public class DefaultNeorisProductVisibilityFacade implements
		NeorisProductVisibilityFacade {
	
    private static final Logger LOG = Logger.getLogger(DefaultNeorisProductVisibilityFacade.class);
		
	@Resource(name = "neorisProductVisibilityService")
	private NeorisProductVisibilityService neorisProductVisibilityService;

	@Resource(name = "productService")
	private ProductService productService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisProductVisibilityFacade#getProductByCode(java.lang.String)
	 */
	@Override
	public ProlamsaProductModel getProductByCode(String code) throws Exception {
		return neorisProductVisibilityService.getProductByCode(code);		
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisProductVisibilityFacade#getProductByAttribute(java.lang.String, java.util.Map)
	 */
	@Override
	public SearchResult<ProlamsaProductModel> getProductByAttribute(
			String baseStore, Map<String, Object> mapAttribute)
			throws Exception {
		return neorisProductVisibilityService.getProductByAttribute(baseStore, mapAttribute);	
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisProductVisibilityFacade#getAllProductVisibility(java.lang.String)
	 */
	@Override
	public SearchResult<ProductVisibilityModel> getAllProductVisibility(
			String baseStore) throws Exception {
		return neorisProductVisibilityService.getAllProductVisibility(baseStore);
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisProductVisibilityFacade#getProductVisibilityByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductVisibilityModel getProductVisibilityByCode(String baseStore,
			String code) throws Exception {
		return neorisProductVisibilityService.getProductVisibilityByCode(baseStore, code);
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisProductVisibilityFacade#getProductVisibilityByCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductVisibilityModel getProductVisibilityByCategory(
			String baseStore, String category) throws Exception {
		return neorisProductVisibilityService.getProductVisibilityByCategory(baseStore, category);
	}

}
