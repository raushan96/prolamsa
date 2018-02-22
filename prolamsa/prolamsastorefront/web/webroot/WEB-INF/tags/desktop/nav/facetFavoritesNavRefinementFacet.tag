<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${not empty facetData.values}">
	<div class="item" id="${facetData.name}">
		<div class="category">
			<spring:theme code="text.hideFacet" var="hideFacetText"/>
			<spring:theme code="text.showFacet" var="showFacetText"/>
			<a title="Hide Refinement" class="refinementToggle" href="#" data-hide-facet-text="${hideFacetText}" data-show-facet-text="${showFacetText}">
				<h4 style="color: black;"><spring:theme code="search.nav.facetTitle" arguments="${facetData.name}"/></h4>
			</a>
			<a class="collapsableArrow" href="#">
				<span class="dropdown">
					<span class="dropdown-img"></span>
				</span>
			</a>
		</div>

		<ycommerce:testId code="facetNav_facet${facetData.name}_links">
			<div class="facetValues">
				<c:if test="${not empty facetData.topValues}">
					<div class="topFacetValues">
						<ul class="facet_block ${facetData.multiSelect ? '' : 'indent'}">
							<c:forEach items="${facetData.topValues}" var="facetValue">
								<li>
									<c:if test="${facetData.multiSelect}">
										<form action="#" method="get">
											<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
											<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
											<label class="facet_block-label">
												
												
												<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="$(this).closest('form').submit()"/>
<%--												${facetValue.name} --%>
													<spring:theme code="${facetValue.name}" />
											</label>
											<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/>
										</form>
									</c:if>
									<c:if test="${not facetData.multiSelect}">
										<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
										<a href="${facetValueQueryUrl}">${facetValue.name}</a>
										<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/>
									</c:if>
								</li>
							</c:forEach>
						</ul>
						<span class="more">
							<a href="#" onclick="$(this).closest('div.topFacetValues').hide().siblings('div.allFacetValues').show();return false;"><spring:theme code="search.nav.facetShowMore_${facetData.code}"/></a>
						</span>
					</div>
				</c:if>
				<div class="allFacetValues" style="${not empty facetData.topValues ? 'display:none' : ''}">
					<ul class="facet_block ${facetData.multiSelect ? '' : 'indent'}">
						<c:forEach items="${facetData.values}" var="facetValue">
							<li>
								<c:if test="${facetData.multiSelect}">
									<form action="#" method="get">
										<input type="hidden" id="q" name="q" value="${facetValue.query.query.value}"/>
										<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
										<label class="facet_block-label">
											<input id="${facetValue.name}" type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="$(this).closest('form').submit()"/>
											<spring:theme code="${facetValue.name}" />
										</label>
										<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/>
									</form>
								</c:if>
								<c:if test="${not facetData.multiSelect}">
									<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
									<a href="${facetValueQueryUrl}&text=${searchPageData.freeTextSearch}">
<%-- 									${facetValue.name} --%>
										<spring:theme code="${facetValue.name}" />
									</a>
									<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/>
								</c:if>
							</li>
						</c:forEach>
					</ul>
					<c:if test="${not empty facetData.topValues}">
						<span class="more">
							<a href="#" onclick="$(this).closest('div.allFacetValues').hide().siblings('div.topFacetValues').show();return false;"><spring:theme code="search.nav.facetShowLess_${facetData.code}"/></a>
						</span>
					</c:if>
				</div>
			</div>
		</ycommerce:testId>
	</div>
</c:if>
