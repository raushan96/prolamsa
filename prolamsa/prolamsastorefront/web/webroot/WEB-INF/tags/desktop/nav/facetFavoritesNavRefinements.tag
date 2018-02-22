<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<script type="text/javascript">
	onDOMLoaded(hideFamiliy);
	
	function hideFamiliy()
	{
		<c:if test="${baseStore.name eq 'Prolamsa Mex'}">
			if ( $("#Family").length )
			{
				$("#Family").css("display", "none");
			}
			if ( $("#Familia").length )
			{
				$("#Familia").css("display", "none");
			}
		</c:if>
		
		//31Oct2016 CILS Esconder facet locaciones
		<c:if test="${baseStore.allowCategoryVisibility}">
			if ( $("#ShippingPoint").length )
			{
				$("#ShippingPoint").css("display", "none");
			}
			if ( $("#Locaciones").length )
			{
				$("#Locaciones").css("display", "none");
			}
		</c:if>
	}
</script>

<c:if test="${not empty pageData.facets}">
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

	<c:forEach items="${pageData.facets}" var="facet">
		<c:choose>
			<c:when test="${facet.code eq 'availableInStores'}">
				<nav:facetFavoritesNavRefinementStoresFacet facetData="${facet}" userLocation="${userLocation}"/>
			</c:when>
			<c:otherwise>
				<nav:facetFavoritesNavRefinementFacet facetData="${facet}"/> 
			</c:otherwise>
		</c:choose>
	</c:forEach>
</div>
</c:if>
