<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<c:url value="/cart/miniCart/${totalDisplay}" var="refreshMiniCartUrl"/>
<c:url value="/cart/rollover/${component.uid}" var="rolloverPopupUrl"/>

<c:set var="tonsTotal" value="0"/>
<c:set var="tonsStock" value="0"/>
<c:set var="tonsProd" value="0"/>

<c:choose>
	<c:when test="${cartData.hasAPIProducts}">
		<c:forEach items="${cartData.entries }" var="eachEntry">
			<c:set var="tonsTotal" value="${tonsTotal + eachEntry.product.ftTnEquiv*eachEntry.quantity }"/>
			<c:choose>
				<c:when test="${empty eachEntry.rollingScheduleWeek }">
					<c:set var="tonsStock" value="${tonsStock + eachEntry.product.ftTnEquiv*eachEntry.quantity }"/>
				</c:when>
				<c:otherwise>
					<c:set var="tonsProd" value="${tonsProd + eachEntry.product.ftTnEquiv*eachEntry.quantity }"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:forEach items="${cartData.entries }" var="eachEntry">
			<c:set var="tonsTotal" value="${tonsTotal + eachEntry.product.tnEquiv*eachEntry.quantity }"/>
			<c:choose>
				<c:when test="${empty eachEntry.rollingScheduleWeek }">
					<c:set var="tonsStock" value="${tonsStock + eachEntry.product.tnEquiv*eachEntry.quantity }"/>
				</c:when>
				<c:otherwise>
					<c:set var="tonsProd" value="${tonsProd + eachEntry.product.tnEquiv*eachEntry.quantity }"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:otherwise>
</c:choose>

<script  type="text/javascript">
/*<![CDATA[*/
           
function confirmDeleteCurrentCart(){
     
      	ACC.modals.confirmModalOK("<spring:theme code="cart.delete.title"/>", "<spring:theme code="cart.delete.confirm"/>", 
      			function () { emptyCart() },
      			null);
      	}

function emptyCart(){
	
     $.ajax
		({
			url:         "<c:url value="/misc/emptyUsedCart.json" />",
			type:        'POST',
			dataType:    'json',
			beforeSend: function ()
			{
				blockUI();
			},
			error: function (xht, textStatus, ex)
			{
				unblockUI();
				ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
	       	},
	       	success: function (data)
	       	{
	           	if (data.status == 0)
	           	{
	           		// removed to avoid message of cart cleared
	           		//ACC.modals.messageModal("<spring:theme code="cart.delete.title"/>", "<spring:theme code="cart.delete.success"/>");
	               	location.reload(true);
	           	}
	           	else
	           	{
	           		unblockUI();
	           		ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
	           	}
	       	}
		});
}

function ifEmptyCart(){
	
    $.ajax
		({
			url:         "<c:url value="/misc/ifEmptyCart.json" />",
			type:        'POST',
			dataType:    'json',
			beforeSend: function ()
			{
				blockUI();
			},
			error: function (xht, textStatus, ex)
			{
				unblockUI();
				ACC.modals.messageModal("<spring:theme code="error"/>", "Ajax call failed. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
	       	},
	       	success: function (data)
	       	{
	           	if (data.status == 0)
	           	{
	           		unblockUI();
	           		popupTemplate();
	           	}
	           	else
	           	{
	           		unblockUI();
	           		ACC.modals.messageModal("<spring:theme code="error"/>", data.message);
	           	}
	       	}
		});
}


function popupTemplate()
{
	//ga('send','event','CreateDraft','createDraft');
	ACC.modals.openOnModal("<spring:theme text="Save draft" code="draft.title.button"/>", "<c:url value="/templates/popup" />", "#popTemplateContainer", {}, function() { jQuery("#templateDraftName_textField").val(currentDraftName); });
}

/*]]>*/
</script>

<div id="popTemplateContainer" style="display: none;"></div>

<script id="miniCartTemplate" type="text/x-jquery-tmpl">
/*<![CDATA[*/
	<dt><ycommerce:testId code="miniCart_items_label"><spring:theme text="items" code="cart.items" arguments="{{= totalItems}}"/></ycommerce:testId> - </dt>
	<dd><ycommerce:testId code="miniCart_total_label">
			<c:if test="${totalDisplay == 'TOTAL'}">{{= totalPrice.formattedValue}}</c:if>
			<c:if test="${totalDisplay == 'SUBTOTAL'}">{{= subTotal.formattedValue}}</c:if>
			<c:if test="${totalDisplay == 'TOTAL_WITHOUT_DELIVERY'}">{{= totalNoDelivery.formattedValue}}</c:if>
	</ycommerce:testId></dd>
/*]]>*/
</script>

<script type="text/javascript"> // set vars
var rolloverPopupUrl = '${rolloverPopupUrl}';
var refreshMiniCartUrl = '${refreshMiniCartUrl}/?';
</script>

<div id="cart_header">
	<div id="cart_content">
		<c:url value="/cart" var="cartUrl"/>
		<h2>${component.title}</h2>
		<theme:image code="img.iconCart" />
		<a href="javascript:void(0)"></a>
		
		<div id="minicart_data" style="">
<%-- 				<ycommerce:testId code="miniCart_items_label"> --%>
<%-- 					<spring:theme code="cart.items" arguments="${totalItems}"/> --%>
<%-- 				</ycommerce:testId> --%>
			<span class="totalItemsSpan" style="">(${totalItems })</span>
			<c:choose>
				<c:when test="${tonsTotal != 0 }">
					 <spring:theme code="minicart.totalweight" />&nbsp;<fmt:formatNumber value="${tonsTotal}" pattern="${tonPattern }"/>&nbsp;<spring:theme code="backorder.list.tonUnit" />
				</c:when>
				<c:otherwise>
					<spring:theme code="minicart.totalweight" />&nbsp;0&nbsp;<spring:theme code="backorder.list.tonUnit" />
				</c:otherwise>
			</c:choose>
				 
  <div class="available_ton" style="">
    <spring:theme code="minicart.available"/> <br>
    <c:choose>
				<c:when test="${tonsStock != 0 }">
					<span><fmt:formatNumber value="${tonsStock}" pattern="${tonPattern}"/>&nbsp;<spring:theme code="backorder.list.tonUnit" /></span>
				</c:when>
				<c:otherwise>
					<span>----</span>
				</c:otherwise>
			</c:choose>
  
  </div>
  <div class="rolling_ton" style="">
     <spring:theme code="minicart.rolling"/> <br>
        <c:choose>
				<c:when test="${tonsProd != 0 }">
					<span><fmt:formatNumber value="${tonsProd}" pattern="${tonPattern}"/>&nbsp;<spring:theme code="backorder.list.tonUnit" /></span>
				</c:when>
				<c:otherwise>
					<span>----</span>
				</c:otherwise>
			</c:choose>
  </div>
				<!-- NEORIS_CHANGE #51 -->
				<!-- 
				-
					<span>
						<ycommerce:testId code="miniCart_total_label">
							<c:if test="${totalDisplay == 'TOTAL'}">
								<format:price priceData="${totalPrice}"/>
							</c:if>
							<c:if test="${totalDisplay == 'SUBTOTAL'}">
								<format:price priceData="${subTotal}"/>
							</c:if>
							<c:if test="${totalDisplay == 'TOTAL_WITHOUT_DELIVERY'}">
								<format:price priceData="${totalNoDelivery}"/>
							</c:if>
						</ycommerce:testId>
					</span>
				-->
		</div>
	</div>
	<ul>
		<li class="">
			<ycommerce:testId code="miniCart_viewCart_link">
				<!-- NEORIS_CHANGE #54 -->
				<a href="#" onclick="ifEmptyCart();" class="doCheckoutBut"><spring:theme text="Save draft" code="draft.title.button"/></a>
			</ycommerce:testId>
		</li>
<%--
		<li class="active">
			<ycommerce:testId code="miniCart_viewCart_link">
				<a href="#" onclick="confirmDeleteCurrentCart();" class="doCheckoutBut"><spring:theme code="cart.empty-cart" /></a>
			</ycommerce:testId>
		</li>
--%>
		<li class="">
			<ycommerce:testId code="miniCart_viewCart_link">
				<c:choose>
					<c:when test="${baseStore.uid eq '2000'}">
						<a href="${cartUrl}" class="doCheckoutBut"><spring:theme code="cart.checkout.2000"/></a>
					</c:when>
					<c:otherwise>
						<a href="${cartUrl}" class="doCheckoutBut"><spring:theme code="cart.checkout"/></a>
					</c:otherwise>
				</c:choose>
			</ycommerce:testId>
		</li>
	</ul>
</div>
