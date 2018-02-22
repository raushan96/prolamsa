/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.services.NeorisProductInventoryService;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisFavoriteProductFacade;
import mx.neoris.facades.NeorisProductFacade;

import org.apache.log4j.Logger;

/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisProductFacade implements NeorisProductFacade
{
	/**
	 * Field LOG.
	 */
	private static final Logger LOG = Logger.getLogger(DefaultNeorisProductFacade.class);
	
	private Map<String, String> locationMap;
	
	
	@Resource(name = "neorisProductInventoryService")
	private NeorisProductInventoryService productInventoryService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "neorisFavoriteProductFacade")
	private NeorisFavoriteProductFacade neorisFavoriteProductFacade;

	public String getProductLocationCodeForName(String name)
	{
		return getLocationMap().get(name);
	}

	@Override
	public void injectProductInventoryEntriesOn(List<ProductData> productDataList, String customerType)
	{
		List<String> productCodes = new ArrayList<String>();
		List<String> locationCodes = new ArrayList<String>();

		for (ProductData eachProductData : productDataList)
		{
			// use base code as we are passing the location
			productCodes.add(eachProductData.getBaseCode());
			locationCodes.add(eachProductData.getLocation().getCode());
		}

		try
		{
			if (!productCodes.isEmpty())
			{
				Map<String, ProductInventoryEntry> map = getProductInventoryEntriesFor(productCodes, locationCodes, customerType);
				
				for (ProductData eachProductData : productDataList)
				{
					// get the product inventory entry list for the product, use the full product code
					ProductInventoryEntry entry = map.get(eachProductData.getCode());
					
					// if the entry exists
					if (entry == null)
					{
						LOG.warn("No ProductInventory found for Product: " + eachProductData.getCode());
						
						entry = new ProductInventoryEntry();
						entry.setAvailableStockBundles(0);
						entry.setAvailableStockBundlesInternal(0);
						entry.setLocation(eachProductData.getLocation());
						entry.setRollingScheduleDates(Collections.<Date> emptyList());
						entry.setRollingScheduleBundles(0);
						entry.setNoInventoyRoleBundles(0);
						eachProductData.setInventoryEntry(entry);
					}
					
					eachProductData.setInventoryEntry(entry);
				}
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while getting product inventory entries from service", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisProductFacade#injectCustomerProductReferenceData
	 * (java.util.List)
	 */
	@Override
	public void injectCustomerNameAndDescriptionDataOn(List<ProductData> prolamsaProductDatas)
	{
		B2BUnitModel b2bUnitModel = sessionService.getAttribute(NeorisFacade.B2BUNIT_SLOT);
		
		// Get productCodes from productDatas
		List<String> prolamsaProductCodes = new ArrayList<String>();
		List<ProductLocation> location = new ArrayList<ProductLocation>();

		// use the base codes for searching the customer product reference
		// catalog
		for (ProductData productData : prolamsaProductDatas)
		{
			prolamsaProductCodes.add(productData.getBaseCode());
			location.add(ProductLocation.valueOf(productData.getLocation().toString()));
		}
		
		//Christian Loredo 12022015
		//Add parameter CatlogId to search CustomerProductReference
		String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";

		Map<String, CustomerProductReferenceModel> customerProductReferences = customerProductReferenceService.getCustomerProductReferenceFor(prolamsaProductCodes, b2bUnitModel, catalogId, location);
		
		for (ProductData eachProductData : prolamsaProductDatas)
		{
			// retrieve the customerProductReference via the base code
			CustomerProductReferenceModel customerProductReference = customerProductReferences.get(eachProductData.getBaseCode()+eachProductData.getLocation().getCode());
			injectCustomerDataOnProductData(customerProductReference, eachProductData);
		}
		
		// inject isFavoriteProduct in order to put this line on several places
		neorisFavoriteProductFacade.injectIsFavoriteProductOn(prolamsaProductDatas);
	}

	private void injectCustomerDataOnProductData(CustomerProductReferenceModel customerProductReference, ProductData productData)
	{
		if (customerProductReference != null)
		{
			// set customer information
			productData.setVisibleCode(customerProductReference.getCode());
			productData.setName(customerProductReference.getDescription());

			productData.setCustomerCode(customerProductReference.getCode());
			productData.setCustomerDescription(customerProductReference.getDescription());
		}
		else
		{	// if there is not customerProductReferene
			// set the base code (no location) as the visible one
			productData.setVisibleCode(productData.getBaseCode());			
		}
	}

	// NEORIS_CHANGE #74
	@Override
	public void injectCustomerNameAndDescriptionDataOnOrderEntries(List<OrderEntryData> orderEntriesData)
	{
		if (orderEntriesData == null || orderEntriesData.size() == 0)
			return;

		List<ProductData> productDatas = new ArrayList<ProductData>();

		for (OrderEntryData ecahOrderEntryData : orderEntriesData)
		{
			productDatas.add(ecahOrderEntryData.getProduct());
		}

		injectCustomerNameAndDescriptionDataOn(productDatas);
	}

	// NEORIS_CHANGE #79
	@Override
	public ProductModel getProductByCode(String code)
	{

		ProductModel product = productService.getProductForCode(code);

		return product;
	}

	@Override
	public Map<String, ProductInventoryEntry> getProductInventoryEntriesFor(List<String> productCodes, List<String> locationCodes, String customerType) throws Exception
	{
		return productInventoryService.getProductInventoryEntriesFor(productCodes, locationCodes, customerType);
	}

	/**
	 * @return the productInventoryService
	 */
	public NeorisProductInventoryService getProductInventoryService()
	{
		return productInventoryService;
	}

	/**
	 * @param productInventoryService
	 *            the productInventoryService to set
	 */
	public void setProductInventoryService(NeorisProductInventoryService productInventoryService)
	{
		this.productInventoryService = productInventoryService;
	}

	/*
	 * NEORIS_CHANGE #74 When creating a order confirmation email we need to
	 * pass the b2bunit to get the customer product reference in order to show
	 * the proper product info(visibleCode and customer description)
	 */
	@Override
	public void injectCustomerNameAndDescriptionDataOn(List<OrderEntryData> orderEntryDatas, B2BUnitModel b2bUnitModel)
	{
		if (orderEntryDatas == null || orderEntryDatas.size() == 0)
			return;

		List<ProductData> prolamsaProductDatas = new ArrayList<ProductData>();
		
		for (OrderEntryData ecahOrderEntryData : orderEntryDatas)
		{
			prolamsaProductDatas.add(ecahOrderEntryData.getProduct());
		}

		// Get productCodes from productDatas
		List<String> prolamsaProductCodes = new ArrayList<String>();
		List<ProductLocation> location = new ArrayList<ProductLocation>();

		for (ProductData productData : prolamsaProductDatas)
		{	prolamsaProductCodes.add(productData.getBaseCode());
			location.add(ProductLocation.valueOf(productData.getLocation().toString()));
		}
		
		//Christian Loredo 12022015
		//Add parameter CatlogId to search CustomerProductReference
		String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";

		Map<String, CustomerProductReferenceModel> customerProductReferences = customerProductReferenceService.getCustomerProductReferenceFor(prolamsaProductCodes, b2bUnitModel, catalogId, location);

		for (ProductData eachProductData : prolamsaProductDatas)
		{
			// retrieve the customerProductReference via the base code
			CustomerProductReferenceModel customerProductReference = customerProductReferences.get(eachProductData.getBaseCode()+eachProductData.getLocation().getCode());
			injectCustomerDataOnProductData(customerProductReference, eachProductData);
		}
		
		// inject isFavoriteProduct in order to put this line on several places
		neorisFavoriteProductFacade.injectIsFavoriteProductOn(prolamsaProductDatas);
	}
	
	@Override
	public void injectCustomerNameAndDescriptionDataOnEmail(List<OrderEntryData> orderEntryDatas, B2BUnitModel b2bUnitModel)
	{
		if (orderEntryDatas == null || orderEntryDatas.size() == 0)
			return;

		List<ProductData> prolamsaProductDatas = new ArrayList<ProductData>();
		
		for (OrderEntryData ecahOrderEntryData : orderEntryDatas)
		{
			prolamsaProductDatas.add(ecahOrderEntryData.getProduct());
		}

		// Get productCodes from productDatas
		List<String> prolamsaProductCodes = new ArrayList<String>();
		List<ProductLocation> location = new ArrayList<ProductLocation>();

		for (ProductData productData : prolamsaProductDatas)
		{	prolamsaProductCodes.add(productData.getBaseCode());
			location.add(ProductLocation.valueOf(productData.getLocation().toString()));
		}
		
		//Christian Loredo 12022015
		//Add parameter CatlogId to search CustomerProductReference
		String catalogId = neorisFacade.getCurrentBaseStore().getUid() + "ProductCatalog";

		Map<String, CustomerProductReferenceModel> customerProductReferences = customerProductReferenceService.getCustomerProductReferenceFor(prolamsaProductCodes, b2bUnitModel, catalogId, location);

		for (ProductData eachProductData : prolamsaProductDatas)
		{
			// retrieve the customerProductReference via the base code
			CustomerProductReferenceModel customerProductReference = customerProductReferences.get(eachProductData.getBaseCode()+eachProductData.getLocation().getCode());
			injectCustomerDataOnProductData(customerProductReference, eachProductData);
		}
		
	}
	
	//UPLOAD_EXCEL
	@Override
	public void injectProductInfoExcel(List<ProductData> productDataList, Map<String,Double> quantityMap, List<String> skuList)
	{
		try
		{
			for (ProductData eachProductData : productDataList)
			{
				// visible code is the baseCode or the customerCode
				// key combines the visibleCode + the location code

				//String key = eachProductData.getVisibleCode() + eachProductData.getLocation().getCode();
				String key = eachProductData.getBaseCode() + eachProductData.getLocation().getCode();

				eachProductData.setQuantityExcel(quantityMap.get(key));
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while getting product info from Excel", ex);
		}
	}


	/**
	 * @return the locationMap
	 */
	protected Map<String, String> getLocationMap() {
		return locationMap;
	}

	/**
	 * @param locationMap the locationMap to set
	 */
	public void setLocationMap(Map<String, String> locationMap) {
		this.locationMap = locationMap;
	}
}
