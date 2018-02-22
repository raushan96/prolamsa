// User Location jQuery
ACC.userlocation = {

	bindAll: function() {
		ACC.userlocation.bindUserLocationSearchButtonClick();
		ACC.userlocation.bindAutoLocationSearchButtonClick($("#findStoresNearMe"));
	},

	bindUserLocationSearchButtonClick: function() {
		$('#user_location_query_button').click(function(e) {
			$.ajax({
				url: searchUserLocationUrl,
				type: 'GET',
				data: {q: $('#user_location_query').attr("value")},
				success: function(data) {
					location.reload();
				}
			});
		});
	},
	
	bindAutoLocationSearchButtonClick: function(findStoresNearMe) {
		if (findStoresNearMe.length > 0) {
			findStoresNearMe.click(function(event) {
				event.preventDefault();
				var gps = navigator.geolocation;
				if (gps) {
					gps.getCurrentPosition(ACC.userlocation.positionSuccessStoresNearMe, function(error) {
						console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
					});
				}
			});
		}
	},

	positionSuccessStoresNearMe: function(position) {
		$("#latitude").val(position.coords.latitude);
		$("#longitude").val(position.coords.longitude);
		$.ajax({
			url: autoUserLocationUrl,
			type: 'POST',
			data: {latitude: position.coords.latitude, longitude: position.coords.longitude},
			success: function(data) {
				location.reload();
			}
		});
		
		return false;
	}

};

$(document).ready(function(){
	ACC.userlocation.bindAll();
});
