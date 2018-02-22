<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selectedTransportationMode" required="false" type="de.hybris.platform.commercefacades.user.data.TransportationModeData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url value="/checkout/single/summary/setTransportationMode.json" var="setTransportationModeUrl" />
<spring:url value="/checkout/single/summary/getIncoterms.json" var="getIncotemrsUrl" />
<spring:url value="/checkout/single/summary/setRequestedDeliveryDate.json" var="setRequestedDeliveryDateUrl" />

<c:set var="addedDays" value="${addedDays}"/>

<script type="text/javascript">
	var setTransportationModeUrl = '${setTransportationModeUrl}';
	var setRequestedDeliveryDateUrl = '${setRequestedDeliveryDateUrl}';
	var getIncotermsURL = '${getIncotemrsUrl}';
	var selectIncotermText = '<spring:theme code="checkout.summary.select_incoterm" />';
</script>

	<!-- NEORIS_CHANGE #74 Change on template-->
<script id="transportationModeTemplate" type="text/x-jquery-tmpl">
	<select id="transportationModeSelect">
		{{if deliveryAddress}}
			<option >{{= deliveryAddress.transportationMode.name}}</option>
		{{else}}
			<option><spring:theme code="checkout.summary.delivery_addres.select_address" /></option>				
		{{/if}}
	</select>
</script>

<div class="checkout_summary_flow_c complete" id="checkout_summary_deliverymode_div">
	<div class="item_container_holder">
		<ycommerce:testId code="checkout_deliveryModeData_text">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2>
					<spring:theme code="checkout.summary.deliveryMode.header" htmlEscape="false"/>
					<span></span></h2>
			</div>

			<!-- NEORIS_CHANGE #74 Replaced UL with select -->
			<div class="item_container" >
				<select id="transportationModeSelect">
					<c:if test="${empty selectedTransportationMode}">
						<option selected value="none"><spring:theme code="checkout.summary.select_transportation_mode" /></option>
					</c:if>
					<c:if test="${not empty selectedTransportationMode}">
						<option selected value="none"><spring:theme code="checkout.summary.select_transportation_mode" /></option>
					</c:if>
					<c:forEach items="${transportationModes}" var="eachTransportation">
						<c:set var="selected" value=""/>
						<c:if test="${selectedTransportationMode.code eq eachTransportation.code}">
							<c:set var="selected" value="selected"/>
						</c:if>
						<option ${selected} value="${eachTransportation.code}"> ${eachTransportation.name} (${eachTransportation.maxCapacity} Tons)</option>
					</c:forEach>									
				</select>								
			</div>
			
			<div class="item_container" >
				<select id="incotermsSelect">
					<option selected value="none"><spring:theme code="checkout.summary.select_incoterm" /></option>
					<c:if test="${not empty selectedTransportationMode}">
						<c:forEach items="${incoterms}" var="eachIncoterm">
							<c:set var="selected" value=""/>
							<c:if test="${selectedTransportationMode.incotermCode eq eachIncoterm.incotermCode}">
								<c:set var="selected" value="selected"/>
							</c:if>
							<option ${selected} value="${eachIncoterm.incotermCode}"> ${eachIncoterm.incotermCode} - ${eachIncoterm.incotermDescription }</option>
					</c:forEach>
					</c:if>
				</select>								
			</div>
			
			<br /><br />
			
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2>
					 <spring:theme code="checkout.summary.requestedDeliveryDate" htmlEscape="false"/>
					<span></span></h2>
			</div>
			<div class="item_container">	   				   				   
	            <form:form action="${placeOrderUrl}" id="placeOrderForm3" commandName="placeOrderForm">
	                  <input type="text"								       
				       value="${placeOrderForm.requestedDeliveryDate}"						       
				       required="required" 
				       id="requestedDeliveryDate"
				       name="requestedDeliveryDate"
				       readonly="readonly"
				       style="font-size: 12px; color: black; width: 90px;"								       					       							       
				      />
				      <img  onclick="openCalendar()"   src="${themeResourcePath}/images/icon_calendar.gif" alt="" style="width: 20px; vertical-align: middle;">
	             </form:form>				      					  									 									    
			</div>
			<input type="hidden" id="site" name="site" value="${baseStore.uid}"  style="display: none;"  />
		</ycommerce:testId>
	</div>

	<!-- NEORIS_CHANGE #74 Removed "Edit Deli..." button -->
</div>

<script type="text/javascript">
onDOMLoaded(initDocumentList);

function bindSearchFormElements()
{
	//jQuery(".sortDropDownFieldClass").on('change', submitForm);	
}

function openCalendar(){	
	jQuery("#requestedDeliveryDate").focus();
}

function initDocumentList()
{	
		
	var dateToday = new Date();	
	dateToday.setDate(dateToday.getDate() + ${addedDays});
	
	if(document.getElementById("site").value == "1000" || document.getElementById("site").value == "5000")
	{
		var dateFormatCustom = "dd-M-yy";
	}else
	{
		var dateFormatCustom = "M-dd-yy";
	}
	
	$( "#requestedDeliveryDate" ).datepicker({
		  //<formDate:formFormatDate cambio el dateFormat
	    	//dateFormat: 'M-dd-yy',
	    	dateFormat: dateFormatCustom,
			buttonImage: "${themeResourcePath}"+"/images/icon_calendar.gif",	    
		    minDate: dateToday
		    //defaultDate: "+1w"	   	  
	    });	 	
	 
	 if ($( "#requestedDeliveryDate" ).val() == "") {	
		 //<formDate:formFormatDate cambio el dateFormat
		 //$("#requestedDeliveryDate").val($.datepicker.formatDate('M-dd-yy', dateToday));
		 $("#requestedDeliveryDate").val($.datepicker.formatDate(dateFormatCustom, dateToday));
	 }	 
   
	 ACC.requesteddeliverydate.requestedDeliveryDateSet();
	bindSearchFormElements();
}

///////////////////////////////////////////
//function initDocumentList()
//{	
		
//	var dateToday = new Date();	
//	dateToday.setDate(dateToday.getDate() + ${addedDays});
	     
//	 $( "#requestedDeliveryDate" ).datepicker({
//	    	dateFormat: 'mm-dd-yy',		
//			buttonImage: "${themeResourcePath}"+"/images/icon_calendar.gif",	    
//		    minDate: dateToday
		    //XdefaultDate: "+1w"	   	  
//	    });	 	
	 
//	 if ($( "#requestedDeliveryDate" ).val() == "") {				    
//		 $("#requestedDeliveryDate").val($.datepicker.formatDate('mm-dd-yy', dateToday));
//	 }	 
   
//	 ACC.requesteddeliverydate.requestedDeliveryDateSet();
//	bindSearchFormElements();
//}

//////////////////////////////////////////
</script>


