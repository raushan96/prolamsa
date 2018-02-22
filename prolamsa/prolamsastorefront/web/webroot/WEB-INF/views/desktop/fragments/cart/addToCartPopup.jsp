<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="json" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="frm" tagdir="/WEB-INF/tags/desktop/form" %>


<spring:theme code="popupcart.part" var="popupCartPartNumerText"/>
<spring:theme code="popupcart.description" var="popupCartDescriptionText"/>
<spring:theme code="popupcart.quantity" var="popupCartQuantityText"/>
<spring:theme code="popupcart.showall" var="popupCartShowallText"/> 
<spring:theme code="popupcart.resetcart" var="popupCartResetcartText"/> 

<c:set var="divStyleIE9" value=""/>
<c:if test="${fn:contains(header['User-Agent'],'MSIE') and (fn:contains(header['User-Agent'],'9') or fn:contains(header['User-Agent'],'8'))}">
	<c:set var="divStyleIE9" value="max-height:130px;min-height:130px;overflow-y:auto;overflow-x:hidden;"/>
</c:if>


{
	"cartData": {	   
		"total":	"${cartData.totalPrice.value}",
		
		"products":	[ <c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
						{
							"code":			"${cartEntry.product.code}",
							"name":			"<c:out value='${cartEntry.product.name}' />",
							"quantity":		"${cartEntry.quantity}",
							"convertedQuantity":	"<c:out value='${cartEntry.convertedQuantity}' />",
							"price":		"${cartEntry.basePrice.value}",
							"categories":	[<c:forEach items="${cartEntry.product.categories}" var="category" varStatus="categoryStatus">
												"<c:out value='${category.name}' />"<c:if test="${not categoryStatus.last}">,</c:if>
											</c:forEach>]
						}<c:if test="${not status.last}">,</c:if>
					</c:forEach>]
	},

	"cartGlobalMessagesHtml" : "<spring:escapeBody javaScriptEscape="true"><common:globalMessages/></spring:escapeBody>"
	,

	"cartPopupHtml": "<spring:escapeBody javaScriptEscape="true">
						<spring:theme code="text.addToCart" var="addToCartText"/>
						<c:url value="/cart" var="cartUrl"/>
						<c:url value="/cart/checkout" var="checkoutUrl"/>
						<div class="cart_modal_popup">
							<div class="title">
								<theme:image code="img.addToCartIcon" alt="${addToCartText}" title="${addToCartText}"/>
								<h3> <spring:theme code="basket.added.to.basket" /></h3>
								<a href="#" class="close" id="add_to_cart_close"></a>
							</div>
					
							<%-- NEORIS_CHANGE #63 Removed code to show "Showing..."--%>
								<p class="legend">
									<a href="${cartUrl}">${porupCartShowllText}</a>
								</p>
								<div class="addedItems" style="${divStyleIE9}">
									<table class="cartPopupTable">
										<thead>
										  <tr>										     
										     <th class="partNumberTd"><p>${popupCartPartNumerText}</p></th>
							                 <th class="product_description"><p>${popupCartDescriptionText}</p></th>
							                 <th><p>${popupCartQuantityText}</p></th>
										  </tr>
										</thead>
										<tbody>
										
										<c:forEach items="${entries}" var="entry" >											
										  <tr>										    
										    <td class="partNumberTd"><p><c:out value="${entry.product.visibleCode ne '' ? entry.product.visibleCode :  entry.product.baseCode}" /></p></td>
										    <td class="product_description"><p><c:out value="${entry.product.name}" /></p></td>
										    <%-- <td><p><fmt:formatNumber pattern="###,###,###,###,##0.00"  value="${entry.convertedQuantity}"/></p></td> --%>
										    <td><p><fmt:formatNumber pattern="${ (fn:contains(unit.code,'bun')  or fn:contains(unit.code,'pc')) ? '###,###,###,###,##0' : '###,###,###,###,##0.000' }"  value="${entry.convertedQuantity}"/></p></td>
										  </tr>											 
										</c:forEach>
										</tbody>
									</table> 
								 </div>
	
							
							<div class="links">
								 <div align="center" style="align:center; font-size: 10px" ><spring:theme code="checkout.creditUsed"/>							       
								   <frm:formProgressBar value="${creditScoreCard}"  lowLimit="${scoreLowLimit}"  highLimit="${scoreHighLimit}"/><br/>
								   <frm:formSemaphore semaphoreCredit="${semaphoreCredit}"/>
								</div>
							    
								<a href="${cartUrl}" class="button">
									<spring:theme code="checkout.checkout" />
								</a>
							</div>
																				
																			
						</div>
					</spring:escapeBody>"
}


