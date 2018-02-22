<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:page pageTitle="${pageTitle}">
	<div id="globalMessages">
		<common:globalMessages/>
	</div>

	<cms:pageSlot position="Section1" var="feature">
		<cms:component component="${feature}" element="div" class="span-24 section1 cms_disp-img_slot"/>
	</cms:pageSlot>
	
	<div class="pagehead-wrapper">
			<div class="pagehead grid-container">
			
			  <c:choose>
			  	<c:when test="${baseStore.uid eq '1000'}">
			  		<h1>
	                    <spring:theme code="dashboard.content.level1Mx" /> <br>
	                </h1>
			  	</c:when>
			  	<c:when test="${baseStore.uid eq '2000'}">
			  		<h1>
	                    <spring:theme code="dashboard.content.level1" /> <br>
	                    <span class="weight-regular"><spring:theme code="dashboard.content.level2" /></span>
	                </h1>
			  	</c:when>
			  	<c:when test="${baseStore.uid eq '5000'}">
			  		<h1>
	                    <spring:theme code="dashboard.content.level1A4C" /> <br>
	                </h1>
			  	</c:when>
			  	<c:when test="${baseStore.uid eq '6000'}">
			  		<h1>
	                    <spring:theme code="dashboard.content.level1Axis" /> <br>
	                    <span class="weight-regular"><spring:theme code="dashboard.content.level2Axis" /></span>
	                </h1>
			  	</c:when>
			  </c:choose>				

			</div>
		</div>

        <div class="grid-container" style="bottom-padding:10px;">
	

			<div class="span-24 section2">
				<cms:pageSlot position="Section2A" var="feature" element="div" class="span-12 zone_a cms_disp-img_slot">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
		
				<cms:pageSlot position="Section2B" var="feature" element="div" class="span-6 zone_b thumbnail_detail">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
		
				<cms:pageSlot position="Section2C" var="feature" element="div" class="span-6 zone_c last thumbnail_detail">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
			</div>
		
			<cms:pageSlot position="Section3" var="feature" element="div" class="span-24 section3 cms_disp-img_slot">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
		
		<!-- NEORIS_CHANGE check for control panel here -->
		<%-- 	<cms:pageSlot position="Section4" var="feature" element="div" class="span-24"> --%>
		<%-- 		<cms:component component="${feature}" element="div" class="span-6 section4 small_detail ${(elementPos%4 == 3) ? 'last' : ''}"/> --%>
		<%-- 	</cms:pageSlot> --%>
		
			<cms:pageSlot position="Section5" var="feature" element="div" class="span-24 section5 cms_disp-img_slot">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
			
		<%-- 	<div class="yCmsContentSlot span-8 disp-img_left">
				<div class="content">
					<div class="helpBanner">
						<span class="bannerHeadline">NEED HELP?</span>
						<span class="bannerText">Click here for help with checking out<a href="#">GO &gt; &gt;</a></span>
					</div>
				</div>
			</div>
		--%>
	<common:dashboard/>
	</div>
</template:page>