ACC.paymenttype = {

	bindAll: function()
	{
		this.bindToPaymentTypeSelection();
		this.bindToPurchaseOrderNumberInput();
	},

	bindToPaymentTypeSelection: function()
	{
		$("input:radio[name='PaymentType']").change(function()
		{
			$('#checkout_summary_paymentType_div').removeClass('complete');
			var paymentTypeSelected = $("input:radio[name='PaymentType']:checked").val();
			$.postJSON(setPaymentTypeURL, {paymentType: paymentTypeSelected}, ACC.paymenttype.summaryFlowAfterPaymentTypeSetSuccess);
		});
	},

	bindToPurchaseOrderNumberInput: function()
	{
		//NEORIS_CHANGE #74
		$("#PurchaseOrderNumber").focusout(function()
		{
			var purchaseOrderNumber = $("input[name='PurchaseOrderNumber']").val();
			
			// validate PO Number
			jQuery.ajax
			({
				url: validatePOURL,
				type: 'POST',
				dataType: 'json',
				data: {purchaseOrderNumber: purchaseOrderNumber},
				beforeSend: function ()
				{
					blockUI();
				},
				success: function (data)
				{					
					if(data.isPOAlreadyUsed == true)
					{
						var buttons = {};
						buttons[POAlredyUsedModalOK] = function(){
							ACC.paymenttype.setPurchaseOrderNumber(purchaseOrderNumber);
						};
						buttons[POAlredyUsedModalCancel] = function(){
							$("input[name='PurchaseOrderNumber']").val("");
							ACC.paymenttype.setPurchaseOrderNumber("");
						};
						
						ACC.modals.confirmModal(POAlredyUsedModalTitle,
								POAlredyUsedModalContent,
								buttons,
								null);
					}
					else
					{
						ACC.paymenttype.setPurchaseOrderNumber(purchaseOrderNumber);
					}
				},
				error: function (xht, textStatus, ex)
				{
					ACC.modals.messageModal("e", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					unblockUI();
				}
			});
		});
	},

	 setPurchaseOrderNumber: function(purchaseOrderNumber)
	 {		 
		 jQuery.ajax
			({
				url: setPurchaseOrderNumberURL,
				type: 'POST',
				dataType: 'json',
				data: {purchaseOrderNumber: purchaseOrderNumber},
				beforeSend: function ()
				{
					blockUI();
				},
				success: function (data)
				{
					updateButtons();
				},
				error: function (xht, textStatus, ex)
				{
					ACC.modals.messageModal("e", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					unblockUI();
				}
			});
	 },
	
	summaryFlowAfterPaymentTypeSetSuccess: function(checkoutCartData) 
	{
		ACC.paymenttype.markPaymentTypeSectionAsCompleted();
		ACC.paymenttype.hideAndShowRequiredDiv( checkoutCartData.paymentType.code);
		ACC.costcenter.refreshCostCenterSection(checkoutCartData);
		// NEORIS_CHANGE #74
		//ACC.address.refreshDeliveryAddressSection(checkoutCartData);
		ACC.deliverymode.refreshDeliveryMethodSection(checkoutCartData);
		ACC.refresh.refreshCartItems(checkoutCartData);
		ACC.refresh.refreshCartTotals(checkoutCartData);
		ACC.negotiatequote.cancelQuoteNegotiationEvent();
		ACC.replenishment.cancelReplenishmentEvent();
	},

	refreshPaymentTypeSection: function(checkoutCartData)
	{
			var paymentTypeFromCart =  checkoutCartData.paymentType.code;
		   	$("input:radio[name='PaymentType'][value="+paymentTypeFromCart+"]").attr('checked',true);
		   	$("input[name='PurchaseOrderNumber']").attr('value',checkoutCartData.purchaseOrderNumber);
		    ACC.paymenttype.markPaymentTypeSectionAsCompleted();
			ACC.paymenttype.hideAndShowRequiredDiv(checkoutCartData.paymentType.code);
	},

	summaryFlowAfterPurchaseOrderNumberSuccess: function(checkoutCartData) 
	{
		if ($("input:radio[name='PaymentType']:checked").val() != ''){
			$('#checkout_summary_paymentType_div').addClass('complete');
		}else{
			$('#checkout_summary_paymentType_div').removeClass('complete');
		}
	},

	markPaymentTypeSectionAsCompleted: function()
	{
		if ($("input:radio[name='PaymentType']:checked").val() != ''){
			$('#checkout_summary_paymentType_div').addClass('complete');
		}else{
			$('#checkout_summary_paymentType_div').removeClass('complete');
		}
	},

	hideAndShowRequiredDiv: function(paymentType)
	{
		if(paymentType == 'CARD'){
			$('#checkout_summary_costcenter_div').hide();
			$('#checkout_summary_payment_div').show();
		}else{
			$('#checkout_summary_payment_div').hide();
			$('#checkout_summary_costcenter_div').show();
		}
	}

};

$(document).ready(function()
{
	ACC.paymenttype.bindAll();
});



	
	
