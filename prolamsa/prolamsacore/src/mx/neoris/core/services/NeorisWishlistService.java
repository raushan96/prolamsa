/**
 * 
 */

/**
 * @author christian.loredo
 *
 */

package mx.neoris.core.services;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.List;


public interface NeorisWishlistService
{
	List<Wishlist2Model> getWishlistForUserAndB2BUnit(UserModel user, B2BUnitModel unit);

	List<Wishlist2Model> getWishlistForUserAndAllB2BUnits(UserModel user, List<B2BUnitModel> listUnit);

	List<Wishlist2EntryModel> getWishlistEntry(String wishlist);

	Boolean getExistWishlist(String name, UserModel user, B2BUnitModel unit);

	Boolean getExistTemplate(final String orderCode);

	List<Wishlist2Model> getWishlistForOrderCodeAndB2BUnit(String orderCode, B2BUnitModel currentB2BUnit);

	List<Wishlist2Model> getWishlistForNameAndB2BUnit(final String name, final B2BUnitModel currentB2BUnit);

}
