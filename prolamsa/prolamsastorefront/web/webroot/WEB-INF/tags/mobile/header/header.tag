<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>


<c:set var="logoutBaseUrl" value="/logout" />
<spring:url var="logoutUrl" value="${logoutBaseUrl}" />

<form:form modelAttribute="headerForm" method="post" action="${logoutUrl}">
<div class="header_mobile" style="width: 100%; border: solid 1px green; float: left; height:auto; background: #fff;">
	<table style="width: 100%;">
		<tr style="vertical-align: top;" >
			<td style="padding-left: 0px;">
				<%-- <cms:pageSlot position="SiteLogo" var="logo" >
					<cms:component component="${logo}"/>
				</cms:pageSlot> --%>
				
				<a href="/store/"><img id="logo" src="${themeResourcePath}/images/logo.png" style="width: 180px; " alt="logo"></a>
			</td>
			<td style="width: 100%; padding-top: 10px;">
				<formUtil:formButton
					id="logoutSubmit"
					type="submit" 
					css="button yellow positive button-float-right2"
					tabindex="106"
					springThemeText="Sign out" />	
					
				<%-- <button  id="logout" type="button" class="button yellow positive button-float-left"  title=""  value="${logoutUrl}" tabindex="107" name="" >
        		<span class="icon-download"></span>
                <spring:theme code="Sign out" /> 
            </button>
            --%>
				
			</td>
		</tr>
		<%-- <tr>
			<td colspan="2">
				<formUtil:formButton
					id="menu"
					type="button" 
					css="button yellow positive button-float-right2"
					tabindex="106"
					springThemeText="Menu" />
			</td>
		</tr> --%>
		
	</table>

	<!-- <p>Menu</p>
		<ul id="desplegar" >
			<li>Item1</li>
			<li>Item2</li>
			<li>Item3</li>
			<li>Item4</li>
		</ul> -->
				

 </div>
 <%-- <div class="nav-main-wrapper" id="menu">
 	<spring:theme code="mobile.main.menu" />
 </div> --%>


 
 </form:form>
 
 
 
 <script type="text/javascript">
onDOMLoaded(initHeader);


function bindHeader()
{
	jQuery("#logoutSubmit").click(submitForm);
	
	
	
}
	

function submitForm()
{
	var val = jQuery(this).val();
	
	jQuery("#headerForm").submit();
}

function initHeader()
{
	bindHeader();
}
 </script>
 
