/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.impl.DefaultCartFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.services.NeorisCartService;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;

import org.apache.log4j.Logger;

/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisCartFacade extends DefaultCartFacade implements NeorisCartFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisCartFacade.class);
	
	@Resource
	CartService cartService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisCartService")
	private NeorisCartService neorisCartService;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name = "neorisProductConverter")
	private AbstractConverter<ProductModel, ProductData> neorisProductConverter;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	public UnitModel getDefaultUnit()
	{
		return unitService.getUnitForCode("pieces");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.commercefacades.order.impl.DefaultCartFacade#
	 * getSessionCart()
	 */
	//Modificaciones echas por Christian L. 5 de Octubre 2014
	//Método modificado, no es necesario la llamdada al RFC de Stock, para eso se creó un método nuevo más abajo en esta misma clase
	//Modificacion al if de los entries del carrito, ya que nunca entraba, ni siquiera cuando el carrito estaba vacio.
	@Override
	public CartData getSessionCart()
	{
		// YTODO Auto-generated method stub
		//System.out.println("TEST SESSIONCART");
		CartModel currentCart = (CartModel) sessionService.getAttribute("cart");
		CartData cartData = super.getSessionCart();
		
		//cartData.setTryCreateOrder(currentCart.isTryCreateOrder());
		
		//if (currentCart.getEntries().size() == 0)
		//{
		//	System.out.println("CARRITO VACIO");
		//}

		// NEORIS_CHANGE #74 cart entries = null was causing an exception, this
		// way we avoid the null
		//if (cartData.getEntries() == null)
		if (currentCart.getEntries().size() == 0)
		{
			List<OrderEntryData> entries = new ArrayList<OrderEntryData>();
			cartData.setEntries(entries);
			return cartData;
		}

		//List<ProductData> productDatas = new ArrayList<ProductData>();

		// NEORIS_CHANGE #64 Getting product from order entries
		//for (OrderEntryData orderEntryData : cartData.getEntries())
		//{
		//	orderEntryData.getProduct().setConvertedQuantity(orderEntryData.getConvertedQuantity());
		//	productDatas.add(orderEntryData.getProduct());
		//}
		
		//System.out.println("TEST CARRITO");
	/*	//int cartLength = cartData.getEntries
		neorisProductFacade.injectProductInventoryEntriesOn(productDatas, neorisFacade.getCurrentCustomerType());

		// NEORIS_CHANGE #75 We need to change the quantities in the cart
		// depending on the current unit
		UnitModel currentUnit = neorisFacade.getCurrentUnit();

		Double convertedQuantity;

		// optimize to gather all product inventories from a single call
		List<ProductData> products = new ArrayList<ProductData>();
		for (OrderEntryData orderEntryData : cartData.getEntries())
			if (!products.contains(orderEntryData.getProduct()))
				products.add(orderEntryData.getProduct());
		neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(currentUnit, products);

		// NEORIS_CHANGE #67 In the cart page, we need the product inventory
		// data for each order entry
		for (OrderEntryData orderEntryData : cartData.getEntries())
		{
			// this new inventory entry is create to hold the inventory data for
			// the order entry but with only one rolling schedule week
			ProductInventoryEntry productInventoryEntryForOrderEntry = new ProductInventoryEntry();

			// For each product in the cart we get the inventory entries
			ProductInventoryEntry productInventoryEntry = orderEntryData.getProduct().getInventoryEntry();

			if (productInventoryEntry.getLocation().equals(orderEntryData.getProduct().getLocation()))
			{
				// Copy all the attributes and set the new inventory entry to
				// the order entry
				productInventoryEntryForOrderEntry.setLocation(productInventoryEntry.getLocation());

				productInventoryEntryForOrderEntry.setAvailableStockBundles(productInventoryEntry.getAvailableStockBundles());
				productInventoryEntryForOrderEntry.setAvailableStockBundlesCol(productInventoryEntry.getAvailableStockBundlesCol());

				productInventoryEntryForOrderEntry.setAvailableStockBundlesInternal(productInventoryEntry.getAvailableStockBundlesInternal());
				productInventoryEntryForOrderEntry.setAvailableStockBundlesColInternal(productInventoryEntry.getAvailableStockBundlesColInternal());

				productInventoryEntryForOrderEntry.setRollingScheduleBundles(productInventoryEntry.getRollingScheduleBundles());
				productInventoryEntryForOrderEntry.setRollingScheduleBundlesCol(productInventoryEntry.getRollingScheduleBundlesCol());

				List<Date> rollingScheduleDates = new ArrayList<Date>();
				rollingScheduleDates.add(orderEntryData.getRollingScheduleWeek());

				// productInventoryEntryForOrderEntry.setRollingScheduleDates(rollingScheduleDates);
				productInventoryEntryForOrderEntry.setRollingScheduleDates(productInventoryEntry.getRollingScheduleDates());

				orderEntryData.setProductInventoryEntry(productInventoryEntryForOrderEntry);
			}

			convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnit, orderEntryData.getQuantity(), orderEntryData.getProduct());
			orderEntryData.setConvertedQuantity(convertedQuantity);
		}

		neorisProductFacade.injectCustomerNameAndDescriptionDataOn(productDatas);

		// NEORIS_CHANGE #67 Sort Cart Entries
		Collections.sort(cartData.getEntries(), new OrderEntryDataComparatorByCodeAndLocation());
		*/
		return cartData;
	}
	
	@Override
	public CartData getSessionCartCheckStock()
	{
		// YTODO Auto-generated method stub
		//System.out.println("TEST SESSIONCART2");
		CartModel currentCart = (CartModel) sessionService.getAttribute("cart");
		CartData cartData = super.getSessionCart();
		
		//cartData.setTryCreateOrder(currentCart.isTryCreateOrder());
		
		//if (currentCart.getEntries().size() == 0)
		//{
		//	System.out.println("CARRITO VACIO2");
		//}

		// NEORIS_CHANGE #74 cart entries = null was causing an exception, this
		// way we avoid the null
		//if (cartData.getEntries() == null)
		if (currentCart.getEntries().size() == 0)
		{
			List<OrderEntryData> entries = new ArrayList<OrderEntryData>();
			cartData.setEntries(entries);
			return cartData;
		}

		List<ProductData> productDatas = new ArrayList<ProductData>();

		// NEORIS_CHANGE #64 Getting product from order entries
		for (OrderEntryData orderEntryData : cartData.getEntries())
		{
			orderEntryData.getProduct().setConvertedQuantity(orderEntryData.getConvertedQuantity());
			productDatas.add(orderEntryData.getProduct());
		}
		
		//System.out.println("TEST CARRITO2");
		//int cartLength = cartData.getEntries
		neorisProductFacade.injectProductInventoryEntriesOn(productDatas, neorisFacade.getCurrentCustomerType());

		// NEORIS_CHANGE #75 We need to change the quantities in the cart
		// depending on the current unit
		UnitModel currentUnit = neorisFacade.getCurrentUnit();

		Double convertedQuantity;

		// optimize to gather all product inventories from a single call
		List<ProductData> products = new ArrayList<ProductData>();
		for (OrderEntryData orderEntryData : cartData.getEntries())
			if (!products.contains(orderEntryData.getProduct()))
				products.add(orderEntryData.getProduct());
		neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(currentUnit, products);

		// NEORIS_CHANGE #67 In the cart page, we need the product inventory
		// data for each order entry
		for (OrderEntryData orderEntryData : cartData.getEntries())
		{
			// this new inventory entry is create to hold the inventory data for
			// the order entry but with only one rolling schedule week
			ProductInventoryEntry productInventoryEntryForOrderEntry = new ProductInventoryEntry();

			// For each product in the cart we get the inventory entries
			ProductInventoryEntry productInventoryEntry = orderEntryData.getProduct().getInventoryEntry();

			if (productInventoryEntry.getLocation().equals(orderEntryData.getProduct().getLocation()))
			{
				// Copy all the attributes and set the new inventory entry to
				// the order entry
				productInventoryEntryForOrderEntry.setLocation(productInventoryEntry.getLocation());

				productInventoryEntryForOrderEntry.setAvailableStockBundles(productInventoryEntry.getAvailableStockBundles());
				productInventoryEntryForOrderEntry.setAvailableStockBundlesCol(productInventoryEntry.getAvailableStockBundlesCol());

				productInventoryEntryForOrderEntry.setAvailableStockBundlesInternal(productInventoryEntry.getAvailableStockBundlesInternal());
				productInventoryEntryForOrderEntry.setAvailableStockBundlesColInternal(productInventoryEntry.getAvailableStockBundlesColInternal());

				productInventoryEntryForOrderEntry.setRollingScheduleBundles(productInventoryEntry.getRollingScheduleBundles());
				productInventoryEntryForOrderEntry.setRollingScheduleBundlesCol(productInventoryEntry.getRollingScheduleBundlesCol());
				
				productInventoryEntryForOrderEntry.setStockOuts(productInventoryEntry.getStockOuts());

				List<Date> rollingScheduleDates = new ArrayList<Date>();
				rollingScheduleDates.add(orderEntryData.getRollingScheduleWeek());

				// productInventoryEntryForOrderEntry.setRollingScheduleDates(rollingScheduleDates);
				productInventoryEntryForOrderEntry.setRollingScheduleDates(productInventoryEntry.getRollingScheduleDates());

				orderEntryData.setProductInventoryEntry(productInventoryEntryForOrderEntry);
			}
			
			convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnit, orderEntryData.getQuantity(), orderEntryData.getProduct());
			orderEntryData.setConvertedQuantity(convertedQuantity);				
		}

		neorisProductFacade.injectCustomerNameAndDescriptionDataOn(productDatas);

		// NEORIS_CHANGE #67 Sort Cart Entries
		//Change to remove gruopind data and keep the order en in the entries
		//Collections.sort(cartData.getEntries(), new OrderEntryDataComparatorByCodeAndLocation());
		
		return cartData;
	}

	// NEORIS_CHANGE #67 Added inner class to sort cart entries
	public class OrderEntryDataComparatorByCodeAndLocation extends AbstractComparator<OrderEntryData>
	{
		@Override
		protected int compareInstances(final OrderEntryData a, final OrderEntryData b)
		{
			int firstComparation = compareValues(a.getProduct().getVisibleCode(), b.getProduct().getVisibleCode(), false);

			if (firstComparation == 0)
				return compareValues(a.getProduct().getLocation().getCode(), b.getProduct().getLocation().getCode(), false);
			else
				return firstComparation;
		}
	}

	// NEORIS_CHANGE #75 When updating a cart entry we need to convert the
	// current unit to the base unit before update the quantity
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.commercefacades.order.impl.DefaultCartFacade#
	 * updateCartEntry(long, long)
	 */
	@Override
	public CartModificationData updateCartEntry(long entryNumber, double quantity) throws CommerceCartModificationException
	{
		final CartData cartData = getSessionCart();
		final OrderEntryData orderEntry = getEntryDataForNumber(cartData, (int) entryNumber);

		final ProductData productData = orderEntry.getProduct();
		final UnitModel defaUnitModel = neorisFacade.getCurrentUnit();
		
		Long convertedQuantity = neorisUoMQuantityConverter.convertInputQuantityFrom(defaUnitModel, quantity, productData);			
		return super.updateCartEntry(entryNumber, convertedQuantity);
	}

	private OrderEntryData getEntryDataForNumber(AbstractOrderData order, int number)
	{
		List<OrderEntryData> entries = order.getEntries();
		if ((entries != null) && (!(entries.isEmpty())))
		{
			Integer requestedEntryNumber = Integer.valueOf(number);
			for (OrderEntryData entry : entries)
			{
				if ((entry != null) && (requestedEntryNumber.equals(entry.getEntryNumber())))
				{
					return entry;
				}
			}
		}
		return null;
	}

	private AbstractOrderEntryModel getEntryModelForNumber(AbstractOrderModel order, int number)
	{
		List<AbstractOrderEntryModel> entries = order.getEntries();
		if ((entries != null) && (!(entries.isEmpty())))
		{
			Integer requestedEntryNumber = Integer.valueOf(number);
			for (AbstractOrderEntryModel entry : entries)
			{
				if ((entry != null) && (requestedEntryNumber.equals(entry.getEntryNumber())))
				{
					return entry;
				}
			}
		}
		return null;
	}

	public void updateRollingSchedule(int entryNumber, Date rollingScheduleWeek)
	{
		CartModel cartModel = getCartService().getSessionCart();
		List<AbstractOrderEntryModel> entries = cartModel.getEntries();

		if (entryNumber >= 0 && entryNumber < entries.size())
		{
			AbstractOrderEntryModel entry = getEntryModelForNumber(cartModel, entryNumber);
			entry.setRollingScheduleWeek(rollingScheduleWeek);
			modelService.save(entry);
		}
	}

	@Override
	public CartModificationData addToCart(String code, double quantityToAdd, ProlamsaAPIProductConfigurationModel configModel, Date rollingSchedule, UnitModel unitModel, String stockOuts) throws CommerceCartModificationException
	{
		ProductModel productModel = getProductService().getProductForCode(code);
		final CartModel cartModel = getCartService().getSessionCart();

		ServicesUtil.validateParameterNotNull(cartModel, "Cart model cannot be null");
		ServicesUtil.validateParameterNotNull(productModel, "Product model cannot be null");

		if (productModel.getVariantType() != null)
		{
			throw new CommerceCartModificationException("Choose a variant instead of the base product");
		}

		// get the product data with all inventory information
		ProductData productData = neorisProductConverter.convert(productModel);

		// convert the quantity to bundles if is needed
		Long quantity = neorisUoMQuantityConverter.convertInputQuantityFrom(unitModel, quantityToAdd, productData);
			
		if (quantity < 1L)
		{
			throw new CommerceCartModificationException("Quantity must not be less than one");
		}

		try
		{
			getProductService().getProductForCode(productModel.getCode());
		}
		catch (UnknownIdentifierException localUnknownIdentifierException)
		{
			CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode("unavailable");
			modification.setQuantityAdded(0L);
			modification.setQuantity(quantity);
			CartEntryModel entry = new CartEntryModel();
			entry.setProduct(productModel);
			modification.setEntry(entry);
			// return modification;
			return null;
		}

		if (configModel != null)
			modelService.save(configModel);

		CartEntryModel cartEntryModel = (CartEntryModel) neorisCartService.addNewEntry(cartModel, productModel, configModel, rollingSchedule, quantity, getDefaultUnit(), -1, !(false), quantityToAdd, stockOuts);

		modelService.save(cartEntryModel);

		CommerceCartModification modification = new CommerceCartModification();
		modification.setQuantityAdded(quantity);
		modification.setQuantity(quantity);

		if (cartEntryModel != null)
		{
			modification.setEntry(cartEntryModel);
		}

		return getCartModificationConverter().convert(modification);
	}

	@Override
	public void removeConfigurationFromOrderEntry(Integer entryNumber)
	{
		CartModel cartModel = getCartService().getSessionCart();

		AbstractOrderEntryModel orderEntry = cartModel.getEntries().get(entryNumber);

		updateAPIProductConfigurationForEntry(orderEntry, null);
	}

	public void updateAPIProductConfigurationForEntry(AbstractOrderEntryModel entry, ProlamsaAPIProductConfigurationModel configModel)
	{
		// save new configModel
		if (configModel != null)
			modelService.save(configModel);

		// get any previous existing configuration
		ProlamsaAPIProductConfigurationModel currentConfig = entry.getApiProductConfiguration();

		// set the new configModel and save the entry
		entry.setApiProductConfiguration(configModel);
		modelService.save(entry);

		// remove previous if exists
		if (currentConfig != null)
			modelService.remove(currentConfig);
	}

	/**
	 * @return the neorisFacade
	 */
	public NeorisFacade getNeorisFacade()
	{
		return neorisFacade;
	}

	/**
	 * @param neorisFacade
	 *            the neorisFacade to set
	 */
	public void setNeorisFacade(NeorisFacade neorisFacade)
	{
		this.neorisFacade = neorisFacade;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisCartFacade#saveCalculatePrice(de.hybris.platform.commercefacades.order.data.CartData)
	 */
	@Override
	public void saveCalculatePrice(CartData cartData) {
		
		CartModel cartModel = cartService.getSessionCart();
		
		cartModel.setHotRolledWeight(cartData.getHotRolledWeight()); 
		cartModel.setColdRolledWeight(cartData.getColdRolledWeight());
		cartModel.setGalvanizedWeight(cartData.getGalvanizedWeight());
		cartModel.setAluminizedWeight(cartData.getAluminizedWeight());
		cartModel.setGalvametalWeight(cartData.getGalvametalWeight());
		cartModel.setTotalWeight(cartData.getTotalWeight());

		//set prices categories
		cartModel.setHotRolledPrice(cartData.getHotRolledPrice());
		cartModel.setColdRolledPrice(cartData.getColdRolledPrice());
		cartModel.setGalvanizedPrice(cartData.getGalvanizedPrice());
		cartModel.setGalvametalPrice(cartData.getGalvametalPrice());
		cartModel.setAluminizedPrice(cartData.getAluminizedPrice());
		cartModel.setSapTotalPrice(cartData.getSapTotalPrice());
		cartModel.setSapSubtotalPrice(cartData.getSapSubtotalPrice());
		
		//set taxas, delivery cost and assurance totals
		cartModel.setTotalAssurance(cartData.getTotalAssurance());
		cartModel.setTotalDeliveryCost(cartData.getTotalDeliveryCost());
		cartModel.setTotalTaxas(cartData.getTotalTaxas());
		
		//set the SAP Weight Unit and Currency
		cartModel.setSapLabelWeightUnit(cartData.getSapLabelWeightUnit());
		cartModel.setSapWeightUnit(cartData.getSapWeightUnit());
		cartModel.setSapCurrency(cartData.getSapCurrency());
		
		//cartModel.setSemaphoreCredit(cartData.get);
		
		for (final OrderEntryData eachEntryData : cartData.getEntries())
		{
			final int entryNumber = eachEntryData.getEntryNumber();

			final CartEntryModel entryModel = (CartEntryModel) cartModel.getEntries().get(entryNumber);

			entryModel.setPricePerFeet(eachEntryData.getPricePerFeet());
			entryModel.setPricePerTon(eachEntryData.getPricePerTon());
			entryModel.setPricePerPc(eachEntryData.getPricePerPc());
			entryModel.setNetPriceWOTaxes(eachEntryData.getNetPriceWOTaxes());
			entryModel.setReadyToShip(eachEntryData.getReadyToShip());
			
			entryModel.setIsLastOnTraspotation(eachEntryData.getIsLastOnTraspotation());
			entryModel.setIsTransportationGroupFull(eachEntryData.getIsTransportationGroupFull());
			entryModel.setGroupNumber(eachEntryData.getGroupNumber());

			//Commented to keep the order of the items
			//orderEntryModel.setSapEntryNumber(eachEntryData.getSapEntryNumber());
			entryModel.setSapEntryNumber((entryNumber + 1) * 10);
			
			entryModel.setIsAvailableToNegotiatePrice(eachEntryData.getIsAvailableToNegotiatePrice());
			
			entryModel.setSteelCategory(eachEntryData.getSteelCategory());
			entryModel.setTotalTaxas(eachEntryData.getTotalTaxas());
			entryModel.setTotalAssurance(eachEntryData.getTotalAssurance());
			entryModel.setTotalDeliveryCost(eachEntryData.getTotalDeliveryCost());
			entryModel.setWeight(eachEntryData.getWeight());
		}
		
		modelService.save(cartModel);
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisCartFacade#setCartOwnership(de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public void setCartOwnership(B2BUnitModel owner) {
		if(owner == null)
		{
			return;
		}
		
		CartModel cartModel = cartService.getSessionCart();
		
		if(cartModel == null)
			return;
		
		if(cartModel.getUnitOwner() == null)
		{
			cartModel.setUnitOwner(owner);
			modelService.save(cartModel);
		}
	}

	/*
	@Override
	public void updateCartPlaceOrder() {
		
		CartModel cart = (CartModel) sessionService.getAttribute("cart");

		if (cart != null) {
			cart.setTryCreateOrder(true);
			modelService.save(cart);
		}
		
	}
	*/
}
