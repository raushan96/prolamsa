<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="deliveryAddress" required="true" type="de.hybris.platform.commercefacades.user.data.AddressData" %>
<%@ attribute name="costCenter" required="true" type="de.hybris.platform.b2bacceleratorfacades.order.data.B2BCostCenterData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout/single" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<spring:url value="/checkout/single/summary/getDeliveryAddresses.json" var="getDeliveryAddressesUrl" />
<spring:url value="/checkout/single/summary/setDeliveryAddress.json" var="setDeliveryAddressUrl" />
<spring:url value="/checkout/single/summary/getDeliveryAddressForm.json" var="getDeliveryAddressFormUrl" />
<spring:url value="/checkout/single/summary/setDefaultAddress.json" var="setDefaultAddressUrl" />
<spring:url value="/_ui/desktop/common/images/spinner.gif" var="spinnerUrl" />

<script type="text/javascript"> // set vars
	var getDeliveryAddressesUrl = '${getDeliveryAddressesUrl}';
	var getDeliveryAddressFormUrl = '${getDeliveryAddressFormUrl}';
	var setDeliveryAddressUrl = '${setDeliveryAddressUrl}';
	var setDefaultAddressUrl = '${setDefaultAddressUrl}';
	var spinnerUrl = '${spinnerUrl}';		
</script>

	<!-- NEORIS_CHANGE #74 Change on template-->
<script id="deliveryAddressSummaryTemplate" type="text/x-jquery-tmpl">
				<ul id="deliveryAddressInfo">
					{{if deliveryAddress}}
						<li>{{= deliveryAddress.line1}}</li>
						<li>{{= deliveryAddress.line2}}</li>						
						<li>{{= deliveryAddress.postalCode}}</li>					                      
						<li>{{= deliveryAddress.town}},	
                            {{= deliveryAddress.region.name}},
                            {{= deliveryAddress.country.name}}</li>
					{{else}}
						<li><spring:theme code="checkout.summary.deliveryAddress.noneSelected"/></li>
					{{/if}}
				</ul>
</script>



<script id="deliveryAddressesTemplate" type="text/x-jquery-tmpl">
	{{if !addresses.length}}
		<spring:theme code="checkout.summary.deliveryAddress.noExistingAddresses"/>
	{{/if}}
	{{if addresses.length}}
		<form>
			{{each addresses}}
				<div class="existing_address">
					<div class="left">
						<div class="checkmark">
							{{if defaultAddress}}
								<theme:image code="img.iconSelected" alt="Selected"/>
							{{/if}}
						</div>
						{{if $value.editable}}
							<button class="form left edit" data-address-id="{{= $value.id}}"><spring:theme code="checkout.summary.deliveryAddress.edit"/></button>
						{{/if}}
					</div>
					<ul>
						<li>{{= $value.title}}&nbsp;{{= $value.firstName}}&nbsp;{{= $value.lastName}}</li>
						<li>{{= $value.line1}}</li>
						<li>{{= $value.line2}}</li>
                        <li>{{= $value.postalCode}}</li>
						<li>{{= $value.town},{= $value.region.name},{= $value.country.name}}</li>				                        
					</ul>
					{{if !(defaultAddress) && $value.editable}}
						<button class="form right default" data-address="{{= $value.id}}"><spring:theme code="text.setDefault" text="Set as default" /></button>
					{{/if}}
					<button class="positive right pad_left use_address" data-address-id="{{= $value.id}}"><spring:theme code="checkout.summary.deliveryAddress.useThisAddress"/></button>
				</div>
			{{/each}}
		</form>
	{{/if}}
</script>

<c:set value="${not empty deliveryAddress}" var="deliveryAddressOk"/>
<div class="checkout_summary_flow_c complete" id="checkout_summary_deliveryaddress_div">
	<div class="item_container_holder">
		<ycommerce:testId code="checkout_deliveryAddressData_text">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2><spring:theme code="checkout.summary.deliveryAddress.header" htmlEscape="false"/><span></span></h2>
			</div>
			
			<!-- NEORIS_CHANGE #74 -->
			<div  class="item_container">
				
				<select id="deliveryAddressSelect">
					<c:if test="${fn:length(deliveryAddresses) gt 1}">
						<option selected="selected" value="none"><spring:theme code="checkout.summary.delivery_addres.select_address" /> </option>
					</c:if>
					<c:forEach items="${ deliveryAddresses}" var="eachDeliveryAddress">
						<c:set value="" var="selected"></c:set>
						<c:if test="${deliveryAddress.code eq eachDeliveryAddress.code}" >
							<c:set value="selected" var="selected"></c:set>	
						</c:if>
						<%-- <option value="${eachDeliveryAddress.code }" ${selected }>${eachDeliveryAddress.formattedAddress}</option> --%>
						<c:choose>
							<c:when test="${baseStore.name eq 'Prolamsa Mex'}">
								<option value="${eachDeliveryAddress.code }" ${selected }>(${eachDeliveryAddress.code}) - ${eachDeliveryAddress.line1} , ${eachDeliveryAddress.town}</option>
							</c:when>
							<c:when test="${baseStore.name eq 'Prolamsa USA'}">
								<option value="${eachDeliveryAddress.code }" ${selected }>${eachDeliveryAddress.name} - ${eachDeliveryAddress.line1} - (${eachDeliveryAddress.code})</option>
							</c:when>
							<c:otherwise>
								<option value="${eachDeliveryAddress.code }" ${selected }>${eachDeliveryAddress.name} - ${eachDeliveryAddress.code} - (${eachDeliveryAddress.line1})</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			
			   <br />
			   
			   <a id="advancedAddressSearchLink" href="#"  ><spring:theme code="advanced-address-search.title"/></a>
			    <ul id="deliveryAddressInfo">
					<c:choose>
						<c:when test="${deliveryAddressOk}">
							<li>${fn:escapeXml(deliveryAddress.line1)}</li>
							<li>${fn:escapeXml(deliveryAddress.line2)}</li>
							<li>${fn:escapeXml(deliveryAddress.postalCode)}</li>
							<li>${fn:escapeXml(deliveryAddress.town)},${fn:escapeXml(deliveryAddress.region.name)},${fn:escapeXml(deliveryAddress.country.name)}</li>
						</c:when>
						<c:otherwise>
							<li><spring:theme code="checkout.summary.deliveryAddress.noneSelected"/></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</ycommerce:testId>
	</div>

	<!-- NEORIS_CHANGE #74 Removed Edit Addres Button. -->
</div>

<checkout:deliveryAddressPopup />


