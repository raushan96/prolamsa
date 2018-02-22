<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ attribute name="isOrderDetailsPage" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>
<!-- Added variable to activate or deactivate functionality to apply colors to cart lines -->
	<c:set var="hasColorCartLines" value="false" />
	<fmt:setLocale value="en_US" scope="session"/>
	<br />
	  <%-- <div>
		<p><spring:theme code="text.quotes.details.quoteNumberDisplay" text="Quote number is {0}" arguments="${order.code}"/></p>
		<p><spring:theme code="text.quotes.details.orderPlacedDate" />&nbsp; <formDate:formFormatDate value="${order.created}" pattern="MM/dd/yyyy"/></p>
		<c:if test="${not empty order.statusDisplay}">
			<spring:theme code="text.account.order.status.display.${order.statusDisplay}" var="orderStatus"/>
			<p><spring:theme code="text.quotes.details.orderStatus" text="The quote status is {0}" arguments="${orderStatus}"/></p>
		</c:if>
		<p><spring:theme code="text.account.order.unitWhenPlaced"/>&nbsp;${order.unitWhenPlaced.name}</p>

        </div>
        
        <br /> --%>
		<table id="your_order">
		<!-- NEORIS_CHANGE #74 Change default table for a custom table with SAP data -->
			<tr class="firstrow">
				<td class="col_center" style="padding-left: 0px !important;"><spring:theme code="checkout.summary.items.sap_line_item" /></td>
				<td><spring:theme code="checkout.summary.items.part_number" /></td>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.description" /></td>
				
				<c:if test="${empty order.isAPIOrder || order.isAPIOrder eq false}">
					<td class="col_center"><spring:theme code="checkout.summary.items.pcs_bundle" /></td>
				</c:if>
				
				<c:choose>
					<c:when test="${order.isAPIOrder eq true}">
						<c:set value="(Ft)" var="wtLabel" />
						<td class="col_center"><spring:theme code="checkout.summary.items.quantity" /><br/>${wtLabel}</td>
					</c:when>
					<c:otherwise>
						<td class="col_center">
							<spring:theme code="checkout.summary.items.quantity" />
							<c:if test="${order.isHSSOrder eq true}">
								<br/>
								<spring:theme code="checkout.summary.items.quantity.pieces" />
							</c:if>
						</td>					
					</c:otherwise>
				</c:choose>
				
				<td class="col_center"><spring:theme code="checkout.summary.items.weight" /></td>
				<td class="location_col"><spring:theme code="checkout.summary.items.location" /></td>
				<c:choose>
					<c:when test="${baseStore.priceNegotiationEnabled and orderData.status eq 'PENDING_QUOTE' }">
						<td class="attribute_col"><spring:theme code="checkout.summary.items.negotiablePrice" /></td>
					</c:when>
					<c:when test="${order.hasNegPrices }">
						<c:choose>
							<c:when test="${order.isAPIOrder eq true}">
								<td class="attribute_col"><spring:theme code="checkout.summary.items.price_per" />&nbsp;FT</td>
							</c:when>
							<c:when test="${order.isHSSOrder eq true}">
								<td class="attribute_col"><spring:theme code="checkout.summary.items.price_per_pc" /></td>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${not empty order.unidadPrecio}">
										<td class="attribute_col"><spring:theme code="checkout.summary.items.price_per" />&nbsp; ${order.unidadPrecio}</td>
									</c:when>
									<c:otherwise>
										<td class="attribute_col"><spring:theme code="checkout.summary.items.price" /></td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:if test="${baseStore.uid eq '6000' and isInternalCustomer }">
							<td class="col_center"><spring:theme code="checkout.summary.items.priceTN" /></td>
						</c:if>
						<td class="attribute_col"><spring:theme code="checkout.summary.items.negotiablePrice" /></td>
						<td class="attribute_col"><spring:theme code="checkout.summary.items.net_price_wo_taxes" /></td>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${order.isAPIOrder eq true}">
								<td class="attribute_col"><spring:theme code="checkout.summary.items.price_per" />&nbsp;FT</td>
							</c:when>
							<c:when test="${order.isHSSOrder eq true}">
								<td class="attribute_col"><spring:theme code="checkout.summary.items.price_per_pc" /></td>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${not empty order.unidadPrecio}">
										<td class="attribute_col"><spring:theme code="checkout.summary.items.price_per" />&nbsp; ${order.unidadPrecio}</td>
									</c:when>
									<c:otherwise>
										<td class="attribute_col"><spring:theme code="checkout.summary.items.price" /></td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:if test="${baseStore.uid eq '6000' and isInternalCustomer }">
							<td class="col_center"><spring:theme code="checkout.summary.items.priceTN" /></td>
						</c:if>
						<td class="attribute_col"><spring:theme code="checkout.summary.items.net_price_wo_taxes" /></td>
					</c:otherwise>
				</c:choose>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.ready_to_ship" /></td>
				<td class="attribute_col"><spring:theme code="checkout.summary.items.status" /></td>
			</tr>
			<tbody id="orderEntryTBody">
				<c:forEach items="${order.entries}" var="orderEntry" varStatus="status">
					<c:url value="${orderEntry.product.url}" var="productUrl"/>
					<c:set var="productInventoryEntry" value="${orderEntry.product.inventoryEntry}" />
					
					<c:set value="" var="trClass"/>
					<c:set value="" var="tdClass"/>
					<c:choose>
						<c:when test="${hasColorCartLines eq false}">
							<c:set value="" var="trClass"/>
							<c:set value="" var="tdClass"/>
						</c:when>
						<c:when test="${orderEntry.isTransportationGroupFull}">
							<c:set value="groupCompleted" var="trClass"/>
						</c:when>
						<c:otherwise>
							<c:set value="groupUncompleted" var="trClass"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${orderEntry.isLastOnTraspotation and  hasColorCartLines eq true}">
						<c:set value="lastInGroup" var="tdClass"/>
					</c:if>
					
					<tr class="prolamsa_product_row ${trClass}" entryNumber="${orderEntry.sapEntryNumber}" hasChanged="false">
						<td class="${tdClass} col_center" style="padding-left: 0px !important;"><fmt:formatNumber pattern="000000" value="${orderEntry.sapEntryNumber}" /></td>
						<c:choose>
							<c:when test="${baseStore.allowCategoryVisibility}">
								<c:choose>
									<c:when test="${orderEntry.product.approvalVisibility eq 'Y'}">
										<td class="${tdClass}"><a href="${productUrl}">${orderEntry.product.visibleCode}</a></td>
									</c:when>
									<c:otherwise>
										<td class="${tdClass}">${orderEntry.product.visibleCode}</td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td class="${tdClass}"><a href="${productUrl}">${orderEntry.product.visibleCode}</a></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty orderEntry.productDescription }">
								<td class="${tdClass}">${orderEntry.productDescription}</td>
							</c:when>
							<c:otherwise>
								<td class="${tdClass}">${orderEntry.product.name}</td>
							</c:otherwise>
						</c:choose>
						
						<c:if test="${empty order.isAPIOrder || order.isAPIOrder eq false}">
							<td class="${tdClass} col_center"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.product.piecesPerBundle}"/></td>
						</c:if>
						
						<c:choose>
 					   		<c:when test="${order.isHSSOrder eq true}">
 					   			<td class="${tdClass} col_center"><fmt:formatNumber pattern="###,###,###,###,##0"  value="${orderEntry.quantity*orderEntry.product.piecesPerBundle}" /></td>
 					   		</c:when>
 					   		<c:otherwise>
		 					   	<td class="${tdClass} col_center"><fmt:formatNumber pattern="${orderQtyPattern}"  value="${orderEntry.convertedQuantity}" /></td>		 					   	
 					   		</c:otherwise>
 					   	</c:choose>
 					   
 					   <c:choose>
							<c:when test="${orderEntry.isQuantityInPieces eq true}">
								<c:choose>
									<c:when test="${fn:contains(order.unitWhenPlaced.code,'kg') || fn:contains(order.unitWhenPlaced.code,'mt')}">
										<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}"  value="${orderEntry.quantity*orderEntry.product.pcKgEquiv}"/> Kgs</td>
									</c:when>
									<c:when test="${fn:contains(order.unitWhenPlaced.code,'lb') || fn:contains(order.unitWhenPlaced.code,'ft')}">
										<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}"  value="${orderEntry.quantity*orderEntry.product.pcLbEquiv}"/> Lbs</td>
									</c:when>
									<c:when test="${fn:contains(order.unitWhenPlaced.code,'ton') }">
										<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}" value="${(orderEntry.product.tnEquiv/orderEntry.product.piecesPerBundle*orderEntry.quantity)}"/> Tons</td>
									</c:when>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${order.isAPIOrder eq true}">
										<c:choose>
											<c:when test="${fn:contains(order.unitWhenPlaced.code,'kg') || fn:contains(order.unitWhenPlaced.code,'mt')}">
												<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}"  value="${ orderEntry.quantity*orderEntry.product.ftKgEquiv}"/> Kgs</td>
											</c:when>
											<c:when test="${fn:contains(order.unitWhenPlaced.code,'lb') || fn:contains(order.unitWhenPlaced.code,'ft')}">
												<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}"  value="${orderEntry.quantity*orderEntry.product.ftLbEquiv}"/> Lbs</td>
											</c:when>
											<c:when test="${fn:contains(order.unitWhenPlaced.code,'ton') }">
												<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}" value="${(orderEntry.quantity*orderEntry.product.ftTnEquiv)}"/> Tons</td>
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${fn:contains(order.unitWhenPlaced.code,'kg') || fn:contains(order.unitWhenPlaced.code,'mt')}">
												<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}"  value="${ orderEntry.quantity*orderEntry.product.bundleKgEquiv}"/> Kgs</td>
											</c:when>
											<c:when test="${fn:contains(order.unitWhenPlaced.code,'lb') || fn:contains(order.unitWhenPlaced.code,'ft')}">
												<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}"  value="${orderEntry.quantity*orderEntry.product.bundleLbEquiv }"/> Lbs</td>
											</c:when>
											<c:when test="${fn:contains(order.unitWhenPlaced.code,'ton') }">
												<td class="${tdClass} col_right"><fmt:formatNumber pattern="${orderWeightPattern}" value="${ (orderEntry.quantity*orderEntry.product.tnEquiv)}"/> Tons</td>
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
					   </c:choose>
					   
						<td class="${tdClass}">
							<spring:theme code="${orderEntry.product.location}" />
						</td>
						
						<c:choose>
							<c:when test="${baseStore.priceNegotiationEnabled and orderData.status eq 'PENDING_QUOTE' }">
								<td class="sap-data ${tdClass } col_right">
									
									<fmt:formatNumber pattern="$ ###,###,###.00"	value="${orderEntry.negotiablePrice}"/>
								</td>
							</c:when>
							<c:when test="${order.hasNegPrices }">
								<c:choose>
									<c:when test="${order.isHSSOrder eq true}">
										<td class="sap-data ${tdClass } col_right">
										   ${orderEntry.formattedPricePerPc}							
										</td >
									</c:when>
									<c:otherwise>
										<td class="sap-data ${tdClass } col_right">
										   ${orderEntry.formattedPricePerFeet}							
										</td >
									</c:otherwise>
								</c:choose>
								<c:if test="${baseStore.uid eq '6000' and isInternalCustomer }">
									<td class="sap-data ${tdClass } col_right">
										<c:if test="${not empty  orderEntry.pricePerTon}">
											<fmt:formatNumber pattern="$ ###,###,###.00"	value="${orderEntry.pricePerTon}"/>
										</c:if>
									</td>
								</c:if>
								<td class="sap-data ${tdClass } col_right">
									<c:if test="${not empty  orderEntry.negotiablePrice}">
										<fmt:formatNumber pattern="$ ###,###,###.00"	value="${orderEntry.pricePerTon + orderEntry.negotiablePrice}"/>
									</c:if>
								</td>
								<td class="col_right sap-data ${tdClass }">
									${orderEntry.formattedNetPriceWOTaxes}
								</td>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${order.isHSSOrder eq true}">
										<td class="sap-data ${tdClass } col_right">
										   ${orderEntry.formattedPricePerPc}							
										</td >
									</c:when>
									<c:otherwise>
										<td class="sap-data ${tdClass } col_right">
										   ${orderEntry.formattedPricePerFeet}							
										</td >
									</c:otherwise>
								</c:choose>
								<c:if test="${baseStore.uid eq '6000' and isInternalCustomer }">
									<td class="sap-data ${tdClass } col_right">
										<c:if test="${not empty  orderEntry.pricePerTon}">
											<fmt:formatNumber pattern="$ ###,###,###.00"	value="${orderEntry.pricePerTon}"/>
										</c:if>
									</td>
								</c:if>
								<td class="col_right sap-data ${tdClass }">
									${orderEntry.formattedNetPriceWOTaxes}
								</td>
							</c:otherwise>
						</c:choose>
		
						<td class="col_center sap-data ${tdClass }">
							${orderEntry.formattedReadyToShip}
							
							<%--<fmt:parseDate value="${orderEntry.formattedReadyToShip}" pattern="MM/dd/yyyy" var="myDate"/>
							<formDate:formFormatDate pattern="MM/dd/yyyy" value="${myDate}" />
							--%>
						</td>
						
						<td class="col_center sap-data ${tdClass }">
							${orderEntry.status}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
<script type="text/javascript"> // set vars
	onDOMLoaded(initialize_OrderDetailsItem);
	
	function initialize_OrderDetailsItem()
	{
		ACC.neorisproduct.checkForProductEntries = false;
	}
</script>
