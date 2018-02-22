ACC.storefinder = {

	bindAll: function()
	{
		this.bindToFindStoresNearMe($("#findStoresNearMe"));
	},

	bindToFindStoresNearMe: function(findStoresNearMe) {
		if (findStoresNearMe.length > 0) {
			findStoresNearMe.click(function(event) {
				event.preventDefault();
				var gps = navigator.geolocation;
				if (gps) {
					gps.getCurrentPosition(ACC.storefinder.positionSuccessStoresNearMe, function(error) {
						console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
					});
				}
			});
		}
	},

	positionSuccessStoresNearMe: function(position) {
		$("#latitude").val(position.coords.latitude);
		$("#longitude").val(position.coords.longitude);
		$("#nearMeStorefinderForm").submit();

		return false;
	}
};

$(document).ready(function()
{
	ACC.storefinder.bindAll();
});
