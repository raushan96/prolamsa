package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commercefacades.user.data.AlertConfigurationData;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;



/**
 * Pojo for Invoice Search form.
 */
public class AlertsConfigurationParameters
{


	private String b2BUnitId;

	private B2BCustomerModel customerModel;
	private BaseStoreModel baseStoreModel;

	private List<AlertConfigurationData> alertConfigurationDatas;

	public String getB2BUnitId()
	{
		return b2BUnitId;
	}

	public void setB2BUnitId(final String b2bUnitId)
	{
		b2BUnitId = b2bUnitId;
	}

	public List<AlertConfigurationData> getAlertConfigurationDatas()
	{
		return alertConfigurationDatas;
	}

	public void setAlertConfigurationDatas(final List<AlertConfigurationData> alertConfigurationDatas)
	{
		this.alertConfigurationDatas = alertConfigurationDatas;
	}

	public B2BCustomerModel getCustomerModel()
	{
		return customerModel;
	}

	public void setCustomerModel(B2BCustomerModel customerModel)
	{
		this.customerModel = customerModel;
	}

	public BaseStoreModel getBaseStoreModel()
	{
		return baseStoreModel;
	}

	public void setBaseStoreModel(BaseStoreModel baseStoreModel)
	{
		this.baseStoreModel = baseStoreModel;
	}


}
