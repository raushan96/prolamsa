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

<%-- style CSS
<link rel="stylesheet" type="text/css" media="all" href="${commonResourcePath}/css/invoicelistaddon.css" />
 --%>
<c:set var="balanceStatementDetailBaseUrl" value="/my-account/balance-statement-detail?row=0&customer=${searchData.customer.uid}" />
<spring:url var="balanceStatementDetailSearchUrl" value="${balanceStatementDetailBaseUrl}" />



<div class="title_holder">
	
	<h2><spring:theme code="text.account.balanceStatement.detail" text="Account Detail"/></h2>
</div>
<br>
	<div>
		
		
		<table id="balance_statement" class="span-10"  data-zebra="tbody tr">
			<thead class="">
				<tr class="">
					<td class="balanceStatement-customer noBorder" style="width: 50px;">
						<spring:theme code="text.account.balanceStatement.detail.client" />:
					</td>
					<td class="balanceStatement-customer noBorder" style="text-align: right; width: 150px;">
						${searchData.customer.shortName} &nbsp; (${searchData.customer.uid})
					
					</tr>			
					<tr class="">
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.creditLimit" />:
					</td>
					<td class="balanceStatement-customer noBorder" style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.creditLimit}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
					</td>
					
				</tr>
				<tr class="">
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.creditAvailable" />:
					</td>
					<td class="balanceStatement-customer noBorder" style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.creditAvailable}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
					</tr>
					<tr class="">
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.balance" />:
					</td>
					<td class="balanceStatement-customer noBorder" style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.balance}" maxFractionDigits="2" currencySymbol="$" />&nbsp;${currentCurrency.isocode}
					</td>
					<td style="width: 300px;">
					</td>
				</tr>
			</thead>
		</table>
	</div>
	 
    <br/> 
		<table style="width: 100%;">
	    	<tr>
	    	<td  style="width: 2%; padding-left: 0px;"></td>
	       		<td style="width: 43%; border: 2px solid #4b4b4c; padding-left: 0px; padding-right: 0px;">
					
						<div class="title_holder">
							<h2><spring:theme code="text.account.balanceStatement.detail.overdue" text="Overdue"/></h2>
					 	</div>	
					  
						
							<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
								<thead class="">
									<tr class="firstrow">
										<td class="balanceStatement-customer">
											<spring:theme code="text.account.balanceStatement.detail.documentType" />
										</td>
										<td class="balanceStatement-customer">
											<spring:theme code="text.account.balanceStatement.detail.totalAmount" />
										</td>
									</tr>
									<tr class="">
										<td class="balanceStatement-customer noBorder">
											<spring:theme code="text.account.balanceStatement.detail.charges" />
										</td>
									<td class="balanceStatement-customer noBorder" style="text-align: right">
										<fmt:formatNumber type="currency" value="${searchData.overdueCharge}" maxFractionDigits="2" currencySymbol="$" />
									</td>
								</tr>
								<tr class="">
									<td class="balanceStatement-customer noBorder">
										<spring:theme code="text.account.balanceStatement.detail.credit" />
									</td>
									<td class="balanceStatement-customer noBorder" style="text-align: right">
										<fmt:formatNumber type="currency" value="${searchData.overdueCredit}" maxFractionDigits="2" currencySymbol="$" />
									</td>
								</tr>
								<tr class="">
									<td class="balanceStatement-customer noBorder left">
										
				<%--						<c:url value="/invoice/by-customer?customer=${searchData.customer.uid}&typeInvoice=overdue" var="invoiceByCustomer" /> --%>
											<%--Modificado por Christian 26082014 --%>
											<c:url value="/invoice/by-customer?customer=${searchData.customer.uid}&typeInvoice=2&typeDocto=F" var="invoiceByCustomer" />
											<a class="underline" href="${invoiceByCustomer}">${invoice.number}
											    <spring:theme code="text.account.balanceStatement.detail.invoices" />
											</a>						  
										
									</td>
									<td class="balanceStatement-customer noBorder" style="text-align: right">
										<fmt:formatNumber type="currency" value="${searchData.overdueInvoice}" maxFractionDigits="2" currencySymbol="$" />
									</td>																				
								</tr>
								<!-- NEORIS_CHANGE #Incidencia JCVM 21/08/2014 Se elimina el dato de payment.
								<tr class="" >
									<td class="balanceStatement-customer noBorder">
										<spring:theme code="text.account.balanceStatement.detail.payments" />
									</td>
									<td class="balanceStatement-customer noBorder">
										<fmt:formatNumber type="currency" value="${searchData.overduePayment}" maxFractionDigits="2" currencySymbol="$" />
									</td>
								</tr>
								-->
								
								<tr>
								<td></td>
							</tr>
								
								
								
								<tr class=""  style="border-top-style: solid; border-width: 1px;">
									<td class="balanceStatement-customer">
										<spring:theme code="text.account.balanceStatement.detail.overdueAmount" />
									</td>
									<td style="text-align: right">
										<fmt:formatNumber type="currency" value="${searchData.overdueAmount}" maxFractionDigits="2" currencySymbol="$" />
									</td>
								</tr>								
							</thead>
						</table>
					
	       </td>
	       
	      <td  style="width: 2%;"></td>
	       
	       <td style="width: 42%; border: 2px solid #4b4b4c; padding-left: 0px; padding-right: 0px;">
	           <!-- CURRENT -->
				
					<div class="title_holder">						
						<h2><spring:theme code="text.account.balanceStatement.detail.current" text="Current"/></h2>
					 </div>		 	  
				  
				  
					<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr" >
						<thead class="">
							<tr class="firstrow">
								<td class="balanceStatement-customer">
									<spring:theme code="text.account.balanceStatement.detail.documentType" />
								</td>
								<td class="balanceStatement-customer">
									<spring:theme code="text.account.balanceStatement.detail.totalAmount" />
								</td>
							</tr>
							<tr class="">
								<td class="balanceStatement-customer noBorder" >
									<spring:theme code="text.account.balanceStatement.detail.charges" />
								</td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">
									<fmt:formatNumber type="currency" value="${searchData.currentCharge}" maxFractionDigits="2" currencySymbol="$" />
								</td>
							</tr>
							<tr class="">
								<td class="balanceStatement-customer noBorder">
									<spring:theme code="text.account.balanceStatement.detail.credit" />
								</td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">
									<fmt:formatNumber type="currency" value="${searchData.currentCredit}" maxFractionDigits="2" currencySymbol="$" />
								</td>
							</tr>
							<tr class="">
								<td class="balanceStatement-customer noBorder underline left">
									
			<%--                        <c:url value="/invoice/by-customer?customer=${searchData.customer.uid}&typeInvoice=current" var="invoiceByCustomer" /> --%>
										<%--Modificado por Christian 26082014 --%>
										<c:url value="/invoice/by-customer?customer=${searchData.customer.uid}&typeInvoice=1&typeDocto=F" var="invoiceByCustomer" />
										<a class="underline" href="${invoiceByCustomer}">${invoice.number}
										    <spring:theme code="text.account.balanceStatement.detail.invoices" />
										</a>	
			                       
								</td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">
									<fmt:formatNumber type="currency" value="${searchData.currentInvoice}" maxFractionDigits="2" currencySymbol="$" />
								</td>
							</tr>
							<!-- NEORIS_CHANGE #Incidencia JCVM 21/08/2014 Se elimina el dato de payment. 
							<tr class="" >
								<td class="balanceStatement-customer noBorder">
									<spring:theme code="text.account.balanceStatement.detail.payments" />
																	</td>
								<td class="balanceStatement-customer noBorder">
									<fmt:formatNumber type="currency" value="${searchData.currentPayment}" maxFractionDigits="2" currencySymbol="$" />
								</td>
							</tr>
							-->
							 <tr class="">
									<td class="balanceStatement-customer">
										<spring:theme code="text.account.balanceStatement.detail.salVal" />
									</td>
									<td style="text-align: right">
										<fmt:formatNumber type="currency" value="${searchData.salVal}" maxFractionDigits="2" currencySymbol="$" />
									</td>
								</tr>
								
							<tr class="" style="border-top-style: solid; border-width: 1px;">
								<td class="balanceStatement-customer">
									<spring:theme code="text.account.balanceStatement.detail.currentBalance" />
								</td>
								<td class="balanceStatement-customer" style="text-align: right">
									<fmt:formatNumber type="currency" value="${searchData.currentBalance}" maxFractionDigits="2" currencySymbol="$" />
								</td>
								 
							</tr>
							
							
							
						</thead>
					</table>
				 
			    
	       </td>
	       
	      <td  style="width: 2%;"></td>
	      
	    </tr>
	     </table>
	     <br>
	      <table>
	    
	    <tr>
	    <td  style="width: 2%; padding-left: 0px;"></td>
	    	<td style="width: 100%; border: 2px solid #4b4b4c; padding-left: 0px; padding-right: 0px;">
	    		
					<div class="title_holder">						
						<h2><spring:theme code="text.account.balanceStatement.detail.overdueSummary" text="Overdue Summary"/></h2>
					 </div>		 	  
				  
				  
					<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr" >
						<thead class="">
							<tr class="firstrow">
					
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.pastDue" />
					</td>
					<td class="balanceStatement-customer noBorder">
						
					</td>
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.from1_30days" />
					</td>
					<td class="balanceStatement-customer noBorder">
						
					</td>
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.from31_60days" />
					</td>
					<td class="balanceStatement-customer noBorder">
						
					</td>
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.from61_90days" />
					</td>
					<td class="balanceStatement-customer noBorder">
						
					</td>
					<td class="balanceStatement-customer noBorder">
						<spring:theme code="text.account.balanceStatement.detail.more90days" />
					</td>													
				</tr>	
				<tr class="">
				    <td class="balanceStatement-customer dueDateColor noBorder"  style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.pastDue}" maxFractionDigits="2" currencySymbol="$" />
					</td>
					<td class="balanceStatement-customer noBorder">
						<h4></h4>
					</td>
					<td class="balanceStatement-customer dueDateColor noBorder"  style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.pastDue1_30}" maxFractionDigits="2" currencySymbol="$" />
					</td>
					<td class="balanceStatement-customer noBorder" >
						
					</td>
					<td class="balanceStatement-customer dueDateColor noBorder"  style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.pastDue31_60}" maxFractionDigits="2" currencySymbol="$" />
					</td>
					<td class="balanceStatement-customer noBorder">
						
					</td>
					<td class="balanceStatement-customer noBorder"  style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.pastDue61_90}" maxFractionDigits="2" currencySymbol="$" />
					</td>
					<td class="balanceStatement-customer noBorder">
						 
					</td>
					<td class="balanceStatement-customer noBorder"  style="text-align: right">
						<fmt:formatNumber type="currency" value="${searchData.pastDueMore90}" maxFractionDigits="2" currencySymbol="$" />
					</td>
				</tr>						
			</thead>
		</table>
				
	    	
	       
	       </td>
	        <td  style="width: 2%; padding-right: 15px;"></td>
	    </tr>
	 </table>
	 
	 <br />


<div >
		
	
		
		<form:form  id="searchForm" name="searchForm" class="js-form" action="${balanceStatementDetailSearchUrl}" method="get">
	        <input type="hidden" id="_export" name="_export" value=""/>
	        <input type="hidden" id="customer" name="customer" value="${searchData.customer.uid}" />
	        <input type="hidden" id="row" name="row" value="0" />
	    </form:form>
	        
			<div class="search-form-options-search-button">
				
							<button id="backButton" type="button" class="button yellow positive button-float-right"  title="" value="" tabindex="107" name="" >
				        		<span class="icon-download"></span>
				                <spring:theme code="text.account.balanceStatement.detail.button.back" />
				            </button>
							<button id="exportPDFButton" type="button" class="button yellow positive button-float-right" title="" value="" tabindex="108" name="">
				    	        <span class="icon-print"></span>
				                <spring:theme code="general.button.exportPDF" />
				            </button>
				            <button id="exportXLSButton" type="button" class="button yellow positive button-float-right"  title="" value="" tabindex="109" name="" >
				        		<span class="icon-download"></span>
				                <spring:theme code="general.button.exportXLS" />
				            </button> 
				  						
			    </div>			
	 
	</div>
    
    

<iframe name="iframePost" style="visibility: hidden; display: none;"></iframe>


<script type="text/javascript">
onDOMLoaded(initBalanceStatementList);

function initBalanceStatementList()
{	
	jQuery("#exportPDFButton").click(exportPDFAction);
	jQuery("#exportXLSButton").click(exportXLSAction);
	jQuery("#backButton").click(backAction);
}

function exportPDFAction()
{	
	exportTo("pdf");	
}

function exportXLSAction()
{
	exportTo("xls");	
}

function backAction()
{
	// NEORIS_CHANGE #Incidencia 177 JCVM 29/08/2014 Se genera la URL del boton atras dado que con el history puede provocar un bloqueo.
	//history.go(-1);
	window.location = "<spring:url value="/my-account/balance-statement?_export=&sort=&customer="/>${b2bunit.uid}";
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
