<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData" %>
<%@ attribute name="removeQueryBaseUrl" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<c:if test="${not empty pageData.breadcrumbs}">
	<div class="nav_column">
		<div class="title_holder">
			<div class="title">
				<div class="title-top">
					<span></span>
				</div>
			</div>
			<h2><spring:theme code="search.nav.appliedFilters"/></h2>
		</div>
		<div class="item">
			<ul class="facet_block">
				<c:forEach items="${pageData.breadcrumbs}" var="breadcrumb">
					<li>
						<c:url value="${breadcrumb.removeQuery.url}" var="removeQueryUrl"/>
						<c:url value="${removeQueryBaseUrl}" var="removeQueryUrl">
  							<c:param name="q" value="${breadcrumb.removeQuery.query.value}" />
						</c:url>						
						<a href="${removeQueryUrl}">
							<spring:theme code="${breadcrumb.facetValueName}" />
						</a>
						<a href="${removeQueryUrl}" style="float: right;">
							<spring:theme code="search.nav.removeAttribute" var="removeFacetAttributeText" />
							&nbsp;<theme:image code="img.iconSearchFacetDelete" title="${removeFacetAttributeText}" alt="${removeFacetAttributeText}" />
						</a> 
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>