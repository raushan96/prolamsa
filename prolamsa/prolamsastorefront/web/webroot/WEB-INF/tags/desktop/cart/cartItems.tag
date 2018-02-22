<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>

<c:choose>
	<c:when test="${supportProductVariantConfig }">
		<cart:neorisCartConfigurableItems cartData="${cartData}" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${cartData.hasAPIProducts}">
				<cart:neorisCartNotConfigurableAPIItems cartData="${cartData}"/>
			</c:when>
			<c:otherwise>
				<cart:neorisCartNotConfigurableItems cartData="${cartData}" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<script type="text/javascript"> // set vars
	onDOMLoaded(initialize_CartItems);
	
	function initialize_CartItems()
	{
		ACC.neorisproduct.checkForProductEntries = false;
	}
</script>