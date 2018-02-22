/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import mx.neoris.core.model.CustomerProductReferenceModel;


/**
 * @author e-hicastaneda
 * 
 */
public class CustomerProductReferenceOnChangeEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomerProductReferenceModel customerProductReferenceModel;

	/**
	 * @return the customerProductReferenceModel
	 */
	public CustomerProductReferenceModel getCustomerProductReferenceModel()
	{
		return customerProductReferenceModel;
	}

	/**
	 * @param customerProductReferenceModel
	 *           the customerProductReferenceModel to set
	 */
	public void setCustomerProductReferenceModel(final CustomerProductReferenceModel customerProductReferenceModel)
	{
		this.customerProductReferenceModel = customerProductReferenceModel;
	}
}
