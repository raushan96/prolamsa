<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>

<sec:authorize ifAnyGranted="ROLE_PURCHASER">
<div class="add_items_to_cart_div" style="float: right;">
	<button id="addItemsToCartButton" type="${buttonType}" class="add_to_cart_button positive large" onclick="ga('send', 'event', 'Cart', 'addItemToCart', 'Add item to cart button')">
		<span class="icon-addtocart-black"></span>
		<c:choose>
			<c:when test="${baseStore.uid eq '2000'}">
				<spring:theme code="cart.button.add-items-to-cart.2000" /></c:when>
			<c:otherwise>
				<spring:theme code="cart.button.add-items-to-cart" /></c:otherwise>
		</c:choose>
	</button>
</div>
</sec:authorize>
