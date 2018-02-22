<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="costCenter" required="true" type="de.hybris.platform.b2bacceleratorfacades.order.data.B2BCostCenterData" %>
<%@ attribute name="billingAddress" required="true" type="de.hybris.platform.commercefacades.user.data.AddressData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<spring:url value="/checkout/single/summary/getCostCenters.json" var="getCostCenters"/>
<spring:url value="/checkout/single/summary/getCheckoutCart.json" var="getCheckoutCartUrl" />
<spring:url value="/checkout/single/summary/setCostCenter.json" var="setCostCenterUrl" />
<spring:url value="/checkout/single/summary/updateCostCenter.json" var="updateCostCenterUrl" />
<script type="text/javascript"> // set vars

	var updateCostCenterUrl = '${updateCostCenterUrl}';
	var setCostCenterUrl = '${setCostCenterUrl}';

</script>

<div class="checkout_summary_flow_b" id="checkout_summary_costcenter_div">
	<div class="item_container_holder">
		<div class="title_holder">
			<div class="title">
				<div class="title-top">
					<span></span>
				</div>
			</div>
			<h2><spring:theme code="checkout.summary.costCenter.header" htmlEscape="false"/><span></span></h2>
		</div>

		<div class="item_container">
				<!-- NEORIS_CHANGE #74 Hide select cost center element and only show the current cost center -->
				<form:select id="CostCenter" style="display:none;" path="costCenters" cssClass="card_date">
					<option value="" label="<spring:theme code='costCenter.title.pleaseSelect'/>">
					<form:options items="${costCenters}" itemValue="code" itemLabel="name"/>
				</form:select>
				
				<!-- NEORIS_CHANGE #74 -->
				<%-- <p id="cost_center_name">${cartData.costCenter.unit.name}</p>
				<p id="cost_center_streetName">${cartData.billingAddress.line1}</p>
				<p id="cost_center_streetName">${cartData.billingAddress.line2}</p>
				<p id="cost_center_town">${cartData.billingAddress.town}, ${cartData.billingAddress.region.name},${cartData.billingAddress.country.name}</p> --%>
				
				
				<p id="cost_center_name">${billingAddress.name}</p>
				<p id="cost_center_streetName">${billingAddress.line1}</p>
				<p id="cost_center_streetName">${billingAddress.line2}</p>
				<p id="cost_center_town">${billingAddress.town}, ${billingAddress.region.name},${billingAddress.country.name}</p>
				<c:if test="${not empty paymentTerms}">
				    <p id="cost_center_town"><spring:theme code="checkout.summary.paymentTerms"/></p>
				    <p id="cost_center_town"><spring:theme code="tPayment_${paymentTerms}"/></p>
				</c:if>
				
		</div>
	</div>
</div>
