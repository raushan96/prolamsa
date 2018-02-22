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

<script>
	function sendGA ()
	{
		ga('send','event','CargaCodigos','cargaCodigos');
	}
</script>


<template:page pageTitle="${pageTitle}">
	<jsp:attribute name="pageScripts">
<!-- NEORIS_CHANGE #38 -->
		<product:productDetailsJavascript/>
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
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<c:if test="${not empty message}">
			<spring:theme code="${message}" />
		</c:if>
		<div class="span-20 last main-rightXXX" >
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
					<h2>
					<spring:theme code="search.advanced.codes" text="Advanced Search" />
					</h2>
				</div>
			</div>
			<div class="span-20">	
	
						<c:url value="/search/advanced" var="advancedSearchUrl"/>
						<form:form action="${advancedSearchUrl}" method="get" name="advancedSearchForm" commandName="advancedSearchForm">
							<div class="advanced_search_area">
								<div class="search_input">
<!-- NEORIS_CHANGE Advanced_Search
	set div display to none to hide the box with the 'only product ids' check
-->
									<div class="search_option_box" style="display: none;">
<!-- NEORIS_CHANGE Advanced_Search
	replace dynamic formCheckbox with a regular checkbox with the checked attribute value set to checked
	this attribute is used to show the product ids panel
-->
<input type="checkbox" id="js-enable-product-ids" name="onlyProductIds" checked="checked" />
<%--  										<formUtil:formCheckbox idKey="js-enable-product-ids" labelKey="search.advanced.onlyproductids" path="onlyProductIds" inputCSS="advanced-onlyProductIds" labelCSS="" mandatory="false" /> --%>
									</div>
									<div class="search_text_box" style="margin-left:20px; padding-top: 10px; padding-left: 9.5px;">
										<formUtil:formInputBox idKey="js-product-ids" labelKey="search.advanced.keyword" path="keywords" inputCSS="advanced_search_text_field" mandatory="false" />
										<br>
										<button id="js-add-product-ids" class="button yellow positive" type="submit">
											<spring:theme code="search.advanced.productids.add" text="Enter" />
										</button>
										
										<button class="form adv_search_button button yellow positive" type="submit" onclick="sendGA();">
											<spring:theme code="search.advanced.search" text="Search" />
										</button>
									</div>

								</div>
							</div>
							<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
<!-- NEORIS_CHANGE Advanced_Search
	set div display to none to hide the box with radio buttons
	also fix the searchResultType param value to order-form
-->
								<div class="adv_search_result_area" style="display: none;">
									<input type="hidden" name="searchResultType" value="order-form"></input>

<%-- 									<formUtil:formRadioBox idKey="search-order-form" labelKey="search.advanced.orderform" value="order-form" path="searchResultType" /> --%>
<%-- 									<formUtil:formRadioBox idKey="search-create-order-form" labelKey="search.advanced.createorderform" value="create-order-form" path="searchResultType" /> --%>
<%-- 									<formUtil:formRadioBox idKey="search-catalog" labelKey="search.advanced.catalog" value="catalog" path="searchResultType" /> --%>
								</div>
							</sec:authorize>
							<c:set var="isCreateOrderForm" value="${form.createOrderForm}" />
							<c:if test="${empty isCreateOrderForm }">
								<c:set var="isCreateOrderForm" value="false" />
							</c:if>

							<input type="hidden" name="skus" id="skus" value=""/>
							<input type="hidden" name="isCreateOrderForm" id="isCreateOrderForm" value="${isCreateOrderForm}"/>
							
							    <%-- please leave the instock checkbox it will come in the next sprint, just hiding for now --%>
								<%-- formUtil:formCheckbox idKey="advanced-exact" labelKey="search.advanced.inventory" path="inStockOnly" inputCSS="" labelCSS="" mandatory="false" /> --%>

							
						</form:form>
					
					<div id="js-selected-product-ids" class="selected_product_ids"></div>
				
				<c:url value="/search" var="currentURL"/>
				<div id="currentPath" data-current-path="${currentURL}"></div>
			
<!-- NEORIS_CHANGE #38 -->
<%-- 				<c:if test="${advancedSearchForm.orderFormSearchResultType}"> --%>
<!-- 					<div class="span-8 last orderFormTotal"> -->
<%-- 						<product:productFormAddToCartPanel product="${product}" showViewDetails="false"/> --%>
<!-- 					</div> -->
<%-- 				</c:if> --%>
				
<br>
				<%-- <c:if test="${not empty advancedSearchForm.keywords}">
						<c:set var="sortQueryParams" value="searchResultType=${advancedSearchForm.searchResultType}&keywords=${advancedSearchForm.keywords}&onlyProductIds=${advancedSearchForm.onlyProductIds}&isCreateOrderForm=${advancedSearchForm.createOrderForm}" />

						<nav:pagination top="true"  supportShowPaged="false"
													supportShowAll="false"
													sortQueryParams="${sortQueryParams}"
													searchPageData="${searchPageData}"
													searchUrl="${searchPageData.currentQuery.url}?searchResultType=${advancedSearchForm.searchResultType}&keywords=${advancedSearchForm.keywords}&onlyProductIds=${advancedSearchForm.onlyProductIds}&isCreateOrderForm=${advancedSearchForm.createOrderForm}"
													numberPagesShown="${numberPagesShown}"
						/>


				</c:if> --%>

				<c:if test="${false}">
					<div class="span-4">
						<nav:facetNavAppliedFilters pageData="${searchPageData}"/>
						<nav:facetNavRefinements pageData="${searchPageData}"/>

						<cms:pageSlot position="Section5" var="feature">
							<cms:component component="${feature}" element="div" class="section5 cms_disp-img_slot"/>
						</cms:pageSlot>
					</div>
				</c:if>

				<c:set var="skuIndex" scope="session" value="0" />
				<spring:theme code="product.grid.confirmQtys.message" var="gridConfirmMessage" />

				<c:if test="${advancedSearchForm.catalogSearchResultType}">
					<div id="resultsList"  data-isOrderForm="false" data-isOnlyProductIds="${advancedSearchForm.onlyProductIds}">
						<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
							<product:productListerItem product="${product}" />
						</c:forEach>
					</div>
				</c:if>

				<c:if test="${advancedSearchForm.orderFormSearchResultType}">
					<c:if test="${not empty searchPageData.results }">
<%-- 					<grid:gridLegend /> --%>
					
<%--					<product:neorisAddItemsToCartButton /> --%>

					<form name="AddToCartOrderForm" id="AddToCartOrderForm" class="add_to_cart_order_form" action="<c:url value="/cart/addGrid"/>" method="post" data-grid-confirm-message="${gridConfirmMessage}">
						<div id="resultsList" class="product_table" data-isOrderForm="true" data-isOnlyProductIds="${advancedSearchForm.onlyProductIds}">
							<!-- NEORIS_CHANGE #38 -->
							<product:neorisProductList productList="${searchPageData.results}" />
						</div> 
					</form>
				<div>
				<%-- <c:if test="${not empty advancedSearchForm.keywords}">
						<c:set var="sortQueryParams" value="searchResultType=${advancedSearchForm.searchResultType}&keywords=${advancedSearchForm.keywords}&onlyProductIds=${advancedSearchForm.onlyProductIds}&isCreateOrderForm=${advancedSearchForm.createOrderForm}" />

						 <nav:paginationReportFooter top="false"  supportShowPaged="false"
													supportShowAll="false"
													sortQueryParams="${sortQueryParams}"
													searchPageData="${searchPageData}"
													searchUrl="${searchPageData.currentQuery.url}?searchResultType=${advancedSearchForm.searchResultType}&keywords=${advancedSearchForm.keywords}&onlyProductIds=${advancedSearchForm.onlyProductIds}&isCreateOrderForm=${advancedSearchForm.createOrderForm}"
													numberPagesShown="${numberPagesShown}"
						/> 
						
				
				</c:if> --%>

					<product:neorisAddItemsToCartButton />
				</div>	
					<div id="skuIndexSavedValue" name="skuIndexSavedValue" data-sku-index="${sessionScope.skuIndex}"><!--  don't remove this div. This is used by the order form search --></div>
					</c:if>
				</c:if>
				<c:if test="${advancedSearchForm.createOrderFormSearchResultType}">
					<form name="createOrderForm" id="createOrderForm" class="create-order-form" data-grid-confirm-message="${gridConfirmMessage}">
						<input id="js-create-order-form-button" type="button" value="<spring:theme code='search.advanced.createorderform' />"> 

						<div id="resultsList"  data-isOrderForm="false" data-isOnlyProductIds="false">
							<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
								<product:productFilterOrderForm product="${product}" />
							</c:forEach>
						</div>
					</form>
				</c:if>

<!-- NEORIS_CHANGE #38 -->
<%-- 				<common:infiniteScroll/> --%>
				
				<script id="product-id-tag-box-template" type="text/x-jquery-tmpl">
				<span class="product-id-tag-box" id="product-id-\${productId}-tag">
				  <span>\${productId}</span>
				  <button class="js-remove-product-id form" type="submit">Remove</button>
				</span>
				</script>

				<product:productOrderFormJQueryTemplates />
				
				<cms:pageSlot position="BottomContent" var="comp" element="div" class="span-20 cms_disp-img_slot right last">
					<cms:component component="${comp}" />
				</cms:pageSlot>
			
		
		<input id="searchByKeywordLabel" type="hidden" value='<spring:theme code="search.advanced.keyword"/>' />
		<input id="searchByIdsLabel" type="hidden" value='<spring:theme code="search.advanced.productids"/>' />
		<c:remove var="skuIndex" scope="session" />
		
		<!--NEORIS_CAHNGE #55 -->
		<!--Shows the not found SKU of the search  -->
		<c:if test="${numberSKU != 0 and not empty numberSKU}">
			<span style="color:red; font-weight:bold">
				${numberSKU}&nbsp;<spring:theme code="SKUs_not_found" />&nbsp;${listNotFound}
			</span>
		</c:if>

		</div>
				</div>
				</div>
				<br>
	</jsp:body>
	
</template:page>
