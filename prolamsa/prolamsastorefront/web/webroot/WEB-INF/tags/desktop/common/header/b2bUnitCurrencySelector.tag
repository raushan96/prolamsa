<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="selectedB2BUnit" required="true" type="de.hybris.platform.b2b.model.B2BUnitModel" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:url value="/_s/currency" var="setCurrencyActionUrl"/>
<form:form action="${setCurrencyActionUrl}" method="post" id="currency-form">
	<!-- NEORIS_CHANGE remove skip class from label 
	<spring:theme code="text.currency" var="currencyText"/>
	<label class="" for="currency-selector">${currencyText}</label>-->
	<ycommerce:testId code="header_currency_select">
		<select name="code" id="currency-selector" style="min-width: 20px;" >
<%-- 			<option value="${selectedB2BUnit.currency.isocode}" ${selected}>${selectedB2BUnit.currency.symbol} ${selectedB2BUnit.currency.isocode}</option> --%>
				<option value="${currentCurrency.isocode}" ${selected}>${currentCurrency.symbol} ${currentCurrency.isocode}</option>
		</select>
	</ycommerce:testId>
</form:form>