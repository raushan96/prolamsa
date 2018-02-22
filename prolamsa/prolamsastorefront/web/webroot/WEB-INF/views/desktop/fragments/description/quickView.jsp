<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="json" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>

<div class="title">
	<h3><spring:theme code="quick-view-product.title"/></h3>
<%--
	<a href="#" class="close" id="ajax_cart_close" title="<spring:theme code="popup.close"/>" alt="<spring:theme code="popup.close"/>"></a>
 --%>
 </div>

<c:choose>
	<c:when test="${productInfo == null}">
		<div class="content">
			<spring:theme code="quick-view-product.not-found"/>
		</div>
	</c:when>
	<c:otherwise>
		<div style="padding: 10px;">

		 
			<spring:theme code="text.product"/>
				: ${productData.visibleCode}
			<br>
			<spring:theme code="product.list.detail.wallthickness"/>
				: ${productInfo.wallThickness}
			<br>
			<spring:theme code="product.list.detail.steel"/>
				: <spring:theme code="${productInfo.steel}"/>
			<br>
			<spring:theme code="product.list.detail.length"/>
				: ${productInfo.length}
			<br>
			<spring:theme code="product.list.detail.material-type"/>
				: <spring:theme code="${productInfo.materialType}"/>
			
			<%--
			<br>
			<br>
			<a style="font-size: 11px; text-transform: capitalize; " href="<c:url value="/p/" />${productInfo.code}?selLocation=${productData.location.code}">
			<spring:theme code="product.list.detail.see_full_product_data"/>
			</a>
			--%>
		
		</div>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
ACC.descriptionQuickView.bindAll(); 
</script>