package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.store.BaseStoreModel;


/**
 * Pojo for Invoice Search form.
 */
public class B2BCustomerSaveSettingParameters
{

	private B2BCustomerModel b2bCustomerModel;
	private B2BUnitModel b2bUnitModel;
	private BaseStoreModel baseStoreModel;

	private String address;
	private String location;
	private String uom;
	private String transportationMode;

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *           the address to set
	 */
	public void setAddress(final String address)
	{
		this.address = address;
	}

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *           the location to set
	 */
	public void setLocation(final String location)
	{
		this.location = location;
	}

	/**
	 * @return the uom
	 */
	public String getUom()
	{
		return uom;
	}

	/**
	 * @param uom
	 *           the uom to set
	 */
	public void setUom(final String uom)
	{
		this.uom = uom;
	}

	/**
	 * @return the transportationMode
	 */
	public String getTransportationMode()
	{
		return transportationMode;
	}

	/**
	 * @param transportationMode
	 *           the transportationMode to set
	 */
	public void setTransportationMode(final String transportationMode)
	{
		this.transportationMode = transportationMode;
	}

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
