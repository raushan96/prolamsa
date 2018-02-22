<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="invoice" tagdir="/WEB-INF/tags/desktop/invoice" %>

<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>

	<cms:pageSlot position="Section1" var="feature">
		<cms:component component="${feature}" element="div" class="span-24 section1 cms_disp-img_slot"/>
	</cms:pageSlot>
	
	<div class="span-24 section2">
		<cms:pageSlot position="Section2A" var="feature" element="div" class="span-6 zone_a thumbnail_detail">
			<cms:component component="${feature}" element="div"/>
		</cms:pageSlot>

		<cms:pageSlot position="Section2B" var="feature" element="div" class="span-6 zone_b thumbnail_detail">
			<cms:component component="${feature}" element="div"/>
		</cms:pageSlot>

		<cms:pageSlot position="Section2C" var="feature" element="div" class="span-12 zone_c cms_disp-img_slot last">
			<cms:component component="${feature}" element="div"/>
		</cms:pageSlot>
	</div>
	
	<invoice:invoiceList />
	
	<cms:pageSlot position="Section3" var="feature" element="div" class="span-24 section3 cms_disp-img_slot">
		<cms:component component="${feature}" element="div"/>
	</cms:pageSlot>
	
	<cms:pageSlot position="Section4" var="feature" element="div" class="span-24">
		<cms:component component="${feature}" element="div" class="span-6 section4 small_detail ${(elementPos%4 == 3) ? 'last' : ''}"/>
	</cms:pageSlot>
	
	<cms:pageSlot position="Section5" var="feature" element="div" class="span-24 section5 cms_disp-img_slot">
		<cms:component component="${feature}" element="div"/>
	</cms:pageSlot>
	
</template:page>