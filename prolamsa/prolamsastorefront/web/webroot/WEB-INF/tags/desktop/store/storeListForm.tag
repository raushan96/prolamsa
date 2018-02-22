<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="locationQuery" required="false" type="java.lang.String" %>
<%@ attribute name="geoPoint" required="false" type="de.hybris.platform.commerceservices.store.data.GeoPoint" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/desktop/store" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>

<c:set value="/store-finder?q=${param.q}" var="searchUrl" />
<c:if test="${not empty geoPoint}">
	<c:set value="/store-finder?latitude=${geoPoint.latitude}&longitude=${geoPoint.longitude}&q=${param.q}" var="searchUrl" />
</c:if>

<script type="text/javascript">
	/*<![CDATA[*/
	function getImageUrl(img, loopIndex) {
		var imageSrc = 'http://maps.google.com/mapfiles/marker' + String.fromCharCode(loopIndex + 65) + '.png';
		if (img.src != imageSrc) { // don't get stuck in an endless loop
			img.src = imageSrc;
		}
	}
	/*]]>*/
</script>


<c:if test="${searchPageData ne null and !empty searchPageData.results}">
	<table id="store_locator">
		<thead>
		<tr>
			<th id="header1"><spring:theme code="storeFinder.table.store"/></th>
			<th id="header2"><spring:theme code="storeFinder.table.distance"/></th>
			<th id="header3"><spring:theme code="storeFinder.table.address"/></th>
			<th id="header4"><spring:theme code="storeFinder.table.opening"/></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${searchPageData.results}" var="pos" varStatus="loopStatus">
			<c:url value="${pos.url}" var="posUrl"/>
			<tr>
				<td headers="header1">										
					<a href="${posUrl}" class="shoplink">
						<img src="${commonResourcePath}/images/lightbox-blank.gif" onload="getImageUrl(this, ${loopStatus.index})" class="locationdesc"/>
						<ycommerce:testId code="storeFinder_result_image">
							<store:storeImage store="${pos}" format="cartIcon"/>
						</ycommerce:testId>
					</a>
					
						<span class="store_details">
							<a href="${posUrl}" ><spring:theme code="storeFinder.table.view.map"/></a><br/><br/>
							<h2>
								<ycommerce:testId code="storeFinder_result_link">
									<a href="${posUrl}">${pos.name}</a>
								</ycommerce:testId>
							</h2>
							<p>${pos.address.phone}</p>
						</span>
				</td>
				<td headers="header2">
					<p>${pos.formattedDistance}</p>
				</td>
				<td headers="header3">
					<ul>
						<ycommerce:testId code="storeFinder_result_address_label">
							<c:if test="${not empty pos.address}">
								<li>${pos.address.line1}</li>
								<li>${pos.address.line2}</li>
								<li>${pos.address.town}</li>
								<li>${pos.address.postalCode}</li>
							</c:if>
						</ycommerce:testId>
					</ul>
				</td>
				<td headers="header4">
					<store:openingSchedule openingSchedule="${pos.openingHours}"/>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4">
				<nav:pagination top="false"
								supportShowAll="false"
								supportShowPaged="false"
								searchPageData="${searchPageData}"
								searchUrl="${searchUrl}"
								msgKey="text.storefinder.desktop.page"
								numberPagesShown="${numberPagesShown}"/>
			</td>
		</tr>
		</tbody>
	</table>
</c:if>
