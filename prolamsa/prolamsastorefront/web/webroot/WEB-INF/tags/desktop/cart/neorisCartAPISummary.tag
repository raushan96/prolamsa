<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="cartTotalStockFt" required="true" type="java.lang.Double" %>
<%@ attribute name="cartTotalStockTon" required="true" type="java.lang.Double" %>
<%@ attribute name="cartTotalRollingFt" required="true" type="java.lang.Double" %>
<%@ attribute name="cartTotalRollingTon" required="true" type="java.lang.Double" %>

<div class="item_container_holder cart-summary-totals">
	<div class="title_holder_cart_summary">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="checkout.cart_summary" /></h2>
	</div>
	<div class="item_container" id="summary_cart_div">
		<table id="summary_cart_table">
			<tr class="firstrow">
				<td>
					<spring:theme code="checkout.cart_summary.unit"/>
				</td>
				<td>
					<spring:theme code="checkout.cart_summary.unit.ft"/>
				</td>
				<td>
					<spring:theme code="checkout.cart_summary.unit.ton"/>
				</td>
			</tr>
			<tr>
				<!-- Preparing data to sum -->
				<fmt:formatNumber pattern="${weightTonPattern}"  value="${cartTotalStockTon}" var="cartTotalStockTonWithPattern"/>
				<fmt:formatNumber pattern="${weightTonPattern}"  value="${cartTotalRollingTon}" var="cartTotalRollingTonWithPattern"/>
				
				<fmt:parseNumber value="${cartTotalStockTonWithPattern}" var="cartTotalStockTonParsed"/>
				<fmt:parseNumber value="${cartTotalRollingTonWithPattern}" var="cartTotalRollingTonParsed"/>
				<!--  -->
			
				<td><spring:theme code="checkout.cart_summary.orderTotal"/></td>
				<td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0" value="${cartTotalStockFt + cartTotalRollingFt}"/></td>
				<td class="col_right"><fmt:formatNumber pattern="${weightTonPattern}"  value="${cartTotalStockTonParsed + cartTotalRollingTonParsed}"/></td>
			</tr>
			<tr>
				<td><spring:theme code="checkout.cart_summary.available"/></td>
				<td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalStockFt}"/></td>
				<td class="col_right"><fmt:formatNumber pattern="${weightTonPattern}"  value="${cartTotalStockTon}"/></td>
			</tr>
			<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
				<tr>
					<td><spring:theme code="checkout.cart_summary.rolling"/></td>
					<td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalRollingFt}"/></td>
					<td class="col_right"><fmt:formatNumber pattern="${weightTonPattern}"  value="${cartTotalRollingTon}"/></td>
				</tr>
			</sec:authorize>
		</table>
	</div>
</div>