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

<%@ taglib prefix="incident" tagdir="/WEB-INF/tags/desktop/incident" %>


	<div class="title_holder">
		<h2><spring:theme code="incident.list.title" text="Incidents"/></h2>
	
	</div>
	
	<br />
	<incident:incidentListSearchForm/>

<br/>

<div class="item_container">
	<table id="incidents" class="incidents-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="firstrow">
				<td class="">
					<h3><spring:theme code="incident.list.number" /></h3> 
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.status" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.customer" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.type" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.invoice" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.businessName" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.shipTo" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.reportedBy" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.phone" /></h3>
				</td>
				<td class="">
					<h3><spring:theme code="incident.list.email" /></h3>
				</td>
			</tr>
		</thead>

		<tbody class="">
			<c:forEach items="${searchPageData.results}" var="incident" varStatus="status">
<%-- 				<c:url value="/my-account/balance-statement-detail?customer=${balanceStatement.customer.uid}&row=${status.index}"  var="balanceStatementDetailUrl" /> --%>
				 	
				<tr class="">
					<td class="Text_Table_Align_Center">
							<c:url value="/incident/incidentDetail?incidentCode=${incident.code}" var="incidentDetailURL" />
							<a href="${incidentDetailURL}">${incident.code}</a>
					</td>
					
					<td class="Text_Table_Align_Center">
							${incident.state.name}
					</td>

					<td class="Text_Table_Center_Center">
							${incident.account.uid }
					</td>
					
					<td class="Text_Table_Center_Center">
							${incident.type.name }
					</td>
					
					<td class="Text_Table_Center_Center">
							${incident.invoiceNumber }
					</td>
					
					<td class="Text_Table_Center_Center">
							${incident.account.locName }
					</td>
					<td class="Text_Table_Center_Center">
							${incident.shipTo }
					</td>
					<td class="Text_Table_Center_Center">
							${incident.contactName }
					</td>
					<td class="Text_Table_Center_Center">
							${incident.phone }
					</td>
					<td class="Text_Table_Center_Center">
							${incident.email }
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>



   <div class="search-form-options-search-button">		    	

	
	  </div>


<!-- <iframe name="iframePost" style="visibility: hidden;"></iframe>-->

<script type="text/javascript">
onDOMLoaded(initIncidentList);

function initIncidentList()
{	
	$("#incidents tr:even").addClass("filaImpar");
    $("#incidents tr:odd").addClass("filaPar");
	
}
</script>

