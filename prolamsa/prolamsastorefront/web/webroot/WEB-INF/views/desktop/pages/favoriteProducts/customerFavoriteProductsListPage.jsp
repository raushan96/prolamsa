<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="backorder" tagdir="/WEB-INF/tags/desktop/backorder"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<template:page pageTitle="${pageTitle}">
	<c:set var="favoriteProductsManageBaseUrl" value="list?sort=${favoriteProductSearchForm.sort}" />
	<spring:url var="favoriteProductsManageSearchUrl" value="${favoriteProductsManageBaseUrl}" />
	
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	 <div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
	
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
			
		</div>
	</div>		
	
	<div class="grid-container" style="width: 1250px !important;">
	<div id="globalMessages">
				<common:globalMessages/>
			</div>		
<%-- 	    <nav:accountNav selected="${cmsPage.label}"/>    	 --%>

		<div class="span-4 aside-left">
			<nav:facetFavoritesNavAppliedFilters pageData="${searchPageData}" removeQueryBaseUrl="/favorite-products/list"/>
			<nav:facetFavoritesNavRefinements pageData="${searchPageData}"/>

		</div>
		
		<div class="span-20 last main-right" style="width: 1000px !important;">
			<div class="title_holder">
				<h2><spring:theme code="text.account.customerFavoriteProductsList" text="List Favorite Products"/></h2>
			</div>
			
			<br>
			
			<fieldset>			
				<div class="span-16">
					<product:neorisAddItemsToCartButton />
					<nav:pagination top="true"  supportShowPaged="false" supportShowAll="false" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}" numberPagesShown="${numberPagesShown}"/>
				</div>
								
				<div class="span-16" style="margin-top: 50px;">
	 				<div id="resultsList" data-isOrderForm="false" class="product_table2">
	 					<product:neorisProductList productList="${searchPageData.results}" />
					</div>
				</div>
							
				<div class="span-16">
					<nav:pagination  top="false"  supportShowPaged="false" supportShowAll="false" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}" numberPagesShown="${numberPagesShown}"/>
					<br><br>
					<product:neorisAddItemsToCartButton />	
				</div>
			</fieldset>
		</div>
	</div>
	<br>
</template:page>

<script type="text/javascript">

onDOMLoaded(bindFavoriteProductManageList);

function bindFavoriteProductManageList()
{
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
}

function submitFormFromDropDown()
{
	var val = jQuery(this).val();		
	jQuery("#sortField").val(val);
	jQuery("#favoriteProductsForm").submit();
}

</script>