/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.Date;

import mx.neoris.core.exception.NeorisValidationError;


/**
 * @author fdeutsch
 * 
 */
public class InvoiceSearchParameters
{
	public String customer;
	public String baseStore;
	public String number;
	public Date initialDate;
	public Date finalDate;
	public String sortBy;
	public String sortOrder;

	//Agregado por Christian 26082014
	public String typeInvoice;
	public String typeDocto;

	public PageableData pageableData;

	public Boolean isEmpty(final String string)
	{
		return string == null || string.length() == 0;
	}

	public void validateInformation() throws NeorisValidationError
	{
		if (initialDate == null && finalDate == null && isEmpty(number))
		{
			throw new NeorisValidationError("invoice.list.invalid.dates.range");
		}
	}

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
	 * @return the sorOrder
	 */
	public String getSortOrder()
	{
		return sortOrder;
	}

	/**
	 * @param sorOrder
	 *           the sorOrder to set
	 */
	public void setSortOrder(final String sorOrder)
	{
		this.sortOrder = sorOrder;
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
	 * @return the typeInvoice
	 */
	public String getTypeInvoice()
	{
		return typeInvoice;
	}

	/**
	 * @param typeInvoice
	 *           the typeInvoice to set
	 */
	public void setTypeInvoice(final String typeInvoice)
	{
		this.typeInvoice = typeInvoice;
	}

	/**
	 * @return the typeDocto
	 */
	public String getTypeDocto()
	{
		return typeDocto;
	}

	/**
	 * @param typeDocto
	 *           the typeDocto to set
	 */
	public void setTypeDocto(final String typeDocto)
	{
		this.typeDocto = typeDocto;
	}



}
