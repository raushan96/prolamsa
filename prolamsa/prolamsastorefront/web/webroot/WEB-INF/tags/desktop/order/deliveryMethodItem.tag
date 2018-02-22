<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<div id="order_summary_deliverymode_div" class="order_summary_flow_b complete" style="display: block;">
	<div class="item_container_holder positive">
		
			<h2><spring:theme code="text.deliveryMethod" text="Delivery Method"/></h2>
		
		<div class="item_container">
			<%-- <ul class="pad_none">
				<!--  NEORIS_CHANGE #74 Change to show transportation mode instead of delivery method  -->
				<li>${order.transportationMode.name} (${order.transportationMode.incotermCode}) (${order.transportationMode.maxCapacity} Tons)</li>
			</ul> --%>			
			
			<!--  NEORIS_CHANGE #74 Change to show transportation mode instead of delivery method  -->
			<p>${order.transportationMode.name}  (${order.transportationMode.maxCapacity} Tons)</p>
			<p>${order.transportationMode.incotermCode} - ${order.transportationMode.incotermDescription}</p>
						
		</div>
		
		<h2><spring:theme code="checkout.summary.requestedDeliveryDate" /></h2>
		<div class="item_container">			
			
			<p><formDate:formFormatDate pattern="MM/dd/yyyy" value="${order.requestedDeliveryDate}" /></p>
			
		</div>
	</div>
</div>

