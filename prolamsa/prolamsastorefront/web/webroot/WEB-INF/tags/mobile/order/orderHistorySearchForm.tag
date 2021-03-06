<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/mobile/form"%>

<c:set var="orderHistoryBaseUrl" value="/my-account/orders/list" />
<spring:url var="orderHistorySearchUrl" value="${orderHistoryBaseUrl}" />

<div class="item_container_holder" style="display: block; ">
	<form:form id="searchForm" class="js-form" action="${orderHistorySearchUrl}" method="get">
		<div>
			<%--
			<table  style="background: url('${themeResourcePath}/images/bg-prolamsa_v2.jpg') repeat-x; width: 100%; float: left; height:auto; padding:0 10px;">
			--%>
			<table id="gradTableForm" style="width: 100%; float: left; height:auto; padding:0 10px;">
			
				<tr>
					<td class="td-form" colspan="2">
                    	<!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       	<spring:theme code="text.account.orderHistory.filter.customer" />
				       </td>	
				  </tr>
				   <tr> 
				   	<td colspan="2" style="padding-right: 10px;">
				       <select id="customer" name="customer" style="border: solid 2px gray;">
						  <option value="all">All</option> 
							<c:forEach items="${listB2BUnits}" var="eachFormattedB2BUnit">
							    <c:choose>
									<c:when test="${eachFormattedB2BUnit.uid eq orderHistorySearchForm.customer}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
						  </c:forEach>
						</select>
				   </td>	
			    </tr>		
			    <tr>
				    <td class="td-form" colspan="2">
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="text.account.orderHistory.filter.shippingTo" />
				    </td>
				 </tr> 
				 <tr>  
				     <td colspan="2" style="padding-right: 10px;">
				     <select id="deliveryAddress" name="deliveryAddress" style="border: solid 2px gray;">
						  <option value="all">All</option> 
							<c:forEach items="${deliveryAddresses}" var="eachDeliveryAddress"> 
							    <c:choose>
									<c:when test="${eachDeliveryAddress.code eq orderHistorySearchForm.deliveryAddress}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachDeliveryAddress.code}" ${selected} >${eachDeliveryAddress.formattedAddress}</option>
						  </c:forEach>
						</select>										     						
					    				   
				    </td>
				    </tr> 
				 <tr>					      
				    <td class="td-form" colspan="2">
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="text.account.orderHistory.filter.orderStatus" />
				    </td>
				    </tr> 
				 <tr>				      
				    <td colspan="2" style="padding-right: 10px;">
				    	<select id="status" name="status" style="border: solid 2px gray;">
						  <option value="all">All</option> 
							<c:forEach items="${orderStatuses}" var="eachStatus">
							    <c:choose>
									<c:when test="${orderHistorySearchForm.status eq eachStatus.code}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachStatus.code}" ${selected} >${eachStatus.code}</option>
						  </c:forEach>
						</select>	
				    </td>
				    </tr> 
				 			    
			  
			    <tr>
				   <td class="td-form" colspan="2">
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="text.account.orderHistory.filter.orderNumber" />
				    </td>
				    </tr> 
				 <tr>
				    <td colspan="2">
				       <formUtil:formInputTextInlineMobile  
						path="" 
						id="orderNumber"
						mandatory="false" 
						type="text" 
						name="orderNumber"					
						tabindex="103" 
						value="${orderHistorySearchForm.orderNumber}"
						divCSS="search-form-options"											
						asterisk="" 
						inputCSS=""  />
				    </td>
				    </tr> 
				 <tr>
				    <td class="td-form" colspan="2">
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				      <spring:theme code="text.account.orderHistory.filter.purchaseOrderNumber" />		
				    </td>
				    </tr> 
				 <tr>				    
				     <td colspan="2">
				       <formUtil:formInputTextInlineMobile
						path="" 
						id="poNumber"
						mandatory="false" 
						type="text" 
						name="poNumber"						
						tabindex="104" 
						value="${orderHistorySearchForm.poNumber}"
						divCSS="search-form-options"						
						asterisk="" 
						inputCSS="" />
				    </td>				    
			    </tr>  
			    <tr>
			       <td class="td-form" style="width: 20%; vertical-align: middle;">
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme  code="text.account.orderHistory.filter.from" />
			       </td>
			     
			       <td colspan="2">
			           <%-- <spring:theme var="placeHolder" code="invoice.list.dateFrom.inline" /> --%>
			           <formUtil:formInputTextInlineCalMobile
						path="" 
						id="initialDate"
						mandatory="false" 
						type="text" 
						name="initialDate"				 	
						tabindex="105" 
						value="${orderHistorySearchForm.initialDate}"
						divCSS="search-form-options"
						placeholderSpringThemeText="${placeHolder}" 						
						asterisk="" 
						inputCSS="" />
						<img src="${themeResourcePath}/images/icon_calendar.gif" alt="" style="width: 25px; vertical-align: middle;"> 
			       </td>
			       </tr> 
				 <tr>
			      <td class="td-form" style="vertical-align: middle;">
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme code="text.account.orderHistory.filter.to" />
			       </td>
			      
			       <td>
			          <%-- <spring:theme var="placeHolder" code="invoice.list.dateTo.inline" /> --%>
			          <formUtil:formInputTextInlineCalMobile
						path="" 
						id="finalDate"
						mandatory="false" 
						type="text" 
						name="finalDate"						
						tabindex="106" 
						value="${orderHistorySearchForm.finalDate}"
						divCSS="search-form-options"	
						placeholderSpringThemeText="${placeHolder}" 					
						asterisk="" 
						inputCSS="" />
						<img src="${themeResourcePath}/images/icon_calendar.gif" alt="" style="width: 25px; vertical-align: middle;">
			       </td>
			    </tr>
			    <tr>
			    </tr>
			    <tr>
			    </tr>
			    <tr>
			    </tr>
			    <tr>
			       <td colspan="2">
			          <formUtil:formButtonMobile
					id="orderSearchSubmit"
					name="orderSearchSubmit"
					type="submit" 
					css="button button yellowXXX positive button-float-right2"
					tabindex="106"
					springThemeText="invoice.button.search" />
			       </td>
			    </tr>
			     
		    </table>
	
			<%-- <div class="search-form-options">
				<formUtil:formButton
					id="orderSearchSubmit"
					name="orderSearchSubmit"
					type="submit" 
					css="positive button-float-right"
					tabindex="106"
					springThemeText="invoice.button.search" />
			</div>	 --%>														
	
		</div>
	 <input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
	
	<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
   
</div>
<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: #09181f; width: 100%; "></div>

<%-- <c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<div class="prod_refine-wrapper top-border-separator-line" style="width: 100%; float: left;">
		<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"							
			searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
			msgKey="text.account.orderHistory.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
	</div>
</c:if> --%>

<script type="text/javascript">

var advanceAddressSearchLink = {};
advanceAddressSearchLink.url = "<c:url value="/client/searchAddressHistory" />";	
advanceAddressSearchLink.linkId = "advancedAddressSearchLink";	
advanceAddressSearchLink.customerHistory="";
advanceAddressSearchLink.popUrlFunc = function()
{
	return advanceAddressSearchLink.url + "?customerHistory=" +  $('#customer').val();
};

onDOMLoaded(initOrderList);

function initOrderList()
{	

	jQuery("#customer").on("change",updateSelectAddress);	
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
	jQuery("#orderSearchSubmit").click(submitForm);		
	jQuery("#initialDate").datepicker({dateFormat: 'yy-mm-dd'});
	jQuery("#finalDate").datepicker({dateFormat: 'yy-mm-dd'});
	
	updateSelectAddress();

	
}

function submitFormFromDropDown()
{
	var val = jQuery(this).val();		
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

function submitForm()
{	
	var val = jQuery(".sortDropDownFieldClass").val();		
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

function sortJsonName(a,b){
    return a.name > b.name ? 1 : -1;
  };

function updateSelectAddress(){
	
	var info = jQuery("#searchForm").serialize();
 	
	var customerHistory = ($("#customer")) ? $("#customer").val() : "";
		
	jQuery.ajax
	({
		url:  "<c:url value='/client/searchAddress/updateAddressSelect.json?customerHistory="+ customerHistory +"'/>",
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
				var current = $("#deliveryAddress").val();
				
				$("#deliveryAddress").empty();
				$("#deliveryAddress").append("<option value='all'>All</option>");											
										
				data = $(data.units).sort(sortJsonName);				
				
				//$.each(data.units, function(index) {					
				$.each(data, function(index) {
					//$("#deliveryAddress").append("<option value='" +data.units[index].code +"'>"+ data.units[index].name +"</option>");
					
					var selected="";
					if (data[index].code==current) {
						var selected = "selected";
					} 
					
					$("#deliveryAddress").append("<option value='" +data[index].code +"' "+ selected +"   >"+ data[index].name +"</option>");
				});	
				
				
           	}
           	else
           	{
           		ACC.modals.messageModal("<spring:theme code="error"/>", data.message);            		
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
	});
}

</script>