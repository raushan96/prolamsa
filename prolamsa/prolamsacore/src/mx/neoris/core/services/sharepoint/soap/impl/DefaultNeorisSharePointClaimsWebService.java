/**
 * 
 */
package mx.neoris.core.services.sharepoint.soap.impl;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import mx.neoris.core.model.IncidentLineModel;
import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointClaimsWebService;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointConstants;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointListWebServiceManager;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.microsoft.schemas.sharepoint.soap.UpdateListItems.Updates;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsResponse.UpdateListItemsResult;


/**
 * @author hector.castaneda
 * 
 */
public class DefaultNeorisSharePointClaimsWebService implements NeorisSharePointClaimsWebService
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisSharePointClaimsWebService.class);

	private static final String PROP_CLAIM_LIST_NAME_KEY = "prolamsa.sharepoint.claims.list.name";
	private static final String PROP_NEW_CLAIM_CAML_KEY = "prolamsa.sharepoint.claims.newIncidentCAML";
	private static final String PROP_NEW_CLAIM_MATERIAL_ENTRY_CAML_KEY = "prolamsa.sharepoint.claims.newIncidentMaterialEntryCAML";

	private static final String PROP_LISTS_WSDL_KEY = "prolamsa.sharepoint.claims.lists.wsdl";
	private static final String PROP_LISTS_ENDPOINT_KEY = "prolamsa.sharepoint.claims.lists.endpoint";

	private static final String NORMALIZE_ATTACHMENT_FILE_NAME_PREFIX = "attachment";

	private String newIncidentCAML;
	private String newIncidentMaterialEntryCAML;

	@Resource(name = "configurationService")
	public ConfigurationService configurationService;

	@Resource(name = "neorisSharePointListWebServiceManager")
	public NeorisSharePointListWebServiceManager neorisSharePointListWebServiceManager;

	@Resource
	private MediaService mediaService;

	public void init() throws Exception
	{
		newIncidentCAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this.getClass().getClassLoader()
				.getResource(configurationService.getConfiguration().getString(PROP_NEW_CLAIM_CAML_KEY)).toURI())));
		newIncidentMaterialEntryCAML = new String(neorisSharePointListWebServiceManager.readAll(new File(this.getClass()
				.getClassLoader()
				.getResource(configurationService.getConfiguration().getString(PROP_NEW_CLAIM_MATERIAL_ENTRY_CAML_KEY)).toURI())));
	}

	@Override
	public String addNewClaim(final AddIncidentParameters parameters, final IncidentModel incidentModel) throws Exception
	{
		final String incidentXMLString = createXMLStringForIncident(parameters, incidentModel);

		if (incidentXMLString == null)
		{
			throw new Exception("Error while publishing claim in Sharepoint");
		}

		final Node newItemNode = neorisSharePointListWebServiceManager.createSharePointCAMLNode(incidentXMLString);

		final Updates updates = new Updates();
		updates.getContent().add(newItemNode);

		final UpdateListItemsResult result = neorisSharePointListWebServiceManager.getListWebService(
				configurationService.getConfiguration().getString(PROP_LISTS_WSDL_KEY),
				configurationService.getConfiguration().getString(PROP_LISTS_ENDPOINT_KEY)).updateListItems(
				configurationService.getConfiguration().getString(PROP_CLAIM_LIST_NAME_KEY), updates);

		final Element element = (Element) result.getContent().get(0);
		final NodeList nl = element.getElementsByTagName("z:row");

		String claimId = null;

		// getting element id
		for (int i = 0; i < nl.getLength(); i++)
		{
			final Node node = nl.item(i);
			claimId = node.getAttributes().getNamedItem("ows_ID").getNodeValue();
		}

		// getting error
		if (claimId == null)
		{
			final NodeList errorCodeNodeList = element.getElementsByTagName("ErrorCode");
			final NodeList errorTextNodeList = element.getElementsByTagName("ErrorText");

			String errorCode = "";
			String errorMessage = "";

			if (errorCodeNodeList.getLength() > 0)
			{
				final Node errorCodeNode = errorCodeNodeList.item(0);
				if (errorCodeNode.getFirstChild() != null)
				{
					errorCode = "Error code:" + errorCodeNode.getFirstChild().getNodeValue();
					LOG.error(errorCode);
				}
			}

			if (errorTextNodeList.getLength() > 0)
			{
				final Node errorTextNode = errorTextNodeList.item(0);
				if (errorTextNode.getFirstChild() != null)
				{
					errorMessage = "Error message:" + errorTextNode.getFirstChild().getNodeValue();
					LOG.error(errorMessage);
				}
			}

			throw new Exception("Error while publishing claim: " + incidentModel.getCode() + " to Sharepoint due to: " + errorCode
					+ " " + errorMessage);
		}

		return claimId;
	}

	@Override
	public void addAttachmentToClaim(final String claimId, final MultipartFile file, final IncidentModel incidentModel,
			final int position) throws Exception
	{
		try
		{
			final String fileNameNormalized = (NORMALIZE_ATTACHMENT_FILE_NAME_PREFIX + position + "." + FilenameUtils
					.getExtension(file.getOriginalFilename())).toLowerCase();

			final String result = neorisSharePointListWebServiceManager.getListWebService(
					configurationService.getConfiguration().getString(PROP_LISTS_WSDL_KEY),
					configurationService.getConfiguration().getString(PROP_LISTS_ENDPOINT_KEY)).addAttachment(
					configurationService.getConfiguration().getString(PROP_CLAIM_LIST_NAME_KEY), claimId, fileNameNormalized,
					file.getBytes());
			LOG.info("Incident attachment result: " + result);
		}
		catch (final Exception e)
		{
			throw new Exception("Error while publishing attachment: " + file.getOriginalFilename() + " for claim: "
					+ incidentModel.getCode() + " to Sharepoint due to: " + e.getMessage());
		}
	}

	protected String createXMLStringForIncident(final AddIncidentParameters parameters, final IncidentModel incidentModel)
	{
		String incidentXMLString = null;

		final String hid = incidentModel.getCode();
		final String cName = StringEscapeUtils.escapeXml(incidentModel.getContactName());
		final String customerName = StringEscapeUtils.escapeXml(incidentModel.getAccount().getLocName());
		final String defectoH = StringEscapeUtils.escapeXml(incidentModel.getType().getName());
		final String telephone = StringEscapeUtils.escapeXml(incidentModel.getPhone());
		final String email = StringEscapeUtils.escapeXml(incidentModel.getEmail());
		final String description = StringEscapeUtils.escapeXml(incidentModel.getDescriptionMax());
		final String invoice = incidentModel.getInvoiceNumber();

		String city = "";
		final String plantCode = incidentModel.getLocation().getCode();

		if ("_1100".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._1100;
		}
		else if ("_1200".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._1200;
		}
		else if ("_1300".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._1300;
		}
		else if ("_1500".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._1500;
		}
		else if ("_1400".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._1400;
		}
		else if ("_1600".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._1600;
		}
		else if ("_2200".equals(plantCode))
		{
			city = NeorisSharePointConstants.ClaimsIntegration.City._2200;
		}

		String location = incidentModel.getCountry();
		final String locationCode = incidentModel.getCountry();

		if ("MX".equals(locationCode))
		{
			location = NeorisSharePointConstants.ClaimsIntegration.Location.MX;
		}
		else if ("USA".equals(locationCode))
		{
			location = NeorisSharePointConstants.ClaimsIntegration.Location.USA;
		}

		String freight = "";
		final String freightCode = incidentModel.getIncoterm();

		if ("CIN".equals(freightCode))
		{
			freight = NeorisSharePointConstants.ClaimsIntegration.Incoterm.CIN;
		}
		else if ("CIP".equals(freightCode))
		{
			freight = NeorisSharePointConstants.ClaimsIntegration.Incoterm.CIP;
		}
		else if ("CPT".equals(freightCode))
		{
			freight = NeorisSharePointConstants.ClaimsIntegration.Incoterm.CPT;
		}
		else if ("EXW".equals(freightCode))
		{
			freight = NeorisSharePointConstants.ClaimsIntegration.Incoterm.EXW;
		}

		final String date = new SimpleDateFormat(NeorisSharePointConstants.DATE_FORMAT).format(incidentModel.getDate());
		final String status = incidentModel.getState().getName();

		final StringBuilder materials = new StringBuilder();
		for (final IncidentLineModel line : incidentModel.getIncidentLines())
		{
			final String lote = String.valueOf(line.getBatch());

			String materialNumber = "";
			String productDescription = "";

			if (line.getProduct() != null)
			{
				final ProlamsaProductModel product = (ProlamsaProductModel) line.getProduct();
				materialNumber = product.getBaseCode();
				productDescription = StringEscapeUtils.escapeXml(product.getName());
			}
			else
			{
				materialNumber = line.getProductBaseCode();
				productDescription = StringEscapeUtils.escapeXml(line.getProductDescription());
			}

			final String cantH = String.valueOf(line.getQuantity());
			final String pesoH = String.valueOf(line.getNetweight());
			final String pieces = String.valueOf(line.getQuantityToClaim());
			final String pesoR = String.valueOf(line.getQuantityToClaim() * line.getNetweight() / line.getQuantity());

			final String eachLineXMLString = String.format(newIncidentMaterialEntryCAML, lote, materialNumber, productDescription,
					cantH, pesoH, pieces, pesoR);
			materials.append(eachLineXMLString);
		}

		incidentXMLString = String.format(newIncidentCAML, date, customerName, description, hid, cName, defectoH, telephone, email,
				invoice, location, city, freight, status, materials.toString());

		return incidentXMLString;
	}

	@Override
	public void addAttachmentToClaimMedia(final String claimId, final MediaModel file, final IncidentModel incidentModel,
			final int position) throws Exception
	{
		try
		{
			final String fileNameNormalized = (NORMALIZE_ATTACHMENT_FILE_NAME_PREFIX + position + "." + FilenameUtils
					.getExtension(file.getRealFileName())).toLowerCase();

			final String result = neorisSharePointListWebServiceManager.getListWebService(
					configurationService.getConfiguration().getString(PROP_LISTS_WSDL_KEY),
					configurationService.getConfiguration().getString(PROP_LISTS_ENDPOINT_KEY)).addAttachment(
					configurationService.getConfiguration().getString(PROP_CLAIM_LIST_NAME_KEY), claimId, fileNameNormalized,
					mediaService.getDataFromMedia(file));
			LOG.info("Incident attachment result: " + result);
		}
		catch (final Exception e)
		{
			throw new Exception("Error while publishing attachment: " + file.getRealFileName() + " for claim: "
					+ incidentModel.getCode() + " to Sharepoint due to: " + e.getMessage());
		}
	}

}
