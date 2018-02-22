
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
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>



<a id="top"></a>
		
			
		<div id="gradtitlePage" class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
			<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
				<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 20px; text-align: justify;">
                	<spring:theme code="mobile.backorder.title" /> <br>
                </h1>
				<p style="font-size: 10.5px; padding: 0 20px; text-align: justify;"><spring:theme code="mobile.backorder.subtitle" /></p>
			</div>
		</div>
		
		<backorder:backOrderSearchFormMobile/>

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
					<c:forEach items="${searchPageData.results}" var="backorder" varStatus="status">
	<%-- <c:url value="/my-account/backorder/detail/?customer=${backorder.customer.code}&name=${backorder.customer.name}" var="backorderDetailUrl" /> --%>
				<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="backorderDetailView(${backorder.customer.code});" id="resultsMobileXXX">
				<tr>
					<td>
						Customer: #
					</td>
					<td style="font-weight: lighter;">
					${backorder.customer.code}
						<%-- <c:url value="/my-account/backorder/detail/?customer=${backorder.customer.code}" var="backorderDetail" />
							<a href="${backorderDetailUrl}" customer="${backorder.customer.code}"><u>${backorder.customer.code}</u></a> --%>
					</td>
				</tr>
				<tr>
					<td  style="border-bottom: solid 1px black;" >
						Description:
					</td>
					<td style="font-weight: lighter; border-bottom: solid 1px black;" colspan="3">
						${backorder.customer.name}
					</td>
					
				</tr>
				
				<tr>
					<td>
						Pieces: 
					</td>
					<td style="font-weight: lighter;">
						${backorder.pcsOrder}
					</td>
					<td>
						Customer: #
					</td>
					<td style="font-weight: lighter;">
						${backorder.customer.code}
					</td>
				</tr>
				
				<tr>
					<td>
						Pending: 
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorder.pendingKilos}" maxFractionDigits="2" minFractionDigits="2"/>
					</td>
					<td>
						Ready:
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorder.readyKilos}" maxFractionDigits="2" minFractionDigits="2"/>
						<img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt="" style="width: 15px;"> 
					</td>
				</tr>
				
				<tr>
					<td>
						Loading: 
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorder.loadingKilos}" maxFractionDigits="2" minFractionDigits="2"/>
					</td>
					<td>
						Balance:
					</td>
					<td style="font-weight: lighter;">
						<fmt:formatNumber type="number" value="${backorder.balanceKilos}" maxFractionDigits="2" minFractionDigits="2"/>
					</td>
				</tr>
				
				
			
				
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
			<c:choose>
				<c:when test="${searchPageData.pagination.totalNumberOfResults gt 0}">
					<foot:footerWTop searchPageData="${searchPageData}"	/>
				</c:when>
				<c:otherwise>
					<foot:footer/>
				</c:otherwise>
			</c:choose>

			
			<script type="text/javascript">
function backorderDetailView(code)
{
	document.location.href=window.location.origin+"/store/my-account/backorder/detail/?customer=" + code + "&name= ";
}

</script>