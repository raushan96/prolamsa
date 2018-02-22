ACC.descriptionQuickView  =
{
	popupUrl: null,
	popup: null,
	cssClass: "product-quick-view",
	lastCode: null,
	currentMousePos: {x: 0, y: 0},
	lastMousePos: {x: 0, y: 0},

	initialize: function()
	{
		var popup = jQuery("<div></div>");
		popup.addClass(ACC.descriptionQuickView.cssClass);

		jQuery(popup).hide();
		jQuery(document.body).append(popup);

		ACC.descriptionQuickView.popup = popup;
		
		jQuery(document).mousemove(function(event)
		{
			ACC.descriptionQuickView.currentMousePos.x = event.pageX;
			ACC.descriptionQuickView.currentMousePos.y = event.pageY;
		});
	},

	bindAll: function()
	{
		var popup = jQuery(ACC.descriptionQuickView.popup);
		var link = jQuery("#" + ACC.descriptionQuickView.triggerLinkId);
		
		jQuery(link).mouseover(this.popUp);
		
		jQuery(popup).find("#ajax_cart_close").click(function(e)
		{
			e.preventDefault();
			jQuery(popup).hide();
		});

		jQuery(popup).mouseleave(function()
		{
			setTimeout(function() { jQuery(popup).hide(); }, 100);
		});
	},

	popUp: function(code, target, event)
	{
		ACC.descriptionQuickView.lastMousePos = jQuery.extend({}, ACC.descriptionQuickView.currentMousePos);

		var popup = jQuery(ACC.descriptionQuickView.popup);

		jQuery(popup).hide();
		
//		var position = jQuery(target).position();
//		var top = position.top;
		
		var width  = jQuery(target).width();		
		var offset = jQuery(target).offset();		
		
//		jQuery(popup).css("top", top - 20);//NA pos.top);
//		jQuery(popup).css("left", event.clientX - 10);//NA pos.left - jQuery(popup).width() + jQuery(link).width());

		// cache the last code to avoid overload the server
		if (ACC.descriptionQuickView.lastCode != code)
		{
			ACC.descriptionQuickView.lastCode = code;

			jQuery.ajax({
				url: ACC.descriptionQuickView.popupUrl,
				cache: false,
				type: 'GET',
				success: function(result)
				{
					setTimeout(function()
					{
						jQuery(popup).html(result);
						jQuery(popup).fadeIn();
						var mousePos = ACC.descriptionQuickView.currentMousePos;
						jQuery(popup).css("top", mousePos.y - 15);
						jQuery(popup).css("left", mousePos.x + 15);
//						jQuery(popup).css("left", offset.left + width);
//						jQuery(popup).css("top",  offset.top);
					}
					, 250);
					//ACC.descriptionQuickView.bindAll();
				}
			});
		}
		else
		{
			// open cached info
			jQuery(popup).fadeIn();
		}
	},
	
	closePopUp: function()
	{
		ACC.descriptionQuickView.lastMousePos = jQuery.extend({}, ACC.descriptionQuickView.currentMousePos);

		var popup = jQuery(ACC.descriptionQuickView.popup);

		jQuery(popup).hide();
	}
	
};

jQuery(document).ready(function()
{
	ACC.descriptionQuickView.initialize();
});

