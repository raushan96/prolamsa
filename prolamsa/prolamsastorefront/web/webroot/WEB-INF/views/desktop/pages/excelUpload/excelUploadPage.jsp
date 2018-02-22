<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="excelUpload" tagdir="/WEB-INF/tags/desktop/excelUpload"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	 <div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>		
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>		

	    <div class="span-20 last main-rightXXX">
			<c:set var="excelBaseUrl" value="/uploadExcel/advanced" />
			<spring:url var="excelSearchUrl" value="${excelBaseUrl}" />
			<div class="item_container_holder" style="display: block;">
				<div class="title_holder">
					<h2><spring:theme code="header.link.uploadExcel" text="Excel Upload"/></h2>
				</div>
				
				<div class="search-form" >
					 <form id="uploadDocumentForm" enctype="multipart/form-data" method="post" action="${excelSearchUrl}">
					
						<input name="_callbackFunctionName" value="uploadDocumentCallback" type="hidden" />	
							<table>
								<tr>
									<td class="">
										<p style="font-size: 12px">
											<c:choose>
											<c:when test="${baseStore.uid eq '2000'}">
												
																		<c:choose>
																			<c:when test="${baseStore.allowCategoryVisibility}">
																				<br>
																				<spring:theme code="uploadExcel.text.location"/> 
																			</c:when>
																		</c:choose>
											</c:when>
											<c:otherwise>
												<spring:theme code="uploadExcel.text"/>
																		<c:choose>
																			<c:when test="${baseStore.allowCategoryVisibility}">
																				<br>
																				<spring:theme code="uploadExcel.text.location"/> 
																			</c:when>
																		</c:choose>
											</c:otherwise>
										</c:choose>
										</p>
									</td>
								</tr>
								<tr>
									<td style="font-size: 15px">	
										<c:set value="display:none;" var="inputFileStyle" />
										<c:set value="jQuery(\"#file\").click()" var="onClickButton"/>
										<c:if test="${fn:contains(header['User-Agent'],'MSIE') and (fn:contains(header['User-Agent'],'9') or fn:contains(header['User-Agent'],'8'))}">
<%-- 											${header['User-Agent']} --%>
											<c:set value="margin-right:-127px;opacity:0; position: relative; filter:alpha(opacity: 0);z-index:2;" var="inputFileStyle" />
											<c:set value="" var="onClickButton"/>
										</c:if>						    																	
									    <spring:theme code="uploadExcel.filename"/>:
									    <input id="textFile" class="positive" type="text" readonly="readonly" size="36" value="<spring:theme code="uploadExcel.selectLocalFile"/>">
									    <input  size="1" type="file" id="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" onchange="checkfile(this);" style="font-size:20px;${inputFileStyle}" />	
									    
									    <formUtil:formButton
											id="buttonFile"
											type="button" 
											css="button yellow positive "											
											springThemeText="uploadExcel.browse"
											onClick="${onClickButton}" 
											
											/>
											&nbsp;

										  <formUtil:formButton
												id="uploadExcelSubmit"
												type="button" 
												css="button yellow positive "
												tabindex="106"
												springThemeText="uploadExcel.upload"
												onClickType="1"
												onClick="sendGA()" />
									</td>	
								</tr>
								<tr>
								<td>
								<c:if test = "${not empty errorDisplay}">						   
								    <div class="information_message negative">
										<span class="single"></span>
										<p style="font-size: 14px;color: red; font-weight: bold;">${errorDisplay}</p>
									</div>						    
								</c:if>		
								</td>
								</tr>
							</table>

<%-- 						<c:if test = "${not empty errorDisplay}">						    --%>
<!-- 						    <div class="information_message negative"> -->
<!-- 								<span class="single"></span> -->
<%-- 								<p style="color: red; font-weight: bold;">${errorDisplay}</p> --%>
<!-- 							</div>						     -->
<%-- 						</c:if>					 --%>
					 </form>
				</div>
				
				 <form name="AddToCartOrderForm" id="AddToCartOrderForm" class="add_to_cart_order_form" action="<c:url value="/cart/addGrid"/>" method="post" data-grid-confirm-message="${gridConfirmMessage}">
						<div id="resultsList" class="product_table" data-isOrderForm="true" data-isOnlyProductIds="${advancedSearchForm.onlyProductIds}">
							<!-- NEORIS_CHANGE #38 -->
							 <c:if test="${ not empty searchPageData.results }" >						
							    <!--NEORIS_CAHNGE #55 -->
								<!--Shows the not found SKU of the search  -->
								<br />
								<c:if test="${numberSKU != 0 and not empty numberSKU}">
									<span style="color:red; font-weight:bold">
										<spring:theme code="SKUs_not_found_important"/>&nbsp;${numberSKU}&nbsp;<spring:theme code="SKUs_not_found" />&nbsp;${listNotFound}&nbsp;<spring:theme code="SKUs_not_found_excel" />
									</span>
								</c:if>
							
							    <product:neorisAddItemsToCartButton />
							    <c:set var="uploadedFromExcel" value="true" scope="request"/>
							    <product:neorisProductList productList="${searchPageData.results}" />
							    <product:neorisAddItemsToCartButton />
							    
							    <c:if test="${ not empty listQuantityExact }" >
							    	<spring:theme code="excel.list.productQtyExact"/>: ${listQuantityExact}
							    </c:if>
							</c:if>
						</div> 
				</form>
			 
				
				
				<div id="skuIndexSavedValue" name="skuIndexSavedValue" data-sku-index="${sessionScope.skuIndex}"><!--  don't remove this div. This is used by the order form search --></div>
												
	 			<iframe name="uploadDocumentIframe" style="display: none;">	</iframe>
			</div>
		</div>
		<c:remove var="skuIndex" scope="session" />	
	</div>	
	<br />
</template:page>

<c:if test="${fn:contains(header['User-Agent'],'MSIE') and (fn:contains(header['User-Agent'],'9') or fn:contains(header['User-Agent'],'8'))}">

	<script type="text/javascript">
		onDOMLoaded(setFileInputWidth);
		
		function setFileInputWidth()
		{
			jQuery("#buttonFile").css({width:  jQuery("#file").width()*.72+"px"});
		}
		
	
	</script>

</c:if>

<%--
<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	
	<div class="item_container">
		<c:set var="excelBaseUrl" value="/uploadExcel/advanced" />
		<spring:url var="excelSearchUrl" value="${excelBaseUrl}" />

		
		  <div class="item_container_holder positive span-10">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2><spring:theme code="header.link.uploadExcel" text="Excel Upload"/></h2>
			</div>
			&nbsp; <!-- required to break flow of float h2 -->
	
			<div class="search-form" >
				 <form id="uploadDocumentForm" enctype="multipart/form-data" method="post" action="${excelSearchUrl}">
				
					<input name="_callbackFunctionName" value="uploadDocumentCallback" type="hidden" />	
						<table>
							<tr>
								<td class="">
									<p class="backOrderReportDownloadExcelDescription">
										<spring:theme code="uploadExcel.text"/>
									</p>
								</td>
							</tr>
							<tr>
								<td>							    																	
								    <input type="file" id="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" onchange="checkfile(this);" style="display:none" />	
								    <spring:theme code="uploadExcel.filename"/>:
								    <input id="textFile" class="positive" type="text" readonly="readonly" size="36" value="<spring:theme code="uploadExcel.selectLocalFile"/>">
								     <formUtil:formButton
										id="buttonFile"
										type="button" 
										css="positive"											
										springThemeText="uploadExcel.browse"
										onClick="jQuery(\"#file\").click()" 
										/>							    																							 																	 
								</td>	
							</tr>
						</table>
						
						<div class="search-form-options-search-button">
						  <formUtil:formButton
							id="uploadExcelSubmit"
							type="button" 
							css="positive right"
							tabindex="106"
							springThemeText="uploadExcel.upload" />
						</div>	
						<c:if test = "${not empty errorDisplay}">						   
						    <div class="information_message negative">
								<span class="single"></span>
								<p>${errorDisplay}</p>
							</div>						    
						</c:if>					
				 </form>
			</div>	
		   </div>
			
				 			
			  <form name="AddToCartOrderForm" id="AddToCartOrderForm" class="add_to_cart_order_form" action="<c:url value="/cart/addGrid"/>" method="post" data-grid-confirm-message="${gridConfirmMessage}">
						<div id="resultsList" class="product_table" data-isOrderForm="true" data-isOnlyProductIds="${advancedSearchForm.onlyProductIds}">
							<!-- NEORIS_CHANGE #38 -->
							 <c:if test="${ not empty searchPageData.results }" >						
							    <!--NEORIS_CAHNGE #55 -->
								<!--Shows the not found SKU of the search  -->
								<br />
								<c:if test="${numberSKU != 0 and not empty numberSKU}">
									<span style="color:red; font-weight:bold">
										<spring:theme code="SKUs_not_found_important"/>&nbsp;${numberSKU}&nbsp;<spring:theme code="SKUs_not_found" />&nbsp;${listNotFound}&nbsp;<spring:theme code="SKUs_not_found_excel" />
									</span>
								</c:if>
							
							    <product:neorisAddItemsToCartButton />
							    <product:neorisProductList productList="${searchPageData.results}" />
							    <product:neorisAddItemsToCartButton />
							</c:if>
						</div> 
				</form>
			 
				
				
				<div id="skuIndexSavedValue" name="skuIndexSavedValue" data-sku-index="${sessionScope.skuIndex}"><!--  don't remove this div. This is used by the order form search --></div>
												
	 			<iframe name="uploadDocumentIframe" style="display: none;">	</iframe>
			
		</div>
		<c:remove var="skuIndex" scope="session" />				
	
</template:page>
 --%>
 <script>
	function sendGA ()
	{
		ga('send','event','UploadExcel','uploadExcel');
	}
</script>
<script type="text/javascript">
onDOMLoaded(initUploadE);

function bindUploadExcel()
{
	jQuery("#uploadExcelSubmit").click(submitForm);
}

function submitForm()
{	
	if(jQuery("#file").val() == "") return false;
	
	jQuery("#uploadDocumentForm").submit();
}

function initUploadE()
{
	bindUploadExcel();
}

function checkfile(sender) {
    var validExts = new Array(".xlsx", ".xls");
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      jQuery("#file").val("");
      return false;
    }
    else return true;
}

$('input[id=file]').change(function() {
	$('#textFile').val($(this).val());
	});
</script>

</script>