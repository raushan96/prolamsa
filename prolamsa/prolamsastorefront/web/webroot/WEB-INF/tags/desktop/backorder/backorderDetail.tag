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
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<fmt:setLocale value="en_US" scope="session"/>

<c:set var="backorderDetailBaseUrl" value="/my-account/backorder/detail" />
<spring:url var="backorderDetailSearchUrl" value="${backorderDetailBaseUrl}" />

<div class="item_container_holder" style="display: block;">

<div class="title_holder">
	
	<h2><spring:theme code="backorderDetail.list.search" text="Backorder Detail"/></h2>
</div>
<p style="margin-left:30px; color:black; font-size:13px; font-weight:bold"><spring:theme code="backorder.message.new"/></p>
<div class="search-form" style="display: block;">
	<form:form id="searchForm" class="js-form" action="${backorderDetailSearchUrl}" method="get">
		<input type="hidden" name="customer" value="${backorderDetailSearchForm.customer}"  style="display: none;" />
		<input type="hidden" name="name" value="${backorderDetailSearchForm.name}"  style="display: none;" />
		<input type="hidden" id="site" name="site" value="${baseStore.uid}"  style="display: none;"  />
		<input type="hidden" id="_export" name="_export" value="" style="display: none;" />
	
		<table>
			<tr>
				    <td style="height: 30px; width: 100px;">
						<span class="labelMedium"><spring:theme code="balance.sortby.customer"/>:</span>
					</td>
					<td style="height: 30px; width: 70%;" >
						<span>${backorderDetailSearchForm.name} (${backorderDetailSearchForm.customer})</span>
					</td>
					<td style="height: 30px;" colspan="4"></td>
					
					
			</tr>
			</table>
			<table>
			<tr>
				   <td style="height: 30px; width:15%">
						<span class="labelMedium"><spring:theme code="backorderDetail.list.order"/>:</span>
					</td>
					<td style="height: 30px; width:15%">
					<formUtil:formInputTextInline 
						path="labelMedium"
						id="order"
						mandatory="false"
						type="text"
						name="order"
						value="${backorderDetailSearchForm.order}"
						tabindex="102"
						divCSS="search-form-options"
						
						 />
				</td>
				
				<td style="height: 30px; width:15%">
						<span class="labelMedium"><spring:theme code="backorderDetail.list.dateFrom"/>:</span>
					</td>
									<td style="height: 30px; width:20%">
					<formUtil:formInputTextInline 
						path="labelMedium"
						id="initialDate"
						mandatory="false"
						type="text"
						name="initialDate"
						value="${backorderDetailSearchForm.initialDate}"
						tabindex="102"
						divCSS="search-form-options"
						
						 />
				</td>
				
				<td style="height: 30px; width:5%">
						<span class="labelMedium"><spring:theme code="backorderDetail.list.dateTo"/>:</span>
					</td>
									<td style="height: 30px; width:15%">
					<formUtil:formInputTextInline 
						path="labelMedium"
						id="finalDate"
						mandatory="false"
						type="text"
						name="finalDate"
						value="${backorderDetailSearchForm.finalDate}"
						tabindex="102"
						divCSS="search-form-options"
						
						 />
				</td>
				
				
					
				


			</tr>
			
			<tr>
			<td style="height: 30px;">
						<span class="labelMedium"><spring:theme code="backorderDetail.list.customerPO"/>:</span>
					</td>
					<td style="height: 30px;">
					<formUtil:formInputTextInline 
						path="labelMedium"
						id="customerPO"
						mandatory="false"
						type="text"
						name="customerPO"
						value="${backorderDetailSearchForm.customerPO}"
						tabindex="102"
						divCSS="search-form-options"/>
					</td>
					
					<td style="height: 30px;">
						<span class="labelMedium"><spring:theme code="backorderDetail.list.address"/>:</span>
					</td>
					
					<td style="height: 30px; " >
					<select id="address" name="address" class="dropDownWide">
					    <option value=""><spring:theme code="All" /></option> 
						<c:forEach items="${listDataB2B}" var="eachAddress">
							<c:set var="selected" value="" />

							<c:if test="${eachAddress.code eq backorderDetailSearchForm.address}">
								<c:set var="selected" value="selected" />
							</c:if>
							<c:choose>
								<c:when test="${baseStore.name eq 'Prolamsa Mex'}">
									<option value="${eachAddress.code}" ${selected} >${eachAddress.formattedAddress2}</option>
								</c:when>
								<c:when test="${baseStore.name eq 'Prolamsa USA'}">
									<option value="${eachAddress.code}" ${selected} >${eachAddress.formattedAddress}</option>
								</c:when>
								<c:otherwise>
									<option value="${eachAddress.code}" ${selected} >${eachAddress.formattedAddress2}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
				<td colspan="2"></td>
			</tr>		
			
			
			  
			<%-- INICIA COMENTARIO
			<tr>
				<td style="padding: 0px 0px 5px 30px; width:200px; height:4px;">
					<label for="customer">
						<span class="labelMedium"><spring:theme code="Customer"/>:</span>
						
					</label>
				</td>
				<td style="padding: 0px 0px 5px 30px; width:700px; height:5px;">
					<label for="customer">
						
						<span>${backorderDetailSearchForm.customer}</span>
					</label>
				</td>
				
			</tr>
			 <tr>
				<td style="padding: 0px 0px 5px 30px; width:200px; height:4px;">
				<label for="customer">
						<span  class="labelMedium"><spring:theme code="backorderDetail.list.order"/>:</span>
						
					</label>
					
				</td>
				<td style="padding: 0px 0px 5px 30px; width:500px; height:5px;">
					<formUtil:formInputTextInline 
						path=""
						id="order"
						mandatory="false"
						type="text"
						name="order"
						value="${backorderDetailSearchForm.order}"
						tabindex="102"
						divCSS="search-form-options"
						 />
				</td>
				
				
				<td style="padding: 0px 0px 5px 30px; width:700px; height:5px;">
					<formUtil:formInputTextInline 
						path="" 
						id="initialDate"
						mandatory="false" 
						type="text" 
						name="initialDate" 
						value="${backorderDetailSearchForm.initialDate}" 
						tabindex="103" 
						divCSS="search-form-options"
						springThemeText="backorderDetail.list.dateFrom"
						includeColon="true"
						asterisk="" 
						inputCSS="" />
				</td>
				
				<td style="padding: 0px 0px 5px 30px; width:500px; height:5px;">
					<formUtil:formInputTextInline 
						path="" 
						id="finalDate"
						mandatory="false" 
						type="text" 
						name="finalDate" 
						value="${backorderDetailSearchForm.finalDate}" 
						tabindex="104" 
						divCSS="search-form-options"
						springThemeText="invoice.list.dateTo"
						includeColon="true"
						asterisk="" 
						inputCSS="" />
				</td>
				</tr>
				
				<tr>
				<td style="padding: 0px 0px 5px 30px; width:200px; height:4px;">
				<label for="customer">
						<span  class="labelMedium"><spring:theme code="backorderDetail.list.customerPO"/>:</span>
						
					</label>
					
				</td>
				<td style="padding: 0px 0px 5px 30px; width:500px; height:5px;">
					<formUtil:formInputTextInline 
						path=""
						id="customerPO"
						mandatory="false"
						type="text"
						name="customerPO"
						value="${backorderDetailSearchForm.customerPO}"
						tabindex="102"
						divCSS="search-form-options"
						 />
				</td>
				
				
				<td style="padding: 0px 0px 5px 30px; width:700px; height:4px;">
				<label for="customer">
						<span  class="labelMedium"><spring:theme code="backorderDetail.list.address"/>:</span>
						
					</label>
					
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="address" name="address" class="dropDownWideXXX">
					    <option value=""><spring:theme code="All" /></option> 
						<c:forEach items="${listDataB2B}" var="eachAddress">
							<c:set var="selected" value="" />

							<c:if test="${eachAddress.code eq backorderDetailSearchForm.address}">
								<c:set var="selected" value="selected" />
							</c:if>
								
						   	<option value="${eachAddress.code}" ${selected} >${eachAddress.formattedAddress}</option>
						</c:forEach>
					</select>
				</td>
				
				
				
				<td style="padding: 0px 0px 5px 30px; width:500px; height:5px;">
					
				</td>
				
				
				
				</tr> 
				TERMINA COMENTARIO
				--%>
				
				
	<%--			<tr>
				<td  style="padding: 0px 0px 5px 30px; width:30px; height:20px;">
					<spring:theme var="placeHolder" code="invoice.list.dateFrom.inline" />
					<formUtil:formInputTextInline 
						path="" 
						id="initialDate"
						mandatory="false" 
						type="text" 
						name="initialDate" 
						value="${backorderDetailSearchForm.initialDate}" 
						tabindex="103" 
						divCSS="search-form-options"
						placeholderSpringThemeText="${placeHolder}" 
						springThemeText="backorderDetail.list.dateFrom"
						includeColon="true"
						asterisk="" 
						inputCSS="" />
						
					<spring:theme var="placeHolder" code="invoice.list.dateFrom.inline" />
					<formUtil:formInputTextInline 
						path="" 
						id="finalDate"
						mandatory="false" 
						type="text" 
						name="finalDate" 
						value="${backorderDetailSearchForm.finalDate}" 
						tabindex="104" 
						divCSS="search-form-options"
						placeholderSpringThemeText="${placeHolder}" 
						springThemeText="invoice.list.dateTo"
						includeColon="true"
						asterisk="" 
						inputCSS="" />
				</td>
			</tr>

			<tr>
			
			
				
				<td style="padding: 0px 0px 5px 30px; width:30px; height:20px;">
					<spring:theme var="placeHolder" code="backorderDetail.list.customerPO" />
					<formUtil:formInputTextInline 
						path=""
						id="customerPO"
						mandatory="false"
						type="text"
						name="customerPO"
						value="${backorderDetailSearchForm.customerPO}"
						tabindex="102"
						divCSS="search-form-options"
						spanCSS="labelMedium"
						placeholderSpringThemeText="${placeHolder}"
						springThemeText="backorderDetail.list.customerPO"
						includeColon="true"
						asterisk=""
						inputCSS="" />
				</td>

				
				<td style="padding: 0px 0px 5px 30px; width:30px; height:20px;">
				<div>
				<spring:theme code="backorderDetail.list.address" />
					
					<select id="address" name="address" class="dropDownWide">
					    <option value=""><spring:theme code="All" /></option> 
						<c:forEach items="${listDataB2B}" var="eachAddress">
							<c:set var="selected" value="" />

							<c:if test="${eachAddress.code eq backorderDetailSearchForm.address}">
								<c:set var="selected" value="selected" />
							</c:if>
								
						   	<option value="${eachAddress.code}" ${selected} >${eachAddress.formattedAddress}</option>
						</c:forEach>
					</select>
					</div>
				</td>
				
				<td style="width:60px; height:20px;">
				
				</td>
			</tr> --%>
		</table>
		
		<input type="hidden" id="sortField" name="sort" value="" />
	</form:form>
</div>

<div class="search-form-options-search-button">
<%-- <formUtil:formButton 
		disabled="${empty searchPageData.results}"
		id="exportXLSButton"
		type="button" 
		css="button yellow positive button-float-right"
		tabindex="106"
		springThemeText="general.button.exportXLS" />	
		
		<formUtil:formButton
		id="backorderGobackButton"
		type="submit" 
		css="button yellow positive button-float-right"
		tabindex="106"
		springThemeText="backorder.button.goback" /> --%>

	<formUtil:formButton
		id="backorderSearchSubmit"
		type="submit" 
		css="button yellow positive button-float-right"
		tabindex="106"
		springThemeText="backorder.button.search" />
	</div>
<br><br>


<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
<div class="prod_refine-wrapper">
	<nav:paginationReport top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderDetailBaseUrl}?order=${backorderDetailSearchForm.order}&customer=${backorderDetailSearchForm.customer}&sort=${backorderDetailSearchForm.sort}&address=${backorderDetailSearchForm.address}&initialDate=${backorderDetailSearchForm.initialDate}&finalDate=${backorderDetailSearchForm.finalDate}&customerPO=${backorderDetailSearchForm.customerPO}"
		msgKey="backorderDetail.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" neorisSortBy="true" />
</div>
</c:if>

	
<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->

<br/><br/>
<div class="item_container">
	<table id="order_history" class="backorder-detail" data-zebra="tbody tr">
		<thead class="">
			<c:set var="suffixHeaderLabels" value=""/>
			<c:if test="${baseStore.uid eq '6000'}">
				<c:set var="suffixHeaderLabels" value=".${baseStore.uid}"/>
			</c:if>
			<tr class="firstrow">
				<c:choose>
					<c:when test="${baseStore.uid eq '6000'}">
						<td class="backorderDetail-address" style="width: 150px;">
							<h3><spring:theme code="backorderDetail.list.plant" /></h3>
						</td>
					</c:when>
					<c:otherwise>
						<td class="backorderDetail-address" style="width: 290px;">
							<h3><spring:theme code="backorderDetail.list.address" /></h3>
						</td>							
					</c:otherwise>
				</c:choose>
				<td class="backorderDetail-part">
					<h3><spring:theme code="backorderDetail.list.part" /></h3>
				</td>
				<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.description" /></h3>
				</td>
				<td class="backorderDetail-deliveryDate">
					<h3><spring:theme code="backorderDetail.list.new.deliveryDate" /></h3>
				</td>
				<td class="backorderDetail-order">
					<h3><spring:theme code="backorderDetail.list.order" /></h3>
				</td>
				<td class="backorderDetail-orderDate">
					<h3><spring:theme code="backorderDetail.list.orderDate" /></h3>
				</td>
				<td class="backorderDetail-customerPO">
					<h3><spring:theme code="backorderDetail.list.po" /></h3>
				</td>
				<c:if test="${baseStore.uid eq '6000' }">
					<td class="balanceStatement-customer noBorder">
					<h3><spring:theme code="backorder.list.orderKilos${suffixHeaderLabels}" /></h3>
				</td>
				</c:if>
				
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
					<h3><spring:theme code="backorderDetail.list.status" /></h3>
				</td>
			</tr>
		</thead>

		<tbody class="">
			<c:forEach items="${searchPageData.results}" var="backorderDetail" varStatus="status">
				<tr class="">
					<td class="backorderDetail-address Text_Table_Minimum_Align_Left">
						<ycommerce:testId code="backorder_address_label_${backorderDetail.address}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									${backorderDetail.plant}
								</c:when>
								<c:otherwise>
									${backorderDetail.address}							
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					<td class="backorderDetail-part Text_Table_Minimum_Align_Left" >
						<ycommerce:testId code="backorderHistory_partNumber_label_${backorderDetail.partNumber}">
							<!--NOTA: Agregar a esta descripción, la concatenacion de las 6 variables (API) -->
							${backorderDetail.partNumber} 
						</ycommerce:testId>
					</td>
					<td class="balanceStatement-date noBorder Text_Table_Minimum_Align_Left">
						<ycommerce:testId code="backorderHistory_description_label_${backorderDetail.description}">
							${backorderDetail.description}
						</ycommerce:testId>
					</td>
					
					<td class="backorderDetail-deliveryDate Text_Table_Minimum_Align_Left">
						<ycommerce:testId code="backorderHistory_deliveryDate_label_${backorderDetail.deliveryDate}">
							
								<formDate:formFormatDate pattern="MM/dd/yyyy" value="${backorderDetail.deliveryDate}" />
							
						</ycommerce:testId>
					</td>
					
					<td class="backorderDetail-order Text_Table_Minimum_Align_Left">
						<ycommerce:testId code="backorderHistory_order_label_${backorderDetail.order}">
							${backorderDetail.order}
						</ycommerce:testId>
					</td>
					
					<td class="backorderDetail-orderDate Text_Table_Minimum_Align_Left">
						<ycommerce:testId code="backorderHistory_orderDate_label_${backorderDetail.orderDate}">
							
								<formDate:formFormatDate pattern="MM/dd/yyyy" value="${backorderDetail.orderDate}" />
							
						</ycommerce:testId>
					</td>
					
					<td class="backorderDetail-customerPO Text_Table_Minimum_Align_Left">
						<ycommerce:testId code="backorderHistory_customerPO_label_${backorderDetail.customerPO}">
							${backorderDetail.customerPO}
						</ycommerce:testId>
					</td>
					
					<c:if test="${baseStore.uid eq '6000' }">
						<td class="balanceStatement-date noBorder Text_Table_Minimum_Align_Right">
							<ycommerce:testId code="backorderHistory_orderKilos_label_${backorderDetail.orderQty}">
									<fmt:formatNumber type="number" value="${backorderDetail.orderQty2}" pattern="${metricTonsPattern }"/>
							</ycommerce:testId>
						</td>
					</c:if>
					
					<td class="balanceStatement-date noBorder Text_Table_Minimum_Align_Right">
						<ycommerce:testId code="backorderHistory_pendingKilos_label_${backorderDetail.pendingKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorderDetail.pendingQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorderDetail.pendingKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Minimum_Align_Right">
						<ycommerce:testId code="backorderHistory_readyKilos_label_${backorderDetail.readyKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorderDetail.readyQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorderDetail.readyKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Minimum_Align_Right">
						<ycommerce:testId code="backorderHistory_loadingKilos_label_${backorderDetail.loadingKilos}">
							<c:choose>
								<c:when test="${baseStore.uid eq '6000'}">
									<fmt:formatNumber type="number" value="${backorderDetail.loadingQty2}" pattern="${metricTonsPattern }"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="number" value="${backorderDetail.loadingKilos}" pattern="${metricTonsPattern }"/>
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
					
					<td class="Text_Table_Minimum_Align_Center" >
						<ycommerce:testId code="backorderHistory_customerPO_label_${backorderDetail.estatusEng}">
							<c:choose>
								<c:when test="${currentLanguage.isocode eq 'en'}">
									${backorderDetail.estatusEng}
								</c:when>
								<c:otherwise>
									${backorderDetail.estatusEsp}
								</c:otherwise>
							</c:choose>
						</ycommerce:testId>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<br>
<!-- <div class="search-form-options-search-button"> -->

<div class="search-form-options-search-button">
<formUtil:formButton 
		disabled="${empty searchPageData.results}"
		id="exportXLSButton"
		type="button" 
		css="button yellow positive button-float-right"
		tabindex="106"
		springThemeText="general.button.exportXLS" />	
		
		<formUtil:formButton
		id="backorderGobackButton"
		type="submit" 
		css="button yellow positive button-float-right"
		tabindex="106"
		springThemeText="backorder.button.goback" />
	</div>
		


<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
<div class="prod_refine-wrapper">

	<nav:paginationReportFooter top="false" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderDetailBaseUrl}?order=${backorderDetailSearchForm.order}&customer=${backorderDetailSearchForm.customer}&sort=${backorderDetailSearchForm.sort}&address=${backorderDetailSearchForm.address}&initialDate=${backorderDetailSearchForm.initialDate}&finalDate=${backorderDetailSearchForm.finalDate}&customerPO=${backorderDetailSearchForm.customerPO}"
		msgKey="backorderDetail.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
</div>
</c:if>

</div>

<script type="text/javascript">
onDOMLoaded(initBackorderDetail);

function bindSearchFormElements()
{
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#backorderSearchSubmit").click(submitForm);
	jQuery("#backorderGobackButton").click(goBackAction);
	jQuery("#exportXLSButton").click(exportXLSAction);
	
	$("#order_history tr:even").addClass("filaImpar");
    $("#order_history tr:odd").addClass("filaPar");
}

function goBackAction()
{
	window.location = "<spring:url value="/my-account/backorder/list" />";
}

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}

function initBackorderDetail()
{
	if(document.getElementById("site").value == "1000" || document.getElementById("site").value == "5000")
	{
		jQuery("#initialDate").datepicker({dateFormat: 'dd-M-yy'});
		jQuery("#finalDate").datepicker({dateFormat: 'dd-M-yy'});
	}else
	{
		jQuery("#initialDate").datepicker({dateFormat: 'M-dd-yy'});
		jQuery("#finalDate").datepicker({dateFormat: 'M-dd-yy'});
	}
	
	
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