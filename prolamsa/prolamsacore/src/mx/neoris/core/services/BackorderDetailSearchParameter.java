/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.Date;


/**
 * @author christian.loredo
 * 
 */
public class BackorderDetailSearchParameter
{
	private String customer;

	private String user;

	private String order;
	private String customerPO;
	public Date initialDate;
	public Date finalDate;
	public String address;
	private String baseStore;

	private String sortBy;
	private String sortOrder;
	private PageableData pageableData;



	/**
	 * @return the baseStore
	 */
	public String getBaseStore()
	{
		return baseStore;
	}

	/**
	 * @param baseStore
	 *           the baseStore to set
	 */
	public void setBaseStore(final String baseStore)
	{
		this.baseStore = baseStore;
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *           the user to set
	 */
	public void setUser(final String user)
	{
		this.user = user;
	}

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
	 * @return the order
	 */
	public String getOrder()
	{
		return order;
	}

	/**
	 * @param order
	 *           the order to set
	 */
	public void setOrder(final String order)
	{
		this.order = order;
	}

	/**
	 * @return the customerPO
	 */
	public String getCustomerPO()
	{
		return customerPO;
	}

	/**
	 * @param customerPO
	 *           the customerPO to set
	 */
	public void setCustomerPO(final String customerPO)
	{
		this.customerPO = customerPO;
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
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *           the address to set
	 */
	public void setAddress(final String address)
	{
		this.address = address;
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
