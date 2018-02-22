<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<%--
			<nav:accountNav selected="${cmsPage.label}"/>
		--%>
		<div class="span-20 last main-rightXXX">
			<div class="title_holder">
					<h2><spring:theme code="text.account.order.yourOrder" text="Your Order"/></h2>
			</div>




<%--
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	
	<div class="span-20 last">
		<div class="span-25 last delivery_stages">
		 --%>
		   <div class="item_container_holder">
		   	<div class="item_container">
				<order:billingAddressItem order="${orderData}"/>
			
				<order:deliveryAddressItem order="${orderData}"/>
			
				<order:paymentMethodItem order="${orderData}"/>
			
				<order:deliveryMethodItem order="${orderData}"/>
			
            <br/>
			<order:orderDetailsItem order="${orderData}" isOrderDetailsPage="true"/>

			<div class="span-8 last right" style="float: right; padding-right: 10px; border: thick thick thick thick;">
						<order:neorisOrderTotalsItem order="${orderData}"/>
					</div>
			
			<%-- <div class="span-8  right place-order-summary-total">
				<order:orderSummaryItem order="${orderData}"/>
			</div>
			<div class="span-8 last right place-order-summary-total">
				<order:orderTotalsItem order="${orderData}"/>
			</div> --%>

		</div></div>
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
