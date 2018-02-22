<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%-- colorbox CSS --%>
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.colorbox-1.3.16.css"/>

<%--  AddOn Common CSS files --%>
<c:forEach items="${addOnCommonCssPaths}" var="addOnCommonCss">
	   <link rel="stylesheet" type="text/css" media="all" href="${addOnCommonCss}" />
</c:forEach>

<%-- theme specific css --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="${themeResourcePath}/css/theme-green.css" /> --%>
<link rel="stylesheet" href="${themeResourcePath}/css/styles.css">

<%--  AddOn Theme CSS files --%>
<c:forEach items="${addOnThemeCssPaths}" var="addOnThemeCss">
	   <link rel="stylesheet" type="text/css" media="all" href="${addOnThemeCss}" />
</c:forEach>
