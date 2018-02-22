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

jQuery(document).ready(function()
{
	$("#templateDraftName_textField").val("");
	//jQuery("#formInfoButton").click(acceptTemplateForm);
	jQuery("#formInfoButton").click(existDraft);
	
	$("#templateDraftName_textField").keypress(function(event){
	    
		if(event.keyCode == 13){			
			jQuery("#formInfoButton").click();
	    }
	});
		
// 	jQuery(document).on('cbox_complete', function ()
// 	{
// 		jQuery.colorbox.resize();
// 	});
});

function existDraft()
{
	var info = jQuery("#formInfo").serialize();
	
	jQuery.ajax
	({
		url: "<c:url value="/templates/checkExistDraft.json" />",
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
           		confirmUpdateDraftName();
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

function acceptTemplateForm()
{
	var info = jQuery("#formInfo").serialize();

	jQuery.ajax
	({
		url: "<c:url value="/templates/saveAjax.json" />",
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
           		location.reload(true);
           		//ACC.modals.messageModal("<spring:theme code="template.creation.title"/>", "<spring:theme code="template.creation.success"/>");
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
		complete: function ()
		{
// 			location.reload(true);
// 			unblockUI();
		}
	});
}

function confirmUpdateDraftName(){
    ACC.modals.confirmModalOK("<spring:theme code="draft.update.name"/>", "<spring:theme code="draft.update.name.confirm"/>", 
  			function () { updateNameDraft() },
  			null);
}

function updateNameDraft()
{
	var info = jQuery("#formInfo").serialize();
	
	jQuery.ajax
	({
		url: "<c:url value="/templates/updateDraft.json" />",
		type: 'POST',
		dataType: 'json',
		data: info,
		success: function (data)
		{
           	if (data.status == 0)
           	{
           		location.reload(true);
           		//ACC.modals.messageModal("<spring:theme code="template.creation.order.title"/>", "<spring:theme code="template.update.order.success"/>");
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


</script>

<br/>
 
<form id="formInfo">
	<span><spring:theme code="documentSearch.list.name"/> </span> <input type="text" id="templateDraftName_textField" name="name" />
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

<p class="saveDraft">
	<spring:theme code="draft.text.note"/>
</p>
