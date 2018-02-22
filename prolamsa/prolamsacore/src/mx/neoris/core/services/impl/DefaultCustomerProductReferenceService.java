/**
 * 
 */
package mx.neoris.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.core.services.CustomerProductReferenceService;
import mx.neoris.facades.backorder.data.BackorderDetailData;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;


/**
 * @author e-lacantu
 * 
 */
public class DefaultCustomerProductReferenceService implements CustomerProductReferenceService
{
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService<B2BCustomerModel, B2BUnitModel> b2bCustomerService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.CustomerProductReferenceService#getWithCodeAndB2BUnit(java.lang.String,
	 * de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public CustomerProductReferenceModel getWithProductCodeAndB2BUnit(final String productCode, final B2BUnitModel unit,
			final String catalogId)
	{
		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p } ");
		queryString.append("WHERE {p." + CustomerProductReferenceModel.PRODUCTCODE + "} = '" + productCode + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = '" + catalogId + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = '" + unit.getPk() + "' ");

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(queryString.toString());

		if (result == null || result.getCount() == 0)
		{
			return null;
		}
		else
		{
			return result.getResult().get(0);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.CustomerProductReferenceService#getWithUnitCodesAndB2BUnit(java.util.List,
	 * de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public Map<String, CustomerProductReferenceModel> getWithUnitCodesAndB2BUnit(final List<String> codes,
			final B2BUnitModel unit, final String catalogId)
	{
		final Map<String, CustomerProductReferenceModel> mapResult = new HashMap<String, CustomerProductReferenceModel>();

		// if no codes defined, return an empty map
		if (codes.size() == 0)
		{
			return mapResult;
		}

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p } ");
		queryString.append("WHERE {p." + CustomerProductReferenceModel.CODE + "} in (?list) ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = ?unit ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = ?catalogId ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString());

		query.addQueryParameter("unit", unit.getPk());
		query.addQueryParameter("list", codes);
		query.addQueryParameter("catalogId", catalogId);

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(query);

		for (final CustomerProductReferenceModel eachRef : result.getResult())
		{
			mapResult.put(eachRef.getCode(), eachRef);
		}

		return mapResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.CustomerProductReferenceService#getProlamsaSkuFromSku(java.util.List,
	 * de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public List<String> getProlamsaSkuFromSku(final List<String> codes, final B2BUnitModel unit, final String catalogId)
	{
		final List<String> listResult = new ArrayList<String>();

		final Map<String, CustomerProductReferenceModel> mapResult = getWithUnitCodesAndB2BUnit(codes, unit, catalogId);

		for (final String dataCode : codes)
		{
			final CustomerProductReferenceModel reference = mapResult.get(dataCode);

			if (reference != null)
			{
				listResult.add(reference.getProductCode());
			}
			else
			{
				listResult.add(dataCode);
			}
		}

		return listResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.CustomerProductReferenceService#getClientUnitCodesAndB2BUnit(java.util.List,
	 * de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public Map<String, String> getClientUnitCodesAndB2BUnit(final List<String> codes, final B2BUnitModel unit,
			final String catalogId)
	{
		final Map<String, String> mapResult = new HashMap<String, String>();

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + ProductModel.CODE + "} AS PRODUCT_CODE, {c." + CustomerProductReferenceModel.CODE
				+ "} AS REFERENCE_CODE ");
		queryString.append("FROM {" + ProductModel._TYPECODE + " AS p JOIN " + CustomerProductReferenceModel._TYPECODE + " AS c ");
		queryString.append("ON {p." + ProductModel.PK + "} = {c." + CustomerProductReferenceModel.PRODUCTCODE + "}  }");
		queryString.append("WHERE {p." + ProductModel.CODE + "} in (?list) ");
		queryString.append("AND {c." + CustomerProductReferenceModel.CUSTOMER + "} = ?unit ");
		queryString.append("AND {c." + CustomerProductReferenceModel.CATALOGID + "} = ?catalogId ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString());

		query.setResultClassList(Lists.newArrayList(String.class, String.class));
		query.addQueryParameter("list", codes);
		query.addQueryParameter("unit", unit.getPk());
		query.addQueryParameter("catalogId", catalogId);


		final SearchResult<List<String>> searchResult = flexibleSearchService.search(query);

		final List<List<String>> result = searchResult.getResult();
		for (final List<String> row : result)
		{
			mapResult.put(row.get(0), row.get(1));
		}

		return mapResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.CustomerProductReferenceService#getFromProlamsaProductsAndB2BUnit(java.util.List,
	 * de.hybris.platform.b2b.model.B2BUnitModel)
	 */
	@Override
	public Map<String, CustomerProductReferenceModel> getCustomerProductReferenceFor(final List<String> prolamsaProductCodes,
			final B2BUnitModel unit, final String catalogId, final List<ProductLocation> location)
	{
		if (prolamsaProductCodes == null || prolamsaProductCodes.size() == 0)
		{
			return null;
		}

		final Map<String, CustomerProductReferenceModel> mapResult = new HashMap<String, CustomerProductReferenceModel>();

		//inicia for
		for (int i = 0; i < prolamsaProductCodes.size(); i++)
		{
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT {c." + CustomerProductReferenceModel.PK + "} FROM {"
					+ CustomerProductReferenceModel._TYPECODE + " AS c JOIN ");
			queryString.append(ProductLocation._TYPECODE + " AS l  ON {c." + CustomerProductReferenceModel.LOCATION
					+ "} = {l.pk} } ");
			queryString.append("WHERE  {c." + CustomerProductReferenceModel.CUSTOMER + "} = ?unit ");
			queryString.append("AND {c." + CustomerProductReferenceModel.PRODUCTCODE + "} = ?list ");
			queryString.append("AND {c." + CustomerProductReferenceModel.LOCATION + "} = ?location ");
			//queryString.append(" AND {l.code} = ?location ");
			queryString.append("AND  {c." + CustomerProductReferenceModel.CATALOGID + "} = ?catalogId ");





			final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString());

			query.addQueryParameter("unit", unit);
			query.addQueryParameter("list", prolamsaProductCodes.get(i));
			query.addQueryParameter("location", location.get(i));
			query.addQueryParameter("catalogId", catalogId);

			final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(query);

			for (final CustomerProductReferenceModel eachRef : result.getResult())
			{
				mapResult.put(eachRef.getProductCode() + eachRef.getLocation().getCode(), eachRef);
			}
		}
		//finaliza for

		return mapResult;
	}

	@Override
	public String getProductCodeFromClientCode(final String code, final B2BUnitModel unit, final String catalogId)
	{
		final Map<String, CustomerProductReferenceModel> mapResult = new HashMap<String, CustomerProductReferenceModel>();

		// if no codes defined, return an empty map
		if (code.equals(""))
		{
			return "";
		}

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p } ");
		queryString.append("WHERE {p." + CustomerProductReferenceModel.CODE + "} = ?code ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = ?unit ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = ?catalogId ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString());

		query.addQueryParameter("unit", unit.getPk());
		query.addQueryParameter("code", code);
		query.addQueryParameter("catalogId", catalogId);

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(query);

		String codeRes = "";

		if (result.getResult().size() > 0)
		{
			codeRes = result.getResult().get(0).getProductCode();
		}

		return codeRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.CustomerProductReferenceService#getCustomerProductReferenceFor(de.hybris.platform.b2b.
	 * model.B2BUnitModel, java.lang.String)
	 */
	@Override
	public Map<String, CustomerProductReferenceModel> getCustomerProductReferenceFor(final B2BUnitModel unit,
			final String catalogId)
	{
		final Map<String, CustomerProductReferenceModel> mapResult = new HashMap<String, CustomerProductReferenceModel>();

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {c." + CustomerProductReferenceModel.PK + "}");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS c}");
		queryString.append("WHERE  {c." + CustomerProductReferenceModel.CUSTOMER + "} = ?unit ");
		queryString.append("AND  {c." + CustomerProductReferenceModel.CATALOGID + "} = ?catalogId ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString());

		query.addQueryParameter("unit", unit);
		query.addQueryParameter("catalogId", catalogId);

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(query);

		for (final CustomerProductReferenceModel eachRef : result.getResult())
		{
			mapResult.put(eachRef.getProductCode(), eachRef);
		}

		return mapResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.neoris.core.services.CustomerProductReferenceService#injectCustomerProductReferencesOn(java.util.List)
	 */
	@Override
	public void injectCustomerProductReferencesOn(final List<BackorderDetailData> backorderDetailData)
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		if (backorderDetailData == null || backorderDetailData.size() == 0)
		{
			return;
		}

		for (final BackorderDetailData eachBackOrderDetail : backorderDetailData)
		{

			final CustomerProductReferenceModel cpm = getWithProductCodeLocationAndB2BUnit(eachBackOrderDetail.getPartNumber(),
					eachBackOrderDetail.getCustomer(), baseStoreModel.getUid() + "ProductCatalog", eachBackOrderDetail.getLocation());

			if (cpm != null)
			{
				if (StringUtils.isNotBlank(cpm.getCode()))
				{
					eachBackOrderDetail.setPartNumber(cpm.getCode());
				}

				//this method is only used by backorderDetail, prolamsa is asking to only replace the product code with the custermo code, the description should 
				// the descript sent by SAP, field "POSTX" 
				//				if (StringUtils.isNotBlank(cpm.getDescription()))
				//				{
				//					eachBackOrderDetail.setDescription(cpm.getDescription());
				//				}
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.services.CustomerProductReferenceService#getWithProductCodeLocationAndB2BUnit(java.lang.String,
	 * de.hybris.platform.b2b.model.B2BUnitModel, java.lang.String)
	 */
	@Override
	public CustomerProductReferenceModel getWithProductCodeLocationAndB2BUnit(final String productCode, final B2BUnitModel unit,
			final String catalogId, final String location)
	{

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p JOIN " + ProductLocation._TYPECODE
				+ " as pl  ON {p." + CustomerProductReferenceModel.LOCATION + "} = {pl.pk} } ");
		queryString.append("WHERE {p." + CustomerProductReferenceModel.CODE + "} = '" + productCode + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = '" + catalogId + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = '" + unit.getPk() + "' ");
		queryString.append("AND {pl.code} = '_" + location + "' ");

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(queryString.toString());

		if (result == null || result.getCount() == 0)
		{
			return null;
		}
		else
		{
			return result.getResult().get(0);
		}
	}

	@Override
	public CustomerProductReferenceModel getWithProductCodeLocationAndB2BUnitExcel(final String productCode,
			final B2BUnitModel unit, final String catalogId, final String location)
	{

		final StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p JOIN " + ProductLocation._TYPECODE
				+ " as pl  ON {p." + CustomerProductReferenceModel.LOCATION + "} = {pl.pk} } ");
		queryString.append("WHERE REPLACE({p." + CustomerProductReferenceModel.CODE + "},' ', '') = '" + productCode + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = '" + catalogId + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = '" + unit.getPk() + "' ");
		queryString.append("AND {pl.code} = '_" + location + "' ");

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(queryString.toString());

		if (result == null || result.getCount() == 0)
		{
			return null;
		}
		else
		{
			return result.getResult().get(0);
		}
	}

	@Override
	public CustomerProductReferenceModel getCustomerProductReference(final String code, final B2BUnitModel unit,
			final String catalogId, final String location)
	{
		final StringBuilder queryString = new StringBuilder();

		System.out.println("Search product to create incident");
		System.out.println("code " + code);
		System.out.println("unit " + unit.getUid());
		System.out.println("catalogId " + catalogId);
		System.out.println("location " + location);

		queryString.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
		queryString.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p JOIN " + ProductLocation._TYPECODE
				+ " as pl  ON {p." + CustomerProductReferenceModel.LOCATION + "} = {pl.pk} } ");
		queryString.append("WHERE {p." + CustomerProductReferenceModel.CODE + "} = '" + code + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = '" + catalogId + "' ");
		queryString.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = '" + unit.getPk() + "' ");
		queryString.append("AND {pl.code} = '_" + location + "' ");

		final SearchResult<CustomerProductReferenceModel> result = flexibleSearchService.search(queryString.toString());

		if (result == null || result.getCount() == 0)
		{
			final StringBuilder queryString2 = new StringBuilder();
			final String productCode = code.substring(code.length() - 6);

			queryString2.append("SELECT {p." + CustomerProductReferenceModel.PK + "}  ");
			queryString2.append("FROM {" + CustomerProductReferenceModel._TYPECODE + " AS p JOIN " + ProductLocation._TYPECODE
					+ " as pl  ON {p." + CustomerProductReferenceModel.LOCATION + "} = {pl.pk} } ");
			queryString2.append("WHERE {p." + CustomerProductReferenceModel.PRODUCTCODE + "} = '" + productCode + "' ");
			queryString2.append("AND {p." + CustomerProductReferenceModel.CATALOGID + "} = '" + catalogId + "' ");
			queryString2.append("AND {p." + CustomerProductReferenceModel.CUSTOMER + "} = '" + unit.getPk() + "' ");
			queryString2.append("AND {pl.code} = '_" + location + "' ");

			final SearchResult<CustomerProductReferenceModel> result2 = flexibleSearchService.search(queryString2.toString());

			if (result2 == null || result2.getCount() == 0)
			{
				return null;
			}
			else
			{
				return result2.getResult().get(0);
			}
		}
		else
		{
			return result.getResult().get(0);
		}
	}
}
