<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="company" tagdir="/WEB-INF/tags/desktop/company" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="company" tagdir="/WEB-INF/tags/desktop/company" %>

<spring:url value="/my-company/organization-management/manage-units/create" var="createUnitUrl">
	<spring:param name="unit" value=""/>
</spring:url>

<template:page pageTitle="${pageTitle}">

	<script type="text/javascript"> // set vars
		/*<![CDATA[*/
		
		var unittree = true;
		
		/*]]>*/
	</script>
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<nav:myCompanyNav selected="units"/>
	<div class="span-20 last">
		<cms:pageSlot position="TopContent" var="feature" element="div" class="span-20 wide-content-slot cms_disp-img_slot">
			<cms:component component="${feature}"/>
		</cms:pageSlot>								
		<div class="span-20 last">
			<div class="item_container_holder">
				<div class="last">
					<a href="${createUnitUrl}">
						<ycommerce:testId code="Unit_CreateNewUnit_button">
							<a href="${createUnitUrl}" class="mycompany right pad_right create"><spring:theme code="text.company.manage.units.newUnitButton" text="Create New Unit"/></a>
						</ycommerce:testId>
					</a>
				</div>
				<div class="title_holder">
					<div class="title">
						<div class="title-top">
							<span></span>
						</div>
					</div>
					<h2>
						<spring:theme code="text.company.manage.units.label" text="Manage Units"/>
					</h2>
				</div>
				<div class="item_container">
					<spring:theme code="text.company.manage.units.subtitle"/>
				</div>
				<div class="item_container">				
					<ul id="unittree" class="treeview">
						<company:unitTree node="${rootNode}"/>
					</ul>
				</div>
			</div>
		</div>
	</div>
</template:page>
