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

<c:set var="backorderListBaseUrl" value="/my-account/backorder/list" />
<spring:url var="backorderListSearchUrl" value="${backorderListBaseUrl}" />

<div class="item_container_holder" style="display: block;">

<div class="title_holder">
	<%--
	<div class="title">
		<div class="title-top">
			<span></span>
		</div>
	</div>
	<h2><spring:theme code="backorder.list.search" text="Backorder Report"/></h2>
</div>
&nbsp; --%><!-- required to break flow of float h2 -->
<div class="search-form" style="display: block;">
	<form:form id="searchForm" class="js-form" action="${backorderListSearchUrl}" method="get">
		<input type="hidden" id="_export" name="_export" value="" style="display: none;" />
		<%--
		<selector:b2bUnitClientSelector dropDownCSS="dropDownMedium" spanCSS="labelSmall" b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${balanceStatementSearchForm.customer}"/>
		<formUtil:formButton
						id="backorderSearchSubmit"
						type="submit" 
						css="button-search positive"
						tabindex="106"
						springThemeText="text.account.balanceStatement.button.search" />
						
						
			 --%>			
						
		<div style="height:  30px; width: 500px; float: right;">
		<formUtil:formButton
					id="backorderSearchSubmit"
					type="submit" 
					css="button-searchSU positive"
					tabindex="106"
					springThemeText="text.account.balanceStatement.button.search"
					onClickType="1"
					onClick="sendGA()" />
					
		<select id="customer" name="customer" class="dropDownMedium" style="float: right;">
		    <option value="">All</option> 
			<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
			    <c:choose>
					<c:when test="${eachFormattedB2BUnit.uid eq backorderSearchForm.customer}">
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
	<%--
		<table>
			<tr>
				<td class="">
					<spring:theme var="placeHolder" code="invoice.list.number" />
					<div class="search-form-options">
   						<selector:b2bUnitClientSelector dropDownCSS="dropDownMedium" spanCSS="labelSmall" b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${backorderSearchForm.customer}"/>
   						<!-- 
						<formUtil:formButton
							id="backorderSearchSubmit"
							type="button" 
							css="positive"
							tabindex="106"
							springThemeText="backorder.button.search" />
							 -->
					</div>
				</td>
				<td class="columnAlignRight">
					<p class="backOrderReportDownloadExcelDescription">
						<spring:theme code="backOrderReportDownloadExcelDescription"/>
					</p>
				</td>
			</tr>
		</table>
		 --%>
		<input type="hidden" id="sortField" name="sort" value="" style="display: none;"  />
	</form:form>
</div>
<h2><spring:theme code="backorder.list.search" text="Backorder Report"/></h2>
</div>
<p style="margin-left:30px; color:black; font-size:13px; font-weight:bold"><spring:theme code="backorder.message.new"/></p>
<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
<div class="prod_refine-wrapper">
	<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderListBaseUrl}?sort=${backorderSearchForm.sort}&customer=${backorderSearchForm.customer}"
		msgKey="backorder.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" neorisSortBy="true" />	 			
</div>
</c:if>

<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
<br/><br/>
<form:form modelAttribute="backorderForm" method="post" action="${backorderListSearchUrl}" class="js-form js-invoice-selected">
<div class="item_container">
	<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
		<thead class="">
			<c:set var="suffixHeaderLabels" value=""/>
			<c:if test="${baseStore.uid eq '6000'}">
				<c:set var="suffixHeaderLabels" value=".${baseStore.uid}"/>
			</c:if>
			<tr class="firstrow">
				<td class="balanceStatement-customer noBorder"  align="center">
					<h3><spring:theme code="backorder.list.customer" /></h3>
				</td>
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="text.account.balanceStatement.list.companyName" /></h3>
				</td>
				<!--NEORIS_CHANGE #incidencia JCVM 21/08/2014 Se eliminan las columnas de Descripcion y Orden.
				    Ademas se agrega el alineado de forma centrada el titulo de las tablas y que en datos
				    numerico aparezcan minimo 2 digitos. 
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.description" /></h3>
				</td>
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.pcsOrder" /></h3>
				</td>
				-->
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.kgsOrder${suffixHeaderLabels}" /></h3>
				</td>
				
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.pendingKilos${suffixHeaderLabels}" /></h3>
				</td>
				<td class="balanceStatement-customer noBorder">
				
					<h3><spring:theme code="backorder.list.kilosReady${suffixHeaderLabels}" /></h3>
					
				</td>
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.kilosLoading${suffixHeaderLabels}" /></h3>
				</td>
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.balanceKilos${suffixHeaderLabels}" /></h3>
					
				</td>
			</tr>
		</thead>
		
		<tbody class="">
			<c:forEach items="${searchPageData.results}" var="backorder" varStatus="status">
				<c:url value="/my-account/backorder/detail/?customer=${backorder.customer.code}&name=${backorder.customer.name}" var="backorderDetailUrl" />
				
				<tr class="">
					<td class="balanceStatement-date noBorder Text_Table_Align_Center"> 
						<ycommerce:testId code="backorderHistory_backorderCustomer_link_${backorder.customer}">
							<c:url value="/my-account/backorder/detail/?customer=${backorder.customer.code}" var="backorderDetail" />
							<%-- <commonUtil:link href="${invoiceDetail}" springThemeText="${invoice.number}" /> --%>
							<a href="${backorderDetailUrl}" customer="${backorder.customer.code}"><u>${backorder.customer.code}</u></a>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Left">
						<ycommerce:testId code="backorder_companyName_label_${backorder.customer.name}">
							${backorder.customer.name}
						</ycommerce:testId>
					</td>
							
					<!--Neoris_Change JCVM 21/08/2014 Se eliminan las columnas de Descripcion y Orden.
				       Ademas se agrega el alineado de forma centrada el titulo de las tablas y que en datos
				       numerico aparezcan minimo 2 digitos.
				       
					<td class="balanceStatement-date noBorder">
						<ycommerce:testId code="backorderHistory_description_label_${backorder.description}">
							${backorder.description}
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder">
						<ycommerce:testId code="backorderHistory_pcsOrder_label_${backorder.pcsOrder}">
							${backorder.pcsOrder}
						</ycommerce:testId>
					</td>-->
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_kgsOrder_label_${backorder.kgsOrder}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorder.orderQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorder.kgsOrder}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_pendingKilos_label_${backorder.pendingKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorder.pendingQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorder.pendingKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_readyKilos_label_${backorder.readyKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorder.readyQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorder.readyKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_loadingKilos_label_${backorder.loadingKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorder.loadingQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorder.loadingKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-customer Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_balanceKilos_label_${backorder.balanceKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorder.balance2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorder.balanceKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
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
<nav:paginationReportFooter top="false" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderListBaseUrl}?sort=${backorderSearchForm.sort}&customer=${backorderSearchForm.customer}"
		msgKey="backorder.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
</div>		 			

</c:if>
<div class="search-form-options-search-button">		    	

	<c:choose>
		<c:when test="${empty searchPageData.results}">
					
		</c:when>
		<c:otherwise>
			
            <button id="exportXLSButton" type="button" class="button yellow positive button-float-right"  title="" value="" tabindex="107" name="" >
        		<span class="icon-download"></span>
                <spring:theme code="general.button.exportXLS" />
            </button>
		</c:otherwise>
	</c:choose>




			<%--

<div class="search-form-options-search-button">
	<formUtil:formButton 
		disabled="${empty searchPageData.results}"
		id="exportXLSButton"
		type="button" 
		css="${(empty searchPageData.results) ? 'opaque' : 'positive'} button-float-right"
		tabindex="107"
		springThemeText="general.button.exportXLS" />
</div>

<div class="">
	<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderListBaseUrl}?sort=${backorderSearchForm.sort}&customer=${backorderSearchForm.customer}"
		msgKey="backorder.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
</div>

<div class="item_container">
	<table id="order_history" class="backorder-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="">
				<td class="backorder-customer">
					<h3><spring:theme code="backorder.list.customer" /></h3>
				</td>
				<td class="backorder-companyName">
					<h3><spring:theme code="text.account.balanceStatement.list.companyName" /></h3>
				</td>
				<td class="backorder-description">
					<h3><spring:theme code="backorder.list.description" /></h3>
				</td>
				<td class="backorder-pcsOrder">
					<h3><spring:theme code="backorder.list.pcsOrder" /></h3>
				</td>
				<td class="backorder-kgsOrder">
					<h3><spring:theme code="backorder.list.kgsOrder" /></h3>
				</td>
				
				<td class="backorder-pendingKilos">
					<h3><spring:theme code="backorder.list.pendingKilos" /></h3>
				</td>
				<td class="backorder-kilosReady">
					<h3><spring:theme code="backorder.list.kilosReady" /></h3>
				</td>
				<td class="backorder-kilosLoading">
					<h3><spring:theme code="backorder.list.kilosLoading" /></h3>
				</td>
				<td class="backorder-balanceKilos">
					<h3><spring:theme code="backorder.list.balanceKilos" /></h3>
				</td>
			</tr>
		</thead>

		
</div>

<div class="">
	<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderListBaseUrl}?sort=${backorderSearchForm.sort}&customer=${backorderSearchForm.customer}"
		msgKey="backorder.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
</div>
 --%> 
</div>
</div>
<%--
<iframe name="iframePost" style="visibility: hidden;"></iframe>
--%>
<script>
	function sendGA ()
	{
		ga('send','event','Backorder','viewBackorder');
	}
</script>
<script type="text/javascript">
onDOMLoaded(initInvoiceList);

function bindSearchFormElements()
{
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#backorderSearchSubmit").click(submitForm);
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

function initInvoiceList()
{
	bindSearchFormElements();
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