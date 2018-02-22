<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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

<!--NEORIS_CHANGE #67  Adding localization for cart page -->
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

<spring:theme code="cart.page.title" var ="cart_page_title"/>
<spring:theme code="cart.page.no-product-selected" var ="cart_page_no_product_selected"/>
<spring:theme code="cart.page.successfull-updated" var ="cart_page_successfull_updated"/>
<spring:theme code="cart.page.problem-updated" var ="cart_page_problem_updated"/>

<spring:theme code="cart.page.update_error.title" var="cartPageUpdateErrorTilteText"/>
<spring:theme code="cart.page.update_error.message" var="cartPageUpdateErrorMessageText"/>
<spring:theme code="cart.page.update_success.title" var="cartPageUpdateSuccessTilteText"/>
<spring:theme code="cart.page.update_success.message" var="cartPageUpdateSuccessMessageText"/>

<script type="text/javascript"> // set vars
/*<![CDATA[*/
	var cartRemoveItem = true;
	
	var updateCartBatch = "<c:url value="/cart/updateBatch" />";

	onDOMLoaded(bindElements);

	function bindElements()
	{
		// Bind delete buttons 
		jQuery("#cartEntriesTable .deleteRowButton").on("click",deleteRowButtonClicked);
		
		// Bind update cart button
		jQuery("#updateCartButton").on("click",updateCartButtonClicked);

		// Bind reset cart Button
		jQuery("#resetCartButton").on("click",resetCartButtonClicked);
		
		jQuery(".qtyField").numeric_input({
			decimal:'.' 
		 });

		 jQuery("#cartEntriesTable .dropdown_date").change(changeRollingScheduleWeek);
	}
	
	function resetCartButtonClicked (event)
	{
		confirmDeleteCurrentCart();
	}

	function deleteRowButtonClicked(event)
    {
        var entryNumber = $(event.target).closest('tr').attr('entryNumber');
        var deleteRowButtonElement = this;
        ACC.modals.confirmModalOK("${cartPageModalDeleteRowTitleText}", "${cartPageModalDeleteRowMessageText}",
                function () {
                    postCartUpdate(entryNumber, 0);
                },
                null);
    }

    function postCartUpdate(entryNumber, qty) {
        var data = JSON.stringify({"entries":[{"orderEntryNumber":entryNumber,"stockQty":qty}]});
        $.ajax({
            url: updateCartBatch,
            type: 'POST',
            dataType: 'json',
            data: "jsonObject=" + data,
            beforeSend: function() { blockUI(); },
            success: function() {
                location.reload(true);
            }
        });
    }

    function changeRollingScheduleWeek(event) {
        var entryNumber = $(event.target).closest('tr').attr('entryNumber');
        var data = JSON.stringify({"entries":[{"orderEntryNumber":entryNumber,"rollingScheduleWeek": $(this).val() }]});
        $.ajax({
            url: '/store/cart/updateRollingScheduleWeek',
            type: 'POST',
            dataType: 'json',
            data: "jsonObject=" + data,
            beforeSend: function() { blockUI(); },
            success: function() {
                location.reload(true);
            }
        });
    }

	function updateCartButtonClicked(event)
	{
		// retrieve all product rows

		var allProducts = jQuery(ACC.neorisproduct.allProductsSelector);

		var entries = new Array();

		jQuery(allProducts).each(function(eachIndex, eachObject)
		{
			// check if has qty values assigned
			//var stockQty = jQuery(eachObject).find("#stockQty").val();
			//var rollingScheduleQty = jQuery(eachObject).find("#rollingScheduleQty").val();

			var hasChanged = jQuery(eachObject).attr("hasChanged");

			//if (stockQty != 0 || rollingScheduleQty != 0)
			if (hasChanged == "true")
			{
				var obj = { };

				// if its ok get product data
				obj.orderEntryNumber = jQuery(eachObject).attr("entryNumber");
				obj.stockQty = jQuery(eachObject).find("#stockQty").val();

				entries[entries.length] = obj;
			}
		});

		if (entries.length == 0)
		{
			ACC.modals.messageModal("${cart_page_title}", "${cart_page_no_product_selected}");
			return;
		}

		var obj = { entries: entries };
		var info = "jsonObject=" + JSON.stringify(obj);

		jQuery.ajax
		({
			url: updateCartBatch,
			type: 'POST',
			dataType: 'json',
			//contentType: 'application/json; charset=UTF-8',
			data: info,
			beforeSend: function ()
			{
				blockUI();
			},
			success: function (addToCartResult)
			{
				if(addToCartResult == null)
				{
					ACC.modals.messageModal("${cartPageUpdateErrorTilteText}", "${cartPageUpdateErrorMessageText}");
					location.reload(true);
				}
				else
				{
					ACC.modals.messageModal("${cartPageUpdateSuccessTilteText}", "${cartPageUpdateSuccessMessageText}");
					location.reload(true);
				}
			},
			error: function (xht, textStatus, ex)
			{
				ACC.modals.messageModal("${cartPageUpdateErrorTilteText}", "cartPageUpdateErrorMessageText");
			},
			complete: function ()
			{
				unblockUI();
			}
		});

	}

	function roundToBundleUnitEquiv(source,event)
	{
		var enteredQty = parseFloat($(source).val());
		var rowTable = $(event.target).closest('tr');
        var entryNumber = rowTable.attr('entryNumber');
		
		if (enteredQty == 0)
		{
			ACC.modals.confirmModalOK("${cartPageModalDeleteRowTitleText}", "${cartPageModalDeleteRowMessageText}",
					function () 
					{ 
						postCartUpdate(entryNumber, 0);
					},
					function () 
					{
						//If the user decides not to update/delete the row we need to put the previous value
						var previousValue = jQuery(rowTable).find(".qtyInput").val();
						jQuery(dropdown_qtyElement).val(previousValue);
					});
			return;
		}

		var bundleUnitEquiv = parseFloat($(source).attr('bundleUnitEquiv'));
		var roundedQuantity = parseFloat(bundleUnitEquiv);
		while (roundedQuantity < enteredQty)
		{
			roundedQuantity = roundedQuantity + bundleUnitEquiv;
		}
		jQuery(rowTable).attr("hasChanged","true");
		postCartUpdate(entryNumber, roundedQuantity.toFixed(3));
	}

	function variantPopup(target, event)
	{
		var params = {};

		var cartEntryNumber = jQuery(target).attr("cartEntryNumber");
		params.cartEntryNumber = cartEntryNumber;
		
		ACC.variantConfig.deleteConfigurationDone = deleteConfigurationDone;
		ACC.variantConfig.saveConfigurationDone = saveConfigurationDone;

		ACC.variantConfig.popUp(params);
	}

	function deleteConfigurationDone(productCode, cartEntryNumber)
	{
		var span = jQuery("#productConfigureLinkLabel_" + cartEntryNumber);
		span.html("<spring:theme code="Update_Config..." />");
	}

	function saveConfigurationDone(productCode, cartEntryNumber)
	{
		var span = jQuery("#productConfigureLinkLabel_" + cartEntryNumber);
		span.html("<spring:theme code="Configured..." />");
	}
/*]]>*/
</script>

<cart:neorisUpdateCartButton />

<br />

<div class="item_container_holder">
	<div id="resultsList" data-isOrderForm="false" class="product_table">
		<!--NEORIS_CHANGE #67  Replaced accelerator cart -->

		<spring:theme code="product.list.header.wt-piece" var="wtPieceLabel" />
		<table id="cartEntriesTable">
			<tr class="firstrow">
				<td></td>
				<td>${cartPagePartNumberText}</td>
				<td>${cartPageDescriptionText}</td>
				<td class="attribute_col">Lb/piece</td>
				<td class="attribute_col">${cartPageAvailStockText}</td>
				<td class="location_col">${cartPageLocationText}</td>
				<td class="col_center">${cartPageQuantityText}</td>
				<td class="col_center">${cartPageRollingScheduleText}</td>

				<c:if test="${supportProductVariantConfig}">
					<td class="col_center"><spring:theme code="product.list.header.customization" /></td>
				</c:if>
			</tr>
			
			<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
				<c:url value="${cartEntry.product.url}" var="productUrl"/>
				<c:set var="productInventoryEntry" value="${cartEntry.product.inventoryEntry}" />
				
				<c:set var="inventory" value="${productInventoryEntry.availableStockBundles}" />
			
				<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
					<c:set var="inventory" value="${productInventoryEntry.availableStockBundlesInternal}" />
				</sec:authorize>
	
				<c:if test="${inventory eq 0 }">
					<c:set var="inventory" value="-" />
				</c:if>
				
				<tr class="prolamsa_product_row" entryNumber="${cartEntry.entryNumber}" hasChanged="false">
					<td><a class="deleteRowButton">${cartPageDeleteRowText}</a></td>
					<td><a href="${productUrl}">${cartEntry.product.visibleCode}</a></td>
					
					<c:choose>
						<c:when test="${not empty cartEntry.productDescription }">
							<td>${cartEntry.productDescription}</td>
						</c:when>
						<c:otherwise>
							<td>${cartEntry.product.name}</td>
						</c:otherwise>
					</c:choose>
					
				<c:set var="product" value="${cartEntry.product }" />
				<c:set var="weightPerPiece" value="" />
				<c:set var="bundleUnitEquiv" value="" />
				<c:set var="availableStock" value="" />
				<c:choose>
					<c:when test="${fn:contains(unit.code,'lb')  }">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="bundleUnitEquiv"  value="${product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'ft') }">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="bundleUnitEquiv"  value="${product.piecesPerBundle*product.ftEquiv  }"/>
						<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
					</c:when>
				</c:choose>
				
				
				<td class="col_right">
					<fmt:formatNumber pattern="#,###,###,###.###"  value="${weightPerPiece}"/>
				</td>

				<td class="col_right">
					<fmt:formatNumber var="availableStockLabel" pattern="###,###,###,###,##0.000" value="${availableStock}" />
					<c:if test="${availableStock eq 0 or not empty cartEntry.rollingScheduleWeek}">
						<c:set var="availableStockLabel" value="-" />
					</c:if>

					<c:out value="${availableStockLabel}" />
				</td>

					<td>
						<spring:theme code="${cartEntry.product.location}" />
					</td>
	
					<td class="col_center">
						<input class="qtyInput" type="hidden" value="${cartEntry.convertedQuantity}" />
						<input id="stockQty" class="qtyField" bundleUnitEquiv="${bundleUnitEquiv}" type="text" size=8 value="<fmt:formatNumber pattern="#,###,###,##0.000"  value="${cartEntry.convertedQuantity}"/>" onblur="roundToBundleUnitEquiv(this,event);" />
					</td>

					<td class="col_center">
						<c:choose>
							<c:when test="${empty cartEntry.rollingScheduleWeek}">
								<p>-</p>
							</c:when>
							<c:otherwise>
								<product:productInventoryRollingScheduleSelector dateFormat="MM-dd-yyyy"  dates="${productInventoryEntry.rollingScheduleDates}" selectedDate="${cartEntry.rollingScheduleWeek}"/>
							</c:otherwise>
						</c:choose>
					</td>

					<td class="col_center">
						<spring:theme code="Update_Config..." var="productConfigLabel"/>

						<c:if test="${not empty cartEntry.apiProductConfiguration }">
							<spring:theme code="Configured..." var="productConfigLabel"/>
						</c:if>
						<a href="#" class="button yellow positive button-float-right" cartEntryNumber="${cartEntry.entryNumber}" onclick="variantPopup(this, event)"><span id="productConfigureLinkLabel_${cartEntry.entryNumber}"><c:out value="${productConfigLabel}" /></span></a>
					</td>
					

				</tr>
			</c:forEach>
	</table>
	</div>
</div>
<a id="resetCartButton">${cartPageResetCartText}</a>
