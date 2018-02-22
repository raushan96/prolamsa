<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="commonUtil" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>

<div class="item_container_holder" style="display: block;">
	<div class="searchFormContainer">
		<form:form id="productSearchForm" class="js-form" action="" method="get">
			<table>
				<tr>
			    	<td  style="display:none">
			    		<spring:theme code="text.account.customerFavoriteProducts.filter.code" />
			    	</td>
			    	<td  style="display:none">
				       <formUtil:formInputTextInline 
						path="" 
						id="uid"
						mandatory="false" 
						type="text" 
						name="code"						
						tabindex="100" 
						value="${productSearchForm.code}"
						divCSS="search-form-options"						
						asterisk="" 
						inputCSS="" 
						
						/>
				    </td>
				    <td>
			    		<spring:theme code="text.account.favoriteCustomers.fieldset.search" />:
			    	</td>
			    	<td>
				       <formUtil:formInputTextInline 
						path="" 
						id="uid"
						mandatory="false" 
						type="text" 
						name="description"						
						tabindex="100" 
						value="${productSearchForm.description}"
						divCSS="search-form-options"						
						asterisk="" 
						inputCSS="" />
				    </td>
<!-- 				</tr> -->
<!-- 				<tr> -->
					<td>
						<spring:theme code="text.account.customerFavoriteProducts.filter.location" />
					</td>
					<td>
						<select id="location" name="location" style="background: white;">
						  	<option value=""><spring:theme code="customerFavoriteProducts.reset.modal.locationSelector.all" /></option>
						  	<c:forEach items="${productLocations}" var="eachLocation">
								<c:choose>
									<c:when test="${productSearchForm.location eq eachLocation.name}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							<option value="${eachLocation.name}" ${selected} >${eachLocation.locDisplayName}</option>
						  	</c:forEach>
						</select>
					</td>
				</tr>
		    </table>
		
			<div class="search-form-options">
				<formUtil:formButton
					id="productSearchSubmit"
					name="productSearchSubmit"
					type="button" 
					css="positive button-float-right"
					tabindex="102"
					springThemeText="customerFavoriteProducts.button.search"
					onClickType="1"
					onClick="sendGA()" />
			</div>
		</form:form>
	</div>
	
	<br>
	<br>

	<div class="item_container">
		<div id="productSearchtResult" class="content">
			<table>
				<thead>
				<tr class="firstrow">
	                <th class="col_center"><h3><spring:theme code="text.account.customerFavoriteProducts.column.part" text="Part #"/></h3></th>
					<th><h3><spring:theme code="text.account.customerFavoriteProducts.column.description" text="Description"/></h3></th>
					<th><h3><spring:theme code="text.account.customerFavoriteProducts.column.location" text="Location"/></h3></th>	
					<th class="col_center"><h3><spring:theme code="text.account.customerFavoriteProducts.column.actions" text="Actions"/></h3></th>
				</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4">
							<spring:theme code="text.account.customerFavoriteProducts.search.do" text="Search for Products"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	   
</div>
<br>
<script>
	function sendGA ()
	{
		ga('send','event','ManageFavProd','manageFavProd');
	}
</script>
<script type="text/javascript">

onDOMLoaded(bindFavoriteProductSearch);

function bindFavoriteProductSearch()
{
	jQuery("#productSearchSubmit").click(submitSearchForm);	
}

function submitSearchForm()
{	
	// remove global messages if is neccesary
	removeGlobalMessageOnFragment();
	
	var info = jQuery("#productSearchForm").serialize();
	
	jQuery.ajax
	({  
		url: "<c:url value="/favorite-products/manage/search.json" />",
		type: 'POST',
		dataType: 'json',
		data: info,
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			if (data.status == 0)
           	{	
				$('#productSearchtResult').empty();
				$('#productSearchtResult').addClass("productSearchtResult");	
				
				var table = $("<table>", {});
				table.append("<thead><tr class='firstrow'>" +
							"<th class='col_center'><h3><spring:theme code='text.account.customerFavoriteProducts.column.part' text='Part #'/></h3></th>" +
							"<th><h3><spring:theme code='text.account.customerFavoriteProducts.column.description' text='Description'/></h3></th>" +
							"<th><h3><spring:theme code='text.account.customerFavoriteProducts.column.location' text='Location'/></h3></th>" +
							"<th class='col_center'><h3><spring:theme code='text.account.customerFavoriteProducts.column.actions' text='Actions'/></h3></th>" +
							"</thead></tr>");
				
				if (data.products != null && data.products.length > 0)
				{	
					$('#productSearchtResult').css("overflow","scroll");
					$('#productSearchtResult').css("height","200px");
					
					$.each(data.products, function(index)
					{
						var nameOrDescription = "";
						
						if(data.products[index].description != null && data.products[index].description != "")
						{
							nameOrDescription = data.products[index].description;
						}
						else if(data.products[index].name != null && data.products[index].name != "")
						{
							nameOrDescription = data.products[index].name;
						}
						
						table.append("<tr>" +
									"<td align='center'>" + data.products[index].baseCode + "</td>" +
									"<td>" + nameOrDescription + "</td>" +
									"<td><spring:theme code='" + data.products[index].location + "'/></td>" +
									"<td align='center'><a href='javascript:void(0);' onclick='addToFavoriteProducts(\""+ data.products[index].code +"\")'><spring:theme code='text.account.customerFavoriteProducts.add' text='Add'/></a></td>" +
									"</tr>");		                   			   					   
					});
				}
				else
				{
					$('#productSearchtResult').css("overflow","");
					$('#productSearchtResult').css("height","");
					
					table.append("<tr><td colspan='5'><spring:theme code='text.account.favoriteCustomers.search.noResults' text='No results'/></td></tr>");
				}
								
				$('#productSearchtResult').append(table);
			 						
           	}
           	else
           	{
           		ACC.modals.messageModal("<spring:theme code="error"/>", data.message);            		
           	}
			 
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
}

</script>