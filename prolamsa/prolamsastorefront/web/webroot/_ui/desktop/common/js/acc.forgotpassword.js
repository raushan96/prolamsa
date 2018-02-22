ACC.forgotpassword = {
	
	$forgotPasswordLink: $('.password-forgotten'),


	bindForgotPasswordLink: function(link) {
		link.click(function() {
			$.get(link.data('url')).done(function(data) {
				$.colorbox({
					html: data,
					height: '250px',
					overlayClose: false,
					onOpen: function() {
						$('#validEmail').remove();
					},
					onComplete: function() {
						$.colorbox.resize();
						$('#forgottenPwdForm').ajaxForm({
							success: function(data)
							{
								if ($(data).closest('#validEmail').length)
								{
									if ($('#validEmail').length === 0)
									{
										$("#loginMessage").append(data);
									}
									$.colorbox.close();
								}
								else
								{
									$('#forgottenPwdForm')
										.find('.form_field-elements')
										.html($(data)
										.find('#forgottenPwdForm .form_field-elements'));
									$.colorbox.resize();
								}
							}
						});
						ACC.forgotpassword.bindForgottenPwdCancelButton();
						ACC.common.refreshScreenReaderBuffer();
					},
					onClosed: function() {
						ACC.common.refreshScreenReaderBuffer();
					}
				});
			});
		});
	},

	bindForgottenPwdCancelButton: function()
	{
		$("#forgottenPwdCancelButton").click(function() { $.colorbox.close(); });
	},

	bindProfileForgottenPwdLink: function()
	{
		$("#profile-forgotten-password").click(function()
		{
			jQuery.ajax
			({
				url: ACC.config.contextPath + "/my-account/reset-password.json",
				type: 'POST',
				dataType: 'json',
				data: "empty=empty",
				beforeSend: function ()
				{
					blockUI();
				},
				success: function (data)
				{
					if (data.status == 0)
						ACC.modals.messageModal("", data.message);
					else
						ACC.modals.messageModal("Error", data.message);
				},
				error: function (xht, textStatus, ex)
				{
					ACC.modals.messageModal("Error", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					unblockUI();
				}
			});
		});
	},

	bindAll: function() {
		ACC.forgotpassword.bindForgotPasswordLink(ACC.forgotpassword.$forgotPasswordLink);
		ACC.forgotpassword.bindProfileForgottenPwdLink();
	}
};

$(document).ready(function() {
	ACC.forgotpassword.bindAll();
});