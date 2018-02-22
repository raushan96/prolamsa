package mx.neoris.facades.impl;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.BackorderDetailModel;
import mx.neoris.core.model.BackorderModel;
import mx.neoris.core.services.BackorderDetailSearchParameter;
import mx.neoris.core.services.BackorderSearchParameters;
import mx.neoris.core.services.NeorisAddressService;
import mx.neoris.core.services.NeorisBackorderService;
import mx.neoris.facades.NeorisBackorderFacade;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.backorder.data.BackorderData;
import mx.neoris.facades.backorder.data.BackorderDetailData;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author christian.loredo
 *
 */
public class DefaultNeorisBackorderFacade implements NeorisBackorderFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultNeorisBackorderFacade.class);

	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name = "neorisAddressService")
	private NeorisAddressService neorisAddressService;
	
	private NeorisBackorderService backorderService;
	private Converter<BackorderModel, BackorderData> backorderConverter;
	private Converter<BackorderDetailModel, BackorderDetailData> backorderDetailConverter;

	public SearchPageData<BackorderData> getPagedBackorder(
			BackorderSearchParameters searchParameters) throws Exception
	{
		SearchPageData<BackorderModel> pageModel = null;

		final List<BackorderData> backorders = new ArrayList<BackorderData>();
		try
		{
			pageModel = backorderService.getPagedBackorder(searchParameters);

			for (final BackorderModel backorderModel : pageModel.getResults())
			{
				final BackorderData backorderData = new BackorderData();
				backorderConverter.convert(backorderModel, backorderData);
				backorders.add(backorderData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageData(pageModel, backorders);
	}

	private SearchPageData<BackorderData> convertToPageData(final SearchPageData<BackorderModel> objData,
			final List<BackorderData> backorders)
	{
		final SearchPageData<BackorderData> result = new SearchPageData<BackorderData>();
		result.setPagination(objData.getPagination());
		result.setResults(backorders);
		result.setSorts(objData.getSorts());

		return result;
	}
	
	//DETAIL
	public SearchPageData<BackorderDetailData> getPagedBackorderDetail(
			BackorderDetailSearchParameter searchParameters) throws Exception
	{
		SearchPageData<BackorderDetailModel> pageModel = null; 

		final List<BackorderDetailData> backorders = new ArrayList<BackorderDetailData>();
		try
		{
			pageModel = backorderService.getPagedBackorderDetail(searchParameters);
			int i = 1;
			
			for (final BackorderDetailModel backorderDetailModel : pageModel.getResults())
			{
				final BackorderDetailData backorderDetailData = new BackorderDetailData();
				
				AddressModel address = null;
				//set the shipping address	
				if (backorderDetailModel.getAddress() != null && !backorderDetailModel.getAddress().equals("")){
					address = neorisAddressService.getAddressWithCode(backorderDetailModel.getAddress().substring(5));	
				}
				
				if (address != null) 
				{
					BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
					
					if (baseStore.getUid().equals("6000")) 
					{
						backorderDetailModel.setAddress(address.getLine1() + " \n"
								+ ((StringUtils.isNotEmpty(address.getLine2())) ? address.getLine2() + ", " + "\n" : "")
								+ address.getTown() + ", " 
								+ address.getRegion().getName() + ", "
								+ address.getCountry().getName());
					} else 
					{
						backorderDetailModel.setAddress("(" + address.getCode() + ") " 
								+ address.getShortName() + ", " + "\n"
								+ address.getLine1() + " \n"
								+ ((StringUtils.isNotEmpty(address.getLine2())) ? address.getLine2() + ", " + "\n" : "")
								+ address.getTown() + ", " 
								+ address.getRegion().getName() + ", "
								+ address.getCountry().getName());
					}
				}
				
				backorderDetailConverter.convert(backorderDetailModel, backorderDetailData);
				
				backorderDetailData.setRowToExcel(i);
				
				backorders.add(backorderDetailData);
				
				i+=1;
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageDataDetail(pageModel, backorders);
	}

	private SearchPageData<BackorderDetailData> convertToPageDataDetail(final SearchPageData<BackorderDetailModel> objData,
			final List<BackorderDetailData> backorders)
	{
		final SearchPageData<BackorderDetailData> result = new SearchPageData<BackorderDetailData>();
		result.setPagination(objData.getPagination());
		result.setResults(backorders);
		result.setSorts(objData.getSorts());

		return result;
	}
	
	public SearchPageData<BackorderData> getPagedBackorderDetailExcel(
			BackorderSearchParameters searchParameters) throws Exception
	{
		SearchPageData<BackorderModel> pageModel = null;

		final List<BackorderData> backorders = new ArrayList<BackorderData>();
		try
		{
			pageModel = backorderService.getPagedBackorderDetailExcel(searchParameters);

			for (final BackorderModel backorderModel : pageModel.getResults())
			{
				final BackorderData backorderData = new BackorderData();
				backorderConverter.convert(backorderModel, backorderData);
				backorders.add(backorderData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageData(pageModel, backorders);
	}
	
	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	/**
	 * @return the backorderService
	 */
	public NeorisBackorderService getBackorderService() {
		return backorderService;
	}

	/**
	 * @param backorderService the backorderService to set
	 */
	public void setBackorderService(NeorisBackorderService backorderService) {
		this.backorderService = backorderService;
	}

	/**
	 * @return the backorderConverter
	 */
	public Converter<BackorderModel, BackorderData> getBackorderConverter() {
		return backorderConverter;
	}

	/**
	 * @param backorderConverter the backorderConverter to set
	 */
	public void setBackorderConverter(
			Converter<BackorderModel, BackorderData> backorderConverter) {
		this.backorderConverter = backorderConverter;
	}

	/**
	 * @return the backorderDetailConverter
	 */
	public Converter<BackorderDetailModel, BackorderDetailData> getBackorderDetailConverter() {
		return backorderDetailConverter;
	}

	/**
	 * @param backorderDetailConverter the backorderDetailConverter to set
	 */
	public void setBackorderDetailConverter(
			Converter<BackorderDetailModel, BackorderDetailData> backorderDetailConverter) {
		this.backorderDetailConverter = backorderDetailConverter;
	}
	

	
}
