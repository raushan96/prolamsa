<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String" %>
<%@ attribute name="action" required="true" type="java.lang.String" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<div class="item_container_holder">
	<div class="item_container" style="width: 600px">
		<c:if test="${isBrowserCompatible eq true}">
		<strong style="font-size: 25px;"><spring:theme code="login.description"/></strong><br>
		<strong style="font-size: 25px;"><spring:theme code="login.description2"/></strong>
		
		<p class="required" style="font-size: 12px;"><spring:theme code="login.required.message"/></p>
		</c:if>
		<form:form action="${action}" method="post" commandName="loginForm">
			
			<dl>
				<c:if test="${not empty accErrorMsgs}">
					<span class="form_field_error">
				</c:if>
				
				<%-- <formUtil:formInputBox idKey="j_username" labelKey="login.username" path="j_username" inputCSS="text" mandatory="true"/>
				<formUtil:formPasswordBox idKey="j_password" labelKey="login.password" path="j_password" inputCSS="text password" mandatory="true"/>
				--%>
				
				<div>
				<c:if test="${isBrowserCompatible eq true}">
					<input type="text" id="j_username" name="j_username" width="150" required="required" placeholder="<spring:theme code="login.username"/>"/>	
					<input type="password" id="j_password" name="j_password" width="150" required="required" placeholder="<spring:theme code="login.password"/>"/>		
				</c:if>
				</div>
				
				
			</dl>
			<c:if test="${isBrowserCompatible eq true}">
			<dd style="display: block; clear: both; text-align:left; margin-left: 1px;">
					<a href="javascript:void(0)" data-url="<c:url value='/login/pw/request'/>" class="password-forgotten" ><spring:theme code="login.link.forgottenPwd"/></a>
				&nbsp;&nbsp;&nbsp;
				<c:choose>
					<c:when test="${baseStore.name eq 'Prolamsa USA'}">
						<a href="mailto:<spring:theme code="login.link.requestNewAccountEmailUSA" />" class="request-account"><spring:theme code="login.link.requestNewAccountMessage"/></a>
					</c:when>
					<c:when test="${baseStore.name eq 'Prolamsa Mex'}">
						<a href="mailto:<spring:theme code="login.link.requestNewAccountEmailMX" />" class="request-account"><spring:theme code="login.link.requestNewAccountMessage"/></a>
					</c:when>
					<c:when test="${baseStore.name eq 'Prolamsa A4C'}">
						<a href="mailto:<spring:theme code="login.link.requestNewAccountEmailA4C" />" class="request-account"><spring:theme code="login.link.requestNewAccountMessage"/></a>
					</c:when>
					<c:otherwise>
						<a href="mailto:<spring:theme code="login.link.requestNewAccountEmail" />" class="request-account"><spring:theme code="login.link.requestNewAccountMessage"/></a>
					</c:otherwise>
				</c:choose>
			</dd>
			</c:if>	
			<br>
			<span style="display: block; clear: both; text-align:left; " >
			<ycommerce:testId code="login_Login_button">
				<c:if test="${isBrowserCompatible eq true}">
					<button type="submit" class="button yellow positive"><spring:theme code="${actionNameKey}"/></button>					
				</c:if>
				
			</ycommerce:testId>
			</span>
			<br><br>
			<c:if test="${not empty accErrorMsgs}">
					</span>
			
				</c:if>
				
			<br><br><br>
			<c:if test="${not empty message}">
				<span class="errors">
					<spring:theme code="${message}"/>
				</span>
			</c:if>
			
			<%-- <dd>
					<a href="javascript:void(0)" data-url="<c:url value='/login/pw/request'/>" class="password-forgotten"><spring:theme code="login.link.forgottenPwd"/></a>
					<a href="mailto:<spring:theme code="login.link.requestNewAccountEmail" />" class="request-account"><spring:theme code="login.link.requestNewAccountMessage"/></a>
				</dd> --%>
		</form:form>
	</div>
</div>
