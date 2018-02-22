<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/mobile/header"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/mobile/footer"%>

<template:master pageTitle="${pageTitle}">
	<div class="span-10 main-right" style="width: 100%; float: left; height:auto; ">

		<header:header />
		<header:menu />
			
		<div id="gradtitlePage" class="pagehead-wrapper" style="width: 100%; float: left; height:auto; ">
			<div class="pagehead grid-container" style="width: 100%;">
				<c:choose>
					<c:when test="${baseStore.uid eq '1000'}">
						<h1 style="font-weight: 700; font-size: 15px; text-transform: uppercase; padding: 0 10px;">
                			<spring:theme code="dashboard.content.level1Mx" /><br>
               			 </h1>
					</c:when>
					<c:when test="${baseStore.uid eq '2000'}">
						<h1 style="font-weight: 700; font-size: 15px; text-transform: uppercase; padding: 0 10px;">
                			<spring:theme code="dashboard.content.level1" /><br>
	                    	<span class="weight-regular"><spring:theme code="dashboard.content.level2" /></span>
               			 </h1>
					</c:when>
					<c:when test="${baseStore.uid eq '5000'}">
						<h1 style="font-weight: 700; font-size: 15px; text-transform: uppercase; padding: 0 10px;">
                			<spring:theme code="dashboard.content.level1A4C" /><br>
               			 </h1>
					</c:when>
					<c:when test="${baseStore.uid eq '6000'}">
						<h1 style="font-weight: 700; font-size: 15px; text-transform: uppercase; padding: 0 10px;">
                			<spring:theme code="dashboard.content.level1Axis" /><br>
	                    	<span class="weight-regular"><spring:theme code="dashboard.content.level2Axis" /></span>
               			 </h1>
					</c:when>
				</c:choose>
			</div>
		</div>
			
		<div class="feautered" style="width: 100%; height:auto; float: left;" >
			<br>
            <div class="feautered-item">
            	<img src="${themeResourcePath}/images/example-img.png" alt=""> 
                <div class="feautered-text">                                                	
                	<c:choose>
					  	<c:when test="${baseStore.uid eq '1000'}">
					  		<h2 style="font-size: 12px; padding: 5px 10px 0 10px; "><spring:theme code="dashboard.content.level1Mx" /></h2>
					  	</c:when>
					  	<c:when test="${baseStore.uid eq '2000'}">
					  		<h2 style="font-size: 12px; padding: 5px 10px 0 10px; "><spring:theme code="dashboard.content.level1" /></h2>
					  	</c:when>
					  	<c:when test="${baseStore.uid eq '5000'}">
					  		<h2 style="font-size: 12px; padding: 5px 10px 0 10px; "><spring:theme code="dashboard.content.level1A4C" /></h2>
					  	</c:when>
					  	<c:when test="${baseStore.uid eq '6000'}">
					  		<h2 style="font-size: 12px; padding: 5px 10px 0 10px; text-transform: none;"><spring:theme code="dashboard.content.level3Axis" /></h2>
					  		<h3 style="font-size: 10px; padding: 5px 10px 0 10px;"><spring:theme code="dashboard.content.level4Axis" /></h3>
					  	</c:when>
			  		</c:choose>
                </div><!-- feautered-text -->
           	</div><!-- #Feautered Item -->
            <br>
    	</div><!-- #Feautered --> 
			
		<footer:footer />
		
	</div>
</template:master>