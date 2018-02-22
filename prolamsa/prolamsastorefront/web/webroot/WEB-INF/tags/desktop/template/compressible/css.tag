<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<link rel="stylesheet" type="text/css" media="all" href="${commonResourcePath}/css/siteSearch.css" />

<%-- colorbox CSS 
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.colorbox-1.3.16.css"/>

 BeautyTips CSS 
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.bt-0.9.5.css" />

 treeview CSS 
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.treeview.css"/>

 scrollplus CSS 
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.scrollplus.css"/>
--%>

<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery_003.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery_002.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery_004.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/css_002.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/css.css"/><!-- #WebFonts Open Sans and Source Sans Pro -->


<%--  AddOn Common CSS files 
<c:forEach items="${addOnCommonCssPaths}" var="addOnCommonCss">
	   <link rel="stylesheet" type="text/css" media="all" href="${addOnCommonCss}" />
</c:forEach>
--%>

<%-- Tooltip css 
<link rel="stylesheet" type="text/css" media="screen" href="${themeResourcePath}/css/hint.css" />
--%>
<link rel="stylesheet" href="${themeResourcePath}/css/hint.css">


<%--************************************************************************** --%>
<%--*****************INICIO AGREGADOS MODIFICACIONES OAK**************************** 
	<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,700' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,300,600,700,800' rel='stylesheet' type='text/css'><!-- #WebFonts Open Sans and Source Sans Pro -->
--%>	
    <link rel="stylesheet" href="${themeResourcePath}/js/bundles/slick-master/slick/slick.css">
    <link rel="stylesheet" href="${themeResourcePath}/css/bundles/jquery-ui-1.css">  <%--   jquery-ui-1.10.4.min.css">   --%>
	<link rel="stylesheet" href="${themeResourcePath}/css/bundles/unsemantic-grid-responsive.css">
	<link rel="stylesheet" href="${themeResourcePath}/css/bundles/normalize.css">
	<link rel="stylesheet" href="${themeResourcePath}/css/styles.css">
<%--*****************FIN DE AGREGADOS MODIFICACIONES OAK********************** --%>
<%--*************************************************************************** --%>

<%--************************************************************************** --%>
<%--*****************INICIO COMENTARIOS MODIFICACIONES OAK**************************** --%>

	<%-- blueprintcss 
	<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/blueprint/screen.css" />
	
	 jQuery UI CSS
	<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.ui.stars-3.0.1.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.ui.autocomplete-1.8.18.css" />

	 theme specific css 
	<link rel="stylesheet" type="text/css" media="screen" href="${themeResourcePath}/css/theme-green.css" />
	
	 B2B css 
	<link rel="stylesheet" type="text/css" media="screen" href="${themeResourcePath}/css/theme-green-b2b.css" />

<%--*****************FIN DE COMENTARIOS MODIFICACIONES OAK********************** --%>
<%--*************************************************************************** --%>	
	

<%--  AddOn Theme CSS files --%>
<c:forEach items="${addOnThemeCssPaths}" var="addOnThemeCss">
	   <link rel="stylesheet" type="text/css" media="all" href="${addOnThemeCss}" />
</c:forEach>


<%-- Added for jqgrid --%>
<c:if test="${activateJQGridFronEndLibraries}">
	<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/themes/redmond/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/css/ui.jqgrid.css" />
	<link rel="stylesheet" href="${themeResourcePath}/css/neoris-jqgrid.css">
	<link rel="stylesheet" href="${themeResourcePath}/css/neoris-gant.css">
</c:if>

<%-- Added for jq rate --%>
<c:if test="${activateJQRateFronEndLibraries}">
	<link rel="stylesheet" type="text/css" media="screen" href="${commonResourcePath}/css/jquery.raty.css"/>
</c:if>