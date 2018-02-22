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

<c:set var="documentListBaseUrl" value="/my-account/document/listMaterial" />
<spring:url var="documentListSearchUrl" value="${documentListBaseUrl}" />

<div class="item_container_holder" style="display: block;">

<div class="title_holder">
   <h2>
      <spring:theme code="documentSearch.list.search" text="Document Search"/>-Material
   </h2>
</div>
	

<div class="search-form"  style="display: block;">
	<form:form id="searchForm" class="js-form" action="${documentListSearchUrl}" method="get">
		
			<table border="0">
				<tr style="display: none; visibility: hidden;">
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
					       		<c:choose>
									<c:when test="${EnumTypeInvoice eq documentSearchForm.documentType}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							   	<option value="${EnumTypeInvoice}" ${selected} ><spring:theme code="documentSearch.list.documentType.${EnumTypeInvoice}"/></option>
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
		                         tabindex="106" springThemeText="documentSearch.button.search" />
</div>
<br>



<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
<div class="prod_refine-wrapper">
	<nav:paginationReportFooter top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${documentListBaseUrl}?sort=${documentSearchForm.sort}&documentType=${documentSearchForm.documentType}&customer=${documentSearchForm.customer}&reference=${documentSearchForm.reference}&initialDate=${documentSearchForm.initialDate}&finalDate=${documentSearchForm.finalDate}"
		msgKey="documentSearch.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
</div>

<br><br>

<%--  <div id="spinnerWrapper" style="display: none;">
   <img id="spinner" src="${commonResourcePath}/images/spinner.gif" alt="spinner"/>
	</div> --%>
	
<form style="display:none; visibility:hidden;" id="massiveDocumentDownloadForm" target="iframePost" action="<spring:url value="/my-account/document/downloadMassiveDocuments" />" method="POST"> 
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
					<%-- <td>
						<h3>
							<img src="${themeResourcePath}/images/icon-invoice-download.png" alt=""/>
						</h3>
					</td> --%>
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
					<%-- <td>
						<h3>
							<spring:theme code="documentSearch.list.debitNote" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.creditNote" />
						</h3>
					</td>
					<td>
						<c:if test="${baseStore.uid eq '2000'}">
							<h3>
								<spring:theme code="documentSearch.list.tally" />
							</h3>
						</c:if>	
					</td>
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
					<td>
						<h3>
							<spring:theme code="documentSearch.list.so" />
						</h3>
					</td>
					<td>
						<h3>
							<spring:theme code="documentSearch.list.quote" />
						</h3>
					</td>
					<td>
						<h3>
							<img src="${themeResourcePath}/images/icon-download.png" alt=""/>
						</h3>
					</td> --%>
				</tr>
			</thead>
			<tbody class="">
				<c:forEach items="${searchPageData.results}" var="document" varStatus="status">
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
								${document.invoice}
						   </ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center" >
												<ycommerce:testId code="backorderHistory_customerPO_label_${document.dateInvoice}">
													${document.dateInvoice}
												</ycommerce:testId>
											</td>
						
									
						<%-- <td class="Text_Table_Minimum_Align_Left">
							<ycommerce:testId code="backorderHistory_partNumber_label_${document.dateInvoice}">
							   ${document.dateInvoice}
						   </ycommerce:testId>
						</td> --%>
						
						
						<%-- <td class="Text_Table_Minimum_Align_Center">
						   <ycommerce:testId code="backorderHistory_customerPO_label_${document.invoice}">
								<input class="downloadCheck" value="${document.invoice}_${document.code}_F,${document.invoice}_${document.code}_M" type="checkbox" name="downloadF_${document.invoice}" id="downloadInvoices_${document.invoice}" title="download invoice"/>
						   </ycommerce:testId>
						</td> --%>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.mtr}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.mtr}_${document.code}_C" class="myLinkMiscDoc">${document.mtr}</a>								
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.remission}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.remission}_${document.code}_P" class="myLinkMiscDoc">${document.remission}</a>
							</ycommerce:testId>
						</td>
						<%-- <td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.debitNote}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.debitNote}_${document.code}_N" class="myLinkMiscDoc">${document.debitNote}</a>
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.creditNote}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.creditNote}_${document.code}_N" class="myLinkMiscDoc">${document.creditNote}</a>
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.tolly}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.tolly}_${document.code}_T" class="myLinkMiscDoc">${document.tolly}</a>							
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.boL}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.boL}_${document.code}_B" class="myLinkMiscDoc"><fmt:formatNumber pattern="#" value="${document.boL}"/></a>							
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.po}">
								<a href="javascript:void(0)" id="myLinkPODoc_${document.so}" class="myLinkPODoc">${document.po}</a>
								<span>${document.po}</span> 
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.so}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.so}_${document.code}_S" class="myLinkMiscDoc"><fmt:formatNumber pattern="#" value="${document.so}"/></a>							
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<ycommerce:testId code="backorderHistory_pieces_label_${document.quote}">
								<a href="javascript:void(0)" id="myLinkMiscDoc_${document.quote}_${document.code}_S" class="myLinkMiscDoc"><fmt:formatNumber pattern="#" value="${document.quote}"/></a>								
							</ycommerce:testId>
						</td>
						<td class="Text_Table_Minimum_Align_Center">
							<input class="downloadCheck"  type="checkbox" value="${document.quote}_${document.code}_S,${document.so}_${document.code}_S,${document.boL}_${document.code}_B,${document.tolly}_${document.code}_T,${document.creditNote}_${document.code}_N,${document.debitNote}_${document.code}_N,${document.remission}_${document.code}_P,${document.mtr}_${document.code}_C,${document.invoice}_${document.code}_F,${document.invoice}_${document.code}_M" name="downloadAll_${document.invoice}"  id="downloadAll_${document.invoice}" class="downloadCheck" title="download all documents"/>
						</td> --%>
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
	<input type="hidden" id="orderDocumentField" name="order"/>
</form>

<iframe name="iframePost"  id="pp" style="visibility: hidden; display:none;">
</iframe>

</div> 
   
</div>

<script type="text/javascript">
onDOMLoaded(initDocumentList);

var intval=""

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

function cargado(){
alert("loadedXXC!");
alert(document.getElementById("pp").value);
}

//$('#pp').load(function(){
//    alert("loaded!");
//});


function bindSearchFormElements()
{
	jQuery("#initialDate").datepicker({dateFormat: 'mm-dd-yy'});
	jQuery("#finalDate").datepicker({dateFormat: 'mm-dd-yy'});
	
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#documentSearchSubmit").click(submitForm);
	jQuery("#documentDownloadSubmit").click(downloadDocumentsXX);
	
	
	$("#order_history tr:even").addClass("filaImpar");
	$("#order_history tr:odd").addClass("filaPar");
}

function downloadDocumentsXX()
{
	//alert("XX");
	//$('#spinnerWrapper').show();
	
	 doc = document.getElementById("pp");
	 if( doc.document ) {
         document.pp.document.body.innerHTML = ""; //Chrome, IE
     }else {
         doc.contentDocument.body.innerHTML = ""; //FireFox
     }
	
	
	blockUI();
	//setTimeout(spinnerHide, 5000);
	
	if(intval==""){
		//alert("inicio interval");
        intval=window.setInterval(revisaIframe,1000)
    }
	
	//intval=window.setInterval(revisaIframe,1000)
	//setInterval(revisaIframe, 1000);
	
	//spinner: $("<img src='" + ACC.config.commonResourcePath + "/images/spinner.gif' />");
	//ACC.common.showSpinnerById('spinner');
	
	/*var tmrReady = setInterval(isPageFullyLoaded, 100);
	
	function isPageFullyLoaded() {
	     if (document.readyState == "loaded" || document.readyState == "interactive" || document.readyState == "complete") {
	         //doWhatYouWant();
	         alert("YA");
	         clearInterval(tmrReady);
	     }
	 }
	*/
	//window.frames["pp"].focus();
	//window.frames["pp"].print();
}

//window.onload = function() {
//	alert("YAX");
//}

  
 

function spinnerHide()
{
	//$('#spinnerWrapper').hide();
	unblockUI();
}

function revisaIframe()
{
	var myIFrame2 = document.getElementById("pp").srcdoc;
	var myIFrame = document.getElementById("pp");
	alert("myIFrame2: " +myIFrame2);
	var content = myIFrame.contentWindow.document.body.innerHTML;
	    
	    //alert("content: " + content); 
	 
	    if(content != "")
	    	{
	    	unblockUI();
	    	window.clearInterval(intval)
	        intval=""
	    //alert("intervalo detenido");    
	 
	    //content = "The inside of my frame has now changed";
	    //myIFrame.contentWindow.document.body.innerHTML = content;
	    	}
	
	
	    alert("myIFrame2X: " +myIFrame2);
	
	//alert("az");
	//alert("url = " + document.frames["pp"].location.href);
	//*var x = document.getElementById("pp").src;
	//alert(x);
	//*if(x == "undefined")
	//*{
		//alert("123");
	//*}else
	//*{
	//*	alert(document.getElementById("pp").src);
	//*}
	
	
}

function initDocumentList()
{
	//alert("initDocumentList");
	ACC.neorisDownloadDocuments.initialize({
		transferModalMessages: {
			title: "<spring:theme code="invoices.modal.download.title"/>",
			content: "<spring:theme code="invoices.modal.download.content"/>",
			button1: "<spring:theme code="invoices.modal.download.button.download"/>",
			button2: "<spring:theme code="invoices.modal.download.button.email"/>"
		}
	});
	
	bindSearchFormElements();
}

</script>