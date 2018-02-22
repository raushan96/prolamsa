/**
 * 
 */
package mx.neoris.core.jobs;

import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.basecommerce.enums.OrderEntryStatus;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.util.Date;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author fdeutsch
 * @modified Christian Loredo
 * 
 */
public class NeorisOrderStatusUpdaterJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(NeorisOrderStatusUpdaterJob.class);

	private B2BOrderService b2bOrderService;

	private String baseStoreCode;

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Override
	public PerformResult perform(final CronJobModel paramT)
	{

		LOG.info("OrderStatusUpdaterJob Start: " + new Date());

		try
		{
			LOG.info("SAP CONNECTION: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();
			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.ORDER_STATUS.ORDER_STATUS, sapDest);

			LOG.info("SAP CONNECTION: Successfull");

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.ORDER_STATUS.BASE_STORE, getBaseStoreCode());

			//Se agrega para pruebas

			final JCoTable inputTable1 = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ORDER_STATUS.TI_PEDIDO);

			inputTable1.appendRow();
			inputTable1.setValue(SAPConstants.RFC.ORDER_STATUS.PEDIDO, SAPUtils.rellenaZeros("30022655", 10));
			//inputTable1.setValue(SAPConstants.RFC.ORDER_STATUS.PEDIDO, SAPUtils.rellenaZeros("30022572", 10));

			System.out.println("BASESTORE " + getBaseStoreCode());
			System.out.println("PEDIDO " + SAPUtils.rellenaZeros("30022655", 10));
			//System.out.println("PEDIDO " + SAPUtils.rellenaZeros("30022572", 10));


			//execute
			sapConnection.executeFunction(sapFunc, sapDest);

			final JCoTable resultTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ORDER_STATUS.TO_STATUS_PEDIDO);


			for (int i = 0; i < resultTable.getNumRows(); i++)
			{
				resultTable.setRow(i);

				final String pedido = resultTable.getString(SAPConstants.RFC.ORDER_STATUS.PEDIDO);
				final Integer partida = resultTable.getInt(SAPConstants.RFC.ORDER_STATUS.PARTIDA);
				final String statusLineaEng = resultTable.getString(SAPConstants.RFC.ORDER_STATUS.STATUS_DETAIL_ENG);
				final String statusLineaEsp = resultTable.getString(SAPConstants.RFC.ORDER_STATUS.STATUS_DETAIL_ESP);
				final String statusHeaderEng = resultTable.getString(SAPConstants.RFC.ORDER_STATUS.STATUS_HEAD_ENG);
				final String statusHeaderEsp = resultTable.getString(SAPConstants.RFC.ORDER_STATUS.STATUS_HEAD_ESP);
				String tipoDocumento = "";

				if ((partida % 10) != 0)
				{
					System.out.println("La partida " + partida + " del pedido " + pedido + " no es multiplo de 10");
					continue;
				}

				final Integer partidaConvert = (partida / 10) - 1;

				if (resultTable.getString(SAPConstants.RFC.ORDER_STATUS.TIPO_DOCUMENTO) != "C")
				{
					tipoDocumento = "Q";
				}
				else
				{
					tipoDocumento = "S";
				}

				//TODO Update status Header & Detail

				/*
				 * *********************************
				 * AL iniciar este desarrollo darle una checada al DefaultNeorisOrderUpdater
				 * *********************************
				 */

				final OrderModel orderModel = b2bOrderService.getOrderForCode(pedido);

				if (orderModel == null)
				{
					System.out.println("Unknown order id: " + pedido);
					continue;
				}
				else
				{
					orderModel.setStatus(this.getConvertedOrderStatus(statusHeaderEng, tipoDocumento));
					modelService.save(orderModel);

					final OrderEntryModel orderEntry = b2bOrderService.getEntryForNumber(orderModel, partidaConvert);
					orderEntry.setStatus(this.getConvertedOrderStatusEntry(statusLineaEng));
					modelService.save(orderEntry);

					//+orderModel.setStatus(OrderStatus.PENDING_APPROVAL);

					//+if (statusHeaderEng.equalsIgnoreCase("PROCESSING"))
					//+{
					//+	orderModel.setStatus(OrderStatus.APPROVED);
					//+}
					//++modelService.save(orderModel);

					//+final OrderEntryModel orderEntry = b2bOrderService.getEntryForNumber(orderModel, partidaConvert);
					//+orderEntry.setStatus(OrderEntryStatus.OPEN);
					//++modelService.save(orderEntry);
				}

			}
		}
		catch (final InterruptedException e)
		{
			LOG.error("Error while get info. ZHSD_ORDER_STATUS", e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}
		catch (final Exception ex)
		{
			LOG.error("Error while get info. ZHSD_ORDER_STATUS", ex);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}

		LOG.info("OrderStatusUpdaterJob End: " + new Date());


		/*
		 * //TODO remove, emulate time spent in updating orders try { LOG.info("Context: " + getBaseStoreCode());
		 * 
		 * Thread.sleep(2000); } catch (final InterruptedException e) { }
		 */


		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public OrderStatus getConvertedOrderStatus(final String statusProlamsa, final String tipoDocumento)
	{
		//String currentStatus = OrderStatus.valueOf(statusProlamsa.toUpperCase());
		OrderStatus response;

		if (tipoDocumento.equalsIgnoreCase("Q"))
		{
			switch (statusProlamsa.toUpperCase())
			{
				case "PENDING":
					response = OrderStatus.PENDING_QUOTE;
					break;
				case "APPROVED":
					response = OrderStatus.APPROVED_QUOTE;
					break;
				case "REJECTED":
					response = OrderStatus.REJECTED_QUOTE;
					break;
				case "ACCEPTED":
					response = OrderStatus.USED_QUOTE;
					break;
				case "OVERDUE":
					response = OrderStatus.EXPIRED_QUOTE;
					break;
				default:
					response = OrderStatus.PENDING_QUOTE;
					break;
			}

		}
		else
		{
			switch (statusProlamsa.toUpperCase())
			{
				case "PROCESSING":
					response = OrderStatus.APPROVED;
					break;
				case "COMPLETED":
					response = OrderStatus.COMPLETED;
					break;
				case "CANCELLED":
					response = OrderStatus.CANCELLED;
					break;
				case "ON HOLD":
					response = OrderStatus.ON_VALIDATION;
					break;
				default:
					response = OrderStatus.PENDING_APPROVAL;
					break;
			}
		}

		return response;

	}

	public OrderEntryStatus getConvertedOrderStatusEntry(final String statusEntryProlamsa)
	{
		OrderEntryStatus responseEntry;

		switch (statusEntryProlamsa.toUpperCase())
		{
			case "PROCESSING":
				responseEntry = OrderEntryStatus.IN_PROCESS;
				break;
			case "CREATED":
				responseEntry = OrderEntryStatus.OPEN;
				break;
			case "OPEN":
				responseEntry = OrderEntryStatus.OPEN;
				break;
			default:
				responseEntry = OrderEntryStatus.OPEN;
				break;
		}

		return responseEntry;

	}

	/**
	 * @return the baseStoreCode
	 */
	public String getBaseStoreCode()
	{
		return baseStoreCode;
	}

	/**
	 * @param baseStoreCode
	 *           the baseStoreCode to set
	 */
	public void setBaseStoreCode(final String baseStoreCode)
	{
		this.baseStoreCode = baseStoreCode;
	}

	/**
	 * @return the b2bOrderService
	 */
	public B2BOrderService getB2bOrderService()
	{
		return b2bOrderService;
	}

	/**
	 * @param b2bOrderService
	 *           the b2bOrderService to set
	 */
	public void setB2bOrderService(final B2BOrderService b2bOrderService)
	{
		this.b2bOrderService = b2bOrderService;
	}
}
