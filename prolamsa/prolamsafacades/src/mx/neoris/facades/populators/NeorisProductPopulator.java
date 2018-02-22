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
package mx.neoris.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.variants.model.VariantProductModel;

import mx.neoris.core.model.ProlamsaProductModel;


/**
 * All extended product properties must be implemented here
 */
public class NeorisProductPopulator implements Populator<ProductModel, ProductData>
{
	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		// if variant product, populate on the base product
		if (source instanceof VariantProductModel)
		{
			VariantProductModel variant = (VariantProductModel) source;
			
			populate(variant.getBaseProduct(), target);
		}
		
		// if prolamsa product model, fill proper attributes
		if (source instanceof ProlamsaProductModel)
		{
			ProlamsaProductModel prolamsaProduct = (ProlamsaProductModel) source;
		
			target.setName(prolamsaProduct.getName());
			target.setBaseCode(prolamsaProduct.getBaseCode());
			target.setCommercialDescription(prolamsaProduct.getName());
			target.setManufacturingDescription(prolamsaProduct.getManufacturingDescription());
	
			target.setLocation(prolamsaProduct.getLocation());
	
			target.setPiecesPerBundle(prolamsaProduct.getPiecesPerBundle());
	
			target.setBundleKgEquiv(prolamsaProduct.getBundleKgEquiv());
			target.setBundleLbEquiv(prolamsaProduct.getBundleLbEquiv());
			target.setPcKgEquiv(prolamsaProduct.getPcKgEquiv());
			target.setPcLbEquiv(prolamsaProduct.getPcLbEquiv());
			target.setKgEquiv(prolamsaProduct.getKgEquiv());
			target.setLbEquiv(prolamsaProduct.getLbEquiv());
			target.setTnEquiv(prolamsaProduct.getTnEquiv());
			target.setMtEquiv(prolamsaProduct.getMtEquiv());
			target.setFtEquiv(prolamsaProduct.getFtEquiv());
			target.setLength(prolamsaProduct.getLength());
			target.setLengthmm(prolamsaProduct.getLengthmm());
			target.setCodeForFavorites(prolamsaProduct.getCodeForFavorites());
			target.setIsAPI(prolamsaProduct.getIsAPI());
			target.setFtKgEquiv(prolamsaProduct.getFtKgEquiv());
			target.setFtLbEquiv(prolamsaProduct.getFtLbEquiv());
			target.setFtTnEquiv(prolamsaProduct.getFtTnEquiv());
			target.setIsHSS(prolamsaProduct.getIsHSS());
			
			target.setApprovalVisibility(prolamsaProduct.getApprovalVisibility());
		}
	}
}
