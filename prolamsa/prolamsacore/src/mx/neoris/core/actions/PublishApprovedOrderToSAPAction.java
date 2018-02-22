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

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.store.BaseStoreModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.orders.NeorisHSSOrderSplitter;
import mx.neoris.core.orders.NeorisSapOrderCreator;
import mx.neoris.core.services.dao.impl.DefaultNeorisUserDao;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;

import org.apache.log4j.Logger;



/**
 * This action implements payment authorization using {@link CreditCardPaymentInfoModel}. Any other payment model could
 * be implemented here, or in a separate action, if the process flow differs.
 */
public class PublishApprovedOrderToSAPAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(PublishApprovedOrderToSAPAction.class);

	@Resource(name = "neorisSapOrderCreator")
	private NeorisSapOrderCreator neorisSapOrdersCreator;

	@Resource(name = "neorisHSSOrderSplitter")
	private NeorisHSSOrderSplitter neorisHSSOrderSplitter;

	@Resource(name = "neorisUserDao")
	private DefaultNeorisUserDao neorisUserDao;

	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	@Override
	public Transition executeAction(final OrderProcessModel process)
	{
		final OrderModel orderModel = process.getOrder();

		boolean transitionOK = false;

		if (orderModel == null)
		{
			return Transition.NOK;
		}

		try
		{
			neorisSapOrdersCreator.createSapOrderFor(orderModel);
			transitionOK = true;
		}
		catch (final Exception e)
		{
			LOG.info("Catch to create order [" + orderModel.getCode() + "]. Store [" + orderModel.getStore().getUid()
					+ "] ... Preparing to send notification");

			orderModel.setStatus(OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID);
			orderModel.setSapExceptionMessage(e.getMessage());
			modelService.save(orderModel);
			
			/////////////////////////HERE
			final String eol = "<br>";
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//Set New Subject
			subjectData.append(orderModel.getStore().getName() + " | ");
			subjectData.append("Error create order/quote | ");
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//Set New Body
			if (orderModel.getStore().getUid().equalsIgnoreCase("1000") || orderModel.getStore().getUid().equalsIgnoreCase("5000"))
			{
				bodyData.append("Buen día." + eol + eol);
				bodyData.append("Se tuvo problemas al momento de crear el pedido del cliente " + orderModel.getUnit().getUid() + "."
						+ eol);
				bodyData.append("Favor de contactar a su asesor de ventas." + eol + eol);
				bodyData.append("Saludos!" + eol);
			}
			else
			{
				bodyData.append("Good day." + eol + eol);
				bodyData.append("There was a problem creating the order client " + orderModel.getUnit().getUid() + "." + eol);
				bodyData.append("Please contact you Sales Representative." + eol + eol);
				bodyData.append("Regards!" + eol);
			}

			if (!orderModel.getStore().getSendNotificationToUserDocument())
			{
				bodyData.append("<b>Description:</b>" + eol);
				bodyData.append(eol + e.getStackTrace().toString());
			}

			B2BCustomerModel salesRep = new B2BCustomerModel();
			B2BCustomerModel backoffice = new B2BCustomerModel();
			String salesRepS = "";
			String backofficeS = "";
			String userCreator = "";

			if (orderModel.getStore().getSendNotificationToUserDocument())
			{
				userCreator = orderModel.getUser().getUid();
				LOG.info("userCreator: " + userCreator);
			}

			if (orderModel.getStore().getSendNotificationToProlamsaInternal())
			{
				try
				{
					salesRep = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(this.getSalesRepFor(orderModel),
							orderModel.getStore());
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
					backoffice = neorisUserDao.getB2bCustomerModelByBackofficeAndStore(this.getBackOfficeAccountFor(orderModel),
							orderModel.getStore());
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

			if (orderModel.getStore().getSendNotificationToUserDocument()
					|| orderModel.getStore().getSendNotificationToProlamsaInternal())
			{
				neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString(), salesRepS, backofficeS,
						userCreator);
			}

			/////////////////////////END HERE

			LOG.error("Error while publishing to SAP order: " + orderModel.getCode() + " error message"
					+ e.getStackTrace().toString());
		}

		if (orderModel.getHssOrder() != null)
		{
			final OrderModel hssOrderModel = orderModel.getHssOrder();

			try
			{
				neorisSapOrdersCreator.createSapOrderFor(hssOrderModel);
				transitionOK = true;
			}
			catch (final Exception e)
			{
				LOG.info("Catch to create HSS Order [" + hssOrderModel.getCode() + "]. Store [" + hssOrderModel.getStore().getUid()
						+ "] ... Preparing to send notification");

				hssOrderModel.setStatus(OrderStatus.ORDER_APPROVED_AND_PENDING_SAP_ID);
				hssOrderModel.setSapExceptionMessage(e.getMessage());
				modelService.save(hssOrderModel);
				
				/////////////////////////HERE
				final String eol = "<br>";
				final StringBuilder subjectData = new StringBuilder();
				final StringBuilder bodyData = new StringBuilder();

				//Set New Subject
				subjectData.append(hssOrderModel.getStore().getName() + " | ");
				subjectData.append("Error create order/quote | ");
				final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				final Date date = new Date();

				subjectData.append(dateFormat.format(date));

				//Set New Body
				if (hssOrderModel.getStore().getUid().equalsIgnoreCase("1000")
						|| hssOrderModel.getStore().getUid().equalsIgnoreCase("5000"))
				{
					bodyData.append("Buen día." + eol + eol);
					bodyData.append("Se tuvo problemas al momento de crear el pedido del cliente " + hssOrderModel.getUnit().getUid()
							+ "." + eol);
					bodyData.append("Favor de contactar a su asesor de ventas." + eol + eol);
					bodyData.append("Saludos!" + eol);
				}
				else
				{
					bodyData.append("Good day." + eol + eol);
					bodyData.append("There was a problem creating the order client " + hssOrderModel.getUnit().getUid() + "." + eol);
					bodyData.append("Please contact you Sales Representative." + eol + eol);
					bodyData.append("Regards!" + eol);
				}

				if (!hssOrderModel.getStore().getSendNotificationToUserDocument())
				{
					bodyData.append("<b>Description:</b>" + eol);
					bodyData.append(eol + e.getStackTrace().toString());
				}


				B2BCustomerModel salesRep = new B2BCustomerModel();
				B2BCustomerModel backoffice = new B2BCustomerModel();
				String salesRepS = "";
				String backofficeS = "";
				String userCreator = "";

				if (hssOrderModel.getStore().getSendNotificationToUserDocument())
				{
					userCreator = hssOrderModel.getUser().getUid();
					LOG.info("userCreator: " + userCreator);
				}

				if (hssOrderModel.getStore().getSendNotificationToProlamsaInternal())
				{
					try
					{
						salesRep = neorisUserDao.getB2bCustomerModelBySalesRepAndStore(this.getSalesRepFor(hssOrderModel),
								hssOrderModel.getStore());
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
						backoffice = neorisUserDao.getB2bCustomerModelByBackofficeAndStore(this.getBackOfficeAccountFor(hssOrderModel),
								hssOrderModel.getStore());
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

				if (hssOrderModel.getStore().getSendNotificationToUserDocument()
						|| hssOrderModel.getStore().getSendNotificationToProlamsaInternal())
				{
					neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString(), salesRepS, backofficeS,
							userCreator);
				}

				/////////////////////////END HERE


				LOG.error("Error while publishing to SAP  HSS order: " + hssOrderModel.getCode() + " error message" + e.getMessage());

			}
		}

		return transitionOK ? Transition.OK : Transition.NOK;
	}
	
	/////////////////////////HERE
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

	/////////////////////////END HERE
}
