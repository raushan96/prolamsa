<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>

<c:set var="getSettingsBaseURL" value="/my-account/settings/get" />
<spring:url var="getSettingsURL" value="${getSettingsBaseURL}" />

<c:set var="saveSettingsBaseURL" value="/my-account/settings/save" />
<spring:url var="saveSettingsURL" value="${saveSettingsBaseURL}" />

<div class="title_holder">
					<h2><spring:theme code="b2bcustomer.settings.title" text="Settings"/></h2>
				</div>
					
						<div>
							<table  style="table-layout: fixed;">
								<tr>
									<td width="25%">
										<spring:theme code="b2bcustomer.settings.client" />
									</td >
								    <td width="25%">
								    <form:form id="b2bCustomerSettingsSearchForm" class="js-form" action="${getSettingsURL}" method="get">	
										<select id="customerSelect" name="name" class="${dropDownCSS}">
											<option value=""><spring:theme code="b2bcustomer.settings.client.select" /> </option> 
											<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
											    <c:choose>
													<c:when test="${eachFormattedB2BUnit.uid eq b2bCustomerSettingsSearchForm.name}">
														<c:set var="selected" value="selected" />
													</c:when>
													<c:otherwise>
														<c:set var="selected" value="" />
													</c:otherwise>
												</c:choose>
											   	<option value="${eachFormattedB2BUnit.uid}" ${selected} >${eachFormattedB2BUnit.name}</option>
											</c:forEach>
										</select>
									</form:form>
								    </td>
								    <td>
								    	&nbsp;
								    </td>
								    <td>&nbsp;
								    </td>
							    </tr>
							</table>
							<form:form id="b2bCustomerSettingForm" class="js-form" action="${saveSettingsURL}" method="get">	
							<table  style="table-layout: fixed;">
							    <tr>
							    	<td>
							    		<input type="hidden" name="b2BUnit" value="${ b2bCustomerSettingsSearchForm.name}"/>
								    	&nbsp;
								    </td>
							    </tr>
							    <tr>
									<td width="25%">
										<spring:theme code="b2bcustomer.settings.address" />
									</td >
								    <td width="25%">
										<select id="address" name="address">
											<option selected="selected" value=""><spring:theme code="b2bcustomer.settings.address.select" /> </option>
											<c:forEach items="${ addresses}" var="eachDeliveryAddress">
												<c:set value="" var="selected"></c:set>
												<c:if test="${eachDeliveryAddress.code eq currentSetting.shippingAddress.code}" >
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
								    </td>
								    <td>
								    	<spring:theme code="b2bcustomer.settings.uom" />
								    </td>
								    <td>
								    	<select id="uom" name="uom">
										<option selected="selected" value=""><spring:theme code="b2bcustomer.settings.uom.select" /> </option>
										<c:forEach items="${ uoms}" var="eachUoM">
											<c:set value="" var="selected"></c:set>
											<c:if test="${eachUoM.code eq currentSetting.uom.code}" >
												<c:set value="selected" var="selected"></c:set>	
											</c:if>
											<option value="${eachUoM.code }" ${selected }>${eachUoM.name}</option>
										</c:forEach>
										</select>
								    </td>
							    </tr>
							    <tr>
									<td width="25%">
										<spring:theme code="b2bcustomer.settings.transportationMode" />
									</td >
								    <td width="25%">
								    	<select id="tranpostationMode" name="transportationMode">
										<option selected="selected" value=""><spring:theme code="b2bcustomer.settings.transportationmode.select" /> </option>
										<c:forEach items="${ transportationModes}" var="eachTransportationMode">
											<c:set value="" var="selected"></c:set>
											<c:if test="${eachTransportationMode.internalCode eq currentSetting.transportationMode.internalCode}" >
												<c:set value="selected" var="selected"></c:set>	
											</c:if>
											<option value="${eachTransportationMode.internalCode }" ${selected }>${eachTransportationMode.name} (${eachTransportationMode.maxCapacity} Tons) - ${eachTransportationMode.incotermCode}</option>
										</c:forEach>
										</select>
								    </td>
								    <td>
								    	<spring:theme code="b2bcustomer.settings.location" />
								    </td>
								    <td>
								    	<select id="location" name="location">
										<option selected="selected" value="none"><spring:theme code="b2bcustomer.settings.location.select" /> </option>
										<c:forEach items="${ locations}" var="eachLocation">
											<c:set value="" var="selected"></c:set>
											<c:if test="${eachLocation.name eq currentSetting.location.name}" >
												<c:set value="selected" var="selected"></c:set>	
											</c:if>
											<option value="${eachLocation.name }" ${selected }>${eachLocation.locDisplayName} </option>
										</c:forEach>
										</select>
								    </td>
							    </tr>
							    
							</table>
					
							<div class="search-form-options">
								<formUtil:formButton
									id="quoteSearchSubmit"
									name="quoteSearchSubmit"
									type="submit" 
									css="positive button-float-right"
									tabindex="106"
									springThemeText="b2bcustomer.settings.save" />
							</div>
							</form:form>
							
							<div style="margin: 40px 30px 0px 30px;">
								<p><spring:theme code="b2bcustomer.settings.location.note" /></p>
							</div>
						</div>
				
				
	<script type="text/javascript">

	onDOMLoaded(bindControls);
	
	function bindControls()
	{
		jQuery("#customerSelect").on("change",loadSettingsFor);
	}
	
	function loadSettingsFor()
	{
		jQuery("#b2bCustomerSettingsSearchForm").submit();
	}
	
	</script>