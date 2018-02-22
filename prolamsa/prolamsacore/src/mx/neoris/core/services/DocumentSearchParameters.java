/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.Date;
import java.util.List;


/**
 * @author christian.loredo
 * 
 */
public class DocumentSearchParameters
{
	private String customer;
	private String baseStore;
	private List<String> listCustomer;
	private String reference;
	private String documentType;

	private Date initialDate;
	private Date finalDate;
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
	 * @return the documentType
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType
	 *           the documentType to set
	 */
	public void setDocumentType(final String documentType)
	{
		this.documentType = documentType;
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
