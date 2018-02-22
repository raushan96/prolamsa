<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>

<script type="text/javascript"> // set vars
	var cartDataquoteAllowed = ${cartData.quoteAllowed};
	var negotiateQuote = ${placeOrderForm.negotiateQuote};
</script>

<div class="span-20 right last" id="negotiate-quote-div" style="display:none">
		<div class="item_container_holder">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<br />
				<h2 style="color: black"><spring:theme code="checkout.summary.negotiateQuote.quoteReason"/></h2>
			</div>
			<div class="item_container">
				<form:textarea cols="100" cssClass="text" id="quoteRequestDescription" path="quoteRequestDescription" />
			</div>
		</div>
		<div class="item_container_cancel_placeorder">
			<form:input type="hidden" class="negotiateQuoteClass" path="negotiateQuote"/>
			<button class="positive right pad_right negotiateQuote" id="negotiateQuoteButton">
				<spring:theme code="checkout.summary.negotiateQuote.proceed"/>
			</button>
			<a href="javascript: ;" class=".placeorder_right button" id="cancel-quote-negotiation"><spring:theme code="checkout.summary.negotiateQuote.cancel"/></a>
		</div>
</div>