<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="categories" required="true" type="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<div class="nav_column">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>

		<c:choose>
				<c:when test="${baseStore.uid eq '2000'}">
					<h2><spring:theme code="search.nav.refinements.2000"/></h2>
				</c:when>
				<c:otherwise>
					<h2><spring:theme code="search.nav.refinements"/></h2>
				</c:otherwise>
			</c:choose>
	</div>

	<div class="item">
		<div class="category">

			<a href="#" onclick="return false;">
				<spring:theme code="search.nav.categoryNav"/>
			</a>
		</div>

		<ycommerce:testId code="categoryNav_category_links">
			<div class="facetValues">
				<div class="allFacetValues">
					<ul class="facet_block indent">
						<c:forEach items="${categories}" var="category">
							<li>
								<c:url value="${category.url}" var="categoryUrl"/>
								<a href="${categoryUrl}">${category.name}</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</ycommerce:testId>
	</div>
</div>
