<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<spring:url
	value="/my-company/organization-management/manage-permissions/disable"
	var="confirmDisableUrl">
	<spring:param name="permissionCode" value="${permissionCode}"/>
</spring:url>

<spring:url
	value="/my-company/organization-management/manage-permissions/view"
	var="cancelDisableUrl">
	<spring:param name="permissionCode" value="${permissionCode}"/>
</spring:url>

<template:page pageTitle="${pageTitle}">
	<div class="pagehead-wrapper">
    	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<nav:myCompanyNav selected="managePermissions"/>	
		<div class="span-20 last main-right">
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
					<h2>
						<spring:theme code="text.company.managePermissions.disable.confirm" text="Confirm Disable"/>
					</h2>
				</div>
			</div>
			<div class="item_container">
				<p>
					<spring:theme code="text.company.managePermissions.disable.confirm.message"></spring:theme>
				</p>
				<div>
					<form:form action="${confirmDisableUrl}">
						<a href="${cancelDisableUrl}" class="button yellow positive button-float-right"><spring:theme code="text.company.managePermissions.disable.confirm.no"/></a>
						<button class="button yellow positive button-float-right"><spring:theme code="text.company.managePermissions.disable.confirm.yes"/></button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</template:page>
