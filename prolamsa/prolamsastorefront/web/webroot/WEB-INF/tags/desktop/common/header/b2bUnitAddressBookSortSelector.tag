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

<%@ attribute name="b2bUnitListSortBy" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedB2BUnitSortBy" required="false" type="String" %>

<%@ attribute name="top" required="true" type="java.lang.Boolean" %>

<c:set var="selected" value="" />

<span><spring:theme code="text.account.balanceStatement.list.sort"/></span>
<select id="sortBy${top ? '1' : '2'}" name="sortBy">
    <option value="">All</option> 
	<c:forEach items="${b2bUnitListSortBy}" var="eachFormattedB2BUnitSortBy">
	    <c:choose>
			<c:when test="${eachFormattedB2BUnitSortBy.uid eq selectedB2BUnitSortBy}">
				<c:set var="selected" value="selected" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>
	   	<option value="${eachFormattedB2BUnitSortBy.uid}" ${selected} >${eachFormattedB2BUnitSortBy.name}</option>
	</c:forEach>
</select>