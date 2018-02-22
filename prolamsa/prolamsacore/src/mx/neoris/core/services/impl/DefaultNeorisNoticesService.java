/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import mx.neoris.core.enums.NoticeType;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.NoticeModel;
import mx.neoris.core.services.AddNoticeParameters;
import mx.neoris.core.services.NeorisNoticesService;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


/**
 * @author eduardo.carrillo
 * 
 */
public class DefaultNeorisNoticesService implements NeorisNoticesService
{

	@Resource(name = "baseStoreService")
	BaseStoreService baseStoreService;

	@Resource(name = "modelService")
	ModelService modelService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Resource
	private MediaService mediaService;

	private static final String PROLAMSA_MEDIA_CATALOG = "prolamsaMediaCatalog";
	private static final String ONLINE = "Online";

	private static final Logger LOG = Logger.getLogger(DefaultNeorisNoticesService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisNoticesService#getNotice(java.lang.String)
	 */
	@Override
	public NoticeModel getNotice(final String code)
	{

		//TODO: Search notice using a flexible search
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		final List<NoticeModel> baseStoreNotices = baseStoreModel.getNotices();

		//there are not notices for current store
		if (baseStoreNotices == null)
		{
			return null;
		}

		for (final NoticeModel eachNoticeModel : baseStoreNotices)
		{
			if (eachNoticeModel.getCode().equalsIgnoreCase(code))
			{
				return eachNoticeModel;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisNoticesService#addNotice(mx.neoris.core.services.AddIncidentParameters)
	 */
	@Override
	public NoticeModel addNotice(final AddNoticeParameters parameters)
	{
		final BaseStoreModel currentStoreModel = baseStoreService.getCurrentBaseStore();

		final NoticeModel newNoticeModel = new NoticeModel();

		//newNoticeModel.setCode(new Date().getTime() + "-" + parameters.getName());
		newNoticeModel.setCode(UUID.randomUUID().toString());
		newNoticeModel.setName(parameters.getName());
		newNoticeModel.setType(enumerationService.getEnumerationValue(NoticeType.class, parameters.getType()));
		newNoticeModel.setCreationtime(parameters.getPublicationDate());
		if (parameters.getMedia() == null)
		{
			return null;
		}

		try
		{
			// retrieve the file content
			final InputStream stream = parameters.getMedia().getInputStream();

			if (stream == null)
			{
				return null;
			}

			final NeorisMediaModel mediaModel = new NeorisMediaModel();

			mediaModel.setCode(new Date().getTime() + "-" + parameters.getName());
			mediaModel.setCreationtime(new Date());

			final CatalogVersionModel catalog = catalogVersionService.getCatalogVersion(PROLAMSA_MEDIA_CATALOG, ONLINE);
			mediaModel.setCatalogVersion(catalog);

			modelService.save(mediaModel);

			mediaService.setStreamForMedia(mediaModel, stream, parameters.getMedia().getOriginalFilename(), parameters.getMedia()
					.getContentType());

			IOUtils.closeQuietly(stream);

			newNoticeModel.setMedia(mediaModel);

			// save the notice
			modelService.save(newNoticeModel);

			final List<NoticeModel> baseStoreNotices = currentStoreModel.getNotices();

			final List<NoticeModel> newStoreNotices = new ArrayList<NoticeModel>();

			if (baseStoreNotices != null && baseStoreNotices.size() > 0)
			{
				newStoreNotices.addAll(baseStoreNotices);
			}

			newStoreNotices.add(newNoticeModel);

			currentStoreModel.setNotices(newStoreNotices);

			//save the baseStore with the new 
			modelService.save(currentStoreModel);

		}
		catch (final Exception ex)
		{
			LOG.error("error while saving file", ex);
		}


		return newNoticeModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisNoticesService#getNoticesForCurrentStore()
	 */
	@Override
	public List<NoticeModel> getNoticesForCurrentStore()
	{
		final BaseStoreModel currentStoreModel = baseStoreService.getCurrentBaseStore();

		return currentStoreModel.getNotices();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisNoticesService#deleteNotice(java.lang.String)
	 */
	@Override
	public boolean deleteNotice(final String code)
	{
		//TODO: Search notice using a flexible search
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		final List<NoticeModel> baseStoreNotices = baseStoreModel.getNotices();

		//there are not notices for current store
		if (baseStoreNotices == null)
		{
			return false;
		}

		final List<NoticeModel> newNotices = new ArrayList<NoticeModel>();

		NoticeModel modelToBeDeleted = null;

		for (final NoticeModel eachNoticeModel : baseStoreNotices)
		{
			if (eachNoticeModel.getCode().equalsIgnoreCase(code))
			{
				modelToBeDeleted = eachNoticeModel;
			}
			else
			{
				newNotices.add(eachNoticeModel);
			}
		}



		if (modelToBeDeleted != null)
		{
			baseStoreModel.setNotices(newNotices);

			modelService.save(baseStoreModel);

			modelService.remove(modelToBeDeleted);

			return true;
		}
		return false;
	}

}
