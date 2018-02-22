<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>


<spring:theme code="cart.page.delete" var="cartPageDeleteRowText"/>
<spring:theme code="cart.page.part_number" var="cartPagePartNumberText"/>
<spring:theme code="cart.page.description" var="cartPageDescriptionText"/>
<spring:theme code="cart.page.pcs_bundle" var="cartPagePCSBundleText"/>
<spring:theme code="cart.page.wt_piece" var="cartPageWTPieceText"/>
<spring:theme code="cart.page.wt_bundle" var="cartPageWTBundleText"/>
<spring:theme code="cart.page.avail_stock" var="cartPageAvailStockText"/>
<spring:theme code="cart.page.location" var="cartPageLocationText"/>
<spring:theme code="cart.page.quantity" var="cartPageQuantityText"/>
<spring:theme code="cart.page.rolling_schedule" var="cartPageRollingScheduleText"/>
<spring:theme code="cart.page.reset_cart" var="cartPageResetCartText"/>
<spring:theme code="cart.page.model.delete_row.title" var ="cartPageModalDeleteRowTitleText"/>
<spring:theme code="cart.page.modal.delete_row.message" var ="cartPageModalDeleteRowMessageText"/>

<fmt:setLocale value="en_US" scope="session"/>

<script>
var addCartBatch = "<c:url value="/cart/addBatch" />";
var data = {target:null, event:null};

function getURLCodeLocation(urlCode)
{	
	var href = "<c:url value='"+ urlCode +"' />";
	location.href = href;	
}

function quickView(target, event)
{	var code = jQuery(target).attr("productCode");
	ACC.descriptionQuickView.popupUrl = "<c:url value="/product/quickView?code=" />" + code;
	ACC.descriptionQuickView.popUp(code, target, event);
}

function setupQuickView(target, event)
{
	data.target = target;
	data.event = event;
}

function closePop()
{
	ACC.descriptionQuickView.closePopUp();
}

onDOMLoaded(
	function()
	{
		ACC.neorisproduct.messages.addToCart = "<spring:theme code="cart.add-to-cart" />";
		ACC.neorisproduct.messages.noProductsSelected = "<spring:theme code="cart.no-products-selected" />";
		ACC.neorisproduct.messages.changePageTitle ="<spring:theme code="search.page.changePageTitle" />";
		ACC.neorisproduct.messages.changePageMessage = "<spring:theme code="search.page.changePageMessage" />";							
			
		ACC.neorisproduct.messages.productSelectionLocationMixup = "<spring:theme code="cart.add-to-cart.productSelectionLocationMixup" />";
		ACC.neorisproduct.messages.productSelectionLocationCartMismatch = "<spring:theme code="cart.add-to-cart.productSelectionLocationCartMismatch" />";
		
		ACC.neorisproduct.messages.minMaxValidationOnError = "<spring:theme code="cart.add-to-cart.minMaxValidationOnError" />";
		
		$("#productListTable tr:even").addClass("filaImpar");
	    $("#productListTable tr:odd").addClass("filaPar");
	}
);
</script>

<script type="text/javascript"> // set vars
/*<![CDATA[*/


	onDOMLoaded(bindElements_templateDetail);

	function bindElements_templateDetail()
	{				
		// Bind back button
		jQuery("#backButton").on("click",backButtonClicked);
		
		$("#cartEntriesTable tr:even").addClass("filaImpar");
		$("#cartEntriesTable tr:odd").addClass("filaPar");
		
		ACC.neorisproduct.checkForProductEntries = false;
		
	}
			
	function backButtonClicked(event)
	{
         location.href="list";
	}
	
	function confirmLoadTemplate(wishlistPK, hasCart)
	{
		var selectionProductLocation = "";
		var variableProductLocations = false;
		
		var selectionProductIsAPI = null;
		var variableProductFamily = false;
		
		// retrieve all product rows
		var allProducts = jQuery(ACC.neorisproduct.allProductsSelector);
		var entries = new Array();

		jQuery(allProducts).each(function(eachIndex, eachObject)
		{
			// check if has qty values assigned
			var stockQty = numeral().unformat(jQuery(eachObject).find("#stockQty").val());
			var rollingScheduleQty = numeral().unformat(jQuery(eachObject).find("#rollingScheduleQty").val());
			
			if ((jQuery.isNumeric(stockQty) && stockQty != 0) || (jQuery.isNumeric(rollingScheduleQty) && rollingScheduleQty != 0 && rollingScheduleQty != undefined))
			{
				var obj = { };
	
				// if its ok get product data
				obj.productCode = jQuery(eachObject).attr("productCode");
				obj.productLocation = jQuery(eachObject).attr("productLocation");
				// get the rolling schedule date
				obj.rollingScheduleDate = jQuery(eachObject).find("#rollingScheduleDate").val();
				obj.stockQty = stockQty;
				obj.rollingScheduleQty = rollingScheduleQty;
				obj.stockOuts = jQuery(eachObject).find("#stockOuts").val();				
				// get is API product
				obj.isAPIProduct = (jQuery(eachObject).attr("isAPIProduct") == "true");
				
				entries[entries.length] = obj;
				
			///// VALIDATE PRODUCT LOCATIONS
				
				// if already set a selection product location
				if (selectionProductLocation.length != 0)
				{
					// if locations are different
					if (selectionProductLocation != obj.productLocation)
						variableProductLocations = true;
				}
				
				selectionProductLocation = obj.productLocation;
				
			///// VALIDATE PRODUCT FAMILIES COMPATIBILITY
				
				// if already set a selection product family
				if (selectionProductIsAPI != null)
				{
					// if families are different: API products for axis
					if (selectionProductIsAPI != obj.isAPIProduct)
						variableProductFamily = true;
				}
			
				selectionProductIsAPI = obj.isAPIProduct;
			}
		});
		
		// if no entries
		if (entries.length == 0)
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.noProductsSelected);
			return;
		}
		
		// if mismatch between selections location
		if (variableProductLocations)
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.productSelectionLocationMixup);
			return;
		}
		
		// if mismatch between selections family
		if (variableProductFamily)
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.productSelectionFamilyMixup);
			return;
		}
		
		// min max validation
		if(!ACC.neorisMinMaxValidator.isValidProductList(allProducts))
		{
			ACC.modals.messageModal(ACC.neorisproduct.messages.addToCart, ACC.neorisproduct.messages.minMaxValidationOnError);
			return;
		}
		
		// if cart has product
		if(hasCart > 0){
			ACC.modals.confirmModalOK("<spring:theme code="templates.load.title" />", "<spring:theme code="templates.load.confirm" />",     	
	     			function () {addItemsToCartAction(entries); },
				null);	
		} else {
			addItemsToCartAction(entries);
		};
	}
	
	function addItemsToCartAction (entries)
	{	
		var selectionProductLocation = entries[0].productLocation;
		var selectionProductIsAPI = entries[0].isAPIProduct;
		
		var obj = {entries: entries, selectionProductLocation: selectionProductLocation, clearCurrentCart: true};
		
		var info = "jsonObject=" + JSON.stringify(obj);

		var unblockPage = true;
		
		jQuery.ajax
		({
			url: addCartBatch,
			type: 'POST',
			dataType: 'json',
			data: info,
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (addToCartResult)
			{
				// assign cart product location after success cart modification
				cartProductLocation = selectionProductLocation;
				
				// refresh variable after success cart modification
				cartHasAPIProducts = selectionProductIsAPI;

				ACC.neorisproduct.clearAmounts();
				
				var wishlistPK = "${wishlist.pk}";
				
				var b2bUnitOwner = "${wishlist.b2bUnit.uid}";
				
				unblockPage = false;
				
				$.ajax
			 	({
			 		url:         "<c:url value="/templates/setTemplateOnSession.json" />",
			 		type:        'POST',
			 		dataType:    'json',
			 		data: { wishlistPK: wishlistPK},
			 		complete: function(data)
			 		{
			 			unblockUI();
			 		},
			 		error: function (xht, textStatus, ex)
			 		{
			 			ACC.modals.messageModal("Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		        	},
		        	success: function (data)
		        	{
		        		if (data.status == 0)
		            	{
		            		//ACC.modals.messageModal("<spring:theme code="templates.load.title"/>", "<spring:theme code="templates.load.success"/>");
		        			window.location = "<spring:url value="/templates/list" />";
		        			
		            	}
		        		else
		        			ACC.modals.messageModal("Error:" + data.message);
		        	}
			 	});					
			},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("e", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			},
			complete: function ()
			{
				if(unblockPage == true)
					unblockUI();
			}
		});

		return;
	}
/*]]>*/
</script>

<div class="item_container_holder" style="display: block;">
	<div class="title_holder">
		<h2><spring:theme code="templates.list.header.title.detail"/></h2>
	</div>
<%--
<div class="span-40 last">
	<button id="backButton"  class="positive right">
		<spring:theme code="templates.list.header.backButton"/>
	</button>
</div>

<br />

<div class="item_container_holder">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="templates.list.header.title.detail"/></h2>
	</div>
	--%>
	<div class="" style="width: auto;">
	      
	    <table>
	       <tr>	
	            	
				<td ><spring:theme code="templates.list.client"/>:</td>
				<td >${wishlist.b2bUnit.shortName} (${wishlist.b2bUnit.uid})</td>
		   </tr>
		   <tr>	
		        
				<td ><spring:theme code="templates.list.uom"/>:</td>
				<td >${wishlist.unit.name}</td>
		   </tr>
		   <tr>	
		        		
				<td ><spring:theme code="templates.list.draftName"/>:</td>
				<td >${wishlist.name}</td>
				<td  ></td>
				<td><spring:theme code="templates.list.header.creationDate"/>:</td>
				<%-- <td ><fmt:formatDate value="${wishlist.creationtime}" pattern="MM/dd/yyyy" /> </td>--%>
				<td ><formDate:formFormatDate value="${wishlist.creationtime}" pattern="MM/dd/yyyy" /> </td> 
				
				
		   </tr>		  			
	    </table>
	   
	    </div>
	    <div class="item_container" style="width: auto;">
	    <div class="search-form-options-search-button">	
	<button id="backButton"  class="button yellow positive button-float-right">
		<spring:theme code="templates.list.header.backButton"/>
	</button>
</div>	    
<br><br>	    
    	
    	<c:choose>
	    	<c:when test="${wishlist.hasAPIProducts eq true}">
	    		<templates:neorisDraftNotConfigurableAPIProductList whislists="${wishlistEntry}"/>
    		</c:when>
    		<c:otherwise>
				<templates:neorisDraftNotConfigurableProductList whislists="${wishlistEntry}"/>
    		</c:otherwise>		
    	</c:choose>
    		
		
		
	<br>
	<div class="search-form-options-search-button">	 
	<button id="loadButton" type="button" class="button yellow positive button-float-right" onclick="confirmLoadTemplate(${wishlist.pk},${hasCart});">
		<spring:theme code="templates.list.header.loadButton" />
	</button>
	</div>
	</div>
	
	
</div> 


 