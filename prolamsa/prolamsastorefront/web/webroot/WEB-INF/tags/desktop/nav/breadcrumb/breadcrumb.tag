<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="breadcrumbs" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/" var="homeUrl"/>
<ul class="clearfix">
	<li>
		<a href="${homeUrl}"><spring:theme code="breadcrumb.home"/></a>
	</li>

	<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
	
		

			<c:choose>

				<c:when test="${breadcrumb.url eq '#'}">
					<li class="${breadcrumb.linkClass}">
					<a href="#" onclick="return false;">&#062; ${breadcrumb.name}</a>
					</li>
				</c:when>

				<c:otherwise>
					<c:url value="${breadcrumb.url}" var="breadcrumbUrl"/>
					<c:if test="${not status.last}">
					<li class="${breadcrumb.linkClass}">
						<a href="${breadcrumbUrl}">&#062; ${breadcrumb.name}</a>
					</li>
					</c:if>
					<c:if test="${status.last}">
						<li class="active">
							<a href="${breadcrumbUrl}">&#062; ${breadcrumb.name}</a>
						</li>
					</c:if>
					
				</c:otherwise>

			</c:choose>
		
	</c:forEach>
</ul>
