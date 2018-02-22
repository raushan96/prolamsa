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

<c:set var="getAlertConfsBaseURL" value="/my-account/alerts/get" />
<spring:url var="getAlertConfsURL" value="${getAlertConfsBaseURL}" />

<c:set var="saveAlertConfsBaseURL" value="/my-account/alerts/save" />
<spring:url var="saveAlertConfsURL" value="${saveAlertConfsBaseURL}" />

<div class="title_holder">
					<h2><spring:theme code="alerts.list.title" text="Settings"/></h2>
				</div>
					
						<div>
							<table  style="table-layout: fixed;">
								<tr>
									<td width="20%" style="text-align: right;">
										<spring:theme code="b2bcustomer.settings.client" />
									</td >
								    <td width="25%">
								    <form:form id="alertConfigurationSearchForm" class="js-form" action="${getAlertConfsURL}" method="get">	
										<select id="customerSelect" name="name" class="${dropDownCSS}">
											<c:forEach items="${formattedB2BUnits}" var="eachFormattedB2BUnit">
											    <c:choose>
													<c:when test="${eachFormattedB2BUnit.uid eq alertConfigurationSearchForm.name}">
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
							    <c:choose>
							    	<c:when test="${not empty alertConfigurations }">
							    		<form:form id="alertsConfigurationForm" class="js-form" action="${saveAlertConfsURL}" method="post">	
										    <tr><td><input type="hidden" name="b2bUnitId" value="${alertConfigurationSearchForm.name}"> </td></tr>
										    <c:forEach items="${ alertConfigurations}" var="eachAlertConfiguration" varStatus="status">
										    <tbody index="head-${status.index }">
										    	<tr>
											    	<td colspan="4">
											    		<c:set value="" var="notifyMeChecked"/>
											    		<c:if test="${eachAlertConfiguration.notify }">
											    			<c:set value="checked" var="notifyMeChecked"/>
											    		</c:if>
											    		<label class="tableLabel"><input type="checkbox" name="alertsConfigurations[${status.index }].notify" class=alertCheckbox index="${status.index}" id="${eachAlertConfiguration.alert.code }" ${notifyMeChecked}>${eachAlertConfiguration.alert.description}</label>
												    	<input type="hidden" name="alertsConfigurations[${status.index }].alertCode" value="${eachAlertConfiguration.alert.code}">
											    	</td>
											    </tr>
											    </tbody>
											    <tbody class="alertConfiguration" index="${status.index }" style="display:none;">
											   <c:if test="${ eachAlertConfiguration.alert.hasTimeOptions}">
												    <tr>
												    	<td style="text-align: right;">
												    		<spring:theme code="alerts.list.receiveAt" />
												    	</td>
												    	<td>
												    		<c:if test="${ eachAlertConfiguration.alert.hasTimeOptions}">
														    	<select class="selectHour" index="${status.index}" name="alertsConfigurations[${status.index }].time">
														    		<c:forEach items="${ eachAlertConfiguration.alert.timeOptions}" var="eachTimeOpt" >
															    		<c:choose>
																			<c:when test="${eachTimeOpt.code eq eachAlertConfiguration.time}">
																				<c:set var="selected" value="selected" />
																			</c:when>
																			<c:otherwise>
																				<c:set var="selected" value="" />
																			</c:otherwise>
																		</c:choose>
														    			<option value="${eachTimeOpt.code}" ${selected }>${eachTimeOpt.description }</option>
														    		</c:forEach>
														    	</select>
														    </c:if>
											    		</td>
												    </tr>
												 </c:if>
												 <c:if test="${ eachAlertConfiguration.alert.hasDailyOption or eachAlertConfiguration.alert.hasWeeklyOption or eachAlertConfiguration.alert.hasMonthlyOption}">
												    <tr>
												    	<td>
												    		<c:set value="" var="checkDaily"/>
												    		<c:set value="" var="checkweekly"/>
												    		<c:set value="" var="checkMonthly"/>
												    		<c:choose>
												    			<c:when test="${eachAlertConfiguration.alert.hasDailyOption and eachAlertConfiguration.periodicity eq 'daily' }">
												    				<c:set value="checked" var="checkDaily"/>
												    			</c:when>
												    			<c:when test="${eachAlertConfiguration.alert.hasWeeklyOption and eachAlertConfiguration.periodicity eq 'weekly' }">
												    				<c:set value="checked" var="checkweekly"/>
												    			</c:when>
												    			<c:when test="${eachAlertConfiguration.alert.hasMonthlyOption and eachAlertConfiguration.periodicity eq 'monthly' }">
												    				<c:set value="checked" var="checkMonthly"/>
												    			</c:when>
												    			<c:otherwise>
												    				<c:choose>
												    					<c:when test="${eachAlertConfiguration.alert.hasDailyOption }">
												    						<c:set value="checked" var="checkDaily"/>
												    					</c:when>
												    					<c:when test="${eachAlertConfiguration.alert.hasWeeklyOption }">
												    						<c:set value="checked" var="checkweekly"/>
												    					</c:when>
												    					<c:when test="${eachAlertConfiguration.alert.hasMonthlyOption }">
												    						<c:set value="checked" var="checkMonthly"/>
												    					</c:when>
												    				</c:choose>
												    			</c:otherwise>
												    		</c:choose>
												    		<br>
												    	<c:if test="${ eachAlertConfiguration.alert.hasDailyOption}">
												    		<label class="tableLabel"><input class="periodicityRadio" index="${status.index}" type="radio" name="alertsConfigurations[${status.index }].periodicity" ${checkDaily  } value="daily"><spring:theme code="alerts.list.daily" /></label><br>
												    	</c:if>
												    	<c:if test="${ eachAlertConfiguration.alert.hasWeeklyOption}">
												    		<label class="tableLabel"><input class="periodicityRadio" index="${status.index}" type="radio" name="alertsConfigurations[${status.index }].periodicity" ${ checkweekly} value="weekly"><spring:theme code="alerts.list.weekly" /></label><br>
												    	</c:if>
												    	<c:if test="${ eachAlertConfiguration.alert.hasMonthlyOption}">
												    		<label class="tableLabel"><input class="periodicityRadio" index="${status.index}" type="radio" name="alertsConfigurations[${status.index }].periodicity" ${ checkMonthly} value="monthly"><spring:theme code="alerts.list.monthly" /></label><br>
												    	</c:if>
												    	&nbsp;
												    	</td>
												    	<td class="tdDayOfWeek" index="${status.index }" colspan="3">
												    		<c:if test="${ eachAlertConfiguration.alert.hasDayOptions}">
																<c:forEach items="${ daysOfWeek}" var="eachDay" >
																	<c:set value="" var="dayChecked" />
																	<c:if test="${fn:contains(eachAlertConfiguration.daysOfWeek,eachDay.code) }" >
																		<c:set value="checked" var="dayChecked" />														
																	</c:if>
												    				<label class="tableLabel"><input type="checkbox" ${dayChecked} class="daysOfWeekCheckbox" index="${status.index}" name="alertsConfigurations[${status.index }].daysOfWeek" value="${eachDay.code }"><spring:theme code="alerts.day.${eachDay.name }" />&nbsp;</label>
												    			</c:forEach>
													    	</c:if>
											    		</td>
											    		<td class="tdDayOfMonth" index="${status.index }" colspan="3">
											    			<spring:theme code="alerts.list.day" /> <select index="${status.index}" class="dayOfMonthSelect" name="alertsConfigurations[${status.index }].dayOfMonth" style="width:40px;">
											    				<c:forEach begin="0" end="30" varStatus="dayStatus">
																	<c:set value="" var="dayOfMonthSelected"/>
																	
																	<c:if test="${ dayStatus.count eq eachAlertConfiguration.dayOfMonth}" >
																		<c:set value="selected" var="dayOfMonthSelected"/>
																	</c:if>
																	
											    					<option value="${dayStatus.count}" ${dayOfMonthSelected }>${dayStatus.count }</option>
											    				
											    				</c:forEach>
											    			
											    			</select> <spring:theme code="alerts.list.ofMonth" />
											    		</td>
												    </tr>
												</c:if>
												 <c:if test="${ eachAlertConfiguration.alert.hasMTROption}">
													<tr>
														<td></td>
														<td style="text-align: left;" colspan="3" >
															<c:set var="mtrChecked" value=""/>
															<c:if test="${eachAlertConfiguration.includeMTR eq true }">
																<c:set var="mtrChecked" value="checked"/>
															</c:if>
															<label class="tableLabel"><input ${mtrChecked } index="${status.index }" class="mtrOptionInput" name="alertsConfigurations[${status.index }].includeMTR" type="checkbox"><spring:theme code="alerts.list.includeMTR" /></label>
														</td>
													</tr>
												</c:if>
											    <c:if test="${ eachAlertConfiguration.alert.hasCCEmailOptions}">
													 <tr class="trCCEmail">
												    	<td style="text-align: right;">
											    			<spring:theme code="alerts.list.sendCopyTo" /> 
										    			</td>
										    			<td>
										    				<input  type="text" class="ccEmail" index="${status.index }" name="alertsConfigurations[${status.index }].ccEmail" value="${eachAlertConfiguration.ccEmail }">
										    			</td>
											    	</tr>
											    </c:if>
											    <c:if test="${ eachAlertConfiguration.alert.hasProductOptions}">
													 <tr class="trProductOptions">
												    	<td style="text-align: right;" >
											    			<spring:theme code="alerts.list.productFamilies" />
										    			</td>
										    			<td colspan="3">
										    				<c:if test="${ eachAlertConfiguration.alert.hasProductOptions}">
																<c:forEach items="${ eachAlertConfiguration.alert.productOptions}" var="eachProductOpt" >
																	<c:set value="" var="prodOptChecked" />
																	<c:if test="${fn:contains(eachAlertConfiguration.productOptions,eachProductOpt.code) }" >
																		<c:set value="checked" var="prodOptChecked" />														
																	</c:if>
												    				<label class="tableLabel"><input class="prodOptsCheckbox" index="${status.index }" type="checkbox" ${prodOptChecked} name="alertsConfigurations[${status.index }].productOptions" value="${eachProductOpt.code }"><c:out value="${eachProductOpt.description }" /> &nbsp;</label>
												    			</c:forEach>
													    	</c:if>
										    			</td>
											    	</tr>
											    </c:if>
												</tbody>
											    </c:forEach>
											    <tr><td><div class="search-form-options">
												<formUtil:formButton
													id="quoteSearchSubmit"
													name="quoteSearchSubmit"
													type="submit" 
													css="positive button-float-right"
													tabindex="106"
													springThemeText="b2bcustomer.settings.save" />
											</div></td></tr>
											
											</form:form>
							    	</c:when>
							    	<c:otherwise>
							    		<tr>
							    			<td colspan="4"><spring:theme code="alerts.list.noalerts" /></td>
							    		</tr>
							    	</c:otherwise>
							    </c:choose>
							    
							</table>
						</div>
				
				
	<script type="text/javascript">

	onDOMLoaded(bindControls);
	
	function bindControls()
	{	
		jQuery("#customerSelect").on("change",loadSettingsFor);
		jQuery(".periodicityRadio").on("click",periodicityClicked);
		jQuery(".alertCheckbox").on("change",alertCheckboxChanged);
		
		updateControls();
	}
	
	function updateControls()
	{
		var alertsConfigurations = jQuery(".alertCheckbox");
		
		jQuery.each(alertsConfigurations, function(index,value){
			
			var index = jQuery(value).attr("index");
			
			var periodicityRadioButton = jQuery(".periodicityRadio[index="+index+"][checked]");
			var allPeriodicityRadioButton = jQuery(".periodicityRadio[index="+index+"]");
			
			var dayOfMonthSelect = jQuery(".dayOfMonthSelect[index="+index+"]");
			
			var daysOfWeekCheckbox = jQuery(".daysOfWeekCheckbox[index="+index+"]");
			
			var selectHour = jQuery(".selectHour[index="+index+"]");
			
			var selectDay = jQuery(".selectDay[index="+index+"]");
			
			var ccEmailInput = jQuery(".ccEmail[index="+index+"]");
			
			var alertTBody = jQuery(".alertConfiguration[index="+index+"]");
			
			var tdDayOfMonth = jQuery(".tdDayOfMonth[index="+index+"]");
			
			var tdDayOfWeek = jQuery(".tdDayOfWeek[index="+index+"]");
			
			var mtrOptionInput = jQuery(".mtrOptionInput[index="+index+"]");
				
			var prodOptsCheckbox = jQuery(".prodOptsCheckbox[index="+index+"]");
			
			if(jQuery(value).prop("checked"))
			{
				alertTBody.fadeIn();
				
				allPeriodicityRadioButton.prop("disabled",false);
				
				selectDay.prop("disabled",true);
				selectHour.prop("disabled",false);
				
				daysOfWeekCheckbox.prop("disabled",true);
				dayOfMonthSelect.prop("disabled",true);
				
				
				tdDayOfMonth.hide();
				tdDayOfWeek.hide();

				if(periodicityRadioButton.val() == "weekly")
				{
					selectDay.prop("disabled",false);	
					
					daysOfWeekCheckbox.prop("disabled",false);
					
					tdDayOfWeek.fadeIn();
				}
				
				if(periodicityRadioButton.val() == "monthly")
				{
					dayOfMonthSelect.prop("disabled",false);
					
					tdDayOfMonth.fadeIn();
				}
				
				ccEmailInput.prop("disabled",false);
			}
			else
			{
				allPeriodicityRadioButton.prop("disabled",true);
				selectHour.prop("disabled",true);
				selectDay.prop("disabled",true);
				ccEmailInput.prop("disabled",true);
				dayOfMonthSelect.prop("disabled",true);
				daysOfWeekCheckbox.prop("disabled",true);
				mtrOptionInput.prop("disabled",true);
				prodOptsCheckbox.prop("disabled",true);
				
				tdDayOfMonth.hide();
				tdDayOfWeek.hide();
			}
			
		});
	}
	
	function periodicityClicked()
	{
		var index = jQuery(this).attr("index");
		
		var selectHour = jQuery(".selectHour[index="+index+"]");
		
		var selectDay = jQuery(".selectDay[index="+index+"]");
		
		var tdDayOfMonth = jQuery(".tdDayOfMonth[index="+index+"]");
		
		var tdDayOfWeek = jQuery(".tdDayOfWeek[index="+index+"]");
		
		var tdDayOfMonth = jQuery(".tdDayOfMonth[index="+index+"]");
		
		var tdDayOfWeek = jQuery(".tdDayOfWeek[index="+index+"]");
		
		var dayOfMonthSelect = jQuery(".dayOfMonthSelect[index="+index+"]");
		
		var daysOfWeekCheckbox = jQuery(".daysOfWeekCheckbox[index="+index+"]");
		
		selectDay.prop("disabled",true);
		dayOfMonthSelect.prop("disabled",true);
		daysOfWeekCheckbox.prop("disabled",true);
		
		
		tdDayOfMonth.hide();
		tdDayOfWeek.hide();


		if(jQuery(this).val() == "weekly")
		{
			selectDay.prop("disabled",false);	
			daysOfWeekCheckbox.prop("disabled",false);
			tdDayOfWeek.fadeIn();

		}
		
		if(jQuery(this).val() == "monthly")
		{
			
			dayOfMonthSelect.prop("disabled",false);
			tdDayOfMonth.fadeIn();
		}	
	}
	
	function alertCheckboxChanged()
	{
		var index = jQuery(this).attr("index");
		
		var periodicityRadioButton = jQuery(".periodicityRadio[index="+index+"]");
		var selectHour = jQuery(".selectHour[index="+index+"]");
		var selectDay = jQuery(".selectDay[index="+index+"]");
		var ccEmailInput = jQuery(".ccEmail[index="+index+"]");
		var alertTBody = jQuery(".alertConfiguration[index="+index+"]");
		var mtrOptionInput = jQuery(".mtrOptionInput[index="+index+"]");
		var prodOptsCheckbox = jQuery(".prodOptsCheckbox[index="+index+"]");
		
		if(jQuery(this).prop("checked"))
		{
			alertTBody.fadeIn();
			
			selectHour.prop("disabled",false);
			periodicityRadioButton.prop("disabled",false);
			
			if(periodicityRadioButton.val() == "weekly")
			{
				
				daysOfWeekCheckbox.prop("disabled",false);
				
			}
			
			if(periodicityRadioButton.val() == "monthly")
			{
				dayOfMonthSelect.prop("disabled",false);
				
			}
			
			ccEmailInput.prop("disabled",false);
			mtrOptionInput.prop("disabled",false);
			prodOptsCheckbox.prop("disabled",false);
		}
		else
		{
			alertTBody.fadeOut();	
			periodicityRadioButton.prop("disabled",true);
			selectHour.prop("disabled",true);
			selectDay.prop("disabled",true);
			ccEmailInput.prop("disabled",true);
			mtrOptionInput.prop("disabled",true);
			prodOptsCheckbox.prop("disabled",true);
		}
	}
	
	function loadSettingsFor()
	{
		jQuery("#alertConfigurationSearchForm").submit();
	}
	
	</script>