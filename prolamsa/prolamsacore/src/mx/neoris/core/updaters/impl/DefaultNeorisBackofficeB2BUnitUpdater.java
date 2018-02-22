/**
 * 
 */
package mx.neoris.core.updaters.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.security.PrincipalModel;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.services.NeorisService;
import mx.neoris.core.updaters.NeorisBackofficeB2BUnitUpdater;

import org.apache.log4j.Logger;


/**
 * @author christian.loredo
 * 
 */
public class DefaultNeorisBackofficeB2BUnitUpdater implements NeorisBackofficeB2BUnitUpdater
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisBackofficeB2BUnitUpdater.class);

	@Resource(name = "neorisService")
	private NeorisService neorisService;

	@Override
	public void updateWith(final B2BUnitModel b2bUnitModel, final String originalBackoffice) throws Exception
	{
		B2BCustomerModel b2bCustomerModel = null;

		if (originalBackoffice != null)
		{
			final String newBackoffice = b2bUnitModel.getBackOfficeAccount();
			if (!newBackoffice.equals(originalBackoffice))
			{
				b2bCustomerModel = neorisService.getCustomerByBackOfficeId(originalBackoffice);
				if (b2bCustomerModel != null)
				{
					removeB2BCustomerFromB2BUnit(b2bUnitModel, b2bCustomerModel);
				}
			}
		}

		b2bCustomerModel = neorisService.getCustomerByBackOfficeId(b2bUnitModel.getBackOfficeAccount());
		if (b2bCustomerModel != null)
		{
			addB2BCustomerToB2BUnit(b2bUnitModel, b2bCustomerModel);
		}
	}

	private void removeB2BCustomerFromB2BUnit(final B2BUnitModel b2bUnitModel, final B2BCustomerModel b2bCustomerModel)
	{
		final Set<PrincipalModel> members = b2bUnitModel.getMembers();
		final Set<PrincipalModel> newMembers = new HashSet<PrincipalModel>();

		if (members != null)
		{
			for (final PrincipalModel member : members)
			{
				if (member instanceof B2BCustomerModel)
				{
					if (!member.getUid().equals(b2bCustomerModel.getUid()))
					{
						newMembers.add(member);
					}
				}
				else
				{
					newMembers.add(member);
				}
			}

			b2bUnitModel.setMembers(newMembers);
		}
	}

	private void addB2BCustomerToB2BUnit(final B2BUnitModel b2bUnitModel, final B2BCustomerModel b2bCustomerModel)
	{
		final Set<PrincipalModel> members = b2bUnitModel.getMembers();
		final Set<PrincipalModel> newMembers = new HashSet<PrincipalModel>();

		if (members != null)
		{
			for (final PrincipalModel member : members)
			{
				newMembers.add(member);
			}
		}

		newMembers.add(b2bCustomerModel);
		b2bUnitModel.setMembers(newMembers);
	}

}
