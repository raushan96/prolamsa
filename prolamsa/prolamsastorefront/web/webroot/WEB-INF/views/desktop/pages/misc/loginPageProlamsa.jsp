<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>


<template:loginProlamsa pageTitle="${pageTitle}">
 <div class="" style="width: 100%; height: 645px;">
		<div class="span-20 main-right" style="width: 100%; height: 100%; ">
			<div class="item_container_holder">
				<div class="title_holder" style="height: 60px;">
				
					<h2 class="login_title"><spring:theme code="login.title"/></h2>
					
				</div>
				<br><br><br>
 				<div>
 				
					<table style="height: 80%; padding-right: 20px;">
						<tr>
							<td  style="width: 47%; padding-right: 80px;" >
								<div align="right">
									
									<!-- Inicio se modificó 28102014 -->
									<%-- <div class="siteLogo">
					                    <div class="simple_disp-img">
											<cms:pageSlot position="SiteLogo" var="logo" >
												<cms:component component="${logo}"/>
											</cms:pageSlot>
					                 	</div>
					                </div> --%>
									<!-- Fin se modificó 28102014 
									
									
									<cms:pageSlot position="SiteLogo" var="logo" limit="1" >
										<cms:component component="${logo}" />
									</cms:pageSlot>
									-->	

									<c:choose>
										<c:when test="${isBrowserCompatible eq true}">
											<a href="/store/"><img id="logo" src="${themeResourcePath}/images/logo.png?id=1" alt="logo" style="width:240px;"></a>
										</c:when>
										<c:otherwise>
											<a href="/store/"><img id="logo" src="${themeResourcePath}/images/logo.png?id=1" alt="logo" style="width:300px; height:150px; margin-left:450px;"></a>
										</c:otherwise>
									</c:choose>
								</div>
							</td> 	  
							<td style="width: 53%; padding-left: 20px;" >
								<div class="span-10 last login-panel" >		   		
									<c:url value="/j_spring_security_check" var="loginActionUrl"/>
									<user:login actionNameKey="login.login" action="${loginActionUrl}"/>
								</div>
							</td>
						</tr>	
					</table>
				</div>
			</div>
			<br><br><br>
			<div class="information_message negative">
				<div id="loginMessage">
					<common:globalMessages/>				
				</div>
			</div>
			<c:if test="${isBrowserCompatible eq false}">
				<p style="padding-left: 17px;"><spring:theme code="login.message.update.browser" />
				
					<a href="http://windows.microsoft.com/es-es/internet-explorer/download-ie" style="color: blue; font-size: 11px;" >&nbsp;<spring:theme code="login.message.update.browser.link" /></a>		
					</p>			
				</c:if>
		</div>
		<div id="copyright" class="grid-100" style="width: 100%; height: 51px;">
			<p style="color: rgba(0, 0, 0, 0)">.</p>
		</div>
	</div>	 
	
</template:loginProlamsa>
