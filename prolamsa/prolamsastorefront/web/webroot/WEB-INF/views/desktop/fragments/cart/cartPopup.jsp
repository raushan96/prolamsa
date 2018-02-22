<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="frm" tagdir="/WEB-INF/tags/desktop/form" %>

<spring:theme code="text.addToCart" var="addToCartText"/>
<spring:theme code="text.popupCartTitle" var="popupCartTitleText"/>

<!-- NEORIS_CHANGE #64  Adding localization for popup cart-->
<spring:theme code="popupcart.part" var="popupCartPartNumerText"/>
<spring:theme code="popupcart.description" var="popupCartDescriptionText"/>
<spring:theme code="popupcart.quantity" var="popupCartQuantityText"/>
<spring:theme code="popupcart.showall" var="popupCartShowallText"/> 
<spring:theme code="popupcart.resetcart" var="popupCartResetcartText"/>
<spring:theme code="popupcart.items" var="popupCartItemsText"/>  

<c:url value="/cart" var="cartUrl"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>
<div class="cart_modal_popup">
	<div class="title">
		<theme:image code="img.addToCartIcon" alt="${popupCartTitleText}" title="${popupCartTitleText}"/>
		<h3><spring:theme code="popup.cart.title"/></h3>
		<a href="#" class="close" id="ajax_cart_close" title="<spring:theme code="popup.close"/>"  alt="<spring:theme code="popup.close"/>"></a>
	</div>
	
	<c:if test="${empty numberItemsInCart or numberItemsInCart eq 0}">
		<div class="cart_modal_popup empty-popup-cart">
			<spring:theme code="popup.cart.empty"/>
		</div>
	</c:if>
	
	<c:if test="${numberItemsInCart > 0 }"> 	
				
				<c:set var="divStyleIE9" value=""/>
				<c:if test="${fn:contains(header['User-Agent'],'MSIE') and (fn:contains(header['User-Agent'],'9') or fn:contains(header['User-Agent'],'8'))}">
					<c:set var="divStyleIE9" value="max-height:130px;min-height:130px;overflow-y:auto;overflow-x:hidden;"/>
				</c:if>
				
					<!-- NEORIS_CHANGE #64 Removed image and added table to show products-->
					<div class="prod_info" style="${divStyleIE9}">
						<p class="legend">
							<a href="${cartUrl}" >${popupCartShowallText}</a>
							<a id="reset_cart" href="#" class="reset_cart_button">${popupCartResetcartText}</a>
						</p>
						<!-- NEORIS_CHANGE #51 -->
						
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
								<c:url value="${entry.product.url}" var="productUrl"/>
									  <tr>
									    <td class="partNumberTd">
									    	<p>
									    		<a href="${productUrl}"><c:out value="${entry.product.visibleCode ne '' ?  entry.product.visibleCode : entry.product.baseCode}" /></a>
									    	</p>
									    </td>
									    <td class="product_description">
									    	<p>
								    			<c:choose>
													<c:when test="${not empty entry.productDescription }">
														<a href="${productUrl}">${entry.productDescription}</a>
													</c:when>
													<c:otherwise>
														<a href="${productUrl}">${entry.product.name}</a>
													</c:otherwise>
												</c:choose>
									    	</p>
									    </td>
									    <td class="col_right">
									        <%-- <p><fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${entry.convertedQuantity}"/></p> --%>  
									    	<%-- <p><fmt:formatNumber pattern="${ (fn:contains(unit,'bun')  or fn:contains(unit,'pc')) ? '###,###,###,###,##0' : '###,###,###,###,##0.000' }"  value="${entry.convertedQuantity}"/></p> --%>
									    	<c:choose>
									    		<c:when test="${entry.product.isAPI eq true}">
									    			<p><fmt:formatNumber pattern="###,###,###,###,##0"  value="${entry.quantity}"/></p>
									    		</c:when>
									    		<c:otherwise>
									    			<c:choose>
											    	   <c:when test="${fn:contains(unit,'prolamsa_pc_lb') || fn:contains(unit,'prolamsa_pc_kg')}">
											    	      <p><fmt:formatNumber pattern="###,###,###,###,##0"  value="${entry.quantity * entry.product.piecesPerBundle}"/></p>
											    	   </c:when>
											    	    <c:when test="${fn:contains(unit,'prolamsa_lb')}">
											    	      <p><fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${entry.quantity * entry.product.bundleLbEquiv}"/></p>
											    	   </c:when>
											    	    <c:when test="${fn:contains(unit,'prolamsa_ft')}">
											    	      <p><fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${entry.quantity * entry.product.ftEquiv}"/></p>
											    	   </c:when>
											    	   <c:when test="${fn:contains(unit,'prolamsa_mt')}">
											    	      <p><fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${entry.quantity * entry.product.mtEquiv}"/></p>
											    	   </c:when>
											    	   <c:when test="${fn:contains(unit,'prolamsa_kg') || fn:contains(unit,'prolamsa_mt')}">
											    	      <p><fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${entry.quantity * entry.product.bundleKgEquiv}"/></p>
											    	   </c:when>
											    	    <c:when test="${fn:contains(unit,'prolamsa_ton')}">
											    	      <p><fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${entry.quantity * entry.product.tnEquiv}"/></p>
											    	   </c:when>									    	   									    	   
											    	   <c:otherwise>
											    	      <p><fmt:formatNumber pattern="${ fn:contains(unit,'prolamsa_bun_lb') || fn:contains(unit,'prolamsa_bun_kg') ? '###,###,###,###,##0' : '###,###,###,###,##0.000' }"  value="${entry.quantity}"/></p>
											    	   </c:otherwise>
									    			</c:choose>
									    		</c:otherwise>
									    	</c:choose>
									    </td>
									  </tr>
								 </c:forEach>
							</tbody>
						</table> 
							<!-- NEORIS_CHANGE #64 Removed product variants code -->
					</div>
	</c:if>
	<!-- NEORIS_CHANGE #51 -->
	<!--
		<div  class="prod_cart-total">
			<spring:theme code="popup.cart.total"/>&nbsp;<format:price priceData="${cartData.totalPrice}"/>
		</div>
	-->
	<!-- NEORIS_CHANGE #43 -->
	<!--
		<c:if test="${not empty lightboxBannerComponent && lightboxBannerComponent.visible}">
			<div class="content_slot">
				<cms:component component="${lightboxBannerComponent}"/>
			</div>
		</c:if>
	-->
	<div class="links">
		<p>${numberItemsInCart}&nbsp${popupCartItemsText}</p>
	
	     <c:if test="${creditScoreCard > 0 and numberItemsInCart > 0}">
		     <div align="center" style="align:center; font-size: 10px" >
		     	<spring:theme code="checkout.creditUsed"/>							       
			     <frm:formProgressBar value="${creditScoreCard}"  lowLimit="${scoreLowLimit}"  highLimit="${scoreHighLimit}"/>
			    <frm:formSemaphore semaphoreCredit="${semaphoreCredit}"/>	    		   		      		        		    
			 </div>
		 </c:if> 
		  <a href="${cartUrl}" class="button">
	         <%--<theme:image code="img.addToCartIcon" alt="${addToCartText}" title="${addToCartText}"/> --%>
			 <spring:theme code="checkout.checkout" />
		 </a> 			
	</div>
</div>