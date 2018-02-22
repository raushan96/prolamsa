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
		<h2><spring:theme code="incident.add.title" text="Incident Detail"/></h2>
	
	</div>
	
	<br />
	
	<incident:addIncidentForm/>
	
	<br/>
	<div class="item_container">
		<br/>
		<table id="incidents" class="incidents-list" data-zebra="tbody tr">
			<thead class="">
				<tr class="firstrow">
					<td class="">
						<h3><spring:theme code="incident.add.order" /></h3> 
					</td>
					<td class="">
						<h3><spring:theme code="incident.add.line" /></h3> 
					</td>
					<td class="">
						<h3><spring:theme code="incident.detail.batch" /></h3>
					</td>
					<td class="">
						<h3><spring:theme code="incident.detail.partNumber" /></h3> 
					</td>
					<td class="">
						<h3><spring:theme code="incident.detail.productDescription" /></h3>
					</td>
					<td class="">
						<h3><spring:theme code="incident.add.quantity" /></h3> 
					</td>
					<td class="">
						<h3><spring:theme code="incident.detail.weight" /></h3>
					</td>
					<td class="">
						<h3><spring:theme code="incident.add.quantityToClaim" /></h3>
					</td>
					
					<td class="">
						<h3><spring:theme code="incident.add.weightToClaim" /></h3>
					</td>
				</tr>
			</thead>
	
			<tbody id="incidentLinesBodyTable">
				
			</tbody>
		</table>
	</div>



   <div class="search-form-options-search-button">		    	

	
  </div>



<!-- <iframe name="iframePost" style="visibility: hidden;"></iframe>-->

<script type="text/javascript">
onDOMLoaded(initBalanceStatementList);

function initBalanceStatementList()
{	
	
	
}


</script>

