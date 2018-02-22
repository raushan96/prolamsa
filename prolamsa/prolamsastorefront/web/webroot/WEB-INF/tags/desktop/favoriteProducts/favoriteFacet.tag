<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

	<%-- Adding favorite facet --%>
				
				<div class="item" id="favoriteFacet">
					<div class="category">
						<spring:theme code="text.hideFacet" var="hideFacetText"/>
						<spring:theme code="text.showFacet" var="showFacetText"/>
						<a title="Hide Refinement" class="refinementToggle" href="#" data-hide-facet-text="${hideFacetText}" data-show-facet-text="${showFacetText}">
							<spring:theme code="search.nav.facet.favorites" var="favoritesLabel"/>
							<h4 style="color: black;"><spring:theme code="search.nav.facetTitle" arguments="${favoritesLabel}"/></h4>
						</a>
						<a class="collapsableArrow" href="#">
							<span class="dropdown">
								<span class="dropdown-img"></span>
							</span>
						</a>
					</div>
			
					<ycommerce:testId code="facetNav_facetFavorite}_links">
						<div class="facetValues">
							<div class="allFacetValues" >
								<ul class="facet_block">
										<li>
													<label class="facet_block-label">
														<input id="favoritesInput" type="checkbox" />
														<spring:theme code="search.nav.facet.favorites" />
													</label>
													<spring:theme code="search.nav.facetValueCount" arguments="${favoriteProductsCount }"/>
											
										</li>
								</ul>
							</div>
						</div>
					</ycommerce:testId>
				</div>

<script type="text/javascript">

onDOMLoaded(bindFavoriteProductSearch);

function bindFavoriteProductSearch()
{
	$('#favoritesInput').attr('checked', false);
	
	jQuery("#favoritesInput").on("click",function(){
		window.location = "<c:url value='/favorite-products/list'/>";
	});
}


</script>