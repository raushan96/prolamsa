<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url value="${product.url}/reviewhtml/3" var="getPageOfReviewsUrl"/>
<c:url value="${product.url}/reviewhtml/all" var="getAllReviewsUrl"/>
<script type="text/javascript"> // set vars
/*<![CDATA[*/
	var showReviewForm = <c:out value="${showReviewForm}" default="false"/>;
	var getPageOfReviewsUrl = '${getPageOfReviewsUrl}';
	var getAllReviewsUrl = '${getAllReviewsUrl}';
/*]]>*/
</script>

<div id="reviews"></div>
<div id="write_reviews" style="display:none">
	<ul class="review_actions">
		<li><a href="#" id="read_reviews_action"><spring:theme code="review.back"/></a></li>
	</ul>
	<div class="write_review">
		<h3><spring:theme code="review.write.title"/></h3>
		<p><spring:theme code="review.write.description"/></p>
		<p><spring:theme code="review.required"/></p>
		<c:url value="/${product.name}/p/${product.code}/review" var="productReviewActionUrl"/>
		<form:form method="post" action="${productReviewActionUrl}" commandName="reviewForm">
			<formUtil:formInputBox idKey="review.headline" labelKey="review.headline" path="headline" inputCSS="text" mandatory="true"/>
			<formUtil:formTextArea idKey="review.comment" labelKey="review.comment" path="comment" areaCSS="textarea" mandatory="true"/>
			<spring:bind path="rating">
				<c:if test="${not empty status.errorMessages}">
					<span class="form_field_error">
				</c:if>
				<div class="form_field-label"><label><spring:theme code="review.rating"/>:</label></div>
				<div id="stars-wrapper">
					<c:forEach begin="1" end="5" varStatus="status">
						<label><img class="no_star" src="${commonResourcePath}/images/jquery.ui.stars.custom.gif" alt="<spring:theme code="review.rating.alt"/>"/><form:radiobutton path="rating" value="${status.index}"/>${status.index}/${status.end}</label></br>
					</c:forEach>
				</div>
				<p><form:errors path="rating" /></p>
				<c:if test="${not empty status.errorMessages}">
					</span>
				</c:if>
				</spring:bind>
				<div class="form_field-label"><label for="alias"><spring:theme code="review.alias"/>:</label></div>
				<div class="form_field-input"><form:input path="alias" /></div>
			<div style="clear:both;"></div>
			<span style="display: block; clear: both;">
				<button class="form" type="submit" value="<spring:theme code="review.submit"/>"><spring:theme code="review.submit"/></button>
			</span>
		</form:form>
	</div>
</div>