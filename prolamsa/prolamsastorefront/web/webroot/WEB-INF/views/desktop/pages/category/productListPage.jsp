<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
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
	<%--
	<cms:pageSlot position="Section1" var="feature">
		<cms:component component="${feature}" element="div" class="span-24 section1 cms_disp-img_slot"/>
	</cms:pageSlot>
	--%>
	<div class="grid-container">	
	<div class="span-24">
		<div class="span-4 aside-left">
			<nav:facetNavAppliedFilters pageData="${searchPageData}"/>
			<nav:facetNavRefinements pageData="${searchPageData}"/>

			<cms:pageSlot position="Section5" var="feature">
				<cms:component component="${feature}" element="div" class="section5 cms_disp-img_slot"/>
			</cms:pageSlot>

		</div>
		<div class="span-20 last main-right no-background-no-border">
		
			<cms:pageSlot position="Section2" var="feature">
				<cms:component component="${feature}" element="div" class="section2 cms_disp-img_slot"/>
			</cms:pageSlot>
		
			<div class="span-16">
				<product:neorisAddItemsToCartButton />
				<nav:pagination top="true"  supportShowPaged="false" supportShowAll="false" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}" numberPagesShown="${numberPagesShown}"/>
			</div>
			<div class="span-20">
				<cms:pageSlot position="Section3" var="feature">
					<cms:component component="${feature}" element="div" class="span-6 section3 small_detail"/>
				</cms:pageSlot>

				<c:url value="${searchPageData.categoryCode}" var="currentURL"/>
				<div id="currentPath" data-current-path="${currentURL}"></div>

				

				<div class="span-16">
	 				<div id="resultsList" data-isOrderForm="false" class="product_table2">
	 					<!-- NEORIS_CHANGE #38 -->
	 					<product:neorisProductList productList="${searchPageData.results}" />
					</div>
				</div>
				
				<cms:pageSlot position="Section4" var="feature">
					<cms:component component="${feature}" element="div" class="span-4 section4 cms_disp-img_slot last"/>
				</cms:pageSlot>
				
			</div>
			<div class="span-16">
					<nav:pagination top="false"  supportShowPaged="false" supportShowAll="false" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}" numberPagesShown="${numberPagesShown}"/>
					<product:neorisAddItemsToCartButton />	
			</div>
		</div>
		
	</div>
	</div>
	<br>
	</jsp:body>
</template:page>

<script type="text/javascript">
    
     if (navigator.userAgent.indexOf("Chrome") > 0){    	
    	 window.onunload = window.onbeforeunload = null;
     } else if (navigator.userAgent.indexOf("Firefox") > 0) {    	 
    	 window.onbeforeunload = null; 
     } else if (navigator.userAgent.indexOf("MSIE") > 0) {    	 
    	 window.onunload = window.onbeforeunload = null; 
     } else if (navigator.userAgent.indexOf("Opera") > 0) {    	 
    	 window.onunload = window.onbeforeunload = null;
     } else if (navigator.userAgent.indexOf("Safari") > 0) {    	   
    	 window.onunload = window.onbeforeunload = null;
     }                  
    
</script>
