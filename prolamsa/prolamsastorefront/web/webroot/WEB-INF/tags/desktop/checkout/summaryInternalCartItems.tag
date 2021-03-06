<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>


<spring:theme code="cart.page.update_error.title" var="cartPageUpdateErrorTilteText"/>
<spring:theme code="cart.page.update_error.message" var="cartPageUpdateErrorMessageText"/>
<spring:theme code="cart.page.update_success.title" var="cartPageUpdateSuccessTilteText"/>
<spring:theme code="cart.page.update_success.message" var="cartPageUpdateSuccessMessageText"/>

<spring:theme code="cart.page.priceneg.error.invalidvalue.message" var="cartPagePriceNegInvalidValueText"/>
<spring:theme code="cart.page.priceneg.error.outofrange.message" var="cartPagePriceNegInvalidValueOutOfRangeText"/>

<fmt:setLocale value="en_US" scope="session"/>

<script type="text/javascript">

	var updateCartBatch = "<c:url value="/cart/updateBatch" />";
	
	var cartPagePriceNegInvalidValueText = "${cartPagePriceNegInvalidValueText}";
	var cartPagePriceNegInvalidValueOutOfRangeText = "${cartPagePriceNegInvalidValueOutOfRangeText}";
	
	function getCurrentUnit()
	{
	 	return	$("#unitSelectorElement").val();
	}
	
	function formatWeight(string)
	{
		var formattedString = string.toFixed(3);
		
		formattedString += '';
		x = formattedString.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		return x1 + x2;
	 	
	}

	function deleteRowButtonClicked(event)
	{
		event.preventDefault();

		var deleteRowButtonElement = this;

		ACC.modals.confirmModalOK("<spring:theme code="checkout.page.model.delete_row.title"/>", "<spring:theme code="checkout.page.modal.delete_row.message"/>", function() { deleteRowButtonAction(deleteRowButtonElement); }, null);
	}

	<!-- NEORIS_CHANGE #74 handle delete row button clicked-->
	function deleteRowButtonAction(component)
	{
		var entries = new Array();
		
		var obj = { };
		
		obj.orderEntryNumber = jQuery(component).parents("tr").attr("entryNumber");
		obj.stockQty = 0;
		
		entries[entries.length] = obj;
		
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
				//jQuery.blockUI({ message: '<h1><img src="busy.gif" /> Just a moment...</h1>' });
				blockUI();
			},
			success: function (data)
			{
				if(data == null)
				{
					
					location.reload(true);
					
				}else
				{
					$('#summary_order_dl').replaceWith($('#summaryOrderTemplate').tmpl(data));
					$('#totals_order_dl').replaceWith($('#totalsOrderTemplate').tmpl(data));
					$('#cartEntryTBody').replaceWith($('#entriesTemplate').tmpl(data));
					pricesUpdated  = false;
					bindElements();
					updateButtons();										
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
</script>

<script id="cartItemsTemplate" type="text/x-jquery-tmpl">
 	{{tmpl({entries:entries},{appliedProductPromotion:appliedProductPromotions,potentialProductPromotion:potentialProductPromotions}) '#entriesTemplate'}}
</script>

<!-- NEORIS_CHANGE #74 Template used to show SAP data-->

<!-- Added div to hide info that is only displayed on IE9-->
<script id="entriesTemplate" type="text/x-jquery-tmpl">

 

<tbody id="cartEntryTBody">
   
     
	{{each(i,item) entries}}
		<div style="display:none;">
			{{= item.trclass = '' }}
			{{= item.tdclass = '' }}
			{{= item.weight = 0 }}
			{{= item.currentUnit = getCurrentUnit() }}

			{{= item.inputDisabled = 'disabled' }}
			{{= item.validateNegPriceClass = '' }}
			{{= item.inputValue = formattedPricePerTon }}		
		
			{{if isTransportationGroupFull == true }}
        	    {{= item.trclass = 'groupCompleted'}}
    	    {{/if}}
			
			{{if isTransportationGroupFull == false }}
				 {{= item.trclass = 'groupUncompleted'}}
        	{{/if}}
        	
			{{if  isLastOnTraspotation}}
            	{{= item.tdclass = ' lastInGroup'}}
        	{{/if}}
            
            
		</div>
			<tr class="prolamsa_product_row {{= item.trclass}}" entryNumber="{{= entryNumber}}" hasChanged="false" >
			<td class="{{= item.tdclass}}"><a class="deleteRowButton"><spring:theme code="checkout.summary.items.delete" /></a></td>
			
            {{if  product.visibleCode != null && product.visibleCode != "" }}
               <td class="{{= item.tdclass}}"><a href={{= "<c:url value="/" />"+product.url}}>{{= product.visibleCode}}</a></td>
            {{else}}
               <td class="{{= item.tdclass}}"><a href={{= "<c:url value="/" />"+product.url}}>{{= product.baseCode}}</a></td>
            {{/if}}

			{{if  productDescription != null && productDescription != "" }}
				<td class="{{= item.tdclass}}">{{= productDescription}}</td>
			{{else}}
				<td class="{{= item.tdclass}}">{{= product.name}}</td>
			{{/if}}

			<c:if test="${not cartData.hasAPIProducts }">
				<td class="col_center {{= item.tdclass}}">{{= product.piecesPerBundle}}</td>           	
			</c:if>
            <td class="col_center {{= item.tdclass}}">{{= formattedConvertedQuantity}}</td>
                   
			<td class="col_right {{= item.tdclass}}">{{= formattedWeight}}</td>                		

			<td class="{{= item.tdclass}}">
				{{= formattedLocation}}
			</td >
						
			<c:choose>
				<c:when test="${cartData.hasHSSProducts eq true}">
					<td class="sap-data col_right{{= item.tdclass}}">
						{{if  product.isHSS}}
							-
						{{else}}
							{{= formattedPricePerFeet}}
						{{/if}}	
					</td>
					<c:if test="${cartData.isInternalCartOrder }">
						<td class="sap-data col_right{{= item.tdclass}}">
							{{if  product.isHSS}}
								-
							{{else}}
								{{= formattedPricePerTon}}
							{{/if}}		
						</td>
					</c:if>
					<td class="sap-data col_right{{= item.tdclass}}">
						{{if  product.isHSS}}
							{{= formattedPricePerPc}}
						{{else}}
							-
						{{/if}}	
					</td>
				</c:when>
				<c:otherwise>
					<td class="sap-data col_right{{= item.tdclass}}">
						{{= formattedPricePerFeet}}	
					</td>
					
					
					<c:if test="${cartData.isInternalCartOrder }">
						<td class="sap-data col_right{{= item.tdclass}}">
						$	{{= formattedPricePerTon}}
							
						</td>
					</c:if>

			
					
				</c:otherwise>
			</c:choose>

			<c:if test="${cartData.isInternalCartOrder }">
				<td class="sap-data col_right{{= item.tdclass}}">
						$ <input entryNumber="{{= entryNumber}}" class="entryDisc" type="text" size="7" style="text-align:right;" valMin="{{= item.minNegPrice}}" valMax="{{= item.maxNegPrice}}" oriPrice="{{= pricePerTon}}" value="{{= pricePerTon}}"/>
							
				</td>
			</c:if>

			
			<td class="sap-data col_right {{= item.tdclass}}">
				{{= formattedNetPriceWOTaxes}}				
			</td>
		
			<td class="sap-data col_center {{= item.tdclass}}">
				{{= formattedReadyToShip}}			
			</td>
		</tr>        
	{{/each}}    
               
				<tr >
					<td></td>
					<td></td>
					<td class="attribute_col"></td>					

					<c:if test="${not cartData.hasAPIProducts}">
						<td class="attribute_col"></td>
					</c:if>
					
					<c:choose>
						<c:when test="${baseStore.uid eq '6000'}">
								<td class="col_right"></td>
								<td class="col_right"></td>
							</c:when>
						<c:otherwise>
								 <td class="col_right"><spring:theme code="order.totals.total" /></td>
								<td class="col_right number"><input  type="number" id="unitTemplateWeight" name="unitTemplateWeight"/></td>
						</c:otherwise>
					</c:choose>

					
                   

					<c:if test="${cartData.hasAPIProducts}">
						<td class="attribute_col"></td>
					</c:if>

					<td class="location_col"></td>								
					<td  class="attribute_col"></td>

					<c:if test="${cartData.hasHSSProducts eq true}">
						<td  class="attribute_col"></td>
					</c:if>

					<td class="attribute_col"></td>	
				<c:if test="${isInternalCustomer }">
					<td class="attribute_col"></td>
				</c:if>
				</tr>
</tbody>
</script>


<script id="iterateProductImagesTemplate" type="text/x-jquery-tmpl">
	{{each images}}
		{{if imageType=='PRIMARY' && format=='thumbnail'}}
			<img src={{= url}} alt={{= $item.name}} title={{= $item.name}}/>
		{{/if}}
	{{/each}}
</script>

<script id="iteratePotentialPromotionEntriesTemplate" type="text/x-jquery-tmpl">
{{each potentialProductPromotions}}
		{{tmpl($value,{entryNumber:$item.entryNumber,description:description}) '#iterateConsumePromotionEntriesTemplate'}}
{{/each}}
</script>


<script id="iterateAppliedPromotionEntriesTemplate" type="text/x-jquery-tmpl">
{{each appliedProductPromotions}}
		{{tmpl($value,{entryNumber:$item.entryNumber,description:description}) '#iterateConsumePromotionEntriesTemplate'}}
{{/each}}
</script>

<script id="iterateConsumePromotionEntriesTemplate" type="text/x-jquery-tmpl">
{{each consumedEntries}}
	{{if orderEntryNumber==$item.entryNumber && adjustedUnitPrice > 0 }}
		<li class="cart-promotions-potential"><span>{{= $item.description}}</span></li>
	{{/if}}
{{/each}}
</script>

<script id="baseOptionSelectedTemplate" type="text/x-jquery-tmpl">
	{{each baseOptions}}
 			{{if selected && selected.url==$item.productUrl}}
				{{each selected.variantOptionQualifiers}}
					<dl>
						<dt>{{= name}}:</dt>
						<dd>{{= value}}</dd>
					</dl>
				{{/each}}
			{{/if}}
	{{/each}}
</script>


<div class="span-24 clearfix last  place-order-top" >
	
	<c:url value="/cart" var="cartUrl"/>
	<spring:theme code="checkout.summary.continueShoppping.here" var="hereText"/>
	<p class="right"><spring:theme code="checkout.summary.continueShoppping" arguments="${ cartUrl},${hereText }"/></p>
</div>
<div class="item_container_holder">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="basket.page.title.yourItems"/></h2>
		<p><span class="cart_id"><spring:theme code="basket.page.cartId"/><span class="cart-id-nr">${cartData.code}</span></span></p>
	</div>
	
	<c:if test="${baseStore.priceNegotiationEnabled eq true}">
		<div id="globalMessages" style="margin-bottom: 3px; height: 37px;">
			<div class="span-24 negative globalMessagesPriceNeg" style="display: none; padding: 1px 0 1px 10px; width: 1180px;">
				<div class="information_message MessageError_Color_White">
					<span class="single"></span>
					<p id="messageErrorPriceNeg"></p>
				</div>
			</div>
		</div>
	</c:if>
	
	<div id="resultsList" data-isOrderForm="false" class="product_table">
		<!--NEORIS_CHANGE #74  Replaced accelerator chechout table-->
		
		<spring:theme code="product.list.header.wt-piece" var="wtPieceLabel" />
		<c:if test="${fn:contains(unit.code,'mt') || fn:contains(unit.code,'kg')}">
		   	<c:set value="(Kg)" var="wtLabel" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ft') || fn:contains(unit.code,'lb')}">
		   	<c:set value="(Lb)" var="wtLabel" />
		</c:if>
		
		<c:if test="${fn:contains(unit.code,'ton')}">
			<c:set value="(Ton)" var="wtLabel" />
		</c:if>
		<c:if test="${cartData.hasAPIProducts }">
			<c:if test="${fn:contains(currentSystem,'English')}">
			   	<c:set value="(Lb)" var="wtLabel" />
			</c:if>
			
			<c:if test="${fn:contains(currentSystem,'Metric')}">
			   	<c:set value="(Kb)" var="wtLabel" />
			</c:if>
			
			<c:if test="${fn:contains(unit.code,'ton')}">
				<c:set value="(Ton)" var="wtLabel" />
			</c:if>
		</c:if>		
		<c:choose >
			<c:when test="${cartData.hasAPIProducts }">
				<spring:theme code="cart.cartItems.header.qty.prolamsa_ft" var="qtyLabel" />
			</c:when>
			<c:otherwise>
				<spring:theme code="cart.cartItems.header.qty.${unit.code}" var="qtyLabel" />
			</c:otherwise>
		</c:choose>
		
		
		
		<table id="cartEntriesTable">
			<c:if test="${cartData.isInternalCartOrder }">
				<tr class="firstrow">
					<td></td>
					<td></td>
					<td></td>
					<c:if test="${not cartData.hasAPIProducts}" >
						<td></td>
					</c:if>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<c:if test="${cartData.hasHSSProducts eq true}">
					<td></td>
					</c:if>
					<td><spring:theme code="checkout.summary.items.discountAmount"/> </td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<c:if test="${not cartData.hasAPIProducts}" >
						<td></td>
					</c:if>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<c:if test="${cartData.hasHSSProducts eq true}">
					<td></td>
					</c:if>
					<td class="col_right">$ <input class="amountDisc validateMinMaxAllowZeroEmpty" type="text" size="7" style="text-align:right;" valMin="${baseStore.quoteNegotiationDiscountMin }" valMax="${baseStore.quoteNegotiationDiscountMax }" value=""/></td>
					<td></td>
					<td></td>
				</tr>
			</c:if>
			<tr class="firstrow">
				<td></td>
				<td><spring:theme code="checkout.summary.items.part_number" /></td>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.description" /></td>
				<c:if test="${not cartData.hasAPIProducts}" >
					<td class="attribute_col"><spring:theme code="checkout.summary.items.pcs_bundle" /></td>
				</c:if>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.quantity" /><br/>${qtyLabel}</td>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.weight" /><br/>${wtLabel}</td>
				<td class="location_col"><spring:theme code="checkout.summary.items.location" /></td>
				<td  class="attribute_col"><spring:theme code="checkout.summary.items.priceFT" /></td>
				<c:if test="${cartData.isInternalCartOrder }">
					<td  class="attribute_col"><spring:theme code="checkout.summary.items.priceTN" /></td>
				</c:if>
				
				<c:if test="${cartData.hasHSSProducts eq true}">
					<td  class="attribute_col"><spring:theme code="checkout.summary.items.price_per_pc" /></td>
				</c:if>
				
				<%--NEORIS_CHANAGE #SAP Unit Label  --%>
				<c:if test="${cartData.isInternalCartOrder }">
					<c:choose>
						<c:when test="${cartData.hasAPIProducts}">
							<td  class="attribute_col"><spring:theme code="checkout.summary.items.negotiablePrice" />&nbsp;<spring:theme code='summary.cartitems.per' />&nbsp;TN</td>
						</c:when>
						<c:otherwise>
							<td  class="attribute_col"><spring:theme code="checkout.summary.items.negotiablePrice" /> <input type="hidden" id="sapUnit" name="sapUnit" /></td>
						</c:otherwise>
					</c:choose>
				</c:if>				
				<td class="attribute_col"><spring:theme code="checkout.summary.items.net_price_wo_taxes" /></td>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.ready_to_ship" /></td>
			</tr>
			<tbody id="cartEntryTBody">
			
			    <c:set var="totalSAPWeight" value="0.000"/>			    
			
				<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
					<c:url value="${cartEntry.product.url}" var="productUrl"/>
					<c:set var="productInventoryEntry" value="${cartEntry.product.inventoryEntry}" />
					<tr class="prolamsa_product_row" entryNumber="${cartEntry.entryNumber}" hasChanged="false">
						<td><a class="deleteRowButton"><spring:theme code="checkout.summary.items.delete" /></a></td>
						<td><a href="${productUrl}">${cartEntry.product.visibleCode ne '' ?  cartEntry.product.visibleCode : cartEntry.product.baseCode}</a></td>
						<c:choose>
							<c:when test="${not empty cartEntry.productDescription}">
								<td>${cartEntry.productDescription}</td>
							</c:when>
							<c:otherwise>
								<td>${cartEntry.product.name}</td>
							</c:otherwise>
						</c:choose>
						<c:if test="${not cartData.hasAPIProducts }" >
							<td class="col_center"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${cartEntry.product.piecesPerBundle}" /></td>
						</c:if>
                        <td class="col_center"><fmt:formatNumber value="${cartEntry.quantity}" pattern="${quantityPattern}" /></td>
                        
                        <c:choose>
                        	<c:when test="${cartData.hasAPIProducts}">
                        		<c:choose>
									<c:when test="${fn:contains(currentSystem,'Metric')}">
										<td class="col_right"><fmt:formatNumber pattern="${weightNumberPattern}"  value="${ cartEntry.quantity*cartEntry.product.ftKgEquiv}"/></td>
										<c:set var="totalSAPWeight" value="${totalSAPWeight + cartEntry.quantity*cartEntry.product.ftKgEquiv}"/>
									</c:when>
									<c:when test="${fn:contains(currentSystem,'English')}">
										<td class="col_right"><fmt:formatNumber pattern="${weightNumberPattern}"  value="${cartEntry.quantity*cartEntry.product.ftLbEquiv}"/></td>
										<c:set var="totalSAPWeight" value="${totalSAPWeight + cartEntry.quantity*cartEntry.product.ftLbEquiv}"/>
									</c:when>
									<c:when test="${fn:contains(unit.code,'ton') }">
										<td class="col_right"><fmt:formatNumber pattern="${weightNumberPattern}" value="${ (cartEntry.quantity*cartEntry.product.ftTnEquiv)}"/></td>
										<c:set var="totalSAPWeight" value="${totalSAPWeight + (cartEntry.quantity*cartEntry.product.ftTnEquiv)}"/>
									</c:when>							
								</c:choose>
                        	</c:when>
                        	<c:otherwise>
                        		<c:choose>
									<c:when test="${fn:contains(unit.code,'kg') || fn:contains(unit.code,'mt') }">
										<td class="col_right"><fmt:formatNumber pattern="${weightNumberPattern}"  value="${ cartEntry.quantity*cartEntry.product.bundleKgEquiv}"/></td>
										<c:set var="totalSAPWeight" value="${totalSAPWeight + cartEntry.quantity*cartEntry.product.bundleKgEquiv}"/>
									</c:when>
									<c:when test="${fn:contains(unit.code,'lb') || fn:contains(unit.code,'ft') }">
										<td class="col_right"><fmt:formatNumber pattern="${weightNumberPattern}"  value="${cartEntry.quantity*cartEntry.product.bundleLbEquiv}"/></td>
										<c:set var="totalSAPWeight" value="${totalSAPWeight + cartEntry.quantity*cartEntry.product.bundleLbEquiv}"/>
									</c:when>
									<c:when test="${fn:contains(unit.code,'ton') }">
										<td class="col_right"><fmt:formatNumber pattern="${weightNumberPattern}" value="${ (cartEntry.quantity*cartEntry.product.tnEquiv)}"/></td>
										<c:set var="totalSAPWeight" value="${totalSAPWeight + (cartEntry.quantity*cartEntry.product.tnEquiv)}"/>
									</c:when>							
								</c:choose>
                        	</c:otherwise>
                        </c:choose>
                        
						<td>
							<spring:theme code="${cartEntry.product.location}" />
						</td>
							
						<td class="col_center sap-data ftPrice">
							
						</td>
						<c:if test="${isInternalCustomer }" >
							<td class="col_center sap-data tnPrice">
							
							</td>
						</c:if>
						
						<c:if test="${cartData.hasHSSProducts eq true}">
							<td class="col_center sap-data">
							</td>
						</c:if>
						
						<td class="col_center sap-data">
						
						</td>
		
						<td class="col_center sap-data">
						
						</td>
					</tr>
				</c:forEach>
				
				<tr >
				  <c:set var="colspanValue" value="12" />
				  <c:if test="${not isInternalCustomer }">
				  	<c:set var="colspanValue" value="9" />	
				  </c:if>
				  <c:if test="${baseStore.priceNegotiationEnabled eq true}">
					<c:set var="colspanValue" value="11" />
				  </c:if>
				  
				  <c:if test="${cartData.hasHSSProducts eq true}">
					<c:set var="colspanValue" value="${colspanValue + 1}" />
				  </c:if>
				  
				  <td colspan="${colspanValue}">
				      <hr/>
				  </td>
				</tr>
				<tr >
					<td></td>
					<td></td>
					<td class="attribute_col"></td>
					
					<c:if test="${not cartData.hasAPIProducts}">
						<td class="attribute_col"></td>
					</c:if>
					
					<%--Comentado Fase9 CILS
					<td class="col_right"><spring:theme code="order.totals.total" /></td>
					<td class="col_right"><fmt:formatNumber  pattern="${weightNumberPattern}" value="${totalSAPWeight}"  var="fmtTotal"/>${fmtTotal}</td> --%>
					<c:choose>
	<c:when test="${baseStore.uid eq '6000'}">
		<td class="col_right"></td>
		<td class="col_right"></td>
	</c:when>
	<c:otherwise>
		<td class="col_right"><spring:theme code="order.totals.total" /></td>
		<td class="col_right"><fmt:formatNumber  pattern="${weightNumberPattern}" value="${totalSAPWeight}"  var="fmtTotal"/>${fmtTotal}</td>
	</c:otherwise>
</c:choose>


					
					<c:if test="${cartData.hasAPIProducts}">
						<td class="attribute_col"></td>
					</c:if>
										
					<td class="location_col"></td>
					<%--NEORIS_CHANAGE #SAP Unit Label  --%>				
					<td  class="attribute_col"></td>
					
					<c:if test="${cartData.hasHSSProducts eq true}">
						<td class="attribute_col"></td>
				  	</c:if>
				  	
					<c:if test="${baseStore.priceNegotiationEnabled eq true}">
						<td class="attribute_col"></td>
				  	</c:if>									
					<td class="attribute_col"></td>
					<c:if test="${isInternalCustomer }">
						<td class="attribute_col"></td>
					</c:if>
				</tr>
				
			</tbody>
	</table>
	
	<input type="hidden" id="total"  pattern="#,###,###,###.000" name="total" value="${fmtTotal}" />
	
	<%-- <table >
	  <!--NEORIS_CHANGE #Adding total  -->  	            	            				
		<tr class="prolamsa_product_row">																		
			<td  id="tdLabel1"  width="91"  class="col_right"><spring:theme code="order.totals.total" /></td>								
			<td  id="tdLabel2"  width="71"  class="col_left">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber  pattern="#,###,###,###.###" value="${totalSAPWeight}"/></td>
		</tr> 
	</table> --%>
	
	</div>
</div>


<script type="text/javascript"> // set vars
	onDOMLoaded(initialize_SummaryCartItems);
	onDOMLoaded(bindInputPrice);
	function initialize_SummaryCartItems()
	{
		ACC.neorisproduct.checkForProductEntries = false;
		
		ACC.neorisproduct.messages.minMaxValidationOnError = "<spring:theme code="checkout.cart_summary.minMaxQuotePriceError" />";
		
	}
	function bindInputPrice()
	{
		$(".amountDisc").numericInput({
			allowFloat:true,
			allowNegative:true
		});
		
		
		$(".entryDisc").numericInput({
			allowFloat:true,
			allowNegative:false
		});

		
		$(".entryDisc").on("keyup",entryDiscChanged);
		
		$(".amountDisc").on("keyup",amountDiscChanged);
	}
	
	function entryDiscChanged()
	{
		var input = $(this);
		if(ACC.neorisMinMaxValidator.isInputValid(this) == false)
		{
			input.addClass("minMaxValidationOnError");
			if(input.hasClass("amountDisc"))
			{
				$(".doublePrice").addClass("minMaxValidationOnError");
			}
		}else
		{
			input.removeClass("minMaxValidationOnError");
			if(input.hasClass("amountDisc"))
			{
				$(".doublePrice").removeClass("minMaxValidationOnError");
			}
		}
		if(input.val().trim() == "")
			input.removeClass("minMaxValidationOnError");
		
	}
	
	function amountDiscChanged()
	{
		var amountDisc = parseFloat($(this).val());
		var amountDiscHasError = false;
		
		if(isNaN(amountDisc))
			amountDisc=0;
		
		if(ACC.neorisMinMaxValidator.isInputValid(this) == false)
		{
			$(this).addClass("minMaxValidationOnError");
			amountDiscHasError=true;
		}
		else
		{
			$(this).removeClass("minMaxValidationOnError");
			amountDiscHasError=false;
		}
		$.each($(".entryDisc"), function( index, value ) {
			
				if(amountDiscHasError == true)
				{
					$(value).addClass("minMaxValidationOnError");
					$(value).val(0);
				}
				else
				{
					var priceDiscount = parseFloat($(value).attr("oriPrice"))+amountDisc;
					  
					if(priceDiscount<=0 )
					{
						$(value).addClass("minMaxValidationOnError");
						$(value).val(0);
					}
					else
					{
						$(value).val(priceDiscount.toFixed(2));
						$(value).removeClass("minMaxValidationOnError");
					}
				}
			
				
					
			  
			  
			});
	}
	
</script>