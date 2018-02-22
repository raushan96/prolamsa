/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.DocumentSearchModel;
import mx.neoris.facades.document.data.DocumentSearchData;

/**
 * @author christian.loredo
 *
 */
public class NeorisDocumentSearchPopulator implements Populator<DocumentSearchModel, DocumentSearchData>
{

	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(DocumentSearchModel source, DocumentSearchData target)
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
