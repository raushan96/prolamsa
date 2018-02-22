<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="orderData" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData"%>
<%@ attribute name="orderHistoryEntries" required="true" type="java.util.List"%>
<%@ attribute name="quoteOrderForm" required="true" type="mx.neoris.storefront.forms.QuoteOrderForm"%>
<%@ attribute name="isOrderDetailsPage" type="java.lang.Boolean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>
<%--
	~ /*
	~  * [y] hybris Platform
	~  *
	~  * Copyright (c) 2000-2011 hybris AG
	~  * All rights reserved.
	~  *
	~  * This software is the confidential and proprietary information of hybris
	~  * ("Confidential Information"). You shall not disclose such Confidential
	~  * Information and shall use it only in accordance with the terms of the
	~  * license agreement you entered into with hybris.
	~  *
	~  */
--%>

<spring:url value="/my-account/quote/quoteOrderDecision"
	var="quoteOrderDecisionURL" />

<script type="text/javascript"> // set vars
	var quoteActive=true;
	var negotiateQuote = ${quoteOrderForm.negotiateQuote};
	
	onDOMLoaded(bindElements);
	
	function bindElements()
	{
		jQuery("#acceptQuoteButton").click(function(e){
			e.preventDefault();
			confirmShippingInstructionsPlaceOrder();
		});
	}
	
	function confirmShippingInstructionsPlaceOrder()
	{					
		ACC.modals.confirmModal("<spring:theme code="shippingInstructions.enter.confirm.title"/>",
				"<spring:theme code="shippingInstructions.enter.confirm.description"/>",
				{
					"<spring:theme code="shippingInstructions.enter.confirm.button.yes"/>" : function(){
						introduceShippingInstructionsModal();
						},
					"<spring:theme code="shippingInstructions.enter.confirm.button.no"/>" : function(){
						ACC.quote.submitQuoteDecision('ACCEPTQUOTE');
						}
				},
				null);
	}
	
	function introduceShippingInstructionsModal()
	{
		ACC.modals.openOnModal("<spring:theme text="Shipping Instructions" code="text.order.shippingInstructions.popup.title"/>", 
				"<c:url value="/checkout/single/shipping-instructions-popup" />", 
				"#shippingInstructionsPopupContainer", 
				{
					popupCallback: function(instructions)
					{
						$("#shippingInstructions").val(instructions);
						ACC.quote.submitQuoteDecision('ACCEPTQUOTE');
					}
				}, 
				null);
	}
</script>

<div id="shippingInstructionsPopupContainer" style="display: none;"></div>

<form:form method="post" id="quoteOrderDecisionForm"
	commandName="quoteOrderDecisionForm" action="${quoteOrderDecisionURL}">
	<div class="item_container_holder positive">

		<div class="item_container">
			<div id="quote_detail_buttons">
				<form:input type="hidden" name="orderCode" path="orderCode"
					value="${orderData.code}" />
				<form:input type="hidden" name="selectedQuoteDecision"
					path="selectedQuoteDecision" id="selectedQuoteDecision" />
				
				<form:input type="hidden" name="shippingInstructions"
					path="shippingInstructions" id="shippingInstructions" />
									
					<c:set var="disabled" value=""/>
					<c:if test="${orderData.status ne 'APPROVED_QUOTE'}" > <!--el deber ser es que sea 'ne', pero se pone 'eq' para hacer la prueba -->
						<c:set var="disabled" value="disabled"/>	
					</c:if>
					
					<!-- NEORIS_CHANGE #  -->
					<c:if test="${orderData.status eq 'APPROVED_QUOTE'}" >
						<button class="button" name="ACCEPTQUOTE" id="acceptQuoteButton" ${disabled}>
							<spring:theme code="text.quotes.acceptQuoteButton.displayName"	 text="Accept Quote" /> 
						</button>
	
						<button class="button" name="CANCELQUOTE" ${disabled} id="cancelQuoteButton">
							<spring:theme code="text.quotes.cancelQuoteButton.displayName" 	text="Cancel Quote" />
						</button>
					</c:if>
					<br>			
			</div>
<%--
<div>
				<div class="span-10 right" id="negotiate-quote-div2" style="display:none">
					<div>
						<div class="item_container_holder">
							<div class="title_holder">
								<div class="title">
									<div class="title-top">
										<span></span>
									</div>
								</div>
								<h2>
									<div id="negotiate-quote-div-label-add-comment2" style="display:none">
										<spring:theme
											code="checkout.summary.negotiateQuote.quoteReason" />
									</div>
									<div id="negotiate-quote-div-label-cancel2" style="display:none">
										<spring:theme
											code="checkout.summary.negotiateQuote.quotecancelreason" />
									</div>
								</h2>
							</div>
							<div class="item_container">
								<ul>
									<div class="your_cart">
										<c:if test="${quoteOrderForm.negotiateQuote}" >
											<span class="form_field_error">
										</c:if>
										<form:input cssClass="text" id="comments" path="comments" />
									</div>
								</ul>
							</div>
						</div>
						<div class="item_container">
							<ul>
								<form:input type="hidden" name="negotiateQuote" class="negotiateQuoteClass" path="negotiateQuote"/>
								<button class="positive right pad_right negotiateQuote" id="proceedButton">
									<spring:theme code="checkout.summary.negotiateQuote.proceed" />
								</button>
								<button class="positive right pad_right cancel" id="cancelComment">
									<spring:theme code="checkout.summary.negotiateQuote.cancel" />
								</button>
							</ul>
						</div>
					</div>
				</div>
			</div>
 --%>
 			</div>
 			</div>
 			<div>
			
				<table>
					<tr class="firstrow">
							<td class="balanceStatement-customer noBorder" style="text-align: left; width: 60px;"><spring:theme
									code="text.quote.detail.title" text="Quote status" /></td>

							<td class="balanceStatement-customer noBorder" style="text-align: left; width: 270px;"></td>

							
						</tr>	
					<tr>
                    	<!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme                                
							code="text.quotes.details.quoteNumber"
							/>
						</td>
						<td>${orderData.code}</td>
					</tr>
					<tr></tr>
					<tr>
                        <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme
 								code="text.quotes.details.quotePlacedBy"
 								 /></td> 
 						<td>${orderData.b2bCustomerData.name}</td> 
					</tr>

<!-- 					<tr> -->
<%-- 						<td><spring:theme --%>
<%-- 								code="text.account.orderApprovalDetails.paidOntoAccount" --%>
<%-- 								text="Paid onto account" />: --%>
<!-- 						</td> -->
<%-- 						<c:if test="${orderData.paymentType.code eq 'CARD'}"> --%>
<%-- 							<td>${orderData.paymentInfo.cardNumber}</td> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${orderData.paymentType.code eq 'ACCOUNT'}"> --%>
<%-- 							<td>${orderData.costCenter.code}</td> --%>
<%-- 						</c:if> --%>
<!-- 					</tr> -->

					<tr>
                        <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme
								code="text.quotes.details.purchaseOrderNumber"
								/></td>
						<td>${orderData.purchaseOrderNumber}</td>
					</tr>
					<tr>
                        <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme code="text.quotes.details.quoteStatus"
							/></td>
						<td><spring:theme code="text.account.order.status.display.${orderData.statusDisplay}"/></td>
					</tr>
					<tr>
                        <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme
								code="text.quotes.details.quoteCreated"
								/></td>
						<td><formDate:formFormatDate pattern="MM/dd/yyyy hh:mm:ss a" value="${orderData.created}" /></td>
					</tr>
				</table>
			
		</div>
	
	
	<%--****************INICIO************************************************* --%>
	<div class="span-10 right" id="negotiate-quote-div" style="display:none">
		<div class="item_container_holder">
			<div id="negotiate-quote-div-label-cancel" style="display:none">
				<h2 style="color:#F1AD1D">
					<spring:theme code="checkout.summary.negotiateQuote.quotecancelreason" />
				</h2>
			</div>
			<div class="item_container">
				<ul>
					<div class="your_cart">
						<c:if test="${quoteOrderForm.negotiateQuote}" >
							<span class="form_field_error">
						</c:if>
						<form:input cssClass="text" id="comments" path="comments" />
					</div>
				</ul>
			</div>
			<div class="item_container">
				<ul>
					<form:input type="hidden" name="negotiateQuote" class="negotiateQuoteClass" path="negotiateQuote"/>
						<button class="positive right pad_right negotiateQuote" id="proceedButton">
							<spring:theme code="checkout.summary.negotiateQuote.proceed" />
						</button>
						<button class="positive right pad_right cancel" id="cancelComment">
							<spring:theme code="checkout.summary.negotiateQuote.cancel" />
						</button>
				</ul>
			</div>
		</div>
	</div>
	<%--*****************FIN*************************************************+ --%>
	<br>
	
					<div class="item_container_holder">
			<div class="title_holder">
				
				<h4>
					<spring:theme code="text.quotes.comments.label"
						text="Quotes Comments Details" />
				</h4>
			</div>
			
				<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">
					
					<thead class="">
							<tr class="firstrow">
							<td class="balanceStatement-customer noBorder" style="text-align: left; width: 270px;"><spring:theme
									code="text.quote.orderHistoryEntry.date" text="Date" /></td>

							<td class="balanceStatement-customer noBorder" style="text-align: left; width: 270px;"><spring:theme
									code="text.quote.orderHistoryEntry.user" text="User" /></td>

							<td class="balanceStatement-customer noBorder" style="text-align: left;"><spring:theme
									code="text.quote.orderHistoryEntry.comment" text="Comment" /></td>
						</tr>	
				
						<c:forEach items="${orderData.b2bCommentData}"
							var="b2bComments">
							<tr class="">
								<td class="balanceStatement-customer dueDateColor noBorder"  style="text-align: left;"><formDate:formFormatDate pattern="MM/dd/yyyy hh:mm:ss a" value="${b2bComments.timeStamp}" /></td>
								<td class="balanceStatement-customer dueDateColor noBorder"  style="text-align: left">${b2bComments.ownerData.name}</td>
								<td class="balanceStatement-customer dueDateColor noBorder"  style="text-align: left">${b2bComments.comment}</td>
							</tr>
						</c:forEach>
					</thead>
				</table>
			
		</div>
		

</form:form>