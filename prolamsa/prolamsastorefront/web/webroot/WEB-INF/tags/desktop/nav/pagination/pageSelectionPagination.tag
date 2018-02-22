<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="numberPagesShown" required="true" type="java.lang.Integer" %>
<%@ attribute name="themeMsgKey" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>


<ul class="pager">
	<c:set var="hasPreviousPage" value="${searchPageData.pagination.currentPage > 0}"/>
	<c:set var="hasNextPage" value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}"/>
	<li>
		<c:if test="${hasPreviousPage}">
			<spring:url value="${searchUrl}" var="firstPageUrl" htmlEscape="true">
				<spring:param name="page" value="0"/>
			</spring:url>
			<ycommerce:testId code="firstPage_link">
				<a href="${firstPageUrl}"><spring:theme code="${themeMsgKey}.firstPage"/></a>
			</ycommerce:testId>
		</c:if>
	</li>
	<li>
		<c:if test="${hasPreviousPage}">
			<spring:url value="${searchUrl}" var="previousPageUrl" htmlEscape="true">
				<spring:param name="page" value="${searchPageData.pagination.currentPage - 1}"/>
			</spring:url>
			<ycommerce:testId code="searchResults_previousPage_link">
				<a href="${previousPageUrl}" rel="prev"><spring:theme code="${themeMsgKey}.linkPreviousPage"/></a>
			</ycommerce:testId>
		</c:if>
		<c:if test="${not hasPreviousPage}">
			<a href="#" class="hidden" onclick="return false">
				<spring:theme code="${themeMsgKey}.linkPreviousPage"/>
			</a>
		</c:if>
	</li>
	<li>
		<c:set var="limit" value="${numberPagesShown}"/>
		<c:set var="halfLimit"><fmt:formatNumber value="${limit/2}" maxFractionDigits="0"/></c:set>
		<c:set var="beginPage">
			<c:choose>
				<%-- Limit is higher than number of pages --%>
				<c:when test="${limit gt searchPageData.pagination.numberOfPages}">1</c:when>
				<%-- Start shifting page numbers once currentPage reaches halfway point--%>
				<c:when test="${searchPageData.pagination.currentPage + halfLimit ge limit}">
					<c:choose>
						<c:when test="${searchPageData.pagination.currentPage + halfLimit lt searchPageData.pagination.numberOfPages}">
							${searchPageData.pagination.currentPage + 1 - halfLimit}
						</c:when>
						<c:otherwise>${searchPageData.pagination.numberOfPages + 1 - limit}</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>1</c:otherwise>
			</c:choose>
		</c:set>
		<c:set var="endPage">
			<c:choose>
				<c:when test="${limit gt searchPageData.pagination.numberOfPages}">
					${searchPageData.pagination.numberOfPages}
				</c:when>
				<c:when test="${hasNextPage}">
					${beginPage + limit - 1}
				</c:when>
				<c:otherwise>
					${searchPageData.pagination.numberOfPages}
				</c:otherwise>
			</c:choose>
		</c:set>
		<c:forEach begin="${beginPage}" end="${endPage}" var="pageNumber">
			<c:choose>
				<c:when test="${searchPageData.pagination.currentPage + 1 ne pageNumber}">
					<spring:url value="${searchUrl}" var="pageNumberUrl" htmlEscape="true">
						<spring:param name="page" value="${pageNumber - 1}"/>
					</spring:url>
					<ycommerce:testId code="pageNumber_link">
						<a href="${pageNumberUrl}">${pageNumber}</a>
					</ycommerce:testId>
				</c:when>
				<c:otherwise>
					<%-- TODO: pageNumber needs to be aligned correctly with the other page numbers and bolded to indicate that it is selected--%>
					<a href="#" style="text-decoration: none;">${pageNumber}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</li>
	<li>
		<c:if test="${hasNextPage}">
			<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
				<spring:param name="page" value="${searchPageData.pagination.currentPage + 1}"/>
			</spring:url>
			<ycommerce:testId code="searchResults_nextPage_link">
				<a href="${nextPageUrl}" rel="next">
					<spring:theme code="${themeMsgKey}.linkNextPage"/>
				</a>
			</ycommerce:testId>
		</c:if>
		<c:if test="${not hasNextPage}">
			<a href="#" class="hidden" onclick="return false">
				<spring:theme code="${themeMsgKey}.linkNextPage"/>
			</a>
		</c:if>
	</li>
	<li>
		<c:if test="${hasNextPage}">
			<spring:url value="${searchUrl}" var="lastPageUrl" htmlEscape="true">
				<spring:param name="page" value="${searchPageData.pagination.numberOfPages - 1}"/>
			</spring:url>
			<ycommerce:testId code="lastPage_link">
				<a href="${lastPageUrl}" rel="prev"><spring:theme code="${themeMsgKey}.lastPage"/></a>
			</ycommerce:testId>
		</c:if>
	</li>
</ul>