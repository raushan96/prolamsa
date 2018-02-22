<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="springThemeText" required="false" type="java.lang.String" %>
<%@ attribute name="sorts" required="true" type="java.util.Collection" %>
<%@ attribute name="divCSS" required="false" type="java.lang.String" %>

<div class="input-text ${divCSS}">


<label for="sortOptions1"><spring:theme code="${springThemeText}"/></label>

	<%-- Show asterisk IF asterisk="Y"--%>
	<c:if test="${asterisk == 'Y'}">
	<span class="required">*</span>
	</c:if>
		
<select name="sort" class="sortOptions sortDropDownFieldClass">
	<c:forEach items="${sorts}" var="sort">
		<option value="${sort.code}" ${sort.selected ? 'selected="selected"' : ''}>
		<c:choose>
			<c:when test="${not empty sort.name}">
				${sort.name}
			</c:when>
			<c:otherwise>
				<spring:theme code="${themeMsgKey}.sort.${sort.code}"/>
			</c:otherwise>
		</c:choose>
		</option>
	</c:forEach>
</select>

</div>