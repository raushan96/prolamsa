/**
 * 
 */
package mx.neoris.core.services;


import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.store.BaseStoreModel;

import java.util.Date;



/**
 * @author fdeutsch
 * 
 */
public class IncidentSearchParameters
{
	private String customer;
	private String number;
	private Date initialDate;
	private Date finalDate;
	private String type;
	private String state;
	private String user;
	private BaseStoreModel baseStore;

	private PageableData pageableData;

	/**
	 * @return the number
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number
	 *           the number to set
	 */
	public void setNumber(final String number)
	{
		this.number = number;
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

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

	public String getState()
	{
		return state;
	}

	public void setState(final String state)
	{
		this.state = state;
	}

	public PageableData getPageableData()
	{
		return pageableData;
	}

	public void setPageableData(final PageableData pageableData)
	{
		this.pageableData = pageableData;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(final String user)
	{
		this.user = user;
	}

	public BaseStoreModel getBaseStore()
	{
		return baseStore;
	}

	public void setBaseStore(BaseStoreModel baseStore)
	{
		this.baseStore = baseStore;
	}
}
