/**
 * 
 */
package mx.neoris.core.updaters;

import de.hybris.platform.b2b.model.B2BUnitModel;


/**
 * @author christian.loredo
 * 
 */
public interface NeorisBackofficeB2BUnitUpdater
{
	void updateWith(final B2BUnitModel b2bUnit, final String originalBackoffice) throws Exception;
}
