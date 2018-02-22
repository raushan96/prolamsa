ACC.categorynavigation =
{
	initialize : function() {},
	
	processCategoryNavigation: function(navigationPath)
	{
		// if UoM is API
		var isApi = unitCode.indexOf('api') != -1;
		// if UoM is ASTM
		var isASTM = !isApi;

		// if category is API compat
		var apiCompat = (navigationPath.indexOf('5L') != -1) || (navigationPath.indexOf('5CT') != -1)
		// if category is ASTM compat
		var astmCompat = (navigationPath.indexOf('ASTM') != -1);

		// proceed on navigation if compat
		if (isApi && apiCompat || (isASTM && astmCompat))
		{
			window.location = ACC.config.contextPath + "/" + navigationPath;
		}
		else
		{
			ACC.modals.messageModal(categorySelectionMsg, updateUnitOfMeasureMsg);
		}
	}
};

$(document).ready(function()
{
	ACC.categorynavigation.initialize();
});

navigationLinkFunction = ACC.categorynavigation.processCategoryNavigation;
