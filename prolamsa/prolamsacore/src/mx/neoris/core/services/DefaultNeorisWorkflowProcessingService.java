/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.WorkflowActionService;
import de.hybris.platform.workflow.enums.WorkflowActionStatus;
import de.hybris.platform.workflow.enums.WorkflowActionType;
import de.hybris.platform.workflow.exceptions.ActivationWorkflowActionException;
import de.hybris.platform.workflow.exceptions.AutomatedWorkflowActionException;
import de.hybris.platform.workflow.exceptions.AutomatedWorkflowTemplateException;
import de.hybris.platform.workflow.impl.DefaultWorkflowProcessingService;
import de.hybris.platform.workflow.jalo.Workflow;
import de.hybris.platform.workflow.jobs.AutomatedWorkflowTemplateJob;
import de.hybris.platform.workflow.model.AbstractWorkflowActionModel;
import de.hybris.platform.workflow.model.AutomatedWorkflowActionTemplateModel;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;
import de.hybris.platform.workflow.model.WorkflowItemAttachmentModel;
import de.hybris.platform.workflow.model.WorkflowModel;
import de.hybris.platform.workflow.strategies.ActionActivationStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;


/**
 * 
 */
public class DefaultNeorisWorkflowProcessingService extends DefaultWorkflowProcessingService
{
	private static final Logger LOG = Logger.getLogger(DefaultWorkflowProcessingService.class);

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "actionActivationStrategy")
	private ActionActivationStrategy actionActivationStrategy;

	@Resource(name = "workflowActionService")
	private WorkflowActionService workflowActionService;

	@Override
	public boolean startWorkflow(final WorkflowModel workflowModel)
	{
		if (this.modelService.isNew(workflowModel))
		{
			throw new IllegalArgumentException("Workflow model " + workflowModel + " has to be saved before start");
		}



		boolean startFlagFound = false;
		final List<WorkflowActionModel> actions = workflowModel.getActions();

		for (final WorkflowActionModel action : actions)
		{
			final List<WorkflowItemAttachmentModel> attachments = workflowModel.getAttachments();

			if (attachments != null && attachments.size() > 0)
			{
				for (final WorkflowItemAttachmentModel eachAttachment : attachments)
				{
					if (eachAttachment.getItem() instanceof B2BApprovalProcessModel)
					{
						final OrderModel orderModel = ((B2BApprovalProcessModel) eachAttachment.getItem()).getOrder();
						if (orderModel != null)
						{
							action.setB2bUnit(orderModel.getUnit());
							modelService.save(action);
						}
					}
				}
			}

			if (action.getActionType() != WorkflowActionType.START)
			{
				continue;
			}
			setPausedStatus(workflowModel);
			modelService.refresh(workflowModel);
			LOG.info("After: " + workflowModel.getStatus());

			activate(action);
			startFlagFound = true;


		}

		if (!(startFlagFound))
		{
			for (final WorkflowActionModel action : actions)
			{
				if (action.getPredecessors().size() <= 0)

				{
					continue;
				}

				setPausedStatus(workflowModel);
				activate(workflowModel);
				this.modelService.refresh(workflowModel);

			}

		}

		return startFlagFound;
	}

	private void activate(final WorkflowModel workflowModel)
	/*     */
	{
		/* 258 */Collection activeActionsBefore = null;
		/* 259 */Collection activeActionsAfter = null;
		/*     */do
		/*     */
		{
			/* 262 */activeActionsBefore = getActiveActions(workflowModel);
			/* 263 */for (final WorkflowActionModel action : workflowModel.getActions())
			/*     */
			{
				/* 265 */tryActivate(action);
				/*     */}
			/* 267 */activeActionsAfter = getActiveActions(workflowModel);
			/*     */}
		/* 269 */while ((activeActionsBefore.size() != activeActionsAfter.size())
				&& (!(activeActionsBefore.containsAll(activeActionsAfter))));
		/*     */}

	private void setPausedStatus(final WorkflowModel workflow)
	/*     */
	{
		/* 647 */final Workflow _workflow = (Workflow) this.modelService.getSource(workflow);
		/* 648 */_workflow.setPausedStatus();
		/*     */}

	@Override
	public boolean activate(final WorkflowActionModel action)
	/*     */throws ActivationWorkflowActionException
	/*     */
	{
		/* 86 */if (action.getActionType() == WorkflowActionType.END)
		/*     */
		{
			/* 88 */endWorkflow(action.getWorkflow());
			/* 89 */return true;
			/*     */}
		/* 91 */if ((WorkflowActionStatus.DISABLED != action.getStatus()) &&
		/* 92 */(action.getStatus() != WorkflowActionStatus.ENDED_THROUGH_END_OF_WORKFLOW))
		/*     */
		{
			/* 94 */action.setStatus(WorkflowActionStatus.IN_PROGRESS);
			/* 95 */action.setActivated(new Date());
			/* 96 */if (action.getFirstActivated() == null)
			/*     */
			{
				/* 98 */action.setFirstActivated(new Date());
				/*     */}
			/*     */
			/* 101 */if (action.getTemplate() instanceof AutomatedWorkflowActionTemplateModel)
			/*     */
			{
				/* 103 */performAutomatedWorkflow(action);
				/*     */}
			/* 105 */this.actionActivationStrategy.doAfterActivation(action);
			/* 106 */this.modelService.save(action);
			/* 107 */return false;
			/*     */}
		/* 109 */return true;
		/*     */}

	private void performAutomatedWorkflow(final WorkflowActionModel action)
	/*     */
	{
		/*     */try
		/*     */{
			/* 117 */final AutomatedWorkflowTemplateJob automatedWorkflow = getAutomatedWorkflowRegistry()
					.getAutomatedWorkflowTemplateJobForTemplate(
					/* 118 */(AutomatedWorkflowActionTemplateModel) action.getTemplate());
			/* 119 */if (LOG.isDebugEnabled())
			/*     */
			{
				/* 121 */LOG.debug("calling perform on action object: " + automatedWorkflow);
				/*     */}
			/* 123 */writeAutomatedComment(action, "text.automatedworkflowactionTemplate.perform.start", new String[]
			{ automatedWorkflow.getClass()
			/* 124 */.getName() });
			/* 125 */final WorkflowDecisionModel decision = automatedWorkflow.perform(action);
			/* 126 */writeAutomatedComment(action, "text.automatedworkflowactionTemplate.perform.end", new String[]
			{ automatedWorkflow.getClass().getName() });
			/* 127 */if (LOG.isDebugEnabled())
			/*     */
			{
				/* 129 */LOG.debug("finish perform on action object");
				/*     */
				/*     */}
			/*     */
			/* 133 */if (decision == null)
			{
				/*     */return;
			}
			/* 135 */action.setSelectedDecision(decision);
			/* 136 */decide(action);
			/* 137 */if (!(LOG.isDebugEnabled()))
			{
				/*     */return;
			}
			/* 139 */LOG.debug("decide for next workflow action");
			/*     */
			/*     */
			/*     */}
		/*     */catch (final AutomatedWorkflowTemplateException e)
		/*     */
		{
			/* 145 */writeAutomatedComment(action, "text.automatedworkflowactionTemplate.perform.error", new String[]
			{ e.getMessage() });
			/* 146 */throw new AutomatedWorkflowActionException(e);
			/*     */}
		/*     */final AutomatedWorkflowTemplateJob automatedWorkflow;
		/*     */}

	/*     */private Collection<WorkflowActionModel> getActiveActions(final WorkflowModel workflowModel)
	/*     */
	{
		/* 319 */final Collection ret = new ArrayList();
		/* 320 */for (final WorkflowActionModel action : workflowModel.getActions())
		/*     */
		{
			/* 322 */if (!(this.workflowActionService.isActive(action)))
			{
				/*     */continue;
			}
			/* 324 */ret.add(action);
			/*     */}
		/*     */
		/* 327 */return ret;
		/*     */}

	private void tryActivate(final WorkflowActionModel action)
	/*     */
	{
		/* 275 */if (this.workflowActionService.isCompleted(action))
		/*     */
		{
			/* 277 */if (predecessorsCompleted(action))
			{
				/*     */return;
			}
			/* 279 */LOG.warn("Invalid state: Action " + action.getCode() + " is completed, but not all predecessors");
			/*     */}
		/*     */else
		{
			/* 282 */if (this.workflowActionService.isActive(action))
			{
				/*     */return;
				/*     */}
			/* 285 */if (predecessorsCompleted(action))
			/*     */
			{
				/* 287 */activate(action);
				/*     */}
			/*     */else
			/*     */{
				/* 291 */tryActivateSuccessors(action);
				/*     */}
			/*     */}
		/*     */}

	/*     */private void tryActivateSuccessors(final WorkflowActionModel action)
	/*     */
	{
		/* 298 */for (final AbstractWorkflowActionModel suc : action.getSuccessors())
		/*     */
		{
			/* 300 */tryActivate((WorkflowActionModel) suc);
			/*     */}
		/*     */}
}
