<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<br />
<div id="order_summary_billing_div" class="order_summary_flow_b complete" style="display: block;">
	<div class="item_container_holder positive">
		
			
			<h2><spring:theme code="text.billingAddress" text="Delivery Address"/></h2>
		
		<div class="item_container">
		    <p>${fn:escapeXml(order.billingAddress.formattedAddress)}</p>
		    <p>${fn:escapeXml(order.billingAddress.name)}</p>
			<p>${fn:escapeXml(order.billingAddress.line1)}</p>
			<p>${fn:escapeXml(order.billingAddress.line2)}</p>
			<p>${fn:escapeXml(order.billingAddress.town)}, ${fn:escapeXml(order.billingAddress.region.name)}, ${fn:escapeXml(order.billingAddress.postalCode)}</p>
			<p>${fn:escapeXml(order.billingAddress.country.name)}</p>
		    <c:if test="${not empty fn:escapeXml(order.TPago)}">
		       <p><spring:theme code="checkout.summary.paymentTerms"/></p>
		       <p><spring:theme code="tPayment_${fn:escapeXml(order.TPago)}"/></p>
		    </c:if>
		    
		
			<%-- <ul class="pad_none">
			    <!-- NEORIS_CHANGE -->											
				<li>${fn:escapeXml(order.billingAddress.name)}</li>
				<li>${fn:escapeXml(order.billingAddress.line1)}</li>
				<li>${fn:escapeXml(order.billingAddress.line2)}</li>
				<li>${fn:escapeXml(order.billingAddress.town)}, ${fn:escapeXml(order.billingAddress.region.name)}, ${fn:escapeXml(order.billingAddress.postalCode)}</li> 
			</ul> --%>
		</div>
	</div>
</div>

<%--
<div id="order_summary_billing_div" class="order_summary_flow_b complete" style="display: block;">
	<div class="item_container_holder positive">
		<div class="title_holder">
			<div class="title">
				<div class="title-top">
					<span></span>
				</div>
			</div>
			<h2><spring:theme code="text.billingAddress" text="Delivery Address"/></h2>
		</div>
		<div class="item_container">
			<ul class="pad_none">
				<li>${fn:escapeXml(order.costCenter.unit.name)}</li>
				<li>${fn:escapeXml(order.billingAddress.line1)}</li>
				<li>${fn:escapeXml(order.billingAddress.line2)}</li>
				<li>${fn:escapeXml(order.billingAddress.town)}, ${fn:escapeXml(order.billingAddress.region.name)}, ${fn:escapeXml(order.billingAddress.postalCode)}</li> 
			</ul>
		</div>
	</div>
</div>
 --%>