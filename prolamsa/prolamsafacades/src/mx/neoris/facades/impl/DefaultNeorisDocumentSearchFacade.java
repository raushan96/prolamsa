/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.neoris.core.model.DocumentSearchModel;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.services.DocumentSearchParameters;
import mx.neoris.core.services.NeorisDocumentSearchService;
import mx.neoris.facades.NeorisDocumentSearchFacade;
import mx.neoris.facades.document.data.DocumentSearchData;
import mx.neoris.facades.media.data.NeorisMediaData;

/**
 * @author christian.loredo
 *
 */
public class DefaultNeorisDocumentSearchFacade implements NeorisDocumentSearchFacade
{
	/**
	 * Field LOG.
	 */
	private static final Logger LOG = Logger.getLogger(DefaultNeorisDocumentSearchFacade.class);

	private NeorisDocumentSearchService documentService;
	private Converter<DocumentSearchModel, DocumentSearchData> documentConverter;
	
	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;
	
	@Resource (name="neorisMediaConverter")
	private Converter<NeorisMediaModel, NeorisMediaData> neorisMediaConverter;

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisDocumentSearchFacade#getPagedDocumentss(mx.neoris.core.services.DocumentSearchParameters)
	 */
	@Override
	public SearchPageData<DocumentSearchData> getPagedDocuments(
			DocumentSearchParameters searchParameters) throws Exception {
		
		SearchPageData<DocumentSearchModel> pageModel = null;

		final List<DocumentSearchData> documents = new ArrayList<DocumentSearchData>();
		try
		{
			pageModel = documentService.getPagedDocuments(searchParameters);

			for (final DocumentSearchModel documentModel : pageModel.getResults())
			{
				final DocumentSearchData documentData = new DocumentSearchData();
				documentConverter.convert(documentModel, documentData);
				documents.add(documentData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageData(pageModel, documents);
	}

	private SearchPageData<DocumentSearchData> convertToPageData(final SearchPageData<DocumentSearchModel> objData,
			final List<DocumentSearchData> documents)
	{
		final SearchPageData<DocumentSearchData> result = new SearchPageData<DocumentSearchData>();
		result.setPagination(objData.getPagination());
		result.setResults(documents);
		result.setSorts(objData.getSorts());

		return result;
	}
	
	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisDocumentSearchFacade#injectSOAttachmentsOn(de.hybris.platform.commerceservices.search.pagedata.SearchPageData)
	 */
	@Override
	public void injectSOAttachmentsOn(List<DocumentSearchData> searchDataResult) {
		
		if(CollectionUtils.isEmpty(searchDataResult))
		{
			return;
		}
				
		for (DocumentSearchData documentSearchData : searchDataResult) 
		{
			String so = documentSearchData.getSo();
			
			if(StringUtils.isNotEmpty(so))
			{
				OrderModel order = b2bOrderService.getOrderForCode(so);
				
				if (order == null)
				{
					LOG.debug("Order " + so + " not found.");
					continue;			
				}

				List<NeorisMediaData> neorisMedia = Converters.convertAll(order.getAttachmentsPO(), neorisMediaConverter);
				documentSearchData.setSoAttachments(neorisMedia);
			}
		}
	}

	/**
	 * @return the documentService
	 */
	public NeorisDocumentSearchService getDocumentService() {
		return documentService;
	}

	/**
	 * @param documentService the documentService to set
	 */
	public void setDocumentService(NeorisDocumentSearchService documentService) {
		this.documentService = documentService;
	}

	/**
	 * @return the documentConverter
	 */
	public Converter<DocumentSearchModel, DocumentSearchData> getDocumentConverter() {
		return documentConverter;
	}

	/**
	 * @param documentConverter the documentConverter to set
	 */
	public void setDocumentConverter(
			Converter<DocumentSearchModel, DocumentSearchData> documentConverter) {
		this.documentConverter = documentConverter;
	}
	
	

}
