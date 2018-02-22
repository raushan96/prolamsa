<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
	<div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>		
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<div class="span-20 last main-rightXXX">
			<div class="title_holder">
				<c:choose>
					<c:when test="${orderData.status == 'CANCELLED'}">
						<h2>
							<spring:theme code="order.quoteRequest.cancelled.confirmation"  arguments="${orderData.code}"/>
						</h2>
					</c:when>
					<c:otherwise>
						<h2>
							<spring:theme code="order.quoteRequest.confirmation"/>
						</h2>
					</c:otherwise>
				</c:choose>
			</div>
			<br>
			<div class="item_container_holder">
		   		<div class="item_container">
		   			<p>
						<spring:theme code="order.quoteRequest.thankYou"/>
					</p>
					
					<order:billingAddressItem order="${orderData}"/>
					<order:deliveryAddressItem order="${orderData}"/>
					<order:paymentMethodItem order="${orderData}"/>
					<order:deliveryMethodItem order="${orderData}"/>
					<br>
					<order:orderDetailsItem order="${orderData}" isOrderDetailsPage="true"/>
					<br>					
					<%-- <div class="span-8 right place-order-summary-total">
						<order:orderSummaryItem order="${orderData}"/>
					</div>
					<div class="span-8 right last place-order-summary-total">
						<order:orderTotalsItem order="${orderData}"/>
					</div> --%>
					<div class="span-8 last right" style="float: right; padding-right: 10px; border: thick thick thick thick;">
						<order:neorisOrderTotalsItem order="${orderData}"/>
					</div>							
				</div>	
			</div>
		</div>
	</div>
</template:page>

<script type="text/javascript">
onDOMLoaded(initPage);

function initPage()
{
	ACC.neorisDownloadDocuments.initialize({});	
}
</script>
<!--

//-->
</script>
