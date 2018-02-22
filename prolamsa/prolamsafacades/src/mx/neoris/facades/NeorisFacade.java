/**
 * 
 */
package mx.neoris.facades;


import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderDataHomePage;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import mx.neoris.core.enums.MeasurementSystemType;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.product.ProductVariantConfiguration;
import mx.neoris.core.services.B2BUnitSearchParameters;
import mx.neoris.core.services.OrderHistorySearchParameters;
import mx.neoris.facades.wishlist2entry.data.Wishlist2EntryData;

import com.greycon.reports.ArrayOfReportParameter;
import com.greycon.reports.AuthenticationContract;
import com.greycon.reports.IReportProvider;
import com.greycon.reports.ReportProvider;

/**
 * @author fdeutsch
 * 
 */
public interface NeorisFacade
{
	static final String PRODUCTUNIT_SLOT = "productUnit";
	static final String B2BUNIT_SLOT = "rootunit";
	static final String SYSTEM_UNIT_SLOT = "systemUnit";
	static final String CURRENT_CURRENCY = "currentCurrency";
	static final String PRODUCT_VARIANT_CONFIGURATIONS = "ProductVariantConfigurations";
	static final String B2BCUSTOMER_DEFAULT_SETTINGS_SLOT = "defaultSettings";
	static final String LOCATION_SLOT = "locationB2BUnit";
	
	String getEnumerationNameFromEnumValue(HybrisEnumValue enumValue);

	UserModel getCurrentUser();
	String getCurrentCustomerType();
	boolean currentUserIsMemberOfUserGroup(String userGroup);
	
	CartModel getCurrentCart();
	BaseStoreModel getCurrentBaseStore();

	List<UnitModel> getAllUnits();
	
	List<UnitModel> getUnitBySystem(String system);		
	
	List<B2BUnitData> getFormattedB2BUnits();
	List<B2BUnitData> getFormattedB2BUnitsAll();
	List<B2BUnitData> getFormattedB2BUnitsSortBy();
	List<B2BUnitModel> getB2BUnitModelsFromCustomer(B2BCustomerModel b2bCustomer);

	List<AddressData> getShippingAddress(B2BUnitModel preDetailModel);	

	B2BUnitModel getRootUnit();
	B2BUnitModel getB2BUnitWithUid(String uid);
	String setRootUnitWithId(String rootUnit);
	String setUnitWithId(String unitId);
		
	//NEORIS_CHANGE #54
	List<Wishlist2Model> getWishlistForUserAndB2BUnit(UserModel user, B2BUnitModel unit);
	List<Wishlist2Model> getWishlistForUserAndAllB2BUnits(UserModel user, List<B2BUnitModel> listUnit);
	
	Wishlist2Model saveWishlist(String name, UserModel user, UnitModel b2bUnit, B2BUnitModel unit, CartModel cart) throws Exception;
	Wishlist2Model loadCartFromWishlist(Long wishlistPK) throws Exception;
	void deleteWishlist(Long wishlistPK) throws Exception;
	Boolean getExistWishlist(final String name, final UserModel user, final B2BUnitModel unit);
	void emptyCartFromSession(CartModel currentCart)  throws Exception;

	List<String> getProlamsaSkuFromSku(List<String> codes,B2BUnitModel unit, String catalogId);
	
	Map<String, String> getClientUnitCodesAndB2BUnit(List<String> codes, B2BUnitModel unit, String catalogId);
	
	void setCurrentCurrency(CurrencyModel currencyModel);
	CurrencyModel getCurrentCurrency();
	Locale getCurrentLocale();
	B2BCustomerModel getCurrentCustomer();
	String getCurrentLocationABC();
	void setLocationInit(String rootUnit);
	
	SearchPageData<B2BUnitData> getB2BUnitBySearch(B2BUnitSearchParameters searchParameters) throws Exception;	
		
	List<AddressData> getAddresessForSearch(B2BUnitSearchParameters searchParameters);
	List<AddressData> getAddresessForSearch(B2BUnitSearchParameters searchParameters,List<String> listPk);
		
	AddressModel getBillingAddress(B2BUnitModel unit);
	
	//NEORIS_CHANGE #83
	List<Wishlist2EntryData> getWishlistEntry(String wishlist);
	
	OrderModel getOrderFromCode(String code);
	
	SearchPageData<OrderDataHomePage> getPagedOrderHistoryForHomePage(OrderHistorySearchParameters searchParameters, B2BCustomerModel customer, List<OrderStatus> statusList);
	SearchPageData<OrderData> getPagedOrderHistory(OrderHistorySearchParameters searchParameters, B2BCustomerModel customer, List<OrderStatus> statusList);
	List<OrderData> getOrderHistoryByB2BUnitModel(final List<Long> listB2BUnits) throws Exception;

	//void updateCustomer(CustomerData customer) throws DuplicateUidException;
	
	Boolean getExistTemplate(final String orderCode);
	
	Wishlist2Model saveTemplateOrder(String name, String orderCode, UserModel user,  B2BUnitModel b2bUnit, UnitModel unit, OrderModel orderDetails) throws Exception;
	
	Wishlist2Model updateTemplateOrder(String name, Wishlist2Model wishlist) throws Exception;
	
	List<Wishlist2Model> getWishlistForOrderCodeAndB2BUnit(String orderCode, B2BUnitModel currentB2BUnit);
	List<Wishlist2Model> getWishlistForNameAndB2BUnit(final String name, final B2BUnitModel currentB2BUnit);
	
	/*
	 * Product Variants
	 */
	ProlamsaAPIProductConfigurationModel getProductConfigurationModelFromSessionWithCode(String productCode);
	ProlamsaAPIProductConfigurationModel getProductConfigurationModelFrom(ProductVariantConfiguration sessionConfig);
	ProductVariantConfiguration getProductConfigurationFrom(ProlamsaAPIProductConfigurationModel configModel);

	void setProductVariantConfigurationMap(Map<String,ProductVariantConfiguration> map);
	Map<String,ProductVariantConfiguration> getProductVariantConfigurationMap();
	Map<String,ProductVariantConfiguration> getReadWriteProductVariantConfigurationMap();
	
	List<B2BUnitData> getAllB2BUnits();
	
	List<B2BUnitData> getB2BUnitsForStore(BaseStoreModel baseStore);
	
	boolean isValidB2bUnitUIDInStore(BaseStoreModel baseStore, String uid);

	B2BUnitData getB2BUnitForUID(String uid);

	List<B2BUnitData> getB2BUnitsForUser(String userUID);

	void updateB2BUnits(CustomerData b2bCustomerData);
	void setBaseStore(CustomerData b2bCustomerData);
	
	String removeB2BUnitFromCurrentUser(String unitUID);
	String addB2BUnitToCurrentUser(String unitUID);
	Integer getTotalNumberB2BUnitsCurrentUser();
	
	
	UnitModel getCurrentUnit();
	
	void getXMLUpdateOrderStatus(List<OrderData> recentOrders);
	
	List<OrderStatus> getValidStatusForOrdersOnly();
	List<OrderStatus> getValidStatusForQuotesOnly();
	
	List<AddressData> getCurrentCustomerB2BUnitAddresses(final String selectedUnitUid, final String sortBy, final String sortType);
	
	List<OrderStatus> getValidStatusForOrdersOnlyEdit();
	
	 //05Ene2015 Agrega llamado de WebService para reportes
	String getOptStudioReportInformation(AuthenticationContract authentication, String reportName, ArrayOfReportParameter reportParameters) throws Exception;
	
	boolean isFacetAutoSelectionEnabledForCurrentUser();
	
	PaymentTermsType getTPagoFor(B2BUnitModel b2bUnitModel,BaseStoreModel baseStoreModel);
	CurrencyModel getCurrencyFor(B2BUnitModel b2bUnitModel,BaseStoreModel baseStoreModel);
	
	HybrisEnumValue getCurrentSystem();
	
	NeorisB2BCustomerDefaultSettingModel getDefaultSettings();
	
	void updateFirstAccesPortal(B2BCustomerModel customer);
	
	List<HybrisEnumValue> getListLocation (String unit);
	
	String getLocationSlot();
	
	public List<String> getlistQuantityExact(List<ProductData> productDataList, Map<String,Double> quantityMap);
	
	public boolean isInternalCustomer();
	
	void deleteDataB2BCustomerLogout(String id);
	
}
