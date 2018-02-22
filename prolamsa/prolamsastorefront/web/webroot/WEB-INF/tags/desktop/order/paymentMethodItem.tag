<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="order" required="true"
	type="de.hybris.platform.commercefacades.order.data.AbstractOrderData"%>
<%@ taglib prefix="ordertl" tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<div id="order_summary_purchase_div" class="order_summary_flow_b complete" style="display: block;">
	<div class="item_container_holder positive">
		
			<h2>
				<spring:theme code="text.paymentMethod" text="Payment Method" />
			</h2>
		
	
		<div class="item_container">
			<c:if test="${order.paymentType.code eq 'CARD'}">
				<ordertl:paymentMethodItemOnCreditCard order="${order}"/>
			</c:if>
			<c:if test="${order.paymentType.code eq 'ACCOUNT'}">
				<ordertl:paymentMethodItemOnAccount order="${order}"/>
			</c:if>

			<p class="positive small" style="color: #2A0A1B"><spring:theme code="checkout.summary.purchaseOrderAttach"/></p>
			<div id="attachmentsListContainer">
				<table style="table-layout: fixed;">
					<c:choose>
						<c:when test="${not empty order.attachmentsPO}">
							<c:forEach items="${order.attachmentsPO}" var="attachment">
								<tr>
									<td style="padding-left: 10px !important; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="javascript:void(0);" id="myLinkPODoc_${order.code}_${attachment.code}" class="myLinkPODoc" style="font-size: 12px;" title="Download">${attachment.name}</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td style="padding-left: 10px !important; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><spring:theme code="checkout.summary.purchaseOrderAttach.noFiles"/></td>
							</tr>
						</c:otherwise>
					</c:choose>	
				</table>
			</div>
			<form id="PODocumentDownloadForm" target="iframePost" action="<spring:url value="/my-account/document/order" />"> 
				<input type="hidden" id="orderDocumentField" name="orderCode"/>
				<input type="hidden" id="attachmentCodeField" name="attachmentCode" />
			</form>
			<iframe name="iframePost" style="display: none;"></iframe>
		</div>
	</div>
</div>