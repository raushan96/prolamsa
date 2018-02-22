<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="balanceStatement" tagdir="/WEB-INF/tags/mobile/balanceStatement" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/mobile/header"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav" %>

<template:master pageTitle="${pageTitle}">
	<div class="" style="width: 100%; float: left; height:auto; overflow: hidden;">
		<header:header />
		<header:menu />
		<balanceStatement:balanceStatementInvoice />
	</div>
	<br>
</template:master>