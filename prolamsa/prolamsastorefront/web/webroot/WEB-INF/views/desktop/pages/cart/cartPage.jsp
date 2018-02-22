<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="frm" tagdir="/WEB-INF/tags/desktop/form" %>

<spring:theme text="Your Shopping Cart" var="title" code="cart.page.title"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>

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
			<cart:cartRestoration/>
			<cart:cartValidation/>
		</div>

		<div class="span-20 last main-rightXXX">
			
			<div class="title_holder">
					<h2><spring:theme code="basket.page.title.yourItems"/></h2>
			</div>
			<c:if test="${not empty cartData.entries and creditScoreCard > 0}">
				 <br/>
				<div align="center" style="width:100%; font-size: 12px">
				     <spring:theme code="checkout.creditUsed"/>
				     <frm:formProgressBarDOM value="${creditScoreCard}"  lowLimit="${scoreLowLimit}"  highLimit="${scoreHighLimit}"/>
				     <frm:formSemaphore semaphoreCredit="${semaphoreCredit}"/>
				</div>
			
				<br>	   	
		   		<span class="cart_id Text_Table_Align_Left"><spring:theme code="basket.page.cartId"/><span class="cart-id-nr">${cartData.code}</span></span>
	   		</c:if>
			<div class="item_container">
			   	<div class="">
				   	<c:if test="${not empty cartData.entries}">	            			            							       			
						<br/>
						<c:url value="${continueUrl}" var="continueShoppingUrl"/>
						
						<div class="span-24">			    				    				    
							<div class="span-24">					     
								<cart:continueShopping continueShoppingUrl="${continueShoppingUrl}" />
								<cart:checkoutButton checkoutUrl="${checkoutUrl}" basketAddToCart="${basketAddToCart}" />
								<br><br>
							</div>
																															
							<div class="span-24">
								<c:if test="${not empty message}">
									<br />
									<span class="errors">
										<spring:theme code="${message}"/>
									</span>
								</c:if>										
							</div>
							
							<div class="span-24 last">
								<cart:cartItems cartData="${cartData}"/>
							</div>
							
							<div class="span-12">
								<cart:cartPromotions cartData="${cartData}"/>
							</div>	
								
					        <div class="span-24 place-order-bottom" style="font-size: 16;">		 			
							  <cart:checkoutButton checkoutUrl="${checkoutUrl}" basketAddToCart="${basketAddToCart}" />
							</div>	
										   																				 
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>		
</template:page>

