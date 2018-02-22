<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>
		
	<div class="grid-100 grid-parent">
	  <div class="feautered">
	     <div class="feautered-item">
	         <img src="${themeResourcePath}/images/example-img.jpg" alt="">
	         <div class="feautered-text">  
	         <c:choose>
			  	<c:when test="${baseStore.uid eq '1000'}">
			  		<h2><spring:theme code="dashboard.content.level1Mx" /></h2>
			  	</c:when>
			  	<c:when test="${baseStore.uid eq '2000'}">
			  		<h2><spring:theme code="dashboard.content.level1" /></h2>
	            	<h3><spring:theme code="dashboard.content.level2" /></h3>
	            	<p><spring:theme code="dashboard.content.level3" /></p>
			  	</c:when>
			  	<c:when test="${baseStore.uid eq '5000'}">
			  		<h2><spring:theme code="dashboard.content.level1A4C" /></h2>
			  	</c:when>
			  	<c:when test="${baseStore.uid eq '6000'}">
			  		<h2 style="text-transform: none;"><spring:theme code="dashboard.content.level3Axis"/></h2>
	            	<h3 style="text-transform: none;"><spring:theme code="dashboard.content.level4Axis" /></h3>
			  	</c:when>
			  </c:choose>
	         </div><!-- feautered-text -->
	     </div><!-- #Feautered Item -->
                       
                       
                    </div><!-- #Feautered -->
	    <sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER" >             
		<div class="dashboard-panel-container-holder">
			<div class="title">
				<p><span class="icon-clock"></span><spring:theme code="dashboard.recentOrders.title" /></p>		
			</div>
		
			<c:url value="/my-account/orders" var="orderDocumentUrl" />
			<div class="dashboard-panel-container">
		
				<table>
					<tr class="firstRow">
						<c:choose>
						  	<c:when test="${baseStore.uid eq '1000'}">
						  		<th><spring:theme code="dashboard.recentOrders.order.mx" /></th>
						  	</c:when>
						  	<c:when test="${baseStore.uid eq '2000'}">
						  		<th><spring:theme code="dashboard.recentOrders.order.usa" /></th>
						  	</c:when>
						  	<c:when test="${baseStore.uid eq '5000'}">
						  		<th><spring:theme code="dashboard.recentOrders.order.a4c" /></th>
						  	</c:when>
						  	<c:when test="${baseStore.uid eq '6000'}">
						  		<th><spring:theme code="dashboard.recentOrders.order.axis" /></th>
						  	</c:when>
						  </c:choose>
					
						<th><spring:theme code="dashboard.recentOrders.customer" /></th>
						<th><spring:theme code="dashboard.recentOrders.status" /></th>
					</tr>
				
					<c:set var="cont" value="0" />
					<c:forEach items="${recentOrders}" var="order" varStatus="varStatus" end="10">
					<c:url value="/my-account/order/${order.orderNumber}" var="orderDetailUrl" />
					
					    <tr>
					        <c:set var="cont" value="${cont + 1}" />
					        <spring:theme code="text.account.order.status.display.${order.statusDisplay}" var="status" />
					        
					        <c:choose>
					           <c:when test="${not empty order.orderNumber}">
					           
					               <td><a href="${orderDetailUrl}"><fmt:formatNumber pattern="#" value="${order.orderNumber}"/></a></td>
								   <td>${fn:substring(order.shortB2BUnitName,0,14)} ${fn:length(order.shortB2BUnitName)>14 ? "...":"" }</td>						   
								   <td>
								        ${fn:substring(status,0,16)} ${fn:length(status)>16 ? "...":""}	
								   </td>	
					           </c:when>			               
					           <c:otherwise>
					               <td>...</td>
							  	   <td>...</td>
								   <td>...</td>
					           </c:otherwise>
					        </c:choose>						        			        			        
					   </tr>
					   
					  </c:forEach> 
					   
				      <c:if test="${cont < 10}" >
			            <c:forEach begin="${cont + 1}" end="10">
			              <tr>
			               <td>...</td>
					  	   <td>...</td>
						   <td>...</td>
						  </tr>
			            </c:forEach>
				      </c:if>			  									
		
				</table>
			
				<div class="footerContent">
				     <a href="${orderDocumentUrl}">
				     <spring:theme code="dashboard.click_for_more_details" />
				     </a>
				</div>		
		
			</div>
		</div>
		</sec:authorize>
	</div>
	<sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER" >    
	<div class="">
		<div class="dashboard-panel-container-holder2">
			<div class="title">
				<p><span class="icon-quote"></span><spring:theme code="dashboard.recentQuotes.title" /></p>		
			</div>
			
			<c:url value="/my-account/my-quotes" var="quoteDocumentUrl" />
			<div class="dashboard-panel-container">
				<table>
					<tr class="firstRow">
						<th><spring:theme code="dashboard.recentQuotes.quotes" /></th>
						<th><spring:theme code="dashboard.recentQuotes.customer" /></th>
						<th><spring:theme code="dashboard.recentQuotes.status" /></th>
					</tr>
					
					<c:set var="cont" value="0" />
					
					<c:forEach items="${recentQuotes}" var="quote" varStatus="varStatus" end="10">
					<c:url value="/my-account/my-quote/${quote.orderNumber}" var="quoteDetailUrl" />
					
					 <spring:theme code="text.account.order.status.display.${quote.statusDisplay}" var="status" />
					
					    <tr>
					        <c:set var="cont" value="${cont + 1}" />
					        <c:choose>
					           <c:when test="${not empty quote.orderNumber}">
					               <td><a href="${quoteDetailUrl}"><fmt:formatNumber pattern="#" value="${quote.orderNumber}"/></a></td>
								   <td>${fn:substring( (quote.shortB2BUnitName=="") ? quote.unitName : quote.shortB2BUnitName,0,13)} ${fn:length((quote.shortB2BUnitName=="") ? quote.unitName : quote.shortB2BUnitName)>13 ? "...":"" }</td>
								   <td>
								        ${fn:substring(status,0,16)} ${fn:length(status)>16 ? "...":""}	
								   </td>					   
					           </c:when>			               
					           <c:otherwise>
					               <td>...</td>
							  	   <td>...</td>
								   <td>...</td>
					           </c:otherwise>
					        </c:choose>						        			        			        
					   </tr>
					  
					  </c:forEach>
					   
				      <c:if test="${cont < 10}" >
			            <c:forEach begin="${cont + 1}" end="10">
			              <tr>
			               <td>...</td>
					  	   <td>...</td>
						   <td>...</td>
						  </tr>
			            </c:forEach>
				      </c:if>
				  									
				</table>
				
				<div class="footerContent">
				     <a href="${quoteDocumentUrl}">
				     <spring:theme code="dashboard.click_for_more_details" />
				     </a>
				</div>
			</div>
		</div>
	</div>
	</sec:authorize>
<sec:authorize ifAnyGranted="ROLE_ACCOUNTER">

	<div class="dashboard-panel-container-holder3">
		<div class="title">
			<p><span class="icon-money"></span><spring:theme code="dashboard.recentInvoices.title" /></p>		
		</div>
		
		<div class="dashboard-panel-container">
			<table>
				<tr class="firstRow">
					<th><spring:theme code="dashboard.recentInvoices.invoice" /></th>
					<th><spring:theme code="dashboard.recentInvoices.customer" /></th>
					<th><spring:theme code="dashboard.recentInvoices.date" /></th>
				</tr>
				
				<c:set var="cont" value="0" />
				
				<c:forEach items="${recentInvoices}" var="invoice" varStatus="varStatus" end="10">					
					<c:url value="/invoice/by-SAP-invoice?invoice=${invoice.number}&customer=${invoice.customer}" var="invoiceDetailUrl" />		
					<c:url value="/my-account/document/list" var="invoiceDocumentUrl" />
				
				    <tr>
				        <c:set var="cont" value="${cont + 1}" />
				        <c:choose>
				           <c:when test="${not empty invoice.number}">
				               <td><a href="javascript:void(0)" id="myLinkInvoice_${invoice.number}_${invoice.customer.uid}" class="myLinkInvoice"><fmt:formatNumber pattern="#" value="${invoice.number}"/></a></td>
							   <td>${fn:substring(invoice.customer.shortName,0,14)} ${fn:length(invoice.customer.shortName)>14 ? "...":"" }</td>
							   <%--<td><fmt:formatDate pattern="MM/dd/yyyy" value="${invoice.invoiceDate}" /></td>--%>					   
							   <td><formDate:formFormatDate pattern="MM/dd/yyyy" value="${invoice.invoiceDate}" /></td> 					   
				           </c:when>			               
				           <c:otherwise>
				               <td>...</td>
						  	   <td>...</td>
							   <td>...</td>
				           </c:otherwise>
				        </c:choose>						        			        			        
				   </tr>
				  
				  </c:forEach>
				   
			      <c:if test="${cont < 10}" >
		            <c:forEach begin="${cont + 1}" end="10">
		              <tr>
		               <td>...</td>
				  	   <td>...</td>
					   <td>...</td>
					  </tr>
		            </c:forEach>
			      </c:if>
			  									
			</table>
			
			<div class="footerContent">
				<c:set var="style" value=""/>
				<c:if test="${baseStore.showDocumentSearch eq false}">
					<c:set var="style" value="visibility:hidden;"/>
				</c:if>
			     <a style ="${style}" href="${invoiceDocumentUrl}">
			     <spring:theme code="dashboard.click_for_more_details" />
			     </a>
				
				
			</div>
		</div>
	</div>

</sec:authorize>



<sec:authorize ifAnyGranted="ROLE_PURCHASER">
<div class="dashboard-panel-container-holder4">
	<div class="title">
		<p><span class="icon-box"></span><spring:theme code="dashboard.recentTemplates.title" /></p>		
	</div>
	
	<c:url value="/templates/list" var="templateDocumentUrl" />
	<div class="dashboard-panel-container">
		<table>
			<tr class="firstRow">
				<th><spring:theme code="dashboard.recentTemplates.name" /></th>
				<th><spring:theme code="dashboard.recentTemplates.customer" /></th>
				<th><spring:theme code="dashboard.recentTemplates.type" /></th>
			</tr>
			
			<c:set var="cont" value="0" />
			
			<c:forEach items="${recentTemplates}" var="template" varStatus="varStatus" end="10">
			<c:url value="/templates/detail?wishlist=${template.pk}" var="templateDetailUrl" />
			
						
			    <tr>
			        <c:set var="cont" value="${cont + 1}" />
			        <c:choose>
			           <c:when test="${not empty template.name}">
			               <td><a href="${templateDetailUrl}">${fn:substring(template.name,0,10)} ${fn:length(template.name)>10 ? "...":"" }</a></td>
						   <td>${fn:substring(template.b2bUnit.shortName,0,13)} ${fn:length(template.b2bUnit.shortName)>13 ? "...":"" }</td>
						   <td>
						   		<spring:theme code="dashboard.recentTemplates.type.${template.type.code}" />
<%-- 						        ${fn:substring(template.type.code,0,16)} ${fn:length(template.type.code)>16 ? "...":""}	 --%>
						   </td>					   
			           </c:when>			               
			           <c:otherwise>
			               <td>...</td>
					  	   <td>...</td>
						   <td>...</td>
			           </c:otherwise>
			        </c:choose>						        			        			        
			   </tr>
			  
			  </c:forEach>
			   
		      <c:if test="${cont < 10}" >
	            <c:forEach begin="${cont + 1}" end="10">
	              <tr>
	               <td>...</td>
			  	   <td>...</td>
				   <td>...</td>
				  </tr>
	            </c:forEach>
		      </c:if>
		  									
		</table>
		
		<div class="footerContent">
		     <a href="${templateDocumentUrl}">
		     <spring:theme code="dashboard.click_for_more_details" />
		     </a>
		</div>
	</div>
</div>
</sec:authorize>

<form id="transferDocumentForm"  target="iframePost" action="<spring:url value="/my-account/document/transferDocument" />" >
	<input type="hidden" id="documentNumberField" name="documentNumber" />
	<input type="hidden" id="documentOwnerField" name="documentOwner" />
	<input type="hidden" id="transferTypeField" name="transferType" />
</form>

<iframe name="iframePost" style="visibility: hidden;"></iframe>

<script type="text/javascript">
onDOMLoaded(initDashboard);

function initDashboard()
{
	ACC.neorisDownloadDocuments.initialize({
		transferModalMessages: {
			title: "<spring:theme code="invoices.modal.download.title"/>",
			content: "<spring:theme code="invoices.modal.download.content"/>",
			button1: "<spring:theme code="invoices.modal.download.button.download"/>",
			button2: "<spring:theme code="invoices.modal.download.button.email"/>"
		}
	});
}
</script>