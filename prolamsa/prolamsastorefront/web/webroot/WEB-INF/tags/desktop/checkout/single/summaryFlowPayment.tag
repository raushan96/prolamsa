<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout/single" %>

<spring:url value="/checkout/single/summary/getSavedCards.json" var="getSavedCardsUrl" />
<spring:url value="/checkout/single/summary/setPaymentDetails.json" var="setPaymentDetailsUrl"/>
<spring:url value="/checkout/single/summary/getPaymentDetailsForm.json" var="getPaymentDetailsFormUrl"/>
<spring:url value="/_ui/desktop/common/images/spinner.gif" var="spinnerUrl" />
<script type="text/javascript"> // set vars

	var securityWhatText = "<spring:theme code='checkout.summary.paymentMethod.securityCode.whatIsThis.description'/>";
	var getPaymentDetailsFormUrl = '${getPaymentDetailsFormUrl}';
	var getSavedCardsUrl = '${getSavedCardsUrl}';
	var spinnerUrl = '${spinnerUrl}';
	var setPaymentDetailsUrl = '${setPaymentDetailsUrl}';

</script>


<script id="paymentSummaryTemplate" type="text/x-jquery-tmpl">

	<div class="checkout_summary_flow_b {{if paymentInfo}}complete{{/if}}" id="checkout_summary_payment_div">
		<div class="item_container_holder">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<h2><spring:theme code="checkout.summary.paymentMethod.header" htmlEscape="false"/><span></span></h2>
			</div>

			<div class="item_container">
				<div class="left">
					<ul>
						{{if paymentInfo}}
							<li>{{= paymentInfo.cardNumber}}</li>
							<li>{{= paymentInfo.cardType}}</li>
							<li><spring:theme code="checkout.summary.paymentMethod.paymentDetails.expires" arguments="{{= paymentInfo.expiryMonth}},{{= paymentInfo.expiryYear}}"/></li>
						{{else}}
							<li><spring:theme code="checkout.summary.paymentMethod.paymentDetails.noneSelected"/></li>
						{{/if}}
					</ul>

					{{if paymentInfo}}
						<form>
							<div class="form_field-label security-code">
								<label for="SecurityCode1"><spring:theme code="checkout.summary.paymentMethod.securityCode" />*</label>
							</div>
		
							<div class="form_field-input security-code">
								<input type="text" class="text security" id="SecurityCode1"/>
							</div>
									<a href="#" class="security_code_what"><spring:theme code="checkout.summary.paymentMethod.securityCode.whatIsThis"/></a>
						</form>
					{{/if}}

				</div>

				<ul class="payment_method">
					{{if paymentInfo}}
						{{if paymentInfo.billingAddress}}
							<li><spring:theme code="checkout.summary.paymentMethod.billingAddress.header"/></li>
							<li>{{= paymentInfo.billingAddress.title}}&nbsp;{{= paymentInfo.billingAddress.firstName}}&nbsp;{{= paymentInfo.billingAddress.lastName}}</li>
							<li>{{= paymentInfo.billingAddress.line1}}</li>
							<li>{{= paymentInfo.billingAddress.line2}}</li>
							<li>{{= paymentInfo.billingAddress.town}}</li>
							<li>{{= paymentInfo.billingAddress.postalCode}}</li>
						{{/if}}
					{{/if}}
				</ul>

			</div>
		</div>

		{{if paymentInfo}}
			<ycommerce:testId code="checkout_changePayment_element">
			<a href="#" class="edit_complete change_payment_method_button" data-payment_id="{{= paymentInfo.id}}" ><spring:theme code="checkout.summary.paymentMethod.editPaymentMethod"/></a>
			</ycommerce:testId>
		{{else}}
			<ycommerce:testId code="checkout_changePayment_element">
			<button style="display: none;" class="form change_payment_method_button" data-payment_id=""><spring:theme code="checkout.summary.paymentMethod.editPaymentMethod"/></button>
			</ycommerce:testId>
		{{/if}}
	</div>

</script>

<script id="savedCardsTemplate" type="text/x-jquery-tmpl">

	{{if !savedCards.length}}
		<tr>
			<td headers="header1" colspan="3">
		<spring:theme code="checkout.summary.paymentMethod.savedCards.noExistingSavedCards"/>
			</td>
		</tr>
	{{/if}}
	{{if savedCards.length}}
		{{each savedCards}}
			<tr>
				<td headers="header1">
				<ul>
					<li>{{= $value.cardNumber}}</li>
					<li>{{= $value.cardType}}</li>
					<li><spring:theme code="checkout.summary.paymentMethod.paymentDetails.expires" arguments="{{= $value.expiryMonth}},{{= $value.expiryYear}}"/></li>
				</ul>
				</td>
				<td headers="header2">
					<ul>
						<li>{{= $value.billingAddress.title}}&nbsp;{{= $value.billingAddress.firstName}}&nbsp;{{= $value.billingAddress.lastName}}</li>
						<li>{{= $value.billingAddress.line1}}</li>
						<li>{{= $value.billingAddress.line2}}</li>
						<li>{{= $value.billingAddress.town}}</li>
						<li>{{= $value.billingAddress.postalCode}}</li>
					</ul>
				</td>
				<td headers="header3">
					<button class="form use_this_saved_card_button" data-payment_id='{{= $value.id}}'><spring:theme code="checkout.summary.paymentMethod.savedCards.UseThisSavedCard"/></button>
				</td>
			</tr>
		{{/each}}
	{{/if}}

</script>



<c:set value="${not empty paymentInfo}" var="paymentInfoOk"/>
<c:set value="${not empty paymentInfo and not empty paymentInfo.billingAddress}" var="billingAddressOk"/>

<div class="checkout_summary_flow_b ${paymentInfoOk ? 'complete' : ''}" id="checkout_summary_payment_div">
	<div class="item_container_holder">
		<ycommerce:testId code="checkout_changePayment_element">
			<div class="title_holder">
				<div class="title">
					<div class="title-top">
						<span></span>
					</div>
				</div>
				<%-- <h2><spring:theme code="checkout.summary.paymentMethod.header" htmlEscape="false"/><span></span></h2> --%>
				<h2><spring:theme code="checkout.summary.costCenter.header" htmlEscape="false"/><span></span></h2>
			</div>

			<div class="item_container">
				<div class="left">
					<ul>
					<c:choose>
						<c:when test="${paymentInfoOk}">
							<li>${fn:escapeXml(paymentInfo.cardNumber)}</li>
							<li>${fn:escapeXml(paymentInfo.cardType)}</li>
							<li><spring:theme code="checkout.summary.paymentMethod.paymentDetails.expires" arguments="${fn:escapeXml(paymentInfo.expiryMonth)},${fn:escapeXml(paymentInfo.expiryYear)}"/></li>
						</c:when>
						<c:otherwise>
							<li><spring:theme code="checkout.summary.paymentMethod.paymentDetails.noneSelected"/></li>
						</c:otherwise>
					</c:choose>
					</ul>
	
					<c:if test="${paymentInfoOk}">
						<form>
							<dl>
								<dt><label for="SecurityCode"><spring:theme code="checkout.summary.paymentMethod.securityCode" />*</label></dt>
								<dd>
									<input type="text" class="text security" id="SecurityCode2"/>
									<a href="#" class="security_code_what"><spring:theme code="checkout.summary.paymentMethod.securityCode.whatIsThis"/></a>
								</dd>
							</dl>
						</form>
					</c:if>
	
				</div>
				<ul>
					<c:if test="${billingAddressOk}">
						<li><spring:theme code="checkout.summary.paymentMethod.billingAddress.header"/></li>
						<li>${fn:escapeXml(paymentInfo.billingAddress.title)}&nbsp;${fn:escapeXml(paymentInfo.billingAddress.firstName)}&nbsp;${fn:escapeXml(paymentInfo.billingAddress.lastName)}</li>
						<li>${fn:escapeXml(paymentInfo.billingAddress.line1)}</li>
						<li>${fn:escapeXml(paymentInfo.billingAddress.line2)}</li>
						<li>${fn:escapeXml(paymentInfo.billingAddress.town)}</li>
						<li>${fn:escapeXml(paymentInfo.billingAddress.postalCode)}</li>
						<li>${fn:escapeXml(paymentInfo.billingAddress.country.name)}</li>
					</c:if>
				</ul>
			</div>
		</ycommerce:testId>
	</div>

	<ycommerce:testId code="checkout_changePayment_element">
		<c:choose>
			<c:when test="${paymentInfoOk}">
				<a href="#" class="edit_complete change_payment_method_button" data-payment_id="${not empty paymentInfo ? paymentInfo.id : ''}"><spring:theme code="checkout.summary.paymentMethod.editPaymentMethod"/></a>
			</c:when>
			<c:otherwise>
				<button style="display: none;" class="form change_payment_method_button" data-payment_id="${not empty paymentInfo ? paymentInfo.id : ''}"><spring:theme code="checkout.summary.paymentMethod.editPaymentMethod"/></button>
			</c:otherwise>
		</c:choose>
	</ycommerce:testId>
</div>


<div style="display:none">
	<div class="item_container_holder edit_payment_details_modal" id="popup_checkout_add_edit_payment_method">
		<checkout:paymentDetailsForm/>
	</div>
</div>


<div style="display:none">

	<div class="item_container_holder payment_details_modal"  id="popup_checkout_saved_payment_method">
		<div class="title_holder">
			<div class="title">
				<div class="title-top">
					<span></span>
				</div>
			</div>
			<h2><spring:theme code="checkout.summary.paymentMethod.savedCards.header"/></h2>
		</div>
		<div class="item_container">
			<p><spring:theme code="checkout.summary.paymentMethod.savedCards.selectSavedCardOrEnterNew"/></p>
			<form>
				<button type="submit" class="form enter_new_payment_button">
					<spring:theme code="checkout.summary.paymentMethod.savedCards.enterNewPaymentDetails"/>
				</button>
			</form>
			<form>
				<ycommerce:testId code="paymentAddress_addressesTable_table">
					<table id="payment_details_modal">
						<thead>
							<tr>
								<th id="header1"><spring:theme code="checkout.summary.paymentMethod.savedCards.paymentCard"/></th>
								<th id="header2"><spring:theme code="checkout.summary.paymentMethod.savedCards.billingAddress"/></th>
								<th id="header3"><span class="hidden"><spring:theme code="checkout.summary.paymentMethod.savedCards.actions"/></span></th>
							</tr>
						</thead>
						<tbody id="saved_cards_tbody">
							<%-- saved cards --%>
						</tbody>
					</table>
				</ycommerce:testId>
			</form>
		</div>
	</div>

</div>
