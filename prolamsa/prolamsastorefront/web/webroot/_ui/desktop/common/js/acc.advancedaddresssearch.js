ACC.advancedaddresssearch =
{
	popupId: "advancedAddressSearch",
	triggerLinkId: advanceAddressSearchLink.linkId,
	popupUrl: advanceAddressSearchLink.url,
	popupUrlFunc: advanceAddressSearchLink.popUrlFunc,
	popup: null,
	cssClass: "advanced-client-search", 
		
	initialize: function()
	{		
				
		var popup = jQuery("<div></div>");
		popup.attr("id", ACC.advancedaddresssearch.popupId);
		popup.addClass(ACC.advancedaddresssearch.cssClass);
		
		jQuery(popup).hide();
		jQuery(document.body).append(popup);       
		ACC.advancedaddresssearch.popup = popup;
	},

	bindAll: function()
	{
		var popup = jQuery(ACC.advancedaddresssearch.popup);
		var link = jQuery("#" + ACC.advancedaddresssearch.triggerLinkId);

		jQuery(link).click(this.popUp);

		jQuery(popup).find("#ajax_cart_close").click(function(e)
		{
			e.preventDefault();
			jQuery(popup).hide();
		});		
	},

	popUp: function()
	{
		var popup = jQuery(ACC.advancedaddresssearch.popup);
		var link = jQuery("#" + ACC.advancedaddresssearch.triggerLinkId);

		var pos = jQuery(link).offset();
	
		jQuery(popup).hide();
		jQuery(popup).css("top", pos.top - 200);
		jQuery(popup).css("left", pos.left - jQuery(popup).width() + jQuery(link).width());

		var url =  ACC.advancedaddresssearch.popupUrl;
		
		if (ACC.advancedaddresssearch.popupUrlFunc != null)
			url = ACC.advancedaddresssearch.popupUrlFunc();

		jQuery.ajax({
			url: url,
			cache: false,
			type: 'GET',
			success: function(result)
			{			
				jQuery(popup).html(result);			
				jQuery(popup).fadeIn();				
			}
		});
	}
};

jQuery(document).ready(function()
{	
	ACC.advancedaddresssearch.initialize();
	ACC.advancedaddresssearch.bindAll();
});

