<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>


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
				<h2><spring:theme code="checkout.orderConfirmation.thankYou" arguments="${orderData.code}"/></h2>
			</div>
			
			<div class="item_container_holder">
		   		<div class="item_container">
		   			<c:if test="${baseStore.uid eq '1000'}">
						<c:if test="${orderData.semaphoreCredit eq 'R'}">
							<p style="color:#FF0000; font-size:medium;">
								<br>
								<spring:theme code="order.confirmation.semaphore.red"/>
								<br>
							</p>
						</c:if>
					</c:if>
					<c:if test="${baseStore.uid eq '2000'}">
						<c:if test="${orderData.semaphoreCredit eq 'R'}">
							<p style="color:#FF0000; font-size:medium;">
								<br>
								<spring:theme code="order.confirmation.semaphore.red.usa"/>
								<br>
							</p>
						</c:if>
					</c:if>
					<p><br>
							<spring:theme code="checkout.orderConfirmation.copySentTo" arguments="${email}"/>
					</p>
					<div class="placeorder_right">
						<div class="span-24 clearfix last place-order-top" >
							<p class="right"><spring:theme code="checkout.orderConfirmation.recurrentOrder"/>
								<a href="#" onclick="popupTemplateOrder('${orderData.code}');" class="doCheckoutBut"><spring:theme text="Create a template" code="checkout.orderConfirmation.createTemplate"/>
								</a>
							</p>
						</div>						
					</div>
					
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
					</div>	 --%>	
					<div class="span-8 last right" style="float: right; padding-right: 10px; border: thick thick thick thick;">
						<order:neorisOrderTotalsItem order="${orderData}"/>
					</div>						
					
				</div>
			</div>
		</div>
	</div>
</template:page>

<script  type="text/javascript">
function popupTemplateOrder(codeOrder)
{ 
	ACC.modals.openOnModal("<spring:theme code="templates.title.save"/>", "<c:url value="/templatesOrder/popup" />?codeOrder=" + codeOrder, "#popTemplateContainer");
}

onDOMLoaded(initPage);

function initPage()
{
	ACC.neorisDownloadDocuments.initialize({});	
}
</script>
