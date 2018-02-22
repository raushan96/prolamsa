<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="css" required="false" type="java.lang.String" %>

<%@ attribute name="divCSS" required="false" type="java.lang.String" %>
<%@ attribute name="inputCSS" required="false" type="java.lang.String" %>
<%@ attribute name="id" required="false" type="java.lang.String" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ attribute name="name" required="false" type="java.lang.String" %>
<%@ attribute name="tabindex" required="false" type="java.lang.String" %>
<%@ attribute name="datarequired" required="false" type="java.lang.String" %>
<%@ attribute name="datatype" required="false" type="java.lang.String" %>
<%@ attribute name="datawarningtext" required="false" type="java.lang.String" %>
<%@ attribute name="value" required="false" type="java.lang.String" %>
<%@ attribute name="placeholderSpringThemeText" required="false" type="java.lang.String" %>
<%@ attribute name="path" required="true" type="java.lang.String" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="springThemeText" required="false" type="java.lang.String" %>
<%@ attribute name="asterisk" required="false" type="java.lang.String" %>
<%@ attribute name="onChange" required="false" type="java.lang.String" %>
<%@ attribute name="includeColon" required="false" type="java.lang.Boolean" %>
<%@ attribute name="spanCSS" required="false" type="java.lang.String" %>



<c:choose>
	<c:when test="${divCSS != 'stacked'}">
		<%--<div class="input-text inline ${divCSS}">--%>
		<div class="input-text ${divCSS}">
	</c:when>
	<c:otherwise>
		<div class="input-text ${divCSS}">
	</c:otherwise>
</c:choose>

<%-- <div class="input-text inline ${inputCSS}"> --%>
	<label for="${id}">
        <span class="${spanCSS}">

			<spring:theme code="${springThemeText}"/>

			<c:if test="${includeColon}">:</c:if>
			
			<%-- Show asterisk IF asterisk="Y"--%>
			<c:if test="${asterisk == 'Y'}">
				<span class="required">*</span>
			</c:if>

		</span>

		<%-- IF type is empty, add "text" --%>
		<c:if test="${empty type}">
			<c:set var="type" value="text" />
		</c:if>
		
		<input path="${path}"
			   mandatory="${mandatory}"
			   type="${type}"
			   name="${name}"
			   id="${id}"
			   tabindex="${tabindex}"
			   data-type="${datatype}"
			   data-required="${datarequired}"
			   data-warning="${datawarningtext}"
			   value="${value}"
			   placeholder="${placeholderSpringThemeText}"
			   class="styled ${css}"
			   asterisk="${asterisk}"
			   onchange="${onChange}"
			   style="width: 75%; background-color: #C6C3C3; border: solid 2px gray; margin-top: 10px;"
			>
    </label>
</div>