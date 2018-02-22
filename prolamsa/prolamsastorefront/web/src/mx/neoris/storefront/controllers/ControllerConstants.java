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
package mx.neoris.storefront.controllers;

import de.hybris.platform.acceleratorcms.model.components.CategoryFeatureComponentModel;
import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.acceleratorcms.model.components.NavigationBarComponentModel;
import de.hybris.platform.acceleratorcms.model.components.ProductFeatureComponentModel;
import de.hybris.platform.acceleratorcms.model.components.ProductReferencesComponentModel;
import de.hybris.platform.acceleratorcms.model.components.PurchasedCategorySuggestionComponentModel;
import de.hybris.platform.cms2.model.contents.components.CMSLinkComponentModel;
import de.hybris.platform.cms2lib.model.components.ProductCarouselComponentModel;


/**
 * Class with constants for controllers.
 */
public interface ControllerConstants
{
	String UIIdleTimeoutSeconds = "ui.idletimeout.seconds";

	/**
	 * Class with action name constants
	 */
	interface Actions
	{
		interface Cms
		{
			String _Prefix = "/view/";
			String _Suffix = "Controller";

			/**
			 * Default CMS component controller
			 */
			String DefaultCMSComponent = _Prefix + "DefaultCMSComponentController";

			/**
			 * CMS components that have specific handlers
			 */
			String PurchasedCategorySuggestionComponent = _Prefix + PurchasedCategorySuggestionComponentModel._TYPECODE + _Suffix;
			String ProductReferencesComponent = _Prefix + ProductReferencesComponentModel._TYPECODE + _Suffix;
			String ProductCarouselComponent = _Prefix + ProductCarouselComponentModel._TYPECODE + _Suffix;
			String MiniCartComponent = _Prefix + MiniCartComponentModel._TYPECODE + _Suffix;
			String ProductFeatureComponent = _Prefix + ProductFeatureComponentModel._TYPECODE + _Suffix;
			String CategoryFeatureComponent = _Prefix + CategoryFeatureComponentModel._TYPECODE + _Suffix;
			String NavigationBarComponent = _Prefix + NavigationBarComponentModel._TYPECODE + _Suffix;
			String CMSLinkComponent = _Prefix + CMSLinkComponentModel._TYPECODE + _Suffix;
		}
	}

	/**
	 * Class with view name constants
	 */
	interface Views
	{
		interface Cms
		{
			String ComponentPrefix = "cms/";
		}

		interface Pages
		{
			interface Backorder
			{
				String BackorderItemPageMobile = "pages/backorder/backorderMobile";
			}
			
			interface Order
			{
				String Document = "OrderDocument";
			}

			interface Account
			{
				String AccountLoginPage = "pages/account/accountLoginPage";
				String AccountHomePage = "pages/account/accountHomePage";
				String AccountOrderHistoryPage = "pages/account/accountOrderHistoryPage";
				String AccountOrderPage = "pages/account/accountOrderPage";
				String AccountOrderItemPageMobile = "pages/account/accountOrderItemPage";
				String AccountProfilePage = "pages/account/accountProfilePage";
				String AccountProfileEditPage = "pages/account/accountProfileEditPage";
				String AccountProfileEmailEditPage = "pages/account/accountProfileEmailEditPage";
				String AccountChangePasswordPage = "pages/account/accountChangePasswordPage";
				String AccountAddressBookPage = "pages/account/accountAddressBookPage";
				String AccountEditAddressPage = "pages/account/accountEditAddressPage";
				String AccountPaymentInfoPage = "pages/account/accountPaymentInfoPage";
				String AccountMyQuotesPage = "pages/account/accountMyQuotesPage";
				String AccountReplenishmentSchedule = "pages/account/accountReplenishmentSchedule";
				String AccountReplenishmentScheduleDetails = "pages/account/accountReplenishmentScheduleDetails";
				String AccountOrderApprovalDashboardPage = "pages/account/accountOrderApprovalDashboardPage";
				String AccountOrderApprovalDetailsPage = "pages/account/accountOrderApprovalDetailsPage";
				String AccountQuoteDetailPage = "pages/account/accountQuoteDetailPage";
				String AccountCancelActionConfirmationPage = "pages/account/accountCancelActionConfirmationPage";
				//NEORIS_CHANGE #61
				String AccountBalanceStatementListPage = "pages/account/accountBalanceStatementListPage";
				//NEORIS_CHANGE #62
				String AccountBalanceStatementDetailPage = "pages/account/accountBalanceStatementDetailPage";
			}

			interface Checkout
			{
				String CheckoutLoginPage = "pages/checkout/checkoutLoginPage";
				String CheckoutConfirmationPage = "pages/checkout/checkoutConfirmationPage";
				String QuoteCheckoutConfirmationPage = "pages/checkout/quoteCheckoutConfirmationPage";
				String CheckoutReplenishmentConfirmationPage = "pages/checkout/checkoutReplenishmentConfirmationPage";
				
				String QuickCheckoutConfirmationPage = "pages/checkout/quickConfirmation/quickCheckoutConfirmationPage";
				String QuickQuoteCheckoutConfirmationPage = "pages/checkout/quickConfirmation/quickQuoteCheckoutConfirmationPage";
			}

			interface SingleStepCheckout
			{
				String CheckoutSummaryPage = "pages/checkout/single/checkoutSummaryPage";
			}

			interface MultiStepCheckout
			{
				String CheckoutSampleLandingPage = "pages/checkout/multi/checkoutSampleLandingPage";
			}

			interface Password
			{
				String PasswordResetChangePage = "pages/password/passwordResetChangePage";
			}

			interface Error
			{
				String ErrorNotFoundPage = "pages/error/errorNotFoundPage";
			}

			interface Cart
			{
				String CartPage = "pages/cart/cartPage";
			}

			interface StoreFinder
			{
				String StoreFinderSearchPage = "pages/storeFinder/storeFinderSearchPage";
				String StoreFinderDetailsPage = "pages/storeFinder/storeFinderDetailsPage";
			}

			interface Misc
			{
				String MiscRobotsPage = "pages/misc/miscRobotsPage";
				//NEORIS_CHANGE #39
				String LoginPageProlamsa = "pages/misc/loginPageProlamsa";
				
				String ContactUsPage = "pages/misc/contactUsPage";				
			}

			interface MyCompany
			{
				String MyCompanyLoginPage = "pages/company/myCompanyLoginPage";
				String MyCompanyHomePage = "pages/company/myCompanyHomePage";
				String MyCompanyManageUnitsPage = "pages/company/myCompanyManageUnitsPage";
				String MyCompanyManageUnitEditPage = "pages/company/myCompanyManageUnitEditPage";
				String MyCompanyManageUnitDetailsPage = "pages/company/myCompanyManageUnitDetailsPage";
				String MyCompanyManageUnitCreatePage = "pages/company/myCompanyManageUnitCreatePage";
				String MyCompanyManageBudgetsPage = "pages/company/myCompanyManageBudgetsPage";
				String MyCompanyManageBudgetsViewPage = "pages/company/myCompanyManageBudgetsViewPage";
				String MyCompanyManageBudgetsEditPage = "pages/company/myCompanyManageBudgetsEditPage";
				String MyCompanyManageBudgetsAddPage = "pages/company/myCompanyManageBudgetsAddPage";
				String MyCompanyManageCostCentersPage = "pages/company/myCompanyManageCostCentersPage";
				String MyCompanyCostCenterViewPage = "pages/company/myCompanyCostCenterViewPage";
				String MyCompanyCostCenterEditPage = "pages/company/myCompanyCostCenterEditPage";
				String MyCompanyAddCostCenterPage = "pages/company/myCompanyAddCostCenterPage";
				String MyCompanyManagePermissionsPage = "pages/company/myCompanyManagePermissionsPage";
				String MyCompanyManageUnitUserListPage = "pages/company/myCompanyManageUnitUserListPage";
				String MyCompanyManageUnitApproverListPage = "pages/company/myCompanyManageUnitApproversListPage";
				String MyCompanyManageUserDetailPage = "pages/company/myCompanyManageUserDetailPage";
				String MyCompanyManageUserAddEditFormPage = "pages/company/myCompanyManageUserAddEditFormPage";
				String MyCompanyManageUsersPage = "pages/company/myCompanyManageUsersPage";
				String MyCompanyManageUserDisbaleConfirmPage = "pages/company/myCompanyManageUserDisableConfirmPage";
				String MyCompanyManageUnitDisablePage = "pages/company/myCompanyManageUnitDisablePage";
				String MyCompanySelectBudgetPage = "pages/company/myCompanySelectBudgetsPage";
				String MyCompanyCostCenterDisableConfirm = "pages/company/myCompanyDisableCostCenterConfirmPage";
				String MyCompanyManageUnitAddAddressPage = "pages/company/myCompanyManageUnitAddAddressPage";
				String MyCompanyManageUserPermissionsPage = "pages/company/myCompanyManageUserPermissionsPage";
				String MyCompanyManageUserResetPasswordPage = "pages/company/myCompanyManageUserPassword";
				String MyCompanyBudgetDisableConfirm = "pages/company/myCompanyDisableBudgetConfirmPage";
				String MyCompanyManageUserGroupsPage = "pages/company/myCompanyManageUserGroupsPage";
				String MyCompanyManageUsergroupViewPage = "pages/company/myCompanyManageUsergroupViewPage";
				String MyCompanyManageUsergroupEditPage = "pages/company/myCompanyManageUsergroupEditPage";
				String MyCompanyManageUsergroupCreatePage = "pages/company/myCompanyManageUsergroupCreatePage";
				String MyCompanyManageUsergroupDisableConfirmationPage = "pages/company/myCompanyManageUsergroupDisableConfirmationPage";
				String MyCompanyManagePermissionDisablePage = "pages/company/myCompanyManagePermissionDisablePage";
				String MyCompanyManagePermissionsViewPage = "pages/company/myCompanyManagePermissionsViewPage";
				String MyCompanyManagePermissionsEditPage = "pages/company/myCompanyManagePermissionsEditPage";
				String MyCompanyManagePermissionTypeSelectPage = "pages/company/myCompanyManagePermissionTypeSelectPage";
				String MyCompanyManagePermissionAddPage = "pages/company/myCompanyManagePermissionAddPage";
				String MyCompanyManageUserCustomersPage = "pages/company/myCompanyManageUserCustomersPage";
				String MyCompanyManageUserGroupPermissionsPage = "pages/company/myCompanyManageUserGroupPermissionsPage";
				String MyCompanyManageUserGroupMembersPage = "pages/company/myCompanyManageUserGroupMembersPage";
				String MyCompanyRemoveDisableConfirmationPage = "pages/company/myCompanyRemoveDisableConfirmationPage";
				String MyCompanyManageUserB2BUserGroupsPage = "pages/company/myCompanyManageUserB2BUserGroupsPage";
				String MyCompanyManageUsergroupRemoveConfirmationPage = "pages/company/myCompanyManageUsergroupRemoveConfirmationPage";
			}

			interface Product
			{
				String OrderForm = "pages/product/productOrderFormPage";
			}
			
			// NEORIS_CHANGE
			interface UploadExcel
			{
				String UploadExcel = "pages/excelUpload/excelUploadPage";
			}
			
			interface Incidents
			{
				String AddIncident ="pages/incident/addIncidentPage";
				String IncidentDetail ="pages/incident/incidentDetailPage";
				String IncidentList ="pages/incident/incidentListPage";
			}
			
			interface Notices
			{
				String List = "pages/notices/noticeListPage";
			}
		}

		interface Fragments
		{
			interface Cart
			{
				String AddToCartPopup = "fragments/cart/addToCartPopup";
				String MiniCartPanel = "fragments/cart/miniCartPanel";
				String MiniCartErrorPanel = "fragments/cart/miniCartErrorPanel";
				String CartPopup = "fragments/cart/cartPopup";
			}

			interface Checkout
			{
				String TermsAndConditionsPopup = "fragments/checkout/termsAndConditionsPopup";
				String OrderConditionsPopup = "fragments/checkout/orderConditionsPopup";
			}

			interface SingleStepCheckout
			{
				String DeliveryAddressFormPopup = "fragments/checkout/single/deliveryAddressFormPopup";
				String PaymentDetailsFormPopup = "fragments/checkout/single/paymentDetailsFormPopup";
			}

			interface Password
			{
				String PasswordResetRequestPopup = "fragments/password/passwordResetRequestPopup";
				String ForgotPasswordValidationMessage = "fragments/password/forgotPasswordValidationMessage";
			}

			interface Product
			{
				String FutureStockPopup = "fragments/product/futureStockPopup";
				String QuickViewPopup = "fragments/product/quickViewPopup";
				String ZoomImagesPopup = "fragments/product/zoomImagesPopup";
				String ReviewsTab = "fragments/product/reviewsTab";
				String ProductLister = "fragments/product/productLister";
			}
			
			interface FavoriteCustomers
			{
				String FavoriteCustomersList = "/fragments/favoriteCustomers/favoriteCustomersList";
			}
			
			interface FavoriteProducts
			{
				String FavoriteProductsManageList = "/fragments/favoriteProducts/favoriteProductsManageList";
			}
		}
	}
	
	// NEORIS_CHANGE
	interface Export
	{ 
		String PDF_SAP = "pdf_sap";
		String PDF_BY_CUSTOMER = "pdf_by_customer"; 
		String XLS_BY_CUSTOMER = "xls_by_customer";
		String PDF = "pdf";
		String XLS = "xls";
		String ExportParam = "_export";
		String XlsTemplatesFolder = "xls-templates";
		String PdfTemplatesFolder = "reports";
	}
	// NEORIS_CHANGE
	interface Templates
	{
		String DRAFT = "Draft";
		String TEMPLATE = "Template";
	}
	
	interface ProductVariantConfiguration
	{
		String CONFIGURATIONS = "ProductVariantConfigurations";
	}
	
	interface ProductFacetSelection
	{
		String FACET_MATERIALTYPE_AUTOSELECTION_SEARCH_QUERY = ":materialType:L";
		String FACET_LOCATION_AUTOSELECTION_SEARCH_QUERY = ":location:";
		String FACET_DESCRIPTION_ORDER_ASC = ":description-asc";
	}
	
	interface LocationSlot
	{
		String LOCATION = "LOCATION_SLOT";
	}
}
