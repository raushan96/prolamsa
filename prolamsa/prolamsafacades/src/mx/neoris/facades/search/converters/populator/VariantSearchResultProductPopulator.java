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

package mx.neoris.facades.search.converters.populator;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;


/**
 * Converter implementation for {@link de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData} as
 * source and {@link de.hybris.platform.commercefacades.product.data.ProductData} as target type. Adds all the
 * information related to multivariant products.
 */
public class VariantSearchResultProductPopulator extends SearchResultProductPopulator
{
	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		super.populate(source, target);
		target.setMultidimensional(this.<Boolean> getValue(source, "multidimensional"));
	}
}
