/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;


/**
 * @author e-lacantu
 * 
 */
public class B2BUnitSearchParameters
{
	private String uid;
	private String name;
	private String city;
	private String state;
	private String transportationMode;
	private String pk;
	public PageableData pageableData;
	public String sortBy;
	public String sortOrder;
	public String baseStorePk;
	public String currentUserPk;

	/**
	 * @return the uid
	 */
	public String getUid()
	{
		return uid;
	}

	/**
	 * @param uid
	 *           the uid to set
	 */
	public void setUid(final String uid)
	{
		this.uid = uid;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *           the name to set
	 */
	public void setName(final String name)
	{
		this.name = name;
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
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *           the city to set
	 */
	public void setCity(final String city)
	{
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *           the state to set
	 */
	public void setState(final String state)
	{
		this.state = state;
	}

	/**
	 * @return the transportationMode
	 */
	public String getTransportationMode()
	{
		return transportationMode;
	}

	/**
	 * @param transportationMode
	 *           the transportationMode to set
	 */
	public void setTransportationMode(final String transportationMode)
	{
		this.transportationMode = transportationMode;
	}

	/**
	 * @return the pk
	 */
	public String getPk()
	{
		return pk;
	}

	/**
	 * @param pk
	 *           the pk to set
	 */
	public void setPk(final String pk)
	{
		this.pk = pk;
	}

	/**
	 * @return the baseStorePk
	 */
	public String getBaseStorePk()
	{
		return baseStorePk;
	}

	/**
	 * @param baseStorePk
	 *           the baseStorePk to set
	 */
	public void setBaseStorePk(final String baseStorePk)
	{
		this.baseStorePk = baseStorePk;
	}

	/**
	 * @return the currentUserPk
	 */
	public String getCurrentUserPk()
	{
		return currentUserPk;
	}

	/**
	 * @param currentUserPk
	 *           the currentUserPk to set
	 */
	public void setCurrentUserPk(final String currentUserPk)
	{
		this.currentUserPk = currentUserPk;
	}
}
