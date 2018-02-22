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
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<spring:url value="/my-company/organization-management/manage-users/disable"
			var="disableUserUrl">
	<spring:param name="user" value="${b2BCustomerForm.uid}"/>
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/enable"
			var="enableUserUrl">
	<spring:param name="user" value="${b2BCustomerForm.uid}"/>
</spring:url>
<spring:url value="/my-company/organization-management/manage-users/resetpassword"
			var="resetPasswordUrl">
	<spring:param name="user" value="${b2BCustomerForm.uid}"/>
</spring:url>

<c:if test="${empty saveUrl}">
	<c:choose>
		<c:when test="${not empty b2BCustomerForm.uid}">
			<spring:url value="/my-company/organization-management/manage-users/edit" var="saveUrl">
				<spring:param name="user" value="${b2BCustomerForm.uid}"/>
			</spring:url>
		</c:when>
		<c:otherwise>
			<spring:url value="/my-company/organization-management/manage-users/create" var="saveUrl"/>
		</c:otherwise>
	</c:choose>
</c:if>
<c:if test="${empty cancelUrl}">
	<c:choose>
		<c:when test="${not empty b2BCustomerForm.uid}">
<%--No esta documentado en el BBP Word que se muestren los detalles del usuario --%>		
<%--			<spring:url value="/my-company/organization-management/manage-users/details" --%>
				<spring:url value="/my-company/organization-management/manage-users"
						var="cancelUrl">
				<spring:param name="user" value="${b2BCustomerForm.uid}"/>
			</spring:url>
		</c:when>
		<c:otherwise>
			<c:url value="/my-company/organization-management/manage-users" var="cancelUrl"/>
		</c:otherwise>
	</c:choose>
</c:if>

<template:page pageTitle="${pageTitle}">
	<div class="pagehead-wrapper">
    	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
				<%--
				<common:back cancelUrl="${cancelUrl}"/>
				 --%>
			</div>
		</div>
	</div>		
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<nav:myCompanyNav selected="users"/>

	
	
	<div class="span-20 last main-right">
		<div class="item_container_holder" style="display: block;">
			<div class="title_holder">
				<h2>
					<c:choose>
						<c:when test="${not empty b2BCustomerForm.uid}">
							<spring:theme code="text.company.${action}.edituser.title" text="${action}" arguments="${b2BCustomerForm.parentB2BUnit}"/>
						</c:when>
						<c:otherwise>
							<spring:theme code="text.company.${action}.adduser.title" text="${action}" arguments="${param.unit}"/>
						</c:otherwise>
					</c:choose>
				</h2>
			</div>
		</div>	
		<div class="item_container">
			<p>
				<c:choose>
					<c:when test="${not empty b2BCustomerForm.uid}">
						<spring:theme code="text.mycompany.user.updateForm"
									  text="Please use this form to update customer details"
									  arguments="${b2BCustomerForm.uid}"/>
					</c:when>
					<c:otherwise>
						<spring:theme code="text.mycompany.user.createForm"
									  text="Please use this form to create a new customer details"/>
					</c:otherwise>
				</c:choose>
			<br>
					<spring:theme code="form.required" text="Fields marked * are required"/>
				</p>
				<form:form action="${saveUrl}" method="post" commandName="b2BCustomerForm">
					<dl class="">
						<form:input type="hidden" name="uid" path="uid" id="uid"/>
						
						<c:choose>
							<c:when test="${not empty b2BCustomerForm.uid}">
								<a href="${resetPasswordUrl}"><spring:theme code="text.company.user.resetPassword" text="Reset Password"/></a>
								<br>
								<c:choose>
									<c:when test="${b2BCustomerForm.active}">
										Activo:
										<input type="radio" name="active" value="true" checked="checked">
										Inactivo:
										<input type="radio" name="active" value="false"><br>
									</c:when>
									<c:otherwise>
										Activo:
										<input type="radio" name="active" value="true">
										Inactivo:
										<input type="radio" name="active" value="false"  checked="checked"><br>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>
						
						<formUtil:formSelectBox idKey="user.title" labelKey="user.title" path="titleCode"
												mandatory="true"
												skipBlank="false" skipBlankMessageKey="form.select.empty"
												items="${titleData}"/>
						<br>						
						<formUtil:formInputBox idKey="user.firstName" labelKey="user.firstName" path="firstName"
											   inputCSS="text" mandatory="true"/>
						<br>
						<formUtil:formInputBox idKey="user.lastName" labelKey="user.lastName" path="lastName"
											   inputCSS="text" mandatory="true"/>
						<br>					   
						<formUtil:formInputBox idKey="user.email" labelKey="user.email" path="email"
													   inputCSS="text"
													   mandatory="true"/>
						<br>							   
						<!-- NEORIS_CHANGE #111 -->
						<formUtil:formCheckboxes idKey="text.company.user.unit.title" 
												labelKey="text.company.user.unit.title" path="b2bUnits"
												items="${b2bUnits}" disabled="${not empty param.unit and not empty param.role}"
												/>
						<br>						
						<formUtil:formCheckboxes idKey="text.company.user.roles" labelKey="text.company.user.roles"
												 path="roles"
												 mandatory="false" items="${roles}" disabled="${not empty param.unit and not empty param.role}" typeIdentifier="String"/>
					</dl>
   
				   <ycommerce:testId code="User_Save_button">
						<button id="backButton" type="button" class="button yellow positive button-float-right"><spring:theme code="text.account.balanceStatement.detail.button.back" text="Back"/></button>
					</ycommerce:testId>			          
					<ycommerce:testId code="User_Cancel_button">
						<a href="${cancelUrl}" class="button yellow positive button-float-right"><spring:theme code="b2bcustomer.cancel" text="Cancel"/></a>
					</ycommerce:testId>
					<ycommerce:testId code="User_Save_button">
						<button type="submit" class="button yellow positive button-float-right"><spring:theme code="text.account.user.saveUpdates" text="Save Updates"/></button>
					</ycommerce:testId>

				</form:form>
			</div>
		</div>
	</div>
	<br/>
</template:page>

<script type="text/javascript">
onDOMLoaded(initUser);

function initUser()
{	
	jQuery("#backButton").click(backAction);	
}

function backAction()
{
	history.go(-1);
}
</script>
