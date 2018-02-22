<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/mobile/user" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<template:master pageTitle="${pageTitle}"> 
    	
<!-- <div class="span-10 last login-panel" >	 -->	   		
	<c:url value="/j_spring_security_check" var="loginActionUrl"/>
	<user:login actionNameKey="login.login" action="${loginActionUrl}"/>

	<div class="information_message negative" style="background-color: #C62D2D;">
		<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
			<c:out value="Login failed: ${SPRING_SECURITY_LAST_EXCEPTION.message}" />		
		</c:if>
	</div>
<!-- </div> -->

</template:master>
