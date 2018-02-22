<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/my-account/order/approvalDecision"
            var="orderApprovalDecisionURL"/>

<template:page pageTitle="${pageTitle}">
	<div class="pagehead-wrapper">
     	<div class="pagehead grid-container">
			<div id="breadcrumb" class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>		
	<div class="grid-container">
		<div id="globalMessages">
			<common:globalMessages/>
		</div>
		<div class="span-20 last main-rightXXX">
			<form:form method="post" id="approvalDecisionForm"
	           commandName="orderApprovalDecisionForm"
	           action="${orderApprovalDecisionURL}">
				<div class="title_holder">
						<h2><spring:theme
					code="text.account.orderApprovalDetails.orderStatusDetails.label"
					text="Order Status Details" /></h2>
				</div>
				<div class="item_container_holder">
		   			<div class="item_container">
						<order:orderApprovalStatusDetailsItem
						orderApprovalData="${orderApprovalData}"/>
						<order:orderDetailsItem order="${orderApprovalData.b2bOrderData}"
				                        isOrderDetailsPage="true"/>
<%-- 				        <order:receivedPromotions order="${orderApprovalData.b2bOrderData}"/> --%>
<%-- 				        <c:if test="${orderApprovalData.b2bOrderData.triggerData ne null}"> --%>
<%-- 							<order:replenishmentScheduleInformation --%>
<%-- 									order="${orderApprovalData.b2bOrderData}"/> --%>
<%-- 						</c:if> --%>
						
						
						<div class="span-8 last right place-order-summary-total">
							<order:orderSummaryItem order="${orderApprovalData.b2bOrderData}"/>
						</div>
						<div class="span-8 last right place-order-summary-total">
							<order:orderTotalsItem order="${orderApprovalData.b2bOrderData}"/>
						</div>
						<%--
						<div class="span-8 last right">
				<order:orderHistoryEntriesDetailsItem
						orderHistoryEntries="${orderApprovalData.quotesApprovalHistoryEntriesData}" itemHolderTitleKey="text.account.orderHistoryEntry.QuoteTitle" isQuoteNegotiation="true"/>
			</div>
			--%>
			<div class="span-8 last right">
				<order:orderHistoryEntriesDetailsItem
						orderHistoryEntries="${orderApprovalData.orderHistoryEntriesData}" itemHolderTitleKey="text.account.orderHistoryEntry.customerTitle"/>
			</div>
						
						
						<c:choose>
				<c:when test="${orderApprovalData.approvalDecisionRequired}">
					<div class="span-40  right">
						<form:input type="hidden" name="workflowActionCode"
						            path="workFlowActionCode"
						            value="${orderApprovalData.workflowActionModelCode}"/>
						<form:input type="hidden" name="approverSelectedDecision"
						            path="approverSelectedDecision" id="approverSelectedDecision"/>
						<!-- NEORIS_CHANGE CLS 01092014 -->
						<form:input type="hidden" name="orderCode"
						            path="orderCode"
						            value="${orderApprovalData.b2bOrderData.code}"/>
						<button class="approverDecisionButton positive left pad_right" type="button"
						        id="approverDecisionApprove"
						        data-decision="APPROVE">
							<spring:theme code="text.account.orderApproval.approveButton.displayName"/>
						</button>
						<button class="approverDecisionButton positive right pad_right" type="button"
						        id="approverDecisionReject"
						        data-decision="REJECT">
							<spring:theme code="text.account.orderApproval.rejectButton.displayName"/>
						</button>
					</div>
					<div class="span-8 left">
						<div class="item_container_holder">
							<div class="title_holder">
								<div class="title">
									<div class="title-top">
										<span></span>
									</div>
								</div>
								<h2>
									<spring:theme
											code="text.account.orderApprovalDetails.comments.label"
											text="Comments"/>
								</h2>
							</div>
							<div class="item_container">
								<div class="your_cart">
									<form:input cssClass="textarea" path="comments"
									            cssStyle="width:290px;height:30px;"/>
								</div>
							</div>
						</div>
					</div>
				</c:when>
			</c:choose>
			<%--
			<div class="span-20 last">
				<order:orderHistoryEntriesDetailsItem
						orderHistoryEntries="${orderApprovalData.merchantApprovalHistoryEntriesData}" itemHolderTitleKey="text.account.orderHistoryEntry.merchantTitle"/>
			</div>
			--%>
		</div>
		</div>

	</form:form>
	</div></div>
	<br/>
</template:page>	

<%--
<template:page pageTitle="${pageTitle}">
	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<nav:accountNav selected="approval-dashboard"/>
	<form:form method="post" id="approvalDecisionForm"
	           commandName="orderApprovalDecisionForm"
	           action="${orderApprovalDecisionURL}">
		<div class="span-20 last">
			<div class="span-20 last ">
				<order:orderApprovalStatusDetailsItem
						orderApprovalData="${orderApprovalData}"/>
			</div>
			<div class="span-20 last">
				<order:orderDetailsItem order="${orderApprovalData.b2bOrderData}"
				                        isOrderDetailsPage="true"/>
				<div class="span-12">
					<order:receivedPromotions order="${orderApprovalData.b2bOrderData}"/>
				</div>
				<c:if test="${orderApprovalData.b2bOrderData.triggerData ne null}">
					<order:replenishmentScheduleInformation
							order="${orderApprovalData.b2bOrderData}"/>
				</c:if>
				<div class="span-8 last right">
					<order:orderTotalsItem order="${orderApprovalData.b2bOrderData}"/>
				</div>
			</div>
			<div class="span-20 last">
				<order:orderHistoryEntriesDetailsItem
						orderHistoryEntries="${orderApprovalData.quotesApprovalHistoryEntriesData}" itemHolderTitleKey="text.account.orderHistoryEntry.QuoteTitle" isQuoteNegotiation="true"/>
			</div>
			<div class="span-20 last">
				<order:orderHistoryEntriesDetailsItem
						orderHistoryEntries="${orderApprovalData.orderHistoryEntriesData}" itemHolderTitleKey="text.account.orderHistoryEntry.customerTitle"/>
			</div>
			<c:choose>
				<c:when test="${orderApprovalData.approvalDecisionRequired}">
					<div class="span-40  right">
						<form:input type="hidden" name="workflowActionCode"
						            path="workFlowActionCode"
						            value="${orderApprovalData.workflowActionModelCode}"/>
						<form:input type="hidden" name="approverSelectedDecision"
						            path="approverSelectedDecision" id="approverSelectedDecision"/>
						<button class="approverDecisionButton positive left pad_right" type="button"
						        id="approverDecisionApprove"
						        data-decision="APPROVE">
							<spring:theme code="text.account.orderApproval.approveButton.displayName"/>
						</button>
						<button class="approverDecisionButton positive right pad_right" type="button"
						        id="approverDecisionReject"
						        data-decision="REJECT">
							<spring:theme code="text.account.orderApproval.rejectButton.displayName"/>
						</button>
					</div>
					<div class="span-8 left">
						<div class="item_container_holder">
							<div class="title_holder">
								<div class="title">
									<div class="title-top">
										<span></span>
									</div>
								</div>
								<h1>
									<spring:theme
											code="text.account.orderApprovalDetails.comments.label"
											text="Comments"/>
								</h1>
							</div>
							<div class="item_container">
								<div class="your_cart">
									<form:input cssClass="textarea" path="comments"
									            cssStyle="width:290px;height:30px;"/>
								</div>
							</div>
						</div>
					</div>
				</c:when>
			</c:choose>
			<div class="span-20 last">
				<order:orderHistoryEntriesDetailsItem
						orderHistoryEntries="${orderApprovalData.merchantApprovalHistoryEntriesData}" itemHolderTitleKey="text.account.orderHistoryEntry.merchantTitle"/>
			</div>
		</div>

	</form:form>
</template:page>
 --%>
