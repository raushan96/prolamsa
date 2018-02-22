ACC.advancedclientsearch =
{
	popupId: "advancedClientSearch",
	triggerLinkId: (typeof advanceClientSearch != 'undefined') ? advanceClientSearch.linkId : null,
	popupUrl: (typeof advanceClientSearch != 'undefined') ? advanceClientSearch.url : null,
	popup: null,
	cssClass: "advanced-client-search",

	initialize: function()
	{
		var popup = jQuery("<div></div>");
		popup.attr("id", ACC.advancedclientsearch.popupId);
		popup.addClass(ACC.advancedclientsearch.cssClass);
		
		jQuery(popup).hide();
		jQuery(document.body).append(popup);

		ACC.advancedclientsearch.popup = popup;
	},

	bindAll: function()
	{
		var popup = jQuery(ACC.advancedclientsearch.popup);
		var link = jQuery("#" + ACC.advancedclientsearch.triggerLinkId);

		jQuery(link).click(this.popUp);

		jQuery(popup).find("#ajax_cart_close").click(function(e)
		{
			e.preventDefault();
			jQuery(popup).hide();
		});		
	},

	popUp: function()
	{		
		var popup = jQuery(ACC.advancedclientsearch.popup);
		var link = jQuery("#" + ACC.advancedclientsearch.triggerLinkId);

		var pos = jQuery(link).offset();

		jQuery(popup).hide();
		jQuery(popup).css("top", pos.top);
		jQuery(popup).css("left", pos.left );

		jQuery.ajax({
			url: ACC.advancedclientsearch.popupUrl,
			cache: false,
			type: 'GET',
			success: function(result)
			{
				jQuery(popup).html(result);
				jQuery(popup).fadeIn();
			}
		})
	}
};

jQuery(document).ready(function()
{
	ACC.advancedclientsearch.initialize();
	ACC.advancedclientsearch.bindAll();
});

