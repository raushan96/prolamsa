/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author christian.loredo
 * 
 */
public class B2BUnitBackofficeOnChangeEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private static final long serialVersionUID = 1L;

	private B2BUnitModel b2bUnitModel;
	private String originalBackofficeId;

	/**
	 * @return the b2bUnitModel
	 */
	public B2BUnitModel getB2bUnitModel()
	{
		return b2bUnitModel;
	}

	/**
	 * @param b2bUnitModel
	 *           the b2bUnitModel to set
	 */
	public void setB2bUnitModel(final B2BUnitModel b2bUnitModel)
	{
		this.b2bUnitModel = b2bUnitModel;
	}

	/**
	 * @return the originalBackofficeId
	 */
	public String getOriginalBackofficeId()
	{
		return originalBackofficeId;
	}

	/**
	 * @param originalBackofficeId
	 *           the originalBackofficeId to set
	 */
	public void setOriginalBackofficeId(final String originalBackofficeId)
	{
		this.originalBackofficeId = originalBackofficeId;
	}
}
