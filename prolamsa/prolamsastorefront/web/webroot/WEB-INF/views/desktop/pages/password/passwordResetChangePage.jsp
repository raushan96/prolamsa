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

<spring:theme code="updatePwd.title" var="pageTitle2"/>
<template:loginProlamsa pageTitle="${pageTitle2}">
	<div class="" style="width: 100%; height: 645px;">
		<div class="span-20 main-right" style="width: 100%; height: 100%; ">
			<div class="item_container_holder">
				<div class="title_holder">
					<div class="title">
						<div class="title-top">
							<span></span>
						</div>
					</div>
					<h2><spring:theme code="updatePwd.title"/></h2>
				</div>
				<br><br><br>
 				<div>
 					<table style="height: 80%; padding-right: 20px;">
						<tr>
							<td style="width: 47%; padding-right: 80px;" >
								<div align="right">
									<a href="/store/"><img id="logo" src="${themeResourcePath}/images/logo.png" alt="logo" style="width:240px;"></a>
								</div>
							</td> 	  
							<td style="width: 53%; padding-left: 20px;" >
								<user:updatePwd/>
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
		</div>
		<div id="copyright" class="grid-100" style="width: 100%; height: 51px;">
			<p style="color: rgba(0, 0, 0, 0)">.</p>
		</div>
	</div>	 
</template:loginProlamsa>
