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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:set var="incidentListBaseUrl" value="/incident/incidentList" />
<spring:url var="incidentListSearchBaseUrl" value="${incidentListBaseUrl}" />
<spring:url value="/incident/addIncident" var="addIncidentURL" />

<div class="item_container_holder" style="display: block;">
	<a href="${addIncidentURL }" class="button yellow positive button-float-right" id="addNewIncidentLink"><spring:theme code="incident.add.title" /></a>
	<br>
	<br>
	<form:form id="searchForm" class="js-form" action="${incidentListSearchBaseUrl}"  method="get">
		<div>
		    <table>
		  		<tr>
				    <td>
				       <spring:theme code="incident.list.number" />
				    </td>
				    <td>
				       <formUtil:formInputTextInline  
						path="number" 
						id="number"
						mandatory="false" 
						type="text" 
						name="number"					
						tabindex="103" 
						value="${searchForm.number}"
						divCSS="search-form-options"											
						asterisk="" 
						inputCSS=""  />
				    </td>
				   <td>
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="incident.list.customer" />
				    </td>
				    <td>
				       <select id="customer" name="customer">
				       	<sec:authorize ifAllGranted="ROLE_QUALITY">
				       		<option value="all"><spring:theme code="incident.list.allCustomer" /></option>
				       		<c:set var="selected" value="" />
				       		<c:if test="${searchForm.customer eq 'all-favorites' }">
				       			<c:set var="selected" value="selected" />
				       		</c:if>
				       		 <option value="all-favorites" ${selected }><spring:theme code="incident.list.allFavoriteCustomers" /></option> 
				       	</sec:authorize>
				       	<sec:authorize ifNotGranted="ROLE_QUALITY">
				       		 <option value="all-favorites"><spring:theme code="incident.list.allCustomer" /></option> 
				       	</sec:authorize>		
							<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
							    <c:choose>
									<c:when test="${eachFormattedB2BUnit.uid eq searchForm.customer}">
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
				    <td>
				       <spring:theme code="incident.list.status" />
				    </td>				      
				    <td>
				    	<select id="state" name="state">
						  <option value="all"><spring:theme code="incident.list.allStates" /></option> 
							<c:forEach items="${incidentStates}" var="eachState">
							    <c:choose>
									<c:when test="${searchForm.state eq eachState.code}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachState.code}" ${selected} >${eachState.name}</option>
						  </c:forEach>
						</select>
				   
				    </td>						      
				    <td>
				       <spring:theme code="incident.list.type" />
				    </td>				      
				    <td>
				    	<select id="type" name="type">
						  <option value="all"><spring:theme code="incident.list.allTypes" /></option> 
							<c:forEach items="${incidentTypes}" var="eachType">
							    <c:choose>
									<c:when test="${searchForm.type eq eachType.code}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachType.code}" ${selected} >${eachType.name}</option>
						  </c:forEach>
						</select>
				   
				    </td>			    
			    </tr> 
			    <tr>
			    </tr>  
			    <tr>
			       <td>
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme  code="text.account.orderHistory.filter.from" />
			       </td>
			       
			       <td>
			           <spring:theme var="placeHolder" code="invoice.list.dateFrom.inline.${baseStore.uid}" />
			           <formUtil:formInputTextInline 
						path="initialDate" 
						id="initialDate"
						mandatory="false" 
						type="text" 
						name="initialDate"				 	
						tabindex="105" 
						value="${searchForm.initialDate}"
						divCSS="search-form-options"
						placeholderSpringThemeText="${placeHolder}" 						
						asterisk="" 
						inputCSS=""  />
			       </td>
			       
			       <td>
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme code="text.account.orderHistory.filter.to" />
			       </td>
			       
			       <td>
			          <spring:theme var="placeHolder" code="invoice.list.dateTo.inline.${baseStore.uid}" />
			          <formUtil:formInputTextInline 
						path="finalDate" 
						id="finalDate"
						mandatory="false" 
						type="text" 
						name="finalDate"						
						tabindex="106" 
						value="${searchForm.finalDate}"
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
					onClick="sendGA()"
					/>
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
			searchUrl="${incidentListBaseUrl}?customer=${searchForm.customer}&initialDate=${searchForm.initialDate}&state=${searchForm.state}&&finalDate=${searchForm.finalDate}&type=${searchForm.type}"	
			msgKey="text.account.orderHistory.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="false" />
	</div>
</c:if>
<br>

<script>
	function sendGA ()
	{
		ga('send','event','ViewIncident','viewIncident');
	}
</script>


<script type="text/javascript">


onDOMLoaded(initOrderList);

function initOrderList()
{	

// 	jQuery("#customer").on("change",updateSelectAddress);	
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
	jQuery("#orderSearchSubmit").click(submitForm);		
	jQuery("#initialDate").datepicker({dateFormat: 'yy-mm-dd',maxDate: '+0d'});
	<c:choose>
		<c:when test="${searchForm.initialDate ne ''}">
		jQuery("#finalDate").datepicker({dateFormat: 'yy-mm-dd',maxDate: '+0d',minDate:'${searchForm.initialDate}'});
		</c:when>
		<c:otherwise>
		jQuery("#finalDate").datepicker({dateFormat: 'yy-mm-dd',maxDate: '+0d'});
		</c:otherwise>
	</c:choose>
	
	
	
// 	updateSelectAddress();
	jQuery("#initialDate").on("change",validateDate);
	jQuery("#finalDate").on("change",validateDate);
	
}

function validateDate()
{
	   
	   try{
	    $.datepicker.parseDate("yy-mm-dd", jQuery(this).val(), null);
	   }
	   catch(e){
		   ACC.modals.messageModal("<spring:theme code="error"/>", "<spring:theme code="incident.list.badDate"/>");    
		   jQuery(this).val("")
		   return;
	   }
	   
	   if(jQuery(this).attr('id') == 'initialDate')
		{
// 		   jQuery("#finalDate").datepicker('destroy');
		   
		   jQuery("#finalDate").datepicker('option','minDate',jQuery(this).val());
		   jQuery("#finalDate").datepicker('option','maxDate',new Date());
		}else
		{
			jQuery("#initialDate").datepicker('option','maxDate',jQuery(this).val());
		}
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
		$("#deliveryAddress").append("<option value='all'>All</option>");
		return;
	}
		
		
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