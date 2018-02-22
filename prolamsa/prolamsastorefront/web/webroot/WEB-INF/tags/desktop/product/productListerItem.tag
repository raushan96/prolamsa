<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="isOrderForm" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>

<div class="prod_list">
	<ycommerce:testId code="test_searchPage_wholeProduct">
		<product:productListerItemDetails product="${product}" />
		<div class="cart">
<!-- NEORIS_CHANGE Advanced_Search
Do not show the price
-->
<%-- 			<product:productListerItemPrice product="${product}" /> --%>
			<ycommerce:testId code="searchPage_addToCart_button_${product.code}">

			<c:choose>
				<%-- Verify if products is a multidimensional product --%>
				<c:when test="${product.multidimensional}">
					<div class="span-4 viewDetailButton">
						<c:url value="${productUrl}" var="backToProductUrl"/>
						<a href="${backToProductUrl}" ><spring:theme code="product.view.details" /></a>
					</div>
					
					<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
						<div class="span-4">
							<c:url value="${product.url}/orderForm" var="productOrderFormUrl"/>
							<a href="${productOrderFormUrl}" ><spring:theme code="order.form" /></a>
						</div>
					</sec:authorize>

				</c:when>
				<c:otherwise>
					<c:set var="buttonType">submit</c:set>
					<%--NEORIS_CHANGE --%>
					<%--Se quita validacion del precio debido a que actualmente no se maneja con solr --%>
					<%-- <c:if test="${product.stock.stockLevelStatus.code eq 'outOfStock' or empty product.price}"> --%>
					<c:if test="${product.stock.stockLevelStatus.code eq 'outOfStock'}">
						<c:set var="buttonType">button</c:set>
						<spring:theme code="text.addToCart.outOfStock" var="addToCartText"/>
					</c:if>
					<c:if test="${empty isOrderForm || not isOrderForm}">
						<form id="addToCartForm${product.code}" action="<c:url value="/cart/add"/>" method="post" class="add_to_cart_form">
							<input type="hidden" name="productCodePost" value="${product.code}"/>
							<button type="${buttonType}" disabled="true" class="add_to_cart_button positive large <c:if test="${fn:contains(buttonType, 'button')}">out-of-stock</c:if>" <c:if test="${fn:contains(buttonType, 'button')}">disabled="true" aria-disabled="true"</c:if>>
								<theme:image code="img.addToCartIcon" alt="${addToCartText}" title="${addToCartText}"/>
								${addToCartText}
							</button>
						</form>
					</c:if>
					<c:if test="${not empty isOrderForm && isOrderForm}">
						<label for="qty"><spring:theme code="basket.page.quantity" /></label>
						<input type=hidden id="productPrice[${sessionScope.skuIndex}]" value="${product.price.value}" />
						<input type="hidden" class="${product.code} sku"  name="productQuantities[${sessionScope.skuIndex}].sku" id="productQuantities[${sessionScope.skuIndex}].sku" value="${product.code}" />
						<input type="text" maxlength="3"  size="1" id="productQuantities[${sessionScope.skuIndex}].quantity" name="productQuantities[${sessionScope.skuIndex}].quantity" class="sku-quantity" value="0">

						<c:set var="skuIndex" scope="session" value="${sessionScope.skuIndex + 1}"/>
					</c:if>
				</c:otherwise>
			</c:choose>

<!-- NEORIS_CHANGE Advanced_Search
Include a false to hide the productFutureAvailability
-->
			<c:if test="${false and isOrderForm and !product.multidimensional}">
				<span class="prod prod_results">
					<product:productFutureAvailability product="${product}" futureStockEnabled="${futureStockEnabled}" />
				</span>
			</c:if>
			
			</ycommerce:testId>
		</div>
	</ycommerce:testId>
</div>
