<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>
<%@ attribute name="details" required="true" type="de.hybris.platform.core.model.product.ProductModel" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>

<spring:theme code="text.addToCart" var="addToCartText"/>

<div class="span-4">
	<!-- NEORIS CHANGE -->
	<!--
		<product:productImageCarousel galleryImages="${galleryImages}"/>
	-->	
</div>
<!--
<div class="span-8">
 NEORIS CHANGE 
	
	<product:productImagePanel product="${product}"/>
	
</div>
-->
<div class="span-8 last">
	<div class="prod">
		<ycommerce:testId code="productDetails_productNamePrice_label_${product.code}">
			<h1>${product.name}</h1>
			<!-- NEORIS_CHANGE  Tag mostrando lo relativo al precio -->
			<!--
				<product:productPricePanel product="${product}"/>
			-->	
		</ycommerce:testId>
		<!--
		<p class="prod_summary">${product.summary}</p>
		
		<product:productPromotionSection product="${product}"/>
		
		<cms:pageSlot position="VariantSelector" var="component" element="div" class="span-8">
			<cms:component component="${component}"/>
		</cms:pageSlot>
		-->
		
		<cms:pageSlot position="AddToCart" var="component" element="div" class="span-8">
			<cms:component component="${component}"/>
		</cms:pageSlot>
		
		
		<!-- NEORIS_CHANGE
		<product:productShareTag/>
		-->
	</div>
</div>