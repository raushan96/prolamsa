<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String" %>
<%@ attribute name="action" required="true" type="java.lang.String" %>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/mobile/footer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<%-- 
<div  class="item_container_holder" style="width: 100%; float: left; height:auto; overflow: hidden; background: url('${themeResourcePath}/images/bg-prolamsa_v2.jpg') repeat-x;">
 --%>
 
 <div  id="gradLogin" class="item_container_holder" style="width: 100%; float: left; height:auto; overflow: hidden;">
	
	<a href="/store/"><img id="logo" src="${themeResourcePath}/images/logo.png?id=1" style="width: 190px; " alt="logo"></a>
			
	<%-- <img id="logo" src="${themeResourcePath}/images/Logo.gif" alt="logo"> 
	<a href="/store/"><img id="logo" src="${themeResourcePath}/images/prolamsa.png" alt="logo"></a>--%>
	<br><br>
	<p style="font-size: 14px;  padding: 0 20px; margin: 0; font-weight: 700; text-transform: uppercase;"><spring:theme code="mobile.login.description"/></p>
	<p style="font-size: 14px;  padding: 0 20px; margin: 0; text-align: justify;"><spring:theme code="mobile.login.description2"/></p>
	<br>
	<p class="required" style="font-size: 10px; padding: 0 20px; margin: 0; text-align: justify;"><spring:theme code="login.required.message"/></p>
		
	<form:form action="${action}" method="post" commandName="loginForm">
		<c:if test="${not empty accErrorMsgs}">
			<span class="form_field_error">
		</c:if>
			
		<br>

		<div style="width: 100%; float: left; height:auto; padding: 0 20px 0 20px;">
			<p style="font-size: 14px; margin: 0; text-align: justify; font-weight: 600;"><spring:theme code="mobile.login.email.address"/></p>
			<input type="text" id="j_username" name="j_username" style="border: solid 1px gray; height:31px; width: 100%; background: none repeat scroll 0 0 #BFBEBE;  " required="required" />
			<br><br>
			<p style="font-size: 14px; margin: 0; text-align: justify; font-weight: 600;"><spring:theme code="mobile.login.password"/></p>
			<input  type="password" id="j_password" name="j_password" style="border: solid 1px gray; height:31px; width: 100%; background: none repeat scroll 0 0 #BFBEBE;" required="required" />
		</div>
		<br><br><br><br><br>

		<a style="font-size: 12px; margin: 0; font-weight: 800; padding: 0 20px;" href="javascript:void(0)" data-url="<c:url value='/login/pw/request'/>" class="password-forgotten" ><spring:theme code="mobile.login.forgot.password"/></a>
				
		<br>

		<ycommerce:testId code="login_Login_button">
			<button style="margin: 10px 20px;" type="submit" class="button yellow positive button-float-right2"><spring:theme code="mobile.login.submit"/></button>
		</ycommerce:testId>

		
		
		<button style="margin: 10px; background: #7c858c none repeat scroll 0 0; color: #fff; width: 95%; padding: 10px; font-weight: 600; font-size: large;" type="button" onClick="sendMail('<spring:theme code="login.link.requestNewAccountEmail" />');"><spring:theme code="mobile.login.request.newAccount"/></button>
		<br><br>
		
		<a style=" width:100%;  text-transform:uppercase; text-align:center; font-size: 14px; margin: 0; font-weight: 800; padding: 20px 16% 20px 20%;" href="/store/ui-experience?level=Desktop" class="goto-fullsite"><spring:theme code="mobile.login.full.site"/></a>
		<br><br>
		
		
		
		<c:if test="${not empty accErrorMsgs}">
			</span>
		</c:if>
			
		
		<c:if test="${not empty message}">
			<span class="errors">
				<spring:theme code="${message}"/>
			</span>
		</c:if>
	</form:form>

	

</div>
<footer:footer />

<script>
	function sendMail(mail)
	{
		parent.location="mailto:" + mail ;
	}
</script>
