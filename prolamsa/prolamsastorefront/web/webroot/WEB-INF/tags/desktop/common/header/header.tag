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
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>

<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />

<div id="header">
	<div class="nav-1-wrapper">
			<input type="hidden" id="user" name="user" value="${user.firstName}" style="display: none; visibility: hidden;"/>
			<ul class="nav nav-1 clearfix grid-container">
            	<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
                	<li class="logged_in"><ycommerce:testId code="header_LoggedUser"><spring:theme code="header.welcome" arguments="${user.firstName},${user.lastName}" htmlEscape="true"/></ycommerce:testId></li>
                </sec:authorize>
                <sec:authorize ifNotGranted="ROLE_CUSTOMERGROUP">
					<li><ycommerce:testId code="header_Login_link"><a href="<c:url value='/login'/>"><spring:theme code="header.link.login"/></a></ycommerce:testId></li>
				</sec:authorize>
                
                <li><a href="<c:url value='/my-account'/>"><spring:theme code="header.link.account"/></a></li>
                <sec:authorize ifAnyGranted="ROLE_B2BADMINGROUP">
				<li><ycommerce:testId code="header_myCompany"><a href="<c:url value='/my-company/organization-management'/>"><spring:theme code="header.link.company"/></a></ycommerce:testId></li>
				</sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
                	<li><ycommerce:testId code="header_signOut"><a href="<c:url value='/logout'/>"><spring:theme code="header.link.logout"/></a></ycommerce:testId></li>
                </sec:authorize>
                <sec:authorize ifNotGranted="ROLE_INCIDENT_USER">
                <c:if test="${baseStore.showNoticesModule eq true}">
	                <li><ycommerce:testId code="header_signOut"><a href="<c:url value='/my-account/notices/list'/>"><spring:theme code="notices.list.link"/></a></ycommerce:testId></li>
                </c:if>
				</sec:authorize>
                <!--NEORIS_CHANGE 28-MARCH-14  -->
                <%-- <li><a href="<c:url value="/store-finder"/>"><spring:theme code="general.find.a.store" /></a></li> --%>
                
                  <%--  <cms:pageSlot position="HeaderLinks" var="link">
				<cms:component component="${link}" element="li"/>
			</cms:pageSlot> --%>
			
			<li class="yCmsComponent"><div class="content">Ll&aacute;manos: (81) 8154 0200 Ext. 2</div></li>
                   
            </ul>   
	</div>
	
	<div class="grid-container">
		<div class="siteLogo">
			<div class="simple_disp-img">
	                  	<%--<img src="${themeResourcePath}/images/Prolamsa240x120.png" alt="">
					 <cms:pageSlot position="SiteLogo" var="logo" >
						<cms:component component="${logo}"/>
					</cms:pageSlot>
					
					<cms:pageSlot position="SiteLogo" var="logo" >
						<cms:component component="${logo}"/>
					</cms:pageSlot>
					--%>
				<a href="/store/"><img id="logo" src="${themeResourcePath}/images/logo.png?id=1" alt="logo" style="width:240px;"></a>
			</div>
		</div>
		<br/>
        <span id="Branding"></span>

		<cart:rolloverCartPopup />
		<cms:pageSlot position="MiniCart" var="cart" limit="1">
			<cms:component component="${cart}" />
		</cms:pageSlot>


	</div><!-- #Logo + chart -->
                
                
                <!-- NEORIS_CHANGE #48 -->

<div class="ui-widget-overlay ui-front" style="display: none;"></div>
                
<div id="dialog-confirm" style="display: none;">
<div id="dialog-contents" class="content-popup"></div>
</div>              
                
   <a id="skiptonavigation"></a>
				<nav:topNavigation/>             
                             
    <%--  style="${ fn:containsIgnoreCase(url,'my-account') ? 'display:none;visibility:hidden' : ''}" --%>
     
     <sec:authorize ifAnyGranted="ROLE_MATERIAL_SUPPLIER_RECEPTION,ROLE_INCIDENT_USER,ROLE_AXISONLYREPORTS">
     <br>
     </sec:authorize>
     
     
	 <div class="nav-wrapper">
	 	<!--  <div class="nav-inner-wrapper grid-container" > -->
			
				
		<c:choose>
		  <c:when test="${ fn:containsIgnoreCase(url,'my-account') or fn:containsIgnoreCase(url,'incident') or fn:containsIgnoreCase(url,'batch')}">
		     <div class="nav-inner-wrapper grid-container">
		       <ul class="nav" style="float:right">				 		 				
				<li >
						<header:languageSelector languages="${languages}" currentLanguage="${currentLanguage}" />
				</li>
				<sec:authorize ifNotGranted="ROLE_INCIDENT_USER">	
				<li >
						&nbsp;&nbsp;&nbsp;
				</li>	
					
				<li>
						<header:b2bUnitCurrencySelector selectedB2BUnit="${rootunit}" />
				</li>
				</sec:authorize>	
			  </ul>
			 </div>
		  </c:when>
		  
		  <c:otherwise>
		    <div class="nav-inner-wrapper grid-container" >
	        <ul class="nav">
	           
	        
			    <!-- NEORIS_CHANGE #48 -->
			    <sec:authorize ifNotGranted="ROLE_INCIDENT_USER">
				<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
				
					<%-- <li  class="address-select">
					<a id="advancedClientSearchLink" href="#" ><spring:theme code="b2bunitselector.advance-client-search"/></a>	
					</li> --%>
					<li class="address-select" style="margin-right: 3px;">
						<header:b2bUnitSelector b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${rootunit}"/>
					</li>
					
					<%-- Combo de locaciones CILS 26092016 --%>
					<c:choose>
						<c:when test="${baseStore.allowCategoryVisibility}">
							<li>
								<header:locationSelector locations="${locationsHeaderList}"/>
							</li>
						</c:when>
					</c:choose>
				</sec:authorize>
				 	
				 <li >
						&nbsp;&nbsp;&nbsp;
				</li>	
				 
	            <li >
	            </sec:authorize>
	            <c:set var="selectedUnit" value="${unit}" />
	            <c:set var="selectedSystem" value="${selectedUnit.defaultMeasurementSystem}" />
	            	            
	            <sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER" >
					<header:systemSelector systemList="${systemList}" selectedSystem="${selectedSystem}"/>
				</sec:authorize>	
				</li>
					 
				<li >
						&nbsp;&nbsp;&nbsp;
				</li>	
					 			
			   			 
				<li class="address-select"> 
				
				<sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER" >
				    <c:choose>				       
				       <c:when test="${selectedSystem  eq 'English'}">
				          <header:unitSelector unitList="${unitsByEnglishSystem}" selectedUnit="${selectedUnit}"/>
				       </c:when>
				       <c:otherwise>
				          <header:unitSelector unitList="${unitsByMetricSystem}" selectedUnit="${selectedUnit}"/>
				       </c:otherwise>
				    </c:choose>
					</sec:authorize>
				</li>		
				
				
				<li >
						&nbsp;&nbsp;&nbsp;
				</li>	
				
				<li>
				
						<header:languageSelector languages="${languages}" currentLanguage="${currentLanguage}" />
						
				</li>
				<sec:authorize ifNotGranted="ROLE_INCIDENT_USER">	
				<li >
						&nbsp;&nbsp;&nbsp;
				</li>	
					
				<li>
				
						<header:b2bUnitCurrencySelector selectedB2BUnit="${rootunit}" />
					
				</li>	
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_PURCHASER,ROLE_ACCOUNTER" >
				<li >
						
						<cms:pageSlot position="SearchBox" var="component">
							<cms:component component="${component}"/>
						</cms:pageSlot>
				</li>
				</sec:authorize>
				<br>
				 <li  class="address-select">
 <c:choose>
		<c:when test="${baseStore.uid eq '2000'}">
			<a id="advancedClientSearchLink" href="#" style="margin-left: 0px; margin-top: -15px;"><spring:theme code="b2bunitselector.advance-client-search.2000"/></a>	
		</c:when>
		<c:otherwise>
			<a id="advancedClientSearchLink" href="#" style="margin-left: 0px; margin-top: -15px;"><spring:theme code="b2bunitselector.advance-client-search"/></a>	
		</c:otherwise>
	</c:choose>
	</li> 
			</ul>
			</div>
		  </c:otherwise>
		</c:choose>
			     
	</div>
			
</div>

<script type="text/javascript">
onDOMLoaded(initPage);

function initPage()
{	
	var usuario = document.getElementById('user').value;
	var b2bUnitList = document.getElementById('b2bUnitListView').value;
	
	if(usuario == "Anonymous" || b2bUnitList == "1")
	{
		window.location.href = '<spring:url value="/logout" />';
	}
}
</script>