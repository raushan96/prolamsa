<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>

<script type="text/javascript" src="${sharedResourcePath}/js/analyticsmediator.js"></script>
<analytics:googleAnalytics/>
<analytics:jirafe/>

<!-- AppDynamics -->

<%-- Uncomment only for production

<script type="text/javascript"> 
    window['adrum-start-time'] = new Date().getTime(); 
</script> 
<script type="text/javascript" src="${sharedResourcePath}/js/adrum.js"></script> 

--%>