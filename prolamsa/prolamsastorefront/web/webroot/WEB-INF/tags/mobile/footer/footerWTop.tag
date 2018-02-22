<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>

<HR style="height: 3px; padding: 0px; margin: 0px; background: black; width: 100%;border: 0; ">
<div id="footerwt-container">

<c:if test="${searchPageData.pagination.totalNumberOfResults gt 0}" >
	<table style="margin: 0; padding: 0;">
		<tr>
			<td style="text-transform: uppercase; width: 100%; padding: 0 0 0 140px;">
				<a href="#top" style="font-weight: 800; vertical-align: baseline; text-decoration: none; font-size: 11px;">Go to top </a>
				
			</td>
		</tr>
	</table>
</c:if>

</div>

<div style="width: 100%; height: 70px; float: left;"></div>
