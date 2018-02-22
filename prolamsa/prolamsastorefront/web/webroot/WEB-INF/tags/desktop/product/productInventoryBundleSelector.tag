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

<c:set var="selected" value="" />
<c:set var="disabled" value="" />
<c:set var="isQuantityInList" value="false" />

<c:if test="${ (empty quantityCol) or (fn:length(quantityCol)==1) }">
	<c:set var="disabled" value="disabled" />
</c:if>

	<c:set var="maxNumerOfOptions" value="${fn:length(quantityCol) }" />
	<!-- maxItemsOnQTYCombos comes from the BaseStore and is injected by the Controller  -->
	<c:if test="${not empty  maxItemsOnQTYCombos}" >
		<c:set var="maxNumerOfOptions" value="${maxItemsOnQTYCombos}" />
	</c:if>
     

<c:choose>
			<c:when test="${(fn:length(quantityCol)>1)}">
                <select id="${bundleName}" class="dropdown_qty col_right" ${disabled}>
					<c:forEach var="eachQty" end="${ (maxNumerOfOptions) <=0 ? 1 :  (maxNumerOfOptions) }" items="${quantityCol}">
						<c:if test=""></c:if>
						<c:choose>
							<c:when test="${eachQty eq selectedQuantity}">
								<c:set var="selected" value="selected" />
								<c:set var="isQuantityInList" value="true" />
							</c:when>
							<c:otherwise>
								<c:set var="selected" value="" />
							</c:otherwise>
						</c:choose>						
						
						<%-- <option value="${eachQty}" ${selected}><fmt:formatNumber value="${eachQty}" pattern="###,###,###,###,##0.000" /></option> --%>
						<option value="${eachQty}" ${selected}><fmt:formatNumber value="${eachQty}" pattern="${quantityPattern }"   /></option>
					</c:forEach>
					<c:if test="${ isQuantityInList ne  true }">
						<%-- <option value="${selectedQuantity}" selected><fmt:formatNumber value="${selectedQuantity}" pattern="###,###,###,###,##0.000" /></option> --%>
						<option value="${selectedQuantity}" selected><fmt:formatNumber value="${selectedQuantity}" pattern="${quantityPattern }" /></option>
					</c:if>		
				</select>                				
			</c:when>
			<c:otherwise>				
				 <select id="${bundleName}" style="color: red;" class="dropdown_qty col_center" ${disabled}>
				    <option style="font-size: 20px" value="0">----</option>
				 </select>		
			</c:otherwise>
		</c:choose>	
