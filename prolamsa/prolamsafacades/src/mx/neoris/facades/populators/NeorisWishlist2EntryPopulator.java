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
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.facades.wishlist2entry.data.Wishlist2EntryData;


public class NeorisWishlist2EntryPopulator implements Populator<Wishlist2EntryModel, Wishlist2EntryData>
{
	public final static String SEPARATOR = " - ";
	
	@Resource(name = "neorisProductConverter")
	private AbstractConverter<ProductModel, ProductData> neorisProductConverter;
	
	@Override
	public void populate(final Wishlist2EntryModel source, final Wishlist2EntryData target)
	{
		ProductData productData = new ProductData();
		target.setAddedDate(source.getAddedDate());
		target.setPriority(source.getPriority());
		
		neorisProductConverter.convert(source.getProduct(),productData);		
		
		target.setProduct(productData);
		target.setRollingScheduleWeek(source.getRollingScheduleWeek());
		target.setWishlist(source.getWishlist());	
		target.setDesired(source.getDesired());
		target.setComment(source.getComment());
		target.setReceived(source.getReceived());		
		
		if (source.getApiProductConfiguration()!= null)
		{
			ProlamsaAPIProductConfigurationModel configModel = source.getApiProductConfiguration();
			
			StringBuilder productDescription = new StringBuilder();
			
			productDescription.append(source.getProduct().getName());
			productDescription.append(SEPARATOR);
			
			
			if (configModel.getPressure() != null)
			{
				productDescription.append(configModel.getPressure());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getDuration() != null)
			{
				productDescription.append(configModel.getDuration());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getSpecificStencil()))
			{
				productDescription.append(configModel.getSpecificStencil());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getSpecialDrifter() != null)
			{
				productDescription.append(configModel.getSpecialDrifter());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getSpecificLength() != null)
			{
				productDescription.append(configModel.getSpecificLength());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getLocationOfTest()))
			{
				productDescription.append(configModel.getLocationOfTest());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getSampleDirection()))
			{
				productDescription.append(configModel.getSampleDirection());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getTestTemp()!= null)
			{
				productDescription.append(configModel.getTestTemp());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getSampleSize()))
			{
				productDescription.append(configModel.getSampleSize());
				productDescription.append(SEPARATOR);
			}
			
			target.setProductDescription(productDescription.toString());
		}
	}
}
