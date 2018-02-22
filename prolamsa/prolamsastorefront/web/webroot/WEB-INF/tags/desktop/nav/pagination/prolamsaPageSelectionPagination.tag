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

	<c:set var="hasPreviousPage" value="${(searchPageData.pagination.currentPage - 5) > 0}"/>
	<c:set var="hasNextPage" value="${(searchPageData.pagination.currentPage + 5) < searchPageData.pagination.numberOfPages}"/>
	
	<c:set var="beginPage" value="${searchPageData.pagination.currentPage - 5}" />
	<c:choose>
		<c:when test="${beginPage < 0}">
			<c:set var="beginPage" value="0" />
		</c:when>
		<c:otherwise>
			<c:set var="beginPage" value="${searchPageData.pagination.currentPage - 5}" />
		</c:otherwise>
	</c:choose>
	
	<c:set var="endPage" value="${searchPageData.pagination.currentPage + 5}" />
	<c:choose>
		<c:when test="${endPage > searchPageData.pagination.numberOfPages - 1}">
			<c:set var="endPage" value="${searchPageData.pagination.numberOfPages - 1}" />
		</c:when>
		<c:otherwise>
			<c:set var="endPage" value="${searchPageData.pagination.currentPage + 5}" />
		</c:otherwise>
	</c:choose>

	
	<c:set var="previousPage" value="${searchPageData.pagination.currentPage - 1}" />
	<c:if test="${previousPage < 0}">
		<c:set var="previousPage" value="0" />
	</c:if>

	<c:set var="nextPage" value="${searchPageData.pagination.currentPage + 1}" />
	<c:if test="${nextPage + 1 > searchPageData.pagination.numberOfPages}">
		<c:set var="nextPage" value="${searchPageData.pagination.numberOfPages - 1}" />
	</c:if>
	
	<li style="font-size:.8em;">
		<spring:url value="${searchUrl}" var="previousPageUrl" htmlEscape="true">
			<spring:param name="page" value="${previousPage}"/>
		</spring:url>
		<c:if test="${hasPreviousPage}">
			<a href="${previousPageUrl}" rel="prev">
				<span class="icon-arrow-left"></span>
			</a>
		</c:if>
	</li>
	<li>
	<!-- Christian Loredo 04122015 Muestra las paginas para navegar por ellas. -->
	<c:forEach begin="${beginPage}" end="${endPage}" step="1" var="pagesCLS" varStatus="status">

		<c:url var="url" value="${searchUrl}"  >
			<c:param name="page" value="${pagesCLS}" />
		</c:url>

		<c:choose>
			<c:when test="${searchPageData.pagination.currentPage eq pagesCLS}">
				<a href="${url}" style="color:#f1ad1d" >${pagesCLS + 1}</a>
			</c:when>
			<c:otherwise>
				<a href="${url}" >${pagesCLS + 1}</a>
			</c:otherwise>
		</c:choose>

	</c:forEach>
	</li>
	<li style="font-size:.8em;">
		<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
			<spring:param name="page" value="${nextPage}"/>
		</spring:url>
		<c:if test="${hasNextPage}">
			<a href="${nextPageUrl}" rel="next">
				<span class="icon-arrow-right"></span>
			</a>
		</c:if>
	</li>
	<%-- <li style="font-size: .7em">
		-${searchPageData.pagination.numberOfPages}
	</li> --%>

	<%--
	<c:set var="hasPreviousPage" value="${searchPageData.pagination.currentPage > 0}"/>
	<c:set var="hasNextPage" value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}"/>
	
	<c:set var="previousPage" value="${searchPageData.pagination.currentPage - 1}" />
	<c:if test="${previousPage < 0}">
		<c:set var="previousPage" value="0" />
	</c:if>

	<c:set var="nextPage" value="${searchPageData.pagination.currentPage + 1}" />
	<c:if test="${nextPage + 1 > searchPageData.pagination.numberOfPages}">
		<c:set var="nextPage" value="${searchPageData.pagination.numberOfPages - 1}" />
	</c:if>

	<li style="font-size:.8em;">
		<spring:url value="${searchUrl}" var="previousPageUrl" htmlEscape="true">
			<spring:param name="page" value="${previousPage}"/>
		</spring:url>
		<c:if test="${hasPreviousPage}">
			<a href="${previousPageUrl}" rel="prev">
				<span class="icon-arrow-left"></span>
			</a>
		</c:if>
	</li>
	<li style="font-size:.8em;">
		<spring:theme code="Page" />&nbsp;
		${searchPageData.pagination.currentPage + 1}&nbsp;
		<spring:theme code="of" />&nbsp;
		${searchPageData.pagination.numberOfPages}
	</li>
	<li style="font-size:.8em;">
		<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
			<spring:param name="page" value="${nextPage}"/>
		</spring:url>
		<c:if test="${hasNextPage}">
			<a href="${nextPageUrl}" rel="next">
				<span class="icon-arrow-right"></span>
			</a>
		</c:if>
	</li>
	--%>
</ul>