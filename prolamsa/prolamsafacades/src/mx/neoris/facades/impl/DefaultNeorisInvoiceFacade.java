/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.InvoiceModel;
import mx.neoris.core.services.InvoiceSearchParameters;
import mx.neoris.core.services.NeorisInvoiceService;
import mx.neoris.facades.NeorisInvoiceFacade;
import mx.neoris.facades.invoice.data.InvoiceData;

import org.apache.log4j.Logger;

/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisInvoiceFacade implements NeorisInvoiceFacade 
{
	/**
	 * Field LOG.
	 */
	private static final Logger LOG = Logger.getLogger(DefaultNeorisInvoiceFacade.class);

	@Resource(name = "modelService")
	private ModelService modelService;

	private NeorisInvoiceService invoiceService;
	private Converter<InvoiceModel, InvoiceData> invoiceConverter;

	public SearchPageData<InvoiceData> getPagedInvoices(final InvoiceSearchParameters searchParameters) throws Exception
	{
		SearchPageData<InvoiceModel> pageModel = null;

		final List<InvoiceData> invoices = new ArrayList<InvoiceData>();
		try
		{
			pageModel = invoiceService.getPagedInvoices(searchParameters);

			for (final InvoiceModel invoiceModel : pageModel.getResults())
			{
				final InvoiceData invoiceData = new InvoiceData();
				invoiceConverter.convert(invoiceModel, invoiceData);
				invoices.add(invoiceData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return convertToPageData(pageModel, invoices);
	}
	
	public SearchPageData<InvoiceData> getPagedInvoicesByRange(final InvoiceSearchParameters searchParameters, String range) throws Exception
	{
		SearchPageData<InvoiceModel> pageModel = null;

		final List<InvoiceData> invoices = new ArrayList<InvoiceData>();
		try
		{
			pageModel = invoiceService.getPagedInvoicesByRange(searchParameters,range);

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

		return convertToPageData(pageModel, invoices);
	}

	private SearchPageData<InvoiceData> convertToPageData(final SearchPageData<InvoiceModel> objData,
			final List<InvoiceData> invoices)
	{
		final SearchPageData<InvoiceData> result = new SearchPageData<InvoiceData>();
		result.setPagination(objData.getPagination());
		result.setResults(invoices);
		result.setSorts(objData.getSorts());

		return result;
	}

	/**
	 * @return the invoiceService
	 */
	public NeorisInvoiceService getInvoiceService()
	{
		return invoiceService;
	}

	/**
	 * @param invoiceService
	 *            the invoiceService to set
	 */
	public void setInvoiceService(NeorisInvoiceService invoiceService)
	{
		this.invoiceService = invoiceService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *            the modelService to set
	 */
	public void setModelService(ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the invoiceConverter
	 */
	public Converter<InvoiceModel, InvoiceData> getInvoiceConverter()
	{
		return invoiceConverter;
	}

	/**
	 * @param invoiceConverter
	 *            the invoiceConverter to set
	 */
	public void setInvoiceConverter(Converter<InvoiceModel, InvoiceData> invoiceConverter)
	{
		this.invoiceConverter = invoiceConverter;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisInvoiceFacade#getInvoicesByB2BUnit(java.util.List)
	 */
	@Override
	public List<InvoiceData> getInvoicesByB2BUnit(
			List<B2BUnitData> listB2BUnitData) throws Exception {
		
		SearchPageData<InvoiceModel> pageData = null;

		final List<InvoiceData> invoices = new ArrayList<InvoiceData>();
		try
		{
			pageData = invoiceService.getInvoicesByB2BUnit(listB2BUnitData);

			for (final InvoiceModel invoiceModel : pageData.getResults())
			{
				final InvoiceData invoiceData = new InvoiceData();
				invoiceConverter.convert(invoiceModel, invoiceData);
				invoices.add(invoiceData);
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return invoices;
	}
	
	@Override
	public String getPDFDocument(String invoice, String customer) throws Exception {
		
		return invoiceService.getPDFDocument(invoice, customer);
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
