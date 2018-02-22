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
package mx.neoris.facades.flow;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.B2BCheckoutFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.order.InvalidCartException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import mx.neoris.core.enums.B2BCheckoutFlowEnum;
import mx.neoris.core.enums.B2BCheckoutPciOptionEnum;
import mx.neoris.core.enums.PaymentTermsType;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.model.TransportationModeModel;

import org.springframework.web.multipart.MultipartFile;


/**
 * CheckoutFlowFacade interface extends the {@link CheckoutFacade}. The CheckoutFlowFacade supports resolving the
 * {@link B2BCheckoutFlowEnum} for the current request.
 * 
 * @since 4.6
 * @spring.bean checkoutFacade
 */
public interface B2BCheckoutFlowFacade extends B2BCheckoutFacade
{
	B2BCheckoutFlowEnum getCheckoutFlow();

	B2BCheckoutPciOptionEnum getSubscriptionPciOption();
	
	// NEORIS_CHANGE #74
	List<? extends AddressData> getDeliveryAdrressesFor(B2BUnitModel b2bUnitModel);
	List<? extends AddressData> getBillingAdrressesFor(B2BUnitModel b2bUnitModel);
	OrderData placeOrderWith(MultipartFile multipartFile) throws InvalidCartException;

	boolean setDeliveryAddressIfAvailableFor();
	boolean setTransportationModelIfAvailableFor();
	boolean calculateCart(CartData cartData) throws Exception;
	boolean setBillingAddressIfAvailable();
// NEORIS_CHANGE #74 Added to set transportation mode to order
	boolean setTransportationMode(TransportationModeModel transportationModeModel);
	/**
	 * @return
	 */
	boolean setPaymentTerms();
	
	PaymentTermsType getPaymentTerms();	
	
    boolean setRequestedDeliveryDate(Date requestedDeliveryDate);
    
    void setShippingInstructions(String shippingInstructions);
    
    void setQuoteNegotiablePrices(String negotiablePrices);

    
    CartData addAttachment(MultipartFile multipartFile) throws IOException;
    CartData deleteAttachment(String mediaCode) throws Exception;
    NeorisMediaModel getAttachment(String mediaCode) throws Exception;
    
    void setIsAPIOrder();
}
