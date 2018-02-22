/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.List;


/**
 * @author e-lacantu
 * 
 */
public class BalanceStatementSearchParameters
{
	private String user;
	private String customer;
	private List<String> listCustomer;
	private String baseStore;
	private String sortBy;
	private String sortOrder;
	private PageableData pageableData;

	public String getCustomer()
	{
		return customer;
	}

	public void setCustomer(final String client)
	{
		this.customer = client;
	}

	public String getSortBy()
	{
		return sortBy;
	}

	public void setSortBy(final String sortBy)
	{
		this.sortBy = sortBy;
	}

	public String getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(final String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public PageableData getPageableData()
	{
		return pageableData;
	}

	public void setPageableData(final PageableData pageableData)
	{
		this.pageableData = pageableData;
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




}
