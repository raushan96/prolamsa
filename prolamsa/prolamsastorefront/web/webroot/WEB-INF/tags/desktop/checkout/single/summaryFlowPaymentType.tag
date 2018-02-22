<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:url value="/_ui/desktop/common/images/spinner.gif" var="spinnerUrl" />
<spring:url value="/checkout/single/summary/setPaymentType.json" var="setPaymentTypeURL" />
<spring:url value="/checkout/single/summary/getCheckoutCart.json" var="getCheckoutCartUrl" />
<spring:url value="/checkout/single/summary/setPurchaseOrderNumber.json" var="setPurchaseOrderNumberURL" />
<spring:url value="/checkout/single/summary/validatePO.json" var="validatePOURL" />

<script type="text/javascript"> // set vars
	var setPaymentTypeURL = '${setPaymentTypeURL}';
	var setPurchaseOrderNumberURL = '${setPurchaseOrderNumberURL}';	
	
	var validatePOURL = '${validatePOURL}';
	var POAlredyUsedModalTitle = "<spring:theme code="checkout.summary.purchaseOrderNumberValidation.modal.title"/>";
	var POAlredyUsedModalContent = "<spring:theme code="checkout.summary.purchaseOrderNumberValidation.modal.content"/>";
	var POAlredyUsedModalOK = "<spring:theme code="checkout.summary.purchaseOrderNumberValidation.modal.ok"/>";
	var POAlredyUsedModalCancel = "<spring:theme code="checkout.summary.purchaseOrderNumberValidation.modal.cancel"/>";
</script>

<div class="checkout_summary_flow_a" id="checkout_summary_paymentType_div" style="min-height: 350px !important; background-position: 18px 309px !important;">
	<div class="item_container_holder">
		<ycommerce:testId code="paymentType_text">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2><spring:theme code="checkout.summary.paymentType.header" htmlEscape="false"/><span></span></h2>
			</div>
			
			
			<div class="item_container">
				<!-- NEORIS_CHANGE #29 -->
					<!-- NEORIS_CHANGE #74 Hide Select Payment Type-->
					<div class="radiobuttons_paymentselection" style="display: none;">
	                    <c:forEach items="${paymentTypes}" var="paymentType">
	                        <form:radiobutton path="paymentTypes" id="PaymentTypeSelection_${paymentType.code}" name="PaymentType" value="${paymentType.code}" label="${paymentType.displayName}"/><br>
	                    </c:forEach>
	                </div>
			 
				<div class="pon">
					<label><spring:theme code="checkout.summary.purchaseOrderNumber"/></label>
					<br>
					<span style="color: black">
					<!-- NEORIS_CHANGE #Incidencia 82 JCVM 29/08/2014 Se cambia el color de la letra para el texto ingresado-->
					<%-- <input type="text" id="PurchaseOrderNumber" style="color: #2A0A1B" name="PurchaseOrderNumber" maxlength="255" value="${cartData.purchaseOrderNumber}" /> --%>
					<!-- <input   type="text" id="PurchaseOrderNumber" style="color: #2A0A1B" name="PurchaseOrderNumber" maxlength="255" value="" />
					 --></span>
				</div>
				<c:set value="display:none;" var="inputFileStyle" />
				<c:set value="jQuery('#file').click()" var="onClickButton"/>
				<c:set value="" var="buttonStyle"/>
				<c:if test="${fn:contains(header['User-Agent'],'MSIE') and (fn:contains(header['User-Agent'],'9') or fn:contains(header['User-Agent'],'8'))}">
					<c:set value="margin-left:-40px;opacity:0; position: relative; filter:alpha(opacity: 0);z-index:2;" var="inputFileStyle" />
					<c:set value="" var="onClickButton"/>
					<c:set value="margin-left:-102px;" var="buttonStyle"/>
				</c:if>	
				
				<!-- NEORIS_CHANGE #89 -->
				<form id="uploadDocumentForm" enctype="multipart/form-data" method="post" target="uploadDocumentIframe" action="<spring:url value="/checkout/single/upload-document" />">
					<input name="_callbackFunctionName" value="uploadDocumentCallback" type="hidden" />
					<div>
						<input id="PurchaseOrderNumber" name="PurchaseOrderNumber" class="positive" type="text" size="36" onkeyup="validateCalculateButton()" onchange="validateCalculateButton()" value=".">
						<label><spring:theme code="uploadExcel.selectLocalFile"/></label>
						<input id="PurchaseOrderAttach" name="PurchaseOrderAttach" class="positive" type="text" size="36" readonly="readonly">
						<input  style="${inputFileStyle}" size="1" type="file" id="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" onchange="checkfile(this);" />	
						<button id="buttonFile" style="${buttonStyle} margin-top: 5px;" class="positive small" type="button"  onclick="${onClickButton}"><spring:theme code="uploadExcel.browse"/></button>

						<button id="uploadDocument" class="positive small" type="button" style="margin-top: 5px;"><spring:theme code="uploadExcel.upload"/></button>			
					</div>			
				</form>                   				
										
				<iframe name="uploadDocumentIframe" style="display: none;">
				</iframe>
				
				<p class="positive small" style="color: #2A0A1B"><spring:theme code="checkout.summary.purchaseOrderAttach"/></p>
				
				<div id="attachmentsListContainer">
					<table style="table-layout: fixed;">
					<c:choose>
						<c:when test="${not empty cartData.attachmentsPO}">
							<c:forEach items="${cartData.attachmentsPO}" var="attachment">
								<tr>
									<td style="padding-left: 10px !important; width: 85%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
										<a href="javascript:void(0);" class="downloadAttachment" id="${attachment.code}" style="font-size: 12px;" title="${attachment.name}">${attachment.name}</a>
									</td>
									<td style="padding-left: 10px !important; width: 15%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
										<a href="javascript:void(0);" class="deleteAttachment" id="${attachment.code}">
											<img src="${themeResourcePath}/images/delete-attachment.png" alt="Delete">
										</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="2" style="padding-left: 10px !important; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><spring:theme code="checkout.summary.purchaseOrderAttach.noFiles"/></td>
							</tr>
						</c:otherwise>
					</c:choose>	
					</table>
				</div>
				<form id="downloadPOAttachmentForm" target="iframePost" action="<spring:url value="/checkout/single/summary/downloadAttachment" />"> 
					<input type="hidden" id="poAttachmentNumberField" name="poAttachmentCode" />
				</form>
				<iframe name="iframePost" style="display: none;"></iframe>
			</div>
			
		</ycommerce:testId>
	</div>
	
</div>

<c:if test="${fn:contains(header['User-Agent'],'MSIE') and (fn:contains(header['User-Agent'],'9') or fn:contains(header['User-Agent'],'8'))}">
	<script type="text/javascript">
		onDOMLoaded(setFileInputWidth);
		
		function setFileInputWidth()
		{
			jQuery("#buttonFile").css({width:  jQuery("#file").width()*.70+"px"});
		}
	</script>
</c:if>

<script type="text/javascript">

function checkfile(sender) {
    $('#PurchaseOrderAttach').val(sender.value);
    validateCalculateButton();
}
</script>
