<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

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
					<h2><spring:theme code="text.account.profile" text="Profile"/></h2>
				</div>
				<div class="item_container">
					<p><spring:theme code="text.account.profile.updateForm" text="Please use this form to update your personal details"/></p>
					<p class="required"><spring:theme code="form.required" text="Fields marked * are required"/></p>
					<form:form action="update-profile" method="post" commandName="updateProfileForm">
							<formElement:formSelectBox idKey="profile.title" labelKey="profile.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="form.select.empty" items="${titleData}"/>
							<formElement:formInputBox idKey="profile.firstName" labelKey="profile.firstName" path="firstName" inputCSS="text labelSmall" mandatory="true"/>
							<formElement:formInputBox idKey="profile.lastName" labelKey="profile.lastName" path="lastName" inputCSS="text labelSmall" mandatory="true"/>
	
						<span style="display: block; clear: both;">
						<ycommerce:testId code="profilePage_SaveUpdatesButton">
							<button class="button yellow positive button-float-right"><spring:theme code="text.account.profile.saveUpdates" text="Save Updates"/></button>
						</ycommerce:testId>
						</span>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<br/>
</template:page>