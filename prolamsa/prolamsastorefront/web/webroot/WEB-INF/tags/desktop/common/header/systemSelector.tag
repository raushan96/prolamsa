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

<%@ attribute name="systemList" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedSystem" required="false" type="mx.neoris.core.enums.MeasurementSystemType" %>

<c:set var="selected" value="" />
<!--NEORIS_CAHNGE# Disable Selector for My-Account modules  -->
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />

<div>
<span style="margin-right: 3px; margin-left: 0px;" ><spring:theme code="systemselector.using"/></span>
<select id="systemSelectorElement">
<c:forEach items="${systemList}" var="eachSystem">
	<c:choose>
		<c:when test="${eachSystem eq selectedSystem}">
			<c:set var="selected" value="selected" />
		</c:when>
		<c:otherwise>
			<c:set var="selected" value="" />
		</c:otherwise>
	</c:choose>

    <%--<input type="radio" name="systemSelectorElement" value="${eachSystem.code}" ${checked}>${eachSystem.name} --%>
    <option value="${eachSystem}" ${selected} ><spring:theme code="text.measurementSystemType.${eachSystem}"/></option>  
</c:forEach>
</select>
</div>

<script type="text/javascript">

var homeUrl = "<spring:url value="/" />";

onDOMLoaded(bindUnitSelectorElement);

function bindUnitSelectorElement()
{
	var component = jQuery("#systemSelectorElement");
	
	jQuery(component).on("change", function()
	{
		var value = jQuery(component).val();
				
		jQuery.ajax
		({
			url: "<c:url value="/misc/setUnitBySystem.json" />",
			type: 'POST',
			dataType: 'json',
			data: {unit: value},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				
				if (data != null)
				{
					if (data.status == 0)
					{																																												
						if (data.result != null)
						{
							$('#unitSelectorElement').empty();							
							$.each(data.result, function(index) {							
								 $('#unitSelectorElement').append('<option value="'+ data.result[index].code +'">'+data.result[index].name +'</option>');								       
							});
							
							jQuery("#unitSelectorElement").val(data.result[0].code);
							$('#unitSelectorElement').change();
						}						
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
				unblockUI();		
			}
		})
	});
}

  
</script>