<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/desktop/grid" %>


<template:page pageTitle="${pageTitle}">
	<jsp:attribute name="pageScripts">
		<product:productDetailsJavascript/>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.productlisting.js"></script>
	</jsp:attribute>
	
	<jsp:body>
		<div id="breadcrumb" class="breadcrumb">
			<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
		</div>
		<c:if test="${not empty message}">
			<spring:theme code="${message}" />
		</c:if>
		<div id="globalMessages">
			<common:globalMessages />
		</div>
		<div class="span-24">
			<cms:pageSlot position="SideContent" var="feature" element="div" class="span-4 side-content-slot cms_disp-img_slot">
				<cms:component component="${feature}" />
			</cms:pageSlot>
			<div class="span-20 right last advanced_search">
						
				<c:url value="/search" var="currentURL"/>
				<div id="currentPath" data-current-path="${currentURL}"></div>
													
			
				<c:set var="skuIndex" scope="session" value="0" />
				<spring:theme code="product.grid.confirmQtys.message" var="gridConfirmMessage" />

				
					<div id="resultsList"  data-isOrderForm="false" data-isOnlyProductIds="${advancedSearchForm.onlyProductIds}">
						<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
							<product:productListerItem product="${product}" />
						</c:forEach>
					</div>
									
					<grid:gridLegend />
											
					<div id="skuIndexSavedValue" name="skuIndexSavedValue" data-sku-index="${sessionScope.skuIndex}"><!--  don't remove this div. This is used by the order form search --></div>
					
				<common:infiniteScroll/>
				
				
				<script id="product-id-tag-box-template" type="text/x-jquery-tmpl">
				<span class="product-id-tag-box" id="product-id-\${productId}-tag">
				  <span>\${productId}</span>
				  <button class="js-remove-product-id form" type="submit">x</button>
				</span>
				</script>			

				<product:productOrderFormJQueryTemplates />				
				
			</div>
		</div>
		<input id="searchByKeywordLabel" type="hidden" value='<spring:theme code="search.advanced.keyword"/>' />
		<input id="searchByIdsLabel" type="hidden" value='<spring:theme code="search.advanced.productids"/>' />
		<c:remove var="skuIndex" scope="session" />
	</jsp:body>
</template:page>
