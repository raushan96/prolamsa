<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:url var="addressBookUrl" value="/my-account/address-book" />

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
	
	<div class="grid-container">
		<nav:accountNav selected="${cmsPage.label}"/> 		
	    <div class="span-20 last main-right">
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
					<div class="search-form" style="display: block;">
						<form:form id="searchForm" method="get" action="${addressBookUrl}" class="js-form js-invoice-selected">
							<div style="height:  30px; width: 500px; float: right;">
								<formUtil:formButton
												id="backorderSearchSubmit"
												type="submit" 
												css="button-searchSU positive"
												tabindex="106"
												springThemeText="text.account.balanceStatement.button.search" />
							
							
								
								<select id="customer" name="customer" class="dropDownMedium"  style="float: right;">
								    <option value="">All</option> 
									<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
									    <c:choose>
											<c:when test="${eachFormattedB2BUnit.uid eq addressForm.customer}">
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
										  	 
							</div>				
							<input type="hidden" id="sortField" name="sortBy" value="${addressForm.sortBy}" />
						</form:form>
					</div>
					<h2><spring:theme code="text.account.addressBook" text="Address Book"/></h2>
				</div>
				<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
					<div class="prod_refine-wrapper">
						<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
							supportShowAll="${isShowAllAllowed}"
							searchPageData="${searchPageData}"
							searchUrl="${addressBookUrl}?sort=${addressForm.sortBy}&customer=${addressForm.customer}"
							msgKey="text.account.address"
							numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />	 			
					</div>
				</c:if>
				<br/><br/>
			
				<form:form id="searchForm" method="post" action="${addressBookUrl}" class="js-form js-invoice-selected">
					<div class="item_container">
						<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
							<thead class="">
								<tr class="firstrow">
									<td class="backorder-customer">
										<spring:theme code="text.account.address.billingAddress" text="Billing Address"/>
									</td>
									<td>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.id"/>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.name"/>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.street"/>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.number"/>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.city"/>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.state"/>
									</td>
									<td class="backorder-customer">
									<spring:theme code="text.account.address.country"/>
									</td>
									<td class="backorder-customer">
										<spring:theme code="text.account.address.zipCode"/>
									</td>
								</tr>
							</thead>
							<tbody class="">
								<c:forEach items="${unitList}" var="eachUnitList">
									<c:set var="billingPrinted" value="false" />
									<c:forEach items="${eachUnitList.shippingAddressList}" var="shippingAddress">
										<tr class="">  
											<c:if test="${!billingPrinted}">
												<c:forEach items="${eachUnitList.billingAddressList}" var="billingAddress">
													<td rowspan="${fn:length(eachUnitList.shippingAddressList)}" class="backorder-companyName">
														${billingAddress.name} (${billingAddress.code})<br>
														${billingAddress.line1} ${billingAddress.line2}<br> ${billingAddress.town} <br> ${billingAddress.postalCode}
													</td>
												</c:forEach>
												<c:set var="billingPrinted" value="true" />
											</c:if>
											<!-- empty required as the first column after the rowspan gets a wrong alignment -->
											<td></td>
			
											<td class="backorder-companyName">
													${shippingAddress.code}
											</td>
											<td class="backorder-companyName">
													${shippingAddress.shortName}
											</td>
											<td class="backorder-companyName">
													${shippingAddress.line1}
											</td>
											<td class="backorder-companyName">
													${shippingAddress.line2}
											</td>
											<td class="backorder-companyName">
													${shippingAddress.town}
											</td>
											<td class="backorder-companyName">
													${shippingAddress.region.name}
											</td>
											<td class="backorder-companyName">
												${shippingAddress.country.name}
											</td>
											<td class="backorder-companyName">
													${shippingAddress.postalCode}
											</td>
										</tr>
									</c:forEach>	
								</c:forEach>
							</tbody>
						</table>
					</div>			
				</form:form>
			
				<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
					<div class="prod_refine-wrapper">
						<nav:paginationReportFooter top="false" supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${addressBookUrl}?sort=${addressForm.sortBy}&customer=${addressForm.customer}"
								msgKey="text.account.address"
								numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
					</div>		 			
				</c:if>
			</div>	
		</div>
	</div>
<br>
</template:page>

<script>

onDOMLoaded(bind_AddressBookForm);

function bind_AddressBookForm()
{
	jQuery("#customer").on('change', submitForm);
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
}

function submitFormFromDropDown()
{
	var val = jQuery(this).val();		
	jQuery("#sortField").val(val);
	submitForm();
}

function submitForm()
{
	jQuery("#searchForm").submit();
}
</script>