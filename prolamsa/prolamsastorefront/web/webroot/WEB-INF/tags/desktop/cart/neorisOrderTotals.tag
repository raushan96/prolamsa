<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<script id="totalsOrderTemplate" type="text/x-jquery-tmpl">

	
	  <dl class="order_totals" id="totals_order_dl">
	     {{if sapTotalPrice != null }} 
            {{if hotRolledPrice != 0}}
			  <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>            
			  <dd>{{= formattedHotRolledPrice}}   {{= sapCurrency}}</dd>
            {{/if}}

            {{if coldRolledPrice != 0}}
              <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>            
			  <dd>{{= formattedColdRolledPrice}}   {{= sapCurrency}}</dd>
            {{/if}}

            {{if galvanizedPrice != 0}}
		      <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
		      <dd>{{= formattedGalvanizedPrice}}   {{= sapCurrency}}</dd>
            {{/if}}

            {{if aluminizedPrice != 0}}
              <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd>{{= formattedAluminizedPrice}}   {{= sapCurrency}}</dd>
            {{/if}}

			{{if galvametalPrice != 0}}
              <dt><spring:theme code="checkout.summary.galvametal"></spring:theme></dt>
			  <dd>{{= formattedGalavametalPrice}}   {{= sapCurrency}}</dd>
            {{/if}}

			{{if totalDeliveryCost > 0}}
              <dt><spring:theme code="checkout.summary.totalDeliveryCost"></spring:theme></dt>
			  <dd>{{= formattedTotalDelvieryCost}}   {{= sapCurrency}}</dd>
            {{/if}}

			{{if totalAssurance > 0 }}
              <dt><spring:theme code="checkout.summary.totalAssurance"></spring:theme></dt>
			  <dd>{{= formattedTotalAssurance}}   {{= sapCurrency}}</dd>
            {{/if}}

			<c:if test="${baseStore.name eq 'Prolamsa Mex'}">
			{{if sapSubtotalPrice != 0}}
			 <dt>_____________________</dt>
			 <dd>_____________________</dd>
			 <dt>Subtotal</dt>
			 <dd>{{= formattedSapSubtotalPrice}}   {{= sapCurrency}}</dd>
			 {{/if}}
			</c:if>

			<c:if test="${baseStore.name eq 'Prolamsa A4C'}">
			{{if sapSubtotalPrice != 0}}
			 <dt>_____________________</dt>
			 <dd>_____________________</dd>
			 <dt>Subtotal</dt>
			 <dd>{{= formattedSapSubtotalPrice}}   {{= sapCurrency}}</dd>
			 {{/if}}
			</c:if>

			{{if totalTaxas > 0 }}
              <dt><spring:theme code="checkout.summary.totalTaxas"></spring:theme></dt>
			  <dd>{{= formattedTotalTaxas}}   {{= sapCurrency}}</dd>
            {{/if}}

			<dt><spring:theme code="checkout.summary.totals"></spring:theme></dt>
			<dd>{{= formattedSapTotalPrice}}   {{= sapCurrency}}</dd>
		   {{/if}}
		</dl>
	

</script>



<div class="item_container_holder order-totals">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="checkout.order_totals" /></h2>
	</div>
	<div class="item_container" id="summary_order_div" style="padding-left: 0px; padding-right: 0px;">
		<table style="margin: 0px; padding: 0px;">
		<tr class="firstrow">
			<td>
				<spring:theme code="checkout.summary.base_product"></spring:theme>
			</td>
			<td>
				<spring:theme code="checkout.summary.total"></spring:theme>
			</td>
		</tr>
		
		</table>
		<dl class="order_totals" id="totals_order_dl">
			<%-- <dt><spring:theme code="checkout.summary.base_product"></spring:theme></dt>
			<dd style="text-align: center;"><spring:theme code="checkout.summary.total"></spring:theme></dd>	 --%>								
			
		    <c:if test = "${cartData.hotRolledPrice > 0}" >
			  <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>					
			  <dd></dd>		
			</c:if>		
			
			 <c:if test = "${cartData.coldRolledPrice > 0}" >		
			  <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>			
			  <dd></dd>
			</c:if>
					
			<c:if test = "${cartData.galvanizedPrice > 0}" >												
			  <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
			  <dd></dd>
			</c:if>
			
			<c:if test = "${cartData.aluminizedPrice > 0}" >	
			  <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd></dd>
			</c:if>
						
			<%-- <dt><spring:theme code="checkout.summary.totals"></spring:theme>we</dt>
			<dd></dd> --%>
			
		</dl>
	</div>
</div>
