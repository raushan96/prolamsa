/**
 * 
 */
package mx.neoris.core.services.sap.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.data.AlertConfigurationData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.AlertsConfigurationParameters;
import mx.neoris.core.services.NeorisAlertConfigurationService;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import mx.neoris.core.util.SAPUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * @author fdeutsch
 * @modified Christian.Loredo
 * 
 */
public class DefaultSAPAlertConfigurationsService implements NeorisAlertConfigurationService
{
	private static final Logger LOG = Logger.getLogger(DefaultSAPAlertConfigurationsService.class);

	public final static String B2BUNIT_SLOT = "rootunit";
	public final static String USER_SLOT = "user";
	public final static String PRODUCTUNIT_SLOT = "productUnit";
	public final static String DIST_CHANNEL_SLOT = "10";

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisAlertConfigurationService#publisAlertConfigurations(mx.neoris.core.services.
	 * AlertsConfigurationParameters)
	 */
	@Override
	public boolean publisAlertConfigurations(final AlertsConfigurationParameters parameters)
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute(USER_SLOT);

		final B2BUnitModel b2bUnit = (B2BUnitModel) sessionService.getAttribute(B2BUNIT_SLOT);

		final StringBuilder emailDesc = new StringBuilder();
		final String eol = "<br>";


		try
		{
			LOG.info("SAP CONNECTION: Initialize");

			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX 
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.ALERTS.FUNCTION, custDest);

			LOG.info("SAP CONNECTION: Successfull");

			// input header structure		
			final JCoTable alertsTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.ALERTS.INPUT_TABLE);

			LOG.info("RFC :" + SAPConstants.RFC.ALERTS.FUNCTION);
			LOG.info("--INPUT PARAMETERS--");

			for (final AlertConfigurationData eachAlertConfData : parameters.getAlertConfigurationDatas())
			{
				alertsTable.appendRow();

				final String alertCode = eachAlertConfData.getAlertCode();
				if (StringUtils.isBlank(alertCode))
				{
					throw new Exception("alert code cannot be blank");
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_ALERTCODE, alertCode);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_ALERTCODE + ": " + alertCode);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_ALERTCODE + ":</b> " + alertCode + eol);

				final String b2bUnitID = parameters.getB2BUnitId();
				if (StringUtils.isBlank(b2bUnitID))
				{
					throw new Exception("b2bUnitID code cannot be blank");
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_B2BUNIT, b2bUnitID);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_B2BUNIT + ": " + b2bUnitID);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_B2BUNIT + ":</b> " + b2bUnitID + eol);

				final String baseStore = parameters.getBaseStoreModel().getUid();
				if (StringUtils.isBlank(baseStore))
				{
					throw new Exception("baseStore code cannot be blank");
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_BASE_STORE, baseStore);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_BASE_STORE + ": " + baseStore);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_BASE_STORE + ":</b> " + baseStore + eol);

				final String b2bCustomer = parameters.getCustomerModel().getUid();
				if (StringUtils.isBlank(b2bCustomer))
				{
					throw new Exception("b2bCustomer code cannot be blank");
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_B2BCUSTOMER, b2bCustomer);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_B2BCUSTOMER + ": " + b2bCustomer);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_B2BCUSTOMER + ":</b> " + b2bCustomer + eol);

				final Boolean notifyBol = eachAlertConfData.getNotify();
				String notifyString = null;
				if (notifyBol == null || notifyBol == false)
				{
					notifyString = "";
				}
				else
				{
					notifyString = "X";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_NOTIFY, notifyString);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_NOTIFY + ": " + notifyString);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_NOTIFY + ":</b> " + notifyString + eol);


				String periodicity = eachAlertConfData.getPeriodicity();
				if (StringUtils.isBlank(periodicity))
				{
					periodicity = "";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_PERIODICITY, periodicity);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_PERIODICITY + ": " + periodicity);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_PERIODICITY + ":</b> " + periodicity + eol);

				String time = eachAlertConfData.getTime();
				if (StringUtils.isBlank(time))
				{
					time = "";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_TIME, time);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_TIME + ": " + time);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_TIME + ":</b> " + time + eol);

				String daysNOT = eachAlertConfData.getDaysOfWeek();
				if (StringUtils.isBlank(daysNOT))
				{
					daysNOT = "";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_DAYSNOT, daysNOT);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_DAYSNOT + ": " + daysNOT);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_DAYSNOT + ":</b> " + daysNOT + eol);

				String daysOfMonth = eachAlertConfData.getDayOfMonth();
				if (StringUtils.isBlank(daysOfMonth))
				{
					daysOfMonth = "";
				}
				else
				{
					//Gerardo asked to send days with "01, 02, 03..."
					if (daysOfMonth.length() == 1)
					{
						daysOfMonth = "0" + daysOfMonth;
					}
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_DAYSOFMONTH, daysOfMonth);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_DAYSOFMONTH + ": " + daysOfMonth);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_DAYSOFMONTH + ":</b> " + daysOfMonth + eol);

				String productOptions = eachAlertConfData.getProductOptions();
				if (StringUtils.isBlank(productOptions))
				{
					productOptions = "";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_PRODUCTOPTIONS, productOptions);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_PRODUCTOPTIONS + ": " + productOptions);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_PRODUCTOPTIONS + ":</b> " + productOptions + eol);



				final Boolean includeMTRBol = eachAlertConfData.getIncludeMTR();
				String includeMTRSting = null;
				if (includeMTRBol == null || includeMTRBol == false)
				{
					includeMTRSting = "";
				}
				else
				{
					includeMTRSting = "X";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_INCLUDEMTR, includeMTRSting);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_INCLUDEMTR + ": " + includeMTRSting);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_INCLUDEMTR + ":</b> " + includeMTRSting + eol);

				String ccEmail = eachAlertConfData.getCcEmail();
				if (StringUtils.isBlank(ccEmail))
				{
					ccEmail = "";
				}
				alertsTable.setValue(SAPConstants.RFC.ALERTS.INPUT_CCEMAIL, ccEmail);
				LOG.info(SAPConstants.RFC.ALERTS.INPUT_CCEMAIL + ": " + ccEmail);
				emailDesc.append("<b>" + SAPConstants.RFC.ALERTS.INPUT_CCEMAIL + ":</b> " + ccEmail + eol);
			}








			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			//execute
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ALERTS.FUNCTION, currentUser.getUid(), true,
					sessionActual));

			sapConnection.executeFunction(sapFunc, custDest);

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.ALERTS.FUNCTION, currentUser.getUid(), false,
					sessionActual));

			final String message = sapFunc.getExportParameterList().getString("MESSAGE");

			if (StringUtils.isNotBlank(message))
			{
				LOG.info("The alert configuration has been saved.");
				LOG.info("Message returned by SAP: " + message);
				return true;
			}
			else
			{
				LOG.info("The alert configuration has not been saved.");
				return false;
			}



		}
		catch (final Exception ex)
		{
			LOG.error("Error while executing RFC: ZHSD_VALORIZATION", ex);
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(baseStoreService.getCurrentBaseStore().getName() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CALCULATE_PRICE.SUBJECT + " | ");
			subjectData.append("RFC error: " + SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION + " | ");
			subjectData.append(currentUser.getName() + " | ");

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Date date = new Date();

			subjectData.append(dateFormat.format(date));

			//set the body
			bodyData.append("<b>Store name:</b> " + baseStoreService.getCurrentBaseStore().getName() + eol + eol);
			bodyData.append("<b>User:</b> " + currentUser.getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.ALERTS.FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Save Alerts" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());
			return false;
		}

	}
}
