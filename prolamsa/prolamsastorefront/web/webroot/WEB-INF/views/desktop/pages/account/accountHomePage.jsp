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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
	    <nav:accountNav selected="${cmsPage.label}"/>    	
		<div class="span-20 last main-right">
			<div class="span-20 last">
				<div class="cust_acc">
					<div class="cust_acc_tile">
						<c:url value="/my-account/profile" var="encodedUrl" />
						<span>
							<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/profile.png" alt="<spring:theme code="text.account.profile" text="Profile"/>" title="<spring:theme code="text.account.profile" text="Profile"/>" />
							</a>
						</span>
						<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.account.profile" text="Profile"/></a></h3>
						<ul class="ul_account" >
							<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.profile.updatePersonalDetails" text="Update personal details"/></a></li>
							<li class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.profile.changePassword" text="Change your password"/></a></li>
							<sec:authorize ifAllGranted="ROLE_SUPERUSER">
								<c:url value="/my-account/favorite-customers" var="encodedUrl" />
								<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.favoriteCustomers" text="Favorite Customers"/></a></li>
							</sec:authorize>
							<sec:authorize ifNotGranted="ROLE_INCIDENT_USER">
								<c:url value="/my-account/settings/list" var="encodedUrl" />
								<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="b2bcustomer.settings.title" text="Setting"/></a></li>
							</sec:authorize>
						</ul>
					</div>

				<!-- B2BCUSTOMER GROUP STARTS -->
					<sec:authorize ifAllGranted="ROLE_B2BCUSTOMERGROUP">
					    <sec:authorize ifAllGranted="ROLE_ADDRESSBOOK">
						<div class="cust_acc_tile">
							<c:url value="/my-account/address-book" var="encodedUrl" />
							<span>
								<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/address.png" alt="<spring:theme code="text.account.addressBook" text="Address Book"/>" title="<spring:theme code="text.account.addressBook" text="Address Book"/>" />
								</a>
							</span>
							<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.account.addressBook" text="Address Book"/></a></h3>
							<ul class="ul_account" >
								<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.addressBook.viewBilling-DeliveryAddresses" text="View billing & delivery addresses"/></a></li>
							</ul>
						</div>
						</sec:authorize>
						
						<c:if test="${baseStore.showAlertsDashboard eq true}">
							<div class="cust_acc_tile">
								<c:url value="/my-account/alerts/list" var="encodedUrl" />
								<span>
									<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/Icono-Alertas1.png" alt="<spring:theme code="alerts.list.title" text="Alerts"/>" title="<spring:theme code="alerts.list.title" text="Alerts"/>" />
									</a>
								</span>
								<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="alerts.list.title" text="Alerts"/></a></h3>
								<ul class="ul_account" >
									<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="alerts.list.view" text="View alerts configuration"/></a></li>
								</ul>
							</div>
						</c:if>
						
						<c:if test="${baseStore.showOrdersAndQuotes eq true}">
							<sec:authorize ifAnyGranted="ROLE_PURCHASER" >
								<div class="cust_acc_tile">
									<c:url value="/my-account/my-quotes" var="encodedUrl" />
									<span>
										<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/quotes.png" alt="<spring:theme code="text.account.quotes" text="Quotes"/>" title="<spring:theme code="text.account.quotes" text="Quotes"/>" />
										</a>
									</span>
									<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.account.quotes" text="Quotes"/></a></h3>
									<ul class="ul_account" >
										<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.viewQuotes" text="View my quotes"/></a></li>
									</ul>
								</div>
							</sec:authorize>
						</c:if>
						
						<sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER,ROLE_AXISONLYREPORTS" >
						<div class="cust_acc_tile">
							<c:url value="/my-account/document/list" var="encodedUrl" />
							<span>
								<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/history.png" alt="<spring:theme code="text.account.onlyDocuments" text="Documents"/>" title="<spring:theme code="text.account.orderHistory" text="Order History"/>" />
								</a>
							</span>
							<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.account.onlyDocuments" text="Documents"/></a></h3>
							<ul class="ul_account" >
								
								<c:url value="/my-account/document/list" var="encodedUrl2" />
								<c:if test="${baseStore.showDocumentSearch eq true}">
									<li  class="ul_account_item" ><a href="${encodedUrl2}"><spring:theme code="text.account.documentsReport" text="View documents"/></a></li>
								</c:if>
								<c:if test="${baseStore.showAccountBalance eq true}">
									<c:url value="/my-account/balance-statement" var="encodedUrl" />
									<c:url value="/my-account/balance-statement" var="encodedUrl3" />
									<li  class="ul_account_item" ><a href="${encodedUrl3}"><spring:theme code="text.account.balanceStatement.account" text="Balance report"/></a></li>
								</c:if>
								
								<sec:authorize ifAnyGranted="ROLE_PURCHASER" >
									<c:url value="/my-account/backorder/list" var="encodedUrl1" />
									<c:if test="${baseStore.showBackorder eq true}">
										<li  class="ul_account_item" ><a href="${encodedUrl1}"><spring:theme code="text.account.backorderReport" text="Backorder report"/></a></li>
									</c:if>
									
									<c:if test="${baseStore.showOrdersAndQuotes eq true}">
										<c:url value="/my-account/orders" var="encodedUrl" />
										<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.viewOrderHistory" text="View order history"/></a></li>
									</c:if>
								</sec:authorize>
								
							</ul>
							</div>
						</sec:authorize>

						<sec:authorize ifAnyGranted="ROLE_MATERIAL_SUPPLIER_RECEPTION">
							<div class="cust_acc_tile">
								<c:url value="/my-account/document/listMaterial" var="encodedUrl" />
								<span>
									<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/history.png" alt="<spring:theme code="text.account.orderHistory" text="Order History"/>" title="<spring:theme code="text.account.orderHistory" text="Order History"/>" />
									</a>
								</span>
								<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.account.onlyDocuments" text="Documents"/></a></h3>
								<ul class="ul_account" >
									
									<c:url value="/my-account/document/listMaterial" var="encodedUrl2" />
									<c:if test="${baseStore.showDocumentSearch eq true}">
										<li  class="ul_account_item" ><a href="${encodedUrl2}"><spring:theme code="text.account.documentsReport" text="View documents"/></a></li>
									</c:if>
									
								</ul>
							</div>
						</sec:authorize>
						
						<c:if test="${baseStore.showIncidentsModule eq true}">
							<sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER,ROLE_QUALITY,ROLE_INCIDENT_USER" >
								<div class="cust_acc_tile">
									<c:url value="/incident/addIncident" var="addIncidentEncodedUrl" />
									<c:url value="/incident/incidentList" var="incidentListEncodedUrl" />
									<span>
										<a class="account_img" href="${incidentListEncodedUrl}"><img src="${themeResourcePath}/images/Icono-Claims.png" alt="<spring:theme code="incident.list.title" />" title="<spring:theme code="incident.list.title" />" />
										</a>
									</span>
									<h3 class="ul_account_item"  ><a href="${incidentListEncodedUrl}"><spring:theme code="incident.title" /></a></h3>
									<ul class="ul_account" >
										<c:url value="/my-account/balance-statement" var="encodedUrl1" />
											<li  class="ul_account_item" ><a href="${incidentListEncodedUrl}"><spring:theme code="incident.list.title" /></a></li>
											<li  class="ul_account_item" ><a href="${addIncidentEncodedUrl}"><spring:theme code="incident.add.title" /></a></li>
									</ul>
								</div>
							</sec:authorize>
						</c:if>
						
							<c:if test="${baseStore.showOptStudioReports eq true}">
							<sec:authorize ifAnyGranted="ROLE_ACCESS_OPSTUDIO_REPORTS">
							
							
							
								<c:choose>
									<c:when test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
										<div class="cust_acc_tile">
									<c:url value="/my-account/balance-statement" var="encodedUrl" />
									<span>
										<a class="account_img" ><img src="${themeResourcePath}/images/icon-alert.png" alt="<spring:theme code="opstudio.title" text="Reports"/>" title="<spring:theme code="opstudio.title" text="Reports"/>" />
										</a>
									</span>
									<h3 class="ul_account_item" style="color: black;" ><spring:theme code="opstudio.title" text="Reports"/></h3>
									<ul class="ul_account" >
									
									<li  class="ul_account_item" ><a href="http://plmescopt1.prolamsa.com/WebReports/Default.aspx"><spring:theme code="text.account.ReportePedidos" /></a></li>
									<li  class="ul_account_item" ><a href="http://plmescopt1.prolamsa.com/WebReports/Default.aspx"><spring:theme code="text.account.ReporteCalibres" /></a></li>
										
								</ul>
								</div>
								
									</c:when>
									<c:when test="${baseStore.uid eq '2000'}">
									<div class="cust_acc_tile">
									<c:url value="/my-account/balance-statement" var="encodedUrl" />
									<span>
										<a class="account_img" ><img src="${themeResourcePath}/images/icon-alert.png" alt="<spring:theme code="opstudio.title" text="Reports"/>" title="<spring:theme code="opstudio.title" text="Reports"/>" />
										</a>
									</span>
									<h3 class="ul_account_item" style="color: black;" ><spring:theme code="opstudio.title" text="Reports"/></h3>
									<ul class="ul_account" >
									
										
									
									<c:url value="/reports/wsOrderReport" var="encodedUrlReport1" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport1}"><spring:theme code="text.account.orderReport" /></a></li>
										<c:url value="/reports/wsOrderReportV2" var="encodedUrlReport2" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport2}"><spring:theme code="text.account.orderReportV2" /></a></li>
										<c:url value="/reports/wsRiskProductionScheduleReport" var="encodedUrlReport3" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport3}"><spring:theme code="text.account.orderReportRisk" /></a></li>
										<c:url value="/reports/wsRollingScheduleGeneral" var="encodedUrlReport4" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport4}"><spring:theme code="text.account.rollingScheduleReport" /></a></li>
									
								</ul>
								</div>
									</c:when>
							
							
							<c:otherwise>
							<div class="cust_acc_tile">
									<span>
										<a class="account_img" ><img src="${themeResourcePath}/images/icon-alert.png" alt="Rolling Schedule Reports" title="Rolling Schedule Reports" />
										</a>
									</span>
									<h3 class="ul_account_item" style="color: black;" >Rolling Schedule Reports</h3>
									<ul class="ul_account" >	
									
										<li  class="ul_account_item" ><a href="http://axisbynoptp02-v/webreports/Default.aspx">Rolling Schedule Reports</a></li>
										
								</ul>
								</div>
							
							
							<%-- <div class="cust_acc_tile">
									<c:url value="/my-account/balance-statement" var="encodedUrl" />
									<span>
										<a class="account_img" ><img src="${themeResourcePath}/images/icon-alert.png" alt="<spring:theme code="opstudio.title" text="Reports"/>" title="<spring:theme code="opstudio.title" text="Reports"/>" />
										</a>
									</span>
									<h3 class="ul_account_item" style="color: black;" ><spring:theme code="opstudio.title" text="Reports"/></h3>
									<ul class="ul_account" >
							
								
										<c:url value="/reports/wsOrderReport" var="encodedUrlReport1" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport1}"><spring:theme code="text.account.orderReport" /></a></li>
										<c:url value="/reports/wsOrderReportV2" var="encodedUrlReport2" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport2}"><spring:theme code="text.account.orderReportV2" /></a></li>
										<c:url value="/reports/wsRiskProductionScheduleReport" var="encodedUrlReport3" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport3}"><spring:theme code="text.account.orderReportRisk" /></a></li>
										<c:url value="/reports/wsRollingScheduleGeneral" var="encodedUrlReport4" />
										<li  class="ul_account_item" ><a href="${encodedUrlReport4}"><spring:theme code="text.account.rollingScheduleReport" /></a></li>
									</ul>
								</div> --%>
							</c:otherwise>		
									</c:choose>
							</sec:authorize>
						</c:if>
					
					
					<c:if test="${baseStore.showOptStudioReports eq true}">
							<sec:authorize ifAnyGranted="ROLE_ACCESS_OPSTUDIO_REPORTS">
							
							<c:choose>
								<c:when test="${baseStore.uid eq '2000'}">
									<div class="cust_acc_tile">
									<c:url value="/my-account/balance-statement" var="encodedUrl" />
									<span>
										<a class="account_img" ><img src="${themeResourcePath}/images/icon-alert.png" alt="<spring:theme code="opstudio.title" text="Reports"/>" title="<spring:theme code="opstudio.title" text="Reports"/>" />
										</a>
									</span>
									<h3 class="ul_account_item" style="color: black;" ><spring:theme code="opstudio.title" text="Reports"/></h3>
									<ul class="ul_account" >
									
									<li  class="ul_account_item" ><a href="http://plmescopt1.prolamsa.com/WebReports/Default.aspx"><spring:theme code="text.account.ReportePedidos" /></a></li>
									<li  class="ul_account_item" ><a href="http://plmescopt1.prolamsa.com/WebReports/Default.aspx"><spring:theme code="text.account.ReporteCalibres" /></a></li>
										
								</ul>
								</div>
								</c:when>
							</c:choose>
							</sec:authorize>
					</c:if>
						
						
						<!-- MX 
						<c:if test="${baseStore.showOptStudioReports eq true}">
							<sec:authorize ifAnyGranted="ROLE_ACCESS_OPSTUDIO_REPORTS">
								<div class="cust_acc_tile">
									<c:url value="/my-account/balance-statement" var="encodedUrl" />
									<span>
										<a class="account_img" ><img src="${themeResourcePath}/images/icon-alert.png" alt="<spring:theme code="opstudio.title" text="Reports"/>" title="<spring:theme code="opstudio.title" text="Reports"/>" />
										</a>
									</span>
									<h3 class="ul_account_item" style="color: black;" ><spring:theme code="opstudio.title" text="Reports"/> MX</h3>
									<ul class="ul_account" >
									
										<c:choose>
											<c:when test="${baseStore.uid eq '1000'}">
												<c:url value="/reports/wsReportePedidos" var="encodedUrlReport1" />
												<li  class="ul_account_item" ><a href="${encodedUrlReport1}"><spring:theme code="text.account.ReportePedidos" /></a></li>
												<c:url value="/reports/wsReporteCalibres" var="encodedUrlReport2" />
												<li  class="ul_account_item" ><a href="${encodedUrlReport2}"><spring:theme code="text.account.ReporteCalibres" /></a></li>
											 
											 </c:when>
											<c:when test="${baseStore.uid eq '2000'}">
												<c:url value="/reports/wsReportePedidos" var="encodedUrlReport1" />
												<li  class="ul_account_item" ><a href="${encodedUrlReport1}"><spring:theme code="text.account.ReportePedidos" /></a></li>
												<c:url value="/reports/wsReporteCalibres" var="encodedUrlReport2" />
												<li  class="ul_account_item" ><a href="${encodedUrlReport2}"><spring:theme code="text.account.ReporteCalibres" /></a></li>
											 </c:when>
											<c:otherwise>
											</c:otherwise>
										
										</c:choose>
										
									</ul>
								</div>
							</sec:authorize>
						</c:if>
						-->
					</sec:authorize>
				<!-- B2BCUSTOMER GROUP ENDS -->
					
				<!-- B2BAPPROVER GROUP STARTS -->
					<sec:authorize ifAllGranted="ROLE_B2BAPPROVERGROUP">
						<div class="cust_acc_tile">
							<c:url value="/my-account/approval-dashboard" var="encodedUrl" />
							<span>
								<a class="account_img" href="${encodedUrl}"><img src="${themeResourcePath}/images/balence.png" alt="<spring:theme code="text.account.orderApproval" text="Order Approval"/>" title="<spring:theme code="text.account.orderApproval" text="Order Approval"/>" />
								</a>
							</span>
							<h3 class="ul_account_item"  ><a href="${encodedUrl}"><spring:theme code="text.account.orderApproval" text="Order Approval"/></a></h3>
							<ul class="ul_account" >
								<li  class="ul_account_item" ><a href="${encodedUrl}"><spring:theme code="text.account.viewOrderApproval" text="View orders that require approval"/></a></li>
							</ul>
						</div>
					</sec:authorize>
				<!-- B2BAPPROVER GROUP ENDS -->
				</div>
			</div>
		</div>
	</div>
	<br>
</template:page>
