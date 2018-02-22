/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.storelocator.pos.PointOfServiceService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisB2BCustomerDefaultSettingModel;
import mx.neoris.core.model.TransportationModeModel;
import mx.neoris.core.services.B2BCustomerSaveSettingParameters;
import mx.neoris.core.services.B2BCustomerSettingParameters;
import mx.neoris.core.services.NeorisAddressService;
import mx.neoris.core.services.NeorisB2BCustomerDefaultSettingsService;
import mx.neoris.core.services.NeorisTransportationModeService;

import org.apache.commons.lang.StringUtils;


/**
 * @author hector.castaneda
 * 
 */
public class DefaultNeorisB2BCustomerDefaultSettingsService implements NeorisB2BCustomerDefaultSettingsService
{

	@Resource(name = "modelService")
	ModelService modelService;

	@Resource(name = "pointOfServiceService")
	PointOfServiceService pointOfServiceService;

	@Resource(name = "unitService")
	UnitService unitService;

	@Resource(name = "neorisTransportationModeService")
	NeorisTransportationModeService neorisTransportationModeService;

	@Resource(name = "neorisAddressService")
	NeorisAddressService neorisAddressService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisB2BCustomerDefaultSettingsService#getSetting(mx.neoris.core.services.
	 * B2BCustomerSettingParameters)
	 */
	@Override
	public NeorisB2BCustomerDefaultSettingModel getSetting(final B2BCustomerSettingParameters parameters)
	{
		final B2BUnitModel b2bUnitModel = parameters.getB2bUnitModel();
		final B2BCustomerModel b2bCustomerModel = parameters.getB2bCustomerModel();
		final BaseStoreModel baseStoreModel = parameters.getBaseStoreModel();

		if (b2bUnitModel == null || b2bCustomerModel == null || baseStoreModel == null)
		{
			return null;
		}

		final List<NeorisB2BCustomerDefaultSettingModel> settings = parameters.getB2bCustomerModel().getDefaultSettings();

		if (settings == null || settings.size() == 0)
		{
			return null;
		}

		for (final NeorisB2BCustomerDefaultSettingModel eachSetting : settings)
		{
			if (eachSetting.getB2bunit().getUid().equals(b2bUnitModel.getUid())
					&& eachSetting.getBaseStore().getUid().equals(baseStoreModel.getUid()))
			{
				return eachSetting;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisB2BCustomerDefaultSettingsService#saveSetting(mx.neoris.core.services.
	 * B2BCustomerSettingParameters)
	 */
	@Override
	public NeorisB2BCustomerDefaultSettingModel saveSetting(final B2BCustomerSaveSettingParameters parameters)
	{
		final B2BCustomerModel b2BCustomerModel = parameters.getB2bCustomerModel();

		final B2BCustomerSettingParameters getSettingParameters = new B2BCustomerSettingParameters();

		boolean isNewSetting = false;

		getSettingParameters.setB2bCustomerModel(b2BCustomerModel);
		getSettingParameters.setB2bUnitModel(parameters.getB2bUnitModel());
		getSettingParameters.setBaseStoreModel(parameters.getBaseStoreModel());

		NeorisB2BCustomerDefaultSettingModel currentSetting = getSetting(getSettingParameters);

		if (currentSetting == null)
		{
			isNewSetting = true;

			currentSetting = new NeorisB2BCustomerDefaultSettingModel();
			currentSetting.setB2bunit(parameters.getB2bUnitModel());
			currentSetting.setBaseStore(parameters.getBaseStoreModel());
			currentSetting.setOwner(b2BCustomerModel);
		}

		final String pointOfServiceName = parameters.getLocation();

		if (StringUtils.isNotBlank(pointOfServiceName))
		{
			final PointOfServiceModel pointOfServiceModel = pointOfServiceService.getPointOfServiceForName(pointOfServiceName);
			currentSetting.setLocation(pointOfServiceModel);
		}
		else
		{
			currentSetting.setLocation(null);
		}

		final String uomCode = parameters.getUom();

		if (StringUtils.isNotBlank(uomCode))
		{
			final UnitModel unitModel = unitService.getUnitForCode(uomCode);
			currentSetting.setUom(unitModel);
		}
		else
		{
			currentSetting.setUom(null);
		}

		final String transportationModeCode = parameters.getTransportationMode();

		if (StringUtils.isNotBlank(transportationModeCode))
		{
			TransportationModeModel transportationModeModel;
			try
			{
				transportationModeModel = neorisTransportationModeService
						.getTransportationModeForInternalCode(transportationModeCode);
				currentSetting.setTransportationMode(transportationModeModel);
			}
			catch (final Exception e)
			{
				currentSetting.setTransportationMode(null);
			}

		}
		else
		{
			currentSetting.setTransportationMode(null);
		}

		final String shippingAddressCode = parameters.getAddress();

		if (StringUtils.isNotBlank(shippingAddressCode))
		{
			final AddressModel addressModel = neorisAddressService.getAddressWithCode(shippingAddressCode);
			currentSetting.setShippingAddress(addressModel);
		}
		else
		{
			currentSetting.setShippingAddress(null);
		}

		modelService.save(currentSetting);

		if (isNewSetting)
		{
			List<NeorisB2BCustomerDefaultSettingModel> settings = parameters.getB2bCustomerModel().getDefaultSettings();

			if (settings != null && settings.size() == 0)
			{
				settings = new ArrayList<NeorisB2BCustomerDefaultSettingModel>();
				settings.add(currentSetting);
				b2BCustomerModel.setDefaultSettings(settings);
			}
			else
			{

				final List<NeorisB2BCustomerDefaultSettingModel> newSettings = new ArrayList<NeorisB2BCustomerDefaultSettingModel>();

				newSettings.addAll(settings);

				newSettings.add(currentSetting);

				b2BCustomerModel.setDefaultSettings(newSettings);

			}

			modelService.save(b2BCustomerModel);
		}



		// YTODO Auto-generated method stub
		return currentSetting;
	}
}
