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

<template:master pageTitle="${pageTitle}">
	<div class="" style="width: 100%; float: left; height:auto; overflow: hidden;">
		<header:header />
		<header:menu />
		
		<a id="top"></a>
		
		<div class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 10px; text-align: justify;">
                	<spring:theme code="mobile.orderHistory.detail.title" /> <br>
                </h1>
				<p style="font-size: 10.5px; padding: 0 10px; text-align: justify;"><spring:theme code="mobile.orderHistory.detail.subtitle" /></p>
			</div>
		</div>

		<div class="item_container_holder" style="display: block; background: none repeat scroll 0 0 #BFBEBE; width: 100%; float: left; height:auto; ">
			<table  style="background: none repeat scroll 0 0 #BFBEBE; width: 100%; float: left; height:auto; ">
				<tr>
					<td>
						<spring:theme code="mobile.orderHistory.detail.order" />
					</td>
					<td>
						${orderData.code}
					</td>
				</tr>
				<tr>
					<td>
						<spring:theme code="mobile.orderHistory.detail.placed" />
					</td>
					<td>
						<fmt:formatDate pattern="MM/dd/y" value="${orderData.created}" />
					</td>
				</tr>
				<c:if test="${not empty orderData.statusDisplay}">
					<tr>
						<td>
							<spring:theme code="mobile.orderHistory.detail.status" />
						</td>
						<td>
							<spring:theme code="text.account.order.status.display.${orderData.statusDisplay}"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<spring:theme code="mobile.orderHistory.detail.transportation" />
					</td>
					<td>
						${orderData.transportationMode.name} (${orderData.transportationMode.incotermCode}) (${orderData.transportationMode.maxCapacity} Tons)
					</td>
				</tr>
			</table>
			
			<br><br><br><br><br><br><br>
			<p style="padding-left: 8px;"><spring:theme code="mobile.orderHistory.detail.purchaseorder" /></p>
			<table style=" border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				
				<tr >
					<td>
						<spring:theme code="mobile.orderHistory.detail.po" />
					</td>
					<td>
						${orderData.purchaseOrderNumber}
					</td>
				</tr>
				<c:if test="${not empty orderData.attachmentName }">
					<tr>
						<td>
							<spring:theme code="mobile.orderHistory.detail.po.att" />
						</td>
						<td>
							${orderData.attachmentName }
						</td>
					</tr>
				</c:if>
			</table>		
		
			<br><br>
			<p style="padding-left: 8px;"><spring:theme code="mobile.orderHistory.detail.billing" /></p>
			<table style="border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				<c:if test="${not empty orderData.billingAddress.formattedAddress}">
					<tr>
						<td>
							${fn:escapeXml(orderData.billingAddress.formattedAddress)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.billingAddress.name}">
					<tr>
						<td>
							${fn:escapeXml(orderData.billingAddress.name)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.billingAddress.line1}">
					<tr>
						<td>
							${orderData.billingAddress.line1}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.billingAddress.line2}">
					<tr>
						<td>
							${orderData.billingAddress.line2}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.billingAddress.town }">
					<tr>
						<td>
							${fn:escapeXml(orderData.billingAddress.town)}, ${fn:escapeXml(orderData.billingAddress.region.name)}, ${fn:escapeXml(orderData.billingAddress.postalCode)}
						</td>
					</tr>
				</c:if>
			</table>
			
			<br><br>
			<p style="padding-left: 8px; padding-top: 85px;"><spring:theme code="mobile.orderHistory.detail.delivery" /></p>
			<table style="border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				<c:if test="${not empty orderData.deliveryAddress.formattedAddress}">
					<tr>
						<td>
							${fn:escapeXml(orderData.deliveryAddress.formattedAddress)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.name}">
					<tr>
						<td>
							${fn:escapeXml(orderData.deliveryAddress.name)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.line1}">
					<tr>
						<td>
							${orderData.deliveryAddress.line1}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.line2}">
					<tr>
						<td>
							${orderData.deliveryAddress.line2}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.town }">
					<tr>
						<td>
							${fn:escapeXml(orderData.deliveryAddress.town)}, ${fn:escapeXml(orderData.deliveryAddress.region.name)}, ${fn:escapeXml(orderData.deliveryAddress.postalCode)}
						</td>
					</tr>
				</c:if>
			</table>		
		
			<br><br>
			<p style="padding-left: 8px; padding-top: 85px;"><spring:theme code="mobile.orderHistory.detail.order.summary" /></p>
			<table style="border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				<c:if test="${orderData.hotRolledWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.hot_rolled"></spring:theme>
						</td>
						<td>
							${orderData.formattedHotRolledWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.coldRolledWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.cold_rolled"></spring:theme>
						</td>
						<td>
							${orderData.formattedColdRolledWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.galvanizedWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.galvanized"></spring:theme>
						</td>
						<td>
							${orderData.formattedGalvanizedWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.aluminizedWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.aluminized"></spring:theme>
						</td>
						<td>
							${orderData.formattedAluminizedWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<spring:theme code="checkout.summary.totals"></spring:theme>
					</td>
					<td>
						${orderData.formattedTotalWeight} &nbsp;${orderData.sapWeightUnit}
					</td>
				</tr>
			</table>		
		
			<br><br>
			<p style="padding-left: 8px; padding-top: 27px;"><spring:theme code="mobile.orderHistory.detail.order.totals" /></p>
			<table style="border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				<c:if test="${orderData.hotRolledPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.hot_rolled"></spring:theme>
						</td>
						<td>
							${orderData.formattedHotRolledPrice} &nbsp;${orderData.sapCurrency}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.coldRolledPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.cold_rolled"></spring:theme>
						</td>
						<td>
							${orderData.formattedColdRolledPrice} &nbsp;${orderData.sapCurrency}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.galvanizedPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.galvanized"></spring:theme>
						</td>
						<td>
							${orderData.formattedGalvanizedPrice} &nbsp;${orderData.sapCurrency}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.aluminizedPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.aluminized"></spring:theme>
						</td>
						<td>
							${orderData.formattedAluminizedPrice} &nbsp;${orderData.sapCurrency}
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<spring:theme code="checkout.summary.totals"></spring:theme>
					</td>
					<td>
						${orderData.formattedSapTotalPrice} &nbsp;${orderData.sapCurrency}
					</td>
				</tr>
			</table>
			
			<br><br>
			<p style="padding-left: 8px; padding-top: 27px;"><spring:theme code="mobile.orderHistory.detail.items" /></p>
			<table style="border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				<c:forEach items="${orderData.entries}" var="orderEntry" varStatus="status">
					<table style="border:solid 1px black; background: url('${themeResourcePath}/images/bg-prolamsa.jpg') repeat-x; width: 100%; float: left; height:auto;">
				
						<c:url value="${orderEntry.product.url}" var="productUrl"/>
						<c:set var="productInventoryEntry" value="${orderEntry.product.inventoryEntry}" />
					
						<tr class="prolamsa_product_row ${trClass}" entryNumber="${orderEntry.sapEntryNumber}" hasChanged="false">
							<td class="${tdClass}"><a href="${productUrl}">${orderEntry.product.visibleCode}</a></td>
							<c:choose>
								<c:when test="${not empty orderEntry.productDescription }">
									<td class="${tdClass}">${orderEntry.productDescription}</td>
								</c:when>
								<c:otherwise>
									<td class="${tdClass}">${orderEntry.product.name}</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td></td>
							<td><fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.product.piecesPerBundle}"/> bun / 
							<fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.convertedQuantity}"/> pcs each </td>
						</tr>
						<tr>
							<td></td>
							<td>
								${orderEntry.formattedNetPriceWOTaxes}
							</td>
						</tr>
					</table>
				</c:forEach>		
			</table>
		</div>
			
		</div> 

</template:master>