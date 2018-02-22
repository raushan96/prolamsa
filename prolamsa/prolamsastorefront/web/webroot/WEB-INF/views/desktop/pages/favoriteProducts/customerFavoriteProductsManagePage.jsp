<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="templates" tagdir="/WEB-INF/tags/desktop/templates"  %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="backorder" tagdir="/WEB-INF/tags/desktop/backorder"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="favoriteProducts" tagdir="/WEB-INF/tags/desktop/favoriteProducts" %>


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
	
	<div id="productLocationSelectorPopupContainer" style="display: none;"></div>		
	
	<div class="grid-container">

	    <nav:accountNav selected="${cmsPage.label}"/>    	
		
		<div class="span-20 last main-right">
			<div class="title_holder">
				<h2><spring:theme code="text.account.customerFavoriteProductsManage" text="Manage Favorite Products"/></h2>
			</div>
			
			<br>
			
			<fieldset>
<%-- 				<legend><spring:theme code="text.account.favoriteCustomers.fieldset.search" text="Search"/></legend> --%>
				<favoriteProducts:favoriteProductSearchForm/>
			</fieldset>

    		<br>
    		
    		<fieldset>
				<legend><spring:theme code="text.account.customerFavoriteProducts.fieldset.favorites" text="Favorites"/></legend>
				<div id="favoriteCustomersManageListContainer">
					<jsp:include page="../../fragments/favoriteProducts/favoriteProductsManageList.jsp" />
				</div>
			</fieldset>
			
		</div>
	</div>
	<br>
</template:page>

<script type="text/javascript">

onDOMLoaded(bindFavoriteProductManageList);

function bindFavoriteProductManageList()
{
	jQuery("#loadFavoriteProducts").click(showModalSelectorWithProductLocation);	
	jQuery(".sortDropDownFieldClass").on('change', submitFormFromDropDown);

	window.setTimeout(function() {
		removeGlobalMessageOnFragment();
		}, 5000);
}

function submitFormFromDropDown()
{
	var val = jQuery(this).val();		
	jQuery("#sortField").val(val);
	jQuery("#favoriteProductsForm").submit();
}

function showModalSelectorWithProductLocation(code)
{
	ACC.modals.openOnModal("<spring:theme text="Locations" code="customerFavoriteProducts.reset.modal.locationSelector.title"/>", 
			"<c:url value="/favorite-products/manage/product-location-popup" />", 
			"#productLocationSelectorPopupContainer", 
			{
				popupCallback: function(location)
				{
					loadFavoriteProducts(location);
				}
			}, 
			null);
}

function loadFavoriteProducts(location)
{
	jQuery.ajax
	({  
		url: "<c:url value="/favorite-products/manage/reset"/>",
		type: 'POST',
		dataType: 'html',
		data: {location: location},
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			 $('#favoriteCustomersManageListContainer').empty().append(data);
			 bindFavoriteProductManageList();
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

function addToFavoriteProducts(productCode)
{
	// remove global messages if is neccesary
	removeGlobalMessageOnFragment();
	
	jQuery.ajax
	({  
		url: "<c:url value="/favorite-products/manage/add"/>",
		type: 'POST',
		dataType: 'html',
		data: {'productCode' : productCode},
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			$('#favoriteCustomersManageListContainer').empty().append(data);
			 bindFavoriteProductManageList();
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

function removeProductFromFavorites(productCode)
{	
	jQuery.ajax
	({  
		url: "<c:url value="/favorite-products/manage/remove"/>",
		type: 'POST',
		dataType: 'html',
		data: {'productCode' : productCode},
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			$('#favoriteCustomersManageListContainer').empty().append(data);
			 bindFavoriteProductManageList();
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