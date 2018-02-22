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
		<div class="span-20 last main-rightXXX" style="padding-bottom: 30px !important;">
			<div class="title_holder">
				<h2><spring:theme code="order.quoteRequest.thankYou"/></h2>
			</div>
			<div class="item_container_holder">
		   		<div class="item_container">
		   			<div>
		   				<p><spring:theme code="order.quickQuoteRequest.message"/></p>
		   			</div>
		   			
		   			<c:if test="${isOrderSplittedDueHSS eq true}">
		   				<div class="messageForSplit">
		   					<p><spring:theme code="order.quickQuoteRequest.hssQuoteSplitted.message"/></p>
		   				</div>
		   			</c:if>
					
					<c:url value="${continueUrl}" var="continueShoppingUrl"/>
					
					<div class="span-24">
						<div class="span-24">
							<cart:continueShopping
								continueShoppingUrl="${continueShoppingUrl}" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template:page>

<script type="text/javascript">
</script>