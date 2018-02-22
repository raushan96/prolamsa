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
import de.hybris.platform.core.model.user.UserModel;

import mx.neoris.core.model.AddedIncidentProcessModel;
import mx.neoris.core.model.HotFolderImportFailedProcessModel;
import mx.neoris.core.model.IncidentModel;



/**
 * Velocity context for a forgotten password email.
 */
public class AddedIncidentEmailContext extends CustomerEmailContext
{
	private IncidentModel incidentModel;
	private UserModel userModel;

	
	
	@Override
	public void init(final StoreFrontCustomerProcessModel model, final EmailPageModel emailPageModel)
	{
		super.init(model, emailPageModel);

		if (model instanceof AddedIncidentProcessModel)
		{
			AddedIncidentProcessModel addedIncidentProcessModel = (AddedIncidentProcessModel) model;
			setIncidentModel(addedIncidentProcessModel.getIncident());
			setUserModel(addedIncidentProcessModel.getUser());
		}
	}



	public UserModel getUserModel() {
		return userModel;
	}



	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}



	public IncidentModel getIncidentModel() {
		return incidentModel;
	}



	public void setIncidentModel(IncidentModel incidentModel) {
		this.incidentModel = incidentModel;
	}
}
