package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.store.BaseStoreModel;


/**
 * Pojo for Invoice Search form.
 */
public class B2BCustomerSettingParameters
{

	private B2BCustomerModel b2bCustomerModel;
	private B2BUnitModel b2bUnitModel;
	private BaseStoreModel baseStoreModel;

	public BaseStoreModel getBaseStoreModel()
	{
		return baseStoreModel;
	}

	public void setBaseStoreModel(final BaseStoreModel baseStoreModel)
	{
		this.baseStoreModel = baseStoreModel;
	}

	public B2BUnitModel getB2bUnitModel()
	{
		return b2bUnitModel;
	}

	public void setB2bUnitModel(final B2BUnitModel b2bUnitModel)
	{
		this.b2bUnitModel = b2bUnitModel;
	}

	public B2BCustomerModel getB2bCustomerModel()
	{
		return b2bCustomerModel;
	}

	public void setB2bCustomerModel(final B2BCustomerModel b2bCustomerModel)
	{
		this.b2bCustomerModel = b2bCustomerModel;
	}

}
