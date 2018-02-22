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

<%@ attribute name="b2bUnitList" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedB2BUnit" required="false" type="String" %>
<%@ attribute name="codeX" required="false" type="String" %>


<c:set var="selected" value="" />

<span><spring:theme code="text.account.balanceStatement.list.client"/></span>
<select id="customer" name="customer">
    <option value="">All</option> 
	<c:forEach items="${b2bUnitList}" var="eachFormattedB2BUnit">
	    <c:choose>
			<c:when test="${eachFormattedB2BUnit.uid eq selectedB2BUnit}">
				<c:set var="selected" value="selected" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>
	   	<option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
	</c:forEach>
</select>