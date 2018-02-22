
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="backorder" tagdir="/WEB-INF/tags/mobile/backorder" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/mobile/common" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/mobile/header"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="foot" tagdir="/WEB-INF/tags/mobile/footer"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/mobile/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>


<c:set var="backorderDetailBaseUrl" value="/my-account/backorder/detail" />
<spring:url var="backorderDetailSearchUrl" value="${backorderDetailBaseUrl}" />

<a id="top"></a>
		
			
		<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 20px; text-align: justify;">
                	<spring:theme code="mobile.backorder.title" /> <br>
                </h1>
				<p style="font-size: 10.5px; padding: 0 20px; text-align: justify;"><spring:theme code="mobile.backorder.subtitle" /></p>
			</div>
		</div>
		
		 <%-- <backorder:backOrderDetailSearchFormMobile/>  --%>
		 

		
		<div class="item_container_holder" style="display: block; ">
	<form:form id="searchForm" class="js-form" action="${backorderDetailSearchUrl}" method="get">
		
		
		
			<input type="hidden" name="customer" value="${backorderDetailSearchForm.customer}" />
			<input type="hidden" id="_export" name="_export" value="" />
		
			<table id="gradTableForm" style="width: 100%; float: left; height:auto; padding:0 10px;">
				<tr>
					<td class="td-form" style="width: 10%;">
					<spring:theme code="Customer"/>
					</td>
					<td style="font-weight: lighter;">
					${backorderDetailSearchForm.customer}
					</td>
				</tr>
				
				<tr>
				<td class="td-form" style="width: 10%;">
					<spring:theme code="backorderDetail.list.order"/>
					</td>
					<td colspan="2">
				       <formUtil:formInputTextInlineMobile  
						path="" 
						id="order"
						mandatory="false" 
						type="text" 
						name="order"					
						tabindex="103" 
						value="${backorderDetailSearchForm.order}"
						divCSS="search-form-options"											
						asterisk="" 
						inputCSS=""  />
				    </td>	
					 <%-- <td class="td-form" colspan="2">
						<formUtil:formInputTextInlineMobile
							path=""
							id="order"
							mandatory="false"
							type="text"
							name="order"
							value="${backorderDetailSearchForm.order}"
							tabindex="102"
							divCSS="search-form-options"
							spanCSS="labelMedium"
							placeholderSpringThemeText="${placeHolder}"
							springThemeText="backorderDetail.list.order"
							includeColon="true"
							asterisk=""
							inputCSS="" />
					</td>  --%>
				</tr>
				<tr>
				<td class="td-form" style="width: 30%;">
					<spring:theme code="backorderDetail.list.customerPO"/>
					</td>
					<td colspan="2">
				       <formUtil:formInputTextInlineMobile  
						path="" 
						id="customerPO"
						mandatory="false" 
						type="text" 
						name="customerPO"					
						tabindex="103" 
						value="${backorderDetailSearchForm.customerPO}"
						divCSS="search-form-options"											
						asterisk="" 
						inputCSS=""  />
				    </td>	
					<%-- <td class="td-form" colspan="2">
						<formUtil:formInputTextInlineMobile
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
					</td> --%>
				</tr>
				<tr>
					 <td class="td-form" style="width: 20%">
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme  code="text.account.orderHistory.filter.from" />
			       </td>
			     
			       <td colspan="2">
			           <%-- <spring:theme var="placeHolder" code="invoice.list.dateFrom.inline" /> --%>
			           <formUtil:formInputTextInlineCalMobile
						path="" 
						id="initialDate"
						mandatory="false" 
						type="text" 
						name="initialDate"				 	
						tabindex="105" 
						value="${backorderDetailSearchForm.initialDate}" 
						divCSS="search-form-options"
						placeholderSpringThemeText="${placeHolder}" 						
						asterisk="" 
						inputCSS="" />
						<img src="${themeResourcePath}/images/icon_calendar.gif" alt="" style="width: 20px; vertical-align: middle;"> 
			       </td>
			       
					<%-- <td class="td-form" colspan="2">
						<spring:theme var="placeHolder" code="invoice.list.dateFrom.inline" />
						<formUtil:formInputTextInlineCalMobile
							path="" 
							id="initialDate"
							mandatory="false" 
							type="text" 
							name="initialDate" 
							value="${backorderDetailSearchForm.initialDate}" 
							tabindex="103" 
							divCSS="search-form-options"
							placeholderSpringThemeText="${placeHolder}" 
							springThemeText="mobile.backorderDetail.list.dateFrom"
							includeColon="true"
							asterisk="" 
							inputCSS="" />
						</td> --%>
				</tr>
				<tr>
				 <td class="td-form">
                      <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
			          <spring:theme code="text.account.orderHistory.filter.to" />
			       </td>
			      
			       <td>
			          <%-- <spring:theme var="placeHolder" code="invoice.list.dateTo.inline" /> --%>
			          <formUtil:formInputTextInlineCalMobile
						path="" 
						id="finalDate"
						mandatory="false" 
						type="text" 
						name="finalDate"						
						tabindex="106" 
						value="${backorderDetailSearchForm.finalDate}" 
						divCSS="search-form-options"	
						placeholderSpringThemeText="${placeHolder}" 					
						asterisk="" 
						inputCSS="" />
						<img src="${themeResourcePath}/images/icon_calendar.gif" alt="" style="width: 20px; vertical-align: middle;">
			       </td>
				<%-- <td class="td-form" colspan="2">
						<spring:theme var="placeHolder" code="invoice.list.dateFrom.inline" />
						<formUtil:formInputTextInlineCalMobile
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
					</td> --%>
				</tr>
				
				<tr>
					<td colspan="2" style="padding:0 10px 10px 10px;">
						<span><spring:theme code="backorderDetail.list.address"/></span>
						<select id="address" name="address" class="dropDownWide" style="border: solid 3px gray;">
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
				</tr>	
				<tr>
					<td colspan="2">
				          <formUtil:formButtonMobile
						id="backorderSearchSubmit"
						name="backorderSearchSubmit"
						type="submit" 
						css="button button yellowXXX positive button-float-right2"
						tabindex="106"
						springThemeText="invoice.button.search" />
			       	</td>
			       
			      
			    </tr>			
				<%-- <tr>
					<td>
						<spring:theme var="placeHolder" code="backorderDetail.list.customerPO" />
					</td>
				</tr>
				
				
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
					<td class="columnAlignRight">
						<span><spring:theme code="backorderDetail.list.address"/>:</span>
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
					</td>
				</tr> --%>
			</table>
			<%-- <div class="search-form-options-search-button">
				<formUtil:formButtonMobile
					id="backorderSearchSubmit"
					type="submit" 
					css="positive button-float-right"
					tabindex="106"
					springThemeText="backorder.button.search" />
			</div> --%>
			<input type="hidden" id="sortField" name="sort" value="" />
			
		</form:form>
	</div>

		<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: black; width: 100%; "></div>
		<br><br>
		
		<div id="resultsMobile" class="item_container" style="width: 100%; float: left; height:auto; text-align: justify;">	
			<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				
					<nav:paginationReportMobile top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderListBaseUrl}?sort=${backorderSearchForm.sort}&customer=${backorderSearchForm.customer}"
		msgKey="backorder.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
					
						
			</c:if>
			
			<%-- <div class="item_container" style="width: 100%; float: left; height:auto; text-align: justify; background: none repeat scroll 0 0 #939292;">
				<c:forEach items="${searchPageData.results}" var="order">
					<c:url value="/my-account/order/${order.code}" var="myAccountOrderDetailsUrl"/>
					<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="orderDetailView(${order.code});">
						<tr>
							<td>
								${order.code}
							</td>
						</tr>
						<tr>
							<td>
								${order.purchaseOrderNumber}
							</td>
							<td>
								<spring:theme code="text.account.order.status.display.${order.statusDisplay}"/>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:formatDate pattern="MM/dd/y" value="${order.created}" />
							</td>
						</tr>
					</table>
				</c:forEach>
			</div> --%>
			
			<form:form modelAttribute="backorderForm" method="post" action="${backorderListSearchUrl}" class="js-form js-invoice-selected">
	<c:forEach items="${searchPageData.results}" var="backorderDetail" varStatus="status">
	<%-- <c:url value="/my-account/backorder/detail/?customer=${backorder.customer.code}&name=${backorder.customer.name}" var="backorderDetailUrl" />--%>
	<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="backOItem(${backorderDetail.order},${backorderDetailSearchForm.customer},${backorderDetail.partNumber});" id="resultsMobileXXX">
				
	<%-- 
	<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="backOItem(${backorderDetailSearchForm.customer},${backorderDetail.order},${backorderDetail.order});">
	 --%>			 <tr>
					<td>
						<spring:theme code="backorderDetail.list.order" />:
					</td>
					<td style="font-weight: lighter;">
						${backorderDetail.order} 
					</td>
				
					<td>
						<spring:theme code="backorderDetail.list.part" />
					</td>
					<td style="font-weight: lighter;">
						${backorderDetail.partNumber}
					</td>
				</tr>
				 
				<tr>
					<td>
						<spring:theme code="backorderDetail.list.orderDate" />
					</td>
					<td style="font-weight: lighter;">
						<formDate:formFormatDate pattern="MM/dd/yyyy" value="${backorderDetail.orderDate}" />
					</td>
					<td>
						<spring:theme code="backorder.list.pendingKilos" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.pendingKilos}" maxFractionDigits="3" minFractionDigits="3"/>
						
					</td>
					<td><img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt="">	</td>
					
				</tr> 
				
				<tr>
					<td>
						<spring:theme code="backorder.list.kilosReady" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.readyKilos}" maxFractionDigits="3" minFractionDigits="3"/>
					</td>
					<td>
						<spring:theme code="backorder.list.kilosLoading" />
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorderDetail.loadingKilos}" maxFractionDigits="3" minFractionDigits="3"/>
					</td>
				</tr>
				
				<%-- <tr>
					
					<td>
						Balance:
					</td>
					<td>
						<fmt:formatNumber type="number" value="${backorderDetail.balanceKilos}" maxFractionDigits="3" minFractionDigits="3"/>
					</td>
				</tr> --%>
				
				
			
				
				<%-- <tr class="">
					<td class="balanceStatement-date noBorder Text_Table_Align_Center"> 
						<ycommerce:testId code="backorderHistory_backorderCustomer_link_${backorder.customer}">
							<c:url value="/my-account/backorder/detail/?customer=${backorder.customer.code}" var="backorderDetail" />
							<commonUtil:link href="${invoiceDetail}" springThemeText="${invoice.number}" />
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
							
								<fmt:formatNumber type="number" value="${backorder.kgsOrder}" maxFractionDigits="2" minFractionDigits="2"/>
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_pendingKilos_label_${backorder.pendingKilos}">
							
								<fmt:formatNumber type="number" value="${backorder.pendingKilos}" maxFractionDigits="2" minFractionDigits="2"/>
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_readyKilos_label_${backorder.readyKilos}">
							
								<fmt:formatNumber type="number" value="${backorder.readyKilos}" maxFractionDigits="2" minFractionDigits="2"/>
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-date noBorder Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_loadingKilos_label_${backorder.loadingKilos}">
							
								<fmt:formatNumber type="number" value="${backorder.loadingKilos}" maxFractionDigits="2" minFractionDigits="2"/>
							
						</ycommerce:testId>
					</td>
					
					<td class="balanceStatement-customer Text_Table_Align_Right">
						<ycommerce:testId code="backorderHistory_balanceKilos_label_${backorder.balanceKilos}">
							
								<fmt:formatNumber type="number" value="${backorder.balanceKilos}" maxFractionDigits="2" minFractionDigits="2"/>
							
						</ycommerce:testId>
					</td>
				</tr>
			 --%>

	</table>
	</c:forEach>
		
</form:form>
			
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
			
					<nav:paginationReportMobile top="true" supportShowPaged="${isShowPageAllowed}"
		supportShowAll="${isShowAllAllowed}"
		searchPageData="${searchPageData}"
		searchUrl="${backorderListBaseUrl}?sort=${backorderSearchForm.sort}&customer=${backorderSearchForm.customer}"
		msgKey="backorder.list.page.currentPage"
		numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />
				
			</c:if>
	</div>		
			<foot:footerWTop searchPageData="${searchPageData}"	/>
			
<script type="text/javascript">



onDOMLoaded(initbackOrderList);

function initbackOrderList()
{	

	//jQuery("#customer").on("change",updateSelectAddress);	
	//jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
	//jQuery("#orderSearchSubmit").click(submitForm);		
	jQuery("#initialDate").datepicker({dateFormat: 'yy-mm-dd'});
	jQuery("#finalDate").datepicker({dateFormat: 'yy-mm-dd'});
	
}

function backOItem(code, customer, partNumber)
{
	//alert(customer);
	while (code.toString().length<10)
	{
		code = '0'+code;
	}
	
	document.location.href=window.location.origin+"/store/my-account/backorder/detailItem/?order=" + code + "&customer=" + customer+ "&partNum=" +partNumber;
}

	
</script>
