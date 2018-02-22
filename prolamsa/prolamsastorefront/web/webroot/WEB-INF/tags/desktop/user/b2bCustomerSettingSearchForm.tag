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

<c:set var="createNoticeBaseURL" value="/my-account/notices/create" />
<spring:url var="createNoticeURL" value="${createNoticeBaseURL}" />



<div class="item_container_holder" style="display: block;">

	<form:form  enctype="multipart/form-data" id="createNoticeForm" class="js-form" action="${createNoticeURL}" method="post">
				
		<div>
		    <table style="table-layout: fixed;">
		    <tbody>
		  		<tr>
				     <td>
				       <spring:theme code="notices.list.name" />*
				    </td>
				    <td>
				       <input type="text" id="name" name="name" value="${createNoticeForm.name}" size="30">
				    </td>
				    	
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>
				</tr>
				<tr>
				    <td>
				       <spring:theme code="notices.list.type" />*
				    </td>				      
				    <td>
				    	<select id="type" name="type">
						  <option value=""><spring:theme code="incident.add.selectType" /></option> 
							<c:forEach items="${noticeTypes}" var="eachType">
							    <c:choose>
									<c:when test="${createNoticeForm.type eq eachType}">
										<c:set var="selected" value="selected" />
									</c:when>
									<c:otherwise>
										<c:set var="selected" value="" />
									</c:otherwise>
								</c:choose>
							 <option value="${eachType}" ${selected} ><spring:theme code="notices.type.${eachType}" /></option>
						  </c:forEach>
						</select>
				   
				    </td>	
				    <td>
				    &nbsp;
				    </td>
				    <td>
				    &nbsp;
				    </td>	
			    </tr>
			    <tr>
			    	<td>
				       <spring:theme code="notices.list.media" />*
				    </td>
				    <td>
				       <input type="file" id="media" name="media">
				    </td>
			    </tr>
			    <tr>
			  		<td>
				    &nbsp;
				    </td>	
				    <td>
				    <div class="search-form-options">
							<button class="button positive button-float-right" id="addNoticeFormSubmit" ><spring:theme code='notices.list.create'/></button>
						</div>
				    </td>	
				    <td>
				    &nbsp;
				    </td>	
			    	<td>
			    		
					</td>		
			    </tr>
			     </tbody>
		    </table>
	
																
	
		</div>
	 
	</form:form>
	
   
</div>

<script type="text/javascript">

// onDOMLoaded();




</script>