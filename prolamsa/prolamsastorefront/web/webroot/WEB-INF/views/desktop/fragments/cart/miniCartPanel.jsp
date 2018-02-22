<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="tonsTotal" value="0"/>
<c:set var="tonsStock" value="0"/>
<c:set var="tonsProd" value="0"/>

<c:choose>
	<c:when test="${cartData.hasAPIProducts}">
		<c:forEach items="${cartData.entries }" var="eachEntry">
			<c:set var="tonsTotal" value="${tonsTotal + eachEntry.product.ftTnEquiv*eachEntry.quantity }"/>
			<c:choose>
				<c:when test="${empty eachEntry.rollingScheduleWeek }">
					<c:set var="tonsStock" value="${tonsStock + eachEntry.product.ftTnEquiv*eachEntry.quantity }"/>
				</c:when>
				<c:otherwise>
					<c:set var="tonsProd" value="${tonsProd + eachEntry.product.ftTnEquiv*eachEntry.quantity }"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:forEach items="${cartData.entries }" var="eachEntry">
			<c:set var="tonsTotal" value="${tonsTotal + eachEntry.product.tnEquiv*eachEntry.quantity }"/>
			<c:choose>
				<c:when test="${empty eachEntry.rollingScheduleWeek }">
					<c:set var="tonsStock" value="${tonsStock + eachEntry.product.tnEquiv*eachEntry.quantity }"/>
				</c:when>
				<c:otherwise>
					<c:set var="tonsProd" value="${tonsProd + eachEntry.product.tnEquiv*eachEntry.quantity }"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:otherwise>
</c:choose>

	<ycommerce:testId code="miniCart_items_label">
<%-- 				<ycommerce:testId code="miniCart_items_label"> --%>
<%-- 					<spring:theme code="cart.items" arguments="${totalItems}"/> --%>
<%-- 				</ycommerce:testId> --%>
			<c:choose>
				<c:when test="${tonsTotal != 0 }">
					<span class="totalItemsSpan" style="">(${totalItems })</span> <spring:theme code="minicart.totalweight" />&nbsp;<fmt:formatNumber value="${tonsTotal}" pattern="${tonPattern }"/>&nbsp;<spring:theme code="backorder.list.tonUnit" />
				</c:when>
				<c:otherwise>
					<span class="totalItemsSpan" style="">(${totalItems })</span> <spring:theme code="minicart.totalweight" />&nbsp;0&nbsp;<spring:theme code="backorder.list.tonUnit" />
				</c:otherwise>
			</c:choose>
				 
  <div class="available_ton" style="">
    <spring:theme code="minicart.available"/> <br>
    <c:choose>
				<c:when test="${tonsStock != 0 }">
					<span><fmt:formatNumber value="${tonsStock}" pattern="${tonPattern}"/>&nbsp;<spring:theme code="backorder.list.tonUnit" /></span>
				</c:when>
				<c:otherwise>
					<span>----</span>
				</c:otherwise>
			</c:choose>
  
  </div>
  <div class="rolling_ton" style="">
     <spring:theme code="minicart.rolling"/> <br>
        <c:choose>
				<c:when test="${tonsProd != 0 }">
					<span><fmt:formatNumber value="${tonsProd}" pattern="${tonPattern}"/>&nbsp;<spring:theme code="backorder.list.tonUnit" /></span>
				</c:when>
				<c:otherwise>
					<span>----</span>
				</c:otherwise>
			</c:choose>
  </div>
	</ycommerce:testId>
	
<span>
<!-- NEORIS_CHANGE #51 -->
		<!--
	<ycommerce:testId code="miniCart_total_label">
		
			<c:if test="${totalDisplay == 'TOTAL'}">
				<format:price priceData="${totalPrice}"/>
			</c:if>
			<c:if test="${totalDisplay == 'SUBTOTAL'}">
				<format:price priceData="${subTotal}"/>
			</c:if>
			<c:if test="${totalDisplay == 'TOTAL_WITHOUT_DELIVERY'}">
				<format:price priceData="${totalNoDelivery}"/>
			</c:if>
		
	</ycommerce:testId>
	-->
</span>
