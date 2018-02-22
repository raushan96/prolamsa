/**
 * 
 */
package mx.neoris.core.search.solrfacetsearch;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.services.impl.DefaultNeorisB2BUnitService;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisAllCategoriesSetFacetInjector
{

	static final String B2BUNIT_SLOT = "rootunit";

	@Resource(name = "userService")
	UserService userService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "defaultNeorisB2BUnitService")
	private DefaultNeorisB2BUnitService defaultNeorisB2BUnitService;


	@Resource(name = "sessionService")
	private SessionService sessionService;

	private List<String> categoryList;
	private String categoryPatternName;

	public void injectFacetsToQuery(final SearchQuery searchQuery)
	{
		final String patternName = getCategoryPatternName();
		Boolean hasCategoryProductDefined = false;

		// Visibility for B2BUnit
		final B2BUnitModel b2bUnit = getRootUnit();

		final Collection<CategoryModel> categories = b2bUnit.getAccessibleCategories();

		for (final CategoryModel eachCategory : categories)
		{
			final Integer index = eachCategory.getCode().indexOf(patternName);

			// check by pattern
			if (index != -1)
			{
				final String categoryName = eachCategory.getCode();//.substring(0, index);
				searchQuery.addFacetValue("allCategoriesSet", categoryName);
				// mark a product category group found
				hasCategoryProductDefined = true;
			}

			// check on list
			if (getCategoryList().contains(eachCategory.getCode()))
			{
				// mark a product category group found
				hasCategoryProductDefined = true;
			}
		}

		// Visibility for users members of Prolamsa-Group or Axis-Group
		final UserModel currentUser = getCurrentUser();

		final Set<PrincipalGroupModel> col = currentUser.getAllGroups();
		for (final PrincipalGroupModel eachGroup : col)
		{
			if (getCategoryList().contains(eachGroup.getUid()))
			{
				// mark a product category group found
				hasCategoryProductDefined = true;
			}
		}

		// if not found, assign a non existing category so no products would be found
		if (!hasCategoryProductDefined)
		{
			searchQuery.addFacetValue("allCategoriesSet", "NONE_CATEGORY");
		}
	}

	public UserModel getCurrentUser()
	{
		return userService.getCurrentUser();
	}

	public UnitModel getCurrentUnit()
	{
		UnitModel currentUnit = sessionService.getAttribute("productUnit");

		// if current unit is null, assign the default defined on the baseStore
		if (currentUnit == null)
		{
			final BaseStoreModel baseStore = getCurrentBaseStore();

			if (baseStore != null)
			{
				currentUnit = baseStore.getUnit();
			}

			// if no default unit, select the first one from the list
			if (currentUnit == null)
			{
				final List<UnitModel> list = this.getAllUnits();
				if (list.size() > 0)
				{
					currentUnit = list.get(0);
				}
			}
		}

		return currentUnit;
	}

	public BaseStoreModel getCurrentBaseStore()
	{
		return baseStoreService.getCurrentBaseStore();
	}

	public List<UnitModel> getAllUnits()
	{
		final BaseStoreModel baseStore = getCurrentBaseStore();

		final List<UnitModel> result = baseStore.getUnits();

		return result;
	}


	public B2BUnitModel getRootUnit()
	{
		return sessionService.getAttribute(B2BUNIT_SLOT);
	}

	/**
	 * @return the categoryPatternName
	 */
	public String getCategoryPatternName()
	{
		return categoryPatternName;
	}

	/**
	 * @param categoryPatternName
	 *           the categoryPatternName to set
	 */
	public void setCategoryPatternName(final String categoryPatternName)
	{
		this.categoryPatternName = categoryPatternName;
	}

	/**
	 * @return the categoryList
	 */
	public List<String> getCategoryList()
	{
		return categoryList;
	}

	/**
	 * @param categoryList
	 *           the categoryList to set
	 */
	public void setCategoryList(final List<String> categoryList)
	{
		this.categoryList = categoryList;
	}
}
