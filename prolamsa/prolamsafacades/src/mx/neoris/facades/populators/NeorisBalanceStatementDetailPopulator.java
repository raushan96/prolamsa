/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import mx.neoris.core.model.BalanceStatementDetailModel;
import mx.neoris.facades.balancestatement.data.BalanceStatementDetailData;

/**
 * @author e-lacantu
 *
 */
public class NeorisBalanceStatementDetailPopulator implements	Populator<BalanceStatementDetailModel, BalanceStatementDetailData> {

	
	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(BalanceStatementDetailModel source,
			BalanceStatementDetailData target) throws ConversionException {
		target.setCustomer(source.getCustomer());
		target.setBalance(source.getBalance());
		target.setCreditLimit(source.getCreditLimit());
		target.setCreditAvailable(source.getCreditAvailable());
		target.setSalVal(source.getSalVal());
		target.setPastDue(source.getPastDue());		
		target.setPastDue1_30(source.getPastDue1_30());
		target.setPastDue31_60(source.getPastDue31_60());
		target.setPastDue61_90(source.getPastDue61_90());
		target.setPastDueMore90(source.getPastDueMore90());
		target.setOverdueAmount(source.getOverdueAmount());
		target.setOverdueCharge(source.getOverdueCharge());
		target.setOverdueCredit(source.getOverdueCredit());
		target.setOverdueInvoice(source.getOverdueInvoice());
		target.setOverduePayment(source.getOverduePayment());
		target.setCurrentBalance(source.getCurrentBalance());
		target.setCurrentCharge(source.getCurrentCharge());
		target.setCurrentCredit(source.getCurrentCredit());
		target.setCurrentInvoice(source.getCurrentInvoice());
		target.setCurrentPayment(source.getCurrentPayment());			
		target.setCurrent(source.getCurrent());
	}

}
