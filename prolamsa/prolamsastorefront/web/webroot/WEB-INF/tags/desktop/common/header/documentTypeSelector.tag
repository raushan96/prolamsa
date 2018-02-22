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

<%@ attribute name="documentTypes" required="true" type="java.util.Enumeration" %>
<%@ attribute name="selectedType" required="true" type="String" %>

<%@ attribute name="spanCSS" required="false" type="String" %>
<%@ attribute name="dropDownCSS" required="false" type="String" %>

<c:set var="selected" value="" />


<select id="documentType" name="documentType" class="${dropDownCSS}">
    <option value="">All</option> 
	<c:forEach items="${documentTypes}" var="each">
	    <c:choose>
			<c:when test="${each eq selectedType}">
				<c:set var="selected" value="selected" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>
	   	<option value="${each}" ${selected} >${each}</option>
	</c:forEach>
</select>





