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
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	 <div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
	
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
			<div id="globalMessages">
				<common:globalMessages/>
			</div>
		</div>
	</div>		
	
	<c:if test = "${ (not empty accConfMsgs) ||  (not empty accInfoMsgs) || (not empty accErrorMsgs)}" >
	    <br/><br/>
	</c:if>
	
	<div class="grid-container">		
		<div class="span-20 last main-rightXXX">

			<div class="title_holder">
				<h2><spring:theme code="text.quotes.orderStatusDetails.label" text="Quote Status Details" /></h2>
			</div>
			<div class="item_container_holder">
		   		<div class="item_container">
		   		    <br />
		   			<order:quoteOrderStatusDecisionItem quoteOrderForm="${quoteOrderDecisionForm}" orderData ="${orderData}" orderHistoryEntries="${orderHistoryEntryData}"/>
					<order:billingAddressItem order="${orderData}"/>
					<order:deliveryAddressItem order="${orderData}"/>
					<order:paymentMethodItem order="${orderData}"/>
					<order:deliveryMethodItem order="${orderData}"/>
					<br><br><br><br><br><br><br><br><br>
					<order:quoteDetailsItem order="${orderData}" isOrderDetailsPage="true"/>
					<div class="span-12">
						<order:receivedPromotions order="${orderData}"/>
					</div>
					<c:if test="${orderData.triggerData ne null}">
						<order:replenishmentScheduleInformation order="${orderData}"/>
					</c:if>
					</div>
					<div class="span-8 last right" style="float: right; padding-right: 10px; border: thick thick thick thick;">
						<order:quoteTotalsItem order="${orderData}"/>
					</div>
					<%-- <div class="span-8  right">
						<order:quoteSummaryItem order="${orderData}"/>
					</div> --%>
				
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