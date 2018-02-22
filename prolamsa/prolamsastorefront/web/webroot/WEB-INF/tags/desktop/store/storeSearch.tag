<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="errorNoResults" required="true" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<div class="item_container_holder">
	<div class="title_holder">
		<div class="title">
			<div class="title-top">
				<span></span>
			</div>
		</div>
		<h2><spring:theme code="storeFinder.find.a.store" /></h2>
	</div>
	<div class="item_container">
		<p><spring:theme code="storeFinder.use.this.form" /></p>
		<c:url value="/store-finder" var="storeFinderFormAction" />
		<form:form action="${storeFinderFormAction}" method="get" commandName="storeFinderForm">
				<ycommerce:testId code="storeFinder_search_box">
					<formUtil:formInputBox idKey="storeFinder.postcode.town" labelKey="storeFinder.postcode.town" path="q" inputCSS="text" mandatory="true" />
					<button class="form search">
						<span class="search-icon"> <spring:theme code="storeFinder.search" /> </span>
					</button>
				</ycommerce:testId>
		</form:form>
		<c:url value="/store-finder/position" var="nearMeStorefinderFormAction"/>
		<form:form id="nearMeStorefinderForm" name="near_me_storefinder_form" method="POST" action="${nearMeStorefinderFormAction}">
			<input type="hidden" id="latitude" name="latitude"/>
			<input type="hidden" id="longitude" name="longitude"/>
			<span class="find-stores-near-me">
				<a href="#" id="findStoresNearMe">
					<spring:theme code="storeFinder.findStoresNearMe"/>
				</a>
			</span>
		</form:form>
	</div>
</div>
