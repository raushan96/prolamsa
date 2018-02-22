<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout/single" %>

<spring:url value="/checkout/single/summary/getCheckoutCart.json" var="getCheckoutCartUrl" />

<script type="text/javascript"> // set vars
	 var getCheckoutCartUrl = '${getCheckoutCartUrl}';
</script>

<div class="checkout_summary_flow clearfix">
	<checkout:summaryFlowPaymentType />
	
	<checkout:summaryFlowCostCenter billingAddress="${billingAddresses}" costCenter="${costCenter}" />
	
	<checkout:summaryFlowPayment />

	<checkout:summaryFlowDeliveryAddress deliveryAddress="${deliveryAddress}" costCenter="${costCenter}"/>
	<!-- NEORIS_CHANGE #74 -->
	<checkout:summaryFlowDeliveryMode selectedTransportationMode="${cartData.transportationMode}" />
</div>
