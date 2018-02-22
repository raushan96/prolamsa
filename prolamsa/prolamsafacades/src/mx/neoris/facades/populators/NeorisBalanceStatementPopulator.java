/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.BalanceStatementModel;
import mx.neoris.facades.balancestatement.data.BalanceStatementData;

/**
 * @author e-lacantu
 *
 */
public class NeorisBalanceStatementPopulator implements	Populator<BalanceStatementModel, BalanceStatementData> {

	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(BalanceStatementModel source,
			BalanceStatementData target) throws ConversionException {
		
		target.setCustomer(source.getCustomer());
		target.setBalance(source.getBalance());
		target.setCreditLimit(source.getCreditLimit());
		target.setCurrentAmount(source.getCurrentAmount());
		target.setPastDue(source.getPastDue());
		
	}

}
