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
<%-- <%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %> --%>

<c:set var="documentListBaseUrl" value="/my-account/document/list" />
<spring:url var="documentListSearchUrl" value="${documentListBaseUrl}" />

<div class="item_container_holder" style="display: block;">

<div class="title_holder">
   <h2>
      <spring:theme code="documentSearch.list.search" text="Document Search"/>
   </h2>
</div>
	

<div class="search-form"  style="display: block;">
	<form:form id="searchForm" class="js-form" action="${documentListSearchUrl}" method="get">
		
			<table border="0">
				<tr>
					<td>
						<span><spring:theme code="documentSearch.list.customer"/>:</span>						
					</td>				
					<td>
					   <span><selector:b2bUnitClientSelector2 b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${documentSearchForm.customer}"/></span>
					</td>
					
					<td>
					   <span>
					      <spring:theme code="documentSearch.list.dateFrom" />
					   </span>
					</td>				 
					<td>
					   <spring:theme var="placeHolder" code="documentSearch.list.dateFrom.inline" />
					   <formUtil:formInputTextInline 
							path="" 
							id="initialDate"
							mandatory="false" 
							type="text" 
							name="initialDate" 
							value="${documentSearchForm.initialDate}" 
							tabindex="103" 
							divCSS="search-form-options"
							placeholderSpringThemeText="${placeHolder}" 
							includeColon="true"
							asterisk="" 
							inputCSS="" />
					</td>
					<td>
					   <spring:theme code="documentSearch.list.dateTo" />
					</td>
					<td>
					   <spring:theme var="placeHolder" code="documentSearch.list.dateTo.inline" />
					   <formUtil:formInputTextInline 
							path="" 
							id="finalDate"
							mandatory="false" 
							type="text" 
							name="finalDate" 
							value="${documentSearchForm.finalDate}" 
							tabindex="104" 
							divCSS="search-form-options"
							placeholderSpringThemeText="${placeHolder}" 
							includeColon="true"
							asterisk="" 
							inputCSS="" />
					</td>
				</tr>
				<tr>
					<td>
							<span ><spring:theme code="documentSearch.list.documentType"/>:</span>
						<%--
						<span><selector:documentTypeSelector documentTypes="${EnumTypes}" selectedType="${documentSearchForm.documentType}"/></span>
						--%>
					</td>
					<td>
					    <span>
					       <select id="documentType" name="documentType" class="${dropDownCSS}">
							 <c:forEach items="${EnumTypes}" var="eachDocumentType">
							    <c:choose>
									<c:when test="${eachDocumentType eq documentSearchForm.documentType}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${baseStore.uid eq '1000'}">
										<option value="${eachDocumentType}" ${selected} ><spring:theme code="documentSearch.list.documentType.mx.${eachDocumentType}"/></option>
									</c:when>
									<c:when test="${baseStore.uid eq '2000'}">
										<option value="${eachDocumentType}" ${selected} ><spring:theme code="documentSearch.list.documentType.usa.${eachDocumentType}"/></option>
									</c:when>
									<c:when test="${baseStore.uid eq '5000'}">
										<option value="${eachDocumentType}" ${selected} ><spring:theme code="documentSearch.list.documentType.a4c.${eachDocumentType}"/></option>
									</c:when>
									<c:when test="${baseStore.uid eq '6000'}">
										<option value="${eachDocumentType}" ${selected} ><spring:theme code="documentSearch.list.documentType.axis.${eachDocumentType}"/></option>
									</c:when>
								</c:choose>
								
							   	<%-- <option value="${eachDocumentType}" ${selected} ><spring:theme code="documentSearch.list.documentType.${eachDocumentType}"/></option> --%>
							</c:forEach>
						</select>
					    </span>
					</td>
					
					<td>
					   <span>
						   <spring:theme code="documentSearch.list.reference" />
						</span>
					</td>
					<td>
					   <spring:theme var="placeHolder" code="documentSearch.list.reference" />
						<formUtil:formInputTextInline 
							path=""
							id="reference"
							mandatory="false"
							type="text"
							name="reference"
							value="${documentSearchForm.reference}"
							tabindex="102"
							divCSS="search-form-options"
							placeholderSpringThemeText="${placeHolder}"
							asterisk=""
							inputCSS="" />
					</td>
					
				</tr>
								
			</table>
			<input type="hidden" id="sortField" name="sort" value="" />
		
   	
	</form:form>
</div>

<div class="search-form-options-search-button">
	<formUtil:formButton id="documentSearchSubmit" type="submit" css="button yellow positive button-float-right"
		                         tabindex="106" springThemeText="documentSearch.button.search" onClickType="1"
					onClick="sendGA()"
					/> 
</div>
<br>

<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
<div class="prod_refine-wrapper">
	<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${documentListBaseUrl}?sort=${documentSearchForm.sort}&documentType=${documentSearchForm.documentType}&customer=${documentSearchForm.customer}&reference=${documentSearchForm.reference}&initialDate=${documentSearchForm.initialDate}&finalDate=${documentSearchForm.finalDate}"
		msgKey="documentSearch.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" neorisSortBy="true" />
</div>

<br><br>
<form id="massiveDocumentDownloadForm" target="iframePost" action="<spring:url value="/my-account/document/downloadMassiveDocuments" />" method="POST"> 
	<div class="search-form-options-search-button">
		<input id="massiveDocumentsToDownload" name="documentsToDownload" value="" type="hidden">
		<formUtil:formButton id="documentDownloadSubmit" type="submit" css="button yellow positive button-float-right" springThemeText="documentSearch.button.download" />
	</div>
</form>
</c:if>

<br>
   <!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
   <br/>
   <div class="item_container">
      <table id="order_history" class="backorder-detail documentsearchTable" data-zebra="tbody tr">
         <thead class="">
            <tr class="firstrow">
               		<td>
                  		<h3>
                    		 <spring:theme code="documentSearch.list.code" />
                  		</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.name" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.invoice" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.invoiceDate" />
						</h3>
					</td>
					<td>
						<h3>
							<img src="${themeResourcePath}/images/icon-invoice-download.png" alt=""/><input type="checkbox" id="headerInvoiceCheckbox">
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.mtr" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.remission" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.debitNote" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.creditNote" />
						</h3>
					</td>
					<c:if test="${baseStore.uid eq '2000' || baseStore.uid eq '6000'}">
						<td>
							<h3>
								<spring:theme code="documentSearch.list.tally" />
							</h3>
						</td>
					</c:if>	
					<td>
						<h3>
							<spring:theme code="documentSearch.list.boL" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.po" />
						</h3>
					</td>
					<c:choose>
						<c:when test="${baseStore.uid eq '5000'}">
							<td>
								<h3>
									<spring:theme code="documentSearch.list.so.a4c" />
								</h3>
							</td>
						</c:when>
						<c:when test="${baseStore.uid eq '6000'}">
							<td>
								<h3>
									<spring:theme code="documentSearch.list.so.axis" />
								</h3>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<h3>
									<spring:theme code="documentSearch.list.so" />
								</h3>
							</td>
						</c:otherwise>
					</c:choose>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.quote" />
						</h3>
					</td>
					<td>
						<h3>
							<img src="${themeResourcePath}/images/icon-download.png" alt=""/><input type="checkbox" id="headerAllCheckbox">
						</h3>
					</td>
				</tr>
			</thead>
			<tbody class="">
				<c:forEach items="${searchPageData.results}" var="document" varStatus="status">
					<!-- variable to concatenate the doc codes for the checkbox displayed at the end of each row -->
					<c:set var="downloadAllCheckboxValue" value=""/>
					<tr class="">
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorder_address_label_${document.code}">
								${document.code}
						   </ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Left">
							<ycommerce:testId code="backorderHistory_partNumber_label_${document.name}">
							   ${document.name}
						   </ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
						   <ycommerce:testId code="backorderHistory_customerPO_label_${document.invoice}">
								<a href="javascript:void(0)" id="myLinkInvoice_${document.invoice}_${document.code}" class="myLinkInvoice" >${document.invoice}</a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.invoice}_${document.code}_F,${document.invoice}_${document.code}_M"/>
						   </ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center" >
							<ycommerce:testId code="backorderHistory_customerPO_label_${document.dateInvoice}">
								 ${document.dateInvoice}
								 <%--
								 <fmt:parseDate value="${document.dateInvoice}" pattern="yyyy-MM-dd" var="myDate"/>
								 <formDate:formFormatDate pattern="yyyy-MM-dd" value="${myDate}" />
								 --%>
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
						   <ycommerce:testId code="backorderHistory_customerPO_label_${document.invoice}">
								<input index="${status.index}" class="downloadCheck" value="${document.invoice}_${document.code}_F,${document.invoice}_${document.code}_M" type="checkbox" name="downloadF_${document.invoice}" id="downloadInvoices_${document.invoice}" title="download invoice"/>
						   </ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.mtr}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.mtr}_${document.code}_C" class="myLinkMiscDoc">${document.mtr}</a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.mtr}_${document.code}_C"/>								
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.remission}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.remission}_${document.code}_P" class="myLinkMiscDoc">${document.remission}</a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.remission}_${document.code}_P"/>
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.debitNote}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.debitNote}_${document.code}_N" class="myLinkMiscDoc">${document.debitNote}</a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.debitNote}_${document.code}_N"/>
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.creditNote}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.creditNote}_${document.code}_N" class="myLinkMiscDoc">${document.creditNote}</a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.creditNote}_${document.code}_N"/>
							</ycommerce:testId>
						</td>
						<c:if test="${baseStore.uid eq '2000' || baseStore.uid eq '6000'}">
							<td class="Text_Table_Minimum_Align_Center">
								<ycommerce:testId code="backorderHistory_pieces_label_${document.tolly}">
									<a href="javascript:void(0)" id="myLinkMiscDoc_${document.tolly}_${document.code}_T" class="myLinkMiscDoc">${document.tolly}</a>
									<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.tolly}_${document.code}_T"/>							
								</ycommerce:testId>
							</td>
						</c:if>
						<c:choose>
							<c:when test="${baseStore.uid eq '5000'}">
								<td class="Text_Table_Minimum_Align_Center">
									<ycommerce:testId code="backorderHistory_pieces_label_${document.boL}">
										${document.boL}
									</ycommerce:testId>
								</td>
							</c:when>
							<c:otherwise>
								<td class="Text_Table_Minimum_Align_Center">
									<ycommerce:testId code="backorderHistory_pieces_label_${document.boL}">
										<a href="javascript:void(0)" id="myLinkMiscDoc_${document.boL}_${document.code}_B" class="myLinkMiscDoc"><fmt:formatNumber pattern="#" value="${document.boL}"/></a>							
										<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.boL}_${document.code}_B"/>
									</ycommerce:testId>
								</td>
							</c:otherwise>
						</c:choose>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.po}">
								<c:choose>
									<c:when test="${not empty document.soAttachments}">
										<a href="javascript:void(0)" id="myLinkPODoc_${document.so}_ORDER-ALL" class="myLinkPODoc">${document.po}</a>
										<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.so}_${document.code}_ORDER-ALL"/>
									</c:when>
									<c:otherwise>
										<span>${document.po}</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.so}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.so}_${document.code}_S" class="myLinkMiscDoc"><fmt:formatNumber pattern="#" value="${document.so}"/></a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.so}_${document.code}_S"/>							
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.quote}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.quote}_${document.code}_S" class="myLinkMiscDoc"><fmt:formatNumber pattern="#" value="${document.quote}"/></a>
								<c:set var="downloadAllCheckboxValue" value="${downloadAllCheckboxValue},${document.quote}_${document.code}_S"/>								
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<input index="${status.index}" class="downloadCheck"  type="checkbox" value="${downloadAllCheckboxValue}" name="downloadAll_${document.invoice}"  id="downloadAll_${document.invoice}" class="downloadCheck" title="download all documents"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
      </table>
   </div>

   
   <div class="item_container">
   
   <c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
   <div class="prod_refine-wrapper">
	<nav:paginationReportFooter top="false" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${documentListBaseUrl}?sort=${documentSearchForm.sort}&documentType=${documentSearchForm.documentType}&customer=${documentSearchForm.customer}&reference=${documentSearchForm.reference}&initialDate=${documentSearchForm.initialDate}&finalDate=${documentSearchForm.finalDate}"
		msgKey="documentSearch.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
	</div>
</c:if>


<form id="miscDocumentDownloadForm" target="iframePost" action="<spring:url value="/my-account/document/downloadDocument" />"> 
	<input type="hidden" id="miscDocumentNumberField" name="documentNumber" />
	<input type="hidden" id="miscDocumentOwnerField" name="documentOwner" />
	<input type="hidden" id="miscDocumentTypeField" name="documentType" />
</form>

<form id="transferDocumentForm"  target="iframePost" action="<spring:url value="/my-account/document/transferDocument" />" >
	<input type="hidden" id="documentNumberField" name="documentNumber" />
	<input type="hidden" id="documentOwnerField" name="documentOwner" />
	<input type="hidden" id="transferTypeField" name="transferType" />
</form>

<form id="PODocumentDownloadForm" target="iframePost" action="<spring:url value="/my-account/document/order" />">
	<input type="hidden" id="orderDocumentField" name="orderCode"/>
	<input type="hidden" id="attachmentCodeField" name="attachmentCode" />
</form>

<iframe name="iframePost" style="visibility: hidden; display:none;">
</iframe>

</div> 
   
</div>
<script>
	function sendGA ()
	{
		ga('send','event','DocumentSearch','viewDocumentSearch');
	}
</script>
<script type="text/javascript">
onDOMLoaded(initDocumentList);

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

function bindSearchFormElements()
{
	jQuery("#initialDate").datepicker({dateFormat: 'mm-dd-yy'});
	jQuery("#finalDate").datepicker({dateFormat: 'mm-dd-yy'});
	
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#documentSearchSubmit").click(submitForm);
	
	$("#order_history tr:even").addClass("filaImpar");
	$("#order_history tr:odd").addClass("filaPar");
}

function initDocumentList()
{
	ACC.neorisDownloadDocuments.initialize({
		transferModalMessages: {
			title: "<spring:theme code="invoices.modal.download.title"/>",
			content: "<spring:theme code="invoices.modal.download.content"/>",
			button1: "<spring:theme code="invoices.modal.download.button.download"/>",
			button2: "<spring:theme code="invoices.modal.download.button.email"/>"
		}
	});
	
	bindSearchFormElements();
	
	$("#headerAllCheckbox").on("change",toogleAllDocumentsCheckboxes);
	$("#headerInvoiceCheckbox").on("change",toogleInvoiceCheckboxes);
}

function toogleInvoiceCheckboxes()
{
	if($(this).attr("checked") == "checked")
	{
// 		$('[id*="downloadInvoices"]').attr("checked",true);
		jQuery.each($('[id*="downloadInvoices"]'),function(key,value){
			if($(value).attr("checked") != "checked")
			{
				$(value).attr("checked",true)
				jQuery(value).trigger("change");
			}
		});
	}else
	{
// 		$('[id*="downloadInvoices"]').attr("checked",false);
		jQuery.each($('[id*="downloadInvoices"]'),function(key,value){
			if($(value).attr("checked") == "checked")
			{
				$(value).attr("checked",false)
				jQuery(value).trigger("change");
			}
		});
	}
}

function toogleAllDocumentsCheckboxes()
{
	if($(this).attr("checked") == "checked")
	{
// 		$('[id*="downloadAll"]').attr("checked",true);
		jQuery.each($('[id*="downloadAll"]'),function(key,value){
			if($(value).attr("checked") != "checked")
			{
				$(value).attr("checked",true)
				jQuery(value).trigger("change");
			}
		});
	}else
	{
// 		$('[id*="downloadAll"]').attr("checked",false);
		jQuery.each($('[id*="downloadAll"]'),function(key,value){
			if($(value).attr("checked") == "checked")
			{
				$(value).attr("checked",false)
				jQuery(value).trigger("change");
			}
		});
	}
}

</script>