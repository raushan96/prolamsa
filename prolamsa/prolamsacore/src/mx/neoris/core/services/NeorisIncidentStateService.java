/**
 * 
 */
package mx.neoris.core.services;

import java.util.List;

import mx.neoris.core.model.IncidentStateModel;


/**
 * @author e-jecarrilloi
 * 
 */
public interface NeorisIncidentStateService
{
	public List<IncidentStateModel> getAll();

	public IncidentStateModel getState(String code);
}
