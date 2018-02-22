/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.impl.DefaultB2BUnitService;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.ClassMismatchException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.session.Session;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisB2BUnitService extends DefaultB2BUnitService
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisB2BUnitService.class);
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Override
	public void updateBranchInSession(final Session session, final UserModel currentUser)
	{
		// do a super to keep existing implementation
		super.updateBranchInSession(session, currentUser);

		if (!(currentUser instanceof B2BCustomerModel))
		{
			return;
		}

		// get all the B2BUnits assigned to the customer via groups
		final List<B2BUnitModel> b2bUnits = getB2BUnitModelsFromCustomer((B2BCustomerModel) currentUser);

		// get the existing branch assignments
		@SuppressWarnings("unchecked")
		final Set<B2BUnitModel> currentBranch = (Set<B2BUnitModel>) getSessionService().getAttribute("branch");
		// add the B2BUnits assigned via groups to the branch collection
		b2bUnits.addAll(currentBranch);

		// update the branch session attribute with the new info
		getSessionService().setAttribute("branch", b2bUnits);
	}

	// get all the B2BUnitModels from the customer via usergroup assignment
	private List<B2BUnitModel> getB2BUnitModelsFromCustomer(final B2BCustomerModel b2bCustomer)
	{
		final List<B2BUnitModel> col = new ArrayList<B2BUnitModel>();

		for (final PrincipalGroupModel group : b2bCustomer.getGroups())
		{
			if (group instanceof B2BUnitModel)
			{
				final B2BUnitModel b2bUnit = (B2BUnitModel) group;
				col.add(b2bUnit);
			}
		}

		return col;
	}
	
	@Override
	public B2BUnitModel getUnitForUid(final String uid)
	{
		return sessionService.executeInLocalView(new SessionExecutionBody(){
			@Override
			public B2BUnitModel execute()
			{
				B2BUnitModel unit;
				try {
					unit = (B2BUnitModel) getUserService().getUserGroupForUID(uid,
							B2BUnitModel.class);
				} catch (UnknownIdentifierException localUnknownIdentifierException) {
					unit = null;
				} catch (ClassMismatchException localClassMismatchException) {
					unit = null;
				}
				//Esto no manda el error cuando no se tiene asignado un b2bUnit para el store
				catch (Exception e)
				{
					unit = null;
					LOG.error("Error getUnitForUid: " + e.getMessage());
				}
				
				return unit;
			}
		}, getUserService().getAdminUser());
	}
}
