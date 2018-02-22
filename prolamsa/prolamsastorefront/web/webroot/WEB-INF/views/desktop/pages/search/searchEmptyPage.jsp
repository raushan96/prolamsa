<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	<div class="pagehead-wrapper">
    	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>		
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<div class="span-24">
			<cms:pageSlot position="SideContent" var="feature" element="div" class="span-4 side-content-slot cms_disp-img_slot">
				<cms:component component="${feature}"/>
			</cms:pageSlot>			
		    <div class="span-20 last main-rightXXA">
				<div class="item_container_holder" style="display: block;">
					<div class="title_holder">
						<h2><spring:theme code="search.no.results" text="No Results Found"/></h2>
					</div>
					<%-- <cms:pageSlot position="MiddleContent" var="comp" element="div" class="item_container">
						<cms:component component="${comp}"/>
					</cms:pageSlot> --%>
					<div class="item_container">
						<nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" />
					</div>
				</div>
				<cms:pageSlot position="BottomContent" var="comp" element="div" class="span-20 cms_disp-img_slot right last">
					<cms:component component="${comp}"/>
				</cms:pageSlot>	
			</div>
		</div>	
	</div>
	<br>	
</template:page>

<%-- 
<template:page pageTitle="${pageTitle}">
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>

	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<div class="span-24">
		<cms:pageSlot position="SideContent" var="feature" element="div" class="span-4 side-content-slot cms_disp-img_slot">
			<cms:component component="${feature}"/>
		</cms:pageSlot>	
		<div class="span-20 last main-right">
			<div class="item_container_holder">
				<div class="title_holder">
					<div class="title">
						<div class="title-top">
							<span></span>
						</div>
					</div>
					<h2 style="color: black"><spring:theme code="search.no.results" text="No Results Found"/></h2>
				</div>
				
				<cms:pageSlot position="MiddleContent" var="comp" element="div" class="item_container">
					<cms:component component="${comp}"/>
				</cms:pageSlot>
				
				<div class="item_container">
					<nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" />
				</div>
			</div>
			<cms:pageSlot position="BottomContent" var="comp" element="div" class="span-20 cms_disp-img_slot right last">
				<cms:component component="${comp}"/>
			</cms:pageSlot>			
		</div>
	</div>
</template:page>
--%>