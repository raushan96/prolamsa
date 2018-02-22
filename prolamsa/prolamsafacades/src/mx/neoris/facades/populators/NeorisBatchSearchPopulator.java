/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.BatchSearchModel;
import mx.neoris.facades.document.data.BatchSearchData;

/**
 * @author christian.loredo
 *
 */
public class NeorisBatchSearchPopulator implements Populator<BatchSearchModel, BatchSearchData>
{

	@Override
	public void populate(BatchSearchModel source, BatchSearchData target)
			throws ConversionException {
		
		target.setBoL(source.getBoL());
		target.setCode(source.getCode());
		target.setCreditNote(source.getCreditNote());
		target.setDebitNote(source.getDebitNote());
		target.setInvoice(source.getInvoice());
		target.setMtr(source.getMtr());
		target.setName(source.getName());
		target.setPo(source.getPo());
		target.setQuote(source.getQuote());
		target.setRemission(source.getRemission());
		target.setSo(source.getSo());
		target.setTolly(source.getTolly());
		target.setDateInvoice(source.getDateInvoice());
		
	}

}
