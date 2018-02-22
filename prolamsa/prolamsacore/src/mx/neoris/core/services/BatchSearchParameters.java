/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.List;


/**
 * @author christian.loredo
 * 
 */
public class BatchSearchParameters
{
	private String customer;
	private String baseStore;
	private List<String> listCustomer;
	private String reference;
	private String batchType;

	private String sortBy;
	private String sortOrder;

	private PageableData pageableData;

	public Boolean isEmpty(final String string)
	{
		return string == null || string.length() == 0;
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
	 * @return the listCustomer
	 */
	public List<String> getListCustomer()
	{
		return listCustomer;
	}

	/**
	 * @param listCustomer
	 *           the listCustomer to set
	 */
	public void setListCustomer(final List<String> listCustomer)
	{
		this.listCustomer = listCustomer;
	}

	/**
	 * @return the reference
	 */
	public String getReference()
	{
		return reference;
	}

	/**
	 * @param reference
	 *           the reference to set
	 */
	public void setReference(final String reference)
	{
		this.reference = reference;
	}

	/**
	 * @return the batchType
	 */
	public String getBatchType()
	{
		return batchType;
	}

	/**
	 * @param batchType
	 *           the batchType to set
	 */
	public void setBatchType(final String batchType)
	{
		this.batchType = batchType;
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
