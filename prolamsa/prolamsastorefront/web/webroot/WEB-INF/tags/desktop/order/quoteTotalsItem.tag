<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="containerCSS" required="false" type="java.lang.String" %>

<c:if test="${fn:contains(defaultUoM.code, 'kg') || fn:contains(defaultUoM.code, 'mt') }">
	<c:set value="kgs" var="currentUoM" />
</c:if>

<c:if test="${fn:contains(defaultUoM.code, 'lb') || fn:contains(defaultUoM.code, 'ft')}">
	<c:set value="lbs" var="currentUoM" />
</c:if>

<c:if test="${fn:contains(defaultUoM.code, 'ton')}">
	<c:set value="tons" var="currentUoM" />
</c:if>
	
<br/>

<table style="padding-right: 125px;">
	<tr>
		<td style="display: none;"></td>
		<c:set var="showPriceTotals" value="true"/>
		<c:if test="${baseStore.priceNegotiationEnabled and orderData.status eq 'PENDING_QUOTE'  }">
			<c:set var="showPriceTotals" value="false"/>
		</c:if>	
	
	
		<c:if test="${showPriceTotals}">
	   	 <td style="width: 52%; padding-left: 0px; width: 250px; vertical-align: top;">
			<div class="title_holder">
				<h2><spring:theme code="text.quotes.details.quoteTotals" text="Quote Totals"/></h2>
			</div>	
					  
			<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr" style="border-style: groove groove groove groove; width: 350px; padding-right: 35px;">
				<thead class="">
					<tr class="firstrow">
						<td class="balanceStatement-customer">
							<spring:theme code="checkout.summary.base_product" />
						</td>
						<td class="balanceStatement-customer">
							<spring:theme code="checkout.summary.total" />
						</td>
					</tr>
					<tr class="">
						<c:if test = "${order.hotRolledPrice > 0}" >
				  			<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.hot_rolled"></spring:theme>		</td>		
				  			<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedHotRolledPrice} &nbsp;${order.sapCurrency} </td>
				  		</c:if>		
					</tr>			
					<tr class="">
						<c:if test = "${order.coldRolledPrice > 0}" >
							<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.cold_rolled"></spring:theme>		</td>		
						  	<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedColdRolledPrice} &nbsp;${order.sapCurrency} </td>
						</c:if>		
					</tr>			
					<tr class="">
						<c:if test = "${order.galvanizedPrice > 0}" >
						  	<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.galvanized"></spring:theme>		</td>		
						  	<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedGalvanizedPrice} &nbsp;${order.sapCurrency}</td>
						</c:if>		
					</tr>			
					<tr class="">
						<c:if test = "${order.aluminizedPrice > 0}" >
						  	<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.aluminized"></spring:theme>		</td>		
						  	<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedAluminizedPrice} &nbsp;${order.sapCurrency}</td>
						</c:if>		
					</tr>
					<tr>
						<c:if test = "${order.galvametalPrice > 0}" >
						  	<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.galvametal"></spring:theme>		</td>		
						  	<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedGalvametalPrice} &nbsp;${order.sapCurrency}</td>
						</c:if>		
					</tr>
					<tr class="">
						<c:if test = "${order.totalDeliveryCost > 0}" >
						  <td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.totalDeliveryCost"></spring:theme>		</td>		
						  <td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedTotalDelvieryCost} &nbsp;${order.sapCurrency}</td>
						</c:if>		
					</tr>
					<tr class="">
						<c:if test = "${order.totalAssurance > 0}" >
						  <td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.totalAssurance"></spring:theme>		</td>		
						  <td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedTotalAssurance} &nbsp;${order.sapCurrency}</td>
						</c:if>		
					</tr>
					<c:if test="${baseStore.name eq 'Prolamsa Mex'}">
						<c:if test = "${order.sapSubtotalPrice > 0}" >
							<tr class="">
								<td class="balanceStatement-customer noBorder">------------------------------------</td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">------------------------------------</td>					
							</tr>
							<tr class="">
								<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.subtotal"></spring:theme></td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedSapSubtotalPrice} &nbsp;${order.sapCurrency}</td>					
							</tr>
						</c:if>
					</c:if>
					<c:if test="${baseStore.name eq 'Prolamsa A4C'}">
						<c:if test = "${order.sapSubtotalPrice > 0}" >
							<tr class="">
								<td class="balanceStatement-customer noBorder">------------------------------------</td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">------------------------------------</td>					
							</tr>
							<tr class="">
								<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.subtotal"></spring:theme></td>
								<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedSapSubtotalPrice} &nbsp;${order.sapCurrency}</td>					
							</tr>
						</c:if>
					</c:if>
					<tr class="">
						<c:if test = "${order.totalTaxas > 0}" >
						  <td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.totalTaxas"></spring:theme>		</td>		
						  <td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedTotalTaxas} &nbsp;${order.sapCurrency}</td>
						</c:if>		
					</tr>
					<tr class="">
						<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.totals"></spring:theme></td>
						<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedSapTotalPrice} &nbsp;${order.sapCurrency}</td>					
					</tr>
				</thead>
			</table>
		</td>
	     </c:if>  
	    <td style="width: 48%; padding-right: 0px; width: 250px; vertical-align: top;">
	    	<div class="title_holder">
				<h2><spring:theme code="text.quotes.details.summaryTotals" text="Quote Totals"/></h2>
			</div>	
					  
			<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr" style="border-style: groove groove groove groove; width: 350px; padding-right: 35px;">
				<thead class="">
					<tr class="firstrow">
						<td class="balanceStatement-customer">
							<spring:theme code="checkout.summary.base_product" />
						</td>
						<td class="balanceStatement-customer">
							<spring:theme code="checkout.summary.weight" />
						</td>
					</tr>
					
					<c:set var="weightUnit" value="${order.sapWeightUnit}"></c:set>
					<c:if test="${empty weightUnit}">
					<!-- Avoiding display weight without units -->
						<c:set var="weightUnit" value="KG"></c:set>
						<c:if test="${baseStore.uid eq '2000'}">
							<c:set var="weightUnit" value="LB"></c:set>
						</c:if>
					</c:if>
					
					<tr class="">
						<c:if test = "${order.hotRolledWeight > 0}" >
			  				<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.hot_rolled"></spring:theme>		</td>		
			  				<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedHotRolledWeight} &nbsp; ${weightUnit}</td>
			  			</c:if>		
					</tr>			
					<tr class="">
						<c:if test = "${order.coldRolledWeight > 0}" >
							<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.cold_rolled"></spring:theme>		</td>		
						  	<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedColdRolledWeight} &nbsp; ${weightUnit}</td>
						</c:if>		
					</tr>			
					<tr class="">
						<c:if test = "${order.galvanizedWeight > 0}" >
					  		<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.galvanized"></spring:theme>		</td>		
					  		<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedGalvanizedWeight} &nbsp; ${weightUnit}</td>
					  	</c:if>		
					</tr>			
					<tr class="">
						<c:if test = "${order.aluminizedWeight > 0}" >
					  		<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.aluminized"></spring:theme>		</td>		
					  		<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedAluminizedWeight} &nbsp; ${weightUnit}</td>
					  	</c:if>		
					</tr>
					<tr class="">
						<td class="balanceStatement-customer noBorder"><spring:theme code="checkout.summary.totals"></spring:theme></td>
						<td class="balanceStatement-customer noBorder" style="text-align: right">${order.formattedTotalWeight} &nbsp; ${weightUnit}</td>					
					</tr>
				</thead>
			</table>
		</td>
	</tr>
</table>
