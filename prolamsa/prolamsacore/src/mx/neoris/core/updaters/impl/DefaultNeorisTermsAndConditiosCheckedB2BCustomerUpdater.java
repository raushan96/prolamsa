/**
 * 
 */
package mx.neoris.core.updaters.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.cms2.model.contents.ContentCatalogModel;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.TermsAndCoditionsByStoreModel;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.updaters.NeorisTermsAndConditionsCheckedB2BCustomerUpdater;

import org.apache.log4j.Logger;


/**
 * @author e-jecarrilloi
 * 
 */
public class DefaultNeorisTermsAndConditiosCheckedB2BCustomerUpdater implements NeorisTermsAndConditionsCheckedB2BCustomerUpdater
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisTermsAndConditiosCheckedB2BCustomerUpdater.class);

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2BCustomerService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.updaters.NeorisTermsAndConditionsB2BCustomerUpdater#updateAllB2BCustomerWith(de.hybris.platform
	 * .cms2.model.contents.components.CMSParagraphComponentModel)
	 */
	@Override
	public void updateAllB2BCustomerWith(final CMSParagraphComponentModel cmsParagraphComponentModel)
	{
		// YTODO Auto-generated method stub
		final List<B2BCustomerModel> allCUstomerModels = b2BCustomerService.getAllUsers();

		for (final B2BCustomerModel eachCustomerModel : allCUstomerModels)
		{

			final ContentCatalogModel contentCatalogModel = (ContentCatalogModel) (cmsParagraphComponentModel.getCatalogVersion()
					.getCatalog());

			final String baseStoreId = contentCatalogModel.getCmsSites().iterator().next().getUid();

			final List<TermsAndCoditionsByStoreModel> termsByCustomer = eachCustomerModel.getTermsAndCoditionsByStore();

			final List<TermsAndCoditionsByStoreModel> newTermsAndConditios = new ArrayList<TermsAndCoditionsByStoreModel>();

			boolean hasTermsAndCoditionsForStore = false;
			if (termsByCustomer != null && termsByCustomer.size() > 0)
			{
				for (final TermsAndCoditionsByStoreModel eachTerm : termsByCustomer)
				{
					newTermsAndConditios.add(eachTerm);
					if (baseStoreId.equalsIgnoreCase(eachTerm.getBaseStoreId()))
					{
						hasTermsAndCoditionsForStore = true;

						eachTerm.setTermsAndConditionsChecked(false);

						modelService.save(eachTerm);
					}
				}
			}


			if (!hasTermsAndCoditionsForStore)
			{
				final TermsAndCoditionsByStoreModel termsConditions = new TermsAndCoditionsByStoreModel();

				termsConditions.setBaseStoreId(baseStoreId);

				termsConditions.setTermsAndConditionsChecked(false);

				newTermsAndConditios.add(termsConditions);

				eachCustomerModel.setTermsAndCoditionsByStore(newTermsAndConditios);

				try
				{
					modelService.save(eachCustomerModel);
				}
				catch (final Exception ex)
				{
					LOG.error("Error updating termsAndCoditionsChecked for Customer: " + eachCustomerModel.getUid()
							+ " Has not b2bunit");
				}
			}


		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.updaters.NeorisTermsAndConditionsB2BCustomerUpdater#updateFor(de.hybris.platform.b2b.model.
	 * B2BCustomerModel, de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public void updateFor(final B2BCustomerModel b2bCustomerModel, final BaseStoreModel baseStoreModel)
	{
		final String baseStoreId = baseStoreModel.getUid();

		final List<TermsAndCoditionsByStoreModel> termsAndConditions = b2bCustomerModel.getTermsAndCoditionsByStore();

		final List<TermsAndCoditionsByStoreModel> newTermsAndConditios = new ArrayList<TermsAndCoditionsByStoreModel>();

		boolean hasTermsAndCoditionsForStore = false;
		if (termsAndConditions != null && termsAndConditions.size() > 0)
		{

			for (final TermsAndCoditionsByStoreModel eachTermsAndConditions : termsAndConditions)
			{
				newTermsAndConditios.add(eachTermsAndConditions);
				if (baseStoreId.equalsIgnoreCase(eachTermsAndConditions.getBaseStoreId()))
				{
					hasTermsAndCoditionsForStore = true;

					eachTermsAndConditions.setTermsAndConditionsChecked(true);

					modelService.save(eachTermsAndConditions);

					break;
				}
			}
		}



		if (!hasTermsAndCoditionsForStore)
		{
			final TermsAndCoditionsByStoreModel termsConditions = new TermsAndCoditionsByStoreModel();

			termsConditions.setBaseStoreId(baseStoreId);

			termsConditions.setTermsAndConditionsChecked(true);

			newTermsAndConditios.add(termsConditions);

			b2bCustomerModel.setTermsAndCoditionsByStore(newTermsAndConditios);

			try
			{
				modelService.save(b2bCustomerModel);
			}
			catch (final Exception ex)
			{
				LOG.error("Error updating termsAndCoditionsChecked for Customer: " + b2bCustomerModel.getUid() + " after place order");
			}
		}

	}
}
