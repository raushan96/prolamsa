<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="favoriteCustomers" tagdir="/WEB-INF/tags/desktop/favoriteCustomers" %>

<template:page pageTitle="${pageTitle}">
	<c:if test="${not empty message}">
		<spring:theme code="${message}"/>
	</c:if>
	 <div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>		
	<div class="grid-container">
<!-- 		<div id="globalMessages"> -->
<%-- 			<common:globalMessages/> --%>
<!-- 		</div> -->
		<nav:accountNav selected="${cmsPage.label}" />	
	    <div class="span-20 last main-right">
			
			<div class="title_holder">
				<h2><spring:theme code="text.account.favoriteCustomers" text="Favorite Customers"/></h2>
			</div>
			
			<br>
			
			<fieldset>
				<legend><spring:theme code="text.account.favoriteCustomers.fieldset.search" text="Search"/></legend>
				<favoriteCustomers:favoriteCustomerSearchForm/>
			</fieldset>

    		<br>
    		
    		<fieldset>
				<legend><spring:theme code="text.account.favoriteCustomers.fieldset.favorites" text="Favorites"/></legend>
				<div id="favoriteCustomersListContainer">
					<jsp:include page="../../fragments/favoriteCustomers/favoriteCustomersList.jsp" />
				</div>
			</fieldset>
			
		</div>		
					
	</div>
	<br>	
</template:page>

<script type="text/javascript">

onDOMLoaded(bindFavoriteCustomerList);

function bindFavoriteCustomerList()
{	
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);
	
	window.setTimeout(function() {
		removeGlobalMessageOnFragment();
		}, 5000);
}

function submitFormFromDropDown()
{
	var val = jQuery(this).val();		
	jQuery("#sortField").val(val);
	jQuery("#favoriteUnitsForm").submit();
}

function addToFavorites(unitUid)
{
	// remove global messages if is neccesary
	removeGlobalMessageOnFragment();
	
	jQuery.ajax
	({  
		url: "<c:url value="/my-account/favorite-customers/add"/>",
		type: 'POST',
		dataType: 'html',
		data: {'unitUid' : unitUid},
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			 $('#favoriteCustomersListContainer').empty().append(data);
			 bindFavoriteCustomerList();
		},
		error: function (xht, textStatus, ex)
		{    
			ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		complete: function ()
		{
			unblockUI();
		}
	});
	
	return false;
}

function removeFromFavorites(unitUid)
{
	// remove global messages if is neccesary
	removeGlobalMessageOnFragment();
	
	jQuery.ajax
	({  
		url: "<c:url value="/my-account/favorite-customers/remove"/>",
		type: 'POST',
		dataType: 'html',
		data: {'unitUid' : unitUid},
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			 $('#favoriteCustomersListContainer').empty().append(data);
			 bindFavoriteCustomerList();
		},
		error: function (xht, textStatus, ex)
		{    
			ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		complete: function ()
		{
			unblockUI();
		}
	});
	
	return false;
}

function removeGlobalMessageOnFragment()
{
	jQuery(".globalMessagesFragment").empty();
}

</script>