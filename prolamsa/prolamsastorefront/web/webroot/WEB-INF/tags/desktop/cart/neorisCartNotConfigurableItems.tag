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

<fmt:setLocale value="en_US" scope="session"/>

<script type="text/javascript"> // set vars
/*<![CDATA[*/
	var cartRemoveItem = true;
	var updateCartBatch = "<c:url value="/cart/updateBatch" />";

	onDOMLoaded(bindElements);

	function bindElements()
	{
		// Bind delete buttons 
		jQuery("#cartEntriesTable .deleteRowButton").on("click",deleteRowButtonClicked);
		
		// Bind quantity elements
		jQuery("#cartEntriesTable .dropdown_qty").change(dropdown_qtyChanged);
		
		// Bind reset cart Button
		jQuery("#resetCartButton").on("click",resetCartButtonClicked);

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

    function dropdown_qtyChanged(event) {
        var dropdown_qtyElement = this;
        var newVal = $(this).val();
        var rowTable = $(event.target).closest('tr');
        var entryNumber = rowTable.attr('entryNumber');
        if (newVal == 0) {
            ACC.modals.confirmModalOK("${cartPageModalDeleteRowTitleText}", "${cartPageModalDeleteRowMessageText}",
                function() { postCartUpdate(entryNumber, newVal); },
                function() {
                    var previousValue = $(rowTable).find(".qtyInput").val();
                    $(dropdown_qtyElement).val(previousValue);
                });
        } else {
            postCartUpdate(entryNumber, newVal);
        }
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

	function variantPopup(target, event)
	{
		var params = {};

		var cartEntryNumber = jQuery(target).attr("cartEntryNumber");
		params.cartEntryNumber = cartEntryNumber;
		
		ACC.variantConfig.popUp(params);
	}

/*]]>*/
</script>

<div class="item_container_holder">
	<%--
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="basket.page.title.yourItems"/></h2>
	</div>
	 --%>
	<div id="resultsList" data-isOrderForm="false" class="product_table">
		<!--NEORIS_CHANGE #67  Replaced accelerator cart -->

		<spring:theme code="product.list.header.wt-piece" var="wtPieceLabel" />
		<c:if test="${unit.code eq  'prolamsa_mt'}">
	   	<c:set value="Kg/Pc" var="wtPieceLabel" />
		</c:if>
		
		<c:if test="${unit.code eq 'prolamsa_ft'}">
	   	<c:set value="Lb/Pc" var="wtPieceLabel" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'kg')}">
			<c:set value="(Kg)" var="wtLabel" />
			<c:set value="(Kg)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'mt')}">
			<c:set value="(Kg)" var="wtLabel" />
			<c:set value="(Mts)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'bun_kg')}">
			<c:set value="(Kg)" var="wtLabel" />
			<c:set value="(Paq)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'pc_kg')}">
			<c:set value="(Kg)" var="wtLabel" />
			<c:set value="(Pza)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'lb')}">
			<c:set value="(Lb)" var="wtLabel" />
			<c:set value="(Lb)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ft')}">
			<c:set value="(Lb)" var="wtLabel" />
			<c:set value="(Ft)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'bun_lb')}">
			<c:set value="(Lb)" var="wtLabel" />
			<c:set value="(Bun)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'pc_lb')}">
			<c:set value="(lb)" var="wtLabel" />
			<c:set value="(Pc)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ton')}">
			<c:set value="(Ton)" var="wtLabel" />
			<c:set value="(Ton)" var="wtLabel2" />
		</c:if>
		
<%-- 		<c:if test="${fn:contains(unit.code,'mt') || fn:contains(unit.code,'kg') || fn:contains(unit.code,'ton')}">
		   	<c:set value="(Kg)" var="wtLabel" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ft') || fn:contains(unit.code,'lb')}">
		   	<c:set value="(Lb)" var="wtLabel" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'kg')}">
			<c:set value="(Kg)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'mt')}">
			<c:set value="(Mts)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'bun_kg')}">
			<c:set value="(Paq)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'pc_kg')}">
			<c:set value="(Pza)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ft') || fn:contains(unit.code,'lb')}">
			<c:set value="(Lb)" var="wtLabel2" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ton')}">
			<c:set value="(Ton)" var="wtLabel2" />
		</c:if> --%>
		
		<spring:theme code="cart.cartItems.header.qty.${unit.code}" var="qtyLabel"/>
		
		<table id="cartEntriesTable">
			<tr class="firstrow">
				<td></td>
				<td>${cartPagePartNumberText}</td>
				<td>${cartPageDescriptionText}</td>
				<td class="attribute_col">${cartPagePCSBundleText}</td>
				<td class="attribute_col">${wtPieceLabel}<br/>${wtLabel}</td>
				<td class="attribute_col">${cartPageWTBundleText}<br/>${wtLabel}</td>
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<td class="attribute_col">${cartPageAvailStockText}<br/>${wtLabel2}</td>
				</sec:authorize>
				<td class="location_col">${cartPageLocationText}</td>
				<td class="col_center">${cartPageQuantityText}<br/>${qtyLabel}</td>
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<td class="col_center">${cartPageRollingScheduleText}</td>
				</sec:authorize>

				<c:if test="${supportProductVariantConfig}">
					<td class="col_center"><spring:theme code="product.list.header.customization" /></td>
				</c:if>
			</tr>
			
			<c:set var="totalWeightQuantity" value="0.000"/>
			
			<c:set var="cartTotalStockKg" value="0"/>
			<c:set var="cartTotalStockPcs" value="0"/>
			<c:set var="cartTotalRollingKg" value="0"/>
			<c:set var="cartTotalRollingPcs" value="0"/>
			
			
			<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
				<c:url value="${cartEntry.product.url}" var="productUrl"/>
				<c:set var="productInventoryEntry" value="${cartEntry.product.inventoryEntry}" />
				
				<c:set var="inventory" value="${productInventoryEntry.availableStockBundles}" />
			
				<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
					<c:set var="inventory" value="${productInventoryEntry.availableStockBundlesInternal}" />
				</sec:authorize>
	
				<tr class="prolamsa_product_row" entryNumber="${cartEntry.entryNumber}" hasChanged="false">
					<td><a class="deleteRowButton">${cartPageDeleteRowText}</a></td>
					<td><a href="${productUrl}">${cartEntry.product.visibleCode}</a></td>
					<td>${cartEntry.product.name}</td>

					<td class="col_center">
						<fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartEntry.product.piecesPerBundle}"/>
					</td>
				<c:set var="product" value="${cartEntry.product }" />
				<c:set var="weightPerPiece" value="" />
				<c:set var="weightPerBundle" value="" />
				<c:set var="availableStock" value="" />
				<c:choose>
					<c:when test="${fn:contains(unit.code,'bun_kg') }">
						<c:set var="weightPerPiece"  value="${ product.pcKgEquiv}"/>
						<c:set var="weightPerBundle"  value="${ product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory}" />
					</c:when>
					
					<c:when test="${fn:contains(unit.code,'pc_kg' ) }">
						<c:set var="weightPerPiece"  value="${product.pcKgEquiv }"/>
						<c:set var="weightPerBundle"  value="${product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'kg') }">
						<c:set var="weightPerPiece"  value="${product.pcKgEquiv }"/>
						<c:set var="weightPerBundle"  value="${product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.bundleKgEquiv}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'mt') }">
						<c:set var="weightPerPiece"  value="${product.pcKgEquiv }"/>
						<c:set var="weightPerBundle"  value="${product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.mtEquiv}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'bun_lb') }">
						<c:set var="weightPerPiece"  value="${product.pcLbEquiv }"/>
						<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'pc_lb') }">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'lb')  }">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.bundleLbEquiv}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'ft') }">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.ftEquiv}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'ton')}">
						<c:set var="weightPerPiece"  value="${ product.tnEquiv / product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${ product.tnEquiv}"/>
						<c:set var="availableStock" value="${inventory*product.tnEquiv}"/>
					</c:when>
				</c:choose>
				
				
					<td class="col_right">
						<fmt:formatNumber pattern="${weightNumberPattern}"  value="${weightPerPiece}"/>												
					</td>
					<td class="col_right">
						<fmt:formatNumber  pattern="${weightNumberPattern}" value="${ weightPerBundle}"/>						
					</td>
					<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
						<c:choose>
							<c:when test="${availableStock eq 0 or not empty cartEntry.rollingScheduleWeek}">
								<td class="col_center" style="color: red;">
									<c:set var="availableStockLabel" value="----" />
									<c:out value="${availableStockLabel}" />
								</td>
							</c:when>
							<c:otherwise>
								<td class="col_right">
									<fmt:formatNumber var="availableStockLabel" pattern="${quantityPattern }" value="${availableStock}" />
									<c:out value="${availableStockLabel}" />
								</td>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
			
					<td>
						<spring:theme code="${cartEntry.product.location}" />
					</td>
	
					<td class="col_center">
						<input class="qtyInput" type="hidden" value="${cartEntry.quantity}" />
						<c:choose>
							<c:when test="${empty cartEntry.rollingScheduleWeek}">
							
								<c:set var="availableStockBundlesCol" value="${productInventoryEntry.availableStockBundlesCol}" />
									
								<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
									<c:set var="availableStockBundlesCol" value="${productInventoryEntry.availableStockBundlesColInternal}" />
								</sec:authorize>
								
								<sec:authorize ifAnyGranted="ROLE_PROLAMSA_NO_INVENTORY">
									<c:set var="availableStockBundlesCol" value="${productInventoryEntry.noInventoryRoleBundlesCol}" />
								</sec:authorize>
								<product:productInventoryBundleSelector bundleName="stockQty" quantityCol="${availableStockBundlesCol}"  selectedQuantity="${cartEntry.convertedQuantity}" />
								<c:set var="totalWeightQuantity" value="${totalWeightQuantity + cartEntry.convertedQuantity}"/>
							</c:when>
							<c:otherwise>
								<product:productInventoryBundleSelector bundleName="stockQty" quantityCol="${productInventoryEntry.rollingScheduleBundlesCol}"  selectedQuantity="${cartEntry.convertedQuantity}" />
								<c:set var="totalWeightQuantity" value="${totalWeightQuantity + cartEntry.convertedQuantity}"/>
							</c:otherwise>
						</c:choose>
					</td>
					<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
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
					</sec:authorize>
					<c:if test="${supportProductVariantConfig}">
						<td class="col_center">
							<a href="#" class="button yellow positive button-float-right" cartEntryNumber="${cartEntry.entryNumber}" onclick="variantPopup(this, event)"><spring:theme code="Update_Config..." /></a>
						</td>
					</c:if>

				</tr>
				
				<!-- Start calculating totals for summary box -->
				
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<c:choose>
						<c:when test="${availableStock eq 0 or not empty cartEntry.rollingScheduleWeek}">
							<c:set var="cartTotalRollingKg" value="${cartTotalRollingKg + (cartEntry.quantity * product.bundleKgEquiv)}"/>
							<c:set var="cartTotalRollingPcs" value="${cartTotalRollingPcs + (cartEntry.quantity * product.piecesPerBundle)}"/>
						</c:when>
						<c:otherwise>
							<c:set var="cartTotalStockKg" value="${cartTotalStockKg + (cartEntry.quantity * product.bundleKgEquiv)}"/>
							<c:set var="cartTotalStockPcs" value="${cartTotalStockPcs + (cartEntry.quantity * product.piecesPerBundle)}"/>
						</c:otherwise>
					</c:choose>	
				</sec:authorize>
				
				<sec:authorize ifAnyGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<c:set var="cartTotalStockKg" value="${cartTotalStockKg + (cartEntry.quantity * product.bundleKgEquiv)}"/>
					<c:set var="cartTotalStockPcs" value="${cartTotalStockPcs + (cartEntry.quantity * product.piecesPerBundle)}"/>
				</sec:authorize>
				
				<!-- End calculating totals for summary box -->
				
			</c:forEach>
						
			<!-- NEORIS_CHANGE #Adding totals -->
			<c:set var="colspanNumber" value="10"/>
			<sec:authorize ifAnyGranted="ROLE_PROLAMSA_NO_INVENTORY">
				<c:set var="colspanNumber" value="8"/>
			</sec:authorize>
			
			<tr>
			    <td colspan="${colspanNumber}"><hr></td>
			</tr> 
			   
			<tr >			
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<td></td>
				</sec:authorize>	
				<td></td>			
				<td class="attribute_col"></td>
				<td class="attribute_col"></td>				
				<td class="attribute_col"></td>
				<td class="location_col"></td>
				<td class="col_center"></td>
				<c:choose>
					<c:when test="${baseStore.uid eq '6000'}">
						<td class="col_right"></td>
						<td class="col_right"></td>
					</c:when>
					<c:otherwise>
						<td class="col_right"><spring:theme code="order.totals.total" /></td>
						<td class="col_right">
						   <fmt:formatNumber  pattern="${quantityPattern}" value="${totalWeightQuantity}"/>
						</td>
					</c:otherwise>
				</c:choose>
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<td class="col_center"></td>
				</sec:authorize>
				<c:if test="${supportProductVariantConfig}">
					<td class="col_center"></td>
				</c:if>
			</tr>
	</table>
	</div>
</div>

<a id="resetCartButton" class="button yellow positive button-float-left">${cartPageResetCartText}</a>

<div class="span-8 right last place-order-cart-total" style="min-height: 200px !important;">
	<cart:neorisCartSummary 
		cartTotalStockKg="${cartTotalStockKg}" 
		cartTotalStockPcs="${cartTotalStockPcs}" 
		cartTotalRollingKg="${cartTotalRollingKg}" 
		cartTotalRollingPcs="${cartTotalRollingPcs}"/>
</div>

<script type="text/javascript"> // set vars
</script>
