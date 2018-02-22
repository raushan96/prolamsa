<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<c:set var="favoriteProductsManageBaseUrl" value="/favorite-products/manage" />
<spring:url var="favoriteProductsManageSearchUrl" value="${favoriteProductsManageBaseUrl}" />

<div id="globalMessages" class="globalMessagesFragment">
	<common:globalMessages/>
</div>

<br>

<c:choose>
				<c:when test="${baseStore.uid eq '2000'}">
					<div class="search-form-options">
	<formUtil:formButton
		id="loadFavoriteProducts"
		name="loadFavoriteProducts"
		type="button" 
		css="positive button-float-right"
		tabindex="102"
		springThemeText="customerFavoriteProducts.button.reset.2000" />
</div>
				</c:when>
				<c:otherwise>
					<div class="search-form-options">
	<formUtil:formButton
		id="loadFavoriteProducts"
		name="loadFavoriteProducts"
		type="button" 
		css="positive button-float-right"
		tabindex="102"
		springThemeText="customerFavoriteProducts.button.reset" />
</div>
				</c:otherwise>
			</c:choose>

<br><br>

<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<div class="prod_refine-wrapper top-border-separator-line">
		<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"							
			searchUrl="${favoriteProductsManageBaseUrl}?sort=${favoriteProductSearchForm.sort}"	
			msgKey="text.account.customerFavoriteProducts.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
	</div>
</c:if>

<br/><br/>

<div class="item_container">
	<table>
		<thead>
			<tr class="firstrow">
                <th class="col_center"><h3><spring:theme code="text.account.customerFavoriteProducts.column.part" text="Part #"/></h3></th>
				<th><h3><spring:theme code="text.account.customerFavoriteProducts.column.description" text="Description"/></h3></th>
<%-- 				<th><h3><spring:theme code="text.account.customerFavoriteProducts.column.customerDescription" text="Customer Description"/></h3></th>		 --%>
				<th><h3><spring:theme code="text.account.customerFavoriteProducts.column.location" text="Location"/></h3></th>	
				<th class="col_center"><h3><spring:theme code="text.account.customerFavoriteProducts.column.actions" text="Actions"/></h3></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty searchPageData.results}">
					<tr>
						<td colspan="5">
							<spring:theme code="text.account.customerFavoriteProducts.search.noFavorites" text="No favorites assigned"/>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${searchPageData.results}" var="product">
					<c:set var="inventoryEntry" value="${product.inventoryEntry}" />
						<tr>
							<td align="center">
									${product.baseCode}
							</td>
							<td align="left">
									${product.commercialDescription}
							</td>
<!-- 							<td align="left"> -->
<%-- 									${product.customerDescription} --%>
<!-- 							</td> -->
							<td>
								<spring:theme code="${product.location.code}" />
							</td>
							<td align="center">
									<a href="javascript:void(0);" onclick="removeProductFromFavorites('${product.code}')"><spring:theme code="text.account.customerFavoriteProducts.remove" text="Remove"/></a>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	<form:form id="favoriteProductsForm" class="js-form" action="${favoriteProductsManageSearchUrl}" method="get">
		<input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
</div>

<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<div class="prod_refine-wrapper top-border-separator-line">
		<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"							
			searchUrl="${favoriteProductsManageBaseUrl}?sort=${favoriteProductSearchForm.sort}"	
			msgKey="text.account.customerFavoriteProducts.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
	</div>
</c:if>

<br/><br/>