package mx.neoris.core.services.sap.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.services.NeorisCreditScoreCard;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


/**
 * @author lacantu
 * 
 */
public class DefaultSAPNeorisCreditScoreCard implements NeorisCreditScoreCard
{

	public final static String B2BUNIT_SLOT = "rootunit";
	public final static String USER_SLOT = "user";
	public final static String PRODUCTUNIT_SLOT = "productUnit";
	public final static String DIST_CHANNEL_SLOT = "10";

	@Resource(name = "SAPConnectionManager")
	SAPConnectionManager sapConnection;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name = "b2bUnitService")
	B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;


	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisCreditScoreCard.class);


	@Override
	public void calculateCreditScoreCard(final AbstractOrderData orderData) throws Exception
	{
		final UserModel currentUser = (UserModel) sessionService.getAttribute(USER_SLOT);

		final String salesOrganization = baseStoreService.getCurrentBaseStore().getUid();

		final B2BUnitModel b2bUnit = (B2BUnitModel) sessionService.getAttribute(B2BUNIT_SLOT);
		final String buildTo = b2bUnit.getUid();

		final StringBuilder emailDesc = new StringBuilder();
		final String eol = "<br>";
		final Double quantity = 0.00d;

		final String distributionChannel = DIST_CHANNEL_SLOT;

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

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION,
					custDest);

			LOG.info("SAP CONNECTION: Successfull");

			// input header structure		
			final JCoStructure orderHeader = sapFunc.getImportParameterList().getStructure(
					SAPConstants.RFC.CREDIT_SCORE_CARD.INPUT_STRUCTURE);

			LOG.info("RFC :" + SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION);
			LOG.info("--INPUT PARAMETERS--");

			orderHeader.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.BASE_STORE, salesOrganization);
			LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.BASE_STORE + ": " + salesOrganization);
			emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.BASE_STORE + ":</b> " + salesOrganization + eol);

			orderHeader.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.DIST_CHANNEL, distributionChannel);
			LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.DIST_CHANNEL + ": " + distributionChannel);
			emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.DIST_CHANNEL + ":</b>" + distributionChannel + eol);

			orderHeader.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.CUSTOMER1, buildTo);
			LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.CUSTOMER1 + ": " + buildTo);
			emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.CUSTOMER1 + ":</b> " + buildTo + eol);

			orderHeader.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.CUSTOMER2, buildTo);

			orderHeader.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.UOM, "PC");
			LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.UOM + ": PC");
			emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.UOM + ":</b> PC " + eol);


			// input entry table
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.CREDIT_SCORE_CARD.INPUT_TABLE2);

			for (final OrderEntryData entry : orderData.getEntries())
			{

				inputTable.appendRow();

				emailDesc.append(eol + "<b>PRODUCT DETAIL:</b> " + eol);

				inputTable.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.PRODUCT_ID, entry.getProduct().getBaseCode());
				LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.PRODUCT_ID + ": " + entry.getProduct().getBaseCode());
				emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.PRODUCT_ID + ":</b> " + entry.getProduct().getBaseCode()
						+ eol);


				inputTable.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.QUANTITY,
						entry.getProduct().getPiecesPerBundle() * entry.getQuantity());
				LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.QUANTITY + ": " + entry.getProduct().getPiecesPerBundle()
						* entry.getQuantity());
				emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.QUANTITY + ":</b> "
						+ entry.getProduct().getPiecesPerBundle() * entry.getQuantity() + eol);

				inputTable.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.UNIT_BASE, "PC");
				LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.UNIT_BASE + ": PC");
				emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.UNIT_BASE + ":</b> PC " + eol);

				inputTable.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.LOCATION, entry.getProduct().getLocation().getCode()
						.substring(1));
				LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.LOCATION + ": " + entry.getProduct().getLocation().getCode().substring(1));
				emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.LOCATION + ":</b> "
						+ entry.getProduct().getLocation().getCode().substring(1) + eol);

				inputTable.setValue(SAPConstants.RFC.CREDIT_SCORE_CARD.LINE_ITEM, (entry.getEntryNumber() + 1) * 10);
				LOG.info(SAPConstants.RFC.CREDIT_SCORE_CARD.LINE_ITEM + ": " + (entry.getEntryNumber() + 1) * 10);
				emailDesc.append("<b>" + SAPConstants.RFC.CREDIT_SCORE_CARD.LINE_ITEM + ":</b> " + (entry.getEntryNumber() + 1) * 10);

			}


			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			//execute
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION,
					currentUser.getUid(), true, sessionActual));

			sapConnection.executeFunction(sapFunc, custDest);

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION,
					currentUser.getUid(), false, sessionActual));

			// output table
			final JCoTable outputTable = sapFunc.getTableParameterList().getTable(
					SAPConstants.RFC.CREDIT_SCORE_CARD.OUTPUT_TABLE_SCORE_CARD);

			if (outputTable.getNumRows() > 0)
			{

				final Double creditScoreCard = Double.parseDouble(outputTable
						.getString(SAPConstants.RFC.CREDIT_SCORE_CARD.SIMUL_PERCENTAGE));

				final String semaphoreCredit = outputTable.getString(SAPConstants.RFC.CREDIT_SCORE_CARD.SEMAPHORE);

				orderData.setCreditScoreCard(creditScoreCard);
				orderData.setSemaphoreCredit(semaphoreCredit);

			}
			else
			{

				LOG.error("Not found result data for Credit Score Card in RFC: ZHSD_VALORIZATION");

				final StringBuilder subjectData = new StringBuilder();
				final StringBuilder bodyData = new StringBuilder();

				//set the subject
				subjectData.append(baseStoreService.getCurrentBaseStore().getName() + " | ");
				subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CALCULATE_PRICE.SUBJECT + " ");
				subjectData.append(SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION + " | ");
				subjectData.append(currentUser.getName() + " | ");

				final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				final Date date = new Date();

				subjectData.append(dateFormat.format(date));

				//set the body
				bodyData.append("<b>Store name:</b> " + baseStoreService.getCurrentBaseStore().getName() + eol + eol);
				bodyData.append("<b>User:</b> " + currentUser.getName() + eol + eol);
				bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
				bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION + eol + eol);
				bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
				bodyData.append("<b>Description:</b> RFC Credit Score Card not found data" + eol + eol);

				neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());
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
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.CREDIT_SCORE_CARD.CREDIT_SCORE_CARD_FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Credit Score Card" + eol);
			bodyData.append(eol + ex.getMessage());

			neorisEmailNotifierSAP.sendEmailMessageWith(subjectData.toString(), bodyData.toString());
		}

	}









}
