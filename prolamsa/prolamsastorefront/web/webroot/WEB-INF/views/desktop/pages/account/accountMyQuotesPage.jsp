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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<template:page pageTitle="${pageTitle}">

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
			<div class="title_holder">
				<h2><spring:theme code="text.account.quotes.myquotes" text="My Quotes"/></h2>
			</div>
			
			<order:quoteHistorySearchForm/>
			
            <br/> <br/>
             
			<div class="item_container">
				<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
					<thead class="">
						<tr class="firstrow">
	                        <td class="balanceStatement-customer noBorder"  align="center"><h3><spring:theme code="text.account.orderHistory.customer" /></h3></td>
							<td class="balanceStatement-customer noBorder">	<h3><spring:theme code="text.account.orderHistory.customerName" /></h3></td>	                        																									
							<td class="balanceStatement-customer noBorder"><h3><spring:theme code="text.account.quoteHistory.quoteNumber" text="Quote Number"/></h3></td>
							<td class="balanceStatement-customer noBorder"><h3><spring:theme code="text.account.quoteHistory.quoteStatus" text="Quote Status"/></h3></td>
							<td class="balanceStatement-customer noBorder"><h3><spring:theme code="text.account.quoteHistory.purchaseOrderNumber" text="P.O.No"/></h3></td>
							<td class="balanceStatement-customer noBorder"><h3><spring:theme code="text.account.quoteHistory.datePlaced" text="Date Placed"/></h3></td>
							<td class="balanceStatement-customer noBorder" align="center"><h3><spring:theme code="text.account.quoteHistory.actions" text="Actions"/></h3></td>
						</tr>
					</thead>
					<tbody class="">								
							<c:forEach items="${searchPageData.results}" var="quote">
								<c:url value="/my-account/my-quote/${quote.code}" var="orderQuoteLink" />
								<tr>
								   <td>
										<ycommerce:testId code="quoteHistory_customer">
											${quote.unit.uid}
										</ycommerce:testId>
									</td>
									<td>
										<ycommerce:testId code="quoteHistory_customerName">
											${quote.unit.shortName}
										</ycommerce:testId>
									</td>
								
									<td>
										<ycommerce:testId code="quoteHistory_orderNumber_link">
											<a href="${orderQuoteLink}">${quote.code}</a>
										</ycommerce:testId>
									</td>
									<td>
										<ycommerce:testId code="quoteHistory_orderStatus_label">
											<spring:theme code="text.account.order.status.display.${quote.statusDisplay}"/>
										</ycommerce:testId>
									</td>
									<td>
										<ycommerce:testId code="quoteHistory_purchaseOrderNumber_label">
											${quote.purchaseOrderNumber}
										</ycommerce:testId>
									</td>
									<td>
										<ycommerce:testId code="quoteHistory_orderDate_label">
											<formDate:formFormatDate pattern="MM/dd/yyyy" value="${quote.created}" />
										</ycommerce:testId>
									</td>

									<td align="center">
										<ycommerce:testId code="quoteHistory_Actions_links">
											<a href="${orderQuoteLink}"><spring:theme code="text.view" text="View"/></a>
											<a href="javascript:void(0)" id="myLinkQuote_${quote.code}_${quote.unit.uid}" class="myLinkQuote" style="margin-left: 10px;">
												<img src="${themeResourcePath}/images/icon-download-black-16.png" alt="<spring:theme code="text.download" text="Download"/>" style="vertical-align:middle;"/>
											</a>
										</ycommerce:testId>
									</td>
								</tr>
							</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
			<div class="prod_refine-wrapper top-border-separator-line">
				<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
					supportShowAll="${isShowAllAllowed}"
					searchPageData="${searchPageData}"							
					searchUrl="${quoteHistoryBaseUrl}?quote=${quoteHistorySearchForm.quoteNumber}&customer=${quoteHistorySearchForm.customer}&status=${quoteHistorySearchForm.status}&sort=${quoteHistorySearchForm.sort}&deliveryAddress=${quoteHistorySearchForm.deliveryAddress}&initialDate=${quoteHistorySearchForm.initialDate}&finalDate=${quoteHistorySearchForm.finalDate}&poNumber=${quoteHistorySearchForm.poNumber}"	
					msgKey="text.account.quoteHistory.page"
					numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
			</div>
			</c:if>	
		</div>
		
		<form id="transferDocumentForm"  target="iframePost" action="<spring:url value="/my-account/document/transferDocument" />" >
			<input type="hidden" id="documentNumberField" name="documentNumber" />
			<input type="hidden" id="documentOwnerField" name="documentOwner" />			
			<input type="hidden" id="transferTypeField" name="transferType" />
			<input type="hidden" id="documentTypeField" name="documentType" />
		</form>
		
		<iframe name="iframePost" style="visibility: hidden; display:none;">
		</iframe>
	</div>
	
<script type="text/javascript" >
onDOMLoaded(initQuotes);
	
function initQuotes()
{
	ACC.neorisDownloadDocuments.initialize({
		transferModalMessages: {
			title: "<spring:theme code="quote.modal.download.title"/>",
			content: "<spring:theme code="quote.modal.download.content"/>",
			button1: "<spring:theme code="quote.modal.download.button.download"/>",
			button2: "<spring:theme code="quote.modal.download.button.email"/>"
		}
	});
	
	jQuery("#documentTypeField").val('S');
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	
	$("#balance_statement tr:even").addClass("filaImpar");
    $("#balance_statement tr:odd").addClass("filaPar");
}
	
function submitForm()
{		
	var val = jQuery(this).val();				
	jQuery("#sortField").val(val);		
	jQuery("#searchForm").submit();			
}
</script>
</template:page>