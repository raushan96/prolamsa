/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import java.util.Set;

import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;


/**
 * @author e-hicastaneda
 * 
 */
public class B2BUnitSalesRepOnChangeEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private B2BUnitModel b2bUnitModel;
	private String originalSalesRepId;
	private String StoreId;
	private Set<NeorisB2BUnitSettingByStoreModel> originalSettings;


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
	 * @return the originalSalesRepId
	 */
	public String getOriginalSalesRepId()
	{
		return originalSalesRepId;
	}

	/**
	 * @param originalSalesRepId
	 *           the originalSalesRepId to set
	 */
	public void setOriginalSalesRepId(final String originalSalesRepId)
	{
		this.originalSalesRepId = originalSalesRepId;
	}

	public String getStoreId()
	{
		return StoreId;
	}

	public void setStoreId(final String storeId)
	{
		StoreId = storeId;
	}

	public Set<NeorisB2BUnitSettingByStoreModel> getOriginalSettings()
	{
		return originalSettings;
	}

	public void setOriginalSettings(final Set<NeorisB2BUnitSettingByStoreModel> originalSettings)
	{
		this.originalSettings = originalSettings;
	}





}
