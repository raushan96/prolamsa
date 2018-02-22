<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formUtil" tagdir="/WEB-INF/tags/desktop/form"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/mobile/template" %>

<div id="menu-desplegable" style="width: 100%; float: left; height:auto; ">
	<table class="menu-desplegable-table">
		<tr>
			<td style="width: 90%; padding-left: 13px;">
				<span style="font-weight: 700; font-size: 14px;"><spring:theme code="mobile.main.menu" /></span>
			</td>
			<td style="width: 10%;">
				<span><img id="logo" src="${themeResourcePath}/images/hamburguesaNew.gif" alt="logo"></span>
			</td>
		</tr>
	</table>
	 
	<ul id="sub-menu">
		<li><a tabindex="0" style="padding-top:40px; font-size:12px; color: white;z-index: 1;  display: block;	font-weight: lighter;" href="/store/">Home</a></li>
		<c:if test="${baseStore.showOrdersAndQuotes eq true}">
			<li><a tabindex="0" style="padding-top:12px; font-size:12px; color: white;z-index: 1; position:relative; display: block;	font-weight: lighter;" href="<c:url value="/my-account/orders" />"><spring:theme code="Order History" /></a></li>
		</c:if>
		<c:if test="${baseStore.showBackorder eq true}">
			<li><a tabindex="0" style="font-size:12px; color: white;z-index: 1; position:relative; display: block;	font-weight: lighter;" href="<c:url value="/my-account/backorder/list" />"><spring:theme code="Backorder Report" /></a></li>
		</c:if>
		<c:if test="${baseStore.showAccountBalance eq true}">
			<li><a tabindex="0" style="font-size:12px; color: white;z-index: 1; position:relative; display: block;	font-weight: lighter;" href="<c:url value="/my-account/balance-statement" />"><spring:theme code="Balance Statement" /></a></li>
		</c:if>
	</ul>
	
	
</div>
 
 <script type="text/javascript">
onDOMLoaded(initHeader2);

function abrirMenu(){
	$("#sub-menu").toggleClass("activo");
	return false;
}
function cerrarMenu(){
	$("#sub-menu").removeClass("activo");
}
function elegirOpcion(){
	//$("#menu-desplegable span").text($(this).text());
	location.href = $(this).find("a").attr("href");
}
function mouseO(){
	alert("XTR");
	//$("#sub-menu li a").toggleClass("first_row");
}

//$("#sub-menu li a").mouseover(function(){
//	  $("#sub-menu li a").css("background-color","yellow");
//	});

function bindHeader2()
{
	
	
	$("#menu-desplegable").on("click", abrirMenu);
	$("#sub-menu li").on("click", elegirOpcion);
	//$("#orderH").mouseover(mouseO);
	$(document).on("click", cerrarMenu);	
	
	//$("menu").click(function() {alert("1");
	//	$("#desplegar").show('slow');
	//});
					
	//$("menu").click(function() {alert("2");
	//	$("#desplegar").hide('slow');
	
}
	

   


function initHeader2()
{
	bindHeader2();
}
 </script>