<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="numberPagesShown" required="true" type="java.lang.Integer" %>
<%@ attribute name="themeMsgKey" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/mobile/form"%>


<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

 <ul class="pager">
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
	
	<br>
	
	<li style="font-size:10px; width: 35%; float: left;">
		<spring:theme code="Page" />&nbsp;
		${searchPageData.pagination.currentPage + 1}&nbsp;
		<spring:theme code="of" />&nbsp;
		${searchPageData.pagination.numberOfPages}
	</li>
	
	<li style="font-size:1.2em;  float: right; padding: 0; font-weight: bolder;">
		<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
			<spring:param name="page" value="${nextPage}"/>
		</spring:url>
		<c:if test="${hasNextPage}">
			<a href="${nextPageUrl}" rel="next">
				<!-- <span class="icon-arrow-right"></span> -->
				<formUtil:formButtonNextMobile
					id="nextSubmit"
					name="nextSubmit"
					type="submit" 
					css="button button yellow positive button-float-right2"
					tabindex="106"
					springThemeText="mobile.pagination.next" />
					
			</a>
		</c:if>
	</li>
	
	<li style="font-size:1.2em; float: right;  padding: 0; font-weight: bolder;">
		<spring:url value="${searchUrl}" var="previousPageUrl" htmlEscape="true">
			<spring:param name="page" value="${previousPage}"/>
		</spring:url>
		<c:if test="${hasPreviousPage}">
			<a href="${previousPageUrl}" rel="prev">
				<!-- <span class="icon-arrow-left"></span> -->
				<formUtil:formButtonPrevMobile
					id="prevSubmit"
					name="prevSubmit"
					type="submit" 
					css="button button yellow positive button-float-right2"
					tabindex="106"
					springThemeText="mobile.pagination.prev" />
			</a>
		</c:if>
	</li>
	
	

<%-- 	<li style="font-size:.8em; width: 25%;">
		<spring:url value="${searchUrl}" var="previousPageUrl" htmlEscape="true">
			<spring:param name="page" value="${previousPage}"/>
		</spring:url>
		<c:if test="${hasPreviousPage}">
			<a href="${previousPageUrl}" rel="prev">
				<!-- <span class="icon-arrow-left"></span> -->
				<formUtil:formButton
					id="orderSearchSubmit"
					name="orderSearchSubmit"
					type="submit" 
					css="positive button-float-right"
					tabindex="106"
					springThemeText="mobile.pagination.prev" />
			</a>
		</c:if>
	</li>
	
	<li style="font-size:.8em; width: 25%;">
		<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
			<spring:param name="page" value="${nextPage}"/>
		</spring:url>
		<c:if test="${hasNextPage}">
			<a href="${nextPageUrl}" rel="next">
				<!-- <span class="icon-arrow-right"></span> -->
				
				<formUtil:formButton
					id="orderSearchSubmit"
					name="orderSearchSubmit"
					type="submit" 
					css="positive button-float-right"
					tabindex="106"
					springThemeText="mobile.pagination.next" />
					
			</a>
		</c:if>
	</li> --%>
</ul>