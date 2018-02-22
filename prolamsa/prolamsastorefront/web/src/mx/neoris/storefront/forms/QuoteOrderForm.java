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
 * Pojo for 'quote order' form.
 */
public class QuoteOrderForm
{
	private String selectedQuoteDecision;
	private String orderCode;
	private String comments;
	private boolean negotiateQuote;
	private String shippingInstructions;


	/**
	 * @return the negotiateQuote
	 */
	public boolean isNegotiateQuote()
	{
		return negotiateQuote;
	}

	/**
	 * @param negotiateQuote
	 *           the negotiateQuote to set
	 */
	public void setNegotiateQuote(final boolean negotiateQuote)
	{
		this.negotiateQuote = negotiateQuote;
	}

	/**
	 * @return the selectedQuoteDecision
	 */
	public String getSelectedQuoteDecision()
	{
		return selectedQuoteDecision;
	}

	/**
	 * @param selectedQuoteDecision
	 *           the selectedQuoteDecision to set
	 */
	public void setSelectedQuoteDecision(final String selectedQuoteDecision)
	{
		this.selectedQuoteDecision = selectedQuoteDecision;
	}

	/**
	 * @return the orderCode
	 */
	public String getOrderCode()
	{
		return orderCode;
	}

	/**
	 * @param orderCode
	 *           the orderCode to set
	 */
	public void setOrderCode(final String orderCode)
	{
		this.orderCode = orderCode;
	}

	/**
	 * @return the comments
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * @param comments
	 *           the comments to set
	 */
	public void setComments(final String comments)
	{
		this.comments = comments;
	}

	/**
	 * @return the shippingInstructions
	 */
	public String getShippingInstructions() {
		return shippingInstructions;
	}

	/**
	 * @param shippingInstructions the shippingInstructions to set
	 */
	public void setShippingInstructions(String shippingInstructions) {
		this.shippingInstructions = shippingInstructions;
	}
}
