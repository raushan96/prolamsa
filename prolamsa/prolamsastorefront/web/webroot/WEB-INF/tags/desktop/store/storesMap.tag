<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="storeSearchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<c:if test="${storeSearchPageData ne null and !empty storeSearchPageData.results}">
	<div class="store_map" id="map_canvas"></div>


<script type="text/javascript"> // set vars
	
		var map;
		var markers = [];
		var infowindow;
	
		var singlePosgeoPointlatitude = new Array();
		var singlePosgeoPointlongitude = new Array();
		var singlePosname = new Array();
	<c:forEach items="${storeSearchPageData.results}" var="singlePos">
		singlePosgeoPointlatitude.push("${singlePos.geoPoint.latitude}");
		singlePosgeoPointlongitude.push("${singlePos.geoPoint.longitude}")
		singlePosname.push("${singlePos.name}");
	</c:forEach>
	
	
	var drawMapStores = true;
	var latitude = ${storeSearchPageData.sourceLatitude};
	var longitude = ${storeSearchPageData.sourceLongitude};
	var boundSouthLatitude = ${storeSearchPageData.boundSouthLatitude};
	var boundWestLongitude = ${storeSearchPageData.boundWestLongitude};
	var boundNorthLatitude = ${storeSearchPageData.boundNorthLatitude};
	var boundEastLongitude = ${storeSearchPageData.boundEastLongitude};

</script>

</c:if>
