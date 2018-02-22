<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<c:set var="invoiceListBaseUrl" value="/invoice/list" />
<spring:url var="invoiceListSearchUrl" value="${invoiceListBaseUrl}" />

<div class="">

<div class="item_container_holder" style="display: block;">

<div class="">
	<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${invoiceListBaseUrl}?sort=${invoiceSearchForm.sort}&initialDate=${invoiceSearchForm.initialDate}&finalDate=${invoiceSearchForm.finalDate}"
		msgKey="invoice.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" />
</div>

<div class="">
	<div class="search-form-options-search-button">
		<formUtil:formButton
			id="exportXLSButton"
			type="button" 
			css="positive"
			tabindex="106"
			springThemeText="general.button.exportXLS" />
		<formUtil:formButton
			id="exportPDFButton"
			type="button" 
			css="positive"
			tabindex="106"
			springThemeText="general.button.exportPDF" />
	</div>

	<form:form id="searchForm" class="js-form" action="${invoiceListSearchUrl}" method="get">
		<input type="hidden" id="_export" name="_export" value="" />

		<div class="">
			<h2>
				<spring:theme code="invoice.list.search" />
			</h2>
		</div>
	
		<div>
			<spring:theme var="placeHolder" code="invoice.list.number" />
						
			<formUtil:formInputTextInline 
				path=""
				id="number"
				mandatory="false"
				type="text"
				name="number"
				value="${invoiceSearchForm.number}"
				tabindex="102"
				divCSS="search-form-options"
				placeholderSpringThemeText="${placeHolder}"
				springThemeText="invoice.list.invoiceNumber"
				asterisk=""
				inputCSS="" />
		
			<spring:theme var="placeHolder" code="invoice.list.dateFrom.inline.${baseStore.uid}" />
		
			<formUtil:formInputTextInline 
				path="" 
				id="initialDate"
				mandatory="false" 
				type="text" 
				name="initialDate" 
				value="${invoiceSearchForm.initialDate}" 
				tabindex="103" 
				divCSS="search-form-options"
				placeholderSpringThemeText="${placeHolder}" 
				springThemeText="invoice.list.dateFrom" 
				asterisk="" 
				inputCSS="" />
		
			<spring:theme var="placeHolder" code="invoice.list.dateTo.inline.${baseStore.uid}" />
		
			<formUtil:formInputTextInline 
				path="" 
				id="finalDate"
				mandatory="false" 
				type="text" 
				name="finalDate" 
				value="${invoiceSearchForm.finalDate}" 
				tabindex="104" 
				divCSS="search-form-options"
				placeholderSpringThemeText="${placeHolder}" 
				springThemeText="invoice.list.dateTo" 
				asterisk="" 
				inputCSS="" />
		
			<div class="search-form-options">
				<formUtil:formSortDropdown
					sorts="${sorts}"
					divCSS="search-form-options"
					springThemeText="sort.by" />
			</div>
	
			<div class="search-form-options-search-button">
				<formUtil:formButton
					id="invoiceSearchSubmit"
					type="submit" 
					css="positive"
					tabindex="106"
					springThemeText="invoice.button.search" />
			</div>
	
		</div>
	</form:form>
</div>

<form:form modelAttribute="invoiceForm" method="post" action="${invoiceListSearchUrl}" class="js-form js-invoice-selected">

<div class="item_container">
	<table id="order_history" class="invoice-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="">
				<td class="invoice-number">
					<h3><spring:theme code="invoice.list.invoiceNumber" /></h3>
				</td>
				<td class="invoice-amount">
					<h3><spring:theme code="invoice.list.originalAmount" /></h3>
				</td>
				<td class="invoice-amount">
					<h3><spring:theme code="invoice.list.balanceAmount" /></h3>
				</td>
				<td class="invoice-date">
					<h3><spring:theme code="invoice.list.invoiceDate" /></h3>
				</td>
			</tr>
		</thead>

		<tbody class="">
			<c:forEach items="${searchPageData.results}" var="invoice" varStatus="status">
				<c:url value="/invoice/detail/${invoice.number}" var="invoiceDetailUrl" />
	
				<tr class="">
					<td class="invoice-number">
						<ycommerce:testId code="invoiceHistory_invoiceNumber_link_${invoice.number}">
							<c:url value="/invoice/detail/?invoiceNumber=${invoice.number}" var="invoiceDetail" />
							<%-- <commonUtil:link href="${invoiceDetail}" springThemeText="${invoice.number}" /> --%>
							<a href="${invoiceDetailUrl}">${invoice.number}</a>
						</ycommerce:testId>
					</td>
	
					<td class="invoice-amount">
						<ycommerce:testId code="invoiceHistory_amount_label_${invoice.number}">
							<p class="price">
								<fmt:formatNumber type="currency" value="${invoice.originalAmount.value}" maxFractionDigits="2" currencySymbol="$" />
							</p>
						</ycommerce:testId>
					</td>

					<td class="invoice-amount">
						<ycommerce:testId code="invoiceHistory_amount_label_${invoice.number}">
							<p class="price">
								<fmt:formatNumber type="currency" value="${invoice.balanceAmount.value}" maxFractionDigits="2" currencySymbol="$" />
							</p>
						</ycommerce:testId>
					</td>

					<td class="invoice-date">
						<ycommerce:testId code="invoiceHistory_invoiceDate_label_${invoice.number}">
							<p>
								<formDate:formFormatDate pattern="MM/dd/yyyy" value="${invoice.dueDate}" />
							</p>
						</ycommerce:testId>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

</form:form>


<div class="">
	<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${invoiceListBaseUrl}?sort=${searchPageData.pagination.sort}&page=${searchPageData.pagination.currentPage}&initialDate=${invoiceSearchForm.initialDate}&finalDate=${invoiceSearchForm.finalDate}"
		msgKey="invoice.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" />
</div>

</div>
</div>

<iframe name="iframePost" style="visibility: hidden;"></iframe>

<script type="text/javascript">
onDOMLoaded(initInvoiceList);

function initInvoiceList()
{
	jQuery("#initialDate").datepicker({dateFormat: 'mm-dd-yy'});
	jQuery("#finalDate").datepicker({dateFormat: 'mm-dd-yy'});
	
	jQuery("#exportPDFButton").click(exportPDFAction);
	jQuery("#exportXLSButton").click(exportXLSAction);
}

function exportPDFAction()
{
	exportTo("pdf");
}

function exportXLSAction()
{
	exportTo("xls");
}

function exportTo(exportType)
{
	var searchForm = jQuery("#searchForm");
	
	// change form parameters to post on iframe
	jQuery(searchForm).attr("target", "iframePost");
	jQuery("#_export").val(exportType);
	// submit form
	jQuery(searchForm).submit();

	// change form parameters back to regular search
	jQuery(searchForm).removeAttr("target");
	jQuery("#_export").val("");
}
</script>