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

<c:set var="invoiceByCustomerBaseUrl" value="/invoice/by-customer" />
<spring:url var="invoiceByCustomerSearchUrl" value="${invoiceByCustomerBaseUrl}" />

<div class="item_container_holder" style="display: block;">

	<div class="title_holder">

		<c:choose>
		  <c:when test="${typeInvoice eq '2'}">
		      <h2><spring:theme code="invoice.byCustomer.titleOverdue"/></h2>
		  </c:when>
		  <c:otherwise>
		      <h2><spring:theme code="invoice.byCustomer.titleCurrent"/></h2>
		  </c:otherwise>
		</c:choose>
	
	</div>
	
	<div class="search-form" style="display: block;">
	

	<!-- <div class="prod_refine-wrapper_account">
                       
  		<div class="prod_refine">
-->
		
		
		
		<table>
			
				<tr>
							<td style="height: 30px; width: 100px;">
								<spring:theme code="text.account.balanceStatement.detail.client" />:
							</td>
							<td class="balanceStatement-customer noBorder">
								${b2bunit.shortName} &nbsp; (${b2bunit.uid})
							</td>
						</tr>
				
						<c:choose>
						  <c:when test="${typeInvoice eq 'overdue'}">
						      <tr class="">
								<td style="height: 30px; width: 100px;">
									<spring:theme code="text.account.balanceStatement.detail.overdue" />:
								</td>
								<td class="balanceStatement-customer noBorder">
									<fmt:formatNumber type="currency" value="" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
								</td>
								<td style="width: 300px;">
					</td>
							  </tr>
						  </c:when>
						  <c:otherwise>
						    <tr class="">
								<td class="balanceStatement-customer noBorder">
									<spring:theme code="text.account.balanceStatement.detail.current" />:
								</td>
								<td class="balanceStatement-customer noBorder">
									<fmt:formatNumber type="currency" value="${searchData.currentBalance}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
								</td>
								<td style="width: 300px;">
					</td>
							</tr>
						  </c:otherwise>
						</c:choose>										
				
					
				</table>
				

	

               		

                       <c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
                       	<div class="prod_refine-wrapper">
                       
	                        <nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${invoiceByCustomerBaseUrl}?customer=${invoiceSearchForm.customer}&typeInvoice=${typeInvoice}&typeDocto=${typeDocto}&sort=${invoiceSearchForm.sort}"
								msgKey="invoice.list.page.currentPage"
								numberPagesShown="${numberPagesShown}" includeSortDropdown="true" neorisSortBy="true" />		 			
	                        
	                  	</div>
	                  	</c:if>
	                  	<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
                        <br/><br/>
						<div class="item_container">
						<table id="order_history" class="backorder-detail" data-zebra="tbody tr">
					    	<thead class="">
					        	<tr class="firstrow">
									<td class="backorderDetail-address">
										<h3><spring:theme code="invoice.list.invoiceNumber" /></h3>
									</td>
									<td class="backorderDetail-address">
										<h3><spring:theme code="invoice.list.invoiceDate" /></h3>
									</td>
									<td class="backorderDetail-address">
										<h3><spring:theme code="invoice.list.dueDate" /></h3>
									</td>
									<td class="backorderDetail-address">
										<h3><spring:theme code="invoice.list.originalAmount" /></h3>
									</td>
									<td class="backorderDetail-address">
										<h3><spring:theme code="invoice.list.balanceAmount" /></h3>
									</td>
								</tr>
								</thead>
								<tbody class="">
								<c:forEach items="${searchPageData.results}" var="invoice" varStatus="status">
									<c:url value="/invoice/detail/${invoice.number}" var="invoiceDetailUrl" />

										<tr class="">
											<td class="backorderDetail-part Text_Table_Minimum_Align_Left" >
												<ycommerce:testId code="invoiceHistory_invoiceNumber_link_${invoice.number}">
													<%-- <c:url value="/invoice/by-invoice?invoice=${invoice.number}" var="invoiceDetail" />	 --%>
													
													<%-- <c:url value="/invoice/by-SAP-invoice?invoice=${invoice.number}&customer=${invoice.customer}" var="invoiceDetail" />												
													<a href="${invoiceDetail}">${invoice.number}</a> --%>
													
													<a href="javascript:void(0)" id="myLinkInvoice_${invoice.number}_${invoice.customer.uid}" class="myLinkInvoice">${invoice.number}</a>
												</ycommerce:testId>
											</td>
											
											<td class="backorderDetail-part Text_Table_Minimum_Align_Left" >
												<ycommerce:testId code="invoiceHistory_invoiceDate_label_${invoice.invoiceDate}">
													<formDate:formFormatDate pattern="MM/dd/yyyy" value="${invoice.invoiceDate}" />
												</ycommerce:testId>
											</td>
											<td class="backorderDetail-part Text_Table_Minimum_Align_Left" >
												<ycommerce:testId code="invoiceHistory_invoiceDate_label_${invoice.dueDate}">
													<formDate:formFormatDate pattern="MM/dd/yyyy" value="${invoice.dueDate}" />
												</ycommerce:testId>
											</td>
											<td class="invoice-amount noBorder Text_Table_Align_Right">
												<ycommerce:testId code="invoiceHistory_amount_label_${invoice.originalAmount}">
													<fmt:formatNumber type="currency" value="${invoice.originalAmount.value}" maxFractionDigits="2" currencySymbol="$" />
												</ycommerce:testId>
											</td>
						
											<td class="invoice-amount noBorder Text_Table_Align_Right">
												<ycommerce:testId code="invoiceHistory_amount_label_${invoice.balanceAmount}">
													<fmt:formatNumber type="currency" value="${invoice.balanceAmount.value}" maxFractionDigits="2" currencySymbol="$" />
												</ycommerce:testId>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
					</div>
                     <br>           
					<div class="go_back_two">
                    	<button id="backButton" type="submit" class="button yellow positive button-float-right" onclick='' title="" value="" tabindex="107" name=""><span>Back</span></button>
                   	</div>
                   	<div class="search-form-options-search-button">
						        
						        	<button id="exportPDFButton" type="button" class="${(empty searchPageData.results) ? 'button opaque button-float-right' : 'button yellow button-float-right'}"
						        	        title="" value="" tabindex="108" name="">
						    	        <span class="icon-print"></span>
						                <spring:theme code="general.button.exportPDF" />
						            </button>
						            
						             <button id="exportXLSButton" type="button" class="${(empty searchPageData.results) ? 'button opaque button-float-right' : 'button yellow button-float-right'}"  
						                    title="" value="" tabindex="107" name="" >
						        		<span class="icon-download"></span>
						                <spring:theme code="general.button.exportXLS" />
						            </button>						  
						</div>                 
                  </div>
                        
                  <form:form  id="searchForm" name="searchForm" class="js-form" action="${invoiceByCustomerSearchFromUrl}" method="get">
	               	<input type="hidden" id="_export" name="_export" value=""/>
	               	<input type="hidden" id="customer" name="customer" value="${b2bunit.uid}" />
	               	<input type="hidden" id="typeInvoice" name="typeInvoice" value="${typeInvoice}" />
	               	<input type="hidden" id="typeDocto" name="typeDocto" value="F" />
	               	<input type="hidden" id="sortField" name="sort" value="" />
	               </form:form>
	               		<%--
                        <div class="main-right-footer clearfix">
							<nav:paginationReportFooter top="true" supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${invoiceByCustomerBaseUrl}?customer=${invoiceSearchForm.customer}&typeInvoice=${typeInvoice}&typeDocto=${typeDocto}&sort=${invoiceSearchForm.sort}"
								msgKey="invoice.list.page.currentPage"
								numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
						</div>
						--%>
						<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
                       	<div class="prod_refine-wrapper">
                       
	                        <nav:paginationReportFooter top="true" supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${invoiceByCustomerBaseUrl}?customer=${invoiceSearchForm.customer}&typeInvoice=${typeInvoice}&typeDocto=${typeDocto}&sort=${invoiceSearchForm.sort}"
								msgKey="invoice.list.page.currentPage"
								numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
	                        
	                  	</div>
	                  	</c:if>
	                  	
						
					</div>
				</div>

 

 
 
<form id="transferDocumentForm"  target="iframePost" action="<spring:url value="/my-account/document/transferDocument" />" >
	<input type="hidden" id="documentNumberField" name="documentNumber" />
	<input type="hidden" id="documentOwnerField" name="documentOwner" />			
	<input type="hidden" id="transferTypeField" name="transferType" />
	<input type="hidden" id="documentTypeField" name="documentType" />
</form>

<iframe name="iframePost" style="visibility: hidden;"></iframe>
     
<script type="text/javascript">
onDOMLoaded(initInvoiceList);

function initInvoiceList()
{	
	ACC.neorisDownloadDocuments.initialize({
		transferModalMessages: {
			title: "<spring:theme code="invoices.modal.download.title"/>",
			content: "<spring:theme code="invoices.modal.download.content"/>",
			button1: "<spring:theme code="quote.modal.download.button.download"/>",
			button2: "<spring:theme code="quote.modal.download.button.email"/>"
		}
	});
	
	jQuery("#exportPDFButton").click(exportPDFAction);
	jQuery("#exportXLSButton").click(exportXLSAction);
	jQuery("#backButton").click(backAction);
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
	
	$("#order_history tr:even").addClass("filaImpar");
    $("#order_history tr:odd").addClass("filaPar");
}

function submitFormFromDropDown()
{
	var val = jQuery(this).val();		
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

function exportPDFAction()
{
	exportTo("pdf_by_customer");
}

function exportXLSAction()
{
	exportTo("xls_by_customer");
}

function backAction()
{
	//window.location = "<spring:url value="/my-account/balance-statement-detail?customer="/>${b2bunit.uid}";
	window.location = "<spring:url value="/my-account/balance-statement-detail?row=0&customer="/>${b2bunit.uid}";
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