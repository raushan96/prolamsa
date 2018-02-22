/**
 * 
 */
package mx.neoris.core.sap.document;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import javax.annotation.Resource;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.document.NeorisDocumentDownloadUrlResolver;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.util.SAPUtils;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;



/**
 * @author lacantu
 * 
 */
public class DefaultSAPNeorisDocumentDownloadUrlResolver implements NeorisDocumentDownloadUrlResolver
{

	@Resource(name = "SAPConnectionManager")
	private SAPConnectionManager sapConnection;

	@Resource(name = "b2bUnitService")
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "sessionService")
	protected SessionService sessionService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	private String temporaryDirectory;

	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisDocumentDownloadUrlResolver.class);

	@Override
	public String urlDownloadDocumentFor(final String organization, final String documentType, final String billTo,
			final String documentNumber)
	{

		//SAP Implementation
		final UserModel currentUser = (UserModel) sessionService.getAttribute("user");

		LOG.info("SAP CONNECTION: Initialize");

		String filePath = "";
		try
		{
			final JCoDestination sapDest = sapConnection.getDestination();

			//Se customiza el lenguaje del Destination cuando el sitio es MX 
			final JCoCustomDestination custDest = sapDest.createCustomDestination();

			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000")
					|| baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}

			final JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.PDF.DOCUMENT_PDF, custDest);

			LOG.info("SAP CONNECTION: Successfull");
			// input table parameter 1.13 DOCUMENT SEARCH

			LOG.info("RFC: " + SAPConstants.RFC.PDF.DOCUMENT_PDF);

			//fill up the fields
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.CLIENTE, SAPUtils.getFormattedUID(billTo));
			LOG.info(SAPConstants.RFC.PDF.CLIENTE + ": " + SAPUtils.getFormattedUID(billTo));

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.DOCUMENTO, documentNumber);
			LOG.info(SAPConstants.RFC.PDF.DOCUMENTO + ": " + documentNumber);

			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.TIPO_DOCTO, documentType);
			LOG.info(SAPConstants.RFC.PDF.TIPO_DOCTO + ": " + documentType);


			temporaryDirectory = configurationService.getConfiguration().getString("sap.invoices.temporaryDirectory");
			sapFunc.getImportParameterList().setValue(SAPConstants.RFC.PDF.RUTA, temporaryDirectory);
			LOG.info(SAPConstants.RFC.PDF.RUTA + ": " + temporaryDirectory);

			final String sessionActual = sessionService.getCurrentSession().getSessionId();

			//execute
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PDF.DOCUMENT_PDF, currentUser.getUid(), true,
					sessionActual));
			sapConnection.executeFunction(sapFunc, custDest);

			// get result
			filePath = sapFunc.getExportParameterList().getString(SAPConstants.RFC.PDF.EXPORT_ARCHIVO);
			LOG.info(SAPConstants.RFC.PDF.EXPORT_ARCHIVO + ": " + filePath);

			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PDF.DOCUMENT_PDF, currentUser.getUid(), false,
					sessionActual));
		}
		catch (final Exception ex)
		{
			LOG.error("Error while connecting to ZHSD_DOCUMENT_PDF", ex);
		}

		return filePath;
	}

	/**
	 * @return the temporaryDirectory
	 */
	public String getTemporaryDirectory()
	{
		return temporaryDirectory;
	}

	/**
	 * @param temporaryDirectory
	 *           the temporaryDirectory to set
	 */
	public void setTemporaryDirectory(final String temporaryDirectory)
	{
		this.temporaryDirectory = temporaryDirectory;
	}



}
