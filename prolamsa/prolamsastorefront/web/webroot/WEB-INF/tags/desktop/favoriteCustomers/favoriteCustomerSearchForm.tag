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
		<form:form id="unitSearchForm" class="js-form" action="" method="get">
			<table>
				<tr>
			    	<td>
			    		<spring:theme code="text.account.favoriteCustomers.filter.id" />
			    	</td>
			    	<td>
				       <formUtil:formInputTextInline 
						path="" 
						id="uid"
						mandatory="false" 
						type="text" 
						name="uid"						
						tabindex="100" 
						value="${b2bUnitSearchForm.uid}"
						divCSS="search-form-options"						
						asterisk="" 
						inputCSS="" />
				    </td>
				    <td>
			    		<spring:theme code="text.account.favoriteCustomers.filter.name" />
			    	</td>
			    	<td>
				       <formUtil:formInputTextInline 
						path="" 
						id="name"
						mandatory="false" 
						type="text" 
						name="name"						
						tabindex="101" 
						value="${b2bUnitSearchForm.name}"
						divCSS="search-form-options"						
						asterisk="" 
						inputCSS="" />
				    </td>
				</tr>
		    </table>
		
			<div class="search-form-options">
				<formUtil:formButton
					id="customerSearchSubmit"
					name="customerSearchSubmit"
					type="button" 
					css="positive button-float-right"
					tabindex="102"
					springThemeText="favoriteCustomers.button.search" />
			</div>
		</form:form>
	</div>
	
	<br>
	<br>

	<div class="item_container">
		<div id="b2bUnitResult" class="content">
			<table>
				<thead>
				<tr class="firstrow">
	                <th class="col_center"><h3><spring:theme code="text.account.favoriteCustomers.column.id" text="Id"/></h3></th>
					<th><h3><spring:theme code="text.account.favoriteCustomers.column.name" text="Name"/></h3></th>	
					<th><h3><spring:theme code="text.account.favoriteCustomers.column.city" text="City"/></h3></th>
					<th><h3><spring:theme code="text.account.favoriteCustomers.column.state" text="State"/></h3></th>
					<th class="col_center"><h3><spring:theme code="text.account.favoriteCustomers.column.actions" text="Actions"/></h3></th>
				</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="5">
							<spring:theme code="text.account.favoriteCustomers.search.do" text="Search for Customers"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	   
</div>
<br>

<script type="text/javascript">

onDOMLoaded(bindFavoriteCustomerSearch);

function bindFavoriteCustomerSearch()
{
	jQuery("#customerSearchSubmit").click(submitSearchForm);	
}
			
function submitSearchForm()
{	
	// remove global messages if is neccesary
	removeGlobalMessageOnFragment();
	
	var info = jQuery("#unitSearchForm").serialize();
	
	jQuery.ajax
	({  
		url: "<c:url value="/my-account/favorite-customers/search.json" />",
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
				$('#b2bUnitResult').empty();
				$('#b2bUnitResult').addClass("b2bUnitResult");	
				
				var table = $("<table>", {});
				table.append("<thead><tr class='firstrow'>" +
							"<th class='col_center'><h3><spring:theme code='text.account.favoriteCustomers.column.id' text='Id'/></h3></th>" +
							"<th><h3><spring:theme code='text.account.favoriteCustomers.column.name' text='Name'/></h3></th>" +
							"<th><h3><spring:theme code='text.account.favoriteCustomers.column.city' text='City'/></h3></th>" +
							"<th><h3><spring:theme code='text.account.favoriteCustomers.column.state' text='State'/></h3></th>" +
							"<th class='col_center'><h3><spring:theme code='text.account.favoriteCustomers.column.actions' text='Actions'/></h3></th>" +
							"</thead></tr>");
				
				if (data.units != null && data.units.length > 0)
				{	
					$('#b2bUnitResult').css("overflow","scroll");
					$('#b2bUnitResult').css("height","200px");
					
					$.each(data.units, function(index)
					{						
						table.append("<tr>" +
									"<td align='center'>" + data.units[index].uid + "</td>" +
									"<td>" + data.units[index].name + "</td>" +
									"<td>" + data.units[index].city + "</td>" +
									"<td>" + data.units[index].state + "</td>" +
									"<td align='center'><a href='javascript:void(0);' onclick='addToFavorites(\""+ data.units[index].uid +"\")'><spring:theme code='text.account.favoriteCustomers.add' text='Add'/></a></td>" +
									"</tr>");		                   			   					   
					});
				}
				else
				{
					$('#b2bUnitResult').css("overflow","");
					$('#b2bUnitResult').css("height","");
					
					table.append("<tr><td colspan='5'><spring:theme code='text.account.favoriteCustomers.search.noResults' text='No results'/></td></tr>");
				}
								
				$('#b2bUnitResult').append(table);
			 						
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