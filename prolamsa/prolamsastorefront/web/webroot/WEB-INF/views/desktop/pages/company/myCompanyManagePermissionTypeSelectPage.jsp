<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<spring:url value="/my-company/organization-management/manage-permissions" var="cancelUrl" />

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
					<h2><spring:theme code="text.company.managePermissions.create.permission.title" text="Create Permission" /></h2>
				</div>
			</div>
			<div class="item_container">
				<p>
					
					<spring:theme code="form.required" text="Fields marked * are required"/>
					<br><br>
					<spring:theme code="text.company.managePermissions.create.step1" text="Step 1 of 2"/>
					
				</p>
				<form:form id="b2BPermissionTypeSelectionForm" commandName="b2BPermissionTypeSelectionForm" method="POST">
					<formUtil:formSelectBox idKey="text.company.managePermissions.type.label" labelKey="text.company.managePermissions.type.label" skipBlankMessageKey="text.company.managePermissions.selectBox.permissionType" mandatory="true" path="b2BPermissionType" items="${ b2bPermissionTypes}" />
							
					<button type="submit" class="button yellow positive button-float-right"><spring:theme code="text.company.managePermissions.create.continueButton" text="Continue" /></button> 
					<a href="${cancelUrl}" class="button yellow positive button-float-right"><spring:theme code="text.company.managePermissions.edit.cancelButton" text="Cancel" /></a>
				</form:form>
			</div>
		</div>	
	</div>
</template:page>