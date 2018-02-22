<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<c:set var="addIncidentBaseUrl" value="/incident/addIncident" />
<spring:url var="addIncidentUrl" value="${addIncidentBaseUrl}" />

<c:set var="validateInvoiceBaseUrl" value="/incident/validateInvoice" />
<spring:url var="validateInvoiceUrl" value="${validateInvoiceBaseUrl}" />


<div class="item_container_holder" style="display: block;">

	<form:form  enctype="multipart/form-data" id="addIncidentForm" class="js-form" action="${addIncidentUrl}" method="post">
				
		<div>
		    <table style="table-layout: fixed;">
		    <tbody>
		  		<tr>
				     <td>
                       <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 25/08/2014 Se agregan las nuevas etiquetas en los archivos de propiedades -->
				       <spring:theme code="incident.list.customer" />*
				    </td>
				    <td>
				       <select id="customer" name="customer">
						  <option value=""><spring:theme code="incident.list.selectCustomer" /></option> 
							<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
							    <c:choose>
									<c:when test="${eachFormattedB2BUnit.uid eq addIncidentForm.customer}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
						  </c:forEach>
						</select>
				    </td>
				    	
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
				</tr>
				<tr>
				    <td>
				       <spring:theme code="incident.list.type" />*
				    </td>				      
				    <td>
				    	<select id="type" name="type">
						  <option value=""><spring:theme code="incident.add.selectType" /></option> 
							<c:forEach items="${incidentTypes}" var="eachType">
							    <c:choose>
									<c:when test="${addIncidentForm.type eq eachType.code}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachType.code}" ${selected} >${eachType.name}</option>
						  </c:forEach>
						</select>
				   
				    </td>	
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>	
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.country" />
				    </td>
				    <td>
				       <input type="hidden" id="country" name="country" value="${addIncidentForm.country }" size="15">
				       <span id="countrySpan" ></span>
				    </td>
				    <td>
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			       <tr>
			    	<td>
				       <spring:theme code="incident.add.location" />
				    </td>
				    <td>
				       <input type="hidden" id="productLocation" name="productLocation" value="${addIncidentForm.productLocation}" size="15">
				       <span id="productLocationSpan" ></span>
				    </td>
				    <td>
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.invoice" />*
				    </td>
				    <td>
				       <input type="text" id="invoice" name="invoice" value="${addIncidentForm.invoice}" size="15">
				       <formUtil:formButton
					id="searchInvoice"
					name="searchInvoice"
					type="button" 
					css="positive button-float-right"
					tabindex="106"
					springThemeText="incident.add.search" />
				    </td>
				    <td>
				    <span id="previosReportsSpan" style="display:none;"><spring:theme code="incident.add.invoice.hasIncidentReports" /></span>
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.customerName" />
				    </td>
				    <td>
				       <span id="customerName">${addIncidentForm.customerName}</span>
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.detail.shipTo" />
				    </td>
				    <td>
				    	<input type="hidden" value="${addIncidentForm.shipTo}" name="shipTo" id="shipTo"/>
				      	<span id="shipToSpan" >${addIncidentForm.shipTo}</span>
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.incoterm" />
				    </td>
				    <td>
				       <input type="hidden" id="incoterm" name="incoterm" value="${addIncidentForm.incoterm}" size="15">
				       <span id="incotermSpan" ></span>
				    </td>
				    <td>
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.detail.customerNumber" />
				    </td>
				    <td>
				       <input type="text" id="customerIncidentNumber" name="customerIncidentNumber" value="${addIncidentForm.customerIncidentNumber}" size="30">
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.detail.reportedBy" />*
				    </td>
				    <td>
				      <input type="text" id="contactName" name="contactName" value="${addIncidentForm.contactName}" size="30">
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.detail.alternateReportedBy" />
				    </td>
				    <td>
				       <input type="text" id="alternateContactName" name="alternateContactName" value="${addIncidentForm.alternateContactName}" size="30">
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.detail.phone" />*
				    </td>
				    <td>
						<input type="text" id="phone" name="phone" value="${addIncidentForm.phone}" size="30">
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="incident.detail.email" />*
				    </td>
				    <td>
				    	<input type="text" id="email" name="email" value="${addIncidentForm.email}" size="30"> 
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>		
			     <tr>
			    	<td>
				       <spring:theme code="incident.detail.description" />*
				    </td>
				    <td>
				       <textarea id="description"  name="description"  >${addIncidentForm.description}</textarea>
				    </td>
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
			    </tr>
			    <tr>
			    	<td colspan="4">
			    		<br/>
			    	</td>
			    </tr>
			    <tr>
			    	<td colspan="4">
			    		<spring:theme code="incident.add.attachments.message" />
			    		<br/><br/>
			    	</td>
			    </tr>	
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.file1" />
				    </td>
				    <td>
				       <input type="file" name="atach1" id="atach1" value="${addIncidentForm.atach1}" />
				    </td>
			    </tr>
			    <tr>
			    	<td>
				    	<spring:theme code="incident.add.fileDescription" />
				    </td>
				    <td>
				    	<input type="text" id="atach1Description" name="atach1Description" value="${addIncidentForm.atach1Description}" size="30"> 
				    </td>
			    </tr>	
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.file2" />
				    </td>
				    <td>
				       <input type="file" name="atach2" id="atach2" value="${addIncidentForm.atach2}">
				    </td>
			    </tr>
			    <tr>
			    	<td>
				    	<spring:theme code="incident.add.fileDescription" />
				    </td>
				    <td>
				   		<input type="text" id="atach2Description" name="atach2Description" value="${addIncidentForm.atach2Description}" size="30"> 
				    </td>
			    </tr>		
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.file3" />
				    </td>
				    <td>
				       <input type="file" name="atach3" id="atach3" value="${addIncidentForm.atach3}">
				    </td>
			    </tr>
			    <tr>
			    	<td>
				    	<spring:theme code="incident.add.fileDescription" />
				    </td>
				    <td>
				    	<input type="text" id="atach3Description" name="atach3Description" value="${addIncidentForm.atach3Description}" size="30"> 
				    </td>
			    </tr>		
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.file4" />
				    </td>
				    <td>
				       <input type="file" name="atach4" id="atach4" value="${addIncidentForm.atach4}">
				    </td>
			    </tr>
			    <tr>
			    	<td>
				    	<spring:theme code="incident.add.fileDescription" />
				    </td>
				    <td>
				    	<input type="text" id="atach4Description" name="atach4Description" value="${addIncidentForm.atach4Description}" size="30"> 
				    </td>
			    </tr>		
			    <tr>
			    	<td>
				       <spring:theme code="incident.add.file5" />
				    </td>
				    <td>
				       <input type="file" name="atach5" id="atach5" value="${addIncidentForm.atach5}">
				    </td>
			    </tr>			
			   	<tr>
			   		<td>
				    	<spring:theme code="incident.add.fileDescription" />
				    </td>
				    <td>
				    	<input type="text" id="atach5Description" name="atach5Description" value="${addIncidentForm.atach5Description}" size="30"> 
				    </td>
				 </tr>
			     </tbody>
		    </table>
	
			<div class="search-form-options">
				<button style="opacity: 0.3;" class="button positive button-float-right" id="addIncidentFormSubmit" value="<spring:theme code='incident.add.create'/>" disabled="disabled" onclick="ga('send', 'event', 'CreateIncident', 'createIncident');"><spring:theme code='incident.add.create'/></button>
			</div>															
	
		</div>
	 <textarea style="display:none;" id="incidentLines"  name="incidentLines">${addIncidentForm.incidentLines}</textarea>
	<input   type="hidden" id="sortField" name="sort" value="" />
	</form:form>
	
	<!-- NEORIS_CHANGE #Incidencia 190 JCVM 28/08/2014 Se agrega un salto de linea para que no aparezca encimada la paginación. -->
   
</div>

<script type="text/javascript">
 var exectValidationInvoiceNumber= false;
 var incidentLines = "";
<c:if test="${addIncidentForm.invoice ne '' and addIncidentForm.customer ne ''}">
	exectValidationInvoiceNumber= true;
	incidentLines = '<c:out escapeXml="true" value="${addIncidentForm.incidentLines}" />'; 
</c:if>



onDOMLoaded(initOrderList);

function initOrderList()
{	
	
	jQuery("#invoice").on('change',clearIncidentForm);
	jQuery("#addIncidentFormSubmit").click(submitForm);
	jQuery("#customer").on("change",clearIncidentForm);
	
	jQuery("#searchInvoice").on("click",invoiceSearchClicked);
	
	jQuery("#incidentLinesBodyTable").on("blur",".quantityInput",validateQuantity);
	
	jQuery("input:file").on("change",validateFileExtension);
	
	if(exectValidationInvoiceNumber == true)
	{
		validateInvoiceNumber();
		
		
	}
	
}

	function invoiceSearchClicked()
	{
		incidentLines='';
		jQuery("#incidentLines").val('');
		validateInvoiceNumber();
	}
	
	function clearIncidentForm()
	{
		jQuery("#incidentLinesBodyTable").empty();
		jQuery("#previosReportsSpan").hide();
		jQuery("#customerName").html("");
		jQuery("#shipTo").val("");
		jQuery("#shipToSpan").html("");
		jQuery("#addIncidentFormSubmit").css('opacity',0.3);
		jQuery("#addIncidentFormSubmit").prop('disabled',true);
		jQuery("#productLocation").val("");
		jQuery("#productLocationSpan").html("");
		jQuery("#country").val("");
		jQuery("#countrySpan").html("");
		jQuery("#incoterm").val("");
		jQuery("#incotermSpan").html("");
		jQuery("#incidentLinesBodyTable").empty();
		
	}

	function validateFileExtension()
	{
		if(jQuery(this).val() != "")
		{
			// validate file name
			var invalidCharsReg = new RegExp('~|"|#|%|&|\\*|:|<|>|\\?|/|\\\\|{|\\||}|\\.');
			// since file name has the form C:\fakepath\nombrearchivo.extension
			var wholeFileName = jQuery(this).val().split('C:\\fakepath\\')[1];
			
			//13Ene2017 CILS Se agregó el siguiente IF ya que en ocasiones no se toma el fakepath
			if(wholeFileName == "" || wholeFileName == null || wholeFileName == 'undefined')
			{
				wholeFileName = jQuery(this).val();
			}
			
			// getting file name without extension
			var fileNameNoExtensionParts = wholeFileName.split(".");
			var fileName = "";
			if(fileNameNoExtensionParts.length == 2){
				fileName = fileNameNoExtensionParts[0];
			}
			else if(fileNameNoExtensionParts.length > 2){
				var extension = "." + fileNameNoExtensionParts[fileNameNoExtensionParts.length-1];
				fileName = wholeFileName.replace(extension, "");
			}
			else{
				return;
			}
			
			//13Ene2017 CILS, Se modificó el regExp para que solo sea alfanumerico.
			//if(invalidCharsReg.test(fileName))
			
			//if(!fileName.match("^[a-zA-Z0-9]*$"))
			//{
			//	ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.fileName" />');
			//	jQuery(this).val("");
			//	return;
			//}
			
			// validate file extension
			var ext = jQuery(this).val().split('.').pop().toLowerCase(); 
			if(jQuery.inArray(ext, ['xlsx', 'docx', 'pptx', 'pdf', 'ppt', 'doc', 'xls', 'txt', 'png', 'gif', 'bmp', 'jpg', 'jpeg', 'zip', 'rar']) == -1) {
				ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.fileType" />');
				jQuery(this).val("");
			}
		}
	}

	function submitForm()
	{
		var incidentLines= [];
		
		var allRows = jQuery("#incidentLinesBodyTable .quantityInput");
		
		$.each( allRows, function( key, value ) {
			
			var eachIncidentLine = {};
			
			var jqInput = jQuery(value);
			
			
			
			var quantityToClaim = jQuery.isNumeric(jqInput.val())?jqInput.val():0;
			
			if(quantityToClaim >0)
			{
				eachIncidentLine.plant=jqInput.attr("plant");
				eachIncidentLine.salesUnit=jqInput.attr("salesUnit");
				eachIncidentLine.quantity=jqInput.attr("quantity");
				eachIncidentLine.weightUnit=jqInput.attr("weightUnit");
				eachIncidentLine.netweight=jqInput.attr("netweight");
				eachIncidentLine.batch=jqInput.attr("batch");
				eachIncidentLine.sorder_p=jqInput.attr("sorder_p");
				eachIncidentLine.sorder=jqInput.attr("sorder");
				eachIncidentLine.product=jqInput.attr("product");
				eachIncidentLine.invoice=jqInput.attr("invoice");
				eachIncidentLine.invoice_p=jqInput.attr("invoice_p");
				eachIncidentLine.shipTo=jqInput.attr("shipto");
				eachIncidentLine.quantityToClaim=quantityToClaim;
				eachIncidentLine.productDescription=jqInput.attr("productDescription");
				incidentLines.push(eachIncidentLine);
			
			}
			
		});
		
		if(incidentLines.length >0)
			jQuery("#incidentLines").val(JSON.stringify(incidentLines));
		
		blockUI();
		jQuery("#addIncidentForm").submit();
	}
	
	function validateQuantity()
	{
		if (jQuery(this).val()== '')
			return;
		var quantityToClaim = 0;
		var totalWeight = parseFloat(jQuery(this).attr("netweight"));
		var quantityLimit = parseInt(jQuery(this).attr("quantity"));
		var weightUnit = jQuery(this).attr("weightunit");
		var index = jQuery(this).attr("index");
		
		if(jQuery.isNumeric(jQuery(this).val()) == false || jQuery(this).val().indexOf(".") >= 0 || jQuery(this).val().indexOf(",") >= 0)
		{
			ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.validQty" />'+' '+quantityLimit);
			jQuery(this).val(0);
			return;
		}
		
		quantityToClaim = parseInt(jQuery(this).val());
		
		if(quantityToClaim > quantityLimit)
		{
			ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.validQty" />'+' '+quantityLimit);
			jQuery(this).val(0);
			return;
		}
		
		var weightToClaim = totalWeight/quantityLimit*quantityToClaim;
		
		jQuery("#weightToClaim"+index).html(formatWeight(weightToClaim)+' '+weightUnit);
		
	}

	function validateInvoiceNumber()
	{
		var invoiceNumber = jQuery("#invoice").val();
		var customerId = jQuery("#customer").val();
		
		if (invoiceNumber == '')
		{
			return;			
		}
		else
		{
			var regOnlyDigits = new RegExp('^[0-9]+$');
			
			if(!regOnlyDigits.test(invoiceNumber))
			{
				ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.invoice.invalid" />');
				return;	
			}
		}
		
		if(customerId == '')
		{
			ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.customer.invalid" />');
			return;
		}
		jQuery.ajax
		({
			url: '<c:out value="${validateInvoiceUrl}" />',
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: {invoiceNumber:invoiceNumber,customer: customerId},
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (data)
			{
				if(data == null)
				{
					clearIncidentForm();
					ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.invoice.unexpectedError" />');
					return;
				}
				
				// assign cart product location after success cart modification
				if(data.message == null || data.message == undefined)
				{
					jQuery("#customerName").html(data.invoiceLines[0].customerName);
				
					jQuery("#shipTo").val(data.invoiceLines[0].shipToDescription)
					jQuery("#shipToSpan").html(data.invoiceLines[0].shipToDescription)
					jQuery("#productLocation").val(data.locationCode);
					jQuery("#productLocationSpan").html(data.location);
					jQuery("#country").val(data.country);
					jQuery("#countrySpan").html(data.countryDisplay);
					jQuery("#incoterm").val(data.incoterm);
					jQuery("#incotermSpan").html(data.incotermDisplay);
					jQuery("#incidentLinesBodyTable").empty();
					
					$.each( data.invoiceLines, function( key, value ) {
					
						var eachRow = jQuery('<tr>');
						
						eachRow.append(jQuery('<td class="Text_Table_Align_Center">'+value.sorder+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Center">'+value.sorder_p+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Center">'+value.batch+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Center">'+value.product+'</td>'));
						eachRow.append(jQuery('<td>'+value.productDescription+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Right">'+value.quantity+' '+ value.salesUnit+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Right" width="10%">'+formatWeight(value.netweight)+' '+ value.weightUnit+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Right"><input productdescription="'+value.productDescription+'" align="right" size="5" index="'+key+'"class="quantityInput" shipTo="'+value.shipTo+'" plant="'+value.plant+'"salesUnit="'+value.salesUnit+'"quantity="'+value.quantity+'"weightUnit="'+value.weightUnit+'"netweight="'+value.netweight+'"  batch="'+value.batch+'" sorder_p="'+value.sorder_p+'" sorder="'+value.sorder+'" product="'+value.product+'" invoice="'+ value.invoice+ '" invoice_p="'+ value.invoice_p+'" type="text"> '+value.salesUnit+'</td>'));
						eachRow.append(jQuery('<td class="Text_Table_Align_Right" id="weightToClaim'+key+'"></td>'));
						
						
						jQuery("#incidentLinesBodyTable").append(eachRow);
						
						if(data.hasIncidentReports == true)
						{
							jQuery("#previosReportsSpan").show();
						}
						else
						{
							jQuery("#previosReportsSpan").hide();
						}
						
						jQuery("#addIncidentFormSubmit").prop("disabled",false);
						jQuery("#addIncidentFormSubmit").css("opacity",1);
						  
					});
					
					
					if (incidentLines != '')
					{
						var lines = jQuery.parseJSON( jQuery("#incidentLines").val());
						$.each( lines, function( key, value ) 
						{
							var eachInput = jQuery( "input[batch='"+value.batch+"'][product='"+value.product+"']" );
							eachInput.val(value.quantityToClaim);
							eachInput.blur();
						});
					
					}
					
					
				}else
				{
					//TO-DO: Send appropiate messages.
					jQuery("#invoice").val("");
					ACC.modals.messageModal(data.message);
					
					jQuery("#addIncidentFormSubmit").prop("disabled",true);
					jQuery("#addIncidentFormSubmit").css("opacity",0.3);
					
					if(data.message == "No Existe")
					{
						ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.invoiceNotExits" />');
					}else
					{
						ACC.modals.messageModal('<spring:theme code="incident.add.error" />', '<spring:theme code="incident.add.error.cancelledInvoice" />');
					}
					clearIncidentForm();
					
				}
				
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
	function formatWeight(string)
	{
		var formattedString = string.toFixed(3);
		
		formattedString += '';
		x = formattedString.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		return x1 + x2;
	 	
	}


</script>