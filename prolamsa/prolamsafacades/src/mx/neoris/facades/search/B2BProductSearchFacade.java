/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.facades.search;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.Collection;


/**
 * B2B Product search facade interface. Used to retrieve products of type
 * {@link de.hybris.platform.commercefacades.product.data.ProductData} (or subclasses of).
 * 
 * @param <ITEM>
 *           The type of the product result items
 */
public interface B2BProductSearchFacade<ITEM extends ProductData>
{
	/**
	 * Initiate a new search using a collection of product ids (skus)
	 * 
	 * @param skus
	 *           the collection of product ids
	 * @return the search results
	 */
	ProductSearchPageData<SearchStateData, ITEM> searchForSkus(Collection<String> skus, PageableData pageableData);
}
