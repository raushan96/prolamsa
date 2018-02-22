<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="metaDescription" required="false" %>
<%@ attribute name="metaKeywords" required="false" %>
<%@ attribute name="pageCss" required="false" fragment="true" %>
<%@ attribute name="pageScripts" required="false" fragment="true" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="${currentLanguage.isocode}">
	<head>
		<title>${not empty pageTitle ? pageTitle : not empty cmsPage.title ? cmsPage.title : 'Accelerator Title'}</title>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	
		<%-- Additional meta tags --%>
		<c:forEach var="metatag" items="${metatags}">
			<c:if test="${not empty metatag.content}" >
				<meta name="${metatag.name}" content="${metatag.content}" />
			</c:if>
		</c:forEach>

		<spring:theme code="img.favIcon" text="/" var="favIconPath"/>
        <link rel="shortcut icon" type="image/x-icon" media="all" href="${contextPath}${favIconPath}" />

		<%-- CSS Files Are Loaded First as they can be downloaded in parallel --%>
		<template:styleSheets />

		<%-- Inject any additional CSS required by the page --%>
		<jsp:invoke fragment="pageCss"/>
		
		<analytics:analytics />
		
		<template:javaScriptVariables/>

		<!-- NEORIS_CHANGE #102 -->
		<template:neorisJavaScriptVariables/>
	</head>

	<body class="${pageBodyCssClasses} ${cmsPageRequestContextData.liveEdit ? ' yCmsLiveEdit' : ''} language-${currentLanguage.isocode}">

		<%-- Inject the page body here --%>
		<jsp:doBody/>
		
		
		<%-- Load JavaScript required by the site --%>
		<template:javaScript />
		
		<%-- Inject any additional JavaScript required by the page --%>
		<jsp:invoke fragment="pageScripts"/>

		<script type="text/javascript" src="${commonResourcePath}/js/jquery.pstrength.custom-1.2.0.js"></script>
		<script type="text/javascript">
			/*<![CDATA[*/
				$(function() {
					$('.strength').pstrength({ verdicts:["<spring:theme code="password.strength.veryweak" />",
														 "<spring:theme code="password.strength.weak" />",
														 "<spring:theme code="password.strength.medium" />",
														 "<spring:theme code="password.strength.strong" />",
														 "<spring:theme code="password.strength.verystrong" />"],
											   tooShort: "<spring:theme code="password.strength.tooshortpwd" />",
											   minCharText: "<spring:theme code="password.strength.minchartext"/>" });
				});
			/*]]>*/
		</script>

	</body>

	<%-- <template:debugFooter /> --%>
</html>
