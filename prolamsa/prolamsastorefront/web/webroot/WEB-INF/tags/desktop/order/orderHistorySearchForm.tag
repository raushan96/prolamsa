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
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>

<c:set var="orderHistoryBaseUrl" value="/my-account/orders/list" />
<spring:url var="orderHistorySearchUrl" value="${orderHistoryBaseUrl}" />

<div class="item_container_holder" style="display: block;">

	<form:form id="searchForm" class="js-form" action="${orderHistorySearchUrl}" method="get">
				<input type="hidden" id="site" name="site" value="${baseStore.uid}"  style="display: none;"  />
		<div>
		    <table>
			    <tr>
				    <td>
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="text.account.orderHistory.filter.customer" />
				    </td>
				    <td>
				       <span><selector:b2bUnitClientSelector2 b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${orderHistorySearchForm.customer}"/></span>
				    </td>
			    </tr>		
			    <tr>
				    <td>
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="text.account.orderHistory.filter.shippingTo" />
				    </td>
				     <td>
				     <select id="deliveryAddress" name="deliveryAddress">
						  <option value="all"><spring:theme code="selector.all"/></option> 
							<c:forEach items="${deliveryAddresses}" var="eachDeliveryAddress"> 
							    <c:choose>
									<c:when test="${eachDeliveryAddress.code eq orderHistorySearchForm.deliveryAddress}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachDeliveryAddress.code}" ${selected} >${eachDeliveryAddress.formattedAddress2}</option>
						  </c:forEach>
						</select>
				     <!--
				     	<select id="deliveryAddress" name="deliveryAddress">
						    <option value=""><spring:theme code="All" /></option> 
							<c:forEach items="${deliveryAddresses}" var="eachDeliveryAddress">
								<c:set var="selected" value="" />
	
								<c:if test="${eachDeliveryAddress.code eq orderHistorySearchForm.deliveryAddress}">
									<c:set var="selected" value="selected" />	
								</c:if>
									
							   	<option value="${eachDeliveryAddress.code}" ${selected} >${eachDeliveryAddress.formattedAddress}</option>
							</c:forEach>
						</select>
				     					       
				        <select id="deliveryAddress" name="deliveryAddress">	
						  <option value="all">All</option> 			
						  <c:forEach items="${deliveryAddresses}" var="eachDeliveryAddress">
							<c:set value="" var="selected"></c:set>
							<c:if test="${orderHistorySearchForm.deliveryAddress eq eachDeliveryAddress.code}" >
								<c:set value="selected" var="selected"></c:set>	
							</c:if>
						   <option value="${eachDeliveryAddress.code}" ${selected }>${eachDeliveryAddress.formattedAddress}</option>
					       </c:forEach>
					    </select>
					    -->											     						
					    <br/>
					    <a id="advancedAddressSearchLink" href="javascript:void(0);" class="left"><spring:theme code="advanced-address-search.title"/></a>					   
				    </td>					      
				    <td>
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="text.account.orderHistory.filter.orderStatus" />
				    </td>				      
				    <td>
				    	<select id="status" name="status">
						  <option value="all"><spring:theme code="selector.all"/></option>
							<c:forEach items="${orderStatuses}" var="eachStatus">
							    <c:choose>
									<c:when test="${orderHistorySearchForm.status eq eachStatus.code}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachStatus.code}" ${selected} ><spring:theme code="orderHistory.list.status.${eachStatus.code}"/></option>
						  </c:forEach>
						</select>
				    <%--
				        <select id="status" name="status">		
						    <option value="all">All</option> 		
							<c:forEach items="${orderStatuses}" var="eachStatus">
								<c:set value="" var="selected"></c:set>
								<c:if test="${orderHistorySearchForm.status eq eachStatus.code}" >
									<c:set value="selected" var="selected"></c:set>	
								</c:if>
								<option value="${eachStatus.code}" ${selected }>${eachStatus.code}</option>
							</c:forEach>
						</select>
					--%>	
				    </td>			    
			    </tr> 
			    <tr>
				    <c:choose>
						<c:when test="${baseStore.uid eq '5000'}">
							<td>
								<spring:theme code="text.account.orderHistory.filter.orderNumberA4C" />
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<spring:theme code="text.account.orderHistory.filter.orderNumber" />
							</td>
						</c:otherwise>
					</c:choose>
				    <td>
				       <formUtil:formInputTextInline  
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
				    
				    <td>
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				      <spring:theme code="text.account.orderHistory.filter.purchaseOrderNumber" />		
				    </td>				    
				     <td>
				       <formUtil:formInputTextInline 
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
			       <td>
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme  code="text.account.orderHistory.filter.from" />
			       </td>
			       
			       <td>
			           <spring:theme var="placeHolder" code="invoice.list.dateFrom.inline.${baseStore.uid}" />
			           <formUtil:formInputTextInline 
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
			       </td>
			       
			       <td>
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme code="text.account.orderHistory.filter.to" />
			       </td>
			       
			       <td>
			          <formUtil:formInputTextInline 
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
			       </td>
			    </tr>
			     
		    </table>
	
			<div class="search-form-options">
				<formUtil:formButton
					id="orderSearchSubmit"
					name="orderSearchSubmit"
					type="submit" 
					css="positive button-float-right"
					tabindex="106"
					springThemeText="invoice.button.search"
					onClickType="1"
					onClick="sendGA()" />
			</div>															
	
		</div>
	 <input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
	
	<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
   
</div>
<br>
<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<div class="prod_refine-wrapper top-border-separator-line">
		<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"							
			searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
			msgKey="text.account.orderHistory.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" neorisSortBy="true"/>
	</div>
</c:if>
<br>
<script>
	function sendGA ()
	{
		ga('send','event','ViewOrderHistory','viewOrderHistory');
	}
</script>
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
	//jQuery("#initialDate").datepicker({dateFormat: 'yy-mm-dd'});
	//jQuery("#finalDate").datepicker({dateFormat: 'yy-mm-dd'});
	
	if(document.getElementById("site").value == "1000" || document.getElementById("site").value == "5000")
	{
		jQuery("#initialDate").datepicker({dateFormat: 'dd-M-yy'});
		jQuery("#finalDate").datepicker({dateFormat: 'dd-M-yy'});
	}else
	{
		jQuery("#initialDate").datepicker({dateFormat: 'M-dd-yy'});
		jQuery("#finalDate").datepicker({dateFormat: 'M-dd-yy'});
	}
	
	$("#order_history tr:even").addClass("filaImpar");
    $("#order_history tr:odd").addClass("filaPar");
	
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
	
	if(customerHistory == "all")
	{
		$("#deliveryAddress").empty();
		$("#deliveryAddress").append("<option value='all'><spring:theme code="selector.all"/></option>");
		return;
	}
		
		
	jQuery.ajax
	({
		url:  "<c:url value='/client/searchAddress/updateAddressSelectComplete.json?customerHistory="+ customerHistory +"'/>",
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
				$("#deliveryAddress").append("<option value='all'><spring:theme code="selector.all"/></option>");											
										
				data = $(data.units).sort(sortJsonName);				
				
				//$.each(data.units, function(index) {					
				$.each(data, function(index) {
					//$("#deliveryAddress").append("<option value='" +data.units[index].code +"'>"+ data.units[index].name +"</option>");
					
					var selected="";
					var separador=", ";
					var separador2=" - ";
					if (data[index].code==current) {
						var selected = "selected";
					} 
					
					$("#deliveryAddress").append("<option value='" +data[index].code +"' "+ selected +"   >"+ data[index].code + separador2 + data[index].street + separador + data[index].city +"</option>");
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
