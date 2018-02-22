<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/product" %>

<script type="text/javascript">

jQuery(document).ready(function()
{
	jQuery("#formInfoButton").click(acceptTemplateForm);
	
	jQuery(document).on('cbox_complete', function ()
	{
		jQuery.colorbox.resize();
	});
});

function acceptTemplateForm()
{
	var info = jQuery("#formInfo").serialize();

	jQuery.ajax
	({
		url: "<c:url value="/templates/saveAjax.json" />",
		type: 'POST',
		dataType: 'json',
		//contentType: 'application/json; charset=UTF-8',
		data: info,
		beforeSend: function ()
		{
			jQuery.colorbox.toggleLoadingOverlay();
		},
		success: function (data)
		{
			if (data != null)
			{
				if (data.status == 0)
					alert("success: " + data.message);
				else
					alert("error:" + data.message);
				location.reload(true)
			}
			else
			{
				alert("Ajax call failed.");
			}
		},
		error: function (xht, textStatus, ex)
		{
			alert("Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		complete: function ()
		{
			jQuery.colorbox.toggleLoadingOverlay();						
		}
	});
}

</script>

<br/>

<div class="span-20">
<div class="title_holder">
<div class="title">
</div>
<h2>Save Template Draft</h2>
</div>

<form id="formInfo">
	<input type="radio" name="type" value="template" checked="checked"/> Template
	<br/>
	<input type="radio" name="type" value="draft" /> Draft
	<br/>
	Name <input type="text" name="name" />
</form>

<input id="formInfoButton" type="button" value="Save" />

</div>