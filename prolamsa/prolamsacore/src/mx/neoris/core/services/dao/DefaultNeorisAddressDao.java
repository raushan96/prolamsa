/**
 * 
 */
package mx.neoris.core.services.dao;


import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.daos.impl.DefaultAddressDao;

import java.util.List;

import javax.annotation.Resource;


/**
 * @author LilianaOW
 * 
 */
public class DefaultNeorisAddressDao extends DefaultAddressDao implements NeorisAddressDao
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	/*
	 * sortBy - should be code or name sortType - ASC or DESC
	 */
	public List<AddressModel> getCurrentCustomerB2BUnitAddresses(final B2BCustomerModel b2bCustomer, final String sortBy,
			final String sortType)
	{

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {addr.pk} FROM { PrincipalGroupRelation as pgr ");
		sb.append("JOIN B2BCustomer as c    ON {pgr.source} = {c.pk} ");
		sb.append("JOIN B2BUnit as unit  ON {pgr.target} = {unit.pk} ");
		sb.append("RIGHT JOIN Address as addr ON {unit.pk} = {addr.owner}} ");
		sb.append("WHERE  {c.uid} = ?userUid ");
		sb.append("ORDER BY {unit.uid},{addr." + sortBy + "} " + sortType);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameter("userUid", b2bCustomer.getUid());

		return flexibleSearchService.<AddressModel> search(query).getResult();
	}

	public List<AddressModel> getCurrentCustomerB2BUnitAddresses(final String selectedUnitUid, final String sortBy,
			final String sortType)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Select {addr.pk} FROM {B2BUnit as unit ");
		sb.append("JOIN Address as addr ON {unit.pk} = {addr.owner}} ");
		sb.append("where {unit.uid} =  ?uid ");
		sb.append("ORDER BY {unit.uid},{addr." + sortBy + "} " + sortType);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameter("uid", selectedUnitUid);

		return flexibleSearchService.<AddressModel> search(query).getResult();
	}

	@Override
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	@Override
	protected FlexibleSearchService getFlexibleSearchService()
	{
		return this.flexibleSearchService;
	}
}
