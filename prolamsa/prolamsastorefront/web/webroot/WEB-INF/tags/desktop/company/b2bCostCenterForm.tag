<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="saveUrl" required="true" type="java.lang.String"%>
<%@ attribute name="cancelUrl" required="true" type="java.lang.String"%>
<%@ attribute name="b2BCostCenterForm" required="true"
	type="mx.neoris.storefront.forms.B2BCostCenterForm"%>

<form:form action="${saveUrl}" id="b2BCostCenterform"
	commandName="b2BCostCenterForm" method="POST">
	<p class="required">
		<spring:theme code="form.required" text="Fields marked * are required" />
	</p>
		<form:input type="hidden" name="originalCode" path="originalCode"
			id="originalCode" />
		<formUtil:formInputBox idKey="text.company.costCenter.id.title"
			labelKey="text.company.costCenter.id.title" path="code"
			inputCSS="text" mandatory="true" />
		<formUtil:formInputBox idKey="text.company.costCenter.name.title"
			labelKey="text.company.costCenter.name.title" path="name"
			inputCSS="text" mandatory="true" />
		<formUtil:formSelectBox idKey="text.company.costCenter.unit.title"
			skipBlank="true" labelKey="text.company.costCenter.unit.title"
			path="parentB2BUnit" mandatory="true" items="${b2bUnits}" />
		<formUtil:formSelectBox idKey="text.company.costCenter.currency.title"
			skipBlank="true" labelKey="text.company.costCenter.currency.title"
			path="currency" mandatory="true" items="${b2bCostCenterCurrencies}" />
	<ycommerce:testId code="CC_cancel">
		<a href="${cancelUrl}" class="mycompany form no-confirm"><spring:theme code="text.company.costCenter.cancelButton.displayName" /></a>
	</ycommerce:testId>
	<ycommerce:testId code="CC_save">
		<button type="submit" class="mycompany form confirm"><spring:theme code="text.company.costCenter.saveButton.displayName" /></button>
	</ycommerce:testId>
</form:form>