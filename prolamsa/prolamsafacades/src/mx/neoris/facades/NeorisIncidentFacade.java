/**
 * 
 */
package mx.neoris.facades;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;










import java.util.List;

import mx.neoris.core.invoice.ValidateInvoiceResponse;
import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.model.IncidentStateModel;
import mx.neoris.core.model.IncidentTypeModel;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.IncidentSearchParameters;

/**
 * @author e-jecarrilloi
 *
 */
public interface NeorisIncidentFacade {
	public SearchPageData<IncidentModel> getPagedIncidents(final IncidentSearchParameters searchParameters) throws Exception;
	
	public IncidentModel getIncident(String code);
	
	public List<IncidentTypeModel> getAllIncidentTypes();
	
	public List<IncidentStateModel> getAllIncidentStates();
	
	public IncidentModel addIncident(AddIncidentParameters parameters);
	
	public ValidateInvoiceResponse validateInvoice(String invoiceNumber, String customer) throws Exception;
	
	void resendSharepoint(Long incidentPK) throws Exception;
}
