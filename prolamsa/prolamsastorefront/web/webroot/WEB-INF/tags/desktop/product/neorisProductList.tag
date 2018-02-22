<%@ tag trimDirectiveWhitespaces="true" %>
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="productList" required="true" type="java.util.Collection" %>

<script>
var addCartBatch = "<c:url value="/cart/addBatch" />";
var data = {target:null, event:null};

function getURLCodeLocation(urlCode)
{	
	var href = "<c:url value='"+ urlCode +"' />";
	location.href = href;	
}

function quickView(target, event)
{	var code = jQuery(target).attr("productCode");
	ACC.descriptionQuickView.popupUrl = "<c:url value="/product/quickView?code=" />" + code;
	ACC.descriptionQuickView.popUp(code, target, event);
}

function setupQuickView(target, event)
{
	data.target = target;
	data.event = event;
}

function closePop()
{
	ACC.descriptionQuickView.closePopUp();
}

onDOMLoaded(
	function()
	{
		ACC.neorisproduct.messages.addToCart = "<spring:theme code="cart.add-to-cart" />";
		ACC.neorisproduct.messages.noProductsSelected = "<spring:theme code="cart.no-products-selected" />";
		ACC.neorisproduct.messages.changePageTitle ="<spring:theme code="search.page.changePageTitle" />";
		ACC.neorisproduct.messages.changePageMessage = "<spring:theme code="search.page.changePageMessage" />";							
			
		ACC.neorisproduct.messages.productSelectionLocationMixup = "<spring:theme code="cart.add-to-cart.productSelectionLocationMixup" />";
		ACC.neorisproduct.messages.productSelectionLocationCartMismatch = "<spring:theme code="cart.add-to-cart.productSelectionLocationCartMismatch" />";
		
		ACC.neorisproduct.messages.productSelectionFamilyMixup = "<spring:theme code="cart.add-to-cart.productSelectionFamilyMixup" />";
		ACC.neorisproduct.messages.productSelectionFamilyCartMismatch = "<spring:theme code="cart.add-to-cart.productSelectionFamilyCartMismatch" />";
		
		ACC.neorisproduct.messages.minMaxValidationOnError = "<spring:theme code="cart.add-to-cart.minMaxValidationOnError" />";
		
		$("table.productListTable tr:even").addClass("filaImpar");
	    $("table.productListTable tr:odd").addClass("filaPar");
	}
);
</script>


<c:if test="${supportProductVariantConfig}">
</c:if>

<c:if test="${activateJQRateFronEndLibraries}">
	<product:productFavoriteJS />
</c:if>

<c:choose>
	<c:when test="${supportProductVariantConfig }">
		<product:neorisConfigurableProductList productList="${searchPageData.results}" />
	</c:when>
	<c:otherwise>
		<product:neorisNotConfigurableProductList productList="${searchPageData.results}" />											
	</c:otherwise>
</c:choose>