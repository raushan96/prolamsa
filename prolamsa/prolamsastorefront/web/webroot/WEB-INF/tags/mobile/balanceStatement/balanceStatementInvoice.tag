<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav"%>
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
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="foot" tagdir="/WEB-INF/tags/mobile/footer"%>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<c:set var="invoiceByCustomerBaseUrl" value="/invoice/by-customer" />
<spring:url var="invoiceByCustomerSearchUrl" value="${invoiceByCustomerBaseUrl}" />
<a id="top"></a>

<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 20px; text-align: justify;">
                	<c:choose>
					  <c:when test="${typeInvoice eq '2'}">
					      <spring:theme code="invoice.byCustomer.titleOverdue"/>
					  </c:when>
					  <c:otherwise>
					      <spring:theme code="invoice.byCustomer.titleCurrent"/>
					  </c:otherwise>
					</c:choose>
		 <br>
                </h1>
			</div>
		</div>	


<div id="gradTableData" style="display: block; width: 100%; float: left; height:auto;">
			<table style="width: 100%; float: left; height:auto; padding: 0 10px;">
					
				<tr>
					<td>
					<spring:theme code="text.account.balanceStatement.detail.client" />:
					</td>
					<td style="font-weight: lighter;">
					${b2bunit.shortName} &nbsp; (${b2bunit.uid})
					</td>
				</tr>
				
				<c:choose>
						  <c:when test="${typeInvoice eq 'overdue'}">
						      <tr class="">
								<td >
									<spring:theme code="text.account.balanceStatement.detail.overdue" />:
								</td>
								<td  style="font-weight: lighter;">
									<fmt:formatNumber type="currency" value="" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
								</td>
								
							  </tr>
						  </c:when>
						  <c:otherwise>
						    <tr class="">
								<td class="balanceStatement-customer noBorder">
									<spring:theme code="text.account.balanceStatement.detail.current" />:
								</td>
								<td  style="font-weight: lighter;">
									<fmt:formatNumber type="currency" value="${searchData.currentBalance}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
								</td>
								
							</tr>
						  </c:otherwise>
						</c:choose>
				
				
				
				
		</table>
	</div>
	
<div id="resultsMobile" class="item_container" style="width: 100%; float: left; height:auto; text-align: justify;">
	<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
                       	
                       
	                        <nav:paginationReportMobile top="true" supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${invoiceByCustomerBaseUrl}?customer=${invoiceSearchForm.customer}&typeInvoice=${typeInvoice}&typeDocto=${typeDocto}&sort=${invoiceSearchForm.sort}"
								msgKey="invoice.list.page.currentPage"
								numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
	                        
	                  	
	                  	</c:if>
	
	
	

				<c:forEach items="${searchPageData.results}" var="invoice">
					<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" id="resultsMobileXXX">
					<!-- <table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px; background: #fff;"> -->
						<tr>
						<td style="width: 50%;">
								Invoice no.
							</td>
							<td  style="font-weight: lighter;">
								${invoice.number}
							</td>
						</tr>
						<tr>
							<td style="width: 50%;">
								Invoice date
							</td>
							<td  style="font-weight: lighter;">
								<formDate:formFormatDate pattern="MM/dd/yyyy" value="${invoice.invoiceDate}" />
							</td>
						</tr>
						<tr>
							<td style="width: 50%;">
								Due date
							</td>
							<td  style="font-weight: lighter;">
								<formDate:formFormatDate pattern="MM/dd/yyyy" value="${invoice.dueDate}" />
							</td>
						</tr>
						<tr>
							<td style="width: 50%;">
								Original amount
							</td>
							<td  style="font-weight: lighter;">
								<fmt:formatNumber type="currency" value="${invoice.originalAmount.value}" maxFractionDigits="2" currencySymbol="$" />
							</td>
						</tr>
						<tr>
							<td style="width: 50%;">
								Balance Amount
							</td>
							<td  style="font-weight: lighter;">
								<fmt:formatNumber type="currency" value="${invoice.balanceAmount.value}" maxFractionDigits="2" currencySymbol="$" />
							</td>
						</tr>
					</table>
				</c:forEach>
			
	</div>
	    

				<c:if test="${searchPageData.pagination.totalNumberOfResults eq 0}" >
                       	
                       
	                        <nav:paginationReportMobile top="true" supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${invoiceByCustomerBaseUrl}?customer=${invoiceSearchForm.customer}&typeInvoice=${typeInvoice}&typeDocto=${typeDocto}&sort=${invoiceSearchForm.sort}"
								msgKey="invoice.list.page.currentPage"
								numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
	                        
	                  
	                  	</c:if>
			
			<foot:footerWTop searchPageData="${searchPageData}"	/>
