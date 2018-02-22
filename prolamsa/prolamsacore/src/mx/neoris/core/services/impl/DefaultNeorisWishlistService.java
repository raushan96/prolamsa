/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.services.NeorisWishlistService;


/**
 * @author christian.loredo
 * 
 */
public class DefaultNeorisWishlistService implements NeorisWishlistService
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Override
	public List<Wishlist2Model> getWishlistForUserAndB2BUnit(final UserModel _user, final B2BUnitModel _unit)
	{

		final List<Wishlist2Model> validWishlist = new ArrayList<Wishlist2Model>();

		final String queryString = "SELECT {" + Wishlist2Model.PK + "} FROM {" + Wishlist2Model._TYPECODE + "} WHERE {"
				+ Wishlist2Model.USER + "} = ?user AND {" + Wishlist2Model.B2BUNIT + "} = ?unit " + " ORDER BY {"
				+ Wishlist2Model.CREATIONTIME + "} desc ";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("user", _user);
		query.addQueryParameter("unit", _unit);

		final SearchResult<Wishlist2Model> result = flexibleSearchService.search(query);

		if (result != null && result.getResult() != null && result.getResult().size() > 0)
		{
			for (final Wishlist2Model eachWishlist : result.getResult())
			{
				if (isWishlistValidOnCurrentStore(eachWishlist))
				{
					validWishlist.add(eachWishlist);
				}
			}
		}

		return validWishlist;
	}

	@Override
	public Boolean getExistWishlist(final String _name, final UserModel _user, final B2BUnitModel _unit)
	{
		Boolean exist = false;

		final String queryString = "SELECT {" + Wishlist2Model.PK + "} FROM {" + Wishlist2Model._TYPECODE + "} WHERE {"
				+ Wishlist2Model.USER + "} = ?user AND {" + Wishlist2Model.B2BUNIT + "} = ?unit  AND {" + Wishlist2Model.NAME
				+ "} = ?name";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("user", _user);
		query.addQueryParameter("unit", _unit);
		query.addQueryParameter("name", _name);

		final SearchResult<Wishlist2Model> result = flexibleSearchService.search(query);

		final List<Wishlist2Model> validWishlist = new ArrayList<Wishlist2Model>();

		if (result != null && result.getResult() != null && result.getResult().size() > 0)
		{
			for (final Wishlist2Model eachWishlist : result.getResult())
			{
				if (isWishlistValidOnCurrentStore(eachWishlist))
				{
					validWishlist.add(eachWishlist);
				}
			}
		}

		if (validWishlist.size() > 0)
		{
			exist = true;
		}

		return exist;
	}

	@Override
	public Boolean getExistTemplate(final String _orderCode)
	{
		Boolean exist = false;

		final String queryString = "SELECT {" + Wishlist2Model.PK + "} FROM {" + Wishlist2Model._TYPECODE + "} WHERE {"
				+ Wishlist2Model.ORDERCODE + "} = ?orderCode";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("orderCode", _orderCode);

		final SearchResult<Wishlist2Model> result = flexibleSearchService.search(query);

		final List<Wishlist2Model> validWishlist = new ArrayList<Wishlist2Model>();

		if (result != null && result.getResult() != null && result.getResult().size() > 0)
		{
			for (final Wishlist2Model eachWishlist : result.getResult())
			{
				if (isWishlistValidOnCurrentStore(eachWishlist))
				{
					validWishlist.add(eachWishlist);
				}
			}
		}

		if (validWishlist.size() > 0)
		{
			exist = true;
		}

		return exist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisWishlistService#getWishlistEntry(de.hybris.platform.wishlist2.model.Wishlist2Model)
	 */
	@Override
	public List<Wishlist2EntryModel> getWishlistEntry(final String wishlist)
	{
		final String queryString = "SELECT {" + Wishlist2EntryModel.PK + "} FROM {" + Wishlist2EntryModel._TYPECODE + "} WHERE {"
				+ Wishlist2EntryModel.WISHLIST + "} = ?wishlist ORDER BY {" + Wishlist2EntryModel.CREATIONTIME + "}";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("wishlist", wishlist);

		final SearchResult<Wishlist2EntryModel> result = flexibleSearchService.search(query);

		return result.getResult();
	}

	@Override
	public List<Wishlist2Model> getWishlistForOrderCodeAndB2BUnit(final String orderCode, final B2BUnitModel currentB2BUnit)
	{
		final String queryString = "SELECT {" + Wishlist2Model.PK + "} FROM {" + Wishlist2Model._TYPECODE + "} WHERE {"
				+ Wishlist2Model.ORDERCODE + "} = ?code AND {" + Wishlist2Model.B2BUNIT + "} = ?unit";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", orderCode);
		query.addQueryParameter("unit", currentB2BUnit);

		final SearchResult<Wishlist2Model> result = flexibleSearchService.search(query);

		return result.getResult();
	}

	@Override
	public List<Wishlist2Model> getWishlistForNameAndB2BUnit(final String name, final B2BUnitModel currentB2BUnit)
	{
		final String queryString = "SELECT {" + Wishlist2Model.PK + "} FROM {" + Wishlist2Model._TYPECODE + "} WHERE {"
				+ Wishlist2Model.NAME + "} = ?name AND {" + Wishlist2Model.B2BUNIT + "} = ?unit";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("name", name);
		query.addQueryParameter("unit", currentB2BUnit);

		final SearchResult<Wishlist2Model> result = flexibleSearchService.search(query);

		return result.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.NeorisWishlistService#getWishlistForUserAndAllB2BUnits(de.hybris.platform.core.model.user
	 * .UserModel, java.util.List)
	 */
	@Override
	public List<Wishlist2Model> getWishlistForUserAndAllB2BUnits(final UserModel user, final List<B2BUnitModel> listUnit)
	{
		final List<Wishlist2Model> validWishlist = new ArrayList<Wishlist2Model>();

		final String queryString = "SELECT {" + Wishlist2Model.PK + "} FROM {" + Wishlist2Model._TYPECODE + "} WHERE {"
				+ Wishlist2Model.USER + "} = ?user AND {" + Wishlist2Model.B2BUNIT + "} IN (?listUnit) " + " ORDER BY {"
				+ Wishlist2Model.CREATIONTIME + "} desc ";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("user", user);
		query.addQueryParameter("listUnit", listUnit);

		final SearchResult<Wishlist2Model> result = flexibleSearchService.search(query);

		if (result != null && result.getResult() != null && result.getResult().size() > 0)
		{
			for (final Wishlist2Model eachWishlist : result.getResult())
			{
				if (isWishlistValidOnCurrentStore(eachWishlist))
				{
					validWishlist.add(eachWishlist);
				}
			}
		}

		return validWishlist;
	}

	protected boolean isWishlistValidOnCurrentStore(final Wishlist2Model wishlistModel)
	{
		final BaseStoreModel currentBaseStoreModel = baseStoreService.getCurrentBaseStore();

		final List<Wishlist2EntryModel> entries = wishlistModel.getEntries();

		// if the wishlist is null or has no entries then is not valid
		if (entries == null || entries.size() == 0)
		{
			return false;
		}

		final ProductModel productModel = entries.get(0).getProduct();

		// if at least one of the products in the entries is null then the wishlist is not valid
		if (productModel == null)
		{
			return false;
		}

		//if the catalog has no the id of the current store then the wishlist is not valid
		final String catalogId = productModel.getCatalogVersion().getCatalog().getId();
		if (!catalogId.contains(currentBaseStoreModel.getUid()))
		{
			return false;
		}

		return true;
	}
}
