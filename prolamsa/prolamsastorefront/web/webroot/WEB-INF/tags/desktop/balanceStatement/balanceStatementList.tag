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

<fmt:setLocale value="en_US" scope="session"/>

<c:set var="balanceStatementListBaseUrl" value="/my-account/balance-statement" />
<spring:url var="balanceStatementListSearchUrl" value="${balanceStatementListBaseUrl}" />

<div class="item_container_holder" style="display: block;">

<div class="title_holder">
	<%--
	<div class="title">
		<div class="title-top">
			<span></span>
		</div>
	</div>
	 
	<h2><spring:theme code="text.account.balanceStatement.list" text="Balance Statement"/></h2>
</div>
&nbsp; --%><!-- required to break flow of float h2 -->
<div class="search-form" style="display: block;">
	<form:form  id="searchForm" class="js-form" action="${balanceStatementListSearchUrl}" method="get">
	<input type="hidden" id="_export" name="_export" value="" />
	<%--
	<selector:b2bUnitClientSelector dropDownCSS="dropDownMedium" spanCSS="labelSmall" b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${balanceStatementSearchForm.customer}"/>
	 --%>
	<div style="height:  30px; width: 500px; float: right;">
	
	<formUtil:formButton
					id="balanceStatementSearchSubmit"
					type="submit" 
					css="button-searchSU positive"
					tabindex="106"
					springThemeText="text.account.balanceStatement.button.search"
					onClickType="1"
					onClick="sendGA()"
					/>
					

<select id="customer" name="customer" class="dropDownMedium" style="float: right;">
    <option value="">All</option> 
	<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
	    <c:choose>
			<c:when test="${eachFormattedB2BUnit.uid eq balanceStatementSearchForm.customer}">
				<c:set var="selected" value="selected" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>
	   	<option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
	</c:forEach>
</select>

<span class="labelSmall" style="float: right;" ><spring:theme code="text.account.balanceStatement.list.client"/></span>
	
			  	 
</div>
	<%--
	<table>
		<tr>
			<td>
			  	 <selector:b2bUnitClientSelector dropDownCSS="dropDownMedium" spanCSS="labelSmall" b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${balanceStatementSearchForm.customer}"/>
			  	 <formUtil:formButton
					id="balanceStatementSearchSubmit"
					type="submit" 
					css="positive"
					tabindex="106"
					springThemeText="text.account.balanceStatement.button.search" />
			</td>
		</tr>
	</table>			

     <div class="search-form-options-search-button">		
        <formUtil:formButton
			disabled="${empty searchPageData.results}"
			id="exportPDFButton"
			type="button" 
			css="${(empty searchPageData.results) ? 'opaque button-float-right' : 'positive button-float-right'}"
			tabindex="108"
			springThemeText="general.button.exportPDF" />	
	    <formUtil:formButton 
		    disabled="${empty searchPageData.results}"
			id="exportXLSButton"
			type="button" 
			css="${(empty searchPageData.results) ? 'opaque button-float-right' : 'positive button-float-right'}"
			tabindex="107"
			springThemeText="general.button.exportXLS" />						
	  </div>
 --%>
    <input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
</div>
<h2><spring:theme code="text.account.balanceStatement.list" text="Balance Statement"/></h2>
</div>
<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
<div class="prod_refine-wrapper">
	<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${balanceStatementListBaseUrl}?sort=${balanceStatementSearchForm.sort}&customer=${balanceStatementSearchForm.customer}"
		msgKey="text.account.balanceStatement.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" neorisSortBy="true" />		 			
</div>
</c:if>
<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
<br/><br/>

<form:form modelAttribute="balanceStatementForm" method="post" action="${balanceStatementListSearchUrl}" class="js-form js-invoice-selected">

<div class="item_container">
	<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="firstrow">
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.customer" /></h3> 
				</td>
				<td class="balanceStatement-companyName noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.companyName" /></h3>
				</td>
				<td class="balanceStatement-pastDue noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.pastDue" /></h3>
				</td>
				<td class="balanceStatement-current noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.current" /></h3>
				</td>
				<td class="balanceStatement-balance noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.balance" /></h3>
				</td>
				<td class="balanceStatement-creditLimit noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.creditLimit" /></h3>
				</td>
			</tr>
		</thead>

		<tbody class="">
			<c:forEach items="${searchPageData.results}" var="balanceStatement" varStatus="status">
				<c:url value="/my-account/balance-statement-detail?customer=${balanceStatement.customer.uid}&row=${status.index}"  var="balanceStatementDetailUrl" />
				 	
				<tr class="">
					<td class="balanceStatement-number noBorder Text_Table_Align_Center">
						<ycommerce:testId code="balanceStatement_customer_link_${balanceStatement.customer}">
							<c:url value="/balanceStatement/detail/?balanceStatementCustomer=${balanceStatement.customer}" var="balanceStatementDetail" />
							<a href="${balanceStatementDetailUrl}" onclick="ga('send', 'event', 'BalanceDetail', 'viewBalanceStatementDetail', 'View detail balance statement');"><u>${balanceStatement.customer.uid}</u></a>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-companyName noBorder Text_Table_Align_Left">
						<ycommerce:testId code="balanceStatement_companyName_label_${balanceStatement.customer.shortName}">
							${balanceStatement.customer.shortName} (${balanceStatement.customer.uid})
						</ycommerce:testId>
					</td>

					<td class="balanceStatement-pastDue noBorder Text_Table_Align_Right">
						<ycommerce:testId code="balanceStatement_pastDue_label_${balanceStatement.pastDue}">
							
								<fmt:formatNumber type="currency" value="${balanceStatement.pastDue}" maxFractionDigits="2" minFractionDigits="2" currencySymbol="$" />
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="balanceStatement_current_label_${balanceStatement.current}">
							
								<fmt:formatNumber type="currency" value="${balanceStatement.current}" maxFractionDigits="2" minFractionDigits="2" currencySymbol="$" />
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="balanceStatement_balance_label_${balanceStatement.balance}">
							
								<fmt:formatNumber type="currency" value="${balanceStatement.balance}" maxFractionDigits="2" minFractionDigits="2" currencySymbol="$" />
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="balanceStatement_creditLimit_label_${balanceStatement.creditLimit}">
							
								<fmt:formatNumber type="currency" value="${balanceStatement.creditLimit}" maxFractionDigits="2" minFractionDigits="2" currencySymbol="$" />
							
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
	<nav:paginationReportFooter top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${balanceStatementListBaseUrl}?sort=${balanceStatementSearchForm.sort}&customer=${balanceStatementSearchForm.customer}"
		msgKey="text.account.balanceStatement.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
</div>
</c:if>

   <div class="search-form-options-search-button">		    	

	<c:choose>
		<c:when test="${empty searchPageData.results}">
					
		</c:when>
		<c:otherwise>
			<button id="exportPDFButton" type="button" class="button yellow positive button-float-right" title="" value="" tabindex="108" name="">
    	        <span class="icon-print"></span>
                <spring:theme code="general.button.exportPDF" />
            </button>
            <button id="exportXLSButton" type="button" class="button yellow positive button-float-right"  title="" value="" tabindex="107" name="" >
        		<span class="icon-download"></span>
                <spring:theme code="general.button.exportXLS" />
            </button>
		</c:otherwise>
	</c:choose>
		
		
		<%--
		
		<formUtil:formButton
			disabled="${empty searchPageData.results}"
			id="exportPDFButton"
			type="button" 
			css="button yellow positive button-float-right"
			tabindex="108"
			springThemeText="general.button.exportPDF"
			 />
		<formUtil:formButton 
		    disabled="${empty searchPageData.results}"
			id="exportXLSButton"
			type="button" 
			css="button yellow positive button-float-right"
			tabindex="107"
			springThemeText="general.button.exportXLS" />	
			 --%> 		
	  </div>

</div>

<!-- <iframe name="iframePost" style="visibility: hidden;"></iframe>-->

<script>
	function sendGA ()
	{
		ga('send','event','BalanceStatement','viewBalanceStatement');
	}
</script>


<script type="text/javascript">
onDOMLoaded(initBalanceStatementList);

function initBalanceStatementList()
{	
	
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#balanceStatementSearchSubmit").click(submitForm);
	jQuery("#exportPDFButton").click(exportPDFAction);
	jQuery("#exportXLSButton").click(exportXLSAction);
	
	$("#balance_statement tr:even").addClass("filaImpar");
    $("#balance_statement tr:odd").addClass("filaPar");
}

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
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

