/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import mx.neoris.core.enums.ProductLocation;


/**
 * @author e-hicastaneda
 * 
 */
public class FavoriteProductSearchParameters
{
	private B2BUnitModel customer;
	private CatalogVersionModel catalogVersion;
	private String code;
	private String description;
	public String sortBy;
	public String sortOrder;
	private PageableData pageableData;
	private ProductLocation location;

	/**
	 * @return the customer
	 */
	public B2BUnitModel getCustomer()
	{
		return customer;
	}

	/**
	 * @param customer
	 *           the customer to set
	 */
	public void setCustomer(final B2BUnitModel customer)
	{
		this.customer = customer;
	}

	/**
	 * @return the catalogVersion
	 */
	public CatalogVersionModel getCatalogVersion()
	{
		return catalogVersion;
	}

	/**
	 * @param catalogVersion
	 *           the catalogVersion to set
	 */
	public void setCatalogVersion(final CatalogVersionModel catalogVersion)
	{
		this.catalogVersion = catalogVersion;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *           the description to set
	 */
	public void setDescription(final String description)
	{
		this.description = description;
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

	/**
	 * @return the location
	 */
	public ProductLocation getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *           the location to set
	 */
	public void setLocation(final ProductLocation location)
	{
		this.location = location;
	}
}