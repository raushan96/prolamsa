<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/desktop/store" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:page pageTitle="${pageTitle}">

	<jsp:attribute name="pageScripts">
		<c:if test="${!empty googleApiVersion}">
			<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=${googleApiVersion}&key=${googleApiKey}&sensor=false"></script>
		</c:if>
	</jsp:attribute>

	<jsp:body>
		<div id="breadcrumb" class="breadcrumb">
			<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
		</div>
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<cms:pageSlot position="SideContent" var="feature" element="div" class="span-4 left-content-slot cms_disp-img_slot">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		<div class="span-20 right last">
			<cms:pageSlot position="TopContent" var="feature" element="div" class="span-20 last top-content-slot cms_disp-img_slot">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
			
			<div class="span-10 store_finder">
				<store:storeSearch errorNoResults="${errorNoResults}"/>
			</div>
			<div class="span-10 last">
				<store:storesMap storeSearchPageData="${searchPageData}"/>
			</div>
			<div class="span-20 last">
				<store:storeListForm searchPageData="${searchPageData}" locationQuery="${locationQuery}"
									 geoPoint="${geoPoint}"/>
			</div>
		</div>
	</jsp:body>

</template:page>
