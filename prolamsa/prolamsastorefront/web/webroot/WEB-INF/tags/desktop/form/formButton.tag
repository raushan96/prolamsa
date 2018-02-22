<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="springThemeText" required="false" type="java.lang.String" %>
<%@ attribute name="css" required="false" type="java.lang.String" %>
<%@ attribute name="id" required="false" type="java.lang.String" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ attribute name="value" required="false" type="java.lang.String" %>
<%@ attribute name="tabindex" required="false" type="java.lang.String" %>
<%@ attribute name="name" required="false" type="java.lang.String" %>
<%@ attribute name="onClick" required="false" type="java.lang.String" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="onClickType" required="false" type="java.lang.String" %>

<c:if test="${empty type}">
	<c:set var="type" value="button" />
</c:if>

<c:choose>
	<c:when test="${onClickType eq '1'}">
		<button id="${id}" type="${type}" class="button ${css}" onclick="${onClick}" title="${title}" value="${value}" tabindex="${tabindex}" name="${name}" ${(disabled) ? 'disabled' : ''}>
		    <span><spring:theme code="${springThemeText}"/></span>
		</button>
	</c:when>
	<c:otherwise>
		<button id="${id}" type="${type}" class="button ${css}" onclick='${onClick}' title="${title}" value="${value}" tabindex="${tabindex}" name="${name}" ${(disabled) ? 'disabled' : ''}>
		    <span><spring:theme code="${springThemeText}"/></span>
		</button>
	</c:otherwise>
</c:choose>
