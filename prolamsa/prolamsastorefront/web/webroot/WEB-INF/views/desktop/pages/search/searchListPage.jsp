<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
	<jsp:attribute name="pageScripts">
<!-- NEORIS_CHANGE #38 -->
<%-- 		<script type="text/javascript" src="${commonResourcePath}/js/acc.productlisting.js"></script> --%>
	</jsp:attribute>
	
	<jsp:body>
	<div class="pagehead-wrapper">
		<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<div class="grid-container">
	<div class="span-24">
			<div class="span-4 aside-left">
				<nav:facetNavAppliedFilters pageData="${searchPageData}"/>
				<nav:facetNavRefinements pageData="${searchPageData}"/>
			</div>

			<div class="span-20 last main-right no-background-no-border">

				<cms:pageSlot position="Section2" var="feature" element="div" class="span-20">
					<cms:component component="${feature}" element="div" class="section2 cms_disp-img_slot"/>
				</cms:pageSlot>

				<div class="span-16">
					<product:neorisAddItemsToCartButton />
		
					<nav:pagination top="true"
						supportShowPaged="false" 
						 supportShowAll="false" 
						 searchPageData="${searchPageData}" 
						 searchUrl="${searchPageData.currentQuery.url}" 
						 numberPagesShown="${numberPagesShown}"/>
				</div>
			
				<div class="span-20">
					<div class="results">
						<spring:theme code="search.page.searchText" arguments="${searchPageData.freeTextSearch}"/>
					</div>
					<br/>
				
<%-- 					<nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" /> --%>
		
					<c:url value="/search" var="currentURL"/>
					<div id="currentPath" data-current-path="${currentURL}"></div>


					<div class="span-16">
						<div id="resultsList" data-isOrderForm="false" class="product_table">
							<!-- NEORIS_CHANGE #38 -->
							<product:neorisProductList productList="${searchPageData.results}" />
						</div>
					</div>
					
					<product:neorisAddItemsToCartButton />
			</div>
	
			<cms:pageSlot position="Section4" var="feature">
				<cms:component component="${feature}" element="div" class="span-4 section4 cms_disp-img_slot last"/>
			</cms:pageSlot>
		</div>
	</div>
	</div>
	</jsp:body>
</template:page>
