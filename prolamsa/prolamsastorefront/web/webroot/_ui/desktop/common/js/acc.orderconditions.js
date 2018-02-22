ACC.orderconditions = {

	bindOrderConditionsLink: function(link) {
		link.click(function() {
			$.colorbox({
				href: getOrderConditionsUrl,
				onComplete: function() {
					ACC.common.refreshScreenReaderBuffer();
				},
				onClosed: function() {
					ACC.common.refreshScreenReaderBuffer();
				}
			});
		});
	},

	initialize: function() {
		with(ACC.orderconditions) {
			bindOrderConditionsLink($('.orderConditionsLink'));
		}
	}
}

$(document).ready(function(){
	ACC.orderconditions.initialize();
});
