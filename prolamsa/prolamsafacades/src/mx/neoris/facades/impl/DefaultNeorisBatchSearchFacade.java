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
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.neoris.core.model.BatchSearchModel;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.services.BatchSearchParameters;
import mx.neoris.core.services.NeorisBatchSearchService;
import mx.neoris.facades.NeorisBatchSearchFacade;
import mx.neoris.facades.document.data.BatchSearchData;
import mx.neoris.facades.document.data.DocumentSearchData;
import mx.neoris.facades.media.data.NeorisMediaData;

/**
 * @author christian.loredo
 *
 */
public class DefaultNeorisBatchSearchFacade implements NeorisBatchSearchFacade
{ 
	private static final Logger LOG = Logger.getLogger(DefaultNeorisBatchSearchFacade.class);

	private NeorisBatchSearchService batchService;
	private Converter<BatchSearchModel, BatchSearchData> batchConverter;
	
	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;
	
	@Resource (name="neorisMediaConverter")
	private Converter<NeorisMediaModel, NeorisMediaData> neorisMediaConverter;

	@Override
	public SearchPageData<BatchSearchData> getPagedBatch(
			BatchSearchParameters searchParameters) throws Exception {
		
		SearchPageData<BatchSearchModel> pageModel = null;

		final List<BatchSearchData> batchs = new ArrayList<BatchSearchData>();
		try
		{
			pageModel = batchService.getPagedBatch(searchParameters);

			for (final BatchSearchModel batchModel : pageModel.getResults())
			{
				final BatchSearchData batchData = new BatchSearchData();
				batchConverter.convert(batchModel, batchData);
				batchs.add(batchData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageData(pageModel, batchs);
	}

	private SearchPageData<BatchSearchData> convertToPageData(final SearchPageData<BatchSearchModel> objData,
			final List<BatchSearchData> batchs)
	{
		final SearchPageData<BatchSearchData> result = new SearchPageData<BatchSearchData>();
		result.setPagination(objData.getPagination());
		result.setResults(batchs);
		result.setSorts(objData.getSorts());

		return result;
	}

	/**
	 * @return the batchService
	 */
	public NeorisBatchSearchService getBatchService() {
		return batchService;
	}

	/**
	 * @param batchService the batchService to set
	 */
	public void setBatchService(NeorisBatchSearchService batchService) {
		this.batchService = batchService;
	}

	/**
	 * @return the documentConverter
	 */
	public Converter<BatchSearchModel, BatchSearchData> getBatchConverter() {
		return batchConverter;
	}

	/**
	 * @param batchConverter the batchConverter to set
	 */
	public void setBatchConverter(
			Converter<BatchSearchModel, BatchSearchData> batchConverter) {
		this.batchConverter = batchConverter;
	}
	
	@Override
	public void injectSOAttachmentsOn(List<BatchSearchData> searchDataResult) {
		
		if(CollectionUtils.isEmpty(searchDataResult))
		{
			return;
		}
				
		for (BatchSearchData batchSearchData : searchDataResult) 
		{
			String so = batchSearchData.getSo();
			
			if(StringUtils.isNotEmpty(so))
			{
				OrderModel order = b2bOrderService.getOrderForCode(so);
				
				if (order == null)
				{
					LOG.debug("Order " + so + " not found.");
					continue;			
				}

				List<NeorisMediaData> neorisMedia = Converters.convertAll(order.getAttachmentsPO(), neorisMediaConverter);
				batchSearchData.setSoAttachments(neorisMedia);
			}
		}
	}
}
