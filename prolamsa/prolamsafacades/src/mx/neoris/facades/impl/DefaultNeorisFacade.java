package mx.neoris.facades.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderDataHomePage;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.session.SessionService.SessionAttributeLoader;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.enums.Wishlist2EntryPriority;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;

import mx.neoris.core.enums.MeasurementSystemType;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.enums.Wishlist2Type;
import mx.neoris.core.jalo.NeorisB2BUnitSettingByStore;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.product.ProductVariantConfiguration;
import mx.neoris.core.services.B2BUnitSearchParameters;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.services.NeorisB2BUnitSearchService;
import mx.neoris.core.services.NeorisOrderHistoryService;
import mx.neoris.core.services.NeorisProductVisibilityService;
import mx.neoris.core.services.NeorisService;


import mx.neoris.core.services.NeorisWishlistService;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisB2BCustomerDefaultSettingsFacade;
import mx.neoris.facades.NeorisCartFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.facades.search.B2BProductSearchFacade;
import mx.neoris.facades.wishlist2entry.data.Wishlist2EntryData;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Lists;
import com.greycon.reports.ArrayOfReportParameter;
import com.greycon.reports.AuthenticationContract;
import com.greycon.reports.IReportProvider;
import com.greycon.reports.ReportParameter;
import com.greycon.reports.ReportProvider;

/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisFacade implements NeorisFacade {

	/**
	 * Field LOG.
	 */
	private static final Logger LOG = Logger
			.getLogger(DefaultNeorisFacade.class);

	private static final String PROLAMSA_INTERNAL_GROUP = "prolamsa_internal";
	private static final String PROLAMSA_EXTERNAL_GROUP = "prolamsa_external";
	private static final String PROLAMSA_NO_INVENTORY_GROUP = "prolamsa_no_inventory";
	private static final String CART_DRAFT_SESSIONSLOT = "Draft";

	private static final String PROLAMSA_FACETAUTOSELECTION_GROUP = "prolamsa_facetautoselection";
	private static final String PROLAMSA_MX_STORE_UID = "1000";

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource(name = "b2bProductFlexibleSearchFacade")
	private B2BProductSearchFacade<ProductData> b2bProductFlexibleSearchFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;

	private Converter<OrderModel, OrderData> orderConverter;

	@Resource(name = "neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2bCustomerService;

	@Resource(name = "neorisWishlistService")
	private NeorisWishlistService wishlistService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name = "neorisCartFacade")
	private NeorisCartFacade cartFacade;

	@Resource(name = "b2bUnitService")
	B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "neorisAddressConverter")
	private Converter<AddressModel, AddressData> addressConverter;

	@Resource(name = "neorisOrderConverter")
	private Converter<OrderModel, OrderData> neorisOrderConverter;

	@Resource(name = "neorisB2BUnitConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter;

	// NEORIS_CHANGE #55
	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	// NEORIS_CHANGE #82
	@Resource(name = "neorisB2BUnitSearchService")
	private NeorisB2BUnitSearchService neorisB2BUnitSearchService;

	// NEORIS_CHANGE #83
	@Resource(name = "neorisWishlist2EntryConverter")
	private Converter<Wishlist2EntryModel, Wishlist2EntryData> neorisWishlist2EntryConverter;

	@Resource(name = "neorisB2BUnitSearchConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2bUnitSearchConverter;

	@Resource(name = "neorisProductConverter")
	private AbstractConverter<ProductModel, ProductData> neorisProductConverter;

	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;

	@Resource(name = "neorisOrderHistoryService")
	private NeorisOrderHistoryService neorisOrderHistoryService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "categoryService")
	private CategoryService categoryService;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "neorisB2BCustomerDefaultSettingsFacade")
	NeorisB2BCustomerDefaultSettingsFacade neorisB2BCustomerDefaultSettingsFacade;
	
	@Resource(name = "neorisProductVisibilityService")
	private NeorisProductVisibilityService neorisProductVisibilityService;
	
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	public CartModel getCurrentCart() {
		return cartService.getSessionCart();
	}

	public BaseStoreModel getCurrentBaseStore() {
		return baseStoreService.getCurrentBaseStore();
	}

	public String getEnumerationNameFromEnumValue(HybrisEnumValue enumValue) {
		return enumerationService.getEnumerationName(enumValue,
				getCurrentLocale());
	}

	public String getMessageWithDefaultContext(final String resourceKey) {
		return messageSource.getMessage(resourceKey, null, getCurrentLocale());
	}

	public CurrencyModel getCurrentCurrency() {
		CurrencyModel currencyModel = sessionService
				.getAttribute(CURRENT_CURRENCY);
		B2BUnitModel b2bUnitModel = this.getRootUnit();
		BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		if (b2bUnitModel == null || baseStoreModel == null)
			return null;
		else {
			currencyModel = this.getCurrencyFor(b2bUnitModel, baseStoreModel);
			if (currencyModel == null) {
				currencyModel = baseStoreService.getCurrentBaseStore()
						.getDefaultCurrency();
			}
		}

		// Just Make Sure basestore has the same currency
		this.setCurrentCurrency(currencyModel);

		return currencyModel;
	}

	public Locale getCurrentLocale() {
		return i18nService.getCurrentLocale();
	}

	public B2BCustomerModel getCurrentCustomer() {
		return b2bCustomerService.getCurrentB2BCustomer();
	}

	@Override
	public List<AddressData> getCurrentCustomerB2BUnitAddresses(
			final String selectedUnitUid, final String sortBy,
			final String sortType) {
		List<AddressModel> allAddresses = null;
		List<AddressModel> filteredAddresses = new ArrayList<AddressModel>();

		if (StringUtils.isEmpty(selectedUnitUid)) {
			final B2BCustomerModel b2bCustomer = getCurrentCustomer();

			allAddresses = sessionService.executeInLocalView(
					new SessionExecutionBody() {
						@Override
						public List<AddressModel> execute() {
							return neorisService
									.getCurrentCustomerB2BUnitAddresses(
											b2bCustomer, sortBy, sortType);
						}
					}, userService.getAdminUser());
		} else {
			allAddresses = sessionService.executeInLocalView(
					new SessionExecutionBody() {
						@Override
						public List<AddressModel> execute() {
							return neorisService
									.getCurrentCustomerB2BUnitAddresses(
											selectedUnitUid, sortBy, sortType);
						}
					}, userService.getAdminUser());
		}

		if (allAddresses != null && allAddresses.size() > 0) {
			for (AddressModel eachAddress : allAddresses) {
				B2BUnitModel owner = (B2BUnitModel) (eachAddress.getOwner());
				if (owner.getBaseStores().contains(getCurrentBaseStore()))
					if (eachAddress.getBaseStores().contains(
							this.getCurrentBaseStore()))
						filteredAddresses.add(eachAddress);
			}
		}

		final List<AddressData> result = Converters.convertAll(
				filteredAddresses, addressConverter);

		return result;
	}

	@Override
	public List<AddressData> getShippingAddress(B2BUnitModel preDetailModel) {
		final List<AddressModel> col = new ArrayList<AddressModel>();

		if (preDetailModel != null) {
			for (final AddressModel eachAddress : preDetailModel.getAddresses()) {
				if (eachAddress.getShippingAddress())
					if (eachAddress.getBaseStores().contains(
							this.getCurrentBaseStore()))
						col.add(eachAddress);
			}
		}

		final List<AddressData> result = Converters.convertAll(col,
				addressConverter);
		Collections.sort(result, new AddressDataByFormattedAddress());
		return result;
	}

	public List<B2BUnitModel> getB2BUnitModelsFromCustomer(
			B2BCustomerModel b2bCustomer) {
		List<B2BUnitModel> all = neorisService
				.getB2BUnitModelsFromCustomer(b2bCustomer);
		List<B2BUnitModel> filtered = new ArrayList<B2BUnitModel>();

		for (B2BUnitModel eachUnit : all) {
			if (eachUnit.getBaseStores().contains(getCurrentBaseStore())) {
				filtered.add(eachUnit);
			}
		}

		return filtered;
	}

	public List<B2BUnitModel> getB2BUnitModelsFromCustomerAll(
			B2BCustomerModel b2bCustomer) {
		return neorisService.getB2BUnitModelsFromCustomerAll(b2bCustomer);
	}

	@Override
	public List<B2BUnitData> getFormattedB2BUnits() {
		final B2BCustomerModel b2bCustomer = getCurrentCustomer();

		List<B2BUnitModel> col = new ArrayList<B2BUnitModel>();
		List<B2BUnitModel> b2bUnitForCurrentStore = new ArrayList<B2BUnitModel>();

		if (b2bCustomer != null) {
			col = getB2BUnitModelsFromCustomer(b2bCustomer);

			for (B2BUnitModel eachB2BUnitModel : col) {
				if (eachB2BUnitModel.getBaseStores().contains(
						baseStoreService.getCurrentBaseStore())) {
					b2bUnitForCurrentStore.add(eachB2BUnitModel);
				}
			}
		}

		final List<B2BUnitData> result = Converters.convertAll(
				b2bUnitForCurrentStore, b2bUnitConverter);
		Collections.sort(result, new B2BUnitDataName());
		return result;
	}

	@Override
	public List<B2BUnitData> getFormattedB2BUnitsAll() {
		final B2BCustomerModel b2bCustomer = getCurrentCustomer();

		List<B2BUnitModel> col = new ArrayList<B2BUnitModel>();

		if (b2bCustomer != null) {
			col = getB2BUnitModelsFromCustomerAll(b2bCustomer);
		}

		final List<B2BUnitData> result = Converters.convertAll(col,
				b2bUnitConverter);
		Collections.sort(result, new B2BUnitDataName());
		return result;
	}

	@Override
	public List<B2BUnitData> getFormattedB2BUnitsSortBy() {
		final List<B2BUnitData> result = new ArrayList<B2BUnitData>();

		B2BUnitData unitData = createUnitData("code_ASC",
				"Delivery Code (ascending)");
		result.add(unitData);
		unitData = createUnitData("code_DESC", "Delivery Code (descending)");
		result.add(unitData);
		unitData = createUnitData("name_ASC", "Delivery Name (ascending)");
		result.add(unitData);
		unitData = createUnitData("name_DESC", "Delivery Name (descending)");
		result.add(unitData);

		return result;
	}

	private B2BUnitData createUnitData(final String code, final String name) {
		B2BUnitData unitData = new B2BUnitData();
		unitData.setUid(code);
		unitData.setName(name);

		return unitData;
	}

	public B2BUnitModel getRootUnit() {
		return sessionService.getAttribute(B2BUNIT_SLOT);
	}

	@Override
	public String setRootUnitWithId(final String rootUnit) {
		final B2BUnitModel b2bUnit = sessionService.executeInLocalView(
				new SessionExecutionBody() {
					@Override
					public B2BUnitModel execute() {
						return getB2BUnitWithUid(rootUnit);
					}
				}, userService.getAdminUser());

		if (b2bUnit == null) {
			return null;
		}

		b2bUnitService.setCurrentUnit(getCurrentCustomer(), b2bUnit);
		sessionService.setAttribute(B2BUNIT_SLOT, b2bUnit);
		// if (b2bUnit.getCurrency() != null)
		// commonI18NService.setCurrentCurrency(b2bUnit.getCurrency());

		this.setCurrentCurrency(this.getCurrencyFor(b2bUnit,
				getCurrentBaseStore()));
		CartModel cart = (CartModel) sessionService.getAttribute("cart");

		if (cart != null) {
			cart.setUnitOwner(b2bUnit);
			modelService.save(cart);
		}
		
		// place b2bcustomer default settings on session after set root unit
		placeDefaultSettingsAfterSetRootUnit();
		
		return b2bUnit.getUid();
	}
	
	private void placeDefaultSettingsAfterSetRootUnit()
	{
		final B2BCustomerModel b2bCustomer = getCurrentCustomer();
		final BaseStoreModel baseStore = getCurrentBaseStore();
		final B2BUnitModel b2bUnit = getRootUnit();
		
		final NeorisB2BCustomerDefaultSettingModel defaultSettings = neorisB2BCustomerDefaultSettingsFacade.getSetting(b2bCustomer, b2bUnit, baseStore);
		sessionService.setAttribute(B2BCUSTOMER_DEFAULT_SETTINGS_SLOT, defaultSettings);
		
		// clean session attributes
		sessionService.removeAttribute(PRODUCTUNIT_SLOT);
	}

	@Override
	public String setUnitWithId(String unitCode) {
		final UnitModel unit = unitService.getUnitForCode(unitCode);

		if (unit == null) {
			return null;
		}

		sessionService.setAttribute(PRODUCTUNIT_SLOT, unit);

		return unit.getCode();
	}

	public B2BUnitModel getB2BUnitWithUid(final String uid) {
		return b2bUnitService.getUnitForUid(uid);
	}

	@Override
	public List<UnitModel> getAllUnits() {
		final BaseStoreModel baseStore = getCurrentBaseStore();

		final List<UnitModel> result = baseStore.getUnits();

		return result;
	}

	@Override
	public HybrisEnumValue getCurrentSystem() {
		return sessionService.getAttribute(SYSTEM_UNIT_SLOT);
	}

	@Override
	public List<UnitModel> getUnitBySystem(String system) {
		final BaseStoreModel baseStore = getCurrentBaseStore();

		List<UnitModel> result = new ArrayList<UnitModel>();

		for (UnitModel unit : baseStore.getUnits()) {
			if (unit.getDefaultMeasurementSystem().getCode()
					.equalsIgnoreCase(system)) {
				result.add(unit);
			}
		}

		return result;
	}

	public class UnitModelComparator extends AbstractComparator<UnitModel> {
		Locale locale;

		public UnitModelComparator(final Locale localeParam) {
			locale = localeParam;
		}

		@Override
		protected int compareInstances(final UnitModel a, final UnitModel b) {
			return compareValues(a.getName(locale), b.getName(locale), false);
		}
	}

	public class B2BUnitDataName extends AbstractComparator<B2BUnitData> {
		@Override
		protected int compareInstances(final B2BUnitData a, final B2BUnitData b) {
			return compareValues(a.getName(), b.getName(), false);
		}
	}

	public class AddressDataByFormattedAddress extends
			AbstractComparator<AddressData> {
		@Override
		protected int compareInstances(final AddressData a, final AddressData b) {
			return compareValues(a.getFormattedAddress(),
					b.getFormattedAddress(), false);
		}
	}

	@Override
	public Wishlist2Model saveWishlist(final String name, final UserModel user,
			final UnitModel unitModel, final B2BUnitModel b2bUnit,
			final CartModel cart) throws Exception {
		int cartTotalEntries = 0;

		if (cart.getEntries().size() == 0) {
			throw new Exception("Template cart cannot be empty");
		}

		if (name.length() == 0) {
			throw new Exception("Template name cannot be empty");
		}

		final Wishlist2Model newWishList = new Wishlist2Model();
		newWishList.setName(name);
		newWishList.setB2bUnit(b2bUnit);
		newWishList.setUnit(unitModel);
		newWishList.setUser(user);
		newWishList.setType(Wishlist2Type.DRAFT);

		modelService.save(newWishList);

		cartTotalEntries = cart.getEntries().size();

		Date date = new Date();

		for (int i = 0; i < cartTotalEntries; i++) {
			final Wishlist2EntryModel newWishListEntry = new Wishlist2EntryModel();

			CartEntryModel eachCartEntry = (CartEntryModel) cart.getEntries()
					.get(i);

			newWishListEntry.setProduct(eachCartEntry.getProduct());
			newWishListEntry.setComment(eachCartEntry.getProduct().getCode());
			newWishListEntry.setDesired(eachCartEntry.getQuantity().intValue());
			newWishListEntry.setPriority(Wishlist2EntryPriority.LOW);
			newWishListEntry.setAddedDate(date);

			newWishListEntry.setApiProductConfiguration(eachCartEntry
					.getApiProductConfiguration());
			newWishListEntry.setRollingScheduleWeek(eachCartEntry
					.getRollingScheduleWeek());

			newWishListEntry.setWishlist(newWishList);

			modelService.save(newWishListEntry);
		}

		return newWishList;
	}

	@Override
	public Wishlist2Model saveTemplateOrder(String name, String orderCode,
			UserModel user, B2BUnitModel b2bUnit, UnitModel unit,
			OrderModel orderDetails) throws Exception {
		int totalEntries = 0;

		if (orderDetails.getEntries().size() == 0) {
			throw new Exception("Template cannot be empty");
		}

		if (name.length() == 0) {
			throw new Exception("Template name cannot be empty");
		}

		final Wishlist2Model newWishList = new Wishlist2Model();
		newWishList.setName(name);
		newWishList.setOrderCode(orderCode);
		newWishList.setB2bUnit(b2bUnit);
		newWishList.setUnit(unit);
		newWishList.setUser(user);
		newWishList.setType(Wishlist2Type.TEMPLATE);

		modelService.save(newWishList);

		totalEntries = orderDetails.getEntries().size();

		Date date = new Date();

		for (int i = 0; i < totalEntries; i++) {
			final Wishlist2EntryModel newWishListEntry = new Wishlist2EntryModel();
			OrderEntryModel eachOrderEntry = (OrderEntryModel) orderDetails
					.getEntries().get(i);

			newWishListEntry.setProduct(eachOrderEntry.getProduct());
			newWishListEntry.setComment(eachOrderEntry.getProduct().getCode());
			newWishListEntry
					.setDesired(eachOrderEntry.getQuantity().intValue());
			newWishListEntry.setPriority(Wishlist2EntryPriority.LOW);
			newWishListEntry.setAddedDate(date);
			newWishListEntry.setApiProductConfiguration((eachOrderEntry
					.getApiProductConfiguration()));
			newWishListEntry.setRollingScheduleWeek(eachOrderEntry
					.getRollingScheduleWeek());
			newWishListEntry.setWishlist(newWishList);

			modelService.save(newWishListEntry);
		}

		return newWishList;
	}

	@Override
	public List<Wishlist2Model> getWishlistForUserAndB2BUnit(
			final UserModel user, final B2BUnitModel unit) {
		return wishlistService.getWishlistForUserAndB2BUnit(user, unit);
	}

	// NEORIS_CHANGE #83
	public List<Wishlist2EntryData> getWishlistEntry(final String wishlist) {
		List<Wishlist2EntryModel> col = wishlistService
				.getWishlistEntry(wishlist);
		List<Wishlist2EntryData> result = new ArrayList<Wishlist2EntryData>();

		for (final Wishlist2EntryModel wishModel : col) {
			UnitModel currentUnit = this.getCurrentUnit();

			Wishlist2EntryData wishData = new Wishlist2EntryData();
			neorisWishlist2EntryConverter.convert(wishModel, wishData);
			Double convertedQuantity = neorisUoMQuantityConverter
					.convertOutputQuantityFrom(currentUnit,
							Long.valueOf(wishData.getDesired()),
							wishData.getProduct());
			wishData.setConvertedQuantity(convertedQuantity);
			result.add(wishData);
		}

		return result;
	}

	public UserModel getCurrentUser() {
		return getUserService().getCurrentUser();
	}
	
	@Override
	public boolean isInternalCustomer()
	{
		UserModel currentUser = getCurrentUser();

		UserGroupModel prolamsaInternalGroup = getUserService()
				.getUserGroupForUID(PROLAMSA_INTERNAL_GROUP);

		return getUserService().isMemberOfGroup(currentUser, prolamsaInternalGroup);
	}

	public String getCurrentCustomerType() {
		UserModel currentUser = getCurrentUser();

		// Added to handle no inventory role
		UserGroupModel prolamsaNoInventoryGroup = getUserService()
				.getUserGroupForUID(PROLAMSA_NO_INVENTORY_GROUP);

		if (getUserService().isMemberOfGroup(currentUser,
				prolamsaNoInventoryGroup))
			return PROLAMSA_NO_INVENTORY_GROUP;
		// ///////

		UserGroupModel prolamsaInternalGroup = getUserService()
				.getUserGroupForUID(PROLAMSA_INTERNAL_GROUP);

		if (getUserService()
				.isMemberOfGroup(currentUser, prolamsaInternalGroup))
			return PROLAMSA_INTERNAL_GROUP;
		else
			return PROLAMSA_EXTERNAL_GROUP;
	}

	public boolean currentUserIsMemberOfUserGroup(String userGroup) {
		UserModel currentUser = getCurrentUser();

		UserGroupModel userGroupModel = getUserService().getUserGroupForUID(
				userGroup);

		if (getUserService().isMemberOfGroup(currentUser, userGroupModel)) {
			return true;
		}

		return false;
	}

	@Override
	// NEORIS_CHANGE #55
	public List<String> getProlamsaSkuFromSku(final List<String> codes,
			final B2BUnitModel unit, final String catalogId) {
		final List<String> listResult = new ArrayList<String>();

		final Map<String, CustomerProductReferenceModel> mapResult = customerProductReferenceService
				.getWithUnitCodesAndB2BUnit(codes, unit, catalogId);

		for (final String dataCode : codes) {
			final CustomerProductReferenceModel reference = mapResult
					.get(dataCode);

			if (reference != null) {
				listResult.add(reference.getProductCode());
			} else {
				listResult.add(dataCode);
			}
		}

		return listResult;
	}

	@Override
	public Wishlist2Model loadCartFromWishlist(final Long wishlistPK)
			throws Exception {
		// search wishlist by its PK
		Wishlist2Model wishList = (Wishlist2Model) modelService.get(PK
				.fromLong(wishlistPK));

		// always protect objects being found by PK or id
		if (wishList == null)
			throw new Exception("wishlist not found for pk " + wishlistPK);

		// Clean last saved cart for current user and current basesite
		cartFacade.cleanSavedCart();

		UnitModel currentUnit = this.getCurrentUnit();

		UnitModel wishListUnit = wishList.getUnit();
		if (wishListUnit != null) {
			currentUnit = wishListUnit;
		}

		// add wishlist entries to current cart
		for (Wishlist2EntryModel eachWishlistEntry : wishList.getEntries()) {
			ProductModel product = eachWishlistEntry.getProduct();

			String productCode = eachWishlistEntry.getComment();

			// Bundles o pieces quantity
			Long quantity = Long.valueOf(eachWishlistEntry.getDesired());

			if (product instanceof ProlamsaProductModel) {
				ProlamsaProductModel prolamsaProduct = (ProlamsaProductModel) product;

				ProductLocation productLocation = prolamsaProduct.getLocation();
				Date rollingScheduleDate = eachWishlistEntry
						.getRollingScheduleWeek();
				ProlamsaAPIProductConfigurationModel config = eachWishlistEntry
						.getApiProductConfiguration();

				// get product data
				ProductData productData = neorisProductConverter
						.convert(prolamsaProduct);

				Double convertedQuantity = neorisUoMQuantityConverter
						.convertOutputQuantityFrom(currentUnit, quantity,
								productData);
				neorisProductFacade.injectProductInventoryEntriesOn(
						Lists.newArrayList(productData),
						this.getCurrentCustomerType());

				neorisUoMQuantityConverter
						.injectInventoryConvertedQuantitiesOn(currentUnit,
								Lists.newArrayList(productData));

				// if rolling schedule defined, only add the product to the cart
				// if rolling schedule date exists on the actual inventory
				// information

				ProductInventoryEntry eachInventoryEntry = productData
						.getInventoryEntry();
				
				String stockOuts = eachInventoryEntry.getStockOuts();

				if (rollingScheduleDate != null) {

					// if wishlist entry location matches
					if (productLocation
							.equals(eachInventoryEntry.getLocation())) {
						if (this.getCurrentCustomerType().equalsIgnoreCase(
								"prolamsa_no_inventory")) {
							rollingScheduleDate = null;

							List<Double> quantities = eachInventoryEntry
									.getNoInventoryRoleBundlesCol();
							if (!hasValidInventory(quantities))// if there is no
																// valid
																// quantiues
																// ignore it
							{
								continue;
							} else if (!eachInventoryEntry
									.getRollingScheduleBundlesCol().contains(
											convertedQuantity))// if the
																// quantity is
																// not valid any
																// more adjust
																// it
							{
								convertedQuantity = eachInventoryEntry
										.getNoInventoryRoleBundlesCol()
										.get(eachInventoryEntry
												.getNoInventoryRoleBundlesCol()
												.size() - 1);
							}
						} else {
							if (eachInventoryEntry.getRollingScheduleDates() == null
									|| eachInventoryEntry
											.getRollingScheduleDates()
											.isEmpty())// if there are not
														// availbale rolling
														// weeks ignore it
							{
								continue;
							} else if (!eachInventoryEntry
									.getRollingScheduleDates().contains(
											rollingScheduleDate))// if the date
																	// is not
																	// avaible
																	// adjust it
							{

								rollingScheduleDate = eachInventoryEntry
										.getRollingScheduleDates().get(0);
							}

							List<Double> quantities = eachInventoryEntry
									.getRollingScheduleBundlesCol();
							if (!hasValidInventory(quantities))// if there is no
																// valid
																// quantiues
																// ignore it
							{
								continue;
							} else if (!eachInventoryEntry
									.getRollingScheduleBundlesCol().contains(
											convertedQuantity))// if the
																// quantity is
																// not valid any
																// more adjust
																// it
							{
								convertedQuantity = eachInventoryEntry
										.getRollingScheduleBundlesCol()
										.get(eachInventoryEntry
												.getRollingScheduleBundlesCol()
												.size() - 1);
							}
						}

						cartFacade.addToCart(productCode, convertedQuantity,
								config, rollingScheduleDate, currentUnit, stockOuts);
					}
				} else// STOCK
				{
					if (productLocation
							.equals(eachInventoryEntry.getLocation())) {
						List<Double> quantities;
						if (PROLAMSA_INTERNAL_GROUP.equalsIgnoreCase(this
								.getCurrentCustomerType()))
							quantities = eachInventoryEntry
									.getAvailableStockBundlesColInternal();
						else
							quantities = eachInventoryEntry
									.getAvailableStockBundlesCol();

						if (this.getCurrentCustomerType().equalsIgnoreCase(
								"prolamsa_no_inventory"))
							quantities = eachInventoryEntry
									.getNoInventoryRoleBundlesCol();

						if (hasValidInventory(quantities))// there is valid
															// inventory
						{
							if (!quantities.contains(convertedQuantity))// if
																		// there
																		// is
																		// not
																		// enough
																		// inventory
																		// adjust
																		// it to
																		// the
																		// max!
							{
								convertedQuantity = quantities.get(quantities
										.size() - 1);
							}

						} else // there is no stock then create rolling schedule
						{
							if (eachInventoryEntry.getRollingScheduleDates() == null
									|| eachInventoryEntry
											.getRollingScheduleDates()
											.isEmpty())// if there is not
														// rolling ignore the
														// entry
							{
								continue;
							} else {
								rollingScheduleDate = eachInventoryEntry
										.getRollingScheduleDates().get(0);
								quantities = eachInventoryEntry
										.getRollingScheduleBundlesCol();
								if (hasValidInventory(quantities)) {
									if (!quantities.contains(convertedQuantity)) {
										convertedQuantity = quantities
												.get(quantities.size() - 1);
									}
								} else {
									continue;
								}
							}
						}

						cartFacade.addToCart(productCode, convertedQuantity,
								config, rollingScheduleDate, currentUnit, stockOuts);
					}
				}

			} else {
				cartFacade.addToCart(productCode, quantity);
			}
		}

		// return the found wishList
		return wishList;
	}

	protected boolean hasValidInventory(List<Double> inventory) {
		if (inventory == null)
			return false;

		if (inventory.size() == 0)
			return false;

		if (inventory.size() == 1 && inventory.get(0) == 0.0)
			return false;

		return true;
	}

	@Override
	public void deleteWishlist(final Long wishlistPK) throws Exception {
		// search wishlist by its PK
		Wishlist2Model wishList = (Wishlist2Model) modelService.get(PK
				.fromLong(wishlistPK));

		// always protect objects PK or id
		if (wishList == null)
			throw new Exception("could not erase wishlist with pk "
					+ wishlistPK);

		modelService.remove(wishList);

	}

	@Override
	public Boolean getExistWishlist(final String name, final UserModel user,
			final B2BUnitModel unit) {
		return wishlistService.getExistWishlist(name, user, unit);
	}

	@Override
	public Boolean getExistTemplate(final String orderCode) {
		return wishlistService.getExistTemplate(orderCode);
	}

	@Override
	public void emptyCartFromSession(final CartModel currentCart)
			throws Exception {
		// always protect objects PK or id
		if (currentCart == null)
			throw new Exception("could not erase cart");
		
		// clean attachments
		if(CollectionUtils.isNotEmpty(currentCart.getAttachmentsPO()))
		{
			currentCart.setAttachmentsPO(new ArrayList<NeorisMediaModel>());
		}
		
		// clean delivery address
		currentCart.setDeliveryAddress(null);
		// clean transportation modes
		currentCart.setTransportationMode(null);
		
		modelService.save(currentCart);
		
		cartFacade.cleanSavedCart();

		// NEORIS_CHANGE #64 Added code to remove possible reference between
		// current cart and a template.
		Wishlist2Model wishList = (Wishlist2Model) sessionService
				.getAttribute(CART_DRAFT_SESSIONSLOT);
		if (wishList != null)
			sessionService.removeAttribute(CART_DRAFT_SESSIONSLOT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getClientUnitCodesAndB2BUnit(java.util
	 * .List, de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public Map<String, String> getClientUnitCodesAndB2BUnit(List<String> codes,
			B2BUnitModel unit, String catalogId) {
		final Map<String, String> mapResult = customerProductReferenceService
				.getClientUnitCodesAndB2BUnit(codes, unit, catalogId);

		return mapResult;
	}

	public SearchPageData<B2BUnitData> getB2BUnitBySearch(
			final B2BUnitSearchParameters searchParameters) throws Exception {
		SearchPageData<B2BUnitModel> pageModel = null;

		List<B2BUnitData> units = new ArrayList<B2BUnitData>();

		// search units on the current base store
		BaseStoreModel currentBaseStore = getCurrentBaseStore();
		if (currentBaseStore != null) {
			searchParameters
					.setBaseStorePk(currentBaseStore.getPk().toString());
		}

		// perform search as admin user to get all results and avoid
		// restrictions
		pageModel = sessionService.executeInLocalView(
				new SessionExecutionBody() {
					@Override
					public SearchPageData<B2BUnitModel> execute() {
						try {
							return neorisB2BUnitSearchService
									.getB2BUnitBySearch(searchParameters);
						} catch (final Exception e) {
							LOG.error(e.getMessage());
						}
						return null;
					}
				}, userService.getAdminUser());

		for (final B2BUnitModel b2bModel : pageModel.getResults()) {
			B2BUnitData b2bUnitData = new B2BUnitData();
			b2bUnitSearchConverter.convert(b2bModel, b2bUnitData);

			units.add(b2bUnitData);

			// assign the address list first, as there may be a problem to find
			// a billing address for the b2bunit
			List<AddressData> list = new ArrayList<AddressData>();
			b2bUnitData.setAddresses(list);
			modelService.refresh(b2bModel);

			AddressModel addressModel = getBillingAddress(b2bModel);

			if (addressModel != null) {
				AddressData addressData = new AddressData();
				addressConverter.convert(addressModel, addressData);

				list.add(addressData);
			} else {
				LOG.error("Billing Address not found for b2bUnit uid: "
						+ b2bModel.getUid());
			}
		}

		return convertToPageData(pageModel, units);
	}

	private SearchPageData<B2BUnitData> convertToPageData(
			final SearchPageData<B2BUnitModel> objData,
			final List<B2BUnitData> units) {
		final SearchPageData<B2BUnitData> result = new SearchPageData<B2BUnitData>();
		result.setPagination(objData.getPagination());
		result.setResults(units);
		result.setSorts(objData.getSorts());

		return result;
	}

	/**
	 * @return the b2bUnitSearchConverter
	 */
	public Converter<B2BUnitModel, B2BUnitData> getB2bUnitSearchConverter() {
		return b2bUnitSearchConverter;
	}

	/**
	 * @param b2bUnitSearchConverter
	 *            the b2bUnitSearchConverter to set
	 */
	public void setB2bUnitSearchConverter(
			Converter<B2BUnitModel, B2BUnitData> b2bUnitSearchConverter) {
		this.b2bUnitSearchConverter = b2bUnitSearchConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getBillingAddress(de.hybris.platform.b2b
	 * .model.B2BUnitModel)
	 */
	@Override
	public AddressModel getBillingAddress(B2BUnitModel unit) {
		AddressModel addressModel = unit.getBillingAddress();

		if (addressModel != null)
			return addressModel;

		for (AddressModel eachAddress : unit.getAddresses())
			if (eachAddress.getBillingAddress()
					&& eachAddress.getBaseStores().contains(
							this.getCurrentBaseStore()))
				return eachAddress;

		return null;
	}

	public OrderModel getOrderFromCode(String code) {
		return b2bOrderService.getOrderForCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getAddresessForSearch(mx.neoris.core.services
	 * .B2BUnitSearchParameters)
	 */
	@Override
	public List<AddressData> getAddresessForSearch(
			B2BUnitSearchParameters searchParameters) {

		List<AddressModel> listModel = neorisB2BUnitSearchService
				.getAddresessForSearch(searchParameters);
		List<AddressData> listData = new ArrayList<AddressData>();

		for (AddressModel addressModel : listModel) {
			AddressData data = new AddressData();

			addressConverter.convert(addressModel, data);
			listData.add(data);
		}

		return listData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getAddresessForSearch(mx.neoris.core.services
	 * .B2BUnitSearchParameters)
	 */
	@Override
	public List<AddressData> getAddresessForSearch(
			B2BUnitSearchParameters searchParameters, List<String> listPk) {

		List<AddressModel> listModel = neorisB2BUnitSearchService
				.getAddresessForSearch(searchParameters, listPk);
		List<AddressData> listData = new ArrayList<AddressData>();

		for (AddressModel addressModel : listModel) {
			AddressData data = new AddressData();

			addressConverter.convert(addressModel, data);
			listData.add(data);
		}

		return listData;
	}

	/*
	 * @Override public void updateCustomer(final CustomerData customerData)
	 * throws DuplicateUidException {
	 * validateParameterNotNullStandardMessage("customerData", customerData);
	 * Assert.hasText(customerData.getTitleCode(),
	 * "The field [TitleCode] cannot be empty");
	 * Assert.hasText(customerData.getFirstName(),
	 * "The field [FirstName] cannot be empty");
	 * Assert.hasText(customerData.getLastName(),
	 * "The field [LastName] cannot be empty"); final B2BCustomerModel
	 * customerModel; if (StringUtils.isEmpty(customerData.getUid())) {
	 * customerModel = modelService.create(B2BCustomerModel.class); } else {
	 * customerModel = b2bCustomerService.getUserForUID(customerData.getUid());
	 * }
	 * 
	 * neorisUserPopulator.populate(customerData, customerModel);
	 * modelService.save(customerModel); }
	 */

	public SearchPageData<OrderData> getPagedOrderHistory(
			OrderHistorySearchParameters searchParameters,
			B2BCustomerModel customer, List<OrderStatus> statusList) {
		SearchPageData<OrderModel> pageModel = null;

		List<OrderData> units = new ArrayList<OrderData>();
		final SearchPageData<OrderData> result = new SearchPageData<OrderData>();

		try {
			pageModel = neorisOrderHistoryService.getPagedOrderHistory(
					searchParameters, customer, statusList);

			for (final OrderModel orderModel : pageModel.getResults()) {
				OrderData orderData = new OrderData();
				neorisOrderConverter.convert(orderModel, orderData);
				units.add(orderData);
			}

			result.setPagination(pageModel.getPagination());
			result.setResults(units);
			result.setSorts(pageModel.getSorts());
		} catch (final Exception e) {
			LOG.error(e.getMessage());
		}

		return result;
	}

	public ProlamsaAPIProductConfigurationModel getProductConfigurationModelFromSessionWithCode(
			String productCode) {
		Map<String, ProductVariantConfiguration> map = getReadWriteProductVariantConfigurationMap();

		// if no map
		if (map == null)
			return null;

		ProductVariantConfiguration sessionConfig = map.get(productCode);

		// if no configuration
		if (sessionConfig == null)
			return null;

		ProlamsaAPIProductConfigurationModel config = getProductConfigurationModelFrom(sessionConfig);

		// remove the configuration from the session
		map.remove(productCode);
		setProductVariantConfigurationMap(map);

		return config;
	}

	public ProlamsaAPIProductConfigurationModel getProductConfigurationModelFrom(
			ProductVariantConfiguration sessionConfig) {
		ProlamsaAPIProductConfigurationModel config = new ProlamsaAPIProductConfigurationModel();

		// TODO
		// property values can be null, so validate those toString() calls
		if (StringUtils.isNotBlank(sessionConfig.getPressure()))
			config.setPressure(Integer.parseInt(sessionConfig.getPressure()));

		if (StringUtils.isNotBlank(sessionConfig.getDuration()))
			config.setDuration(Integer.parseInt(sessionConfig.getDuration()));

		if (sessionConfig.getSpecificStencil() != null)
			config.setSpecificStencil(sessionConfig.getSpecificStencil());

		if (StringUtils.isNotBlank(sessionConfig.getDrifter()))
			config.setSpecialDrifter(Double.parseDouble(sessionConfig
					.getDrifter()));

		if (StringUtils.isNotBlank(sessionConfig.getLength()))
			config.setSpecificLength(Double.parseDouble(sessionConfig
					.getLength()));

		if (sessionConfig.getLocationOfTest() != null)
			config.setLocationOfTest(sessionConfig.getLocationOfTest());

		if (sessionConfig.getSampleDirection() != null)
			config.setSampleDirection(sessionConfig.getSampleDirection());

		if (StringUtils.isNotBlank(sessionConfig.getTestTemp()))
			config.setTestTemp(Integer.parseInt(sessionConfig.getTestTemp()));

		if (sessionConfig.getSampleSize() != null)
			config.setSampleSize(sessionConfig.getSampleSize());

		return config;
	}

	public ProductVariantConfiguration getProductConfigurationFrom(
			ProlamsaAPIProductConfigurationModel configModel) {
		ProductVariantConfiguration configuration = new ProductVariantConfiguration();

		// TODO
		// property values can be null, so validate those toString() calls
		if (configModel.getPressure() != null)
			configuration.setPressure(configModel.getPressure().toString());

		if (configModel.getDuration() != null)
			configuration.setDuration(configModel.getDuration().toString());

		if (configModel.getSpecificStencil() != null)
			configuration.setSpecificStencil(configModel.getSpecificStencil());

		if (configModel.getSpecialDrifter() != null)
			configuration
					.setDrifter(configModel.getSpecialDrifter().toString());

		if (configModel.getSpecificLength() != null)
			configuration.setLength(configModel.getSpecificLength().toString());

		if (configModel.getLocationOfTest() != null)
			configuration.setLocationOfTest(configModel.getLocationOfTest());

		if (configModel.getSampleDirection() != null)
			configuration.setSampleDirection(configModel.getSampleDirection());

		if (configModel.getTestTemp() != null)
			configuration.setTestTemp(configModel.getTestTemp().toString());

		if (configModel.getSampleSize() != null)
			configuration.setSampleSize(configModel.getSampleSize());

		return configuration;
	}

	@Override
	public List<B2BUnitData> getAllB2BUnits() {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getCurrentB2BCustomer();

		final List<B2BUnitData> col = new ArrayList<B2BUnitData>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups()) {
			if (group instanceof B2BUnitModel) {
				final B2BUnitModel b2bUnit = (B2BUnitModel) group;
				col.add(b2bUnitConverter.convert(b2bUnit));
			}
		}

		return col;

	}

	@Override
	public List<B2BUnitData> getB2BUnitsForUser(String userUID) {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getUserForUID(userUID);

		final List<B2BUnitData> col = new ArrayList<B2BUnitData>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups()) {
			if (group instanceof B2BUnitModel) {
				final B2BUnitModel b2bUnit = (B2BUnitModel) group;
				col.add(b2bUnitConverter.convert(b2bUnit));
			}
		}

		return col;

	}

	@Override
	public B2BUnitData getB2BUnitForUID(String uid) {
		B2BUnitModel b2bUnit = b2bUnitService.getUnitForUid(uid);
		return b2bUnitConverter.convert(b2bUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#setProductVariantConfigurationMap(java
	 * .util.Map)
	 */
	@Override
	public void setProductVariantConfigurationMap(
			Map<String, ProductVariantConfiguration> map) {
		sessionService.setAttribute(
				NeorisFacade.PRODUCT_VARIANT_CONFIGURATIONS, map);
	}

	@Override
	public Map<String, ProductVariantConfiguration> getProductVariantConfigurationMap() {
		return sessionService
				.getAttribute(NeorisFacade.PRODUCT_VARIANT_CONFIGURATIONS);
	}

	/*
	 * We need to create a new map and assign it to the session as the internal
	 * map collection becomes an unmodifiable collection by hybris
	 */
	@Override
	public Map<String, ProductVariantConfiguration> getReadWriteProductVariantConfigurationMap() {
		Map<String, ProductVariantConfiguration> map = getProductVariantConfigurationMap();
		Map<String, ProductVariantConfiguration> newMap = new HashMap<String, ProductVariantConfiguration>();

		if (map != null) {
			for (String key : map.keySet())
				newMap.put(key, map.get(key));
		}

		return newMap;
	}

	@Override
	public List<Wishlist2Model> getWishlistForOrderCodeAndB2BUnit(
			String orderCode, B2BUnitModel currentB2BUnit) {
		return wishlistService.getWishlistForOrderCodeAndB2BUnit(orderCode,
				currentB2BUnit);
	}

	@Override
	public Wishlist2Model updateTemplateOrder(String name,
			Wishlist2Model wishList) throws Exception {
		if (name.length() == 0) {
			throw new Exception("Template name cannot be empty");
		}

		CartModel cart = (CartModel) sessionService.getAttribute("cart");

		if (cart == null || cart.getEntries() == null
				|| cart.getEntries().size() == 0) {
			throw new Exception("Template cart cannot be empty");
		}

		int cartTotalEntries = cart.getEntries().size();

		wishList.setName(name);
		wishList.setEntries(new ArrayList<Wishlist2EntryModel>());
		modelService.save(wishList);

		Date date = new Date();

		for (int i = 0; i < cartTotalEntries; i++) {
			final Wishlist2EntryModel newWishListEntry = new Wishlist2EntryModel();

			CartEntryModel eachCartEntry = (CartEntryModel) cart.getEntries()
					.get(i);

			newWishListEntry.setProduct(eachCartEntry.getProduct());
			newWishListEntry.setComment(eachCartEntry.getProduct().getCode());
			newWishListEntry.setDesired(eachCartEntry.getQuantity().intValue());
			newWishListEntry.setPriority(Wishlist2EntryPriority.LOW);
			newWishListEntry.setAddedDate(date);

			newWishListEntry.setApiProductConfiguration(eachCartEntry
					.getApiProductConfiguration());
			newWishListEntry.setRollingScheduleWeek(eachCartEntry
					.getRollingScheduleWeek());

			newWishListEntry.setWishlist(wishList);

			modelService.save(newWishListEntry);
		}

		modelService.save(wishList);

		return wishList;
	}

	@Override
	public List<Wishlist2Model> getWishlistForNameAndB2BUnit(String name,
			B2BUnitModel currentB2BUnit) {
		return wishlistService.getWishlistForNameAndB2BUnit(name,
				currentB2BUnit);
	}

	@Override
	public void updateB2BUnits(CustomerData b2bCustomerData) {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getUserForUID(b2bCustomerData.getUid());

		Set<PrincipalGroupModel> groups = b2bCustomer.getGroups();

		Set<PrincipalGroupModel> newGroups = new HashSet<PrincipalGroupModel>();

		List<B2BUnitModel> unitModels = new ArrayList<B2BUnitModel>();

		// Getting all b2bUnits for the user to be edited
		for (final PrincipalGroupModel group : groups) {
			if (group instanceof B2BUnitModel) {
				final B2BUnitModel b2bUnit = (B2BUnitModel) group;
				unitModels.add(b2bUnit);
			} else {
				newGroups.add(group);
			}
		}

		// Adding new b2bUnits
		for (final B2BUnitData b2bUnitData : b2bCustomerData.getB2bUnits()) {
			B2BUnitModel b2bUnitModel = b2bUnitService
					.getUnitForUid(b2bUnitData.getUid());

			newGroups.add(b2bUnitModel);
		}

		b2bCustomer.setGroups(newGroups);
		b2bCustomer.setActive(b2bCustomerData.isActive());

		modelService.save(b2bCustomer);

	}

	@Override
	public void setBaseStore(CustomerData b2bCustomerData) {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getUserForUID(b2bCustomerData.getUid());

		List<BaseStoreModel> baseStores = new ArrayList<BaseStoreModel>();
		baseStores.add(getCurrentBaseStore());

		Set<BaseStoreModel> set = new HashSet<BaseStoreModel>(baseStores);

		b2bCustomer.setBaseStores(set);

		modelService.save(b2bCustomer);

	}

	@Override
	public String removeB2BUnitFromCurrentUser(final String unitUID) {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getCurrentB2BCustomer();

		Set<PrincipalGroupModel> groups = b2bCustomer.getGroups();
		Set<PrincipalGroupModel> newGroups = new HashSet<PrincipalGroupModel>();

		for (final PrincipalGroupModel group : groups) {
			if (group instanceof B2BUnitModel) {
				final B2BUnitModel b2bUnit = (B2BUnitModel) group;

				if (!group.getUid().equals(unitUID)) {
					newGroups.add(group);
				} else {
					// validate if b2bunit is multisite
					if (b2bUnit.getBaseStores().size() > 1) {
						return "text.account.favoriteCustomers.remove.multisite.error";
					}
				}
			} else {
				newGroups.add(group);
			}
		}

		b2bCustomer.setGroups(newGroups);
		modelService.save(b2bCustomer);

		return null;
	}

	@Override
	public String addB2BUnitToCurrentUser(final String unitUID) {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getCurrentB2BCustomer();

		B2BUnitModel unitModel = b2bUnitService.getUnitForUid(unitUID);

		// unit model not found
		if (unitModel == null) {
			return "text.account.favoriteCustomers.add.general.error";
		}

		// validate if b2b unit is already assigned to user
		boolean isAlreadyAdded = false;

		List<B2BUnitModel> customerb2bUnits = neorisService
				.getB2BUnitModelsForCustomerAndCurrentStore(b2bCustomer);

		for (final B2BUnitModel b2bUnitModel : customerb2bUnits) {
			if (b2bUnitModel.getUid().equals(unitUID)) {
				isAlreadyAdded = true;
				break;
			}
		}

		if (isAlreadyAdded) {
			return "text.account.favoriteCustomers.add.isAlreadyAdded.error";
		}

		// validate fav max limit set at base store level
		BaseStoreModel currentBaseStore = getCurrentBaseStore();
		Integer favCustomersMaxLimit = currentBaseStore
				.getFavoriteCustomersMaxLimit();
		if (favCustomersMaxLimit == null) {
			return "text.account.favoriteCustomers.add.general.error";
		}

		// validate total b2b units assigned to current user
		Integer totalNumberCustomerB2BUnits = getTotalNumberB2BUnitsCurrentUser();
		if (totalNumberCustomerB2BUnits == null) {
			return "text.account.favoriteCustomers.add.general.error";
		}

		// validate if max fav customers limits has been reached
		if (totalNumberCustomerB2BUnits.intValue() >= favCustomersMaxLimit
				.intValue()) {
			return "text.account.favoriteCustomers.add.max.error";
		}

		// since previous validations passed... then assign b2bunit
		Set<PrincipalGroupModel> groups = b2bCustomer.getGroups();
		Set<PrincipalGroupModel> newGroups = new HashSet<PrincipalGroupModel>();

		for (final PrincipalGroupModel group : groups) {
			newGroups.add(group);
		}

		newGroups.add(unitModel);
		b2bCustomer.setGroups(newGroups);

		// save model with new b2bunit assigned
		modelService.save(b2bCustomer);

		return null;
	}

	@Override
	public Integer getTotalNumberB2BUnitsCurrentUser() {
		B2BCustomerModel currentCustomer = getCurrentCustomer();
		List<B2BUnitModel> b2bunits = neorisService
				.getB2BUnitModelsForCustomerAndCurrentStore(currentCustomer);
		if (b2bunits != null) {
			return b2bunits.size();
		}

		return null;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService() {
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *            the customerAccountService to set
	 */
	public void setCustomerAccountService(
			CustomerAccountService customerAccountService) {
		this.customerAccountService = customerAccountService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService() {
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *            the baseStoreService to set
	 */
	public void setBaseStoreService(BaseStoreService baseStoreService) {
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the orderConverter
	 */
	public Converter<OrderModel, OrderData> getOrderConverter() {
		return orderConverter;
	}

	/**
	 * @param orderConverter
	 *            the orderConverter to set
	 */
	public void setOrderConverter(
			Converter<OrderModel, OrderData> orderConverter) {
		this.orderConverter = orderConverter;
	}

	/**
	 * @return the neorisOrderHistoryService
	 */
	public NeorisOrderHistoryService getNeorisOrderHistoryService() {
		return neorisOrderHistoryService;
	}

	/**
	 * @param neorisOrderHistoryService
	 *            the neorisOrderHistoryService to set
	 */
	public void setNeorisOrderHistoryService(
			NeorisOrderHistoryService neorisOrderHistoryService) {
		this.neorisOrderHistoryService = neorisOrderHistoryService;
	}

	@Override
	public UnitModel getCurrentUnit() 
	{
		
		// force to place b2bcustomer default settings on session after remember me
		final NeorisB2BCustomerDefaultSettingModel defaultSettings = getDefaultSettings();
		
		System.out.println("sessionService.getCurrentSession().getSessionId() en getCurrentUnit: " +sessionService.getCurrentSession().getSessionId());
		UnitModel currentUnit = sessionService.getAttribute(NeorisFacade.PRODUCTUNIT_SLOT);
		
		///////////////////////////
		//UnitModel currentUnit = new UnitModel();
				
		if (defaultSettings != null) {
			currentUnit = defaultSettings.getUom();
			sessionService.setAttribute(NeorisFacade.PRODUCTUNIT_SLOT,
					currentUnit);
		}else
		{
			B2BCustomerModel currentCustomerModel = this.getCurrentCustomer();
			if(currentCustomerModel != null && currentCustomerModel.getCustomerCurrentUMedida() != null)
			{	
				currentUnit = unitService.getUnitForCode(currentCustomerModel.getCustomerCurrentUMedida());
				sessionService.setAttribute(NeorisFacade.PRODUCTUNIT_SLOT,
						currentUnit);
			}	
		}
		//////////////////////////7
		

		// if current unit is null, assign default from b2bcustomer default
		// settings
		if (currentUnit == null) {
			if (defaultSettings != null) {
				currentUnit = defaultSettings.getUom();
			}

			// if current unit is null, assign the default defined on the
			// baseStore
			if (currentUnit == null) {
				BaseStoreModel baseStore = this.getCurrentBaseStore();

				if (baseStore != null) {
					currentUnit = baseStore.getUnit();
				}
			}

			// if no default unit, assign the first from the list
			if (currentUnit == null) {
				List<UnitModel> list = this.getAllUnits();
				if (list.size() > 0) {
					currentUnit = list.get(0);
					this.setUnitWithId(currentUnit.getCode());
				}
			} else {
				sessionService.setAttribute(NeorisFacade.PRODUCTUNIT_SLOT,
						currentUnit);
			}
		}

		return currentUnit;
	
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getXMLUpdateOrderStatus(java.util.List)
	 */
	@Override
	public void getXMLUpdateOrderStatus(List<OrderData> recentOrders) {
		OrderData x = new OrderData();
		x.setStatus(OrderStatus.APPROVED);

		if (recentOrders.isEmpty()) {
			recentOrders.add(x);
		}

		String nombre_archivo = "ORDER_DATA";
		/*
		 * ArrayList key = new ArrayList(); ArrayList value = new ArrayList();
		 * 
		 * key.add("opcion1"); value.add("22");
		 * 
		 * key.add("opcion2"); value.add("22");
		 * 
		 * key.add("opcion3"); value.add("22");
		 * 
		 * key.add("opcion4"); value.add("25");
		 * 
		 * try { generate(nombre_archivo, key, value); } catch (Exception e) {}
		 * }
		 */

		// public static void generate(String name, ArrayList<String>
		// key,ArrayList<String> value) throws Exception{
		try {

			if (recentOrders.isEmpty()) {
				System.out.println("ERROR empty ArrayList");
				return;
			} else {

				/*
				 * DocumentBuilderFactory factory =
				 * DocumentBuilderFactory.newInstance(); DocumentBuilder builder
				 * = factory.newDocumentBuilder(); DOMImplementation
				 * implementation = builder.getDOMImplementation(); Document
				 * document = implementation.createDocument(null,
				 * nombre_archivo, null); document.setXmlVersion("1.0");
				 */

				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				Document document = docBuilder.newDocument();

				// Main Node

				// Por cada key creamos un item que contendrá la key y el value
				for (int i = 0; i < recentOrders.size(); i++) {

					Element raiz = document.createElement(nombre_archivo);
					document.appendChild(raiz);

					// Element raiz = document.getDocumentElement();
					// document.appendChild(raiz);

					Attr attr = document.createAttribute("action");
					attr.setValue("Status");
					raiz.setAttributeNode(attr);

					Attr attr2 = document.createAttribute("type");
					attr2.setValue("S");
					raiz.setAttributeNode(attr2);

					Attr attr3 = document.createAttribute("id");
					attr3.setValue("1803924900");
					raiz.setAttributeNode(attr3);

					Attr attr4 = document.createAttribute("status");
					attr4.setValue("D");
					raiz.setAttributeNode(attr4);

					// Item Node
					// Element itemNode = document.createElement("ITEM");
					// Key Node
					// Element keyNode = document.createElement("KEY");
					// Text nodeKeyValue =
					// document.createTextNode("action=Status");
					// //x.getStatus().toString());
					// Text nodeKeyValue2 = document.createTextNode("type=S");
					// keyNode.appendChild(nodeKeyValue);
					// Value Node
					// Element valueNode = document.createElement("VALUE");
					// Text nodeValueValue =
					// document.createTextNode(value.get(i));
					// valueNode.appendChild(nodeValueValue);
					// append keyNode and valueNode to itemNode
					// itemNode.appendChild(keyNode);
					// itemNode.appendChild(valueNode);
					// append itemNode to raiz
					document.appendChild(raiz); // pegamos el elemento a la raiz
												// "Documento"
				}
				// Generate XML
				Source source = new DOMSource(document);
				// Indicamos donde lo queremos almacenar
				Result result = new StreamResult(new java.io.File(
						"D:\\Documents\\Escritorios\\" + nombre_archivo
								+ ".xml")); // nombre del archivo
				Transformer transformer = TransformerFactory.newInstance()
						.newTransformer();
				transformer.transform(source, result);
			}
		} catch (final Exception e) {
			LOG.error(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getWishlistForUserAndAllB2BUnits(de.hybris
	 * .platform.core.model.user.UserModel, java.util.List)
	 */
	@Override
	public List<Wishlist2Model> getWishlistForUserAndAllB2BUnits(
			UserModel user, List<B2BUnitModel> listUnit) {

		return wishlistService.getWishlistForUserAndAllB2BUnits(user, listUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getOrderHistoryByB2BUnitModel(java.util
	 * .List)
	 */
	@Override
	public List<OrderData> getOrderHistoryByB2BUnitModel(List<Long> listB2BUnits)
			throws Exception {

		List<OrderData> units = new ArrayList<OrderData>();

		try {
			units = neorisOrderHistoryService.getOrderHistoryByB2BUnitModel(
					listB2BUnits).getResults();
		} catch (final Exception e) {
			LOG.error(e.getMessage());
		}

		return units;
	}

	public List<OrderStatus> getValidStatusForOrdersOnly() {
		final List<OrderStatus> validStates = enumerationService
				.getEnumerationValues(OrderStatus._TYPECODE);

		// remove all QUOTE status
		validStates.remove(OrderStatus.PENDING_QUOTE);
		validStates.remove(OrderStatus.APPROVED_QUOTE);
		validStates.remove(OrderStatus.REJECTED_QUOTE);
		validStates.remove(OrderStatus.USED_QUOTE);
		validStates.remove(OrderStatus.EXPIRED_QUOTE);
		validStates.remove(OrderStatus.CANCELLED_QUOTE);
		
		validStates.remove(OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID);
		validStates.remove(OrderStatus.ORDER_PENDING_APPROVAL_AND_PENDING_SAP_ID);
		validStates.remove(OrderStatus.QUOTE_PENDING_SAP_ID);

		return validStates;
	}

	public List<OrderStatus> getValidStatusForQuotesOnly() {
		List<OrderStatus> orderStatus = new ArrayList<OrderStatus>();

		orderStatus.add(OrderStatus.PENDING_QUOTE);
		orderStatus.add(OrderStatus.APPROVED_QUOTE);
		orderStatus.add(OrderStatus.REJECTED_QUOTE);
		orderStatus.add(OrderStatus.USED_QUOTE);
		orderStatus.add(OrderStatus.EXPIRED_QUOTE);
		orderStatus.add(OrderStatus.CANCELLED_QUOTE);

		return orderStatus;
	}

	public List<OrderStatus> getValidStatusForOrdersOnlyEdit() {
		List<OrderStatus> orderStatus = new ArrayList<OrderStatus>();

		// orderStatus.add(OrderStatus.OPEN);
		orderStatus.add(OrderStatus.COMPLETED);
		orderStatus.add(OrderStatus.CANCELLED);
		orderStatus.add(OrderStatus.IN_PROCESS);
		orderStatus.add(OrderStatus.PENDING_APPROVAL);
		orderStatus.add(OrderStatus.APPROVED);
		orderStatus.add(OrderStatus.ON_VALIDATION);

		return orderStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getOrderReportInformationWS(java.lang.
	 * Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public String getOptStudioReportInformation(
			AuthenticationContract authentication, String reportName,
			ArrayOfReportParameter reportParameters) throws Exception {
		
		ReportProvider reportProvider = new ReportProvider();
		IReportProvider bindingEndpoint = reportProvider.getSoap11();
		
		String[] reportsParts = reportName.split(" ");
		String reportPart = reportsParts[0];
		
		//10Nov2016 CILS Si el sitio es MX, cambia el endpoint del WS
		if (PROLAMSA_MX_STORE_UID.equals(getCurrentBaseStore().getUid()) ||  reportPart.equals("Reporte"))
		{
			BindingProvider bp = (BindingProvider)bindingEndpoint;
			//TODO Modificar para que se pueda configurar el endpoint desde el local.properties
			//URL EndPoint de QA, cambiarla a PRD cuando sea requerido.
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://plmescoptqas:8098/GreyconReports/soap11");
		}

		String result = "";

		String withParams = "";
		if (reportParameters != null
				&& reportParameters.getReportParameter() != null) {
			for (ReportParameter eachParameter : reportParameters
					.getReportParameter()) {
				withParams += eachParameter.getParameter() + ":"
						+ eachParameter.getValue() + " ";
			}
		}

		LOG.info(String.format(
				"Start GREYCON OPTSTUDIO call: %s with parameters: %s",
				reportName, withParams));

		long start = 0;

		try {
			start = System.currentTimeMillis();
			// call web service
			result = bindingEndpoint.getCustomReportAsXml(authentication,
					reportName, reportParameters);
		} finally {
			final long time = System.currentTimeMillis() - start;
			LOG.info(String.format(
					"End GREYCON OPTSTUDIO call: %s with parameters: %s",
					reportName, withParams));
			LOG.info(String.format("Average log time was %,d ms%n", time));
		}

		return result;
	}
	
	@Override
	public void setLocationInit(String rootUnit)
	{
		List<HybrisEnumValue> listLocations = getListLocation(rootUnit);
		
		sessionService.setAttribute(LOCATION_SLOT, listLocations.get(0).getCode()); //return listLocations.get(0).getCode();
	}
	
	@Override
	public String getCurrentLocationABC() {
		//return sessionService.getAttribute(NeorisFacade.LOCATION_SLOT).toString();
		
		BaseStoreModel currentBaseStoreModel = getCurrentBaseStore();
		
		if(currentBaseStoreModel.isAllowCategoryVisibility())
		{
			if(sessionService.getAttribute(NeorisFacade.LOCATION_SLOT) != null)
			{
				return sessionService.getAttribute(NeorisFacade.LOCATION_SLOT).toString();
			}	
			else
			{
				B2BUnitModel customer = getRootUnit();
				if(customer != null)
				{
					setLocationInit(customer.getUid());
					return sessionService.getAttribute(NeorisFacade.LOCATION_SLOT).toString();
				}else
				{
					return null;
				}			
			}
		}else
		{
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getB2BUnitsForStore(de.hybris.platform
	 * .store.BaseStoreModel)
	 */
	@Override
	public List<B2BUnitData> getB2BUnitsForStore(BaseStoreModel baseStore) {
		final B2BCustomerModel b2bCustomer = b2bCustomerService
				.getCurrentB2BCustomer();

		final List<B2BUnitData> col = new ArrayList<B2BUnitData>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups()) {
			if (group instanceof B2BUnitModel
					&& ((B2BUnitModel) group).getBaseStores().contains(
							baseStore)) {

				final B2BUnitModel b2bUnit = (B2BUnitModel) group;
				col.add(b2bUnitConverter.convert(b2bUnit));
			}
		}

		return col;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#isValidB2bUnitUIDInStore(de.hybris.platform
	 * .store.BaseStoreModel, java.lang.String)
	 */
	@Override
	public boolean isValidB2bUnitUIDInStore(BaseStoreModel baseStore, String uid) {
		boolean isValid = false;
		List<B2BUnitData> b2bUnits = getB2BUnitsForStore(baseStore);

		for (final B2BUnitData b2bUnitData : b2bUnits) {
			if (uid.equals(b2bUnitData.getUid())) {
				isValid = true;
				break;
			}
		}

		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#isFacetAutoSelectionEnabledForCurrentUser
	 * ()
	 */
	@Override
	public boolean isFacetAutoSelectionEnabledForCurrentUser() {
		boolean isFacetAutoSelectionEnabled = false;

		// MX
		if (PROLAMSA_MX_STORE_UID.equals(getCurrentBaseStore().getUid())) {
			UserModel currentUser = getCurrentUser();

			UserGroupModel prolamsaFacetAutoselectionGroup = getUserService()
					.getUserGroupForUID(PROLAMSA_FACETAUTOSELECTION_GROUP);

			if (getUserService().isMemberOfGroup(currentUser,
					prolamsaFacetAutoselectionGroup)) {
				isFacetAutoSelectionEnabled = true;
			}
		}

		return isFacetAutoSelectionEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getPagedOrderHistoryForHomePage(mx.neoris
	 * .core.services.OrderHistorySearchParameters,
	 * de.hybris.platform.b2b.model.B2BCustomerModel, java.util.List)
	 */
	@Override
	public SearchPageData<OrderDataHomePage> getPagedOrderHistoryForHomePage(
			OrderHistorySearchParameters searchParameters,
			B2BCustomerModel customer, List<OrderStatus> statusList) {
		SearchPageData<OrderModel> pageModel = null;
		List<OrderDataHomePage> units = new ArrayList<OrderDataHomePage>();
		final SearchPageData<OrderDataHomePage> result = new SearchPageData<OrderDataHomePage>();

		try {
			pageModel = neorisOrderHistoryService.getPagedOrderHistory(
					searchParameters, customer, statusList);

			for (final OrderModel orderModel : pageModel.getResults()) {
				OrderDataHomePage orderData = new OrderDataHomePage();
				orderData.setOrderNumber(orderModel.getCode());
				orderData.setShortB2BUnitName(orderModel.getUnit()
						.getShortName());
				orderData.setStatusDisplay(orderModel.getStatusDisplay());
				orderData.setUnitName(orderModel.getUnit().getName());
				// neorisOrderConverter.convert(orderModel, orderData);
				units.add(orderData);
			}

			result.setPagination(pageModel.getPagination());
			result.setResults(units);
			result.setSorts(pageModel.getSorts());
		} catch (final Exception e) {
			LOG.error(e.getMessage());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getTPagoFor(de.hybris.platform.b2b.model
	 * .B2BUnitModel, de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public PaymentTermsType getTPagoFor(B2BUnitModel b2bUnitModel,
			BaseStoreModel baseStoreModel) {

		Set<NeorisB2BUnitSettingByStoreModel> availableTPagos = b2bUnitModel
				.getTPagoForStore();

		if (availableTPagos != null && availableTPagos.size() > 0) {
			for (NeorisB2BUnitSettingByStoreModel eachSetting : availableTPagos) {
				if (baseStoreModel.getUid().equalsIgnoreCase(
						eachSetting.getStoreId()))
					;
				{
					HybrisEnumValue enumValue = enumerationService
							.getEnumerationValue("PaymentTermsType",
									eachSetting.getSetting());

					if (enumValue == null)
						enumValue = enumerationService.getEnumerationValue(
								"PaymentTermsType", "Z000");

					return (PaymentTermsType) enumValue;
				}
			}
		}

		return enumerationService.getEnumerationValue("PaymentTermsType",
				"Z000");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.NeorisFacade#getCurrencyFor(de.hybris.platform.b2b.
	 * model.B2BUnitModel, de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public CurrencyModel getCurrencyFor(B2BUnitModel b2bUnitModel,
			BaseStoreModel baseStoreModel) {

		Set<NeorisB2BUnitSettingByStoreModel> currenciesSettings = b2bUnitModel
				.getCurrencyForStore();

		if (currenciesSettings != null && currenciesSettings.size() > 0) {
			for (NeorisB2BUnitSettingByStoreModel eachSetting : currenciesSettings) {
				if (baseStoreModel.getUid().equalsIgnoreCase(
						eachSetting.getStoreId())) {
					CurrencyModel currencyModel = commonI18NService
							.getCurrency(eachSetting.getSetting());

					if (currencyModel == null)
						currencyModel = baseStoreModel.getDefaultCurrency();

					return currencyModel;
				}
			}
		}
		return baseStoreModel.getDefaultCurrency();
	}

	@Override
	public void setCurrentCurrency(CurrencyModel currencyModel) {
		sessionService.setAttribute(CURRENT_CURRENCY, currencyModel);
		commonI18NService.setCurrentCurrency(currencyModel);
	}

	@Override
	public NeorisB2BCustomerDefaultSettingModel getDefaultSettings() 
	{
		// try to get default settings from session
		NeorisB2BCustomerDefaultSettingModel defaultSettings = sessionService.getAttribute(B2BCUSTOMER_DEFAULT_SETTINGS_SLOT);
		
		if(defaultSettings == null)
		{
			// review if b2bcustomer has default settings defined on model level
			final B2BCustomerModel b2bCustomer = getCurrentCustomer();
			final BaseStoreModel baseStore = getCurrentBaseStore();
			final B2BUnitModel b2bUnit = getRootUnit();
			
			defaultSettings = neorisB2BCustomerDefaultSettingsFacade.getSetting(b2bCustomer, b2bUnit, baseStore);
			sessionService.setAttribute(B2BCUSTOMER_DEFAULT_SETTINGS_SLOT, defaultSettings);
		}
		
		return defaultSettings;
	}
	
	@Override
	public void updateFirstAccesPortal(B2BCustomerModel customer) {
		
		customer.setIsFirstAccesPortal(false);
		
		modelService.save(customer);
	}

	//CILS Obtener las locaciones dependiendo de la visibilidad del cliente
	@Override
	public List<HybrisEnumValue> getListLocation(String unit) {
		final BaseStoreModel baseStore = getCurrentBaseStore();
		
		final SearchResult<ProductVisibilityModel> listProductVisibility = neorisService.getAllProductVisibility(baseStore.getUid());   // neorisProductVisibilityService
				
		List<ProductVisibilityModel> visibilidadCliente = new ArrayList<ProductVisibilityModel>();
		
		Map<String, ProductLocation> locationMap = new LinkedHashMap<String, ProductLocation>();
		
		if (listProductVisibility.getResult() != null && listProductVisibility.getResult().size() > 0)
		{
			for (final ProductVisibilityModel productVisibility : listProductVisibility.getResult())
			{
				final CatalogModel catalog = catalogVersionService.getCatalogVersion(baseStore.getUid() + "ProductCatalog", "Online")
						.getCatalog();

				if (productVisibility.getCatalog().contains(catalog))
				{
					//only the active Visibilities
					if (productVisibility.getActive().equals(true) && productVisibility.getCatalog().contains(catalog))
					{
						final List<CategoryModel> listCategories = productVisibility.getCategory();
						
						for(PrincipalModel cliente : listCategories.get(0).getAllowedPrincipals())
						{
							
							if(cliente instanceof B2BUnitModel)
							{
								if(unit.equalsIgnoreCase(cliente.getUid()))
								{
									visibilidadCliente.add(productVisibility);
								}		
							}
						}
					}
				}
			}
		}
		
		List<CategoryModel> categoryVisibilityB2BUnit = neorisService.getCategoryCustomerVisibilityForB2BUnit(unit);
		
		if(visibilidadCliente.isEmpty() && categoryVisibilityB2BUnit.isEmpty())
		{
			return  Collections.<HybrisEnumValue> emptyList();
		}
		
		if(!visibilidadCliente.isEmpty())
		{
			for(int i = 0; i<visibilidadCliente.size(); i++ )
			{
				if(visibilidadCliente.get(i).getLocation().size() >= 1)
				{
					for(int j=0; j<visibilidadCliente.get(i).getLocation().size(); j++ )
					{
						//Example: locationMap.put("_0000",_0000);
						locationMap.put(visibilidadCliente.get(i).getLocation().get(j).toString(), visibilidadCliente.get(i).getLocation().get(j));
					}
				}
			}
		}
		
		if(!categoryVisibilityB2BUnit.isEmpty())
		{
			for(int k = 0; k<categoryVisibilityB2BUnit.size(); k++)
			{
				if(categoryVisibilityB2BUnit.get(k).getProducts().size() >= 1)
				{
					for(ProductModel product : categoryVisibilityB2BUnit.get(k).getProducts())
					{
						String code = product.getCode().toString();
						String[] codeSplit = code.split("_");
						try{
							locationMap.put("_"+codeSplit[1], ProductLocation.valueOf("_"+codeSplit[1]));
						}
						catch(Exception e)
						{
							LOG.error("Es necesario agregar la locación: " + "_"+codeSplit[1], e);
							continue;
						}
					}
				}
			}
		}
		
		final List<ProductLocation> listLocations = new ArrayList<ProductLocation>(locationMap.values());
		List<HybrisEnumValue> listlocationB2BUnit = new ArrayList<HybrisEnumValue>();
		
		for(int k=0; k<listLocations.size(); k++)
		{	
			listlocationB2BUnit.add(ProductLocation.valueOf(listLocations.get(k).toString()));
		}
		
		return listlocationB2BUnit;
	}
	
	@Override
	public String getLocationSlot() {
		
		String currentLocationSession = null;
		
		if(sessionService.getAttribute(LOCATION_SLOT) != null)
		{
			currentLocationSession = sessionService.getAttribute(LOCATION_SLOT).toString();
		}
		
		return currentLocationSession;
	}

	@Override
	public List<String> getlistQuantityExact (List<ProductData> productDataList, Map<String, Double> quantityMap)
	{
		List<String> listNotFoundExact = new ArrayList<String>();
		
		try
		{
			for (ProductData eachProductData : productDataList)
			{
				String key = eachProductData.getBaseCode() + eachProductData.getLocation().getCode();
				String productCode = eachProductData.getBaseCode();

				boolean hasStock = false;
				boolean isExact = false;
				
				if(eachProductData.getInventoryEntry().getAvailableStockBundlesColInternal().size() > (double)1 )
				{
					hasStock = true;
					for(int i = 0; i < eachProductData.getInventoryEntry().getAvailableStockBundlesColInternal().size(); i++ )
					{
						if(Math.ceil(eachProductData.getInventoryEntry().getAvailableStockBundlesColInternal().get(i)) == (quantityMap.get(key)+1.0d))
						{
							isExact = true;
							break;
						}
					}
				}
				
				if(eachProductData.getInventoryEntry().getAvailableStockBundlesCol().size() > (double)1 )
				{
					hasStock = true;
					for(int i = 0; i < eachProductData.getInventoryEntry().getAvailableStockBundlesCol().size(); i++ )
					{
						if(Math.ceil(eachProductData.getInventoryEntry().getAvailableStockBundlesCol().get(i)) == (quantityMap.get(key)+1.0d))
						{
							isExact = true;
							break;
						}
					}
				}
				
				if(eachProductData.getInventoryEntry().getRollingScheduleBundlesCol().size() > (double)1 && hasStock == false )
				{
					for(int i = 0; i < eachProductData.getInventoryEntry().getRollingScheduleBundlesCol().size(); i++ )
					{
						if(Math.ceil(eachProductData.getInventoryEntry().getRollingScheduleBundlesCol().get(i)) == (quantityMap.get(key)+1.0d))
						{
							isExact = true;
							break;
						}
					}
				}
		
				if(!isExact)
				{				
					listNotFoundExact.add(productCode);
				}
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while getting product info from Excel", ex);
		}
		return listNotFoundExact;
	}
	
	@Override
	public void deleteDataB2BCustomerLogout(String id)
	{
		final B2BCustomerModel user = getCurrentCustomer();
		
		if(user != null)
		{
			String valNull = null;
				
			user.setCurrentLocation(valNull);
			user.setCustomerCurrentMoneda(valNull);
			user.setCustomerCurrentLenguaje(valNull);
			user.setCustomerCurrentSMetrico(valNull);
			user.setCustomerCurrentUMedida(valNull);
				
			modelService.save(user);
		}	
	}
}
