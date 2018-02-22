<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>



<div class="prod_image_main" id="primary_image">
		<c:if test="${fn:contains(product.url, '?sku=')}">
			<c:url value="${fn:substringBefore(product.url, '?sku=')}/zoomImages" var="productZoomImagesUrl"/>
		</c:if>
		<c:if test="${not fn:contains(product.url, '?sku=')}">
			<c:url value="${product.url}/zoomImages" var="productZoomImagesUrl"/>
		</c:if>
		<a class="modal" id="imageLink" href="${productZoomImagesUrl}" target="_blank" title="<spring:theme code="general.zoom"/>">
			<product:productPrimaryImage product="${product}" format="product"/>
		</a>
		<ycommerce:testId code="productDetails_zoomImage_button">
			<a class="modal" id="zoomLink" href="${productZoomImagesUrl}" target="_blank" title="<spring:theme code="general.zoom"/>">
				<span class="details" style="display: block; position: absolute; bottom: 10px; right: 10px; width: 18px; height: 18px;" title="<spring:theme code="general.zoom"/>"></span>
			</a>
		</ycommerce:testId>
	</div>