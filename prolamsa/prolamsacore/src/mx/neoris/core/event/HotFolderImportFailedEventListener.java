/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import mx.neoris.core.model.HotFolderImportFailedProcessModel;

import org.springframework.beans.factory.annotation.Required;


/**
 * Event listener for forgotten password functionality.
 */
public class HotFolderImportFailedEventListener extends AbstractSiteEventListener<HotFolderImportFailedEvent>
{
	private BusinessProcessService businessProcessService;
	private ModelService modelService;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	@Override
	protected void onSiteEvent(final HotFolderImportFailedEvent event)
	{
		final HotFolderImportFailedProcessModel processModel = (HotFolderImportFailedProcessModel) getBusinessProcessService()
				.createProcess("hotFolderImportFailed" + "-" + event.getFilename() + "-" + System.currentTimeMillis(),
						"hotFolderImportFailedEmailProcess");

		processModel.setFilename(event.getFilename());
		processModel.setStackTrace(event.getStackTrace());

		processModel.setSite(event.getSite());
		processModel.setStore(event.getBaseStore());
		processModel.setCustomer(event.getCustomer());
		processModel.setLanguage(event.getLanguage());
		processModel.setCurrency(event.getCurrency());

		getModelService().save(processModel);
		getBusinessProcessService().startProcess(processModel);
	}

	@Override
	protected boolean shouldHandleEvent(final HotFolderImportFailedEvent event)
	{
		//return true;
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return SiteChannel.B2B.equals(site.getChannel());
	}
}
