ACC.neorisproduct =
{
	// cached jQuery objects
	$cartPopup:             $('#cart_popup'),
	$addItemsToCartButton : $('.add_to_cart_button'),
	allProductsSelector: ".prolamsa_product_row",
	$allPagerAnchors:	$(".pager a"),
	
	messages:
	{
		addToCart: "Add to Cart",
		noProductsSelected: "No products selected",
		changePageTitle: "Change Page",	
		changePageMessage: "If you change the page without saving the changes, the data will be lost, Do you want to continue?"
	},

	bindToAddItemsToCartButton : function(options)
	{
		jQuery(ACC.neorisproduct.$addItemsToCartButton).click(ACC.neorisproduct.addItemsToCartAction);
	},
	
	clearAmounts: function()
	{
		var allProducts = jQuery(ACC.neorisproduct.allProductsSelector);
		
		jQuery(ACC.neorisproduct.allProductsSelector).find("#stockQty").val(0);
		
		if(jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleQty"))
			jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleQty").val(0);
		
		if(jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleDate")[0])
			jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleDate")[0].selectedIndex = 0;
		
		// API products
		jQuery(ACC.neorisproduct.allProductsSelector).find("#stockQty.edit").val("");
		jQuery(ACC.neorisproduct.allProductsSelector).find("#stockQty.noedit").val("");
		
		if(jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleQty")){
			jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleQty.edit").val("");
			jQuery(ACC.neorisproduct.allProductsSelector).find("#rollingScheduleQty.noedit").val("");
		}
	},

	addItemsToCartAction: function()
	{
		// retrieve all product rows
		var selectionProductLocation = "";
		var variableProductLocations = false;
		var cartProductLocationMismatch = false;
		
		var selectionProductIsAPI = null;
		var variableProductFamily = false;
		var cartProductFamilyMismatch = false;
		var isCartEmpty = (cartProductLocation.length == 0);
				
		var allProducts = jQuery(ACC.neorisproduct.allProductsSelector);

		var entries = new Array();

		jQuery(allProducts).each(function(eachIndex, eachObject)
		{
			// check if has qty values assigned
			var stockQty = numeral().unformat(jQuery(eachObject).find("#stockQty").val());
			var rollingScheduleQty = numeral().unformat(jQuery(eachObject).find("#rollingScheduleQty").val());
			
			if ((jQuery.isNumeric(stockQty) && stockQty != 0) || (jQuery.isNumeric(rollingScheduleQty) && rollingScheduleQty != 0 && rollingScheduleQty != undefined))
			{
				var obj = { };
	
				// if its ok get product data
				obj.productCode = jQuery(eachObject).attr("productCode");
				obj.productLocation = jQuery(eachObject).attr("productLocation");
				// get the rolling schedule date
				obj.rollingScheduleDate = jQuery(eachObject).find("#rollingScheduleDate").val();
				obj.stockQty = stockQty;
				obj.rollingScheduleQty = rollingScheduleQty;
				// get is API product
				obj.isAPIProduct = (jQuery(eachObject).attr("isAPIProduct") == "true");

				entries[entries.length] = obj;
				
			///// VALIDATE PRODUCT LOCATIONS
				
				// if already set a selection product location
				if (selectionProductLocation.length != 0)
				{
					// if locations are different
					if (selectionProductLocation != obj.productLocation)
						variableProductLocations = true;
				}
				
				selectionProductLocation = obj.productLocation;

				// if selection product location different from cart product location
				if (cartProductLocation.length > 0 && cartProductLocation != obj.productLocation)
					cartProductLocationMismatch = true;
				
				
			///// VALIDATE PRODUCT FAMILIES COMPATIBILITY
				
				// if already set a selection product family
				if (selectionProductIsAPI != null)
				{
					// if families are different: API products for axis
					if (selectionProductIsAPI != obj.isAPIProduct)
						variableProductFamily = true;
				}
				
				selectionProductIsAPI = obj.isAPIProduct;
				
				// if selection product family different from cart product
				if (!isCartEmpty && cartHasAPIProducts != obj.isAPIProduct)
					cartProductFamilyMismatch = true;				
			}
		});

		// if no entries
		if (entries.length == 0)
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.noProductsSelected);
			return;
		}
		
		// if mismatch between selections location
		if (variableProductLocations)
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.productSelectionLocationMixup);
			return;
		}

		// if mismatch between cart and selection location
		if (cartProductLocationMismatch)
		{
			ACC.modals.confirmModalOK(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.productSelectionLocationCartMismatch,
				function () 
				{
					var obj = { entries: entries, selectionProductLocation: selectionProductLocation, selectionProductIsAPI: selectionProductIsAPI, clearCurrentCart: true };
					ACC.neorisproduct.addItemsToCartServerCall(obj);
				},
      			null);
			
			return;
		}
		
		// if mismatch between selections family
		if (variableProductFamily)
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.productSelectionFamilyMixup);
			return;
		}
		
		// if mismatch between cart and selection family
		if (cartProductFamilyMismatch)
		{
			ACC.modals.confirmModalOK(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.productSelectionFamilyCartMismatch,
				function () 
				{
					var obj = { entries: entries, selectionProductLocation: selectionProductLocation, selectionProductIsAPI: selectionProductIsAPI, clearCurrentCart: true };
					ACC.neorisproduct.addItemsToCartServerCall(obj);
				},
      			null);
			
			return;
		}
		
		// min max validation
		if(!ACC.neorisMinMaxValidator.isValidProductList(allProducts))
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.minMaxValidationOnError);
			return;
		}

		var obj = { entries: entries, selectionProductLocation: selectionProductLocation, selectionProductIsAPI: selectionProductIsAPI, clearCurrentCart: false };
		ACC.neorisproduct.addItemsToCartServerCall(obj);
	},
	
	addItemsToCartServerCall: function(obj)
	{
		var info = "jsonObject=" + JSON.stringify(obj);

		jQuery.ajax
		({
			url: addCartBatch,
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: info,
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (addToCartResult)
			{
				// assign cart product location after success cart modification
				cartProductLocation = obj.selectionProductLocation;
				
				// refresh variable after success cart modification
				cartHasAPIProducts = obj.selectionProductIsAPI;

				ACC.neorisproduct.clearAmounts();

				refreshMiniCart();

				ACC.neorisproduct.$cartPopup.hide();
				ACC.neorisproduct.$cartPopup.html(addToCartResult.cartPopupHtml);

				$('#add_to_cart_close').click(function(event) {
					event.preventDefault();
					ACC.neorisproduct.$cartPopup.hide();
				});

				ACC.neorisproduct.$cartPopup.fadeIn();
				if (typeof timeoutId != 'undefined') {
					clearTimeout(timeoutId);
				}
				timeoutId = setTimeout(function() {ACC.neorisproduct.$cartPopup.fadeOut();}, 5000);
				
			},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("e", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			},
			complete: function ()
			{
				unblockUI();
			}
		});
	},
	
	changePageAction: function (event)
	{
		//  just put the anchor into a variable to be used inside another function
		var clickedAchor = this; 

		if (clickedAchor.href.indexOf("#") > 0)
			return false;
		
		var entries = new Array();
		var allProducts = jQuery(ACC.neorisproduct.allProductsSelector);
			jQuery(allProducts).each(function(eachIndex, eachObject)
			{
				// check if has qty values assigned
				var stockQty = jQuery(eachObject).find("#stockQty").val();
				var rollingScheduleQty = jQuery(eachObject).find("#rollingScheduleQty").val();
				
				if (stockQty != 0 || rollingScheduleQty != 0)
				{
					var obj = { };
		
					// if its ok get product data
					obj.productCode = jQuery(eachObject).attr("productCode");
					obj.productLocation = jQuery(eachObject).attr("productLocation");
					// get the rolling schedule date
					obj.rollingScheduleDate = jQuery(eachObject).find("#rollingScheduleDate").val();
					obj.stockQty = stockQty;
					obj.rollingScheduleQty = rollingScheduleQty;
					
					entries[entries.length] = obj;
				}
			});

			if (entries.length != 0)
			{
				// prevent the default behavior
				event.preventDefault();
				
				ACC.modals.confirmModalOK(ACC.neorisproduct.messages.changePageTitle, ACC.neorisproduct.messages.changePageMessage, 
		      			function () { window.location.href = clickedAchor.href },
		      			null);
				
			}
			else
			{
				return true;
			}
	},
	
	validateExitPage: function(obj)
	{
		if (!ACC.neorisproduct.checkForProductEntries)
			return;

		var entries = new Array();
		var allProducts = jQuery(ACC.neorisproduct.allProductsSelector);
			jQuery(allProducts).each(function(eachIndex, eachObject)
			{
				// check if has qty values assigned
				var stockQty = jQuery(eachObject).find("#stockQty").val();
				var rollingScheduleQty = jQuery(eachObject).find("#rollingScheduleQty").val();
				
				if (stockQty != 0 || rollingScheduleQty != 0)
				{
					var obj = { };
		
					// if its ok get product data
					obj.productCode = jQuery(eachObject).attr("productCode");
					obj.productLocation = jQuery(eachObject).attr("productLocation");
					// get the rolling schedule date
					obj.rollingScheduleDate = jQuery(eachObject).find("#rollingScheduleDate").val();
					obj.stockQty = stockQty;
					obj.rollingScheduleQty = rollingScheduleQty;
					
					entries[entries.length] = obj;
				}
			});

		if (entries.length == 0)
		{
			return;
		}
		
		return ACC.neorisproduct.messages.changePageMessage;
	},
	
	// this and changePageAction are not longer required
	bindAllPagerAnchors: function()
	{
		$(ACC.neorisproduct.$allPagerAnchors).click(ACC.neorisproduct.changePageAction);
	},

	bindValidateExitPage: function()
	{
		jQuery(window).bind('beforeunload', ACC.neorisproduct.validateExitPage);
	},

	bindAll : function()
	{
		ACC.neorisproduct.bindToAddItemsToCartButton();
		ACC.neorisproduct.bindValidateExitPage();
	}
};

$(document).ready(function()
{
	ACC.neorisproduct.bindAll();
	
	ACC.neorisproduct.checkForProductEntries = true;
});
