ACC.variantConfig  =
{
	container: null,
	cssClass: "product-variant-configuration",
	initFunction: null,
	productCode: null,
	cartEntryNumber: null,
	saveConfigurationDone: null,
	deleteConfigurationDone: null,

	initialize: function()
	{
		var container = jQuery("<div></div>");
		container.addClass(ACC.variantConfig.cssClass);

		jQuery(container).hide();
		jQuery(document.body).append(container);

		ACC.variantConfig.container = container;
	},

	bindAll: function()
	{
	},

	closePopUp: function()
	{
		ACC.modals.closeModal(ACC.variantConfig.container);
	},

	popUp: function(information)
	{
		// clear the container first
		jQuery(ACC.variantConfig.container).html("");
		ACC.variantConfig.productCode = null;
		ACC.variantConfig.cartEntryNumber = null;

		var productCode = information.productCode;
		var cartEntryNumber = information.cartEntryNumber;

		ACC.variantConfig.productCode = productCode;
		ACC.variantConfig.cartEntryNumber = cartEntryNumber;

		var urlContent = ACC.config.contextPath + "/product/variantConfigurationPopup?";
		
		if (cartEntryNumber != null)
			urlContent += "cartEntryNumber=" + cartEntryNumber;
		
		if (productCode != null)
			urlContent += "productCode=" + productCode;
		
		var paramOptions = { width: 560, height: 530 };
		var initFunction = function() { ACC.variantConfig.initFunction(); };

		ACC.modals.openOnModal(customizeYourProductMsg, urlContent, jQuery(ACC.variantConfig.container), paramOptions, initFunction);
	}
};

jQuery(document).ready(function()
{
	ACC.variantConfig.initialize();
});

