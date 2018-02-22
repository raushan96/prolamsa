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
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="en_US" scope="session"/>
	
	<spring:url value="/incident/addIncident" var="addIncidentURL" />
	<spring:url value="/incident/incidentList" var="searchIncidentURL" />
	<spring:url value="/incident/resendSharepoint" var="resendSharepointURL" />

	<div class="title_holder">
		<h2><spring:theme code="incident.detail.title" text="Incident Detail"/></h2>
	
	</div>
	<br>
	<a href="${addIncidentURL }" class="button yellow positive button-float-right" id="addNewIncidentLink"><spring:theme code="incident.add.title" /></a>
	<a href="${ searchIncidentURL}" class="button yellow positive button-float-right" id="searchIncidentList"><spring:theme code="incident.list.title"/></a>
	<table style="table-layout: fixed;">
		<tr>
			<td width="25%">
				<spring:theme code="incident.detail.number" />:
			</td>
			<td class="Text_Table_Center_Left">
				${incident.code}
			</td>
		</tr>
		<c:if test="${not empty incident.sharePointId}">
			<tr>
				<td>
					<spring:theme code="incident.detail.sharePointNumber" />:
				</td>
				<td class="Text_Table_Center_Left">
					<spring:theme code="incident.detail.sharePointNumber.label" arguments="${incident.sharePointId}"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<td>
				<spring:theme code="incident.detail.date" />:
			</td>
			<td>
				 <formDate:formFormatDate value="${incident.date}" pattern="MM/dd/yyyy" />
			</td>
		</tr> 
		<tr>
			<td width="25%">
				<spring:theme code="incident.detail.status" />
			</td>
			<td class="Text_Table_Center_Left">
				${incident.state.name}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.customer" />:
			</td>
			<td>
				${incident.account.locName}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.type" />:
			</td>
			<td>
				${incident.type.name}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.country" />:
			</td>
			<td>
				${incident.country}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.location" />:
			</td>
			<td>
				<spring:theme code="${incident.location}" />
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.documentNumber" />:
			</td>
			<td>
				${incident.invoiceNumber}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.shipTo" />:
			</td>
			<td>
				${incident.shipTo}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.incoterm" />:
			</td>
			<td>
				${incident.incoterm}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.customerNumber" />:
			</td>
			<td>
				${incident.customerCode}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.reportedBy" />:
			</td>
			<td>
				${incident.contactName}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.alternateReportedBy" />:
			</td>
			<td>
				${incident.alternateContactName}	
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.phone" />:
			</td>
			<td>
				${incident.phone}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.email" />:
			</td>
			<td>
				${incident.email}
			</td>
		</tr>
		<tr>
			<td>
				<spring:theme code="incident.detail.description" />:
			</td>
			<c:choose>
				<c:when test="${not empty incident.descriptionMax }">
					<td>
						${fn:escapeXml(incident.descriptionMax)}
					</td>
				</c:when>
				<c:otherwise>
					<td>
						${fn:escapeXml(incident.description)}
					</td>
				</c:otherwise>
			</c:choose>
		</tr>
		<sec:authorize ifAnyGranted="ROLE_ALLOWRESENDINCIDENT">
		<tr>
			<td>
				En sharepoint
				<br>
				${incident.sharePointErrorTrace}
			</td>
			<td>
				<c:choose>
					<c:when test="${empty incident.sharePointErrorTrace}">Si</c:when>
					<c:otherwise>No
						<br>
					<%-- 	<a href="${resendSharepointURL}" class="button yellow positive button-float-right" id="resendSharepoint"><spring:theme code="incident.resend.sharepoint"/></a>
					 --%>
					 <a href="#" onClick="resendSharepoint(${incident.pk})" class="button yellow positive button-float-right"><spring:theme code="incident.resend.sharepoint" /></a>
					 </c:otherwise>
				</c:choose>
			</td>
		</tr>
		</sec:authorize>
	</table>

<br/>

<div class="item_container">
	<c:if test="${not empty incident.attachement1 or not empty incident.attachement2 or not empty incident.attachement3 or not empty incident.attachement4 or not empty incident.attachement5  }">
		<p><spring:theme code="incident.detail.attachment"></spring:theme></p>
		<table id="incidents" class="incidents-list" data-zebra="tbody tr">
			<thead class="">
				<tr class="firstrow">
					<td><spring:theme code="incident.detail.fileName" /></td>
					<td><spring:theme code="incident.detail.fileDescription"/></td>
					<td width="10%">&nbsp;</td>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty incident.attachement1  }">
					<tr>
						<td class="Text_Table_Align_Center">
							${ incident.attachement1.realFileName}
						</td>
						<td class="Text_Table_Align_Center">
							${ incident.attachement1.description}
						</td>
						<td class="Text_Table_Align_Center">
							<a href="<spring:url value='/incident/incidentAttach?incidentCode=${incident.code}&attachIndex=1' />" ><spring:theme code="incident.detail.attachment.download" /></a>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty incident.attachement2  }">
					<tr>
						<td class="Text_Table_Align_Center">
							${ incident.attachement2.realFileName}
						</td>
						<td class="Text_Table_Align_Center">
							${ incident.attachement2.description}
						</td>
						<td class="Text_Table_Align_Center">
							<a href="<spring:url value='/incident/incidentAttach?incidentCode=${incident.code}&attachIndex=2' />" ><spring:theme code="incident.detail.attachment.download" /></a>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty incident.attachement3  }">
					<tr>
						<td class="Text_Table_Align_Center">
							${ incident.attachement3.realFileName}
						</td>
						<td class="Text_Table_Align_Center">
							${ incident.attachement3.description}
						</td>
						<td class="Text_Table_Align_Center">
							<a href="<spring:url value='/incident/incidentAttach?incidentCode=${incident.code}&attachIndex=3' />" ><spring:theme code="incident.detail.attachment.download" /></a>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty incident.attachement4  }">
					<tr>
						<td class="Text_Table_Align_Center">
							${ incident.attachement4.realFileName}
						</td>
						<td class="Text_Table_Align_Center">
							${ incident.attachement4.description}
						</td>
						<td class="Text_Table_Align_Center">
							<a href="<spring:url value='/incident/incidentAttach?incidentCode=${incident.code}&attachIndex=4' />" ><spring:theme code="incident.detail.attachment.download" /></a>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty incident.attachement5  }">
					<tr>
						<td class="Text_Table_Align_Center">
							${ incident.attachement5.realFileName}
						</td>
						<td class="Text_Table_Align_Center">
							${ incident.attachement5.description}
						</td>
						<td class="Text_Table_Align_Center">
							<a href="<spring:url value='/incident/incidentAttach?incidentCode=${incident.code}&attachIndex=5' />" ><spring:theme code="incident.detail.attachment.download" /></a>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>	
	</c:if>
	<br/>
	
	<table id="incidents2" class="incidents-list" data-zebra="tbody tr">
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

		<tbody class="">
			<c:forEach items="${incident.incidentLines}" var="eachLine" varStatus="status">
				 	
				<tr class="">
					<td class="Text_Table_Align_Center">
						${eachLine.sorder }
					</td>
					<td class=" Text_Table_Align_Left">
						${eachLine.sorder_p }
					</td>
					<td class="Text_Table_Align_Right">
						${eachLine.batch}
					</td>
					<td class="Text_Table_Align_Right">
					<c:choose>
						<c:when test="${not empty eachLine.product}">
							${eachLine.product.baseCode}
						</c:when>
						<c:otherwise>
							${eachLine.productBaseCode}
						</c:otherwise>
					</c:choose>
						
					</td>
					<td class="Text_Table_Align_Right">
						${eachLine.productDescription}
					</td>
					<td class="Text_Table_Align_Right">
						${eachLine.quantity}&nbsp;${eachLine.salesUnit}
					</td>
					<td class="Text_Table_Align_Right">
						<fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${eachLine.netweight}"/>&nbsp;${eachLine.weightUnit}
					</td>
					<td class="Text_Table_Align_Right">
						${eachLine.quantityToClaim}&nbsp;${eachLine.salesUnit}
					</td>
					<td class="Text_Table_Align_Right">
						<fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${ eachLine.netweight/eachLine.quantity*eachLine.quantityToClaim}"/>&nbsp;${eachLine.weightUnit}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>



   <div class="search-form-options-search-button">		    	

	
	  </div>

</div>

<!-- <iframe name="iframePost" style="visibility: hidden;"></iframe>-->

<script type="text/javascript">
onDOMLoaded(initIncidentdetail);

function initIncidentdetail()
{	
	$("#incidents tr:even").addClass("filaImpar");
    $("#incidents tr:odd").addClass("filaPar");
    
    $("#incidents2 tr:even").addClass("filaImpar");
    $("#incidents2 tr:odd").addClass("filaPar");
    
	//jQuery("#resendSharepoint").click(submitForm);
}

function resendSharepoint(incidentPK)
{
	//alert(incidentPK);
	
	jQuery.ajax
		({
			url:         "<c:url value="/incident/resendSharepoint.json" />",
			type:        'POST',
			dataType:    'json',
			data: {incidentPK: incidentPK},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
      	},
      	beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
      	{
      		if (data.status == 0)
          	{
          		ACC.modals.messageModal(title, msg);
              	location.reload();
          	}
      		else
      			ACC.modals.messageModal("Error:" + data.message);
      	},
      	complete: function ()
		{
			//location.reload();
			unblockUI();
		}
		});
}


</script>

