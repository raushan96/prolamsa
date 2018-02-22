ACC.negotiatequote = {

	bindAll: function()
	{
		
		if(typeof cartDataquoteAllowed != 'undefined')
		{
			$(".request-quote").attr('quoteAllowed',cartDataquoteAllowed);
			this.bindToCancelQuoteClick();
			//NEORIS_CHANGE #74
			//this.updateRequestQuoteButton();
			this.bindToProceedButtonClick();
			this.bindToRequestQuoteButtonClick();
		}
		
	},
	
	displayNegotiateQuoteDiv: function()
	{
		$(".request-quote").addClass('pressed');
		$('#negotiate-quote-div').show();
		//NEORIS_CHANGE #74
		//$(".place-order").attr('disabled', true);
		$("#scheduleReplenishmentButton").attr('disabled', true);
		return false;	
	},
	
	bindToCancelQuoteClick: function()
	{
		$('#cancel-quote-negotiation').click(function()
		{
			ACC.negotiatequote.cancelQuoteNegotiationEvent();
		});
	},
	
	cancelQuoteNegotiationEvent: function()
	{
		$(".request-quote").removeClass('pressed');
		$('#negotiate-quote-div').hide();
		$('#quoteRequestDescription').value = "";
		$("#negotiateQuote").val(false);
		//NEORIS_CHANGE #74
		//ACC.placeorder.updatePlaceOrderButton();
		//ACC.negotiatequote.updateRequestQuoteButton();
		ACC.replenishment.updateScheduleReplenishmentButton();
	},
	
	bindToProceedButtonClick: function()
	{
		$('#negotiateQuoteButton').click(function()
		{
			$("#negotiateQuote").val(true);
			
			if (isNegotiablePricesFuncEnabled)
			{
				if(ACC.negotiatequote.validateNegotiablePrices())
				{
					ACC.placeorder.placeOrderWithSecurityCode();	
				}
			}
			else if(axisNegotiation)
			{
				if(ACC.negotiatequote.validateAxisNegotiablePrices())
					ACC.placeorder.placeOrderWithSecurityCode();
				else
					ACC.modals.messageModal(ACC.neorisproduct.messages.addToCArt,ACC.neorisproduct.messages.minMaxValidationOnError);
				
			}else
			{
				ACC.placeorder.placeOrderWithSecurityCode();
			}
			
			return false;
		});
	},
	
	bindToRequestQuoteButtonClick: function()
	{
		$('.request-quote').click(function()
		{
			$('.request-quote').addClass("pressed");;
			ACC.negotiatequote.displayNegotiateQuoteDiv();
			return false;
		});
	},
	
	updateRequestQuoteButton: function()
	{
		var paymentType = $("#checkout_summary_paymentType_div").hasClass("complete");
		var deliveryAddress = $("#checkout_summary_deliveryaddress_div").hasClass("complete");
		var deliveryMode = $("#checkout_summary_deliverymode_div").hasClass("complete");
		var costCenter = $('#checkout_summary_costcenter_div').hasClass("complete");
		var paymentDetails = $("#checkout_summary_payment_div").hasClass("complete")
		var selectedPaymentType = $("input:radio[name='PaymentType']:checked").val() != 'CARD';
		var costCenterSelected = $("#CostCenter option:selected")[0].value != '';
		var quoteAllowed = 'true' == $(".request-quote").attr("quoteAllowed");
		
		if (quoteAllowed && paymentType && deliveryAddress && deliveryMode && (costCenter || paymentDetails) && selectedPaymentType
			&& costCenterSelected)
		{
			$(".request-quote").removeAttr('disabled');
		}
		else
		{
			$(".request-quote").attr('disabled', true);
		}

		if ($(".request-quote").hasClass("pressed"))
		{
			$(".place-order").attr('disabled', true);
			$("#scheduleReplenishmentButton").attr('disabled', true);
		}
	},
	
	validateNegotiablePrices: function()
	{
		var proceed = true;
		var negPricesArray = new Array();
				
		$(".negotiablePriceInput").each(function(index, element)
		{
			if(!proceed)
			{
				return false;
			}
			
			if($(element).hasClass("validateNegPrice"))
			{
				var eachPrice = $(element).val();
				var entryNumber =  $(element).attr("entryNumber");
				
				if(isNaN(parseFloat(eachPrice)))
				{
					$(element).addClass("negotiablePriceInputError");
					$("#messageErrorPriceNeg").html(cartPagePriceNegInvalidValueText);
					$(".globalMessagesPriceNeg").show();
					
					window.setTimeout(function() {
						$(".globalMessagesPriceNeg").hide();
						}, 5000);
					
					proceed = false;
					return;
				}
				else
				{
					var numeric = parseFloat(eachPrice);
					eachPrice = numeric.toFixed(4);
					$(element).val(eachPrice);
					
					var minNegPrice = $(element).attr("minNegPrice");
					var maxNegPrice = $(element).attr("maxNegPrice");
					if (numeric< parseFloat(minNegPrice) || numeric > parseFloat(maxNegPrice))
					{
						$(element).addClass("negotiablePriceInputError");
						$("#messageErrorPriceNeg").html(cartPagePriceNegInvalidValueOutOfRangeText);
						$(".globalMessagesPriceNeg").show();
						
						window.setTimeout(function() {
							$(".globalMessagesPriceNeg").hide();
							}, 5000);
						
						proceed = false;
						return;
					}
				}
				
				var obj = {};
				obj.entryNumber = entryNumber;
				obj.negPrice = eachPrice;
				
				negPricesArray.push(obj)
			}
		});
		
		var negPricesObj = {negotiablePrices : negPricesArray};
		var prices = JSON.stringify(negPricesObj);
		
		$("#negotiablePrices").val(prices);
		
		if(!proceed)
		{
			ACC.negotiatequote.bindToNegotiablePriceInputs()
		}

		return proceed;
	},
	
	validateAxisNegotiablePrices: function()
	{
		var proceed = true;
		var negPricesArray = new Array();
				
		$(".entryDisc").each(function(index, element)
		{
			var negPrice = $(element).val();
			var maxPrice = $(element).attr("valMax");
			var minPrice =$(element).attr("valMin");
			var oriPrice = $(element).attr("oriPrice");
			var entryNumber = $(element).attr("entryNumber");
			
			if(negPrice.trim() == "")
				return;
			
			var negPriceNumeric = parseFloat(negPrice);
			var maxPriceNumeric = parseFloat(maxPrice);
			var minPriceNumeric = parseFloat(minPrice);
			
			if(negPriceNumeric <=0 || negPriceNumeric < minPriceNumeric || negPriceNumeric > maxPriceNumeric)
			{	
				proceed = false;
				return false;
			}
			var obj = {};
			obj.entryNumber = entryNumber;
			obj.negPrice = negPrice-oriPrice;
			
			negPricesArray.push(obj)
			
		});
		
		var negPricesObj = {negotiablePrices : negPricesArray};
		var prices = JSON.stringify(negPricesObj);
		
		$("#negotiablePrices").val(prices);
		
		
		return proceed;
	},
	
	bindToNegotiablePriceInputs: function()
	{
		$(".negotiablePriceInput").keypress(function(event)
			{
				$(this).removeClass("negotiablePriceInputError");
			});
	}
};

$(document).ready(function()
{
	if (typeof negotiateQuote !== 'undefined' && negotiateQuote)
	{
		ACC.negotiatequote.displayNegotiateQuoteDiv();

		// Used in Account Quote Management page
		$('#negotiate-quote-div-label-add-comment').show();
	}

	ACC.negotiatequote.bindAll();
});








