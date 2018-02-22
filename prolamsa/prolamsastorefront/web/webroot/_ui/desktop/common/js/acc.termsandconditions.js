ACC.termsandconditions = {

	bindTermsAndConditionsLink: function(link) {
		link.click(function() {
			$.colorbox({
				href: getTermsAndConditionsUrl,
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
		with(ACC.termsandconditions) {
			bindTermsAndConditionsLink($('.termsAndConditionsLink'));
		}
	}
}

$(document).ready(function(){
	ACC.termsandconditions.initialize();
});
