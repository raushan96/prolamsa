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
package mx.neoris.storefront.forms;

import de.hybris.platform.b2bacceleratorfacades.product.data.ProductQuantityData;

import java.util.List;





/**
 * @author fagner.santos
 * 
 */
public class AddToCartOrderForm
{

	private List<ProductQuantityData> productQuantities;

	/**
	 * @return Return the productQuantities.
	 */
	public List<ProductQuantityData> getProductQuantities()
	{
		return productQuantities;
	}

	/**
	 * @param ProductQuantities
	 *           The productQuantities to set.
	 */
	public void setProductQuantities(final List<ProductQuantityData> productQuantities)
	{
		this.productQuantities = productQuantities;
	}


}
