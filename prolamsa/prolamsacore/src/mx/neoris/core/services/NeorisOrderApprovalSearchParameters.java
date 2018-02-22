/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;


/**
 * @author e-lacantu
 * 
 */
public class NeorisOrderApprovalSearchParameters
{
	private String b2bUnits[];
	private String user;
	private String sortBy;
	private String sortOrder;
	private String store;

	public String getStore()
	{
		return store;
	}

	public void setStore(final String store)
	{
		this.store = store;
	}

	private PageableData pageableData;

	public String[] getB2bUnits()
	{
		return b2bUnits;
	}

	public void setB2bUnits(final String[] b2bUnits)
	{
		this.b2bUnits = b2bUnits;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(final String user)
	{
		this.user = user;
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
}
