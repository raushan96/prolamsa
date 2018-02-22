<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<%@ attribute name="unitList" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedUnit" required="true" type="de.hybris.platform.core.model.product.UnitModel" %>

<c:set var="selected" value="" />
<!--NEORIS_CAHNGE# Disable Selector for My-Account modules  -->
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />

<div>
<c:choose>
		<c:when test="${baseStore.uid eq '2000'}">
			<span style="margin-right: 3px; margin-left: 0px;"><spring:theme code="unitselector.using.2000"/></span>
		</c:when>
		<c:otherwise>
			<span style="margin-right: 3px; margin-left: 0px;"><spring:theme code="unitselector.using"/></span>
		</c:otherwise>
	</c:choose>
<select id="unitSelectorElement" >
	<c:forEach items="${unitList}" var="eachUnit">
		<c:choose>
			<c:when test="${eachUnit.code eq selectedUnit.code}">
				<c:set var="selected" value="selected" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>

		<option value="${eachUnit.code}" ${selected}>${eachUnit.name}</option>
	</c:forEach>
</select>
</div>

<script type="text/javascript">

var isAxis = unitCode.search("axis") != -1;

var homeUrl = "<spring:url value="/" />";

onDOMLoaded(bindUnitSelectorElement);

function bindUnitSelectorElement()
{
	var component = jQuery("#unitSelectorElement");

	jQuery(component).on("change", function()
	{
		var value = jQuery(component).val();

		jQuery.ajax
		({
			url: "<c:url value="/misc/setUnit.json" />",
			type: 'POST',
			dataType: 'json',
			data: {unit: value},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				//NEORIS_CHANGE # 104, Validation for UoM for Asix whne exist any changes between API and ASTM
				if (data != null)
				{
					if (data.status == 0)
					{						
						var currentApi = unitCode.search("api");
						var newApi = component.val().search("api");
						var hasCart = data.hasCart;
												
						//validation ONLY for Axis UoM
						if (isAxis) 
						{
							if (currentApi != -1 && newApi != -1) 
							{
								//No change: two are API
								//location.href(homeUrl);
								window.location.href = homeUrl;
							} else if (currentApi == -1 && newApi == -1) { 
								//No change: two are ASTM
								//location.reload(true);
								window.location.href = homeUrl;
							} else if (currentApi == -1 && newApi != -1) {
								//There is change: current is ASTM and new is API (we change to API)														
								//confirmDeleteCart("/store/catalog/c/5CT",hasCart);
								confirmDeleteCart(homeUrl, hasCart);
							} else if (currentApi != -1 && newApi == -1) {
								//There is change: current is API and new is ASTM (we change to ASTM)									
								//confirmDeleteCart("/store/catalog/c/ASTM",hasCart);
								confirmDeleteCart(homeUrl, hasCart);
							}
						} else {
							// if no axis, just reload the page
							location.reload(true);
						}
						
						//ACC.modals.messageModal("<spring:theme code="unitselector.confirm.title"/>", "<spring:theme code="unitselector.refreshing-page"/>");
						
					}
					else
						ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
				}
				else
				{
					ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed.");
				}
			},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			},
			complete: function ()
			{
				if (!isAxis)
					unblockUI();		
			}
		})
	});
}


//NEORIS_CHANGE #104
function confirmDeleteCart(url,hasCart){
	
    if (hasCart == "1") {   	    	
    	
    	ACC.modals.confirmModalOK("<spring:theme code="cart.delete.title"/>", "<spring:theme code="cart.delete.confirmSpecial"/>", 
      			function () { deleteCart(url) },
      			function() { jQuery("#unitSelectorElement").val(unitCode);jQuery("#unitSelectorElement").change();},
      			null);    	    	
    } else {
    	location.href=url; 	
    }
  	
}

function deleteCart(url)
 	{
		$.ajax
			({
				url:         "<c:url value="/cart/deleteCart.json" />",
				type:        'POST',
				dataType:    'json',
				data: { url: url},
				beforeSend: function ()
				{
					blockUI();
				},
				error: function (xht, textStatus, ex)
				{
					unblockUI();
					ACC.modals.messageModal("Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
	        	},
	        	success: function (data)
	        	{
	        		if (data.status == 0)
	            	{
	        			// removed to avoid message of cart cleared
	            		//ACC.modals.messageModal("<spring:theme code="cart.delete.title"/>", "<spring:theme code="cart.delete.success"/>");
	                	location.reload(true);           		
	            	}
	        		else
	        		{
	        			unblockUI();
	        			ACC.modals.messageModal("Error:" + data.message);
	        		}
	        	}
			});
  }
</script>