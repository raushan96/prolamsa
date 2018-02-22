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
			<c:when test="${baseStore.uid eq '6000' || baseStore.uid eq '5000'}">
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
		
		<table id="prods"><tr><td/></tr></table>
    	<div id="pager"></div>
    	<textarea style="display: none; visibility: hidden;" cols="100" rows="100" id="xmlResponse">${code}</textarea>
    	<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
			<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
		</button>	
			</c:when>
			<c:otherwise> 
		
		
		
		
		
		
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		
		<table id="prods"><tr><td/></tr></table>
    	<div id="pager"></div>
    	<textarea style="display: none; visibility: hidden;" cols="100" rows="100" id="xmlResponse">${code}</textarea>
    	<button id="refreshpagebutton" class="button yellow positive" type="button" style="margin: 10px 0 10px 0;">
			<spring:theme code="text.account.reports.refreshButton" text="Refresh Page" />
		</button>	
		 </c:otherwise>
		</c:choose>	
	</div>
</template:page>
    
    	<script type="text/javascript">
        	$(document).ready(function () {
        	var valorXML = jQuery("#xmlResponse").val(); 

        	$("#prods").jqGrid({
          		datatype: 'xmlstring',
          		datastr : valorXML,
          		colNames:["Sales Group",  "Sales Rep", "Order Code", "Customer", "Product Code", "Product Desc", "Due Date", "OD", "Net Tons", 
                    		"Qty (Pcs)", "Completed Qty", "Status"], 
                           colModel:[
                               {name:"salesGroup", width:150, xmlmap:">SalesGroup"} ,
                               {name:"salesRep", width:150, xmlmap:">SalesRep"},
                               {name:"OrderCode", width:100,  xmlmap:">Code", key:true},
                               {name:"Customer", width:180, xmlmap:">Customer"},
                               {name:"productCode", width:170, xmlmap:">Product"},
                               {name:"productDescription", width:200, xmlmap:">MaterialDescription"},
                               {name:"dueDate", width:150, xmlmap:">DueDate", sorttype:'date',type: 'date' , cellsformat: 'yy-mm-dd',filtertype: 'date',
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
                               {name:"od", width:100, xmlmap:">ODIn", align:"right"},
                               {name:"netTons", width:100, xmlmap:">QuantityInDefaultUnits"},
                               {name:"quantity", width:100, xmlmap:">OrderQuantityPieces"},
                               {name:"completedQty", width:100, xmlmap:">CompletedQuantityResolved"},
                               {name:"status", width:100, xmlmap:">Status"}
                           ],
                 xmlReader: {
                     root: "Data",
                     row: "Order",
                     repeatitems: false
                 },
                 caption: '<spring:theme code="text.account.orderReportV2" />',
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