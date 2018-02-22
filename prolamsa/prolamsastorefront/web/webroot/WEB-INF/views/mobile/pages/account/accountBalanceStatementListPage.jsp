<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="balanceStatement" tagdir="/WEB-INF/tags/mobile/balanceStatement" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/mobile/header"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav" %>

<template:master pageTitle="${pageTitle}">
	<div class="" style="width: 100%; float: left; height:auto; overflow: hidden;">
		<header:header />
		<header:menu />
		<balanceStatement:balanceStatementList />
	</div>
	<br>
</template:master>




		<%-- 
		<a id="top"></a>
		
			
		<div class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 10px; text-align: justify;">
                	<spring:theme code="text.account.orderHistory.title" /> <br>
                </h1>
				<p style="font-size: 10.5px; padding: 0 10px; text-align: justify;"><spring:theme code="mobile.orderHistory.subtitle" /></p>
			</div>
		</div>



<div class="item_container_holder" style="display: block; ">
	<form:form id="searchForm" class="js-form" action="${orderHistorySearchUrl}" method="get">
		<div>
			<table  style="background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto; ">
				<tr>
					<td>
                    	<!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       	<spring:theme code="text.account.orderHistory.filter.customer" />
				       </td>	
				  </tr>
				   <tr> 
				   	<td>
				       <select id="customer" name="customer">
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
			    
			    </table>
	
															
	
		</div>
	 <input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
</div>	
			<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				<div class="prod_refine-wrapper top-border-separator-line" style="width: 100%; float: left; height:auto;">
					<nav:paginationReportMobile top="false" supportShowPaged="${isShowPageAllowed}"
						supportShowAll="${isShowAllAllowed}"
						searchPageData="${searchPageData}"							
						searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
						msgKey="text.account.orderHistory.page"
						numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
				</div>
			</c:if>
			
			<div class="item_container" style="width: 100%; float: left; height:auto; text-align: justify; background: none repeat scroll 0 0 #939292;">
				<c:forEach items="${searchPageData.results}" var="order">
					<c:url value="/my-account/order/${order.code}" var="myAccountOrderDetailsUrl"/>
					<table style="background: none repeat scroll 0 0 #BFBEBE;" onclick="orderDetailView(${order.code});">
						<tr>
							<td>
								${order.code}
							</td>
						</tr>
						<tr>
							<td>
								${order.purchaseOrderNumber}
							</td>
							<td>
								<spring:theme code="text.account.order.status.display.${order.statusDisplay}"/>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:formatDate pattern="MM/dd/y" value="${order.created}" />
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
			
					
	
	<br>	


<script type="text/javascript">
function orderDetailView(code)
{
	while (code.toString().length<10)
	{
		code = '0'+code;
	}

	document.location.href=window.location.origin+"/store/my-account/order/"+code;
}

</script>

 --%>