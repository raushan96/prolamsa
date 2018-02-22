<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<spring:url value="/my-company/organization-management/manage-users/create"
			var="manageUsersUrl"/>	
			
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
		<nav:myCompanyNav selected="users"/>
		<div class="span-20 last main-right">
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">	
					<h2>
						<spring:theme code="text.company.manageusers.label" text="All Users"/>
					</h2>
				</div>
				<%--	
				<div class="last">
						<ycommerce:testId code="User_AddUser_button">
							<a href="${manageUsersUrl}" class="mycompany right pad_right create"><spring:theme code="text.company.manageUser.button.create" text="Create New User"/></a>
						</ycommerce:testId>
				</div>
				 --%>
				<div class="item_container">
					<p>
						<spring:theme code="text.company.manageusers.subtitle" arguments="${b2bStore}"/>
					</p>	
				</div>
				<div class="item_container">
					<c:if test="${not empty searchPageData.results}">
						<%--
						<p>
							<spring:theme code="text.company.manageUser.viewUsers" text="View Users"/>
						</p>
						--%>
						<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
						<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}"
										searchUrl="/my-company/organization-management/manage-users?sort=${searchPageData.pagination.sort}"
										msgKey="text.company.manageUser.pageAll" numberPagesShown="${numberPagesShown}"/>
						</c:if>				
						<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
                  <br/>
						<form>
							<table id="manage_user">
								<thead>
									<tr class="firstrow">
										<th id="header1">
											<spring:theme code="text.company.column.name.name" text="Name"/>
										</th>
										<th id="header2">
											<spring:theme code="text.company.column.roles.name" text="Roles"/>
										</th>
										<%--
										<th id="header3">
											<spring:theme code="text.company.column.parentUnit.name" text="Parent Unit"/>
										</th>
										<th id="header4">
											<spring:theme code="text.company.manageUser.user.costCenter" text="Cost center"/>
										</th>
										--%>
										<th id="header3">
											<spring:theme code="text.company.status.title" text="Status"/>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${searchPageData.results}" var="user">
										<tr>
											<%--			
 											 <spring:url value="/my-company/organization-management/manage-users/details/" 
														var="viewUserUrl2">	 
												<spring:param name="user" value="${user.uid}"/>
											</spring:url>
											--%>
											<spring:url value="/my-company/organization-management/manage-users/edit/" 
														var="viewUserUrl">	 
												<spring:param name="user" value="${user.uid}"/>
											</spring:url>
		
											<td headers="header1">
												<ycommerce:testId code="my-company_username_label">
													<p><a href="${viewUserUrl}">${user.firstName}&nbsp;${user.lastName}</a></p
														>
												</ycommerce:testId>
											</td>
											<td headers="header2">
												<ycommerce:testId code="my-company_user_roles_label">
													<c:forEach items="${user.roles}" var="role">
														<p>
															<spring:theme code="b2busergroup.${role}.name"/>
														</p>
													</c:forEach>
												</ycommerce:testId>
											</td>
											<%--
											<td headers="header3">
												<ycommerce:testId code="my-company_user_unit_label">
													<p><a href="${viewUnitUrl}">${user.unit.name}</a></p>
												</ycommerce:testId>
											</td>
											<td headers="header4">
												<ycommerce:testId code="my-company_user_costcenter_label">
													<c:forEach items="${user.unit.costCenters}" var="costCenter">
														<spring:url value="/my-company/organization-management/manage-costcenters/view/"
															var="viewCostCenterUrl">
															<spring:param name="costCenterCode" value="${costCenter.code}"/>
														</spring:url>
														<p><a href="${viewCostCenterUrl}">${costCenter.code}</a></p>
													</c:forEach>
												</ycommerce:testId>
											</td>
											--%>
											<td headers="header3">
												<ycommerce:testId code="costCenter_status_label">
													<%--
													<p><a href="${viewUserUrl2}"><spring:theme code="text.company.status.active.${user.active}" /></a></p>
													--%>
												
													<p>
														<spring:theme code="text.company.status.active.${user.active}" />
													</p>
												 	
												</ycommerce:testId>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
						<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
						<div class="main-right-footer clearfix">
							<nav:paginationReport top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
										searchPageData="${searchPageData}" searchUrl="/my-company/organization-management/manage-users?sort=${searchPageData.pagination.sort}"
										msgKey="text.company.manageUser.pageAll" numberPagesShown="${numberPagesShown}"/>
						</div>
						</c:if>
						<div class="search-form-options-search-button">
							<ycommerce:testId code="User_Create_button">
								<a href="${manageUsersUrl}" class="button yellow positive button-float-right"><spring:theme code="text.company.manageUser.button.create" text="Create New User"/></a>
							</ycommerce:testId>
						</div>
					</c:if>
					<c:if test="${empty searchPageData.results}">
						<p>
							<spring:theme code="text.company.manageUser.noUser" text="You have no users"/>
						</p>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<br/>	
</template:page>
