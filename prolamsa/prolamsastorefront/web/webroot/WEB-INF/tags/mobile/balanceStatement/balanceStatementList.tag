<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/mobile/form"%>
<%@ taglib prefix="selectorM" tagdir="/WEB-INF/tags/mobile/common"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav" %>
<%@ taglib prefix="foot" tagdir="/WEB-INF/tags/mobile/footer"%>

<c:set var="balanceStatementListBaseUrl" value="/my-account/balance-statement" />
<spring:url var="balanceStatementListSearchUrl" value="${balanceStatementListBaseUrl}" />

<a id="top"></a>

<div id="gradtitlePage"  class="pagehead-wrapperXXA" style="position:relative; z-index:2; width: 100%; float: left; height:auto; -webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
	<div class="pagehead grid-container" style="width: 100%; float: left; height:auto; 	-webkit-box-shadow: 0px 9px 11px 0px rgba(50, 50, 50, 0.23); -moz-box-shadow:    0px 9px 11px 0px rgba(50, 50, 50, 0.23); box-shadow:         0px 9px 11px 0px rgba(50, 50, 50, 0.23);">
		<h1 style="font-weight: 700; font-size: 12.5px; text-transform: uppercase; padding: 0 20px; text-align: justify;">
        	<spring:theme code="mobile.balance.title" /> <br>
        </h1>
		<p style="font-size: 10.5px; padding: 0 20px; text-align: justify;"><spring:theme code="mobile.balance.subtitle" /></p>
	</div>
</div>

<div class="item_container_holder" style="display: block; ">
		<form:form  id="searchForm" class="js-form" action="${balanceStatementListSearchUrl}" method="get">
			<div>
			
			<table id="gradTableForm" style="width: 100%; float: left; height:auto; padding:0 10px;">	<tr>
					<td style="font-size: 16px; padding:0 8px 0px 10px;">
					  	 <selectorM:b2bUnitClientSelectorMobile dropDownCSS="dropDownMedium" spanCSS="labelSmall" b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${balanceStatementSearchForm.customer}"/>
					  	 <br>
					  	 <formUtil:formButtonMobile
							id="balanceStatementSearchSubmit"
							type="submit" 
							css="button yellowXXX positive button-float-right2"
							tabindex="106"
							springThemeText="text.account.balanceStatement.button.search" />
					</td>
				</tr>
			</table>
					
			<input type="hidden" style="display: none;" id="sortField" name="sort" value="" />
			</div>	
		</form:form>
	</div>
<!--  -->

<div style="float:left; height: 3px; padding: 0px; margin: 0px; background: black; width: 100%; "></div>
<br><br>

<div id="resultsMobile" class="item_container" style="width: 100%; float: left; height:auto; text-align: justify;">

<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
			<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				
					<nav:paginationReportMobile top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"
			searchUrl="${balanceStatementListBaseUrl}?sort=${balanceStatementSearchForm.sort}&customer=${balanceStatementSearchForm.customer}"
			msgKey="text.account.balanceStatement.list.page.currentPage"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />	
				
			</c:if>
			
			
				<c:forEach items="${searchPageData.results}" var="balanceStatement">
					<c:url value="/my-account/balance-statement-detail?customer=${balanceStatement.customer.uid}&row=0" var="balanceStatementDetailUrl" />
					<table style="background: none repeat scroll 0 0 #BFBEBE; margin-bottom: 5px;" onclick="balanceDetailView(${balanceStatement.customer.uid});" id="resultsMobileXXX">
					
				
					<tr>
						<td>
							<spring:theme code="backorder.list.customer" />:
						</td>
						<td style="font-weight: lighter;">
							${balanceStatement.customer.uid}
							<%-- <spring:theme code="backorder.list.customer" /># : ${balanceStatement.customer.uid}
							<c:url value="/balanceStatement/detail/?balanceStatementCustomer=${balanceStatement.customer.uid}&row=0" var="balanceStatementDetail" />
							<a href="${balanceStatementDetailUrl}" customer="${balanceStatement.customer.uid}"><u>${balanceStatement.customer.uid}</u></a>
							</h3> --%>
						</td>
					</tr>
					<tr>
						<td>
							Name:
						</td>
						<td style="font-weight: lighter; " colspan="3">
							${balanceStatement.customer.shortName} (${balanceStatement.customer.uid})
						</td>
						<td>
							<img src="${themeResourcePath}/images/icon_gtr_gray.gif" alt=""> 
							</td>
					</tr>
					<tr>
						<td>
							<spring:theme code="text.account.balanceStatement.list.pastDue" />:
						</td>
						<td style="font-weight: lighter; ">
							<fmt:formatNumber type="currency" value="${balanceStatement.pastDue}" maxFractionDigits="2" currencySymbol="$" />
						</td>
					
						<td>
							<spring:theme code="text.account.balanceStatement.list.current" />:
						</td>
						<td style="font-weight: lighter; ">
							<fmt:formatNumber type="currency" value="${balanceStatement.current}" maxFractionDigits="2" currencySymbol="$" />
						</td>
					</tr>
					
					<tr>
						<td>
							<spring:theme code="text.account.balanceStatement.list.balance" />:
						</td>
						<td style="font-weight: lighter; " >
							<fmt:formatNumber type="currency" value="${balanceStatement.balance}" maxFractionDigits="2" currencySymbol="$" />
						</td>
					
						<td>
							<spring:theme code="text.account.balanceStatement.list.creditLimit" />:
						</td>
						<td style="font-weight: lighter; " >
							<fmt:formatNumber type="currency" value="${balanceStatement.creditLimit}" maxFractionDigits="2" currencySymbol="$" />
						</td>
					</tr>
					
					
					
					<%-- <tr>
						<td>
							<h3>Name: ${balanceStatement.customer.shortName} (${balanceStatement.customer.uid})</h3>
						</td>
					</tr>
					<tr>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.pastDue" />: <fmt:formatNumber type="currency" value="${balanceStatement.pastDue}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.current" />: <fmt:formatNumber type="currency" value="${balanceStatement.current}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
					</tr>
					<tr>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.balance" />: <fmt:formatNumber type="currency" value="${balanceStatement.balance}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.creditLimit" />: <fmt:formatNumber type="currency" value="${balanceStatement.creditLimit}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
					</tr> --%>
				
			</table>
			
	</c:forEach>
			
				
						<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
				
					<nav:paginationReportMobile top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"
			searchUrl="${balanceStatementListBaseUrl}?sort=${balanceStatementSearchForm.sort}&customer=${balanceStatementSearchForm.customer}"
			msgKey="text.account.balanceStatement.list.page.currentPage"
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
function balanceDetailView(customer)
{
	document.location.href=window.location.origin+"/store/my-account/balance-statement-detail?customer="+customer+"&row=0";
}

</script>

<%-- 
<div class="item_container_holder" style="display: block;">
	<div class="title_holder">
		<h2><spring:theme code="text.account.balanceStatement.list" text="Balance Statement"/></h2>
	</div>
	
	<br><br>
	
	<p style="font-size: 16px">
		&nbsp;&nbsp;	<spring:theme code="text.mobile.backorder"/>
	</p>
	<p style="font-size: 16px">
		&nbsp;&nbsp;	<spring:theme code="text.mobile.backorder.two"/>
	</p>

	<div class="search-form">
		<form:form  id="searchForm" class="js-form" action="${balanceStatementListSearchUrl}" method="get">
			<table>
				<tr>
					<td style="font-size: 16px">
					  	 <selector:b2bUnitClientSelector dropDownCSS="dropDownMedium" spanCSS="labelSmall" b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${balanceStatementSearchForm.customer}"/>
					  	 <formUtil:formButton
							id="balanceStatementSearchSubmit"
							type="submit" 
							css="button yellow positive button-float-right"
							tabindex="106"
							springThemeText="text.account.balanceStatement.button.search" />
					</td>
				</tr>
			</table>			
			<input type="hidden" id="sortField" name="sort" value="" />
		</form:form>
	</div>

	<div class="">
		<nav:paginationReportFooter top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"
			searchUrl="${balanceStatementListBaseUrl}?sort=${balanceStatementSearchForm.sort}&customer=${balanceStatementSearchForm.customer}"
			msgKey="text.account.balanceStatement.list.page.currentPage"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
	</div>
	
	<c:forEach items="${searchPageData.results}" var="balanceStatement" varStatus="status">
		<c:url value="/my-account/balance-statement-detail?customer=${balanceStatement.customer.uid}" var="balanceStatementDetailUrl" />
		<div class="item_container">
			<table style="font-size: 1.1em">
				<tbody>
					<tr>
						<td>
							<h3><spring:theme code="backorder.list.customer" /># : ${balanceStatement.customer.uid}</h3> 
							<c:url value="/balanceStatement/detail/?balanceStatementCustomer=${balanceStatement.customer.uid}" var="balanceStatementDetail" />
							<a href="${balanceStatementDetailUrl}" customer="${balanceStatement.customer.uid}"><u>${balanceStatement.customer.uid}</u></a>
							</h3>
						</td>
					</tr>
					<tr>
						<td>
							<h3>Name: ${balanceStatement.customer.shortName} (${balanceStatement.customer.uid})</h3>
						</td>
					</tr>
					<tr>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.pastDue" />: <fmt:formatNumber type="currency" value="${balanceStatement.pastDue}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.current" />: <fmt:formatNumber type="currency" value="${balanceStatement.current}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
					</tr>
					<tr>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.balance" />: <fmt:formatNumber type="currency" value="${balanceStatement.balance}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
						<td>
							<h3><spring:theme code="text.account.balanceStatement.list.creditLimit" />: <fmt:formatNumber type="currency" value="${balanceStatement.creditLimit}" maxFractionDigits="2" currencySymbol="$" /></h3>
						</td>
					</tr>
				</tbody>
			</table>
		</div>	
	</c:forEach>		
		
	<div class="">
		<nav:paginationReportFooter top="true" supportShowPaged="${isShowPageAllowed}"
			supportShowAll="${isShowAllAllowed}"
			searchPageData="${searchPageData}"
			searchUrl="${balanceStatementListBaseUrl}?sort=${balanceStatementSearchForm.sort}&customer=${balanceStatementSearchForm.customer}"
			msgKey="text.account.balanceStatement.list.page.currentPage"
			numberPagesShown="${numberPagesShown}" includeSortDropdown="true" />		 			
	</div>

	<c:choose>
		<c:when test="${empty searchPageData.results}">
		</c:when>
		<c:otherwise>
			<a href="#top">Go to top</a>
		</c:otherwise>
	</c:choose>

</div>

<script type="text/javascript">
onDOMLoaded(initBalanceStatementList);

function initBalanceStatementList()
{	
	jQuery(".sortDropDownFieldClass").on('change', submitForm);
	jQuery("#balanceStatementSearchSubmit").click(submitForm);
}

function submitForm()
{
	var val = jQuery(this).val();
	jQuery("#sortField").val(val);
	jQuery("#searchForm").submit();
}
</script> --%>