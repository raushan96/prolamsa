<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="products" required="true" type="java.util.List" %>
    
<div class="scroller">   
	<ul id="carousel_alternate" class="jcarousel-skin">
		<c:forEach items="${products}" var="product" varStatus="varStatus">
			<li>
				<span class="thumb">
					<a href="${contextPath}${product.url}?selLocation=${product.location.code}">
						<img src=""  data-primaryimagesrc="${product.url}" data-galleryposition="${varStatus.index}" alt="Part # ${product.baseCode}" title="Part # ${product.baseCode}" />
					</a>
				</span>
			</li>
		</c:forEach>
	</ul> 
</div>