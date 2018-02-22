<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="locations" required="false" type="java.util.Collection" %>
<%@ attribute name="selectedLocation" required="false" type="mx.neoris.core.enums.ProductLocation" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<div>
	<!-- <input type="text" id="hid" name="hid"> -->

	<span style="margin-right: 3px; margin-left: 5px;"><spring:theme code="location.using"/></span>
	<select id="locationSelectorElement" style=" margin-top: 11px; min-width: 10px;" >
		<%--
		<c:forEach items="${locations}" var="eachLocation">
			 <c:choose>
				 <c:when test="${eachLocation.code eq selectedLocation.code}">
					<c:set var="selected" value="selected" />
				</c:when>
				<c:otherwise>
					<c:set var="selected" value="" />
				</c:otherwise>
			</c:choose> 
			
			<option value="${eachLocation.code}" ${selected}>${eachLocation.code} gg</option>
		</c:forEach>
		--%>
	</select>
</div> 

<script type="text/javascript">

onDOMLoaded(initLocationSelectorElement2);

function initLocationSelectorElement2 ()
{
	bindLocationSelectorElement2();
	
	var componentX = jQuery("#locationSelectorElement");
	
	jQuery(componentX).on("change", function()
	{
		var newlocation = jQuery("#locationSelectorElement").val();
		
		jQuery.ajax
		({
			url: "<c:url value="/misc/setNewLocation.json" />",
			type: 'POST',
			dataType: 'json',
			data: {newlocation: newlocation},
			beforeSend: function ()
			{
				blockUI();
			},
			//success: function (data)
			//{
			//	if (data != null)
			//	{
			//		if (data.status == 0)
			//		{
			//			 var val = data.newLocationSelected;
			//		        alert("NEW " + val );
			//		                $('#hid').val(val); 
			//		}
			//	}
			//},	
			complete: function ()
			{
				location.reload();
				unblockUI();
			},
			error: function(a,b,c)
			{
				//alert("ERROR");
			}
		}) 
	});
}

function bindLocationSelectorElement2()
{
	var component = jQuery("#b2bUnitSelectorElement");
	var location = jQuery("#locationSelectorElement");
	//var newlocation = jQuery("#hid");
	
	var value = jQuery(component).val();
	var location = jQuery(location).val();
	//var newlocation = jQuery(newlocation).val();
	
	//alert(newlocation);
	
	//if(newlocation == "")
	//{
	//	newlocation = 0;
	//}
	
	//alert(newlocation);
	
	jQuery.ajax
	({
		url: "<c:url value="/misc/setLocation.json" />",
		type: 'POST',
		dataType: 'json',
		//data: {unit: value, cambioCliente: cambioCliente},
		//data: {unit: value, newlocation: newlocation},
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
					$.each(data.result, function(index)
					{
						$('#locationSelectorElement').append('<option value="'+ data.result[index].code +'"><spring:theme code= "'+ data.result[index].name +'"/></option>');
					});
					
					jQuery("#locationSelectorElement").val(data.result[0].selectedLocation);
					//$('#locationSelectorElement').change();
					
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
	
	//jQuery("#locationSelectorElement").on('change', changeLocation);
}

</script>
