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
package mx.neoris.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;

import mx.neoris.core.model.HotFolderImportFailedProcessModel;



/**
 * Velocity context for a forgotten password email.
 */
public class HotFolderImportFailedEmailContext extends CustomerEmailContext
{
	private String filename;
	private String stackTrace;

	/**
	 * @return the filename
	 */
	public String getFilename()
	{
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	/**
	 * @return the stackTrace
	 */
	public String getStackTrace()
	{
		return stackTrace;
	}

	/**
	 * @param stackTrace
	 *            the stackTrace to set
	 */
	public void setStackTrace(String stackTrace)
	{
		this.stackTrace = stackTrace;
	}
	
	@Override
	public void init(final StoreFrontCustomerProcessModel model, final EmailPageModel emailPageModel)
	{
		super.init(model, emailPageModel);

		if (model instanceof HotFolderImportFailedProcessModel)
		{
			HotFolderImportFailedProcessModel hotFolderModel = (HotFolderImportFailedProcessModel) model;
			setFilename(hotFolderModel.getFilename());
			setStackTrace(hotFolderModel.getStackTrace());
		}
	}
}
