<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>

<c:set var="b2bUnitBaseUrl" value="/client/search/list" />
<spring:url var="b2bUnitSearchUrl" value="${b2bUnitBaseUrl}" />

<div class="title">
	<h3><spring:theme code="advanced-client-search.title"/></h3>
	<a href="#" class="close" id="ajax_cart_close" title="<spring:theme code="popup.close"/>" alt="<spring:theme code="popup.close"/>"></a>
</div>

<div class="content">
 
   <form:form id="searchForm" class="js-form" action="${b2bUnitSearchUrl}" method="get">
 	  <table >
	    <tr>
	      <td class="noBorder">
	          <spring:theme code="advanced-client-search.clientId" />          					
	      </td>
	      <td class="noBorder">
	         
	         <formUtil:formInputTextInline 
					path=""
					id="uid"
					mandatory="false"
					type="text"
					name="uid"
					value=""
					tabindex="102"
					divCSS="search-form-options"	
					css="shortInput"			
					asterisk=""
					inputCSS="" />      
	      </td> 
	    </tr>
	    
	    <tr>     
	      <td class="noBorder">
	          <spring:theme code="advanced-client-search.clientName" />						
	      </td>
	      <td class="noBorder">         
	         <formUtil:formInputTextInline 
					path=""
					id="name"
					mandatory="false"
					type="text"
					name="name"
					value=""
					tabindex="103"
					divCSS="search-form-options"				
					asterisk=""
					css="longInput"
					inputCSS="" />
	      </td>
	    </tr>        
	  </table>
	  <hr/>
	  <div class="search-form-options-search-button">
			<formUtil:formButton
				id="searchSubmit"
				type="button" 
				css="positive right"
				tabindex="104"
				springThemeText="invoice.button.search" />
	  </div>			
		
  </form:form>     
</div>

<form:form modelAttribute="b2bUnitForm" method="post" action="${b2bUnitSearchUrl}" class="js-form">

<div id="b2bUnitResult" class="content" style="overflow:scroll; height:200px;">
</div>

</form:form>

<script type="text/javascript">

jQuery(document).ready(function()
		{
	        	        
			jQuery("#searchSubmit").click(submitSearchForm);				   		    
			
			//jQuery('a#selectLink').live('click',function (e) {
			/* jQuery('a#selectLink').on('click', function (e) {
		        e.preventDefault();		        
		        var caption = jQuery(this).data('caption');
       		       		        
		        //alert(caption);
		        //alert(jQuery("#b2bUnitSelectorElement").val());
		        
		        if (caption != jQuery("#b2bUnitSelectorElement").val() )
		         {		        	
		        	jQuery("#b2bUnitSelectorElement").val(caption);
		        	jQuery("#b2bUnitSelectorElement").change();	
		        	 return false;
		         }	else {		        	 
		        	 ACC.modals.messageModal("<spring:theme code="error"/>", "<spring:theme code='advanced-client-search.messageSelected'/>");
		        	 return false;
		         }	    	  		        
		    }); */
						
			jQuery(document).on('cbox_complete', function ()
			{
				jQuery.colorbox.resize();
			});
});
			
function compareB2B(caption)
{		
        
    if (caption != jQuery("#b2bUnitSelectorElement").val() )
     {  	    	
    	jQuery("#b2bUnitSelectorElement").val(caption);
    	jQuery("#b2bUnitSelectorElement").change();	    	
    	 return false;
     }	else {		        	 
    	 ACC.modals.messageModal("<spring:theme code="error"/>", "<spring:theme code='advanced-client-search.messageSelected'/>");
    	 return false;
     }	  
	
}

function submitSearchForm()
{	
	var info = jQuery("#searchForm").serialize();
	
	jQuery.ajax
	({  
		url: "<c:url value="/client/search/list.json" />",
		type: 'POST',
		dataType: 'json',
		//contentType: 'application/json; charset=UTF-8',
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
				$("#b2bUnitResult").append("<table id='resultTable'>");
				$("#b2bUnitResult").append("<thead>");
				$("#b2bUnitResult").append("<tr>");
				$("#b2bUnitResult").append("<td class='labelSmall col_center' width='50'>&nbsp;</td>"+ 
				                           "<td class='labelSmall col_center' width='50'><spring:theme code='advanced-client-search.clientId' /></td>" +
						                   "<td class='labelSmall col_center' width='230'><spring:theme code='advanced-client-search.clientName' /></td>" +
				                           "<td class='labelSmall col_center' width='80'><spring:theme code='advanced-client-search.city' /></td>" +
				                           "<td class='labelSmall col_center' width='80'><spring:theme code='advanced-client-search.state' /></td>");
				$("#b2bUnitResult").append("</tr>");
				$("#b2bUnitResult").append("</thead>");
				
				$("#b2bUnitResult").append("<hr/>");

				//get all unit of the current user
				var options = $('#b2bUnitSelectorElement option');
				// next translate that into an array of just the values
				var optionsValues = $.map(options, function(elt, i) { return $(elt).val();});

				if (data.units != null)
				{
					$.each(data.units, function(index) {
						$("#b2bUnitResult").append("<tr>");		
						
						//must validate to show only units for the current user														
						if ( optionsValues.indexOf(data.units[index].uid) != -1 ) {
							$("#b2bUnitResult").append("<td class='labelSmall' width='50'><a href='#' id='selectLink' onclick='compareB2B(\""+ data.units[index].uid +"\")' data-caption='"+ data.units[index].uid +"'>SELECT</a></td>" +
							         "<td class='labelSmall' width='50'>" + data.units[index].uid + "</td>" +
									 "<td class='labelSmall' width='230'>" + data.units[index].name + "</td>" +
									 "<td class='labelSmall' width='80'>" + data.units[index].city + "</td>" +
									 "<td class='labelSmall' width='80'>" + data.units[index].state + "</td>");			                   			   					   
							 $("#b2bUnitResult").append("</tr>");			      	
					    }	
					});
				}
				 $("#b2bUnitResult").append("</table>");				 						
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



jQuery(ACC.advancedclientsearch.popup).find("#ajax_cart_close").click(function(e)
{
	e.preventDefault();
	jQuery(ACC.advancedclientsearch.popup).hide();
});


</script>