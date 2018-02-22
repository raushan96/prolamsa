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
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="foot" tagdir="/WEB-INF/tags/mobile/footer"%>

<c:set var="balanceStatementDetailBaseUrl" value="/my-account/balance-statement-detail?cutsomer=${b2bunit.uid}" />
<spring:url var="balanceStatementDetailSearchUrl" value="${balanceStatementDetailBaseUrl}" />
<a id="top"></a>

<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 20px; text-align: justify;">
                	<spring:theme code="text.account.balanceStatement.detail" /> <br>
                </h1>
			</div>
		</div>	
<div id="gradTableData" style="display: block; width: 100%; float: left; height:auto;">


			<table style="width: 100%; float: left; height:auto; padding: 0 8px;">
					
				<tr>
					<td style=" width: 40%;">
					<spring:theme code="text.account.balanceStatement.detail.client" />:
					</td>
					<td style="font-weight: lighter;">
					${searchData.customer.shortName} &nbsp; (${searchData.customer.uid})
					</td>
				</tr>
				<tr>
					<td>
						<spring:theme code="text.account.balanceStatement.detail.creditLimit" />:
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.creditLimit}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
					</td>
				</tr>
				<tr>
					<td>
						<spring:theme code="text.account.balanceStatement.detail.creditAvailable" />:
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.creditAvailable}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
					</td>
					
				</tr>
				
				<tr>
					<td>
						<spring:theme code="text.account.balanceStatement.detail.balance" />:
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.balance}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
					</td>
				</tr>
			
		</table>
	
		<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="text.account.balanceStatement.detail.overdue" /></p>
			
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px 10px 20px;  background: #fff;" onclick="viewOverdue(${searchData.customer.uid})">
			<tr class="firstrow">
			<td class="balanceStatement-customer noBorder">
						Document Type
					</td>
					<td colspan="2">
						Total Amount
					</td>
			</tr>
			<tr class="">
					<td style="width: 50%;">
						<spring:theme code="text.account.balanceStatement.detail.charges" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.overdueCharge}" maxFractionDigits="2" currencySymbol="$" />
					</td>
				</tr>
				
				<tr class="">
					<td>
						<spring:theme code="text.account.balanceStatement.detail.credit" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.overdueCredit}" maxFractionDigits="2" currencySymbol="$" />
					</td>
					<td style="padding: 0 1px; text-align: right; ">
							<img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt="">
							</td>
				</tr>
				<tr class="">
					<td>
							<%-- <c:url value="/invoice/by-customer?customer=${searchData.customer.uid}&typeInvoice=2&typeDocto=F" var="invoiceByCustomer" />
							<a class="underline" href="${invoiceByCustomer}">${invoice.number}
							    <spring:theme code="text.account.balanceStatement.detail.invoices" />
							</a> --%>						  
							<spring:theme code="text.account.balanceStatement.detail.invoices" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.overdueInvoice}" maxFractionDigits="2" currencySymbol="$" />
					</td>																				
				</tr>
				<tr class="">
									<td>
										<spring:theme code="text.account.balanceStatement.detail.salVal" />
									</td>
									<td style="font-weight: lighter;">
										<fmt:formatNumber type="currency" value="${searchData.salVal}" maxFractionDigits="2" currencySymbol="$" />
									</td>
								</tr>
								
								
				
				
				
			</table>
			
			<!-- </div>
	 
   <div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; "> -->
		
		<p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;">Current</p>
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px 10px 20px;  background: #fff;" onclick="viewCurrent(${searchData.customer.uid})">
			
			
			
			<tr class="firstrow">
			<td class="balanceStatement-customer noBorder">
						Document Type
					</td>
					<td colspan="2">
						Total Amount
					</td>
			</tr>
			<tr class="">
					<td class="balanceStatement-customer noBorder" style="width: 50%;">
						<spring:theme code="text.account.balanceStatement.detail.charges" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.currentCharge}" maxFractionDigits="2" currencySymbol="$" />
					</td>
				</tr>
				<tr class="">
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.credit" />
					</td>
					
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.currentCredit}" maxFractionDigits="2" currencySymbol="$" />
					</td>
					<td style="padding: 0 1px; text-align: right; ">
							<img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt="">
							</td>
				</tr>
				<tr class="">
					<td class="balanceStatement-customer noBorder underline left">
						
							
							<%--Modificado por Christian 26082014 --%>
							<%-- <c:url value="/invoice/by-customer?customer=${searchData.customer.uid}&typeInvoice=1&typeDocto=F" var="invoiceByCustomer" />
							<a class="underline" href="${invoiceByCustomer}">${invoice.number}
							    <spring:theme code="text.account.balanceStatement.detail.invoices" />
							</a> --%>	
							<spring:theme code="text.account.balanceStatement.detail.invoices" />
                       
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="currency" value="${searchData.currentInvoice}" maxFractionDigits="2" currencySymbol="$" />
					</td>
				</tr>
				
				<tr class="">
				
					<td class="balanceStatement-customer">
						<spring:theme code="text.account.balanceStatement.detail.currentBalance" />
					</td>
					<td style="font-weight: lighter;">
						<b><fmt:formatNumber type="currency" value="${searchData.currentBalance}" maxFractionDigits="2" currencySymbol="$" /></b>
					</td>
				</tr>
			</table>
		<!-- </div>	
	
	 <div class="item_container_holderMobile" style="display: block; width: 100%; float: left; height:auto; ">
		 -->
		 <p style="padding-left: 20px; text-transform: uppercase; font-weight: 900;"><spring:theme code="text.account.balanceStatement.detail.overdueSummary" text="Overdue Summary"/></p>
			<table style=" border:solid 2px black; width: 89%; float: left; height:auto; margin: 0 20px 10px 20px;  background: #fff;">
			
				<tr class="">
					<td style="width: 50%;">
						<spring:theme code="text.account.balanceStatement.detail.pastDue" />
					</td>
					<td style="font-weight: lighter;">
						<b><fmt:formatNumber type="currency" value="${searchData.pastDue}" maxFractionDigits="2" currencySymbol="$" /></b>
					</td>
				</tr>
				<tr class="">
					<td class="balanceStatement-customer noBorder">
					<spring:theme code="text.account.balanceStatement.detail.from1_30days" />
					</td>
					<td style="font-weight: lighter;">
						<b><fmt:formatNumber type="currency" value="${searchData.pastDue1_30}" maxFractionDigits="2" currencySymbol="$" /></b>
					</td>
				</tr>
				<tr class="" >
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.from31_60days" />
					</td>
					<td style="font-weight: lighter;">
						<b><fmt:formatNumber type="currency" value="${searchData.pastDue31_60}" maxFractionDigits="2" currencySymbol="$" /></b>
					</td>
				</tr>
				<tr class="">
					<td class="balanceStatement-customer">
						<spring:theme code="text.account.balanceStatement.detail.from61_90days" />
					</td>
					<td style="font-weight: lighter;">
						<b><fmt:formatNumber type="currency" value="${searchData.pastDue61_90}" maxFractionDigits="2" currencySymbol="$" /></b>
					</td>
				</tr>
				<tr class="">
					<td class="balanceStatement-customer">
						<spring:theme code="text.account.balanceStatement.detail.more90days" />
					</td>
					<td style="font-weight: lighter;">
						<b><fmt:formatNumber type="currency" value="${searchData.pastDueMore90}" maxFractionDigits="2" currencySymbol="$" /></b>
					</td>
				</tr>
	 
	 	</table></div>
	 

	<form:form  id="searchForm" name="searchForm" class="js-form" action="${balanceStatementDetailSearchUrl}" method="get">
		<input type="hidden" id="customer" name="customer" value="${b2bunit.uid}" />
	</form:form>
	    

	 


<c:choose>
				<c:when test="${searchPageData.pagination.totalNumberOfResults gt 0}">
					<foot:footerWTop searchPageData="${searchPageData}"	/>
				</c:when>
				<c:otherwise>
					<foot:footer/>
				</c:otherwise>
			</c:choose>

<script type="text/javascript">
function viewOverdue(customer)
{
	document.location.href=window.location.origin+"/store/invoice/by-customer?customer="+customer+"&typeInvoice=2&typeDocto=F";
}

function viewCurrent(customer)
{
	document.location.href=window.location.origin+"/store/invoice/by-customer?customer="+customer+"&typeInvoice=1&typeDocto=F";
}



//	document.location.href=window.location.origin+"/store/my-account/order/"+code;

</script>
