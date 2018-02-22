package mx.neoris.facades.orders.impl;



import de.hybris.platform.b2b.services.B2BWorkflowIntegrationService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.b2bacceleratorfacades.order.impl.DefaultB2BOrderFacade;
import de.hybris.platform.b2bacceleratorservices.dao.PagedB2BWorkflowActionDao;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.workflow.enums.WorkflowActionType;
import de.hybris.platform.workflow.model.WorkflowActionModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.NeorisB2BOrderService;
import mx.neoris.core.services.NeorisOrderApprovalSearchParameters;
import mx.neoris.core.services.NeorisQuoteSearchParameters;
import mx.neoris.core.services.dao.NeorisPagedB2BWorkflowActionDao;
import mx.neoris.facades.orders.NeorisB2BOrderFacade;

import org.apache.log4j.Logger;


public class DefaultNeorisB2BOrderFacade extends DefaultB2BOrderFacade implements NeorisB2BOrderFacade
{
	
	private static final Logger LOG = Logger.getLogger(DefaultNeorisB2BOrderFacade.class);
	
	
	@Resource (name="neorisB2BOrderService")
	private NeorisB2BOrderService neorisB2BOrderService;
	
	@Resource (name="cartService")
	private CartService cartService;
	
	@Resource (name="modelService")
	private ModelService modelService;
	
	@Resource (name="baseStoreService")
	private BaseStoreService baseStoreService;
	
	@Resource (name="b2bOrderApprovalDataConverter")
	private Converter<WorkflowActionModel, B2BOrderApprovalData> b2bOrderApprovalDataConverter;
	
	@Resource (name="pagedB2BWorkflowActionDao")
	private PagedB2BWorkflowActionDao pagedB2BWorkflowActionDao;
	
	@Resource (name="neorisPagedB2BWorkflowActionDao")
	private NeorisPagedB2BWorkflowActionDao neorisPagedB2BWorkflowActionDao;
	
	@Resource (name="typeService")
	private TypeService typeService;

	@Resource (name="neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrderCreator;
	
	@Override
	public SearchPageData<OrderHistoryData> getPagedOrderHistoryForStatuses(NeorisQuoteSearchParameters searchParameters,
			OrderStatus[] statuses) throws Exception
	{
		searchParameters.setStore(baseStoreService.getCurrentBaseStore().getPk().toString());
		SearchPageData<OrderModel> orderResults = null;

		final List<OrderData> orderDatas = new ArrayList<OrderData>();
		try
		{
			orderResults = neorisB2BOrderService.getPagedQuotesWithStatuses(searchParameters,statuses);

			for (final OrderModel orderModel : orderResults.getResults())
			{
				final OrderData orderData = new OrderData();
				getOrderConverter().convert(orderModel, orderData);
				orderDatas.add(orderData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertPageData(orderResults, getOrderHistoryConverter());
	}
	
	@Override
	public SearchPageData<B2BOrderApprovalData> getPagedOrdersForApproval(final WorkflowActionType[] actionTypes,
			final PageableData pageableData,NeorisOrderApprovalSearchParameters searchParameters)
	{

		final SearchPageData<WorkflowActionModel> actions = neorisPagedB2BWorkflowActionDao.findPagedWorkflowActionsByUserAndActionTypes(getUserService().getCurrentUser(), actionTypes, pageableData,searchParameters);
		return convertPageData(actions, b2bOrderApprovalDataConverter);
	}
	
	@Override
	public B2BOrderApprovalData setOrderApprovalDecision(final B2BOrderApprovalData b2bOrderApprovalData)
	{
		// I'm assumming 
		UserModel previousUser = getUserService().getCurrentUser();
		final WorkflowActionModel workflowActionModel = getB2bWorkflowIntegrationService().getActionForCode(
				b2bOrderApprovalData.getWorkflowActionModelCode());
		addCommentToWorkflowAction(workflowActionModel, b2bOrderApprovalData.getApprovalComments());
		getB2bWorkflowIntegrationService().decideAction(workflowActionModel,
				B2BWorkflowIntegrationService.DECISIONCODES.valueOf(b2bOrderApprovalData.getSelectedDecision().toUpperCase()).name());
		CartModel cartModel = cartService.getSessionCart();
		cartModel.setUser(previousUser);
		modelService.save(cartModel);
		return getB2bOrderApprovalDataConverter().convert(workflowActionModel);
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.orders.NeorisB2BOrderFacade#getPagedQuotesWithStatusesByB2BUnits(java.util.List, java.lang.String, java.util.List)
	 */
	@Override
	public SearchPageData<OrderHistoryData> getPagedQuotesWithStatusesByB2BUnits(
			List<String> b2bUnits, List<String> statuses) throws Exception {
		
		String store = baseStoreService.getCurrentBaseStore().getPk().toString();
		
		SearchPageData<OrderModel> orderResults = null;

		final List<OrderData> orderDatas = new ArrayList<OrderData>();
		try
		{
			orderResults = neorisB2BOrderService.getPagedQuotesWithStatusesByB2BUnits(b2bUnits, store, statuses);

			for (final OrderModel orderModel : orderResults.getResults())
			{
				final OrderData orderData = new OrderData();
				getOrderConverter().convert(orderModel, orderData);
				orderDatas.add(orderData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertPageData(orderResults, getOrderHistoryConverter());
	}

	@Override
	public String cloneAndSetNewOrderFromApprovedQuote(String orderCode, String comment, String shippingInstructions) throws Exception
	{
		OrderModel quote = getB2bOrderService().getOrderForCode(orderCode);
		
		this.setB2BComment(comment, quote);
		
		ComposedTypeModel orderComposedTypeModel = typeService.getComposedTypeForClass(OrderModel.class);
		ComposedTypeModel entryComposedTypeModel = typeService.getComposedTypeForClass(OrderEntryModel.class);
		
		OrderModel newOrder = getB2bOrderService().clone(orderComposedTypeModel, entryComposedTypeModel, quote, null);

		// setting shipping instructions to the order
		newOrder.setShippingInstructions(shippingInstructions);
		
		//Se comento la segunda linea
		neorisSapOrderCreator.createSapOrderByQuote(newOrder,quote);
//		getB2bQuoteOrderService().placeQuoteOrder(newOrder);
		
		quote.setFinalOrder(newOrder);
			
		newOrder.setOriginalQuote(quote);
			
		quote.setStatus(OrderStatus.USED_QUOTE);
			
		getModelService().save(quote);
		
		//NEORIS_CHANGE
		//update the Requested Delivery Date if necessary
		/*Integer daysAdded = baseStoreService.getCurrentBaseStore().getDaysAddedDeliveryDate();

		if (daysAdded != null && daysAdded.equals(""))
		{
			daysAdded = 0;
		}

		final Date quoteDate = quote.getRequestedDeliveryDate();
		Date now = new Date();

		//get the current day and add days
		final Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, daysAdded);
		now = c.getTime();

		//get the quote time to compare versus current
		final Calendar cQuote = Calendar.getInstance();
		cQuote.setTime(quoteDate);

		//now is greater than quote time
		if (c.compareTo(cQuote) > 0)
		{
			cQuote.setTime(now);
			newOrder.setRequestedDeliveryDate(now);			
		}*/
		
		newOrder.setStatus(OrderStatus.ON_VALIDATION);
		modelService.save(newOrder);
		
		return newOrder.getCode();
	}
}
