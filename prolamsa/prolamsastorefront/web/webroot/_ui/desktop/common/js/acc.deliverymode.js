ACC.deliverymode = {

	bindAll: function ()
	{
		this.bindEditDeliveryMethodButton();
		
		// NEORIS_CHANGE #74 
		$('#transportationModeSelect').change( ACC.deliverymode.transportationModeChanged);
		$('#incotermsSelect').change( ACC.deliverymode.incotermsChanged);
		
		if($('#transportationModeSelect').val() != 'none' && $('#incotermsSelect').val() == 'none')
		{
			 $('#incotermsSelect')
		     .find('option')
		     .remove()
		     .end()
		     .append('<option value="none">'+selectIncotermText+'</option>')
		     .val('none')
		}
		if($('#transportationModeSelect').val() == 'none')
		{
			 $('#incotermsSelect')
		     .find('option')
		     .remove()
		     .end()
		     .append('<option value="none">'+selectIncotermText+'</option>')
		     .val('none')
		}
		
	},

	bindEditDeliveryMethodButton: function ()
	{
		$('div.checkout_summary_flow_c .change_mode_button').on("click", function ()
		{
			$.ajax({
				url: getDeliveryModesUrl,
				type: 'GET',
				dataType: 'json',
				success: function (data)
				{
					// Fill the available delivery addresses and select button
					$('#delivery_modes_dl').html($('#deliveryModesTemplate').tmpl({deliveryModes: data}));
					$('#delivery_modes_button').html($('#deliveryModeButton').tmpl({deliveryModes: data}));

					// Show the delivery modes popup
					$.colorbox({inline: true, href: "#popup_checkout_delivery_modes", height: false, overlayClose: false});
					$(document).on('cbox_complete', function ()
					{
						$.colorbox.resize();
					});

					ACC.deliverymode.bindUseThisDeliveryMode();
				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to get delivery modes. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}
			});
			return false;
		});
	},

	bindUseThisDeliveryMode: function ()
	{
		$('#use_this_delivery_method').click(function ()
		{
			var selectedCode = $('input:radio[name=delivery]:checked').val();
			if (selectedCode)
			{
				$.ajax({
					url: setDeliveryModeUrl,
					type: 'POST',
					dataType: 'json',
					data: {modeCode: selectedCode},
					beforeSend: function ()
					{
						$.colorbox.toggleLoadingOverlay();
					},
					success: function (data)
					{
						if (data != null)
						{
							//alert("delivery mode set successfully");
							ACC.refresh.refreshPage(data);
							parent.$.colorbox.close();
						}
						else
						{
							alert("Failed to set delivery mode");
						}
					},
					error: function (xht, textStatus, ex)
					{
						alert("Ajax call failed while trying to set delivery mode. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
					},
					complete: function ()
					{
						$.colorbox.toggleLoadingOverlay();						
					}
				});
			}
			return false;
		});
	},

	refreshDeliveryMethodSection: function (data)
	{
		//$('#checkout_summary_deliverymode_div').replaceWith($('#deliveryModeSummaryTemplate').tmpl(data));
		//ACC.deliverymode.bindEditDeliveryMethodButton();
	},
	
	// NEORIS_CHANGE #74
	transportationModeChanged: function ()
	{
		var transportationModeCode = $(this).val();
		
		if (pricesUpdated)
		{
			ACC.modals.messageModal(pricesNeedUpdateTitle, pricesNeedUpdateMessage);
			ACC.deliverymode.clearSAPData();
		}
		
		pricesUpdated= false;
		
		jQuery.ajax
		({
			url: getIncotermsURL,
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: {transportationModeCode: transportationModeCode},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				ACC.deliverymode.transportationModeSuccessfullyUpdated(data);
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
		
		return false;
		
	},
	
	incotermsChanged: function ()
	{
		var incotemrs = $(this).val();
		var transportationModeCode = $('#transportationModeSelect').val();
		
		if (pricesUpdated)
		{
			ACC.modals.messageModal(pricesNeedUpdateTitle, pricesNeedUpdateMessage);
			ACC.deliverymode.clearSAPData();
		}
		
		pricesUpdated= false;
		
		jQuery.ajax
		({
			url: setTransportationModeUrl,
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: {transportationModeCode: transportationModeCode+"-"+incotemrs},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				ACC.deliverymode.incotermsSuccessfullyUpdated(data);
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
		
		return false;
		
	},
	
	// NEORIS_CHANGE #74	
	transportationModeSuccessfullyUpdated: function(data)
	{
		
		 updateButtons();
		 
		 $('#incotermsSelect')
	     .find('option')
	     .remove()
	     .end()
	     .append('<option value="none">'+selectIncotermText+'</option>')
	     .val('none')
	 ;
		 
		 jQuery.each( data, function( i, val ) {
			 jQuery("#incotermsSelect").append('<option value=\"'+ val.incotermCode +'\">'+ val.incotermCode +" - "+ val.incotermDescription +"</option>");
		 });
		 


		 


		 
		 
	},
	// NEORIS_CHANGE #74	
	incotermsSuccessfullyUpdated: function(data)
	{
		
		 updateButtons();
	},
	clearSAPData: function ()
	{
		jQuery("#cartEntriesTable .sap-data").empty();

		var emptyObj = {};
		emptyObj.hotRolledWeight = 0;
		emptyObj.coldRolledWeight = 0;
		emptyObj.galvanizedWeight = 0;
		emptyObj.galvametalWeight = 0;
		emptyObj.aluminizedWeight = 0;
		emptyObj.hotRolledPrice = 0;
		emptyObj.coldRolledPrice = 0;
		emptyObj.galvanizedPrice = 0;
		emptyObj.galvametalPrice = 0;
		emptyObj.aluminizedPrice = 0;
		emptyObj.totalDeliveryCost=0;
		emptyObj.totalAssurance=0;
		emptyObj.totalTaxas=0;
		

		$('#summary_order_dl').replaceWith($('#summaryOrderTemplate').tmpl(emptyObj));
		$('#totals_order_dl').replaceWith($('#totalsOrderTemplate').tmpl(emptyObj));
	}

};

$(document).ready(function ()
{
	ACC.deliverymode.bindAll();
});




