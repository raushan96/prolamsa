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
}

   </script> 
    
    	<script type="text/javascript">
        	$(document).ready(function () {
        	var valorXML = jQuery("#xmlResponse").val(); 
        	
        	$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Workcentre code",  "Block code", "Run code", "Status", "Grade (Esp-Lamina) (225)", "Espesor-Dim-Seccion (556)", "Dim B (215)", "Start date", "End date", 
                    	  "Expected close date", "Total duration", "Free duration", "Free %", "Planned quantity (ft)", "Remaining quantity (ft)", "Completed %",
                    	  "Is frozen?", "Is late?"], 
                           colModel:[
								{name:"WorkcentreCode", width:160, xmlmap:">WorkcentreCode"},
								{name:"BlockCode", width:120, xmlmap:">BlockCode"} ,
								{name:"Code", width:140, xmlmap:">Code", key:true} ,
								{name:"Status", width:190, xmlmap:">Status"} ,
								{name:"GradeEspLamina", width:150, xmlmap:">GradeEspLamina"} ,
								{name:"EspesorDimSecc", width:150, xmlmap:">EspesorDimSecc"} ,
								{name:"DimB", width:300, xmlmap:">DimB"} ,
								{name:"start", width:250, xmlmap:">Start", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	                                   // use it to place a third party control to customize the toolbar
	                                   dataInit: function (element) {
	                                       $(element).datepicker({
	                                           id: 'start_datePicker',
	                                           dateFormat: 'yy-mm-dd',
	                                           maxDate: new Date(2020, 0, 1),
	                                           showOn: 'focus'
	                                       });
	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},
	                             {name:"end", width:250, xmlmap:">End", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	  	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	  	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	  	                                   // use it to place a third party control to customize the toolbar
	  	                                   dataInit: function (element) {
	  	                                       $(element).datepicker({
	  	                                           id: 'end_datePicker',
	  	                                           dateFormat: 'yy-mm-dd',
	  	                                           maxDate: new Date(2020, 0, 1),
	  	                                           showOn: 'focus'
	  	                                       });
	  	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},
	  	                                   
	  	                                 {name:"expected", width:250, xmlmap:">ExpectedCloseDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	  	  	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	  	  	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	  	  	                                   // use it to place a third party control to customize the toolbar
	  	  	                                   dataInit: function (element) {
	  	  	                                       $(element).datepicker({
	  	  	                                           id: 'expected_datePicker',
	  	  	                                           dateFormat: 'yy-mm-dd',
	  	  	                                           maxDate: new Date(2020, 0, 1),
	  	  	                                           showOn: 'focus'
	  	  	                                       });
	  	  	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},           
								
								
								{name:"TotalDuration", width:190, xmlmap:">TotalDuration"} ,
								{name:"FreeDuration", width:190, xmlmap:">FreeDuration"} ,
								{name:"FreeCoveragePercentage", width:150, xmlmap:">FreeCoveragePercentage"} ,
								{name:"PlannedQuantity", width:190, xmlmap:">PlannedQuantity"} ,
								{name:"RemainingQuantity", width:300, xmlmap:">RemainingQuantity"} ,
								{name:"CompletedCoveragePercentage", width:400, xmlmap:">CompletedCoveragePercentage"} ,
								{name:"IsFrozen", width:190, xmlmap:">IsFrozen"} ,
								{name:"IsLate", width:180, xmlmap:">IsLate"}
							 	//{name:"od", width:100, xmlmap:">ODIn", align:"right"},
                           ],
                 xmlReader: {
                     root: "Data",
                     row: "FlattenToRun",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.ReporteCalibres" />',
                 loadonce: true,
  				viewrecords: true,
                  autowidth: true,
                  height: 440,
                  //rowNum:20,
              	 rowTotal: 60,
              	 rowList : [20,40,60,100,150],
                  pager: "#pager",
                  excel: true,
                  ignoreCase: true,
                  rownumbers:true,
                  sortable: true,
          	});
			
			$("#prodsExcel").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Workcentre code",  "Block code", "Run code", "Status", "Grade (Esp-Lamina) (225)", "Espesor-Dim-Seccion (556)", "Dim B (215)", "Start date", "End date", 
                    	  "Expected close date", "Total duration", "Free duration", "Free %", "Planned quantity (ft)", "Remaining quantity (ft)", "Completed %",
                    	  "Is frozen?", "Is late?"], 
                           colModel:[
								{name:"WorkcentreCode", width:160, xmlmap:">WorkcentreCode"},
								{name:"BlockCode", width:120, xmlmap:">BlockCode"} ,
								{name:"Code", width:140, xmlmap:">Code", key:true} ,
								{name:"Status", width:190, xmlmap:">Status"} ,
								{name:"GradeEspLamina", width:150, xmlmap:">GradeEspLamina"} ,
								{name:"EspesorDimSecc", width:150, xmlmap:">EspesorDimSecc"} ,
								{name:"DimB", width:300, xmlmap:">DimB"} ,
								{name:"start", width:250, xmlmap:">Start", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	                                   // use it to place a third party control to customize the toolbar
	                                   dataInit: function (element) {
	                                       $(element).datepicker({
	                                           id: 'start_datePicker',
	                                           dateFormat: 'yy-mm-dd',
	                                           maxDate: new Date(2020, 0, 1),
	                                           showOn: 'focus'
	                                       });
	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},
	                             {name:"end", width:250, xmlmap:">End", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	  	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	  	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	  	                                   // use it to place a third party control to customize the toolbar
	  	                                   dataInit: function (element) {
	  	                                       $(element).datepicker({
	  	                                           id: 'end_datePicker',
	  	                                           dateFormat: 'yy-mm-dd',
	  	                                           maxDate: new Date(2020, 0, 1),
	  	                                           showOn: 'focus'
	  	                                       });
	  	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},
	  	                                   
	  	                                 {name:"expected", width:250, xmlmap:">ExpectedCloseDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
	  	  	                              	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
	  	  	                                   // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
	  	  	                                   // use it to place a third party control to customize the toolbar
	  	  	                                   dataInit: function (element) {
	  	  	                                       $(element).datepicker({
	  	  	                                           id: 'expected_datePicker',
	  	  	                                           dateFormat: 'yy-mm-dd',
	  	  	                                           maxDate: new Date(2020, 0, 1),
	  	  	                                           showOn: 'focus'
	  	  	                                       });
	  	  	                                   },sopt:['eq','ne','le','lt','gt','ge'] }},           
								
								
								{name:"TotalDuration", width:190, xmlmap:">TotalDuration"} ,
								{name:"FreeDuration", width:190, xmlmap:">FreeDuration"} ,
								{name:"FreeCoveragePercentage", width:150, xmlmap:">FreeCoveragePercentage"} ,
								{name:"PlannedQuantity", width:190, xmlmap:">PlannedQuantity"} ,
								{name:"RemainingQuantity", width:300, xmlmap:">RemainingQuantity"} ,
								{name:"CompletedCoveragePercentage", width:400, xmlmap:">CompletedCoveragePercentage"} ,
								{name:"IsFrozen", width:190, xmlmap:">IsFrozen"} ,
								{name:"IsLate", width:180, xmlmap:">IsLate"}
							 	//{name:"od", width:100, xmlmap:">ODIn", align:"right"},
                           ],
                 xmlReader: {
                     root: "Data",
                     row: "FlattenToRun",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.ReporteCalibres" />',
                 loadonce: true,
  				viewrecords: true,
                  autowidth: true,
                  //height: 440,
                  rowNum:-1,
              	 //rowTotal: 60,
              	 //rowList : [20,40,60,100,150],
                  pager: "#pagerExcel",
                  excel: true,
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