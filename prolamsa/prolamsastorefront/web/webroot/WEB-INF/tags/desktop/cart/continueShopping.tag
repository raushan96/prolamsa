<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<%@ attribute name="continueShoppingUrl" required="true" type="java.lang.String"%>


    <!-- NEORIS_CHANGE # "/store" replace "continueShoppingUrl" -->
	<button type="submit" class="form button" onclick="window.location = '/store'; return false">
		<span class="icon-addtocart-black"></span>
		<spring:theme text="Continue Shopping" code="cart.page.continue"/>
	</button>
