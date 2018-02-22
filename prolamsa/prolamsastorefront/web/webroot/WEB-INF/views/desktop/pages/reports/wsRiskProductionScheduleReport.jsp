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
		
		<br>
		<table id="prods"><tr><td/></tr></table>
		<div id="pager"></div>
		<textarea style="display: none; visibility: hidden;" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
		<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
			<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
		</button>
			</c:when>
			<c:otherwise>
		
		
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		
		<br>
		<table id="prods"><tr><td/></tr></table>
		<div id="pager"></div>
		<textarea style="display: none; visibility: hidden;" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
		<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
			<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
		</button>
		</c:otherwise>
		</c:choose>
	</div>
	
</template:page>

<style>

</style>

<script type="text/javascript">
    
        	jQuery(document).ready(function () {
        	var valorXML = jQuery("#xmlResponse").val(); 

          	$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Sales Group",  "Sales Rep", "Order Code", "Customer", "Product Code", "Product Desc", "Due Date", "Lateness", "Customer Promise","OD", "Net Tons", 
                  		"Qty (Pieces)", "Completed Quantity", "Status", "Is Late"], 
                  		colModel:[
                                  {name:"salesGroup", width:250, xmlmap:">SalesGroup"} ,
                                  {name:"salesRep", width:200, xmlmap:">SalesRep"},
                                  {name:"OrderCode", width:200,  xmlmap:">Code", key:true},
                                  {name:"Customer", width:200, xmlmap:">Customer"},
                                  {name:"productCode", width:200, xmlmap:">Product"},
                                  {name:"productDescription", width:200, xmlmap:">MaterialDescription"},
                                  {name:"dueDate", width:200, xmlmap:">DueDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
                                 	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
                                      // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
                                      // use it to place a third party control to customize the toolbar
                                      dataInit: function (element) {
                                          $(element).datepicker({
                                              id: 'dueDate_datePicker',
                                              dateFormat: 'yy-mm-dd',
                                              //minDate: new Date(2010, 0, 1),
                                              maxDate: new Date(2020, 0, 1),
                                              showOn: 'focus'
                                          });
                                      },sopt:['eq','ne','le','lt','gt','ge'] }},
                                      {name:"lateness", width:150, xmlmap:">LatenessDays"} ,   
                                      {name:"customerPromise", width:280, xmlmap:">EstimatedDelivery", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
                                     	 formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, datefmt: 'Y-m-d', searchoptions: {
                                          // dataInit is the client-side event that fires upon initializing the toolbar search field for a column
                                          // use it to place a third party control to customize the toolbar
                                          dataInit: function (element) {
                                              $(element).datepicker({
                                                  id: 'customerPromise_datePicker',
                                                  dateFormat: 'yy-mm-dd',
                                                  //minDate: new Date(2010, 0, 1),
                                                  maxDate: new Date(2020, 0, 1),
                                                  showOn: 'focus'
                                              });
                                          },sopt:['eq','ne','le','lt','gt','ge'] }},    
                                  {name:"od", width:100, xmlmap:">ODIn", align:"right"},
                                  {name:"netTons", width:100, xmlmap:"QuantityInDefaultUnits"},
                                  {name:"quantity", width:100, xmlmap:">OrderQuantityPieces"},
                                  {name:"completedQty", width:100, xmlmap:">CompletedQuantityResolved"},
                                  {name:"status", width:100, xmlmap:">Status"},
                                  {name:"isLate", width:100, xmlmap:">IsLate"}
                              ],
                 xmlReader: {
                     root: "Data",
                     row: "Order",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.orderReportRisk" />',
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
                     
                     afterInsertRow:function(row_id, rowdata) 
                     { 
                    	 /* colorizing row based on the following criteria:
 							 -	Green: IsLate=false
                    		 -	Red: IsLate=true and DueDate<Today
							 -	Yellow: IsLate=true and DueDate>Today
                    	 */	 
                    		 
                    	 var nowDate = moment();
                    	 var dueDate = moment(rowdata.dueDate);
                    	                     	 
                    	 if (rowdata.isLate == "false")
                    	 {
                    		 $("#prods").jqGrid('setRowData', row_id,  false, 'rowGreen');
                    	 }
                    	 else if (rowdata.isLate == "true" && dueDate.isValid() && !dueDate.isAfter(nowDate))
                    	 {
                    		 $("#prods").jqGrid('setRowData', row_id,  false, 'rowRed');
                    	 }
                    	 else if (rowdata.isLate == "true" && dueDate.isValid() && dueDate.isAfter(nowDate))
                    	 {
                    		 $("#prods").jqGrid('setRowData', row_id,  false, 'rowYellow');
                    	 }
                     },
                     ////////////////////////////////////////
                     
                     //////////////SE AGREGA VISTA DE SUBGRID CON CLICK EN LINEA//////////////////////////////////////////////
                    onSelectRow: function (row_id) {
                $("#prods").toggleSubGridRow(row_id);
            },
                     subGrid: true,
                     subGridRowExpanded: function(grid_id, row_id) {
                    	 //alert("1 " +grid_id);
                    	 //alert("2 " +row_id);
                     var subgrid_table_id = grid_id + "_t";
                     //alert(subgrid_table_id);
                     $("#" + grid_id).html("<table id='" + subgrid_table_id + "''></table>");
                     $("#" + subgrid_table_id).jqGrid( {
                    	 colNames: ['Operation Code', 'Workcenter', 'Start Date', 'End Date', 'Status', 'Completed Qty', 'Remaining Qty'],
                         datatype:'xmlstring',
                         datastr: valorXML,
                         colModel: [
                                    {name:"OperationNumber", index:"OperationNumber", width:80, xmlmap:"OperationNumber"},
                                    {name:"WorkcentreProcessed", index:"WorkcentreProcessed", width:80, xmlmap:"WorkcentreProcessed"},
                                    {name:"ScheduleStart", index:"ScheduleStart", width:80, xmlmap:"ScheduleStart", cellsformat: 'yy-mm-dd', formatter: 'date', formatoptions: {srcformat: 'Y-m-dTH:i:s', newformat: 'Y-m-d H:i:s'}, datefmt: 'Y-m-d'},
                                    {name:"ScheduleEnd", index:"ScheduleEnd", width:80, xmlmap:"ScheduleEnd", cellsformat: 'yy-mm-dd', formatter: 'date', formatoptions: {srcformat: 'Y-m-dTH:i:s', newformat: 'Y-m-d H:i:s'}, datefmt: 'Y-m-d'},
                                    {name:"Status", index:"Status", width:80, xmlmap:"Status"},
                                    {name:"CompletedQtyPounds", index:"CompletedQtyPounds", width:80, xmlmap:"CompletedQtyPounds"},
                                    {name:"CompletedQtyFt", index:"CompletedQtyFt", width:80, xmlmap:"CompletedQtyFt"}
                                
                                ],
                         xmlReader: {
                             root: "Data>Order:has('Code:contains('"+row_id+"')')",
                             row: ">Task",
                             repeatitems: false
                         },
                         gridview:true,
                         height: "100%",
                         rowNum:1000,
                         autowidth:true
                     });
                 }
                     ////////////END ADD SUBGRID////////////////////

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
        	
            //Refresh the screen and reload data from WebService
            $('#refreshpagebutton').click(function () {
            	location.reload(true);
            });
        	
        });
    </script>
