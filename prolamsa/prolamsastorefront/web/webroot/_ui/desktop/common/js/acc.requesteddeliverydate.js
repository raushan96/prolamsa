ACC.requesteddeliverydate = {

	bindAll: function ()
	{
		//this.bindEditDeliveryMethodButton();
		
		// NEORIS_CHANGE #74 
		//$('#requestedDeliveryDate').change( ACC.deliverymode.requestedDeliveryDateChanged);
		//$('#requestedDeliveryDate').on("focus", ACC.deliverymode.requestedDeliveryDateChanged);
		this.requestedDeliveryDateChanged();
		
	},
	
	requestedDeliveryDateChanged: function ()
	{		
	  $("#requestedDeliveryDate").change(function()
		 {
			var requestedDeliveryDate = $("input[name='requestedDeliveryDate']").val();					
		
		jQuery.ajax
		({
			url: setRequestedDeliveryDateUrl,
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: {requestedDeliveryDate: requestedDeliveryDate},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				//ACC.deliverymode.transportationModeSuccessfullyUpdated(data);
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
	
	
	requestedDeliveryDateSet: function ()
	{		
	 
		var requestedDeliveryDate = $("input[name='requestedDeliveryDate']").val();					
		
		jQuery.ajax
		({
			url: setRequestedDeliveryDateUrl,
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: {requestedDeliveryDate: requestedDeliveryDate},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				//ACC.deliverymode.transportationModeSuccessfullyUpdated(data);
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
	
	} 
	// NEORIS_CHANGE #74	
	//transportationModeSuccessfullyUpdated: function(data)
	//{
    //   updateButtons();
	//}	

};

$(document).ready(function ()
{
	ACC.requesteddeliverydate.bindAll();	
});




