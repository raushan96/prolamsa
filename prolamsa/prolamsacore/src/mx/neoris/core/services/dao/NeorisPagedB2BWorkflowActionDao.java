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
package mx.neoris.core.services.dao;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.workflow.enums.WorkflowActionType;
import de.hybris.platform.workflow.model.WorkflowActionModel;

import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;


public interface NeorisPagedB2BWorkflowActionDao
{
	SearchPageData<WorkflowActionModel> findPagedWorkflowActionsByUserAndActionTypes(UserModel user,
			WorkflowActionType[] actionTypes, PageableData pageableData, NeorisOrderApprovalSearchParameters searchParameters);
}
