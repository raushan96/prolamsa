<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="json" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/desktop/form" %>

<div class="product-variant-configuration">
<form id="variantConfigurationForm">
	<span ><spring:theme code="variant.partnumber" /></span> ${productData.visibleCode}<br/>
	<span ><spring:theme code="variant.Description" />:</span> ${productData.description}<br/>
	<br/>
	
	<input type="hidden" name="productCode" id="productCode" value="${productData.code}" />
	<input type="hidden" name="orderEntryNumber" id="orderEntryNumber" value="${configuration.orderEntryNumber}" />

	<span class="labelMedium"><spring:theme code="variant.Pressure" />:</span>
	<input class="inputSmall" type="text" name="pressure" id="pressureField" value="${configuration.pressure}" onkeypress="return isNumberKey(event);"> <spring:theme code="variant.psi" />
	<br/>
	
	<span class="labelMedium"><spring:theme code="variant.Duration" />:</span>
	<input class="inputSmall" type="text" name="duration" id="durationField" value="${configuration.duration}" onkeypress="return isNumberKey(event);">
	<spring:theme code="variant.seconds" />
	<br/>

	<span class="labelMedium"><spring:theme code="variant.Specific_stencil" />:</span>
	<form:formDropdown name="specificStencil" id="specificStencilField" css="dropDownLong" col="${stencilCol}" selection="${configuration.specificStencil}"/>
	<br/>
	
	<span class="labelMedium"><spring:theme code="variant.Special_drifter" />:</span>
	<form:formDropdown name="drifter" id="drifterField" css="dropDownLong" col="${drifterCol}" selection="${configuration.drifter}"/>
	<br/>
	
	<span class="labelMedium"><spring:theme code="variant.Specific_length" />:</span>
	<input class="inputSmall" type="text" name="length" id="lengthField" value="${configuration.length}">
	<spring:theme code="variant.ft" />
	<br/>
	<br/>
	
	<p><spring:theme code="variant.Charpy_Testing" /></p>
	
	<span class="labelLong columnAlignRight"><spring:theme code="variant.Location_of_test" />:</span>
	<form:formDropdown name="locationOfTest" id="locationOfTestField" css="dropDownLong" col="${locationTestCol}" selection="${configuration.locationOfTest}"/>
	<br/>
	
	<span class="labelLong columnAlignRight"><spring:theme code="variant.Sample_direction" />:</span>
	<form:formDropdown name="sampleDirection" id="sampleDirectionField" css="dropDownLong" col="${sampleDirectionCol}" selection="${configuration.sampleDirection}"/>
	<br/>
	
	<span class="labelLong columnAlignRight"><spring:theme code="variant.Test_temp" />:</span>
	<input class="inputSmall" type="text" name="testTemp" id="testTempField" value="${configuration.testTemp}" onkeypress="return isNumberKey(event);">
	<spring:theme code="variant.F." />
	<br/>
	
	<span class="labelLong columnAlignRight"><spring:theme code="variant.Sample_size" />:</span>
	<form:formDropdown name="sampleSize" id="sampleSizeField" css="dropDownLong" col="${sampleSizeCol}" selection="${configuration.sampleSize}"/>
	<br/>
</form>
	<br/>
	<br/>
	<br/>
	
	<div class="search-form-options-search-button">
		<a href="#" id="variantDeleteConfiguration"><spring:theme code="variant.Delete_configuration_and_start_again" /></a>
		<button id="variantConfigSave" class="button-float-right"><spring:theme code="variant.Save_and_go_back" /></button>
	</div>
</div>

<script type="text/javascript">
ACC.variantConfig.initFunction = function()
{
	jQuery("#variantConfigSave").click(variantConfigSaveAction);
	jQuery("#variantDeleteConfiguration").click(variantDeleteConfigurationAction);

	$("#lengthField").numeric_input({
			decimal:'.' 
		 });
	
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}


function variantConfigSaveAction()
{
	if (isValidConfig())
	{
		var info = jQuery("#variantConfigurationForm").serialize();
	
		jQuery.ajax
		({
			url: ACC.config.contextPath + "/product/variantConfigurationSave.json",
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: info,
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				if (data.status == 0)
				{
					ACC.variantConfig.closePopUp();
					if (ACC.variantConfig.saveConfigurationDone)
						ACC.variantConfig.saveConfigurationDone(ACC.variantConfig.productCode, ACC.variantConfig.cartEntryNumber);
				}
				else
					ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
	
			},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			},
			complete: function ()
			{
				unblockUI();
			}
		});
	}
}

function variantDeleteConfigurationAction()
{
	ACC.modals.confirmModalOK("<spring:theme code="variant.Product_Variant_Configuration"/>", "<spring:theme code="variant.Are_you_sure_want_to_delete_the_current_configuration"/>", variantDeleteConfigurationActionConfirmed);
}

function isValidConfig()
{
	var pressure = $("#pressureField").val();
	var duration = $("#durationField").val();
	var length = $("#lengthField").val();
	var testTemp = $("#testTempField").val();
	// 	Pressure = 1000 to 15000

	if (pressure != null && pressure.length > 0 && (pressure < pressureRange.lowerLimit || pressure > pressureRange.upperLimit))
	{
		ACC.modals.messageModal("<spring:theme code="variant.Product_Variant_Configuration"/>", "<spring:theme code="variant.error.pressure"/> " +  pressureRange.lowerLimit + " - " + pressureRange.upperLimit);
		return false;
	}
	
	//	Duration= from 5 to 20
	if (duration != null && duration.length > 0 && (duration < durationRange.lowerLimit || duration > durationRange.upperLimit))
	{
		ACC.modals.messageModal("<spring:theme code="variant.Product_Variant_Configuration"/>", "<spring:theme code="variant.error.duration"/> "  +  durationRange.lowerLimit + " - " + durationRange.upperLimit);
		return false;
	}
	
	
	//	lenght from 21 to 60
	if (length != null && length.length > 0 && (length < lengthRange.lowerLimit || length > lengthRange.upperLimit))
	{
		ACC.modals.messageModal("<spring:theme code="variant.Product_Variant_Configuration"/>", "<spring:theme code="variant.error.length"/> "  +  lengthRange.lowerLimit + " - " + lengthRange.upperLimit);
		return false;
	}
	
	//	testTemp = 32 to 49
	if (testTemp != null && testTemp.length > 0 && (testTemp < testTempRange.lowerLimit || testTemp > testTempRange.upperLimit))
	{
		ACC.modals.messageModal("<spring:theme code="variant.Product_Variant_Configuration"/>", "<spring:theme code="variant.error.temp"/> "  +  testTempRange.lowerLimit + " - " + testTempRange.upperLimit);
		return false;
	}
	
	return true;
}
	
function variantDeleteConfigurationActionConfirmed()
{
	
		var info = jQuery("#variantConfigurationForm").serialize();

		jQuery.ajax
		({
			url: ACC.config.contextPath + "/product/variantConfigurationDelete.json",
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: info,
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				if (data.status == 0)
				{
					ACC.variantConfig.closePopUp();
					if (ACC.variantConfig.deleteConfigurationDone)
						ACC.variantConfig.deleteConfigurationDone(ACC.variantConfig.productCode, ACC.variantConfig.cartEntryNumber);
				}
				else
					ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
			},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			},
			complete: function ()
			{
				unblockUI();
			}
		});
	
}
</script>