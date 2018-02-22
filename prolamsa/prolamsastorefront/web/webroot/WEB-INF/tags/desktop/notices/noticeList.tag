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
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="formDate" tagdir="/WEB-INF/tags/desktop/formUtil" %>

<%@ taglib prefix="notices" tagdir="/WEB-INF/tags/desktop/notices" %>


	<div class="title_holder">
		<h2><spring:theme code="notice.list.title" text="Notices"/></h2>
	
	</div>
	
	
	
	<sec:authorize  ifAnyGranted="ROLE_NOTICES_ADMIN">
		<br />
		<notices:addNoticeForm/>
		<br/>
	</sec:authorize>



<div class="item_container">
	<h3 class="notices-h3"><spring:theme code="notice.list.title.catalog" text="Notices"/></h3>
	<table   class="notices-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="firstrow">
				<td class="">
					<h3><spring:theme code="notices.list.name" /></h3> 
				</td>
				<td class="">
					<h3><spring:theme code="notices.list.date" /></h3>
				</td>
				<sec:authorize  ifAnyGranted="ROLE_NOTICES_ADMIN">
					<td></td>
				</sec:authorize>
			</tr>
		</thead>

		<tbody class="">
			<c:forEach items="${notices}" var="eachNotice" varStatus="status">
				<c:if test="${eachNotice.type eq 'CATALOG'}">
					<tr class="">
						<td class="Text_Table_Align_Center">
							<c:url value="/my-account/notices/media?noticeCode=${eachNotice.code}" var="noticeMediaURL" />
							<a href="${noticeMediaURL}"> ${eachNotice.name}</a>
						</td>
	
						<td class="Text_Table_Align_Center">
							<%-- <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${eachNotice.publishedDate }" /> --%>
							<formDate:formFormatDate pattern="MM/dd/yyyy" value="${eachNotice.publishedDate}" />
						</td>
						<sec:authorize  ifAnyGranted="ROLE_NOTICES_ADMIN">
							<td>
								<c:url value="/my-account/notices/delete?noticeCode=${eachNotice.code}" var="noticeDeleteURL" />								
								<a href="${noticeDeleteURL}" class="deleteAttachment">
									<img src="${themeResourcePath}/images/delete-attachment.png" alt="Delete">
								</a>
							</td>
						</sec:authorize>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="item_container">
	<c:choose>
			<c:when test="${baseStore.uid eq '2000'}">
				<h3 class="notices-h3"><spring:theme code="notices.list.title.notice.2000" text="Catalogs"/></h3>
			</c:when>
			<c:otherwise>
				<h3 class="notices-h3"><spring:theme code="notices.list.title.notice" text="Catalogs"/></h3>
			</c:otherwise>
	</c:choose>
	<table   class="notices-list" data-zebra="tbody tr">
		<thead class="">
			<tr class="firstrow">
				<td class="">
					<h3><spring:theme code="notices.list.name" /></h3> 
				</td>
				<td class="">
					<h3><spring:theme code="notices.list.date" /></h3>
				</td>
				<sec:authorize  ifAnyGranted="ROLE_NOTICES_ADMIN">
					<td></td>
				</sec:authorize>
			</tr>
		</thead>

		<tbody class="">
			<c:forEach items="${notices}" var="eachNotice" varStatus="status">
				<c:if test="${eachNotice.type eq 'NOTICE'}">
					<tr class="">
						<td class="Text_Table_Align_Center">
							<c:url value="/my-account/notices/media?noticeCode=${eachNotice.code}" var="noticeMediaURL" />
								<a href="${noticeMediaURL}"> ${eachNotice.name}</a>
						</td>
	
						<td class="Text_Table_Align_Center">
							<%-- <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${eachNotice.publishedDate }" /> --%>
							<formDate:formFormatDate pattern="MM/dd/yyyy" value="${eachNotice.publishedDate}" />
						</td>
						<sec:authorize  ifAnyGranted="ROLE_NOTICES_ADMIN">
							<td>
								<c:url value="/my-account/notices/delete?noticeCode=${eachNotice.code}" var="noticeDeleteURL" />
								<a href="${noticeDeleteURL}" class="deleteAttachment">
									<img src="${themeResourcePath}/images/delete-attachment.png" alt="Delete">
								</a>
							</td>
						</sec:authorize>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</div>

<!-- <iframe name="iframePost" style="visibility: hidden;"></iframe>-->

<script type="text/javascript">
// onDOMLoaded(initIncidentList);


</script>

