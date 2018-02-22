/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.storefront.forms;

/**
 * Pojo for 'order approval decision' form.
 */
public class OrderApprovalDecisionForm
{
	private String workFlowActionCode;
	private String approverSelectedDecision;
	private String comments;
	
	private String orderCode;

	/**
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getWorkFlowActionCode()
	{
		return workFlowActionCode;
	}

	public void setWorkFlowActionCode(final String workFlowActionCode)
	{
		this.workFlowActionCode = workFlowActionCode;
	}

	public String getApproverSelectedDecision()
	{
		return approverSelectedDecision;
	}

	public void setApproverSelectedDecision(final String approverSelectedDecision)
	{
		this.approverSelectedDecision = approverSelectedDecision;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(final String comments)
	{
		this.comments = comments;
	}
}
