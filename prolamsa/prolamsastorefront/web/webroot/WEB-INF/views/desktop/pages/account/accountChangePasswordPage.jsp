<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
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
		<nav:accountNav selected="${cmsPage.label}"/>    	
		<div class="span-20 last main-right">
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
					<h2><spring:theme code="text.account.profile.updatePasswordForm" text="Update Password"/></h2>
				</div>
				<div class="item_container">
					<p><spring:theme code="text.account.profile.updatePassword" text="Please use this form to update your account password"/></p>
					<p class="required"><spring:theme code="form.required" text="Fields marked * are required"/></p>
					<form:form action="update-password" method="post" commandName="updatePasswordForm" autocomplete="off">
							<formUtil:formPasswordBox idKey="profile.currentPassword" labelKey="profile.currentPassword" path="currentPassword" inputCSS="text password" mandatory="true"/>
							<formUtil:formPasswordBox idKey="profile-newPassword" labelKey="profile.newPassword" path="newPassword" inputCSS="text password strength" mandatory="true"/>
							<formUtil:formPasswordBox idKey="profile.checkNewPassword" labelKey="profile.checkNewPassword" path="checkNewPassword" inputCSS="text password" mandatory="true"/>
							
							<dd>
								<a id="profile-forgotten-password" class="labelSmall" href="#"><spring:theme code="login.link.forgottenPwd"/></a>
							</dd>
							
							<button class="button yellow positive button-float-right"><spring:theme code="text.account.profile.updatePasswordForm" text="Update Password"/></button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<br/>
</template:page>