/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.daos.CatalogVersionDao;
import de.hybris.platform.catalog.impl.DefaultCatalogService;
import de.hybris.platform.catalog.impl.DefaultCatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.impl.DefaultFlexibleSearchService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;
import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.NeorisB2BUnitSettingByStoreModel;
import mx.neoris.core.model.ProductVisibilityModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.services.NeorisProductVisibilityService;


/**
 * @author e-lacantu
 * 
 */
public class DefaultNeorisProductVisibilityService implements NeorisProductVisibilityService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "catalogService")
	private CatalogService catalogService;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@Resource(name = "catalogVersionDao")
	private CatalogVersionDao catalogVersionDao;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;


	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getProductByCode(java.lang.String)
	 */
	@Override
	public ProlamsaProductModel getProductByCode(final String code) throws Exception
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {" + ProlamsaProductModel.PK + "} FROM {"
				+ ProlamsaProductModel._TYPECODE + "} WHERE {" + ProlamsaProductModel.CODE + "} =" + code);


		if (flexibleSearchService == null)
		{
			flexibleSearchService = new DefaultFlexibleSearchService();
		}


		final SearchResult<ProlamsaProductModel> result = flexibleSearchService.search(fQuery);

		if (result.getResult() != null)
		{
			return result.getResult().get(0);
		}
		else
		{
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getProductByAttribute(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public SearchResult<ProlamsaProductModel> getProductByAttribute(final String baseStore, final Map<String, Object> mapAttribute)
			throws Exception
	{

		if (catalogService == null)
		{
			catalogService = new DefaultCatalogService();
			catalogVersionService = new DefaultCatalogVersionService();
			catalogVersionService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");
		}
		else
		{
			catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");
		}



		if (flexibleSearchService == null)
		{
			flexibleSearchService = new DefaultFlexibleSearchService();
		}

		final StringBuilder query = new StringBuilder();
		query.append("SELECT {p." + ProlamsaProductModel.PK + "} FROM {" + ProlamsaProductModel._TYPECODE + " AS p JOIN ");
		query.append(ProductLocation._TYPECODE + " AS l  ON {p." + ProlamsaProductModel.LOCATION + "} = {l.pk} } ");

		query.append("WHERE 1=1  ");

		//LOCATIONS
		@SuppressWarnings("unchecked")
		final List<ProductLocation> locations = (List<ProductLocation>) mapAttribute.get("location");
		if (locations.size() > 0)
		{
			String listLocations = "";
			for (final ProductLocation location : locations)
			{
				listLocations = listLocations + "'" + location.getCode() + "',";
			}
			listLocations = listLocations.substring(0, listLocations.lastIndexOf(","));
			query.append(" AND {l.code} IN (" + listLocations + ") ");
		}

		//FAMILY
		@SuppressWarnings("unchecked")
		final List<String> families = (List<String>) mapAttribute.get("family");
		if (families.size() > 0)
		{
			String listFamily = "";
			for (final String family : families)
			{
				listFamily = listFamily + "'" + family + "',";
			}
			listFamily = listFamily.substring(0, listFamily.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.FAMILY + "} IN (" + listFamily + ") ");
		}


		//SECTION
		@SuppressWarnings("unchecked")
		final List<String> sections = (List<String>) mapAttribute.get("section");
		if (sections.size() > 0)
		{
			String listSection = "";
			for (final String section : sections)
			{
				listSection = listSection + "'" + section + "',";
			}
			listSection = listSection.substring(0, listSection.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.SECTION + "} IN (" + listSection + ") ");
		}


		//MATERIAL TYPE
		@SuppressWarnings("unchecked")
		final List<String> materialTypes = (List<String>) mapAttribute.get("materialType");

		if (materialTypes.size() > 0)
		{
			String listMaterialTypes = "";
			for (final String materialType : materialTypes)
			{
				listMaterialTypes = listMaterialTypes + "'" + materialType + "',";
			}

			listMaterialTypes = listMaterialTypes.substring(0, listMaterialTypes.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.MATERIALTYPE + "} IN (" + listMaterialTypes + ") ");
		}


		//STEEL
		@SuppressWarnings("unchecked")
		final List<String> steels = (List<String>) mapAttribute.get("steel");
		if (steels.size() > 0)
		{
			String listSteel = "";
			for (final String steel : steels)
			{
				listSteel = listSteel + "'" + steel + "',";
			}
			listSteel = listSteel.substring(0, listSteel.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.STEEL + "} IN (" + listSteel + ") ");
		}


		//QUALITY
		@SuppressWarnings("unchecked")
		final List<String> qualities = (List<String>) mapAttribute.get("quality");
		if (qualities.size() > 0)
		{
			String listQuality = "";
			for (final String quality : qualities)
			{
				listQuality = listQuality + "'" + quality + "',";
			}
			listQuality = listQuality.substring(0, listQuality.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.QUALITY + "} IN (" + listQuality + ") ");
		}


		//FINISH
		@SuppressWarnings("unchecked")
		final List<String> finishes = (List<String>) mapAttribute.get("finish");
		if (finishes.size() > 0)
		{
			String listFinish = "";
			for (final String finish : finishes)
			{
				listFinish = listFinish + "'" + finish + "',";
			}
			listFinish = listFinish.substring(0, listFinish.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.FINISH + "} IN (" + listFinish + ") ");
		}


		//COVERING
		@SuppressWarnings("unchecked")
		final List<String> coverings = (List<String>) mapAttribute.get("covering");
		if (coverings.size() > 0)
		{
			String listCovering = "";
			for (final String covering : coverings)
			{
				listCovering = listCovering + "'" + covering + "',";
			}
			listCovering = listCovering.substring(0, listCovering.lastIndexOf(","));
			query.append(" AND {p." + ProlamsaProductModel.COVERING + "} IN (" + listCovering + ") ");
		}

		final String Y = "Y";

		query.append(" AND {p." + ProlamsaProductModel.APPROVALVISIBILITY + "} = '" + Y + "' ");


		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);

		//return getFlexibleSearchService().search(strQuery);
		return flexibleSearchService.search(strQuery);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getAllProductVisibility(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public SearchResult<ProductVisibilityModel> getAllProductVisibility(final String baseStore) throws Exception
	{


		catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");

		final StringBuilder query = new StringBuilder();
		query.append("SELECT {p." + ProductVisibilityModel.PK + "} FROM {" + ProductVisibilityModel._TYPECODE + " AS p } ");

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);

		//return getFlexibleSearchService().search(strQuery);
		return flexibleSearchService.search(strQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getProductVisibilityByCode(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ProductVisibilityModel getProductVisibilityByCode(final String baseStore, final String code) throws Exception
	{
		catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");

		final StringBuilder query = new StringBuilder();
		query.append("SELECT {p." + ProductVisibilityModel.PK + "} FROM {" + ProductVisibilityModel._TYPECODE + " as p } ");
		query.append("WHERE {p." + ProductVisibilityModel.CODE + "} =?code ");

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);
		strQuery.addQueryParameter("code", code);

		if (flexibleSearchService.search(strQuery).getResult() != null)
		{
			return (ProductVisibilityModel) flexibleSearchService.search(strQuery).getResult().get(0);
		}
		else
		{
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getProductVisibilityByCategory(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ProductVisibilityModel getProductVisibilityByCategory(final String baseStore, final String category) throws Exception
	{
		catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");

		final StringBuilder query = new StringBuilder();
		query.append("SELECT {p." + ProductVisibilityModel.PK + "} FROM {" + ProductVisibilityModel._TYPECODE + " as p } ");
		query.append("WHERE {p." + ProductVisibilityModel.CATEGORY + "} =?category ");

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);
		strQuery.addQueryParameter("category", category);

		if (flexibleSearchService.search(strQuery).getResult() != null)
		{
			return (ProductVisibilityModel) flexibleSearchService.search(strQuery).getResult().get(0);
		}
		else
		{
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getB2BUnitByIndustry(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public SearchResult<B2BUnitModel> getB2BUnitByIndustry(final String baseStore, final Map<String, Object> mapAttribute)
			throws Exception
	{
		Assert.assertNotNull("Base store cannot be null", baseStore);

		catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");
		final BaseStoreModel baseStoreModel = baseStoreService.getBaseStoreForUid(baseStore);

		final StringBuilder query = new StringBuilder();
		final StringBuilder whereClause = new StringBuilder();

		query.append("SELECT {b." + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE + " AS b ");
		query.append("JOIN B2BUnits2BaseStores AS b2br ");
		query.append("ON {b." + B2BUnitModel.PK + "} = {b2br.source} ");

		whereClause.append("WHERE {b2br.target} = ?baseStorePk ");

		//INDUSTRY
		@SuppressWarnings("unchecked")
		final List<String> industries = (List<String>) mapAttribute.get("industry");

		if (industries.size() > 0)
		{
			String listIndustries = "";
			for (final String industry : industries)
			{
				listIndustries = listIndustries + "'" + industry + "',";
			}

			listIndustries = listIndustries.substring(0, listIndustries.lastIndexOf(","));

			query.append("JOIN " + NeorisB2BUnitSettingByStoreModel._TYPECODE + " AS nss1 ");
			query.append("ON {b." + B2BUnitModel.INDUSTRYFORSTORE + "} LIKE CONCAT('%', CONCAT({nss1.pk}, '%')) ");
			whereClause.append("AND {nss1.storeId} = ?baseStoreUid ");
			whereClause.append("AND {nss1.setting} IN (" + listIndustries + ") ");
		}

		//SALES REP
		@SuppressWarnings("unchecked")
		final List<String> salesReps = (List<String>) mapAttribute.get("salesRep");

		if (salesReps.size() > 0)
		{
			String listSalesReps = "";
			for (final String salesRep : salesReps)
			{
				listSalesReps = listSalesReps + "'" + salesRep + "',";
			}

			listSalesReps = listSalesReps.substring(0, listSalesReps.lastIndexOf(","));

			query.append("JOIN " + NeorisB2BUnitSettingByStoreModel._TYPECODE + " AS nss2 ");
			query.append("ON {b." + B2BUnitModel.SALESREPFORSTORE + "} LIKE CONCAT('%', CONCAT({nss2.pk}, '%')) ");
			whereClause.append("AND {nss2.storeId} = ?baseStoreUid ");
			whereClause.append("AND {nss2.setting} IN (" + listSalesReps + ") ");
		}

		//SALES AREA
		@SuppressWarnings("unchecked")
		final List<String> salesAreas = (List<String>) mapAttribute.get("salesArea");

		if (salesAreas.size() > 0)
		{
			String listSalesAreas = "";
			for (final String salesArea : salesAreas)
			{
				listSalesAreas = listSalesAreas + "'" + salesArea + "',";
			}

			listSalesAreas = listSalesAreas.substring(0, listSalesAreas.lastIndexOf(","));

			query.append("JOIN " + NeorisB2BUnitSettingByStoreModel._TYPECODE + " AS nss3 ");
			query.append("ON {b." + B2BUnitModel.SALESAREAFORSTORE + "} LIKE CONCAT('%', CONCAT({nss3.pk}, '%')) ");
			whereClause.append("AND {nss3.storeId} = ?baseStoreUid ");
			whereClause.append("AND {nss3.setting} IN (" + listSalesAreas + ") ");
		}

		//SALES AREA WILDCARDS
		@SuppressWarnings("unchecked")
		final List<String> salesAreasWildcards = (List<String>) mapAttribute.get("salesAreaWilcards");

		if (salesAreasWildcards.size() > 0)
		{
			String listSalesAreaWildcards = "";
			for (final String wildcard : salesAreasWildcards)
			{
				listSalesAreaWildcards = listSalesAreaWildcards + "'" + wildcard + "',";
			}

			listSalesAreaWildcards = listSalesAreaWildcards.substring(0, listSalesAreaWildcards.lastIndexOf(","));

			query.append("JOIN " + NeorisB2BUnitSettingByStoreModel._TYPECODE + " AS nss4 ");
			query.append("ON {b." + B2BUnitModel.SALESAREAWILDCARDVISIBILITYFORSTORE + "} LIKE CONCAT('%', CONCAT({nss4.pk}, '%')) ");
			whereClause.append("AND {nss4.storeId} = ?baseStoreUid ");
			whereClause.append("AND {nss4.setting} IN (" + listSalesAreaWildcards + ") ");
		}

		query.append("} ");

		query.append(whereClause.toString());

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);
		strQuery.addQueryParameter("baseStoreUid", baseStore);
		strQuery.addQueryParameter("baseStorePk", baseStoreModel.getPk());

		return flexibleSearchService.search(strQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.NeorisProductVisibilityService#getUserGroupByName(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public SearchResult<UserGroupModel> getUserGroupByName(final String baseStore, final String userGroup) throws Exception
	{
		catalogService.setSessionCatalogVersion(baseStore + "ProductCatalog", "Online");

		final StringBuilder query = new StringBuilder();
		query.append("SELECT {u." + UserGroupModel.PK + "} FROM {" + UserGroupModel._TYPECODE + " as u } ");
		query.append("WHERE {u." + UserGroupModel.UID + "} =?userGroup ");

		final FlexibleSearchQuery strQuery = new FlexibleSearchQuery(query);
		strQuery.addQueryParameter("userGroup", userGroup);

		if (flexibleSearchService.search(strQuery).getResult() != null)
		{
			return flexibleSearchService.search(strQuery);
		}
		else
		{
			return null;
		}
	}


	public Collection<CatalogVersionModel> findCatalogVersions(final String catalogId, final String catalogVersionName)
	{
		ServicesUtil.validateParameterNotNull(catalogId, "catalog Id must not be null");
		ServicesUtil.validateParameterNotNull(catalogVersionName, "catalog Id must not be null");
		//TODO: result should only contains ONE element, not more
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT {catalogVersion.");
		sql.append(CatalogVersionModel.PK);
		sql.append("} FROM {");
		sql.append(CatalogVersionModel._TYPECODE);
		sql.append(" AS catalogVersion JOIN ");
		sql.append(CatalogModel._TYPECODE);
		sql.append(" as catalog ON {catalog.");
		sql.append(CatalogModel.PK);
		sql.append("}={");
		sql.append(CatalogVersionModel.CATALOG);
		sql.append("} } WHERE {catalog.");
		sql.append(CatalogModel.ID);
		sql.append("}=?id AND {catalogVersion.");
		sql.append(CatalogVersionModel.VERSION);
		sql.append("}=?cv");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameter("id", catalogId);
		query.addQueryParameter("cv", catalogVersionName);

		final SearchResult<CatalogVersionModel> result = flexibleSearchService.search(query);

		return result.getResult();
	}

}
