<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout" %>
<%@ taglib prefix="single-checkout" tagdir="/WEB-INF/tags/desktop/checkout/single" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>

<spring:url value="/checkout/single/placeOrder" var="placeOrderUrl" />
<spring:url value="/checkout/single/termsAndConditions" var="getTermsAndConditionsUrl" />
<spring:url value="/checkout/single/orderConditions" var="getOrderConditionsUrl" />

<template:page pageTitle="${pageTitle}">
	<script type="text/javascript">
		
		var isNegotiablePricesFuncEnabled = ${baseStore.priceNegotiationEnabled};
		
		var axisNegotiation = false;
		
		<c:if test="${cartData.isInternalCartOrder and baseStore.uid eq '6000'}">
			var axisNegotiation = true;
		</c:if>
	
		var getTermsAndConditionsUrl = "${getTermsAndConditionsUrl}";
		
		var getOrderConditionsUrl = "${getOrderConditionsUrl}";
		
		var pricesUpdated = false;
								
		<c:choose>
			<c:when test="${not empty cartData.transportationMode }">
				var isTransportationModeSelected = true;
			</c:when>
			<c:otherwise>
				var isTransportationModeSelected = false;
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty cartData.deliveryAddress }">
				var isDeliveryAddressSelected = true;
			</c:when>
			<c:otherwise>
				var isDeliveryAddressSelected = false;
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty cartData.purchaseOrderNumber }">
				var isPurchaseNumberEntered = true;
			</c:when>
			<c:otherwise>
				var isPurchaseNumberEntered = false;
			</c:otherwise>
		</c:choose>
		
		
		 <c:choose>
			<c:when test="{termsCondition}">
				var isTermsConditionsEntered = true;
			</c:when>
			<c:otherwise>
				var isTermsConditionsEntered = false;
			</c:otherwise>			
		</c:choose>
		
		
		<c:choose>
			<c:when test="${orderCondition}">
				var isOrderConditionEntered = true;
			</c:when>
			<c:otherwise>
				var isOrderConditionEntered = false;
			</c:otherwise>			
		</c:choose> 
	

		
		var calculatePriceURL = "<c:url value="/checkout/single/summary/calculatePrices.json" />";
		
		var pricesNeedUpdateTitle ="<spring:theme code='checkout.summary.prices_recalculate.title' />";
		var pricesNeedUpdateMessage ="<spring:theme code='checkout.summary.prices_recalculate.message' />";
		
		var advanceAddressSearchLink = {};
		advanceAddressSearchLink.url = "<c:url value="/client/searchAddress" />";
		advanceAddressSearchLink.linkId = "advancedAddressSearchLink";
			
		onDOMLoaded(bindElements);

		function bindDeleteButtons()
		{
			// NEORIS_CHANGE #74 Bind delete buttons 
			jQuery("#cartEntriesTable .deleteRowButton").on("click", deleteRowButtonClicked);
		}

		function bindElements()
		{						
			
			var oderCondition = jQuery("#OrderCondition").attr('checked');
								
			var termsCondition = jQuery("#Terms1").attr('checked');
			
			// Bind delete buttons 
			bindDeleteButtons();

			// bind prices button
			jQuery(".update-prices").on("click", calculatePriceButtonClicked);
					
			// Update Buttons according to current state
			updateButtons();
			
			jQuery("#uploadDocument").click(uploadDocumentAction);
	
			jQuery('#file').on("change",updateFile);
			
			var elements = jQuery("button.place-order[type='submit']");
			elements.click(function(e){
				e.preventDefault();
				var secCode = jQuery(this).attr("data-sec-code");
				confirmShippingInstructionsPlaceOrder(secCode);
			});
			
			bindPOAttachmentsElements();
		}
		
		function bindPOAttachmentsElements()
		{
			jQuery(".deleteAttachment").click(function()
			{
				var mediaCode = jQuery(this).attr("id");
				
				jQuery.ajax
				({
					url: "<c:url value="/checkout/single/summary/deleteAttachment.json" />",
					type: 'POST',
					dataType: 'json',
					data: {"code" : mediaCode},
					beforeSend: function ()
					{
						blockUI();
					},
					success: function (data)
					{
						updatePOAttachments(data.attachmentsPO);
					},
					error: function (xht, textStatus, ex)
					{
						ACC.modals.messageModal("<spring:theme code='checkout.summary.prices_updated.error.title'/>", "<spring:theme code='checkout.summary.prices_updated.error.message'/>");					
					},
					complete: function ()
					{
						unblockUI();		
					}
				});
			});
			
			jQuery(".downloadAttachment").click(function()
			{
				var mediaCode = jQuery(this).attr("id");
				jQuery("#poAttachmentNumberField").val(mediaCode);
				jQuery("#downloadPOAttachmentForm").submit();
			});
		}
		
		function updateFile()
		{			
			jQuery('#textFile').val($(this).val());
		}
					
		
		function uploadDocumentAction()
		{
			blockUI();
			jQuery("#uploadDocumentForm").submit();
		}
		
		function uploadDocumentCallback(data)
		{
			var msg = "";

			if (data.status == 1)
			{
				// error
				msg = data.message;
			}
			else
			{
				// success
				msg = data.message;
				updatePOAttachments(data.attachmentsPO);
			}
			
			jQuery("#PurchaseOrderAttach").val("");
			jQuery("#file").val("");
			
			unblockUI();
           	ACC.modals.messageModal("<spring:theme code="checkout.documentload.title"/>", msg);
		}
		
		function updatePOAttachments(attachments)
		{
			//update ui
			if (attachments != null)
			{
				jQuery("#attachmentsListContainer").empty();
				var table = $("<table style='table-layout: fixed;'>", {});
				
				if(attachments.length > 0)
				{
					$.each(attachments, function(index)
					{			
						table.append("<tr>" +
									"<td style='padding-left: 10px !important; width: 85%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;'><a href='javascript:void(0);' class='downloadAttachment' id='" + attachments[index].code + "' style='font-size: 12px;' title='Download'>" + attachments[index].name + "</a></td>" +
									"<td style='padding-left: 10px !important; width: 15%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;'><a href='javascript:void(0);' class='deleteAttachment' id='" + attachments[index].code + "' title='Delete'><img src='${themeResourcePath}/images/delete-attachment.png' alt='Delete'></a></td>" +
									"</tr>");		                   			   					   
					});
				}
				else
				{
					table.append("<tr><td colspan='2' style='padding-left: 10px !important; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;'><spring:theme code='checkout.summary.purchaseOrderAttach.noFiles'/></td></tr>");
				}
				
				$('#attachmentsListContainer').append(table);
			}
			
			bindPOAttachmentsElements();
		}

		function updateButtons()
		{
						
			if($("#transportationModeSelect").val() != "none" && $("#incotermsSelect").val() != "none" && $("#incotermsSelect").val() != "whatever")
				isTransportationModeSelected = true;
			else
				isTransportationModeSelected = false;
			
			
			if($("#deliveryAddressSelect").val() != "none")
				isDeliveryAddressSelected = true;
			else
				isDeliveryAddressSelected = false;
			
			if($("#PurchaseOrderNumber").val().length > 2 && $("#PurchaseOrderNumber").val().trim().length > 2)
				isPurchaseNumberEntered = true;
			else
				isPurchaseNumberEntered = false;
			
			
			if($("#OrderCondition").attr('checked') )
				isOrderConditionEntered = true;
			else
				isOrderConditionEntered = false;
			
			
			if($("#Terms1").attr('checked') )
				isTermsConditionsEntered = true;
			else
				isTermsConditionsEntered = false;
			
			if (pricesUpdated && isPurchaseNumberEntered && isTransportationModeSelected && isOrderConditionEntered && isTermsConditionsEntered)
			{
				$(".place-order").removeAttr('disabled');
				$(".request-quote").removeAttr('disabled');
			}
			else
			{
				$(".place-order").attr('disabled', true);
				$(".request-quote").attr('disabled', true);
				ACC.negotiatequote.cancelQuoteNegotiationEvent();
			}
			
			if (isPurchaseNumberEntered && isDeliveryAddressSelected && isTransportationModeSelected)
				$(".update-prices").removeAttr('disabled');
			else
			{
				ACC.negotiatequote.cancelQuoteNegotiationEvent();
				$(".update-prices").attr('disabled', true);
			}
		}
		
		function calculatePriceButtonClicked(event)
		{
			event.preventDefault();
			pricesUpdated= true;			
			
			jQuery.ajax
			({
				url: calculatePriceURL,
				type: 'GET',
				dataType: 'json',
				//contentType: 'application/json; charset=UTF-8',
				beforeSend: function ()
				{
					blockUI();
				},
				success: function (data)
				{
					/* //alert("data.sapTotalPrice " + data.result.sapTotalPrice);
							$('#summary_order_dl').replaceWith($('#summaryOrderTemplate').tmpl(data));
							 $('#totals_order_dl').replaceWith($('#totalsOrderTemplate').tmpl(data));
							$('#cartEntryTBody').replaceWith($('#entriesTemplate').tmpl(data));																	
							$('#sapUnit').replaceWith( (data.sapLabelWeightUnit !='') ?   " <spring:theme code='summary.cartitems.per' /> " + data.sapLabelWeightUnit : "");																				
							$('#unitTemplateWeight').replaceWith($("#total").val());
							<c:if test="${cartData.isInternalCartOrder and baseStore.uid eq '6000' }">
								bindInputPrice();
							</c:if>
							pricesUpdated = true;
							
							if(data.sapTotalPrice == null)
							{
								pricesUpdated = false;
							}
							
							updateButtons();
							bindDeleteButtons(); */	 			
							
						
							
					//	}
					/* 	else
							ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
						pricesUpdated= false;						
						updateButtons();
					}
					else
					{
						ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed.");
						pricesUpdated= false;						
						updateButtons();
					} */
					
					  if (data == null)
					{//alert("1");
						ACC.modals.messageModal("<spring:theme code='checkout.summary.prices_updated.error.title'/>", "<spring:theme code='checkout.summary.prices_updated.error.message'/>");
						pricesUpdated= false;						
						updateButtons();
					}
					else
					{
						
						$('#summary_order_dl').replaceWith($('#summaryOrderTemplate').tmpl(data));
						$('#totals_order_dl').replaceWith($('#totalsOrderTemplate').tmpl(data));
						$('#cartEntryTBody').replaceWith($('#entriesTemplate').tmpl(data));																	
						$('#sapUnit').replaceWith( (data.sapLabelWeightUnit !='') ?   " <spring:theme code='summary.cartitems.per' /> " + data.sapLabelWeightUnit : "");
						
						if(data.noPriceEntries !=null){
							$('#noPriceEntries').replaceWith($('#noPriceTemplate').tmpl(data));
							$('#no-price-popup').removeClass('no-popup');
							$('#no-price-popup').addClass('popup-class');
							$('#no-price-popup').dialog();
							$('#no-price-popup').focus();
							$('#modal-price-popup').addClass('background-fade');
							$('#content').css("opacity","0.3")
							$('#content').css("pointer-events","none");
							//alert("baba1");
							
						}
						$('#unitTemplateWeight').replaceWith($("#total").val());
						<c:if test="${cartData.isInternalCartOrder and baseStore.uid eq '6000' }">
							bindInputPrice();
						</c:if>
						pricesUpdated = true;
						
						if(data.sapTotalPrice == null)
						{
							pricesUpdated = false;
						}
						updateButtons();
						bindDeleteButtons();						
						
// 						ACC.modals.messageModal("<spring:theme code='checkout.summary.prices_updated.title'/>", "<spring:theme code='checkout.summary.prices_updated.message'/>");	
					}
						 
					
				},
				error: function (xht, textStatus, ex)
				{
					//alert("error");
					pricesUpdated= false;
					ACC.modals.messageModal("<spring:theme code='checkout.summary.prices_updated.error.title'/>", "<spring:theme code='checkout.summary.prices_updated.error.message'/>");					
					updateButtons();
				},
				complete: function ()
				{
					unblockUI();		
				}
			});
			
			updateButtons();

		}
		
		function validateCalculateButton(){
			//Se comenta ya que se valida esto mismo en la parte de arriba.
			//var purchaseOrder = jQuery("#PurchaseOrderNumber").val();
			//if ( (purchaseOrder.trim().length > 0 ||  isPurchaseNumberEntered) && isDeliveryAddressSelected && isTransportationModeSelected)
			if (isPurchaseNumberEntered && isDeliveryAddressSelected && isTransportationModeSelected)	
				$(".update-prices").removeAttr('disabled');
			else
			{
				ACC.negotiatequote.cancelQuoteNegotiationEvent();
				$(".update-prices").attr('disabled', true);
			}
		}
		
		function confirmShippingInstructionsPlaceOrder(code)
		{			
			ACC.modals.confirmModal("<spring:theme code="shippingInstructions.enter.confirm.title"/>",
					"<spring:theme code="shippingInstructions.enter.confirm.description"/>",
					{
						"<spring:theme code="shippingInstructions.enter.confirm.button.yes"/>" : function(){
							introduceShippingInstructionsModal(code);
							},
						"<spring:theme code="shippingInstructions.enter.confirm.button.no"/>" : function(){
							ACC.placeorder.placeOrderWithSecurityCode(code);
							}
					},
					null);
		}
		
		function introduceShippingInstructionsModal(code)
		{
			ACC.modals.openOnModal("<spring:theme text="Shipping Instructions" code="text.order.shippingInstructions.popup.title"/>", 
					"<c:url value="/checkout/single/shipping-instructions-popup" />", 
					"#shippingInstructionsPopupContainer", 
					{
						popupCallback: function(instructions)
						{
							$("#shippingInstructions" + code).val(instructions);
							ACC.placeorder.placeOrderWithSecurityCode(code);
						}
					}, 
					null);
		}		
	</script>
	
	<div id="shippingInstructionsPopupContainer" style="display: none;"></div>
	
	<div class="pagehead-wrapper">
		<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<ul>
	             <li class="active">
	                 <a href='<c:url value="/checkout"></c:url>'>Checkout</a>
	             </li>
	         </ul>
			</div>
		
		
			<div id="globalMessages">
				<common:globalMessages/>
			</div>
		</div>
	</div>
	
	<c:if test="${(not empty accConfMsgs) or (not empty accInfoMsgs) or (not empty accErrorMsgs)}">
	    <br /><br />
	</c:if>
	
	<div class="grid-container">
		<div class="span-4 side-content-slot cms_disp-img_slot">
	
		</div>
	
		<div class="span-24 last">
	
			<div class="span-24 last">
				<single-checkout:summaryFlow />
			</div>
	
			<div class="span-24 last place-order-top">
				<div class="span-24 last placeorder_right">
				
					<form:form action="${placeOrderUrl}" id="placeOrderForm1" autocomplete="off" commandName="placeOrderForm">
						
						<form:input type="hidden" id="securityCode1" class="securityCodeClass" path="securityCode"/>
						<form:input type="hidden" id="shippingInstructions1" path="shippingInstructions"/>
						<form:input type="hidden" id="negotiablePrices" path="negotiablePrices" />
						
						<cart:continueShopping continueShoppingUrl="${continueShoppingUrl}" />
						
						<div class="span-24 place-order-right">		
							<button  disabled="disabled"  class="positive right pad_right update-prices place-order" id="calculatePriceButton" onclick="ga('send', 'event', 'CalculatePrice', 'calculatePrice', 'Customer clicking the calculate price button')">
								<span class="icon-addtocart-yellow"></span><spring:theme code="checkout.summary.calculate_price" />
							</button>
							
							<!-- NEORIS_CHANGE #incidencia 63 JCVM 23/08/20144 Se cambia el color del boton de Solicitar Cotización
							     de acuerdo a como lo solicitan a color blanco mediante CSS.
							<button type="button" class="positive right pad_right request-quote" id="requestQuoteButton">
							-->
							<c:choose>
								<c:when test="${baseStore.uid eq '1000'}">
								</c:when>
								<c:otherwise>
									<button type="button" class="positive right pad_right request-quote Text_Color_White" id="requestQuoteButton" onclick="ga('send', 'event', 'CreateQuote', 'createQuote', 'Customer clicking the create quote button');">
										<spring:theme code="checkout.summary.negotiateQuote"/>
									</button>
								</c:otherwise>
							</c:choose>
							
							<c:if test="${enabledPlaceOrderButton}">
							    <button type="submit" disabled="disabled" class="positive right pad_right place-order show_processing_message" data-sec-code="1" onclick="ga('send', 'event', 'CreateOrder', 'placeOrder', 'Customer clicking the place order button');">
							        <spring:theme code="checkout.summary.placeOrder"/>
							    </button>
							</c:if>												
							
						</div>		
						<!-- NEORIS_CHANGE #33 -->
						
	<!-- 					<button type="button" class="positive right pad_right schedule-replenishment" id="scheduleReplenishmentButton"> -->
	<%-- 						<spring:theme code="checkout.summary.scheduleReplenishment"/> --%>
	<!-- 					</button> -->
						<c:set value="" var="inputChecked"/>
						<c:if test="${termsAndConditionsChecked}">
							<c:set value="checked" var="inputChecked"/>
						</c:if>
	
						<div class="terms pad_right right">
						    <br/>
						    <span><font size="2" color="red"><spring:theme code="checkout.summary.placeOrder.label"/></font></span>
						    <br/>						    						    
						    <input type="checkbox" id="OrderCondition"  ${inputChecked } name="OrderCondition" size="25" onClick="updateButtons()"/>
							<label for="OrderCondition"  ><spring:theme code="checkout.summary.placeOrder.orderConditions"/></label>
						    <br/>
							<input type="checkbox" ${inputChecked } id="Terms1" name="Terms1"  size="25" onClick="updateButtons()"  />
							
							<label for="Terms1"  >
								<c:set var="siteSuffix" value=""/>
								<c:if test="${baseStore.uid eq '6000'}">
									<c:set var="siteSuffix" value=".${baseStore.uid}"/>
								</c:if>
								<spring:theme code="checkout.summary.placeOrder.readTermsAndConditions${siteSuffix}"/>
							</label>
							
						</div>
						 <cart:negotiateQuote/>
						 <cart:replenishmentScheduleForm/> 						 
					</form:form>
										
				<div class="span-24 last">
					<c:choose>
						<c:when test="${baseStore.uid eq '6000' }">
							<checkout:summaryInternalCartItems cartData="${cartData}"/>
						</c:when>
						<c:otherwise>
							<checkout:summaryCartItems cartData="${cartData}"/>
						</c:otherwise>
					</c:choose>				    
				</div>
				
				<!-- NEORIS_CHANGE #74 Change of component's position -->				
			
			
			    <cms:pageSlot position="SideContent" var="feature" element="div" class="left">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
		        
		        
				<div class="span-12">		  				
					<cart:cartPromotions cartData="${cartData}"/>
				</div>
				
								
		
				<div class="span-8 right  place-order-cart-total">
					<cart:neorisOrderSummary/>
				</div>
				
				<div class="span-8 right last place-order-cart-total">
					<cart:neorisOrderTotals/>
				</div>
		
				<div class="span-24 place-order-bottom">
					   <form:form action="${placeOrderUrl}" id="placeOrderForm2"  commandName="placeOrderForm">					
						<button disabled="disabled"  class="positive right pad_right update-prices place-order" id="calculatePriceButton" onclick="ga('send', 'event', 'CalculatePrice', 'calculatePrice');">
							<span class="icon-addtocart-yellow"></span><spring:theme code="checkout.summary.calculate_price" />
						</button>
						<!-- NEORIS_CHANGE #incidencia 63 JCVM 23/08/20144 Se cambia el color del boton de Solicitar Cotización
						     de acuerdo a como lo solicitan a color blanco mediante CSS.
						<button type="button" class="positive right pad_right request-quote" id="requestQuoteButton">
						-->
						<c:choose>
							<c:when test="${baseStore.uid eq '1000'}">
							</c:when>
							<c:otherwise>
								<button type="button" class="positive right pad_right request-quote Text_Color_White" id="requestQuoteButton" onclick="ga('send', 'event', 'CreateQuote', 'createQuote', 'Customer clicking the create quote button');">
									<spring:theme code="checkout.summary.negotiateQuote"/>
								</button>
							</c:otherwise>
						</c:choose>
												
						<form:input type="hidden" id="securityCode2" class="securityCodeClass" path="securityCode"/>
						<form:input type="hidden" id="shippingInstructions2" path="shippingInstructions"/>
						
						<c:if test="${enabledPlaceOrderButton}">
							<button type="submit" disabled="disabled" class="positive right pad_right place-order show_processing_message"  data-sec-code="2"  onclick="ga('send', 'event', 'CreateOrder', 'placeOrder', 'Customer clicking the place order button');">
								<spring:theme code="checkout.summary.placeOrder"/>
							</button>
						</c:if>
						
						<div class="terms pad_right right" style="display:none;">
							<formUtil:formCheckbox idKey="Terms2" labelKey="checkout.summary.placeOrder.readTermsAndConditions"
									inputCSS="checkbox-input" labelCSS="checkbox-label" path="termsCheck" mandatory="true" />
						</div>
					</form:form>
				</div>
			</div>
		</div>
		
		
		
		</div>
	</div>
</template:page>
