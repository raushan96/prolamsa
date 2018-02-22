/**
 * 
 */
package mx.neoris.core.services;

import java.util.List;

import mx.neoris.core.model.IncidentTypeModel;


/**
 * @author e-jecarrilloi
 * 
 */
public interface NeorisIncidentTypeService
{
	public List<IncidentTypeModel> getAll();

	public IncidentTypeModel getType(String code);
}
