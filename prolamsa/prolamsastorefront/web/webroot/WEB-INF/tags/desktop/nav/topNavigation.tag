<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <sec:authorize ifAllGranted="ROLE_PURCHASER,ROLE_ACCOUNTER">
  

<div class="nav-main-wrapper">
        <div class="lightgrey-bg nav-main-right-bg"></div>
<div id="nav_main" class="nav_main grid-container" style="padding-left: 3px; padding-right: 3px;">
	<cms:pageSlot position="NavigationBar" var="component" element="ul" class="clear_fix">
		<cms:component component="${component}"/>
	</cms:pageSlot>
</div>
</div>


     </sec:authorize>