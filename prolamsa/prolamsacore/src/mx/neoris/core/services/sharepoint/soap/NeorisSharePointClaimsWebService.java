/**
 * 
 */
package mx.neoris.core.services.sharepoint.soap;

import de.hybris.platform.core.model.media.MediaModel;

import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.services.AddIncidentParameters;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author hector.castaneda
 * 
 */
public interface NeorisSharePointClaimsWebService
{
	String addNewClaim(AddIncidentParameters parameters, IncidentModel incidentModel) throws Exception;

	void addAttachmentToClaim(String claimId, MultipartFile file, IncidentModel incidentModel, int position) throws Exception;

	void addAttachmentToClaimMedia(String claimId, MediaModel file, IncidentModel incidentModel, int position) throws Exception;
}
