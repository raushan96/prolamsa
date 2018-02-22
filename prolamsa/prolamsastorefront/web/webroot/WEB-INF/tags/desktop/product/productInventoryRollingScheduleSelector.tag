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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="dates" required="true" type="java.util.Collection" %>
<%@ attribute name="dateFormat" required="true" type="java.lang.String" %>
<%@ attribute name="selectedDate" required="false" type="java.util.Date" %>

<c:set var="selected" value="" />

<c:if test="${ not empty selectedDate}" >
	<fmt:formatDate var="formattedSelecetedDate" pattern="${dateFormat}" value="${selectedDate}" />
</c:if>

<c:choose>
	<c:when test="${empty dates}">
		<span class="dropdown_date"><spring:theme code="text.contact.sales.rep" text="Contact your sales rep."/></span>
	</c:when>
	
	<c:when test="${not empty dates}">
		<select id="rollingScheduleDate" class="dropdown_date">
			<c:forEach var="eachDate" items="${dates}">
				<fmt:formatDate var="formattedDate" pattern="${dateFormat}" value="${eachDate}" />${formattedDate}
				<c:choose>
					<c:when test="${formattedDate eq formattedSelecetedDate}">
						<c:set var="selected" value="selected" />
					</c:when>
					<c:otherwise>
						<c:set var="selected" value="" />
					</c:otherwise>
				</c:choose>
				
				
				<c:choose>
				<c:when test="${dateFormat eq 'MM-dd-yyyy'}">
		<c:if test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
			<option value="${formattedDate}" ${selected}>${fn:substring(formattedDate,3,5)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,6,11)}</option>
		</c:if>
		<c:if test="${baseStore.uid eq '2000' || baseStore.uid eq '6000'}">
			<option value="${formattedDate}" ${selected}><spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,3,5)}-${fn:substring(formattedDate,6,11)}</option>
		</c:if>
	</c:when></c:choose>
				
				
				
				
				<%-- 
					<option value="${formattedDate}" ${selected}> mes${fn:substring(formattedDate,0,2)}.. dia ${fn:substring(formattedDate,3,5)}.. año ${fn:substring(formattedDate,6,11)}.. ${formattedDate}</option>
				--%>
					<%-- <c:if test="${currentLanguage.isocode eq 'es'}">
						<option value="${formattedDate}" ${selected}>${fn:substring(formattedDate,3,5)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,6,11)}</option>
					</c:if>
					<c:if test="${currentLanguage.isocode eq 'en'}">
						<option value="${formattedDate}" ${selected}><spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,3,5)}-${fn:substring(formattedDate,6,11)}</option>
					</c:if> --%>
			</c:forEach>
		</select>
	</c:when>
</c:choose>
