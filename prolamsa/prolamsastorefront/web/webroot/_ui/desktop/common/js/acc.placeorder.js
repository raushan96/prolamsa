ACC.placeorder = {

	bindAll: function ()
	{
		this.bindPlaceOrd();
		//NEORIS_CHANGE #74
		//this.updatePlaceOrderButton();
	},
	bindPlaceOrd: function ()
	{
		$("#Terms1").click(function ()
		{
			var terms1enable = $('#Terms1').attr("checked");
			if (terms1enable == undefined || terms1enable == 'false')
			{
				$('#Terms2').attr("checked", false);
			}
			else
			{
				$('#Terms2').attr("checked", true);
			}
		});

		$("#Terms2").click(function ()
		{
			var terms2enable = $('#Terms2').attr("checked");
			if (terms2enable == undefined || terms2enable == 'false')
			{
				$('#Terms1').attr("checked", false);
			}
			else
			{
				$('#Terms1').attr("checked", true);
			}
		});
	},

	updatePlaceOrderButton: function ()
	{
		var paymentType = $("#checkout_summary_paymentType_div").hasClass("complete");
		var deliveryAddress = $("#checkout_summary_deliveryaddress_div").hasClass("complete");
		var deliveryMode = $("#checkout_summary_deliverymode_div").hasClass("complete");
		var costCenter = $('#checkout_summary_costcenter_div').hasClass("complete");
		var paymentDetails = $("#checkout_summary_payment_div").hasClass("complete")

		// NEORIS_CHANGE #74
//		if (paymentType && deliveryAddress && deliveryMode && (costCenter || paymentDetails))
//		{
//			$(".place-order").removeAttr('disabled');
//		}
//		else
//		{
//			$(".place-order").attr('disabled', true);
//		}
	},

	placeOrderWithSecurityCode: function (formnr)
	{
		var securityCode = $("#SecurityCode" + formnr).val();
		$(".securityCodeClass").val(securityCode);
		blockUI();
		document.getElementById("placeOrderForm1").submit();
	}
};

$(document).ready(function ()
{
	ACC.placeorder.bindAll();
});


