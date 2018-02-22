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

<%@ attribute name="value" required="true" type="java.util.Date" %>
<%@ attribute name="pattern" required="true" type="java.lang.String" %>

<fmt:formatDate var="formattedDate" pattern="${pattern}" value="${value}"/>

<c:if test="${not empty value}">
	<c:if test="${pattern eq 'MMM/dd/yyyy' || pattern eq 'dd/MMM/yyyy'}">
		${formattedDate}
	</c:if>
<c:choose>
	<c:when test="${pattern eq 'MM/dd/yyyy hh:mm:ss a'}">
		<c:if test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
			${fn:substring(formattedDate,3,5)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,6,11)} ${fn:substring(formattedDate,11,19)} ${fn:substring(formattedDate,20,22)} 
		</c:if>
		<c:if test="${baseStore.uid eq '2000' || baseStore.uid eq '6000'}">
			<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,3,5)}-${fn:substring(formattedDate,6,11)} ${fn:substring(formattedDate,11,19)} ${fn:substring(formattedDate,20,22)}
		</c:if>
	</c:when>
	<c:when test="${pattern eq 'MM/dd/yyyy'}">
		<c:if test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
			${fn:substring(formattedDate,3,5)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,6,11)}
		</c:if>
		<c:if test="${baseStore.uid eq '2000' || baseStore.uid eq '6000'}">
			<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,3,5)}-${fn:substring(formattedDate,6,11)}
		</c:if>
	</c:when>
	<c:when test="${pattern eq 'yyyy-MM-dd'}">
		<c:if test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
			${fn:substring(formattedDate,8,10)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,5,7)}"/>-${fn:substring(formattedDate,0,4)}
		</c:if>
		<c:if test="${baseStore.uid eq '2000' || baseStore.uid eq '6000'}">
			<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,5,7)}"/>-${fn:substring(formattedDate,8,10)}-${fn:substring(formattedDate,0,4)}
		</c:if>
	</c:when>
	
	
</c:choose>
</c:if>

<%--
<c:if test="${not empty value}">
<c:choose>
	<c:when test="${pattern eq 'MM/dd/yyyy hh:mm:ss a'}">
		<c:if test="${currentLanguage.isocode eq 'es'}">
			${fn:substring(formattedDate,3,5)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,6,11)} ${fn:substring(formattedDate,11,19)} ${fn:substring(formattedDate,20,22)} 
		</c:if>
		<c:if test="${currentLanguage.isocode eq 'en'}">
			<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,3,5)}-${fn:substring(formattedDate,6,11)} ${fn:substring(formattedDate,11,19)} ${fn:substring(formattedDate,20,22)}
		</c:if>
	</c:when>
	<c:when test="${pattern eq 'MM/dd/yyyy'}">
		<c:if test="${currentLanguage.isocode eq 'es'}">
			${fn:substring(formattedDate,3,5)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,6,11)}
		</c:if>
		<c:if test="${currentLanguage.isocode eq 'en'}">
			<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,0,2)}"/>-${fn:substring(formattedDate,3,5)}-${fn:substring(formattedDate,6,11)}
		</c:if>
	</c:when>
	<c:when test="${pattern eq 'yyyy-MM-dd'}">
		<c:if test="${currentLanguage.isocode eq 'es'}">
			${fn:substring(formattedDate,8,10)}-<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,5,7)}"/>-${fn:substring(formattedDate,0,4)}
		</c:if>
		<c:if test="${currentLanguage.isocode eq 'en'}">
			<spring:theme code="text.month.calendar.short.${fn:substring(formattedDate,5,7)}"/>-${fn:substring(formattedDate,8,10)}-${fn:substring(formattedDate,0,4)}
		</c:if>
	</c:when>
</c:choose>
</c:if>
--%>