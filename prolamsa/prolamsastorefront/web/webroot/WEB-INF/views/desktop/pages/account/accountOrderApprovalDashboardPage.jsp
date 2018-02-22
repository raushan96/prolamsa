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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%-- 
<template:page pageTitle="${pageTitle}">

<c:set var="appBaseUrl" value="/my-account/approval-dashboard" />
<spring:url var="appSearchUrl" value="${appBaseUrl}" />

<div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
	
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
			<div id="globalMessages">
				<common:globalMessages/>
			</div>
		</div>
	</div>		
	
	<div class="grid-container">		
	    <nav:accountNav selected="${cmsPage.label}"/>    	
		
		<div class="span-20 last main-right">
			    
	    <!-- *************************************************** -->
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
                    <!-- NEORIS_CHANGE #incidencia 181 JCVM 26/08/2014 Se alinea el boton Search dado que aparece abajo. -->
					<div class="search-form" style="display: block;">
						<form:form  id="searchForm" class="js-form" action="${appSearchUrl}" method="get">
												
		  					<!--
		  					<input type="hidden" id="_export" name="_export" value="" />
		  					-->
		  					
		  					<div style="height:  30px; width: 500px; float: right;">
		  					
		  					<formUtil:formButton
								id="balanceStatementSearchSubmit"
								type="submit" 
								css="button-searchSU positive button-float-rigth"
								tabindex="106"
								springThemeText="text.account.balanceStatement.button.search"/>
								
							

		  					<spring:theme var="placeHolder" code="text.account.balanceStatement.list.customer" />
				   			<c:set var="selected" value="" />
					
							<c:set var="allClients" value=""/>
							<c:forEach varStatus="status" items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
								<c:choose>
									<c:when test="${status.first}">
										<c:set var="allClients" value="${eachFormattedB2BUnit.uid}-"/>
									</c:when>
									<c:when test="${status.last}">
										<c:set var="allClients" value="${allClients}${eachFormattedB2BUnit.uid}"/>
									</c:when>
									<c:otherwise>
										<c:set var="allClients" value="${allClients}${eachFormattedB2BUnit.uid}-"/>
									</c:otherwise>
								</c:choose>
								</c:forEach> 
							<select id="b2bUnit" name="b2bUnit"  style="float: right;">
								<option value="${allClients }"><spring:theme code="text.account.quotes.allClients" text="My Quotes"/></option>
						    	<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
							    	<c:choose>
										<c:when test="${eachFormattedB2BUnit.uid eq neorisOrderApprovalSearchForm.b2bUnit}">
											<c:set var="selected" value="selected" />
										</c:when>
										<c:otherwise>
											<c:set var="selected" value="" />
										</c:otherwise>
									</c:choose>
							   		<option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
								</c:forEach>
							</select>
					   			
							<span class="labelSmall" style="float: right;"><spring:theme code="text.account.balanceStatement.list.client"/></span>
							
							<input type="hidden" id="sortField" name="sort" value="" />
							</div>
						</form:form>
					</div>
					<h2><spring:theme code="text.account.orderApprovalDashboard" text="Order Approval Dashboard"/></h2>
				</div>
				<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				<div class="prod_refine-wrapper">
					<nav:paginationReport
						top="true" 
						supportShowPaged="${isShowPageAllowed}" 
						supportShowAll="${isShowAllAllowed}" 
						searchPageData="${searchPageData}" 
						searchUrl="/my-account/approval-dashboard?sort=${neorisOrderApprovalSearchForm.sort}&b2bUnit=${neorisOrderApprovalSearchForm.b2bUnit}" 
						msgKey="text.account.orderHistory.page" 
						numberPagesShown="${numberPagesShown}"
						includeSortDropdown="true"/>
				</div>	
				  </c:if>	
				<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
            <br> <br> 
				<form:form modelAttribute="quoteOrderForm" method="post" action="${quotesSearchUrl}" class="js-form js-invoice-selected">
<div class="item_container">
	<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="firstrow">
									<td><spring:theme code="text.account.orderApprovalDashBoard.orderNumber" text="Order Number"/></td>
									<td><spring:theme code="text.account.order.purchase" text="Purchaser"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.purchaseOrderNumber" text="P.O.No"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.client" text="P.O.No"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.orderCreated" text="Order Created"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.orderStatus" text="Order Status"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.actions" text="Actions"/></td>
								</tr>
							</thead>
							<tbody class="">
							<c:if test="${not empty searchPageData.results}">
								
									<c:forEach items="${searchPageData.results}" var="order">
									<tr>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderNumber_link">
												<a href="orderApprovalDetails/${order.workflowActionModelCode}">${order.b2bOrderData.code}</a>
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderpurchaser_link">
												${order.b2bOrderData.b2bCustomerData.name}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_purchaseOrderNumber_label">
												${order.b2bOrderData.purchaseOrderNumber}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_Client_label">
												${order.b2bOrderData.costCenter.unit.name}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
												${order.b2bOrderData.created}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
												<spring:theme code="text.account.order.status.display.${order.b2bOrderData.statusDisplay}"/>
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_Actions_links">
												<a href="orderApprovalDetails/${order.workflowActionModelCode}"><spring:theme code="text.view" text="View"/></a>
											</ycommerce:testId>
										</td>
									</tr>
								</c:forEach>
								
							</c:if>
							 <c:if test="${empty searchPageData.results}">
								<p><spring:theme code="text.account.quotes.noQuotes" text="You have no quotes"/></p>
							</c:if>
							</tbody>
						</table>
					</div>	
				</form:form>
				
				<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				<div class="prod_refine-wrapper">
					<nav:paginationReport
						top="true" 
						supportShowPaged="${isShowPageAllowed}" 
						supportShowAll="${isShowAllAllowed}" 
						searchPageData="${searchPageData}" 
						searchUrl="/my-account/approval-dashboard?sort=${neorisOrderApprovalSearchForm.sort}&b2bUnit=${neorisOrderApprovalSearchForm.b2bUnit}" 
						msgKey="text.account.orderHistory.page" 
						numberPagesShown="${numberPagesShown}"
						includeSortDropdown="true"/>
				</div>
				</c:if>
			</div>
		</div>
	</div>
	<br>
	
		<script type="text/javascript">
onDOMLoaded(initOrderApprovaltList);

function initOrderApprovaltList()
{	
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#orderApprovalSearchSubmit").click(submitForm);
}

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

</script>
</template:page> 	

TERMINA MODIFICADO --%>

 <template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	
	<div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
	
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
			<div id="globalMessages">
				<common:globalMessages/>
			</div>
		</div>
	</div>	
	
	<%-- <div class="grid-container">
		<nav:accountNav selected="approval-dashboard" />   	
		<div class="span-20 last main-right">
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
					<div class="search-form" style="display: block;"> --%>
	
	<div class="grid-container">		
	    <nav:accountNav selected="${cmsPage.label}"/>    	
		
		<div class="span-20 last main-right">
			    
	    <!-- *************************************************** -->
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
                    <div class="search-form" style="display: block;">
					
						<form:form  id="searchForm" class="js-form" action="${balanceStatementListSearchUrl}" method="get">
							<input type="hidden" id="_export" name="_export" value="" />
							
							<div style="height:  30px; width: 500px; float: right;">
							
							<formUtil:formButton
								id="balanceStatementSearchSubmit"
								type="submit" 
								css="button-searchSU positive"
								tabindex="106"
								springThemeText="text.account.balanceStatement.button.search" />
							
							<c:set var="selected" value="" />
	
							<c:set var="allClients" value=""/>
							
							<c:forEach varStatus="status" items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
								<c:choose>
									<c:when test="${status.first}">
										<c:set var="allClients" value="${eachFormattedB2BUnit.uid}-"/>
									</c:when>
									<c:when test="${status.last}">
										<c:set var="allClients" value="${allClients}${eachFormattedB2BUnit.uid}"/>
									</c:when>
									<c:otherwise>
										<c:set var="allClients" value="${allClients}${eachFormattedB2BUnit.uid}-"/>
									</c:otherwise>
								</c:choose>
							</c:forEach> 
							<select id="b2bUnit" name="b2bUnit" style="float: right;">
								<option value="all"><spring:theme code="text.account.quotes.allClients" text="My Quotes"/></option>
						    
								<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
							   
								   <c:choose>
										<c:when test="${eachFormattedB2BUnit.uid eq neorisOrderApprovalSearchForm.b2bUnit}">
											<c:set var="selected" value="selected" />
										</c:when>
										<c:otherwise>
											<c:set var="selected" value="" />
										</c:otherwise>
									</c:choose>
							   
							   	<option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
								</c:forEach>
							</select>
							
							<span class="labelSmall" style="float: right;"><spring:theme code="text.account.balanceStatement.list.client"/></span>
	
							<input type="hidden" id="sortField" name="sort" value="" />
							</div>
						</form:form>
					</div>
					<h2><spring:theme code="text.account.orderApprovalDashboard" text="Order Approval Dashboard"/></h2>
				</div>
				<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >		
				<div class="prod_refine-wrapper">
					<nav:paginationReport
						top="true" 
						supportShowPaged="${isShowPageAllowed}" 
						supportShowAll="${isShowAllAllowed}" 
						searchPageData="${searchPageData}" 
						searchUrl="/my-account/approval-dashboard?sort=${neorisOrderApprovalSearchForm.sort}&b2bUnit=${neorisOrderApprovalSearchForm.b2bUnit}" 
						msgKey="text.account.orderHistory.page" 
						numberPagesShown="${numberPagesShown}"
						includeSortDropdown="true"/>	 			
				</div>
				</c:if>
				<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
            <br/>
				<form:form>
					<div class="item_container">
						<table id="order_history">
							<thead>
								<tr class="firstrow">
									<td><spring:theme code="text.account.orderApprovalDashBoard.orderNumber" text="Order Number"/></td>
									<td><spring:theme code="text.account.order.purchase" text="Purchaser"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.purchaseOrderNumber" text="P.O.No"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.client" text="P.O.No"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.orderCreated" text="Order Created"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.orderStatus" text="Order Status"/></td>
									<td><spring:theme code="text.account.orderApprovalDashBoard.actions" text="Actions"/></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${searchPageData.results}" var="order">
									<tr>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderNumber_link">
												<a href="orderApprovalDetails/${order.workflowActionModelCode}">${order.b2bOrderData.code}</a>
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderpurchaser_link">
												${order.b2bOrderData.b2bCustomerData.name}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_purchaseOrderNumber_label">
												${order.b2bOrderData.purchaseOrderNumber}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_Client_label">
												${order.b2bOrderData.costCenter.unit.name}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
												${order.b2bOrderData.created}
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
												<spring:theme code="text.account.order.status.display.${order.b2bOrderData.statusDisplay}"/>
											</ycommerce:testId>
										</td>
										<td>
											<ycommerce:testId code="orderApprovalDashboard_Actions_links">
												<a href="orderApprovalDetails/${order.workflowActionModelCode}"><spring:theme code="text.view" text="View"/></a>
											</ycommerce:testId>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</form:form>
				<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				<div class="prod_refine-wrapper">
					<nav:paginationReportFooter
						top="false" 
						supportShowPaged="${isShowPageAllowed}" 
						supportShowAll="${isShowAllAllowed}" 
						searchPageData="${searchPageData}" 
						searchUrl="/my-account/approval-dashboard?sort=${neorisOrderApprovalSearchForm.sort}&b2bUnit=${neorisOrderApprovalSearchForm.b2bUnit}" 
						msgKey="text.account.orderHistory.page" 
						numberPagesShown="${numberPagesShown}"
						includeSortDropdown="true"/>	
				</div>
				</c:if>
			</div>
		</div>
	</div>
	<br/>
		<script type="text/javascript">
onDOMLoaded(initOrderApprovaltList);

function initOrderApprovaltList()
{	
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#orderApprovalSearchSubmit").click(submitForm);
	
	jQuery("#order_history tr:even").addClass("filaImpar");
	jQuery("#order_history tr:odd").addClass("filaPar");
}

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

</script>
</template:page> 

<%--
<template:page pageTitle="${pageTitle}">
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<nav:accountNav selected="approval-dashboard" />
	<div class="span-20 last">
		<div class="item_container_holder">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2><spring:theme code="text.account.orderApprovalDashboard" text="Order Approval Dashboard"/></h2>
			</div>
&nbsp; <!-- required to break flow of float h2 -->
<div class="search-form" style="display: block;">
	<form:form  id="searchForm" class="js-form" action="${balanceStatementListSearchUrl}" method="get">
	<input type="hidden" id="_export" name="_export" value="" />

	<table>
		<tr>
			<td>
			  	 
			  	 <c:set var="selected" value="" />

					<span><spring:theme code="text.account.balanceStatement.list.client"/></span>
					<c:set var="allClients" value=""/>
					
					<c:forEach varStatus="status" items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
						<c:choose>
							<c:when test="${status.first}">
								<c:set var="allClients" value="${eachFormattedB2BUnit.uid}-"/>
							</c:when>
							<c:when test="${status.last}">
								<c:set var="allClients" value="${allClients}${eachFormattedB2BUnit.uid}"/>
							</c:when>
							<c:otherwise>
								<c:set var="allClients" value="${allClients}${eachFormattedB2BUnit.uid}-"/>
							</c:otherwise>
						</c:choose>
					</c:forEach> 
					<select id="b2bUnit" name="b2bUnit">
						<option value="${allClients }"><spring:theme code="text.account.quotes.allClients" text="My Quotes"/></option>
				    
						<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
					   
						   <c:choose>
								<c:when test="${eachFormattedB2BUnit.uid eq neorisOrderApprovalSearchForm.b2bUnit}">
									<c:set var="selected" value="selected" />
								</c:when>
								<c:otherwise>
									<c:set var="selected" value="" />
								</c:otherwise>
							</c:choose>
					   
					   	<option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
						</c:forEach>
					</select>
			  	 
			  	 <formUtil:formButton
					id="orderApprovalSearchSubmit"
					type="submit" 
					css="positive"
					tabindex="106"
					springThemeText="text.account.balanceStatement.button.search" />
			</td>
		</tr>
	</table>			

        <input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
</div>

			<div class="item_container">
					<nav:pagination 
						top="true" 
						supportShowPaged="${isShowPageAllowed}" 
						supportShowAll="${isShowAllAllowed}" 
						searchPageData="${searchPageData}" 
						searchUrl="/my-account/approval-dashboard?sort=${neorisOrderApprovalSearchForm.sort}&b2bUnit=${neorisOrderApprovalSearchForm.b2bUnit}" 
						msgKey="text.account.orderHistory.page" 
						numberPagesShown="${numberPagesShown}"
						includeSortDropdown="true"/>

					<form>
						<table id="order_history">
							<thead>
								<tr>
									<th id="header1"><spring:theme code="text.account.orderApprovalDashBoard.orderNumber" text="Order Number"/></th>
									<th id="header2"><spring:theme code="text.account.order.purchase" text="Purchaser"/></th>
									<th id="header3"><spring:theme code="text.account.orderApprovalDashBoard.purchaseOrderNumber" text="P.O.No"/></th>
									<th id="header3"><spring:theme code="text.account.orderApprovalDashBoard.client" text="P.O.No"/></th>
									<th id="header4"><spring:theme code="text.account.orderApprovalDashBoard.orderCreated" text="Order Created"/></th>
									<th id="header5"><spring:theme code="text.account.orderApprovalDashBoard.orderStatus" text="Order Status"/></th>
									<th id="header6"><spring:theme code="text.account.orderApprovalDashBoard.actions" text="Actions"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${searchPageData.results}" var="order">
									<tr>
										<td headers="header1">
											<ycommerce:testId code="orderApprovalDashboard_orderNumber_link">
												<a href="orderApprovalDetails/${order.workflowActionModelCode}">${order.b2bOrderData.code}</a>
											</ycommerce:testId>
										</td>
										<td headers="header2">
											<ycommerce:testId code="orderApprovalDashboard_orderpurchaser_link">
												<p>${order.b2bOrderData.b2bCustomerData.name}</p>
											</ycommerce:testId>
										</td>
										<td headers="header3">
											<ycommerce:testId code="orderApprovalDashboard_purchaseOrderNumber_label">
												<p>${order.b2bOrderData.purchaseOrderNumber}</p>
											</ycommerce:testId>
										</td>
										<td headers="header3">
											<ycommerce:testId code="orderApprovalDashboard_Client_label">
												<p>${order.b2bOrderData.costCenter.unit.name}</p>
											</ycommerce:testId>
										</td>
										<td headers="header4">
											<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
												<p>${order.b2bOrderData.created}</p>
											</ycommerce:testId>
										</td>
										<td headers="header5">
											<ycommerce:testId code="orderApprovalDashboard_orderStatus_label">
												<p><spring:theme code="text.account.order.status.display.${order.b2bOrderData.statusDisplay}"/></p>
											</ycommerce:testId>
										</td>
										<td headers="header6">
											<ycommerce:testId code="orderApprovalDashboard_Actions_links">
												<ul class="updates">
													<li><a href="orderApprovalDetails/${order.workflowActionModelCode}"><spring:theme code="text.view" text="View"/></a></li>
												</ul>
											</ycommerce:testId>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
					<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/my-account/approval-dashboard?sort=${searchPageData.pagination.sort}" msgKey="text.account.orderHistory.page" numberPagesShown="${numberPagesShown}"/>
			</div>
			</div>
		</div>
		</template:page>
		 --%>
		 


