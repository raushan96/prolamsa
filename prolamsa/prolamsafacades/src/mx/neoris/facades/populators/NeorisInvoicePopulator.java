package mx.neoris.facades.populators;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import java.math.BigDecimal;

import javax.annotation.Resource;

import mx.neoris.core.model.InvoiceModel;
import mx.neoris.facades.invoice.data.InvoiceData;


public class NeorisInvoicePopulator implements Populator<InvoiceModel, InvoiceData>
{
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Override
	public void populate(final InvoiceModel source, final InvoiceData target) throws ConversionException
	{
		target.setCustomer(source.getCustomer());
		target.setNumber(source.getNumber());
		target.setDueDate(source.getDueDate());
		target.setInvoiceDate(source.getInvoiceDate());

		// Populate Entries
		target.setOriginalAmount(createPrice(source.getOriginalAmount()));
		target.setBalanceAmount(createPrice(source.getBalanceAmount()));
		
		target.setDocumentNumber(source.getDocumentNumber());
		target.setDocumentDescription(source.getDocumentDescription());
		target.setCurrency(source.getCurrency());
		target.setCredits(createPrice(source.getCredits()));
		target.setDebits(createPrice(source.getDebits()));
	}
	
	private PriceData createPrice(Double value)
	{
		final CurrencyModel currency = commonI18NService.getBaseCurrency();

		if (value==null)
			    value=0.0;
		
		return priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(value.doubleValue()), currency.getIsocode());
	}
}