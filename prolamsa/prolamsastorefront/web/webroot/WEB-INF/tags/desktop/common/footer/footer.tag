<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize ifNotGranted="ROLE_INCIDENT_USER">	
<cms:pageSlot position="Footer" var="feature" element="div">
	<cms:component component="${feature}"/>
</cms:pageSlot>
</sec:authorize>
