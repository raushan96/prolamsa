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

<c:set var="contactUsBaseUrl" value="/contactUs/sendEmail" />
<spring:url var="contactUsUrl" value="${contactUsBaseUrl}" />


<div class="item_container_holder" style="display: block;">
	<div class="title_holder">
		<h2><spring:theme code="text.contact.title" /></h2>
	</div>

	<div class="search-form" style="display: block;">
		<%-- <form:form id="searchForm" class="js-form" action="${backorderDetailSearchUrl}" method="get"> --%>
		<form:form  id="contactUsForm" class="js-form" action="${contactUsUrl}" method="get">
		
			<div class=" class="item_container_holder"">
				<br>
		 		<table   class="product_table" >
		       		<tr>
		           		<td>		             
		             		<span>
		                 		<spring:theme code="text.contact.p1"/>
		             		</span>
		             		<span>
		                 		<spring:theme code="text.contact.p2"/>
		             		</span> 
		              		<span>
		                 		<spring:theme code="text.contact.p3"/>
		             		</span>
		           		</td>
		       		</tr>
		      	</table> 
		       
		       	<table   class="product_table" >
					<tr>
		          		<td style="width:200px;">
		             		<span class="required">*</span>
		             		<spring:theme code="text.contact.name" />
		         		</td>		             		     
		         		<td>		  
		             		<input type="text" id="name" name="name" style="width: 350px; border-color: black black black black;"  required="required" value="${contactUsForm.name}"/>		                        		             
		         		</td>
		       		</tr>
		        	<tr>
		         		<td>
		            		<span class="required">*</span>
		            		<spring:theme code="text.contact.lastName" />
		         		</td>
		         		<td>
		             		<input type="text" id="lastName" name="lastName"style="width: 350px; border-color: black black black black;"  required="required" value="${contactUsForm.lastName}"/>		             
		         		</td>
		       		</tr>
		            <tr>
		         		<td>
		             		<span class="required">*</span>
		             		<spring:theme code="text.contact.email" />
		         		</td>
		         		<td>
		             		<input type="text" id="email" name="email" style="width: 350px; border-color: black black black black;"  required="required" value="${contactUsForm.email}"/>
		             	</td>
		       		</tr>
		       		<tr>
		         		<td>
		             		<span class="required">*</span>
		             		<spring:theme code="text.contact.phone" />
		         		</td>
		         		<td>
		            		<input type="text" id="phone" name="phone" style="width: 350px; border-color: black black black black;"  required="required" value="${contactUsForm.phone}"/>
		         		</td>
		       		</tr>
		       		<tr>
				        <td>
				        	<spring:theme code="text.contact.altPhone" />
				        </td>
		         		<td>
		            		<input type="text" id="altPhone" name="altPhone" style="width: 350px; border-color: black black black black;" value="${contactUsForm.altPhone}" />		             
		         		</td>
		       		</tr>
		       		<tr>
		         		<td>
		             		<span class="required">*</span>
		             		<spring:theme code="text.contact.contactArea" />
		         		</td>
		         		<td>		   
		            		<select style="width: 350px; border-color: black black black black;" id="contactArea" name="contactArea" class="search-form-options" required="required">
		              			<option value="0">
		                 			<spring:theme code="text.contact.option0" />
		              			</option>
		              			<option value="1">
		              				<spring:theme code="text.contact.option1" />
		              			</option>
		              			<option value="2">
		              				<spring:theme code="text.contact.option2" />
		              			</option>
		              			<option value="3">
		              				<spring:theme code="text.contact.option3" />
		              			</option>
		            		</select>         		             		            									      
		         		</td>
		       		</tr>
       	       		<tr>
		         		<td>
		             		<span class="required">*</span>
		             		<spring:theme code="text.contact.comment"/>
		         		</td>
		         		<td>
		             		<textarea  style="border-color: black black black black;" rows="5" cols="45" id="comment" name="comment" placeholder="${placeHolder}" required="required">${contactUsForm.comment}</textarea>	
			      		</td>
		       		</tr>
		       		<tr>
		       			<td></td>
		          		<td align="center">
		             		<formUtil:formButton
							id="sendSubmit"
							type="submit" 
							css="button yellow positive button-float-right"
							tabindex="105"
							springThemeText="text.contact.send" />
				        </td>	
		          		<td style="width: 600px;"></td>	          
		       		</tr>
	       		</table>
		       	<c:if test="${baseStore.uid eq '2000'}">
		       		<table>
		       			<tr>
	      					<td>
				              	<span>
					              	BY PHONE:<br/>
								  	Tel: (281) 494-0900 ext. 2<br/> 
								  	Monday to Friday from 8 am to 5 pm<br/>
								    Saturday - Closed<br/>
									Sunday - Closed<br/>
								</span>		              
		          			</td>
		          			&nbsp;&nbsp;&nbsp;&nbsp;
		          			<td>
				            	<span>
						        	BY MAIL:<br/>
									Prolamsa, Inc.<br/>
									770 South Post Oak Lane, Suite 200,<br/>
									Houston, TX 77056<br/>
									Sales@prolamsausa.com<br/>
							 	</span>	
		          			</td>
		          			&nbsp;&nbsp;&nbsp;&nbsp;
	          				<td>
				            	<span>
				              		BY FAX:<br/>
		                      		Fax: (281) 494-0990<br/>
		                      		Sales Fax: (713) 579-2666<br/>
		                      	<br/><br/>
							  	</span>	
		          			</td>
		       			</tr>	
	       			</table>
				</c:if>
				<c:if test="${baseStore.uid eq '1000'}">
		       		<table>
		       			<tr>
	      					<td>
				              	<span>
					              	OFICINAS CORPORATIVAS<br/>
								  	Carretera a Colombia Km 5.75<br/>
								    General Escobedo, N.L. México<br/>
									C.P. 66052<br/>
									Tel: (81) 8154 0200<br/>
								</span>		              
		          			</td>
	          			</tr>
          			</table>
        		</c:if>
        		<c:if test="${baseStore.uid eq '5000'}">
		       		<table>
		       			<tr>
	      					<td>
				              	<span>
					              	Información Pendiente<br/>
								</span>		              
		          			</td>
	          			</tr>
          			</table>
        		</c:if>
        		<c:if test="${baseStore.uid eq '6000'}">
		       		<table>
		       			<tr>
	      					<td style="width: 200px;">
				              	<span>
					              	PHONE<br/>
								  	979-599-7600<br/> 
								  	Monday to Friday from 8 am to 5 pm<br/>
								    Saturday - Closed<br/>
									Sunday - Closed
								</span>	
		          			</td>
							<td style="width: 200px;">
				              	<span>
					              	CORPORATE<br/>
									770 South Post Oak Lane, Suite 200,<br/>
									Houston, Texas 77056<br/>
									<br/><br/>
								</span>	
							</td>
		          			<td style="width: 200px;">
				              	<span>
					              	MILL<br/>
									1451 Louis Mikulin Rd<br/>
									Bryan, Texas 77807<br/>
									<br/><br/>
								</span>	
							</td>		              
	          			</tr>
          			</table>
        		</c:if>
			</div>
		</form:form>
	</div>
</div>

<script type="text/javascript">
onDOMLoaded(initcontactUs);

function initcontactUs()
{		
	jQuery("#sendSubmit").click(sendSubmit);	
}


function sendSubmit()
{
	var form = jQuery("#contactUsForm");	
	
	// submit form
	jQuery(form).submit();
}
</script>
