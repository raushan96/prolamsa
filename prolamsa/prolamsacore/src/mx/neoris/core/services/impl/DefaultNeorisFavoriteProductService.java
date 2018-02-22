/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.core.services.FavoriteProductSearchParameters;
import mx.neoris.core.services.NeorisFavoriteProductService;
import mx.neoris.core.services.NeorisProductService;

import org.apache.log4j.Logger;


/**
 * @author e-hicastaneda
 * 
 */
public class DefaultNeorisFavoriteProductService implements NeorisFavoriteProductService
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisFavoriteProductService.class);

	public static final String PRODCUT_CATALOG = "ProductCatalog";
	public static final String CATALOG_VERSION = "Online";

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "customerProductReferenceService")
	private CustomerProductReferenceService customerProductReferenceService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "neorisProductService")
	private NeorisProductService neorisProductService;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Override
	public void cleanCustomerFavoriteProducts(final B2BUnitModel customer) throws Exception
	{
		LOG.info("Cleaning favorite products for customer: " + customer.getUid());

		if (customer.getFavoriteProducts() != null && customer.getFavoriteProducts().size() > 0)
		{
			final Set<ProlamsaProductModel> favoriteProductEmptySet = Collections.emptySet();
			customer.setFavoriteProducts(favoriteProductEmptySet);

			modelService.save(customer);
		}
	}

	@Override
	public String reloadCustomerFavoriteProducts(final B2BUnitModel customer, final ProductLocation location) throws Exception
	{
		LOG.info("Reloading favorite products for customer: " + customer.getUid());

		final String catalogId = baseStoreService.getCurrentBaseStore().getUid() + PRODCUT_CATALOG;

		final Map<String, CustomerProductReferenceModel> customerProductReference = customerProductReferenceService
				.getCustomerProductReferenceFor(customer, catalogId);

		final Set<ProlamsaProductModel> favoriteProductSet = new HashSet<ProlamsaProductModel>();

		// add current favorites
		for (final ProlamsaProductModel eachProductModelFav : customer.getFavoriteProducts())
		{
			favoriteProductSet.add(eachProductModelFav);
		}

		int newFavoriteProductsAdded = 0;

		if (customerProductReference != null)
		{
			for (final Map.Entry<String, CustomerProductReferenceModel> entry : customerProductReference.entrySet())
			{
				final String productBaseCode = entry.getKey();

				final List<ProlamsaProductModel> productList = neorisProductService.searchProductsByBaseCodeAndLocation(
						productBaseCode, location);

				if (productList != null)
				{
					// merge favorites
					for (final ProlamsaProductModel eachProductModel : productList)
					{
						if (!favoriteProductSet.contains(eachProductModel))
						{
							favoriteProductSet.add(eachProductModel);
							newFavoriteProductsAdded++;
						}
					}
				}
			}

			customer.setFavoriteProducts(favoriteProductSet);
			modelService.save(customer);
		}

		// return number of new favorite products added
		return String.valueOf(newFavoriteProductsAdded);
	}

	@Override
	public SearchPageData<ProlamsaProductModel> getPagedFavoriteProducts(final FavoriteProductSearchParameters searchParameters)
			throws Exception
	{
		LOG.info("Getting favorite products for customer: " + searchParameters.getCustomer());

		final StringBuilder query = new StringBuilder();
		final StringBuilder sortQuery = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		// general query
		query.append("SELECT {p." + ProlamsaProductModel.PK + "} FROM {" + ProlamsaProductModel._TYPECODE + " as p ");
		query.append("JOIN B2BUnits2ProlamsaProducts as b2p ON ");
		query.append("{p.pk} = {b2p.target} ");
		query.append("JOIN " + B2BUnitModel._TYPECODE + " as b ON ");
		query.append("{b." + B2BUnitModel.PK + "} = {b2p.source}} ");

		// where clause
		query.append("WHERE {b." + B2BUnitModel.PK + "} = ?customer ");
		query.append("AND {p." + ProlamsaProductModel.CATALOGVERSION + "} = ?catalogVersion ");


		// adding customer parameter
		queryParams.put("customer", searchParameters.getCustomer().getPk());
		// adding catalog version parameter
		queryParams.put("catalogVersion", searchParameters.getCatalogVersion().getPk());

		// sort by and sort order
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append("ORDER BY {p.");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");

			if (searchParameters.getSortOrder() != null)
			{
				sortQuery.append(searchParameters.getSortOrder());
			}
		}

		query.append(sortQuery);

		return pagedFlexibleSearchService.search(query.toString(), queryParams, searchParameters.getPageableData());
	}

	@Override
	public String removeFavoriteProductWith(final B2BUnitModel customer, final String productCode)
	{
		LOG.info("Removing favorite product with code: " + productCode + "and customer: " + customer.getUid());

		if (customer.getFavoriteProducts() != null && customer.getFavoriteProducts().size() > 0)
		{
			final Set<ProlamsaProductModel> newFavoriteProducts = new HashSet<ProlamsaProductModel>();

			for (final ProlamsaProductModel eachProductModel : customer.getFavoriteProducts())
			{
				if (!eachProductModel.getCode().equals(productCode))
				{
					newFavoriteProducts.add(eachProductModel);
				}
			}

			customer.setFavoriteProducts(newFavoriteProducts);

			// save model with new b2bunit assigned
			try
			{
				modelService.save(customer);
			}
			catch (final ModelSavingException e)
			{
				return "text.account.customerFavoriteProducts.remove.general.error";
			}
		}

		return null;
	}

	@Override
	public String addFavoriteProductWith(final B2BUnitModel customer, final String productCode, final String productCatalogId)
	{
		LOG.info("Adding favorite product with code: " + productCode + " customer: " + customer.getUid() + " catalog: "
				+ productCatalogId);

		// validate if b2b unit is already assigned to user
		boolean isAlreadyAdded = false;

		final Set<ProlamsaProductModel> favorites = customer.getFavoriteProducts();

		for (final ProlamsaProductModel productModel : favorites)
		{
			if (productModel.getCode().equals(productCode))
			{
				isAlreadyAdded = true;
				break;
			}
		}

		if (isAlreadyAdded)
		{
			return "text.account.customerFavoriteProducts.add.isAlreadyAdded.error";
		}

		// since previous validations passed... then assign product as favorite
		final Set<ProlamsaProductModel> newFavorites = new HashSet<ProlamsaProductModel>();

		for (final ProlamsaProductModel productModel : favorites)
		{
			newFavorites.add(productModel);
		}

		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(productCatalogId, CATALOG_VERSION);

		final ProlamsaProductModel product = (ProlamsaProductModel) neorisProductService.getProductForCode(catalogVersion,
				productCode);

		if (product == null)
		{
			return "text.account.customerFavoriteProducts.add.general.error";
		}

		newFavorites.add(product);
		customer.setFavoriteProducts(newFavorites);

		// save model with new b2bunit assigned
		try
		{
			modelService.save(customer);
		}
		catch (final ModelSavingException e)
		{
			return "text.account.customerFavoriteProducts.add.general.error";
		}

		return null;
	}

	@Override
	public List<ProlamsaProductModel> findFavoriteCandidateProducts(final FavoriteProductSearchParameters searchParameters)
			throws Exception
	{
		LOG.info("Searching favorite candidate products");

		final StringBuilder query = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		query.append("SELECT {p." + ProlamsaProductModel.PK + "} FROM {" + ProlamsaProductModel._TYPECODE + " as p} ");
		query.append("WHERE {p." + ProlamsaProductModel.CATALOGVERSION + "} = ?catalogVersion ");

		if (searchParameters.getLocation() != null)
		{
			query.append("AND {p." + ProlamsaProductModel.LOCATION + "} = ?location ");
		}

		if (searchParameters.getCode() != null && searchParameters.getCode().trim().length() > 0)
		{
			query.append("AND {p." + ProlamsaProductModel.CODE + "} LIKE (?code) ");

			if (searchParameters.getDescription() != null && searchParameters.getDescription().trim().length() > 0)
			{
				query.append("OR {p." + ProlamsaProductModel.DESCRIPTION + "} LIKE (?description) ");
			}
		}
		else
		{
			if (searchParameters.getDescription() != null && searchParameters.getDescription().trim().length() > 0)
			{
				query.append("AND {p." + ProlamsaProductModel.NAME + "} LIKE (?description) ");
			}
		}

		query.append("ORDER BY {p.code}");

		queryParams.put("code", "%" + searchParameters.getCode() + "%");
		queryParams.put("description", "%" + searchParameters.getDescription() + "%");
		queryParams.put("catalogVersion", searchParameters.getCatalogVersion().getPk());
		queryParams.put("location", searchParameters.getLocation());

		final SearchResult<ProlamsaProductModel> searchResult = flexibleSearchService.search(new FlexibleSearchQuery(query
				.toString(), queryParams));

		return searchResult.getResult();
	}


	@Override
	public List<ProlamsaProductModel> getFavoriteProducts(final FavoriteProductSearchParameters searchParameters) throws Exception
	{
		LOG.info("Getting favorite products for customer: " + searchParameters.getCustomer());

		final StringBuilder query = new StringBuilder();
		final StringBuilder sortQuery = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		// general query
		query.append("SELECT {p." + ProlamsaProductModel.PK + "} FROM {" + ProlamsaProductModel._TYPECODE + " as p ");
		query.append("JOIN B2BUnits2ProlamsaProducts as b2p ON ");
		query.append("{p.pk} = {b2p.target} ");
		query.append("JOIN " + B2BUnitModel._TYPECODE + " as b ON ");
		query.append("{b." + B2BUnitModel.PK + "} = {b2p.source}} ");

		// where clause
		query.append("WHERE {b." + B2BUnitModel.PK + "} = ?customer ");
		query.append("AND {p." + ProlamsaProductModel.CATALOGVERSION + "} = ?catalogVersion ");


		// adding customer parameter
		queryParams.put("customer", searchParameters.getCustomer().getPk());
		// adding catalog version parameter
		queryParams.put("catalogVersion", searchParameters.getCatalogVersion().getPk());

		// sort by and sort order
		if (searchParameters.getSortBy() != null)
		{
			sortQuery.append("ORDER BY {p.");
			sortQuery.append(searchParameters.getSortBy());
			sortQuery.append("} ");

			if (searchParameters.getSortOrder() != null)
			{
				sortQuery.append(searchParameters.getSortOrder());
			}
		}

		query.append(sortQuery);

		final SearchResult<ProlamsaProductModel> searchResult = flexibleSearchService.search(query.toString(), queryParams);

		return searchResult.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisFavoriteProductService#getFavoriteProductsCount(FavoriteProductSearchParameters
	 * searchParameters)
	 */
	@Override
	public long getFavoriteProductsCount(final FavoriteProductSearchParameters searchParameters)
	{

		LOG.info("Getting favorite products count for customer: " + searchParameters.getCustomer());

		final StringBuilder query = new StringBuilder();
		final Map<String, Serializable> queryParams = new HashMap<String, Serializable>();

		// general query
		query.append("SELECT {p." + ProlamsaProductModel.PK + "} FROM {" + ProlamsaProductModel._TYPECODE + " as p ");
		query.append("JOIN B2BUnits2ProlamsaProducts as b2p ON ");
		query.append("{p.pk} = {b2p.target} ");
		query.append("JOIN " + B2BUnitModel._TYPECODE + " as b ON ");
		query.append("{b." + B2BUnitModel.PK + "} = {b2p.source}} ");

		// where clause
		query.append("WHERE {b." + B2BUnitModel.PK + "} = ?customer ");
		query.append("AND {p." + ProlamsaProductModel.CATALOGVERSION + "} = ?catalogVersion ");


		// adding customer parameter
		queryParams.put("customer", searchParameters.getCustomer().getPk());
		// adding catalog version parameter
		queryParams.put("catalogVersion", searchParameters.getCatalogVersion().getPk());

		final SearchResult<ProlamsaProductModel> favoriteProducts = flexibleSearchService.search(new FlexibleSearchQuery(query
				.toString(), queryParams));

		if (favoriteProducts == null || favoriteProducts.getCount() == 0)
		{
			return 0;
		}
		else
		{
			return favoriteProducts.getCount();
		}
	}
}
