ACC.neorisDownloadDocuments = {
	
	transferFormId: "#transferDocumentForm",
	docNumberFieldId: "#documentNumberField",
	docOwnerFieldId: "#documentOwnerField",
	docTypeFieldId: "#documentTypeField",
	transferTypeFieldId: "#transferTypeField",
	
	miscDocDownloadFormId: "#miscDocumentDownloadForm",
	miscDocNumberFieldId: "#miscDocumentNumberField",
	miscDocOwnerFieldId: "#miscDocumentOwnerField",
	miscDocTypeFieldId: "#miscDocumentTypeField",
	
	PODocumentDownloadForm: "#PODocumentDownloadForm",
	orderCodeFieldId: "#orderDocumentField",
	orderAttachmentCodeFieldId: "#attachmentCodeField",
	
	massiveDocumentsToDownloadFieldId: "#massiveDocumentsToDownload",
	
	options : {},
		
	initialize: function(options)
	{
		ACC.neorisDownloadDocuments.options = options;
		ACC.neorisDownloadDocuments.bindAll();
	},
	
	bindAll: function()
	{
		jQuery(".myLinkInvoice, .myLinkQuote").click(function()
		{
			var elementId = jQuery(this).attr("id");
			var docNumber = elementId.split("_")[1];
			var docOwner = elementId.split("_")[2];
			
			ACC.neorisDownloadDocuments.showTransferModal(docNumber,docOwner);
		});
		
		jQuery(".myLinkMiscDoc").click(function()
		{
			alert(".");
			var elementId = jQuery(this).attr("id");
			var docNumber = elementId.split("_")[1];
			var docOwner = elementId.split("_")[2];
			var docType = elementId.split("_")[3];
			
			ACC.neorisDownloadDocuments.miscDocumentDownload(docNumber, docOwner, docType);
		});	
		
		/*
		jQuery(".myLinkMiscDoc").on("click",function()
		{
			var elementId = jQuery(this).attr("id");
			var docNumber = elementId.split("_")[1];
			var docOwner = elementId.split("_")[2];
			var docType = elementId.split("_")[3];
			
			var docNumberJson = JSON.stringify(docNumber);
			var docOwnerJson = JSON.stringify(docOwner);
			var docTypeJson = JSON.stringify(docType);
		
			jQuery.ajax
			({
				url: downloadDocumentURL,
				type: 'POST',
				dataType: 'json',
				data: {documentNumber: docNumberJson, documentOwner: docOwnerJson, documentType: docTypeJson},
				beforeSend: function ()
				{
					blockUI();
				},
	
				complete: function ()
				{
					unblockUI();		
				}
			});
		});
		*/
		
		jQuery("#documentDownloadSubmit").on("click",function()
		{
			var massiveDocumentsToDownload = jQuery("#massiveDocumentsToDownload").val();
				
			jQuery.ajax
			({
				url: downloadDocumentMassiveURL,
				type: 'POST',
				dataType: 'json',
				data: {massiveDocumentsToDownload: massiveDocumentsToDownload},
				beforeSend: function ()
				{
					blockUI();
				},
			
				complete: function ()
				{
					unblockUI();		
				}
			});
		});
		
		jQuery(".myLinkPODoc").click(function()
		{
			var elementId = jQuery(this).attr("id");
			var orderCode = elementId.split("_")[1];
			var attachmentCode = elementId.split("_")[2];
			
			ACC.neorisDownloadDocuments.PODocumentDownload(orderCode, attachmentCode);
		});	
		
		jQuery(".downloadCheck").on("change",function()
		{
			ACC.neorisDownloadDocuments.updateMassiveDownloadContent(this);
		});
	},
	
	updateMassiveDownloadContent: function(downloadCheckBox)
	{
		var checkId = jQuery(downloadCheckBox).attr("id");
		var checkStatus = jQuery(downloadCheckBox).attr("checked");
		var index = jQuery(downloadCheckBox).attr("index");
		
		// downloadInvoices or DownloadAll
		var checkType = checkId.split("_")[0]
		var invoiceId = checkId.split("_")[1];
		
		var currentSelectedDocs = jQuery(ACC.neorisDownloadDocuments.massiveDocumentsToDownloadFieldId).val();

		if(checkStatus == "checked")
		{
			if(checkType == "downloadAll")
			{
				jQuery("[id*=downloadInvoices_" + invoiceId+"][index="+index+"]").attr("checked", false);
				currentSelectedDocs=currentSelectedDocs.replace(jQuery("[id*=downloadInvoices_" + invoiceId+"][index="+index+"]").val(), "");
			}
			else
			{
				jQuery("[id*=downloadAll_" + invoiceId+"][index="+index+"]").attr("checked", false);
				currentSelectedDocs=currentSelectedDocs.replace(jQuery("[id*=downloadAll_" + invoiceId+"][index="+index+"]").val(), "");
			}
			
			currentSelectedDocs = currentSelectedDocs + "," + jQuery(downloadCheckBox).val();
		}
		else
		{
			currentSelectedDocs=currentSelectedDocs.replace(jQuery(downloadCheckBox).val(), "");
		}
		
		jQuery(ACC.neorisDownloadDocuments.massiveDocumentsToDownloadFieldId).val(currentSelectedDocs);
	},

	showTransferModal: function(documentNumber, documentOwner)
	{
		var buttons = {};
		buttons[ACC.neorisDownloadDocuments.options.transferModalMessages.button1] = function(){
			ACC.neorisDownloadDocuments.transferDocument(documentNumber, documentOwner, "DOWNLOAD");
		};
		buttons[ACC.neorisDownloadDocuments.options.transferModalMessages.button2] = function(){
			ACC.neorisDownloadDocuments.transferDocument(documentNumber, documentOwner, "EMAIL");
		};
		
		ACC.modals.confirmModal(ACC.neorisDownloadDocuments.options.transferModalMessages.title,
			ACC.neorisDownloadDocuments.options.transferModalMessages.content,
			buttons,
			null);
	},
	
	transferDocument: function(documentNumber, documentOwner, transferType)
	{
		jQuery(ACC.neorisDownloadDocuments.docNumberFieldId).val(documentNumber);
		jQuery(ACC.neorisDownloadDocuments.docOwnerFieldId).val(documentOwner);
		jQuery(ACC.neorisDownloadDocuments.transferTypeFieldId).val(transferType);

		jQuery(ACC.neorisDownloadDocuments.transferFormId).submit();
	},
	
	//*Original
	miscDocumentDownload: function(documentNumber, documentOwner, documentType)
	{
		//blockUI();
		jQuery(ACC.neorisDownloadDocuments.miscDocNumberFieldId).val(documentNumber);
		jQuery(ACC.neorisDownloadDocuments.miscDocOwnerFieldId).val(documentOwner);
		jQuery(ACC.neorisDownloadDocuments.miscDocTypeFieldId).val(documentType);
				
		jQuery(ACC.neorisDownloadDocuments.miscDocDownloadFormId).submit();
	},
	
	//miscDocumentDownload: function(documentNumber, documentOwner, documentType)
	/*function miscDocumentDownload(documentNumber, documentOwner, documentType)
	{
		//blockUI();
		//jQuery(ACC.neorisDownloadDocuments.miscDocNumberFieldId).val(documentNumber);
		//jQuery(ACC.neorisDownloadDocuments.miscDocOwnerFieldId).val(documentOwner);
		//jQuery(ACC.neorisDownloadDocuments.miscDocTypeFieldId).val(documentType);
				
		//jQuery(ACC.neorisDownloadDocuments.miscDocDownloadFormId).submit();
		alert("HERE");
		
		 jQuery.ajax
			({
				alert("COMES");
				url: "<c:url value="/my-account/document/downloadDocument.json" />",
				type: 'POST',
				dataType: 'json',
				data: {documentNumber: documentNumber, documentOwner: documentOwner, documentType: documentType},
				beforeSend: function ()
				{
					blockUI();
				},

				complete: function ()
				{
					unblockUI();		
				}
			}) 
			
		});
		 
	},*/
	
	PODocumentDownload: function(orderCode, attachmentCode)
	{
		jQuery(ACC.neorisDownloadDocuments.orderCodeFieldId).val(orderCode);
		jQuery(ACC.neorisDownloadDocuments.orderAttachmentCodeFieldId).val(attachmentCode);		
	
		jQuery(ACC.neorisDownloadDocuments.PODocumentDownloadForm).submit();
	}
}

jQuery(document).ready(function() {
});
