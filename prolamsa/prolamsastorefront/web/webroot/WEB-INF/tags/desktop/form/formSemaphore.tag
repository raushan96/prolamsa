<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="semaphoreCredit" required="false" type="java.lang.String" %>

<spring:theme code="text.semaphore.title" />
<c:if test="${semaphoreCredit eq 'G' }">
	<ul class="semaphore green">
</c:if>
<c:if test="${semaphoreCredit eq 'Y' }">
	<ul class="semaphore yellow">
</c:if>
<c:if test="${semaphoreCredit eq 'R' }">
	<ul class="semaphore red">
</c:if>
<li></li>
<li></li>
<li></li>
</ul>
