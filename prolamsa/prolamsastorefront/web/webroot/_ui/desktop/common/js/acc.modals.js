ACC.modals =
{
	elementId: "#dialog-confirm",
	paragraphId: "#dialog-contents",
	canceled: true,

	messageModal: function(title, contents)
	{
		var options = {
			title: title,
			resizable: false,
			modal: true,
			zIndex: 10000,
			buttons: {}
		};
		
		options.buttons["OK"] = ACC.modals.closeDialog;

		jQuery(ACC.modals.elementId).find(ACC.modals.paragraphId).html(contents);
		jQuery(ACC.modals.elementId).dialog(options);
		jQuery(ACC.modals.elementId).dialog("open");
	},

	confirmModal: function(title, contents, buttons, paramOptions)
	{
		var options = {
			title: title,
			resizable: false,
			//height: 240,
			modal: true,
			zIndex: 10000,
			buttons: {}
		}
		
		jQuery.extend(options, paramOptions);

		for (var key in buttons)
		{
			var action = buttons[key];
			var func = null;

			if (action != null)
				func = ACC.modals.createPerformActionFunction(action);
			else
				func = ACC.modals.closeDialog;
			
			options.buttons[key] = func;
		}

		ACC.modals.canceled = true;

		jQuery(ACC.modals.elementId).find(ACC.modals.paragraphId).html(contents);
		jQuery(ACC.modals.elementId).dialog(options);
		jQuery(ACC.modals.elementId).dialog("open");
	},
	
	createPerformActionFunction: function(action)
	{
		return function() { ACC.modals.performAction(action); };
	},
	
	closeDialog: function()
	{
		jQuery(ACC.modals.elementId).dialog("close");
	},

	performAction: function(aFunc)
	{
		ACC.modals.canceled = false;
		aFunc();
		ACC.modals.closeDialog();
	},

	performCancelAction: function(aFunc)
	{
		if (ACC.modals.canceled)
			if (aFunc != null)
				aFunc();
	},

	confirmModalOK: function(title, contents, okAction, cancelAction, paramOptions)
	{
		var buttons = {};
		buttons["OK"] = okAction;
		buttons["Cancel"] = null;

		var cancelCall = function() { ACC.modals.performCancelAction(cancelAction); }
		
		var options = { close: cancelCall };
		jQuery.extend(options, paramOptions);

		ACC.modals.confirmModal(title, contents, buttons, options)
	},

	closeModal: function(container)
	{
		jQuery(container).dialog("close");
	},

	openOnModal: function(title, urlContent, container, paramOptions, initFunction)
	{
		var sep = "?";

		if (urlContent.indexOf(sep) != -1)
			sep = "&";

		jQuery(container).html("");
		jQuery(container).load(urlContent + sep + "ts=" + new Date().getTime(), initFunction);

		var options = {
			title: title,
			resizable: false,
			//height: 340,
			modal: true,
			zIndex: 10000
		}
		
		jQuery.extend(options, paramOptions);

		jQuery(container).dialog(options);
		jQuery(container).dialog("open");
	}
};