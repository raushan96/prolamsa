<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="containerCSS" required="false" type="java.lang.String" %>

	<c:if test="${fn:contains(defaultUoM.code, 'kg') || fn:contains(defaultUoM.code, 'mt') }">
   	<c:set value="kgs" var="currentUoM" />
	</c:if>
	
	<c:if test="${fn:contains(defaultUoM.code, 'lb') || fn:contains(defaultUoM.code, 'ft')}">
   	<c:set value="lbs" var="currentUoM" />
	</c:if>
	<c:if test="${fn:contains(defaultUoM.code, 'ton')}">
   	<c:set value="tons" var="currentUoM" />
	</c:if>

<div class="item_container_holder ${containerCSS}">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="text.quotes.details.summaryTotals" text="Quote Totals"/></h2>
	</div>
	<div class="item_container">
		<dl class="order_totals" id="totals_order_dl">
<%-- 		<format:price priceData="${cartData.subTotal}"/> --%>
			
			<dt style="font-weight: bold;"><spring:theme code="checkout.summary.base_product"></spring:theme></dt>
			<dd style="font-weight: bold;"><spring:theme code="checkout.summary.weight"></spring:theme></dd>
			  
			<c:if test = "${order.hotRolledWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>
			 <dd>${order.formattedHotRolledWeight} &nbsp; ${order.sapWeightUnit}</dd>
			</c:if>
			
			<c:if test = "${order.coldRolledWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>
			  <dd>${order.formattedColdRolledWeight} &nbsp; ${order.sapWeightUnit}</dd>
			</c:if>
			
		     <c:if test = "${order.galvanizedWeight > 0}" >
			   <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
			   <dd>${order.formattedGalvanizedWeight} &nbsp; ${order.sapWeightUnit}</dd>
			</c:if>
			
			<c:if test = "${order.aluminizedWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd>${order.formattedAluminizedWeight} &nbsp; ${order.sapWeightUnit}</dd>
			</c:if>
			
			<dt><spring:theme code="checkout.summary.totals"></spring:theme></dt>
			<dd>${order.formattedTotalWeight} &nbsp; ${order.sapWeightUnit}</dd>
		</dl>
	</div>
</div>
