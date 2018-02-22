<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>

<%@ taglib prefix="foot" tagdir="/WEB-INF/tags/mobile/footer"%>
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
	<div style="width: 100%; float: left; height:auto;">
		<header:header />
		<header:menu />
		
		<a id="top"></a>
		
		<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 900; font-size: 12.5px; text-transform: none; padding: 0 20px; text-align: justify;">
                	<spring:theme code="mobile.orderHistory.detail.title" /> <br>
                </h1>
				<%-- <p style="font-size: 10.5px; padding: 0 10px; text-align: justify;"><spring:theme code="mobile.orderHistory.detail.subtitle" /></p>
			 --%>
			</div>
		</div>

		
		<div id="gradTableData" style="display: block; width: 100%; float: left; height:auto;">
				
			<table style="width: 100%; float: left; height:auto; padding: 0 18px;">
				<tr>
					<td style="text-transform: uppercase; padding: 0; width: 40%;">
						<spring:theme code="mobile.orderHistory.detail.order" />
					</td>
					<td style="font-weight: lighter;" >
						${orderData.code}											
					</td>
				</tr>
				<tr>
					<td style="padding: 0;">
						<spring:theme code="mobile.orderHistory.detail.placed" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatDate pattern="MM/dd/y" value="${orderData.created}" />
					</td>
				</tr>
				<c:if test="${not empty orderData.statusDisplay}">
					<tr>
						<td style="padding: 0;">
							<spring:theme code="mobile.orderHistory.detail.status" />
						</td>
						<td style="font-weight: lighter;">
							<spring:theme code="text.account.order.status.display.${orderData.statusDisplay}"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="padding: 0;">
						<spring:theme code="mobile.orderHistory.detail.transportation" />
					</td>
					<td style="font-weight: lighter;">
						${orderData.transportationMode.name} (${orderData.transportationMode.incotermCode}) (${orderData.transportationMode.maxCapacity} Tons)
					</td>
				</tr>
				
				
				
				
			</table>
			
			
			 <p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.purchaseorder" /></p>
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px; background: #fff;">
				
				<tr>
					<td style="width: 20%;">
						<spring:theme code="mobile.orderHistory.detail.po" />
					</td>
					<td style="font-weight: lighter;">
						${orderData.purchaseOrderNumber}
					</td>
				</tr>
				<c:if test="${not empty orderData.attachmentName }">
					<tr>
						<td>
							<spring:theme code="mobile.orderHistory.detail.po.att" />
						</td>
						<td style="font-weight: lighter;">
							${orderData.attachmentName }
						</td>
					</tr>
				</c:if>
			</table>	 
		<%-- </div>
		<div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; "> --%>
		
			<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.billing" /></p>
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px; background: #fff;">
				<c:if test="${not empty orderData.billingAddress.formattedAddress}">
					<tr>
						<td style="font-weight: lighter;">
							${fn:escapeXml(orderData.billingAddress.formattedAddress)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.billingAddress.name}">
					<tr>
						<td style="font-weight: lighter;">
							${fn:escapeXml(orderData.billingAddress.name)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.billingAddress.line1}">
					<tr>
						<td style="font-weight: lighter;">
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
						<td style="font-weight: lighter;">
							${fn:escapeXml(orderData.billingAddress.town)}, ${fn:escapeXml(orderData.billingAddress.region.name)}, ${fn:escapeXml(orderData.billingAddress.postalCode)}
						</td>
					</tr>
				</c:if>
			</table>
			
			<%-- </div>
		<div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; "> --%>
		
			<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.delivery" /></p>
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px 10px 20px; background: #fff;">
				<c:if test="${not empty orderData.deliveryAddress.formattedAddress}">
					<tr>
						<td style="font-weight: lighter;">
							${fn:escapeXml(orderData.deliveryAddress.formattedAddress)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.name}">
					<tr>
						<td style="font-weight: lighter;">
							${fn:escapeXml(orderData.deliveryAddress.name)}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.line1}">
					<tr>
						<td style="font-weight: lighter;">
							${orderData.deliveryAddress.line1}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.line2}">
					<tr>
						<td style="font-weight: lighter;">
							${orderData.deliveryAddress.line2}
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty orderData.deliveryAddress.town }">
					<tr>
						<td style="font-weight: lighter;">
							${fn:escapeXml(orderData.deliveryAddress.town)}, ${fn:escapeXml(orderData.deliveryAddress.region.name)}, ${fn:escapeXml(orderData.deliveryAddress.postalCode)}
						</td>
					</tr>
				</c:if>
			</table>
			</div>
			<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: black; width: 100%; "></div>
			
			<div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto;">
			<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.order.summary" /></p>
			
			<c:choose>
									<c:when test="${fn:contains(orderData.unitWhenPlaced.code,'kg') || fn:contains(orderData.unitWhenPlaced.code,'mt')}">
									<c:set var="UOM" value="Kgs" />
									</c:when>
									<c:when test="${fn:contains(orderData.unitWhenPlaced.code,'lb') || fn:contains(orderData.unitWhenPlaced.code,'ft')}">
									<c:set var="UOM" value="Lbs" /></c:when>
									<c:when test="${fn:contains(orderData.unitWhenPlaced.code,'ton') }">
									<c:set var="UOM" value="Tons" />
									</c:when>
								</c:choose>
			
			
			
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px; background: #fff;">
				<c:if test="${orderData.hotRolledWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.hot_rolled"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedHotRolledWeight} &nbsp;${UOM}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.coldRolledWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.cold_rolled"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedColdRolledWeight} &nbsp;${UOM}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.galvanizedWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.galvanized"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedGalvanizedWeight} &nbsp;${UOM}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.aluminizedWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.aluminized"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedAluminizedWeight} &nbsp;${UOM}
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<spring:theme code="checkout.summary.totals"></spring:theme>
					</td>
					<td style="font-weight: lighter;text-align: right;">
						${orderData.formattedTotalWeight} &nbsp;${UOM}
					</td>
				</tr>
			</table>
			
			<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.order.totals" /></p>
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin:0 20px 10px 20px; background: #fff;">
				<c:if test="${orderData.hotRolledPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.hot_rolled"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedHotRolledPrice} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.coldRolledPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.cold_rolled"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedColdRolledPrice} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.galvanizedPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.galvanized"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedGalvanizedPrice} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.aluminizedPrice > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.aluminized"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedAluminizedPrice} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.totalDeliveryCost > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.totalDeliveryCost"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedTotalDelvieryCost} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.totalAssurance > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.totalAssurance"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedTotalAssurance} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.totalTaxas > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.totalTaxas"></spring:theme>
						</td>
						<td style="font-weight: lighter;text-align: right;">
							${orderData.formattedTotalTaxas} &nbsp;${currentCurrency.isocode}
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<spring:theme code="checkout.summary.totals"></spring:theme>
					</td>
					<td style="font-weight: lighter; text-align: right;">
						${orderData.formattedSapTotalPrice} &nbsp;${currentCurrency.isocode}
					</td>
				</tr>
				</table>
			
			<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: black; width: 100%; "></div>
			
			</div>
			
			<%-- <div id="resultsMobile" class="item_container" style="width: 100%; float: left; height:auto; text-align: justify; background: none repeat scroll 0 0 #939292;">
				<c:forEach items="${searchPageData.results}" var="order">
					<c:url value="/my-account/order/${order.code}" var="myAccountOrderDetailsUrl"/>
					<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="orderDetailView(${order.code});">
			 --%>
			
			
			<div id="resultsMobile" class="item_container" style="width: 100%; float: left; height:auto; text-align: justify; padding-bottom: 10px;">
				<p style="text-transform: uppercase; font-weight: 900; font-size: 12px;"><spring:theme code="mobile.orderHistory.detail.items" /></p>
				
				
				
				<c:forEach items="${orderData.entries}" var="orderEntry" varStatus="status">
				
				    <c:set var="tableCode" value="ref-${orderData.code}" />
					 
					<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="detailView('${tableCode}',${orderEntry.product.visibleCode});">
					<!-- <table style=" border:solid 2px black; width: 95%; float: left; height:auto; margin: 0 10px 10px 10px;"> -->
			
						<c:url value="${orderEntry.product.url}" var="productUrl"/> 
						<c:url value="/my-account/orderMobile/${orderData.code}" var="productUrl2"/>
						
						<c:set var="accountBaseUrl" value="/my-account/orderMobile" />
						<spring:url var="productUrl2" value="${accountBaseUrl}" />
						
						<c:set var="productInventoryEntry" value="${orderEntry.product.inventoryEntry}" />
					
						<tr class="prolamsa_product_row ${trClass}" entryNumber="${orderEntry.sapEntryNumber}" hasChanged="false">
							<%-- <td class="${tdClass}"><a href="${productUrl2}?orderCode=${orderData.code}&productCode=${orderEntry.product.visibleCode}">${orderEntry.product.visibleCode}</a></td>
							 --%>
							 <td>
							 	${orderEntry.product.visibleCode}
							 </td>
							 <c:choose>
								<c:when test="${not empty orderEntry.productDescription }">
									<td class="${tdClass}" style="font-weight:lighter;"> ${orderEntry.productDescription}</td>
								</c:when>
								<c:otherwise>
									<td class="${tdClass}"  style="font-weight:lighter;">${orderEntry.product.name}</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td></td>
							<td  style="font-weight:lighter;"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.product.piecesPerBundle}"/> bun / 
							<fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.convertedQuantity}"/> pcs each </td>
							<td>
							<img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt=""> 
							</td>
						</tr>
						<tr>
							<td></td>
							<td style="font-weight:lighter;">
								${orderEntry.formattedNetPriceWOTaxes}
							</td>
						</tr>
						<tr>
						<td></td>
						<td colspan="2" >
						 Ready to ship:	${orderEntry.formattedReadyToShip}
						</td>
						</tr>
					</table>
				</c:forEach>		
			
			</div>
			
			<%-- <c:choose>
				<c:when test="${orderData.code gt 0}">
					<foot:footerWTop searchPageData=1	/>
				</c:when>
				<c:otherwise> --%>
				<foot:footer />
				<%-- </c:otherwise>
			</c:choose> --%>




		<%-- 
		<div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; ">
		
			<br>
<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: black; width: 100%; "></div>
			<p style="padding-left: 8px; text-transform: uppercase; font-weight: 900; background: url(../images/pagehead-homepage-bg.png) repeat-x;"><spring:theme code="mobile.orderHistory.detail.order.summary" /></p>
			<table style=" border:solid 2px black; width: 95%; float: left; height:auto; margin: 10px;">
				<c:if test="${orderData.hotRolledWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.hot_rolled"></spring:theme>
						</td>
						<td style="font-weight: lighter;">
							${orderData.formattedHotRolledWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.coldRolledWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.cold_rolled"></spring:theme>
						</td>
						<td style="font-weight: lighter;">
							${orderData.formattedColdRolledWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.galvanizedWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.galvanized"></spring:theme>
						</td>
						<td style="font-weight: lighter;">
							${orderData.formattedGalvanizedWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<c:if test="${orderData.aluminizedWeight > 0}">
					<tr>
						<td>
							<spring:theme code="checkout.summary.aluminized"></spring:theme>
						</td>
						<td style="font-weight: lighter;">
							${orderData.formattedAluminizedWeight} &nbsp;${orderData.sapWeightUnit}
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<spring:theme code="checkout.summary.totals"></spring:theme>
					</td>
					<td style="font-weight: lighter;">
						${orderData.formattedTotalWeight} &nbsp;${orderData.sapWeightUnit}
					</td>
				</tr>
			</table>		
		</div>
		
		<div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; ">
		
			
			
				</div>
				
			<div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; ">
		
			<br>
<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: black; width: 100%; "></div>
			
			<p style="padding-left: 8px; text-transform: uppercase; font-weight: 900;"><spring:theme code="mobile.orderHistory.detail.items" /></p>
				<c:forEach items="${orderData.entries}" var="orderEntry" varStatus="status">
				
					<table style=" border:solid 2px black; width: 95%; float: left; height:auto; margin: 10px;">
			
						<c:url value="${orderEntry.product.url}" var="productUrl"/> 
						<c:url value="/my-account/orderMobile/${orderData.code}" var="productUrl2"/>
						
						<c:set var="accountBaseUrl" value="/my-account/orderMobile" />
						<spring:url var="productUrl2" value="${accountBaseUrl}" />
						
						<c:set var="productInventoryEntry" value="${orderEntry.product.inventoryEntry}" />
					
						<tr class="prolamsa_product_row ${trClass}" entryNumber="${orderEntry.sapEntryNumber}" hasChanged="false">
							<td class="${tdClass}"><a href="${productUrl2}?orderCode=${orderData.code}&productCode=${orderEntry.product.visibleCode}">${orderEntry.product.visibleCode}</a></td>
							<c:choose>
								<c:when test="${not empty orderEntry.productDescription }">
									<td class="${tdClass}" style="font-weight:lighter;"> ${orderEntry.productDescription}</td>
								</c:when>
								<c:otherwise>
									<td class="${tdClass}"  style="font-weight:lighter;">${orderEntry.product.name}</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td></td>
							<td  style="font-weight:lighter;"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.product.piecesPerBundle}"/> bun / 
							<fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.convertedQuantity}"/> pcs each </td>
						</tr>
						<tr>
							<td></td>
							<td style="font-weight:lighter;">
								${orderEntry.formattedNetPriceWOTaxes}
							</td>
						</tr>
						<tr>
						<td colspan="2" class="col_center sap-data ${tdClass }">
						 Ready to ship:	${orderEntry.formattedReadyToShip}
						</td>
						</tr>
					</table>
				</c:forEach>		
			
			
		
		
			</div> --%>
			
		
			<%-- <div class="item_container">
				<table style="width: 10px">
					<tbody>
						<tr>
							<td>
								<order:billingAddressItem order="${orderData}"/>
							</td>
							<td>
								<order:deliveryAddressItem order="${orderData}"/>
							</td>
						</tr>
						<tr>
							<td>
								<order:paymentMethodItem order="${orderData}"/>
							</td>
							<td>
								<order:deliveryMethodItem order="${orderData}"/>
							</td>
						</tr>
					</tbody>
				</table>
				
				<br>

				<order:orderDetailsItem order="${orderData}" isOrderDetailsPage="true"/>
	
				<div class="span-8 last right">
					<order:orderTotalsItem order="${orderData}"/>
				</div>
				<div class="span-8  right">
					<order:orderSummaryItem order="${orderData}"/>
				</div>
			</div> 
			 --%>
			
			
			
			
			
			
			
		</div> 

</template:master>

<script type="text/javascript">
function detailView(code, product)
{    //NEORIS_CHANGE#LCC, 26-Jan-15-->Fix the product search by Code and Product Item
     code = code.substring(code.indexOf("-")+1);
      
	while (code.toString().length<10)
	{
		code = '0'+code;
	}

	document.location.href=window.location.origin+"/store/my-account/orderMobile?orderCode=" + code + "&productCode=" + product;

}

</script>