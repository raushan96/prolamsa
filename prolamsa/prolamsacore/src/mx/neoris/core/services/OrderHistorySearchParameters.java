/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.Date;


/**
 * @author e-lacantu
 * 
 */
public class OrderHistorySearchParameters
{
	private String customer;
	private String deliveryAddress;
	private String orderNumber;
	private String status;
	private String poNumber;
	private Date initialDate;
	private Date finalDate;
	private String sortBy;
	private String sortOrder;
	private PageableData pageableData;

	/**
	 * @return the customer
	 */
	public String getCustomer()
	{
		return customer;
	}

	/**
	 * @param customer
	 *           the customer to set
	 */
	public void setCustomer(final String customer)
	{
		this.customer = customer;
	}

	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress()
	{
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress
	 *           the deliveryAddress to set
	 */
	public void setDeliveryAddress(final String deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *           the orderNumber to set
	 */
	public void setOrderNumber(final String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}

	/**
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return poNumber;
	}

	/**
	 * @param poNumber
	 *           the poNumber to set
	 */
	public void setPoNumber(final String poNumber)
	{
		this.poNumber = poNumber;
	}

	/**
	 * @return the initialDate
	 */
	public Date getInitialDate()
	{
		return initialDate;
	}

	/**
	 * @param initialDate
	 *           the initialDate to set
	 */
	public void setInitialDate(final Date initialDate)
	{
		this.initialDate = initialDate;
	}

	/**
	 * @return the finalDate
	 */
	public Date getFinalDate()
	{
		return finalDate;
	}

	/**
	 * @param finalDate
	 *           the finalDate to set
	 */
	public void setFinalDate(final Date finalDate)
	{
		this.finalDate = finalDate;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy()
	{
		return sortBy;
	}

	/**
	 * @param sortBy
	 *           the sortBy to set
	 */
	public void setSortBy(final String sortBy)
	{
		this.sortBy = sortBy;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder()
	{
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *           the sortOrder to set
	 */
	public void setSortOrder(final String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the pageableData
	 */
	public PageableData getPageableData()
	{
		return pageableData;
	}

	/**
	 * @param pageableData
	 *           the pageableData to set
	 */
	public void setPageableData(final PageableData pageableData)
	{
		this.pageableData = pageableData;
	}



}
