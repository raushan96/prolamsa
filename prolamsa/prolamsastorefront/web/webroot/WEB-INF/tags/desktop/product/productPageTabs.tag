<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="details" required="true" type="de.hybris.platform.core.model.product.ProductModel" %>



<script type="text/javascript">

var PRODUCTTABSARRAY = new Array();
var tabindex = 0;

function AddProductTab(componentUid, componentTitle, componentContent, titleClass, contentClass) 
{	
	PRODUCTTABSARRAY[tabindex++] = new ProductTab(componentUid, componentTitle, componentContent, titleClass, contentClass);
};

function ProductTab(componentUid, componentTitle, componentContent, titleClass, contentClass) {
    this.componentUid = componentUid;
    this.componentTitle = componentTitle;
    this.componentContent = componentContent;
    this.titleClass = titleClass;
    this.contentClass = contentClass;
}
</script>

<div id="prod_tabs">
	<ycommerce:testId code="productOptions_tabs">
		<ul class="tab_strip" id="tab_strip">
			<li id="tab_01">
				<h2>
					<a href="#tab-details" class="tab_01"><spring:theme code="product.product.details" /></a>
				</h2>
			</li>
			<!-- NEORIS CHANGE 
				<li>
					<h2>
						<a href="#tab-reviews" class="tab_03"><spring:theme code="review.reviews" /></a>
					</h2>
				</li>
			-->
		</ul>
	</ycommerce:testId>
	<div class="prod_content" id="tab-details">
	 <%-- NEORIS_CHANGE #57
	 <h3><spring:theme code="product.product.details" /></h3>
	 --%>
		<product:productDetailsTab product="${product}" details="${details}"/>
	</div>
	<!-- NEORIS CHANGE 
		<div class="prod_content" id="tab-reviews">
			<h3><spring:theme code="review.reviews" /></h3>
			<product:productPageReviewsTab/>
		</div>
	-->
</div>

<cms:pageSlot position="Tabs" var="tabs">
	<cms:component component="${tabs}"/>
</cms:pageSlot>
