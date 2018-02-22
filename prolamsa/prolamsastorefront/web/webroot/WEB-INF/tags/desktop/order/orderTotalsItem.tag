<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<%@ attribute name="containerCSS" required="false" type="java.lang.String" %>

<div class="item_container_holder ${containerCSS}">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="text.account.order.orderTotals" text="Order Totals"/></h2>
	</div>
	
	<div class="item_container">
		<!-- NEORIS_CHANGE #74 -->
		<dl class="order_totals" id="totals_order_dl">
<%-- 		<format:price priceData="${cartData.subTotal}"/> --%>
			
<%-- 			<dt  style="font-weight: bold;"><spring:theme code="checkout.summary.base_product"></spring:theme></dt> --%>
			<dt  style="font-weight: bold;">&nbsp;</dt>
			<dd  style="font-weight: bold;"><spring:theme code="checkout.summary.total"></spring:theme></dd>									
			
		    <c:if test = "${order.hotRolledPrice > 0}" >
			  <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>					
			  <dd>${order.formattedHotRolledPrice} &nbsp;${order.sapCurrency}</dd>		
			</c:if>		
			
			 <c:if test = "${order.coldRolledPrice > 0}" >		
			  <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>			
			  <dd>${order.formattedColdRolledPrice} &nbsp;${order.sapCurrency}</dd>
			</c:if>
					
			<c:if test = "${order.galvanizedPrice > 0}" >												
			  <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
			  <dd>${order.formattedGalvanizedPrice} &nbsp;${order.sapCurrency}</dd>
			</c:if>
			
			<c:if test = "${order.aluminizedPrice > 0}" >	
			  <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd>${order.formattedAluminizedPrice} &nbsp;${order.sapCurrency}</dd>
			</c:if>
			
			<c:if test = "${order.galvametalPrice > 0}" >	
			  <dt><spring:theme code="checkout.summary.galvametal"></spring:theme></dt>
			  <dd>${order.formattedGalvametalPrice} &nbsp;${order.sapCurrency}</dd>
			</c:if>
			
			<c:if test = "${order.totalDeliveryCost > 0}" >	
			  <dt><spring:theme code="checkout.summary.totalDeliveryCost"></spring:theme></dt>
			  <dd>${order.formattedTotalDelvieryCost} &nbsp;${order.sapCurrency}</dd>
			</c:if>
			
			<c:if test = "${order.totalAssurance > 0}" >	
			  <dt><spring:theme code="checkout.summary.totalAssurance"></spring:theme></dt>
			  <dd>${order.formattedTotalAssurance} &nbsp;${order.sapCurrency}</dd>
			</c:if>
			
			<c:if test="${baseStore.name eq 'Prolamsa Mex'}">
				<c:if test = "${order.sapSubtotalPrice > 0}" >	
					<dt>-----------------------------</dt>
					<dd>-----------------------------</dd>
					
					<dt><spring:theme code="checkout.summary.subtotal"></spring:theme></dt>
					<dd>${order.formattedSapSubtotalPrice} &nbsp;${order.sapCurrency}</dd>
				</c:if>
			</c:if>
			
			<c:if test="${baseStore.name eq 'Prolamsa A4C'}">
				<c:if test = "${order.sapSubtotalPrice > 0}" >	
					<dt>-----------------------------</dt>
					<dd>-----------------------------</dd>
					
					<dt><spring:theme code="checkout.summary.subtotal"></spring:theme></dt>
					<dd>${order.formattedSapSubtotalPrice} &nbsp;${order.sapCurrency}</dd>
				</c:if>
			</c:if>
			
			<c:if test = "${order.totalTaxas > 0}" >	
			  <dt><spring:theme code="checkout.summary.totalTaxas"></spring:theme></dt>
			  <dd>${order.formattedTotalTaxas} &nbsp;${order.sapCurrency}</dd>
			</c:if>
						
			<dt><spring:theme code="checkout.summary.totals"></spring:theme></dt>
			<dd>${order.formattedSapTotalPrice} &nbsp;${order.sapCurrency}</dd>
		</dl>
	</div>
</div>
