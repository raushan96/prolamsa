<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="skipSummary" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:url value="${product.url}" var="productUrl"/>

<a href="${productUrl}" title="<c:out value='${product.name}' />" class="productMainLink">
<!-- NEORIS_CHANGE Advanced_Search -->
<!-- 	<div class="thumb"> -->
<%-- 		<c:if test="${not empty product.averageRating}"> --%>
<!-- 				<span class="stars large" style="display: inherit;"> -->
<%-- 				<span style="width: <fmt:formatNumber maxFractionDigits="0" value="${product.averageRating * 24}" />px;"></span> --%>
<!-- 			</span> -->
<%-- 		</c:if> --%>

<%-- 		<product:productPrimaryImage product="${product}" format="thumbnail"/> --%>
<%-- 		<c:if test="${not empty product.potentialPromotions and not empty product.potentialPromotions[0].productBanner}"> --%>
<%-- 			<img class="promo" src="${product.potentialPromotions[0].productBanner.url}" alt="${product.potentialPromotions[0].description}" title="${product.potentialPromotions[0].description}"/> --%>
<%-- 		</c:if> --%>
<!-- 	</div> -->

	<div class="details">
		<ycommerce:testId code="searchPage_productName_link_${product.code}">
			<h2><c:out value='${product.name}' /></h2>
		</ycommerce:testId>

		<c:if test="${not empty product.summary && not skipSummary}">
			<p><c:out value='${product.summary}' /></p>
		</c:if>

		<product:productListerClassifications product="${product}"/>
	</div>
</a>