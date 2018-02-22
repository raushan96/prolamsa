<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<script id="summaryOrderTemplate" type="text/x-jquery-tmpl">
	<c:choose>
		<c:when test="${baseStore.uid eq '6000'}">
			<dl class="order_totals" id="summary_order_dl">
			 {{if totalWeight != null }} 
				<%--
			{{if hotRolledWeight != 0}}
  		      <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>
		      <dd>{{= formattedHotRolledWeight}}   Ton</dd>
            {{/if}}

            {{if coldRolledWeight != 0}}
              <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>
			  <dd>{{= formattedColdRolledWeight}}  Ton</dd>
            {{/if}}

            {{if galvanizedWeight != 0}}
		      <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
		      <dd>{{= formattedGalvanizedWeight }}   Ton</dd>
            {{/if}}

            {{if aluminizedWeight != 0}}
              <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd>{{= formattedAluminizedWeight }}  Ton</dd>
            {{/if}}

			{{if galvametalWeight != 0}}
              <dt><spring:theme code="checkout.summary.galvametal"></spring:theme></dt>
			  <dd>{{= formattedGalvametalWeight }}   Ton</dd>
            {{/if}}
			--%>

				<dt><spring:theme code="checkout.summary.totals"></spring:theme></dt>
				<dd>{{= totalWeight }}   Ton</dd>
			 {{/if}}
			</dl>
		</c:when>
		<c:otherwise>
			<dl class="order_totals" id="summary_order_dl">
			 {{if totalWeight != null }} 
				{{if hotRolledWeight != 0}}
				  <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>
				  <dd>{{= formattedHotRolledWeight}}   {{= sapWeightUnit}}</dd>
				{{/if}}

				{{if coldRolledWeight != 0}}
				  <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>
				  <dd>{{= formattedColdRolledWeight}}   {{= sapWeightUnit}}</dd>
				{{/if}}

				{{if galvanizedWeight != 0}}
				  <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
				  <dd>{{= formattedGalvanizedWeight }}   {{= sapWeightUnit}}</dd>
				{{/if}}

				{{if aluminizedWeight != 0}}
				  <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
				  <dd>{{= formattedAluminizedWeight }}   {{= sapWeightUnit}}</dd>
				{{/if}}

				{{if galvametalWeight != 0}}
				  <dt><spring:theme code="checkout.summary.galvametal"></spring:theme></dt>
				  <dd>{{= formattedGalvametalWeight }}   {{= sapWeightUnit}}</dd>
				{{/if}}

				<dt><spring:theme code="checkout.summary.totals"></spring:theme></dt>
				<dd>{{= formattedTotalWeight }}   {{= sapWeightUnit}}</dd>
			 {{/if}}
			</dl>
		</c:otherwise>
	</c:choose>
</script>

<div class="item_container_holder order-totals">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="checkout.order_summary" /></h2>
	</div>
	<div class="item_container" id="summary_order_div" style="padding-left: 0px; padding-right: 0px;">
		<table style="margin: 0px; padding: 0px;">
		<tr class="firstrow">
			<td>
				<spring:theme code="checkout.summary.base_product"></spring:theme>
			</td>
			<td>
				<spring:theme code="checkout.summary.weight"></spring:theme>
			</td>
		</tr>
		
		</table>
		<dl class="order_totals" id="summary_order_dl">

			<%-- <dt><spring:theme code="checkout.summary.base_product"></spring:theme></dt>
			<dd style="text-align: center;"><spring:theme code="checkout.summary.weight"></spring:theme></dd> --%>
			  
			<c:if test = "${cartData.hotRolledWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.hot_rolled"></spring:theme></dt>
			  <dd></dd>
			</c:if>
			
			<c:if test = "${cartData.coldRolledWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.cold_rolled"></spring:theme></dt>
			  <dd></dd>
			</c:if>
			
		     <c:if test = "${cartData.galvanizedWeight > 0}" >
			   <dt><spring:theme code="checkout.summary.galvanized"></spring:theme></dt>
			   <dd></dd>
			</c:if>
			
			<c:if test = "${cartData.aluminizedWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd></dd>
			</c:if>
			
			<c:if test = "${cartData.galvametalWeight > 0}" >
			  <dt><spring:theme code="checkout.summary.aluminized"></spring:theme></dt>
			  <dd></dd>
			</c:if>
			
			<%-- <dt><spring:theme code="checkout.summary.totals"></spring:theme></dt>
			<dd></dd> --%>
		</dl>
		
	</div>
</div>
