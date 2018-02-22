<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="orderApprovalData" required="true"
	type="de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData"%>
<%@ attribute name="isOrderDetailsPage" type="java.lang.Boolean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order"%>


<div class="item_container_holder">
	<div class="item_container">
		<div class="span-80 left">
			<table class="table_budget">
				<tr>
                    <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
					<td><spring:theme
							code="text.account.orderApprovalDetails.OrderNumber"
							 /></td>
					<td>${orderApprovalData.b2bOrderData.code}</td>
				</tr>

				<tr>
                    <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
					<td><spring:theme
							code="text.account.orderApprovalDetails.orderPlacedBy"
							 /></td>
					<td>${orderApprovalData.b2bOrderData.b2bCustomerData.name}</td>
				</tr>

				<tr>
                    <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
					<td><spring:theme
							code="text.account.orderApprovalDetails.purchaseOrderNumber"
							 /></td>
					<td>${orderApprovalData.b2bOrderData.purchaseOrderNumber}</td>
				</tr>
<%--
			</table>
		</div>
		<div class="span-40 left center">
			<table class="table_budget">
--%>			
				<tr>
                    <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
					<td><spring:theme
							code="text.account.orderApprovalDetails.parentBusinessUnit"
							 /></td>
					<td>${orderApprovalData.b2bOrderData.costCenter.unit.name}</td>
				</tr>
				<c:if
					test="${orderApprovalData.b2bOrderData.paymentType.code eq 'ACCOUNT'}">
					<tr>
                        <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme
								code="text.account.orderApprovalDetails.costCenter"
								 /></td>
						<td>${orderApprovalData.b2bOrderData.costCenter.code}</td>
					</tr>

					<tr>
                        <!-- NEORIS_CHANGE #incidencia 187 JCVM 25/08/2014 Se Eliminan los textos para tomar los valores de los archivos de propiedades -->
						<td><spring:theme
								code="text.account.orderApproval.orderStatus"
								 /></td>
						<td><spring:theme code="text.account.order.status.display.${orderApprovalData.b2bOrderData.statusDisplay}"/></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>
</div>

