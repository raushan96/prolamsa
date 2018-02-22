<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/mobile/order" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/mobile/common" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/mobile/header"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="foot" tagdir="/WEB-INF/tags/mobile/footer"%>


<template:master pageTitle="${pageTitle}">
	<div class="" style="width: 100%; float: left; height:auto; ">
		<header:header />
		<header:menu />
		
		<a id="top"></a>
		 
			
		<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 800; font-size: 12.5px; text-transform: none; padding: 0 20px; text-align: justify;">
                	<spring:theme code="text.account.orderHistory.title" /> <br>
                </h1>
				<p style="font-size: 10.5px; padding: 0 20px; text-align: justify;"><spring:theme code="mobile.orderHistory.subtitle" /></p>
				<br>
			</div>
		</div>

			<order:orderHistorySearchForm/>
			
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
			<div id="resultsMobile" class="item_container" style="width: 100%; float: left; height:auto; text-align: justify; padding-bottom: 10px;">
			
			<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				
					<nav:paginationReportMobile top="false" supportShowPaged="${isShowPageAllowed}"
						supportShowAll="${isShowAllAllowed}"
						searchPageData="${searchPageData}"							
						searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
						msgKey="text.account.orderHistory.page"
						numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
				
			</c:if>
			
				<c:forEach items="${searchPageData.results}" var="order">
					<c:url value="/my-account/order/${order.code}" var="myAccountOrderDetailsUrl"/>
					<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;"  id="table-${order.code}" onclick="orderDetailView(this.id);" >
						<tr>
							<td style="padding: 0; font-size: 14px; ">
								${order.code}
							</td>
						</tr>
						<tr>
							<td style="font-weight: lighter; padding: 0;">
								${order.purchaseOrderNumber}
							</td>
							<td style="text-transform: uppercase; font-size: 14px; padding: 0;">
								<c:if test="${fn:contains(order.statusDisplay,'cancelled') }">
									<img src="${themeResourcePath}/images/icon_x_gray.gif" alt="" style="width: 33px; vertical-align: text-bottom;"> 
								</c:if>
								<c:if test="${fn:contains(order.statusDisplay,'inprocess') }">    
									<img src="${themeResourcePath}/images/icon_arrow_gray.gif" alt="" style="width: 33px; vertical-align: text-bottom;">
									<%-- <img src="${themeResourcePath}/images/icon_truck_gray.gif" alt="" style="width: 33px; vertical-align: text-bottom;">  --%>
								</c:if>
								<%--
								<c:if test="${fn:contains(order.statusDisplay,'shipped') }">    
									<img src="${themeResourcePath}/images/icon_truck_gray.gif" alt="" style="width: 33px; vertical-align: text-bottom;">
								</c:if>
								--%>
								<spring:theme code="text.account.order.status.display.${order.statusDisplay}"/>
							</td>
							<td style="padding: 0;">
							<img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt=""> 
							</td>
						</tr>
						<tr>
							<td style="font-weight: lighter; padding: 0;">
								<fmt:formatDate pattern="MM/dd/y" value="${order.created}" />
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
			
			
				
					<nav:paginationReportMobile top="false" supportShowPaged="${isShowPageAllowed}"
						supportShowAll="${isShowAllAllowed}"
						searchPageData="${searchPageData}"							
						searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
						msgKey="text.account.orderHistory.page"
						numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
				
			</c:if>
			<c:choose>
				<c:when test="${searchPageData.pagination.totalNumberOfResults gt 0}">
					<foot:footerWTop searchPageData="${searchPageData}"	/>
				</c:when>
				<c:otherwise>
					<foot:footer/>
				</c:otherwise>
			</c:choose>
			
			
		</div>		
					
	
	<br>	
</template:master>

<script type="text/javascript">
function orderDetailView(code)
{ //NEORIS_CHANGE#LCC, 26-Jan-15-->Fix the product search by Code
 	code = code.substring(code.indexOf("-")+1);
  
	while (code.toString().length<10)
	{
		code = '0'+code;
	}

	document.location.href=window.location.origin+"/store/my-account/order/"+code;
}

</script>