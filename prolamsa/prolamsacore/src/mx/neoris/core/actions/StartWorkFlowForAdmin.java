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
package mx.neoris.core.actions;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.services.dao.impl.DefaultNeorisUserDao;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import de.hybris.platform.store.BaseStoreModel;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.enums.PermissionStatus;
import de.hybris.platform.b2b.enums.WorkflowTemplateType;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BPermissionResultModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.process.approval.actions.AbstractSimpleB2BApproveOrderDecisionAction;
import de.hybris.platform.b2b.process.approval.actions.B2BPermissionResultHelperImpl;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2b.services.B2BWorkflowIntegrationService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.workflow.WorkflowProcessingService;
import de.hybris.platform.workflow.WorkflowService;
import de.hybris.platform.workflow.model.WorkflowModel;
import de.hybris.platform.workflow.model.WorkflowTemplateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


public class StartWorkFlowForAdmin extends AbstractSimpleB2BApproveOrderDecisionAction
{
	private static final Logger LOG = Logger.getLogger(StartWorkFlowForAdmin.class);

	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;
	private B2BWorkflowIntegrationService b2bWorkflowIntegrationService;
	private WorkflowProcessingService workflowProcessingService;
	private WorkflowService workflowService;
	private B2BPermissionResultHelperImpl permissionResultHelper;
	private UserService userService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "neorisUserDao")
	private DefaultNeorisUserDao neorisUserDao;

	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	@Override
	public Transition executeAction(final B2BApprovalProcessModel process) throws RetryLaterException
	{
		final OrderModel order = process.getOrder();
		try
		{
			final B2BCustomerModel customer = (B2BCustomerModel) order.getUser();
			B2BCustomerModel admin = findB2BAdministratorForCustomer(customer);

			if (admin == null)
			{
				admin = userService.getUserForUID(configurationService.getConfiguration().getString("customer.administration.order"),
						B2BCustomerModel.class);
			}

			if (admin != null)
			{
				// extract only the permissions that required approval by the administrator
				final Collection<B2BPermissionResultModel> permissionsToApprove = getPermissionResultHelper()
						.filterResultByPermissionStatus(order.getPermissionResults(), PermissionStatus.OPEN);

				// make the admin owner of the permissions
				for (final B2BPermissionResultModel b2bPermissionResultModel : permissionsToApprove)
				{
					b2bPermissionResultModel.setApprover(admin);
				}
				order.setPermissionResults(permissionsToApprove);

				// assign the administrator to a b2b approver group if he is not a member of this group
				assignToGroup(admin, userService.getUserGroupForUID(B2BConstants.B2BAPPROVERGROUP));

				// create the workflow for the admin
				final WorkflowModel workflow = createAndStartWorkflow(process, admin);
				order.setWorkflow(workflow);
				order.setStatus(OrderStatus.ASSIGNED_TO_ADMIN);
				this.modelService.saveAll();
				return Transition.OK;
			}
			else
			{
				LOG.error(String.format("Order %s placed by %s has failed to get approved, no approvers or administrators where"
						+ " " + "found in the system to approve it", order.getCode(), order.getUser().getUid()));

				order.setStatus(OrderStatus.B2B_PROCESSING_ERROR);
				modelService.save(order);
				return Transition.NOK;
			}
		}
		catch (final Exception e)
		{
			LOG.info("Catch to create order [" + order.getCode() + "]. Store [" + order.getStore().getUid()
					+ "] ... Preparing to send notification");

			/////////////////////////HERE
			final String eol = "<br>";
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//Set New Subject
			subjectData.append(order.getStore().getName() + " | ");
			subjectData.append("Error create order/quote | ");
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));
			
			//Set New Body
			if (order.getStore().getUid().equalsIgnoreCase("1000") || order.getStore().getUid().equalsIgnoreCase("5000"))
			{
				bodyData.append("Buen día." + eol + eol);
				bodyData.append("Se tuvo problemas al momento de crear el pedido del cliente " + order.getUnit().getUid() + "."
						+ eol);
				bodyData.append("Favor de contactar a su asesor de ventas." + eol + eol);
				bodyData.append("Saludos!" + eol);
			}
			else
			{
				bodyData.append("Good day." + eol + eol);
				bodyData.append("There was a problem creating the order client " + order.getUnit().getUid() + "." + eol);
				bodyData.append("Please contact you Sales Representative." + eol + eol);
				bodyData.append("Regards!" + eol);
			}

			if (!order.getStore().getSendNotificationToUserDocument())
			{
				bodyData.append("<b>Description:</b>" + eol);
				bodyData.append(eol + e);
			}


			B2BCustomerModel salesRep = new B2BCustomerModel();
			B2BCustomerModel backoffice = new B2BCustomerModel();
			String salesRepS = "";
			String backofficeS = "";
			String userCreator = "";

			if (order.getStore().getSendNotificationToUserDocument())
			{
				userCreator = order.getUser().getUid();
				LOG.info("userCreator: " + userCreator);
			}

			if (order.getStore().getSendNotificationToProlamsaInternal())
			{
				try
				{
					salesRep = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(this.getSalesRepFor(order), order.getStore());
				}
				catch (final Exception e1)
				{
					// YTODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (salesRep != null)
				{
					LOG.info("Sales rep Id from B2BUnit: " + salesRep.getEmail());
					salesRepS = salesRep.getEmail();
				}


				try
				{
					backoffice = neorisUserDao.getB2bCustomerModelByBackofficeAndStore(this.getBackOfficeAccountFor(order),
							order.getStore());
				}
				catch (final Exception e1)
				{
					// YTODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (backoffice != null)
				{
					LOG.info("backoffice from B2BUnit: " + backoffice.getEmail());
					backofficeS = backoffice.getEmail();
				}

			}

			if (order.getStore().getSendNotificationToUserDocument() || order.getStore().getSendNotificationToProlamsaInternal())
			{
				neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString(), salesRepS, backofficeS,
						userCreator);
			}

			/////////////////////////END HERE

			handleError(order, e);
			return Transition.NOK;

		}
	}
	
	protected String getSalesRepFor(final OrderModel orderModel)
	{
		final BaseStoreModel orderBaseStore = orderModel.getStore();
		final B2BUnitModel orderB2BUnit = orderModel.getUnit();
		final Set<NeorisB2BUnitSettingByStoreModel> salesRepIds = orderB2BUnit.getSalesRepForStore();

		String salesRepId = "00000";

		for (final NeorisB2BUnitSettingByStoreModel eachSaleRepId : salesRepIds)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase(eachSaleRepId.getStoreId()))
			{
				salesRepId = eachSaleRepId.getSetting();
			}
		}
		return salesRepId;
	}

	protected String getBackOfficeAccountFor(final OrderModel orderModel)
	{
		final BaseStoreModel orderBaseStore = orderModel.getStore();
		final B2BUnitModel orderB2BUnit = orderModel.getUnit();
		final Set<NeorisB2BUnitSettingByStoreModel> backofficeAccounts = orderB2BUnit.getBackOfficeAccountForStore();

		String backofficeID = "00000";

		for (final NeorisB2BUnitSettingByStoreModel eachBackoffice : backofficeAccounts)
		{
			if (orderBaseStore.getUid().equalsIgnoreCase(eachBackoffice.getStoreId()))
			{
				backofficeID = eachBackoffice.getSetting();
			}
		}
		return backofficeID;
	}


	protected void assignToGroup(final B2BCustomerModel admin, final UserGroupModel userGroup)
	{
		final Set<PrincipalGroupModel> groupModelSet = admin.getGroups();
		if (!groupModelSet.contains(userGroup))
		{
			final Set<PrincipalGroupModel> groups = new HashSet<PrincipalGroupModel>(groupModelSet);
			groups.add(userGroup);
			admin.setGroups(groups);
		}
	}

	protected B2BCustomerModel findB2BAdministratorForCustomer(final B2BCustomerModel customer)
	{
		final List<B2BCustomerModel> b2bAdminGroupUsers = new ArrayList<B2BCustomerModel>(getB2bUnitService().getUsersOfUserGroup(
				getB2bUnitService().getParent(customer), B2BConstants.B2BADMINGROUP, true));
		// remove the user who placed the order.
		CollectionUtils.filter(b2bAdminGroupUsers, PredicateUtils.notPredicate(PredicateUtils.equalPredicate(customer)));
		return (CollectionUtils.isNotEmpty(b2bAdminGroupUsers) ? b2bAdminGroupUsers.get(0) : null);
	}

	protected WorkflowModel createAndStartWorkflow(final B2BApprovalProcessModel process, final B2BCustomerModel admin)
	{
		final String workflowTemplateCode = getB2bWorkflowIntegrationService().generateWorkflowTemplateCode(
				"B2B_APPROVAL_WORKFLOW", Collections.singletonList(admin));
		final WorkflowTemplateModel workflowTemplate = getB2bWorkflowIntegrationService().createWorkflowTemplate(
				Collections.singletonList(admin), workflowTemplateCode, "Generated B2B Order Approval Workflow",
				WorkflowTemplateType.ORDER_APPROVAL);
		final WorkflowModel workflow = getWorkflowService().createWorkflow(workflowTemplate.getName(), workflowTemplate,
				Collections.<ItemModel> singletonList(process), workflowTemplate.getOwner());
		getWorkflowProcessingService().startWorkflow(workflow);
		if (LOG.isDebugEnabled())
		{
			LOG.debug(String.format("Started workflow for order %s placed by %s assigned to administrator %s", process.getOrder()
					.getCode(), process.getOrder().getUser().getUid(), admin.getUid()));
		}

		return workflow;
	}

	protected void handleError(final OrderModel order, final Exception exception)
	{
		if (order != null)
		{
			this.setOrderStatus(order, OrderStatus.B2B_PROCESSING_ERROR);
		}
		LOG.error(exception.getMessage(), exception);
	}

	protected B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2bUnitService()
	{
		return b2bUnitService;
	}

	@Required
	public void setB2bUnitService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

	protected B2BWorkflowIntegrationService getB2bWorkflowIntegrationService()
	{
		return b2bWorkflowIntegrationService;
	}

	@Required
	public void setB2bWorkflowIntegrationService(final B2BWorkflowIntegrationService b2bWorkflowIntegrationService)
	{
		this.b2bWorkflowIntegrationService = b2bWorkflowIntegrationService;
	}

	protected WorkflowProcessingService getWorkflowProcessingService()
	{
		return workflowProcessingService;
	}

	@Required
	public void setWorkflowProcessingService(final WorkflowProcessingService workflowProcessingService)
	{
		this.workflowProcessingService = workflowProcessingService;
	}

	protected WorkflowService getWorkflowService()
	{
		return workflowService;
	}

	@Required
	public void setWorkflowService(final WorkflowService workflowService)
	{
		this.workflowService = workflowService;
	}

	protected B2BPermissionResultHelperImpl getPermissionResultHelper()
	{
		return permissionResultHelper;
	}

	@Required
	public void setPermissionResultHelper(final B2BPermissionResultHelperImpl permissionResultHelper)
	{
		this.permissionResultHelper = permissionResultHelper;
	}

	protected UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}
}
