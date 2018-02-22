<!DOCTYPE html>
<html lang="en">
	<head>
	    <title id='Description'>Prolamsa
	    </title>
<!--     
    	 The jQuery library is a prerequisite for all jqSuite products
    <script type="text/ecmascript" src="../../../js/jquery.min.js"></script> 
    This is the Javascript file of jqGrid   
    <script type="text/ecmascript" src="../../../js/trirand/jquery.jqGrid.min.js"></script>
    This is the localization file of the grid controlling messages, labels, etc.
    <!-- We support more than 40 localizations
    <script type="text/ecmascript" src="../../../js/trirand/i18n/grid.locale-en.js"></script>
    A link to a jQuery UI ThemeRoller theme, more than 22 built-in and many more custom
    <link rel="stylesheet" type="text/css" media="screen" href="../../../css/jquery-ui.css" />
    The link to the CSS that the grid needs
    <link rel="stylesheet" type="text/css" media="screen" href="../../../css/trirand/ui.jqgrid.css" /> -->
    	
    	
	    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/themes/redmond/jquery-ui.css" />
	    <link rel="stylesheet" type="text/css" href="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/css/ui.jqgrid.css" />
	    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/i18n/grid.locale-en.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.base.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.common.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.formedit.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.inlinedit.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.celledit.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.subgrid.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.treegrid.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.grouping.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.custom.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/jquery.fmatter.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/jquery.searchFilter.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.jqueryui.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/grid.tbltogrid.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/jqDnR.js"></script>
	    <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-3.8.2/src/jqModal.js"></script> 

		 <style type="text/css">

        /* set the size of the datepicker search control for Order Date*/
        #ui-datepicker-div { font-size:11px; }
        .ui-datepicker { z-index: 2000 }
        
        /* set the size of the autocomplete search control*/
        .ui-menu-item {
            font-size: 11px;
        }

         .ui-autocomplete {
            z-index: 2000;
            font-size: 11px;
        }       

    </style>
    
    	<script type="text/javascript">
    
		
    
        	$(document).ready(function () {
        	
        		var valorXML = jQuery("#xmlResponse").val(); 
        		//alert(valorXML);

          	$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Sales Group",  "Sales Rep", "Order Code", "Customer", "Product Code", "Product Description", "Due Date","OD","Length", "Net Tons/Pounds",// "Length"],/* 
                    		"Quantity (Pieces)", "Completed Quantity", "Status"], 
                           colModel:[
                               {name:"salesGroup", width:250, xmlmap:">SalesGroup"} ,
                               {name:"salesRep", width:200, xmlmap:">SalesRep"},
                               {name:"OrderCode", width:200, index:"Code", xmlmap:">Code", key:true},
                               {name:"Customer", width:200, xmlmap:">Customer"},
                               {name:"productCode", width:200, xmlmap:">Product"},
                               {name:"productDescription", width:200, xmlmap:""},
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
                               {name:"od", width:200, xmlmap:">ODIn", align:"right"},
                               {name:"lengthFt", width:100, xmlmap:">LengthFt", align:"right"},
                               {name:"netTons", width:100, xmlmap:""},
                               {name:"quantity", width:100, xmlmap:">QuantityInDefaultUnits"},
                               {name:"completedQty", width:100, xmlmap:">CompletedQuantityResolved"},
                               {name:"status", width:100, xmlmap:">Status"}
                           ],
                 xmlReader: {
                     root: "Data",
                     row: "Order",
                     repeatitems: false
                 },
                 caption: "Order Report - Version 2",
                 loadonce: true,
  				viewrecords: true,
                  width: 900,
                  height: 450,
                  rowNum:20,
              	 rowTotal: 60,
              	 rowList : [20,40,60],
                  pager: "#pager",
                  ignoreCase: true,
                  rownumbers:true,
                  sortable: true,
                     //,scroll:true

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
        
</head>
<body class='default'>

    <table id="prods"><tr><td/></tr></table>
    <div id="pager"></div>
    <textarea style="display: none; visibility: hidden;" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
    <input value="Refresh Page" id="refreshpagebutton" type="button" onclick="refreshPage();" />

    
</body>
</html>










<!-- Version de JQXGRID -->
<%-- <!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>This example shows how to create a Grid from XML string.
    </title>
    
    <link rel="stylesheet" href="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/scripts/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxgrid.filter.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/globalization/globalize.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="http://www.jqwidgets.com/jquery-widgets-demo/scripts/demos.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            
        	var valorXML = jQuery("#xmlResponse").val(); 
	        
            // prepare the data
            var source =
            {
                datatype: "xml",
                datafields: [
                    { name: 'salesGroup', map: 'Code', type: 'string' },
                    { name: 'salesRep', map: 'Order>Code', type: 'string' },                         
                    { name: 'orderCode', map: 'Order>Code', type: 'string' },
                    { name: 'customer', map: 'Order>Code', type: 'string' },
                    { name: 'productCode', map: 'Order>Product', type: 'string' },
                    { name: 'productDescription', map: 'Order>Product', type: 'string' },
                    { name: 'dueDate', map: 'Order>DueDate', type: 'date' },
                    { name: 'otd', map: 'Order>OD', type: 'number' },
                    { name: 'netTons', map: 'Order>NetT', type: 'number' },
                    { name: 'length', map: 'Order>OD', type: 'number' },
                    { name: 'quantity', map: 'Order>CompletedQtyPounds', type: 'number' },
                    { name: 'completedQty', map: 'Order>CompletedQtyFt', type: 'number' },
                    { name: 'status', map: 'Order>Code', type: 'string' }
                ],
                root: "Data",
                record: "Order",
                localdata: valorXML
            };
            var dataAdapter = new $.jqx.dataAdapter(source);

            // Create jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                width: 910,
                source: dataAdapter,
                columnsresize: true,
                filterable: true,
                sortable: true,
                columns: [
					{ text: 'Sales Group', datafield: 'salesGroup', width: 120 },
					{ text: 'Sales Rep', datafield: 'salesRep', width: 120 },
					{ text: 'Order Code', datafield: 'orderCode', width: 120 },
					{ text: 'Customer', datafield: 'customer', width: 150 },
					{ text: 'Product Code', datafield: 'productCode', width: 150 },
					{ text: 'Product Description', datafield: 'productDescription', width: 150 },
					{ text: 'Due Date', datafield: 'dueDate', filtertype: 'date', width: 160, cellsformat: 'dd-MMMM-yyyy' },
					{ text: 'OTD', datafield: 'otd', width: 70 },
					{ text: 'Net Tons/Pounds', datafield: 'netTons', width: 150, cellsalign: 'right' },
					{ text: 'Length', datafield: 'length', width: 80, cellsalign: 'right' },
					{ text: 'Quantity (Pieces)', datafield: 'quantity', width: 150, cellsalign: 'right' },
					{ text: 'Completed Quantity', datafield: 'completedQty', width: 150, cellsalign: 'right' },
					{ text: 'Status', datafield: 'status', width: 120 }
                ]
            });
            
            $('#events').jqxPanel({ width: 300, height: 80});

            $("#jqxgrid").on("filter", function (event) {
                $("#events").jqxPanel('clearcontent');
                var filterinfo = $("#jqxgrid").jqxGrid('getfilterinformation');

                var eventData = "Triggered 'filter' event";
                for (i = 0; i < filterinfo.length; i++) {
                    var eventData = "Filter Column: " + filterinfo[i].filtercolumntext;
                    $('#events').jqxPanel('prepend', '<div style="margin-top: 5px;">' + eventData + '</div>');
                }
            });
            
            $('#clearfilteringbutton').jqxButton({ height: 25});
            $('#refreshpagebutton').jqxButton({ height: 25});
            $('#filterbackground').jqxCheckBox({ checked: false, height: 25});
            $('#filtericons').jqxCheckBox({ checked: false, height: 25});
            // clear the filtering.
            $('#clearfilteringbutton').click(function () {
                $("#jqxgrid").jqxGrid('clearfilters');
            });
            // show/hide filter background
            $('#filterbackground').on('change', function (event) {
                $("#jqxgrid").jqxGrid({ showfiltercolumnbackground: event.args.checked });
            });
            // show/hide filter icons
            $('#filtericons').on('change', function (event) {
                $("#jqxgrid").jqxGrid({ autoshowfiltericon: !event.args.checked });
            });
            //Refresh the screen and reload data from WebService
            $('#refreshpagebutton').click(function () {
            	location.reload(true);
            });
        });
        
    </script>
</head>
<body class='default'>
    <div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
        <div id="jqxgrid">
        </div>
        <div id="eventslog" style="margin-top: 30px;">
            <div style="width: 200px; float: left; margin-right: 10px;">
            <textarea style="visibility: hidden; display: none;" cols="100" rows="100" id="xmlResponse" >${code}</textarea>
            <input value="Remove Filter" id="clearfilteringbutton" type="button" />
            <input value="Refresh Page" id="refreshpagebutton" type="button" onclick="refreshPage();" />
             <div style="margin-top: 10px; visibility: hidden; display: none;" id='filterbackground'>Filter Background</div>
            <div style="margin-top: 10px; visibility: hidden; display: none;" id='filtericons'>Show All Filter Icons</div>
            </div>
            <div style="float: left; visibility: hidden; display: none;">
                Event Log:
                <div style="border: none;" id="events">
                </div>
            </div>
        </div>
    </div>
</body>


</html> --%>