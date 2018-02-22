<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="saveUrl" required="true" type="java.lang.String"%>
<%@ attribute name="cancelUrl" required="true" type="java.lang.String"%>
<%@ attribute name="b2BBudgetForm" required="true"
	type="mx.neoris.storefront.forms.B2BBudgetForm"%>

<form:form action="${saveUrl}" id="editB2bBudgetform"
	commandName="b2BBudgetForm" method="POST">
	<p class="required">
		<spring:theme code="form.required" text="Fields marked * are required" />
	</p>
		<form:input type="hidden" name="originalCode" path="originalCode"
			id="originalCode" />

		<formUtil:formInputBox idKey="text.company.budget.budgetId"
			labelKey="text.company.budget.budgetId" path="code" inputCSS="text"
			mandatory="true" />

		<formUtil:formInputBox idKey="text.company.budget.name"
			labelKey="text.company.budget.name" path="name" inputCSS="text"
			mandatory="true" />

		<formUtil:formSelectBox idKey="text.company.budget.unit"
			skipBlankMessageKey="text.company.costCenter.selectBox.unit"
			labelKey="text.company.costCenter.unit.title" path="parentB2BUnit"
			mandatory="true" items="${b2bUnits}" />

		<formUtil:formInputBox idKey="budgetStartDate"
			labelKey="text.company.budget.startDate" path="startDate"
			inputCSS="date text" mandatory="true" />

		<formUtil:formInputBox idKey="budgetEndDate"
			labelKey="text.company.budget.endDate" path="endDate" inputCSS="date text"
			mandatory="true" />


		<formUtil:formSelectBox idKey="text.company.budget.currency"
			skipBlankMessageKey="text.company.costCenter.selectBox.currency"
			labelKey="text.company.costCenter.currency.title" path="currency"
			mandatory="true" items="${b2bCostCenterCurrencies}" />


		<formUtil:formInputBox idKey="text.company.budget.amount"
			labelKey="text.company.budget.amount" path="budget" inputCSS="text"
			mandatory="true" />

	<ycommerce:testId code="Budget_Cancel_button">
		<a href="${cancelUrl}" class="mycompany form no-confirm"><spring:theme code="text.company.budget.cancelButton.displayName" text="Cancel" /></a>
	</ycommerce:testId>
	<ycommerce:testId code="Budget_Save_button">
		<button type="submit" class="mycompany form confirm" id="SubmitBudget_button"><spring:theme code="text.company.budget.saveButton.displayName" text="Save" /></button>
	</ycommerce:testId>
</form:form>
