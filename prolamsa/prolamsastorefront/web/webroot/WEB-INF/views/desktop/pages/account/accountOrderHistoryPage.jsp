<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

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
		<nav:accountNav selected="${cmsPage.label}" />	
	    <div class="span-20 last main-right">
			
			<div class="title_holder">
				<h2><spring:theme code="text.account.orderHistory.title" text="Order History"/></h2>
			</div>
				
			<order:orderHistorySearchForm/>

			<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
			<br/>
			<div class="item_container">
			<table id="order_history">
				<thead>
					<tr class="firstrow">
                              <!-- NEORIS_CHANGE #incidencia 187 JCVM 21/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
                        <td align="center"><h3><spring:theme code="text.account.orderHistory.customer" /></h3></td>
						<td><h3><spring:theme code="text.account.orderHistory.customerName" /></h3></td>
						<c:choose>
							<c:when test="${baseStore.uid eq '5000'}">
								<td><h3><spring:theme code="text.account.orderHistory.orderNumbera4c"/></h3></td>
							</c:when>
							<c:otherwise>
								<td><h3><spring:theme code="text.account.orderHistory.orderNumber"/></h3></td>
							</c:otherwise>
						</c:choose>
						<td><h3><spring:theme code="text.account.orderHistory.orderStatus"/></h3></td>
						<td><h3><spring:theme code="text.account.orderHistory.purchaseOrderNumber"/></h3></td>
						<td><h3><spring:theme code="text.account.orderHistory.datePlaced"/></h3></td>
						<td><h3><spring:theme code="text.account.orderHistory.total" /></h3></td>
						<td><h3><spring:theme code="text.account.orderHistory.shippingTo"/></h3></td>														
						<td><h3><spring:theme code="text.account.orderHistory.actions" /></h3></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${searchPageData.results}" var="order">

						<c:url value="/my-account/order/${order.code}" var="myAccountOrderDetailsUrl"/>

						<tr style="font-size: 25px">
						    <td>
								<ycommerce:testId code="quoteHistory_customer">
									${order.unit.uid}
								</ycommerce:testId>
							</td>
							<td>
								<ycommerce:testId code="quoteHistory_customerName">
									${order.unit.shortName}
								</ycommerce:testId>
							</td>
							<td>
								<ycommerce:testId code="orderHistory_orderNumber_link">
									<a href="${myAccountOrderDetailsUrl}">${order.code}</a>
								</ycommerce:testId>
							</td>
							<td class="invoice-date noBorder Text_Table_Align_Center">
								<ycommerce:testId code="orderHistory_orderStatus_label">
									<spring:theme code="text.account.order.status.display.${order.statusDisplay}"/>
								</ycommerce:testId>
							</td>
							<td>
								<ycommerce:testId code="orderHistory_purchaseOrderNumber_label">
									${order.purchaseOrderNumber}
								</ycommerce:testId>
							</td>
							<td >
								<ycommerce:testId code="orderHistory_orderDate_label">
									<formDate:formFormatDate pattern="MM/dd/yyyy" value="${order.created}" />
								</ycommerce:testId>
							</td>
							<td class="balanceStatement-pastDue noBorder Text_Table_Align_Right">
								<ycommerce:testId code="orderHistory_total_label">
									${order.formattedSapTotalPrice}
								</ycommerce:testId>
							</td>
							<td >
								<ycommerce:testId code="orderHistory_ship-to_label">									
									${order.deliveryAddress.formattedAddress}<br/>
									${order.deliveryAddress.line1}<br/>
									<c:if test="${order.deliveryAddress.line2 != null}">${order.deliveryAddress.line2}<br/></c:if>									
									${order.deliveryAddress.town},${order.deliveryAddress.region.name},${order.deliveryAddress.country.name}
								</ycommerce:testId>
							</td>
							<td>
								<ycommerce:testId code="orderHistory_Actions_links">
									
										<a href="${myAccountOrderDetailsUrl}"><spring:theme code="text.view" text="View"/></a>
									
								</ycommerce:testId>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
			<div class="prod_refine-wrapper top-border-separator-line">
				<nav:paginationReportFooter top="false" supportShowPaged="${isShowPageAllowed}"
					supportShowAll="${isShowAllAllowed}"
					searchPageData="${searchPageData}"							
					searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
					msgKey="text.account.orderHistory.page"
					numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
			</div>
			</c:if>	
		</div>		
					
	</div>
	<br>	
</template:page>

<%--
<template:page pageTitle="${pageTitle}">
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<nav:accountNav selected="${cmsPage.label}" />
	<div class="span-20 last">
		<div class="item_container_holder">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2><spring:theme code="text.account.orderHistory" text="Order History"/></h2>
			</div>
			<div class="item_container">
									
                   <order:orderHistorySearchForm/>
                    <br />
					<div class="">
					<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
						supportShowAll="${isShowAllAllowed}"
						searchPageData="${searchPageData}"							
						searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
						msgKey="text.account.orderHistory.page"
						numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
					</div>

					<table id="order_history">
						<thead>
							<tr>
								<th id="header1"><spring:theme code="text.account.orderHistory.orderNumber" text="Order Number"/></th>
								<th id="header2"><spring:theme code="text.account.orderHistory.orderStatus" text="Order Status"/></th>
								<th id="header3"><spring:theme code="text.account.orderHistory.purchaseOrderNumber" text="P.O.No"/></th>
								<th id="header4"><spring:theme code="text.account.orderHistory.datePlaced" text="Date Placed"/></th>
								<th id="header5"><spring:theme code="text.account.orderHistory.total" text="Total(USD)"/></th>
								<th id="header6"><spring:theme code="text.account.orderHistory.shippingTo" text="Ship-to"/></th>														
								<th id="header7"><spring:theme code="text.account.orderHistory.actions" text="Actions"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${searchPageData.results}" var="order">

								<c:url value="/my-account/order/${order.code}" var="myAccountOrderDetailsUrl"/>

								<tr>
									<td headers="header1">
										<ycommerce:testId code="orderHistory_orderNumber_link">
											<a href="${myAccountOrderDetailsUrl}">${order.code}</a>
										</ycommerce:testId>
									</td>
									<td headers="header2">
										<ycommerce:testId code="orderHistory_orderStatus_label">
											<p><spring:theme code="text.account.order.status.display.${order.statusDisplay}"/></p>
										</ycommerce:testId>
									</td>
									<td headers="header3">
										<ycommerce:testId code="orderHistory_purchaseOrderNumber_label">
											<p>${order.purchaseOrderNumber}</p>
										</ycommerce:testId>
									</td>
									<td headers="header4">
										<ycommerce:testId code="orderHistory_orderDate_label">
											<p><fmt:formatDate pattern="MM/dd/y" value="${order.created}" /></p>
										</ycommerce:testId>
									</td>
									<td headers="header5">
										<ycommerce:testId code="orderHistory_total_label">
											<p class="price right">
												${order.totalPrice.formattedValue} 
											</p>											
										</ycommerce:testId>
									</td>
									<td headers="header6">
										<ycommerce:testId code="orderHistory_ship-to_label">
											<p>${order.deliveryAddress.shortName}</p>
										</ycommerce:testId>
									</td>
									<td headers="header7">
										<ycommerce:testId code="orderHistory_Actions_links">
											<ul class="updates">
												<li><a href="${myAccountOrderDetailsUrl}"><spring:theme code="text.view" text="View"/></a></li>
											</ul>
										</ycommerce:testId>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
										 
					 <div class="">
						<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
							supportShowAll="${isShowAllAllowed}"
							searchPageData="${searchPageData}"							
							searchUrl="${orderHistoryBaseUrl}?order=${orderHistorySearchForm.orderNumber}&customer=${orderHistorySearchForm.customer}&status=${orderHistorySearchForm.status}&sort=${orderHistorySearchForm.sort}&deliveryAddress=${orderHistorySearchForm.deliveryAddress}&initialDate=${orderHistorySearchForm.initialDate}&finalDate=${orderHistorySearchForm.finalDate}&poNumber=${orderHistorySearchForm.poNumber}"	
							msgKey="text.account.orderHistory.page"
							numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
					</div>
				
			</div>
		</div>
	</div>
</template:page>
--%>