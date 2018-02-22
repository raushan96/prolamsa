package mx.neoris.storefront.forms;

import de.hybris.platform.commercefacades.user.data.AlertConfigurationData;

import java.io.File;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;


/**
 * Pojo for Invoice Search form.
 */
public class AlertsConfigurationForm
{

	private List<AlertConfigurationData> alertsConfigurations;
	private String b2bUnitId;
	
	public String getB2bUnitId() {
		return b2bUnitId;
	}
	public void setB2bUnitId(String b2bUnitId) {
		this.b2bUnitId = b2bUnitId;
	}
	public List<AlertConfigurationData> getAlertsConfigurations() {
		return alertsConfigurations;
	}
	public void setAlertsConfigurations(List<AlertConfigurationData> alertsConfigurations) {
		this.alertsConfigurations = alertsConfigurations;
	}
	
	
}
