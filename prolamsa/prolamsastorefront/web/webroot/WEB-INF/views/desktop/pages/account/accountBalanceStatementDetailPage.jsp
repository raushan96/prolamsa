<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="balanceStatement" tagdir="/WEB-INF/tags/desktop/balanceStatement" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>

<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	 <div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
	
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
			<div id="globalMessages">
				<common:globalMessages/>
			</div>
		</div>
	</div>		
	
	<div class="grid-container">		
	    <nav:accountNav selected="${cmsPage.label}"/>    	
		
		<div class="span-20 last main-right">
			<balanceStatement:balanceStatementDetail />
		</div>
	</div>
	<br>
</template:page>

<%--
<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>

    <nav:accountNav selected="${cmsPage.label}"/>    	
	
	<div class="span-20 last">
		<balanceStatement:balanceStatementDetail />
	</div>
		
</template:page>
--%>