<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="details" required="true" type="de.hybris.platform.core.model.product.ProductModel" %>

<div class="featureClass" style="height: 520px">
	<table>
		<tbody>
			<tr>
				<td class="attrib"><spring:theme code="product.list.header.part" /></td>
				<td><spring:theme code="${product.baseCode}"/></td>
			</tr>

			<tr>
				<td class="attrib"><spring:theme code="product.list.header.customer.description" /></td>
				<td><spring:theme code="${product.customerDescription}"/></td>
			</tr>

			<tr>
				<td class="attrib"><spring:theme code="product.list.header.commercial.description" /></td>
				<td><spring:theme code="${product.commercialDescription}"/></td>
			</tr>

			<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
			<tr>
				<td class="attrib"><spring:theme code="product.list.header.manufacturing.description" /></td>
				<td><spring:theme code="${product.manufacturingDescription}"/></td>
			</tr>
			</sec:authorize>

			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.pcs-bundle" /></td>
				<td><fmt:formatNumber pattern="###,###,###,###,##0"  value="${product.piecesPerBundle}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.wallthickness" /></td>
				<td><spring:theme code="${details.wallThickness}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.dimenision-a" /></td>
				<td><spring:theme code="${details.dimA}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.dimenision-b" /></td>
				<td><spring:theme code="${details.dimB}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.finished" /></td>
				<td><spring:theme code="${details.finish}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.quality" /></td>
				<td><spring:theme code="quality_${details.quality}"/></td>
			</tr>
			
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.product-family" /></td>
				<td><spring:theme code="family_${details.family}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.section" /></td>
				<td><spring:theme code="section_${details.section}"/></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${baseStore.uid eq '1000' || baseStore.uid eq '5000'}">
						<td class="attrib"><spring:theme code="product.list.detail.lengthmm" /></td>
						<td><spring:theme code="${details.lengthmm}"/></td>
					</c:when>
					<c:otherwise>
						<td class="attrib"><spring:theme code="product.list.detail.length" /></td>
						<td><spring:theme code="${details.length}"/></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.standard" /></td>
				<td><spring:theme code="${details.norm}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.covering" /></td>
				<td><spring:theme code="covering_${details.covering}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.steel" /></td>
				<td><spring:theme code="steel_${details.steel}"/></td>
			</tr>
			<tr>
				<td class="attrib"><spring:theme code="product.list.detail.material-type" /></td>
				<td><spring:theme code="materialType_${details.materialType}"/></td>
			</tr>
	    </tbody>
	</table>
</div>



