/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.impex.model.cronjob.ImpExImportCronJobModel;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


/**
 * @author fdeutsch
 * 
 */
public class NeorisImportService extends de.hybris.platform.servicelayer.impex.impl.DefaultImportService
{
	@Resource(name = "userService")
	UserService userService;

	Boolean useAdminUser = false;

	@Override
	protected void configureCronJob(final ImpExImportCronJobModel cronJob, final ImportConfig config)
	{
		super.configureCronJob(cronJob, config);

		if (getUseAdminUser())
		{
			final UserModel user = userService.getAdminUser();
			cronJob.setSessionUser(user);
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the useAdminUser
	 */
	public Boolean getUseAdminUser()
	{
		return useAdminUser;
	}

	/**
	 * @param useAdminUser
	 *           the useAdminUser to set
	 */
	public void setUseAdminUser(final Boolean useAdminUser)
	{
		this.useAdminUser = useAdminUser;
	}
}
