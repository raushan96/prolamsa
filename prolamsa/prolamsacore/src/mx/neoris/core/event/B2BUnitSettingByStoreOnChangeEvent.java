/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author e-hicastaneda
 * 
 */
public class B2BUnitSettingByStoreOnChangeEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String originalSetting;
	private String storeId;
	private B2BUnitModel b2BUnitOwner;
	private String setting;


	/**
	 * @return the originalSetting
	 */
	public String getOriginalSetting()
	{
		return originalSetting;
	}

	/**
	 * @param originalSetting
	 *           the originalSetting to set
	 */
	public void setOriginalSetting(final String originalSetting)
	{
		this.originalSetting = originalSetting;
	}

	public String getStoreId()
	{
		return storeId;
	}

	public void setStoreId(final String storeId)
	{
		this.storeId = storeId;
	}

	public B2BUnitModel getB2BUnitOwner()
	{
		return b2BUnitOwner;
	}

	public void setB2BUnitOwner(final B2BUnitModel b2bUnitOwner)
	{
		b2BUnitOwner = b2bUnitOwner;
	}

	public String getSetting()
	{
		return setting;
	}

	public void setSetting(final String setting)
	{
		this.setting = setting;
	}






}
