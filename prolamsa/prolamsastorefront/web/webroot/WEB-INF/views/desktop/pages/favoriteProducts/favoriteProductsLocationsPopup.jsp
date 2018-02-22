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
			jQuery("#continue_formProductLocations").click(continueOnClick);
			jQuery("#locationSelector").change(function() {
			});
		});
		
		function continueOnClick()
		{	
			var locationSelected = jQuery("#locationSelector").val()
			if (locationSelected == "selectOne")
			{
				jQuery("#error-note").show();
				return;	
			}
			
			var dialogContainer = jQuery(this).parent().parent();
			var popupCallback = jQuery(dialogContainer).dialog( "option", "popupCallback" );
			popupCallback.call(this, locationSelected);
			jQuery(dialogContainer).dialog("close");
		}

</script>

<br/>
 
<form id="formProductLocations">
	<select id="locationSelector" style="background: white;">
	  <option value="selectOne"><spring:theme code="customerFavoriteProducts.reset.modal.locationSelector.pleaseSelect" /></option> 
		<c:forEach items="${productLocations}" var="eachLocation">
		 <option value="${eachLocation.name}">${eachLocation.locDisplayName}</option>
	  </c:forEach>
	</select>	
</form>

<br/>

<div class="div-text-align-right">
	<formUtil:formButton 
		id="continue_formProductLocations"
		type="button" 
		tabindex="107"
		springThemeText="customerFavoriteProducts.reset.modal.locationSelector.continue.button"
		 />
</div>

<br/>

<p id="error-note" class="saveDraft" style="color: red; display: none;">
	<spring:theme code="customerFavoriteProducts.reset.modal.locationSelector.error"/>
</p>