/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;


/**
 * @author e-lacantu
 * 
 */

public interface NeorisCreditScoreCard
{
	public abstract void calculateCreditScoreCard(AbstractOrderData orderData) throws Exception;

}