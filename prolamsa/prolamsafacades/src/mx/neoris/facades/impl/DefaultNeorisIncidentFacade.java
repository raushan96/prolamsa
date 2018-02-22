/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;










import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import mx.neoris.core.invoice.ValidateInvoiceResponse;
import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.model.IncidentStateModel;
import mx.neoris.core.model.IncidentTypeModel;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.IncidentSearchParameters;
import mx.neoris.core.services.NeorisIncidentService;
import mx.neoris.core.services.NeorisIncidentStateService;
import mx.neoris.core.services.NeorisIncidentTypeService;
import mx.neoris.core.services.NeorisService;
import mx.neoris.core.services.NeorisValidateInvoice;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisIncidentFacade;

/**
 * @author e-jecarrilloi
 *
 */
public class DefaultNeorisIncidentFacade implements NeorisIncidentFacade {

	private static final Logger LOG = Logger.getLogger(DefaultNeorisIncidentFacade.class);

	@Resource(name="neorisIncidentService")
	private NeorisIncidentService neorisIncidentService;
	
	@Resource(name="neorisFacade")
	private NeorisFacade neorisFacade;
	
	@Resource(name="neorisIncidentTypeService")
	private NeorisIncidentTypeService neorisIncidentTypeService;
	
	@Resource(name="neorisIncidentStateService")
	private NeorisIncidentStateService neorisIncidentStateService;
	
	@Resource(name="neorisSAPInvoiceValidateService")
	private NeorisValidateInvoice neorisSAPInvoiceValidateService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "userService")
	private UserService userService;
	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisIncidentFacade#getPagedInvoices(mx.neoris.core.services.IncidentSearchParameters)
	 */
	@Override
	public SearchPageData<IncidentModel> getPagedIncidents(
			final IncidentSearchParameters searchParameters) throws Exception {
		return sessionService.executeInLocalView(new SessionExecutionBody() {
			@Override
			public SearchPageData<IncidentModel> execute() {
				try {
					return neorisIncidentService.getPagedIncidents(searchParameters);
				} catch (Exception e) {
					return null;
				}
			}
		}, userService.getAdminUser());
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisIncidentFacade#getAllIncidentTypes()
	 */
	@Override
	public List<IncidentTypeModel> getAllIncidentTypes() {
		return neorisIncidentTypeService.getAll();
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisIncidentFacade#getAllIncidentStates()
	 */
	@Override
	public List<IncidentStateModel> getAllIncidentStates() {
		// YTODO Auto-generated method stub
		return neorisIncidentStateService.getAll();
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisIncidentFacade#getIncident(java.lang.String)
	 */
	@Override
	public IncidentModel getIncident(String code) {
		return neorisIncidentService.getIncident(code);
				
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisIncidentFacade#addIncident(mx.neoris.core.services.AddIncidentParameters)
	 */
	@Override
	public IncidentModel addIncident(AddIncidentParameters parameters) {
		
		return neorisIncidentService.addIncident(parameters);
		
		
		
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisIncidentFacade#validateInvoice(java.lang.String)
	 */
	@Override
	public ValidateInvoiceResponse validateInvoice(String invoiceNumber,String customer) throws Exception {
		// YTODO Auto-generated method stub
		return neorisSAPInvoiceValidateService.validateInvoice(invoiceNumber,customer);
	}

	@Override
	public void resendSharepoint(Long incidentPK) throws Exception {
		
		neorisIncidentService.resendSahrepoint(incidentPK);
	}
}
