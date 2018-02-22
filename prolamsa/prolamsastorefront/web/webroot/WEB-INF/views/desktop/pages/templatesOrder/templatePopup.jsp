<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/product" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<script type="text/javascript">

jQuery(document).ready(
		function()
		{
			
			jQuery("#formInfoButton").click(checkExist);
			
			jQuery(document).on('cbox_complete', function ()
			{
				jQuery.colorbox.resize();
			});
		});


function checkExist()
{
	var info = jQuery("#formInfo").serialize();
	
	jQuery.ajax
	({
		url: "<c:url value="/templatesOrder/checkExistTemplate.json" />",
		type: 'POST',
		dataType: 'json',
		data: info,
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
           	if (data.status == 0)
           	{
           		acceptTemplateForm();
           	}
           	else
           	{
           		confirmUpdateTemplateName();
           	}	
		},
		error: function (xht, textStatus, ex)
		{
			ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		complete: function ()
		{
			unblockUI();
		}
	});
}

function confirmUpdateTemplateName(){
    ACC.modals.confirmModalOK("<spring:theme code="template.update.name"/>", "<spring:theme code="template.update.name.confirm"/>", 
  			function () { updateName() },
  			null);
}

function updateName()
{
	var info = jQuery("#formInfo").serialize();
	
	jQuery.ajax
	({
		url: "<c:url value="/templatesOrder/updateTemplate.json" />",
		type: 'POST',
		dataType: 'json',
		data: info,
		success: function (data)
		{
           	if (data.status == 0)
           	{
           		//ACC.modals.messageModal("<spring:theme code="template.creation.order.title"/>", "<spring:theme code="template.update.order.success"/>");
           		location.reload(true);
           	}
           	else
           	{
           		unblockUI();
           		ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
           	}
		},
		error: function (xht, textStatus, ex)
		{
			unblockUI();
			ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		beforeSend: function ()
		{
			blockUI();
		},
		complete: function ()
		{
// 			location.reload(true);
// 			unblockUI();
		}
	});
}

function acceptTemplateForm()
{
	var info = jQuery("#formInfo").serialize();
	
	jQuery.ajax
	({
		url: "<c:url value="/templatesOrder/saveTemplate.json" />",
		type: 'POST',
		dataType: 'json',
		data: info,
		success: function (data)
		{
           	if (data.status == 0)
           	{
           		//ACC.modals.messageModal("<spring:theme code="template.creation.order.title"/>", "<spring:theme code="template.creation.order.success"/>");
           		location.reload(true);
           	}
           	else
           	{
           		unblockUI();
           		ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
           	}
		},
		error: function (xht, textStatus, ex)
		{
			unblockUI();
			ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		beforeSend: function ()
		{
			blockUI();
		}
	});
}

</script>

<br/>

<form id="formInfo">
	<span><spring:theme code="documentSearch.list.name"/></span>
	<input type="text" name="name"/>
	<input type="hidden" value="${order}" name="orderCode" />
</form>

<br/>

<div class="div-text-align-right">
	<formUtil:formButton 
		id="formInfoButton"
		type="button" 
		tabindex="107"
		springThemeText="text.company.managePermissions.edit.saveButton"
		 />
</div>


<br/>

