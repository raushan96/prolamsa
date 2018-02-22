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
<%@ taglib prefix="selector" tagdir="/WEB-INF/tags/desktop/common/header"%>

<div class="nav-wrapper search-form" style="display: block;">
    <div class="nav-inner-wrapper grid-container">
	    <ul class="nav nav-noDeco">
		   <li>
                 <div class="siteSearch search">
		         <selector:b2bUnitAddressBookSelector b2bUnitList="${formattedB2BUnits}" selectedB2BUnit="${addressForm.customer}"/>
		         </div> 
		   </li>
		</ul>
	</div> 
</div>				