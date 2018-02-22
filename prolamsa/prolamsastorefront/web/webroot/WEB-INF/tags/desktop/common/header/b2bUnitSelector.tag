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

<!--NEORIS_CAHNGE# Disable Selector for My-Account modules  -->
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />

<div>
<c:choose>
		<c:when test="${baseStore.uid eq '2000'}">
			<span><spring:theme code="b2bunitselector.purchase-for.2000" /></span>
		</c:when>
		<c:otherwise>
			<span><spring:theme code="b2bunitselector.purchase-for" /></span>
		</c:otherwise>
	</c:choose>
<%-- <select id="b2bUnitSelectorElement"  ${ fn:containsIgnoreCase(url,'my-account') ? 'disabled' : ''}> --%>
<select id="b2bUnitSelectorElement" style="width:250px">

	<c:forEach items="${b2bUnitList}" var="eachB2BUnit">
		<c:choose>
			<c:when test="${eachB2BUnit.uid eq selectedB2BUnit.uid}">
				<c:set var="selected" value="selected" />
			</c:when>
			<c:otherwise>
				<c:set var="selected" value="" />
			</c:otherwise>
		</c:choose>

		<option value="${eachB2BUnit.uid}" ${selected}>${eachB2BUnit.name}</option>
	</c:forEach>
</select>

<c:choose>
	<c:when test="${empty b2bUnitList}">
		<input type="hidden" id="b2bUnitListView" name="b2bUnitListView" value="1" style="display: none; visibility: hidden;"/>
	</c:when>
	<c:otherwise>
		<input type="hidden" id="b2bUnitListView" name="b2bUnitListView" value="2" style="display: none; visibility: hidden;"/>
	</c:otherwise>
</c:choose>
</div>

<script type="text/javascript">
var advanceClientSearch = {};
advanceClientSearch.url = "<c:url value="/client/search" />";
advanceClientSearch.linkId = "advancedClientSearchLink";

var b2bUnitSelectorInfo = {};
b2bUnitSelectorInfo.element = "#b2bUnitSelectorElement";

onDOMLoaded(bind_b2bUnitSelectorElement);

function bind_b2bUnitSelectorElement()
{
	//keep existing selection
	b2bUnitSelectorInfo.sel = jQuery(b2bUnitSelectorInfo.element).val();

	var element = jQuery(b2bUnitSelectorInfo.element);
		
	jQuery(element).on("change", function()
	{	
		ACC.modals.confirmModalOK("<spring:theme code="b2bunitselector.confirm.title"/>",
					"<spring:theme code="b2bunitselector.confirm.description"/>", 
					b2bUnitAcceptAction, b2bUnitCancelAction);
	});
}

function b2bUnitCancelAction()
{
	// restore previous value
	jQuery(b2bUnitSelectorInfo.element).val(b2bUnitSelectorInfo.sel);
}

function b2bUnitAcceptAction()
{
	b2bUnitSelectorInfo.sel = jQuery(b2bUnitSelectorInfo.element).val();

	jQuery.ajax
	({
		url: "<c:url value="/misc/setRootUnit.json" />",
		type: 'POST',
		dataType: 'json',
		data: {rootUnit: b2bUnitSelectorInfo.sel},
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
					//ACC.modals.messageModal("<spring:theme code="b2bunitselector.confirm.title"/>", "<spring:theme code="b2bunitselector.customer-changed-refreshing-page"/>");
					window.location = "<spring:url value="/" />";
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
}
</script>