<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<%@ attribute name="b2bUnitList" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedB2BUnit" required="true" type="de.hybris.platform.b2b.model.B2BUnitModel" %>

<c:set var="selected" value="" />

<div>
	<span><spring:theme code="b2bunitselector.purchase-for"/></span>
	<select id="b2bUnitSelectorElement">
		<c:forEach items="${b2bUnitList}" var="eachFormattedB2BUnit">
			<c:choose>
				<c:when test="${eachFormattedB2BUnit.id eq selectedB2BUnit.uid}">
					<c:set var="selected" value="selected" />
				</c:when>
				<c:otherwise>
					<c:set var="selected" value="" />
				</c:otherwise>
			</c:choose>
	
			<option value="${eachFormattedB2BUnit.id}" ${selected}>${eachFormattedB2BUnit.formattedAddress}</option>
		</c:forEach>
	</select>
</div>

<script type="text/javascript">

onDOMLoaded(bind_b2bSelectorElement);

function bind_b2bUnitSelectorElement()
{
	jQuery.ajax
	({
		url: "<c:url value="/misc/setCustomerUnit.json" />",
		type: 'POST',
		dataType: 'json',
		data: {},
		beforeSend: function ()
		{
			blockUI();
		},
		success: function (data)
		{
			if (data != null)
			{
				if (data.status == 0)
				{
					ACC.modals.messageModal("<spring:theme code="b2bunitselector.confirm.title"/>", "<spring:theme code="b2bunitselector.customer-changed-refreshing-page"/>");
					location.reload();
				}
				else
					ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
			}
			else
			{
				ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed.");
			}
		},
		error: function (xht, textStatus, ex)
		{
			ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
		},
		complete: function ()
		{
			unblockUI();
		}
	});

</script>
}