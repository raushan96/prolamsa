/**
 * 
 */
package mx.neoris.facades.populators;

/**
 * @author e-lacantu
 *
 */
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


import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.converters.populator.OrderHistoryPopulator;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.enumeration.EnumerationService;

import org.springframework.beans.factory.annotation.Required;



/**
 * Converter implementation for {@link de.hybris.platform.core.model.order.OrderModel} as source and
 * {@link de.hybris.platform.commercefacades.order.data.OrderHistoryData} as target type.
 */
public class NeorisOrderHistoryPopulator extends OrderHistoryPopulator
{
	private EnumerationService enumerationService;
	private PriceDataFactory priceDataFactory;

	@Override
	public void populate(final OrderModel source, final OrderHistoryData target)
	{
		
		super.populate(source, target);
		//NEORIS_CHANGE #108 Only ShortName to avoid overflow with B2BUnit Populator
		final B2BUnitData b2bUnitData = new B2BUnitData();

		b2bUnitData.setUid(source.getUnit().getUid().toString());
		b2bUnitData.setShortName(source.getUnit().getShortName());

		//b2bUnitConverter.convert(source.getUnit(), b2bUnitData);
		target.setUnit(b2bUnitData);
	}

	/**
	 * @return the enumerationService
	 */
	public EnumerationService getEnumerationService() {
		return enumerationService;
	}

	/**
	 * @param enumerationService the enumerationService to set
	 */
	public void setEnumerationService(EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}

	/**
	 * @return the priceDataFactory
	 */
	public PriceDataFactory getPriceDataFactory() {
		return priceDataFactory;
	}

	/**
	 * @param priceDataFactory the priceDataFactory to set
	 */
	public void setPriceDataFactory(PriceDataFactory priceDataFactory) {
		this.priceDataFactory = priceDataFactory;
	}

	
	

}

