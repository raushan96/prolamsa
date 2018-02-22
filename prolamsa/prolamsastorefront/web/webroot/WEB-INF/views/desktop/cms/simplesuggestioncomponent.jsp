<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component" %>

<c:choose>
	<c:when test="${not empty suggestions and component.maximumNumberProducts > 0}">
		<div class="scroller">
			<div class="title_holder">
				<h2>${component.title}</h2>
			</div>
			<ul class="carousel jcarousel-skin">
				<c:forEach end="${component.maximumNumberProducts}" items="${suggestions}" var="suggestion">
					<c:url value="${suggestion.url}/quickView" var="productQuickViewUrl"/>
					<li>
						<span>
							<a href="${productQuickViewUrl}" class="popup">
								<product:productPrimaryImage product="${suggestion}" format="thumbnail"/>
							</a>
						</span>
						<c:if test="${component.displayProductTitles}">
							<h3><a href="${productQuickViewUrl}" class="popup">${suggestion.name}</a></h3>
						</c:if>
						<c:if test="${component.displayProductPrices}">
							<p>
								<a href="${productQuickViewUrl}" class="popup">
									<format:fromPrice priceData="${suggestion.price}"/>
								</a>
							</p>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:when>

	<c:otherwise>
		<component:emptyComponent/>
	</c:otherwise>
</c:choose>
