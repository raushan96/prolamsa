<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/desktop/store" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<template:page pageTitle="${store.name} | ${siteName}">

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
			
			<div class="span-20 last">
				<div class="item_container_holder">
					<div class="title_holder">
						<div class="title">
							<div class="title-top">
								<span></span>
							</div>
						</div>
						<h2>${store.name}</h2>
					</div>
					<div class="item_container">
						<ycommerce:testId code="storeFinder_storeDetails_label">
							<div class="store_details_left_col">
								<store:storeImage store="${store}" format="store"/>
								<store:storeDetails store="${store}"/>
							</div>
						</ycommerce:testId>
						<div class="store_details_right_col">
							<store:storeMap store="${store}"/>
							<div class="store_paragraph_content">
								${store.storeContent}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>

</template:page>