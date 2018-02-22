/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author e-jecarrilloi
 * 
 */
public class CMSParagraphComponentContentOnChangeEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CMSParagraphComponentModel cmsParagraphComponentModel;


	public CMSParagraphComponentModel getCmsParagraphComponentModel()
	{
		return cmsParagraphComponentModel;
	}

	public void setCmsParagraphComponentModel(final CMSParagraphComponentModel cmsParagraphComponentModel)
	{
		this.cmsParagraphComponentModel = cmsParagraphComponentModel;
	}
}
