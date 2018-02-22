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
package mx.neoris.core.services.dao.impl;


import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2bacceleratorservices.dao.impl.DefaultPagedB2BWorkflowActionDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.workflow.enums.WorkflowActionType;
import de.hybris.platform.workflow.model.WorkflowActionModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.dao.NeorisPagedB2BWorkflowActionDao;


public class DefaultNeorisPagedB2BWorkflowActionDao extends DefaultPagedB2BWorkflowActionDao implements
		NeorisPagedB2BWorkflowActionDao
{

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2bCustomerService;

	public DefaultNeorisPagedB2BWorkflowActionDao(final String code)
	{
		super(code);
	}

	public DefaultNeorisPagedB2BWorkflowActionDao()
	{
		super("WorkflowAction");
	}

	@Override
	public SearchPageData<WorkflowActionModel> findPagedWorkflowActionsByUserAndActionTypes(final UserModel user,
			final WorkflowActionType[] actionTypes, final PageableData pageableData,
			final NeorisOrderApprovalSearchParameters searchParameters)
	{
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		String query = "SELECT {wa.pk}" + "FROM { WorkflowAction as wa "
				+ "JOIN WorkflowActionType as wfa ON {wa.actionType} = {wfa.pk} "
				+ "JOIN B2BUnit as b2bunit ON {wa.b2bUnit} = {b2bunit.pk}} " + "WHERE { wa.principalAssigned} = " + user.getPk();


		final StringBuilder b2bunitsQuery = new StringBuilder();
		if (searchParameters.getB2bUnits() != null && searchParameters.getB2bUnits().length > 0)
		{
			b2bunitsQuery.append("(");
			for (final String eachB2BUnit : searchParameters.getB2bUnits())
			{
				b2bunitsQuery.append("'");
				b2bunitsQuery.append(eachB2BUnit);
				b2bunitsQuery.append("',");
			}

			b2bunitsQuery.deleteCharAt(b2bunitsQuery.length() - 1);
			b2bunitsQuery.append(")");

			query = query + " AND  {b2bunit.uid} IN " + b2bunitsQuery.toString();
		}
		else
		{
			final List<String> listB2BUnitCode = new ArrayList<String>();
			final List<String> listB2BUnitCode2 = new ArrayList<String>();

			final List<B2BUnitModel> allUnits = neorisService.getB2BUnitModelsForCustomerAndCurrentStore(b2bCustomerService
					.getCurrentB2BCustomer());

			for (final B2BUnitModel eachB2BUnit : allUnits)
			{
				listB2BUnitCode.add(eachB2BUnit.getPk().getLongValueAsString());
			}

			///////INICIO////////////
			for (final B2BUnitModel eachB2BUnit2 : allUnits)
			{
				listB2BUnitCode2.add(eachB2BUnit2.getUid());
			}

			final StringBuilder b2bunitsQuery2 = new StringBuilder();
			if (listB2BUnitCode2 != null)
			{
				b2bunitsQuery2.append("(");
				for (final String eachB2BUnit3 : listB2BUnitCode2)
				{
					b2bunitsQuery2.append("'");
					b2bunitsQuery2.append(eachB2BUnit3);
					b2bunitsQuery2.append("',");
				}

				b2bunitsQuery2.deleteCharAt(b2bunitsQuery2.length() - 1);
				b2bunitsQuery2.append(")");

				query = query + " AND  {b2bunit.uid} IN " + b2bunitsQuery2.toString();
			}
			//////FIN/////////////

			query.concat(" AND ({order." + OrderModel.UNIT + "} = '");
			//queryParams.put("listCode", (Serializable) listB2BUnitCode);

			for (final String eachCode : listB2BUnitCode)
			{
				query.concat(eachCode + "' OR {" + OrderModel.UNIT + "}='");
			}
			query.substring(0, query.length() - 11);
			query.concat(" ) ");
		}

		final StringBuilder actionTypesQuery = new StringBuilder();
		if (actionTypes != null && actionTypes.length > 0)
		{
			actionTypesQuery.append("(");
			for (final WorkflowActionType eachActionType : actionTypes)
			{
				actionTypesQuery.append("'");
				actionTypesQuery.append(eachActionType.getCode());
				actionTypesQuery.append("',");
			}

			actionTypesQuery.deleteCharAt(actionTypesQuery.length() - 1);
			actionTypesQuery.append(")");

			query = query + " AND {wfa.code} IN " + actionTypesQuery.toString();
		}

		final StringBuilder sortQuery = new StringBuilder();
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append(" ORDER BY {wa.");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");
			sortQuery.append(searchParameters.getSortOrder());

			query = query + " " + sortQuery.toString();
		}

		return getPagedFlexibleSearchService().search(query, queryParams, searchParameters.getPageableData());

	}
}
