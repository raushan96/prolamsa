<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

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
					<table class="" >
						<tr>
							<td style="width: 145px;"><spring:theme code="profile.title" text="Title"/>: </td>
							<td>${fn:escapeXml(title.name)}</td>
						</tr>
						<tr>
							<td><spring:theme code="profile.firstName" text="First name"/>: </td>
							<td>${fn:escapeXml(customerData.firstName)}</td>
						</tr>
						<tr>
							<td><spring:theme code="profile.lastName" text="Last name"/>: </td>
							<td>${fn:escapeXml(customerData.lastName)}</td>
						</tr>
						<tr>
							<td><spring:theme code="profile.email" text="E-mail"/>: </td>
							<td>${fn:escapeXml(customerData.displayUid)}</td>
						</tr>
						<tr>
							<td>
							</td>
							<td align="left">
								<formUtil:formButton
									id="updatePwd"
									type="submit" 
									css="button yellow positive button-float-left"
									tabindex="106"
									springThemeText="text.account.profile.updatePasswordForm"
									title="Change password"/>
							
						
							&nbsp;&nbsp;&nbsp;&nbsp;
							
							
							
								<formUtil:formButton
									id="updatePrf"
									type="submit" 
									css="button yellow positive "
									tabindex="106"
									springThemeText="text.account.profile.updatePersonalDetails"
									title="Update personal details"/>
									
							
							
							<sec:authorize ifAllGranted="ROLE_B2BADMINGROUP">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<formUtil:formButton
										id="updateEmail"
										type="submit" 
										css="button yellow positive "
										tabindex="106"
										springThemeText="text.account.profile.updateEmail"
										title="Update email"/>
							</sec:authorize>
									
							</td>
							
						
						
						</tr>
					</table>
				</div>
			</div>
		</div>
		<br/>
	<script type="text/javascript">
	onDOMLoaded(initProfile);
	
	function bindSearchFormElements()
	{
		jQuery("#updatePwd").click(goBackAction);
		jQuery("#updatePrf").click(goUpdatePrf);
		jQuery("#updateEmail").click(goUpdateEmail);
	}
	
	function goBackAction()
	{
		window.location = "<spring:url value="/my-account/update-password" />";
	}
	
	function goUpdatePrf()
	{
		window.location = "<spring:url value="/my-account/update-profile" />";
	}
	
	function goUpdateEmail()
	{
		window.location = "<spring:url value="/my-account/update-email" />";
	}
	
	function initProfile()
	{
		bindSearchFormElements();
	}
	</script>
</template:page>



