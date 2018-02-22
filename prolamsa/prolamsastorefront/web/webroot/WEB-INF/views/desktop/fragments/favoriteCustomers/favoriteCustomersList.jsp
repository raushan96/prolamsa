<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>


<c:set var="favoriteCustomersBaseUrl" value="/my-account/favorite-customers" />
<spring:url var="favoriteCustomersSearchUrl" value="${favoriteCustomersBaseUrl}" />

<div id="globalMessages" class="globalMessagesFragment">
	<common:globalMessages/>
</div>
	
<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<div class="prod_refine-wrapper top-border-separator-line">
		<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"							
			searchUrl="${favoriteCustomersBaseUrl}?sort=${b2bFavoriteUnitsForm.sort}"	
			msgKey="text.account.favoriteCustomers.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
	</div>
</c:if>
<br/><br/>

<div class="item_container">
	<table>
		<thead>
			<tr class="firstrow">
                <th class="col_center"><h3><spring:theme code="text.account.favoriteCustomers.column.id" text="Id"/></h3></th>
				<th><h3><spring:theme code="text.account.favoriteCustomers.column.name" text="Name"/></h3></th>	
				<th class="col_center"><h3><spring:theme code="text.account.favoriteCustomers.column.actions" text="Actions"/></h3></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty searchPageData.results}">
					<tr>
						<td colspan="5">
							<spring:theme code="text.account.favoriteCustomers.search.noFavorites" text="No favorites assigned"/>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${searchPageData.results}" var="unit">
						<tr>
							<td align="center">
									${unit.uid}
							</td>
							<td align="left">
									${unit.name}
							</td>
							<td align="center">
							<c:choose>
								<c:when test="${currentRootUnitUID == unit.uid}">
									<spring:theme code="text.account.favoriteCustomers.currentlySelected" text="Currently Selected"/>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" onclick="removeFromFavorites('${unit.uid}')"><spring:theme code="text.account.favoriteCustomers.delete" text="Remove"/></a>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	<form:form id="favoriteUnitsForm" class="js-form" action="${favoriteCustomersSearchUrl}" method="get">
		<input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
</div>


<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<div class="prod_refine-wrapper top-border-separator-line">
		<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"							
			searchUrl="${favoriteCustomersBaseUrl}"	
			msgKey="text.account.favoriteCustomers.page"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
	</div>
</c:if>

<br/><br/>