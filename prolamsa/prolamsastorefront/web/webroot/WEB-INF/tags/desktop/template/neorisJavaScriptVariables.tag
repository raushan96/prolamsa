<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--
<script>

  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
 
  <c:choose>
  	<c:when test="${baseStore.uid eq '1000'}">
  		ga('create', 'UA-62962800-2', 'auto');
  	</c:when>
  	<c:when test="${baseStore.uid eq '2000'}">
  		ga('create', 'UA-62962800-1', 'auto');  	
  	</c:when>
  	<c:when test="${baseStore.uid eq '5000'}">
  		ga('create', 'UA-62962800-3', 'auto');
  	</c:when>
  	<c:when test="${baseStore.uid eq '6000'}">
    	ga('create', 'UA-62962800-4', 'auto'); 
  	</c:when>
  </c:choose>
  
  
  
 ga('send', 'pageview');
 
// ga('require', 'ecommerce');
 
</script>
 --%>

<script type="text/javascript">
/*
 * NEORIS_CHANGE include Javascript unit code information
 */
var updateUnitOfMeasureMsg = "<spring:theme code="Please_update_the_unit_of_measure_in_the_Header_section_before_selecting_this_option"/>";
var categorySelectionMsg = "<spring:theme code="category_selection"/>";
var customizeYourProductMsg = "<spring:theme code="variant.customize_your_product"/>";
var uiIdleTimeoutSeconds = ${uiIdleTimeoutSeconds};

<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
var userLogged = false;
</sec:authorize>
<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
var userLogged = true;
</sec:authorize>

// cart product location
var cartProductLocation = "${cartProductLocation}";

// cart contains API products
var cartHasAPIProducts = ${cartHasAPIProducts};

// advanced address search link variables
var advanceAddressSearchLink = { };

// product axis configuration variables
<c:set var="supportProductVariantConfig" value="${fn:contains(unit.code,'api')}" scope="request" />
var supportProductVariantConfig = ${supportProductVariantConfig};

var pressureRange =
	{
		lowerLimit : 1000,
		upperLimit :15000
	};
	
var durationRange =
{
	lowerLimit : 5,
	upperLimit :20
};

var lengthRange =
{
	lowerLimit : 21,
	upperLimit :60
};

var testTempRange =
{
	lowerLimit : 32,
	upperLimit :49
};

// unit code variables
var unitCode = "${unit.code}";

// draft variables
var currentDraftName = "${currentDraftName}";

/*
 * NEORIS_CHANGE support for func call on DOMLoaded
 */

function onDOMLoaded(func)
{
	//TODO ensure func is executed on all browsers when DOM has been totally loaded
	if (window.addEventListener)
		window.addEventListener('load', func, false);
	else
		window.attachEvent('onload', func);
}

/*
 * NEORIS_CHANGE function for block and unblock ui
 */
function blockUI(message)
{
	var opts = { theme: true };
	
	if (message == null)
		message = "<spring:theme code="general.processing-request"/>";

	opts.message = '<p class="processing-request">' + message + '</p>';

	jQuery.blockUI(opts);
}

function unblockUI()
{
	jQuery.unblockUI();
}

onDOMLoaded(registerIdleTimer);


function registerIdleTimer()
{
	if (!userLogged)
		return;

	jQuery(document).idleTimer({timeout: uiIdleTimeoutSeconds * 1000});
	
	jQuery( document ).on( "idle.idleTimer", function(event, elem, obj)
	{
		window.location.href = '<spring:url value="/logout" />';
    });
}
</script>
