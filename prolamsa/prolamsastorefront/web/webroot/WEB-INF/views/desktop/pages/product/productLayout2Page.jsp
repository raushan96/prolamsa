<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
	<jsp:attribute name="pageScripts">
		<product:productDetailsJavascript/>
	</jsp:attribute>

	<jsp:body>
		<c:if test="${not empty message}">
			<spring:theme code="${message}"/>
		</c:if>
		<div class="pagehead-wrapperXXX">
			<div class="pagehead grid-container">
				<div id="breadcrumb" class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
				</div>
				<h4>${product.name}</h4>
				<h5><spring:theme code="product.list.header.part" /> ${product.baseCode}</h5>
			</div>
		</div>
		<div class="grid-container">
			<div id="globalMessages">
				<common:globalMessages/>
			</div>
			<cms:pageSlot position="Section1" var="comp" element="div" class="span-24 section1 cms_disp-img_slot">
				<cms:component component="${comp}"/>
			</cms:pageSlot>
			<div class="description-wrapper">
				<table>
					<tr>
						<td class="first-col col-1">
							<spring:theme code="product.list.header.customer.description" />
						</td>
						<td>
							${product.customerDescription}
						</td>
					</tr>
					<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
					<tr>
						<td class="first-col col-2">
							<spring:theme code="product.list.header.manufacturing.description" />
						</td>
						<td>
							${product.manufacturingDescription}
						</td>
					</tr>
					</sec:authorize>
				
					<tr>
						<td class="first-col col-3">
							<spring:theme code="product.list.header.commercial.description" />
						</td>
						<td>
							 ${product.commercialDescription}
						</td>
					</tr>
				</table>
			</div>
	
	
			<product:neorisAddItemsToCartButton />
	
			<div class="grid-100 grid-parent">
				<div id="resultsList" data-isOrderForm="false" class="product_table">
					<!-- NEORIS_CHANGE #38 -->
					<product:neorisProductList productList="${productList}" />
				</div>
			</div>
			
			<!-- NEORIS_CHANGE #107 -->
			<c:if test="${not empty relatedProductList}">				
				<div class="span-24">	
					  <div class="">
					     <h4><spring:theme code="product.list.detail.similarProducts" /></h4>
					  </div> 		      		   	
				   	  <product:productCarousel products="${relatedProductList}"/>	
					  <div class="">
					      <spring:theme code="product.list.detail.carouselFooter" />
					  </div>			
				</div>
			</c:if>

			<div class="span-20">
				<div class="span-20" id="productDetailUpdateable">
	            <!-- NEORIS_CHANGE #38 -->
	<%-- 				<product:productDetailsPanel product="${product}" galleryImages="${galleryImages}" details="${productDetail}" /> --%>
					<cms:pageSlot position="Section2" var="feature" element="div" class="span-8 section2 cms_disp-img_slot last">
						<cms:component component="${feature}"/>
					</cms:pageSlot>				
				</div>
				<cms:pageSlot position="Section3" var="feature" element="div" class="span-20 section3 cms_disp-img_slot">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
				
				<div class="span-20">
					<cms:pageSlot position="UpSelling" var="comp" element="div" class="span-4">
						<cms:component component="${comp}"/>
					</cms:pageSlot>
					<div class="span-16 right last">
						<product:productPageTabs  details="${productDetail}" />
					</div>
				</div>
			</div>
						
			<!--
			<cms:pageSlot position="CrossSelling" var="comp" element="div" class="span-4 last">
				<cms:component component="${comp}"/>
			</cms:pageSlot>
			
			<cms:pageSlot position="Section4" var="feature" element="div" class="span-24 section4 cms_disp-img_slot">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
			-->
			</div>
	</jsp:body>
</template:page>
