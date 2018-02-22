/**
 * 
 */
package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import mx.neoris.core.model.IncidentModel;


/**
 * @author e-jecarrilloi
 * 
 */
public interface NeorisIncidentService
{
	public SearchPageData<IncidentModel> getPagedIncidents(final IncidentSearchParameters searchParameters) throws Exception;

	public IncidentModel getIncident(String code);

	public IncidentModel addIncident(AddIncidentParameters parameters);

	public String getIncidentCodeForCustomer(B2BUnitModel customer);

	public boolean hasIncidentReports(String invoiceNumber);

	public void resendSahrepoint(Long incidentPK) throws Exception;


}
