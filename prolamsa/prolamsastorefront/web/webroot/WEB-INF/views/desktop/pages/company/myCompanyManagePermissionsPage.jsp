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
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<spring:url value="/my-company/organization-management/manage-permissions/add" var="createUrl"/>

<template:page pageTitle="${pageTitle}">
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
		<nav:myCompanyNav selected="managePermissions"/>
		<div class="span-20 last main-right">
		<div class="item_container_holder" >
			<div class="title_holder">
				<h2>
					<spring:theme code="text.company.${action}.title" text="All Permissions"/>
				</h2>
			</div>
		
<%--
		<div class="item_container">
			<p>	
				<spring:theme code="text.company.${action}.subtitle" />
			</p>


	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<nav:myCompanyNav selected="managePermissions"/>
	<div class="span-20 last">
		<cms:pageSlot position="TopContent" var="feature" element="div" class="span-20 wide-content-slot cms_disp-img_slot">
			<cms:component component="${feature}"/>
		</cms:pageSlot>				
		<div class="item_container_holder">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2>
					<spring:theme code="text.company.${action}.title" text="All Permissions"/>
				</h2>
			</div>
			<div class="right last">
						<ycommerce:testId code="Add_New_Permission_button">
							<a href="${createUrl}" class="mycompany right pad_right create"><spring:theme code="text.company.managePermissions.addButton.displayName" text="Add New" /></a>
						</ycommerce:testId>	
			</div>
 --%>				
			<div class="item_container">
				<p>
					<spring:theme code="text.company.${action}.subtitle" />
				</p>	
			</div>
		
			<div class="item_container">
				<nav:paginationReport top="true"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}"
								searchUrl="/my-company/organization-management/manage-permissions?sort=${searchPageData.pagination.sort}"
								msgKey="text.company.${action}.page"
								numberPagesShown="${numberPagesShown}"/>
				<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
            <br/>
			<form>					
				<table id="order_history">
					<thead>
					<tr class="firstrow">
						<th id="headerXXX">
							<spring:theme code="text.company.column.id.name" text="ID"/>
						</th>
						<th id="headerXXX">
							<spring:theme code="text.company.column.name.name" text="Name"/>
						</th>
						<th id="headerXXX">
							<spring:theme code="text.company.${action}.currency.title" text="Currency"/>
						</th>
						<th id="headerXXX">
							<spring:theme code="text.company.${action}.value.title" text="Value"/>
						</th>
						<th id="headerXXX">
							<spring:theme code="text.company.${action}.timespan.title" text="TimeSpan"/>
						</th>
						<th id="headerXXX">
							<spring:theme code="text.company.column.parentUnit.name" text="Parent Unit"/>
						</th>
						<th id="headerXXX">
							<spring:theme code="text.company.column.status.name" text="Status"/>
						</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${searchPageData.results}" var="result">
						<spring:url value="/my-company/organization-management/manage-permissions/view" var="viewPermissionDetailsUrl">
								<spring:param name="permissionCode" value="${result.code}"/>
						</spring:url>
						<tr id="headerXXX">
							<td id="headerXXX">
								<ycommerce:testId code="${action}_name_link">
									<a href="${viewPermissionDetailsUrl}">${result.code}</a>
								</ycommerce:testId>
							</td>
							<td id="headerXXX">
								<ycommerce:testId code="${action}_type_link">
									${result.b2BPermissionTypeData.name}
								</ycommerce:testId>
							</td>
							
							<td id="h3">
								<ycommerce:testId code="${action}_currency_link">
									<p>${result.currency.name}</p>
								</ycommerce:testId>
							</td>
							<td id="h3">
								<ycommerce:testId code="${action}_value_link">
									<p><fmt:formatNumber maxFractionDigits="0" value="${result.value}" /></p>
								</ycommerce:testId>
							</td>
							<td id="headerXXX">
								<ycommerce:testId code="${action}_timespan_link">
									<p>${result.timeSpan}</p>
								</ycommerce:testId>
							</td>
							<td id="headerXXX">
								<ycommerce:testId code="${action}_b2bunit_label">
									<p>${result.unit.uid}</p>
								</ycommerce:testId>
							</td>

							<td id="headerXXX">
								<ycommerce:testId code="${action}_status_label">
									<c:choose>
										<c:when test="${result.active}">
											<spring:theme code="text.company.${action}.status.enabled" text="Enabled"/>
										</c:when>
										<c:otherwise>
											<spring:theme code="text.company.${action}.status.disabled" text="Disabled"/>
										</c:otherwise>
									</c:choose>
								</ycommerce:testId>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				</form>
				<div class="main-right-footer clearfix">
				<nav:paginationReportFooter top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}"
								searchUrl="/my-company/organization-management/manage-permissions?sort=${searchPageData.pagination.sort}"
								msgKey="text.company.${action}.page"
								numberPagesShown="${numberPagesShown}"/>
				</div>
				<div class="search-form-options-search-button">
							<ycommerce:testId code="Add_New_Permission_button">
								<a href="${createUrl}" class="button yellow positive button-float-right"><spring:theme code="text.company.managePermissions.addButton.displayName" text="Add New" /></a>
							</ycommerce:testId>
				</div>	

			</div>
			
			
					
				<c:if test="${empty searchPageData.results}">
					<p><spring:theme code="text.company.noentries" text="No entries" /></p>
				</c:if>
			</div>
		</div>
	</div>
</template:page>
