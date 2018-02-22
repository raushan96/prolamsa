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
		<nav:myCompanyNav selected="organizationManagement"/>
		<div class="span-20 last main-right">
			<div class="span-20 last">
				<div class="cust_acc">
					<div class="cust_acc_tile">
						<c:url value="/my-company/organization-management/manage-users" var="encodedUrl" />
						<span>
							<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/profile.png" alt="<spring:theme code="text.company.manageUsers" text="Manage Users"/>" title="<spring:theme code="text.company.manageUsers" text="Manage Users"/>" />
							</a>
						</span>
						<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.company.manageUsers" text="Manage Users"/></a></h3>
						<ul class="ul_account" >
							<c:url value="/my-company/organization-management/manage-users/create" var="encodedUrl1" />
							<li  class="ul_account_item" ><a href="${encodedUrl1}"><spring:theme code="text.company.addNewUsers" text="Add new users"/></a></li>
							<c:url value="/my-company/organization-management/manage-users" var="encodedUrl2" />
							<li  class="ul_account_item" ><a href="${encodedUrl2}"><spring:theme code="text.company.editOrDisableUsers" text="Edit or disable users"/></a></li>
						</ul>
					</div>
					<div class="cust_acc_tile">
						<c:url value="/my-company/organization-management/manage-permissions" var="encodedUrl" />
						<span>
							<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/profile.png" alt="<spring:theme code="text.company.managePermissions" text="Manage Permissions"/>" title="<spring:theme code="text.company.managePermissions" text="Manage Permissions"/>" />
							</a>
						</span>
						<h3 class="ul_account_item"><a href="${encodedUrl}"><spring:theme code="text.company.managePermissions" text="Manage Permissions"/></a></h3>
						<ul class="ul_account" >
							<c:url value="/my-company/organization-management/manage-permissions/add" var="encodedUrl1" />
							<li  class="ul_account_item" ><a href="${encodedUrl1}"><spring:theme code="text.company.addNewPermission" text="Add new permission"/></a></li>
							<c:url value="/my-company/organization-management/manage-permissions" var="encodedUrl2" />
							<li  class="ul_account_item" ><a href="${encodedUrl2}"><spring:theme code="text.company.editOrDisablePermissions" text="Edit or disable permissions"/></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>	
</template:page>