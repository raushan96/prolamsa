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
import mx.neoris.core.model.NoticeModel;
import mx.neoris.core.services.AddIncidentParameters;
import mx.neoris.core.services.AddNoticeParameters;
import mx.neoris.core.services.IncidentSearchParameters;
import mx.neoris.facades.document.data.NoticeData;

/**
 * @author e-jecarrilloi
 *
 */
public interface NeorisNoticesFacade {
	
	public NoticeModel getNotice(String code);
	
	public boolean deleteNotice(String code);

	public NoticeData addNotice(AddNoticeParameters parameters);

	public List<NoticeData> getNoticesForCurrentStore();
}
