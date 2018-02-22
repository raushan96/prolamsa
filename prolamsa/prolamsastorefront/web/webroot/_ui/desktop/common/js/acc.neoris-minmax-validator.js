ACC.neorisMinMaxValidator =
{
	// cached jQuery objects
	allInputsSelectorToValidate:		"input.validateMinMax",
	minAttributeName:					"valMin",
	maxAttributeName:					"valMax",
	classOnErrorName:					"minMaxValidationOnError",
	classAllowZeroEmptyName:			"validateMinMaxAllowZeroEmpty",
	
	isValidProductList: function (productList)
	{
		var isValid = true;
		
		// review all row
		jQuery(productList).each(function(eachIndex, eachObject)
		{
			// getting all inputs to validate per each row
			var inputsToValidatePerObject = jQuery(eachObject).find("input.validateMinMax")
			
			// review inputs per row
			jQuery(inputsToValidatePerObject).each(function(index, object)
			{
				if(jQuery(object).val() == '' || jQuery(object).val() == 0)
				{
					return true; // skip iteration
				}
								
				if(!ACC.neorisMinMaxValidator.isInputValid(object))
				{
					isValid = false;
				}
			});
		});
		
		return isValid;
	},

	isInputValid: function (input)
	{
		// allow empty and zero
		if(jQuery(input).hasClass(ACC.neorisMinMaxValidator.classAllowZeroEmptyName))
		{
			if(jQuery(input).val() == '' || jQuery(input).val() == 0)
			{
				return true;
			}			
		}
		
		// getting input value
		var inputValue = numeral().unformat(jQuery(input).val());
		
		// getting min and max values
		var minValue = jQuery(input).attr(ACC.neorisMinMaxValidator.minAttributeName);
		var maxValue = jQuery(input).attr(ACC.neorisMinMaxValidator.maxAttributeName);
		
		// convert to numeric
		var minValueNumeric = parseFloat(minValue);
		var maxValueNumeric = parseFloat(maxValue);
		var inputValueNumeric = parseFloat(inputValue);
		
		// validation
		if(inputValueNumeric < minValueNumeric || inputValueNumeric > maxValueNumeric)
		{
			jQuery(input).addClass(ACC.neorisMinMaxValidator.classOnErrorName);
			ACC.neorisMinMaxValidator.bindInputOnError(input);
			
			return false
		}
		
		return true;
	},
		
	bindInputOnError: function(inputOnError)
	{
		jQuery(inputOnError).on("keydown", ACC.neorisMinMaxValidator.keypressOnErrorHandler);
	},
	
	keypressOnErrorHandler: function(event)
	{
		$(this).removeClass(ACC.neorisMinMaxValidator.classOnErrorName);
		ACC.neorisMinMaxValidator.unBindInputOnError(this);
	},
	
	unBindInputOnError: function(input)
	{
		jQuery(input).off("keydown", ACC.neorisMinMaxValidator.keypressOnErrorHandler);
	},
	
	bindAll : function()
	{		
	}
};

$(document).ready(function()
{
	ACC.neorisMinMaxValidator.bindAll();	
});
