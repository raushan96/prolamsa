/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.event.IncidentAddedEvent;
import mx.neoris.core.incident.IncidentLine;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.model.IncidentLineModel;
import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.services.IncidentSearchParameters;
import mx.neoris.core.services.NeorisIncidentService;
import mx.neoris.core.services.NeorisIncidentStateService;
import mx.neoris.core.services.NeorisIncidentTypeService;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.sharepoint.soap.NeorisSharePointClaimsWebService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author e-jecarrilloi
 * 
 */
public class DefaultNeorisIncidentService implements NeorisIncidentService
{

	private static final Logger LOG = Logger.getLogger(DefaultNeorisIncidentService.class);

	private static final String VALIDATING_STATE_CODE = "1";


	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Resource
	private UserService userService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Resource(name = "neorisIncidentTypeService")
	private NeorisIncidentTypeService neorisIncidentTypeService;

	@Resource(name = "neorisIncidentStateService")
	private NeorisIncidentStateService neorisIncidentStateService;

	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "eventService")
	protected EventService eventService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "neorisSharePointClaimsWebService")
	private NeorisSharePointClaimsWebService neorisSharePointClaimsWebService;

	private static final String PROLAMSA_MEDIA_CATALOG = "prolamsaMediaCatalog";
	private static final String ONLINE = "Online";

	public static final Integer MAX_STACK_TRACE_SIZE = 4096;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisIncidentService#getPagedInvoices(mx.neoris.core.services.IncidentSearchParameters)
	 */
	@Override
	public SearchPageData<IncidentModel> getPagedIncidents(final IncidentSearchParameters searchParameters) throws Exception
	{
		final String b2bUnit = searchParameters.getCustomer();
		final String incidentNumber = searchParameters.getNumber();
		final String type = searchParameters.getType();
		final String state = searchParameters.getState();
		final Date initialDate = searchParameters.getInitialDate();
		final Date finalDate = searchParameters.getFinalDate();
		final String user = searchParameters.getUser();

		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();


		final List<String> listB2BUnitCode = new ArrayList<String>();
		final StringBuilder query = new StringBuilder("SELECT {i.pk} FROM {Incident as i ");

		// JOINS Sections

		// customer filter
		final B2BCustomerModel customer = (B2BCustomerModel) userService.getUserForUID(user);
		if (StringUtils.isNotBlank(searchParameters.getCustomer()) && !searchParameters.getCustomer().equals("all"))
		{
			if (searchParameters.getCustomer().equalsIgnoreCase("all-favorites"))
			{
				final List<B2BUnitModel> allUnits = neorisService.getB2BUnitModelsForCustomerAndCurrentStore(customer);

				for (final B2BUnitModel eachB2BUnit : allUnits)
				{
					listB2BUnitCode.add(eachB2BUnit.getPk().getLongValueAsString());
				}
			}
			else
			{
				final B2BUnitModel unit = neorisService.getB2BUnitForUID(b2bUnit);

				if (unit != null)
				{
					listB2BUnitCode.add(unit.getPk().getLongValueAsString());
				}
			}

		}

		//JOINS
		if (listB2BUnitCode.size() > 0)
		{
			query.append(" JOIN B2BUnit as b  ON {i.account}={b.pk} ");
		}

		if (!"all".equalsIgnoreCase(type))
		{
			query.append(" JOIN IncidentType as it ON {i.type}={it.pk} ");
		}
		if (!"all".equalsIgnoreCase(state))
		{
			query.append(" JOIN IncidentState as is ON {i.state}={is.pk} ");
		}

		query.append("} WHERE 1=1 ");

		//Conditions
		if (listB2BUnitCode.size() > 0)
		{
			query.append("AND (");
			for (final String string : listB2BUnitCode)
			{
				query.append(" {b.pk} = '" + string + "' OR ");
			}
			query.delete(query.length() - 3, query.length());
			query.append(")");
		}


		if (StringUtils.isNotBlank(incidentNumber))
		{
			query.append(" AND {i.code} = " + incidentNumber + " ");
		}




		if (initialDate != null && finalDate != null)
		{
			//Range
			query.append(" AND {i.date} >= ?initialDate ");
			query.append(" AND {i.date} <= ?finalDate ");

			queryParams.put("initialDate", initialDate);

			// today    
			final Calendar date = new GregorianCalendar();
			date.setTime(finalDate);

			// reset hour, minutes, seconds and millis
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);

			// next day
			date.add(Calendar.DAY_OF_MONTH, 1);

			queryParams.put("finalDate", date.getTime());
		}
		else
		{
			if (initialDate != null)
			{
				query.append(" AND {i.date} >= ?initialDate ");
				queryParams.put("initialDate", initialDate);
			}
			if (finalDate != null)
			{

				// today    
				final Calendar date = new GregorianCalendar();
				date.setTime(finalDate);

				// reset hour, minutes, seconds and millis
				date.set(Calendar.HOUR_OF_DAY, 0);
				date.set(Calendar.MINUTE, 0);
				date.set(Calendar.SECOND, 0);
				date.set(Calendar.MILLISECOND, 0);

				// next day
				date.add(Calendar.DAY_OF_MONTH, 1);

				query.append(" AND {i.date} <= ?finalDate ");
				queryParams.put("finalDate", date.getTime());
			}

		}

		if (!"all".equalsIgnoreCase(type))
		{
			query.append(" AND {it.code} = ?typeCode ");
			queryParams.put("typeCode", type);
		}

		if (!"all".equalsIgnoreCase(state))
		{
			query.append(" AND {is.code} = ?stateCode ");
			queryParams.put("stateCode", state);
		}

		if (searchParameters.getBaseStore() != null)
		{
			query.append(" AND {i.baseStore} = '" + searchParameters.getBaseStore().getPk() + "'");
		}

		return pagedFlexibleSearchService.search(query.toString(), queryParams, searchParameters.getPageableData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisIncidentService#getIncident(java.lang.String)
	 */
	@Override
	public IncidentModel getIncident(final String code)
	{
		final String query = "SELECT {i.pk} FROM {Incident as i} WHERE {i.code} = ?code";

		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		queryParams.put("code", code);

		final SearchResult<IncidentModel> result = flexibleSearchService.search(query, queryParams);

		if (result != null && result.getTotalCount() > 0)
		{
			return result.getResult().get(0);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisIncidentService#addIncident(mx.neoris.core.services.AddIncidentParameters)
	 */
	@Override
	public IncidentModel addIncident(final AddIncidentParameters parameters)
	{
		final IncidentModel newIncidentModel = new IncidentModel();
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		final B2BUnitModel b2bUnitModel = neorisService.getB2BUnitForUID(parameters.getCustomer());

		final String invoice = parameters.getInvoice();
		final String phone = parameters.getPhone();
		final String email = parameters.getEmail();
		final String description = parameters.getDescription();
		final String contactName = parameters.getContactName();
		final String alternateContactName = parameters.getAlternateContactName();
		final String customerIncidentNumber = parameters.getCustomerIncidentNumber();
		final String typeCode = parameters.getType();
		final String shipTo = parameters.getShipTo();

		newIncidentModel.setCode(this.getIncidentCodeForCustomer(b2bUnitModel));
		newIncidentModel.setAccount(b2bUnitModel);
		newIncidentModel.setInvoiceNumber(invoice);
		newIncidentModel.setPhone(phone);
		newIncidentModel.setEmail(email);
		newIncidentModel.setDescriptionMax(description);
		newIncidentModel.setContactName(contactName);
		newIncidentModel.setAlternateContactName(alternateContactName);
		newIncidentModel.setCustomerCode(customerIncidentNumber);
		newIncidentModel.setType(neorisIncidentTypeService.getType(typeCode));
		newIncidentModel.setDate(new Date());
		newIncidentModel.setShipTo(shipTo);
		newIncidentModel.setState(neorisIncidentStateService.getState(VALIDATING_STATE_CODE));
		newIncidentModel.setBaseStore(parameters.getBaseStore());

		final ProductLocation productLocation = enumerationService.getEnumerationValue(ProductLocation.class,
				parameters.getProductLocation());

		newIncidentModel.setLocation(productLocation);
		newIncidentModel.setCountry(parameters.getCountry());
		newIncidentModel.setIncoterm(parameters.getIncoterm());



		final List<IncidentLineModel> incidentLineModelList = new ArrayList<IncidentLineModel>();

		if (parameters.getLines() != null && parameters.getLines().size() > 0)
		{
			for (final IncidentLine eachIncidentLine : parameters.getLines())
			{
				final IncidentLineModel eachIncidentLineModel = new IncidentLineModel();

				eachIncidentLineModel.setQuantity(eachIncidentLine.getQuantity());
				eachIncidentLineModel.setBatch(eachIncidentLine.getBatch());
				eachIncidentLineModel.setSalesUnit(eachIncidentLine.getSalesUnit());
				eachIncidentLineModel.setWeightUnit(eachIncidentLine.getWeightUnit());
				eachIncidentLineModel.setNetweight(eachIncidentLine.getNetweight());
				eachIncidentLineModel.setSorder_p(eachIncidentLine.getSorder_p());
				eachIncidentLineModel.setSorder(eachIncidentLine.getSorder());
				eachIncidentLineModel.setInvoice(eachIncidentLine.getInvoice());
				eachIncidentLineModel.setInvoice_p(eachIncidentLine.getInvoice_p());
				eachIncidentLineModel.setQuantityToClaim(eachIncidentLine.getQuantityToClaim());
				eachIncidentLineModel.setPlant(eachIncidentLine.getPlant());
				eachIncidentLineModel.setProductDescription(eachIncidentLine.getProdcutDescription());

				String productCode = eachIncidentLine.getProduct();
				final String location = eachIncidentLine.getPlant();

				final CustomerProductReferenceModel customerProductReferenceModel = customerProductReferenceService
						.getCustomerProductReference(productCode, b2bUnitModel, baseStoreModel.getUid() + "ProductCatalog", location);

				if (customerProductReferenceModel != null)
				{
					productCode = customerProductReferenceModel.getProductCode();
				}

				try
				{
					final ProductModel productModel = productService
							.getProductForCode((productCode.substring(productCode.length() - 6) + "_" + eachIncidentLine.getPlant()));
					eachIncidentLineModel.setProduct(productModel);
				}
				catch (final Exception ex)
				{
					LOG.error(ex.getMessage());
					eachIncidentLineModel.setProductBaseCode(productCode);
				}

				incidentLineModelList.add(eachIncidentLineModel);
			}
		}

		if (incidentLineModelList.size() > 0)
		{
			newIncidentModel.setIncidentLines(incidentLineModelList);
		}

		modelService.save(newIncidentModel);

		addAtachments(parameters, newIncidentModel);

		try
		{
			publishIncidentOnSharePoint(parameters, newIncidentModel);
		}
		catch (final Exception e)
		{
			final String message = e.getMessage().substring(0, Math.min(MAX_STACK_TRACE_SIZE, e.getMessage().length() - 1));
			newIncidentModel.setSharePointErrorTrace(message);
			modelService.save(newIncidentModel);

			LOG.error("Error: " + e.getMessage());
		}

		final IncidentAddedEvent event = new IncidentAddedEvent();
		event.setBaseSiteModel(baseSiteService.getCurrentBaseSite());
		event.setBaseStoreModel(baseStoreService.getCurrentBaseStore());
		event.setCustomerModel((CustomerModel) userService.getCurrentUser());
		event.setIncidentModel(newIncidentModel);
		event.setLanguageModel(commonI18NService.getCurrentLanguage());
		eventService.publishEvent(event);

		return newIncidentModel;
	}

	protected void publishIncidentOnSharePoint(final AddIncidentParameters parameters, final IncidentModel incidentModel)
			throws Exception
	{
		final String sharePointIncidentId = neorisSharePointClaimsWebService.addNewClaim(parameters, incidentModel);

		if (StringUtils.isNotBlank(sharePointIncidentId))
		{
			incidentModel.setSharePointId(sharePointIncidentId);
			modelService.save(incidentModel);

			LOG.info("Incident published on Sharepoint with id: " + sharePointIncidentId);

			String msgsErrorOnAttach = "";

			if (parameters != null)
			{
				if (parameters.getAtach1() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaim(sharePointIncidentId, parameters.getAtach1(),
								incidentModel, 1);
					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}

				if (parameters.getAtach2() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaim(sharePointIncidentId, parameters.getAtach2(),
								incidentModel, 2);
					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}

				if (parameters.getAtach3() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaim(sharePointIncidentId, parameters.getAtach3(),
								incidentModel, 3);
					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}

				if (parameters.getAtach4() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaim(sharePointIncidentId, parameters.getAtach4(),
								incidentModel, 4);
					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}

				if (parameters.getAtach5() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaim(sharePointIncidentId, parameters.getAtach5(),
								incidentModel, 5);
					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}
			}
			else
			{
				if (incidentModel.getAttachement1() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaimMedia(sharePointIncidentId,
								incidentModel.getAttachement1(), incidentModel, 1);

					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}
				if (incidentModel.getAttachement2() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaimMedia(sharePointIncidentId,
								incidentModel.getAttachement2(), incidentModel, 2);

					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}
				if (incidentModel.getAttachement3() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaimMedia(sharePointIncidentId,
								incidentModel.getAttachement3(), incidentModel, 3);

					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}
				if (incidentModel.getAttachement4() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaimMedia(sharePointIncidentId,
								incidentModel.getAttachement4(), incidentModel, 4);

					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}
				if (incidentModel.getAttachement5() != null)
				{
					try
					{
						neorisSharePointClaimsWebService.addAttachmentToClaimMedia(sharePointIncidentId,
								incidentModel.getAttachement5(), incidentModel, 5);

					}
					catch (final Exception e)
					{
						msgsErrorOnAttach += e.getMessage() + " ";
					}
				}
			}

			// check for errors on attachments
			if (StringUtils.isNotBlank(msgsErrorOnAttach))
			{
				final String message = msgsErrorOnAttach.substring(0, Math.min(MAX_STACK_TRACE_SIZE, msgsErrorOnAttach.length() - 1));
				incidentModel.setSharePointErrorTrace(message);
				modelService.save(incidentModel);
			}
		}
	}

	protected void saveAttachmentToModel(final IncidentModel incidentModel, final MultipartFile attachment,
			final String attachmentDescrption, final int attachmentIndex) throws Exception
	{
		// retrieve the file content
		final InputStream stream = attachment.getInputStream();

		if (stream == null)
		{
			return;
		}

		final MediaModel model = new MediaModel();
		model.setDescription(attachmentDescrption);
		model.setCode(incidentModel.getCode() + new Timestamp(new Date().getTime()));
		model.setCreationtime(new Date());

		final CatalogVersionModel catalog = catalogVersionService.getCatalogVersion(PROLAMSA_MEDIA_CATALOG, ONLINE);
		model.setCatalogVersion(catalog);

		modelService.save(model);

		mediaService.setStreamForMedia(model, stream, attachment.getOriginalFilename(), attachment.getContentType());

		modelService.save(model);

		IOUtils.closeQuietly(stream);

		// set the blob container to the order
		// save the order
		switch (attachmentIndex)
		{
			case 1:
				incidentModel.setAttachement1(model);
				break;
			case 2:
				incidentModel.setAttachement2(model);
				break;
			case 3:
				incidentModel.setAttachement3(model);
				break;
			case 4:
				incidentModel.setAttachement4(model);
				break;
			case 5:
				incidentModel.setAttachement5(model);
				break;
		}

		modelService.save(incidentModel);
	}

	protected void addAtachments(final AddIncidentParameters parameters, final IncidentModel incidentModel)
	{

		final MultipartFile attach1File = parameters.getAtach1();
		final MultipartFile attach2File = parameters.getAtach2();
		final MultipartFile attach3File = parameters.getAtach3();
		final MultipartFile attach4File = parameters.getAtach4();
		final MultipartFile attach5File = parameters.getAtach5();

		if (attach1File != null)
		{

			try
			{
				saveAttachmentToModel(incidentModel, attach1File, parameters.getAtach1Description(), 1);
			}
			catch (final Exception ex)
			{
				LOG.error("error while saving file 1", ex);
			}
		}
		if (attach2File != null)
		{

			try
			{
				saveAttachmentToModel(incidentModel, attach2File, parameters.getAtach2Description(), 2);
			}
			catch (final Exception ex)
			{
				LOG.error("error while saving file 2", ex);
			}
		}
		if (attach3File != null)
		{

			try
			{
				saveAttachmentToModel(incidentModel, attach3File, parameters.getAtach3Description(), 3);
			}
			catch (final Exception ex)
			{
				LOG.error("error while saving file 3", ex);
			}
		}
		if (attach4File != null)
		{

			try
			{
				saveAttachmentToModel(incidentModel, attach4File, parameters.getAtach4Description(), 4);
			}
			catch (final Exception ex)
			{
				LOG.error("error while saving file 4", ex);
			}
		}
		if (attach5File != null)
		{

			try
			{
				saveAttachmentToModel(incidentModel, attach5File, parameters.getAtach5Description(), 5);
			}
			catch (final Exception ex)
			{
				LOG.error("error while saving file 5", ex);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisIncidentService#getIncidentCodeForCustomer(de.hybris.platform.b2b.model.B2BUnitModel
	 * )
	 */
	@Override
	public String getIncidentCodeForCustomer(final B2BUnitModel customer)
	{
		final String query = "SELECT {i.pk},{i.code} FROM {Incident AS i} WHERE {i.account}=?account ORDER BY {i.code} DESC";


		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		queryParams.put("account", customer);

		final SearchResult<IncidentModel> result = flexibleSearchService.search(query, queryParams);

		if (result != null && result.getTotalCount() > 0 && result.getResult().get(0) != null)
		{
			final String biggestCode = result.getResult().get(0).getCode();

			if (StringUtils.isNumeric(biggestCode))
			{
				final Integer nextCode = Integer.valueOf(biggestCode) + 1;

				return fillLeftZeros(nextCode.toString(), 10);

			}

		}
		else
		{
			return fillLeftZeros(customer.getUid(), 6) + fillLeftZeros("1", 4);
		}

		return null;
	}

	protected String fillLeftZeros(String string, final int maxLenght)
	{
		final int limit = maxLenght - string.length();
		for (int i = 0; i < limit; i++)
		{
			string = "0" + string;
		}

		return string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisIncidentService#hasIncidentReports(java.lang.String)
	 */
	@Override
	public boolean hasIncidentReports(final String invoiceNumber)
	{
		final String query = "SELECT {i.pk} FROM {Incident AS i} WHERE {i.invoiceNumber}=?invoiceNumber";


		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		queryParams.put("invoiceNumber", invoiceNumber);

		final SearchResult<IncidentModel> result = flexibleSearchService.search(query, queryParams);

		if (result != null)
		{
			return result.getTotalCount() > 0;
		}

		return false;
	}

	@Override
	public void resendSahrepoint(final Long incidentPK) throws Exception
	{
		try
		{
			final IncidentModel incident = (IncidentModel) modelService.get(PK.fromLong(incidentPK));

			final AddIncidentParameters params = new AddIncidentParameters();

			publishIncidentOnSharePoint(params, incident);
		}
		catch (final Exception e)
		{
			throw new Exception("Error resend incident to sharepoint for pk " + incidentPK);
		}

	}

}
