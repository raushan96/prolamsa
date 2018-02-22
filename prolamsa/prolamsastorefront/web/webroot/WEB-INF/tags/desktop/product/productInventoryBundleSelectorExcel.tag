<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@ attribute name="bundleName" required="true" type="java.lang.String" %>
<%@ attribute name="quantityCol" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedQuantity" required="false" type="java.lang.Double" %>
<%@ attribute name="product" required="false" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<c:set var="selected" value="" />
<c:set var="disabled" value="" />
<c:set var="isQuantityInList" value="false" />

<c:if test="${empty quantityCol}">
	<c:set var="disabled" value="disabled" />
</c:if>

	<c:set var="maxNumerOfOptions" value="${fn:length(quantityCol) }" />
	<!-- maxItemsOnQTYCombos comes from the BaseStore and is injected by the Controller  -->
	<c:if test="${not empty  maxItemsOnQTYCombos}" >
		<c:set var="maxNumerOfOptions" value="${maxItemsOnQTYCombos}" />
	</c:if>
<c:set var="qtFound" value="false" />
<select id="${bundleName}" class="dropdown_qty col_right"  ${disabled}>
	<c:forEach var="eachQty" end="${ (maxNumerOfOptions) <=0 ? 1 :  (maxNumerOfOptions) }" items="${quantityCol}">
		<c:if test=""></c:if>
		<c:choose>
			<c:when test="${ (eachQty ge selectedQuantity) && (qtFound eq false) }">
				<c:set var="selected" value="selected" />
				<c:set var="qtFound" value="true" />
				<c:set var="isQuantityInList" value="true" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>
		<option value="${eachQty}" ${selected}><fmt:formatNumber value="${eachQty}" pattern="${quantityPattern}" /></option> 
		<%-- <option value="${eachQty}" ${selected}><fmt:formatNumber value="${eachQty}" pattern="###,###,###,###,##0" /></option>--%>
	</c:forEach>
	<c:if test="${ isQuantityInList ne  true }">
		<c:choose>
					<c:when test="${fn:contains(unit.code,'bun_kg') }">
						<c:set var="selectedQuantity"  value="${selectedQuantity}"/>
					</c:when>
					
					<c:when test="${fn:contains(unit.code,'pc_kg')  }">
						<fmt:formatNumber value="${selectedQuantity/product.piecesPerBundle + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.piecesPerBundle }"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'kg')}">
						<fmt:formatNumber value="${selectedQuantity/product.kgEquiv + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.kgEquiv }"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'mt')}">
						<fmt:formatNumber value="${selectedQuantity/product.mtEquiv + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.mtEquiv }"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'bun_lb') }">
						<c:set var="selectedQuantity"  value="${selectedQuantity}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'pc_lb') }">
						<fmt:formatNumber value="${selectedQuantity/product.piecesPerBundle + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.piecesPerBundle }"/>
					</c:when>
					
					<c:when test="${fn:contains(unit.code,'lb')}">
						<fmt:formatNumber value="${selectedQuantity/product.lbEquiv + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.lbEquiv }"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'ft')}">
						<fmt:formatNumber value="${selectedQuantity/product.ftEquiv + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.ftEquiv }"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'ton')}">
						<fmt:formatNumber value="${selectedQuantity/product.tnEquiv + 0.5}" type="number" pattern="#" var="selectedQuantity" />
						<c:set var="selectedQuantity"  value="${selectedQuantity*product.tnEquiv }"/>
					</c:when>
				</c:choose>
	
	
		<option value="${selectedQuantity}" selected><fmt:formatNumber value="${selectedQuantity}" pattern="${quantityPattern }" /></option>
		<%-- <option value="${selectedQuantity}" selected><fmt:formatNumber value="${selectedQuantity}" pattern="###,###,###,###,##0" /></option> --%>
	</c:if>
</select>
