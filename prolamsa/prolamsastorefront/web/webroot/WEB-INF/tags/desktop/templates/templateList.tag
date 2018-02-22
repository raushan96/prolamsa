<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<script  type="text/javascript">
/*<![CDATA[*/
           
    onDOMLoaded(initDraftTemplate);

	function initDraftTemplate()
	{	
		$("#balance_statement tr:even").addClass("filaImpar");
    	$("#balance_statement tr:odd").addClass("filaPar");
	}       
    
	function showEntryDraft(wishlistPK)
	{				
		location.href="detail?wishlist=" + wishlistPK;				
	}	
	
	function confirmLoadTemplate(wishlistPK)
	{
     	ACC.modals.confirmModalOK("<spring:theme code="templates.load.title" />", "<spring:theme code="templates.load.confirm" />", 
			function () { loadCartTemplate(wishlistPK) },
			null);
	}

	function loadCartTemplate(wishlistPK)
	{
      	
$.ajax
	({
		url:         "<c:url value="/templates/loadTemplateToCart.json" />",
		type:        'POST',
		dataType:    'json',
		data: { wishlistPK: wishlistPK},
		error: function (xht, textStatus, ex)
		{
			ACC.modals.messageModal("Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
       	},
       	success: function (data)
       	{
       		if (data.status == 0)
           	{
           		ACC.modals.messageModal("<spring:theme code="templates.load.title"/>", "<spring:theme code="templates.load.success"/>");
               	location.reload(true);
           	}
       		else
       			ACC.modals.messageModal("Error:" + data.message);
       	}
	});
} 

      function confirmDeleteTemplate(wishlistPK, type)
      {
    	var title = "<spring:theme code="templates.delete.title"/>";
    	var msg = "<spring:theme code="templates.delete.confirm"/>";

     	if (type == "DRAFT")
     	{
     		title = "<spring:theme code="draft.delete.title"/>";
     		msg = "<spring:theme code="draft.delete.confirm"/>";
     	}

      	ACC.modals.confirmModalOK(title, msg, 
      			function () { deleteCartTemplate(wishlistPK, type) },
      			null);
      	}

      function deleteCartTemplate(wishlistPK, type)
     	{
      	var title = "<spring:theme code="templates.delete.title"/>";
    	var msg = "<spring:theme code="templates.delete.success"/>";

     	if (type == "DRAFT")
     	{
     		title = "<spring:theme code="draft.delete.title"/>";
     		msg = "<spring:theme code="draft.delete.success"/>";
     	}

  		$.ajax
  			({
  				url:         "<c:url value="/templates/deleteTemplate.json" />",
  				type:        'POST',
  				dataType:    'json',
  				data: { wishlistPK: wishlistPK},
  				error: function (xht, textStatus, ex)
  				{
  					ACC.modals.messageModal("Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
  	        	},
  	        	success: function (data)
  	        	{
  	        		if (data.status == 0)
  	            	{
  	            		ACC.modals.messageModal(title, msg);
  	                	location.reload();
  	            	}
  	        		else
  	        			ACC.modals.messageModal("Error:" + data.message);
  	        	}
  			});
      }
/*]]>*/
</script>

<div class="item_container_holder" style="display: block;">
	<div class="title_holder">
		<h2><spring:theme code="templates.list.header.title" text="Shopping Cart Draft & Templates"/></h2>
	</div>

	<c:choose>
		<c:when test="${not empty templates[0].b2bUnit.uid}">      	      	              	
	   		<div class="item_container_holder">
		   		<table >
			    	<tr>	         	
				    	<td width="20"><spring:theme code="templates.list.client"/>:</td>
					  	<td class="cart-id-nr">${templates[0].b2bUnit.shortName} (${templates[0].b2bUnit.uid})</td>		   
			    	</tr>
			  	</table>
			  </div>
			  <div class="item_container">
		   		<table id="balance_statement" class="balanceStatement-list" data-zebra="tbody tr">	 
		    		<tr class="firstrow">
			   			<td><h3><spring:theme code="templates.list.header.type" /></h3></td>		
						<td><h3><spring:theme code="templates.list.header.name" /></h3></td>			
						<td><h3><spring:theme code="templates.list.header.creationDate" /></h3></td>
						<td><h3><spring:theme code="templates.list.header.actions" /></h3></td>
						<td></td>		
					</tr>
					<c:forEach items="${templates}" var="eachTemplate" varStatus="status">
						<tr>				   				
							<td id="type" class="col_center" style="vertical-align: text-top; padding-top: 5px; height: 20px;"><spring:theme code="templates.list.header.type.${eachTemplate.type.code}" /></td>
							<td  id="name" class="col_center" style="vertical-align: text-top; padding-top: 5px; height: 20px;"><a href="#" id="nameLink_${eachTemplate.name}" class="linkDraft left"  onclick="showEntryDraft(${eachTemplate.pk})">${eachTemplate.name}</a></td>				
							<td class="col_center" style="vertical-align: text-top; padding-top: 5px; height: 20px;"><formDate:formFormatDate  value="${eachTemplate.creationtime}" pattern="MM/dd/yyyy" /> <%-- <fmt:formatDate value="${eachTemplate.creationtime}" pattern="MM/dd/yyyy" /> --%> </td>
							<td colspan="2" id="actions" class="col_center" style="vertical-align: text-top; padding-top: 5px; height: 20px;"><a href="#" id="actionLink_${eachTemplate.name}" class="linkDraft left"  onclick="showEntryDraft(${eachTemplate.pk})"><spring:theme code="templates.list.header.view"/></a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" onClick="confirmDeleteTemplate(${eachTemplate.pk}, '${eachTemplate.type}')" class="linkDraft"><spring:theme code="templates.list.header.deleteLink" /></a></td>
										    
						</tr>
					</c:forEach>		
		 		</table>
	 		</div>
	 	</c:when>	 
		<c:otherwise>
			<br/><p style="padding-left: 30px; font-size: 12px;"><spring:theme code="templates.list.notFound"/></p>
		</c:otherwise>
  	</c:choose>
 </div>