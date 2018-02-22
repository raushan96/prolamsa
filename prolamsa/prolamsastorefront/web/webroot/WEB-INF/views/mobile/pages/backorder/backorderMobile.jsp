<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/mobile/header"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/mobile/footer"%>

<template:master pageTitle="${pageTitle}">
	<div class="" style="width: 100%; float: left; height:auto; overflow: hidden;">
		<header:header />
		<header:menu />
 		<%-- item page ${productCode}  --%>
		<a id="top"></a>
		
		<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 20px; text-align: justify;">
                	<spring:theme code="mobile.order.detail.item" /><br>
                	<%-- <spring:theme code="mobile.order.detail.dos" />${orderData.code}<br> --%>
                </h1>
			</div>
		</div>
		
		<div id="gradTableData" style="display: block; width: 100%; float: left; height:auto;">
				
				<c:forEach items="${searchPageData.results}" var="backorderDetail" varStatus="status">
				 <c:if test="${backorderDetail.partNumber eq partNum}"> 
			<table style="width: 100%; float: left; height:auto; padding: 0 18px;">
				<%-- <tr>
					<td>
						<spring:theme code="mobile.order.detail.item" />
					</td>
					
				</tr> --%>
				<tr>
					<td style="padding: 0; width: 35%;">
						Customer:
					</td>
					<td style="font-weight: lighter;">
						${customer}
					</td>
				</tr>
				<tr>
					<td style="padding: 0;">
						<spring:theme code="backorderDetail.list.order" />:
					</td>
					<td style="font-weight: lighter;">
						${orderCode}
					</td>
				</tr>
				<tr>
					<td style="padding: 0;">
						<spring:theme code="backorderDetail.list.orderDate" />:
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatDate pattern="MM/dd/yyyy" value="${backorderDetail.orderDate}" />
					</td>
				</tr>
				<tr>
					<td style="padding: 0;">
						<spring:theme code="backorderDetail.list.customerPO" />:
					</td>
					<td style="font-weight: lighter;">
						${backorderDetail.customerPO}
					</td>
				</tr>
				
				
				
				
				
				
			
				
			</table>
			
			</c:if>
			</c:forEach>	
			
			<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.purchaseorder" /></p>
			
		<%-- --
		
		<div class="item_container_holder" style="display: block; background: none repeat scroll 0 0 #BFBEBE; width: 100%; float: left; height:auto; ">
			<table style="background: url(../images/pagehead-homepage-bg.png) repeat-x; width: 100%; float: left; height:auto; ">
				<tr>
					<td style="width: 30%">
						<spring:theme code="mobile.orderHistory.detail.order" />
					</td>
					<td style="font-weight:lighter; width: 70%;">
						${orderData.code}
					</td>
				</tr>
				<tr>
					<td style="width: 30%">
						<spring:theme code="mobile.orderHistory.detail.placed" />
					</td>
					<td style="font-weight:lighter; width: 70%;">
						<fmt:formatDate pattern="MM/dd/y" value="${orderData.created}" />
					</td>
				</tr>
				<c:if test="${not empty orderData.statusDisplay}">
					<tr>
						<td style="width: 30%">
							<spring:theme code="mobile.orderHistory.detail.status" />
						</td>
						<td style="font-weight:lighter; width: 70%;">
							<spring:theme code="text.account.order.status.display.${orderData.statusDisplay}"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="width: 30%">
						<spring:theme code="mobile.orderHistory.detail.transportation" />
					</td>
					<td style="font-weight:lighter; width: 70%;">
						${orderData.transportationMode.name} (${orderData.transportationMode.incotermCode}) (${orderData.transportationMode.maxCapacity} Tons)
					</td>
				</tr>
			</table>
			
			<br><br><br><br><br>

			<p style="padding:10px 0 0 10px; margin: 0; font-size: 16px; text-transform: uppercase; font-weight: 600;"><spring:theme code="mobile.order.detail.subtitle" /></p>
 --%>			
 	<c:forEach items="${searchPageData.results}" var="backorderDetail" varStatus="status">
				 <c:if test="${backorderDetail.partNumber eq partNum}"> 
					<table style="border:solid 3px black; width: 89%; float: left; height:auto; margin:0 20px 10px 20px; background: #fff;">
					 <tr>
					 	<td style="width: 35%;">
					 		<spring:theme code="backorderDetail.list.part" />
					 	</td>
					 	<td style="font-weight: lighter;">
						${backorderDetail.partNumber}
						</td>
					 </tr>
					 <tr>
					 	<td>
					 		<spring:theme code="backorder.list.description" />:
					 	</td>
					 	<td style="font-weight: lighter;">
						${backorderDetail.description}
						</td>
					 </tr>
					 <tr>
					 	<td>
					 		<spring:theme code="backorderDetail.list.new.deliveryDate" />:
					 	</td>
					 	<td style="font-weight: lighter;">
						<fmt:formatDate pattern="MM/dd/yyyy" value="${backorderDetail.deliveryDate}" />
						</td>
					 </tr>
					  <tr>
					 	<td>
					 		Delivery:
					 	</td>
					 	<td style="font-weight: lighter;">
						${backorderDetail.address}
						</td>
					 </tr>
					 <%-- <tr>
					 	<td>
					 		Pieces:
					 	</td>
					 	<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.pieces}" maxFractionDigits="0" />
						</td>
					 </tr> --%>
					 <tr>
					 	<td>
					 		<spring:theme code="backorder.list.pendingKilos" />:
					 	</td>
					 	<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.pendingKilos}" maxFractionDigits="3" minFractionDigits="3"/>
							</td>
					 </tr>
					 <tr>
					 	<td>
					 		Ready:
					 	</td>
					 	<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.readyKilos}" maxFractionDigits="3" minFractionDigits="3"/>
							</td>
					 </tr>
					 <tr>
					 	<td>
					 		<spring:theme code="backorder.list.kilosLoading" />:
					 	</td>
					 	<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.loadingKilos}" maxFractionDigits="3" minFractionDigits="3"/>
							</td>
					 </tr>
					<%--  <tr>
					 	<td>
					 		Balance:
					 	</td>
					 	<td style="font-weight: lighter;">
						${backorderDetail.address}
						</td>
					 </tr> --%>
					 
					
						<%-- <tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.part" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								${orderEntry.product.visibleCode}
							</td>
						</tr>						
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.description" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								<c:choose>
									<c:when test="${not empty orderEntry.productDescription }">
										${orderEntry.productDescription}
									</c:when>
									<c:otherwise>
										${orderEntry.product.name}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.pcs.bundle" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								<fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.product.piecesPerBundle}"/>
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.quantity" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								<fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.convertedQuantity}"/>
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.weight" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								<c:choose>
									<c:when test="${fn:contains(orderData.unitWhenPlaced.code,'kg') || fn:contains(orderData.unitWhenPlaced.code,'mt')}">
										<fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${ orderEntry.quantity*orderEntry.product.bundleKgEquiv}"/> Kgs
									</c:when>
									<c:when test="${fn:contains(orderData.unitWhenPlaced.code,'lb') || fn:contains(orderData.unitWhenPlaced.code,'ft')}">
										<fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${orderEntry.quantity*orderEntry.product.bundleLbEquiv }"/> Lbs
									</c:when>
									<c:when test="${fn:contains(orderData.unitWhenPlaced.code,'ton') }">
										<fmt:formatNumber pattern="###,###,###,###,##0.000" value="${ (orderEntry.quantity*orderEntry.product.tnEquiv)}"/> Tons
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.location" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								<spring:theme code="${orderEntry.product.location}" />
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.price.per.feet" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								 ${orderEntry.formattedPricePerFeet}&nbsp;${currentCurrency.isocode}
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.net.price.wo.tax" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								${orderEntry.formattedNetPriceWOTaxes}&nbsp;${currentCurrency.isocode}
							</td>
						</tr>
						<tr>
							<td style="width: 49%">
								<spring:theme code="mobile.order.detail.ready.to.ship" />
							</td>
							<td style="font-weight:lighter; width: 70%;">
								${orderEntry.formattedReadyToShip}
							</td>
						</tr> --%>
					</table> 
				</c:if>
			</c:forEach>	 	
		</div>
		<footer:footerWTop searchPageData="${searchPageData}"	/>

	</div> 
</template:master>