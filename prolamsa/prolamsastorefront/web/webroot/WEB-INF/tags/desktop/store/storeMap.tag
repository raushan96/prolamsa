<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="store" required="false" type="de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${store ne null and store.geoPoint.latitude ne null and store.geoPoint.longitude ne null}">
	<div class="store_map_details" id="map_canvas"></div>
</c:if>


<script type="text/javascript"> // set vars

	var map;
	var markers = [];
	var infowindow;
	
	var latitude = ${store.geoPoint.latitude};
	var longitude = ${store.geoPoint.longitude};
	var storename = '${store.name}';
	var storeaddressline1 = '${store.address.line1}';
	var storeaddressline2 = '${store.address.line2}';
	var storeaddresstown = '${store.address.town}';
	var storeaddresspostalCode = '${store.address.postalCode}';
	var storeaddresscountryname = '${store.address.country.name}';
	
	var drawMapStore = true;
	
</script>