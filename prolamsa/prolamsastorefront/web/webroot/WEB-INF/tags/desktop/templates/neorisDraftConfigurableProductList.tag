<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="productList" required="true" type="java.util.Collection" %>

<fmt:setLocale value="en_US" scope="session"/>

<!-- Variable for batch post -->
<script>

<c:if test="${uploadedFromExcel}">
var uploadedFromExcel = true;
</c:if>
<c:if test="${!uploadedFromExcel}">
var uploadedFromExcel = false;
</c:if>


var addCartBatch = "<c:url value="/cart/addBatch" />";

function getURLCodeLocation(urlCode)
{	
	var href = "<c:url value='"+ urlCode +"' />";
	location.href = href;	
}

function quickView(target, event)
{
	var code = jQuery(target).attr("productCode");
	ACC.descriptionQuickView.popupUrl = "<c:url value="/product/quickView?code=" />" + code;
	ACC.descriptionQuickView.popUp(code, target, event);
}

function variantPopup(target, event)
{
	var params = {};

	var code = jQuery(target).attr("productCode");
	params.productCode = code;
	
	ACC.variantConfig.deleteConfigurationDone = deleteConfigurationDone;
	ACC.variantConfig.saveConfigurationDone = saveConfigurationDone;

	ACC.variantConfig.popUp(params);
}

function deleteConfigurationDone(productCode, cartEntryNumber)
{
	var span = jQuery("#productConfigureLinkLabel_" + productCode);
	span.html("<spring:theme code="Update_Config..." />");
}

function saveConfigurationDone(productCode, cartEntryNumber)
{
	var span = jQuery("#productConfigureLinkLabel_" + productCode);
	span.html("<spring:theme code="Configured..." />");
}

function roundToBundleUnitEquiv(source,event)
{
	var enteredQty = parseFloat($(source).val());
	
	if (enteredQty == 0)
		return; 
	
	
	var bundleUnitEquiv = parseFloat($(source).attr('bundleUnitEquiv'));
	var roundedQuantity = parseFloat(bundleUnitEquiv);
	while (roundedQuantity < enteredQty)
	{
		roundedQuantity = roundedQuantity + bundleUnitEquiv;
	}
	
	$(source).val(roundedQuantity.toFixed(3));
}

function isNumber(n) 
{
	  return !isNaN(parseFloat(n)) && isFinite(n);
}
// javascript messages

onDOMLoaded(
	function()
	{
		ACC.neorisproduct.messages.addToCart = "<spring:theme code="cart.add-to-cart" />";
		ACC.neorisproduct.messages.noProductsSelected = "<spring:theme code="cart.no-products-selected" />";
		ACC.neorisproduct.messages.changePageTitle ="<spring:theme code="search.page.changePageTitle" />";
		ACC.neorisproduct.messages.changePageMessage = "<spring:theme code="search.page.changePageMessage" />";
		
		jQuery(".qtyField").numeric_input({
			decimal:'.' 
		 });
		
		if (uploadedFromExcel)
			updateEntriesFromExcel();
	}
);

function updateEntriesFromExcel()
{
	jQuery.each(jQuery(".qtyField"), function( index, value )
	{
		roundToBundleUnitEquiv(value, null);
	});
}
</script>

	<spring:theme code="product.list.header.wt-piece" var="wtPieceLabel" />
	
	<table>
	<tr class="firstrow">
	     <!-- NEORIS_CHANGE #107 -->			
		<c:if test="${selLocation != null}">
	       <td width="5"></td>
	    </c:if>
	    
		<!-- NEORIS_CHANGE #78 -->
		<c:if test="${product.code == null }">
			<td class="col_center"><spring:theme code="product.list.header.part" /></td>
			<td class="col_center"><spring:theme code="product.list.header.description" /></td>
		</c:if>
<%-- 		<td class="col_center"><spring:theme code="product.list.header.pcs-bundle" /></td> --%>
<!-- 	Page 64	Lb/piece: weight in Lbs per piece.  It doesn't matter what is selected in the UoM combo in the header page.  -->
		<td class="col_center">Lb/piece</td>
<%-- 		<td class="attribute_col"><spring:theme code="product.list.header.wt-bundle" /></td> --%>
		<td class="location_col"><spring:theme code="product.list.header.available-stock"/></td>
		<td class="col_center"><spring:theme code="product.list.header.location" /></td>
		<td class="col_center"><spring:theme code="product.list.header.quantity" /></td>
		<td class="col_center"><spring:theme code="product.list.header.rolling-schedule-week" /></td>
		<td class="col_center"><spring:theme code="product.list.header.order-rolling-units" /></td>
		<td class="col_center"><spring:theme code="product.list.header.customization" /></td>
	</tr>
	
	<c:forEach items="${productList}" var="product" varStatus="status">
		<c:set var="inventoryEntry" value="${product.inventoryEntry}" />
		<c:set var="inventory" value="${inventoryEntry.availableStockBundles}" />

		<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
			<c:set var="inventory" value="${inventoryEntry.availableStockBundlesInternal}" />
		</sec:authorize>

		<tr class="prolamsa_product_row" productCode="${product.code}" productLocation="${inventoryEntry.location}">
			<!-- NEORIS_CHANGE #107 -->			
			<c:if test="${selLocation != null}">
				<td width="5">	
					<input type="radio" name="selProduct" value="" onClick="getURLCodeLocation('/store/p/${product.code}?selLocation=${inventoryEntry.location}')" 
					<c:if test="${inventoryEntry.location eq selLocation}">checked</c:if>
					/>
				</td>
			</c:if>
			
			
			<!-- NEORIS_CHANGE #78 -->
			<c:if test="${productCode == null }">
				<td class="text_top"><a href="${contextPath}${product.url}">${product.visibleCode}</a></td>
				<td class="product_list_product_code" productCode="${product.code}" onmouseover="quickView(this, event)">
					<spring:theme code="${product.name}"/>
				</td>
				
			</c:if>
				
				<c:set var="weightPerPiece" value="" />
				<c:set var="bundleUnitEquiv" value="" />
				<c:set var="availableStock" value="" />
				<c:choose>
					<c:when test="${fn:contains(unit.code,'lb')}">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="bundleUnitEquiv"  value="${product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'ft')}">
						<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
						<c:set var="bundleUnitEquiv"  value="${product.piecesPerBundle*product.ftEquiv }"/>
						<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
					</c:when>
				</c:choose>

				<td class="col_right text_top">
					<fmt:formatNumber pattern="###,###,###,###,##0.000"  value="${weightPerPiece}"/>
				</td>
				<c:choose>
				<c:when test="${availableStock eq 0 }">
					<td class="col_center">
						<fmt:formatNumber var="availableStockLabel" pattern="###,###,###,###,##0.000" value="${availableStock}" />
						<c:if test="${availableStock eq 0 }">
							<c:set var="availableStockLabel" value="-" />
						</c:if>
	
						<c:out value="${availableStockLabel}" />
					</td>
				</c:when>
				<c:otherwise> 
					<td class="col_right">
						<fmt:formatNumber var="availableStockLabel" pattern="###,###,###,###,##0.000" value="${availableStock}" />
						<c:if test="${availableStock eq 0 }">
							<c:set var="availableStockLabel" value="-" />
						</c:if>
	
						<c:out value="${availableStockLabel}" />
					</td>
				</c:otherwise>	
				</c:choose>
			<td>
				<spring:theme code="${inventoryEntry.location}" />
			</td>

			<c:set var="availableStockBundlesCol" value="${inventoryEntry.availableStockBundlesCol}" />

			<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
				<c:set var="availableStockBundlesCol" value="${inventoryEntry.availableStockBundlesColInternal}" />
			</sec:authorize>

			<!-- NEORIS_CHANGE #105-->
			<c:choose>
			<c:when test="${empty product.quantityExcel}">
				<td class="col_center">
					<c:set var="disabled" value="" />
					<c:if test="${availableStock == 0 }">
						<c:set var="disabled" value="disabled" />
					</c:if>
					<input ${disabled} id="stockQty" class="qtyField" bundleUnitEquiv="${bundleUnitEquiv}" type="text" size=8 value=0 onblur="roundToBundleUnitEquiv(this,event);" />
				</td>
			</c:when>
			<c:otherwise>
				<td class="col_center">
					<c:set var="disabled" value="" />
					<c:if test="${availableStock == 0 }">
						<c:set var="disabled" value="disabled" />
					</c:if>
					<input ${disabled} id="stockQty" class="qtyField" bundleUnitEquiv="${bundleUnitEquiv}" type="text" size=8 value="${product.quantityExcel}" onblur="roundToBundleUnitEquiv(this,event);" />
				</td>
			</c:otherwise>
			</c:choose>
			
			<!-- Codigo Original de Cantidad modificado por #105
			<td class="col_center">
				<c:set var="disabled" value="" />
				<c:if test="${availableStock == 0 }">
					<c:set var="disabled" value="disabled" />
				</c:if>
				<input ${disabled} id="stockQty" class="qtyField" bundleUnitEquiv="${bundleUnitEquiv}" type="text" size=8 value=0 onblur="roundToBundleUnitEquiv(this,event);" />
			</td>
			-->
			

			<td class="col_center">
				<product:productInventoryRollingScheduleSelector dateFormat="MM-dd-yyyy" dates="${inventoryEntry.rollingScheduleDates}"/>
			</td>

			<td class="col_center">
			<c:set var="disabled" value="" />
				<c:if test="${ empty inventoryEntry.rollingScheduleDates}">
					<c:set var="disabled" value="disabled" />
				</c:if>
			<input ${disabled} id="rollingScheduleQty" class="qtyField" bundleUnitEquiv="${bundleUnitEquiv}" type="text" size=8 value=0 onblur="roundToBundleUnitEquiv(this,event);" />
<%-- 				<product:productInventoryBundleSelector bundleName="rollingScheduleQty" quantityCol="${inventoryEntry.rollingScheduleBundlesCol}" selectedQuantity="0" /> --%>
			</td>
			
			<td class="col_center">
				<a href="#" class="variantConfig" productCode="${product.code}" onclick="variantPopup(this, event)"><span id="productConfigureLinkLabel_${product.code}"><spring:theme code="Update_Config..." /></span></a>
			</td>
		</tr>
	</c:forEach>
</table>