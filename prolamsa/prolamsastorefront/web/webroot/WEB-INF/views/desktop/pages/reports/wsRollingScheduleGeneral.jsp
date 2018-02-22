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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
		<c:choose>
			<c:when test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
				<div>
					<p style="font-size: 12px;text-transform: uppercase;" align="center"><a href="http://mobile.prolamsa.com/rs/rsGateway.aspx?prtipo=sU9R6f5Wh2Y7nthjfmZ/gA==&prsociedad=Frr1BKziO/c341rGmZ8NIg==" target="_blank"><spring:theme code="cart.page.rolling_schedule"/></a></p>
				</div>
			</c:when>
			<c:when test="${baseStore.uid eq '2000'}">
				<div>
					<p style="font-size: 12px;text-transform: uppercase;" align="center"><a href="http://mobile.prolamsa.com/rs/rsGateway.aspx?prtipo=sU9R6f5Wh2Y7nthjfmZ/gA==&prsociedad=Frr1BKziO/c341rGmZ8NIg==" target="_blank"><spring:theme code="cart.page.rolling_schedule"/></a></p>
				</div>
				
				<div id="globalMessages">
					<common:globalMessages/>
				</div>
		
				<div>
					<p style="font-size: 12px;"><spring:theme code="text.account.rollingScheduleReport.note"/></p>
				</div>
		
				<div class="gantt"></div>
				<textarea style="display:none" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
			
				<sec:authorize ifAllGranted="ROLE_ACCESS_ROLLING_SCHEDULE_REPORT_GRID">
					<table id="prods"><tr><td/></tr></table>
					<div id="pager"></div>
					<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
						<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
					</button>
				</sec:authorize>
			</c:when>	
			<c:otherwise>
				<div id="globalMessages">
					<common:globalMessages/>
				</div>
		
				<div>
					<p style="font-size: 12px;"><spring:theme code="text.account.rollingScheduleReport.note"/></p>
				</div>
		
				<div class="gantt"></div>
				<textarea style="" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
			
				<sec:authorize ifAllGranted="ROLE_ACCESS_ROLLING_SCHEDULE_REPORT_GRID">
					<table id="prods"><tr><td/></tr></table>
					<div id="pager"></div>
					<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
						<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
					</button>
				</sec:authorize>
			</c:otherwise>
		</c:choose>
	</div>
</template:page>

	<script type="text/javascript">
	
		$(document).ready(function() 
		{
			loadMachineBlocksGantReport();
			
			<sec:authorize ifAllGranted="ROLE_ACCESS_ROLLING_SCHEDULE_REPORT_GRID">
				loadMachineBlocksGridReport();
				//Refresh the screen and reload data from WebService
	            $('#refreshpagebutton').click(function () {
	            	location.reload(true);
	            });
            </sec:authorize>
		});
		
		function loadMachineBlocksGantReport()
		{
			var valorXML = jQuery("#xmlResponse").val(); 
			xmlDoc = loadXMLString(valorXML);
		 	var total = xmlDoc.getElementsByTagName('Block').length;
			var j = total-1;
			var k = j - 1;
		 	
		 	 var inicio = '[';
		 	 var fin = ']';
		 	 
		 	 var jsontext = inicio;
		 	 //alert(total);
		 	 for(var i=0;i<=j;i++)
		 	 {
		 		 var status = xmlDoc.getElementsByTagName("Status")[i].childNodes[0].nodeValue;
				 //alert(status +" "+ i);
		 		 
		 		 // only display slots different to completed
		 		 if(status != "Completed")
		 		 {
					//alert(i);
		 			// calculate progress from free coverage percentage
		 			var freeCoveragePercentage = xmlDoc.getElementsByTagName("FreeCoveragePercentage")[i].childNodes[0].nodeValue;
		 			//alert("2");
					var progress = 0;
		 			//alert(i);
		 			if (!isNaN(freeCoveragePercentage))
		 			{
						//alert("4");
		 				progress = 100 - freeCoveragePercentage;
						//alert("5 " +progress);
		 			}
					//alert(i);
		 			//alert("6");
		 			// create json object node for each slot
					
					//alert("Aqui viene OD");
					//alert(xmlDoc.getElementsByTagName("Code")[33].childNodes[0].nodeValue);
					//alert(xmlDoc.getElementsByTagName("Start")[33].childNodes[0].nodeValue);
					//alert(xmlDoc.getElementsByTagName("End")[33].childNodes[0].nodeValue);
					//alert(xmlDoc.getElementsByTagName("OD")[33].childNodes[0].nodeValue);
					
			 		jsontext += '{' + 					
		 							'"name":"' + xmlDoc.getElementsByTagName("Code")[i].childNodes[0].nodeValue + '",' + 
		 							'"values":[{' + 
		 								'"from":"' + xmlDoc.getElementsByTagName("Start")[i].childNodes[0].nodeValue + '",' + 
		 								'"to":"' + xmlDoc.getElementsByTagName("End")[i].childNodes[0].nodeValue + '",' + 
		 								'"label":"' + xmlDoc.getElementsByTagName("Code")[i].childNodes[0].nodeValue + '",' + 
		 								'"progress":"'+ progress + '",' + 
		 								'"desc":"' + progress + '%",' + 
		 								'"blockCode":"' + xmlDoc.getElementsByTagName("Code")[i].childNodes[0].nodeValue + '",' + 
		 								'"od":"' + xmlDoc.getElementsByTagName("OD")[i].childNodes[0].nodeValue + '"' +
		 								'}]}';
					//alert(jsontext);
					
			 		if(i<j)
			 		{
						
						//alert("i " +i);
						//alert("j " +j);
						//alert(jsontext);
						jsontext += ",";
						//alert(jsontext);
			 		}
					//alert(jsontext);
					//alert("8");
		 		}
		 	 }
		 	 //alert("9");
		 	 jsontext += fin;
		 	 //alert(jsontext);
		 	 var sour = JSON.parse(jsontext);
		 	
		 	$(".gantt").gantt({
		 	     source: sour,
		 	     itemsPerPage: 5,
		 	     scale: "weeks",
		 	     minScale: "hours",
		 	     maxScale: "weeks"
		 	});
		}
		
		function loadMachineBlocksGridReport()
		{			
			var valorXML = jQuery("#xmlResponse").val();
			
			$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Slot Code",  "Start Date", "End Date", "Duration", "Section", "OD", "Planned Qty","Status", "Booked Qty","Booked Duration", "Free Qty", "Free Duration"], 
                 colModel:[
                     {name:"slotCode", width:50, xmlmap:">SlotCode"} ,
                     /* {name:"startDate", width:80, xmlmap:">Start"}, */
                     {name:"startDate", width:80, xmlmap:">Start", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date', align:"center",
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
                     /* {name:"endDate", width:80, xmlmap:">End"}, */
                     {name:"endDate", width:80, xmlmap:">End", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date', align:"center",
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
                     {name:"Duration", width:100, xmlmap:">UnadjustedDuration", formatter:xsdDurationFormatter},
                     {name:"Section", width:50, xmlmap:">Cycle"},
                     {name:"od", width:50, xmlmap:">OD"},
                     {name:"plannedQtyPounds", width:80, xmlmap:">PlannedQtyPounds", align:"right"},
                     {name:"status", width:80, xmlmap:">Status", align:"right"},
                     {name:"bookedQty", width:80, xmlmap:">BookedQtyPounds"},
                     {name:"BookedDuration", width:100, xmlmap:">BookedDuration", formatter:xsdDurationFormatter},
                     {name:"freeQty", width:80, xmlmap:">FreeQtyPounds"},
                     {name:"freeDuration", width:100, xmlmap:">FreeDuration", formatter:xsdDurationFormatter}
                 ],
                 xmlReader: {
                     root: "Data",
                     row: "Block",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.rollingScheduleReport" />',
                  	 loadonce: true,
     				viewrecords: true,
     				autowidth: true,
                     height: 440,
                     rowNum:20,
                 	 rowTotal: 60,
                 	 rowList : [20,40,60],
                     pager: "#pager",
                     ignoreCase: true,
                     rownumbers:true,
                     sortable: true
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
		}
		
		function loadDataForOrderGridReport(blockCode)
		{	
			jQuery.ajax
			({  
				url: "<c:url value="/reports/wsOrderReportWithParams.json"/>",
				type: 'POST',
				dataType: 'json',
				data: {'blockCode': blockCode},
				beforeSend: function ()
				{
					blockUI();
				},
				success: function (data)
				{
					if(data.status == 0)
					{
						loadOrderGridReport(data.result);
					}
					else
					{
						ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
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
		
		function loadOrderGridReport(data)
		{
			$('#prods').GridUnload('#prods');
			
			var valorXML = data;
			
			$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Order Code", "Sales Group" , "Sales Rep", "Customer", "Product Code", "Due Date", "OD", "Net Tons", "Completed Qty", "Is Late", "Lateness Days", "Status"], 
                 colModel:[
                     {name:"orderCode", width:150,  xmlmap:">Code", key:true},
                     {name:"salesGroup", width:150, xmlmap:">SalesGroup"},
                     {name:"salesRep", width:150, xmlmap:">SalesRep"},
                     {name:"customer", width:150, xmlmap:">Customer"},
                     {name:"productCode", width:100, xmlmap:">Product"},
                     {name:"dueDate", width:100, xmlmap:">DueDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date', align:"center",
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
                     {name:"odIn", width:100, xmlmap:">ODIn", align:"right"},
                     {name:"netTons", width:100, xmlmap:">QuantityInDefaultUnits", align:"right"},
                     {name:"completedQty", width:100, xmlmap:">CompletedQtyFt", align:"right"},
                     {name:"isLate", width:100, xmlmap:">IsLate"},
                     {name:"latenessDays", width:100, xmlmap:">LatenessDays", align:"right"},
                     {name:"status", width:100, xmlmap:">Status",  align:"right"}
                 ],
                 xmlReader: {
                     root: "Data",
                     row: "Order",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.rollingScheduleReport.order" />',
                  	 loadonce: true,
     				viewrecords: true,
     				autowidth: true,
                     height: 440,
                     rowNum:20,
                 	 rowTotal: 60,
                 	 rowList : [20,40,60],
                     pager: "#pager",
                     ignoreCase: true,
                     rownumbers:true,
                     sortable: true,
                     //scroll:true,
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
		}
		
		function loadDataForProductGridReport(odIn)
		{
			jQuery.ajax
			({  
				url: "<c:url value="/reports/wsProductReportWithParams.json"/>",
				type: 'POST',
				dataType: 'json',
				data: {'odIn' : odIn},
				beforeSend: function ()
				{
					blockUI();
				},
				success: function (data)
				{
					if(data.status == 0)
					{
						loadProductGridReport(data.result);
					}
					else
					{
						ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
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
		
		function loadProductGridReport(data)
		{
			$('#prods').GridUnload('#prods');
			
			var valorXML = data;
			
			$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Product Code", "Product Desc", "Section", "OD", "Wallthickness", "Grade", "Actual Cvrg(days)", "Exp Cvrg(days) ", "Exp Coils Avail(Ton)"], 
                 colModel:[
                     {name:"productCode", width:80, xmlmap:">Code"},
                     {name:"productDescription", width:150, xmlmap:">Description"},
                     {name:"section", width:50, xmlmap:">Section"},
                     {name:"od", width:50, xmlmap:">ODIn"},
                     {name:"wall", width:50, xmlmap:">WallThicknessIn"},
                     {name:"grade", width:50, xmlmap:">Grade"},
                     {name:"actualCvrg", width:70, xmlmap:">ActualCoverageDays"},
                     {name:"expectedCvrg", width:70, xmlmap:">ExpectedCoverageDays"},
               		 {name:"expectedCoilsAvailableT", width:100, xmlmap:">ExpectedCoilsAvailableT", align:"right"},
                 ],
                 xmlReader: {
                     root: "Data",
                     row: "Product",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.rollingScheduleReport.product" />',
                  	 loadonce: true,
     				viewrecords: true,
     				autowidth: true,
                     height: 440,
                     rowNum:20,
                 	 rowTotal: 60,
                 	 rowList : [20,40,60],
                     pager: "#pager",
                     ignoreCase: true,
                     rownumbers:true,
                     sortable: true
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
		}
			
		
		function loadXMLString(txt) 
		{
			if (window.DOMParser) 
			{
				parser = new DOMParser();
				xmlDoc = parser.parseFromString(txt, "text/xml");
			} 
			else // code for IE
			{
				xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = false;
				xmlDoc.loadXML(txt);
			}
			return xmlDoc;
		}
		
		function clickEnProgreso(param)
		{
			<sec:authorize ifAllGranted="ROLE_ACCESS_ROLLING_SCHEDULE_REPORT_GRID">
				if($(param).attr('id') == 'barra')
				{
					loadDataForProductGridReport($(param).attr('od'));
				}
				else
				{
					loadDataForOrderGridReport($(param).attr('blockCode'));
					
					//Esto sirve para que no tome el clickEnProgreso del param 'barra'
					var e = window.event || arguments.callee.caller.arguments[0];
					e.cancelBubble = true;
					e.returnValue = false;
					
					if (e.stopPropagation)
					{
						e.stopPropagation();
					}
					
					if (e.preventDefault)
					{
						e.preventDefault();
					} 
				}
			</sec:authorize>
		}
	</script>