<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<%@ attribute name="col" required="true" type="java.util.Collection" %>
<%@ attribute name="css" required="false" type="java.lang.String" %>
<%@ attribute name="selection" required="false" type="java.lang.String" %>

<select name="${name}" id="${id}" class="${css}">
	<c:forEach items="${col}" var="eachOpt">
		<option value="${eachOpt.value}" ${eachOpt.value eq selection ? 'selected="selected"' : ''}>
		${eachOpt.label}
		</option>
	</c:forEach>
</select>
