<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="cartTotalStockKg" required="true" type="java.lang.Double" %>
<%@ attribute name="cartTotalStockPcs" required="true" type="java.lang.Double" %>
<%@ attribute name="cartTotalRollingKg" required="true" type="java.lang.Double" %>
<%@ attribute name="cartTotalRollingPcs" required="true" type="java.lang.Double" %>

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
				<c:choose>
					<c:when test="${baseStore.uid eq '6000'}">
						<td>
						<spring:theme code="checkout.cart_summary.unit.ton"/>
					</td>
					<%-- <td>
						<spring:theme code="checkout.cart_summary.unit.pc"/>
					</td> --%>
					</c:when>
					<c:otherwise>
						<td>
							<spring:theme code="checkout.cart_summary.unit.kg"/>
						</td>
						<td>
							<spring:theme code="checkout.cart_summary.unit.pc"/>
						</td>
					</c:otherwise>
				</c:choose>
				
				
				
			</tr>
			<tr>
				<!-- Preparing data to sum -->
				<fmt:formatNumber pattern="${weightKgPattern}"  value="${cartTotalStockKg}" var="cartTotalStockKgWithPattern"/>
				<fmt:formatNumber pattern="${weightKgPattern}"  value="${cartTotalRollingKg}" var="cartTotalRollingKgWithPattern"/>
				
				<fmt:parseNumber value="${cartTotalStockKgWithPattern}" var="cartTotalStockKgParsed"/>
				<fmt:parseNumber value="${cartTotalRollingKgWithPattern}" var="cartTotalRollingKgParsed"/>
				<!--  -->
				<c:choose>
					<c:when test="${baseStore.uid eq '6000'}">
						<td><spring:theme code="checkout.cart_summary.orderTotal"/></td>
						<td class="col_right"><fmt:formatNumber pattern="${metricTonsPattern }" value="${(cartTotalStockKgParsed + cartTotalRollingKgParsed) / 1000}"/></td>
						<%-- <td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalStockPcs + cartTotalRollingPcs}"/></td> --%>
					</c:when>
					<c:otherwise>
					
						<td><spring:theme code="checkout.cart_summary.orderTotal"/></td>
				<td class="col_right"><fmt:formatNumber pattern="${weightKgPattern}" value="${cartTotalStockKgParsed + cartTotalRollingKgParsed}"/></td>
				<td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalStockPcs + cartTotalRollingPcs}"/></td>
			
			
						
					</c:otherwise>
				</c:choose>	
			</tr>
			<tr>
				<c:choose>
					<c:when test="${baseStore.uid eq '6000'}">
					
					
				<td><spring:theme code="checkout.cart_summary.available"/></td>
				<td class="col_right"><fmt:formatNumber pattern="${metricTonsPattern }"  value="${cartTotalStockKg / 1000}"/></td>
				<%-- <td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalStockPcs}"/></td> --%>
				</c:when>
				<c:otherwise>
					<td><spring:theme code="checkout.cart_summary.available"/></td>
				<td class="col_right"><fmt:formatNumber pattern="${weightKgPattern}"  value="${cartTotalStockKg}"/></td>
				<td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalStockPcs}"/></td>
			
				</c:otherwise>
				</c:choose>
				
			</tr>
			<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
				<tr>
					<c:choose>
					<c:when test="${baseStore.uid eq '6000'}">
					<td><spring:theme code="checkout.cart_summary.rolling"/></td>
					<td class="col_right"><fmt:formatNumber pattern="${metricTonsPattern }"  value="${cartTotalRollingKg / 1000}"/></td>
					<%-- <td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalRollingPcs}"/></td> --%>
					</c:when>
					<c:otherwise>
						<td><spring:theme code="checkout.cart_summary.rolling"/></td>
					<td class="col_right"><fmt:formatNumber pattern="${weightKgPattern}"  value="${cartTotalRollingKg}"/></td>
					<td class="col_right"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartTotalRollingPcs}"/></td>
				
					</c:otherwise>
					</c:choose>
				</tr>
			</sec:authorize>
		</table>
	</div>
</div>