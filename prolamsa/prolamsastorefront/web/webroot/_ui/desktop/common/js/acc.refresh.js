ACC.refresh = {

	bindAll: function()
	{
		this.getCheckoutCartDataAndRefreshPage();
		
		
		jQuery.extend({
		   postJSON: function( url, data, callback) {
			  return jQuery.post(url, data, callback, "json");
		   }
		});
		
		$.blockUI.defaults.overlayCSS = {};
		$.blockUI.defaults.css = {};	
		
	},

	refreshCartTotals: function(checkoutCartData)
	{
		$('#cart_totals_div').html($('#cartTotalsTemplate').tmpl(checkoutCartData));
	},

	refreshCartItems: function(checkoutCartData)
	{
		$('#cart_items_tbody').html($('#cartItemsTemplate').tmpl(checkoutCartData));
	},

	refreshPage: function(checkoutCartData)
	{
		//update delivery address, delivery method and payment sections, and cart totals section
		// NEORIS_CHANGE #74
		//ACC.address.refreshDeliveryAddressSection(checkoutCartData);
		ACC.deliverymode.refreshDeliveryMethodSection(checkoutCartData);
		ACC.paymentmethod.refreshPaymentDetailsSection(checkoutCartData);
		ACC.costcenter.refreshCostCenterSection(checkoutCartData);
		ACC.paymenttype.refreshPaymentTypeSection(checkoutCartData);
		ACC.refresh.refreshCartTotals(checkoutCartData);
		//NEORIS_CHANGE #74 
		//ACC.placeorder.updatePlaceOrderButton();
		//ACC.negotiatequote.updateRequestQuoteButton();
		ACC.replenishment.updateScheduleReplenishmentButton();
		
		if(updateButtons !== undefined)
		{
			updateButtons();
		}
	},

	getCheckoutCartDataAndRefreshPage: function()
	{
		if(typeof getCheckoutCartUrl != 'undefined')
		{
			$.getJSON(getCheckoutCartUrl, function(checkoutCartData) {ACC.refresh.refreshPage(checkoutCartData);});
		}
	}
	
	
};

$(document).ready(function()
{
	ACC.refresh.bindAll();
});
	
	
	
	
	
	
	
	
	

			
	
	