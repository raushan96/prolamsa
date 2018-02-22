<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/product" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<script type="text/javascript">

jQuery(document).ready(function()
		{
			jQuery("#shippingInstructions_textArea").val("");

			jQuery("#save_formShippingInstructions").click(acceptShippingInstructions);
			
			jQuery("#shippingInstructions_textArea").keypress(function(event)
			{
				jQuery("#error-note").hide();
			});
		});
		
		function acceptShippingInstructions()
		{			
			var instructions = jQuery("#shippingInstructions_textArea").val();
			
			if(instructions == "")
			{
				jQuery("#error-note").show();
				return;	
			}
			
			var dialogContainer = jQuery(this).parent().parent();
			var popupCallback = jQuery(dialogContainer).dialog( "option", "popupCallback" );
			popupCallback.call(this, instructions);
			jQuery(dialogContainer).dialog("close");
		}

</script>

<br/>
 
<form id="formShippingInstructions">
	<span><spring:theme code="text.order.shippingInstructions.popup.line"/></span> 
	<textarea rows="5" cols="30" id="shippingInstructions_textArea"></textarea>
</form>

<br/>

<div class="div-text-align-right">
	<formUtil:formButton 
		id="save_formShippingInstructions"
		type="button" 
		tabindex="107"
		springThemeText="text.order.shippingInstructions.popup.button.save"
		 />
</div>

<br/>

<p id="error-note" class="saveDraft" style="color: red; display: none;">
	<spring:theme code="text.order.shippingInstructions.popup.error"/>
</p>
