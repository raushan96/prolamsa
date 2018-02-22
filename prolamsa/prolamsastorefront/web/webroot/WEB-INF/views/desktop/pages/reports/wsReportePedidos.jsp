<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
	<div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>		
	<div class="grid-container">
		<!-- Once the opstudio resports work on prolamsa mex this code should be removed -->

		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		
		<br>
		<table id="prods"><tr><td/></tr></table>
		<div id="pager"></div>
		
		<table id="prodsExcel" style="display: none; visibility: hidden;" ><tr><td/></tr></table>
		<div id="pagerExcel" style="display: none; visibility: hidden;" ></div>
		
		<textarea style="display: none; visibility: hidden;" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
		<input type="checkbox" id="exportAllExcel"  name="exportAllExcel" size="25" />
		<label for="exportAllExcel"><spring:theme code="wsreport.exportAllExcel.checkout"/></label>
		<br>
		<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
			<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
		</button>
	</div>
</template:page>


<script type="text/javascript">
onDOMLoaded(initXX);

function initXX()
{
	document.getElementById("gbox_prodsExcel").style.display = 'none';
	
	/*
	document.getElementById("gbox_prods").style.width = '1310px'; 
	document.getElementById("gview_prods").style.width = '1310px';
	
	var x = document.getElementsByClassName("ui-jqgrid-hdiv");
	var i;
	for (i = 0; i < x.length; i++) {
		x[i].style.width = '1310px';
	}
	
	var y = document.getElementsByClassName("ui-jqgrid-bdiv");
	var j;
	for (j = 0; j < y.length; j++) {
		y[j].style.width = '1310px';
	}
	
	var z = document.getElementsByClassName("ui-state-default ui-jqgrid-pager ui-corner-bottom");
	var k;
	for (k = 0; k < z.length; k++) {
		z[k].style.width = '1310px';
	}
	*/
	
}

   </script> 
    	<script type="text/javascript">
        	$(document).ready(function () {
        	var valorXML = jQuery("#xmlResponse").val(); 
        	
			$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Order code",  "Status", "Material (269)", "Site", "Grupo de articulos (229)", "Customer code", "Block code", "Description", "Grade (Esp-Lamina) (225)", 
                    	  "Grupo vendedores (242)", "Estrategia (227)", "Espesor (206)", "Quantity", "Completed quantity (Kg)", "Quantity unit", "Kg/pza (226)", "Dim A (214)", "Dim B (215)",
                    	  "Largo FT (224)", "Due date", "Estimated delivery", "Is late?"], 
                           colModel:[
								{name:"OrderCode", width:160, xmlmap:">Code", key:true},
								{name:"Estatus", width:120, xmlmap:">Status"} ,
								{name:"Material", width:140, xmlmap:">Material"} ,
								{name:"Site", width:110, xmlmap:">Site"} ,
								{name:"GrupoDeArticulos", width:190, xmlmap:">GrupoDeArticulos"} ,
								{name:"CustomerCode", width:150, xmlmap:">CustomerCode"} ,
								{name:"BlockCode", width:150, xmlmap:">BlockCode"} ,
								{name:"Description", width:300, xmlmap:">Description"} ,
								{name:"GradeEspLamina", width:190, xmlmap:">GradeEspLamina"} ,
								{name:"GrupoVendedores", width:190, xmlmap:">GrupoVendedores"} ,
								{name:"Estrategia", width:150, xmlmap:">Estrategia"} ,
								{name:"Espesor", width:190, xmlmap:">Espesor"} ,
								{name:"Quantity", width:300, xmlmap:">Quantity"} ,
								{name:"CompletedQuantityResolved", width:400, xmlmap:">CompletedQuantityResolved"} ,
								{name:"QuantityUnit", width:190, xmlmap:">QuantityUnit"} ,
								{name:"KgPza", width:180, xmlmap:">KgPza"} ,
								{name:"DimA", width:180, xmlmap:">DimA"} ,
								{name:"DimB", width:180, xmlmap:">DimB"} ,
								{name:"LargoFT", width:180, xmlmap:">LargoFT"} ,
								{name:"dueDate", width:250, xmlmap:">DueDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	                                   // use it to place a third party control to customize the toolbar
	                                   dataInit: function (element) {
	                                       $(element).datepicker({
	                                           id: 'dueDate_datePicker',
	                                           dateFormat: 'yy-mm-dd',
	                                           maxDate: new Date(2020, 0, 1),
	                                           showOn: 'focus'
	                                       });
	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},
	                            {name:"estimatedDate", width:250, xmlmap:">EstimatedDelivery", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
                                   // use it to place a third party control to customize the toolbar
                                   dataInitX: function (element) {
                                       $(element).datepicker({
                                           id: 'estimatedDate_datePicker',
                                           dateFormat: 'yy-mm-dd',
                                           maxDate: new Date(2020, 0, 1),
                                           showOn: 'focus'
                                       });
                                   },sopt:['eq','ne','le','lt','gt','ge'] }},       
								{name:"IsLate", width:120, xmlmap:">IsLate"}
							 	//{name:"od", width:100, xmlmap:">ODIn", align:"right"},
                           ],
                 xmlReader: {
                     root: "Data",
                     row: "Order",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.ReportePedidos" />',
                 loadonce: true,
  				viewrecords: true,
                  autowidth: true,
                  height: 440,
                  //rowNum:-1,
              	 rowTotal: 60,
              	 rowList : [20,40,60,100,150],
                  pager: "#pager",
				  ignoreCase: true,
                  rownumbers:true,
                  sortable: true,
          	});
			
			$("#prodsExcel").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Order code",  "Status", "Material (269)", "Site", "Grupo de articulos (229)", "Customer code", "Block code", "Description", "Grade (Esp-Lamina) (225)", 
                    	  "Grupo vendedores (242)", "Estrategia (227)", "Espesor (206)", "Quantity", "Completed quantity (Kg)", "Quantity unit", "Kg/pza (226)", "Dim A (214)", "Dim B (215)",
                    	  "Largo FT (224)", "Due date", "Estimated delivery", "Is late?"], 
                           colModel:[
								{name:"OrderCode", width:160, xmlmap:">Code", key:true},
								{name:"Estatus", width:120, xmlmap:">Status"} ,
								{name:"Material", width:140, xmlmap:">Material"} ,
								{name:"Site", width:110, xmlmap:">Site"} ,
								{name:"GrupoDeArticulos", width:190, xmlmap:">GrupoDeArticulos"} ,
								{name:"CustomerCode", width:150, xmlmap:">CustomerCode"} ,
								{name:"BlockCode", width:150, xmlmap:">BlockCode"} ,
								{name:"Description", width:300, xmlmap:">Description"} ,
								{name:"GradeEspLamina", width:190, xmlmap:">GradeEspLamina"} ,
								{name:"GrupoVendedores", width:190, xmlmap:">GrupoVendedores"} ,
								{name:"Estrategia", width:150, xmlmap:">Estrategia"} ,
								{name:"Espesor", width:190, xmlmap:">Espesor"} ,
								{name:"Quantity", width:300, xmlmap:">Quantity"} ,
								{name:"CompletedQuantityResolved", width:400, xmlmap:">CompletedQuantityResolved"} ,
								{name:"QuantityUnit", width:190, xmlmap:">QuantityUnit"} ,
								{name:"KgPza", width:180, xmlmap:">KgPza"} ,
								{name:"DimA", width:180, xmlmap:">DimA"} ,
								{name:"DimB", width:180, xmlmap:">DimB"} ,
								{name:"LargoFT", width:180, xmlmap:">LargoFT"} ,
								{name:"dueDate", width:250, xmlmap:">DueDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	                                   // use it to place a third party control to customize the toolbar
	                                   dataInit: function (element) {
	                                       $(element).datepicker({
	                                           id: 'dueDate_datePicker',
	                                           dateFormat: 'yy-mm-dd',
	                                           maxDate: new Date(2020, 0, 1),
	                                           showOn: 'focus'
	                                       });
	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},
	                            {name:"estimatedDate", width:250, xmlmap:">EstimatedDelivery", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
                                   // use it to place a third party control to customize the toolbar
                                   dataInitX: function (element) {
                                       $(element).datepicker({
                                           id: 'estimatedDate_datePicker',
                                           dateFormat: 'yy-mm-dd',
                                           maxDate: new Date(2020, 0, 1),
                                           showOn: 'focus'
                                       });
                                   },sopt:['eq','ne','le','lt','gt','ge'] }},       
								{name:"IsLate", width:120, xmlmap:">IsLate"}
							 	//{name:"od", width:100, xmlmap:">ODIn", align:"right"},
                           ],
                 xmlReader: {
                     root: "Data",
                     row: "Order",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.ReportePedidos" />',
                 loadonce: true,
  				viewrecords: true,
                  autowidth: true,
                  //height: 440,
                  rowNum:-1,
              	 //rowTotal: 60,
              	 //rowList : [20,40,60,100,150],
                  pager: "#pagerExcel",
				  ignoreCase: true,
                  rownumbers:true,
                  sortable: true,
          	});
          	
         // activate the build in search with multiple option
            $('#prods').navGrid("#pager", {                
                search: true, // show search button on the toolbar
                add: false,
                edit: false,
                del: false,
                refresh: true
            },
            {}, // edit options
            {}, // add options
            {}, // delete options
            { multipleSearch: true } // search options - define multiple search
            );
			
			$('#prods').navButtonAdd("#pager",{	
                caption:"Export to Excel", 
            	onClickButton : function(e)
				{
					if($("#exportAllExcel").attr('checked') )
					{exportData(e, '#prodsExcel');}
				else
				{exportData(e, '#prods');}
					
				}
            });
        	
            //Refresh the screen and reload data from WebService
            $('#refreshpagebutton').click(function () {
            	location.reload(true);
            });
		});
		
		function exportData(e, id){
			var gridid 		= jQuery(id).getDataIDs(); // Get all the ids in array
			var label 		= jQuery(id).getRowData(gridid[0]); // Get First row to get the labels
			var selRowIds 	= jQuery(id).getDataIDs();	

			var obj 		= new Object();
			obj.count		= selRowIds.length;
	
			if(obj.count) {
				obj.items		= new Array();
		
				for(elem in selRowIds) {
					obj.items.push(jQuery(id).getRowData( selRowIds[elem] ));
				}
				
				var json = JSON.stringify(obj);
				JSONToCSVConvertor(json, "csv", 1);
			}
		}
		
		function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {     

			//If JSONData is not an object then JSON.parse will parse the JSON string in an Object
			var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
			var CSV = '';    
			//This condition will generate the Label/Header
			if (ShowLabel) {
				var row = "";

				//This loop will extract the label from 1st index of on array
				for (var index in arrData.items[0]) {
					//Now convert each value to string and comma-seprated
					row += index + ',';
				}
				row = row.slice(0, -1);
				//append Label row with line break
				CSV += row + '\r\n';
			}

			//1st loop is to extract each row
			for (var i = 0; i < arrData.items.length; i++) {
				var row = "";
				//2nd loop will extract each column and convert it in string comma-seprated
				for (var index in arrData.items[i]) {
					row += '"' + arrData.items[i][index].replace(/(<([^>]+)>)/ig, '') + '",';
				}
				row.slice(0, row.length - 1);
				//add a line break after each row
				CSV += row + '\r\n';
			}

			if (CSV == '') {        
				alert("Invalid data");
				return;
			}   

			/*
			 * 
			 * FORCE DOWNLOAD
			 * 
			 */
			
			//this trick will generate a temp "a" tag
			var link = document.createElement("a");    
			link.id="lnkDwnldLnk";

			//this part will append the anchor tag and remove it after automatic click
			document.body.appendChild(link);

			var csv = CSV;  
			blob = new Blob([csv], { type: 'text/csv' }); 
			
			var myURL = window.URL || window.webkitURL;
			
			var csvUrl = myURL.createObjectURL(blob);
			var filename = 'UserExport.csv';
			jQuery("#lnkDwnldLnk")
			.attr({
				'download': filename,
				'href': csvUrl
			}); 

			jQuery('#lnkDwnldLnk')[0].click();    
			document.body.removeChild(link);
		}
   </script>