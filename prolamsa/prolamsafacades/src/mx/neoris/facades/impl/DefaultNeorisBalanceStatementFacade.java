/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;

import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.model.BalanceStatementDetailModel;
import mx.neoris.core.model.BalanceStatementModel;
import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.services.BalanceStatementSearchParameters;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.services.NeorisBalanceStatementService;
import mx.neoris.core.util.SAPUtils;
import mx.neoris.facades.NeorisBalanceStatementFacade;
import mx.neoris.facades.balancestatement.data.BalanceStatementData;
import mx.neoris.facades.balancestatement.data.BalanceStatementDetailData;
import mx.neoris.facades.invoice.data.InvoiceData;

/**
 * @author e-lacantu
 *
 */
public class DefaultNeorisBalanceStatementFacade implements
		NeorisBalanceStatementFacade {

	/**
	 * Field LOG.
	 */
	private static final Logger LOG = Logger.getLogger(DefaultNeorisBalanceStatementFacade.class);

	@Resource(name = "modelService")
	private ModelService modelService;
		
	@Resource(name = "neorisBalanceStatementService")	
	private NeorisBalanceStatementService neorisBalanceStatementService;	
	
	private Converter<BalanceStatementModel, BalanceStatementData> balanceStatementConverter;
	private Converter<BalanceStatementDetailModel, BalanceStatementDetailData> balanceStatementDetailConverter;
	private Converter<InvoiceModel, InvoiceData> invoiceConverter;
	
	@Override
	public SearchPageData<BalanceStatementDetailData> getPagedBalanceStatement(
			BalanceStatementSearchParameters searchParameters) throws Exception {
		
		SearchPageData<BalanceStatementDetailModel> pageModel = null;

		final List<BalanceStatementDetailData> balances = new ArrayList<BalanceStatementDetailData>();
		try
		{
			pageModel = neorisBalanceStatementService.getPagedBalanceStatement(searchParameters);

			for (final BalanceStatementDetailModel balanceStatementDetailModel : pageModel.getResults())
			{
				final BalanceStatementDetailData balanceStatementDetailData = new BalanceStatementDetailData();
				balanceStatementDetailConverter.convert(balanceStatementDetailModel, balanceStatementDetailData);
				balances.add(balanceStatementDetailData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageData(pageModel, balances);
	}
	
	private SearchPageData<BalanceStatementDetailData> convertToPageData(final SearchPageData<BalanceStatementDetailModel> objData,
			final List<BalanceStatementDetailData> balances)
	{
		final SearchPageData<BalanceStatementDetailData> result = new SearchPageData<BalanceStatementDetailData>();
		result.setPagination(objData.getPagination());
		result.setResults(balances);
		result.setSorts(objData.getSorts());

		return result;
	}
	
	private SearchPageData<InvoiceData> convertToPageDataInvoice(final SearchPageData<InvoiceModel> objData,
			final List<InvoiceData> invoices)
	{
		final SearchPageData<InvoiceData> result = new SearchPageData<InvoiceData>();
		result.setPagination(objData.getPagination());
		result.setResults(invoices);
		result.setSorts(objData.getSorts());

		return result;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public NeorisBalanceStatementService getBalanceStatementService() {
		return neorisBalanceStatementService;
	}

	public void setBalanceStatementService(
			NeorisBalanceStatementService balanceStatementService) {
		this.neorisBalanceStatementService = balanceStatementService;
	}

	public Converter<BalanceStatementModel, BalanceStatementData> getBalanceStatementConverter() {
		return balanceStatementConverter;
	}

	public void setBalanceStatementConverter(
			Converter<BalanceStatementModel, BalanceStatementData> balanceStatementConverter) {
		this.balanceStatementConverter = balanceStatementConverter;
	}

	public Converter<BalanceStatementDetailModel, BalanceStatementDetailData> getBalanceStatementDetailConverter() {
		return balanceStatementDetailConverter;
	}

	public void setBalanceStatementDetailConverter(
			Converter<BalanceStatementDetailModel, BalanceStatementDetailData> balanceStatementDetailConverter) {
		this.balanceStatementDetailConverter = balanceStatementDetailConverter;
	}

	

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisBalanceStatementFacade#getBalanceStatementDetail(mx.neoris.core.services.BalanceStatementSearchParameters, java.lang.Integer)
	 */
	@Override
	public BalanceStatementDetailData getBalanceStatementDetail(
			BalanceStatementSearchParameters searchParameters, Integer row)
			throws Exception {
		SearchResult<BalanceStatementDetailModel> pageModel = null;		
		final BalanceStatementDetailData balanceStatementDetailData = new BalanceStatementDetailData();
		
		try
		{
			pageModel = neorisBalanceStatementService.getBalanceStatementDetail(searchParameters,row);			

			if  (pageModel.getResult()!=null && pageModel.getResult().size() > 0) {				
				balanceStatementDetailConverter.convert(pageModel.getResult().get(0) , balanceStatementDetailData);				
			}						
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return balanceStatementDetailData;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisBalanceStatementFacade#getPagedBalanceStatementInvoices(mx.neoris.core.services.InvoiceSearchParameters)
	 */
	@Override
	public SearchPageData<InvoiceData> getPagedBalanceStatementInvoices(
			InvoiceSearchParameters searchParameters) throws Exception {
		
		SearchPageData<InvoiceModel> pageModel = null;

		final List<InvoiceData> invoices = new ArrayList<InvoiceData>();
		try
		{
			pageModel = neorisBalanceStatementService.getPagedBalanceStatementInvoices(searchParameters);

			for (final InvoiceModel invoiceModel : pageModel.getResults())
			{
				final InvoiceData invoiceData = new InvoiceData(); 
				invoiceConverter.convert(invoiceModel, invoiceData);
				
				long diferencia1 = getDayDifferent(invoiceData.getDueDate());
				
				LOG.info("Dias de diferencia " + diferencia1);

				invoiceData.setDaysExpire(diferencia1);
				
				invoices.add(invoiceData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageDataInvoice(pageModel, invoices);
	}

	/**
	 * @return the neorisBalanceStatementService
	 */
	public NeorisBalanceStatementService getNeorisBalanceStatementService() {
		return neorisBalanceStatementService;
	}

	/**
	 * @param neorisBalanceStatementService the neorisBalanceStatementService to set
	 */
	public void setNeorisBalanceStatementService(
			NeorisBalanceStatementService neorisBalanceStatementService) {
		this.neorisBalanceStatementService = neorisBalanceStatementService;
	}

	/**
	 * @return the invoiceConverter
	 */
	public Converter<InvoiceModel, InvoiceData> getInvoiceConverter() {
		return invoiceConverter;
	}

	/**
	 * @param invoiceConverter the invoiceConverter to set
	 */
	public void setInvoiceConverter(
			Converter<InvoiceModel, InvoiceData> invoiceConverter) {
		this.invoiceConverter = invoiceConverter;
	}

	private long getDayDifferent(Date fFinal)
	{
		long different = 0;
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
		Date today = new Date(); //Fecha de hoy 
		
		different = (today.getTime() - fFinal.getTime()) / MILLSECS_PER_DAY;

		return different;
	}
	

}
