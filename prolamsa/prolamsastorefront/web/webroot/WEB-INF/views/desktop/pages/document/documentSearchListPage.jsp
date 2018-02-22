<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="document" tagdir="/WEB-INF/tags/desktop/document"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
	<c:if test="${not empty accErrorMsgs}">
		<br><br>
	</c:if>
	<div class="grid-container">		
	    <nav:accountNav selected="${cmsPage.label}"/>    	
		<div class="span-20 last main-right">
		 <sec:authorize ifAllGranted="ROLE_MATERIAL_SUPPLIER_RECEPTION">
     	<document:documentSearchMaterialList />
     </sec:authorize>
     <sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER" >
			<document:documentSearchList />
	</sec:authorize>
	</div>
	</div>
	<br>
</template:page>