<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="showViewDetails" required="false" type="java.lang.Boolean"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>

<c:url value="/${product.url}" var="productUrl"/>

<div class="span-8">
	<h4 class="span-8 orderFormTotalHeader"><spring:theme code="order.form.total"/></h4>

	<div class="span-8">
		<div class="span-4 total-items-count">(<span id="total-items-count">0</span> <spring:theme code="product.grid.itemsText"/>)</div>
		<div class="span-4 last" id="total-price">0</div>
		<input type="hidden" id="total-price-value" value="0">
	</div>

	<div class="span-8">
		<div class="span-4 viewDetailButton">
			<c:if test="${showViewDetails != false}">
				<c:url value="${productUrl}" var="backToProductUrl"/>
				<a href="${backToProductUrl}" ><spring:theme code="product.view.details" /></a>
			</c:if>
		</div>

		<div class="span-4 last prod_add_to_cart">
			<spring:theme code="text.addToCart" var="addToCartText"/>
			<button  id="addToCartBtn" type="button" class="positive large">
				<spring:theme code="text.addToCart" var="addToCartText"/>
				<spring:theme code="basket.add.to.basket" />
			</button>
		</div>
	</div>

</div>
