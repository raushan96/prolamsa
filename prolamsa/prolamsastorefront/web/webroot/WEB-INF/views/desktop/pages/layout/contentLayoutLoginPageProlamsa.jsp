<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="cms" uri="/cms2lib/cmstags/cmstags.tld" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/nav/breadcrumb" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<template:loginProlamsa pageTitle="${pageTitle}"> 
    	
	<div id="globalMessages">
		<common:globalMessages/>				
	</div>
		
	<table  >
	<tr>
	  <td width="600">
	   <center>
	    <cms:pageSlot position="SiteLogo" var="logo" limit="1" >
			<cms:component component="${logo}" />
	   	</cms:pageSlot>
	   </center>
	   	
	  </td> 	  
	  <td width=600>
	    <div class="span-10 last login-panel" >		   		
			<c:url value="/j_spring_security_check" var="loginActionUrl"/>
			<user:login actionNameKey="login.login" action="${loginActionUrl}"/>
		</div>	  
	  </td>	  
	</tr>	
	</table>
			
</template:loginProlamsa>