
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<%@ page import="com.greycon.reports.*" %>
<%@ page import="org.apache.commons.lang.exception.ExceptionUtils" %>

<%
	request.getSession().setAttribute("pageTitle", "Test WebService call");
	
	String message = "Test WebService Call";
	String code = "";
	
	AuthenticationContract authentication = new AuthenticationContract();
	authentication.setUserName("administrator");
	authentication.setDomain("axisbynopt-h");
	authentication.setSystemId(1);
	authentication.setWorkareaId(1);
	authentication.setConnection("[Default]");
	
	ReportProvider reportProvider = new ReportProvider();
	IReportProvider bindingEndpoint = reportProvider.getSoap11();
	ArrayOfReportParameter reportParameters = null;
	
	try
	{
		code = bindingEndpoint.getCustomReportAsXml(authentication, "Orders", reportParameters);
	}
	catch (Exception ex)
	{
		//LOG.error("error when executing WebService call", ex);
	
		message = "Test WebService failed";
		code = ExceptionUtils.getStackTrace(ex);
	}
	
	request.getSession().setAttribute("message", message);
	request.getSession().setAttribute("code", code);
%>
<template:master pageTitle="${pageTitle}"> 

<jsp:body>
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>

	<div class="span-24 section2">
		<h3 style="color: black;">${message}</h3>

		<pre style="color: black;">
		${code}
		</pre>
	</div>
</jsp:body>

</template:master>
